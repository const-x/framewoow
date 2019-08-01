package idv.constx.demo.base.service;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





import org.springframework.beans.factory.annotation.Autowired;

import idv.constx.demo.base.dao.BaseDao;
import idv.constx.demo.base.entity.AuditEntity;
import idv.constx.demo.base.entity.AuditStatusEnum;
import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.base.entity.LevelEntry;
import idv.constx.demo.common.DateUtils;
import idv.constx.demo.common.exception.ExceptionUtils;
import idv.constx.demo.common.redis.RedisCacheWapper;
import idv.constx.demo.security.entity.User;
import idv.constx.demo.security.shiro.ShiroDbRealm.ShiroUser;

public abstract class BaseServiceImpl  {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired(required=false)
	private RedisCacheWapper cacheWapper;

	
	
	/**
	 * 更新操作 点击界面修改按钮弹出修改界面前调用
	 */
	protected void proUpdate(IDEntity... entrys){
    	User user = getCurrentUser();
		int level = user.getUserType();
		boolean isLevelDate = (entrys[0] instanceof LevelEntry);
    	for(IDEntity entry:entrys){
			//设置新增数据等级为对应创建者等级
			if(isLevelDate){
				if(level > ((LevelEntry)entry).getLevel()){
					 ExceptionUtils.throwBizException("当前用户没有修改此数据的权限，只能修改用户等级对应的数据及其下级数据");
				}
			}
		}
	}

	public void proSubmit(int action,AuditEntity entity){
		int auditStatus = entity.getAuditStatus();
		if (auditStatus == AuditStatusEnum.PASS.getValue()) {
			ExceptionUtils.throwBizException("该记录已经审批，不允许再提交或收回！");
		}else if(action ==AuditStatusEnum.CONFIRMING.getValue() && auditStatus != AuditStatusEnum.PENDING.getValue() ){
			ExceptionUtils.throwBizException("只允许收回待审批状态的记录！");
		}else if(action ==AuditStatusEnum.PENDING.getValue() && auditStatus == AuditStatusEnum.PENDING.getValue() ){
			ExceptionUtils.throwBizException("该记录已经提交审批，不允许重复提交！");
		}
	} 
	
	public void proAudit(int action,AuditEntity entity){
		int auditStatus = entity.getAuditStatus();
		if (auditStatus == AuditStatusEnum.CONFIRMING.getValue() || auditStatus == AuditStatusEnum.UN_PASS.getValue()) {
			ExceptionUtils.throwBizException("该记录还未提交，不允许审批或驳回！");
		}
		if(action == AuditStatusEnum.PASS.getValue() && auditStatus == AuditStatusEnum.PASS.getValue() ){
			ExceptionUtils.throwBizException("该记录已经审批，不允许重复审批！");
		}
		if(action == AuditStatusEnum.UN_PASS.getValue() && auditStatus == AuditStatusEnum.PASS.getValue() ){
			ExceptionUtils.throwBizException("该记录已经审批通过，不允许驳回！");
		}
	} 
	

	/**
	 * 根据实体类型，获取具体的Dao实现
	 * 
	 * @param clazz
	 * @return
	 *
	 * @author const.x
	 * @createDate 2014年8月28日
	 */
	protected abstract BaseDao getDaoByEntryType(Class<? extends IDEntity> clazz);
	
	
	
	protected  List<Long> getIds(IDEntity... entrys){
		if(entrys == null){
			return null;
		}
		List<Long> ids = new ArrayList<Long>(entrys.length);
		for(int i = 0 ; i < entrys.length; i++){
			ids.add(entrys[i].getId());
		}
		return ids;
	}
	
	protected  List<Long> getIds(Collection<? extends IDEntity> entrys){
		if(entrys == null){
			return null;
		}
		List<Long> ids = new ArrayList<Long>(entrys.size());
		for (Iterator<? extends IDEntity> iterator = entrys.iterator(); iterator.hasNext();) {
			ids.add(iterator.next().getId());
		}
		return ids;
	}
	
