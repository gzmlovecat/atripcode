package com.trip.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trip.dto.AccountDto;
import com.trip.dto.AgentDto;
import com.trip.dto.AgentRewardDto;
import com.trip.dto.BookDetailDto;
import com.trip.dto.CompensateDetailDto;
import com.trip.dto.EmployeeDto;
import com.trip.dto.OrderDto;
import com.trip.dto.SettlementDetailDto;
import com.trip.entity.BookingDetailGson;
import com.trip.entity.BuyInfoFormulGson;
import com.trip.entity.DateUtils;
import com.trip.entity.Logger;
import com.trip.entity.OrderDetailGson;
import com.trip.entity.OrderQueryEntity;
import com.trip.entity.OrderQueryResultEntity;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.mapper.AgentMapper;
import com.trip.mapper.AgentRewardMapper;
import com.trip.mapper.BookDetailMapper;
import com.trip.mapper.CompensateDetailMapper;
import com.trip.mapper.EmployeeMapper;
import com.trip.mapper.OrderMapper;


@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private BookDetailMapper bookDetailMapper;

    @Autowired
    private AgentRewardMapper agentRewardMapper;

    @Autowired
    private AgentMapper agentMapper;
    
    @Autowired
    private CompensateDetailMapper compensateDetailMapper;

    /**
     * 新增订单
     * @param orderDto
     * @param currentUser
     * @return
     */

    @Transactional
    public ResultEntity addOrder(OrderDto orderDto, AccountDto currentUser){

        try{
            Long currentUserId = currentUser.getId();
            List<BookDetailDto> bookDetailDtoList = orderDto.getBookingList();
            Long orderId = 0L;
            String extraType = "";
            if( !CollectionUtils.isEmpty(bookDetailDtoList)){
                orderDto.setCreatedBy(currentUserId);
                if(currentUser.getEmployeeId() != null) {
                	List<EmployeeDto> eList = employeeMapper.selectEmployeeList(null,currentUser.getEmployeeId());
                    if( !CollectionUtils.isEmpty(eList) ){
                        orderDto.setSalesman(eList.get(0).getEmployeeName());
                    }
                }
                orderDto.setCreatedAt(new Date());
                // 代理返佣总金额
                BigDecimal agentTotal = BigDecimal.ZERO;
                // 上级代理返佣总金额
                BigDecimal superAgentBase = BigDecimal.ZERO;
                
                // add 新增代理以及上级代理返佣
                // 代理额外返佣
//                BigDecimal agentExtraTotal = BigDecimal.ZERO;
                // 上级代理额外返佣
//                BigDecimal superAgentExtraBase = BigDecimal.ZERO;
                
                // 判断购买保险个数
                for(BookDetailDto bookDetailDto : bookDetailDtoList) {
                	// 计算上级代理应付
                	// 购买数量
                	int buyAmount = bookDetailDto.getBuyAmount();
                	BigDecimal agentAmount = bookDetailDto.getAgentReturnAmount().multiply(BigDecimal.valueOf(buyAmount));
                	
                	agentTotal = agentTotal.add(agentAmount);
                	
                	// 代理额外收入
//                	BigDecimal agentExtraAmount = bookDetailDto.getAgentExtraReturnAmount().multiply(BigDecimal.valueOf(buyAmount));
//                	agentExtraTotal = agentExtraTotal.add(agentExtraAmount);
                	
                	if(bookDetailDto.getStandard().equals("成人")) {
                		superAgentBase = superAgentBase.add(agentAmount);
                		// 上级额外收益
//                		superAgentExtraBase = superAgentExtraBase.add(agentExtraAmount);
                		
                	} else if(bookDetailDto.getStandard().equals("儿童")) {
                		superAgentBase = superAgentBase.add(agentAmount);
                		// 上级额外收益
//                		superAgentExtraBase = superAgentExtraBase.add(agentExtraAmount);
                		
                	} else if(bookDetailDto.getStandard().equals("婴儿价")) {
                		superAgentBase = superAgentBase.add(agentAmount);
                		// 上级额外收益
//                		superAgentExtraBase = superAgentExtraBase.add(agentExtraAmount);
                		
                	} else if(bookDetailDto.getStandard().equals("保险")) {
                		if(bookDetailDto.getBuyAmount() > 0) {
                			orderDto.setBuyInsuranceStatus("1"); // 未完成
                		} else {
                			orderDto.setBuyInsuranceStatus("-1001"); // 无
                		}
                	}
                }
                if(bookDetailDtoList.size() > 0) {
                	extraType = bookDetailDtoList.get(0).getExtraType();
                }
                // 计算总金额
                BigDecimal radio = orderDto.getSuperAgentReturnRatio();	// 上级代理返佣系数
                BigDecimal superAgentTotal = superAgentBase.multiply(radio).setScale(0, BigDecimal.ROUND_DOWN);
                
                // 计算上级额外收益
//                BigDecimal extraRadio = orderDto.getSuperAgentExtraReturnRatio();	// 上级额外返佣系数
//                BigDecimal superAgentExtranTotal = superAgentExtraBase.multiply(extraRadio).setScale(0, BigDecimal.ROUND_DOWN);
                
                // 判断上级代理状态
                if(superAgentTotal.compareTo(BigDecimal.ZERO) > 0) {
                	orderDto.setPaySuperAgentStatus("1");	// 未申请
                } else {
                	orderDto.setPaySuperAgentStatus("-1001");	// 无
                }
                
                // 判断定金收款状态
                if(orderDto.getDepositAmount().compareTo(BigDecimal.ZERO) > 0) {
                	orderDto.setDepositStatus("1"); // 未完成
                } else {
                	orderDto.setDepositStatus("-1001"); // 无
                }
                
                // 判断通过付款状态，设置付款代理状态
                if("1".equals(orderDto.getPayType())) {
                	orderDto.setPayAgentStatus("1"); // 未申请
                } else if("2".equals(orderDto.getPayType())) {
                	orderDto.setPayAgentStatus("4"); // 已返佣
                	orderDto.setActualPayAgentAmount(agentTotal);
                }
                
                // 新增没有退款，所以退款状态为-1001
                orderDto.setRefundStatus("-1001");
                
                orderMapper.insertOrder(orderDto);
                orderId =orderDto.getId();

                Long version = Long.valueOf(DateUtils.format(DateUtils.CMS_DRAW_SEQUENCE_FORMAT,new Date()));
                for(BookDetailDto bookDetailDto : bookDetailDtoList){
                    bookDetailDto.setOrderId(orderId);
                    bookDetailDto.setCreatedBy(currentUserId);
                    bookDetailDto.setCreatedAt(new Date());
                    bookDetailDto.setVersion(version);
                    bookDetailMapper.insertBookDetail(bookDetailDto);
                }
            }else{
                return new ResultEntity(ResultEnum.ORDER_NO_BOOK_DETAIL);
            }

            ResultEntity<OrderDetailGson> resultEntity = this.queryOrderDetail(orderId,currentUser);

            //生成返佣信息
            AgentRewardDto agentRewardDto = new AgentRewardDto();
            agentRewardDto.setOrderId(orderId);
            Long agentId = orderDto.getAgentId();
            agentRewardDto.setAgentId(agentId);
            AgentDto agent = agentMapper.selectOneAgent(agentId);
            String agentName = agent!=null?agent.getAgentName():"";
            agentRewardDto.setAgentName(agentName);
            agentRewardDto.setRewardReason("agentReward");
            Map rewardAgentMap = resultEntity.getData().getFinancePaidAgent();
            BigDecimal rewardAgentAmount = BigDecimal.ZERO;
            if( rewardAgentMap != null ){
                rewardAgentAmount = BigDecimal.valueOf(Double.valueOf(rewardAgentMap.get("receivable").toString()));
            }
            agentRewardDto.setRewardAmount(rewardAgentAmount);
            agentRewardDto.setCreatedBy(currentUser.getId());
            agentRewardDto.setCreatedAt(new Date());
            
            // 收益类型 1:订单 2:下级返佣 3:推荐奖励
            agentRewardDto.setRewardType("1");
            BigDecimal agentExtraAmount = BigDecimal.ZERO;
            // 判断这个用户的代理是不是VIP或者创享
            if("2".equals(agent.getAgentType()) || "3".equals(agent.getAgentType())) {
            	if( rewardAgentMap != null ){
            		agentExtraAmount = BigDecimal.valueOf(Double.valueOf(rewardAgentMap.get("agentExtraAmount").toString()));
                }
            	// 额外收益
            	agentRewardDto.setRewardExtraAmount(agentExtraAmount);
                // 额外收益类型
            	agentRewardDto.setExtraType(extraType);
            }
            // 代理支付状态
            agentRewardDto.setPayAgentStatus(orderDto.getPayAgentStatus());
            // 是否订单导入的状态 1:是 0:否
            agentRewardDto.setOrderYn("1");
            
            agentRewardMapper.insertAgentReward(agentRewardDto);

            //生成上级返佣信息
            if( orderDto.getSuperAgent() != null ){
                AgentRewardDto superAgentRewardDto = new AgentRewardDto();
                superAgentRewardDto.setOrderId(orderId);
                superAgentRewardDto.setAgentId(orderDto.getSuperAgent());
                AgentDto superAgent = agentMapper.selectOneAgent(orderDto.getSuperAgent());
                if( superAgent != null ){
                    superAgentRewardDto.setAgentName(superAgent.getAgentName());
                }
                superAgentRewardDto.setSubAgentId(orderDto.getAgentId());
                superAgentRewardDto.setSubAgentName(agentName);
                superAgentRewardDto.setRewardReason("superAgentReward");
                Map rewardSuperAgentMap = resultEntity.getData().getFinancePaidSuperAgent();
                BigDecimal rewardSuperAgentAmount = BigDecimal.ZERO;
                if( rewardAgentMap != null ){
                    rewardSuperAgentAmount = BigDecimal.valueOf(Double.valueOf(rewardSuperAgentMap.get("receivable").toString()));
                }
                superAgentRewardDto.setRewardAmount(rewardSuperAgentAmount);
                superAgentRewardDto.setCreatedBy(currentUser.getId());
                superAgentRewardDto.setCreatedAt(new Date());
                
                // 收益类型 1:订单 2:下级返佣 3:推荐奖励
                superAgentRewardDto.setRewardType("2");
                BigDecimal superAgentExtraAmount = BigDecimal.ZERO;
                // 判断这个用户的代理是不是VIP或者创享
                if("2".equals(superAgent.getAgentType()) || "3".equals(superAgent.getAgentType())) {
                	if( rewardSuperAgentMap != null ){
                		superAgentExtraAmount = BigDecimal.valueOf(Double.valueOf(rewardSuperAgentMap.get("agentExtraAmount").toString()));
                    }
                	// 额外收益
                	superAgentRewardDto.setRewardExtraAmount(superAgentExtraAmount);
                    // 额外收益类型
                	superAgentRewardDto.setExtraType(extraType);
                }
                // 代理支付状态
                superAgentRewardDto.setPayAgentStatus(orderDto.getPaySuperAgentStatus());
                // 是否订单导入的状态 1:是 0:否
                superAgentRewardDto.setOrderYn("1");
                
                agentRewardMapper.insertAgentReward(superAgentRewardDto);
            }

            return resultEntity;
        }catch (Exception e){
        	System.out.println(e.getMessage());
            Logger.error(this,"addOrder error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }


    /**
     * 
     * @param orderQueryEntity
     * @param currentUser
     * @param exportYn 0:否 1:是
     * @return
     */
    public ResultEntity queryOrder(OrderQueryEntity orderQueryEntity,AccountDto currentUser, String exportYn){

        try{
//            if( "2".equals(currentUser.getAccountType())){
//            	// modify by 20180802 bug5 销售人员自身可见 start
////                orderQueryEntity.setCreatedBy(currentUser.getCreatedBy());
//            	orderQueryEntity.setCreatedBy(currentUser.getId());
//                // modify by 20180802 bug5 销售人员自身可见 end
//            }
            
//            orderQueryEntity.setPageNum(2);
//            orderQueryEntity.setPageSize(5);
            int pageSize = 30;
            int pageNum = 1;
            if("1".equals(exportYn)) {
            	pageSize = 99999;
            }
            if(orderQueryEntity.getPageSize() != null &&  !"".equals(orderQueryEntity.getPageSize())) {
            	pageSize = Integer.valueOf(orderQueryEntity.getPageSize());
            }
            if(orderQueryEntity.getPageNum() != null &&  !"".equals(orderQueryEntity.getPageNum())) {
            	pageNum = Integer.valueOf(orderQueryEntity.getPageNum());
            }
            PageHelper.startPage(pageNum, pageSize);
//            PageHelper.startPage(44, 3);
            List<OrderDto> list = orderMapper.selectOrderList(orderQueryEntity);
            PageInfo<OrderDto> pageList = new PageInfo<OrderDto>(list);
            Long totalPageSize = pageList.getTotal();
            Long modTotalPageNum = totalPageSize % pageSize;
            Long totalPageNum = modTotalPageNum == 0 ? totalPageSize / pageSize : totalPageSize / pageSize + 1;
            
            List<OrderQueryResultEntity> resultList = new ArrayList<>();
            for(OrderDto orderDto : pageList.getList() ){
                Long id = orderDto.getId();
                Map<String,Long> map = bookDetailMapper.selectBookDetailVersion(orderDto.getId());
                Long maxVersion = map.get("maxVersion");
                BookDetailDto bookDetailDto = new BookDetailDto();
                bookDetailDto.setOrderId(id);
                bookDetailDto.setVersion(maxVersion);
                List<BookDetailDto> bookDetailDtoList = bookDetailMapper.selectBookDetailList(bookDetailDto);
                OrderDetailGson orderDetailGson = new OrderDetailGson();
                this.handleBookingDetail(orderDetailGson,bookDetailDtoList,orderDto,true,true);

                OrderQueryResultEntity orderQueryResult = new OrderQueryResultEntity();
                orderQueryResult.setSuperAgentName(orderDto.getSuperAgentName());
                BeanUtils.copyProperties(orderDto,orderQueryResult);
                
                // add by 20180812 新增功能20，优化21 start
                orderQueryResult.setBuyInfoFinalFormula(orderDetailGson.getBuyInfoFinalFormula());
                orderQueryResult.setBuyInfoFinal(orderDetailGson.getBuyInfoFinal());
                // add by 20180812 新增功能20，优化21 end

                // receiptAmount 财务-收款
                orderQueryResult.setrAReceivable((BigDecimal) orderDetailGson.getFinanceReceipt().get("receivable"));// 应付
                orderQueryResult.setrAActualReceipt((BigDecimal) orderDetailGson.getFinanceReceipt().get("actualReceipt"));//实收
                orderQueryResult.setrAStatus((String) orderDetailGson.getFinanceReceipt().get("status"));//状态
                // 应收金额 deposit
                orderQueryResult.setrADeposit((BigDecimal)orderDetailGson.getFinanceReceipt().get("depositAmount"));// 定金
                orderQueryResult.setrASurplus((BigDecimal) orderDetailGson.getFinanceReceipt().get("surplus"));
                

                // agentAmount 财务-付款代理
                orderQueryResult.setaAReceivable((BigDecimal) orderDetailGson.getFinancePaidAgent().get("receivable"));
                orderQueryResult.setaAActualReceipt((BigDecimal) orderDetailGson.getFinancePaidAgent().get("actualReceipt"));
                orderQueryResult.setaAStatus((String) orderDetailGson.getFinancePaidAgent().get("status"));
                orderQueryResult.setaASurplus((BigDecimal) orderDetailGson.getFinancePaidAgent().get("surplus"));

                // superAgentName 财务-付款上级代理
                orderQueryResult.setsAAReceivable((BigDecimal) orderDetailGson.getFinancePaidSuperAgent().get("receivable"));
                orderQueryResult.setsAAActualReceipt((BigDecimal) orderDetailGson.getFinancePaidSuperAgent().get("actualReceipt"));
                orderQueryResult.setsAAStatus((String) orderDetailGson.getFinancePaidSuperAgent().get("status"));
                orderQueryResult.setsAASurplus((BigDecimal) orderDetailGson.getFinancePaidSuperAgent().get("surplus"));

                // supplierAmount 财务-付款供应商
                orderQueryResult.setsAReceivable((BigDecimal) orderDetailGson.getFinancePaidSupplier().get("receivable"));
                orderQueryResult.setsAActualReceipt((BigDecimal) orderDetailGson.getFinancePaidSupplier().get("actualReceipt"));
                orderQueryResult.setsAStatus((String) orderDetailGson.getFinancePaidSupplier().get("status"));
                orderQueryResult.setsASurplus((BigDecimal) orderDetailGson.getFinancePaidSupplier().get("surplus"));

                // refundAmount 财务-付款用户退款赔偿
                orderQueryResult.setrFAReceivable((BigDecimal) orderDetailGson.getFinancePaidRefund().get("receivable"));
                orderQueryResult.setrFAActualReceipt((BigDecimal) orderDetailGson.getFinancePaidRefund().get("actualReceipt"));
                orderQueryResult.setrFAStatus((String) orderDetailGson.getFinancePaidRefund().get("status"));
                orderQueryResult.setrFASurplus((BigDecimal) orderDetailGson.getFinancePaidRefund().get("surplus"));
                
                // 结算价
                List<SettlementDetailDto> settlementDetailDtoList = new ArrayList<SettlementDetailDto>();
                for(BookDetailDto bookDto : bookDetailDtoList){
                	//bookDetailDto.getStandard().equals("成人") 
                	if(bookDto.getStandard().equals("成人")) {
                		SettlementDetailDto settlementDto = new SettlementDetailDto();
                		settlementDto.setStandard(bookDto.getStandard());
                		settlementDto.setSettlementPrice(orderDto.getAdultSettlementPrice());
                		settlementDetailDtoList.add(settlementDto);
                	} else if(bookDto.getStandard().equals("儿童")) {
                		SettlementDetailDto settlementDto = new SettlementDetailDto();
                		settlementDto.setStandard(bookDto.getStandard());
                		settlementDto.setSettlementPrice(orderDto.getChildrenSettlementPrice());
                		settlementDetailDtoList.add(settlementDto);
                	} else if(bookDto.getStandard().equals("婴儿价")) {
                		SettlementDetailDto settlementDto = new SettlementDetailDto();
                		settlementDto.setStandard(bookDto.getStandard());
                		settlementDto.setSettlementPrice(orderDto.getBabySettlementPrice());
                		settlementDetailDtoList.add(settlementDto);
                	} else if(bookDto.getStandard().equals("单房差")) {
                		SettlementDetailDto settlementDto = new SettlementDetailDto();
                		settlementDto.setStandard(bookDto.getStandard());
                		settlementDto.setSettlementPrice(orderDto.getSingleRoomBalanceSettlementPrice());
                		settlementDetailDtoList.add(settlementDto);
                	} else if(bookDto.getStandard().equals("附加费")) {
                		SettlementDetailDto settlementDto = new SettlementDetailDto();
                		settlementDto.setStandard(bookDto.getStandard());
                		settlementDto.setSettlementPrice(orderDto.getPlusFeeSettlementPrice());
                		settlementDetailDtoList.add(settlementDto);
                	} else if(bookDto.getStandard().equals("签证")) {
                		SettlementDetailDto settlementDto = new SettlementDetailDto();
                		settlementDto.setStandard(bookDto.getStandard());
                		settlementDto.setSettlementPrice(orderDto.getVisaFeeSettlementPrice());
                		settlementDetailDtoList.add(settlementDto);
                	} else if(bookDto.getStandard().equals("保险")) {
                		SettlementDetailDto settlementDto = new SettlementDetailDto();
                		settlementDto.setStandard(bookDto.getStandard());
                		settlementDto.setSettlementPrice(orderDto.getInsuranceFeeSettlementPrice());
                		settlementDetailDtoList.add(settlementDto);
                	}
                }

                orderQueryResult.setBookingList(bookDetailDtoList);
                // 添加结算价
                orderQueryResult.setVerifyBill(settlementDetailDtoList);
                resultList.add(orderQueryResult);
            }
            ResultEntity resultEntity = new ResultEntity(ResultEnum.SUCCESS,resultList);
            resultEntity.setTotalPageNum(totalPageNum);
            resultEntity.setTotalPageSize(totalPageSize);
            return resultEntity;
        }catch (Exception e){
        	System.out.println("queryOrder error : " + e.getMessage());
            Logger.error(this,"queryOrder error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }

    public ResultEntity<OrderDetailGson> queryOrderDetail(Long orderId,AccountDto currentUser){
        OrderDetailGson orderDetailGson = new OrderDetailGson();
        try{
            OrderQueryEntity orderQueryEntity = new OrderQueryEntity();
            orderQueryEntity.setOrderId(orderId.toString());
            List<OrderDto> list = orderMapper.selectOrderList(orderQueryEntity);
            if( !CollectionUtils.isEmpty(list)){

                //订单信息
                OrderDto orderDto = list.get(0);
                
                orderDetailGson.setDepositAmount(orderDto.getDepositAmount());
                // 把数据库里面的财务的状态和实付都取出来
                orderDetailGson.setGatheringStatus(orderDto.getGatheringStatus()); // 财务状态
                orderDetailGson.setActualReceiptAmount(orderDto.getActualReceiptAmount()); // 财务收款
                orderDetailGson.setPayAgentStatus(orderDto.getPayAgentStatus()); // 代理状态
                orderDetailGson.setActualPayAgentAmount(orderDto.getActualPayAgentAmount());	// 代理实付
                orderDetailGson.setPaySuperAgentStatus(orderDto.getPaySuperAgentStatus());	// 上级代理状态
                orderDetailGson.setActualPaySuperAgentAmount(orderDto.getActualPaySuperAgentAmount());	// 上级代理实付
                orderDetailGson.setPaySupplierStatus(orderDto.getPaySupplierStatus());	// 供应商状态
                orderDetailGson.setActualPaySupplieAmount(orderDto.getActualPaySupplieAmount());	// 供应商实付
                orderDetailGson.setRefundStatus(orderDto.getRefundStatus());	// 退款/赔偿状态
                orderDetailGson.setActualPayUserRefundAmount(orderDto.getActualPayUserRefundAmount());	// 退款/赔偿实付

                Map<String,Long> map = bookDetailMapper.selectBookDetailVersion(orderDto.getId());
                Long minVersion = map.get("minVersion");
                Long maxVersion = map.get("maxVersion");

                //初始订购信息
                BookDetailDto param = new BookDetailDto();
                param.setId(orderId);
                param.setVersion(minVersion);
                List<BookDetailDto> initBookDetailDtoList = bookDetailMapper.selectBookDetailList(param);

                //最终订购信息
                param.setVersion(maxVersion);
                List<BookDetailDto> finalDetailDtoList = bookDetailMapper.selectBookDetailList(param);

                orderDetailGson.setBookingList(finalDetailDtoList);

                //组装返回值

                orderDetailGson.setOrder(orderDto);
                orderDetailGson.setOrderId(orderId);

                List<EmployeeDto> eList = employeeMapper.selectEmployeeList(null,currentUser.getId());
                if( !CollectionUtils.isEmpty(eList) ){
                    orderDetailGson.setSalesman(eList.get(0).getEmployeeName());
                }
                orderDetailGson.setOrderCreateStartTime(orderDto.getCreatedAt());

                String accountType = currentUser.getAccountType();
                boolean needPurchase = (accountType.equals("1")||accountType.equals(4)) ?true:false;
                //先处理初始订单
                this.handleBookingDetail(orderDetailGson,initBookDetailDtoList,orderDto,needPurchase,false);
                //再处理最终订单
                this.handleBookingDetail(orderDetailGson,finalDetailDtoList,orderDto,needPurchase,true);

                Map mixStatus = new HashMap();
                mixStatus.put("tripNotifyStatus",orderDto.getTripNotifyStatus());
                mixStatus.put("contractSignStatus",orderDto.getContractSignStatus());
                mixStatus.put("buyInsuranceStatus",orderDto.getBuyInsuranceStatus());
                orderDetailGson.setMixStatus(mixStatus);



            }else{
                return new ResultEntity(ResultEnum.ORDER_NO_BOOK_DETAIL);
            }

            return new ResultEntity(ResultEnum.SUCCESS,orderDetailGson);
        }catch (Exception e){
            Logger.error(this,"queryOrderDetail error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }

    public void handleBookingDetail(OrderDetailGson orderDetailGson,List<BookDetailDto> bookDetailDtoList,
                                    OrderDto orderDto, boolean needPurchase, boolean finalVersion) throws Exception{
        BookingDetailGson salesBookingDetail = new BookingDetailGson();
        salesBookingDetail.setType("销售");
        BookingDetailGson agentBookingDetail = new BookingDetailGson();
        agentBookingDetail.setType("代理");
        // modify 20180812 新增功能25 增加财务结算单价 start
        BookingDetailGson purchaseBookingDetail = new BookingDetailGson();
        // 原采购价改为产品采购单价
//        purchaseBookingDetail.setType("采购");
        purchaseBookingDetail.setType("产品采购单价");
        // 增加财务结算单价
        BookingDetailGson settlementBookingDetail = new BookingDetailGson();
        settlementBookingDetail.setType("财务结算单价");
        // modify 20180812 新增功能25 增加财务结算单价 end

        // 销售金额
        BigDecimal salesTotal = BigDecimal.ZERO;
        // 代理返佣总金额
        BigDecimal agentTotal = BigDecimal.ZERO;
        // 上级代理返佣总金额
        BigDecimal superAgentBase = BigDecimal.ZERO;
        // 采购金额
        BigDecimal purchaseTotal = BigDecimal.ZERO;
        // 应付结算金额
        BigDecimal settlementTotal = BigDecimal.ZERO;
        
        // 代理额外返佣总金额
        BigDecimal agentExtraTotal = BigDecimal.ZERO;
        // 上级代理额外返佣总金额
//        BigDecimal superAgentExtraBase = BigDecimal.ZERO;

        for(BookDetailDto bookDetailDto : bookDetailDtoList){
            int buyAmount = bookDetailDto.getBuyAmount();
            BigDecimal salesAmount = bookDetailDto.getSalePrice().multiply(BigDecimal.valueOf(buyAmount) );
            BigDecimal agentAmount = bookDetailDto.getAgentReturnAmount().multiply(BigDecimal.valueOf(buyAmount) );

            salesTotal = salesTotal.add(salesAmount);
            agentTotal = agentTotal.add(agentAmount);
            
            BigDecimal agentExtraReturnAmount = bookDetailDto.getAgentExtraReturnAmount();
            agentExtraReturnAmount = agentExtraReturnAmount == null ? BigDecimal.ZERO : agentExtraReturnAmount;
            
            BigDecimal agentExtraAmount = agentExtraReturnAmount.multiply(BigDecimal.valueOf(buyAmount));
            agentExtraTotal = agentExtraTotal.add(agentExtraAmount);

            if( bookDetailDto.getStandard().equals("成人") ){
                salesBookingDetail.setAdultPurchasePrice(salesAmount);
                agentBookingDetail.setAdultPurchasePrice(agentAmount);

                superAgentBase = superAgentBase.add(agentAmount);
//                superAgentExtraBase = superAgentExtraBase.add(agentAmount);

                BigDecimal amount = orderDto.getAdultPurchasePrice().multiply(BigDecimal.valueOf(buyAmount));
                purchaseBookingDetail.setAdultPurchasePrice(amount);

                purchaseTotal = purchaseTotal.add(amount);
                
                // 成人结算金额
                BigDecimal settlementAmount;
                if("0".equals(orderDto.getSettlementPriceSaveYn())) {
                	settlementAmount = BigDecimal.ZERO;
                } else {
                	settlementAmount = orderDto.getAdultSettlementPrice().multiply(BigDecimal.valueOf(buyAmount));
                }
                settlementBookingDetail.setAdultPurchasePrice(settlementAmount);
                settlementTotal = settlementTotal.add(settlementAmount);
                
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” start
                // 成人单价
                salesBookingDetail.setAdultSalePrice(bookDetailDto.getSalePrice());
                agentBookingDetail.setAdultSalePrice(bookDetailDto.getAgentReturnAmount());
                purchaseBookingDetail.setAdultSalePrice(orderDto.getAdultPurchasePrice());
                settlementBookingDetail.setAdultSalePrice(orderDto.getAdultSettlementPrice());
                // 成人购买数量
                salesBookingDetail.setAdultBuyAmount(buyAmount);
                agentBookingDetail.setAdultBuyAmount(buyAmount);
                purchaseBookingDetail.setAdultBuyAmount(buyAmount);
                settlementBookingDetail.setAdultBuyAmount(buyAmount);
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” end

            }else if( bookDetailDto.getStandard().equals("儿童") ){
                salesBookingDetail.setChildrenPurchasePrice(salesAmount);
                agentBookingDetail.setChildrenPurchasePrice(agentAmount);

                superAgentBase = superAgentBase.add(agentAmount);
//                superAgentExtraBase = superAgentExtraBase.add(agentAmount);

                BigDecimal amount = orderDto.getChildrenPurchasePrice().multiply(BigDecimal.valueOf(buyAmount));
                purchaseBookingDetail.setChildrenPurchasePrice(amount);

                purchaseTotal = purchaseTotal.add(amount);
                
                // 儿童结算金额
                BigDecimal settlementAmount;
                if("0".equals(orderDto.getSettlementPriceSaveYn())) {
                	settlementAmount = BigDecimal.ZERO;
                } else {
                	settlementAmount = orderDto.getChildrenSettlementPrice().multiply(BigDecimal.valueOf(buyAmount));
                }
                settlementBookingDetail.setChildrenPurchasePrice(settlementAmount);
                settlementTotal = settlementTotal.add(settlementAmount);
                
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” start
                // 儿童单价
                salesBookingDetail.setChildrenSalePrice(bookDetailDto.getSalePrice());
                agentBookingDetail.setChildrenSalePrice(bookDetailDto.getAgentReturnAmount());
                purchaseBookingDetail.setChildrenSalePrice(orderDto.getChildrenPurchasePrice());
                settlementBookingDetail.setChildrenSalePrice(orderDto.getChildrenSettlementPrice());
                // 儿童购买数量
                salesBookingDetail.setChildrenBuyAmount(buyAmount);
                agentBookingDetail.setChildrenBuyAmount(buyAmount);
                purchaseBookingDetail.setChildrenBuyAmount(buyAmount);
                settlementBookingDetail.setChildrenBuyAmount(buyAmount);
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” end

            }else if( bookDetailDto.getStandard().equals("婴儿价") ){
                salesBookingDetail.setBabyPurchasePrice(salesAmount);
                agentBookingDetail.setBabyPurchasePrice(agentAmount);

                superAgentBase = superAgentBase.add(agentAmount);
//                superAgentExtraBase = superAgentExtraBase.add(agentAmount);

                BigDecimal amount = orderDto.getBabyPurchasePrice().multiply(BigDecimal.valueOf(buyAmount));
                purchaseBookingDetail.setBabyPurchasePrice(amount);

                purchaseTotal = purchaseTotal.add(amount);
                
                // 婴儿结算金额
                BigDecimal settlementAmount;
                if("0".equals(orderDto.getSettlementPriceSaveYn())) {
                	settlementAmount = BigDecimal.ZERO;
                } else {
                	settlementAmount = orderDto.getBabySettlementPrice().multiply(BigDecimal.valueOf(buyAmount));
                }
                settlementBookingDetail.setBabyPurchasePrice(settlementAmount);
                settlementTotal = settlementTotal.add(settlementAmount);
                
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” start
                // 婴儿单价
                salesBookingDetail.setBabySalePrice(bookDetailDto.getSalePrice());
                agentBookingDetail.setBabySalePrice(bookDetailDto.getAgentReturnAmount());
                purchaseBookingDetail.setBabySalePrice(orderDto.getBabyPurchasePrice());
                settlementBookingDetail.setBabySalePrice(orderDto.getBabySettlementPrice());
                // 婴儿购买数量
                salesBookingDetail.setBabyBuyAmount(buyAmount);
                agentBookingDetail.setBabyBuyAmount(buyAmount);
                purchaseBookingDetail.setBabyBuyAmount(buyAmount);
                settlementBookingDetail.setBabyBuyAmount(buyAmount);
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” end

            }else if( bookDetailDto.getStandard().equals("单房差") ){
                salesBookingDetail.setSingleRoomBalance(salesAmount);
                agentBookingDetail.setSingleRoomBalance(agentAmount);

                BigDecimal amount = orderDto.getSingleRoomBalance().multiply(BigDecimal.valueOf(buyAmount));
                purchaseBookingDetail.setSingleRoomBalance(amount);

                purchaseTotal = purchaseTotal.add(amount);
                
                // 单房差结算金额
                BigDecimal settlementAmount;
                if("0".equals(orderDto.getSettlementPriceSaveYn())) {
                	settlementAmount = BigDecimal.ZERO;
                } else {
                	settlementAmount = orderDto.getSingleRoomBalanceSettlementPrice().multiply(BigDecimal.valueOf(buyAmount));
                }
                settlementBookingDetail.setSingleRoomBalance(settlementAmount);
                settlementTotal = settlementTotal.add(settlementAmount);
                
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” start
                // 单房差单价
                salesBookingDetail.setSingleRoomBalanceSalePrice(bookDetailDto.getSalePrice());
                agentBookingDetail.setSingleRoomBalanceSalePrice(bookDetailDto.getAgentReturnAmount());
                purchaseBookingDetail.setSingleRoomBalanceSalePrice(orderDto.getSingleRoomBalance());
                settlementBookingDetail.setSingleRoomBalanceSalePrice(orderDto.getSingleRoomBalanceSettlementPrice());
                // 单房差购买数量
                salesBookingDetail.setSingleRoomBalanceBuyAmount(buyAmount);
                agentBookingDetail.setSingleRoomBalanceBuyAmount(buyAmount);
                purchaseBookingDetail.setSingleRoomBalanceBuyAmount(buyAmount);
                settlementBookingDetail.setSingleRoomBalanceBuyAmount(buyAmount);
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” end

            }else if( bookDetailDto.getStandard().equals("附加费") ){
                salesBookingDetail.setPlusFee(salesAmount);
                agentBookingDetail.setPlusFee(agentAmount);

                BigDecimal amount = orderDto.getPlusFee().multiply(BigDecimal.valueOf(buyAmount));
                purchaseBookingDetail.setPlusFee(amount);

                purchaseTotal = purchaseTotal.add(amount);
                
                // 附加费结算金额
                BigDecimal settlementAmount;
                if("0".equals(orderDto.getSettlementPriceSaveYn())) {
                	settlementAmount = BigDecimal.ZERO;
                } else {
                	settlementAmount = orderDto.getPlusFeeSettlementPrice().multiply(BigDecimal.valueOf(buyAmount));
                }
                settlementBookingDetail.setPlusFee(settlementAmount);
                settlementTotal = settlementTotal.add(settlementAmount);
                
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” start
                // 附加费单价
                salesBookingDetail.setPlusFeeSalePrice(bookDetailDto.getSalePrice());
                agentBookingDetail.setPlusFeeSalePrice(bookDetailDto.getAgentReturnAmount());
                purchaseBookingDetail.setPlusFeeSalePrice(orderDto.getPlusFee());
                settlementBookingDetail.setPlusFeeSalePrice(orderDto.getPlusFeeSettlementPrice());
                // 附加费购买数量
                salesBookingDetail.setPlusFeeBuyAmount(buyAmount);
                agentBookingDetail.setPlusFeeBuyAmount(buyAmount);
                purchaseBookingDetail.setPlusFeeBuyAmount(buyAmount);
                settlementBookingDetail.setPlusFeeBuyAmount(buyAmount);
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” end

            }else if( bookDetailDto.getStandard().equals("签证") ){
                salesBookingDetail.setVisaFee(salesAmount);
                agentBookingDetail.setVisaFee(agentAmount);

                BigDecimal amount = orderDto.getVisaFee().multiply(BigDecimal.valueOf(buyAmount));
                purchaseBookingDetail.setVisaFee(amount);

                purchaseTotal = purchaseTotal.add(amount);
                
                // 签证费结算金额
                BigDecimal settlementAmount;
                if("0".equals(orderDto.getSettlementPriceSaveYn())) {
                	settlementAmount = BigDecimal.ZERO;
                } else {
                	settlementAmount = orderDto.getVisaFeeSettlementPrice().multiply(BigDecimal.valueOf(buyAmount));
                }
                settlementBookingDetail.setVisaFee(settlementAmount);
                settlementTotal = settlementTotal.add(settlementAmount);
                
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” start
                // 签证单价
                salesBookingDetail.setVisaFeeSalePrice(bookDetailDto.getSalePrice());
                agentBookingDetail.setVisaFeeSalePrice(bookDetailDto.getAgentReturnAmount());
                purchaseBookingDetail.setVisaFeeSalePrice(orderDto.getVisaFee());
                settlementBookingDetail.setVisaFeeSalePrice(orderDto.getVisaFeeSettlementPrice());
                // 签证购买数量
                salesBookingDetail.setVisaFeeBuyAmount(buyAmount);
                agentBookingDetail.setVisaFeeBuyAmount(buyAmount);
                purchaseBookingDetail.setVisaFeeBuyAmount(buyAmount);
                settlementBookingDetail.setVisaFeeBuyAmount(buyAmount);
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” end

            }else if( bookDetailDto.getStandard().equals("保险") ){
                salesBookingDetail.setInsuranceFee(salesAmount);
                agentBookingDetail.setInsuranceFee(agentAmount);

                BigDecimal amount = orderDto.getInsuranceFee().multiply(BigDecimal.valueOf(buyAmount));
                purchaseBookingDetail.setInsuranceFee(amount);

                purchaseTotal = purchaseTotal.add(amount);
                
                // 保险费结算金额
                BigDecimal settlementAmount;
                if("0".equals(orderDto.getSettlementPriceSaveYn())) {
                	settlementAmount = BigDecimal.ZERO;
                } else {
                	settlementAmount = orderDto.getInsuranceFeeSettlementPrice().multiply(BigDecimal.valueOf(buyAmount));
                }
                settlementBookingDetail.setInsuranceFee(settlementAmount);
                settlementTotal = settlementTotal.add(settlementAmount);
                
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” start
                // 保险单价
                salesBookingDetail.setInsuranceFeeSalePrice(bookDetailDto.getSalePrice());
                agentBookingDetail.setInsuranceFeeSalePrice(bookDetailDto.getAgentReturnAmount());
                purchaseBookingDetail.setInsuranceFeeSalePrice(orderDto.getInsuranceFee());
                settlementBookingDetail.setInsuranceFeeSalePrice(orderDto.getInsuranceFeeSettlementPrice());
                // 保险购买数量
                salesBookingDetail.setInsuranceFeeBuyAmount(buyAmount);
                agentBookingDetail.setInsuranceFeeBuyAmount(buyAmount);
                purchaseBookingDetail.setInsuranceFeeBuyAmount(buyAmount);
                settlementBookingDetail.setInsuranceFeeBuyAmount(buyAmount);
                // add by 20180804 bug3 订单明细页里“初始购买信息”和“最终购买信息”的拆成“单价 * 人数” end
            }
        }

        List<BookingDetailGson> bookingDetailGsonList = new ArrayList<>();
        bookingDetailGsonList.add(salesBookingDetail);
        bookingDetailGsonList.add(agentBookingDetail);
        // 增加结算价
        bookingDetailGsonList.add(settlementBookingDetail);
        if( needPurchase ){
            bookingDetailGsonList.add(purchaseBookingDetail);
        }

        //计算总金额
        BigDecimal ratio = orderDto.getSuperAgentReturnRatio();	// 上级代理返佣系数
        BigDecimal superAgentTotal = superAgentBase.multiply(ratio);//(成人返利＋儿童返利＋婴儿返利）*系数,取整数，小数位扔掉
        superAgentTotal = superAgentTotal.setScale(0,BigDecimal.ROUND_DOWN);
        
        BigDecimal extraRatio = orderDto.getSuperAgentExtraReturnRatio();	// 上级代理额外返佣系数
        extraRatio = extraRatio == null ? BigDecimal.ZERO : extraRatio;
        BigDecimal superAgentExtraTotal = superAgentBase.multiply(extraRatio);	// (成人返利＋儿童返利＋婴儿返利）*系数,取整数，小数位扔掉
//        BigDecimal superAgentExtraTotal = agentExtraTotal.multiply(extraRatio);		// 代理额外收益*系数,取整数，小数位扔掉
        superAgentExtraTotal = superAgentExtraTotal.setScale(0, BigDecimal.ROUND_DOWN);

        // 购买信息
        BuyInfoFormulGson buyInfoFormula = new BuyInfoFormulGson();
        buyInfoFormula.setAgentTotal(agentTotal);
        buyInfoFormula.setPurchaseTotal(purchaseTotal);
        buyInfoFormula.setSalesTotal(salesTotal);
        buyInfoFormula.setUpAgentRefundTotal(superAgentTotal);
        // 结算总金额
        buyInfoFormula.setSettlementTotal(settlementTotal);
        

        if( finalVersion ){
        	 // 取得该订单ID的赔偿合计金额
            Map<String,BigDecimal> map = compensateDetailMapper.selectCompensateDetailPriceByParam(orderDto.getId());
            BigDecimal compensationPriceTotal = map == null ? BigDecimal.ZERO : map.get("compensationPrice");
            buyInfoFormula.setCompensateTotal(compensationPriceTotal);
            
            orderDetailGson.setBuyInfoFinal(bookingDetailGsonList);
            orderDetailGson.setBuyInfoFinalFormula(buyInfoFormula);

            /*****************财务收款*********************************************/
            Map financeReceipt = new HashMap();
            financeReceipt.put("status",orderDto.getGatheringStatus());
            String payType = orderDto.getPayType();
            financeReceipt.put("payType",payType);

            /**
             * 应收总金额
             * 当付款方＝用户实付，销售价 * 购买数量
             * 当付款方＝代理实付，销售价 * 购买数量－代理返佣 * 购买数量
             */
            BigDecimal receivable = BigDecimal.ZERO;
            if( "1".equals(payType) ){
                receivable = salesTotal;
            }else{
//                receivable = salesTotal.subtract(purchaseTotal);
            	receivable = salesTotal.subtract(agentTotal);
            }
            financeReceipt.put("receivable",receivable);//应收
            financeReceipt.put("actualReceipt",orderDto.getActualReceiptAmount());//实收
            BigDecimal surplus = receivable.subtract(orderDto.getActualReceiptAmount());//剩余应收款
            financeReceipt.put("surplus",surplus);
            // add 20180814 增加定金应收 start
            financeReceipt.put("depositAmount", orderDto.getDepositAmount()); // 定金应收
            // add 20180814 增加定金应收 end
            orderDetailGson.setFinanceReceipt(financeReceipt);


            /*****************财务付款-代理*************************************************************************/
            Map financePaidAgent = new HashMap();
            financePaidAgent.put("status",orderDto.getPayAgentStatus());	// 状态
            financePaidAgent.put("receivable",agentTotal);	// 应付
            financePaidAgent.put("actualReceipt",orderDto.getActualPayAgentAmount());	// 实付
            BigDecimal agentSurplus = agentTotal.subtract(orderDto.getActualPayAgentAmount());	// 剩余
            financePaidAgent.put("surplus",agentSurplus);
            financePaidAgent.put("agentExtraAmount", agentExtraTotal);	// 代理额外收益
            
            orderDetailGson.setFinancePaidAgent(financePaidAgent);

            /*****************财务付款-上级代理*************************************************************************/
            Map financePaidSuperAgent = new HashMap();
            financePaidSuperAgent.put("status",orderDto.getPaySuperAgentStatus());	// 状态
            financePaidSuperAgent.put("receivable",superAgentTotal);	// 应付
            financePaidSuperAgent.put("actualReceipt",orderDto.getActualPaySuperAgentAmount());	// 实付
            BigDecimal superAgentSurplus = superAgentTotal.subtract(orderDto.getActualPaySuperAgentAmount());	// 剩余
            financePaidSuperAgent.put("surplus",superAgentSurplus);
            financePaidSuperAgent.put("agentExtraAmount", superAgentExtraTotal);	// 上级代理额外收益
            orderDetailGson.setFinancePaidSuperAgent(financePaidSuperAgent);


            /*****************财务付款-供应商*************************************************************************/
            Map financePaidSupplier = new HashMap();
            financePaidSupplier.put("status",orderDto.getPaySupplierStatus());	// 状态
            // 应付从产品采购价变成财务结算价
//            financePaidSupplier.put("receivable",purchaseTotal);
            financePaidSupplier.put("receivable", settlementTotal);	// 应付
            financePaidSupplier.put("actualReceipt",orderDto.getActualPaySupplieAmount());	// 实付
            BigDecimal supplieSurplus = settlementTotal.subtract(orderDto.getActualPaySupplieAmount());	// 剩余
            financePaidSupplier.put("surplus",supplieSurplus);
            orderDetailGson.setFinancePaidSupplier(financePaidSupplier);

            /*****************用户退款*************************************************************************/
            Map financePaidRefund = new HashMap();
            // 用于退款/赔偿应付财务收款_实收 - 销售价*退款单数量 - 赔偿单合计金额
//            BigDecimal needPayUserRefundAmount = orderDto.getOriginSalesTotal().subtract(salesTotal);
//            BigDecimal needPayUserRefundAmount = salesTotal.add(compensationPriceTotal).subtract(orderDto.getOriginSalesTotal());
            BigDecimal needPayUserRefundAmount = orderDto.getActualReceiptAmount().subtract(receivable).add(compensationPriceTotal);
            if( needPayUserRefundAmount.compareTo(BigDecimal.ZERO) <= 0 ){
                needPayUserRefundAmount = BigDecimal.ZERO;
            }
            financePaidRefund.put("status",orderDto.getRefundStatus());	// 状态
            financePaidRefund.put("receivable",needPayUserRefundAmount);	// 应付
            financePaidRefund.put("actualReceipt",orderDto.getActualPayUserRefundAmount());	// 实付
            BigDecimal refundSurplus = needPayUserRefundAmount.subtract(orderDto.getActualPayUserRefundAmount());	// 剩余
            financePaidRefund.put("surplus",refundSurplus);
            orderDetailGson.setFinancePaidRefund(financePaidRefund);

        }else{
            orderDetailGson.setBuyInfoInit(bookingDetailGsonList);
            orderDetailGson.setBuyInfoInitFormula(buyInfoFormula);
            orderDto.setOriginSalesTotal(salesTotal);
        }

    }

    public ResultEntity updateOrder(OrderQueryResultEntity orderQueryEntity,AccountDto currentUser){

        try{
        	ResultEntity<OrderDetailGson> resultEntity = queryOrderDetail(orderQueryEntity.getId(),currentUser);
            OrderDetailGson orderDetailGson = resultEntity.getData();
            BigDecimal needPayAmount = BigDecimal.ZERO; // 应付
            BigDecimal actualAmount = BigDecimal.ZERO;	// 实付
            
            // 财务收款
            // 实收-判断实收！=null并且不能和数据库里相同
            if(orderQueryEntity.getrAActualReceipt() != null 
            		&& orderQueryEntity.getrAActualReceipt().compareTo(resultEntity.getData().getActualReceiptAmount()) != 0) {
            	needPayAmount = (BigDecimal) resultEntity.getData().getFinanceReceipt().get("receivable");
            	actualAmount = orderQueryEntity.getrAActualReceipt();
            	needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
            	actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
        		if(actualAmount.compareTo(needPayAmount) >= 0) {
	    			orderQueryEntity.setrAStatus("3");
	    		} else if(actualAmount.compareTo(needPayAmount) < 0 && actualAmount.compareTo(BigDecimal.ZERO) > 0) {
	    			orderQueryEntity.setrAStatus("2");
	    		}
            	
            	
            // 状态-判断状态！=null并且不能和数据库里相同
            } else if("3".equals(orderQueryEntity.getrAStatus()) 
            		&& !orderQueryEntity.getrAStatus().equals(resultEntity.getData().getGatheringStatus())) {
            	needPayAmount = (BigDecimal) resultEntity.getData().getFinanceReceipt().get("receivable");
            	actualAmount = orderQueryEntity.getrAActualReceipt();
            	needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
            	actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
            	
            	if(actualAmount.compareTo(needPayAmount) < 0) {
	        		orderQueryEntity.setrAActualReceipt(needPayAmount);
	        	}
            }
//            if("3".equals(orderQueryEntity.getrAStatus())) {
//            	needPayAmount = (BigDecimal) resultEntity.getData().getFinanceReceipt().get("receivable");
//            	needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
//            	actualAmount = orderQueryEntity.getrAActualReceipt();
//            	actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
//            	
//            	if(actualAmount.compareTo(needPayAmount) < 0) {
//            		orderQueryEntity.setrAActualReceipt(needPayAmount);
//            	}
//            	
//        	// 财务收款 应收 实收
//            } else if(orderQueryEntity.getrAActualReceipt() != null) {
//            	actualAmount = orderQueryEntity.getrAActualReceipt();
//            	needPayAmount = (BigDecimal)resultEntity.getData().getFinanceReceipt().get("receivable");
//                
//                needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
//                actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
//            	
//        		if(actualAmount.compareTo(needPayAmount) >= 0) {
//        			orderQueryEntity.setrAStatus("3");
//        		} else if(actualAmount.compareTo(needPayAmount) < 0
//        				&& actualAmount.compareTo(BigDecimal.ZERO) > 0) {
//        			orderQueryEntity.setrAStatus("2");
//        		}
//        	}
            
        	// 财务实收<定金 未完成 财务实收>=定金 已完成
        	if(orderQueryEntity.getrAActualReceipt() != null) {
        		if(orderQueryEntity.getrAActualReceipt().compareTo(resultEntity.getData().getDepositAmount()) < 0) {
            		orderQueryEntity.setDepositStatus("1");
            	} else if(orderQueryEntity.getrAActualReceipt().compareTo(resultEntity.getData().getDepositAmount()) > 0) {
            		orderQueryEntity.setDepositStatus("2");
            	} else if(resultEntity.getData().getDepositAmount().compareTo(BigDecimal.ZERO) == 0) {
            		orderQueryEntity.setDepositStatus("-1001");
            	}
        	}
        	
        	// 代理变更状态=4 已打款 实付=应付
        	// 代理收款
        	if(orderQueryEntity.getaAActualReceipt() != null 
        			&& orderQueryEntity.getaAActualReceipt().compareTo(resultEntity.getData().getActualPayAgentAmount()) != 0) {
        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidAgent().get("receivable");
    			actualAmount = orderQueryEntity.getaAActualReceipt();
    			needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
    			actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
    			
        		if(actualAmount.compareTo(needPayAmount) >= 0) {
	    			orderQueryEntity.setaAStatus("4");
	    		} else if(actualAmount.compareTo(needPayAmount) < 0 && actualAmount.compareTo(BigDecimal.ZERO) > 0) {
	    			orderQueryEntity.setaAStatus("3");
	    		}
    			
        	// 代理状态
        	} else if("4".equals(orderQueryEntity.getaAStatus())
        			&& !orderQueryEntity.getaAStatus().equals(resultEntity.getData().getPayAgentStatus())) {
        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidAgent().get("receivable");
    			actualAmount = orderQueryEntity.getaAActualReceipt();
    			needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
            	actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
    			if(actualAmount.compareTo(needPayAmount) < 0) {
					orderQueryEntity.setaAActualReceipt(needPayAmount);
				}
        	}
//        	if("4".equals(orderQueryEntity.getaAStatus())) {
//        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidAgent().get("receivable");
//    			needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
//    			actualAmount = orderQueryEntity.getaAActualReceipt();
//    			actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
//    			if(actualAmount.compareTo(needPayAmount) < 0) {
//    				orderQueryEntity.setaAActualReceipt(needPayAmount);
//    			}
//    		// 代理应付<=实付
//        	} else if(orderQueryEntity.getaAActualReceipt() != null) {
//        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidAgent().get("receivable");
//        		needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
//        		
//        		if(orderQueryEntity.getaAActualReceipt().compareTo(needPayAmount) >= 0) {
//        			orderQueryEntity.setaAStatus("4");
//        		} else if(orderQueryEntity.getaAActualReceipt().compareTo(needPayAmount) < 0
//        				&& orderQueryEntity.getaAActualReceipt().compareTo(BigDecimal.ZERO) > 0) {
//        			orderQueryEntity.setaAStatus("3");
//        		}
//        	}
        	
        	// 上级代理变更状态=4 已打款 实付=应付
        	// 上级代理实付
        	if(orderQueryEntity.getsAAActualReceipt() != null
        			&& orderQueryEntity.getsAAActualReceipt().compareTo(resultEntity.getData().getActualPaySuperAgentAmount()) != 0) {
        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidSuperAgent().get("receivable");
    			actualAmount = orderQueryEntity.getsAAActualReceipt();
    			needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
    			actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
        		if(actualAmount.compareTo(needPayAmount) >= 0) {
	    			orderQueryEntity.setsAAStatus("4");
	    		} else if(actualAmount.compareTo(needPayAmount) < 0 && actualAmount.compareTo(BigDecimal.ZERO) > 0) {
	    			orderQueryEntity.setsAAStatus("3");
	    		}
    		// 上级代理状态
        	} else if("4".equals(orderQueryEntity.getsAAStatus())
        			&& !orderQueryEntity.getsAAStatus().equals(resultEntity.getData().getPaySuperAgentStatus())) {
        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidSuperAgent().get("receivable");
    			actualAmount = orderQueryEntity.getsAAActualReceipt();
    			needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
            	actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
    			if(actualAmount.compareTo(needPayAmount) < 0) {
					orderQueryEntity.setsAAActualReceipt(needPayAmount);
				}
        	}
//        	if("4".equals(orderQueryEntity.getsAAStatus())) {
//        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidSuperAgent().get("receivable");
//    			needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
//    			actualAmount = orderQueryEntity.getsAAActualReceipt();
//    			actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
//    			if(actualAmount.compareTo(needPayAmount) < 0) {
//    				orderQueryEntity.setsAAActualReceipt(needPayAmount);
//    			}
//        		
//    		// 上级代理应付<=实付
//        	} else if(orderQueryEntity.getsAAActualReceipt() != null) {
//        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidSuperAgent().get("receivable");
//        		needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
//        		if(orderQueryEntity.getsAAActualReceipt().compareTo(needPayAmount) >= 0) {
//        			orderQueryEntity.setsAAStatus("4");
//        		} else if(orderQueryEntity.getsAAActualReceipt().compareTo(needPayAmount) < 0
//        				&& orderQueryEntity.getsAAActualReceipt().compareTo(BigDecimal.ZERO) > 0) {
//        			orderQueryEntity.setsAAStatus("3");
//        		}
//        	}
        	
        	// 供应商状态变更=已打款 4 应付=实付
        	// 供应商实付
        	if(orderQueryEntity.getsAActualReceipt() != null
        			&& orderQueryEntity.getsAActualReceipt().compareTo(resultEntity.getData().getActualPaySupplieAmount()) != 0) {
        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidSupplier().get("receivable");
    			actualAmount = orderQueryEntity.getsAActualReceipt();
    			needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
    			actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
        		if(actualAmount.compareTo(needPayAmount) >= 0) {
	    			orderQueryEntity.setsAStatus("4");
	    		} else if(actualAmount.compareTo(needPayAmount) < 0 && actualAmount.compareTo(BigDecimal.ZERO) > 0) {
	    			orderQueryEntity.setsAStatus("3");
	    		}
        		
        	// 供应商状态
        	} else if("4".equals(orderQueryEntity.getsAStatus())
        			&& !orderQueryEntity.getsAStatus().equals(resultEntity.getData().getPaySupplierStatus())) {
        		if(orderQueryEntity.getsAActualReceipt() != null) {
        			needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidSupplier().get("receivable");
        			actualAmount = orderQueryEntity.getsAActualReceipt();
        			needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
                	actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
        			if(actualAmount.compareTo(needPayAmount) < 0) {
    					orderQueryEntity.setsAActualReceipt(needPayAmount);
    				}
        		}
        	}
//        	if("4".equals(orderQueryEntity.getsAStatus())) {
//        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidSupplier().get("receivable");
//    			needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
//    			actualAmount = orderQueryEntity.getsAActualReceipt();
//    			actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
//    			if(actualAmount.compareTo(needPayAmount) < 0) {
//    				orderQueryEntity.setsAActualReceipt(needPayAmount);
//    			}
//        		
//    		// 供应商应付<=实付
//        	} else if(orderQueryEntity.getsAActualReceipt() != null) {
//        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidSupplier().get("receivable");
//        		needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
//        		
//        		if(orderQueryEntity.getsAActualReceipt().compareTo(needPayAmount) >= 0) {
//        			orderQueryEntity.setsAStatus("4");
//        		} else if(orderQueryEntity.getsAAActualReceipt().compareTo(needPayAmount) < 0
//        				&& orderQueryEntity.getsAActualReceipt().compareTo(BigDecimal.ZERO) > 0) {
//        			orderQueryEntity.setsAStatus("3");
//        		}
//        	}
        	
        	// 退款/赔偿变更状态=4 已打款 实付=应付
        	// 退款/赔偿实付
        	if(orderQueryEntity.getrFAActualReceipt() != null
        			&& orderQueryEntity.getrFAActualReceipt().compareTo(resultEntity.getData().getActualPayUserRefundAmount()) != 0) {
        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidRefund().get("receivable");
    			actualAmount = orderQueryEntity.getrFAActualReceipt();
    			needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
    			actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
              if(actualAmount.compareTo(needPayAmount) >= 0) {
	            	orderQueryEntity.setrFAStatus("4");
	            } else if(actualAmount.compareTo(needPayAmount) < 0 && actualAmount.compareTo(BigDecimal.ZERO) > 0){
	            	orderQueryEntity.setrFAStatus("3");
	            }
        	} else if("4".equals(orderQueryEntity.getrFAStatus())
        			&& !orderQueryEntity.getrFAStatus().equals(resultEntity.getData().getRefundStatus())) {
        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidRefund().get("receivable");
    			actualAmount = orderQueryEntity.getrFAActualReceipt();
    			needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
            	actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
    			if(actualAmount.compareTo(needPayAmount) < 0) {
					orderQueryEntity.setrFAActualReceipt(needPayAmount);
				}
        	}
//        	if("4".equals(orderQueryEntity.getrFAStatus())) {
//        		needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidRefund().get("receivable");
//    			needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
//    			actualAmount = orderQueryEntity.getrFAActualReceipt();
//    			actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
//    			if(actualAmount.compareTo(needPayAmount) < 0) {
//    				orderQueryEntity.setrFAActualReceipt(needPayAmount);
//    			}
//        		
//    		// // 订单-财务 变更退款/赔偿实付
//        	// 判断退款/赔偿实付不能唯空
//        	} else if( orderQueryEntity.getrFAActualReceipt() != null){
//                needPayAmount = (BigDecimal) resultEntity.getData().getFinancePaidRefund().get("receivable");
//                actualAmount = (BigDecimal)orderQueryEntity.getrFAActualReceipt();
//                
//                needPayAmount = needPayAmount == null ? BigDecimal.ZERO : needPayAmount;
//                actualAmount = actualAmount == null ? BigDecimal.ZERO : actualAmount;
//                
//                if(actualAmount.compareTo(needPayAmount) >= 0) {
//                	orderQueryEntity.setrFAStatus("4");
//                } else if(actualAmount.compareTo(needPayAmount) < 0 && actualAmount.compareTo(BigDecimal.ZERO) > 0){
//                	orderQueryEntity.setrFAStatus("3");
//                }
//                
//            }
        	
        	
            orderQueryEntity.setUpdatedBy(currentUser.getId());
            int updateCount = orderMapper.updateOrder(orderQueryEntity);
            
            // 代理付款状态变更，更新代理奖励表
            if(orderQueryEntity.getaAStatus() != null) {
            	AgentRewardDto agentRewaredDto = new AgentRewardDto();
                agentRewaredDto.setOrderId(orderQueryEntity.getId());
                agentRewaredDto.setRewardType("1");
                agentRewaredDto.setPayAgentStatus(orderQueryEntity.getaAStatus());
                agentRewaredDto.setUpdatedAt(new Date());
                agentRewaredDto.setUpdatedBy(currentUser.getId());
                agentRewardMapper.updatePayAgentStatusByOrderId(agentRewaredDto);
            }
            
            // 上级代理付款状态变更，更新代理奖励表
            if(orderQueryEntity.getsAAStatus() != null) {
            	AgentRewardDto superAgentRewaredDto = new AgentRewardDto();
                superAgentRewaredDto.setOrderId(orderQueryEntity.getId());
                superAgentRewaredDto.setRewardType("2");
                superAgentRewaredDto.setPayAgentStatus(orderQueryEntity.getsAAStatus());
                superAgentRewaredDto.setUpdatedAt(new Date());
                superAgentRewaredDto.setUpdatedBy(currentUser.getId());
                
                agentRewardMapper.updatePayAgentStatusByOrderId(superAgentRewaredDto);
            }
            
            if(updateCount<=0){
                return new ResultEntity(ResultEnum.DATA_CHANGED);
            }

            return new ResultEntity(ResultEnum.SUCCESS);
        }catch (Exception e){
        	System.out.println("updateOrder error : " + e.getMessage());
            Logger.error(this,"updateOrder error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }


    @Transactional
    public ResultEntity addOrderRefund(List<BookDetailDto> bookDetailDtoList,AccountDto currentUser,Long orderId){

        try{
            Long version = Long.valueOf(DateUtils.format(DateUtils.CMS_DRAW_SEQUENCE_FORMAT,new Date()));
            //插入最新版
            for(BookDetailDto detailDto : bookDetailDtoList){
                detailDto.setId(null);
                detailDto.setOrderId(orderId);
                detailDto.setVersion(version);
                detailDto.setCreatedBy(currentUser.getId());
                detailDto.setCreatedAt(new Date());
                bookDetailMapper.insertBookDetail(detailDto);
            }

            ResultEntity<OrderDetailGson> resultEntity = queryOrderDetail(orderId,currentUser);
            OrderDetailGson orderDetailGson = resultEntity.getData();
            BigDecimal needPayUserRefundAmount = BigDecimal.ZERO;
            if( resultEntity.getData().getFinancePaidRefund().get("receivable") != null ){
                needPayUserRefundAmount = (BigDecimal) resultEntity.getData().getFinancePaidRefund().get("receivable");
            }

            //更新应付用户退款金额
            OrderDto order = orderMapper.selectOneOrder(orderId);
            OrderQueryResultEntity updateParam = new OrderQueryResultEntity();
            updateParam.setId(orderId);
            updateParam.setVersion(order.getVersion());
            updateParam.setrFAReceivable(needPayUserRefundAmount);
            if("-1001".equals(order.getRefundStatus())) {
            	// 更新赔款状态
                updateParam.setrFAStatus("1");
            }
            int updateCount = orderMapper.updateOrder(updateParam);
            if(updateCount<=0){
                return new ResultEntity(ResultEnum.DATA_CHANGED);
            }


            OrderDto orderDto = orderDetailGson.getOrder();

            //更新代理返佣金额
            AgentRewardDto agentRewardDto = new AgentRewardDto();
            agentRewardDto.setOrderId(orderId);
            agentRewardDto.setAgentId(orderDto.getAgentId());
            Map rewardAgentMap = resultEntity.getData().getFinancePaidAgent();
            BigDecimal rewardAgentAmount = BigDecimal.valueOf(Double.valueOf(rewardAgentMap.get("receivable").toString()));
            agentRewardDto.setRewardAmount(rewardAgentAmount);
            agentRewardMapper.updateAgentReward(agentRewardDto);

            //更新上级代理返佣金额
            AgentRewardDto superAgentRewardDto = new AgentRewardDto();
            superAgentRewardDto.setOrderId(orderId);
            superAgentRewardDto.setAgentId(orderDto.getSuperAgent());
            Map rewardSuperAgentMap = resultEntity.getData().getFinancePaidSuperAgent();
            BigDecimal rewardSuperAgentAmount = BigDecimal.valueOf(Double.valueOf(rewardSuperAgentMap.get("receivable").toString()));
            superAgentRewardDto.setRewardAmount(rewardSuperAgentAmount);
            agentRewardMapper.updateAgentReward(superAgentRewardDto);

            return new ResultEntity(ResultEnum.SUCCESS,bookDetailDtoList);
        }catch (Exception e){
            Logger.error(this,"addOrUpdateOrderRefund error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }


    public ResultEntity queryOrderRefund(Long orderId){

        try{
            List<Map> refundList = bookDetailMapper.selectRefundHistory(orderId);
            return new ResultEntity(ResultEnum.SUCCESS,refundList);
        }catch (Exception e){
            Logger.error(this,"queryOrder error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }


    public ResultEntity queryOrderCompensate(Long orderId) {
    	try {
    		List<Map> compensateList = compensateDetailMapper.selectCompensateHistory(orderId);
    		return new ResultEntity(ResultEnum.SUCCESS, compensateList);
    	} catch(Exception e) {
    		Logger.error(this, "queryOrderCompensate error:",e);
    		return new ResultEntity(ResultEnum.SYS_ERROR);
    	}
    }
    
    public ResultEntity addOrderCompensate(CompensateDetailDto compensateDetailDto, AccountDto currentUser, Long orderId) {
    	try{
            Long version = Long.valueOf(DateUtils.format(DateUtils.CMS_DRAW_SEQUENCE_FORMAT,new Date()));
            //插入最新版
            compensateDetailDto.setId(null);
            compensateDetailDto.setOrderId(orderId);
            compensateDetailDto.setVersion(version);
            compensateDetailDto.setCreatedBy(currentUser.getId());
            compensateDetailDto.setCreatedAt(new Date());
            compensateDetailMapper.insertCompensateDetail(compensateDetailDto);
            
            // 增加赔偿单是，赔偿金额>0是，变更退款/赔偿状态为1，未申请
            if(compensateDetailDto.getCompensationPrice().compareTo(BigDecimal.ZERO) > 0) {
            	// 取得该订单ID的version
            	OrderDto order = orderMapper.selectOneOrder(orderId);
            	if("-1001".equals(order.getRefundStatus())) {
            		// 更新退款/赔偿状态
                	OrderQueryResultEntity updateParam = new OrderQueryResultEntity();
                	updateParam.setId(orderId);
                	updateParam.setVersion(order.getVersion());
                	updateParam.setrFAStatus("1");
                	int updateCount = orderMapper.updateOrder(updateParam);
                	
                    if(updateCount<=0){
                        return new ResultEntity(ResultEnum.DATA_CHANGED);
                    }
            	}
            }

            return new ResultEntity(ResultEnum.SUCCESS,compensateDetailDto);
        }catch (Exception e){
            Logger.error(this,"addOrUpdateOrderRefund error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }


}
