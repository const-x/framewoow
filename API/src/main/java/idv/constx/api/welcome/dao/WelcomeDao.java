package idv.constx.api.welcome.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import idv.constx.api.base.dao.AbstractDao;
import idv.constx.api.welcome.vo.WelcomeInfo;



@Repository
public class WelcomeDao extends AbstractDao{

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	
	private static final RowMapper<WelcomeInfo> WELCOME_INFO = new BeanPropertyRowMapper<>(WelcomeInfo.class);
	
	public WelcomeInfo getInfo(String keywords) {
		try {
			WelcomeInfo info = new WelcomeInfo();
			info.setCode("1000");
			info.setName(keywords);
			return info;
		} catch (DataAccessException e) {
			logger.warn("查询返回异常：{}, args=[{}]");
			return null;
		}
	}
	
	
   
	
	
}
