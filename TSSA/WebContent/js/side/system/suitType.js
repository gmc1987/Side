Ext.onReady(function(){
	
	var intPageSize = 20;
	
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
	
	var delBut = new Ext.Button({
		text : '删除',
		cls : 'x-btn-text-icon scroll-bottom',
		icon : ctx+'/images/icon/Nuvola/16/actions/edit-delete.png'
	});
	
	var store = new Ext.data.JsonStore({ 
 		url: ctx + '/suitType/list.do',
		autoLoad:true,  
 		totalProperty:'pageMode.count',
		root:'pageMode.records',
 		fields:[{name:'typeId', mapping:'typeId'},
		        {name:'typeName', mapping:'typeName'},
		        {name:'creater', mapping:'creater'},
		        {name:'createDate', type:'date', mapping:'createDate.time', dateFormat :'time'}]
	}); 
  	
 	var colModel = new Ext.grid.ColumnModel([{
		header:'id',
		width:100,
		sortable : true,
		dataIndex : 'typeId',
		hidden : true
	},{
		header : "投诉类型",
		width : 100,
		sortable : true,
		dataIndex : 'typeName',
	},{
		header : "创建人",
		width : 80,
		sortable : true,
		dataIndex : "creater"
	},{
		header : "创建日期",
		width : 120,
		sortable : true,
		dataIndex : 'createDate',
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
		tbar : [addBut, '-', modBut, '-', delBut],
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
		title : '新增投诉类型',
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
					fieldLabel : '类型名称',
					name : 'typeName',
					width : 200,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '类型名称不能为空'
					}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					addForm.form.submit({
						url : ctx+'/suitType/save.do',
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
		title : '修改投诉类型',
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
					name : 'typeId',
					}]
				},{
					layout : 'form',
					border : true,
					items:[{
						xtype : 'textfield',
						fieldLabel : '类型名称',
						name : 'typeName',
						width : 200,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '类型名称不能为空'
						}]
					}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					modForm.form.submit({
						url :  ctx+'/suitType/update.do',
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
	addBut.on('click', function(){
		if(!addWin){
			addWin = new Ext.Window({
				el : 'addForm',
				layout : 'fit',
				closeAction : 'hide',
				width : 370,
				height : 145,
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
					el : 'modForm',
					layout : 'fit',
					closeAction : 'hide',
					width : 370,
					height : 145,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : modForm
				});
			}
			modWin.on('show', function() {
				
 				var id = record.get("typeId");
				var typeName = record.get("typeName");
				
				var mForm = modForm.getForm();
				mForm.reset();
				mForm.findField('typeId').setValue(id);
				mForm.findField('typeName').setValue(typeName);
			}); 
			modWin.show(this);
		}else{
			Ext.MessageBox.alert("错误","没有选中修改项");
			return;
		}
	});
	
	//删除
	delBut.on('click', function(){
		if(gridForm.getSelectionModel().hasSelection()){
			Ext.MessageBox.confirm('提示', '确认要删除该作业吗?', submitDel);
			function submitDel(btn){
				if(btn == 'yes'){
					Ext.MessageBox.wait("数据正在处理中,请稍後...", "提交");
					var record = gridForm.getSelectionModel().getSelected();
					var id = record.get("typeId");
					Ext.Ajax.request({
						url : ctx + '/suitType/delete.do',
						method : 'POST',
						params: { "id" : id },
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