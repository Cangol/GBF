res资源命名规范
=============
	通用资源可以无前缀（模块_）

drawable|color|selector
-----------
	组件-模块-功能|类别-状态

style
-----------
	组件-模块名-功能-状态
	TextView_Common_Hint
	
string
-----------
	模块-功-状态
	feedback_contact_hint
	
layout 命名规范
-----------
	类别-模块-功能-状态
	activity_模块_xxx
	fragment_模块_xxx
	layout_模块_xxx
	view_模块_xxx
	listview_item_模块_xxx
	gridview_item_模块_xxx
	tabview_item_模块_xxx
	
id 命名规范
=============
	模块_功能_状态_组件（或组件缩写）
	例如：
	    apk_download_start_img
		apk_download_start_text
		apk_download_start_layout
		apk_download_start_btn
		apk_download_start_label
		apk_download_start_view
		apk_download_start_customerview

src  命名规范
=============
	包名及类
-----------
	mobi.cangol.mobile.app.模块      
	        fragment类        
	        activity类       	
	mobi.cangol.mobile.app.模块.adapter  
		adapter类   
	mobi.cangol.mobile.app.模块.view 
		CustomerView    	
	mobi.cangol.mobile.app.模块.api  
		API交互Contants	    
	mobi.cangol.mobile.app.模块.model    
		model类 	
	mobi.cangol.mobile.app.模块.utils    
		utils类 
	mobi.cangol.mobile.app.模块.xxx  
		其他功能性类,如视频 
	
java类 命名规范
-----------
	成员变量：驼峰命名法 或m变量命名法
	常量：（大写） 功能_状态 
	其他遵循java基本规范
	
SVN|GIT 注释规范
-----------
	+):新增资源
	-):减少资源
	*):修改
	!):fix bug no.
	B):分支
	T):标记
	M):合并
	
项目style规则
-----------
	gridview 样式(分割线|滚动|缓冲已定义)
	style="@style/GridView"
	
	listview 样式(分割线|滚动|缓冲已定义)
	style="@style/ListView"
	
	列表项第一行文本样式
	Common_List_Title
	
	
	列表项第二行文本样式
	Common_List_Content
	
	列表项第三行（或标注）文本样式
	Common_List_Mark
	
	字体
	大小单位sp=(int)dp*1.25
	
	布局中不得出现 未定义的 、“字符串”、 颜色
	
	字体大小 颜色一律使用 style
	
	drawable/color 下xml 中color须在color中定义
	
	常用边界值 页面边距8dp

  
    