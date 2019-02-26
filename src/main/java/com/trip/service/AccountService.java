package com.trip.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trip.MySessionContext;
import com.trip.dto.AccountDto;
import com.trip.dto.AgentDto;
import com.trip.dto.EmployeeDto;
import com.trip.entity.AccountGson;
import com.trip.entity.AesUtil;
import com.trip.entity.EncryptedDataGson;
import com.trip.entity.Logger;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.entity.UserInfoEntity;
import com.trip.entity.WxUtil;
import com.trip.mapper.AccountMapper;
import com.trip.mapper.AgentMapper;

import net.sf.json.JSONObject;


@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AgentMapper agentMapper;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public ResultEntity checkUserAndPwd(String username, String password,boolean isAgent,
                                        HttpServletRequest request,HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("userInfo");
            AccountDto accountDto = accountMapper.checkUserAndPwd(username,password);
            
            if (accountDto == null) {
                return new ResultEntity(ResultEnum.USERNAME_PWD_NOT_MATCH);
            }
            if(isAgent) {
            	if( accountDto.getAgentId() == null){
                  return new ResultEntity(ResultEnum.NOT_AGENT);
              }
              AgentDto agent = agentMapper.selectSuperAgentByParam(accountDto.getAgentId());
              accountDto.setAgentName(agent.getAgentName());
              accountDto.setSuperAgentId(agent.getSuperAgent());
              accountDto.setSuperAgentName(agent.getSuperAgentName());
              accountDto.setAgentType(agent.getAgentType());
              accountDto.setAgentTypeName(agent.getAgentTypeName());
            }
//            if("5".equals(accountDto.getAccountType())){
//                if( accountDto.getAgentId() == null){
//                    return new ResultEntity(ResultEnum.NOT_AGENT);
//                }
//                AgentDto agent = agentMapper.selectSuperAgentByParam(accountDto.getAgentId());
//                accountDto.setAgentName(agent.getAgentName());
//                accountDto.setSuperAgentId(agent.getSuperAgent());
//                accountDto.setSuperAgentName(agent.getSuperAgentName());
//                accountDto.setPayeeAccount(agent.getPayeeAccount());
//                accountDto.setPayeeBank(agent.getPayeeBank());
//                accountDto.setPayeeName(agent.getPayeeName());
//                accountDto.setPayeePhone(agent.getPayeePhone());
//
//            }
            
            // 登录成功 把password
            accountDto.setPassword("");
//            accountDto.setOpenId("");

            String sessionId = request.getSession().getId();
            accountDto.setJSESSIONID(sessionId);
            //把用户数据保存在session域对象中
            session.setAttribute("userInfo", accountDto);
            MySessionContext.addSession(session);

//            accountDto.setPassword("");//把password清除
//            String user = URLEncoder.encode(new Gson().toJson(accountDto), "UTF-8");
//            CookieUtils.setCookie(response,CookieUtils.WX_CK_ATRIP,CookieUtils.SCENE_7DAY_EXPIRE_SECONDS,user);

            return new ResultEntity(ResultEnum.SUCCESS,accountDto);
        } catch (Exception e) {
            Logger.error(this, "checkUserAndPwd error :", e);
            return new ResultEntity(ResultEnum.SYS_ERROR, null);
        }
    }
    
    /**
     * 小程序登录
     * @param username
     * @param password
     * @param isAgent
     * @param request
     * @param response
     * @return
     */
    public ResultEntity checkMiniProgramUserAndPwd(String username, String password,boolean isAgent,
            HttpServletRequest request,HttpServletResponse response) {
    	try {
    		HttpSession session = request.getSession();
            session.removeAttribute("userInfo");
            AccountDto accountDto = accountMapper.checkUserAndPwd(username,password);
            
            if (accountDto == null) {
                return new ResultEntity(ResultEnum.USERNAME_PWD_NOT_MATCH);
            }
            
            if(isAgent) {
            	if(accountDto.getAgentId() == null || !"5".equals(accountDto.getAccountType())) {
            		return new ResultEntity(ResultEnum.NOT_AGENT);
            	}
            	
            	AgentDto agent = agentMapper.selectSuperAgentByParam(accountDto.getAgentId());
            	accountDto.setAgentName(agent.getAgentName());
            	accountDto.setSuperAgentId(agent.getSuperAgent());
            	accountDto.setSuperAgentName(agent.getSuperAgentName());
            	accountDto.setPayeeAccount(agent.getPayeeAccount());
            	accountDto.setPayeeBank(agent.getPayeeBank());
            	accountDto.setPayeeName(agent.getPayeeName());
            	accountDto.setPayeePhone(agent.getPayeePhone());
            	accountDto.setAgentType(agent.getAgentType());
            	accountDto.setAgentTypeName(agent.getAgentTypeName());
            	accountDto.setVipRemark(agent.getVipRemark());
            }
            
            // 登录成功 把password清空
            accountDto.setPassword("");
            // 登录成功 把openId清空
//            accountDto.setOpenId("");

            String sessionId = request.getSession().getId();
            accountDto.setJSESSIONID(sessionId);
            //把用户数据保存在session域对象中
            session.setAttribute("userInfo", accountDto);
            MySessionContext.addSession(session);
            
          	return new ResultEntity(ResultEnum.SUCCESS,accountDto);
            

    	} catch(Exception e) {
    		Logger.error(this, "checkUserAndPwd error :", e);
            return new ResultEntity(ResultEnum.SYS_ERROR, null);
    	}
    }
    
    /**
     * 授权登录
     * @param encryptedData
     * @param iv
     * @param signature
     * @param rawData
     * @param thirdSessionId
     * @return
     */
    public ResultEntity authorizedLogin(String encryptedData, String iv, String signature, String rawData, 
    									String jsCode, HttpServletRequest request) {
    	try {
    		HttpSession session = request.getSession();
    		session.removeAttribute("userInfo");
    		// 根据jsCode，取得openId和sessionKey
//    		String codeKey = getOpenIdByCode(jsCode, request);
    		// 通过JS_CODE,取得sessionKey和openId
            JSONObject jsonObject = getOpenIdByCode(jsCode, request);
            if(jsonObject == null) {
            	return new ResultEntity(ResultEnum.WX_JSCODE_ERROR);
            } else if(jsonObject.containsKey("errcode")) {
            	return new ResultEntity(ResultEnum.WX_JSCODE_ERROR);
            }
            
            // 取得openId和sessionKey
         	String openId = jsonObject.getString("openid");
         	String sessionKey = jsonObject.getString("session_key");
         	String keyCode = openId + "ß" + sessionKey;
            System.out.println("openId : " + openId);
            System.out.println("sessionKey : " + sessionKey);
            String sha1 = rawData + sessionKey;
            String signature1 = AesUtil.getSha1(sha1);
            if(signature.equals(signature1)) {
            	// 取到用户信息
            	String userInfo = AesUtil.decryptData(encryptedData, sessionKey, iv);
            	JSONObject json = JSONObject.fromObject(userInfo);
            	String id = json.getString("openId");
            	String avatarUrl = json.getString("avatarUrl");
            	if(!StringUtils.equals(id, openId)) {
            		return new ResultEntity(ResultEnum.WX_OPENID_ERROR, null);
            	}
            	// 根据OPENID找到相应的账号
            	AccountDto accountDto = accountMapper.checkUserByOpenId(openId);
	            if (accountDto == null) {
	            	return new ResultEntity(ResultEnum.USERNAME_PWD_NOT_MATCH);
		        }
		        if("5".equals(accountDto.getAccountType())){
		            if( accountDto.getAgentId() == null){
		                return new ResultEntity(ResultEnum.NOT_AGENT);
		            }
		            AgentDto agent = agentMapper.selectSuperAgentByParam(accountDto.getAgentId());
		            accountDto.setAgentName(agent.getAgentName());
		            accountDto.setSuperAgentId(agent.getSuperAgent());
		            accountDto.setSuperAgentName(agent.getSuperAgentName());
		            accountDto.setPayeeAccount(agent.getPayeeAccount());
		            accountDto.setPayeeBank(agent.getPayeeBank());
		            accountDto.setPayeeName(agent.getPayeeName());
		            accountDto.setPayeePhone(agent.getPayeePhone());
		
		        }
	        
		        // 登录成功 把password
		        accountDto.setPassword("");
//		        accountDto.setOpenId("");
		        // 生成thirdSessionid
		        // 加密
				byte[] encode = AesUtil.encrypt(keyCode);
		        
		        //传输过程,不转成16进制的字符串，就等着程序崩溃掉吧
		        String thirdSessionId = AesUtil.parseByte2HexStr(encode);
		        accountDto.setThirdSessionId(thirdSessionId);
		        accountDto.setAvatarUrl(avatarUrl);
		        
		        // 更新头像
		        if(avatarUrl != null && avatarUrl.length() > 0) {
		        	accountMapper.updateAvatarUtl(avatarUrl, openId);
		        }
		
		        String sessionId = request.getSession().getId();
		        accountDto.setJSESSIONID(sessionId);
		        //把用户数据保存在session域对象中
		        session.setAttribute("userInfo", accountDto);
		        MySessionContext.addSession(session);
		        
		        return new ResultEntity(ResultEnum.SUCCESS,accountDto);
            }
            
            return new ResultEntity(ResultEnum.WX_SIGNATURE_ERROR, null);
            
    	} catch(Exception e) {
    		Logger.error(this, "authorizedLogin error :", e);
            return new ResultEntity(ResultEnum.SYS_ERROR, null);
    	}
    }
    
