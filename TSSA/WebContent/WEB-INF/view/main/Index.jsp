<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="Side移动生活服务平台"></c:set>
<c:set var="pageTree" value="true"></c:set>
<c:set var="pageExt" value="true"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="pageAjax" value="true"></c:set> 
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle}</title>
<style type="text/css">
	#head{
	    background: url(${ctx}/images/common/header-bar.gif) repeat-x bottom;
	    padding:5px 4px;
	    text-align:left;
	    width: 100%;
	}
	/*主页面标题*/
	#main-page-title{
		FILTER: Glow(Color=#000000,Strength=3);
		font-size:30px;
		color:#FFFFFF;
		margin-left:38; 
		letter-spacing:5; 
		font-weight:bold;
		text-align:left;
    font-family:黑体;
		width: 800px;
	}
	#menu{
 		text-align: left;
 	}
</style>

</head>
<body>
	<div id="head">
		<table width="100%">
			<tr>
			<td align="left">
				<div id="main-page-title" style="font-family:黑体;" width="100">
					${pageTitle}
				</div>
			</td>
			<td style="color:#ffffff;text-align:right;" width="350">
				欢迎您！ ${user.userName}
				<span id="messageInfo" style="cursor:hand;display:inline-block;" onclick="showMsgInfo()">
					<img src='${ctx}/images/message/mail_new.png'>新消息(0)
				</span>　
				<span onclick="javascript:Ext.MessageBox.confirm('提示', '您确定退出系统么?', logout);" style="cursor:hand;display:inline-block;">
					<img src="${ctx}/images/icon/logout.png">注销
				</span>&nbsp;&nbsp;
			</td>
			</tr>
		</table>
	</div>
	
	<div id="menu">
	
	</div>
	
	<div id="content">
		<iframe id="application-info-iframe" src="" frameborder="0" style="height:100%;width:100%;border:0px none;"></iframe>
	</div>
</body>
<%-- <script src="${ctx}/js/menu.js" type="text/javascript"></script> --%>
<script language="javascript">
var contentPanel = null;
//门户页面地址
//var portalUrl = "${ctx}/portal.do";
Ext.onReady(function(){
    contentPanel = new Ext.TabPanel({
              region:'center',
              deferredRender:false,
              enableTabScroll:true,
              activeTab:0,
              items:[{
                 contentEl:'content',
                 title:'主页',
                 closable:false,
                 autoScroll:true
              } ]
          });
    
    
    var rootNode = new Ext.tree.AsyncTreeNode({  
        text : "系统菜单",  
        id : "root",  
        url : '',
        parentId : '',
        expanded : true  
    }); 
    
    var menuTree = new Ext.tree.TreePanel({
    	region:'west',
        title:'功能菜单',
        split:true,
        width: 150,
        minSize: 100,
        maxSize: 400,
        collapsible: true,
        contentEl:'menu',
        loader:new Ext.tree.TreeLoader({
            dataUrl:'${ctx}/menu/getMenus.do'
        }),
        layoutConfig:{
            animate:true
	     },
	    root:rootNode,
	    rootVisible:false
    });
    
    menuTree.on("beforeload" , function(node){  
    	menuTree.loader.baseParams.id = node.id;  
    	menuTree.loader.baseParams.url = node.attributes.url;  
    	menuTree.loader.baseParams.parentId = node.attributes.parentId;  
    });
    
    menuTree.on("click", function(node){
    	var parentId = node.attributes.parentId;
    	var url = node.attributes.url;
    	if(parentId == "" && (url != null && url != undefined && url != "")){
    		Ext.MessageBox.confirm('提示', '您确定退出系统么?', logout);
    	}else{
    		reloadTab(node.text, node.attributes.url);
    	}
    });  

	viewport = new Ext.Viewport({
            layout:'border',items:[
            menuTree,
            {	
             	region:'north',
                split: false,
                initialSize: 43,
                minSize: 43,
                maxSize: 43,
                collapsible: false,
                contentEl:'head'
             },
             contentPanel
             ]
		});
          
/*   tree=new dhtmlXTreeObject("menu","100%","100%",0);
	tree.loadXML("${ctx}/view/main/menutree.jsp");
	tree.setOnClickHandler(onNodeSelect); */
//  document.getElementById('application-info-iframe').src = portalUrl;
  
  interval=setInterval(keepalive,30000);
	//执行消息轮询
	showMsgTipsCycle();
});

function logout(btn){
if(btn=='yes'){
    document.location.href = '${ctx}/user/loginOut.do';
  }
}

function addTabFrame(atitle, aurl) {
	var contentHtml = "<iframe id=\"frame_"+atitle+"\" src=\"${ctx}"+encodeURI(aurl)+"\" frameborder=\"0\" scrolling=\"auto\" style=\"height:100%;width:100%;border:0px none;\"></iframe>";
	var tabp = contentPanel.add({id:atitle, title:atitle, iconCls:"tabs", html:contentHtml, closable:true}).show();
	contentPanel.setActiveTab(tabp);
}

function reloadTab(title,url){
	var tab = contentPanel.getItem(title);
	var src = document.getElementById('frame_'+title);
	if(url == null || url == "" || url == undefined){
		return;
	}
	if (tab && src != null && src != undefined) { 
		document.getElementById('frame_'+title).src = '${ctx}'+url;
		contentPanel.setActiveTab(title);
	}else{
		if(contentPanel.items.length>9){
			Ext.MessageBox.confirm('提示', '菜单数目已经超过了10个，是否替换当前菜单?', function(btn){
				if(btn=='yes'){
					var tabp = contentPanel.getActiveTab();
					document.getElementById('frame_'+tabp.title).src = '${ctx}'+url;
					document.getElementById('frame_'+tabp.title).id = 'frame_'+title; 
					tabp.setTitle(title);
					return ;
				  }
			});
		}else{
			addTabFrame(title, url);
		}
		
	}
}

function setMainTabActive(){
	contentPanel.setActiveTab(0);
}	

function keepalive(){
  new Ajax.Request('${ctx}/empty.jsp',{method: 'get'});
} 
</script>
</html>