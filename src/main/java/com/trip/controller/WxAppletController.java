package com.trip.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

	/**
	 * 微信
	 * @author YukiRinger
	 *
	 */
	@Controller
	public class WxAppletController {
		
	@RequestMapping(value="/wx/getSessionKey",method = RequestMethod.GET)
    @ResponseBody
	public String getSessionKey(@RequestParam("jsCode") String jsCode,
								HttpServletRequest request,
								HttpServletResponse response) throws Exception {
		
		return null;
	}
	
	@RequestMapping(value="/wx/checkSessionKey",method = RequestMethod.GET)
	@ResponseBody
	public String checkSessionKey(@RequestParam("sessionKey") String sessionKey,
								  HttpServletRequest request,
								  HttpServletResponse response) throws Exception {
		return null;
	}
	
}
