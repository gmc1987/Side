Ext.onReady(function(){
	
	var intPageSize = 20;
	
	var myToolbar = new Ext.Toolbar();
	myToolbar.render('toolBar');
	
	myToolbar.addButton({
		text: '查询', 
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: ctx+'/images/icon/Nuvola/16/actions/edit-find.png',
		handler: function(o, e) {
			search();
		}
	});
	myToolbar.addButton({
		text: '重置', 
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: ctx+'/images/icon/Nuvola/16/actions/view-refresh.png',
		handler: function(o, e) {
			reset();
		}
	});
	
	Ext.override(Ext.menu.DateMenu,{
		 
		render : function(){
		 
		Ext.menu.DateMenu.superclass.render.call(this);
		 
		if(Ext.isGecko|| Ext.isSafari){
		 
		this.picker.el.dom.childNodes[0].style.width = '178px';
		 
		this.picker.el.dom.style.width = '178px';
		 
		}
		 
		}
		 
	});
	
	var dsCurStatus = new Ext.data.JsonStore({ 
		url: ctx + '/suitType/list.do',
		autoLoad:true,  
		root:'pageMode.records',
 		fields:[{name:'typeId', mapping:'typeId'},
		        {name:'typeName', mapping:'typeName'}]
	}); 
	
	//新增投诉类型下拉列表
	var addSuitType = new Ext.form.ComboBox({
		id : 'suiType-add',
		hiddenName : 'suiTypeId',
		store : dsCurStatus,
		valueField : 'typeId',
		displayField : 'typeName',
		fieldLabel : '投诉类型',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
	});
	
	//修改投诉类型下拉列表
	var modSuitType = new Ext.form.ComboBox({
		id : 'suiType-mod',
		hiddenName : 'suiTypeId',
		store : dsCurStatus,
		valueField : 'typeId',
		displayField : 'typeName',
		fieldLabel : '投诉类型',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
	});
	
	//处理投诉类型下拉列表
	var processSuitType = new Ext.form.ComboBox({
		id : 'suiType-process',
		hiddenName : 'suiTypeId',
		store : dsCurStatus,
		valueField : 'typeId',
		displayField : 'typeName',
		fieldLabel : '投诉类型',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
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
				columns : 3
			},
			items : [{
				layout : 'form',
				items : [{
					id : 'custPhone',
					xtype : 'textfield',
					fieldLabel : '客户号码',
					name : 'custPhone',
					width : 100,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [{
					id : 'startDate', 
					xtype : 'datefield',
					fieldLabel : '查询日期',
					name : 'startDate',
					format : 'Y-m-d',
					width : 100
					}]
			},{
				layout : 'form',
				labelWidth : 10,
				labelAlign : 'center',
				labelSeparator : '~',
				items : [{
					id : 'endDate', 
					xtype : 'datefield',
					fieldLabel : ' ',
					name : 'endDate',
					format : 'Y-m-d',
					width : 100
					}]
			}]
		}]
	});
	//显示工具栏
	myToolbar.doLayout(); 
	
	var addBut = new Ext.Button({
		text : '新增',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: ctx+'/images/icon/Nuvola/16/actions/edit-add.png'
	});
	
	var modBut = new Ext.Button({
		text : '修改',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: ctx+'/images/icon/Nuvola/16/actions/edit-editor.png'
	});
	
	var processBut = new Ext.Button({
		text : '处理投诉',
		cls : 'x-btn-text-icon scroll-bottom',
		icon : ctx + '/images/icon/Nuvola/16/actions/edit-redo.png'
	});
	
	var store = new Ext.data.JsonStore({ 
 		url: ctx + '/custSuit/list.do',
		autoLoad:true,  
 		totalProperty:'pageModel.count',
		root:'pageModel.records',
 		fields:[{name:'suitId', mapping:'suitId'},
		        {name:'title', mapping:'title'},
		        {name:'content', mapping:'content'},
		        {name:'suitObjectId', mapping:'suitObject.cooperId'},
		        {name:'suitObject', mapping:'suitObject.cooperName'},
		        {name:'suiterId', mapping:'suiter.id'},
		        {name:'suiter', mapping:'suiter.username'},
		        {name:'suitDate', mapping:'suitDate.time', type:'date', dateFormat:'time'},
		        {name:'suiTypeId', mapping:'suiType.typeId'},
		        {name:'suiType', mapping:'suiType.typeName'}]
	}); 
  	
 	var colModel = new Ext.grid.ColumnModel([{
		header:'id',
		width:100,
		sortable : true,
		dataIndex : 'suitId',
		hidden : true
	},{
		header : "标题",
		width : 100,
		sortable : true,
		dataIndex : 'title',
	},{
		header : "投诉内容",
		width : 100,
		sortable : true,
		dataIndex : 'content',
	},{
		header : "投诉对象ID",
		width : 100,
		sortable : true,
		dataIndex : 'suitObjectId',
		hidden : true
	},{
		header : "投诉对象",
		width : 100,
		sortable : true,
		dataIndex : 'suitObject',
	},{
		header : "投诉人ID",
		width : 100,
		sortable : true,
		dataIndex : 'suiterId',
		hidden : true
	},{
		header : "投诉人",
		width : 80,
		sortable : true,
		dataIndex : "suiter"
	},{
		header : "投诉日期",
		width : 120,
		sortable : true,
		dataIndex : 'suitDate',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},{
		header : "投诉类型ID",
		width : 120,
		sortable : true,
		dataIndex : 'suiTypeId',
		hidden : true
	},{
		header : "投诉类型",
		width : 120,
		sortable : true,
		dataIndex : 'suiType'
	}]);  
	
	//数据集
	var gridForm = new Ext.grid.GridPanel({
		id : 'opercode-grid',
		store : store,
		cm : colModel,  
		viewConfig : {
			forceFit : false
		},
		tbar : [addBut, '-', modBut, '-', processBut],
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
	
	/*----------- 选择投诉对象 ------------*/
	var selectWin = null;
	
	var searchButton = new Ext.Button({
		type : 'button',
		width : 80,
		text: '查询'
	});
	
	var suitObjectSearchForm = new Ext.form.FormPanel({
		id : 'suitObjectSearchForm',
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
				columns : 2
			},
			items : [{
				layout : 'form',
				items : [{
					id : 'cooperName',
					xtype : 'textfield',
					fieldLabel : '商户名称',
					name : 'cooperName',
					width : 100,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [searchButton]
			}]
		}]
	});
	
	var suitStore = new Ext.data.JsonStore({ 
 		url:ctx + '/cooperation/list.do',
		autoLoad:true,  
 		totalProperty:'pageMode.count',
		root:'pageMode.records',
 		fields:[{name:'cooperId', mapping:'cooperId'},
		        {name:'cooperCode', mapping:'cooperCode'},
		        {name:'cooperName', mapping:'cooperName'},
		        {name:'parentCooperType', mapping:'productTypeId.pareTypes.typeCode'},
		        {name:'parentCooperTypeName', mapping:'productTypeId.pareTypes.typeName'},
		        {name:'cooperType', mapping:'productTypeId.typeCode'},
		        {name:'cooperTypeName', mapping:'productTypeId.typeName'},
		        {name:'address', mapping:'address'},
		        {name:'tel1', mapping:'tel1'},
		        {name:'tel2', mapping:'tel2'},
				{name:'businessLicense', mapping:'businessLicense'},
				{name:'pictureUrl', mapping:'pictureUrl'},
				{name:'account', mapping:'account'},
				{name:'allianceDate', type:'date', mapping:'allianceDate.time', dateFormat :'time'}]
	}); 
	
	var suitColModel = new Ext.grid.ColumnModel([{
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
		dataIndex : 'parentCooperType',
		hidden : true
	},{
		header : "行业类型",
		width : 80,
		sortable : true,
		dataIndex : 'parentCooperTypeName'
	},{
		header : "行业细分编码",
		width : 80,
		sortable : true,
		dataIndex : 'cooperType',
		hidden : true
	},{
		header : "细分类型",
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
	
	var cooperationNameTextField = new Ext.form.TextField({
		fieldLabel : '查找商户名称',
		labelSeparator : ':',
		width : 100
	});
	
	var selectSuitGird = new Ext.grid.GridPanel({
		region: 'center',
		store : suitStore,
		cm : suitColModel,  
		frame : true,
		viewConfig : {
			forceFit : false
		},
		autoHeight : true,
		autoWidth : true,
		tbar : [cooperationNameTextField, "-", 
			{
				xtype : 'button',
				cls: 'x-btn-text-icon scroll-bottom', 
				icon: ctx+'/images/icon/Nuvola/16/actions/edit-find.png',
				text : '查找',
				handler:function(){
					var name = cooperationNameTextField.getValue();
					selectSuitGird.getStore().load({
						proxy : new Ext.data.HttpProxy({
							url : ctx+"/cooperation/list.do",//获取数据的后台地址
						}),
						params:{
							businessName : name,
							start:0,
							limit:intPageSize
						}
					});
				}
			}
		],
		bbar : new Ext.PagingToolbar({
			pageSize : intPageSize,
			store : suitStore,
			beforePageText : '第',
			afterPageText : '页,共 {0} 页',
			displayInfo : true,
			displayMsg : '{0} - {1} , 共 {2} 条',
			emptyMsg : "无记录"
		}),
		collapsible : true,
		animCollapse : false,
		trackMouseOver : false,
		iconCls : 'icon-grid',
		loadMask : ({
			msg : '数据正在加载中,请稍後...'
		})
	});
	
	var selectSuitForm = new Ext.FormPanel({
		frame : true,
		title : '选择投诉对象',
		buttonAlign : 'center',
		layout : 'border',
		bodyStyle : 'padding:5px 5px 0',
		/* width : 640,
		height: 400, */
		collapsible : false,
		autoScroll : false,
		items : [{xtype : 'hidden', name : 'saveOrUpdate'},selectSuitGird],
		buttons :[{
			text : '提交',
			handler : function(){
				var record = selectSuitGird.getSelectionModel().getSelected();
				var id = record.get("cooperId");
				var name = record.get("cooperName");
				
				if(selectSuitForm.getForm().findField("saveOrUpdate").getValue() == ""){
					
					var suitForm = addForm.getForm();
					suitForm.findField('buisnessId').setValue(id);
					suitForm.findField('suitObject.cooperName').setValue(name);
					
				} else {
					
					var suitForm = modForm.getForm();
					suitForm.findField('buisnessId').setValue(id);
					suitForm.findField('suitObject.cooperName').setValue(name);
					
				}
				
				selectWin.hide();
			}
		},{
			text : '取消',
			handler : function(){
				selectWin.hide();
			}
		}]
	});
	
	/*-----------选择对象结束 -------------*/
	
	/*-----------选择投诉人开始 --------------*/
	var selectCustWin = null;
	var custStore = new Ext.data.JsonStore({ 
 		url:ctx + '/customer/getCustLilst.do',
		autoLoad:true,  
 		totalProperty:'pageMode.count',
		root:'pageMode.records',
 		fields:[{name:'id', mapping:'id'},
		        {name:'uid', mapping:'uid'},
		        {name:'username', mapping:'username'},
		        {name:'phone', mapping:'phone'},
		        {name:'email', mapping:'email'}]
	});
	
	var custColModel = new Ext.grid.ColumnModel([{
		header:'uuid',
		width:100,
		sortable : true,
		dataIndex : 'id',
		hidden : true
	},{
		header : "用户编码",
		width : 80,
		sortable : true,
		dataIndex : 'uid',
	},{
		header : "用户名",
		width : 80,
		sortable : true,
		dataIndex : 'username',
	},{
		header : "手机号",
		width : 80,
		sortable : true,
		dataIndex : 'phone',
	},{
		header : "email",
		width : 80,
		sortable : true,
		dataIndex : 'email',
		hidden : true
	}]); 
	
	var custTextField = new Ext.form.TextField({
		fieldLabel : '投诉人名称',
		labelSeparator : ':',
		width : 100
	});
	
	var selectCustGird = new Ext.grid.GridPanel({
		region: 'center',
		store : custStore,
		cm : custColModel,  
		frame : true,
		viewConfig : {
			forceFit : false
		},
		width : '80%',
		height : '100%',
		tbar : [custTextField, {
			xtype : 'button',
			cls: 'x-btn-text-icon scroll-bottom', 
			icon: ctx+'/images/icon/Nuvola/16/actions/edit-find.png',
			text : '查找',
			handler:function(){
				var phone = custTextField.getValue();
				selectCustGird.getStore().load({
					proxy : new Ext.data.HttpProxy({
						url : ctx+"/customer/getCustLilst.do",//获取数据的后台地址
					}),
					params:{
						phone : phone,
						start:0,
						limit:intPageSize
					}
				});
			}
		}],
		bbar : new Ext.PagingToolbar({
			pageSize : intPageSize,
			store : custStore,
			beforePageText : '第',
			afterPageText : '页,共 {0} 页',
			displayInfo : true,
			displayMsg : '{0} - {1} , 共 {2} 条',
			emptyMsg : "无记录"
		}),
		collapsible : true,
		animCollapse : false,
		trackMouseOver : false,
		iconCls : 'icon-grid',
		loadMask : ({
			msg : '数据正在加载中,请稍後...'
		})
	});
	
	var selectCustForm = new Ext.FormPanel({
		frame : true,
		title : '选择投诉人',
		buttonAlign : 'center',
		layout : 'border',
		bodyStyle : 'padding:5px 5px 0',
		/* width : 640,
		height: 400, */
		collapsible : false,
		autoScroll : false,
		items : [{xtype : 'hidden', name : 'saveOrUpdate'},selectCustGird],
		buttons :[{
			text : '提交',
			handler : function(){
				var record = selectCustGird.getSelectionModel().getSelected();
				var id = record.get("id");
				var name = record.get("username");
				
				if(selectCustForm.getForm().findField("saveOrUpdate").getValue() == ""){
					
					var custForm = addForm.getForm();
					custForm.findField('customerId').setValue(id);
					custForm.findField('suiter.name').setValue(name);
					
				} else {
					
					var custForm = modForm.getForm();
					custForm.findField('customerId').setValue(id);
					custForm.findField('suiter.name').setValue(name);
					
				}
				
				selectCustWin.hide();
			}
		},{
			text : '取消',
			handler : function(){
				selectCustWin.hide();
			}
		}]
	});
	
	/*-----------选择投诉人结束 --------------*/
	
	//addfrom
	var addForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '新增客户投诉',
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
					fieldLabel : '投诉标题',
					name : 'title',
					width : 450,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '投诉标题不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'buisnessId'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'customerId'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'saveOrUpdate'
					}]
				},{
					layout : 'form',
					border : true,
					items:[{
						xtype : 'textfield',
						fieldLabel : '投诉人',
						name : 'suiter.name',
						width : 450,
						maxLength : 100,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '投诉人不能为空',
						listeners : {
							'focus' : function(){
								if(!selectCustWin){
									selectCustWin = new Ext.Window({
										el : 'selectCustWin',
										layout : 'fit',
										closeAction : 'hide',
										width : 720,
										height : 400,
										modal : true,
										collapsible : true,
										constrain : true,
										resizable : false,
										items : [selectCustForm]
									});
								}
								selectCustWin.on('show', function(){
									selectCustForm.getForm().reset();
								});
								selectCustWin.show(this);
							}
						}
					}]
				},{
					layout : 'form',
					border : true,
					items:[addSuitType]
				},{
					layout : 'form',
					border : true,
					items:[{
						xtype : 'textfield',
						fieldLabel : '投诉对象',
						name : 'suitObject.cooperName',
						width : 450,
						maxLength : 200,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '投诉对象不能为空',
						listeners : {
							'focus' : function(){
								if(!selectWin){
									selectWin = new Ext.Window({
										el : 'selectWin',
										layout : 'fit',
										closeAction : 'hide',
										width : 720,
										height : 400,
										modal : true,
										collapsible : true,
										constrain : true,
										resizable : false,
										items : [selectSuitForm]
									});
								}
								selectWin.on('show', function(){
									selectSuitForm.getForm().reset();
								});
								selectWin.show(this);
							}
						}
					}]
				},{
					layout:'form',
					border:true,
					items:[{
						xtype : 'textarea',
						fieldLabel : '投诉内容',
						name : 'content',
						width : 450,
						height : 100,
						maxLength : 500,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '投诉内容不能为空'
					}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					addForm.form.submit({
						url : ctx+'/custSuit/save.do',
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
		title : '修改客户投诉',
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
					fieldLabel : '投诉标题',
					name : 'title',
					width : 450,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '投诉标题不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'suitId'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'customerId'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'buisnessId'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'saveOrUpdate'
					}]
				},{
					layout : 'form',
					border : true,
					items:[{
						xtype : 'textfield',
						fieldLabel : '投诉人',
						name : 'suiter.name',
						width : 450,
						maxLength : 100,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '投诉人不能为空',
						listeners : {
							'focus' : function(){
								if(!selectCustWin){
									selectCustWin = new Ext.Window({
										el : 'selectCustWin',
										layout : 'fit',
										closeAction : 'hide',
										width : 720,
										height : 400,
										modal : true,
										collapsible : true,
										constrain : true,
										resizable : false,
										items : [selectCustForm]
									});
								}
								selectCustWin.on('show', function(){
									var modCustForm = selectCustForm.getForm();
									modCustForm.reset();
									modCustForm.findField('saveOrUpdate').setValue(1);
								});
								selectCustWin.show(this);
							}
						}
					}]
				},{
					layout : 'form',
					border : true,
					items:[modSuitType]
				},{
					layout : 'form',
					border : true,
					items:[{
						xtype : 'textfield',
						fieldLabel : '投诉对象',
						name : 'suitObject.cooperName',
						width : 450,
						maxLength : 200,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '投诉对象不能为空',
						listeners : {
							'focus' : function(){
								if(!selectWin){
									selectWin = new Ext.Window({
										el : 'selectWin',
										layout : 'fit',
										closeAction : 'hide',
										width : 720,
										height : 400,
										modal : true,
										collapsible : true,
										constrain : true,
										resizable : false,
										items : [selectSuitForm]
									});
								}
								selectWin.on('show', function(){
									var modSuitForm = selectSuitForm.getForm();
									modSuitForm.reset();
									modSuitForm.findField('saveOrUpdate').setValue(1);
								});
								selectWin.show(this);
							}
						}
					}]
				},{
					layout:'form',
					border:true,
					items:[{
						xtype : 'textarea',
						fieldLabel : '投诉内容',
						name : 'content',
						width : 450,
						height : 100,
						maxLength : 500,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '投诉内容不能为空'
					}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					modForm.form.submit({
						url :  ctx+'/custSuit/update.do',
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
	
	//处理投诉form
	var processForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '处理客户投诉',
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
					fieldLabel : '投诉标题',
					name : 'title',
					width : 450,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '投诉标题不能为空',
			    	disabled : true
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'suitId'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'customerId'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'buisnessId'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'hidden',
						name : 'saveOrUpdate'
					}]
				},{
					layout : 'form',
					border : true,
					items:[{
						xtype : 'textfield',
						fieldLabel : '投诉人',
						name : 'suiter.name',
						width : 450,
						maxLength : 100,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '投诉人不能为空',
						disabled : true
					}]
				},{
					layout : 'form',
					border : true,
					items:[processSuitType]
				},{
					layout : 'form',
					border : true,
					items:[{
						xtype : 'textfield',
						fieldLabel : '投诉对象',
						name : 'suitObject.cooperName',
						width : 450,
						maxLength : 200,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '投诉对象不能为空',
						disabled : true
					}]
				},{
					layout:'form',
					border:true,
					items:[{
						xtype : 'textarea',
						fieldLabel : '投诉内容',
						name : 'content',
						width : 450,
						height : 100,
						maxLength : 500,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '投诉内容不能为空',
						disabled : true
					}]
				},{
					layout:'form',
					border:true,
					items:[{
						xtype : 'textarea',
						fieldLabel : '投诉回复',
						name : 'processContext',
						width : 450,
						height : 100,
						maxLength : 500,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '回复内容不能为空'
					}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					processForm.form.submit({
						url :  ctx+'/custSuit/process.do',
						method : 'POST',
						success : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								processingWin.hide();
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
					processingWin.hide();
				}
			}]
	});
	
	//新增
	var addWin = null;
	addBut.on('click', function(){
		if(!addWin){
			addWin = new Ext.Window({
				el : 'add_suit',
				layout : 'fit',
				closeAction : 'hide',
				width : 600,
				height : 350,
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
	modBut.on('click', function(){
		if(gridForm.getSelectionModel().hasSelection()){
			var record = gridForm.getSelectionModel().getSelected();
			if(!modWin){
				modWin = new Ext.Window({
					el : 'mod_suit',
					layout : 'fit',
					closeAction : 'hide',
					width : 600,
					height : 350,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : modForm
				});
			}
			modWin.on('show', function() {
				
 				var id = record.get("suiTypeId");
//				var typeName = record.get("typeName");
				var title = record.get("title");
				var content = record.get("content");
				var suitObject = record.get("suitObject");
				var suitObjectId = record.get("suitObjectId");
				var suiterId = record.get("suiterId");
				var suiter = record.get("suiter");
				var custSuitId = record.get("suitId");
				
				var mForm = modForm.getForm();
				mForm.reset();
				
//				modCustForm.findField('saveOrUpdate').setValue(1);
//				modSuitForm.findField('saveOrUpdate').setValue(1);
//				mForm.findField('typeId').setValue(id);
//				mForm.findField('typeName').setValue(typeName);
				modSuitType.setValue(id);
				mForm.findField('suitId').setValue(custSuitId);
				mForm.findField('title').setValue(title);
				mForm.findField('content').setValue(content);
				mForm.findField('customerId').setValue(suiterId);
				mForm.findField('suiter.name').setValue(suiter);
				mForm.findField('buisnessId').setValue(suitObjectId);
				mForm.findField('suitObject.cooperName').setValue(suitObject);
			}); 
			modWin.show(this);
		}else{
			Ext.MessageBox.alert("错误","没有选中修改项");
			return;
		}
	});
	
	//处理投诉
	var processingWin = null;
	processBut.on('click', function(){
		if(gridForm.getSelectionModel().hasSelection()){
			var record = gridForm.getSelectionModel().getSelected();
			if(!processingWin){
				processingWin = new Ext.Window({
					el : 'processingWin',
					layout : 'fit',
					closeAction : 'hide',
					width : 600,
					height : 450,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : processForm
				});
			}
			processingWin.on('show', function() {
				
 				var id = record.get("suiTypeId");
//				var typeName = record.get("typeName");
				var title = record.get("title");
				var content = record.get("content");
				var suitObject = record.get("suitObject");
				var suitObjectId = record.get("suitObjectId");
				var suiterId = record.get("suiterId");
				var suiter = record.get("suiter");
				var custSuitId = record.get("suitId");
				
				var pForm = processForm.getForm();
				pForm.reset();
				
//				modCustForm.findField('saveOrUpdate').setValue(1);
//				modSuitForm.findField('saveOrUpdate').setValue(1);
//				mForm.findField('typeId').setValue(id);
//				mForm.findField('typeName').setValue(typeName);
				processSuitType.setValue(id);
				processSuitType.disable();
				pForm.findField('suitId').setValue(custSuitId);
				pForm.findField('title').setValue(title);
				pForm.findField('content').setValue(content);
				pForm.findField('customerId').setValue(suiterId);
				pForm.findField('suiter.name').setValue(suiter);
				pForm.findField('buisnessId').setValue(suitObjectId);
				pForm.findField('suitObject.cooperName').setValue(suitObject);
			}); 
			processingWin.show(this);
		}else{
			Ext.MessageBox.alert("错误","没有选中处理项");
			return;
		}
	});
	
	//查询
	function search(){
		var custPhone = searchForm.getForm().findField("custPhone").getValue();
		var startDate = searchForm.getForm().findField("startDate").getValue();
		var endDate = searchForm.getForm().findField("endDate").getValue();
		gridForm.getStore().load({
			proxy : new Ext.data.HttpProxy({
				url : ctx + "/custSuit/list.do",//获取数据的后台地址
			}),
			params:{
				custPhone : custPhone,
				startDate : Ext.util.Format.date(startDate, 'Y-m-d'),
				endDate : Ext.util.Format.date(endDate, 'Y-m-d'),
				pageNumber : 1,
				pageSize : intPageSize
			}
		}); 
	}
	
	//重置
	function reset(){
		searchForm.getForm().findField("custPhone").setValue("");
		searchForm.getForm().findField("startDate").setValue("");
		searchForm.getForm().findField("endDate").setValue("");
	}
});