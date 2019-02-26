
package com.trip.controller;


import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.dto.AccountDto;
import com.trip.dto.BookDetailDto;
import com.trip.dto.CompensateDetailDto;
import com.trip.dto.OrderDto;
import com.trip.entity.BuyInfoFormulGson;
import com.trip.entity.DateUtils;
import com.trip.entity.LogTypeEnum;
import com.trip.entity.OperateTypeEnum;
import com.trip.entity.OrderQueryEntity;
import com.trip.entity.OrderQueryResultEntity;
import com.trip.entity.ResultEntity;
import com.trip.entity.UserInfoEntity;
import com.trip.service.LogAopAnnotation;
import com.trip.service.OrderService;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    private final String[] ORDER_SELL_TITLE = {"订单号","订单创建时间","代理名","联系人姓名","出游时间","产品名称","供应商名",
    			"成人销售单价","成人人数","儿童销售单价","儿童人数","婴儿销售单价","婴儿人数","单房差销售单价","单房差人数","附加费销售单价","附加费人数",
    			"签证销售单价","签证人数","合计(规格*销售单价*人数)","实收","付款方式",
    			"付款代理-应付","付款代理-实付","付款代理-剩余","付款代理-状态",
    			"付款上级代理-应付","付款上级代理-实付","付款上级代理-剩余","付款上级代理-状态",
        		"付款供应商(规格*采购单价*人数)","付款供应商(规格*财务结算单价*人数)",
        		"付款供应-状态","订单状态",
        		"付款用户退款/赔偿-应付","付款用户退款/赔偿-实付","付款用户退款/赔偿-剩余","付款用户退款/赔偿-状态",
        		"合同签约","保险购买", "上级代理名", "产品类型", "出游类型", "公司预估总毛利"};

    /**
     * 新增订单
     * @param orderDto
     * @param request
     * @return
     */
    @RequestMapping(value="/order/add",method = RequestMethod.POST)
    @ResponseBody
    @LogAopAnnotation(logType = LogTypeEnum.ORDER_TYPE,operateType = OperateTypeEnum.ADD)
    public String addOrder(@RequestBody OrderDto orderDto,HttpServletRequest request) throws Exception{

        AccountDto currentUser = UserInfoEntity.getUserInfo(request);
        ResultEntity resultEntity = orderService.addOrder(orderDto,currentUser);

        return resultEntity.toString();
    }


    @RequestMapping(value="/order/query",method = RequestMethod.GET)
    @ResponseBody
    public String queryOrder(@RequestParam(value="orderId",required = false) String orderId               ,
                             @RequestParam(value="orderCreateStartTime",required = false) String orderCreateStartTime  ,
                             @RequestParam(value="orderCreateEndTime",required = false) String orderCreateEndTime    ,
                             @RequestParam(value="tripCreateStartTime",required = false) String tripCreateStartTime   ,
                             @RequestParam(value="tripCreateEndTime",required = false) String tripCreateEndTime     ,
                             @RequestParam(value="productName",required = false) String productName           ,
                             @RequestParam(value="supplierId",required = false) String supplierId          ,
                             @RequestParam(value="salesman",required = false) String salesman              ,
                             @RequestParam(value="touristPhone",required = false) String touristPhone          ,
                             @RequestParam(value="agentName",required = false) String agentName             ,
                             @RequestParam(value="tripNotifyStatus",required = false) String tripNotifyStatus      ,
                             @RequestParam(value="contractSignStatus",required = false) String contractSignStatus    ,
                             @RequestParam(value="buyInsuranceStatus",required = false) String buyInsuranceStatus    ,
                             @RequestParam(value="gatheringStatus",required = false) String gatheringStatus       , // 收款状态
                             @RequestParam(value="payAgentStatus",required = false) String payAgentStatus        ,
                             @RequestParam(value="paySuperAgentStatus",required = false) String paySuperAgentStatus   ,
                             @RequestParam(value="paySupplierStatus",required = false) String paySupplierStatus     ,
                             @RequestParam(value="refundStatus",required = false) String refundStatus          , // 付款用户退款状态
                             @RequestParam(value="noPurchasePrice",required = false) String noPurchasePrice       ,
                             // add 20180801 bug13,bug14 增加订单状态 start
                             @RequestParam(value="orderStatus",required = false) String orderStatus, // 订单状态
                             // add 20180801 bug13,bug14 增加订单状态 end
                             // add 20180802 bug15 查询字段增加出游人姓名和付款方式 start
                             @RequestParam(value="tourist",required = false) String tourist, // 出游人姓名
                             @RequestParam(value="payType",required = false) String payType, // 付款方式
                             // add 20180802 bug15 查询字段增加出游人姓名和付款方式 end
                             // add 20180802 bug23 查询/列表字段增加出游类型 start
                             @RequestParam(value="tripType",required = false) String tripType, 	// 出游类型
                             // add 20180802 bug23 查询/列表字段增加出游类型 end
                             // add 20180811 新增功能9 查询/列表增加出游人信息 start
                             @RequestParam(value="touristInfo",required=false) String touristInfo, // 出游人信息
                             // add 20180811 新增功能9 查询/列表增加出游人信息 end 
                             // add 20180811 新增功能 订单产品调整-增加定金收款状态 start
                             @RequestParam(value="depositStatus",required=false) String depositStatus, // 定金收款状态
                             // add 20180811 新增功能 订单产品调整-增加定金收款状态 end
                             @RequestParam(value="supplierName",required=false) String supplierName,	// 供应商名
                             @RequestParam(value="purchasePriceStatus",required=false) String purchasePriceStatus, // 订单采购价状态 0 不限, 1 未填写 2 已填写
                             @RequestParam(value="pageNum", required=false) String pageNum,	// 页数
                             @RequestParam(value="pageSize", required=false) String pageSize,	// 条数
                             
                             HttpServletRequest request) throws Exception{

        AccountDto currentUser = UserInfoEntity.getUserInfo(request);
//    	AccountDto currentUser = new AccountDto();
        // 日期转换
        if(orderCreateStartTime != null && !"".equals(orderCreateStartTime)) {
        	orderCreateStartTime = DateUtils.format(DateUtils.DATE_FORMAT_SOLIDUS, 
        			DateUtils.parseDate(orderCreateStartTime, DateUtils.DATE_FORMAT_SOLIDUS));
        	orderCreateStartTime = orderCreateStartTime + " " + "00:00:00";
        }
        
        if(orderCreateEndTime != null && !"".equals(orderCreateEndTime)) {
        	orderCreateEndTime = DateUtils.format(DateUtils.DATE_FORMAT_SOLIDUS, 
        			DateUtils.parseDate(orderCreateEndTime, DateUtils.DATE_FORMAT_SOLIDUS));
        	orderCreateEndTime = orderCreateEndTime + " " + "23:59:59";
        }

		if(tripCreateStartTime != null && !"".equals(tripCreateStartTime)) {
			tripCreateStartTime = DateUtils.format(DateUtils.DATE_FORMAT_SOLIDUS, 
        			DateUtils.parseDate(tripCreateStartTime, DateUtils.DATE_FORMAT_SOLIDUS));
			tripCreateStartTime = tripCreateStartTime + " " + "00:00:00";
		}
		
		if(tripCreateEndTime != null && !"".equals(tripCreateEndTime)) {
			tripCreateEndTime = DateUtils.format(DateUtils.DATE_FORMAT_SOLIDUS, 
        			DateUtils.parseDate(tripCreateEndTime, DateUtils.DATE_FORMAT_SOLIDUS));
			tripCreateEndTime = tripCreateEndTime + " " + "23:59:59";
		}
        
        OrderQueryEntity orderQueryEntity = new OrderQueryEntity(orderId, orderCreateStartTime, orderCreateEndTime,
                tripCreateStartTime,tripCreateEndTime, productName, supplierId, salesman,
                touristPhone, agentName, tripNotifyStatus, contractSignStatus,
                buyInsuranceStatus, gatheringStatus, payAgentStatus, paySuperAgentStatus,
                paySupplierStatus, refundStatus, noPurchasePrice, orderStatus, tourist, payType, tripType, touristInfo,
                depositStatus,supplierName,purchasePriceStatus);
        
        orderQueryEntity.setPageNum(pageNum);
        orderQueryEntity.setPageSize(pageSize);
        
        ResultEntity resultEntity = orderService.queryOrder(orderQueryEntity,currentUser, "0");

        return resultEntity.toString();
    }
    
    // add by 20180807 bug15 财务订单批量导出 start
    @RequestMapping(value="/order/export", method = RequestMethod.GET)
    @ResponseBody
    public void orderExport(@RequestParam(value="orderId",required = false) String orderId               ,
				            @RequestParam(value="orderCreateStartTime",required = false) String orderCreateStartTime  ,
				            @RequestParam(value="orderCreateEndTime",required = false) String orderCreateEndTime    ,
				            @RequestParam(value="tripCreateStartTime",required = false) String tripCreateStartTime   ,
				            @RequestParam(value="tripCreateEndTime",required = false) String tripCreateEndTime     ,
				            @RequestParam(value="productName",required = false) String productName           ,
				            @RequestParam(value="supplierId",required = false) String supplierId          ,
				            @RequestParam(value="salesman",required = false) String salesman              ,
		                    @RequestParam(value="touristPhone",required = false) String touristPhone          ,
			                @RequestParam(value="agentName",required = false) String agentName             ,
			                @RequestParam(value="tripNotifyStatus",required = false) String tripNotifyStatus      ,
			                @RequestParam(value="contractSignStatus",required = false) String contractSignStatus    ,
			                @RequestParam(value="buyInsuranceStatus",required = false) String buyInsuranceStatus    ,
			                @RequestParam(value="gatheringStatus",required = false) String gatheringStatus       ,
			                @RequestParam(value="payAgentStatus",required = false) String payAgentStatus        ,
			                @RequestParam(value="paySuperAgentStatus",required = false) String paySuperAgentStatus   ,
			                @RequestParam(value="paySupplierStatus",required = false) String paySupplierStatus     ,
			                @RequestParam(value="refundStatus",required = false) String refundStatus          ,
			                @RequestParam(value="noPurchasePrice",required = false) String noPurchasePrice       ,
			                @RequestParam(value="orderStatus",required = false) String orderStatus, 
			                @RequestParam(value="tourist",required = false) String tourist, // 出游人姓名
			                @RequestParam(value="payType",required = false) String payType, // 付款方式
			                @RequestParam(value="tripType",required = false) String tripType, 	// 出游类型
                            // add 20180811 新增功能9 查询/列表增加出游人信息 start
                            @RequestParam(value="touristInfo",required=false) String touristInfo, // 出游人信息
                            // add 20180811 新增功能9 查询/列表增加出游人信息 end 
                            // add 20180811 新增功能 订单产品调整-增加定金收款状态 start
                            @RequestParam(value="depositStatus",required=false) String depositStatus, // 定金收款状态
                            // add 20180811 新增功能 订单产品调整-增加定金收款状态 end
                            @RequestParam(value="supplierName",required=false) String supplierName,	// 供应商名
                            @RequestParam(value="purchasePriceStatus",required=false) String purchasePriceStatus, // 订单采购价状态 0 不限, 1 未填写 2 已填写
                            
				              HttpServletRequest request, HttpServletResponse response) throws Exception {
    	AccountDto currentUser = UserInfoEntity.getUserInfo(request);
//    	AccountDto currentUser = new AccountDto();
        // 日期转换
        if(orderCreateStartTime != null && !"".equals(orderCreateStartTime)) {
        	orderCreateStartTime = DateUtils.format(DateUtils.DATE_FORMAT_SOLIDUS, 
        			DateUtils.parseDate(orderCreateStartTime, DateUtils.DATE_FORMAT_SOLIDUS));
        	orderCreateStartTime = orderCreateStartTime + " " + "00:00:00";
        }
        
        if(orderCreateEndTime != null && !"".equals(orderCreateEndTime)) {
        	orderCreateEndTime = DateUtils.format(DateUtils.DATE_FORMAT_SOLIDUS, 
        			DateUtils.parseDate(orderCreateEndTime, DateUtils.DATE_FORMAT_SOLIDUS));
        	orderCreateEndTime = orderCreateEndTime + " " + "23:59:59";
        }

		if(tripCreateStartTime != null && !"".equals(tripCreateStartTime)) {
			tripCreateStartTime = DateUtils.format(DateUtils.DATE_FORMAT_SOLIDUS, 
        			DateUtils.parseDate(tripCreateStartTime, DateUtils.DATE_FORMAT_SOLIDUS));
			tripCreateStartTime = tripCreateStartTime + " " + "00:00:00";
		}
		
		if(tripCreateEndTime != null && !"".equals(tripCreateEndTime)) {
			tripCreateEndTime = DateUtils.format(DateUtils.DATE_FORMAT_SOLIDUS, 
        			DateUtils.parseDate(tripCreateEndTime, DateUtils.DATE_FORMAT_SOLIDUS));
			tripCreateEndTime = tripCreateEndTime + " " + "23:59:59";
		}
        
        OrderQueryEntity orderQueryEntity = new OrderQueryEntity(orderId, orderCreateStartTime, orderCreateEndTime,
                tripCreateStartTime,tripCreateEndTime, productName, supplierId, salesman,
                touristPhone, agentName, tripNotifyStatus, contractSignStatus,
                buyInsuranceStatus, gatheringStatus, payAgentStatus, paySuperAgentStatus,
                paySupplierStatus, refundStatus, noPurchasePrice, orderStatus, tourist, payType, tripType, touristInfo,
                depositStatus,supplierName,purchasePriceStatus);

        ResultEntity resultEntity = orderService.queryOrder(orderQueryEntity,currentUser, "1");
        
        Long currentTimeMillis = System.currentTimeMillis();
        String filename = "财务_" + currentTimeMillis + ".xls";  // 导出名称
        // 创建Excel工作薄
        WritableWorkbook wwb;
		response.setContentType("application/vnd.ms-excel; charset=utf-8");// 设置输出类型
		response.setHeader("Content-Disposition", "attachment;filename="
				+ filename);// 设置文件头
		response.setCharacterEncoding("utf-8");
		OutputStream os = response.getOutputStream();
		wwb = Workbook.createWorkbook(os);
		WritableSheet sheet = wwb.createSheet("财务订单", 0);
        
        Label label;
        for(int i = 0; i < ORDER_SELL_TITLE.length; i++) {
        	// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
        	CellView cellView = new CellView();
			cellView.setSize(17 * 256); // 设置列宽
			sheet.setColumnView(i, cellView);
			label = new Label(i, 0, ORDER_SELL_TITLE[i], getHeader());
			sheet.addCell(label);
        }
        
        List<OrderQueryResultEntity> resultList = (List<OrderQueryResultEntity>)resultEntity.getData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日Eahh点mm分", Locale.CHINA);
        for(int i = 0; i < resultList.size(); i++) {
        	OrderQueryResultEntity entity = resultList.get(i);
        	
        	// 订单号
        	sheet.addCell(new Label(0, i + 1, entity.getId().toString()));
        	// 订单创建时间
        	sheet.addCell(new Label(1, i + 1, sdf.format(entity.getCreatedAt())));
        	// 代理名
        	sheet.addCell(new Label(2, i + 1, entity.getAgentName()));
        	// 联系人姓名
        	sheet.addCell(new Label(3, i + 1, entity.getTourist()));
        	// 出游时间
        	sheet.addCell(new Label(4, i + 1, sdf.format(entity.getDepartureTime())));
        	// 产品名称
        	sheet.addCell(new Label(5, i + 1, entity.getProductName()));
        	// 供应商名
        	sheet.addCell(new Label(6, i + 1, entity.getSupplierName()));
        	List<BookDetailDto> bookDetailList = entity.getBookingList();
        	for(BookDetailDto bookDetailDto : bookDetailList) {
        		
//        		"成人销售单价","成人人数","儿童销售单价","儿童人数","婴儿销售单价","婴儿人数","单房差销售单价","单房差人数","附加费销售单价","附加费人数","签证销售单价","签证人数"
        		if( bookDetailDto.getStandard().equals("成人") ) {
        			// 成人销售单价
        			sheet.addCell(new Label(7, i + 1, bookDetailDto.getSalePrice().toString()));
        			// 成人人数
        			sheet.addCell(new Label(8, i + 1, bookDetailDto.getBuyAmount().toString()));
        		} else if( bookDetailDto.getStandard().equals("儿童") ) {
        			// 儿童销售单价
        			sheet.addCell(new Label(9, i + 1, bookDetailDto.getSalePrice().toString()));
        			// 儿童人数
        			sheet.addCell(new Label(10, i + 1, bookDetailDto.getBuyAmount().toString()));
        		} else if( bookDetailDto.getStandard().equals("婴儿价") ) {
        			// 婴儿销售单价
        			sheet.addCell(new Label(11, i + 1, bookDetailDto.getSalePrice().toString()));
        			// 婴儿人数
        			sheet.addCell(new Label(12, i + 1, bookDetailDto.getBuyAmount().toString()));
        		} else if( bookDetailDto.getStandard().equals("单房差") ) {
        			// 单房差销售单价
        			sheet.addCell(new Label(13, i + 1, bookDetailDto.getSalePrice().toString()));
        			// 单房差人数
        			sheet.addCell(new Label(14, i + 1, bookDetailDto.getBuyAmount().toString()));
        		} else if( bookDetailDto.getStandard().equals("附加费") ){
        			// 附加费销售单价
        			sheet.addCell(new Label(15, i + 1, bookDetailDto.getSalePrice().toString()));
        			// 附加费人数
        			sheet.addCell(new Label(16, i + 1, bookDetailDto.getBuyAmount().toString()));
        		} else if( bookDetailDto.getStandard().equals("签证") ) {
        			// 签证销售单价
        			sheet.addCell(new Label(17, i + 1, bookDetailDto.getSalePrice().toString()));
        			// 签证人数
        			sheet.addCell(new Label(18, i + 1, bookDetailDto.getBuyAmount().toString()));
        		}
        	}
        	
        	// 合计(规格*销售单价*人数)
        	sheet.addCell(new Label(19, i + 1, ""));
        	// 实收
        	sheet.addCell(new Label(20, i + 1, entity.getrAActualReceipt().toString()));
        	// 付款方式
        	sheet.addCell(new Label(21, i + 1, entity.getPayTypeName()));
        	// 付款代理-应付
        	sheet.addCell(new Label(22, i + 1, entity.getaAReceivable().toString()));
        	// 付款代理-实收
        	sheet.addCell(new Label(23, i + 1, entity.getaAActualReceipt().toString()));
        	// 付款代理-剩余
        	sheet.addCell(new Label(24, i + 1, entity.getaASurplus().toString()));
        	// 付款代理-状态
        	sheet.addCell(new Label(25, i + 1, entity.getPayAgentStatusName()));
        	// 付款上级代理-应付
        	sheet.addCell(new Label(26, i + 1, entity.getsAAReceivable().toString()));
        	// 付款上级代理-实收
        	sheet.addCell(new Label(27, i + 1, entity.getsAAActualReceipt().toString()));
        	// 付款上级代理-剩余
        	sheet.addCell(new Label(28, i + 1, entity.getsAASurplus().toString()));
        	// 付款上级代理-状态
        	sheet.addCell(new Label(29, i + 1, entity.getPaySuperAgentStatusName()));
        	
        	BuyInfoFormulGson buyInfoFormul = entity.getBuyInfoFinalFormula();
        	// 付款供应商(规格*采购单价*人数)
        	sheet.addCell(new Label(30, i + 1, buyInfoFormul.getPurchaseTotal().toString()));
        	// 付款供应商(规格*财务结算单价*人数)
        	sheet.addCell(new Label(31, i + 1, buyInfoFormul.getSettlementTotal().toString()));
        	// 付款供应-状态
        	sheet.addCell(new Label(32, i + 1, entity.getPaySupplierStatusName()));
        	// 订单状态
        	sheet.addCell(new Label(33, i + 1, entity.getOrderStatusName()));
        	// 付款用户退款/赔偿-应付
        	sheet.addCell(new Label(34, i + 1, entity.getrFAReceivable().toString()));
        	// 付款用户退款/赔偿-实收
        	sheet.addCell(new Label(35, i + 1, entity.getrFAActualReceipt().toString()));
        	// 付款用户退款/赔偿-剩余
        	sheet.addCell(new Label(36, i + 1, entity.getrFASurplus().toString()));
        	// 付款用户退款/赔偿-状态
        	sheet.addCell(new Label(37, i + 1, entity.getRefundStatusName()));
        	// 合同签约
        	sheet.addCell(new Label(38, i + 1, entity.getContractSignStatusName()));
        	// 保险购买
        	sheet.addCell(new Label(39, i + 1, entity.getBuyInsuranceStatusName()));
        	// 上级代理名
        	sheet.addCell(new Label(40, i + 1, entity.getSuperAgentName()));
        	// 产品类型
        	sheet.addCell(new Label(41, i + 1, entity.getProductTypeName()));
        	// 出游类型
        	sheet.addCell(new Label(42, i + 1, entity.getTripTypeName()));
        	
        	// 公司预估总毛利=订单销售金额-采购总金额-代理总金额-上级代理金额-赔偿单总金额
        	BuyInfoFormulGson buyInfoFormulGson = entity.getBuyInfoFinalFormula();
        	BigDecimal grossProfitForecast = BigDecimal.ZERO; 							// 预估总毛利
        	BigDecimal salesTotal = buyInfoFormulGson.getSalesTotal();					// 销售总金额
        	BigDecimal purchaseTotal = buyInfoFormulGson.getPurchaseTotal();			// 采购总金额
        	BigDecimal agentTotal = buyInfoFormulGson.getAgentTotal();					// 代理总金额
        	BigDecimal upAgentRefundTotal = buyInfoFormulGson.getUpAgentRefundTotal();	// 上级代理返佣金额
        	BigDecimal compensateTotal = buyInfoFormulGson.getCompensateTotal();		// 赔偿单总金额
        	grossProfitForecast = salesTotal.subtract(purchaseTotal).subtract(agentTotal).subtract(upAgentRefundTotal).subtract(compensateTotal);
        	
        	// 公司预估总毛利
        	sheet.addCell(new Label(43, i + 1, grossProfitForecast.toString()));
        }
        
        // 写出到硬盘文件
     	wwb.write();
     	wwb.close();
    }
    
    /**
	 * 设置execl表头的样式
	 * 
	 * @return
	 */
	public static WritableCellFormat getHeader() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 10,
				WritableFont.BOLD);// 定义字体
		try {
			font.setColour(Colour.BLUE);// 蓝色字体
		} catch (WriteException e1) {
			e1.printStackTrace();
		}
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(Alignment.CENTRE);// 左右居中
			format.setVerticalAlignment(VerticalAlignment.CENTRE);// 上下居中
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
			format.setBackground(Colour.YELLOW);// 黄色背景
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;
	}
    // add by 20180807 bug15 财务订单批量导出 end


    @RequestMapping(value="/order/detail/query",method = RequestMethod.GET)
    @ResponseBody
    public String queryOrderDetail(@RequestParam(value="orderId") Long orderId,
                             HttpServletRequest request) throws Exception{

        AccountDto currentUser = UserInfoEntity.getUserInfo(request);

        ResultEntity resultEntity = orderService.queryOrderDetail(orderId,currentUser);

        return resultEntity.toString();
    }

    @RequestMapping(value="/order/update",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    @LogAopAnnotation(logType = LogTypeEnum.ORDER_TYPE,operateType = OperateTypeEnum.UPDATE)
    public String updateOrder(@RequestBody OrderQueryResultEntity orderQueryEntity,HttpServletRequest request){

        AccountDto currentUser = UserInfoEntity.getUserInfo(request);
//        AccountDto currentUser = new AccountDto();
//        currentUser.setId(1L);
//        currentUser.setAccountType("1");
        ResultEntity resultEntity = orderService.updateOrder(orderQueryEntity,currentUser);
        return resultEntity.toString();
    }


    /**
     * 新增退款单
     * @param bookDetailDtoList
     * @param request
     * @return
     */
    @RequestMapping(value="/order/refund/add/{orderId}",method = RequestMethod.POST)
    @ResponseBody
    public String addOrderRefund(@RequestBody List<BookDetailDto> bookDetailDtoList,
                                 @PathVariable Long orderId,
                                 HttpServletRequest request) throws Exception{

        AccountDto currentUser = UserInfoEntity.getUserInfo(request);
        ResultEntity resultEntity = orderService.addOrderRefund(bookDetailDtoList,currentUser,orderId);

        return resultEntity.toString();
    }

    /**
     * 查询退款单
     * @param orderId
     * @param request
     * @return
     */
    @RequestMapping(value="/order/refund/query",method = RequestMethod.GET)
    @ResponseBody
    public String queryOrderRefund(@RequestParam(value="orderId") Long orderId, HttpServletRequest request) throws Exception{

        ResultEntity resultEntity = orderService.queryOrderRefund(orderId);
        return resultEntity.toString();
    }
    
    /**
     * 查看赔偿单
     * @param orderId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="order/compensate/query",method=RequestMethod.GET)
    @ResponseBody
    public String queryOrderCompensate(@RequestParam(value="orderId") Long orderId, HttpServletRequest request) throws Exception{
    	ResultEntity resultEntity = orderService.queryOrderCompensate(orderId);
    	return resultEntity.toString();
    }

    /**
     * 新增赔偿单
     * @param request
     * @return
     */
    @RequestMapping(value="/order/compensate/add/{orderId}",method = RequestMethod.POST)
    @ResponseBody
    public String addOrderCompensate(@RequestBody CompensateDetailDto compensateDetailDto,
    							 	 @PathVariable Long orderId,
    							 	 HttpServletRequest request) throws Exception{

        AccountDto currentUser = UserInfoEntity.getUserInfo(request);
        ResultEntity resultEntity = orderService.addOrderCompensate(compensateDetailDto,currentUser,orderId);

        return resultEntity.toString();
    }

}
