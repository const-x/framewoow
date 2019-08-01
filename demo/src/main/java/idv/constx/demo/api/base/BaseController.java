/**
 * @author zhangxulong
 * @createDate 2014年8月26日
 */
package idv.constx.demo.api.base;

import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.common.exception.BussinessException;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * Mobile Api调用控制基础类
 * 
 */
public class BaseController {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());


	
	protected String handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		if(logger.isErrorEnabled()){
			StringBuffer print = new StringBuffer();
			print.append("request:{");
			print.append("method:").append(request.getParameter("method"));
			print.append("}");

			// 打印出调用参数
			Map<String, String[]> tmp = request.getParameterMap();
			Map<String, String[]> paramsMap = tmp instanceof TreeMap ? tmp : new TreeMap<>(tmp);

			print.append(";params:{");
			boolean flag = false;
			for (Entry<String, String[]> entry : paramsMap.entrySet()) {
				if (!entry.getKey().equals("method")) {
					for (String value : entry.getValue()) {

						if (flag) {
							print.append(",");
						} else {
							flag = true;
						}
						print.append(entry.getKey()).append(":").append(value);
					}
				}
			}
			print.append("}");
			if(ex instanceof BussinessException){
				BussinessException c = (BussinessException)ex;
				logger.error(c.getLocalizedMessage()+": {}\n", print.toString(), c.getCause());
			}else{
				logger.error("api异常: {}\n", print.toString(), ex);
			}
		}
		return DataPacket.errorJsonResult(ex.getMessage(), ex);
	}
	
	protected void removeSysFields(IDEntity eitity){
		eitity.setCreater(null);
		eitity.setCreatetime(null);
		eitity.setModifyer(null);
		eitity.setModifytime(null);
		eitity.setAttrbuteWhenExist("dr", null);
	}
	
	
	protected void removeFields(IDEntity eitity,String ... fields){
		for (String field : fields) {
			eitity.setAttrbuteWhenExist(field, null);
		}
	}
	


	

}
