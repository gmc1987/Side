/**
 * @see 派位系统设置
 */
Ext.onReady(function(){
	
	/**
	 * 界面默认最大记录条数
	 */
	var iPageSize = 10;
	
	/**
	 * 是否循环
	 */
	var type_src = [['0', '否'], ['1', '是']];
	var dsCurStatus = new Ext.data.SimpleStore({
		fields : ['cycleValue', 'cycleName'],
		data : type_src
	});
	
	/**
	 * 新增按钮
	 */
	var addButton = new Ext.Button({
		text : '新增',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: ctx+'/images/icon/Nuvola/16/actions/edit-add.png'
	});
	
	/**
	 * 修改按钮
	 */
	var editButton = new Ext.Button({
		text : '修改',
		cls : 'x-btn-text-icon scroll-bottom',
		icon : ctx + '/images/icon/Nuvola/16/actions/edit-editor.png'
	});
	
	/**
	 * 删除按钮
	 */
	var deleteButton = new Ext.Button({
		text : '删除',
		cls : 'x-btn-text-icon scroll-bottom',
		icon : ctx + '/images/icon/Nuvola/16/actions/edit-delete.png'
	});
	
	/**
	 * 列表数据加载对象
	 */
	var store = new Ext.data.JsonStore({
		url: ctx + '/allocateSeats/getList.do',
		autoLoad:true,  
 		totalProperty:'count',
		root:'records',
 		fields:[{name:'uuid', mapping:'uuid'},
		        {name:'typeCode', mapping:'typeCode'},
		        {name:'typeName', mapping:'typeName'},
		        {name:'creater', mapping:'creater'},
		        {name:'createDate', mapping:'createDate', type:'date', dateFormat:'time'},
		        {name:'businessCustomerCode', mapping:'businessCustomerCode'},
		        {name:'maxNumber', mapping:'maxNumber'},
				{name:'allocateReg', mapping:'allocateReg'},
				{name:'basiceNumber', mapping:'basiceNumber'},
				{name:'remark', mapping:'remark'},
				{name:'isCycle', mapping:'isCycle'}]
	});
	
	/**
	 * 数据表列对象
	 */
	var columnModel = new Ext.grid.ColumnModel([
        {header:'id', width:100, sortable : true, dataIndex : 'uuid', hidden : true},
		{header : "类型编码", width : 100, sortable : true, dataIndex : 'typeCode'},
		{header : "派位规则", width : 100, sortable : true, dataIndex : 'typeName'},
		{header : "创建人",width : 100, sortable : true, dataIndex : 'creater'},
		{header : "创建时间",width : 100, sortable : true, dataIndex : 'createDate', renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
		{header : "商户编码",width : 100, sortable : true, dataIndex : 'businessCustomerCode'},
		{header : "最大值", width : 100, sortable : true, dataIndex : 'maxNumber'},
		{header:'出号规则', width:100, sortable:true, dataIndex:'allocateReg'},
		{header:'出号基数', width:100, sortable:true, dataIndex:'basiceNumber'},
		{header:'是否循环', width:100, sortable:true, dataIndex:'isCycle', renderer:function(value){
			if(value=='1'){
				return "是";
			}else{
				return "否";
			}
		}},
		{header:'备注', width:100, sortable:true, dataIndex:'remark'}]);
	
	/**
	 * 数据列表对象
	 */
	var dataGrid = new Ext.grid.GridPanel({
		id : 'allocateSeatsType-grid',
		store : store,
		cm : columnModel,  
		viewConfig : {
			forceFit : false
		},
		tbar : [addButton, '-', editButton, '-', deleteButton],
		bbar : new Ext.PagingToolbar({
			pageSize : iPageSize,
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
		renderTo : 'dataGrid'
	});
	
	/**
	 * 新增界面
	 */
	var addForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '新增派位规则',
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
				columns : 2
			},
			items:[{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '派位规则编码',
					name : 'typeCode',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '派位规则编码不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '派位规则名称',
						name : 'typeName',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '派位规则名称不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'numberfield',
						fieldLabel : '派位最大值',
						name : 'maxNumber',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0}',
						allowBlank : false,
				    	blankText : '触发器名称不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'numberfield',
						fieldLabel : '派位基数',
						name : 'basiceNumber',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0}',
						allowBlank : false,
				    	blankText : '派位基数不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '出号规则',
						name : 'allocateReg',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0}',
						allowBlank : false,
				    	blankText : '出号规则不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'combo',
						fieldLabel : '是否循环',
						name : 'isCycle',
						hiddenName : 'isCycle',
						width : 215,
						store : dsCurStatus,
						valueField : 'cycleValue',
						displayField : 'cycleName',
						emptyText : '请选择...',
						triggerAction : 'all',
						selectOnFocus : true,
						editable : false,
						mode : 'local'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'textarea',
						fieldLabel : '备注信息',
						name : 'remark',
						width : '100%',
						height : 50,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)'
						}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					addForm.form.submit({
						url : ctx+'/allocateSeats/save.do',
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
	
	/**
	 * 修改界面
	 */
	var editForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '修改派位规则',
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
				columns : 2
			},
			items:[{
				layout : 'form',
				border : true,
				colspan : 2,
				items:[{
					xtype : 'hidden',
					name : 'uuid',
					}]
				},{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '派位规则编码',
					name : 'typeCode',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '派位规则编码不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '派位规则名称',
						name : 'typeName',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '派位规则名称不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'numberfield',
						fieldLabel : '派位最大值',
						name : 'maxNumber',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0}',
						allowBlank : false,
				    	blankText : '触发器名称不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'numberfield',
						fieldLabel : '派位基数',
						name : 'basiceNumber',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0}',
						allowBlank : false,
				    	blankText : '派位基数不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '出号规则',
						name : 'allocateReg',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0}',
						allowBlank : false,
				    	blankText : '出号规则不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'combo',
						fieldLabel : '是否循环',
						name : 'isCycle',
						hiddenName : 'isCycle',
						width : 215,
						store : dsCurStatus,
						valueField : 'cycleValue',
						displayField : 'cycleName',
						emptyText : '请选择...',
						triggerAction : 'all',
						selectOnFocus : true,
						editable : false,
						mode : 'local'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'textarea',
						fieldLabel : '备注信息',
						name : 'remark',
						width : 520,
						height : 50,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)'
						}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					editForm.form.submit({
						url : ctx+'/allocateSeats/update.do',
						method : 'POST',
						success : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								editWin.hide();
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
					editWin.hide();
				}
			}]
	});
	
	/**
	 * 新增按钮事件
	 */
	var addWin = null;
	addButton.on('click', function(){
		if(!addWin){
			addWin = new Ext.Window({
				el : 'addForm',
				layout : 'fit',
				closeAction : 'hide',
				width : 650,
				height : 280,
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
	/**
	 * 编辑按钮事件
	 */
	var editWin = null;
	editButton.on('click', function(){
		if(dataGrid.getSelectionModel().hasSelection()){
			var record = dataGrid.getSelectionModel().getSelected();
			if(!editWin){
				editWin = new Ext.Window({
					el : 'editForm',
					layout : 'fit',
					closeAction : 'hide',
					width : 650,
					height : 280,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : editForm
				});
			}
			editWin.on('show', function() {
				
 				var uuid = record.get("uuid");
				var typeCode = record.get("typeCode");
				var typeName = record.get("typeName");
				var maxNumber = record.get("maxNumber");
				var allocateReg = record.get("allocateReg");
				var basiceNumber = record.get("basiceNumber");
				var isCycle = record.get("isCycle");
				var remark = record.get("remark");
				
				var mForm = editForm.getForm();
				mForm.reset();
				mForm.findField('typeCode').setValue(typeCode);
				mForm.findField('typeName').setValue(typeName);
				mForm.findField('maxNumber').setValue(maxNumber);
				mForm.findField('allocateReg').setValue(allocateReg);
				mForm.findField('basiceNumber').setValue(basiceNumber);
				mForm.findField('isCycle').setValue(isCycle);
				mForm.findField('remark').setValue(remark);
				mForm.findField('uuid').setValue(uuid);
			}); 
			editWin.show(this);
		}else{
			Ext.MessageBox.alert("错误","没有选中修改项");
			return;
		}
	});
	/**
	 * 删除按钮事件
	 */
	deleteButton.on('click', function(){
		if(dataGrid.getSelectionModel().hasSelection()){
			Ext.MessageBox.confirm('提示', '确定要删除该派位规则吗?', submitDel);
			function submitDel(btn){
				if(btn == 'yes'){
					Ext.MessageBox.wait("数据正在处理中,请稍後...", "提交");
					var record = dataGrid.getSelectionModel().getSelected();
					var uuid = record.get("uuid");
					Ext.Ajax.request({
						url : ctx + '/allocateSeats/delete.do',
						method : 'POST',
						params: { "uuid" : uuid},
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