package com.trip.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.dto.AccountDto;
import com.trip.entity.AccountGson;
import com.trip.entity.AgentRegisterGson;
import com.trip.entity.DateUtils;
import com.trip.entity.EncryptedDataGson;
import com.trip.entity.PayeeGson;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.entity.UserInfoEntity;
import com.trip.entity.WxUtil;
import com.trip.service.AccountService;
import com.trip.service.AgentRewardService;
import com.trip.service.AgentService;
import com.trip.service.ArticleService;

/**
 * 小程序
 */
@Controller
public class MiniProgramController {
	private static final String OUTPUT_SRC = "src/main/webapp/image/";

    @Autowired
    private AccountService accountService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentRewardService agentRewardService;

    /**
     * 登录
     * @param accountDto
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/miniProgram/login",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String login(@RequestBody AccountDto accountDto, HttpServletRequest request,HttpServletResponse response) throws Exception {

        String username = accountDto.getUsername();
        String password = accountDto.getPassword();

        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return new ResultEntity(ResultEnum.LOSE_PARAM).toString();
        }

        ResultEntity resultEntity = accountService.checkMiniProgramUserAndPwd(username,password,true,request,response);

        return resultEntity.toString();
    }

    /**
     * 
     * @param request
     * @param tripNotifyStatus->tripNotifyStatusVal		出团状态
     * @param contractSignStatus->contractSignStatusVal	合同状态	
     * @param buyInsuranceStatus->insuranceStatusVal	保险购买状态
     * @param payAgentStatus->returnAmountStatusVal		代理付款状态
     * @param refundStatus->refundStatusVal			退款用户状态
     * @param rewardReason->incomeStatusVal			收益类型
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/miniProgram/agent/myIncome",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryMyIncome(HttpServletRequest request,String tripNotifyStatusVal,String contractSignStatusVal ,
                                String insuranceStatusVal ,String returnAmountStatusVal  ,String refundStatusVal,
                                String incomeStatusVal)
            throws Exception{
    	System.out.println("tripNotifyStatusVal : " + tripNotifyStatusVal);
    	System.out.println("contractSignStatusVal : " + contractSignStatusVal);
    	System.out.println("insuranceStatusVal : " + insuranceStatusVal);
    	System.out.println("returnAmountStatusVal : " + returnAmountStatusVal);
    	System.out.println("refundStatusVal : " + refundStatusVal);
    	System.out.println("incomeStatusVal : " + incomeStatusVal);
        Long agentId = UserInfoEntity.getUserInfo(request).getAgentId();
        ResultEntity resultEntity = agentRewardService.queryMyIncome(agentId,tripNotifyStatusVal,contractSignStatusVal,
        		insuranceStatusVal,returnAmountStatusVal,refundStatusVal,incomeStatusVal);

        return resultEntity.toString();
    }
    
    /**
     * 我的收益明细
     * @param request
     * @param orderId	订单ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/miniProgram/agent/myIncomeDetail",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryMyIncomeDetail(HttpServletRequest request, Long orderId) throws Exception {
    	Long agentId = UserInfoEntity.getUserInfo(request).getAgentId();
        ResultEntity resultEntity = agentRewardService.queryMyIncomeDetail(orderId, agentId);

        return resultEntity.toString();
    }

    /**
     * 查看特惠产品
     * @return
     */
    @RequestMapping(value="/miniProgram/article/list",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryArticle(){

        String publishTime = DateUtils.formatDate(DateUtils.add(new Date(),-15, Calendar.DATE)) ;
        ResultEntity resultEntity = articleService.selectArticleByParam(null,publishTime, "1", "99999");

        return resultEntity.toString();
    }


    /**
     * 邀请好友拿奖励
     * @return
     */
    @RequestMapping(value="/miniProgram/agent/queryMySubAgent",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String queryMySubAgent(HttpServletRequest request) throws Exception{
        Long agentId = UserInfoEntity.getUserInfo(request).getAgentId();
        ResultEntity resultEntity = agentService.querySubAgent(agentId);
        return resultEntity.toString();
    }


    /**
     * 微信小程序注册
     * @param agentRegisterGson
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/miniProgram/register",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String register(@RequestBody AgentRegisterGson agentRegisterGson, HttpServletRequest request) throws Exception{
    	
        Long currentUserId = UserInfoEntity.getUserId(request);
        ResultEntity resultEntity = agentService.agentRegister(agentRegisterGson,currentUserId,request);

        return resultEntity.toString();
    }
    
//    @RequestMapping(value="/miniProgram/authorizedLogin",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    public String authorizedLogin(@RequestParam(value="jsCode")String jsCode, HttpServletRequest request) throws Exception {
//    	ResultEntity resultEntity = accountService.authorizedLogin(jsCode, request);
//    	return null;
//    }
    
    /**
     * 我的团队
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/miniProgram/agent/queryMyTeam",method=RequestMethod.GET)
    @ResponseBody
    public String queryMyTeam(HttpServletRequest request) throws Exception {
    	AccountDto accountDto = UserInfoEntity.getUserInfo(request);
    	Long agentId = accountDto.getAgentId();
    	ResultEntity result = agentRewardService.queryMyTeam(agentId);
    	return result.toString();
    }
    
//    @RequestMapping(value="/miniProgram/inviter",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
//    public ModelAndView inviter(@RequestParam("id")Long id,ModelAndView view) {
//    	// 根据代理ID，取得代理名称
//    	ResultEntity entity = agentService.queryAgentById(id);
//    	if("00".equals(entity.getResultEnum().getResultCode()) && entity.getData() != null) {
//    		AgentDto agentDto = (AgentDto)agentService.queryAgentById(id).getData();
//        	String agentName = agentDto.getAgentName();
//        	view.addObject("superAgent",agentDto.getId());
//        	view.addObject("superAgentName",agentName);
//        	view.setViewName("regist");
//    	} else {
//    		view.addObject("resultCode",entity.getResultEnum().getResultCode());
//    		view.addObject("resultMsg",entity.getResultEnum().getResultMsg());
//    	}
//    	
//    	return view;
//    }
    
    /**
     * 我要提现
     * @return
     */
    @RequestMapping(value="/miniProgram/agent/withdrawCash",method=RequestMethod.GET)
    @ResponseBody
    public String withdrawCash(HttpServletRequest request) {
    	AccountDto currentUser = UserInfoEntity.getUserInfo(request);
    	ResultEntity resultEntity = agentService.queryAgentById(currentUser.getAgentId());
    	return resultEntity.toString();
    }
    
    /**
     * 完善收款账户信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/miniProgram/agent/updatePayee",method = RequestMethod.POST)
    @ResponseBody
    public String updateAgentPayee(@RequestBody PayeeGson payeeGson, HttpServletRequest request) throws Exception {
    	Long agentId = UserInfoEntity.getUserInfo(request).getAgentId(); // 取得登录用户的代理ID
    	System.out.println("payeeBank : " + payeeGson.getPayeeBank());
    	System.out.println("payeeAccount : " + payeeGson.getPayeeAccount());
    	System.out.println("payeeName : " + payeeGson.getPayeeName());
    	ResultEntity resultEntity = agentService.updateAgentPayee(agentId, payeeGson.getPayeeBank(), 
    			payeeGson.getPayeeAccount(), payeeGson.getPayeeName());
    	return resultEntity.toString();
    }
    
    /**
     * 邀请生成二维码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/miniProgram/agent/inviterQRCode",method = RequestMethod.GET)
    @ResponseBody
    public String inviterQRCode(HttpServletRequest request, HttpServletResponse response) {
    	AccountDto currentUser = UserInfoEntity.getUserInfo(request);
    	ResultEntity result = agentService.inviterQRCode(currentUser);
    	return result.toString();
//    	try {
//    		AccountDto currentUser = UserInfoEntity.getUserInfo(request);
//        	Long agentId = currentUser.getAgentId();
//        	String agentName = currentUser.getAgentName();
//        	ResourceBundle resource = ResourceBundle.getBundle("wx");
//        	// WX_URL
//        	String requestUrl = resource.getString("miniProgramRegistUrl");
//        	// 原图地址
//        	String srcImagePath = this.getClass().getResource("/atrip.png").getPath();
//        	// 判断图片文件是否存在
////        	String filePath = OUTPUT_SRC + agentId + ".jpg";
//////        	String filePath = OUTPUT_SRC + "2.jpg";
//        	String classpath = this.getClass().getResource("/").getPath();
//        	classpath = classpath.replaceAll("WEB-INF/classes/", "image/");
//        	File file = new File(classpath+agentId+".jpg");
//        	String outSrcFile = "image/"+agentId+".jpg";
////        	String outSrcFile = "image/"+"2.jpg";
//        	ResultEntity result;
//        	//存在
//        	if(file.exists()) {
//        		result = new ResultEntity(ResultEnum.SUCCESS, outSrcFile);
//        	// 不存在
//        	} else {
//        		requestUrl = requestUrl + "?id=" + agentId + "&agentName=" + agentName;
////            	requestUrl = requestUrl + "?id=1&agentName=2";
//            	System.out.println(requestUrl);
//            	BufferedImage image = ImageUtil.creatQrImage(requestUrl, srcImagePath);
////            	ImageIO.write(image, "JPG", response.getOutputStream());
//                // 将image对象输出到磁盘文件中
//            	ImageIO.write(image, "jpg", file);
//            	result = new ResultEntity(ResultEnum.SUCCESS, outSrcFile);
//        	}
//        	return result.toString();
//    	} catch(Exception ex) {
//    		return new ResultEntity(ResultEnum.SYS_ERROR, null).toString();
//    	}
    }
    
    @RequestMapping(value="/miniProgram/getSessionId", method=RequestMethod.GET)
    @ResponseBody
    public String getSessionKeyOrOpenId(String code, HttpServletRequest request, HttpServletResponse response) {
//    	ResultEntity resultEntity = WxUtil.getSessionKeyOrOpenId(code, request, response);
    	ResultEntity resultEntity = accountService.getThirdSessionIdByCode(code, request);
    	return resultEntity.toString();
    }
    
    /**
     * 解密用户数据/授权
     * @param encryptedData
     * @param iv
     * @param signature
     * @param rawData
     * @param jsCode
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/miniProgram/authorizedLogin", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    public String authorizedLogin(@RequestBody EncryptedDataGson encryptedDataGson,
    							  HttpServletRequest request) throws Exception {
    	// 从SESSION里取得session_key
//    	AccountDto currentUser = UserInfoEntity.getUserInfo(request);
    	// 娶到SESSION_KEY
//    	String sessionKey = "";
//    	String result = AesUtil.decryptData(encryptedData, sessionKey, iv);
//    	AccountDto currentUser = UserInfoEntity.getUserInfo(request);
//    	String thirdSessionId = currentUser.getThirdSessionId();
    	String encryptedData = encryptedDataGson.getEncryptedData();
    	String iv = encryptedDataGson.getIv();
    	String signature = encryptedDataGson.getSignature();
    	String rawData = encryptedDataGson.getRawData();
    	String jsCode = encryptedDataGson.getJsCode();
    	ResultEntity resultEntity = accountService.authorizedLogin(encryptedData, iv, signature, rawData, jsCode, request);
    	return resultEntity.toString();
    }
    
    /**
     * 解密用户数据/授权
     * @param encryptedData
     * @param iv
     * @param signature
     * @param rawData
     * @param jsCode
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/miniProgram/authorizedLoginOrRegister", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    public String authorizedLoginOrRegister(@RequestBody EncryptedDataGson encryptedDataGson,
			  HttpServletRequest request) throws Exception {
    	ResultEntity resultEntity = accountService.authorizedLoginOrRegister(encryptedDataGson, request);
    	System.out.println(resultEntity.toString());
    	return resultEntity.toString();
    }
    
    
    @RequestMapping(value="/miniProgram/sessionValidate",  method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String sessionValidate(HttpServletRequest request) throws Exception {
    	AccountDto currentUser = UserInfoEntity.getUserInfo(request);
        if( currentUser == null ) {
        	ResultEntity resultEntity = new ResultEntity(ResultEnum.NOT_LOGIN);
        	return resultEntity.toString();
//        	
//            httpResponse.setContentType("application/json");
//            httpResponse.getWriter().write( new ResultEntity(ResultEnum.NOT_LOGIN).toString());
//            return;
        }
        
        return new ResultEntity(ResultEnum.SUCCESS, currentUser).toString();
    }
    
    @RequestMapping(value="/miniProgram/getAccessToken", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String getAccessTocken(HttpServletRequest request) throws Exception {
    	String accessToken = WxUtil.getAccessToken();
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("accessToken", accessToken);
    	ResultEntity resultEntity = new ResultEntity(ResultEnum.SUCCESS, map);
    	return resultEntity.toString();
    }
    
    @RequestMapping(value="/miniProgram/getAccountInfo", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    public String getAccountInfo(@RequestBody AccountGson accountGson, HttpServletRequest request) {
    	ResultEntity resultEntity = accountService.getAccountInfo(accountGson, request); 
    	return resultEntity.toString();
    }

}
