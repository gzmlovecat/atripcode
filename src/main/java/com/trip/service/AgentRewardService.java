package com.trip.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trip.dto.AccountDto;
import com.trip.dto.AgentDto;
import com.trip.dto.AgentRewardDto;
import com.trip.dto.ArticleDto;
import com.trip.dto.BookDetailDto;
import com.trip.dto.OrderDto;
import com.trip.entity.AgentIncomeGson;
import com.trip.entity.DateUtils;
import com.trip.entity.Logger;
import com.trip.entity.MapUtils;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.mapper.AccountMapper;
import com.trip.mapper.AgentMapper;
import com.trip.mapper.AgentRewardMapper;
import com.trip.mapper.BookDetailMapper;
import com.trip.mapper.OrderMapper;


@Service
public class AgentRewardService {

    @Autowired
    private AgentRewardMapper agentRewardMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BookDetailMapper bookDetailMapper;
    
    @Autowired
    private AccountMapper accountMapper;
    
    @Autowired
    private AgentMapper agentMapper;

    public ResultEntity queryAgentReward(String agentName,String pageNum1,String pageSize1){

        try{
        	int pageNum = 1;
        	int pageSize = 30;
            if(pageNum1 != null && !"".equals(pageNum1)) {
            	pageNum = Integer.parseInt(pageNum1);
            }
            if(pageSize1 != null && !"".equals(pageSize1)) {
            	pageSize = Integer.parseInt(pageSize1);
            }
            
            PageHelper.startPage(pageNum, pageSize);
            Map param = MapUtils.buildKeyValueMap("agentName",agentName);
            List<AgentRewardDto> list = agentRewardMapper.selectAgentRewardByParam(param);
            PageInfo<AgentRewardDto> pageList = new PageInfo<AgentRewardDto>(list);
            Long totalPageSize = pageList.getTotal();
            Long modTotalPageNum = totalPageSize % pageSize;
            Long totalPageNum = modTotalPageNum == 0 ? totalPageSize / pageSize : totalPageSize / pageSize + 1;
        	
            ResultEntity resultEntity =  new ResultEntity(ResultEnum.SUCCESS,pageList.getList());
            resultEntity.setTotalPageNum(totalPageNum);
            resultEntity.setTotalPageSize(totalPageSize);
            
            return resultEntity;
        }catch (Exception e){
            Logger.error(this,"queryAgent error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }


    public ResultEntity queryMyIncome(Long agentId,String tripNotifyStatus,String contractSignStatus ,
                                      String buyInsuranceStatus ,String payAgentStatus  ,String refundStatus,
                                      //String rewardReason
                                      String rewardType){

        try{

            if( "0".equals(rewardType) ){
            	rewardType = null;
            }
            if("0".equals(tripNotifyStatus)) {
            	tripNotifyStatus = null;
            }
            if("0".equals(contractSignStatus)) {
            	contractSignStatus = null;
            }
            if("0".equals(buyInsuranceStatus)) {
            	buyInsuranceStatus = null;
            }
            if("0".equals(payAgentStatus)) {
            	payAgentStatus = null;
            }
            if("0".equals(refundStatus)) {
            	refundStatus = null;
            }
            Map param = MapUtils.buildKeyValueMap("agentId",agentId,"createdAt",new Date(),"tripNotifyStatus",tripNotifyStatus,
                    "contractSignStatus",contractSignStatus,"buyInsuranceStatus",buyInsuranceStatus,
                    "payAgentStatus",payAgentStatus,"refundStatus",refundStatus,"rewardType",rewardType);
            List<AgentRewardDto> rewardDtoList = agentRewardMapper.selectAgentRewardByParam(param);
            List<AgentIncomeGson> incomeList = new ArrayList<>();

            BigDecimal curMonthIncome = BigDecimal.ZERO;
            for(AgentRewardDto rewardDto : rewardDtoList ){
                AgentIncomeGson agentIncome = new AgentIncomeGson();
                agentIncome.setDay( DateUtils.formatDate(rewardDto.getCreatedAt()));
                agentIncome.setId(rewardDto.getId()); 	// 收益流水号
                agentIncome.setRewardType(rewardDto.getRewardType());	// 收益类型
                agentIncome.setRewardAmount(rewardDto.getRewardAmount());	// 代理收益
                agentIncome.setExtraRewardAmount(rewardDto.getRewardExtraAmount());	// 额外收益
                agentIncome.setExtraType(rewardDto.getExtraType());	// 额外收益类型

                OrderDto orderDto = orderMapper.selectOneOrder(rewardDto.getOrderId());
                agentIncome.setOrderId(orderDto.getId());
                agentIncome.setProductName(orderDto.getProductName());
                agentIncome.setSubAgent(rewardDto.getSubAgentName());
                agentIncome.setOrderIncome(rewardDto.getRewardAmount());
                agentIncome.setTourist(orderDto.getTourist());
                agentIncome.setTouristPhone(orderDto.getTouristPhone());
                agentIncome.setTripCreateStartTime(orderDto.getDepartureTime());
                agentIncome.setBuyInsuranceStatus(orderDto.getBuyInsuranceStatus());
                agentIncome.setContractSignStatus(orderDto.getContractSignStatus());
                agentIncome.setTripNotifyStatus(orderDto.getTripNotifyStatus());

                agentIncome.setNeedPayUserRefundAmount(orderDto.getNeedPayUserRefundAmount());
                agentIncome.setActualPayUserRefundAmount(orderDto.getActualPayUserRefundAmount());

                agentIncome.setPayAgentStatus(orderDto.getPayAgentStatus());
                agentIncome.setPaySuperAgentStatus(orderDto.getPaySuperAgentStatus());

                /**
                 * 查询订购明细
                 */
                Long orderId = orderDto.getId();
                Map<String,Long> map = bookDetailMapper.selectBookDetailVersion(orderId);
                Long maxVersion = map.get("maxVersion");
                BookDetailDto bookDetailDto = new BookDetailDto();
                bookDetailDto.setId(orderId);
                bookDetailDto.setVersion(maxVersion);
                List<BookDetailDto> bookDetailDtoList = bookDetailMapper.selectBookDetailList(bookDetailDto);
                agentIncome.setBookingList(bookDetailDtoList);
                incomeList.add(agentIncome);

                curMonthIncome = curMonthIncome.add(rewardDto.getRewardAmount());

            }
            // 累计收益
            BigDecimal incomeTotal = agentRewardMapper.selectAgentTotalIncome(agentId);
            incomeTotal = incomeTotal == null ? BigDecimal.ZERO : incomeTotal;
            // 30天累计收益
            BigDecimal dayOfMonthIncome = agentRewardMapper.selectAgentDayOfMonthIncome(agentId);
            dayOfMonthIncome = dayOfMonthIncome == null ? BigDecimal.ZERO : dayOfMonthIncome;
            // 累计交易金额
            BigDecimal saleTotal = agentRewardMapper.selectSaleTotal(agentId, null);
            saleTotal = saleTotal == null ? BigDecimal.ZERO : saleTotal;
            // 30天累计交易金额
            BigDecimal dayOfMonthSaleTotal = agentRewardMapper.selectSaleTotal(agentId, "1");
            dayOfMonthSaleTotal = dayOfMonthSaleTotal == null ? BigDecimal.ZERO : dayOfMonthSaleTotal;
            // vip收益金额
            BigDecimal extraRewardAmountTotal = agentRewardMapper.selectExtraRewardAmount(agentId);
            extraRewardAmountTotal = extraRewardAmountTotal == null ? BigDecimal.ZERO : extraRewardAmountTotal;
            // 查找我的团队
//            List<AgentRewardDto> myTeamList = agentRewardMapper.selectMyTeam(agentId);
            // 查找我的团队头像
            List<AgentRewardDto> myTeamImageList = agentRewardMapper.selectMyTeamImage(agentId);
            // 团队收益
            BigDecimal teamIncome = BigDecimal.ZERO;
            // 团队贡献收益
            BigDecimal teamIncomePrecent = BigDecimal.ZERO;
            String sTeamIncomePrecent = "0%";
//            List<String> headImageList = new ArrayList<String>();
            List<Map<String, String>> headImageList = new ArrayList<Map<String, String>>();
//            Map<String, String> headMap = new 
            if(myTeamImageList != null && myTeamImageList.size() > 0) {
            	for(int i = 0; i < myTeamImageList.size() && i < 4; i++) {
            		AgentRewardDto dto = myTeamImageList.get(i);
            		BigDecimal income = dto.getRewardAmount();
            		income = income == null ? BigDecimal.ZERO : income;
            		teamIncome = teamIncome.add(income);
//            		headImageList.add(dto.getAvatarUrl());
            		Map<String, String> headMap = new LinkedHashMap<String, String>();
            		if(dto.getAvatarUrl() == null || "".equals(dto.getAvatarUrl())){
            			headMap.put("avatarUrl", "");
            		} else {
            			headMap.put("avatarUrl", dto.getAvatarUrl());
            		}
            		headMap.put("username", dto.getUsername());
            		headImageList.add(headMap);
            	}
            	// 团队贡献收益
            	if(teamIncome.compareTo(BigDecimal.ZERO) > 0) {
//            		teamIncomePrecent = incomeTotal.divide(teamIncome, 2, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100));
            		teamIncomePrecent = teamIncome.divide(teamIncome.add(incomeTotal), 2, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100));
            		teamIncomePrecent = teamIncomePrecent.setScale(0, BigDecimal.ROUND_DOWN);
                    sTeamIncomePrecent = teamIncomePrecent.toString() + "%";
            	}
            }
            
            BigDecimal extraRewardAmountPrecent = BigDecimal.ZERO;
            String sExtraRewardAmountPrecent = "0%";
            if(incomeTotal.compareTo(BigDecimal.ZERO) > 0) {
            	extraRewardAmountPrecent = extraRewardAmountTotal.divide(incomeTotal, 2, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100));
            	extraRewardAmountPrecent = extraRewardAmountPrecent.setScale(0, BigDecimal.ROUND_DOWN);
            	sExtraRewardAmountPrecent = extraRewardAmountPrecent.toString() + "%";
            }
             
            Map result = MapUtils.buildKeyValueMap("incomeTotal",incomeTotal,
                    "curMonthIncome",curMonthIncome,
                    "dayOfMonthIncome",dayOfMonthIncome,
                    "saleTotal",saleTotal,
                    "dayOfMonthSaleTotal",dayOfMonthSaleTotal,
                    "teamIncomePrecent", sTeamIncomePrecent,
                    "teamNum",myTeamImageList.size(),
                    "teamIncome",teamIncome,
                    "headImageList", headImageList,
                    "incomeList",incomeList,
                    "extraRewardAmountPrecent", sExtraRewardAmountPrecent);

            return new ResultEntity(ResultEnum.SUCCESS,result);
        }catch (Exception e){
            Logger.error(this,"queryAgent error:",e);
            System.out.println(e.getMessage());
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }

    public ResultEntity queryMyIncomeDetail(Long orderId, Long agentId) {
    	try{

            Map param = MapUtils.buildKeyValueMap("agentId",agentId,"orderId",orderId);
            List<AgentRewardDto> rewardDtoList = agentRewardMapper.selectAgentRewardByParam(param);
//            List<AgentIncomeGson> incomeList = new ArrayList<>();
            AgentIncomeGson agentIncome = new AgentIncomeGson();

            BigDecimal curMonthIncome = BigDecimal.ZERO;
            if( !CollectionUtils.isEmpty(rewardDtoList)){
            	AgentRewardDto rewardDto = rewardDtoList.get(0);
//                AgentIncomeGson agentIncome = new AgentIncomeGson();
                agentIncome.setDay( DateUtils.formatDate(rewardDto.getCreatedAt()));

                OrderDto orderDto = orderMapper.selectOneOrder(rewardDto.getOrderId());
	            agentIncome.setOrderId(orderDto.getId());
	            agentIncome.setProductName(orderDto.getProductName());
	            agentIncome.setSubAgent(rewardDto.getSubAgentName());
	            agentIncome.setOrderIncome(rewardDto.getRewardAmount());
	            agentIncome.setTourist(orderDto.getTourist());
	            agentIncome.setTouristPhone(orderDto.getTouristPhone());
	            agentIncome.setTripCreateStartTime(orderDto.getDepartureTime());
	            agentIncome.setBuyInsuranceStatus(orderDto.getBuyInsuranceStatus());
	            agentIncome.setContractSignStatus(orderDto.getContractSignStatus());
	            agentIncome.setTripNotifyStatus(orderDto.getTripNotifyStatus());
	
	            agentIncome.setNeedPayUserRefundAmount(orderDto.getNeedPayUserRefundAmount());
	            agentIncome.setActualPayUserRefundAmount(orderDto.getActualPayUserRefundAmount());
	
	            agentIncome.setPayAgentStatus(orderDto.getPayAgentStatus());
	            agentIncome.setPaySuperAgentStatus(orderDto.getPaySuperAgentStatus());
	
	            /**
	             * 查询订购明细
	             */
//	            Long orderId = orderDto.getId();
	            Map<String,Long> map = bookDetailMapper.selectBookDetailVersion(orderId);
	            Long maxVersion = map.get("maxVersion");
               BookDetailDto bookDetailDto = new BookDetailDto();
	            bookDetailDto.setId(orderId);
	            bookDetailDto.setVersion(maxVersion);
	            List<BookDetailDto> bookDetailDtoList = bookDetailMapper.selectBookDetailList(bookDetailDto);
	            agentIncome.setBookingList(bookDetailDtoList);
//	            incomeList.add(agentIncome);
	
	            curMonthIncome = curMonthIncome.add(rewardDto.getRewardAmount());
            }

            BigDecimal incomeTotal = agentRewardMapper.selectAgentTotalIncome(agentId);
            Map result = MapUtils.buildKeyValueMap("incomeTotal",incomeTotal,
                    "curMonthIncome",curMonthIncome,
                    "income",agentIncome);

            return new ResultEntity(ResultEnum.SUCCESS,result);
        }catch (Exception e){
            Logger.error(this,"queryAgent error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }


    public ResultEntity queryMyTeam(Long agentId) {
    	try {
    		List<AgentRewardDto> myTeamList = agentRewardMapper.selectMyTeam(agentId);
    		if(myTeamList != null && myTeamList.size() > 0) {
//    			for(AgentRewardDto dto : myTeamList) {
//    				if(dto.getAvatarUrl() == null || "".equals(dto.getAvatarUrl())) {
//    					// 生成默认头像
//    					String agentName = dto.getAgentName().substring(0, 1);
//    					BufferedImage image = ImageUtil.createDefaultHeadImage(agentName, dto.getAgentId());
////    					String avatarUrl = ImageUtil.createDefaultHeadImage(agentName, dto.getAgentId());
//    					String classpath = this.getClass().getResource("/").getPath();
//    		        	classpath = classpath.replaceAll("WEB-INF/classes/", "agent/");
//    		        	File file = new File(classpath+agentId+".jpg");
//    		        	ImageIO.write(image, "jpg", file);
//    		        	String avatarUrl = "https://api.atrip.club/atrip/agent/"+agentId+".jpg";
//    					dto.setAvatarUrl(avatarUrl);
//    					// 更新avatarUrl
//    					accountMapper.updateAvatarUtilById(avatarUrl, dto.getId());
//    				}
//    			}
    			
    			return new ResultEntity(ResultEnum.SUCCESS, myTeamList);
    		} else {
    			return new ResultEntity(ResultEnum.AGENT_NO_TEAM, null);
    		}
    	} catch(Exception e) {
    		Logger.error(this,"queryMyTeam error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
    	}
    }
    
    public ResultEntity addAgentReward(AgentRewardDto agentRewardDto, AccountDto currentUser) {
    	
//        SupplierDto supplierDto = new SupplierDto();
//        supplierDto.setSupplierName(supplierName);
//        supplierDto.setCreatedBy(currentUserId);
//        supplierDto.setCreatedAt(new Date());

        try{
        	// 根据agentId代理ID，查找代理名
        	AgentDto agentDto = agentMapper.selectOneAgent(agentRewardDto.getAgentId());
        	if(agentDto != null) {
        		agentRewardDto.setAgentName(agentDto.getAgentName());
        	}
        	// 如果有下级代理ID，查找下级代理ID名
        	if(agentRewardDto.getSubAgentId() != null && !"".equals(agentRewardDto.getSubAgentId())) {
        		AgentDto subAgentDto = agentMapper.selectOneAgent(agentRewardDto.getSubAgentId());
        		if(subAgentDto != null) {
        			agentRewardDto.setSubAgentName(subAgentDto.getAgentName());
        		}
        	}
        	
        	agentRewardDto.setCreatedBy(currentUser.getId());
        	agentRewardDto.setCreatedAt(new Date());
        	agentRewardDto.setUpdatedBy(currentUser.getId());
        	agentRewardDto.setUpdatedAt(new Date());
        	
//        	if("1".equals(agentRewardDto.getRewardType())) {
//        		agentRewardDto.setRewardReason("agentReward");
//        	} else if("2".equals(agentRewardDto.getRewardType())) {
//        		agentRewardDto.setRewardReason("superAgentReward");
//        	} else if("3".equals(agentRewardDto.getRewardType())) {
//        		agentRewardDto.setRewardReason("recommendReward");
//        	}
        	
        	agentRewardMapper.insertAgentReward(agentRewardDto);
        	return new ResultEntity(ResultEnum.SUCCESS,agentRewardDto);
        	
        } catch (Exception e){
        	System.out.println("addAgentReward error : " + e.getMessage());
            Logger.error(this,"addAgentReward error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }
    
    public ResultEntity updateAgentReward(AgentRewardDto agentRewardDto, AccountDto currentUser) {
    	
    	agentRewardDto.setUpdatedBy(currentUser.getId());
    	agentRewardDto.setUpdatedAt(new Date());
    	try {
    		if(agentRewardDto.getRewardReason() == null || "".equals(agentRewardDto.getRewardReason())) {
    			if("1".equals(agentRewardDto.getRewardType())) {
            		agentRewardDto.setRewardReason("agentReward");
            	} else if("2".equals(agentRewardDto.getRewardType())) {
            		agentRewardDto.setRewardReason("superAgentReward");
            	} else if("3".equals(agentRewardDto.getRewardType())) {
            		agentRewardDto.setRewardReason("recommendReward");
            	}
    		}
    		
    		agentRewardMapper.updateAgentRewardById(agentRewardDto);
    		return new ResultEntity(ResultEnum.SUCCESS,agentRewardDto);
    		
    	} catch (Exception e){
        	System.out.println("updateAgentReward error : " + e.getMessage());
            Logger.error(this,"updateAgentReward error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }


}
