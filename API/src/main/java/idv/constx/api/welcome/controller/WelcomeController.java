package idv.constx.api.welcome.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idv.constx.api.base.mvc.BaseController;
import idv.constx.api.common.DataPacket;
import idv.constx.api.welcome.server.WelcomeService;

/**
 * 测试接口
 * 
 * @author 
 * 
 */
@RestController
@RequestMapping("/api")
public class WelcomeController  extends BaseController{

	@Autowired
	private WelcomeService service;
	
	@RequestMapping(params = "method=idv.constx.welcome.get")
	public Map<String, Object> index(HttpServletRequest request,HttpServletResponse response, String keywords) {
		Object data = null;
		try {
			data = service.getInfo(keywords);
		} catch (Exception e) {
			return super.handleException(request, response, e);
		}
		return DataPacket.jsonResult(data);
	}
	


}
