<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc"%>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="pageAjax" value="true"></c:set>
<c:set var="title" value="商户产品列表" />
<%@ include file="../../../js/js.inc"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<script type="text/javascript">
Ext.onReady(function() {
			var intPageSize = 10;
			/* 	var type_src = [['01', '餐饮'], ['02', '娱乐'],['03', '购物'],['04','酒店住宿'],['05','旅游']];
			 var dsCurStatus = new Ext.data.SimpleStore({
			 fields : ['type_id', 'type_name'],
			 data : type_src
			 }); */

			var dsCurStatus = new Ext.data.JsonStore({
				url : '${ctx}/cooperationProduct/getProductType.do',
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

			var businessType = new Ext.form.ComboBox(
					{
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

			var childsType = new Ext.form.ComboBox({
				id : 'childsType',
				name : 'childsType',
				store : childProductStore,
				valueField : 'productTypeId',
				displayField : 'typeName',
				fieldLabel : '产品类型',
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
				text : '查询',
				cls : 'x-btn-text-icon scroll-bottom',
				icon : '${img_path_16}/actions/edit-find.png',
				handler : function(o, e) {
					search();
				}
			});
			myToolbar.addButton({
				text : '重置',
				cls : 'x-btn-text-icon scroll-bottom',
				icon : '${img_path_16}/actions/view-refresh.png',
				handler : function(o, e) {
					reset();
				}
			});

			var addBtn = new Ext.Button({
				text : '新增',
				cls : 'x-btn-text-icon scroll-bottom',
				icon : '${img_path_16}/actions/edit-add.png',
			});

			var modBtn = new Ext.Button({
				text : '修改',
				cls : 'x-btn-text-icon scroll-bottom',
				icon : '${img_path_16}/actions/edit-editor.png',
			});

			var delBtn = new Ext.Button({
				text : '删除',
				cls : 'x-btn-text-icon scroll-bottom',
				icon : '${img_path_16}/actions/edit-delete.png',
			});

			var searchForm = new Ext.form.FormPanel({
				id : 'searchForm',
				renderTo : 'searchFormDiv',
				frame : true,
				labelAlign : 'right',
				labelWidth : 60,
				width : '100%',
				waitMsgTarget : true,
				items : [ {
					layout : 'table',
					border : true,
					labelSeparator : ':',
					layoutConfig : {
						columns : 6
					},
					items : [ {
						layout : 'form',
						items : [ {
							id : 'vendorCode',
							xtype : 'textfield',
							fieldLabel : '商户编码',
							name : 'vendorCode',
							width : 100,
							maxLength : 100
						} ]
					}, {
						layout : 'form',
						items : [ {
							id : 'vendorName',
							xtype : 'textfield',
							fieldLabel : '商户名称',
							name : 'vendorName',
							width : 100,
							maxLength : 100
						} ]
					}, {
						layout : 'form',
						items : [ {
							id : 'productCode',
							xtype : 'textfield',
							fieldLabel : '产品编码',
							name : 'productCode',
							width : 100,
							maxLength : 100
						} ]
					}, {
						layout : 'form',
						items : [ {
							id : 'productName',
							xtype : 'textfield',
							fieldLabel : '产品名称',
							name : 'productName',
							width : 100,
							maxLength : 100
						} ]
					}, {
						layout : 'form',
						items : [ businessType ]
					}, {
						layout : 'form',
						items : [ childsType ]
					} ]
				} ]
			});
			//显示工具栏
			myToolbar.doLayout();
			var store = new Ext.data.JsonStore({
				url : '${ctx}/cooperationProduct/list.do',
				autoLoad : true,
				totalProperty : 'pageMode.count',
				root : 'pageMode.records',
				fields : [ {
					name : 'businessProductId',
					mapping : 'businessProductId'
				}, {
					name : 'productCode',
					mapping : 'productCode'
				}, {
					name : 'productName',
					mapping : 'productName'
				}, {
					name : 'description',
					mapping : 'description'
				}, {
					name : 'singlePrice',
					mapping : 'singlePrice'
				}, {
					name : 'salePrice',
					mapping : 'salePrice'
				}, {
					name : 'totalNum',
					mapping : 'totalNum'
				}, {
					name : 'residualNum',
					mapping : 'residualNum'
				}, {
					name : 'cooperCode',
					mapping : 'cooperId.cooperCode'
				}, {
					name : 'cooperName',
					mapping : 'cooperId.cooperName'
				}, {
					name : 'typeCode',
					mapping : 'productType.typeCode'
				}, {
					name : 'typeName',
					mapping : 'productType.typeName'
				}, {
					name : 'parentTypeCode',
					mapping : 'cooperId.productTypeId.pareTypes.typeCode'
				}, {
					name : 'parentTypeName',
					mapping : 'cooperId.productTypeId.pareTypes.typeName'
				}, {
					name : 'createDate',
					type : 'date',
					mapping : 'createDate.time',
					dateFormat : 'time'
				}, {
					name : 'specialType',
					mapping : 'specialType'
				} ]
			});

			var colModel = new Ext.grid.ColumnModel([ {
				header : 'uuid',
				width : 100,
				sortable : true,
				dataIndex : 'businessProductId',
				hidden : true
			}, {
				header : "产品编号",
				width : 80,
				sortable : true,
				dataIndex : 'productCode',
			}, {
				header : "产品名称",
				width : 80,
				sortable : true,
				dataIndex : 'productName',
			}, {
				header : "产品特殊分类",
				width : 80,
				sortable : true,
				dataIndex : 'specialType',
			}, {
				header : "产品描述",
				width : 130,
				sortable : true,
				dataIndex : 'description',
				hidden : true
			}, {
				header : "单价(元)",
				width : 80,
				sortable : true,
				dataIndex : 'singlePrice'
			}, {
				header : '优惠价(元)',
				width : 80,
				sortable : true,
				dataIndex : 'salePrice'
			}, {
				header : "产品总数",
				width : 80,
				sortable : true,
				dataIndex : 'totalNum',
				hidden : true
			}, {
				header : "剩余数",
				width : 80,
				sortable : true,
				dataIndex : 'residualNum',
				hidden : true
			}, {
				header : '商户编码',
				width : 80,
				sortable : true,
				dataIndex : 'cooperCode'
			}, {
				header : '商户名称',
				width : 80,
				sortable : true,
				dataIndex : 'cooperName'
			}, {
				header : '行业编码',
				width : 80,
				sortable : true,
				dataIndex : 'parentTypeCode',
				hidden : true
			}, {
				header : '行业类型',
				width : 80,
				sortable : true,
				dataIndex : 'parentTypeName'
			}, {
				header : '产品类型编码',
				width : 80,
				sortable : true,
				dataIndex : 'typeCode',
				hidden : true
			}, {
				header : '类型名称',
				width : 80,
				sortable : true,
				dataIndex : 'typeName'
			}, {
				header : '创建时间',
				width : 120,
				sortable : true,
				dataIndex : 'createDate',
				renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
			} ]);

			//数据集
			var gridForm = new Ext.grid.GridPanel({
				id : 'opercode-grid',
				store : store,
				cm : colModel,
				viewConfig : {
					forceFit : false
				},
				tbar : [ addBtn, '-', modBtn, '-', delBtn ],
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
			function search() {
				var code = searchForm.getForm().findField("vendorCode")
						.getValue();
				var name = searchForm.getForm().findField("vendorName")
						.getValue();
				var searchCode = searchForm.getForm().findField(
						"productCode").getValue();
				var searchName = searchForm.getForm().findField(
						"productName").getValue();
				var businessType = searchForm.getForm().findField(
						"businessType").getValue();
				var productTypes = searchForm.getForm().findField(
						"childsType").getValue();
				gridForm.getStore().load({
					proxy : new Ext.data.HttpProxy({
						url : "${ctx}/cooperationProduct/list.do",//获取数据的后台地址
					}),
					params : {
						vendorCode : code,
						vendorName : name,
						productCode : searchCode,
						productName : searchName,
						productType : productTypes,
						pageNumber : 0,
						pageSize : intPageSize
					}
				});
			}

			//重置
			function reset() {
				searchForm.getForm().findField("vendorCode").setValue("");
				searchForm.getForm().findField("vendorName").setValue("");
				businessType.reset();
				childsType.reset();
			}

			var vendorType = new Ext.form.ComboBox(
					{
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
						width : 215,
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

			var childType = new Ext.form.ComboBox({
				id : 'childType',
				name : 'childType',
				hiddenName : "childTypeName",
				store : childProductStore,
				valueField : 'productTypeId',
				displayField : 'typeName',
				fieldLabel : '产品类型',
				emptyText : '请选择...',
				triggerAction : 'all',
				selectOnFocus : true,
				width : 215,
				editable : false,
				mode : 'local'
			});

			var vendorType2 = new Ext.form.ComboBox(
					{
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
						width : 215,
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

			var childType2 = new Ext.form.ComboBox({
				id : 'childType2',
				name : 'childType2',
				hiddenName : "childTypeName",
				store : childProductStore,
				valueField : 'productTypeId',
				displayField : 'typeName',
				fieldLabel : '产品类型',
				emptyText : '请选择...',
				triggerAction : 'all',
				selectOnFocus : true,
				width : 215,
				editable : false,
				mode : 'local'
			});

			//addfrom
			var addForm = new Ext.FormPanel(
					{
						labelWidth : 80,
						frame : true,
						fileUpload : true,
						title : '商户产品新增',
						labelAlign : 'right',
						buttonAlign : 'center',
						bodyStyle : 'padding:5px 5px 0',
						autoScroll : true,
						items : [ {
							layout : 'table',
							border : true,
							labelSeparator : ':',
							defaults : {
								bodyStyle : 'padding:1px'
							},
							layoutConfig : {
								columns : 2
							},
							items : [
									{
										layout : 'form',
										border : true,
										items : [ {
											xtype : 'textfield',
											fieldLabel : '产品名称',
											name : 'productName',
											width : 215,
											maxLength : 100,
											maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
											allowBlank : false,
											blankText : '商户名称不能为空'
										} ]
									},
									{
										layout : 'form',
										border : true,
										items : [ {
											xtype : 'textfield',
											fieldLabel : '产品特殊分类',
											name : 'specialType',
											width : 215,
											maxLength : 100,
											maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
											allowBlank : true
										} ]
									},
									{
										layout : 'form',
										border : true,
										items : [ vendorType ]
									},
									{
										layout : 'form',
										border : true,
										items : [ childType ]
									},
									{
										layout : 'form',
										border : true,
										items : [ {
											xtype : 'textfield',
											fieldLabel : '产品价格',
											name : 'singlePrice',
											width : 215,
											maxLength : 100,
											maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
											allowBlank : false,
											blankText : '产品价格不能为空'
										} ]
									},
									{
										layout : 'form',
										border : true,
										items : [ {
											xtype : 'textfield',
											fieldLabel : '优惠价格',
											name : 'salePrice',
											width : 215,
											maxLength : 100,
											maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
											allowBlank : false,
											blankText : '优惠价格不能为空'
										} ]
									}, {
										layout : 'form',
										border : true,
										colspan : 2,
										items : [ {
											xtype : 'newEditor',
											fieldLabel : '产品描述',
											name : 'description',
											width : 520,
											height : 300
										} ]
									},{
										layout : 'form',
										border : true,
										colspan : 2,
										items : [{
											xtype : 'textfield',
											fieldLabel : '上传产品图片',
											name : 'picture',
											width : 520,
											inputType : 'file',
											buttonText: '浏览'
										}]
									} ]
						} ],
						buttons : [
								{
									text : '提交',
									handler : function() {
										var values = addForm.getForm()
												.getValues();
										addForm.form
												.submit({
													url : '${ctx}/cooperationProduct/save.do',
													method : 'POST',
													success : function(
															form, action) {
														Ext.MessageBox
																.alert(
																		"提示",
																		action.result.msg);
														addWin.hide();
														store.load();
													},
													failure : function(
															form, action) {
														Ext.MessageBox
																.alert(
																		"提示",
																		action.result.msg);
													}
												});
									}
								}, {
									text : '取消',
									handler : function() {
										addWin.hide();
									}
								} ]
					});

			//modfrom
			var modForm = new Ext.FormPanel(
					{
						labelWidth : 80,
						frame : true,
						fileUpload : true,
						title : '商户产品修改',
						labelAlign : 'right',
						buttonAlign : 'center',
						bodyStyle : 'padding:5px 5px 0',
						autoScroll : true,
						items : [ {
							layout : 'table',
							border : true,
							labelSeparator : ':',
							defaults : {
								bodyStyle : 'padding:1px'
							},
							layoutConfig : {
								columns : 2
							},
							items : [
									{
										layout : 'form',
										border : true,
										items : [ {
											xtype : 'textfield',
											fieldLabel : '产品名称',
											name : 'productName',
											width : 215,
											maxLength : 100,
											maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
											allowBlank : false,
											blankText : '产品名称不能为空'
										} ]
									},
									{
										layout : 'form',
										border : true,
										items : [ {
											xtype : 'textfield',
											fieldLabel : '产品特殊分类',
											name : 'specialType',
											width : 215,
											maxLength : 100,
											maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
											allowBlank : true
										} ]
									},
									{
										layout : 'form',
										border : true,
										colspan : 2,
										items : [ {
											xtype : 'hidden',
											name : 'businessProductId'
										} ]
									},
									{
										layout : 'form',
										border : true,
										colspan : 2,
										items : [ {
											xtype : 'hidden',
											name : 'productCode'
										} ]
									},
									{
										layout : 'form',
										border : true,
										items : [ vendorType2 ]
									},
									{
										layout : 'form',
										border : true,
										items : [ childType2 ]
									},
									{
										layout : 'form',
										border : true,
										items : [ {
											xtype : 'textfield',
											fieldLabel : '产品价格',
											name : 'singlePrice',
											width : 215,
											maxLength : 100,
											maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
											allowBlank : false,
											blankText : '产品价格不能为空'
										} ]
									},
									{
										layout : 'form',
										border : true,
										items : [ {
											xtype : 'textfield',
											fieldLabel : '优惠价格',
											name : 'salePrice',
											width : 215,
											maxLength : 100,
											maxLengthText : '最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
											allowBlank : false,
											blankText : '优惠价格不能为空'
										} ]
									}, {
										layout : 'form',
										border : true,
										colspan : 2,
										items : [ {
											xtype : 'newEditor_mod',
											fieldLabel : '产品描述',
											name : 'description',
											width : 520,
											height : 300
										} ]
									},{
										layout : 'form',
										border : true,
										colspan : 2,
										items : [{
											xtype : 'textfield',
											fieldLabel : '上传产品图片',
											name : 'picture',
											width : 520,
											inputType : 'file',
											buttonText: '浏览'
										}]
									} ]
						} ],
						buttons : [
								{
									text : '提交',
									handler : function() {
										var values = modForm.getForm()
												.getValues();
										modForm.form
												.submit({
													url : '${ctx}/cooperationProduct/update.do',
													method : 'POST',
													success : function(
															form, action) {
														Ext.MessageBox
																.alert(
																		"提示",
																		action.result.msg);
														modWin.hide();
														store.load();
													},
													failure : function(
															form, action) {
														Ext.MessageBox
																.alert(
																		"提示",
																		action.result.msg);
													}
												});
									}
								}, {
									text : '取消',
									handler : function() {
										modWin.hide();
									}
								} ]
					});

			//新增
			var addWin;
			addBtn.on('click', function() {
				setPath('${ctx}/cooperationProduct/upload.do');
				if (!addWin) {
					addWin = new Ext.Window({
						el : 'add_product',
						layout : 'fit',
						closeAction : 'hide',
						width : 720,
						height : 550,
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
			var modWin;
			modBtn
					.on(
							'click',
							function() {
								setPath('${ctx}/cooperationProduct/upload.do');
								if (gridForm.getSelectionModel()
										.hasSelection()) {
									var record = gridForm
											.getSelectionModel()
											.getSelected();
									if (!modWin) {
										modWin = new Ext.Window({
											el : 'mod_product',
											layout : 'fit',
											closeAction : 'hide',
											width : 720,
											height : 550,
											modal : true,
											collapsible : true,
											constrain : true,
											resizable : false,
											items : modForm
										});
									}
									modWin
											.on(
													'show',
													function() {

														var businessProductId = record
																.get("businessProductId");
														var productCode = record
																.get("productCode");
														var productName = record
																.get("productName");
														var description = record
																.get("description");
														var singlePrice = record
																.get("singlePrice");
														var salePrice = record
																.get("salePrice");
														var parentTypeCode = record
																.get("parentTypeCode");
														var childType2 = record
																.get("typeCode");
														var specialType = record
																.get("specialType");
														//				var childType2Value = record.get("typeCode");

														var mForm = modForm
																.getForm();
														mForm.reset();
														mForm
																.findField(
																		'businessProductId')
																.setValue(
																		businessProductId);
														mForm
																.findField(
																		'productName')
																.setValue(
																		productName);
														mForm
																.findField(
																		'productCode')
																.setValue(
																		productCode);
														mForm
																.findField(
																		'vendorType2')
																.setValue(
																		parentTypeCode);
														mForm
																.findField(
																		'description')
																.setValue(
																		description);
														mForm
																.findField(
																		'singlePrice')
																.setValue(
																		singlePrice);
														mForm
																.findField(
																		'salePrice')
																.setValue(
																		salePrice);
														mForm
																.findField(
																		'specialType')
																.setValue(
																		specialType);
														if (parentTypeCode != null) {
															childProductStore.proxy = new Ext.data.HttpProxy(
																	{
																		url : '${ctx}/cooperationProduct/getChildProductType.do?parentType='
																				+ parentTypeCode
																	});
															childProductStore
																	.load();
															mForm
																	.findField(
																			'childType2')
																	.setValue(
																			childType2);
															//					 mForm.findField('childTypeName').setValue(childType2Value);
														}
													});
									modWin.show(this);
								} else {
									Ext.MessageBox.alert("错误", "没有选中修改项");
									return;
								}
							});

			//删除
			delBtn
					.on(
							'click',
							function() {
								if (gridForm.getSelectionModel()
										.hasSelection()) {
									Ext.MessageBox.confirm('提示',
											'确认要删除该记录吗?', submitDel);
									function submitDel(btn) {
										if (btn == 'yes') {
											Ext.MessageBox.wait(
													"数据正在处理中,请稍後...", "提交");
											var record = gridForm
													.getSelectionModel()
													.getSelected();
											var id = record
													.get('businessProductId');
											Ext.Ajax
													.request({
														url : '${ctx}/cooperationProduct/delete.do',
														method : 'POST',
														params : {
															businessProductId : id
														},
														success : function(
																response,
																request) {
															var jsonObj = eval('('
																	+ response.responseText
																	+ ')');
															Ext.MessageBox
																	.hide();
															Ext.MessageBox
																	.alert(
																			"提示",
																			jsonObj.msg);
															store.load();
														},
														failure : function(
																response,
																request) {
															Ext.MessageBox
																	.hide();
															Ext.MessageBox
																	.alert(
																			"提示",
																			"删除失败");
														}
													});
										}
									}
								} else {
									Ext.MessageBox.alert("错误", "没有选中删除项");
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
	<div id="add_product"></div>
	<div id="mod_product"></div>
</body>
</html>