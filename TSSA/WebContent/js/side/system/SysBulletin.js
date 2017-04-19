Ext.onReady(function(){
	
	var intPageSize = 20;
	
	var type_src = [['0', '商户'], ['1', '客户']];
	
	var dsCurStatus = new Ext.data.SimpleStore({
		fields : ['typeValue', 'typeName'],
		data : type_src
	});
	
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
	
	//工具栏，按钮
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
	
	//控制日期控件样式
	Ext.override(Ext.menu.DateMenu,{
		 
		render : function(){
		 
			Ext.menu.DateMenu.superclass.render.call(this);
			 
			if(Ext.isGecko|| Ext.isSafari){
			 
			this.picker.el.dom.childNodes[0].style.width = '178px';
			 
			this.picker.el.dom.style.width = '178px';
			 
			}
		 
		}
		 
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
					id : 'title',
					xtype : 'textfield',
					fieldLabel : '公告标题',
					name : 'title',
					width : 100,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [{
					id : 'startDate', 
					xtype : 'datefield',
					fieldLabel : '发布日期',
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
	
	var store = new Ext.data.JsonStore({ 
 		url: ctx + '/sys-Bulletin/list.do',
//		autoLoad:true,  
 		totalProperty:'count',
		root:'records',
 		fields:[{name:'id', mapping:'id'},
		        {name:'title', mapping:'title'},
		        {name:'mainText', mapping:'mainText'},
		        {name:'publisher', mapping:'publisher'},
		        {name:'publishDate', mapping:'publishDate'},
		        {name:'bulletinType', mapping:'bulletinType'}]
	}); 
  	
 	var colModel = new Ext.grid.ColumnModel([{
		header:'id',
		width:100,
		sortable : true,
		dataIndex : 'id',
		hidden : true
	},{
		header : "公告标题",
		width : 100,
		sortable : true,
		dataIndex : 'title',
	},{
		header : "公告内容",
		width : 100,
		sortable : true,
		dataIndex : 'mainText',
	},{
		header : "发布对象",
		width : 80,
		sortable : true,
		dataIndex : "bulletinType",
		renderer : typeFormat
	},{
		header : "发布日期",
		width : 100,
		sortable : true,
		dataIndex : 'publishDate',
		renderer : dateFormat
	},{
		header : "发布人",
		width : 100,
		sortable : true,
		dataIndex : 'publisher'
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
		title : '新增公告',
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
					fieldLabel : '公告标题',
					name : 'title',
					width : 440,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '公告标题不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textarea',
						fieldLabel : '公告内容',
						name : 'mainText',
						width : 440,
						height : 200,
						maxLength:500,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '公告内容不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'datefield',
						fieldLabel : '发布日期',
						name : 'publishDate',
						format : 'Y-m-d',
						width : 440
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'combo',
						fieldLabel : '公告对象',
						name : 'bulletinType',
						hiddenName : 'bulletinType',
						width : 440,
						store : dsCurStatus,
						valueField : 'typeValue',
						displayField : 'typeName',
						emptyText : '请选择...',
						triggerAction : 'all',
						selectOnFocus : true,
						editable : false,
						mode : 'local'
					}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					addForm.form.submit({
						url : ctx+'/sys-Bulletin/bulletin-add.do',
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
		title : '修改公告',
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
					name : 'id',
					}]
				},{
					layout : 'form',
					border : true,
					items:[{
						xtype : 'textfield',
						fieldLabel : '公告标题',
						name : 'title',
						width : 440,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '公告标题不能为空'
						}]
					},{
						layout : 'form',
						border : true,
						items : [{
							xtype : 'textarea',
							fieldLabel : '公告内容',
							name : 'mainText',
							width : 440,
							height : 200,
							maxLength:100,
							maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
							allowBlank : false,
					    	blankText : '公告内容不能为空'
						}]
					},{
						layout : 'form',
						border : true,
						items : [{
							xtype : 'datefield',
							fieldLabel : '发布日期',
							name : 'publishDate',
							width : 440,
							format : 'Y-m-d'
						}]
					},{
						layout : 'form',
						border : true,
						items : [{
							xtype : 'combo',
							fieldLabel : '公告对象',
							name : 'bulletinType',
							hiddenName : 'bulletinType',
							width : 440,
							store : dsCurStatus,
							valueField : 'typeValue',
							displayField : 'typeName',
							emptyText : '请选择...',
							triggerAction : 'all',
							selectOnFocus : true,
							editable : false,
							mode : 'local'
						}]
					}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					modForm.form.submit({
						url :  ctx+'/sys-Bulletin/bulletin-modify.do',
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
				width : 580,
				height : 410,
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
					width : 580,
					height : 410,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : modForm
				});
			}
			modWin.on('show', function() {
				
 				var id = record.get("id");
				var title = record.get("title");
				var mainText = record.get("mainText");
				var publishDate = new Date(record.get("publishDate"));
				var bulletinType = record.get("bulletinType");
				
				var mForm = modForm.getForm();
				mForm.reset();
				mForm.findField('id').setValue(id);
				mForm.findField('title').setValue(title);
				mForm.findField('mainText').setValue(mainText);
				mForm.findField('publishDate').setValue(publishDate);
				if(bulletinType != null){
					mForm.findField('bulletinType').setValue(bulletinType);
				}
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
					var id = record.get("id");
					Ext.Ajax.request({
						url : ctx + '/sys-Bulletin/bulletin-delete.do',
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
	
	function dateFormat(value){
		
		var myDate = new Date(value);
		
		var year=myDate.getFullYear(); 
		var month=myDate.getMonth()+1; 
		var date=myDate.getDate(); 
//		var hour=myDate.getHours(); 
//		var minute=myDate.getMinutes(); 
//		var second=myDate.getSeconds(); 
		return year+"-"+month+"-"+date; 
	}
	
	function typeFormat(value){
		var text = "";
		if(value != null){
			if(value == 0){
				text = "商户";
			} else {
				text = "客户";
			}
		}
		return text;
	}
	
	//查询
	function search(){
		var title = searchForm.getForm().findField("title").getValue();
		var startDate = searchForm.getForm().findField("startDate").getValue();
		var endDate = searchForm.getForm().findField("endDate").getValue();
		gridForm.getStore().load({
			proxy : new Ext.data.HttpProxy({
				url : ctx + "/sys-Bulletin/list.do",//获取数据的后台地址
			}),
			params:{
				title : title,
				startDate : Ext.util.Format.date(startDate, 'Y-m-d'),
				endDate : Ext.util.Format.date(endDate, 'Y-m-d'),
				pageNumber : 1,
				pageSize : intPageSize
			}
		}); 
	}
	
	//重置
	function reset(){
		searchForm.getForm().findField("title").setValue("");
		searchForm.getForm().findField("startDate").setValue("");
		searchForm.getForm().findField("endDate").setValue("");
	}
	
});