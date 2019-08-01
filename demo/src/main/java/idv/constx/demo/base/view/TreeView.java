package idv.constx.demo.base.view;

import java.util.ArrayList;
import java.util.List;

import idv.constx.demo.base.entity.TreeEntity;

/**
id：节点的 id，它对于加载远程数据很重要。
text：要显示的节点文本。
state：节点状态，'open' 或 'closed'，默认是 'open'。当设置为 'closed' 时，该节点有子节点，并且将从远程站点加载它们。
checked：指示节点是否被选中。
attributes：给一个节点添加的自定义属性。
children：定义了一些子节点的节点数组。
iconCls:图标
*/
public class TreeView extends View{

	private static final long serialVersionUID = 1L;

	public TreeView(TreeEntity entity){
		super.put("id", entity.getId());
		super.put("text", entity.getShowName());
		super.put("text", entity.getShowName());
		super.put("iconCls", "easy-icon-black");
		this.handleChildren(entity);
	}
	
	private void handleChildren(TreeEntity entity){
		if(entity.getChildren() != null){
			List<TreeView> chs = null;
			if(super.containsKey("children")){
				chs = (List<TreeView>) super.get("children");
			}else{
				chs = new ArrayList<TreeView>(entity.getChildren().size());
				super.put("children", chs);
			}
			for (Object ch : entity.getChildren()) {
				TreeView view = new TreeView((TreeEntity)ch);
				chs.add(view);
			}
		}
	}
	
	public void setState(String state) {
		super.put("state", state);
	}
	
	public void setChecked(boolean checked) {
		super.put("checked", checked);
	}
	
	public void setAttributes(String attributes) {
		super.put("attributes", attributes);
	}

	public void setChildren(List<TreeView> children) {
		super.put("children", children);
	}
	
	
	
	public static void setFristNodeOpened(List<TreeView> views){
		TreeView view = views.get(0);
		if(view.containsKey("children")){
			List<TreeView> chs = (List<TreeView>) view.get("children");
			for (TreeView treeView : chs) {
				treeView.put("state", "closed");
			}
		}
		if(views.size() > 1){
			for (int i = 1; i < views.size(); i++) {
				TreeView treeView = views.get(i);
				treeView.put("state", "closed");
			}
		}
	}
	
	public static void setFristNodeOpened(TreeView view){
		if(view.containsKey("children")){
			List<TreeView> chs = (List<TreeView>) view.get("children");
			for (TreeView treeView : chs) {
				treeView.put("state", "closed");
				setFristNodeOpened(treeView);
			}
		}
	}
    
	
}