//    public ResultEntity authorizedLogin(String jsCode, HttpServletRequest request) {
//    	try {
//    		HttpSession session = request.getSession();
//            session.removeAttribute("userInfo");
//            
//            // 根据jsCode,取得openId和sessionKey
//            String key = getOpenIdByCode(jsCode, request);
//            String openId = "";
//            if(key != null && key.length() > 0) {
//            	openId = key.split("ß")[0];
//            }
//            // 根据openId查找用户信息
//            AccountDto accountDto = accountMapper.checkUserByOpenId(openId);
//            
//            if (accountDto == null) {
//                return new ResultEntity(ResultEnum.USERNAME_PWD_NOT_MATCH);
//            }
//            if("5".equals(accountDto.getAccountType())){
//                if( accountDto.getAgentId() == null){
//                    return new ResultEntity(ResultEnum.NOT_AGENT);
//                }
//                AgentDto agent = agentMapper.selectSuperAgentByParam(accountDto.getAgentId());
//                accountDto.setAgentName(agent.getAgentName());
//                accountDto.setSuperAgentId(agent.getSuperAgent());
//                accountDto.setSuperAgentName(agent.getSuperAgentName());
//                accountDto.setPayeeAccount(agent.getPayeeAccount());
//                accountDto.setPayeeBank(agent.getPayeeBank());
//                accountDto.setPayeeName(agent.getPayeeName());
//                accountDto.setPayeePhone(agent.getPayeePhone());
//
//            }
//            
//            // 登录成功 把password
//            accountDto.setPassword("");
//
//            String sessionId = request.getSession().getId();
//            accountDto.setJSESSIONID(sessionId);
//            //把用户数据保存在session域对象中
//            session.setAttribute("userInfo", accountDto);
//            MySessionContext.addSession(session);
//            
//    		return new ResultEntity(ResultEnum.SUCCESS,accountDto);
//    	} catch(Exception e) {
//    		Logger.error(this, "checkUserAndPwd error :", e);
//            return new ResultEntity(ResultEnum.SYS_ERROR, null);
//    	}
//    }


    public ResultEntity addAccount(String accountType,String username, String password, String status,
                      Long employeeId, Long agentId,Long currentUserId, String openId, String avatarUrl){
        AccountDto accountDto = new AccountDto(accountType,username,password,status,employeeId,agentId);
        accountDto.setCreatedBy(currentUserId);
        accountDto.setUpdatedBy(currentUserId);
        accountDto.setCreatedAt(new Date());
        accountDto.setOpenId(openId);
        accountDto.setAvatarUrl(avatarUrl);

        try{
            AccountDto param = new AccountDto();
            param.setUsername(username);
            List<AccountDto> accountDtoList = accountMapper.selectAccountByParam(param);
            if( !CollectionUtils.isEmpty(accountDtoList) ){
                return new ResultEntity(ResultEnum.USERNAME_EXISTS);
            }

            accountMapper.insertAccount(accountDto);
            return new ResultEntity(ResultEnum.SUCCESS,accountDto);
        }catch (Exception e){
            Logger.error(this,"addAccount error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }


    }

    public ResultEntity updateAccount(Long accountId,String username, String password, String status,
                                   Long currentUserId){

        try{
            AccountDto param = new AccountDto();
            param.setId(accountId);
            List<AccountDto> accountDtoList = accountMapper.selectAccountByParam(param);
            if( CollectionUtils.isEmpty(accountDtoList) ){
                return new ResultEntity(ResultEnum.USER_NOT_EXISTS);
            }

            AccountDto accountDto = new AccountDto();
            accountDto.setUsername(username);
            accountDto.setPassword(password);
            accountDto.setStatus(status);
            accountDto.setUpdatedBy(currentUserId);
            accountDto.setId(accountId);
            accountMapper.updateAccount(accountDto);
            return new ResultEntity(ResultEnum.SUCCESS);
        }catch (Exception e){
            Logger.error(this,"updateAccount error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }

    public ResultEntity queryAccount(String username, String employeeName, String agentName, String pageNum1, String pageSize1){

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
            List<AccountDto> list = accountMapper.selectAccountJoinByParam(username,employeeName,agentName);
            PageInfo<AccountDto> pageList = new PageInfo<AccountDto>(list);
            Long totalPageSize = pageList.getTotal();
            Long modTotalPageNum = totalPageSize % pageSize;
            Long totalPageNum = modTotalPageNum == 0 ? totalPageSize / pageSize : totalPageSize / pageSize + 1;
            
            ResultEntity resultEntity = new ResultEntity(ResultEnum.SUCCESS,pageList.getList());
            resultEntity.setTotalPageNum(totalPageNum);
            resultEntity.setTotalPageSize(totalPageSize);
            
            return resultEntity;
        }catch (Exception e){
            Logger.error(this,"queryAccount error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }


    public ResultEntity getThirdSessionIdByCode(String jsCode, HttpServletRequest request) {
    	try {
    		String result = WxUtil.getSessionKeyOrOpenId(jsCode);
    		if(result != null) {
    			return new ResultEntity(ResultEnum.WX_SESSIONKEY_OPENID, null);
    		}
        	JSONObject json = JSONObject.fromObject(result);
        	// 失败
        	if(json.containsKey("errcode")) {
        		return new ResultEntity(ResultEnum.WX_SESSIONKEY_OPENID, null);
        	}
//			// 取得openId和sessionKey
			String openId = json.getString("openid");
			String sessionKey = json.getString("session_key");
			String content = openId+"ß"+sessionKey;
			// 加密
			byte[] encode = AesUtil.encrypt(content);
	        
	        //传输过程,不转成16进制的字符串，就等着程序崩溃掉吧
	        String code = AesUtil.parseByte2HexStr(encode);
	        System.out.println("密文字符串：" + code);
	        Map map = new HashMap();
			map.put("thirdSessionId", code);
        	return new ResultEntity(ResultEnum.SUCCESS, map);
    	} catch(Exception e) {
    		Logger.error(this,"getThirdSessionIdByCode error:",e);
    		return new ResultEntity(ResultEnum.SYS_ERROR, "");
    	}
    }

    public JSONObject getOpenIdByCode(String jsCode, HttpServletRequest request) {
    	try {
    		String result = WxUtil.getSessionKeyOrOpenId(jsCode);
    		if(result == null) {
    			return null;
    		}
        	JSONObject json = JSONObject.fromObject(result);
        	// 失败
        	if(json.containsKey("errcode")) {
        		System.out.println("json : " + json.toString());
        		return json;
        	}
//			// 取得openId和sessionKey
//			String openId = json.getString("openid");
//			String sessionKey = json.getString("session_key");
//			String key = openId + "ß" + sessionKey;
			return json;
    	} catch(Exception e) {
//    		return new ResultEntity(ResultEnum.SYS_ERROR, null);
    		return null;
    	}
    }
    
    /**
     * 授权登录注册
     * @param encryptedDataGson
     * @return
     */
    public ResultEntity authorizedLoginOrRegister(EncryptedDataGson encryptedDataGson, HttpServletRequest request) {
    	try {
    		HttpSession session = request.getSession();
    		session.removeAttribute("userInfo");
    		// 用户信息的加密数据
    		String encryptedData = encryptedDataGson.getEncryptedData();
    		// 加密算法的初始向量
        	String iv = encryptedDataGson.getIv();
        	// 使用 sha1( rawData + sessionkey ) 得到字符串
        	String signature = encryptedDataGson.getSignature();
        	// 不包括敏感信息的原始数据字符串，用于计算签名
        	String rawData = encryptedDataGson.getRawData();
        	// 用JS_CODE换取OPEN_ID和SESSIONKEY
        	String jsCode = encryptedDataGson.getJsCode();
        	System.out.println("jsCode : " + jsCode);
        	// 上级代理
        	Long superAgent = encryptedDataGson.getSuperAgent();
        	Long sa = new Long(-1001);
			if(superAgent.longValue() == sa.longValue()) {
				superAgent = null;
        	}
        	// 手机
        	String telPhone = encryptedDataGson.getTelPhone();
        	// 上级代理名
        	String superAgentName = encryptedDataGson.getSuperAgentName();
        	// 姓名
        	String name = encryptedDataGson.getName();
        	
        	// 通过JS_CODE,取得sessionKey和openId
        	JSONObject jsonObject = getOpenIdByCode(jsCode, request);
        	if(jsonObject == null) {
        		return new ResultEntity(ResultEnum.WX_JSCODE_ERROR);
	        } else if(jsonObject.containsKey("errcode")) {
	        	return new ResultEntity(ResultEnum.WX_JSCODE_ERROR);
	        }
        	// 取得openId和sessionKey
        	String openId = jsonObject.getString("openid");
        	String sessionKey = jsonObject.getString("session_key");
        	String keyCode = openId + "ß" + sessionKey;
        	
        	// 解析用户信息
    		String sha1 = rawData + sessionKey;
    		String signature1 = AesUtil.getSha1(sha1);
    		if(!signature.equals(signature1)) {
    			// 签名不匹配
        		return new ResultEntity(ResultEnum.WX_SIGNATURE_ERROR);
    		}
    		
    		// 取到用户信息
        	String userInfo = AesUtil.decryptData(encryptedData, sessionKey, iv);
        	JSONObject json = JSONObject.fromObject(userInfo);
        	String id = json.getString("openId");
        	String avatarUrl = json.getString("avatarUrl");
        	String nickName = json.getString("nickName");
        	// appId
        	String appid = json.getJSONObject("watermark").getString("appid");
        	// 通过appid获取accessToken
        	
        	if(!StringUtils.equals(id, openId)) {
        		return new ResultEntity(ResultEnum.WX_OPENID_ERROR);
        	}
        	
        	// 生成thirdSessionid
	        // 加密
			byte[] encode = AesUtil.encrypt(keyCode);
           //传输过程,不转成16进制的字符串，就等着程序崩溃掉吧
	        String thirdSessionId = AesUtil.parseByte2HexStr(encode);
    		
        	
        	// 1.先判断open_id是否已经注册
        	int countOpenId = accountMapper.selectAccountCountByParma(openId, null);
        	if(countOpenId == 1) {
        		// 已经注册过了
        		// 判断输入的手机和OPEN_ID是否匹配
        		int countOpenIdTelPhone = accountMapper.selectAccountCountByParma(openId, telPhone);
        		if(countOpenIdTelPhone == 0) {
        			String telePhone = accountMapper.selectTelPhoneByOpenId(openId);
        			telePhone = telePhone.substring(0, 3) + "****" + telePhone.substring(7, telePhone.length());
        			return new ResultEntity(ResultEnum.OPENID_TLE_NOT_FOUND, telePhone);
        		}
        		
        		// 根据OPENID和手机号找到相应的账号
            	AccountDto accountDto = accountMapper.checkUserByOpenIdTelPhone(openId, telPhone);
            	// 账户状态不对
            	if(accountDto == null) {
            		return new ResultEntity(ResultEnum.STATUS_INVALID);
            	}
            	
            	// 判断用户类型是否是代理
            	if(!"5".equals(accountDto.getAccountType()) || accountDto.getAgentId() == null){
            		return new ResultEntity(ResultEnum.NOT_AGENT);
            	}
            	
            	String username = nickName+"("+telPhone+name+")";
            	
            	// 判断找出来的昵称时候和用户的信息昵称一样，如果不一样，更新account表中的昵称和用户名，agent表中的agentNme
            	System.out.println("account.nickName : " + accountDto.getNickName());
            	System.out.println("nick name : " + nickName);
            	if(!nickName.equals(accountDto.getNickName())) {
            		// 更新ACCOUNT表
            		accountMapper.updateAccountByParam(nickName, username, accountDto.getId());
            		AgentDto agentTypeDto = agentMapper.selectSuperAgentByParam(accountDto.getAgentId());
            		String agentTypeUsername = username + "-" + agentTypeDto.getAgentTypeName();
            		// 更新AGENT表
            		agentMapper.updateAgentByParam(agentTypeUsername, accountDto.getAgentId());
            	}
            	
            	// 更新头像
		        if(avatarUrl != null && avatarUrl.length() > 0) {
		        	accountMapper.updateAvatarUtl(avatarUrl, openId);
		        }
            	
	            AgentDto agent = agentMapper.selectSuperAgentByParam(accountDto.getAgentId());
	            accountDto.setAgentName(agent.getAgentName());
	            accountDto.setSuperAgentId(agent.getSuperAgent());
	            accountDto.setSuperAgentName(agent.getSuperAgentName());
	            accountDto.setPayeeAccount(agent.getPayeeAccount());
	            accountDto.setPayeeBank(agent.getPayeeBank());
	            accountDto.setPayeeName(agent.getPayeeName());
	            accountDto.setPayeePhone(agent.getPayeePhone());
	            accountDto.setAgentType(agent.getAgentType());
	            accountDto.setAgentTypeName(agent.getAgentTypeName());
	            accountDto.setVipRemark(agent.getVipRemark());
		        // 登录成功 把password
		        accountDto.setPassword("");
//		        accountDto.setOpenId("");
		        accountDto.setThirdSessionId(thirdSessionId);
		        accountDto.setAvatarUrl(avatarUrl);
		        accountDto.setNickName(nickName);
		        accountDto.setTelPhone(telPhone);
		        accountDto.setUsername(username);
		        accountDto.setName(name);
		        
		        String sessionId = request.getSession().getId();
		        accountDto.setJSESSIONID(sessionId);
		        //把用户数据保存在session域对象中
		        session.setAttribute("userInfo", accountDto);
		        MySessionContext.addSession(session);
		        
		        return new ResultEntity(ResultEnum.SUCCESS,accountDto);
        		
        		
        	} else {
        		// 判断name是不是为空
        		if(name == null || "".equals(name)) {
        			return new ResultEntity(ResultEnum.NO_NAME);
        		}
        		
        		// 进入注册流程
        		if(telPhone == null || "".equals(telPhone)) {
        			return new ResultEntity(ResultEnum.NO_TELPHONE);
        		}
        		
        		Long currentUserId = UserInfoEntity.getUserId(request);
        		String agentTypeName = "旅游达人";
        		String username = nickName+"("+telPhone+name+")-" + agentTypeName;
        		
        		AgentDto agentDto = new AgentDto();
        		agentDto.setAgentName(username);
        		agentDto.setSuperAgent(superAgent);
                agentDto.setCreatedBy(currentUserId);
                agentDto.setCreatedAt(new Date());
                agentDto.setUpdatedBy(currentUserId);
                agentDto.setUpdatedAt(new Date());
                agentDto.setStatus("2");
                agentDto.setPayeePhone(telPhone);
                agentDto.setAgentType("1");

                try{
                    
                    try {
                    	agentMapper.insertAgent(agentDto);
                    } catch(Exception ex) {
                    	System.out.println(ex.getMessage());
                    	return new ResultEntity(ResultEnum.DUPLICATE_DATA);
                    }
                    
                    AccountDto accountDto = new AccountDto("5",username,"123","2",null, agentDto.getId());
                    accountDto.setCreatedBy(currentUserId);
                    accountDto.setUpdatedBy(currentUserId);
                    accountDto.setCreatedAt(new Date());
                    accountDto.setOpenId(openId);
                    accountDto.setAvatarUrl(avatarUrl);
                    accountDto.setNickName(nickName);
                    accountDto.setTelPhone(telPhone);
                    accountDto.setName(name);

                    try{
                        AccountDto param = new AccountDto();
                        param.setUsername(username);
                        List<AccountDto> accountDtoList = accountMapper.selectAccountByParam(param);
                        if( !CollectionUtils.isEmpty(accountDtoList) ){
                            return new ResultEntity(ResultEnum.USERNAME_EXISTS);
                        }

                        accountMapper.insertAccount(accountDto);
                    }catch (Exception e){
                        Logger.error(this,"addAccount error:",e);
                        return new ResultEntity(ResultEnum.SYS_ERROR);
                    }
                    
                    // 放入session
                    accountDto.setAgentName(agentDto.getAgentName());
                    accountDto.setSuperAgentId(agentDto.getSuperAgent());
                    accountDto.setSuperAgentName(superAgentName);
                    accountDto.setThirdSessionId(thirdSessionId);
                    accountDto.setAgentType("1");
                    accountDto.setAgentTypeName("旅游达人");
                    
                    // 登录成功 把password
                    accountDto.setPassword("");
//                    accountDto.setOpenId("");

                    String sessionId = request.getSession().getId();
                    accountDto.setJSESSIONID(sessionId);
                    //把用户数据保存在session域对象中
                    session.setAttribute("userInfo", accountDto);
                    MySessionContext.addSession(session);
                    
                    return new ResultEntity(ResultEnum.SUCCESS,accountDto);
                }catch (Exception e){
                    Logger.error(this,"agentRegister error:",e);
                    System.out.println("error : " + e.getMessage());
                    return new ResultEntity(ResultEnum.SYS_ERROR);
                }
        		
        	}
        	
    	} catch(Exception e) {
    		Logger.error(this, "authorizedLogin error :", e);
            return new ResultEntity(ResultEnum.SYS_ERROR, null);
    	}
    }

    public ResultEntity getAccountInfo(AccountGson accountGson, HttpServletRequest request) {
    	try {
    		HttpSession session = request.getSession();
    		AccountDto sessionDto = (AccountDto)session.getAttribute("userInfo");
    		session.removeAttribute("userInfo");
    		String thirdSessionId = sessionDto.getThirdSessionId();
    		
    		// 根据OPENID和手机号找到相应的账号
        	AccountDto accountDto = accountMapper.getAccountInfoByAgentId(accountGson.getAgentId());
        	
        	
        	// 账户状态不对
        	if(accountDto == null) {
        		return new ResultEntity(ResultEnum.STATUS_INVALID);
        	}
        	
        	// 判断用户类型是否是代理
        	if(!"5".equals(accountDto.getAccountType()) || accountDto.getAgentId() == null){
        		return new ResultEntity(ResultEnum.NOT_AGENT);
        	}
        	
        	
            AgentDto agent = agentMapper.selectSuperAgentByParam(accountDto.getAgentId());
            accountDto.setAgentName(agent.getAgentName());
            accountDto.setSuperAgentId(agent.getSuperAgent());
            accountDto.setSuperAgentName(agent.getSuperAgentName());
            accountDto.setPayeeAccount(agent.getPayeeAccount());
            accountDto.setPayeeBank(agent.getPayeeBank());
            accountDto.setPayeeName(agent.getPayeeName());
            accountDto.setPayeePhone(agent.getPayeePhone());
            accountDto.setAgentType(agent.getAgentType());
            accountDto.setAgentTypeName(agent.getAgentTypeName());
            accountDto.setVipRemark(agent.getVipRemark());
	        // 登录成功 把password
	        accountDto.setPassword("");
	        accountDto.setThirdSessionId(thirdSessionId);
	        
	        String sessionId = request.getSession().getId();
	        accountDto.setJSESSIONID(sessionId);
	        //把用户数据保存在session域对象中
	        session.setAttribute("userInfo", accountDto);
	        MySessionContext.addSession(session);
	        
	        return new ResultEntity(ResultEnum.SUCCESS,accountDto);
    	} catch(Exception ex) {
    		System.out.println("getAccountInfo error : " + ex.getMessage());
    		Logger.error(this, "getAccountInfo error :", ex);
            return new ResultEntity(ResultEnum.SYS_ERROR, null);
    	}
    }

}
