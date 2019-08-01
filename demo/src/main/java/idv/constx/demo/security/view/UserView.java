package idv.constx.demo.security.view;

import idv.constx.demo.base.view.View;
import idv.constx.demo.security.entity.User;

public class UserView extends View{

	private static final long serialVersionUID = 1L;

	public UserView(User user){
		super(user);
		super.put("status", user.getStatus().equals("enabled") ? "正常":"停用");
		super.remove("serialVersionUID");
		super.remove("salt");
		super.remove("password");
	}
	
	
}
