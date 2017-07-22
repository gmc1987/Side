Ext.onReady(function(){
	var intPageSize = 20;
	
	var addJobBtn = new Ext.Button({
		text : '新增',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: ctx+'/images/icon/Nuvola/16/actions/edit-add.png'
	});

	var modBtn = new Ext.Button({
		text : '修改',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: ctx+'/images/icon/Nuvola/16/actions/edit-editor.png'
	});

	var delBtn = new Ext.Button({
		text : '删除',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: ctx+'/images/icon/Nuvola/16/actions/edit-delete.png'
	});
	
  	var store = new Ext.data.JsonStore({ 
 		url: ctx + '/job/list.do',
//		autoLoad:true,  
 		totalProperty:'pageMode.count',
		root:'pageMode.records',
 		fields:[{name:'jobName', mapping:'jobName'},
		        {name:'jobGroup', mapping:'jobGroup'},
		        {name:'triggerName', mapping:'triggerName'},
		        {name:'triggerGroup', mapping:'triggerGroup'},
		        {name:'description', mapping:'description'},
		        {name:'nextFireTime', mapping:'nextFireTime'},
		        {name:'prevFireTime', mapping:'prevFireTime'},
				{name:'startTime', mapping:'startTime'},
				{name:'endTime', mapping:'endTime'},
				{name:'cronExpression', mapping:'cronExpression'},
				{name:'className', mapping:'className'}]
	}); 
  	
 	var colModel = new Ext.grid.ColumnModel([{
		header:'作业名称',
		width:100,
		sortable : true,
		dataIndex : 'jobName'
	},{
		header : "作业组",
		width : 100,
		sortable : true,
		dataIndex : 'jobGroup',
	},{
		header : "触发器名称",
		width : 100,
		sortable : true,
		dataIndex : 'triggerName',
		hidden : true
	},{
		header : "触发器组",
		width : 100,
		sortable : true,
		dataIndex : 'triggerGroup',
		hidden : true
	},{
		header : "作业描述",
		width : 100,
		sortable : true,
		dataIndex : 'description'
	},{
		header : "下次执行时间",
		width : 100,
		sortable : true,
		dataIndex : 'nextFireTime'
	},{
		header : "上次执行时间",
		width : 100,
		sortable : true,
		dataIndex : 'prevFireTime'
	},{
		header:'开始时间',
		width:100,
		sortable:true,
		dataIndex:'startTime'
	},{
		header:'结束时间',
		width:100,
		sortable:true,
		dataIndex:'endTime'
	},{
		header:'时间表达式',
		width:100,
		sortable:true,
		dataIndex:'cronExpression'
	},{
		header:'执行类',
		width:120,
		sortable:true,
		dataIndex:'className'
	}]);  
	
	//数据集
	var gridForm = new Ext.grid.GridPanel({
		id : 'opercode-grid',
		store : store,
		cm : colModel,  
		viewConfig : {
			forceFit : false
		},
		tbar : [addJobBtn, '-', modBtn, '-', delBtn],
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
		title : '作业新增',
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
					fieldLabel : '作业名称',
					name : 'jobName',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '作业名称不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '组名',
						name : 'jobGroup',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '组名称不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '触发器名称',
						name : 'triggerName',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '触发器名称不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '触发器组名',
						name : 'triggerGroup',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '触发器组名不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '执行时间',
						name : 'cronExpression',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '作业时间不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '执行类',
						name : 'className',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '执行类不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textarea',
						fieldLabel : '作业描述',
						name : 'description',
						width : 215,
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
						url : ctx+'/job/jobAdd.do',
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
		title : '作业修改',
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
					name : 'jobName',
					}]
				},{
					layout : 'form',
					border : true,
					items:[{
						xtype : 'hidden',
						name : 'jobGroup',
						}]
				},{
					layout : 'form',
					border : true,
					items:[{
						xtype : 'hidden',
						name : 'triggerName',
						}]
				},{
					layout : 'form',
					border : true,
					items:[{
						xtype : 'hidden',
						name : 'triggerGroup',
						}]
				},{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '作业名称',
					name : 'jobName1',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '作业名称不能为空',
			    	disabled : true
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '组名',
						name : 'jobGroup1',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '组名称不能为空',
				    	disabled : true
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '触发器名称',
						name : 'triggerName1',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '触发器名称不能为空',
				    	disabled : true
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '触发器组名',
						name : 'triggerGroup1',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '触发器组名不能为空',
				    	disabled : true
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '执行时间',
						name : 'cronExpression',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '作业时间不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '执行类',
						name : 'className',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '执行类不能为空',
				    	disabled : true
						}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textarea',
						fieldLabel : '作业描述',
						name : 'description',
						width : 215,
						height : 50,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)'
						}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					modForm.form.submit({
						url :  ctx+'/job/jobUpdate.do',
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
	addJobBtn.on('click', function(){
		if(!addWin){
			addWin = new Ext.Window({
				el : 'add_job',
				layout : 'fit',
				closeAction : 'hide',
				width : 380,
				height : 340,
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
					el : 'mod_job',
					layout : 'fit',
					closeAction : 'hide',
					width : 380,
					height : 340,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : modForm
				});
			}
			modWin.on('show', function() {
				
 				var jobName = record.get("jobName");
				var jobGroup = record.get("jobGroup");
				var triggerName = record.get("triggerName");
				var triggerGroup = record.get("triggerGroup");
				var cronExpression = record.get("cronExpression");
				var description = record.get("description");
				var className = record.get("className");
				
				var mForm = modForm.getForm();
				mForm.reset();
				mForm.findField('jobName').setValue(jobName);
				mForm.findField('jobGroup').setValue(jobGroup);
				mForm.findField('triggerName').setValue(triggerName);
				mForm.findField('triggerGroup').setValue(triggerGroup);
				mForm.findField('jobName1').setValue(jobName);
				mForm.findField('jobGroup1').setValue(jobGroup);
				mForm.findField('triggerName1').setValue(triggerName);
				mForm.findField('triggerGroup1').setValue(triggerGroup);
				mForm.findField('cronExpression').setValue(cronExpression);
				mForm.findField('description').setValue(description);
				mForm.findField('className').setValue(className);
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
			Ext.MessageBox.confirm('提示', '确认要删除该作业吗?', submitDel);
			function submitDel(btn){
				if(btn == 'yes'){
					Ext.MessageBox.wait("数据正在处理中,请稍後...", "提交");
					var record = gridForm.getSelectionModel().getSelected();
					var jobName = record.get("jobName");
					var jobGroup = record.get("jobGroup");
					Ext.Ajax.request({
						url : ctx + '/job/jobDel.do',
						method : 'POST',
						params: { "jobName" : jobName, "jobGroup" : jobGroup },
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