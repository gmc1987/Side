Ext.onReady(function() {

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	var ordertype_yg = '01';
	var intPageSize =30;
	var cur_status_src = [['0101', '未过期'], ['0102', '已过期'],['0103', '未到期']];
	var dsCurStatus = new Ext.data.SimpleStore({
		fields : ['cur_status_id', 'cur_status_name'],
		data : cur_status_src
	});
	var publish_status_src = [ ['00', '已发布'],['01', '等待发布'],['02', '发布中']];
	var dsPublishStatus = new Ext.data.SimpleStore({
		fields : ['publish_status_id', 'publish_status_name'],
		data : publish_status_src
	});
	var dsNewsType_src = [ ['00', '普通公告'],['01', '重要公告']];
	var dsNewsType = new Ext.data.SimpleStore({
		fields : ['new_type_id', 'new_type_nm'],
		data : dsNewsType_src
	});
	
	
	var publishstatusType_query = new Ext.form.ComboBox({
		name : 'publishstatusType_query',
		store : dsPublishStatus,
		valueField : 'publish_status_id',
		displayField : 'publish_status_name',
		fieldLabel : '发布状态',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local'
	});
	
	var curstatusType = new Ext.form.ComboBox({
		name : 'cur_status_id',
		store : dsCurStatus,
		valueField : 'cur_status_id',
		displayField : 'cur_status_name',
		fieldLabel : '当前状态',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local'
	});
	var NewsType = new Ext.form.ComboBox({
		name : 'NewsType_id',
		store : dsNewsType,
		valueField : 'new_type_id',
		displayField : 'new_type_nm',
		fieldLabel : '公告类型',
		emptyText : '请选择...',
		triggerAction : 'all',
		selectOnFocus : true,
		width : 120,
		editable : false,
		mode : 'local'
	});
	
	var d1 = new Ext.form.DateField({
		name : 'create_date_begin',
		fieldLabel : '发布开始日期',
		width : 120,
		format : 'Ymd'
	});
	var d2 = new Ext.form.DateField({
		name : 'create_date_end',
		width : 120,
		fieldLabel : '发布截止日期',
		format : 'Ymd'
	});

	var t_pubtile = new Ext.form.TextField({
		fieldLabel : '公告标题',
		name : 't_pubtile',
		maxLength : 100,
		maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
		width : 120,
		validator : function(){
						var len = validationStrLen(this.getValue());				
								if (len>this.maxLength){
									this.invalidText='最大字符长度不能超过:'+this.maxLength+',(注:每个中文字符长度为2)';
									return false;
								}else{
									return true;
								}
					}
	});
	var ds = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : './AdminAction.do?func=GFacade',
			success : function(res, req) {
				var node = res.responseXML
						.selectSingleNode("//message/resultSet");
				if (node == null) {
					Ext.MessageBox.alert("提示", "通讯失败");
				}else {
					var errorCode = node.getAttribute("errorCode");
					if (errorCode != '0000') {
						if(errorCode == '8888'){
							parent.window.location.href= basePath + "userLoginOut.do";
						}else{
						 	return Ext.MessageBox.alert("提示", node.getAttribute("errorDesc"));
						}
					}
				}
			}
		}),
			reader : new Ext.data.XmlReader({
			totalRecords : 'recordCount',
			record : 'taskInfo'
		}, [{
			name : 'id',
			mapping : '@id'
		}, {
			name : 'ordertype_id',
			mapping : '@ordertype_id'
		}, {
			name : 'publish_type',
			mapping : '@publish_type'
		}, {
			name : 'publish_status',
			mapping : '@publish_status'
		}, {
			name : 'publish_title',
			mapping : '@publish_title'
		}, {
			name : 'publish_content',
			mapping : '@publish_content'
		}, {
			name : 'publish_addr',
			mapping : '@publish_addr'
		}, {
			name : 'group_code',
			mapping : '@group_code'
		}, {
			name : 'link_href',
			mapping : '@link_href'
		}, {
			name : 'publish_date',
			mapping : '@publish_date'
		}, {
			name : 'expire_date',
			mapping : '@expire_date'
		}, {
			name : 'publish_desc',
			mapping : '@publish_desc'
		}, {
			name : 'cur_status',
			mapping : '@cur_status'
		}, {
			name : 'create_oper',
			mapping : '@create_oper'
		}, {
			name : 'create_date',
			mapping : '@create_date'
		}, {
			name : 'create_time',
			mapping : '@create_time'
		}, {
			name : 'modify_oper',
			mapping : '@modify_oper'
		}, {
			name : 'modify_date',
			mapping : '@modify_date'
		}, {
			name : 'publishSeq',
			type : "int",
			mapping : '@publishSeq'
		},{
			name : 'modify_time',
			mapping : '@modify_time'
		}
		])
	});
	
	var loadGoodsMod = function() {
	ds.proxy.on("beforeload", function(obj, params) {
		var proxy = new XMLProxy();
		var doc = proxy.getMsgCtn();
		var reqNode = doc.createElement(proxy.c_reqElementName);
		reqNode.setAttribute("__reqModule", "publishinf");
		reqNode.setAttribute("__reqType", "queryList");
		reqNode.setAttribute("publish_type", NewsType.getValue());
		reqNode.setAttribute("cur_status", curstatusType.getValue());
		reqNode.setAttribute("publish_status", publishstatusType_query.getValue());
		reqNode.setAttribute("publish_title", t_pubtile.getValue());
		reqNode.setAttribute("ordertype_id", ordertype_yg);
		reqNode.setAttribute("expire_date",  Ext.util.Format.date(d2.getValue(), 'Ymd'));
		reqNode.setAttribute("publish_date",  Ext.util.Format.date(d1.getValue(), 'Ymd'));
		reqNode.setAttribute("__start", params["start"]);
		reqNode.setAttribute("__limit", params["limit"]);
		doc.documentElement.appendChild(reqNode);
		obj.conn.xmlData = doc;
	});
	ds.load({
						params : {
							start : 0,
							limit : intPageSize
						}
					});
	pubBtn.setDisabled(true);				
					
}
					
	var btnSubmit = new Ext.Button({
		text : '查询',
		listeners : {
			click : function() {
				if (myForm.getForm().isValid()) {
					loadGoodsMod();
				}
			}
		}
	});
	var btnCancel = new Ext.Button({
		text : '重置',
		handler : function() {
			myForm.form.reset();
		}
	});
	
	
	var myForm = new Ext.form.FormPanel({

		labelAlign : 'left',
		title : '公告消息维护',
		buttonAlign : 'left',
		bodyStyle : 'padding:1px',
		width : 855,
		frame : true,
		renderTo : 'queryArea',
		items : [{
			layout : 'column',
			border : true,
			labelSeparator : '：',
			items : [ 
			{	
				labelWidth : 80,
				columnWidth : .30,
				layout : 'form',
				border : false,
				items : [NewsType]
			},{
				labelWidth : 80,
				columnWidth : .30,
				layout : 'form',
				border : false,
				items : [curstatusType]
			}, {
				labelWidth : 80,
				columnWidth : .30,
				layout : 'form',
				border : false,
				items : [publishstatusType_query]
			}, {
				layout : 'column',
				border : false,
				columnWidth : 1,
				items : [ {
				labelWidth : 80,
				columnWidth : .30,
				layout : 'form',
				border : false,
				items : [d1]
			}, {
				labelWidth : 80,
				columnWidth : .30,
				layout : 'form',
				border : false,
				items : [d2]
			},{
				labelWidth : 80,
				columnWidth : .25,
				layout : 'form',
				border : false,
				items : [t_pubtile]
			}, 
			  {
				columnWidth : .06,
				layout : 'form',
				border : false,
				items : [btnSubmit]
			},{
				columnWidth : .06,
				layout : 'form',
				border : false,
				items : [btnCancel]
			}]
			}]
			
		}]
	});
	
	function formatStatus(val) {
	if (val == "0101") {
			return "未过期";
		} else if (val == "0102") {
			return "已过期";
		} else if (val == "0103") {
			return "未到期";
		} else
			return val;
	}
	
	function formatContent(val) {
		var regEx = /<[^>]*>/g;
		var nakeText = val.replace(regEx, "");

		return nakeText;
	}
	
	function p_status(val) {
		if (val == "00") {
			return "已发布";
		} else if (val == "01") {
			return "等待发布";
		} else if (val == "02") {
			return "发布中";
		} else
			return val;
	}
	function new_status(val) {
		if (val == "00") {
			return "普通公告";
		} else if (val == "01") {
			return "重要公告";
		} 
	}