	protected  List<Long> getIds(Long... entrys){
		if(entrys == null){
			return null;
		}
		List<Long> ids = new ArrayList<Long>(entrys.length);
		for(int i = 0 ; i < entrys.length; i++){
			ids.add(entrys[i]);
		}
		return ids;
	}
	
	protected  void validateTimeRange(String beginTime,String endTime){
		if( StringUtils.isBlank(beginTime)){
			ExceptionUtils.throwBizException("开始时间不能为空！");
		}
		if( StringUtils.isBlank(endTime)){
			ExceptionUtils.throwBizException("结束时间不能为空！");
		}
		SimpleDateFormat sdf;
		if(beginTime.length() == 19 ){
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else{
			sdf = new SimpleDateFormat("HH:mm:ss");
		}
		
		Date begin = null;
		try {
			begin = sdf.parse(beginTime);
		} catch (ParseException e1) {
			ExceptionUtils.throwBizException("开始时间格式不正确！");
		}
		Date end = null;
		try {
			end = sdf.parse(endTime);
		} catch (ParseException e) {
			ExceptionUtils.throwBizException("结束时间格式不正确！");
		}
		if(end.before(begin)){
			ExceptionUtils.throwBizException("结束时间不能大于开始时间！");
		}
	}
	

	
	protected  void validateTimeRangeWhenExist(String beginTime,String endTime){
		if( StringUtils.isNotBlank(beginTime)&& StringUtils.isNotBlank(endTime)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date begin = null;
			try {
				begin = sdf.parse(beginTime);
			} catch (ParseException e1) {
				ExceptionUtils.throwBizException("开始时间格式不正确！");
			}
			Date end = null;
			try {
				end = sdf.parse(endTime);
			} catch (ParseException e) {
				ExceptionUtils.throwBizException("结束时间格式不正确！");
			}
			if(end.before(begin)){
				ExceptionUtils.throwBizException("结束时间不能大于开始时间！");
			}
		}
		
	}
	
	
	
	/**
	 * 获取当前登录用户
	 * 
	 * @return
	 *
	 * @author const.x
	 * @createDate 2014年8月30日
	 */
	protected User getCurrentUser(){
		ShiroUser shiroUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		return shiroUser.getUser();
	}
   
	
	/**
	 * 保存操作 执行数据库插入前调用
	 */
	protected void beforeSave(IDEntity... entrys){
		User user = getCurrentUser();
		int level = user.getUserType();
		for(IDEntity entry:entrys){
			//插入 创建人  创建时间 最后修改时间
			entry.setCreater(getCurrentUser().getId());
			String now = DateUtils.getNowDateTime();
			entry.setCreatetime(now);
			entry.setModifyer(getCurrentUser().getId());
			entry.setModifytime(now);
			//设置新增数据等级为对应创建者等级
			if(entry instanceof LevelEntry){
				((LevelEntry)entry).setLevel(level);
			}
		}
	}
	

	
    
    /**
     * 获取数据库中的数据,对回传数据进行补充
	 * 验证当前修改数据的版本是否比数据库中的版本低
	 * 更新数据最后修改时间
	 * 
	 * @param entry
	 *
	 * @author const.x
	 * @createDate 2014年8月28日
	 */
	public List<IDEntity> beforeUpdate(IDEntity... entrys){
		Map<Long,IDEntity> entryMap = new HashMap<Long,IDEntity>(entrys.length);
		List<Long> ids = new ArrayList<Long>(entrys.length);
		String now = DateUtils.getNowDateTime();
		for(IDEntity entry : entrys){
			entryMap.put(entry.getId(), entry);
			ids.add(entry.getId());
		}
		BaseDao dao = this.getDaoByEntryType(entrys[0].getClass());
		List<IDEntity> lastEntitys = dao.findLastModifyTime(ids);
		if(lastEntitys.size() < entrys.length){
			ExceptionUtils.throwBizException("当前数据已被其他人修改或删除，请刷新数据后再进行操作");
		}
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for(IDEntity lastentry  : lastEntitys){
        	try {
        		IDEntity entry = entryMap.get(lastentry.getId());
        		String origiModifyTime = entry.getModifytime();
    			String lastModifyTime = lastentry.getModifytime();
    			Date origi = sdf.parse(origiModifyTime);
    			Date last = sdf.parse(lastModifyTime);
    			if(last.after(origi)){
    				ExceptionUtils.throwBizException("当前数据已被其他人修改或删除，请刷新数据后再进行操作");
    			}
    			//补充差异VO
    			for (String field : entry.getModifiedFields()) {
    				lastentry.setAttrbute(field, entry.getAttrbute(field));
				}
    			//更新数据最后修改时间
    			lastentry.setModifyer(getCurrentUser().getId());
    			lastentry.setModifytime(now);
    			
    			entryMap.remove(lastentry.getId());
    		} catch (ParseException e) {
    			ExceptionUtils.wapperException(e);
    		}
        }
        return lastEntitys;
	}

	/**
	 * 删除操作 执行数据库删除前调用
	 */
    protected void beforeDelete(IDEntity... entrys){
    	User user = getCurrentUser();
		int level = user.getUserType();
		boolean isLevelDate = (entrys[0] instanceof LevelEntry);
		boolean isAuditDate = (entrys[0] instanceof AuditEntity);
    	for(IDEntity entry:entrys){
			//设置新增数据等级为对应创建者等级
			if(isLevelDate){
				if(level > ((LevelEntry)entry).getLevel()){
					 ExceptionUtils.throwBizException("当前用户没有修改此数据的权限，只能修改用户等级对应的数据及其下级数据");
				}
			}
			if(isAuditDate){
				int status = (int) entry.getAttrbute(AuditEntity.FIELD_AUDITSTATUS);
				if( status != AuditStatusEnum.CONFIRMING.getValue() && status != AuditStatusEnum.UN_PASS.getValue()){
					 ExceptionUtils.throwBizException("只能删除待提交或已驳回状态的数据！");
				}
			}
		}
	}
    
    public static String cacheKeyRule(String name,Long id){
    	return name+"_"+id;
    }
    
	protected void cache(IDEntity entrys) {
//		if (entrys.getId() != null) {
//			String cacheKey = cacheKeyRule(entrys.getClass().getName(),
//					entrys.getId());
//			cacheWapper.putValueWrapper(cacheKey, entrys, 60 * 30);
//		}
	}
    
    protected <T extends IDEntity> List<T> getInCache(Class<T> clazz,Long... ids){
    	List<T> backList = new ArrayList<T>();
//		T entity = null;
//    	for(Long id : ids){
//			String cacheKey = cacheKeyRule(clazz.getName(), id);
//			entity = cacheWapper.getValueWrapper(cacheKey);
//			backList.add(entity);
//    	}
    	return backList;
    }
    
    protected <T extends IDEntity> T getInCache(Class<T> clazz,Long id){
		T entity = null;
//		if (id != null) {
//			String cacheKey = cacheKeyRule(clazz.getName(), id);
//			entity = cacheWapper.getValueWrapper(cacheKey);
//		}
		return entity;
    }
    
    protected void updateInCache(IDEntity entrys){
//    	cache(entrys);
    }
    
    protected void delInCache(Class<? extends IDEntity> clazz,Long... ids){
//		for (Long id : ids) {
//			if(id != null){
//				String cacheKey = cacheKeyRule(clazz.getName(), id);
//				cacheWapper.del(cacheKey);
//			}
//		}
    }
    
    protected void cache(String key,Object value){
//    	if(StringUtils.isNotBlank(key)){
//			cacheWapper.putValueWrapper(key, value, 60 * 30);
//    	}
    }
    
    protected <T> T getInCache(String key,Class<T> clazz){
		T entity = null;
//		if (StringUtils.isNotBlank(key)) {
//			entity = cacheWapper.getValueWrapper(key);
//		}
		return entity;
    }
    
	/**
	 * 将错误信息写入文件流，用于excel导出操作
	 */
	protected void exportError(Exception e, OutputStream out) {
		try {
			HSSFWorkbook workbook  = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("系统错误");
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
			cell.setCellValue(sw.toString());
			workbook.write(out);
		} catch (Exception ex) {
		    logger.error(ex.getLocalizedMessage(),e);
		}
	}

}
