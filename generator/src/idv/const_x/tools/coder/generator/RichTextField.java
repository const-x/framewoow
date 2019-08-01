package idv.const_x.tools.coder.generator;

import idv.const_x.jdbc.table.meta.SimpleType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;

public class RichTextField extends Field {

	private String tools = "Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,|,RemoveformatAlign,List,Outdent,Indent,Link,Unlink,Img,Table|,Source,Fullscreen";


	public RichTextField(String field, String alias, Integer width, Integer height, Integer size) {
		super(field,SimpleType.clone(SimpleTypeEnum.RICHTEXT), alias);

	}



	public String getTools() {
		return tools;
	}


	/** 可用控件
参数值：full(完全),mfull(多行完全),simple(简单),mini(迷你)
或者自定义字符串，例如：'Cut,Copy,Paste,Pastetext,|,Source,Fullscreen,About'
完整按钮表：
|：分隔符
/：强制换行
Cut：剪切
Copy：复制
Paste：粘贴
Pastetext：文本粘贴
Blocktag：段落标签
Fontface：字体
FontSize：字体大小
Bold：粗体
Italic：斜体
Underline：下划线
Strikethrough：中划线
FontColor：字体颜色
BackColor：字体背景色
SelectAll：全选
Removeformat：删除文字格式
Align：对齐
List：列表
Outdent：减少缩进
Indent：增加缩进
Link：超链接
Unlink：删除链接
Anchor：锚点
Img：图片
Flash：Flash动画
Media：Windows media player视频
Hr：插入水平线
Emot：表情
Table：表格
Source：切换源代码模式
Preview：预览当前代码
Print：打印
Fullscreen：切换全屏模式
	 */
	public void setTools(String tools) {
		this.tools = tools;
	}
	
    
	
   
}
