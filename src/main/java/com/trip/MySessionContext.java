package com.trip;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author Vincent.Pei
 * @date 2018/7/15下午3:13
 * @Description:
 */
public class MySessionContext {

    private static HashMap sessionMap = new HashMap();
    public static synchronized void addSession(HttpSession session) {
        if (session != null) {
            sessionMap.put(session.getId(), session);
        }
    }
    public static synchronized void delSession(HttpSession session) {
        if (session != null) {
            sessionMap.remove(session.getId());
        }
    }
    public static synchronized HttpSession getSession(String sessionId) {
        if (sessionId == null)
            return null;
        return (HttpSession) sessionMap.get(sessionId);
    }
}

