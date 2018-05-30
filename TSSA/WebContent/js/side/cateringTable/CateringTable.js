/**
 *  @see 餐桌管理模块js控件
 */
$(function(){
	
	$("#search_btn").click(function(){
		PageTool.generDataGridHtml({
			elementId : "pageToolBar",
			requestURL : ctx + "/cateringTable/getData.do",
			params : $("#search_form").serialize(),
			dataGrid : 'dataGrid',//数据列表div
			headColumn : ['商户编码','餐桌类型','餐桌编号','容纳人数','餐桌状态','操作'],//表头字段数组
			fieldColumn : ['cooperId','tableTypeText','tableNo','holdNum','statusText'],
			pageSize : 10
		});
	});
	
	PageTool.generDataGridHtml({
		elementId : "pageToolBar",
		requestURL : ctx + "/cateringTable/getData.do",
		dataGrid : 'dataGrid',//数据列表div
		headColumn : ['商户编码','餐桌类型','餐桌编号','容纳人数','餐桌状态','操作'],//表头字段数组
		fieldColumn : ['cooperId','tableTypeText','tableNo','holdNum','statusText'],
		pageSize : 10
	});
	//单个新增
	$("#submit_Btn_add").click(function(){
		$.ajax({
			url : ctx + "/cateringTable/addTable.do",
			data : $("#add_win form").serialize(),
			type : "POST",
			dataType : "json",
			success : function(data){
				if(data.success){
					$('#add_win').modal('hide');
					$("#infoAlert").removeClass("hide");
					PageTool.generDataGridHtml({
						elementId : "pageToolBar",
						requestURL : ctx + "/cateringTable/getData.do",
						params : $("#search_form").serialize(),
						dataGrid : 'dataGrid',//数据列表div
						headColumn : ['商户编码','餐桌类型','餐桌编号','容纳人数','餐桌状态','操作'],//表头字段数组
						fieldColumn : ['cooperId','tableTypeText','tableNo','holdNum','statusText'],
						pageSize : 10
					});
				} else {
					$('#add_win').modal('hide');
					$("#warningAlert").removeClass("hide");
				}
			},
			error : function(xhr, status){
				alert("系统发生异常，清稍后再试！");
			}
		})
	});
	//批量新增
	$("#submit_Btn_batch_add").click(function(){
		$.ajax({
			url : ctx + "/cateringTable/batchAddTable.do",
			data : $("#batch_add_win form").serialize(),
			type : "POST",
			dataType : "json",
			success : function(data){
				if(data.success){
					$('#batch_add_win').modal('hide');
					$("#infoAlert").removeClass("hide");
					PageTool.generDataGridHtml({
						elementId : "pageToolBar",
						requestURL : ctx + "/cateringTable/getData.do",
						params : $("#search_form").serialize(),
						dataGrid : 'dataGrid',//数据列表div
						headColumn : ['商户编码','餐桌类型','餐桌编号','容纳人数','餐桌状态','操作'],//表头字段数组
						fieldColumn : ['cooperId','tableTypeText','tableNo','holdNum','statusText'],
						pageSize : 10
					});
				} else {
					$('#batch_add_win').modal('hide');
					$("#warningAlert").removeClass("hide");
				}
			},
			error : function(xhr, status){
				alert("系统发生异常，清稍后再试！");
			}
		})
	});
	//修改
	$("#submit_Btn_mod").click(function(){
		$.ajax({
			url : ctx + "/cateringTable/modTable.do",
			data : $("#mod_form").serialize(),
			type : "POST",
			dataType : "json",
			success : function(data){
				if(data.success){
					$('#mod_win').modal('hide');
					$("#infoAlert").removeClass("hide");
					PageTool.generDataGridHtml({
						elementId : "pageToolBar",
						requestURL : ctx + "/cateringTable/getData.do",
						params : $("#search_form").serialize(),
						dataGrid : 'dataGrid',//数据列表div
						headColumn : ['商户编码','餐桌类型','餐桌编号','容纳人数','餐桌状态','操作'],//表头字段数组
						fieldColumn : ['cooperId','tableTypeText','tableNo','holdNum','statusText'],
						pageSize : 10
					});
				} else {
					$('#mod_win').modal('hide');
					$("#warningAlert").removeClass("hide");
				}
			},
			error : function(xhr, status){
				alert("系统发生异常，清稍后再试！");
			}
		})
	});
	
	$("#submit_Btn_del").click(function(){
		$.ajax({
			url : ctx + "/cateringTable/delTable.do",
			data : $("#del_form").serialize(),
			type : "POST",
			dataType : "json",
			success : function(data){
				if(data.success){
					$('#del_win').modal('hide');
					$("#infoAlert").removeClass("hide");
					PageTool.generDataGridHtml({
						elementId : "pageToolBar",
						requestURL : ctx + "/cateringTable/getData.do",
						params : $("#search_form").serialize(),
						dataGrid : 'dataGrid',//数据列表div
						headColumn : ['商户编码','餐桌类型','餐桌编号','容纳人数','餐桌状态','操作'],//表头字段数组
						fieldColumn : ['cooperId','tableTypeText','tableNo','holdNum','statusText'],
						pageSize : 10
					});
				} else {
					$('#del_win').modal('hide');
					$("#warningAlert").removeClass("hide");
					$("#warningAlert").html("<strong>"+data.msg+"</strong>");
				}
			},
			error : function(xhr, status){
				alert("系统发生异常，清稍后再试！");
			}
		})
	});
});

//删除赋值方法
function delFunction(dataId){
	$("#del_uuid").val(dataId);
}
//弹出修改界面
function toEdit(record){
	if(record == null){
		$("#dangerAlert").show();
		return;
	}
	$("#uuid").val(record.uuid);
	$("#mod_status").val(record.status);
	$("#mod_cooperId").val(record.cooperId);
	$("#mod_tableNo").val(record.tableNo);
	$("#mod_tableType").val(record.tableType);
	$("#mod_holdNum").val(record.holdNum);
	$("#infoAlert").addClass("hide");
	$("#dangerAlert").addClass("hide");
	$("#warningAlert").addClass("hide");
}

function detailView(record){
	
	if(record == null){
		$("#dangerAlert").show();
		return;
	}
	$("#detail_uuid").val(record.uuid);
	$("#detail_status").val(record.status);
	$("#detail_cooperId").val(record.cooperId);
	$("#detail_tableNo").val(record.tableNo);
	$("#detail_tableType").val(record.tableType);
	$("#detail_holdNum").val(record.holdNum);
	$("#qrCode").attr("src",ctx+record.qrCodeURL);
	
	$("#detail_win").modal("show");
	
	$("#infoAlert").addClass("hide");
	$("#dangerAlert").addClass("hide");
	$("#warningAlert").addClass("hide");
}
