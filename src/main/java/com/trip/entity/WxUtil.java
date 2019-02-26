package com.trip.entity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;



public class WxUtil {
	public static String getSessionKeyOrOpenId(String jsCode) {
		try {
//			HttpSession session = request.getSession();
//			session.removeAttribute("sessionKey");
//			session.removeAttribute("openId");
			// 小程序JS_CODE
			String wxJsCode = jsCode;
			
			ResourceBundle resource = ResourceBundle.getBundle("wx");
			// WX_URL
			String requestUrl = resource.getString("url");
			Map<String, String> param = new HashMap<String, String>();
			param.put("appid", resource.getString("appId"));
			param.put("secret", resource.getString("appSceret"));
			param.put("js_code", jsCode);
			param.put("grant_type", "authorization_code");
			String result = UrlUtil.sendPost(requestUrl, param);
//			JSONObject json = JSONObject.fromObject(result);
//			if(json.containsKey("errcode")) {
//				// 失败
//				System.out.println("失败");
//				return new ResultEntity(ResultEnum.WX_SESSIONKEY_OPENID, null);
//			}
//			// 取得openId和sessionKey
//			String openId = json.getString("openid");
//			String sessionKey = json.getString("session_key");
//			// 根据OPENID，查找
//			// 自己生成SESSION_KEY
//			String sessionId = request.getSession().getId();
//			//把用户数据保存在session域对象中
//            session.setAttribute("sessionKey", sessionKey);
//            session.setAttribute("openId", openId);
//            MySessionContext.addSession(session);
			// 把微信服务端返回的session+openid放入session里面
			
//			Map map = new HashMap();
//			map.put("sessionId", sessionId);
			return result;
		} catch(Exception e) {
//            return new ResultEntity(ResultEnum.SYS_ERROR, null);
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String getAccessToken() throws Exception {
		ResourceBundle resource = ResourceBundle.getBundle("wx");
		// 获取tokenURL地址
		String accessTokenUrl = resource.getString("accessTokenUrl");
		// 建立连接
		URL url = new URL(accessTokenUrl);
		HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
		// 使用自定义信任管理器
		TrustManager[] tm = {new MyX509TrustManager()};
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new SecureRandom());
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		conn.setSSLSocketFactory(ssf);
		conn.setDoInput(true);
		// 设置请求方式
		conn.setRequestMethod("GET");
		// 取得输入流
		InputStream inputStream = conn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		// 读取响应内容
		StringBuffer sb = new StringBuffer();
		String str = null;
		while((str = bufferedReader.readLine()) != null) {
			sb.append(str);
		}
		JSONObject json = JSONObject.fromObject(sb.toString());
		System.out.println(json.toString());
		String accessToken = json.getString("access_token");
		return accessToken;
	}
    
    public static void main(String[] args) throws Exception {
    	String accessToken = getAccessToken();
    	System.out.println(accessToken);
    	// 解密用户信息
//    	String appId = "wx4f4bc4dec97d474b";
// 	    String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
// 	    String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
// 	    String iv = "r7BXXKkLb8qrSNn05n0qiA==";
// 	    String result = AesUtil.decrypt(encryptedData, sessionKey, iv);
// 	    System.out.println(result);
 	    
// 	   System.out.println(getSessionKeyOrOpenId("001cpCW82fqrHQ0P6CY82UcGW82cpCWA"));
    }
    
}
