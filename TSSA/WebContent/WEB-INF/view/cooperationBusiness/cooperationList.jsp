<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageTree" value="true"></c:set>
<c:set var="pageExt" value="true"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="商户资料管理" />
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle}</title>
</head>
<body>
	<div id="vendorInfo" style="text-align:left" height="100%">
		<iframe id="vendorInfo_frame" name="vendorInfo_frame" frameborder="0" height="100%" width="100%" scrolling="no" style="border:0px none;"></iframe>
	</div> 
	<div id="product_list" style="text-align:left" height="100%">
		<iframe id="product_list_frame" name="product_list_frame" frameborder="0" height="100%" width="100%" scrolling="no" style="border:0px none;"></iframe>
	</div>
	<div id="vendor_account" style="text-align:left" height="100%">
		<iframe id="vendor_account_frame" name="vendor_account_frame" frameborder="0" height="100%" width="100%" scrolling="no" style="border:0px none;"></iframe>
	</div>
	<div id="business_order" style="text-align:left" height="100%">
		<iframe id="business_order_frame" name="business_order_frame" frameborder="0" height="100%" width="100%" scrolling="no" style="border:0px none;"></iframe>
	</div>
</body>

<script type="text/javascript">
Ext.onReady(function(){
	
	var businessTabPanel = new Ext.TabPanel({
		id:'doc-body',
		region:'center',
		autoScroll:true,
		enableTabScroll : true,
		deferredRender:false,
		activeTab:0
	});
	
	//商户信息
	var vendorInfoPanel = new Ext.Panel({
		html:'',
		contentEl:'vendorInfo',
		title: '商户信息',
		closable:false,
		autoScroll:true
	});
	businessTabPanel.add(vendorInfoPanel).show();
	vendorInfoPanel.on('activate',openVendorInfoFrame);
	
	//商户产品
	var productListPanel = new Ext.Panel({
		html:'',
		contentEl:'product_list',
		title: '商户产品列表',
		closable:false,
		autoScroll:true
	});
	businessTabPanel.add(productListPanel);
	productListPanel.on('activate',openProductListFrame);
	
	//商户对帐单
	var vendorAccountPanel = new Ext.Panel({
		html:'',
		contentEl:'vendor_account',
		title: '商户对帐单',
		closable:false,
		autoScroll:true
	});
	businessTabPanel.add(vendorAccountPanel);
	vendorAccountPanel.on('activate',openVendorAccountPanelFrame);
	
	//商户订单
	var businessOrderPanel = new Ext.Panel({
		html:'',
		contentEl:'business_order',
		title: '商户订单',
		closable:false,
		autoScroll:true
	});
	businessTabPanel.add(businessOrderPanel);
	businessOrderPanel.on('activate',openBusinessOrderFrame);
	
	
	new Ext.Viewport({
		layout:'border',
		items:[new Ext.BoxComponent(
				{
					region : 'north',
					height : 30,
					autoEl : {
						tag : 'div',
						html : "<p style='line-height:30px;font-size:17px'>${title}</p>"
					}
				}),businessTabPanel]
	});
	
});

function openVendorInfoFrame(){
	if(Ext.get("vendorInfo_frame"))
		Ext.get("vendorInfo_frame").dom.src = "${ctx}/cooperation/toVendorList.do?cacheflag="+new Date().getTime();
}

function openProductListFrame(){
	if(Ext.get("product_list_frame"))
		Ext.get("product_list_frame").dom.src = "${ctx}/cooperation/toVendorProductPage.do?cacheflag="+new Date().getTime();
}

function openVendorAccountPanelFrame(){
	if(Ext.get("vendor_account_frame"))
		Ext.get("vendor_account_frame").dom.src = "";
}

function openBusinessOrderFrame(){
	if(Ext.get("business_order_frame"))
		Ext.get("business_order_frame").dom.src = "${ctx}/order/toList.do?cacheflag="+new Date().getTime();
}

</script>
</html>