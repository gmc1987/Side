<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageMode" value="${result}"></c:set>
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="系统派位管理" />
<%@ include file="../../../js/js.inc" %> 
<!DOCTYPE html>
<html lang="zh-CN">
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
	<script type="text/javascript" src="${ctx}/js/side/AllocateSeatsNumberRecordManage/AllocateSeatsNumberRecordManage.js"></script>
	<link href="${ctx}/css/strap/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div class="row" style="padding-left: 10px;padding-top: 10px;">
	<c:forEach var="list" items="${pageMode}">
		<div class="col-sm-3">
			<div class="panel panel-primary">
				<div class="panel-heading">${list.typeName}</div>
				<div class="panel-body">
					<table class="table">
						<tr>
							<td>
								<label class="label label-info">当前号数:</label>
							</td>
							<td>
								<span id="num_${list.typeId}" style="font-size: 11px;">${list.num}</span>
							</td>
						</tr>
						<tr>
							<td>
								<label class="label label-info">取号客户:</label>
							</td>
							<td>
								<span id="custNo_${list.typeId}" style="font-size: 11px;">${list.custNo}</span>
							</td>
						</tr>
						<tr>
							<td>
								<label class="label label-info">生成时间:</label>
							</td>
							<td>
								<span id="createDate_${list.typeId}" style="font-size: 11px;">${list.createDate}</span>
							</td>
						</tr>
						<tr>
							<td>
								<label class="label label-info">当前状态:</label>
							</td>
							<td>
								<c:choose>
									<c:when test="${list.status==0}">
										<span id="status_${list.typeId}" style="font-size: 11px;">${list.status}:可取号</span>
									</c:when>
									<c:when test="${list.status==1}">
										<span id="status_${list.typeId}" style="font-size: 11px;">${list.status}:已取号</span>
									</c:when>
									<c:otherwise>
										<span id="status_${list.typeId}" style="font-size: 11px;">${list.status}:已过号</span>
									</c:otherwise>
								</c:choose>
								<input type="hidden" name="typeId" value="${list.typeId }">
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<div class="btn-group btn-group-xs">
									<button id="btn_${list.numUuid}" class="btn btn-default" onclick="invalidNum('${list.numUuid}', '${list.num}', this)">作废</button>
									<button id="btn_${list.typeId}" class="btn btn-default" onclick="createNewNum('${list.typeId}','${list.numUuid}')">出号</button>
								</div>
							</td>
						</tr>
					</table>
					<p id="remark_${list.typeId}" style="align-content: center; font-size: 11px; color: red;">
						${list.remark}
					</p>
				</div>
			</div>
		</div>
	</c:forEach>
	</div>
	<div class="alert alert-success" style="display: none;"></div>
	<div class="alert alert-danger" style="display: none;"></div>
</body>
</html>