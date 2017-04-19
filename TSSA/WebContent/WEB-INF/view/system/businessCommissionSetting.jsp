<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageTree" value="true"></c:set>
<c:set var="pageExt" value="true"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="商户佣金设置管理" />
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle}</title>
<script type="text/javascript">
Ext.onReady(function(){
	
	var intPageSize = 10;
	
	 var cycleUnit = [[0, '日'], [1, '周'],[2, '月'],[3,'季度'],[4,'年']];
	 var unitStatus = new Ext.data.SimpleStore({
		 fields : ['type_id', 'type_name'],
		 data : cycleUnit
	 });
	
	var dsCurStatus = new Ext.data.JsonStore({ 
 		url:'${ctx}/cooperation/getProductType.do',
		autoLoad:true,  
		root:'result',
 		fields:[{name:'productTypeId', mapping:'typeCode'},
		        {name:'typeName', mapping:'typeName'}]
	}); 
	
	var businessType = new Ext.form.ComboBox({
		id : 'businessType',
		name : 'businessType',
		store : dsCurStatus,
		valueField : 'productTypeId',
		displayField : 'typeName',
		fieldLabel : '行业类型',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local'
	}); 
	
	var unitType = new Ext.form.ComboBox({
		id : 'cycleUnit',
		name : 'cycleUnit',
		store : unitStatus,
		valueField : 'type_id',
		displayField : 'type_name',
		fieldLabel : '周期单位',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local'
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
					id : 'vendorCode',
					xtype : 'textfield',
					fieldLabel : '商户编码',
					name : 'vendorCode',
					width : 100,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [{
					id : 'vendorName', 
					xtype : 'textfield',
					fieldLabel : '商户名称',
					name : 'vendorName',
					width : 100,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [businessType]
			}]
		}]
	});
	
	//显示工具栏
	myToolbar.doLayout(); 
	
  	var store = new Ext.data.JsonStore({ 
 		url:'${ctx}/cooperation/list.do',
//		autoLoad:true,  
 		totalProperty:'pageMode.count',
		root:'pageMode.records',
 		fields:[{name:'cooperId', mapping:'cooperId'},
		        {name:'cooperCode', mapping:'cooperCode'},
		        {name:'cooperName', mapping:'cooperName'},
		        {name:'cooperType', mapping:'productTypeId.typeCode'},
		        {name:'cooperTypeName', mapping:'productTypeId.typeName'}],
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
		dataIndex : 'cooperId',
		hidden : true
	},{
		header : "商户编号",
		width : 80,
		sortable : true,
		dataIndex : 'cooperCode',
	},{
		header : "商户名称",
		width : 200,
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
	}]);  
	
	//数据集
	var gridForm = new Ext.grid.GridPanel({
		id : 'opercode-grid',
		store : store,
		cm : colModel,  
		viewConfig : {
			forceFit : false
		},
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
	
	gridForm.addListener('cellclick',showCommissionSetting); 
	
	//查询
	function search(){
		var code = searchForm.getForm().findField("vendorCode").getValue();
		var name = searchForm.getForm().findField("vendorName").getValue();
		var type = businessType.getValue();
		gridForm.getStore().load({
			proxy : new Ext.data.HttpProxy({
				url : "${ctx}/cooperation/list.do",//获取数据的后台地址
			}),
			params:{
				businessCode : code,
				businessName : name,
				businessType : type,
				start:0,
				limit:intPageSize
			}
		}); 
	}
	
	//重置
	function reset(){
		searchForm.getForm().findField("vendorCode").setValue("");
		searchForm.getForm().findField("vendorName").setValue("");
		businessType.reset();
	}
	
	
	/*------------------------------------------settingForm start-----------------------------------------------*/
	
	//工具栏，按钮
 	var settingToolBar = new Ext.Toolbar();
 	settingToolBar.render('settingToolBar');
	
 	settingToolBar.addButton({
		text: '保存', 
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-add.png',
		handler: function(o, e) {
			commissionSave();
		}
	});
 	settingToolBar.addButton({
		text: '删除',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-delete.png',
		handler: function(o, e) {
			commissionDelete();
		}
	});
	
	//显示工具栏
	settingToolBar.doLayout(); 
	
	var commissionSettingForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '佣金设置',
		labelAlign : 'right',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px 5px 0',
		width : '100%',
		height : '100%',
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
				colspan : 2,
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '佣金百分比',
					name : 'commissionPercentage',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '佣金百分比不能为空'
					},{
						xtype : 'hidden',
						name : 'commissionSettingId'
					}]
				},{
					layout : 'form',
					columnWidth : .5,
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '结算周期',
						name : 'cycle',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)'
						}]
				},{
					layout : 'form',
					columnWidth : .5,
					border : true,
					items : [unitType]
				}]
			}]
	});
	
	commissionSettingForm.render('settingForm');
	
	
	function showCommissionSetting(){
		var record = gridForm.getSelectionModel().getSelected();
		var cooperationId = record.get("cooperId");
		
		Ext.Ajax.request({
			url : '${ctx}/businessCommissionSetting/settingInfo.do',
			method : 'POST',
			params: { cooperId: cooperationId },
			success : function(response,request){
					var jsonObj = eval('(' + response.responseText + ')');
					if(jsonObj.setting != null){
						var commissionSettingId = jsonObj.setting.commissionId;
						var commissionPercentage = jsonObj.setting.commissionPercentage;
						var cycle = jsonObj.setting.cycle;
						var unit = jsonObj.setting.cycleUnit;
						var mForm = commissionSettingForm.getForm();
						
						mForm.reset();
						mForm.findField('commissionSettingId').setValue(commissionSettingId);
						mForm.findField('commissionPercentage').setValue(commissionPercentage);
						mForm.findField('cycle').setValue(cycle);
						mForm.findField('cycleUnit').setValue(unit);
					}else{
						var mForm = commissionSettingForm.getForm();
						mForm.reset();
					}
					
			}
		});
	}
	
	function commissionSave(){
		var mForm = commissionSettingForm.getForm();
		var record = gridForm.getSelectionModel().getSelected();
		var cooperationId = record.get("cooperId");
		var commissionSettingId = mForm.findField('commissionSettingId').getValue();
		var commissionPercentage = mForm.findField('commissionPercentage').getValue();
		var cycle = mForm.findField('cycle').getValue();
		var cycleUtin = unitType.getValue();
		
		if(commissionSettingId != null && commissionSettingId != ""){
			Ext.Ajax.request({
				url : '${ctx}/businessCommissionSetting/update.do',
				method : 'POST',
				params: { cooperId: cooperationId,
						  settingId: commissionSettingId,
						  percentage: commissionPercentage,
						  settingCycle: cycle,
						  cycleUtil: cycleUtin
						},
				success : function(response,request){
						var jsonObj = eval('(' + response.responseText + ')');
						if(jsonObj != null){
							Ext.MessageBox.alert("提示","设置成功");
						}
				},
				failure : function(response,request){
					Ext.MessageBox.hide();
					Ext.MessageBox.alert("提示","设置失败");
				}
			});
		}else{
			Ext.Ajax.request({
				url : '${ctx}/businessCommissionSetting/save.do',
				method : 'POST',
				params: { cooperId: cooperationId,
						  percentage: commissionPercentage,
						  settingCycle: cycle
					  	},
				success : function(response,request){
						var jsonObj = eval('(' + response.responseText + ')');
						if(jsonObj != null){
							Ext.MessageBox.alert("提示","设置成功");
						}
				},
				failure : function(response,request){
					Ext.MessageBox.alert("提示","设置失败");
				}
			});
		}
	}
	
	function commissionDelete(){
		var mForm = commissionSettingForm.getForm();
		var commissionSettingId = mForm.findField('commissionSettingId').getValue();
		if(commissionSettingId != null && commissionSettingId != ""){
			Ext.Ajax.request({
				url : '${ctx}/businessCommissionSetting/delete.do',
				method : 'POST',
				params: {
						  settingId: commissionSettingId
						},
				success : function(response,request){
						var jsonObj = eval('(' + response.responseText + ')');
						if(jsonObj != null){
							Ext.MessageBox.alert("提示","设置成功");
							mForm.reset();
						}
				},
				failure : function(response,request){
					Ext.MessageBox.hide();
					Ext.MessageBox.alert("提示","设置失败");
				}
			});
		}
	}
	
});
</script>
</head>
<body>
<div style="width: 100%; height: 100%; padding: 0,0,0,0">
	<div id="cooperationListDiv">
		<div id="toolBar"></div>
		<div id="searchFormDiv"></div>
		<div id="dataGrild"></div>
	</div>
	<div id="settingFormDiv">
		<div id="settingToolBar"></div>
		<div id="settingForm"></div>
	</div>
</div>
</body>
</html>