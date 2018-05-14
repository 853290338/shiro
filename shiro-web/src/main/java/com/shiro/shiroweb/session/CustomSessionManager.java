package com.shiro.shiroweb.session;

import java.io.Serializable;
import javax.servlet.ServletRequest;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

/**
 * 自定义sessionmanager
 *
 * @author denpeng
 * @create 2018-05-14 15:12
 **/
public class CustomSessionManager extends DefaultWebSessionManager {

  /**
   * 此方法是为了防止多次访问redis
   */
  @SuppressWarnings("ConstantConditions")
  @Override
  protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {

    Serializable sessionId = getSessionId(sessionKey);
    ServletRequest request = null;
    if (sessionKey instanceof WebSessionKey) {
      request = ((WebSessionKey) sessionKey).getServletRequest();
    }
    if (request != null && sessionId != null) {
      Session session = (Session) request.getAttribute(sessionId.toString());
      if (session != null) {
        return session;
      }
    }
    Session session = super.retrieveSession(sessionKey);
    if (null != request && sessionId != null) {
      request.setAttribute(sessionId.toString(), session);
    }
    return session;
  }
}
