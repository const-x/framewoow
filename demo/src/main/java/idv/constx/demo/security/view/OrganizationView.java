package idv.constx.demo.security.view;

import java.util.ArrayList;
import java.util.List;

import idv.constx.demo.base.view.View;
import idv.constx.demo.security.entity.Organization;

public class OrganizationView extends View{

	private static final long serialVersionUID = 1L;

	public OrganizationView(Organization org){
		super(org);
		if(org.getChildren()!= null){
			List<OrganizationView> chs = new ArrayList<>();
			for (Organization ch : org.getChildren()) {
				OrganizationView v = new OrganizationView(ch);
				chs.add(v);
			}
			super.put("children", chs);
		}
	}

	
	
}
