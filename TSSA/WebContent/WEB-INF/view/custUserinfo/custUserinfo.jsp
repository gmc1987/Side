<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- <c:set var="img_path_16" value="${ctx}/view/images/icon/Nuvola/16" /> --%>
<c:set var="title" value="客户管理" />

<link rel="stylesheet" type="text/css" href="${ctx}/view/css/default.css" >
<link rel="stylesheet" type="text/css" href="${ctx}/view/js/resources/css/ext-all.css" />
<script src="${ctx}/view/js/adapter/ext/ext-base.js" type="text/javascript"></script>
<script src="${ctx}/view/js/ext-all.js" type="text/javascript"></script>
<script src="${ctx}/view/js/jquery/jquery.js" type="text/javascript"></script>
<title>${title}</title>
</head>
<script type="text/javascript">
Ext.onReady(function(){
	var intPageSize = 20;
	var myToolbar = new Ext.Toolbar();
	myToolbar.render('toolBar');
	myToolbar.addButton({
		text: '<span style=\'font-size:13px\'>查询</span>', 
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/edit-find.png',
		handler: function(o, e) {
			search();
		}
	});
	myToolbar.addButton({
		text: '<span style=\'font-size:13px\'>重置</span>', 
		cls: 'x-btn-text-icon scroll-bottom', 
		icon: '${img_path_16}/actions/view-refresh.png',
		handler: function(o, e) {
			reset();
		}
	});
	//显示工具栏
	myToolbar.doLayout(); 
	
	var uploadAdvBtn = new Ext.Button({
		text : '生成二维码',
		icon: '${img_path_16}/actions/media-eject.png',
		listeners : {
			click : function() {
				if (gridForm.getSelectionModel().hasSelection()) {
					var array = gridForm.getSelectionModel().getSelections();
					if(array.length>1){
						 Ext.MessageBox.alert("提示", "请选择一条记录");
						 return;
					}
					var record = gridForm.getSelectionModel().getSelected();
					var id = record.get('id');
					var qrCode = record.get('qrCode');
					var msg = '确认生成二维码';
					if(qrCode == '1'){
						msg = '此记录的二维码已经生成，是否重新生成';
					}
					Ext.MessageBox.confirm('提示', msg ,function(btn){
						if (btn == 'yes'|| bnt == 'ok'){
							var param = {'id':id};
							$.post('${ctx}/customer/makeQrCode.do',param,function(date){
								if(date == "true"){
									Ext.MessageBox.alert('提示', "生成二维码成功");
								} else {
									Ext.MessageBox.alert('提示', "生成二维码失败");
								}
								
								search();
							});
					    }
					});
				} else {
					Ext.MessageBox.alert('提示', '请选中您要生成二维码的记录');
				}
			}
		}
	});
	
	var cm = new Ext.grid.ColumnModel([ 
        {
			header : "客户编码",
			dataIndex : 'uid'
		},{
			header : '昵称',
			dataIndex : 'nickname'
		}, {
			header : '用户姓名',
			dataIndex :'username'
		},
		{
			header : '年龄',
			dataIndex :'age',
			width:50
		},
		{
			header : '性别',
			dataIndex :'sex',
			renderer : sexStatus,
			width:50
		},{
			header : '生日',
			dataIndex :'birthday',
			width:140
		},{
			header : '邮箱',
			dataIndex : 'email',
			width:150
		},{
			header : '手机号',
			dataIndex : 'phone',
			width:100
		},{
			header : 'QQ',
			dataIndex : 'qq',
			width:100
		},{
			header : '客户等级',
			dataIndex : 'customerLever',
			width:70
		},{
			header : '二维码',
			dataIndex : 'qrCode',
			renderer : qrCodeStatus,
			width:70
		}, {
			header : '创建时间',
			dataIndex : 'createDate',
			width:140
		} ]);

	
	var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : '${ctx}/customer/getCustLilst.do'
			}),
			reader : new Ext.data.JsonReader({
				totalProperty : 'pageMode.count',
				root : 'pageMode.records',
			}, [
			{
				name : 'id',
				mapping : 'id'
			},
			{
				name : 'uid',
				mapping : 'uid'
			},{
				name : 'phone',
				mapping : 'phone',
				type : 'string'
			}, {
				name : 'nickname',
				mapping : 'nickname',
				type : 'string'
			}, {
				name : 'sex',
				mapping : 'sex',
				type : 'string'
			}, {
				name : 'username',
				mapping : 'username',
				type : 'string'
			}, {
				name : 'age',
				mapping : 'age',
				type : 'int'
			}, {
				
				name : 'createDate',
				mapping : 'createDate',
				type : 'string'
			},{
				name : 'birthday',
				mapping : 'birthday',
				type : 'string'
			},{
				name : 'email',
				mapping : 'email',
				type : 'string'
			},{
				name : 'qq',
				mapping : 'qq',
				type : 'string'
			},{
				name : 'qrCode',
				mapping : 'qrCode',
				type : 'string'
			},{
				name : 'customerLever',
				mapping : 'customerLever',
				type : 'int'
			}])
		});

		var gridForm = new Ext.grid.GridPanel({
			renderTo : 'dataArea',
			autoHeight : true,
			store : store,
			cm : cm,
			autoScroll:true,
			 loadMask : {
			     msg : '正在查询,请稍后...'
			    },
			viewConfig : {
				forceFit : false
			},
			tbar : [ uploadAdvBtn],
			bbar : new Ext.PagingToolbar({
				pageSize : intPageSize,
				store : store,
				beforePageText : '第',
				afterPageText : '页,共 {0} 页',
				displayInfo : true,
				displayMsg : '{0} - {1} , 共 {2} 条',
				emptyMsg : "无记录"
			})
		});
		
		gridForm.addListener('rowdblclick', rowdblclick);
		  
		/*
		 * 详细页面
		 */
		var formPayView = new Ext.FormPanel({
			labelWidth : 60, 
			frame : true,
			buttonAlign : 'center',
			bodyStyle : 'padding:5px 5px 0',
			defaultType : 'textfield',
			labelAlign : 'right',
			items : [
	 		{
				fieldLabel : '客户编码',
				name : 'uid',
				readOnly : true,
				width : 200
			},{
				fieldLabel : '昵称',
				name : 'nickname',
				readOnly : true,
				width : 200
			},{
				fieldLabel : '用户姓名',
				name : 'username',
				readOnly : true,
				width : 200
			},{
				fieldLabel : '联系方式',
				name : 'phone',
				readOnly : true,
				width : 200
			},{
				fieldLabel : '创建时间',
				name : 'createDate',
				width : 200,
				readOnly : true
			},{
                xtype : 'box',  
                id : 'browseImage',  
                fieldLabel : "预览图片",
                width : 150,
                height : 150,
                autoEl : {
                    tag : 'img', 
                    src : '', 
                    complete : 'off',  
                    id : 'imageBrowse'  
                }
            }],

			buttons : [{
				text : '关闭',
				handler : function() {
					winView.hide();
				}
			}]
		});
		var winView;
		function rowdblclick(grid, rowIndex, e) {
			if (gridForm.getSelectionModel().hasSelection()) {
				if (!winView) {
					winView = new Ext.Window({
						layout : 'fit',
						title : '客户信息详情',
						closeAction : 'hide',
						width : 500,
						height : 400,
						modal : true,
						items : formPayView
					});

					winView.on('show', function() {
						Ext.get('imageBrowse').dom.src = '${ctx}/QrCode/12/12.png';
						var record = gridForm.getSelectionModel().getSelected();
						var id = record.get('id');
						var uid = record.get('uid');
						var nickname = record.get('nickname');
						var username = record.get('username');
						var phone = record.get('phone');
						var createDate = record.get('createDate');
		
						Ext.get('imageBrowse').dom.src = '${ctx}/QrCode/'+id+'/'+id+'.png';
						var vform = formPayView.getForm();
						vform.reset();
						vform.findField('uid').setValue(uid);
						vform.findField("nickname").setValue(nickname);
						vform.findField('username').setValue(username);
						vform.findField('phone').setValue(phone);
						vform.findField("createDate").setValue(createDate);
					});
				}
				winView.show(this);
			}
		}
		
		//初始化加载数据
		store.load({ //传参数，load数据
			params : {
				start : 0,
				limit : intPageSize
			}
		});
		
		function search(){
			var uid = $("#uid").val().trim();
			var phone = $("#phone").val().trim();
			var customerLever = $("#customerLever").val().trim();
			store.load({ //传参数，load数据
				params : {
					start : 0,
					limit : intPageSize,
					uid : uid,
					phone : phone,
					customerLever : customerLever
				}
			});
		}
	});
	function reset(){
		$("#uid").val("");
		$("#phone").val("");
		$("#customerLever").val("");
	}
	
	function sexStatus(val){
		if (val == "0") {
			return "男";
		} else if (val == "1") {
			return "女";
		}
	}
	
	function qrCodeStatus(val){
		if (val == "0") {
			return "未生成";
		} else if (val == "1") {
			return "已生成";
		}
	}
</script>
<body style = 'min-width: 900px;'>
	<div id="toolBar"></div>
	<table width="100%" class="tdTable" >
			<tr height="30">			
				<td class="tdRight" style="width: 8%;min-width: 70px ; max-width: 90px">客户编码：&nbsp;</td>
				<td class="tdLeft"  width="20%">
					<input type="text" name="uid" id="uid" style="width:150px;height: 23px;line-height: 23px;" />
				</td>
				<td class="tdRight" style="width: 8%;min-width: 70px ; max-width: 90px">手机号：&nbsp;</td>
				<td class="tdLeft"  width="20%">
					<input type="text" name="phone" id="phone" style="width:150px;height: 23px;line-height: 23px;"/>
				</td>
				<td class="tdRight"  style="width: 8%;min-width: 70px ; max-width: 90px">客户等级 ：&nbsp;</td>
				<td class="tdLeft"  width="20%">
					<input type="text" name="customerLever" id="customerLever" style="width:150px;height: 23px;line-height: 23px;"/>
				</td>
			</tr>
		</table>
	<div id = 'dataArea'></div>
</body>
</html>