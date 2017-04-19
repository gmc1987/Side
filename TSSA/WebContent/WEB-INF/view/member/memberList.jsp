<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageMode" value="${pageMode}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="会员列表" />
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<script type="text/javascript">
Ext.onReady(function(){
	
	var intPageSize =10;
	
	Ext.override(Ext.menu.DateMenu,{
		 
		render : function(){
			Ext.menu.DateMenu.superclass.render.call(this);
			if(Ext.isGecko|| Ext.isSafari){
				this.picker.el.dom.childNodes[0].style.width = '178px';
				this.picker.el.dom.style.width = '178px';
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
				columns : 4
			},
			items : [{
				layout : 'form',
				items : [{
					id : 'memberPhoneNo',
					xtype : 'textfield',
					fieldLabel : '会员手机',
					name : 'memberPhoneNo',
					width : 100,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [{
					id : 'cardNo', 
					xtype : 'textfield',
					fieldLabel : '会员卡号',
					name : 'cardNo',
					width : 100,
					maxLength:100
					}]
			}]
		}]
	});
	//显示工具栏
	myToolbar.doLayout(); 
  	var store = new Ext.data.JsonStore({ 
 		url:'${ctx}/member/list.do',
//		autoLoad:true,  
 		totalProperty:'pageMode.count',
		root:'pageMode.records',
 		fields:[{name:'memberId', mapping:'memberId'},
		        {name:'memberPhoneNo', mapping:'memberPhoneNo'},
		        {name:'cardNo', mapping:'cardNo'},
		        {name:'sendDate', mapping:'sendDate.time', type:'date', dateFormat :'time'},
		        {name:'cooperName', mapping:'cooperation.cooperName'},
		        {name:'memberName', mapping:'customer.username'},
		        {name:'discount', mapping:'discount'},
		        {name:'validity', mapping:'validity.time', type:'date', dateFormat :'time'}],
		/* params : {
			pageNumber:'${pageMode.pageNumber}',
			pageSize:'${pageMode.pageSize}'
		}  */
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
		dataIndex : 'memberId',
		hidden : true
	},{
		header : "会员卡号",
		width : 160,
		sortable : true,
		dataIndex : 'cardNo',
	},{
		header : "持卡人",
		width : 80,
		sortable : true,
		dataIndex : 'memberName'
	},{
		header : "会员手机号",
		width : 80,
		sortable : true,
		dataIndex : 'memberPhoneNo',
	},{
		header : "会员折扣",
		width : 80,
		sortable : true,
		dataIndex : 'discount',
	},{
		header : "发卡日期",
		width : 120,
		sortable : true,
		dataIndex : 'sendDate',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},{
		header : "有效日期",
		width : 80,
		sortable : true,
		dataIndex : 'validity',
		renderer : Ext.util.Format.dateRenderer('Y-m-d')
	},{
		header : "发卡商户",
		width : 80,
		sortable : true,
		dataIndex : 'cooperName'
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
	
	//查询
	function search(){
		var phoneNo = searchForm.getForm().findField("memberPhoneNo").getValue();
		var memberCardNo = searchForm.getForm().findField("cardNo").getValue();
		gridForm.getStore().load({
			proxy : new Ext.data.HttpProxy({
				url : "${ctx}/member/list.do",//获取数据的后台地址
			}),
			params:{
				memberPhoneNo : phoneNo,
				cardNo : memberCardNo,
				start:0,
				limit:intPageSize
			}
		}); 
	}
	
	//重置
	function reset(){
		searchForm.getForm().findField("memberPhoneNo").setValue("");
		searchForm.getForm().findField("cardNo").setValue("");
	}
	
	//addfrom
	var addForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		fileUpload : true,
		title : '会员登记',
		labelAlign : 'right',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px 5px 0',
	//	width : 640,
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
					xtype : 'textfield',
					fieldLabel : '会员手机',
					name : 'memberPhoneNo',
					width : 200,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '会员手机号不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'textfield',
						fieldLabel : '会员折扣',
						name : 'discount',
						width : 200,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '会员折扣不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'datefield',
						fieldLabel : '有效期至',
						name : 'validity',
						format : 'Y-m-d',
						width : 200
					}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					addForm.form.submit({
						url : '${ctx}/member/add.do',
						method : 'POST',
						success : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								addWin.hide();
								store.load();
						},
						failure : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								addWin.hide();
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
		title : '会员信息修改',
		labelAlign : 'right',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px 5px 0',
	//	width : 640,
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
					xtype : 'textfield',
					fieldLabel : '会员卡号',
					disabled : true,
					name : 'cardNo',
					width : 200
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'textfield',
						fieldLabel : '持卡人',
						name : 'customer.name',
						width : 200,
						disabled : true
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'memberId'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'textfield',
						fieldLabel : '会员手机号',
						name : 'memberPhoneNo',
						width : 200,
						maxLength : 20,
						maxLengthText : '最大不能超过:{0},(注：必须是正确电话号码)',
						allowBlank : false,
						blankText : '会员电话不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'textfield',
						fieldLabel : '会员折扣',
						name : 'discount',
						width : 200,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:折扣必须是数字)',
						allowBlank : false,
				    	blankText : '会员折扣不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'textfield',
						fieldLabel : '发卡日期',
						name : 'sendDate',
						disabled : true,
						width : 200
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
							xtype : 'datefield',
							fieldLabel : '有效期至',
							name : 'validity',
							width : 200,
							format : 'Y-m-d',
							allowBlank : false,
							blankText : '请选择有效期'
					}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					modForm.form.submit({
						url : '${ctx}/member/update.do',
						method : 'POST',
						success : function(form, action){
								Ext.MessageBox.alert("提示",action.result.msg);
								modWin.hide();
								store.load();
						},
						failure : function(form, action){
								Ext.MessageBox.alert("提示",action.result.msg);
								modWin.hide();
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
				el : 'add_pubwin',
				layout : 'fit',
				closeAction : 'hide',
				width : 330,
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
	
	//修改 
	var modWin = null;
	modBtn.on('click', function(){
		if(gridForm.getSelectionModel().hasSelection()){
			var record = gridForm.getSelectionModel().getSelected();
			if(!modWin){
				modWin = new Ext.Window({
					el : 'mod_pubwin',
					layout : 'fit',
					closeAction : 'hide',
					width : 330,
					height : 300,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : modForm
				});
			}
			modWin.on('show', function() {
				
				var memberId = record.get("memberId");
				var memberPhoneNo = record.get("memberPhoneNo");
				/* var cooperName = record.get("cooperName"); */
				var cardNo = record.get("cardNo");
				var memberName = record.get("memberName");
				var validity = record.get("validity");
				var sendDate = record.get("sendDate");
				var discount = record.get("discount");
				
				var mForm = modForm.getForm();
				mForm.reset();
				mForm.findField('memberId').setValue(memberId);
				mForm.findField('memberPhoneNo').setValue(memberPhoneNo);
				/* mForm.findField('cooperName').setValue(cooperName); */
				mForm.findField('cardNo').setValue(cardNo);
				mForm.findField('customer.name').setValue(memberName);
				mForm.findField('validity').setValue(validity);
				mForm.findField('discount').setValue(discount);
				mForm.findField('sendDate').setValue(new Date(sendDate).Format("yyyy-MM-dd hh:mm:ss"));
				/* mForm.findField('businessLicensePic').setValue(businessLicensePic); */
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
			Ext.MessageBox.confirm('提示', '确认要删除这条记录吗?', submitDel);
			function submitDel(btn){
				if(btn == 'yes'){
					Ext.MessageBox.wait("数据正在处理中,请稍後...", "提交");
					var record = gridForm.getSelectionModel().getSelected();
					var id = record.get('memberId');
					Ext.Ajax.request({
						url : '${ctx}/member/delete.do',
						method : 'POST',
						params: { memberId: id },
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
	<div id="add_pubwin"></div>
	<div id="mod_pubwin"></div>
</body>
</html>