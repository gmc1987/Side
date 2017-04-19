<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageMode" value="${pageMode}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="商户产品类型管理" />
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<script type="text/javascript">
Ext.onReady(function(){
	
	var intPageSize = 10;
	
	var parentProductStore = new Ext.data.JsonStore({ 
 		url:'${ctx}/productTypes/getProductType.do',
		autoLoad:true,  
		root:'result',
 		fields:[{name:'productTypeId', mapping:'productTypeId'},
 		        {name:'typeName', mapping:'typeName'}]
	}); 
	
	var parentProducts = new Ext.form.ComboBox({
		id : 'parentTypes',
		name : 'parentTypes',
		hiddenName : 'parentTypeId',
		store : parentProductStore,
		valueField : 'productTypeId',
		displayField : 'typeName',
		fieldLabel : '父级产品类型',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local'
	});
	
	//工具栏，按钮
 	var myToolbar = new Ext.Toolbar();
	myToolbar.render('parentToolBar');
	
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
	
	var searchForm = new Ext.form.FormPanel({
		id : 'searchForm',
		renderTo : 'parentSearchFormDiv',
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
				columns : 6
			},
			items : [{
				layout : 'form',
				items : [{
					id : 'typeCode',
					xtype : 'textfield',
					fieldLabel : '产品类型编码',
					name : 'productTypeCode',
					width : 100,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [{
					id : 'typeName', 
					xtype : 'textfield',
					fieldLabel : '产品类型名称',
					name : 'productTypeName',
					width : 100,
					maxLength:100
					}]
			}]
		}]
	});
	//显示工具栏
	myToolbar.doLayout(); 
	
	var addBtn = new Ext.Button({
		text : '新增产品类型',
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
 		url:'${ctx}/productTypes/list.do?isParent=1',
//		autoLoad:true,  
 		totalProperty:'pageMode.count',
		root:'pageMode.records',
 		fields:[{name:'productTypeId', mapping:'productTypeId'},
		        {name:'typeCode', mapping:'typeCode'},
		        {name:'typeName', mapping:'typeName'},
		        {name:'orderid', mapping:'orderid'},
		        {name:'OperationUser', mapping:'operationUser'},
				{name:'createDate', type:'date', mapping:'createDate.time', dateFormat :'time'}]
	}); 
  	
 	var colModel = new Ext.grid.ColumnModel([{
		header:'uuid',
		width:100,
		sortable : true,
		dataIndex : 'productTypeId',
		hidden : true
	},{
		header : "类型编码",
		width : 80,
		sortable : true,
		dataIndex : 'typeCode',
	},{
		header : "类型名称",
		width : 80,
		sortable : true,
		dataIndex : 'typeName'
	},{
		header : "顺序",
		width : 80,
		sortable : true,
		dataIndex : 'orderid',
		hidden : true
	},{
		header : "创建人",
		width : 80,
		sortable : true,
		dataIndex : 'OperationUser'
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
		tbar : [addBtn, '-', modBtn, '-', delBtn],
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
		renderTo : 'parentDataGrild'
	});
	
	store.load({
    	params:{
  			start:0,
			limit:intPageSize
		}
    });
	
	gridForm.addListener('cellclick',chooseParent); 
	
	//查询
	function search(){
		var code = searchForm.getForm().findField("productTypeCode").getValue();
		var name = searchForm.getForm().findField("productTypeName").getValue();
		gridForm.getStore().load({
			proxy : new Ext.data.HttpProxy({
				url : "${ctx}/productTypes/list.do?isParent=1",//获取数据的后台地址
			}),
			params:{
				productTypeCode : code,
				productTypeName : name,
				start : 0,
				limit : intPageSize
			}
		}); 
	}
	
	//重置
	function reset(){
		searchForm.getForm().findField("productTypeCode").setValue("");
		searchForm.getForm().findField("productTypeName").setValue("");
	}
	
	//addfrom
	var addForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '类型新增',
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
					fieldLabel : '类型名称',
					name : 'typeName',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '类型名称不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '类型编码',
						name : 'typeCode',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)'
						}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					addForm.form.submit({
						url : '${ctx}/productTypes/save.do',
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
	
	//modfrom
	var modForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '产品类型修改',
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
					name : 'productTypeId'
					}]
				},{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '产品类型名称',
					name : 'typeName',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '产品类型名称不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '产品类型编码',
						name : 'typeCode',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)'
						}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					modForm.form.submit({
						url : '${ctx}/productTypes/update.do',
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
				height : 180,
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
					height : 180,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : modForm
				});
			}
			modWin.on('show', function() {
				
 				var tid = record.get("productTypeId");
				var typeName = record.get("typeName");
				var typeCode = record.get("typeCode");
				
				var mForm = modForm.getForm();
				mForm.reset();
				mForm.findField('productTypeId').setValue(tid);
				mForm.findField('typeName').setValue(typeName);
				mForm.findField('typeCode').setValue(typeCode);
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
					var id = record.get('productTypeId');
					Ext.Ajax.request({
						url : '${ctx}/productTypes/delete.do',
						method : 'POST',
						params: { productTypeId : id },
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
	
	/*------------------------------------分割线-----------------------------------------------*/
	
	var intChildPageSize = 10;
	
	//工具栏，按钮
 	var childToolbar = new Ext.Toolbar();
 	childToolbar.render('childtoolBar');
	
 	childToolbar.addButton({
		text: '查询子类', 
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-find.png',
		handler: function(o, e) {
			childSearch();
		}
	});
 	childToolbar.addButton({
		text: '重置', 
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/view-refresh.png',
		handler: function(o, e) {
			childReset();
		}
	});
	
	var childSearchForm = new Ext.form.FormPanel({
		id : 'childSearchForm',
		renderTo : 'childSearchFormDiv',
		frame : true,
		labelAlign : 'right',
		labelWidth : 120,
		width : '100%',	
		waitMsgTarget : true,
		items : [{
			layout : 'table',
			border : true,
			labelSeparator : ':',
			layoutConfig : {
				columns : 6
			},
			items : [{
				layout : 'form',
				items : [{
					id : 'childCode',
					xtype : 'textfield',
					fieldLabel : '子类产品类型编码',
					name : 'childCode',
					width : 100,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [{
					id : 'childName', 
					xtype : 'textfield',
					fieldLabel : '子类产品类型名称',
					name : 'childName',
					width : 100,
					maxLength:100
					}]
			}]
		}]
	});
	//显示工具栏
	childToolbar.doLayout(); 
	
	var addChildMenuBtn = new Ext.Button({
		text : '新增子类型',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-add.png',
	});

	var modChildBtn = new Ext.Button({
		text : '修改',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-editor.png',
	});

	var delChildBtn = new Ext.Button({
		text : '删除',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-delete.png',
	});
	
  	var childStore = new Ext.data.JsonStore({ 
 		url:'${ctx}/productTypes/list.do?isParent=0',
//		autoLoad:true,  
 		totalProperty:'pageMode.count',
		root:'pageMode.records',
 		fields:[{name:'productTypeId', mapping:'productTypeId'},
		        {name:'typeCode', mapping:'typeCode'},
		        {name:'typeName', mapping:'typeName'},
		        {name:'orderid', mapping:'orderid'},
		        {name:'OperationUser', mapping:'operationUser'},
				{name:'createDate', type:'date', mapping:'createDate.time', dateFormat :'time'}]
	}); 
  	
 	var childColModel = new Ext.grid.ColumnModel([{
		header:'uuid',
		width:100,
		sortable : true,
		dataIndex : 'productTypeId',
		hidden : true
	},{
		header : "子类编码",
		width : 80,
		sortable : true,
		dataIndex : 'typeCode',
	},{
		header : "子类名称",
		width : 80,
		sortable : true,
		dataIndex : 'typeName'
	},{
		header : "顺序",
		width : 80,
		sortable : true,
		dataIndex : 'orderid',
		hidden : true
	},{
		header : "创建人",
		width : 80,
		sortable : true,
		dataIndex : 'OperationUser'
	},{
		header:'创建时间',
		width:120,
		sortable:true,
		dataIndex:'createDate',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}]);  
	
	//数据集
	var childGridForm = new Ext.grid.GridPanel({
		id : 'opercode-grid',
		store : childStore,
		cm : childColModel,  
		viewConfig : {
			forceFit : false
		},
		tbar : [addChildMenuBtn, '-', modChildBtn, '-', delChildBtn],
		bbar : new Ext.PagingToolbar({
			pageSize : intChildPageSize,
			store : childStore,
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
	
	childStore.load({
    	params:{
  			start:0,
			limit:intChildPageSize
		}
    });
	
	//查询
	function childSearch(){
		var code = childSearchForm.getForm().findField("childCode").getValue();
		var name = childSearchForm.getForm().findField("childName").getValue();
		var record = gridForm.getSelectionModel().getSelected();
		var parentTypeId = record.get("productTypeId");
		childGridForm.getStore().load({
			proxy : new Ext.data.HttpProxy({
				url : "${ctx}/productTypes/list.do?isParent=0",//获取数据的后台地址
			}),
			params:{
				parentId : parentTypeId,
				productTypeCode : code,
				productTypeName : name,
				start : 0,
				limit : intChildPageSize
			}
		}); 
	}
	
	//重置
	function childReset(){
		childSearchForm.getForm().findField("childCode").setValue("");
		childSearchForm.getForm().findField("childName").setValue("");
	}
	
	function chooseParent(gridForm, rowIndex, columnIndex, e){
		var record = gridForm.getSelectionModel().getSelected();
		var parentTypeId = record.get("productTypeId");
		childGridForm.getStore().load({
			proxy : new Ext.data.HttpProxy({
				url : "${ctx}/productTypes/list.do?isParent=0",//获取数据的后台地址
			}),
			params:{
				parentId : parentTypeId,
				start : 0,
				limit : intChildPageSize
			}
		}); 
	}
	
	//addChildfrom
	var addChildForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '子类型新增',
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
					name : 'productTypeId'
					}]
				},{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '类型名称',
					name : 'typeName',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '类型名称不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '类型编码',
						name : 'typeCode',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)'
						}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					addChildForm.form.submit({
						url : '${ctx}/productTypes/save.do',
						method : 'POST',
						success : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								addChildWin.hide();
								var record = gridForm.getSelectionModel().getSelected();
								var productTypeId = record.get('productTypeId');
								childGridForm.getStore().load({
									proxy : new Ext.data.HttpProxy({
										url : "${ctx}/productTypes/list.do?isParent=0",//获取数据的后台地址
									}),
									params:{
										parentId : productTypeId,
										start : 0,
										limit : intChildPageSize
									}
								});
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
	var childModForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '产品类型修改',
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
					name : 'productTypeId'
					}]
				},{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '产品类型名称',
					name : 'typeName',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '产品类型名称不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '产品类型编码',
						name : 'typeCode',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)'
						}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					childModForm.form.submit({
						url : '${ctx}/productTypes/update.do',
						method : 'POST',
						success : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								childModWin.hide();
								var record = gridForm.getSelectionModel().getSelected();
								var productTypeId = record.get('productTypeId');
								childGridForm.getStore().load({
									proxy : new Ext.data.HttpProxy({
										url : "${ctx}/productTypes/list.do?isParent=0",//获取数据的后台地址
									}),
									params:{
										parentId : productTypeId,
										start : 0,
										limit : intChildPageSize
									}
								});
						},
						failure : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
						}
					});
				}
			},{
				text : '取消',
				handler : function(){
					childModWin.hide();
				}
			}]
	});
	
	//添加子产品类型
	var addChildWin = null;
	addChildMenuBtn.on('click', function(){
		if(gridForm.getSelectionModel().hasSelection()){
			var record = gridForm.getSelectionModel().getSelected();
			if(!addChildWin){
				addChildWin = new Ext.Window({
					el : 'add_new',
					layout : 'fit',
					closeAction : 'hide',
					width : 380,
					height : 180,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : addChildForm
				});
			}
			addChildWin.on('show', function() {
				var parentId = record.get('productTypeId');
 				var aForm = addChildForm.getForm();
				aForm.reset();
				aForm.findField('productTypeId').setValue(parentId);
			}); 
			addChildWin.show(this);
		}else{
			Ext.MessageBox.alert("错误","没有选中修改项");
			return;
		}
	});
	
	//修改
	var childModWin = null;
	modChildBtn.on('click', function(){
		if(childGridForm.getSelectionModel().hasSelection()){
			var record = childGridForm.getSelectionModel().getSelected();
			if(!childModWin){
				childModWin = new Ext.Window({
					el : 'mod_child',
					layout : 'fit',
					closeAction : 'hide',
					width : 380,
					height : 180,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : childModForm
				});
			}
			childModWin.on('show', function() {
				
 				var tid = record.get("productTypeId");
				var typeName = record.get("typeName");
				var typeCode = record.get("typeCode");
				
				var mForm = childModForm.getForm();
				mForm.reset();
				mForm.findField('productTypeId').setValue(tid);
				mForm.findField('typeName').setValue(typeName);
				mForm.findField('typeCode').setValue(typeCode);
			}); 
			childModWin.show(this);
		}else{
			Ext.MessageBox.alert("错误","没有选中修改项");
			return;
		}
	});
	
	//删除
	delChildBtn.on('click', function(){
		if(childGridForm.getSelectionModel().hasSelection()){
			Ext.MessageBox.confirm('提示', '确认要删除该记录吗?', submitDel);
			function submitDel(btn){
				if(btn == 'yes'){
					Ext.MessageBox.wait("数据正在处理中,请稍後...", "提交");
					var record = childGridForm.getSelectionModel().getSelected();
					var id = record.get('productTypeId');
					Ext.Ajax.request({
						url : '${ctx}/productTypes/delete.do',
						method : 'POST',
						params: { productTypeId : id },
						success : function(response,request){
							var jsonObj = eval('(' + response.responseText + ')');
							Ext.MessageBox.hide();
							Ext.MessageBox.alert("提示",jsonObj.msg);
							var record = gridForm.getSelectionModel().getSelected();
							var productTypeId = record.get('productTypeId');
							childGridForm.getStore().load({
								proxy : new Ext.data.HttpProxy({
									url : "${ctx}/productTypes/list.do?isParent=0",//获取数据的后台地址
								}),
								params:{
									parentId : productTypeId,
									start : 0,
									limit : intChildPageSize
								}
							});
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
<div style="width: 100%; height: 100%; padding: 0,0,0,0">
	<div id="parentProduct" style="float: left; clear: left; width: 50%; height: 100%">
		<div id="parentToolBar"></div>
		<div id="parentSearchFormDiv"></div>
		<div id="parentDataGrild"></div>
		<div id="add_menu"></div>
		<div id="mod_menu"></div>
	</div>
	<div id="childProduct" style="float: left; clear: right; width: 50%; height: 100%">
		<div id="childtoolBar"></div>
		<div id="childSearchFormDiv"></div>
		<div id="childDataGrild"></div>
		<div id="add_new"></div>
		<div id="mod_child"></div>
	</div>
</div>
</body>
</html>