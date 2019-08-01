package idv.constx.api.welcome.server.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idv.constx.api.base.mvc.interceptor.Client;
import idv.constx.api.base.mvc.interceptor.OpenApiSecurityInterceptor;
import idv.constx.api.common.ExceptionUtils;
import idv.constx.api.welcome.dao.WelcomeDao;
import idv.constx.api.welcome.server.WelcomeService;
import idv.constx.api.welcome.vo.WelcomeInfo;

@Service
public class WelcomeServiceImpl implements WelcomeService{
	
	@Autowired
	private WelcomeDao dao;

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	@Transactional(readOnly = true)
	public WelcomeInfo getInfo(String keywords) {
		try{
			WelcomeInfo info  =  dao.getInfo(keywords);
			Client client = OpenApiSecurityInterceptor.getContext().getClient();
			 info.setPlatform(client.getPlatform());
			return info;
		}catch(Exception e){
			logger.error("查询失败",e);
			ExceptionUtils.wapperException(e);
		}
		return null;
	}


}
