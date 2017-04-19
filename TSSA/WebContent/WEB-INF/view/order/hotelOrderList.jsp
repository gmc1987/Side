<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageMode" value="${pageMode}"></c:set>
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="商户订单" />
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<script type="text/javascript">

Ext.onReady(function(){
	var intPageSize =10;
	
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
				columns : 7
			},
			items : [{
				layout : 'form',
				items : [{
					id : 'orderId',
					xtype : 'textfield',
					fieldLabel : '订单编号',
					name : 'orderId',
					width : 80,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [{
					id : 'custId', 
					xtype : 'textfield',
					fieldLabel : '客户编号',
					name : 'custId',
					width : 80,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [{
					id : 'custName', 
					xtype : 'textfield',
					fieldLabel : '客户姓名',
					name : 'custName',
					width : 80,
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
  	var orderStore = new Ext.data.JsonStore({ 
 		url:'${ctx}/order/list.do',
		autoLoad:true,  
 		totalProperty:'count',
		root:'pageMode.records',
 		fields:[{name:'orderId', mapping:'orderId'},
		        {name:'channelCode', mapping:'channelCode'},
		        {name:'productCode', mapping:'products'},
		        {name:'productName', mapping:'products'},
		        {name:'description', mapping:'products'},
		        {name:'singlePrice', mapping:'products'},
		        {name:'salePrice', mapping:'products'},
		        {name:'totalPrice', mapping:'settlementPrice'},
		        {name:'vendorCode', mapping:'vendorCode'},
		        {name:'vendorName', mapping:'vendorName'},
				{name:'typeCode', mapping:'products'},
				{name:'typeName', mapping:'products'},
				{name:'custId', mapping:'custId'},
				{name:'custName', mapping:'custName'},
				{name:'custPhone', mapping:'custPhone'},
				{name:'extraMessage', mapping:'extraMessage'},
				{name:'appointmentTime', type:'date', mapping:'appointmentTime.time', dateFormat :'time'},
				{name:'startDate', type:'date', mapping:'startDate.time', dateFormat:'time'},
				{name:'endDate', type:'date', mapping:'endDate.time', dateFormat:'time'},
				{name:'inNum', mapping:'inNum'},
				{name:'createDate', type:'date', mapping:'createDate.time', dateFormat :'time'}],
		listeners:{
			"load":function showColumns() {
						for(var i=0; i<orderStore.getCount(); i++){  
							var record = orderStore.getAt(i);
							//循环数据列
		 	    	  	 	for(var j = 0; j < colModel.getColumnCount(); j++){
		 	    	  	 		//取数据索引值
		 	    	  			var dataIndex = colModel.getDataIndex(j);
		 	    	  	 		//从store里判断数据索引值是否存在
		 	    	  		 	if(typeof(record.get(dataIndex)) == "undefined"){  
		 	    	  		 		gridForm.getColumnModel().setHidden(j,true);  
					            } 
		 	    	  	 	}
			 	       } 
					}
				}
	}); 
  	
  	/*产品编码*/
  	function productCodeColumn(value,cellmeta,record,rowIndex,columnIndex,store){
  		var dataIndex = colModel.getDataIndex(columnIndex);
  		var products = record.get(dataIndex);
  		var str = new Array();
  		for(var i = 0; i < products.length; i++){
  			str.push(products[i].productCode);
  		}
  		if(products.length > 1){
  			str.join(",");
  		}
  		return str.toString();
  	}
  	
  	/*产品名称*/
  	function productNameColumn(value,cellmeta,record,rowIndex,columnIndex,store){
  		var dataIndex = colModel.getDataIndex(columnIndex);
  		var products = record.get(dataIndex);
  		var str = new Array();
  		for(var i = 0; i < products.length; i++){
  			str.push(products[i].productName);
  		}
  		if(products.length > 1){
  			str.join(",");
  		}
  		return str.toString();
  	}
  	
  	/*产品类型编码*/
  	function typeCodeColumn(value,cellmeta,record,rowIndex,columnIndex,store){
  		var dataIndex = colModel.getDataIndex(columnIndex);
  		var products = record.get(dataIndex);
  		var str = new Array();
  		for(var i = 0; i < products.length; i++){
  			str.push(products[i].productType.typeCode);
  		}
  		if(products.length > 1){
  			str.join(",");
  		}
  		return str.toString();
  	}
  	
  	/*产品类型名称*/
  	function typeNameColumn(value,cellmeta,record,rowIndex,columnIndex,store){
  		var dataIndex = colModel.getDataIndex(columnIndex);
  		var products = record.get(dataIndex);
  		var str = new Array();
  		for(var i = 0; i < products.length; i++){
  			str.push(products[i].productType.typeName);
  		}
  		if(products.length > 1){
  			str.join(",");
  		}
  		return str.toString();
  	}
  	
  	/*产品描述*/
  	function descriptionColumn(value,cellmeta,record,rowIndex,columnIndex,store){
  		var dataIndex = colModel.getDataIndex(columnIndex);
  		var products = record.get(dataIndex);
  		var str = new Array();
  		for(var i = 0; i < products.length; i++){
  			str.push(products[i].description);
  		}
  		if(products.length > 1){
  			str.join(",");
  		}
  		return str.toString();
  	}
  	
	/*产品单价*/
  	function singlePriceColumn(value,cellmeta,record,rowIndex,columnIndex,store){
  		var dataIndex = colModel.getDataIndex(columnIndex);
  		var products = record.get(dataIndex);
  		var str = new Array();
  		for(var i = 0; i < products.length; i++){
  			str.push(products[i].singlePrice);
  		}
  		if(products.length > 1){
  			str.join(",");
  		}
  		return str.toString();
  	}
	
	/*产品优惠价*/
  	function salePriceColumn(value,cellmeta,record,rowIndex,columnIndex,store){
  		var dataIndex = colModel.getDataIndex(columnIndex);
  		var products = record.get(dataIndex);
  		var str = new Array();
  		for(var i = 0; i < products.length; i++){
  			str.push(products[i].salePrice);
  		}
  		if(products.length > 1){
  			str.join(",");
  		}
  		return str.toString();
  	}
	
  	/*订单总额＋单位显示*/
  	function totalPriceColumn(value,cellmeta,record,rowIndex,columnIndex,store){
  		return value + " 元";
  	}
  	
	
 	var colModel = new Ext.grid.ColumnModel([{
		header:'订单编号',
		width:100,
		sortable : true,
		dataIndex : 'orderId'
	},{
		header:'客户编码',
		width:80,
		sortable:true,
		dataIndex:'custId'
	},{
		header:'客户名称',
		width:80,
		sortable:true,
		dataIndex:'custName'
	},{
		header:'客户手机',
		width:80,
		sortable:true,
		dataIndex:'custPhone'
	},{
		header:'商户编码',
		width:80,
		sortable:true,
		dataIndex:'vendorCode'
	},{
		header:'商户名称',
		width:80,
		sortable:true,
		dataIndex:'vendorName'
	},{
		
		header:'产品类型编码',
		width:80,
		sortable:true,
		dataIndex:'typeCode',
		hidden : true,
		renderer:typeCodeColumn
	},{
		header:'类型名称',
		width:80,
		sortable:true,
		dataIndex:'typeName',
		hidden : true,
		renderer:typeNameColumn
	},{
		header : "产品编号",
		width : 80,
		sortable : true,
		dataIndex : 'productCode',
		hidden : true,
		renderer:productCodeColumn
	},{
		header : "产品名称",
		width : 80,
		sortable : true,
		dataIndex : 'productName',
		hidden : true,
		renderer:productNameColumn
	},{
		header : "产品描述",
		width : 130,
		sortable : true,
		dataIndex : 'description',
		hidden : true,
		renderer:descriptionColumn
	},{
		header : "单价",
		width : 80,
		sortable : true,
		dataIndex : 'singlePrice',
		hidden : true,
		renderer:singlePriceColumn
	},{
		header:'优惠价',
		width:80,
		sortable:true,
		dataIndex:'salePrice',
		hidden : true,
		renderer:salePriceColumn
	},{
		header:'总价',
		width:80,
		sortable:true,
		dataIndex:'totalPrice',
		renderer:totalPriceColumn
	},/* {
		header:'预订桌数',
		width:120,
		sortable:true,
		dataIndex:'quantity'
	},{
		header:'就餐人数',
		width:120,
		sortable:true,
		dataIndex:'population'
	}, */{
		header:'订单附加信息',
		width:120,
		sortable:true,
		dataIndex:'extraMessage'
	},{
		header:'预约时间',
		width:120,
		sortable:true,
		dataIndex:'appointmentTime',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},{
		header:'入住日期',
		width:120,
		sortable:true,
		dataIndex:'startDate',
		renderer : Ext.util.Format.dateRenderer('Y-m-d')
	},{
		header:'离开日期',
		width:120,
		sortable:true,
		dataIndex:'endDate',
		renderer : Ext.util.Format.dateRenderer('Y-m-d')
	},{
		header:'入住天数',
		width:80,
		sortable:true,
		dataIndex:'inNum'
	},{
		header:'创建时间',
		width:120,
		sortable:true,
		dataIndex:'createDate',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}]);  
 	
 	
	//订单数据集
	var gridForm = new Ext.grid.GridPanel({
		id : 'opercode-grid',
		store : orderStore,
		cm : colModel,  
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.PagingToolbar({
			pageSize : intPageSize,
			store : orderStore,
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
	
	var infoWin = null;
	gridForm.on('rowdblclick', function(){
		var record=gridForm.getSelectionModel().getSelected();
		if(!infoWin){
			infoWin = new Ext.Window({
				el : 'order_info',
				layout : 'fit',
				closeAction : 'hide',
				width : 750,
				height : 550,
				modal : true,
				collapsible : true,
				constrain : true,
				resizable : false,
				items : infoForm
			});
		}
		infoWin.on('show', function() {
			
			var orderId = record.get("orderId");
			var custName = record.get("custName");
			var phone = record.get("custPhone");
			var createDate = record.get("createDate");
			var appointmentTime = record.get("appointmentTime");
			var products = record.get("productCode");
			var extraMessage = record.get("extraMessage");
			
			var iForm = infoForm.getForm();
			iForm.reset();
			iForm.findField('orderId').setValue(orderId);
			iForm.findField('custName').setValue(custName);
			iForm.findField('phone').setValue(phone);
			iForm.findField('createDate').setValue(Ext.util.Format.date(createDate, 'Y-m-d H:i:s'));
			iForm.findField('appointmentTime').setValue(Ext.util.Format.date(appointmentTime, 'Y-m-d H:i:s'));
			iForm.findField('extraMessage').setValue(extraMessage);
			
			if(products != null){
				productGrid.getStore().load({
					proxy : new Ext.data.HttpProxy({
						url : "${ctx}/cooperationProduct/orderProductList.do",//获取数据的后台地址
					}),
					params:{
						orderId : orderId
					}
				});
			}
		});
		infoWin.show(this);
	});
	
	//查询
	function search(){
		var oid = searchForm.getForm().findField("orderId").getValue();
		var cName = searchForm.getForm().findField("custName").getValue();
		var cid = searchForm.getForm().findField("custId").getValue();
		var sDate = searchForm.getForm().findField("startDate").getValue();
		var eDate = searchForm.getForm().findField("endDate").getValue();
		gridForm.getStore().load({
			proxy : new Ext.data.HttpProxy({
				url : "${ctx}/order/list.do",//获取数据的后台地址
			}),
			params:{
				orderId : oid,
				custName : cName,
				custId : cid,
				startDate : Ext.util.Format.date(sDate, 'Y-m-d'),
				endDate : Ext.util.Format.date(eDate, 'Y-m-d'),
				pageNumber : 0,
				pageSize : intPageSize
			}
		}); 
	}
	
	//重置
	function reset(){
		    searchForm.getForm().findField("orderId").setValue("");
			searchForm.getForm().findField("custName").setValue("");
			searchForm.getForm().findField("custId").setValue("");
			searchForm.getForm().findField("productName").setValue("");
			searchForm.getForm().findField("productId").setValue("");
			searchForm.getForm().findField("startDate").reset();
			searchForm.getForm().findField("endDate").reset();
	}
	
	//订单详情产品列表
	var productStore = new Ext.data.JsonStore({ 
 		url:'${ctx}/cooperationProduct/orderProductList.do',
		root:'result',
 		fields:[{name:'businessProductId', mapping:'businessProductId'},
		        {name:'productCode', mapping:'productCode'},
		        {name:'productName', mapping:'productName'},
		        {name:'description', mapping:'description'},
		        {name:'singlePrice', mapping:'singlePrice'},
		        {name:'salePrice', mapping:'salePrice'}]
	}); 
	
//	productStroe.load();
	
 	var productColModel = new Ext.grid.ColumnModel([{
		header:'uuid',
		width:100,
		sortable : true,
		dataIndex : 'businessProductId',
		hidden : true
	},{
		header : "产品编号",
		width : 80,
		sortable : true,
		dataIndex : 'productCode',
		hidden : true
	},{
		header : "产品名称",
		width : 220,
		sortable : true,
		dataIndex : 'productName',
	},{
		header : "产品描述",
		width : 330,
		sortable : true,
		dataIndex : 'description'
	},{
		header : "单价",
		width : 80,
		sortable : true,
		dataIndex : 'singlePrice'
	},{
		header:'优惠价',
		width:80,
		sortable:true,
		dataIndex:'salePrice'
	}]);  
	
	//数据集
	var productGrid = new Ext.grid.GridPanel({
		id : 'product-grid',
		store : productStore,
		cm : productColModel, 
		width : '100%',
		height : 230,
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.PagingToolbar({
			pageSize : intPageSize,
			store : productStore,
			beforePageText : '第',
			afterPageText : '页,共 {0} 页',
			displayInfo : true,
			displayMsg : '{0} - {1} , 共 {2} 条',
			emptyMsg : "无记录"
		}),
		frame : false,
		collapsible : true,
		animCollapse : true,
//		trackMouseOver : false,
		iconCls : 'icon-grid',
		loadMask : ({
			msg : '数据正在加载中,请稍後...'
		}),
	});
	
	//订单详情
	var infoForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		fileUpload : true,
		title : '订单详情',
		labelAlign : 'left',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px 5px 0',
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
					xtype : 'displayfield',
					fieldLabel : '订单编号',
					name : 'orderId',
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'displayfield',
						fieldLabel : '客户姓名',
						name : 'custName',
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'displayfield',
						fieldLabel : '联系电话',
						name : 'phone',
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items:[{
						xtype : 'displayfield',
						fieldLabel : '下单时间',
						name : 'createDate',
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items: [{
						xtype : 'displayfield',
						fieldLabel : '预约时间',
						name : 'appointmentTime',
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'displayfield',
						fieldLabel : '订单附加信息',
						name : 'extraMessage',
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [{
						xtype : 'displayfield',
						fieldLabel : '预订产品信息',
					}]
				},{
					layout : 'form',
					border : true,
					colspan : 2,
					items : [productGrid]
				}]
			}],
			buttons :[{
				text : '关闭',
				handler : function(){
					infoWin.hide();
				}
			}]
	});
});

</script>
</head>
<body>
	<div id="toolBar"></div>
	<div id="searchFormDiv"></div>
	<div id="dataGrild"></div>
	<div id="productGrid"></div>
	<div id="order_info"></div>
</body>
</html>