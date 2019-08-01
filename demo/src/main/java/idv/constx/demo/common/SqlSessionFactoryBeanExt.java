package idv.constx.demo.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idv.constx.demo.base.entity.IMapperEntity;
import idv.constx.demo.common.exception.ExceptionUtils;


/**
 * 支持typeAliases通配符配置
 * 
 * 
 * @author const.x
 */
public class SqlSessionFactoryBeanExt extends SqlSessionFactoryBean {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public void setTypeAliases(String[] packages) {
		if (packages != null) {
			List<Class<?>> classes = new ArrayList<Class<?>>();
			for (String pack : packages) {
				List<String> types = this.parse(pack);
				if(types.size() == 0){
					logger.warn("未找到的实体类型： {} ", pack);
				}
				for (String type : types) {
					try {
						classes.add(Class.forName(type));
					} catch (ClassNotFoundException e) {
					}
				}
			}
			super.setTypeAliases(classes.toArray(new Class<?>[0]));
		}
	}
	
	
	private List<String> parse(String path){
		List<String> array = new ArrayList<String>();
		String[] packages = path.trim().split("\\.");
		String basePackage = "";
		String basepath = this.getClass().getResource("/").getFile();
		array.addAll(this.findInpath(basepath, basePackage,packages[0],packages, 0));
		return array;
	}
   
	private List<String> findInpath(String baseFile,String basePackage, String current,String[] packages,int index){
		List<String> array = new ArrayList<String>();
		File file = new File(baseFile);
		if(!file.exists()){
			return array;
		}
		if(current.contains("*")){
			if(packages.length == index + 1){
				if(current.equals("*")){
					String[] classes = file.list();
					for(String className : classes){
					    className = className.substring(0,className.length()-6);
					    String fullname = basePackage + className;
				    	Class clazz = null;
						try {
							clazz = Class.forName(fullname);
						} catch (ClassNotFoundException e) {
							continue;
						}
				    	if(IMapperEntity.class.isAssignableFrom(clazz)){
				    		array.add(fullname);
				    	}
					}
					return  array;
				}else{
					String[] classes = file.list();
					String p = current.replace("*", ".*");
					Pattern pattern = Pattern.compile(p);
					for(String className : classes){
					    className = className.substring(0,className.length()-6);
					    Matcher matcher = pattern.matcher(className);
					    if(matcher.matches()){
					    	String fullname = basePackage + current;
					    	Class clazz = null;
							try {
								clazz = Class.forName(fullname);
							} catch (ClassNotFoundException e) {
								continue;
							}
							if(IMapperEntity.class.isAssignableFrom(clazz)){
					    		array.add(fullname);
					    	}
					    }
					}
					return  array;
				}
			}else{
				if(current.equals("*")){
					String[] chfiles = file.list();
					for(String chfile : chfiles){
					    array.addAll(this.findInpath(baseFile, basePackage,chfile, packages, index));
					}
					return  array;
				}else{
					String[] chfiles = file.list();
					String p = current.replace("*", ".*");
					Pattern pattern = Pattern.compile(p);
					for(String chfile : chfiles){
						 Matcher matcher = pattern.matcher(chfile);
						if(matcher.matches()){
							 array.addAll(this.findInpath(baseFile, basePackage,chfile, packages, index));
						}
					}
					return  array;
				}
			}
		}else{
			if(packages.length == index + 1){
					String[] classes = file.list();
					for(String className : classes){
					    className = className.substring(0,className.length()-6);
					    if(className.equals(current)){
					    	String fullname = basePackage + current;
					    	Class clazz = null;
							try {
								clazz = Class.forName(fullname);
							} catch (ClassNotFoundException e) {
								ExceptionUtils.throwBizException("未找到实体类型：" + fullname);
							}
							if(IMapperEntity.class.isAssignableFrom(clazz)){
					    		array.add(fullname);
					    	}else{
					    		ExceptionUtils.throwBizException("未找到实体类,或者实体类未实现IMapperEntity接口：" + fullname);
					    	}
					    	return  array;
					    }
					}
			}else{
				baseFile = baseFile + File.separator + current;
				basePackage = basePackage + current + ".";
				array.addAll(findInpath(baseFile,basePackage,packages[index + 1],packages,index + 1));
			}
		}
		return array;
	}
	
	public static void main(String[] args){
		List<String> types = new SqlSessionFactoryBeanExt().parse("com.constx.project.demo.entity.User");
		for (String object : types) {
			System.out.println(object);
		}
	}
	
}
