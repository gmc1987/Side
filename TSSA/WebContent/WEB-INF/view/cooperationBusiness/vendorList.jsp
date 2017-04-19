<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageMode" value="${pageMode}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="商户列表" />
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<script type="text/javascript">
Ext.onReady(function(){
	
	var intPageSize =10;
 	/* var type_src = [['03', '餐饮'], ['00', '娱乐'],['02', '购物'],['01','酒店住宿'],['04','旅游']];
	var dsCurStatus = new Ext.data.SimpleStore({
		fields : ['productTypeId', 'typeName'],
		data : type_src
	});  */
	
	var dsCurStatus = new Ext.data.JsonStore({ 
 		url:'${ctx}/cooperation/getProductType.do',
		autoLoad:true,  
		root:'result',
 		fields:[{name:'productTypeId', mapping:'typeCode'},
		        {name:'typeName', mapping:'typeName'}]
	}); 
	
	var childProductStore = new Ext.data.JsonStore({
		url : '${ctx}/cooperationProduct/getChildProductType.do',
		autoLoad : true,
		root : 'result',
		fields : [ {
			name : 'productTypeId',
			mapping : 'typeCode'
		}, {
			name : 'typeName',
			mapping : 'typeName'
		} ]
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
		mode : 'local',
		listeners : {
			select : function(ProjectCombox, record, index) {
				//  
				childProductStore.proxy = new Ext.data.HttpProxy(
						{
							url : '${ctx}/cooperationProduct/getChildProductType.do?parentType='
									+ businessType.value
						});
				childProductStore.load();
			}
		}
	}); 
	
	//子类
	var childsType = new Ext.form.ComboBox({
		id : 'childsType',
		name : 'childsType',
		store : childProductStore,
		valueField : 'productTypeId',
		displayField : 'typeName',
		fieldLabel : '行业细分',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local'
	});
	
	/*国家*/
	var dsCountryStatus = new Ext.data.JsonStore({
		url:'${ctx}/location/getCountry.do',
		autoLoad:true,
		root:'contries',
		fields:[{name:'countryIds', mapping:'id'},
		        {name:'countryName', mapping:'name'}]
	});
	
	var countriesType = new Ext.form.ComboBox({
		id : 'countryId',
		name : 'countryId',
		store : dsCountryStatus,
		valueField : 'countryIds',
		displayField : 'countryName',
		fieldLabel : '国家',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local',
		listeners:{
			select : function(ProjectCombox, record, index){
				dsProvinceStatus.proxy= new Ext.data.HttpProxy({
	                	url: '${ctx}/location/getProvince.do?countryId=' + countriesType.value
	                			});    
				dsProvinceStatus.load();
			}
		}
	}); 
	
	/*省份*/
	var dsProvinceStatus = new Ext.data.JsonStore({
		url:'${ctx}/location/getProvince.do',
		autoLoad:false,
		root:'provincies',
		fields:[{name:'provinceIds', mapping:'id'},
		        {name:'provinceName', mapping:'name'}]
	});
	
	var provinceType = new Ext.form.ComboBox({
		id : 'provinceId',
		name : 'provinceId',
		store : dsProvinceStatus,
		valueField : 'provinceIds',
		displayField : 'provinceName',
		fieldLabel : '省份',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local',
		listeners:{
			select : function(ProjectCombox, record, index){
				dsCityStatus.proxy= new Ext.data.HttpProxy({
	                	url: '${ctx}/location/getCity.do?provinceId=' + provinceType.value
	                			});    
				dsCityStatus.load();
			}
		}
	}); 
	
	/*城市*/
	var dsCityStatus = new Ext.data.JsonStore({
		url:'${ctx}/location/getCity.do',
		autoLoad:false,
		root:'cities',
		fields:[{name:'cityIds', mapping:'id'},
		        {name:'cityName', mapping:'name'}]
	});
	
	var cityType = new Ext.form.ComboBox({
		id : 'cityId',
		name : 'cityId',
		store : dsCityStatus,
		valueField : 'cityIds',
		displayField : 'cityName',
		fieldLabel : '城市',
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
			},{
				layout : 'form',
				items : [childsType]
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
				{name:'allianceDate', type:'date', mapping:'allianceDate.time', dateFormat :'time'}],
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
		var code = searchForm.getForm().findField("vendorCode").getValue();
		var name = searchForm.getForm().findField("vendorName").getValue();
		var type = businessType.getValue();
		var childType = childsType.getValue();
		gridForm.getStore().load({
			proxy : new Ext.data.HttpProxy({
				url : "${ctx}/cooperation/list.do",//获取数据的后台地址
			}),
			params:{
				businessCode : code,
				businessName : name,
				businessType : type,
				businessChildType : childType,
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
		childsType.reset();
	}
	
	var vendorType = new Ext.form.ComboBox({
		id : 'vendorType',
		name : 'vendorType',
		hiddenName : "vendorTypeName",
		store : dsCurStatus,
		valueField : 'productTypeId',
		displayField : 'typeName',
		fieldLabel : '行业类型',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 200,
		editable : false,
		mode : 'local',
		listeners : {
			select : function(ProjectCombox, record, index) {
				//  
				childProductStore.proxy = new Ext.data.HttpProxy(
						{
							url : '${ctx}/cooperationProduct/getChildProductType.do?parentType='
									+ vendorType.value
						});
				childProductStore.load();
			}
		}
	}); 
	
	var childsType1 = new Ext.form.ComboBox({
		id : 'childsType1',
		name : 'childsType',
		hiddenName : "childTypeId",
		store : childProductStore,
		valueField : 'productTypeId',
		displayField : 'typeName',
		fieldLabel : '行业细分',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 200,
		editable : false,
		mode : 'local'
	});
	
	var vendorType2 = new Ext.form.ComboBox({
		id : 'vendorType2',
		name : 'vendorType2',
		hiddenName : "vendorTypeName",
		store : dsCurStatus,
		valueField : 'productTypeId',
		displayField : 'typeName',
		fieldLabel : '行业类型',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 200,
		editable : false,
		mode : 'local',
		listeners : {
			select : function(ProjectCombox, record, index) {
				//  
				childProductStore.proxy = new Ext.data.HttpProxy(
						{
							url : '${ctx}/cooperationProduct/getChildProductType.do?parentType='
									+ vendorType2.value
						});
				childProductStore.load();
			}
		}
	}); 
	
	var childsType2 = new Ext.form.ComboBox({
		id : 'childsType2',
		name : 'childsType',
		hiddenName : "childTypeId",
		store : childProductStore,
		valueField : 'productTypeId',
		displayField : 'typeName',
		fieldLabel : '行业细分',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 200,
		editable : false,
		mode : 'local'
	});
	
	//addfrom
	var addForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		fileUpload : true,
		title : '商户新增',
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
				columnWidth : .5,
				items:[{
					xtype : 'textfield',
					fieldLabel : '商户名称',
					name : 'cooperationName',
					width : 200,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '商户名称不能为空'
					}]
				},{
					layout : 'form',
					columnWidth : .5,
					items : [{
						xtype : 'textfield',
						fieldLabel : '上传logo',
						name : 'logoImageURL',
						width : 200,
						inputType : 'file',
						buttonText: '浏览'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						fieldLabel : '联系电话1',
						name : 'tel1',
						width : 200,
						maxLength : 20,
						maxLengthText : '最大不能超过:{0},(注：必须是正确电话号码)',
						allowBlank : false,
						blankText : '联系电话不能为空'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
							xtype : 'textfield',
							fieldLabel : '联系电话2',
							name : 'tel2',
							width : 200,
							maxLength : 20,
							maxLengthText : '最大不能超过:{0},(注：必须是正确电话号码)',
							allowBlank : false,
							blankText : '联系电话不能为空'
					}]
				},{
					layout : 'form',
					columnWidth : .5,
					items : [vendorType]
				},{
					layout : 'form',
					columnWidth : .5,
					items : [childsType1]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'country'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'province'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'city'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'countryText'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'provinceText'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'cityText'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'detailAddressText'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'textfield',
						fieldLabel : '地址',
						name : 'address',
						width : 490,
						maxLength : 300,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '地址不能为空',
						listeners : {
							'focus' : function(){
								if(!addressWin){
									addressWin = new Ext.Window({
										el : 'address_win',
										layout : 'fit',
										closeAction : 'hide',
										width : 720,
										height : 170,
										modal : true,
										collapsible : true,
										constrain : true,
										resizable : false,
										items : addressForm
									});
								}
								addressWin.on('show', function(){
									addressForm.getForm().reset();
									addressForm.getForm().findField("saveOrUpdate").setValue(0);
								});
								addressWin.show(this);
							}
						}
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'textfield',
						fieldLabel : '营业执照编号',
						name : 'businessLicense',
						width : 490,
						maxLength : 300,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '营业执照编号不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'textfield',
						fieldLabel : '上传营业执照',
						name : 'businessLicensePic',
						width : 400,
						inputType : 'file',
						buttonText: '浏览'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
							xtype : 'textfield',
							fieldLabel : '银行帐号',
							name : 'account',
							width : 200,
							maxLength : 32,
							maxLengthText : '最大不能超过:{0},(注：正确银行帐号)',
							allowBlank : false,
							blankText : '银行帐号不能为空'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
							xtype : 'button',
							name : 'check',
							width : 100,
							text : '验证银行帐户',
							handler : function(){
								alert("验证银行帐号未开放！");
							}
					}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					addForm.form.submit({
						url : '${ctx}/cooperation/save.do',
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
		title : '商户修改',
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
				columnWidth : .5,
				items:[{
					xtype : 'textfield',
					fieldLabel : '商户名称',
					name : 'cooperationName',
					width : 200,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '商户名称不能为空'
					}]
				},{
					layout : 'form',
					columnWidth : .5,
					items : [{
						xtype : 'textfield',
						fieldLabel : '上传logo',
						name : 'logoImageURL',
						width : 200,
						inputType : 'file',
						buttonText: '浏览'
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'cooperationId'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'cooperationCode'
						}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						fieldLabel : '联系电话1',
						name : 'tel1',
						width : 200,
						maxLength : 20,
						maxLengthText : '最大不能超过:{0},(注：必须是正确电话号码)',
						allowBlank : false,
						blankText : '联系电话不能为空'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
							xtype : 'textfield',
							fieldLabel : '联系电话2',
							name : 'tel2',
							width : 200,
							maxLength : 20,
							maxLengthText : '最大不能超过:{0},(注：必须是正确电话号码)',
							allowBlank : false,
							blankText : '联系电话不能为空'
					}]
				},{
					layout : 'form',
					columnWidth : .5,
					items : [vendorType2]
				},{
					layout : 'form',
					columnWidth : .5,
					items : [childsType2]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'country'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'province'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'city'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'countryText'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'provinceText'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'cityText'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'hidden',
						name : 'detailAddressText'
						}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'textfield',
						fieldLabel : '地址',
						name : 'address',
						width : 490,
						maxLength : 300,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '地址不能为空',
						listeners : {
							'focus' : function(){
								if(!addressWin){
									addressWin = new Ext.Window({
										el : 'address_win',
										layout : 'fit',
										closeAction : 'hide',
										width : 720,
										height : 170,
										modal : true,
										collapsible : true,
										constrain : true,
										resizable : false,
										items : addressForm
									});
								}
								addressWin.on('show',function(){
									addressForm.getForm().reset();
									addressForm.getForm().findField("saveOrUpdate").setValue(1);
									var countryId = modForm.getForm().findField("country").getValue();
									var provinceId = modForm.getForm().findField("province").getValue();
									var cityId = modForm.getForm().findField("city").getValue();
									countriesType.setValue(countryId);
									provinceType.setValue(provinceId);
									cityType.setValue(cityId);
								});
								addressWin.show(this);
							}
						}
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'textfield',
						fieldLabel : '营业执照编号',
						name : 'businessLicense',
						width : 490,
						maxLength : 300,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '营业执照编号不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'textfield',
						fieldLabel : '上传营业执照',
						name : 'businessLicensePic',
						width : 400,
						inputType : 'file',
						buttonText: '浏览'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
							xtype : 'textfield',
							fieldLabel : '银行帐号',
							name : 'account',
							width : 200,
							maxLength : 32,
							maxLengthText : '最大不能超过:{0},(注：正确银行帐号)',
							allowBlank : false,
							blankText : '银行帐号不能为空'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
							xtype : 'button',
							name : 'check',
							width : 100,
							text : '验证银行帐户',
							handler : function(){
								alert("验证银行帐号未开放！");
							}
					}]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					modForm.form.submit({
						url : '${ctx}/cooperation/update.do',
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
	
	var addressWin = null;
	/*地址选择*/
	var addressForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		fileUpload : true,
		title : '选择地址',
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
				columns : 3
			},
			items : [{
				layout : 'form',
				border : true,
				colspan : 3,
				items:[{
					xtype : 'hidden',
					name : 'saveOrUpdate'
					}]
				}, {
					layout : 'form',
					border : true,
					items : [countriesType]
				}, {
					layout : 'form',
					border : true,
					items : [provinceType]
				}, {
					layout : 'form',
					border : true,
					items : [cityType]
				},{
					layout : 'form',
					border : true,
					colspan : 3,
					items : [{
						xtype : 'textfield',
						fieldLabel : '详细地址',
						name : 'detailedAddress',
						width : 530,
						maxLength : 310,
						maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
						blankText : '地址不能为空'
					}]
				}]
		}],
		buttons :[{
			text : '确定',
			handler : function(){
				var status = addressForm.getForm().findField("saveOrUpdate").getValue();
				var country = addressForm.getForm().findField("countryId").getValue();
				var province = addressForm.getForm().findField("provinceId").getValue();
				var city = addressForm.getForm().findField("cityId").getValue();
				var countryText = Ext.get('countryId').dom.value;
				var provinceText = Ext.get("provinceId").dom.value;
				var cityText = Ext.get("cityId").dom.value;
				var detailedAddress = addressForm.getForm().findField("detailedAddress").getValue();
				if(status == 0){
					var address = countryText + " - " + provinceText + " - " + cityText + " - " + detailedAddress;
					addForm.getForm().findField("address").setValue(address);
					addForm.getForm().findField("country").setValue(country);
					addForm.getForm().findField("province").setValue(province);
					addForm.getForm().findField("city").setValue(city);
					addForm.getForm().findField("countryText").setValue(countryText);
					addForm.getForm().findField("provinceText").setValue(provinceText);
					addForm.getForm().findField("cityText").setValue(cityText);
					addForm.getForm().findField("detailAddressText").setValue(detailedAddress);
				}else{
					var address = countryText + " - " + provinceText + " - " + cityText + " - " + detailedAddress;
					modForm.getForm().findField("address").setValue(address);
					modForm.getForm().findField("country").setValue(country);
					modForm.getForm().findField("province").setValue(province);
					modForm.getForm().findField("city").setValue(city);
					modForm.getForm().findField("countryText").setValue(countryText);
					modForm.getForm().findField("provinceText").setValue(provinceText);
					modForm.getForm().findField("cityText").setValue(cityText);
					modForm.getForm().findField("detailAddressText").setValue(detailedAddress);
				}
				addressWin.hide();
			}
		},{
			text : '取消',
			handler : function(){
				addressWin.hide();
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
				width : 720,
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
	modBtn.on('click', function(){
		if(gridForm.getSelectionModel().hasSelection()){
			var record = gridForm.getSelectionModel().getSelected();
			if(!modWin){
				modWin = new Ext.Window({
					el : 'mod_pubwin',
					layout : 'fit',
					closeAction : 'hide',
					width : 720,
					height : 350,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : modForm
				});
			}
			modWin.on('show', function() {
				var cooperId = record.get("cooperId");
				var cooperCode = record.get("cooperCode");
				var cooperName = record.get("cooperName");
				var cooperType = record.get("cooperType");
				var cooperTypeName = record.get("cooperTypeName");
				var account = record.get("account");
				var address = record.get("address");
				var tel1 = record.get("tel1");
				var tel2 = record.get("tel2");
				var businessLicense = record.get("businessLicense");
				var parentCooperType = record.get("parentCooperType");
		        /* var parentCooperTypeName = record.get("parentCooperTypeName"); */
				/* var businessLicensePic = record.get("pictureUrl"); */
				
				childProductStore.proxy = new Ext.data.HttpProxy(
						{
							url : '${ctx}/cooperationProduct/getChildProductType.do?parentType='
									+ parentCooperType
						});
				childProductStore.load();
				
				var mForm = modForm.getForm();
				mForm.reset();
				mForm.findField('cooperationId').setValue(cooperId);
				mForm.findField('cooperationName').setValue(cooperName);
				mForm.findField('cooperationCode').setValue(cooperCode);
			//	mForm.findfield('vendorTypeName').setValue(cooperType);
				mForm.findField('vendorType2').setValue(parentCooperType);
				mForm.findField('childsType2').setValue(cooperType);
				mForm.findField('address').setValue(address);
				mForm.findField('businessLicense').setValue(businessLicense);
				mForm.findField('tel1').setValue(tel1);
				mForm.findField('tel2').setValue(tel2);
				mForm.findField('account').setValue(account); 
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
					var id = record.get('cooperId');
					Ext.Ajax.request({
						url : '${ctx}/cooperation/delete.do',
						method : 'POST',
						params: { cooperId: id },
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
	<div id="address_win"></div>
</body>
</html>