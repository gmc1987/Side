<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageMode" value="${pageMode}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="菜单管理" />
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<script type="text/javascript">
Ext.onReady(function(){
	
	var intPageSize = 10;
	
	var dsCurStatus = new Ext.data.JsonStore({ 
 		url:'${ctx}/menu/getMenuOrder.do',
		autoLoad:true,  
		root:'menuOrders',
 		fields:[{name:'menuOrder', mapping:'orderValue'},
 		        {name:'displayOrder', mapping:'display'}]
	}); 
	
	var parentMenuStore = new Ext.data.JsonStore({ 
 		url:'${ctx}/menu/getParentMenu.do',
		autoLoad:true,  
		root:'parentMenus',
 		fields:[{name:'parentMenuId', mapping:'mid'},
 		        {name:'parentMenuName', mapping:'menuName'}]
	}); 
	
	var menuOrder = new Ext.form.ComboBox({
		id : 'menuOrder',
		name : 'menuOrder',
		store : dsCurStatus,
		valueField : 'menuOrder',
		displayField : 'displayOrder',
		fieldLabel : '菜单顺序',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : true,
		mode : 'local'
	}); 
	
	var menuChildOrder = new Ext.form.ComboBox({
		id : 'menuChildOrder',
		name : 'menuChildOrder',
		store : dsCurStatus,
		valueField : 'menuOrder',
		displayField : 'displayOrder',
		fieldLabel : '菜单顺序',
		emptyText : '请选择...',
		triggerAction : 'all',
		width : 120,
		editable : true,
		mode : 'local'
	}); 
	
	var mod_menuOrder = new Ext.form.ComboBox({
		id : 'modmenuOrder',
		name : 'modmenuOrder',
		store : dsCurStatus,
		valueField : 'menuOrder',
		displayField : 'displayOrder',
		fieldLabel : '菜单顺序',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : true,
		mode : 'local'
	}); 
	
	var parentMenus = new Ext.form.ComboBox({
		id : 'parentMenus',
		name : 'parentMenus',
		hiddenName : 'parentId',
		store : parentMenuStore,
		valueField : 'parentMenuId',
		displayField : 'parentMenuName',
		fieldLabel : '父级菜单',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local'
	}); 
	
	var addBtn = new Ext.Button({
		text : '新增',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-add.png',
	});
	
	var addChildMenuBtn = new Ext.Button({
		text : '新增子菜单',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-add.png',
	});

	var modBtn = new Ext.Button({
		text : '修改',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-editor.png',
	});

	var delBtn = new Ext.Button({
		text : '删除',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-delete.png',
	});
	
  	var store = new Ext.data.JsonStore({ 
 		url:'${ctx}/menu/list.do',
//		autoLoad:true,  
 		totalProperty:'pageMode.count',
		root:'pageMode.records',
 		fields:[{name:'mid', mapping:'mid'},
		        {name:'menuName', mapping:'menuName'},
		        {name:'menuUrl', mapping:'menuUrl'},
		        {name:'menuOrder', mapping:'menuOrder'},
		        {name:'createUser', mapping:'createUser'},
				{name:'createDate', type:'date', mapping:'createDate.time', dateFormat :'time'}]
	}); 
  	
 	var colModel = new Ext.grid.ColumnModel([{
		header:'uuid',
		width:100,
		sortable : true,
		dataIndex : 'mid',
		hidden : true
	},{
		header : "菜单名称",
		width : 80,
		sortable : true,
		dataIndex : 'menuName',
	},{
		header : "菜单地址",
		width : 230,
		sortable : true,
		dataIndex : 'menuUrl'
	},{
		header : "菜单顺序",
		width : 80,
		sortable : true,
		dataIndex : 'menuOrder'
	},{
		header : "创建人",
		width : 80,
		sortable : true,
		dataIndex : 'createUser'
	},{
		header:'创建时间',
		width:120,
		sortable:true,
		dataIndex:'createDate',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}]);  
	
	//数据集
	var gridForm = new Ext.grid.GridPanel({
		id : 'opercode-grid',
		store : store,
		cm : colModel,  
		viewConfig : {
			forceFit : false
		},
		tbar : [addBtn, '-', addChildMenuBtn, '-', modBtn, '-', delBtn],
		bbar : new Ext.PagingToolbar({
			pageSize : intPageSize,
			store : store,
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
		renderTo : 'dataGrild'
	});
	
	store.load({
    	params:{
  			start:0,
			limit:intPageSize
		}
    });
	
	//addfrom
	var addForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '菜单新增',
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
					xtype : 'hidden',
					name : 'parentMenuId'
					}]
				},{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '菜单名称',
					name : 'menuName',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '菜单名称不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '菜单地址',
						name : 'menuUrl',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)'
						}]
				},{
					layout : 'form',
					border : true,
					items : [menuOrder]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					addForm.form.submit({
						url : '${ctx}/menu/save.do',
						method : 'POST',
						success : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								addWin.hide();
								store.load();
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
	
	//addChildfrom
	var addChildForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '子菜单新增',
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
					xtype : 'hidden',
					name : 'parentMenuId'
					}]
				},{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '菜单名称',
					name : 'menuName',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '菜单名称不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '菜单地址',
						name : 'menuUrl',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)'
						}]
				},{
					layout : 'form',
					border : true,
					items : [menuChildOrder]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					addChildForm.form.submit({
						url : '${ctx}/menu/save.do',
						method : 'POST',
						success : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								addChildWin.hide();
								store.load();
						},
						failure : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
						}
					});
				}
			},{
				text : '取消',
				handler : function(){
					addChildWin.hide();
				}
			}]
	});
	
	//modfrom
	var modForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '菜单修改',
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
					xtype : 'hidden',
					name : 'mid'
					}]
				},{
					layout : 'form',
					border : true,
					items:[parentMenus]
				},{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '菜单名称',
					name : 'menuName',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '菜单名称不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '菜单地址',
						name : 'menuUrl',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)'
						}]
				},{
					layout : 'form',
					border : true,
					items : [mod_menuOrder]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					modForm.form.submit({
						url : '${ctx}/menu/update.do',
						method : 'POST',
						success : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								modWin.hide();
								store.load();
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
	addBtn.on('click', function(){
		if(!addWin){
			addWin = new Ext.Window({
				el : 'add_menu',
				layout : 'fit',
				closeAction : 'hide',
				width : 380,
				height : 200,
				modal : true,
				collapsible : true,
				constrain : true,
				resizable : false,
				items : addForm
			});
		}
		addForm.getForm().reset();
		addWin.show(this);
	});
	
	//添加子菜单
	var addChildWin = null;
	addChildMenuBtn.on('click', function(){
		if(gridForm.getSelectionModel().hasSelection()){
			var record = gridForm.getSelectionModel().getSelected();
			if(!addChildWin){
				addChildWin = new Ext.Window({
					el : 'add_childMenu',
					layout : 'fit',
					closeAction : 'hide',
					width : 380,
					height : 200,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : addChildForm
				});
			}
			addChildWin.on('show', function() {
				var parentId = record.get('mid');
 				var aForm = addChildForm.getForm();
 				menuChildOrder.reset();
				aForm.reset();
				aForm.findField('parentMenuId').setValue(parentId);
			}); 
			addChildWin.show(this);
		}else{
			Ext.MessageBox.alert("错误","没有选中修改项");
			return;
		}
	});
	
	//修改
	var modWin = null;
	modBtn.on('click', function(){
		if(gridForm.getSelectionModel().hasSelection()){
			var record = gridForm.getSelectionModel().getSelected();
			if(!modWin){
				modWin = new Ext.Window({
					el : 'mod_menu',
					layout : 'fit',
					closeAction : 'hide',
					width : 380,
					height : 225,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : modForm
				});
			}
			modWin.on('show', function() {
				
 				var mid = record.get("mid");
				var menuName = record.get("menuName");
				var menuUrl = record.get("menuUrl");
				var menuOrder = record.get("menuOrder");
				var parentIds = null;
				
				Ext.Ajax.request({
					url : '${ctx}/menu/getParentMenus.do',
					method : 'POST',
					params: { mid : mid },
					success : function(response,request){
						var jsonObj = eval('(' + response.responseText + ')');
						parentIds = jsonObj.msg;
						var mForm = modForm.getForm();
						mForm.reset();
						mForm.findField('mid').setValue(mid);
						mForm.findField('menuName').setValue(menuName);
						mForm.findField('menuUrl').setValue(menuUrl);
						parentMenus.setValue(parentIds);
						mod_menuOrder.setValue(menuOrder);
					},
					failure : function(response,request){
						var jsonObj = eval('(' + response.responseText + ')');
						parentIds = jsonObj.msg;
						var mForm = modForm.getForm();
						mForm.reset();
						mForm.findField('mid').setValue(mid);
						mForm.findField('menuName').setValue(menuName);
						mForm.findField('menuUrl').setValue(menuUrl);
						parentMenus.setValue(parentIds);
						mod_menuOrder.setValue(menuOrder);
					}
				});
			}); 
			modWin.show(this);
		}else{
			Ext.MessageBox.alert("错误","没有选中修改项");
			return;
		}
	});
	
	//删除
	delBtn.on('click', function(){
		if(gridForm.getSelectionModel().hasSelection()){
			Ext.MessageBox.confirm('提示', '确认要删除该记录吗?', submitDel);
			function submitDel(btn){
				if(btn == 'yes'){
					Ext.MessageBox.wait("数据正在处理中,请稍後...", "提交");
					var record = gridForm.getSelectionModel().getSelected();
					var id = record.get('mid');
					Ext.Ajax.request({
						url : '${ctx}/menu/delete.do',
						method : 'POST',
						params: { mid : id },
						success : function(response,request){
							var jsonObj = eval('(' + response.responseText + ')');
							Ext.MessageBox.hide();
							Ext.MessageBox.alert("提示",jsonObj.msg);
							store.load();
						},
						failure : function(response,request){
								Ext.MessageBox.hide();
								Ext.MessageBox.alert("提示","删除失败");
						}
					});
				}
			}
		}else{
			Ext.MessageBox.alert("错误","没有选中删除项");
			return;
		}
	});

});

</script>
</head>
<body>
	<div id="dataGrild"></div>
	<div id="add_menu"></div>
	<div id="add_childMenu"></div>
	<div id="mod_menu"></div>
</body>
</html>