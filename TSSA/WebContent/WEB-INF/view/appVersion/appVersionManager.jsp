<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageMode" value="${pageMode}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="应用版本管理" />
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<script type="text/javascript">
Ext.onReady(function(){
	var intPageSize = 10;
	
	var platform_src = [['IOS'],['Android']];
	var type_src = [['IOS', 'IOS'], ['Android', 'Android']];
	var dsCurStatus = new Ext.data.SimpleStore({
		fields : ['platformValue', 'platformText'],
		data : type_src
	});
	
	var platformType = new Ext.form.ComboBox({
		id : 'platform',
		name : 'platform',
		store : dsCurStatus,
		valueField : 'platformValue',
		displayField : 'platformText',
		fieldLabel : '应用平台',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local'
	}); 
	
	//工具栏，按钮
 	var myToolbar = new Ext.Toolbar();
	myToolbar.render('ToolBar');
	
	myToolbar.addButton({
		text: '查询', 
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-find.png',
		handler: function(o, e) {
			search();
		}
	});
	myToolbar.addButton({
		text: '重置', 
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/view-refresh.png',
		handler: function(o, e) {
			reset();
		}
	});
	
	var versionToolbar = new Ext.Toolbar();
	versionToolbar.render('versionBar');
	
	versionToolbar.addButton({
		text : '新增应用版本',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-add.png',
		handler: function(o, e) {
			addVersion();
		}
	});
	versionToolbar.addButton({
		text : '修改应用版本',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-editor.png',
		handler: function(o, e) {
			modVersion();
		}
	});
	versionToolbar.addButton({
		text : '删除应用版本',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-delete.png',
		handler: function(o, e) {
			delVersion();
		}
	});
	versionToolbar.addButton({
		text : '发布版本',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/go-top.png',
		handler: function(o, e) {
			publishVersion();
		}
	});
	
	var intChildPageSize = 10;
	
  	var versionStore = new Ext.data.JsonStore({ 
 		url:'${ctx}/app_version/list.do',
//		autoLoad:true,  
 		totalProperty:'pageMode.count',
		root:'pageMode.records',
 		fields:[{name:'id', mapping:'id'},
 		        {name:'appName', mapping:'appName'} ,
 		        {name:'platform', mapping:'platform'},
 		        {name:'version', mapping:'version'},
		        /* {name:'publishDate', mapping:'publishDate.time', type:'date', dateFormat :'time'}, */
		        {name:'publishDate', mapping:'publishDate'},
		        {name:'publisher', mapping:'publisher'},
		        {name:'isPublished', mapping:'isPublished'}]
	}); 
  	
  	var versionModel = new Ext.grid.ColumnModel([{
		header:'id',
		width:100,
		sortable : true,
		dataIndex : 'id',
		hidden : true
	},{
		header : "应用名称",
		width : 80,
		sortable : true,
		dataIndex : 'appName'
	} ,{
		header : "应用平台",
		width : 80,
		sortable : true,
		dataIndex : 'platform'
	},{
		header : "应用版本",
		width : 80,
		sortable : true,
		dataIndex : 'version'
	},{
		header : "发布日期",
		width : 120,
		sortable : true,
		dataIndex : 'publishDate'/* ,
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s') */
	},{
		header : "发布人",
		width : 80,
		sortable : true,
		dataIndex : 'publisher'
	},{
		header:'是否已发布',
		width:80,
		sortable:true,
		dataIndex:'isPublished',
		renderer : function(value){
			if(value == 0){
				return "待发布";
			}else if(value == 1){
				return "已发布";
			}else{
				return "已下架";
			}
		}
	} ]); 
	
	//数据集
	var childGridForm = new Ext.grid.GridPanel({
		id : 'opercode-grid',
		store : versionStore,
		cm : versionModel,  
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.PagingToolbar({
			pageSize : intChildPageSize,
			store : versionStore,
			beforePageText : '第',
			afterPageText : '页,共 {0} 页',
			displayInfo : true,
			displayMsg : '{0} - {1} , 共 {2} 条',
			emptyMsg : "无记录"
		}),
		autoWidth : true,
		autoHeight : true,
		frame : false,
		collapsible : true,
		animCollapse : false,
		trackMouseOver : false,
		iconCls : 'icon-grid',
		loadMask : ({
			msg : '数据正在加载中,请稍後...'
		}),
		renderTo : 'childDataGrild'
	});
	
	/*  versionStore.on('load',function(){
		 
		 alert(versionStore.getCount());
		 
	 }); */
	
	function chooseParent(gridForm, rowIndex, columnIndex, e){
		var record = gridForm.getSelectionModel().getSelected();
		var platformText = record.get("platform");
		childGridForm.getStore().load({
			proxy : new Ext.data.HttpProxy({
				url : "{ctx}/app_version/list.do?isParent=0',",//获取数据的后台地址
			}),
			params:{
				platform : platformText,
				start : 0,
				limit : intChildPageSize
			}
		}); 
	}
	
	var searchForm = new Ext.form.FormPanel({
		id : 'searchForm',
		renderTo : 'SearchFormDiv',
		frame : true,
		labelAlign : 'right',
		labelWidth : 80,
		width : '100%',	
		waitMsgTarget : true,
		items : [{
			layout : 'table',
			border : true,
			labelSeparator : ':',
			layoutConfig : {
				columns : 10
			},
			items : [{
				layout : 'form',
				items : [{
					id : 'versionCode',
					xtype : 'textfield',
					fieldLabel : '版本号',
					name : 'versionCode',
					width : 100,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [platformType]
			}]
		}]
	});
	//显示工具栏
	myToolbar.doLayout(); 
	versionToolbar.doLayout(); 
	
	var platformStore = new Ext.data.ArrayStore({
		fields : [{name:'platform'}]
	});
	platformStore.loadData(platform_src);
	
 	var colModel = new Ext.grid.ColumnModel([{
		header : "应用平台",
		width : 80,
		sortable : true,
		dataIndex : 'platform'
	}]);  
	
	//数据集
	var gridForm = new Ext.grid.GridPanel({
		id : 'opercode-grid',
		store : platformStore,
		cm : colModel,  
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.PagingToolbar({
			pageSize : intPageSize,
			store : platformStore,
			beforePageText : '第',
			afterPageText : '页,共 {0} 页',
			displayInfo : true,
			displayMsg : '{0} - {1} , 共 {2} 条',
			emptyMsg : "无记录"
		}),
		autoWidth : true,
		autoHeight : true,
		frame : false,
		collapsible : true,
		animCollapse : false,
		trackMouseOver : false,
		iconCls : 'icon-grid',
		loadMask : ({
			msg : '数据正在加载中,请稍後...'
		}),
		renderTo : 'parentDataGrild'
	});
	
	gridForm.addListener('cellclick',chooseParent); 
	
	//查询
	function search(){
		var versionCode = searchForm.getForm().findField("versionCode").getValue();
		var platform = platformType.getValue();
		versionStore.load({
			proxy : new Ext.data.HttpProxy({
				url : "${ctx}/app_version/list.do?isParent=1",//获取数据的后台地址
			}),
			params:{
				version : versionCode,
				platform : platform,
				start : 0,
				limit : intPageSize
			}
		}); 
	}
	
	//重置
	function reset(){
		var sForm = searchForm.getForm();
		sForm.reset();
	}
	
	//addfrom
	var addForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		fileUpload : true,
		title : '新增应用版本',
		labelAlign : 'right',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px 5px 0',
		/* width : 640,
		height: 400, */
		autoScroll : true,
		items : [{
			layout : 'table',
			border : true,
			labelSeparator : ':',
			defaults : {
				bodyStyle : 'padding:1px'
			},
			layoutConfig : {
				columns : 1
			},
			items:[{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '应用名称',
					name : 'appName',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '应用名称不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '版本号',
						name : 'version',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '上传应用',
						name : 'appFile',
						width : 215,
						inputType : 'file',
						buttonText: '浏览'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'platform'
						}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					addForm.form.submit({
						url : '${ctx}/app_version/save.do',
						method : 'POST',
						success : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								addWin.hide();
								versionStore.reload();
						},
						failure : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
						}
					});
				}
			},{
				text : '取消',
				handler : function(){
					addWin.hide();
				}
			}]
	});
	
	//modfrom
	var modForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		fileUpload : true,
		title : '修改应用版本',
		labelAlign : 'right',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px 5px 0',
		/* width : 640,
		height: 400, */
		autoScroll : true,
		items : [{
			layout : 'table',
			border : true,
			labelSeparator : ':',
			defaults : {
				bodyStyle : 'padding:1px'
			},
			layoutConfig : {
				columns : 1
			},
			items:[{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '应用名称',
					name : 'appName',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '应用名称不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '版本号',
						name : 'version',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '上传应用',
						name : 'appFile',
						width : 215,
						inputType : 'file',
						buttonText: '浏览'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'platform'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'id'
						}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					modForm.form.submit({
						url : '${ctx}/app_version/update.do',
						method : 'POST',
						success : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								modWin.hide();
								versionStore.reload();
						},
						failure : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
						}
					});
				}
			},{
				text : '取消',
				handler : function(){
					modWin.hide();
				}
			}]
	});
	
	//新增
	var addWin = null;
	function addVersion(){
		if(gridForm.getSelectionModel().hasSelection()){
			var record = gridForm.getSelectionModel().getSelected();
			if(!addWin){
				addWin = new Ext.Window({
					el : 'add_menu',
					layout : 'fit',
					closeAction : 'hide',
					width : 380,
					height : 205,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : addForm
				});
			}
			addWin.on('show', function() {
				var aForm = addForm.getForm();
				aForm.reset();
 				var platform = record.get("platform");
 				aForm.findField('platform').setValue(platform);
			}); 
			addWin.show(this);
		} else {
			Ext.MessageBox.alert("错误","请选择应用平台");
			return;
		}
		
	}
		
	//修改
	var modWin = null;
	function modVersion(){
		if(gridForm.getSelectionModel().hasSelection() && childGridForm.getSelectionModel().hasSelection()){
			var record = gridForm.getSelectionModel().getSelected();
			var versionRecord = childGridForm.getSelectionModel().getSelected();
			if(!modWin){
				modWin = new Ext.Window({
					el : 'mod_menu',
					layout : 'fit',
					closeAction : 'hide',
					width : 380,
					height : 205,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : modForm
				});
			}
			modWin.on('show', function() {
				
				var platform = record.get("platform");
				var id = versionRecord.get("id");
				var appName = versionRecord.get("appName");
				var version = versionRecord.get("version");
				
				var mForm = modForm.getForm();
				mForm.reset();
				mForm.findField('platform').setValue(platform);
				mForm.findField('id').setValue(id);
				mForm.findField('appName').setValue(appName);
				mForm.findField('version').setValue(version);
			}); 
			modWin.show(this);
		}else{
			Ext.MessageBox.alert("错误","没有选中修改项");
			return;
		}
	}
	
	function delVersion(){
		if(gridForm.getSelectionModel().hasSelection() && childGridForm.getSelectionModel().hasSelection()){
			Ext.MessageBox.confirm('提示', '确认要删除该版本吗?', submitDel);
			function submitDel(btn){
				if(btn == 'yes'){
					Ext.MessageBox.wait("数据正在处理中,请稍後...", "提交");
					var record = childGridForm.getSelectionModel().getSelected();
					var id = record.get('id');
					Ext.Ajax.request({
						url : '${ctx}/app_version/delete.do',
						method : 'POST',
						params: { appId : id },
						success : function(response,request){
							var jsonObj = eval('(' + response.responseText + ')');
							Ext.MessageBox.hide();
							Ext.MessageBox.alert("提示",jsonObj.msg);
							versionStore.reload();
						},
						failure : function(response,request){
								Ext.MessageBox.hide();
								Ext.MessageBox.alert("提示","删除失败");
						}
					});
				}
			}
		}else{
			Ext.MessageBox.alert("错误","必须同时选中应用平台与应用版本才能删除");
			return;
		}
	}
	
	/*
	 *	版本发布
	 */
	function publishVersion(){
		if(gridForm.getSelectionModel().hasSelection() && childGridForm.getSelectionModel().hasSelection()){
			var record = childGridForm.getSelectionModel().getSelected();
			var id = record.get('id');
			Ext.Ajax.request({
				url : '${ctx}/app_version/publish.do',
				method : 'POST',
				params: { appId : id },
				success : function(response,request){
					var jsonObj = eval('(' + response.responseText + ')');
					Ext.MessageBox.hide();
					Ext.MessageBox.alert("提示",jsonObj.msg);
					versionStore.reload();
				},
				failure : function(response,request){
						Ext.MessageBox.hide();
						Ext.MessageBox.alert("提示","删除失败");
				}
			});
		}
	}

});

</script>
</head>
<body>
<div style="width: 100%; height: 100%; padding: 0,0,0,0">
	<div id="ToolBar"></div>
	<div id="SearchFormDiv"></div>
	<div id="versionBar"></div>
	<div id="appPlatform" style="float: left; clear: left; width: 50%; height: 100%">
		<div id="parentDataGrild"></div>
		<div id="add_menu"></div>
		<div id="mod_menu"></div>
	</div>
	<div id="appPackage" style="float: left; clear: right; width: 50%; height: 100%">
		<div id="childDataGrild"></div>
	</div>
</div>
</body>
</html>