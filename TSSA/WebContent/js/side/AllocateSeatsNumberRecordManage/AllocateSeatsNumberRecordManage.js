var timer = null;

/**
 * 号码作废方法
 * @param uuid
 * @param num
 * @returns
 */
function invalidNum(uuid, num, btn){
	$(btn).attr("disabled", "disabled");
	var newUuid = "";
	var newNum = "";
	$.ajax({
		url : ctx + "/allocateSeatNumberRecord/numInvalid.do",
		data : {
			uuid:uuid,
			num:num
		},
		type : "POST",
		dataType : "json",
		success : function(data){
			if(data.success == true){
				$("#num_" + data.data.allocateSeatType.uuid).html(data.data.allocateNo);
				$("#custNo_" + data.data.allocateSeatType.uuid).html(data.data.custId);
				var newDate = new Date();
				newDate.setTime(data.data.createDate);
				$("#createDate_" + data.data.allocateSeatType.uuid).html(newDate.format("yyyy-MM-dd h:m:ss"));
				var statusText = "";
				if(data.data.recodeStatus == "0"){
					statusText = "0:可取号";
				}else if(data.data.recodeStatus == "1"){
					statusText = "1:已取号";
				}else{
					statusText = data.data.recodeStatus + ":已过号";
				}
				$("#status_" + data.data.allocateSeatType.uuid).html(statusText);
				$(".alert-success").html(data.msg);
				$(".alert-success").show();
			}else{
				$(".alert-danger").html(data.msg);
				$(".alert-danger").show();
			}
			newUuid = data.data.uuid;
			newNum = data.data.allocateNo;
		},
		error : function(xhr, status){
			$(".alert-danger").html("请求异常");
			$(".alert-danger").show();
		},
		complete : function( xhr, status ) {
			timer = setTimeout(function() {
				$(btn).removeAttr("disabled");
			},1000);
			
		}
	});
}

/**
 * 出号方法
 * @param typeId
 * @returns
 */
function createNewNum(typeId, numUuid){
	$("#btn_" + typeId).attr("disabled", "disabled");
	var newUuid = "";
	var newNum = "";
	$.ajax({
		url : ctx + "/allocateSeatNumberRecord/createNo.do",
		data : {
			typeId:typeId
		},
		type : "POST",
		dataType : "json",
		success : function(data){
			if(data.success == true){
				$("#num_" + typeId).html(data.data.num);
				$("#custNo_" + typeId).html(data.data.custNo);
				$("#createDate_" + typeId).html(data.data.createDate);
				var statusText = "";
				if(data.data.status == "0"){
					statusText = "0:可取号";
				}else if(data.data.status == "1"){
					statusText = "0:已取号";
				}else{
					statusText = data.data.status + ":已过号";
				}
				$("#status_" + typeId).html(statusText);
				$(".alert-success").html(data.msg);
				$(".alert-success").show();
			}else{
				$(".alert-danger").html(data.msg);
				$(".alert-danger").show();
			}
			newUuid = data.data.numUuid;
			newNum = data.data.num;
		},
		error : function(xhr, status){
			$(".alert-danger").html("请求异常");
			$(".alert-danger").show();
		},
		complete : function( xhr, status ) {
			timer = setTimeout(function() {
				$("#btn_" + typeId).removeAttr("disabled");
			},1000);
			$("#btn_" + numUuid).attr("onclick", "invalidNum('" + newUuid + "', '" + newNum + "', this)");
		}
		
	})
}

Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "h+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}