/**
 *
 * 重写htmlEditor,扩展新功能
 *
**/

var path = "";

function setPath(arg){
	if(arg != "" && arg != null){
		path = arg;
	}
}

Ext.onReady(function(){
	 Ext.QuickTips.init(); 
		
	/********************订单详情引用**********************/
	var newEditor = Ext.extend(Ext.form.HtmlEditor,{
		id:'newEditor',
		enableSourceEdit : false, //启用html编辑方式
		cls : 'word_break',
		fontFamilies : ['宋体','黑体','楷体_GB2312','Arial','Tahoma','Verdana'],
		enableFontSize : true, 
		addImages : function(){	//新增上传图片功能
		var editor = this;
		editor.focus(true);
		
		//上传图片表单对象
		var imaForm = new Ext.form.FormPanel({
			id : 'uploadForm',
			labelWidth : 60,
			fileUpload : true,
			autoHeight : true,
	        bodyStyle: 'padding: 10px 10px 0 10px;',
	        defaults: {
	            anchor: '95%',
	            allowBlank: false,
	            msgTarget: 'side'
	        },
			items : [{
				xtype : 'textfield',
				fieldLabel : '选择文件',
				name : 'formfile',
			    id : 'formfile',
	    		inputType : 'file',
	    		height : 25,
	    	    anchor : '98%',
	    		buttonText: '浏览'
			}],
			buttons : [{
				text : '上传',
				//上传按钮业务逻辑
				handler : function(){
					var me = editor;
					var fileUrl = imaForm.form.findField('formfile').getValue();//获取文件路径				
					var type = fileUrl.substring(fileUrl.length-3).toLowerCase();//获取文件后缀
					var fileName = fileUrl.substring(fileUrl.lastIndexOf("\\")+1);//获取文件名称
					var flag = 'xq';
					if(fileUrl == "" || fileUrl == null){
						return;
					}
					if(type != 'jpg' && type != 'bmp' && type != 'gif' && type != 'png'){
						alert("仅支持jpg、bmp、gif、png格式的图片");
						return;
					}
					
	                if(imaForm.getForm().isValid()){
	                	imaForm.getForm().submit({
		                    url: path,
		                    waitMsg : '图片上传中,请稍后...',
		                    method : 'POST',
		                    success : function(form, action){
	                			var element = document.createElement('img');
	                				//图片路径
		                			element.src = action.result.path;
		                			element.border = 0;
		                			element.style.border = "0px solid #d0d0d0";
	                		    if (Ext.isIE) {
	                		    	Ext.getCmp('newEditor').insertAtCursor(element.outerHTML);
//	                		    	rgObj.pasteHTML(element.outerHTML);   
//	                		   		rgObj.select();
//	               			    	rgObj=false;//清空下range对象
	                		    } else {
	                		         var selection = editor.win.getSelection();
	                		         if (!selection.isCollapsed) {
	                		        	 selection.deleteFromDocument();
	                		         }
	                		         selection.getRangeAt(0).insertNode(element);
	                		    }
	                		    editor.syncValue();	//编辑框数据同步
                		        win.close();    
		                    },
		                    failure : function(form, action){
		                    	form.reset();
		                    	alert(action.result.msg);
		                        if (action.failureType == Ext.form.Action.SERVER_INVALID)
		                        	Ext.MessageBox.alert(action.result.msg);
		                    }
		                });
	                }
            	}	
			},{
				text : '重设',
				handler : function(){
					var obj = document.getElementById("formfile");   
 	 		 		obj.outerHTML = obj.outerHTML; 
				}
			}]
		});

		var win = new Ext.Window({
		      title : '上传图片',
		      id : 'picwin',
		      width : 420,
		      height : 120,
		      modal : true,
		      border : false,
		      iconCls:'icon-uploadpic',
		      layout : 'fit',
		      items : imaForm

		     });
		   win.show();
		  
		},
		
		createToolbar : function(editor) {
		   newEditor.superclass.createToolbar.call(this, editor);
		   this.tb.insertButton(16, {
		      cls : "x-btn-icon",
		      icon : "../images/icon/Nuvola/16/actions/document-open.png",
		      handler : this.addImages,
		      tooltip : "添加图片",
		      scope : this
		   });
//		   var me = editor;
//		   var fontData = [['1','1号'],['2','2号'],['3','3号'],['4','4号'],['5','5号'],['6','6号'],['7','7号']];
//		   var font_size = new Ext.form.ComboBox({
//				name : 'fontSize',
//				emptyText : '字号',
//				maxHeight : 80,
////				ctCls: 'x-richedit-font-size',
//				store : fontData,
//				width: 60,
//				colspan: 2,
//				typeAhead: true,
//				triggerAction: 'all',
//				mode: 'local',
//				valueField: 'value',
//				displayField: 'text',
//				listeners: {
//					select: function (c, n, o) { 
//						//执行FontSize命令
//			   			me.currentSelected.execCommand('FontSize', false, c.getValue());
//						me.deferFocus();
//						me.currentSelected = null;
//					},
//					expand:function(){
//						//创建选区对象
//						var rg = me.getDoc().selection.createRange();
//						me.currentSelected = rg;
//					}
//				}
//			});
//		   this.tb.add(font_size);
		}
	});
	
	//注册组件
	Ext.reg('newEditor', newEditor);
	
	
	/********************温馨提示引用**********************/
	var newEditor_mod = Ext.extend(Ext.form.HtmlEditor,{
		id:'newEditor_mod',
		enableSourceEdit : false, //启用html编辑方式
		cls : 'word_break',
		fontFamilies : ['宋体','黑体','楷体_GB2312','Arial','Tahoma','Verdana'],
		enableFontSize : true, 
		addImages : function(){	//新增上传图片功能
		var editor = this;
		editor.focus(true);
		
		//上传图片表单对象
		var imaForm = new Ext.form.FormPanel({
			id : 'uploadForm',
			labelWidth : 60,
			fileUpload : true,
			autoHeight : true,
	        bodyStyle: 'padding: 10px 10px 0 10px;',
	        defaults: {
	            anchor: '95%',
	            allowBlank: false,
	            msgTarget: 'side'
	        },
			items : [{
				xtype : 'textfield',
				fieldLabel : '选择文件',
				name : 'formfile',
			    id : 'formfile',
	    		inputType : 'file',
	    		height : 25,
	    	    anchor : '98%',
	    		buttonText: '浏览'
			}],
			buttons : [{
				text : '上传',
				//上传按钮业务逻辑
				handler : function(){
					var me = editor;
					var fileUrl = imaForm.form.findField('formfile').getValue();//获取文件路径				
					var type = fileUrl.substring(fileUrl.length-3).toLowerCase();//获取文件后缀
					var fileName = fileUrl.substring(fileUrl.lastIndexOf("\\")+1);//获取文件名称
					var flag = 'xq';
					if(fileUrl == "" || fileUrl == null){
						return;
					}
					if(type != 'jpg' && type != 'bmp' && type != 'gif' && type != 'png'){
						alert("仅支持jpg、bmp、gif、png格式的图片");
						return;
					}
					
	                if(imaForm.getForm().isValid()){
	                	imaForm.getForm().submit({
		                    url: path,
		                    waitMsg : '图片上传中,请稍后...',
		                    method : 'POST',
		                    success : function(form, action){
	                			var element = document.createElement('img');
	                				//图片路径
		                			element.src = action.result.path;
		                			element.border = 0;
		                			element.style.border = "0px solid #d0d0d0";
	                		    if (Ext.isIE) {
	                		    	Ext.getCmp('newEditor_mod').insertAtCursor(element.outerHTML);
//	                		    	rgObj.pasteHTML(element.outerHTML);   
//	                		   		rgObj.select();
//	               			    	rgObj=false;//清空下range对象
	                		    } else {
	                		         var selection = editor.win.getSelection();
	                		         if (!selection.isCollapsed) {
	                		        	 selection.deleteFromDocument();
	                		         }
	                		         selection.getRangeAt(0).insertNode(element);
	                		    }
	                		    editor.syncValue();	//编辑框数据同步
                		        win.close();    
		                    },
		                    failure : function(form, action){
		                    	form.reset();
		                    	alert(action.result.msg);
		                        if (action.failureType == Ext.form.Action.SERVER_INVALID)
		                        	Ext.MessageBox.alert(action.result.msg);
		                    }
		                });
	                }
            	}	
			},{
				text : '重设',
				handler : function(){
					var obj = document.getElementById("formfile");   
 	 		 		obj.outerHTML = obj.outerHTML; 
				}
			}]
		});

		var win = new Ext.Window({
		      title : '上传图片',
		      id : 'picwin',
		      width : 420,
		      height : 120,
		      modal : true,
		      border : false,
		      iconCls:'icon-uploadpic',
		      layout : 'fit',
		      items : imaForm

		     });
		   win.show();
		  
		},
		
		createToolbar : function(editor) {
		   newEditor_mod.superclass.createToolbar.call(this, editor);
		   this.tb.insertButton(16, {
		      cls : "x-btn-icon",
		      icon : "../images/icon/Nuvola/16/actions/document-open.png",
		      handler : this.addImages,
		      tooltip : "添加图片",
		      scope : this
		   });
//		   var me = editor;
//		   var fontData = [['1','1号'],['2','2号'],['3','3号'],['4','4号'],['5','5号'],['6','6号'],['7','7号']];
//		   var font_size = new Ext.form.ComboBox({
//				name : 'fontSize',
//				emptyText : '字号',
//				maxHeight : 80,
////				ctCls: 'x-richedit-font-size',
//				store : fontData,
//				width: 60,
//				colspan: 2,
//				typeAhead: true,
//				triggerAction: 'all',
//				mode: 'local',
//				valueField: 'value',
//				displayField: 'text',
//				listeners: {
//					select: function (c, n, o) { 
//						//执行FontSize命令
//			   			me.currentSelected.execCommand('FontSize', false, c.getValue());
//						me.deferFocus();
//						me.currentSelected = null;
//					},
//					expand:function(){
//						//创建选区对象
//						var rg = me.getDoc().selection.createRange();
//						me.currentSelected = rg;
//					}
//				}
//			});
//		   this.tb.add(font_size);
		}
	});
	
	//注册组件
	Ext.reg('newEditor_mod', newEditor_mod);
	
	/********************购物须知引用**********************/
	var newEditor_xz = Ext.extend(Ext.form.HtmlEditor,{
		enableSourceEdit : true, //启用html编辑方式
		cls : 'word_break',
		fontFamilies : ['宋体','黑体','楷体_GB2312','Arial','Courier New','Tahoma','Times New Roman','Verdana'],
		enableFontSize : false, 
		addImages : function(){	//新增上传图片功能
		var editor = this;
		
		editor.focus(true);
		var rgObj = editor.getDoc().selection.createRange();//获取当前光标对象
		
		//上传图片表单对象
		var imaForm = new Ext.form.FormPanel({
			id : 'uploadForm',
			labelWidth : 60,
			fileUpload : true,
			autoHeight : true,
	        bodyStyle: 'padding: 10px 10px 0 10px;',
	        defaults: {
	            anchor: '95%',
	            allowBlank: false,
	            msgTarget: 'side'
	        },
			items : [{
				xtype : 'textfield',
				fieldLabel : '选择文件',
				name : 'formfile',
			    id : 'formfile',
	    		inputType : 'file',
	    		height : 25,
	    	    anchor : '98%',
	    		buttonText: '浏览'
			}],
			buttons : [{
				text : '上传',
				//上传按钮业务逻辑
				handler : function(){
					var me = editor;
					var fileUrl = imaForm.form.findField('formfile').getValue();//获取文件路径
					var type = fileUrl.substring(fileUrl.length-3).toLowerCase();//获取文件后缀
					var fileName = fileUrl.substring(fileUrl.lastIndexOf("\\")+1);//获取文件名称
					var flag = 'xz';
					if(fileUrl == "" || fileUrl == null){
						return;
					}
					if(type != 'jpg' && type != 'bmp' && type != 'gif' && type != 'png'){
						alert("仅支持jpg、bmp、gif、png格式的图片");
						return;
					}
	                if(imaForm.getForm().isValid()){
	                	imaForm.getForm().submit({
		                    url: './uploadGoodsImg.do?method=uploadImg&flag='+flag,
		                    waitMsg : '图片上传中,请稍后...',
		                    method : 'POST',
		                    success : function(form, action){
	                			var element = document.createElement('img');
	                				//图片路径
		                			element.src = action.result.path;
		                			element.border = 0;
		                			element.style.border = "0px solid #d0d0d0";
	                		    if (Ext.isIE) {
	                		    	rgObj.pasteHTML(element.outerHTML);   
	                		   		rgObj.select();
	               			    	rgObj=false;//清空下range对象
	                		    } else {
	                		         var selection = editor.win.getSelection();
	                		         if (!selection.isCollapsed) {
	                		        	 selection.deleteFromDocument();
	                		         }
	                		         selection.getRangeAt(0).insertNode(element);
	                		    }
	                		    editor.syncValue();	//编辑框数据同步
                		        win.close();    
		                    },
		                    failure : function(form, action){
		                    	form.reset();
		                    	alert(action.result.msg);
		                        if (action.failureType == Ext.form.Action.SERVER_INVALID)
		                        	Ext.MessageBox.alert(action.result.msg);
		                    }
		                });
	                }
            	}	
			},{
				text : '重设',
				handler : function(){
					var obj = document.getElementById("formfile");   
 	 		 		obj.outerHTML = obj.outerHTML; 
				}
			}]
		});

		var win = new Ext.Window({
		      title : '上传图片',
		      id : 'picwin',
		      width : 420,
		      height : 120,
		      modal : true,
		      border : false,
		      iconCls:'icon-uploadpic',
		      layout : 'fit',
		      items : imaForm

		     });
		   win.show();
		  
		},
		
		createToolbar : function(editor) {
			newEditor.superclass.createToolbar.call(this, editor);
		   this.tb.insertButton(16, {
		      cls : "x-btn-icon",
		      iconCls : "image",
		      handler : this.addImages,
		      tooltip : "添加图片",
		      scope : this
		   });
		   var me = editor;
		   var font_size = new Ext.form.ComboBox({
				name : 'fontSize',
				emptyText : '字号',
				maxHeight : 80,
				ctCls: 'x-richedit-font-size',
				store : [['1','1号'],['2','2号'],['3','3号'],['4','4号'],['5','5号'],['6','6号'],['7','7号']],
				width: 63,
				colspan: 2,
				typeAhead: true,
				triggerAction: 'all',
				mode: 'local',
				valueField: 'value',
				displayField: 'text',
				listeners: {
					select: function (c, n, o) { 
						//执行FontSize命令
			   			me.currentSelected.execCommand('FontSize', false, c.getValue());
						me.deferFocus();
						me.currentSelected = null;
					},
					expand:function(){
						//创建选区对象
						var rg = me.getDoc().selection.createRange();
						me.currentSelected = rg;
					}
				}
			});
		   this.tb.add(font_size);
		}
	});
	
	//注册组件
	Ext.reg('newEditor_xz', newEditor_xz);
	
	/********************商家地址引用**********************/
	var newEditor_dz = Ext.extend(Ext.form.HtmlEditor,{
		enableSourceEdit : true, //启用html编辑方式
		cls : 'word_break',
		fontFamilies : ['宋体','黑体','楷体_GB2312','Arial','Courier New','Tahoma','Times New Roman','Verdana'],
		enableFontSize : false, 
		
		createToolbar : function(editor) {
			newEditor.superclass.createToolbar.call(this, editor);

		   var me = editor;
		   var font_size = new Ext.form.ComboBox({
				name : 'fontSize',
				emptyText : '字号',
				maxHeight : 80,
				ctCls: 'x-richedit-font-size',
				store : [['1','1号'],['2','2号'],['3','3号'],['4','4号'],['5','5号'],['6','6号'],['7','7号']],
				width: 63,
				colspan: 2,
				typeAhead: true,
				triggerAction: 'all',
				mode: 'local',
				valueField: 'value',
				displayField: 'text',
				listeners: {
					select: function (c, n, o) { 
						//执行FontSize命令
			   			me.currentSelected.execCommand('FontSize', false, c.getValue());
						me.deferFocus();
						me.currentSelected = null;
					},
					expand:function(){
						//创建选区对象
						var rg = me.getDoc().selection.createRange();
						me.currentSelected = rg;
					}
				}
			});
		   this.tb.add(font_size);
		}
	});
	
	//注册组件
	Ext.reg('newEditor_dz', newEditor_dz);
});