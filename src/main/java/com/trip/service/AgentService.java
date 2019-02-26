package com.trip.service;


import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trip.MySessionContext;
import com.trip.dto.AccountDto;
import com.trip.dto.AgentDto;
import com.trip.dto.SupplierDto;
import com.trip.entity.AesUtil;
import com.trip.entity.AgentRegisterGson;
import com.trip.entity.ImageUtil;
import com.trip.entity.Logger;
import com.trip.entity.QrCodeUtil;
import com.trip.entity.ResultEntity;
import com.trip.entity.ResultEnum;
import com.trip.mapper.AgentMapper;

import net.sf.json.JSONObject;


@Service
public class AgentService {

    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private AccountService accountService;

    public ResultEntity addAgent(Long id,String agentName,Long superAgent,Long currentUserId,
                                 String payeeAccount,String payeeBank,String payeeName,String payeePhone,String status,
                                 String agentType,String vipRemark){
        AgentDto agentDto = new AgentDto();
        String agentTypeName = "";
        if(agentType == null || "".equals(agentType) || "-1001".equals(agentType)) {
        	agentTypeName = "旅游达人";
        } else if("1".equals(agentType)) {
        	agentTypeName = "旅游达人";
        } else if("2".equals(agentType)) {
        	agentTypeName = "vip旅游达人";
        } else if("3".equals(agentType)) {
        	agentTypeName = "创享合伙人";
        }
//        agentDto.setAgentName(agentName);
        agentDto.setSuperAgent(superAgent);
        agentDto.setCreatedBy(currentUserId);
        agentDto.setCreatedAt(new Date());
        agentDto.setUpdatedBy(currentUserId);
        agentDto.setUpdatedAt(new Date());
        agentDto.setPayeeAccount(payeeAccount);
        agentDto.setPayeeBank(payeeBank);
        agentDto.setPayeeName(payeeName);
        agentDto.setPayeePhone(payeePhone);
        agentDto.setStatus(status);
        // add by zhuyq version2.1 start
        agentDto.setAgentType(agentType);
        agentDto.setVipRemark(vipRemark);
        // add by zhuyq version2.1 end

        try{
            if( id != null && !id.equals(0L) ){
                agentDto.setId(id);
                int ch1 = agentName.indexOf("-vip旅游达人");
        		int ch2 = agentName.indexOf("-旅游达人");
        		int ch3 = agentName.indexOf("-创享合伙人");
        		if(ch1 > 0) {
        			agentName = agentName.substring(0, ch1) + "-" + agentTypeName;
        		} else if(ch2 > 0) {
        			agentName = agentName.substring(0, ch2) + "-" + agentTypeName;
        		} else if(ch3 > 0) {
        			agentName = agentName.substring(0, ch3) + "-" + agentTypeName;
        		} else {
        			agentName = agentName + "-" + agentTypeName;
        		}
        		agentDto.setAgentName(agentName);
                agentMapper.updateAgent(agentDto);
            }else{
            	agentDto.setAgentName(agentName + "-" + agentTypeName);
                agentMapper.insertAgent(agentDto);
            }
            return new ResultEntity(ResultEnum.SUCCESS,agentDto);
        }catch (DuplicateKeyException dup){
            return new ResultEntity(ResultEnum.DUPLICATE_DATA);
        }
        catch (Exception e){
            Logger.error(this,"addAgent error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }


    public ResultEntity queryAgent(String agentName,String status, String pageNum1, String pageSize1){

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
            List<AgentDto> list = agentMapper.selectAgentByParam(agentName,null,null,status);
            PageInfo<AgentDto> pageList = new PageInfo<AgentDto>(list);
            Long totalPageSize = pageList.getTotal();
            Long modTotalPageNum = totalPageSize % pageSize;
            Long totalPageNum = modTotalPageNum == 0 ? totalPageSize / pageSize : totalPageSize / pageSize + 1;
            
            for(AgentDto agentDto : pageList.getList()){
            	agentDto.setAgentName(agentDto.getAgentName());
                if( agentDto.getSuperAgent() != null ){
                    List<AgentDto> agentDtos = agentMapper.selectAgentByParam(null,agentDto.getSuperAgent(),null,null);
                    if( !CollectionUtils.isEmpty(agentDtos)){
                        agentDto.setSuperAgentName(agentDtos.get(0).getAgentName());
                    }
                }
                String subAgent = agentMapper.selectSubAgentNameByParam(agentDto.getId());
                agentDto.setSubAgent(subAgent);
            }
            
            ResultEntity resultEntity = new ResultEntity(ResultEnum.SUCCESS,pageList.getList());
            resultEntity.setTotalPageNum(totalPageNum);
            resultEntity.setTotalPageSize(totalPageSize);
            
            return resultEntity;
        }catch (Exception e){
            Logger.error(this,"queryAgent error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }

    public ResultEntity querySubAgent(Long agentId){

        try{
            List<AgentDto> superAgentList = agentMapper.selectAgentByParam(null,null,agentId,null);

            return new ResultEntity(ResultEnum.SUCCESS,superAgentList);
        }catch (Exception e){
            Logger.error(this,"queryAgent error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }

    }

    public ResultEntity agentRegister(AgentRegisterGson dto, Long currentUserId,HttpServletRequest request){

        AgentDto agentDto = new AgentDto();
        BeanUtils.copyProperties(dto,agentDto);
        agentDto.setCreatedBy(currentUserId);
        agentDto.setCreatedAt(new Date());
        agentDto.setUpdatedBy(currentUserId);
        agentDto.setUpdatedAt(new Date());
        agentDto.setStatus("2");
        String jsCode = dto.getJsCode();
        System.out.println("jsCode : " + jsCode);

        try{
        	HttpSession session = request.getSession();
            session.removeAttribute("userInfo");
            
            System.out.println("jsCode : " + jsCode);
            // 通过JS_CODE,取得sessionKey和openId
            JSONObject json = accountService.getOpenIdByCode(jsCode, request);
            if(json == null) {
            	return new ResultEntity(ResultEnum.WX_JSCODE_ERROR);
            } else if(json.containsKey("errcode")) {
            	return new ResultEntity(ResultEnum.WX_JSCODE_ERROR);
            }
			// 取得openId和sessionKey
			String openId = json.getString("openid");
			String sessionKey = json.getString("session_key");
			String keyCode = openId + "ß" + sessionKey;
            System.out.println("openId : " + openId);
            System.out.println("sessionKey : " + sessionKey);
            
            // 生成thirdSessionId
            // 加密
 			byte[] encode = AesUtil.encrypt(keyCode);
 			System.out.println("encode : " + encode);
 	        
 	        //传输过程,不转成16进制的字符串，就等着程序崩溃掉吧
            String thirdSessionId = AesUtil.parseByte2HexStr(encode);
            System.out.println("thirdSessionId : " + thirdSessionId);
            
            try {
            	agentMapper.insertAgent(agentDto);
            } catch(Exception ex) {
            	return new ResultEntity(ResultEnum.DUPLICATE_DATA);
            }
            
            
            
//            
//            String classpath = this.getClass().getResource("/").getPath();
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
            
            
            
            // 生成默认头像
//            String agentName = agentDto.getAgentName().substring(0, 1);
//            BufferedImage image = ImageUtil.createDefaultHeadImage(agentName, agentDto.getId());
//			String classpath = this.getClass().getResource("/").getPath();
//        	classpath = classpath.replaceAll("WEB-INF/classes/", "agent/");
//        	File file = new File(classpath+agentDto.getId()+".jpg");
//        	String avatarUrl = "https://api.atrip.club/atrip/agent/"+agentDto.getId()+".jpg";
//            System.out.println("avatarUrl : " + avatarUrl);
//            ImageIO.write(image, "jpg", file);
            
            ResultEntity result = accountService.addAccount("5",dto.getUsername(),dto.getPassword(),"2",null,
                    agentDto.getId(),currentUserId, openId, null);
            if(!result.getResultEnum().getResultCode().equals(ResultEnum.SUCCESS.getResultCode())) {
            	return result;
            }
            AccountDto accountDto = (AccountDto)result.getData();
            // 放入session
            accountDto.setAgentName(agentDto.getAgentName());
            accountDto.setSuperAgentId(agentDto.getSuperAgent());
            accountDto.setSuperAgentName(agentDto.getSuperAgentName());
            accountDto.setPayeeAccount(agentDto.getPayeeAccount());
            accountDto.setPayeeBank(agentDto.getPayeeBank());
            accountDto.setPayeeName(agentDto.getPayeeName());
            accountDto.setPayeePhone(agentDto.getPayeePhone());
//            accountDto.setThirdSessionId(thirdSessionId);
            
            // 登录成功 把password
            accountDto.setPassword("");

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

    /**
     * 
     * @param id	代理ID
     * @param payeeBank	收款银行名-收款银行名称/支付宝/微信
     * @param payeeName	收款人姓名
     * @param payeePhone	收款银行卡号/支付宝账号/微信账号
     * @return
     */
    public ResultEntity updateAgentPayee(Long id, String payeeBank, String payeeAccount, String payeeName) {
    	AgentDto agentDto = new AgentDto();
        agentDto.setPayeeBank(payeeBank);
        agentDto.setPayeeAccount(payeeAccount);
        agentDto.setPayeeName(payeeName);

        try{
            if( id != null && !id.equals(0L) ){
                agentDto.setId(id);
                agentMapper.updateAgent(agentDto);
            }else{
            	return new ResultEntity(ResultEnum.LOSE_PARAM);
            }
            return new ResultEntity(ResultEnum.SUCCESS,agentDto);
        }catch (DuplicateKeyException dup){
            return new ResultEntity(ResultEnum.DUPLICATE_DATA);
        }
        catch (Exception e){
        	System.out.println("updateAgentPayee error:" + e.getMessage());
            Logger.error(this,"updateAgentPayee error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }

    /**
     * 根据参数找代理
     * @param agentId	代理ID
     * @return
     */
    public ResultEntity queryAgentById(Long agentId) {
    	try{
    		AgentDto agentDto;
            List<AgentDto> list = agentMapper.selectAgentByParam(null,agentId,null,null);
//            for(AgentDto agentDto : list){
//                if( agentDto.getSuperAgent() != null ){
//                    List<AgentDto> agentDtos = agentMapper.selectAgentByParam(null,agentDto.getSuperAgent(),null,null);
//                    if( !CollectionUtils.isEmpty(agentDtos)){
//                        agentDto.setSuperAgentName(agentDtos.get(0).getAgentName());
//                    }
//                }
//                String subAgent = agentMapper.selectSubAgentNameByParam(agentDto.getId());
//                agentDto.setSubAgent(subAgent);
//            }
            if( !CollectionUtils.isEmpty(list)){
            	 // 代理信息
            	agentDto = list.get(0);
            } else {
            	return new ResultEntity(ResultEnum.ORDER_NO_BOOK_DETAIL);
            }
            return new ResultEntity(ResultEnum.SUCCESS,agentDto);
        }catch (Exception e){
            Logger.error(this,"queryAgent error:",e);
            return new ResultEntity(ResultEnum.SYS_ERROR);
        }
    }


    public ResultEntity inviterQRCode(AccountDto user) {
    	try {
    		Long agentId = user.getAgentId();
    		String userName = user.getAgentName();
    		int l = userName.lastIndexOf("(");
        	String agentName = userName.substring(0, l);
        	String agentNamePart = userName.substring(l);
        	String agentAvatar = user.getAvatarUrl();
//        	String nickName = agentNa
        	ResourceBundle resource = ResourceBundle.getBundle("wx");
        	// WX_URL
        	String requestUrl = resource.getString("miniProgramRegistUrl");
        	requestUrl = requestUrl + "?id=" + agentId + "&agentName=" + agentName + "&agentNamePart=" + agentNamePart + "&agentAvatar=" + agentAvatar;
        	// 原图地址
        	String srcImagePath = this.getClass().getResource("/Atrip@3x.png").getPath();
        	// 判断图片文件是否存在
//        	String filePath = OUTPUT_SRC + agentId + ".jpg";
////        	String filePath = OUTPUT_SRC + "2.jpg";
        	String classpath1 = this.getClass().getResource("/").getPath();
        	String impageClasspath = classpath1.replaceAll("WEB-INF/classes/", "image/");
        	String qrClasspath = classpath1.replaceAll("WEB-INF/classes/", "qr/");
        	File imageFile = new File(impageClasspath+agentId+".jpg");
        	File qrFile = new File(qrClasspath+agentId+".jpg");
        	String imageSrcFile = "image/"+agentId+".jpg";
        	String qrSrcFile = "qr/"+agentId+".jpg";
//        	String outSrcFile = "image/"+"2.jpg";
        	ResultEntity result;
        	Map<String, String> map = new HashMap<String, String>();
        	//存在
        	if(imageFile.exists() && qrFile.exists()) {
        		
        		map.put("imageUrl", imageSrcFile);
//        		map.put("qrUrl", qrSrcFile);
//        		result = new ResultEntity(ResultEnum.SUCCESS, map);
        	// 不存在
        	} else {
//            	requestUrl = requestUrl + "?id=1&agentName=2";
            	System.out.println(requestUrl);
            	
            	// 根据代理ID，找到对应的头像地址和昵称
            	Map<String, String> resultMap = agentMapper.selectNickImageUrlByAgentId(agentId);
            	String imageUrl = resultMap.get("avatarUrl");
            	String nickName = resultMap.get("nickName");
            	// 二维码要生成的URL， 目标地址， 头像URL， 昵称
            	BufferedImage image = ImageUtil.creatQrImage(requestUrl, srcImagePath, imageUrl, nickName);
//            	ImageIO.write(image, "JPG", response.getOutputStream());
                // 将image对象输出到磁盘文件中
            	ImageIO.write(image, "jpg", imageFile);
            	
            	// 生成二维码
//            	BufferedImage qrImage = QrCodeUtil.createQrCode(requestUrl);
//            	ImageIO.write(qrImage, "jpg", qrFile);
            	
//            	Map<String, String> map = new HashMap<String, String>();
        		map.put("imageUrl", imageSrcFile);
//        		map.put("qrUrl", qrSrcFile);
//            	result = new ResultEntity(ResultEnum.SUCCESS, map);
        	}
        	
        	if(qrFile.exists()) {
        		map.put("qrUrl", qrSrcFile);
        	} else {
        		// 生成二维码
            	BufferedImage qrImage = QrCodeUtil.createQrCode(requestUrl);
            	ImageIO.write(qrImage, "jpg", qrFile);
            	map.put("qrUrl", qrSrcFile);
        	}
        	
        	result = new ResultEntity(ResultEnum.SUCCESS, map);
        	return result;
    	} catch(Exception e) {
    		return new ResultEntity(ResultEnum.SYS_ERROR);
    	}
    }



}
