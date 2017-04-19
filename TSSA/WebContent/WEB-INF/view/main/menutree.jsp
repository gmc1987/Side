<?xml version='1.0' encoding='UTF-8'?>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%response.setContentType("text/xml");%>
<tree id="0">
	<item text="主页" id="index" select="1"/>
	
	<item text="消息管理" id="MessageManager" open="true">
		<item text="新建消息" id="MessageAddNew" />
		<item text="收件箱" id="MessageReceiver" />
		<item text="发件箱" id="MessageSender" />
		<item text="草稿箱" id="MessageDraft" />
		<item text="收藏夹" id="MessageFavorite"/>
		<item text="垃圾箱" id="MessageTrash"/>
	</item>	

	<item text="客户管理" id="CustManager">
	   	<item text="用户投诉" id="CustComplain"/>	
	   	<item text="用户交易查询" id="OrderFee"/>
	   	<item text="客户资料管理" id="CustInfo"/>
	</item>

	<item text="商户管理" id="CooperationBusiness">
		<item text="商户资料管理" id="CooperationBusinessList"/>
	    <item text="商户合作申请" id="ProductQuoteInfo"/>
	    <item text="商户产品维护" id="UrbanRuralArea"/>
	    <item text="商户订单" id="Cooperation"/>
	</item>
					
	<item text="基础设置" id="config">
		<item text="角色管理" id="RoleManager"/>
	   	<item text="用户管理" id="UserManager"/>
	   	<item text="菜单管理" id="MenuManager"/>
	   	<item text="移动端版本管理" id="MobileApp"/>
	</item>	
	
	<item text="修改我的密码" id="pwd"/>
	<item text="注销" id="logout"/>
</tree>
