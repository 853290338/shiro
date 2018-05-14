package com.shiro.shiroweb.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.SerializationUtils;

/**
 * shiro sesison
 *
 * @author denpeng
 * @create 2018-05-14 13:43
 **/
public class SessionDao extends AbstractSessionDAO {

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  private static final String SHIRO_KEY_PREFIX = "shiro_session";

  private String getKey(String sessionId) {
    return SHIRO_KEY_PREFIX + sessionId;
  }

  private void saveSession(Session session) {
    if (session == null || session.getId() == null) {
      return;
    }
    String key = getKey(session.getId().toString());
    byte[] value = SerializationUtils.serialize(session);
    redisTemplate.opsForValue().set(key, value, 180, TimeUnit.SECONDS);
  }

  @Override
  protected Serializable doCreate(Session session) {
    Serializable sessionId = generateSessionId(session);
    assignSessionId(session, sessionId);
    saveSession(session);
    return sessionId;
  }

  @Override
  protected Session doReadSession(Serializable sessionId) {
    System.out.println("sessionId:" + sessionId);
    if (sessionId == null) {
      return null;
    }
    String key = getKey(sessionId.toString());
    return (Session) SerializationUtils.deserialize((byte[]) redisTemplate.opsForValue().get(key));
  }

  @Override
  public void update(Session session) throws UnknownSessionException {
    saveSession(session);

  }

  @Override
  public void delete(Session session) {
    if (session != null && session.getId() != null) {
      redisTemplate.delete(getKey(session.getId().toString()));
    }
  }

  @Override
  public Collection<Session> getActiveSessions() {
    Set<String> keys = redisTemplate.keys(SHIRO_KEY_PREFIX + "*");
    if (CollectionUtils.isEmpty(keys)) {
      return null;
    }
    Set<Session> sessions = new HashSet<>();
    for (String key : keys) {
      Session session = (Session) SerializationUtils.deserialize((byte[]) redisTemplate.opsForValue().get(key));
      sessions.add(session);
    }
    return sessions;
  }
}
