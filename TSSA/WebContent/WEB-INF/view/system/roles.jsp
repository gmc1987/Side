<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageMode" value="${pageMode}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="角色管理" />
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<script type="text/javascript">

var menuArray = new Array();
var optionArray = new Array();

Ext.onReady(function(){
	
	var intPageSize = 10;
	
	var type_src = [['0', '超级管理员'], ['1', '系统管理员'],['2', '管理员'],['3', '商户'],['4','监控员'],['5','审核员'],['6','操作员'],['7', '渠道商']];
	var dsCurStatus = new Ext.data.SimpleStore({
		fields : ['roleVlaue', 'roleValueName'],
		data : type_src
	});
	
	var add_roleValue = new Ext.form.ComboBox({
		id : 'addRoleValue',
		name : 'addRoleValue',
		hiddenName : "myRoleValue",
		store : dsCurStatus,
		valueField : 'roleVlaue',
		displayField : 'roleValueName',
		fieldLabel : '角色类型',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local',
		listeners:{
			select : function(ProjectCombox, record,index){    
				var aForm = addForm.getForm();
				var textValue = Ext.get('addRoleValue').dom.value;
				aForm.findField('roleTypeName').setValue(textValue);
            }  
		}
	}); 
	
	var mod_roleValue = new Ext.form.ComboBox({
		id : 'modRoleValue',
		name : 'modRoleValue',
		hiddenName : "myRoleValue",
		store : dsCurStatus,
		valueField : 'roleVlaue',
		displayField : 'roleValueName',
		fieldLabel : '角色类型',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local',
		listeners:{
			select : function(ProjectCombox, record,index){    
				var mForm = modForm.getForm();
				var textValue = Ext.get('modRoleValue').dom.value;
				mForm.findField('roleTypeName').setValue(textValue);
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
	
	var addOptionBut = new Ext.Button({
		text : '设置权限',
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-add.png',
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
				columns : 6
			},
			items : [{
				layout : 'form',
				items : [{
					id : 'roleCode',
					xtype : 'textfield',
					fieldLabel : '角色编码',
					name : 'roleCode',
					width : 100,
					maxLength:100
					}]
			},{
				layout : 'form',
				items : [{
					id : 'roleName', 
					xtype : 'textfield',
					fieldLabel : '角色名称',
					name : 'roleName',
					width : 100,
					maxLength:100
					}]
			}]
		}]
	});
	//显示工具栏
	myToolbar.doLayout(); 
  	var store = new Ext.data.JsonStore({ 
 		url:'${ctx}/role/list.do',
		autoLoad:true,  
 		totalProperty:'pageMode.count',
		root:'pageMode.records',
 		fields:[{name:'rid', mapping:'roleId'},
		        {name:'roleCode', mapping:'roleCode'},
		        {name:'roleName', mapping:'roleName'},
		        {name:'roleValue', mapping:'roleValue'},
		        {name:'roleValueName', mapping:'roleValueName'},
		        {name:'createUser', mapping:'createUser'},
				{name:'createDate', type:'date', mapping:'createDate.time', dateFormat :'time'}]
	}); 
  	
 	var colModel = new Ext.grid.ColumnModel([{
		header:'uuid',
		width:100,
		sortable : true,
		dataIndex : 'rid',
		hidden : true
	},{
		header : "角色编码",
		width : 80,
		sortable : true,
		dataIndex : 'roleCode',
	},{
		header : "角色名称",
		width : 130,
		sortable : true,
		dataIndex : 'roleName',
	},{
		header : "角色值",
		width : 130,
		sortable : true,
		dataIndex : 'roleValue',
		hidden : true
	},{
		header : "角色类型",
		width : 130,
		sortable : true,
		dataIndex : 'roleValueName'
	},{
		header : "创建人",
		width : 80,
		sortable : true,
		dataIndex : 'createUser'
	},{
		header:'创建时间',
		width:120,
		sortable:true,
		dataIndex:'createDate',
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
		tbar : [addBtn, '-', modBtn, '-', delBtn, '-', addOptionBut],
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
	
	//查询
	function search(){
		var code = searchForm.getForm().findField("roleCode").getValue();
		var name = searchForm.getForm().findField("roleName").getValue();
		gridForm.getStore().load({
			proxy : new Ext.data.HttpProxy({
				url : "${ctx}/role/list.do",//获取数据的后台地址
			}),
			params:{
				roleCode : code,
				roleName : name,
				pageNumber : 0,
				pageSize : intPageSize
			}
		}); 
	}
	
	//重置
	function reset(){
		searchForm.getForm().findField("roleCode").setValue("");
		searchForm.getForm().findField("roleName").setValue("");
	}
	
	//addfrom
	var addForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '角色新增',
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
					name : 'roleTypeName'
					}]
				},{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '角色编码',
					name : 'roleCode',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '角色编码不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '角色名称',
						name : 'roleName',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '角色名称不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [add_roleValue]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					addForm.form.submit({
						url : '${ctx}/role/save.do',
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
		title : '角色修改',
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
					name : 'rid'
					}]
				},{
					layout : 'form',
					border : true,
					items:[{
						xtype : 'hidden',
						name : 'roleTypeName'
						}]
				},{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '角色编码',
					name : 'roleCode',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '角色编码不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '角色名称',
						name : 'roleName',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '角色名称不能为空'
						}]
				},{
					layout : 'form',
					border : true,
					items : [mod_roleValue]
				}]
			}],
			buttons :[{
				text : '提交',
				handler : function(){
					modForm.form.submit({
						url : '${ctx}/role/update.do',
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
	
    var optionStore = new Ext.data.JsonStore({ 
 		url:'${ctx}/options/findMenuOption.do',
//		autoLoad:true,  
		root:'result',
 		fields:[{name:'mid', mapping:'mid'},
 		        {name:'menuName', mapping:'menuName'},
 		        {name:'menuHasSelect', mapping:'menuHasSelect'},
 		        {name:'addId', mapping:'addOption.pid'},
		        {name:'addOption', mapping:'addOption.optionName'},
		        {name:'addOptionHasSelect', mapping:'addOptionHasSelect'},
		        {name:'modId', mapping:'modOption.pid'},
		        {name:'modOption', mapping:'modOption.optionName'},
		        {name:'modOptionHasSelect', mapping:'modOptionHasSelect'},
		        {name:'delId', mapping:'delOption.pid'},
		        {name:'delOption', mapping:'delOption.optionName'},
		        {name:'delOptionHasSelect', mapping:'delOptionHasSelect'},
		        {name:'findId', mapping:'findOption.pid'},
		        {name:'findOption', mapping:'findOption.optionName'},
		        {name:'findOptionHasSelect', mapping:'findOptionHasSelect'},
		        {name:'verifId', mapping:'verificationOption.pid'},
		        {name:'verificationOption', mapping:'verificationOption.optionName'},
		        {name:'verificationOptionHasSelect', mapping:'verificationOptionHasSelect'},
		        {name:'inductionId', mapping:'inductionOption.pid'},
				{name:'inductionOption', mapping:'inductionOption.optionName'},
		        {name:'inductionOptionHasSelect', mapping:'inductionOptionHasSelect'}]
	}); 
    optionStore.load({
    	params:{
  			start:0,
			limit:intPageSize
		}
    });
    
  /* treeGrid.on("render",function(){       
      //此处根据store的第一列来钩选行纪录.  
      for(var i=0; i<optionStore.getCount(); i++){  
    	  	var record = optionStore.getAt(i);
             if(record.get("menuHasSelect") == true){  
            	 treeGrid.selModel.selectRow(i,true);  
             }     
      }  
    },this,{delay:300}); */
    
    function menuCheck(value,cellmeta,record,rowIndex,columnIndex,store){ 
    	var checkBoxText = "<input type='checkbox' id='menu_"+rowIndex+"_"+columnIndex+"' name='menuCheckbox_"+rowIndex+"_"+columnIndex+"' onclick=oneclick(this,'"+null+"') ";
    	var isNewSelect = false;
    	if(record.get("menuHasSelect") == true){
    		checkBoxText += "checked='checked' ";
    		for(var i in menuArray){
    			if(menuArray[i] != record.get("mid")){
    				isNewSelect = true;
    			}else{
    				isNewSelect = false;
    				break;
    			}
    		}
    		if(isNewSelect){
    			menuArray.push(record.get("mid"));
    		}
    	}
    	checkBoxText += "value='"+ record.get("mid") +"'>"+value+"</input>";
        return checkBoxText; 
    }
    
    function addCheck(value,cellmeta,record,rowIndex,columnIndex,store){ 
    	var menuId = record.get("mid");
    	var checkBoxText = "<input type='checkbox' id='addOption_"+rowIndex+"_"+columnIndex+"' name='addOptionCheckbox_"+rowIndex+"_"+columnIndex+"' onclick=oneclick(this,'"+menuId+"') ";
    	if(record.get("addOptionHasSelect") == true){
    		checkBoxText += "checked='checked' ";
    	}
    	checkBoxText += "value='"+ record.get("addId") +"'>"+value+"</input>";
        return checkBoxText; 
    }
    
    function modCheck(value,cellmeta,record,rowIndex,columnIndex,store){ 
    	var menuId = record.get("mid");
    	var checkBoxText = "<input type='checkbox' id='modOption_"+rowIndex+"_"+columnIndex+"' name='modOptionCheckbox_"+rowIndex+"_"+columnIndex+"' onclick=oneclick(this,'"+menuId+"') ";
    	if(record.get("modOptionHasSelect") == true){
    		checkBoxText += "checked='checked' ";
    	}
    	checkBoxText += "value='"+ record.get("modId") +"'>"+value+"</input>";
        return checkBoxText; 
    } 
    
    function delCheck(value,cellmeta,record,rowIndex,columnIndex,store){ 
    	var menuId = record.get("mid");
    	var checkBoxText = "<input type='checkbox' id='delOption_"+rowIndex+"_"+columnIndex+"' name='delOptionCheckbox_"+rowIndex+"_"+columnIndex+"' onclick=oneclick(this,'"+menuId+"') ";
    	if(record.get("delOptionHasSelect") == true){
    		checkBoxText += "checked='checked' ";
    	}
    	checkBoxText += "value='"+ record.get("delId") +"'>"+value+"</input>";
    	return checkBoxText;
    } 
    
    function findCheck(value,cellmeta,record,rowIndex,columnIndex,store){ 
    	var menuId = record.get("mid");
    	var checkBoxText = "<input type='checkbox' id='findOption_"+rowIndex+"_"+columnIndex+"' name='findOptionCheckbox_"+rowIndex+"_"+columnIndex+"' onclick=oneclick(this,'"+menuId+"') ";
    	if(record.get("findOptionHasSelect") == true){
    		checkBoxText += "checked='checked' ";
    	}
    	checkBoxText += "value='"+ record.get("findId") +"'>"+value+"</input>";
    	return checkBoxText;
    } 
    
    function verificationCheck(value,cellmeta,record,rowIndex,columnIndex,store){ 
    	var menuId = record.get("mid");
    	var checkBoxText = "<input type='checkbox' id='verOption_"+rowIndex+"_"+columnIndex+"' name='verOptionCheckbox_"+rowIndex+"_"+columnIndex+"' onclick=oneclick(this,'"+menuId+"') ";
    	if(record.get("verificationOptionHasSelect") == true){
    		checkBoxText += "checked='checked' ";
    	}
    	checkBoxText += "value='"+ record.get("verifId") +"'>"+value+"</input>";
    	return checkBoxText;
    } 
    
    function inductionCheck(value,cellmeta,record,rowIndex,columnIndex,store){ 
    	var menuId = record.get("mid");
    	var checkBoxText = "<input type='checkbox' id='indOption_"+rowIndex+"_"+columnIndex+"' name='indOptionCheckbox_"+rowIndex+"_"+columnIndex+"' onclick=oneclick(this,'"+menuId+"') ";
    	if(record.get("inductionOptionHasSelect") == true){
    		checkBoxText += "checked='checked' ";
    	}
    	checkBoxText += "value='"+ record.get("inductionId") +"'>"+value+"</input>";
    	return checkBoxText;
    } 
    
    var menuModel = new Ext.grid.ColumnModel([{
		header : "菜单名称",
		width : 95,
		sortable : false,
		dataIndex : 'menuName',
		renderer : menuCheck
	}]);
	
	//权限菜单树
  var treeGrid = new Ext.grid.GridPanel({
  		region:'west',
		store : optionStore,
		cm : menuModel,  
		frame : true,
		title : '菜单',
		viewConfig : {
			forceFit : false
		},
		width : '20%',
		height : '100%',
		collapsible : false,
		animCollapse : false,
		trackMouseOver : false,
		iconCls : 'icon-grid',
		loadMask : ({
			msg : '数据正在加载中,请稍後...'
		})
  });
    
    var optionModel = new Ext.grid.ColumnModel([{
		header:'uuid',
		width:100,
		sortable : false,
		dataIndex : 'mid',
		hidden : true
	},{
		header : "增加",
		width : 79,
		sortable : false,
		dataIndex : 'addOption',
		renderer:addCheck
	},{
		header : "删除",
		width : 79,
		sortable : false,
		dataIndex : 'delOption',
		renderer:delCheck
	},{
		header : "修改",
		width : 79,
		sortable : false,
		dataIndex : 'modOption',
		renderer:modCheck
	},{
		header : "查询",
		width : 79,
		sortable : false,
		dataIndex : 'findOption',
		renderer:findCheck
	},{
		header : "导入",
		width : 79,
		sortable : false,
		dataIndex : 'inductionOption',
		renderer:inductionCheck
	},{
		header : "审核",
		width : 79,
		sortable : false,
		dataIndex : 'verificationOption',
		renderer:verificationCheck
	}]);  
    
    //权限列表
    var optionGrid = new Ext.grid.GridPanel({
    	region: 'center',
		store : optionStore,
		cm : optionModel,  
		frame : true,
		title : '权限',
		viewConfig : {
			forceFit : false
		},
		width : '80%',
		height : '100%',
		collapsible : false,
		animCollapse : false,
		trackMouseOver : false,
		iconCls : 'icon-grid',
		loadMask : ({
			msg : '数据正在加载中,请稍後...'
		})
	});
    
	var optionForm = new Ext.FormPanel({
		frame : true,
		title : '角色权限设置',
		buttonAlign : 'center',
		layout : 'border',
		bodyStyle : 'padding:5px 5px 0',
		/* width : 640,
		height: 400, */
		collapsible : false,
		autoScroll : false,
		items : [{
					xtype : 'hidden',
					name : 'rid'},
					treeGrid,
					optionGrid],
		buttons :[{
			text : '提交',
			handler : function(){
				optionForm.form.submit({
					url : '${ctx}/options/setRoleOption.do',
					method : 'POST',
					success : function(form,action){
							Ext.MessageBox.alert("提示",action.result.msg);
							optionWin.hide();
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
				optionWin.hide();
			}
		}]
	});
    
	//新增
	var addWin = null;
	addBtn.on('click', function(){
		if(!addWin){
			addWin = new Ext.Window({
				el : 'add_role',
				layout : 'fit',
				closeAction : 'hide',
				width : 380,
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
					el : 'mod_role',
					layout : 'fit',
					closeAction : 'hide',
					width : 380,
					height : 200,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : modForm
				});
			}
			modWin.on('show', function() {
				
 				var rid = record.get("rid");
				var roleCode = record.get("roleCode");
				var roleName = record.get("roleName");
				var roleValue = record.get("roleValue");
				var roleValueName = record.get("roleValueName");
				
 				var mForm = modForm.getForm();
				mForm.reset();
				mForm.findField('rid').setValue(rid);
				mForm.findField('roleName').setValue(roleName);
				mForm.findField('roleCode').setValue(roleCode);
				mForm.findField('roleTypeName').setValue(roleValueName);
				mod_roleValue.setValue(roleValue);
			}); 
			modWin.show(this);
		}else{
			Ext.MessageBox.alert("错误","没有选中修改项");
			return;
		}
	});
	
	var optionWin = null;
	addOptionBut.on('click', function(){
		if(gridForm.getSelectionModel().hasSelection()){
			var record = gridForm.getSelectionModel().getSelected();
			var id = record.get('rid');
			if(!optionWin){
				optionWin = new Ext.Window({
					el : 'setRoleOption',
					layout : 'fit',
					buttonAlign : 'center',
					closeAction : 'hide',
					width : 625,
					height : 400,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : [optionForm]
				});
			}
			treeGrid.getStore().load({
				params : {
					rid : id
				}
			});
			optionWin.on("show", function(){
				var oForm = optionForm.getForm();
				oForm.findField('rid').setValue(id);
			});
			optionWin.show(this);
		}else{
			Ext.MessageBox.alert("错误","没有选中修改项");
			return;
		}
	});
	
	//删除
	delBtn.on('click', function(){
		if(gridForm.getSelectionModel().hasSelection()){
			Ext.MessageBox.confirm('提示', '确认要删除该记录吗?', submitDel);
			function submitDel(btn){
				if(btn == 'yes'){
					Ext.MessageBox.wait("数据正在处理中,请稍後...", "提交");
					var record = gridForm.getSelectionModel().getSelected();
					var id = record.get('rid');
					Ext.Ajax.request({
						url : '${ctx}/role/delete.do',
						method : 'POST',
						params: { rid : id },
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

	function oneclick(object,mid){
		var menuId = "";
		if(mid == 'null' && object.checked == true){//选择菜单 
			menuId = object.value;
			menuArray.push(menuId);
		}
		if(mid == 'null' && object.checked == false){//取消选择 
			for(var i in menuArray){
				if(object.value == menuArray[i]){
					menuArray.splice(i, i+1);
					break;
				}
			}
			return;
		}
		if(mid != 'null' && object.checked == true){//选择权限 
			var isSelect = false;
			for(var i in menuArray){
				if(mid == menuArray[i]){
					optionArray.push(object.value);
					isSelect = true;
					break;
				}else{
					isSlect = false;
				}
			}
			if(!isSelect){
				Ext.MessageBox.alert("提示","请先选择菜单");
				return;
			}
		}
		if(mid != 'null' && object.checked == false){
			for(var i in menuArray){
				if(mid == menuArray[i]){
					for(var j in optionArray){
						optionArray.splice(object.value);
						break;
					}
				}
			}
		}
	}
	
</script>
</head>
<body>
	<div id="toolBar"></div>
	<div id="searchFormDiv"></div>
	<div id="dataGrild"></div>
	<div id="add_role"></div>
	<div id="mod_role"></div>
	<div id="setRoleOption"></div>
</body>
</html>