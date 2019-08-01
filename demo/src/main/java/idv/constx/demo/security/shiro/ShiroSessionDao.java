package idv.constx.demo.security.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShiroSessionDao   extends AbstractSessionDAO {

    private static final Logger log = LoggerFactory.getLogger(MemorySessionDAO.class);



    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        storeSession(sessionId, session);
        log.debug("create session :" + sessionId);
        return sessionId;
    }

    protected Session storeSession(Serializable id, Session session) {
        if (id == null) {
            throw new NullPointerException("id argument cannot be null.");
        }
        //TODO 缓存session
        log.debug("store session :" + id);
        return session;
    }

    protected Session doReadSession(Serializable sessionId) {
    	//TODO 读取缓存session
    	Session session = null;
    	log.debug("store session :" + sessionId + " result : " + session);
        return session;
    }

    public void update(Session session) throws UnknownSessionException {
        storeSession(session.getId(), session);
    }

    public void delete(Session session) {
        if (session == null) {
            throw new NullPointerException("session argument cannot be null.");
        }
        Serializable id = session.getId();
        if (id != null) {
        	//TODO 删除缓存session
        	log.debug("delete session :" + id );
        }
    }

    public Collection<Session> getActiveSessions() {
    	//TODO 获取所有缓存session
    	Collection<Session> sessions = null;
    	if(sessions == null || sessions.size() == 0){
    		return Collections.EMPTY_LIST;
    	} 
    	List<Session> activeSessions = new ArrayList<Session>();
    	for (Session session : sessions) {
			SimpleSession ss = (SimpleSession)session;
			if(!ss.isExpired()){
				activeSessions.add(session);
			}
		}
    	log.debug("get activity sessions,size :" + activeSessions.size() );
       return activeSessions;
    }
	
	
	

}
