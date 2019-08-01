package idv.constx.demo.security.view;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import idv.constx.demo.base.view.View;
import idv.constx.demo.security.entity.ModuleTypeEnum;
import idv.constx.demo.security.entity.Module;

 
/**
 * 业务模块视图对象
 */ 
public class ModuleView extends View implements Serializable{

private static final long serialVersionUID = 1L;

  public ModuleView(Module module) { 
	super(module);
	if(module.getParent() != null){
		super.put("parent", module.getParent());
	}
    super.putShortString("description");
    super.put("enableStr", module.getEnable()== 1 ? "是":"否");
    if(module.getType() != null){
        super.put("typeStr", ModuleTypeEnum.getTypeByInt(module.getType()).getName());
    }
  }

  private List<ModuleView> children = new ArrayList<ModuleView>();

  public void addChild(ModuleView view){
    children.add(view);
  }

  public void addChildren( List<ModuleView> views){
    if(children == null){
      children = views;
    }else{
      children.addAll(views);
    }
  }

  public List<ModuleView> getChildren(){
    return children;
  }

}