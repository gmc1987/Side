function onNodeSelect(id){
	var text = this.getItemText(id);
	switch(id){
		case "MessageAddNew":
			var url=CONTEXT_PATH+'/message/msg-message!edit.do?cacheflag='+new Date().getTime();
			reloadTab(text,url);
		break;
		case "MessageReceiver":
			var url=CONTEXT_PATH+'/message/msg-message!index.do?activeTab=0&cacheflag='+new Date().getTime();
			reloadTab(text,url);
		break;
		case "MessageSender":
			var url=CONTEXT_PATH+'/message/msg-message!index.do?activeTab=1&cacheflag='+new Date().getTime();
			reloadTab(text,url);
		break;
		case "MessageDraft":
			var url=CONTEXT_PATH+'/message/msg-message!index.do?activeTab=2&cacheflag='+new Date().getTime();
			reloadTab(text,url);
		break;
		case "MessageFavorite":
			var url=CONTEXT_PATH+'/message/msg-message!index.do?activeTab=3&cacheflag='+new Date().getTime();
			reloadTab(text,url);
		break;
		case "MessageTrash":
			var url=CONTEXT_PATH+'/message/msg-message!index.do?activeTab=4&cacheflag='+new Date().getTime();
			reloadTab(text,url);
		break;
		

//		case "index":
//			Ext.get('application-info-iframe').dom.src=portalUrl;
//			setMainTabActive();
//			break;
//		case "mywork_1":
//			var url=CONTEXT_PATH+'/flow/myworklist.do?state=1&cacheflag='+new Date().getTime();
//			reloadTab("个人工作台",url);
//			break;
//		case "mywork_2":
//			var url=CONTEXT_PATH+'/flow/myworklist.do?state=2&cacheflag='+new Date().getTime();
//			reloadTab("个人工作台",url);
//			break;
//		case "mywork_3":
//		    var url=CONTEXT_PATH+'/flow/myworklist.do?state=4&cacheflag='+new Date().getTime();
//			reloadTab("个人工作台",url);
//			break;
					
		case "logout":
			Ext.MessageBox.confirm('提示', '您确定退出系统么?', logout);
			break;
//		case "admin1":
//			var url=CONTEXT_PATH+'/admin/user-index.do?cacheflag='+new Date().getTime();
//			reloadTab(text,url);
//			break;
//		case "admin2":
//			var url=CONTEXT_PATH+'/admin/resource-index.do?cacheflag='+new Date().getTime();
//			reloadTab(text,url);
//			break;
//		case "admin3":
//			var url=CONTEXT_PATH+'/admin/role.do?cacheflag='+new Date().getTime();
//			reloadTab(text,url);
//			break;
//		case "wfdef":
//			var url=CONTEXT_PATH+'/flow/wfupload.do?cacheflag='+new Date().getTime();
//			reloadTab(text,url);
//			break;
		case "pwd":
			var url=CONTEXT_PATH+'/admin/pwd.do';
			reloadTab(text,url);
			break;
//		case "admin4":
//			var url=CONTEXT_PATH+"/dashboards/gadget-define.do?cacheflag="+new Date().getTime();
//			reloadTab(text,url);
//			break;
//		case "admin5":
//			var url=CONTEXT_PATH+"/dashboards/dashboards.do?cacheflag="+new Date().getTime();
//			reloadTab(text,url);
//			break;			

//		case "Order":
//			var url=CONTEXT_PATH+"/biz/order/order.do?cacheflag="+new Date().getTime();
//			reloadTab(text,url);
//			break;	
//		case "OrderControl":
//			var url=CONTEXT_PATH+"/biz/order/order!stateQuery.do?inicontrolpages=1&cacheflag="+new Date().getTime();
//			reloadTab(text,url);
//			break;	

		case "CustComplain":
			var url=CONTEXT_PATH+"/biz/carmanage/car-model-manage.do?cacheflag="+new Date().getTime();
			reloadTab(text,url);
			break;
		case "OrderFee":
			var url=CONTEXT_PATH+"/biz/carmanage/car-maintain-info.do?cacheflag="+new Date().getTime();
			reloadTab(text,url);
			break;
		case "CustInfo":
			var url=CONTEXT_PATH+"/biz/carmanage/car-rank-manage.do?cacheflag="+new Date().getTime();
			reloadTab(text,url);
			break;
			
		case "VenderInfoManage":
			var url=CONTEXT_PATH+"/biz/carmanage/car-rank-manage.do?cacheflag="+new Date().getTime();
			reloadTab(text,url);
			break;
		case "VenderQuoteInfo":
			var url=CONTEXT_PATH+"/biz/carmanage/car-rank-manage.do?cacheflag="+new Date().getTime();
			reloadTab(text,url);
			break;
		case "UrbanRuralArea":
			var url=CONTEXT_PATH+"/biz/carmanage/car-rank-manage.do?cacheflag="+new Date().getTime();
			reloadTab(text,url);
			break;
		case "CooperationBusinessList":
			var url=CONTEXT_PATH+"/cooperation/toList.do?cacheflag="+new Date().getTime();
			reloadTab(text,url);
			break;
			
		case "RoleManager":
			var url=CONTEXT_PATH+"/role/toList.do?cacheflag="+new Date().getTime();
			reloadTab(text,url);
			break;
		case "UserManager":
			var url=CONTEXT_PATH+"/user/toList.do?cacheflag="+new Date().getTime();
			reloadTab(text,url);
			break;
		case "MobileApp":
			var url=CONTEXT_PATH+"/biz/carmanage/car-rank-manage.do?cacheflag="+new Date().getTime();
			reloadTab(text,url);
			break;
		case "MenuManager":
			var url=CONTEXT_PATH+"/menu/toList.do?cacheflag="+new Date().getTime();
			reloadTab(text,url);
			break;
		default:
		;
	}
//	setMainTabActive();
}
