package idv.constx.api.base.dao;

import idv.constx.api.common.ExceptionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;




/**
 * 持久层抽象类
 * 
 * @author const.x
 */
public abstract class AbstractDao {

	public enum Sort {

		DESC("DESC"), ASC("ASC");

		String name;

		Sort(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public static final String DESC = "DESC";
	public static final String ASC = "ASC";

//	@Value("${jdbc.driver}")
	private String driver="com.mysql.jdbc.Driver";
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	/**
	 * 获取分页记录开始位置
	 * 
	 * @param page 页码
	 * @param pageSize 每页大小
	 * @return
	 * 
	 * @author const.x
	 * @createDate 2014年9月7日
	 */
	protected Integer getFirstIndex(Integer page, Integer pageSize) {
		Integer rvalue = 0;
		if (page > 0) {  
			rvalue = (page - 1) * pageSize;
		}
		return rvalue;
	}

	/**
	 * 分页查询（默认按升序排序）
	 * 
	 * @param sql
	 * @param rowmapper
	 * @param page
	 * @param pageSize
	 * @param orderByField
	 * @param args
	 * @return
	 * 
	 * @author const.x
	 * @createDate 2014年9月10日
	 */
	protected <T> PageResult<T> queryByPage(String sql, Class<T> resultClass, Integer page, Integer pageSize,
			String orderByField, Object... args) {
		return this.queryByPage(sql, resultClass, page, pageSize, orderByField, Sort.ASC, args);
	}

	/**
	 * 分页查询（按指定方式排序）
	 * 
	 * @param sql
	 * @param rowmapper
	 * @param page
	 * @param pageSize
	 * @param orderByField
	 * @param sortType
	 * @param args
	 * @return
	 * 
	 * @author const.x
	 * @createDate 2014年9月10日
	 */
	@SuppressWarnings("deprecation")
	protected <T> PageResult<T> queryByPage(String sql, Class<T> resultClass, Integer page, Integer pageSize,
			String orderByField, Sort sortType, Object... args) {
		PageResult<T> result = new PageResult<T>();
		result.setPage(page);
		result.setPageSize(pageSize);
		StringBuilder countSql = new StringBuilder(" select count(1) from (");
		countSql.append(sql).append(" ) as tmp_count ");
		int total = jdbcTemplate.queryForInt(countSql.toString(), args);
		result.setTotal(total);
		if (total == 0) {
			result.setDatas(new ArrayList<T>(0));
			return result;
		}


		StringBuilder pageSql = new StringBuilder();
		int  startIndex = (page - 1) * pageSize;
		if(driver.equalsIgnoreCase("com.mysql.jdbc.Driver")){
			pageSql.append(sql).append(" ORDER BY ").append(orderByField).append(" ").append(sortType.getName()).append(" limit ")
			.append(getFirstIndex(page, pageSize)).append(",").append(pageSize);
		}else if(driver.equalsIgnoreCase("com.mysql.jdbc.Driver")){
			pageSql.append("select * from(select pageview.*,ROWNUM rn from( ");
			pageSql.append(sql).append(") pageview where ROWNUM<= ").append(startIndex + pageSize).append(" ) where rn> ").append(startIndex);
		}else{
			ExceptionUtils.wapperBussinessException("不支持的数据库类型：" + driver);
		}
		RowMapper<T> rowmapper = new BeanPropertyRowMapper<T>(resultClass);
		List<T> datas = jdbcTemplate.query(pageSql.toString(), rowmapper, args);
		result.setDatas(datas);
		return result;
	} 
	
	
	protected <T> T queryForObject(String sql,RowMapper<T> rowMapper, Object... params) {
		List<T> results = null;
		try{
			results = jdbcTemplate.query(sql,new RowMapperResultSetExtractor<T>(rowMapper, 1),params);
		}catch(EmptyResultDataAccessException e){
			
		}
		return requiredSingleResult(results);
	}

	public static <T> T requiredSingleResult(Collection<T> results) throws IncorrectResultSizeDataAccessException {
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		if (results.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, size);
		}
		return results.iterator().next();
	}
	
	/**
	 *  查询数据列表
	 * 
	 * @param sql
	 * @param rowMapper
	 * @param params
	 * @return
	 *
	 * @author const.x
	 * @createDate 2015年1月30日
	 */
	protected <T> List<T> queryForList(String sql, RowMapper<T> rowMapper, Object... params) {
		List<T> results = null;
		try {
			results = jdbcTemplate.query(sql, rowMapper, params);
		} catch (EmptyResultDataAccessException e) {
			results = new ArrayList<T>(0);
		}
		return results;
	}
}
