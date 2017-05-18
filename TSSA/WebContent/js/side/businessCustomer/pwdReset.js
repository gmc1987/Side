Ext.onReady(function(){
	
	var initPageSize = 10;
	
	var resetBtn = new Ext.Button({
		text : '重置密码',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: img_path_16+'/actions/edit-editor.png',
	});

	var toolBar = new Ext.Toolbar();
	toolBar.render('toolbar');
	
	toolBar.addButton({
		text: '查询', 
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: img_path_16+'/actions/edit-find.png',
		handler: function(o, e) {
			search();
		}
	});
	
	toolBar.addButton({
		text: '重置', 
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: img_path_16+'/actions/view-refresh.png',
		handler: function(o, e) {
			reset();
		}
	});
	
	//查询表单
	var searchForm = new Ext.form.FormPanel({
		id : 'search',
		renderTo : 'searchForm',
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
					id : 'mobilePhone',
					xtype : 'textfield',
					fieldLabel : '手机号码',
					name : 'mobilePhone',
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
			}]
		}]
	});
	
	//显示工具栏
	toolBar.doLayout(); 
  	var store = new Ext.data.JsonStore({ 
 		url:ctx+'/pwdReset/getList.do',
		autoLoad:true,  
 		totalProperty:'pageMode.count',
		root:'pageMode.records',
 		fields:[{name:'recordId', mapping:'recordId'},
		        {name:'resetCustomerCode', mapping:'resetCustomerCode'},
		        {name:'resetCustomerName', mapping:'resetCustomerName'},
		        {name:'auditCustomerCode', mapping:'auditCustomerCode'},
		        {name:'mobilePhone', mapping:'mobilePhone'},
		        {name:'entryDate', mapping:'entryDate'},
		        {name:'auditDate', mapping:'auditDate'},
		        {name:'serialStatus', mapping:'serialStatus'},
		        {name:'remark', mapping:'remark'}]
	}); 
  	
  	var colModel = new Ext.grid.ColumnModel([{
		header:'记录流水',
		width:100,
		sortable : true,
		dataIndex : 'recordId'
	},{
		header : "用户编码",
		width : 80,
		sortable : true,
		dataIndex : 'resetCustomerCode'
	},{
		header : "用户名称",
		width : 80,
		sortable : true,
		dataIndex : 'resetCustomerName'
	},{
		header : "用户手机",
		width : 80,
		sortable : true,
		dataIndex : 'mobilePhone'
	},{
		header : "录入日期",
		width : 130,
		sortable : true,
		dataIndex : 'entryDate'
	},{
		header : "审批用户编码",
		width : 80,
		sortable : true,
		dataIndex : 'auditCustomerCode'
	},{
		header : "审批日期",
		width : 130,
		sortable : true,
		dataIndex : 'auditDate'
	},{
		header : "流水状态",
		width : 80,
		sortable : true,
		dataIndex : 'serialStatus',
		renderer : function(value){
			if(value == "0"){
				return "待审批";
			}else if(value == "1"){
				return "已审批";
			}else{
				return "待审批";
			}
		}
	},{
		header : "申请备注",
		width : 120,
		sortable : true,
		dataIndex : 'remark'
	}]);  
  	
	//数据集
	var gridForm = new Ext.grid.GridPanel({
		id : 'opercode-grid',
		store : store,
		cm : colModel,  
		viewConfig : {
			forceFit : false
		},
		tbar : [resetBtn],
		bbar : new Ext.PagingToolbar({
			pageSize : initPageSize,
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
		renderTo : 'recordGrid'
	});
	
	//查询
	function search(){
		var mobile = searchForm.getForm().findField("mobilePhone").getValue();
		var name = searchForm.getForm().findField("userName").getValue();
		var roleId = userRole.getValue();
		gridForm.getStore().load({
			proxy : new Ext.data.HttpProxy({
				url : ctx+"/pwdReset/getList.do",//获取数据的后台地址
			}),
			params:{
				mobilePhone : mobile,
				userName : name,
				role : roleId,
				pageNumber : 0,
				pageSize : initPageSize
			}
		}); 
	}
	
	//重置
	function reset(){
		searchForm.getForm().findField("mobilePhone").setValue("");
		searchForm.getForm().findField("userName").setValue("");
		userRole.reset();
	}
	
	//resetfrom
	var resetForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '密码重置',
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
						name : 'resetCustomerCode'
						}]
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
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
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					var values = resetForm.getForm().getValues();
					resetForm.form.submit({
						url : ctx+'/pwdReset/reset.do',
						method : 'POST',
						success : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								resetWin.hide();
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
					resetWin.hide();
				}
			}]
	});
	
	var resetWin = null;
	resetBtn.on('click', function(){
		if(gridForm.getSelectionModel().hasSelection()){
			var record = gridForm.getSelectionModel().getSelected();
			var serialStatus = record.get("serialStatus");
			if(serialStatus == "1"){
				Ext.Msg.alert("错误", "记录已审批");
				return;
			}
			if(!resetWin){
				resetWin = new Ext.Window({
					el : 'resetWin',
					layout : 'fit',
					closeAction : 'hide',
					width : 380,
					height : 150,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : resetForm
				});
			}
			resetForm.getForm().reset();
			resetWin.on('show', function(){
				var customerCode = record.get("resetCustomerCode");
				var mForm = resetForm.getForm();
				mForm.reset();
				mForm.findField('resetCustomerCode').setValue(customerCode);
			});
			resetWin.show(this);
		}else{
			Ext.Msg.alert('错误', '请选择审批记录');
		}
	});
	
});