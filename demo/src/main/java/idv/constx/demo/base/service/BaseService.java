package idv.constx.demo.base.service;

import java.util.List;

import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.util.dwz.Page;


public interface BaseService<T extends IDEntity, E  extends BaseCondition> {
	
	
	public T getById(Long id);
	
	public List<T> getByIds(Long ...ids);
	
	public List<T> getByCondition(E condition,Page page);
   
	public Long save(T entity);
	
	public void delete(Long ...ids);

	public void update(T entity);
	
	public T getForUpdate(Long id);
	
}