var smPayway_zd = new Ext.grid.CheckboxSelectionModel();
	var colModel = new Ext.grid.ColumnModel([
	//smPayway_zd,
	{
		id : 'id',
		header : "id",
		width : 100,
		sortable : true,
		locked : false,
		dataIndex : 'id',
		hidden : true
	}, {
		header : "公告类型",
		width : 80,
		sortable : true,
		dataIndex : 'publish_type',
		renderer : new_status
	},{
		header : "显示位置",
		width : 100,
		sortable : true,
		hidden : true,
		dataIndex : 'publish_addr'
	},{
		header : "group_code",
		width : 100,
		sortable : true,
		hidden : true,
		dataIndex : 'group_code'
	},{
		header : "公告标题",
		width : 220,
		sortable : true,
		dataIndex : 'publish_title'
	},{
		header : "公告内容",
		width : 160,
		sortable : true,
		dataIndex : 'publish_content',
		renderer : formatContent
	}, {
		header : "显示顺序",
		width : 70,
		sortable : true,
		dataIndex : 'publishSeq'
	},{
		header : "发布状态",
		width : 70,
		sortable : true,
		dataIndex : 'publish_status',
		renderer : p_status
	}, {
		header : "发布日期",
		width : 70,
		sortable : true,
		dataIndex : 'publish_date'
	}, {
		header : "过期日期",
		width : 70,
		sortable : true,
		dataIndex : 'expire_date'
	},  {
		header : "当前状态",
		width : 70,
		sortable : true,
		dataIndex : 'cur_status',
		renderer : formatStatus
	}, {
		header : "备注",
		width : 180,
		sortable : true,
		dataIndex : 'publish_desc'
	},{
		header : "创建用户",
		width : 80,
		sortable : true,
		hidden : true,
		dataIndex : 'create_oper'
	}, {
		header : "创建日期",
		width : 80,
		sortable : true,
		hidden : true,
		dataIndex : 'create_date'
	}, {
		header : "创建时间",
		width : 80,
		sortable : true,
		hidden : true,
		dataIndex : 'create_time'
	}, {
		header : "最近修改用户",
		width : 80,
		sortable : true,
		hidden : true,
		dataIndex : 'modify_oper'
	}, {
		header : "最近修改日期",
		width : 80,
		sortable : true,
		hidden : true,
		dataIndex : 'modify_date'
	}, {
		header : "最近修改时间",
		width : 80,
		sortable : true,
		hidden : true,
		dataIndex : 'modify_time'
	}]);

	var addBtn = new Ext.Button({
		text : '新增',
		///disabled:true,
		iconCls : 'add'
	});

	var modBtn = new Ext.Button({
		text : '修改',
		//disabled:true,
		iconCls : 'edit'
	});

	var delBtn = new Ext.Button({
		text : '删除',
		iconCls : 'remove'
	});
	var pubBtn = new Ext.Button({
		text : '发布',
		iconCls : 'edit'
	});
	pubBtn.on('click', function() {
	if (gridForm.getSelectionModel().hasSelection()) {

				Ext.MessageBox.confirm('提示', '确认要进行发布?', submitSend);
				function submitSend(btn) {
				if (btn == 'yes') {
					var record = gridForm.getSelectionModel().getSelected();
					var id = record.get('id');
					var curStatus =record.get('cur_status');
						var proxy = new XMLProxy();
						var doc = proxy.getMsgCtn();
						var reqNode = doc.createElement(proxy.c_reqElementName);
						reqNode.setAttribute("__reqModule", "publishinf");
						reqNode.setAttribute("__reqType", "publishObj");
						reqNode.setAttribute("id", id);
                        reqNode.setAttribute("curStatus", curStatus);
						doc.documentElement.appendChild(reqNode);
					    Ext.Ajax.request({
						url : './AdminAction.do?func=GFacade',
						method : 'POST',
						xmlData : doc,
						success : function(response, request) {
							var node = response.responseXML
									.selectSingleNode("//message/resultSet");
							var errorCode = node.getAttribute("errorCode");
							if (errorCode == "0000") {
								Ext.MessageBox.alert("提示", "发布成功");
								loadGoodsMod();
							} else {
								if(errorCode == '8888'){
							parent.window.location.href= basePath + "userLoginOut.do";
						}else{
						 	return Ext.MessageBox.alert("提示", node.getAttribute("errorDesc"));
						}
							}
						}
					});
				}
			}
		}else{
				Ext.MessageBox.alert('提示', "请选择公告信息");
			}
		
	
	});
	var viewBtn = new Ext.Button({
		text : '预览',
		iconCls:'image',
		listeners : {
			click : function() {
			if (gridForm.getSelectionModel().hasSelection()) {
			var array = gridForm.getSelectionModel().getSelections();
				if(array.length>1 ||array.length==0 ){
					 Ext.MessageBox.alert("提示", "请选择一条记录");
					 return;
				}
				Ext.MessageBox.confirm('提示', '确认要进行预览?', submitSend);
				function submitSend(btn) {
					if (btn == 'yes') {
							var record = gridForm.getSelectionModel().getSelected();
							var publish_id = record.get('id');
							//loadScan(publish_id);
							var url = urlPath+"SmartPreview/ma.html?doAction=notice_preview&id="+publish_id;
							openWindow(url,800,720);
					}
				}
				
				} else {
							Ext.MessageBox.alert('提示', "请选择一条记录");
						}
			}
			
		}
	});


	var gridForm = new Ext.grid.GridPanel({
		id : 'opercode-grid',
		store : ds,
		cm : colModel,
		//sm : smPayway_zd,
		viewConfig : {
			forceFit : false
		},
		tbar : [addBtn, '-', modBtn, '-', delBtn,'-',viewBtn,'-',pubBtn],
		bbar : new Ext.PagingToolbar({
			pageSize : intPageSize,
			store : ds,
			beforePageText : '第',
			afterPageText : '页,共 {0} 页',
			displayInfo : true,
			displayMsg : '{0} - {1} , 共 {2} 条',
			emptyMsg : "无记录"
		}),
		width : 855,
		height : 380,
		frame : false,
		collapsible : true,
		animCollapse : false,
		trackMouseOver : false,
		iconCls : 'icon-grid',
		loadMask : ({
			msg : '数据正在加载中,请稍後...'
		}),
		renderTo : 'dataArea'
	});
	gridForm.addListener('rowclick', clickInfo);
	function clickInfo(gridForm, rowIndex, e) {
		var record = gridForm.getSelectionModel().getSelected();
		var publish_sta = record.get('publish_status');
		var cur_sta = record.get('cur_status');
		if(publish_sta == '01' && cur_sta == '0101')
		{
		pubBtn.setDisabled(false);
		}else{
		pubBtn.setDisabled(true);
	}
	}
	/*
	 * 新增的form
	 */
	var publish_date = new Ext.form.DateField({
		name : 'publish_date',
		fieldLabel : '发布日期',
		allowBlank : false,
		format : 'Ymd',
		width : 128,
		listeners : {
			change : function(field, newValue, oldValue) {
				expire_date.setMinValue(newValue);
			}
		}
	});
	var expire_date = new Ext.form.DateField({
		name : 'expire_date',
		fieldLabel : '过期日期',
		allowBlank : false,
		format : 'Ymd',
		width : 128,
		listeners : {
			change : function(field, newValue, oldValue) {
				publish_date.setMaxValue(newValue);
			}
		}
	});
	
	var publish_date_mod = new Ext.form.DateField({
		name : 'publish_date_mod',
		fieldLabel : '发布日期',
		allowBlank : false,
		format : 'Ymd',
		//disabled : true,
		width : 128,
		listeners : {
			change : function(field, newValue, oldValue) {
				expire_date_mod.setMinValue(newValue);
			}
		}
	});
	var expire_date_mod = new Ext.form.DateField({
		name : 'expire_date_mod',
		fieldLabel : '过期日期',
		allowBlank : false,
		//disabled : true,
		format : 'Ymd',
		width : 128,
		listeners : {
			change : function(field, newValue, oldValue) {
				publish_date_mod.setMaxValue(newValue);
			}
		}
	});

	
	var addForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '新增公告消息信息',
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
			items : [ {
				colspan : 2,
				layout : 'form',
				border : false,
				items : [{
					xtype : 'textfield',
					fieldLabel : '公告标题',
					name : 'publish_title',
					width : 553,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '公告标题不能空',
			    	validator : function(){
						var len = validationStrLen(this.getValue());				
								if (len>this.maxLength){
									this.invalidText='最大字符长度不能超过:'+this.maxLength+',(注:每个中文字符长度为2)';
									return false;
								}else{
									return true;
								}
					}
				}]
			},{
						colspan : 2,
						layout : 'form',
						items : [{
							layout : 'table',
							colspan : 2,
							height : 306,
							items :[
							{	layout : 'table',
								colspan : 1,
								xtype : 'label',
								text : '公告内容： ',
								width : 89,
								style : 'font-size:12px'
							
							},
							{
								layout : 'table',
								colspan : 2,
								xtype : 'newEditor_dz',//编辑框功能所用
								name : 'publish_content',
								width : 550,
								height : 300
							}
							]
						}]
					},{
					colspan : 2,
					layout : 'form',
					border : false,
					items : [{
					xtype : 'textarea',
					fieldLabel : '备注',
					name : 'publish_desc',
					width : 550, 
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					validator : function(){
						var len = validationStrLen(this.getValue());				
								if (len>this.maxLength){
									this.invalidText='最大字符长度不能超过:'+this.maxLength+',(注:每个中文字符长度为2)';
									return false;
								}else{
									return true;
								}
					}
				}]
			},
		{
				columnWidth : .5,
				layout : 'form',
				items : [new Ext.form.ComboBox({
						name : 'publish_type',
						store : dsNewsType,
						valueField : 'new_type_id',
						displayField : 'new_type_nm',
						fieldLabel : '公告类型',
						//labelStyle : 'width:80;color:red',
						emptyText : '请选择...',
						triggerAction : 'all',
						selectOnFocus : true,
						width : 128,
						editable : false,
						allowBlank : false,
						blankText : '不能空',
						mode : 'local'
	})]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [new Ext.form.ComboBox({
						tpl : '<tpl for="."><div class="x-combo-list-item">{publish_status_name}</div></tpl>',
						name : 'publish_type',
						store : dsPublishStatus,
						valueField : 'publish_status_id',
						displayField : 'publish_status_name',
						//labelStyle : 'width:80;color:red',
						fieldLabel : '发布状态',
						emptyText : '请选择...',
						triggerAction : 'all',
						selectOnFocus : true,
						width : 128,
						editable : false,
						allowBlank : false,
						blankText : '发布状态不能空',
						mode : 'local',
						disabled : true,
						value : '01'
	      })]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [publish_date]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [expire_date]
			} ,{
				columnWidth : .5,
				layout : 'form',
				items : [new Ext.form.ComboBox({
					name : 'cur_status',
					store : dsCurStatus,
					valueField : 'cur_status_id',
					displayField : 'cur_status_name',
					fieldLabel : '当前状态',
					emptyText : '请选择...',
					triggerAction : 'all',
					selectOnFocus : true,
					width : 128,
					value : '0101',
					disabled : true,
					editable : false,
					allowBlank : false,
					blankText : '当前状态不能空',
					mode : 'local'
	})]
			},{
				layout : 'form',
				items : [{
					xtype : 'numberfield',
					fieldLabel : '显示顺序',
					name : 'publishSeq',
					width : 128,
					minValue : 1,
					minText : '最小不能小于{0}',
					maxLength : 3,
					maxLengthText : '不能超过3位',
					allowBlank : false,
					blankText : '显示顺序不能空'       
			        
				}]
			}]
		}],

		buttons : [{
			text : '保存',
			handler : function() {
				if (addForm.getForm().isValid()) {
				
					submitAddForm(addForm.getForm());
					
				}
			}
		}, {
			text : '关闭',
			handler : function() {
				oWin.hide();
				publish_date.reset();
				expire_date.reset();
			}
		}]
	});
	
	/*
	 * 提交新增
	 */
	function submitAddForm(formName) {
		Ext.MessageBox.wait("数据正在处理中,请稍後...", "提交");
		
		var publishContent = formName.findField('publish_content').getValue();
		if(publishContent == ""){
				Ext.MessageBox.alert("提示","公告内容不能为空");
				return;
		}
		if(validationStrLen(publishContent) > 2500){
				Ext.MessageBox.alert("提示","公告内容最大字符长度不能超过:2500,(注:每个中文字符长度为2)");
				return;
		}
		if(Ext.util.Format.date(new Date(), 'Ymd') > 
				Ext.util.Format.date(formName.findField('publish_date').getValue(), 'Ymd')){
			return Ext.MessageBox.alert("提示", "发布日期不能小于系统日期");
		}
		if(Ext.util.Format.date(formName.findField('publish_date').getValue(), 'Ymd') >
				Ext.util.Format.date(formName.findField('expire_date').getValue(), 'Ymd')){
			return Ext.MessageBox.alert("提示", "发布日期不能大于或等于过期日期");
		}
		
		var proxy = new XMLProxy();
		var doc = proxy.getMsgCtn();
		var reqNode = doc.createElement(proxy.c_reqElementName);
		reqNode.setAttribute("__reqModule", "publishinf");
		reqNode.setAttribute("__reqType", "saveObj");
		reqNode.setAttribute("ordertype_id", ordertype_yg);
		reqNode.setAttribute("publish_title", formName
				.findField('publish_title').getValue());
		//		去掉图片里src的IP和端口
	
		var re = /src="http:\/\/(\d+)\.(\d+)\.(\d+)\.(\d+):(\d+)/g;
		publishContent = publishContent.replace(re,'src="');
		reqNode.setAttribute("publish_content",publishContent);	
		reqNode.setAttribute("publish_type", formName
				.findField('publish_type').getValue());
		reqNode.setAttribute("publish_status", '01');
		
		reqNode.setAttribute("publish_date", Ext.util.Format.date(formName
				.findField('publish_date').getValue(), 'Ymd'));
		reqNode.setAttribute("expire_date", Ext.util.Format.date(formName
				.findField('expire_date').getValue(), 'Ymd'));
		reqNode.setAttribute("cur_status", formName.findField('cur_status')
				.getValue());
		reqNode.setAttribute("publish_desc", formName.findField('publish_desc')
				.getValue());
		reqNode.setAttribute("publishSeq", formName.findField('publishSeq')
				.getValue());	
		reqNode.setAttribute("__start", '0');
		reqNode.setAttribute("__limit", '1');
		
		doc.documentElement.appendChild(reqNode);
		Ext.Ajax.request({
			url : './AdminAction.do?func=GFacade',
			method : 'POST',
			xmlData : doc,
			success : function(response, request) {
				Ext.MessageBox.hide();
				var node = response.responseXML
						.selectSingleNode("//message/resultSet");
				if (node == null) {
					Ext.MessageBox.alert("提示", "通讯失败");
				} else {
					var errorCode = node.getAttribute("errorCode");
					if (errorCode == '0000') {
						Ext.MessageBox.alert("提示", "新增成功");
						loadGoodsMod();
						publish_date.reset();
						expire_date.reset();
					} else {
						if(errorCode == '8888'){
							parent.window.location.href= basePath + "userLoginOut.do";
						}else{
						 	return Ext.MessageBox.alert("提示", node.getAttribute("errorDesc"));
						}
					}
				}
				oWin.hide();
			}
		});

	}
	/*
	 * 修改的form
	 */
	
		var modForm = new Ext.form.FormPanel({
		frame : true,
		labelAlign : 'right',
		labelWidth : 80,
	//	width : 640,	
		waitMsgTarget : true,
		bodyStyle : 'padding:5px 5px 0',
		autoScroll : true,
		title:'修改公告消息信息',
		items : [{
			xtype : 'hidden',
			fieldLabel : 'id',
			name : 'id',
			disabled : true
		},{
			layout : 'table',
			border : true,
			labelSeparator : ':',
			defaults : {
				bodyStyle : 'padding:1px'
			},
			layoutConfig : {
				columns : 2
			},
			items : [{
			    colspan : 2,
				layout : 'form',
			//	width : 600,
				items : [{
					xtype : 'textfield',
					fieldLabel : '公告标题',
					name : 'publish_title',
					width : 553,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '公告标题不能空',
			    	validator : function(){
						var len = validationStrLen(this.getValue());				
								if (len>this.maxLength){
									this.invalidText='最大字符长度不能超过:'+this.maxLength+',(注:每个中文字符长度为2)';
									return false;
								}else{
									return true;
								}
					}
				}]
			},{
						colspan : 2,
						layout : 'form',
						items : [{
							layout : 'table',
							colspan : 2,
							height : 306,
							items :[
							{	layout : 'table',
								colspan : 1,
								xtype : 'label',
								text : '公告内容： ',
								width : 89,
								style : 'font-size:12px'
							
							},
							{
								layout : 'table',
								colspan : 2,
								xtype : 'newEditor_dz',//编辑框功能所用
								name : 'publish_content',
								width : 550,
								height : 300
							}
							]
						}]
					},{
				colspan : 2,
				layout : 'form',
					items : [{
					xtype : 'textarea',
					fieldLabel : '备注',
					name : 'publish_desc',
					width : 550, 
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					validator : function(){
						var len = validationStrLen(this.getValue());				
								if (len>this.maxLength){
									this.invalidText='最大字符长度不能超过:'+this.maxLength+',(注:每个中文字符长度为2)';
									return false;
								}else{
									return true;
								}
					}	       
			        
				}]
			},
			{
					columnWidth : .5,
					layout : 'form',
					items : [new Ext.form.ComboBox({
						name : 'publish_type',
						store : dsNewsType,
						valueField : 'new_type_id',
						displayField : 'new_type_nm',
						fieldLabel : '公告类型',
						emptyText : '请选择...',
						triggerAction : 'all',
						selectOnFocus : true,
						width : 128,
						editable : false,
						//disabled : true,
						allowBlank : false,
						blankText : '公告类型不能空',
						mode : 'local'
				 })]
			},{
					columnWidth : .5,
					layout : 'form',
					items : [new Ext.form.ComboBox({
						name : 'publish_status_mod',
						store : dsPublishStatus,
						valueField : 'publish_status_id',
						displayField : 'publish_status_name',
						fieldLabel : '发布状态',
						//labelStyle : 'width:80;color:red',
						emptyText : '请选择...',
						triggerAction : 'all',
						selectOnFocus : true,
						disabled : true,
						width : 128,
						editable : false,
						allowBlank : false,
						blankText : '发布状态不能空',	
						mode : 'local'
					})]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [publish_date_mod]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [expire_date_mod]
			} ,{
				columnWidth : .5,
				layout : 'form',
				items : [new Ext.form.ComboBox({
					name : 'cur_status1',
					store : dsCurStatus,
					valueField : 'cur_status_id',
					displayField : 'cur_status_name',
					fieldLabel : '当前状态',
					emptyText : '请选择...',
					triggerAction : 'all',
					selectOnFocus : true,
					width : 128,
					editable : false,
					disabled : true,
					allowBlank : false,
					blankText : '当前状态不能空',
					mode : 'local'
	})]
			},{
				layout : 'form',
				items : [{
					xtype : 'numberfield',
					fieldLabel : '显示顺序',
					name : 'publishSeq',
					width : 128,
					minValue : 1,
					minText : '最小不能小于{0}',
					maxLength : 3,
					maxLengthText : '不能超过3位',
					allowBlank : false,
					blankText : '显示顺序不能空'
				}]
			}]
		}],
		buttons : [{
			text : '保存',
			handler : function() {
				var formName = modForm.getForm();
				if (formName.isValid()) {
					var id = formName.findField('id').getValue();
					var publish_type = formName.findField('publish_type')
							.getValue();
					var publish_title = formName.findField('publish_title')
							.getValue();
					var publish_content = formName.findField('publish_content').getValue();
					var publish_status = formName.findField('publish_status_mod').getValue();
					var expire_date = Ext.util.Format.date(formName.findField('expire_date_mod').getValue(), 'Ymd');
					var publish_date = Ext.util.Format.date(formName.findField('publish_date_mod').getValue(), 'Ymd');
					var publish_desc = formName.findField('publish_desc').getValue();
					var cur_status = formName.findField('cur_status1').getValue();
					var publishSeq = formName.findField('publishSeq').getValue();
							
					if(publish_content == ""){
						Ext.MessageBox.alert("提示","公告内容不能为空");
						return;
					}
					if(validationStrLen(publish_content) > 2500){
						Ext.MessageBox.alert("提示","公告内容最大字符长度不能超过:2500,(注:每个中文字符长度为2)");
						return;
					}	
							
					var record = gridForm.getSelectionModel().getSelected();
					
					var proxy = new XMLProxy();
					var doc = proxy.getMsgCtn();

					var reqNode = doc.createElement(proxy.c_reqElementName);
					reqNode.setAttribute("__reqModule", "publishinf");
					reqNode.setAttribute("__reqType", "updateObj");
					reqNode.setAttribute("id", id);
					reqNode.setAttribute("publishSeq", publishSeq);
					reqNode.setAttribute("publish_type", publish_type);
					reqNode.setAttribute("publish_title", publish_title);
					var re = /src="http:\/\/(\d+)\.(\d+)\.(\d+)\.(\d+):(\d+)/g;
					publish_content = publish_content.replace(re,'src="');
					reqNode.setAttribute("publish_content", publish_content);
					reqNode.setAttribute("publish_date", publish_date);
					reqNode.setAttribute("expire_date", expire_date);
					reqNode.setAttribute("publish_desc", publish_desc);
					reqNode.setAttribute("publish_status", publish_status);
					reqNode.setAttribute("cur_status", cur_status);
					
					//reqNode.setAttribute("publish_method","modify"); //编辑框功能所用

					doc.documentElement.appendChild(reqNode);
					Ext.Ajax.request({
						url : './AdminAction.do?func=GFacade',
						method : 'POST',
						xmlData : doc,
						success : function(response, request) {
							var node = response.responseXML
									.selectSingleNode("//message/resultSet");
							if (node == null) {
								Ext.MessageBox.alert("提示", "通讯失败");
							} else {
								var errorCode = node.getAttribute("errorCode");
								if (errorCode == '0000') {
									Ext.MessageBox.alert("提示", "修改成功");
									loadGoodsMod();
									
								} else {
									if(errorCode == '8888'){
							parent.window.location.href= basePath + "userLoginOut.do";
						}else{
						 	return Ext.MessageBox.alert("提示", node.getAttribute("errorDesc"));
						}
								}
							}
							mWin.hide();
							publish_date_mod.reset();
							expire_date_mod.reset();
						}
					});
				}
			}
		}, {
			text : '关闭',
			handler : function() {
				mWin.hide();
				publish_date_mod.reset();
				expire_date_mod.reset();
			}
		}]
	});

	/*
	 * 点击新增按钮操作
	 */
	var oWin;
	addBtn.on('click', function() {
		if (!oWin) {
			oWin = new Ext.Window({
				el : 'add_pubwin',
				layout : 'fit',
				closeAction : 'hide',
				width : 720,
				height : 480,
				modal : true,
				collapsible : true,
				constrain : true,
				resizable : false,
				items : addForm
			});
			}
			oWin.on('show', function() {
				});
		addForm.getForm().reset();
		oWin.show(this);
	});
	/*
	 * 点击修改按钮操作
	 */
	var mWin;
	modBtn.on('click', function() {
		if (gridForm.getSelectionModel().hasSelection()) {
			var record = gridForm.getSelectionModel().getSelected();
			var publishState =record.get('publish_status');
			if(publishState == '02')
			{
			Ext.MessageBox.alert('提示', '发布中公告不能修改！');
			return ;
			}
			if (!mWin) {
				mWin = new Ext.Window({
					el : 'mod_pubwin',
					layout : 'fit',
					closeAction : 'hide',
					width : 720,
					height : 480,
					modal : true,
					collapsible : true,
					constrain : true,
					resizable : false,
					items : modForm
				});
				}
				mWin.on('show', function() {
					
					var id = record.get('id');
					var publish_type = record.get('publish_type');
					var publish_title = record.get('publish_title');
					var publish_content = record.get('publish_content');
					var publish_date = record.get('publish_date');
					var publish_addr = record.get('publish_addr');
					var publish_addr_nm = record.get('publish_addr_nm');
					var expire_date = record.get('expire_date');
					var publish_desc = record.get('publish_desc');
					var cur_status = record.get('cur_status');
					var pub_status = record.get('publish_status');
					var publishSeq = record.get('publishSeq');
					var mform = modForm.getForm();
					mform.reset();
					mform.findField('id').setValue(id);
					mform.findField('publishSeq').setValue(publishSeq);
					mform.findField('publish_type').setValue(publish_type);
					mform.findField('publish_title').setValue(publish_title);
					mform.findField('publish_content').setValue(publish_content);
					mform.findField('publish_date_mod').setValue(publish_date);
					mform.findField('expire_date_mod').setValue(expire_date);
					mform.findField('publish_desc').setValue(publish_desc);
					mform.findField('cur_status1').setValue(cur_status);
					mform.findField('publish_status_mod').setValue(pub_status);

				});
			
			mWin.show(this);
		} else {
			Ext.MessageBox.alert('提示', '请选中您要修改的记录');
		}
	});
	/*
	 * 删除选中的记录
	 */
	delBtn.on('click', function() {
		if (gridForm.getSelectionModel().hasSelection()) {
			var record = gridForm.getSelectionModel().getSelected();
			var publishState =record.get('publish_status');
			
			var curStatus = record.get('cur_status');
			if(publishState != '01'|| curStatus != '0102')
			{
			Ext.MessageBox.alert('提示', '只有等待发布且已过期的公告才可以删除！');
			return ;
			}
			Ext.MessageBox.confirm('删除记录', '确认进行删除吗?', showResult);
			function showResult(btn) {
				if (btn == 'yes') {
					Ext.MessageBox.wait("数据正在处理中,请稍後...", "提交");
					var id = record.get('id');
					var publish_status = record.get('publish_status');
					var proxy = new XMLProxy();
					var doc = proxy.getMsgCtn();

					var reqNode = doc.createElement(proxy.c_reqElementName);
					reqNode.setAttribute("__reqModule", "publishinf");
					reqNode.setAttribute("__reqType", "delObj");
					reqNode.setAttribute("ordertype_id", ordertype_yg);
					reqNode.setAttribute("id", id);
                    reqNode.setAttribute("publish_status", publish_status);
                    
					doc.documentElement.appendChild(reqNode);
					Ext.Ajax.request({
						url : './AdminAction.do?func=GFacade',
						method : 'POST',
						xmlData : doc,
						success : function(response, request) {
							Ext.MessageBox.hide();
							var node = response.responseXML
									.selectSingleNode("//message/resultSet");
							if (node == null) {
								Ext.MessageBox.alert("提示", "通讯失败");
							} else {
								var errorCode = node.getAttribute("errorCode");
								if (errorCode == '0000') {
									Ext.MessageBox.alert("提示", "删除成功");
									loadGoodsMod();
								} else {
									if(errorCode == '8888'){
							parent.window.location.href= basePath + "userLoginOut.do";
						}else{
						 	return Ext.MessageBox.alert("提示", node.getAttribute("errorDesc"));
						}
								}
							}
						}
					});
				}
			}
		} else {
			Ext.MessageBox.alert('提示', '请选中您要删除的记录');
		}
	});
	/*加载公告管理数据源*/
loadGoodsMod();
});