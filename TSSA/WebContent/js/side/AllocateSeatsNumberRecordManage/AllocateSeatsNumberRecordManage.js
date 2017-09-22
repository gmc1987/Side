Ext.onReady(function(){
	
	var viewPanel = new Ext.Panel({
		id : "mainPanel",
//		title : "出号管理",
		collapsible : "true",
		autoScroll : "true",
		layout : "table",
		renderTo : "allocateNumber",
		width : "100%",
		height : "100%"
	});
	
	//获取后台商户设置的派位类型数据
	Ext.Ajax.request({
		url : ctx + '/allocateSeats/getList.do',
		method : 'POST',
		success : function(response,request){
			var jsonObj = eval('(' + response.responseText + ')');
			pageInit(jsonObj.records);
		},
		failure : function(response,request){
				Ext.MessageBox.hide();
				Ext.MessageBox.alert("提示","删除失败");
		}
	});
	
	
	/**
	 * 派号界面初始化
	 */
	function pageInit(data){
		if(data==null || data.length == 0){
			Ext.MessageBox.alert("错误","尚未设置派号规则，无法初始化出号功能！");
			return;
		}else{
			var size = data.length;
			if(size >= 4){
//				viewPanel.
			}
			for(var i = 0; i < data.length; i++){
				var numberObj = data[i];
//				viewPanel
			}
		}
	}
	
});