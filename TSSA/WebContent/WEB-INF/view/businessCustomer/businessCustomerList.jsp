<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageTree" value="true"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="商户员工管理" />
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle}</title>
<script type="text/javascript">
Ext.onReady(function(){
	
	var intPageSize = 10;
	
	var status_src = [['0', '禁用'], ['1', '启用']];
	var dsUserStatus = new Ext.data.SimpleStore({
		fields : ['statusValue', 'statusDisplay'],
		data : status_src
	});
	
	var addUserStatus = new Ext.form.ComboBox({
		id : 'status',
		name : 'status',
		hiddenName : "userStatus",
		store : dsUserStatus,
		valueField : 'statusValue',
		displayField : 'statusDisplay',
		fieldLabel : '用户状态',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local'
	});
	
	var modUserStatus = new Ext.form.ComboBox({
		id : 'modStatus',
		name : 'modStatus',
		hiddenName : "userStatus",
		store : dsUserStatus,
		valueField : 'statusValue',
		displayField : 'statusDisplay',
		fieldLabel : '用户状态',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local'
	});
	
	var dsCurStatus = new Ext.data.JsonStore({ 
 		url:'${ctx}/businessCustomer/getRole.do',
		autoLoad:true,  
		root:'result',
 		fields:[{name:'rid', mapping:'roleId'},
		        {name:'roleName', mapping:'roleValueName'},
		        {name:'roleValue', mapping:'roleValue'}]
	}); 
	
	var userRole = new Ext.form.ComboBox({
		id : 'role',
		name : 'role',
		store : dsCurStatus,
		valueField : 'rid',
		displayField : 'roleName',
		fieldLabel : '用户角色',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local'
	}); 
	
	var addSelectWin = null;
	var modSelectWin = null;
	
	var add_userRole = new Ext.form.ComboBox({
		id : 'roleId',
		name : 'roleId',
		hiddenName : 'myRoleValue',
		store : dsCurStatus,
		valueField : 'rid',
		displayField : 'roleName',
		fieldLabel : '用户角色',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local',
		listeners:{
			select : function(ProjectCombox, record,index){    
				var aForm = addForm.getForm();
				if(dsCurStatus.getAt(index).get('roleValue') == 3){
					var store = new Ext.data.JsonStore({ 
				 		url:'${ctx}/cooperation/list.do',
	//					autoLoad:true,  
				 		totalProperty:'pageMode.count',
						root:'pageMode.records',
				 		fields:[{name:'cooperId', mapping:'cooperId'},
						        {name:'cooperCode', mapping:'cooperCode'},
						        {name:'cooperName', mapping:'cooperName'},
						        {name:'cooperType', mapping:'productTypeId.typeCode'},
						        {name:'cooperTypeName', mapping:'productTypeId.typeName'},
						        {name:'address', mapping:'address'},
						        {name:'tel1', mapping:'tel1'},
						        {name:'tel2', mapping:'tel2'},
								{name:'businessLicense', mapping:'businessLicense'},
								{name:'pictureUrl', mapping:'pictureUrl'},
								{name:'account', mapping:'account'},
								{name:'allianceDate', type:'date', mapping:'allianceDate.time', dateFormat :'time'}],
					}); 
				  	
				  	 store.load({
				  		params:{
				  			start:0,
							limit:intPageSize
						}
				  	}); 
				  	 
				  	var colModel = new Ext.grid.ColumnModel([{
						header:'uuid',
						width:100,
						sortable : true,
						dataIndex : 'cooperId',
						hidden : true
					},{
						header : "商户编号",
						width : 80,
						sortable : true,
						dataIndex : 'cooperCode',
					},{
						header : "商户名称",
						width : 80,
						sortable : true,
						dataIndex : 'cooperName',
					},{
						header : "行业编码",
						width : 80,
						sortable : true,
						dataIndex : 'cooperType',
						hidden : true
					},{
						header : "行业类型",
						width : 80,
						sortable : true,
						dataIndex : 'cooperTypeName'
					},{
						header:'银行帐号',
						width:80,
						sortable:true,
						dataIndex:'account'
					},{
						header:'联系电话1',
						width:80,
						sortable:true,
						dataIndex:'tel1'
					},{
						header:'联系电话2',
						width:80,
						sortable:true,
						dataIndex:'tel2'
					},{
						header:'营业执照编号',
						width:100,
						sortable:true,
						dataIndex:'businessLicense'
					},{
						header:'地址',
						width:200,
						sortable:true,
						dataIndex:'address'
					},{
						header:'加盟时间',
						width:120,
						sortable:true,
						dataIndex:'allianceDate',
						renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
					}]);  
					
				  	var addBtn = new Ext.Button({
						text : '绑定商户',
						cls: 'x-btn-text-icon scroll-bottom', 
						icon: '${img_path_16}/actions/edit-add.png',
					});
				  	
				  	addBtn.on('click', function(){
				  		if(selectForm.getSelectionModel().hasSelection()){
							var record = selectForm.getSelectionModel().getSelected();
							var cooperationId = record.get("cooperId");
							aForm.findField('businessVendorCode').setValue(cooperationId);
							addSelectWin.hide();
				  		}else{
							Ext.MessageBox.alert("错误","没有选中修改项");
							return;
						}
				  	});
				  	
					//数据集
					var selectForm = new Ext.grid.GridPanel({
						id : 'operation-grid',
						store : store,
						cm : colModel,  
						viewConfig : {
							forceFit : false
						},
						tbar : [addBtn],
						bbar : new Ext.PagingToolbar({
							pageSize : intPageSize++,
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
					
					if(!addSelectWin){
						addSelectWin = new Ext.Window({
							el : 'add_SelectCooperation',
							layout : 'fit',
							closeAction : 'hide',
							title: "选择商户",
							width : 680,
							height : 400,
							modal : true,
							collapsible : true,
							constrain : true,
							resizable : false,
							items : selectForm
						});
					}
					addSelectWin.show(this);
				}
            }  
		}
	}); 
	
	var mod_userRole = new Ext.form.ComboBox({
		id : 'role_id',
		name : 'role_id',
		hiddenName : 'myRoleValue',
		store : dsCurStatus,
		valueField : 'rid',
		displayField : 'roleName',
		fieldLabel : '用户角色',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local',
		listeners:{
			select : function(ProjectCombox, record,index){    
				var mForm = modForm.getForm();
				if(dsCurStatus.getAt(index).get('roleValue') == 3){
					var store = new Ext.data.JsonStore({ 
				 		url:'${ctx}/cooperation/list.do',
	//					autoLoad:true,  
				 		totalProperty:'pageMode.count',
						root:'pageMode.records',
				 		fields:[{name:'cooperId', mapping:'cooperId'},
						        {name:'cooperCode', mapping:'cooperCode'},
						        {name:'cooperName', mapping:'cooperName'},
						        {name:'cooperType', mapping:'productTypeId.typeCode'},
						        {name:'cooperTypeName', mapping:'productTypeId.typeName'},
						        {name:'address', mapping:'address'},
						        {name:'tel1', mapping:'tel1'},
						        {name:'tel2', mapping:'tel2'},
								{name:'businessLicense', mapping:'businessLicense'},
								{name:'pictureUrl', mapping:'pictureUrl'},
								{name:'account', mapping:'account'},
								{name:'allianceDate', type:'date', mapping:'allianceDate.time', dateFormat :'time'}],
					}); 
				  	
				  	 store.load({
				  		params:{
				  			start:0,
							limit:intPageSize
						}
				  	}); 
				  	 
				  	var colModel = new Ext.grid.ColumnModel([{
						header:'uuid',
						width:100,
						sortable : true,
						dataIndex : 'cooperId',
						hidden : true
					},{
						header : "商户编号",
						width : 80,
						sortable : true,
						dataIndex : 'cooperCode',
					},{
						header : "商户名称",
						width : 80,
						sortable : true,
						dataIndex : 'cooperName',
					},{
						header : "行业编码",
						width : 80,
						sortable : true,
						dataIndex : 'cooperType',
						hidden : true
					},{
						header : "行业类型",
						width : 80,
						sortable : true,
						dataIndex : 'cooperTypeName'
					},{
						header:'银行帐号',
						width:80,
						sortable:true,
						dataIndex:'account'
					},{
						header:'联系电话1',
						width:80,
						sortable:true,
						dataIndex:'tel1'
					},{
						header:'联系电话2',
						width:80,
						sortable:true,
						dataIndex:'tel2'
					},{
						header:'营业执照编号',
						width:100,
						sortable:true,
						dataIndex:'businessLicense'
					},{
						header:'地址',
						width:200,
						sortable:true,
						dataIndex:'address'
					},{
						header:'加盟时间',
						width:120,
						sortable:true,
						dataIndex:'allianceDate',
						renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
					}]);  
					
				  	var addBtn = new Ext.Button({
						text : '绑定商户',
						cls: 'x-btn-text-icon scroll-bottom', 
						icon: '${img_path_16}/actions/edit-add.png',
					});
				  	
				  	addBtn.on('click', function(){
				  		if(selectForm.getSelectionModel().hasSelection()){
							var record = selectForm.getSelectionModel().getSelected();
							var cooperCode = record.get("cooperCode");
							mForm.findField('businessVendorCode').setValue(cooperCode);
							modSelectWin.hide();
				  		}else{
							Ext.MessageBox.alert("错误","没有选中修改项");
							return;
						}
				  	});
				  	
					//数据集
					var selectForm = new Ext.grid.GridPanel({
						id : 'operation-grid',
						store : store,
						cm : colModel,  
						viewConfig : {
							forceFit : false
						},
						tbar : [addBtn],
						bbar : new Ext.PagingToolbar({
							pageSize : intPageSize++,
							store : store,
							beforePageText : '第',
							afterPageText : '页,共 {0} 页',
							displayInfo : true,
							displayMsg : '{0} - {1} , 共 {2} 条',
							emptyMsg : "无记录"
						}),
						width : '100%',
						height : 520,
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
					
					if(!modSelectWin){
						modSelectWin = new Ext.Window({
							el : 'mod_SelectCooperation',
							layout : 'fit',
							closeAction : 'hide',
							title: "选择商户",
							width : 680,
							height : 400,
							modal : true,
							collapsible : true,
							constrain : true,
							resizable : false,
							items : selectForm
						});
					}
					modSelectWin.show(this);
				}
            }  
		}
	}); 
	
	//工具栏，按钮
 	var myToolbar = new Ext.Toolbar();
	myToolbar.render('toolBar');
	
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
	
	var addBtn = new Ext.Button({
		text : '新增',
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
	
	var searchForm = new Ext.form.FormPanel({
		id : 'searchForm',
		renderTo : 'searchFormDiv',
		frame : true,
		labelAlign : 'right',
		labelWidth : 60,
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
					id : 'userCode',
					xtype : 'textfield',
					fieldLabel : '用户编码',
					name : 'userCode',
					width : 100,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [{
					id : 'userName', 
					xtype : 'textfield',
					fieldLabel : '用户名称',
					name : 'userName',
					width : 100,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [userRole]
			}]
		}]
	});
	//显示工具栏
	myToolbar.doLayout(); 
  	var store = new Ext.data.JsonStore({ 
 		url:'${ctx}/businessCustomer/list.do',
		autoLoad:true,  
 		totalProperty:'count',
		root:'pageMode.records',
 		fields:[{name:'uid', mapping:'businessCustomerId'},
		        {name:'userCode', mapping:'businessCustomerCode'},
		        {name:'userName', mapping:'businessCustomerName'},
		        {name:'vendorCode', mapping:'vendorCode'},
		        {name:'mobilePhone', mapping:'mobilePhone'},
		        {name:'status', mapping:'status'},
		        {name:'roleId', mapping:'rid.roleId'},
		        {name:'roleName', mapping:'rid.roleValueName'},
		        {name:'createUser', mapping:'createUser'},
				{name:'createDate', type:'date', mapping:'createDate.time', dateFormat :'time'}]
	}); 
	
 	var colModel = new Ext.grid.ColumnModel([{
		header:'uuid',
		width:100,
		sortable : true,
		dataIndex : 'uid',
		hidden : true
	},{
		header : "用户编码",
		width : 80,
		sortable : true,
		dataIndex : 'userCode'
	},{
		header : "用户名称",
		width : 130,
		sortable : true,
		dataIndex : 'userName'
	},{
		header : "商户编码",
		width : 80,
		sortable : true,
		dataIndex : 'vendorCode'
	},{
		header : "手机号码",
		width : 130,
		sortable : true,
		dataIndex : 'mobilePhone'
	},{
		header : "用户状态",
		width : 80,
		sortable : true,
		dataIndex : 'status'
	},{
		header : "角色id",
		width : 130,
		sortable : true,
		dataIndex : 'roleId',
		hidden : true
	},{
		header : "用户角色",
		width : 130,
		sortable : true,
		dataIndex : 'roleName'
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
		width : '100%',
		height : 520,
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
	
	//查询
	function search(){
		var code = searchForm.getForm().findField("userCode").getValue();
		var name = searchForm.getForm().findField("userName").getValue();
		var roleId = userRole.getValue();
		gridForm.getStore().load({
			proxy : new Ext.data.HttpProxy({
				url : "${ctx}/businessCustomer/list.do",//获取数据的后台地址
			}),
			params:{
				userCode : code,
				userName : name,
				role : roleId,
				pageNumber : 0,
				pageSize : intPageSize
			}
		}); 
	}
	
	//重置
	function reset(){
		searchForm.getForm().findField("userCode").setValue("");
		searchForm.getForm().findField("userName").setValue("");
		userRole.reset();
	}
	
	//addfrom
	var addForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '用户新增',
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
					layout : 'form',
					border : true,
					items:[{
						xtype : 'hidden',
						name : 'businessVendorCode'
						}]
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '用户名称',
						name : 'businessCustomerName',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '用户名称不能为空'
						},{
							xtype : 'textfield',
							fieldLabel : '手机号码',
							name : 'mobilePhone',
							width : 215,
							maxLength:100,
							maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
							allowBlank : false,
					    	blankText : '手机号码不能为空'
						},{
							xtype : 'textfield',
							fieldLabel : '用户密码',
							inputType : 'password',
							name : 'password',
							width : 215,
							maxLength:100,
							maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
							allowBlank : false,
					    	blankText : '用户密码不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [addUserStatus]
				},{
					layout : 'form',
					border : true,
					items : [add_userRole]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					var values = addForm.getForm().getValues();
					addForm.form.submit({
						url : '${ctx}/businessCustomer/save.do',
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
		title : '用户修改',
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
					name : 'uid'
					}]
				},{
					layout : 'form',
					border : true,
					items:[{
						xtype : 'hidden',
						name : 'businessVendorCode'
						}]
				},{
				layout : 'form',
				border : true,
				items:[{
						xtype : 'textfield',
						fieldLabel : '用户名称',
						name : 'businessCustomerName',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '用户名称不能为空'
					},{
						xtype : 'textfield',
						fieldLabel : '手机号码',
						name : 'mobilePhone',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '手机号码不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [modUserStatus]
				},{
					layout : 'form',
					border : true,
					items : [mod_userRole]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					var values = modForm.getForm().getValues();
					modForm.form.submit({
						url : '${ctx}/businessCustomer/update.do',
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
				el : 'add_BusinessUser',
				layout : 'fit',
				closeAction : 'hide',
				width : 380,
				height : 250,
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
					el : 'mod_BusinessUser',
					layout : 'fit',
					closeAction : 'hide',
					width : 380,
					height : 230,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : modForm
				});
			}
			modWin.on('show', function() {
				
 				var uid = record.get("uid");
				var vendorCode = record.get("vendorCode");
				var userName = record.get("userName");
				var roleId = record.get("roleId");
				var mobilePhone = record.get("mobilePhone");
				var status = record.get("status");
				
 				var mForm = modForm.getForm();
				mForm.reset();
				mForm.findField('uid').setValue(uid);
				mForm.findField('businessVendorCode').setValue(vendorCode);
				mForm.findField('businessCustomerName').setValue(userName);
				mForm.findField('mobilePhone').setValue(mobilePhone);
				modUserStatus.setValue(status);
				mod_userRole.setValue(roleId);
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
					var id = record.get('uid');
					Ext.Ajax.request({
						url : '${ctx}/businessCustomer/delete.do',
						method : 'POST',
						params: { uid : id },
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
	<div id="toolBar"></div>
	<div id="searchFormDiv"></div>
	<div id="dataGrild"></div>
	<div id="add_BusinessUser"></div>
	<div id="mod_BusinessUser"></div>
	<div id="add_SelectCooperation"></div>
	<div id="mod_SelectCooperation"></div>
</body>
</html>