<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageMode" value="${result}"></c:set>
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="餐桌管理" />
<%@ include file="../../../js/js.inc" %> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${title}</title>
	<script type="text/javascript">
		var ctx = '${ctx}';
	</script>
	<script type="text/javascript" src="${ctx}/js/common/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/common/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/side/cateringTable/CateringTable.js"></script>
	<script type="text/javascript" src="${ctx}/js/pageTool/PageTool.js"></script>
 	<link href="${ctx}/css/strap/bootstrap.min.css" rel="stylesheet">
<title>${title }</title>
</head>
<body>
	<!-- 查询条件 begin -->
	<nav class="navbar navbar-default" style="text-align: left; ">
		<div class="container-fluid">
			<form id="search_form" class="form-inline " role="form" action="" style="margin-top: 5px;">
				<div class="row" style="text-align: left; margin-left: 10px;">
					<div class="form-group">
						<label class="control-label" for="cooperId">商户编码:</label>
						<c:choose>
							<c:when test="${cooperId==null}">
								<input class="form-control input-sm" type="text" id="cooperId" name="cooperId" placeholder="超级管理员请输入商户编码">
							</c:when>
							<c:otherwise>
								<input class="form-control input-sm" type="text" id="cooperId" name="cooperId" value="${cooperId}" disabled="disabled" placeholder="超级管理员请输入商户编码">
								<input type="hidden" name="cooperId" value="${cooperId}">
							</c:otherwise>
						</c:choose>
					</div>
					<div class="form-group" style="margin-left: 15px;">
						<label class="control-label" for="tableNo">餐桌编号:</label>
						<input class="form-control input-sm" type="text" id="tableNo" name="tableNo" >
					</div>
					<div class="form-group" style="margin-left: 15px;">
						<label class="control-label" for="holdNum">容纳人数:</label>
						<select class="form-control input-sm" id="holdNum" name="holdNum">
							<option value="">请选择</option>
							<option value="2">2</option>
							<option value="4">4</option>
							<option value="6">6</option>
							<option value="8">8</option>
							<option value="10">10</option>
						</select>
					</div>
					<div class="form-group" style="margin-left:15px;">
						<label class="control-label" for="tableType">餐桌类型:</label>
						<select class="form-control input-sm" id="tableType" name="tableType">
							<option value="">请选择</option>
							<option value="0">小桌</option>
							<option value="1">中桌</option>
							<option value="2">大桌</option>
						</select>
					</div>
					<div class="form-group" style="margin-left:15px;">
						<label class="control-label" for="status">餐桌状态:</label>
						<select class="form-control input-sm" id="status" name="status">
							<option value="">全部</option>
							<option value="0">可用</option>
							<option value="1">不可用</option>
						</select>
					</div>
				</div>
			</form>
			<button class="btn btn-default btn-sm navbar-btn" id="search_btn" >查询</button>
			<button class="btn btn-default btn-sm navbar-btn" id="add_btn" data-toggle="modal" data-target="#add_win">新增</button>
			<button class="btn btn-default btn-sm navbar-btn" id="batch_add_btn" data-toggle="modal" data-target="#batch_add_win">批量新增</button>
			<button class="btn btn-default btn-sm navbar-btn" id="import_btn" >导入</button>
		</div>
	</nav>
	<!-- 查询条件 end -->
	<!-- 数据列表 begin -->
	<div class="panel panel-info">
		<div class="panel-heading" style="text-align: left;">
			查询结果
		</div>
		<table id="dataGrid" class="table">
			<thead>
				<tr>
					<th>商户编码</th>
					<th>餐桌类型</th>
					<th>餐桌编号</th>
					<th>容纳人数</th>
					<th>餐桌状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${result.records }">
					<tr>
						<td>${item.cooperId }</td>
						<td>${item.tableType }</td>
						<td>${item.tableNo }</td>
						<td>${item.holdNum }</td>
						<td>
						<c:choose>
							<c:when test="${item.status == 0}">
								可用
							</c:when>
							<c:otherwise>
								不可用
							</c:otherwise>
						</c:choose>
						</td>
						<td>
							<button class="btn btn-success btn-xs" id="mod_btn" data-toggle="modal" data-target="#mod_win">修改</button>
							<button class="btn btn-danger btn-xs" id="del_btn">删除</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 分页控件 begin -->
		<nav id="pageToolBar" aria-label="Page navigation" style="text-align: left; margin-left: 5px;">
			<%-- <ul class="pagination pagination-sm">
			<c:choose>
				<c:when test="${result.hasPreviousPage==true }">
					<li>
						<a href="#" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="disabled">
						<a href="#" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:otherwise>
			</c:choose>
			<c:forEach var="pageIndex" begin="${result.firstIndex }" end="${result.lastIndex }" step="1">
				<li><a href="#">${pageIndex}</a></li>
			</c:forEach>
			<c:choose>
				<c:when test="${result.hasNextPage==true }">
					<li>
				      <a href="#" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				      </a>
				    </li>
				</c:when>
				<c:otherwise>
					<li class="disabled">
				      <a href="#" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				      </a>
				    </li>
				</c:otherwise>
			</c:choose>
			</ul> --%>
		</nav>
		<!-- 分页控件 end -->
	</div>
	<!-- 数据列表 end -->
	<!-- 新增功能弹出窗口 begin -->
	<div class="modal fade" id="add_win" style="text-align: left;" tabindex="-1" role="dialog" aria-labelledby="title" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" style="text-align: right;" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="title">
						新增餐桌
					</h4>
				</div>
				<div class="modal-body">
					<form action="" role="form">
						<div id="first" class="form-group sr-only">
							<label for="cooperId">商户编码</label>
							<input type="text" class="form-control" name="cooperId" placeholder="超级管理员需要输入商户编码">
						</div>
						<div class="form-group">
							<label>餐桌编号</label>
							<input type="text" class="form-control input-sm" name="tableNo">
						</div>
						<div class="form-group">
							<label>餐桌类型</label>
							<select class="form-control input-sm" name="tableType">
								<option value="0">小桌</option>
								<option value="1">中桌</option>
								<option value="2">大桌</option>
							</select>
						</div>
						<div class="form-group">
							<label>容纳人数</label>
							<select class="form-control input-sm" name="holdNum">
								<option value="2">2</option>
								<option value="4">4</option>
								<option value="6">6</option>
								<option value="8">8</option>
								<option value="10">10</option>
							</select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
					<button id="submit_Btn_add" class="btn btn-primary btn-sm">提交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 新增功能弹出窗口 end -->
	
	<!-- 批量新增功能弹出窗口 begin -->
	<div class="modal fade" id="batch_add_win" style="text-align: left;" tabindex="-1" role="dialog" aria-labelledby="batchAddtitle" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" style="text-align: right;" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="batchAddtitle">
						批量新增餐桌
					</h4>
				</div>
				<div class="modal-body">
					<form action="" role="form">
						<div id="first" class="form-group sr-only">
							<label for="cooperId">商户编码</label>
							<input type="text" class="form-control" name="cooperId" placeholder="超级管理员需要输入商户编码">
						</div>
						<div class="form-group">
							<label>前缀</label>
							<input type="text" class="form-control input-sm" name="suffx">
						</div>
						<div class="form-group">
							<label>起始编号</label>
							<input type="text" class="form-control input-sm" name="begin">
						</div>
						<div class="form-group">
							<label>结束编号</label>
							<input type="text" class="form-control input-sm" name="end">
						</div>
						<div class="form-group">
							<label>餐桌类型</label>
							<select class="form-control input-sm" name="tableType">
								<option value="0">小桌</option>
								<option value="1">中桌</option>
								<option value="2">大桌</option>
							</select>
						</div>
						<div class="form-group">
							<label>容纳人数</label>
							<select class="form-control input-sm" name="holdNum">
								<option value="2">2</option>
								<option value="4">4</option>
								<option value="6">6</option>
								<option value="8">8</option>
								<option value="10">10</option>
							</select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
					<button id="submit_Btn_batch_add" class="btn btn-primary btn-sm">提交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 批量新增功能弹出窗口 end -->
	
	<!-- 修改功能弹出窗口 begin -->
	<div class="modal fade" id="mod_win" style="text-align: left;" tabindex="-1" role="dialog" aria-labelledby="title" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" style="text-align: right;" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="title">
						编辑餐桌信息
					</h4>
				</div>
				<div class="modal-body">
					<form id="mod_form" action="" role="form">
						<div id="first" class="form-group sr-only">
							<label for="cooperId">商户编码</label>
							<input type="text" class="form-control" id="mod_cooperId" name="cooperId" placeholder="超级管理员需要输入商户编码">
							<input type="hidden" id="uuid" name="uuid">
							<input type="hidden" id="mod_status" name="status">
						</div>
						<div class="form-group">
							<label>餐桌编号</label>
							<input type="text" class="form-control input-sm" id="mod_tableNo" name="tableNo">
						</div>
						<div class="form-group">
							<label>餐桌类型</label>
							<select class="form-control input-sm" id="mod_tableType" name="tableType">
								<option value="0">小桌</option>
								<option value="1">中桌</option>
								<option value="2">大桌</option>
							</select>
						</div>
						<div class="form-group">
							<label>容纳人数</label>
							<select class="form-control input-sm" id="mod_holdNum" name="holdNum">
								<option value="2">2</option>
								<option value="4">4</option>
								<option value="6">6</option>
								<option value="8">8</option>
								<option value="10">10</option>
							</select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
					<button id="submit_Btn_mod" class="btn btn-primary btn-sm">提交</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 修改功能弹出窗口 end -->
	
	<!-- 查看信息弹出窗口 begin -->
	<div class="modal fade" id="detail_win" style="text-align: left;" tabindex="-1" role="dialog" aria-labelledby="title" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" style="text-align: right;" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="title">
						餐桌信息
					</h4>
				</div>
				<div class="modal-body">
					<form id="detail_form" action="" role="form">
						<div id="first" class="form-group sr-only">
							<label for="cooperId">商户编码</label>
							<input type="text" class="form-control" id="detail_cooperId" name="cooperId" placeholder="超级管理员需要输入商户编码">
							<input type="hidden" id="uuid" name="uuid">
							<input type="hidden" id="mod_status" name="status">
						</div>
						<div class="form-group">
							<label>餐桌编号</label>
							<input type="text" class="form-control input-sm" id="detail_tableNo" name="tableNo" disabled="disabled">
						</div>
						<div class="form-group">
							<label>餐桌类型</label>
							<select class="form-control input-sm" id="detail_tableType" name="tableType" disabled="disabled">
								<option value="0">小桌</option>
								<option value="1">中桌</option>
								<option value="2">大桌</option>
							</select>
						</div>
						<div class="form-group">
							<label>容纳人数</label>
							<select class="form-control input-sm" id="detail_holdNum" name="holdNum" disabled="disabled">
								<option value="2">2</option>
								<option value="4">4</option>
								<option value="6">6</option>
								<option value="8">8</option>
								<option value="10">10</option>
							</select>
						</div>
						<div class="form-group">
							<label>餐桌二维码</label>
							<img id="qrCode" alt="" src="">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
					<button id="submit_Btn_detail" class="btn btn-primary btn-sm">打印二维码</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 查看信息弹出窗口 end -->
	
	<!-- 删除功能弹出窗口 begin -->
	<div class="modal fade" id="del_win" style="text-align: left;" tabindex="-1" role="dialog" aria-labelledby="title" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" style="text-align: right;" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="title">
						警告
					</h4>
				</div>
				<div class="modal-body">
					<form id="del_form" action="" role="form">
						<input type="hidden" id="del_uuid" name="uuid" value="">
						您确定要删除此记录吗？
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default btn-sm" data-dismiss="modal">否</button>
					<button id="submit_Btn_del" class="btn btn-primary btn-sm">是</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 删除功能弹出窗口 end -->
	
	<!-- 提示框 begin -->
	<div id="infoAlert" class="alert alert-success hide">
	    <strong>操作成功！</strong>
	</div>
	
	<div id="dangerAlert" class="alert alert-danger hide">
	    <strong>参数错误！</strong>
	</div>
	
	<div id="warningAlert" class="alert alert-warning hide">
	    <strong>操作失败！</strong>
	</div>
	<!-- 提示框 end -->
</body>
</html>