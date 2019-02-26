package com.trip.controller;


import com.trip.util.IpUtils;
import com.trip.util.StringUtils;
import com.trip.util.PayUtil;
import com.trip.config.WxPayConfig;
import com.trip.entity.ResultEntity;
import com.trip.service.PayService;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Controller
public class PayController{

	@Autowired
	private PayService payService;
	 
	@RequestMapping(value="/trace/list",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryAgent(@RequestParam(name="agentName",required = false)String agentName,
							 @RequestParam(name="orderDate",required=false)String orderDate,
							 @RequestParam(name="payStatus",required=false)String payStatus,
							 @RequestParam(value="pageNum", required=false) String pageNum,	// 页数
							 @RequestParam(value="pageSize", required=false) String pageSize	// 条数
							 ){
	
	    ResultEntity resultEntity = payService.queryOutTrace(agentName,orderDate,payStatus, pageNum, pageSize);
	
	    return resultEntity.toString();
	}
	 
	 
    /**
	 * @Description: 发起微信支付
	 * @param request
	 */
    @RequestMapping(value="/wxpay",method = RequestMethod.POST)
    @ResponseBody
	public Map<String, Object> wxPay(String openid, HttpServletRequest request){
		try{
			//生成的随机字符串
			String nonce_str = StringUtils.getRandomStringByLength(32);
			//商品名称
			String body = "测试商品名称";
			//获取客户端的ip地址
			String spbill_create_ip = IpUtils.getIpAddr(request);
			String order_no = this.doOrderNum();
			
			//组装参数，用户生成统一下单接口的签名
			Map<String, String> packageParams = new HashMap<String, String>();
			packageParams.put("appid", WxPayConfig.appid);
			packageParams.put("mch_id", WxPayConfig.mch_id);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("body", body);
			packageParams.put("out_trade_no", order_no);//商户订单号
			packageParams.put("total_fee", "0.01");//支付金额，这边需要转成字符串类型，否则后面的签名会失败
			packageParams.put("spbill_create_ip", spbill_create_ip);
			packageParams.put("notify_url", WxPayConfig.notify_url);//支付成功后的回调地址
			packageParams.put("trade_type", WxPayConfig.TRADETYPE);//支付方式
			packageParams.put("openid", openid);
			   
	        String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串 
	        
        	//MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
        	String mysign = PayUtil.sign(prestr, WxPayConfig.key, "utf-8").toUpperCase();
	        
	        //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
	        String xml = "<xml>" + "<appid>" + WxPayConfig.appid + "</appid>" 
                    + "<body>" + body + "</body>" 
                    + "<mch_id>" + WxPayConfig.mch_id + "</mch_id>" 
                    + "<nonce_str>" + nonce_str + "</nonce_str>" 
                    + "<notify_url>" + WxPayConfig.notify_url + "</notify_url>" 
                    + "<openid>" + openid + "</openid>" 
                    + "<out_trade_no>" + order_no + "</out_trade_no>" 
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>" 
                    + "<total_fee>" + "1" + "</total_fee>"
                    + "<trade_type>" + WxPayConfig.TRADETYPE + "</trade_type>" 
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";
	        
	        System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);
 
	        //调用统一下单接口，并接受返回的结果
	        String result = PayUtil.httpRequest(WxPayConfig.pay_url, "POST", xml);
	        
	        System.out.println("调试模式_统一下单接口 返回XML数据：" + result);
	        
	        // 将解析结果存储在HashMap中   
	        Map map = PayUtil.doXMLParse(result);
	        
	        String return_code = (String) map.get("return_code");//返回状态码
	        
		    Map<String, Object> response = new HashMap<String, Object>();//返回给小程序端需要的参数
	        if(return_code.equals("SUCCESS")){   
	            String prepay_id = (String) map.get("prepay_id");//返回的预付单信息   
	            response.put("nonceStr", nonce_str);
	            response.put("package", "prepay_id=" + prepay_id);
	            Long timeStamp = System.currentTimeMillis() / 1000;   
	            response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
	            //拼接签名需要的参数
	            String stringSignTemp = "appId=" + WxPayConfig.appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id+ "&signType=MD5&timeStamp=" + timeStamp;   
	            //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
	            String paySign = PayUtil.sign(stringSignTemp, WxPayConfig.key, "utf-8").toUpperCase();
	            
	            response.put("paySign", paySign);
	        }
			
	        // 插入流水
	        payService.addOutTrace(order_no, openid, "00");
	        
	        response.put("appid", WxPayConfig.appid);
			
	        return response;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
    /**
     * @Description:微信支付
     * @return
     */
    @RequestMapping(value="/wxNotify",method = RequestMethod.POST)
    @ResponseBody
    public void wxNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        System.out.println("接收到的报文：" + notityXml);

        Map map = PayUtil.doXMLParse(notityXml);

        String returnCode = (String) map.get("return_code");
        if("SUCCESS".equals(returnCode)){
            //验证签名是否正确
            if(PayUtil.verify(PayUtil.createLinkString(map), (String)map.get("sign"), WxPayConfig.key, "utf-8")){
                /**此处添加自己的业务逻辑代码start**/
            	payService.updateOutTrace("10", (String)map.get("out_trade_no"));

                /**此处添加自己的业务逻辑代码end**/

                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        }else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        System.out.println(resXml);
        System.out.println("微信支付回调数据结束");

        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }
    
    public String doOrderNum() {
        Random random = new Random();
        SimpleDateFormat allTime = new SimpleDateFormat("YYYYMMddHHmmSSS");
        String subjectno = allTime.format(new Date())+random.nextInt(10);       
        return subjectno+random.nextInt(10);
    }

    
    public static void main(String[] args) {
    	
    	
    	try {
			Map map = PayUtil.doXMLParse("<xml><appid><![CDATA[wx016958c715b6d91a]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1523998791]]></mch_id><nonce_str><![CDATA[4775l1up1dsl63fwjt6tr434cu1pwugd]]></nonce_str><openid><![CDATA[oOjH64vaN6tHhcajxH2Ludy_mii4]]></openid><out_trade_no><![CDATA[20190117161944595]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[2D1CF7E96AAEC492AB263FA2D56A082F]]></sign><time_end><![CDATA[20190117162004]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4200000279201901175268933485]]></transaction_id></xml>");
			String str = "appid=wx016958c715b6d91a&bank_type=CFT&cash_fee=1&fee_type=CNY&is_subscribe=N&mch_id=1523998791&nonce_str=4775l1up1dsl63fwjt6tr434cu1pwugd&openid=oOjH64vaN6tHhcajxH2Ludy_mii4&out_trade_no=20190117161944595&result_code=SUCCESS&return_code=SUCCESS&time_end=20190117162004&total_fee=1&trade_type=JSAPI&transaction_id=4200000279201901175268933485&key=f72d9ca31e4137a70ab0eb85864289ac";
			System.out.println(map.toString());
			String returnCode = (String) map.get("return_code");
	        if("SUCCESS".equals(returnCode)){
	        	System.out.println((String)map.get("out_trade_no"));
	        	System.out.println(str);
	        	System.out.println(PayUtil.createLinkString(map));
	        	System.out.println(DigestUtils.md5Hex(getContentBytes(str, "utf-8")));
	        	System.out.println(PayUtil.sign(PayUtil.createLinkString(map), "f72d9ca31e4137a70ab0eb85864289ac", "utf-8").toUpperCase());
        		if(PayUtil.verify(PayUtil.createLinkString(map), (String)map.get("sign"), "f72d9ca31e4137a70ab0eb85864289ac", "utf-8")){
        			System.out.println("SUCCESS");
        		}
	        }
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**  
     * @param content  
     * @param charset  
     * @return  
     * @throws SignatureException  
     * @throws UnsupportedEncodingException  
     */   
    public static byte[] getContentBytes(String content, String charset) {   
        if (charset == null || "".equals(charset)) {   
            return content.getBytes();   
        }   
        try {   
            return content.getBytes(charset);   
        } catch (UnsupportedEncodingException e) {   
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);   
        }   
    } 
}
