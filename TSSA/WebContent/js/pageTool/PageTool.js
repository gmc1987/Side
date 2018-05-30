/**
 *  自定义分页js控件
 */
var PageTool = {
	elementId : "pageToolBar",
	previous : '&laquo;',
	next : '&raquo;',
	firstIndex : 1,
	lastIndex : 10,
	hasNextPage : false,
	hasPreviousPage : false,
	pageNumber : 1,
	pageSize : 10,
	pageCount : 0,
	requestURL : '',
	params : '',
	dataGrid : '',//数据列表div
	headColumn : [],//表头字段数组
	fieldColumn : [],//字段映射数组
//	初始化方法
	init : function(config){
		this.elementId = config.elementId != null ? config.elementId : 'pageToolBar';
		this.firstIndex = isNaN(config.firstIndex) ? 1 : parseInt(config.firstIndex);
		this.lastIndex = isNaN(config.lastIndex) ? 10 : parseInt(config.lastIndex);
		this.hasNextPage = config.hasNextPage;
		this.hasPreviousPage = config.hasPreviousPage;
		this.pageNumber = isNaN(config.pageNumber) ? 1 : parseInt(config.pageNumber);
		this.pageSize = isNaN(config.pageSize) || parseInt(config.pageSize) < 10 ? 10 : parseInt(config.pageSize);
		this.pageCount = isNaN(config.pageCount) || parseInt(config.pageCount) < 0 ? 1 : parseInt(config.pageCount);
		if(config.requestURL != null) this.requestURL = config.requestURL;
		if(config.params != null && config.params.length > 0) this.params = config.params;
		this.dataGrid = config.dataGrid != null ? config.dataGrid : '';
		if(config.headColumn != null && config.headColumn.length > 0) this.headColumn = config.headColumn;
		if(config.fieldColumn != null && config.fieldColumn.length > 0) this.fieldColumn = config.fieldColumn;
		if(config.click && typeof(config.click) == 'function'){this.click = config.click;}
		
		if(!this._config){
			this._config = config;
		}
		this.inited = true;
	},
//	生成分页控件html代码
	generPageHtml : function(config,enforceInit){
		if(!enforceInit || !config.inited){
			this.init(config);
		}
//		previous 上一页按钮， next 下一页按钮 ,  pageIndex每一页按钮
		var pageToolHtml = "";
		var previous = "", next = "", pageIndex = "";
		var ul_start = '<ul class="pagination pagination-sm">';
		var ul_end = '</ul>'
		if (this.hasPreviousPage) {
			previous = '<li><a href="#" aria-label="Previous" onclick="return PageTool._clickHandler('+(parseInt(this.pageNumber)-10)+')"><span aria-hidden="true">'+this.previous+'</span></a></li>';
		} else {
			previous = '<li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">'+this.previous+'</span></a></li>';
		}
		if (this.hasNextPage) {
			next = '<li><a href="#" aria-label="Next" onclick="return PageTool._clickHandler('+(parseInt(this.pageNumber)+10)+')"><span aria-hidden="true">'+this.next+'</span></a></li>'
		} else {
			next = '<li class="disabled"><a href="#" aria-label="Next"><span aria-hidden="true">'+this.next+'</span></a></li>'
		}
		
		if (this.pageNumber > 0 && this.pageCount > 0){
			for(var i = this.firstIndex; i <= this.lastIndex; i++){
//				if(this.pageNumber > 10 && i % 10 == 1){
//					i = this.pageNumber;
//				}
				if(this.pageNumber == i){
					pageIndex += '<li class="active"><a href="#" onclick="return PageTool._clickHandler('+i+')">'+i+'</a></li>';
				} else {
					pageIndex += '<li><a href="#" onclick="return PageTool._clickHandler('+i+')">'+i+'</a></li>';
				}
//				if(i % 10 == 0){
//					break;
//				}
			}
		}
		pageToolHtml = ul_start + previous + pageIndex + next + ul_end;
		$("#" + this.elementId).html(pageToolHtml);
	},
//	分页单击事件
	click : function(n){
		if(n <= 1){//点击第一页或者跳到前10页时
			this._config.pageNumber = 1;
		} else if(n >= this.pageCount){//点击最后一页或者跳到后10页时
			this._config.pageNumber = (this.pageCount-1)*this.pageSize;
		} else{
			this._config.pageNumber = (n-1)*this.pageSize;
		}
		this._config.pageSize = this.pageSize;
		this._config.params = this.params;
		this.generDataGridHtml(this._config);
	},
	_clickHandler	: function(n){
		if(this.click && typeof this.click == 'function'){
			res = this.click.call(this,n)
		}
	},
	generDataGridHtml : function(config){
		if(!config.inited){
			this.init(config);
		}
		if(this.headColumn.length > 0){//初始化表头
			var thead = $("#" +this.dataGrid+ " > thead");
			thead.empty();
			var tr = $("<tr></tr>");
			for(var k = 0; k < this.headColumn.length; k++){
				var th = $("<th>" + this.headColumn[k] + "</th>");
				tr.append(th);
			}
			thead.append(tr);
		}
		var _fieldColumn = this.fieldColumn;
		var _requestURL = this.requestURL;
		var _this = this;
		$.ajax({
			url : this.requestURL,
			data : this.params + "&pageNumber=" + this.pageNumber + "&pageSize=" + this.pageSize,
			type : "POST",
			dataType : "json",
			success : function(data){
				var _firstIndex = parseInt(data.data.firstIndex / data.data.pageSize);
				if(_firstIndex == 0){
					_firstIndex = 1;
				}
				if(data.data.firstIndex != 1 && data.data.firstIndex % data.data.pageSize > 0){
					_firstIndex++;
				}
				var _hasNextPage = data.data.hasNextPage;
				var _hasPreviousPage = data.data.hasPreviousPage;
				var _lastIndex = _firstIndex + data.data.pageSize - 1;
				var _pageCount = data.data.pageCount;
				var _pageNumber = data.data.pageNumber;
				var _pageSize = data.data.pageSize;
				if(_lastIndex >= _pageCount){
					_lastIndex = _pageCount;
				}
				if(data.success){
					var records = data.data.records;
					var tbody = $("#"+dataGrid.id+" > tbody");
					tbody.empty();
					for(var i = 0; i < records.length; i++){
						var tr = $('<tr ondblclick=\'detailView('+ JSON.stringify(records[i]) +')\'></tr>');
						for(var j = 0; j < _fieldColumn.length; j++){
							for(var key in records[i]){
								if(key == _fieldColumn[j]){
									var td = $("<td>"+records[i][_fieldColumn[j]]+"</td>");
									tr.append(td);
								}else{
									continue;
								}
							}
						}
						var td6 = $('<td><button class="btn btn-success btn-xs" id="mod_btn" data-toggle="modal" data-target="#mod_win" onclick=\'toEdit('+ JSON.stringify(records[i]) +')\'>修改</button>&nbsp;&nbsp;<button class="btn btn-danger btn-xs" id="del_btn" data-toggle="modal" data-target="#del_win" onclick="delFunction(\''+records[i].uuid+'\')">删除</button></td>');
						tr.append(td6);
						tbody.append(tr);
					}
					_this.generPageHtml({
						elementId : "pageToolBar",
						previous : '&laquo;',
						next : '&raquo;',
						firstIndex : _firstIndex,
						lastIndex : _lastIndex,
						hasNextPage : _hasNextPage,
						hasPreviousPage : _hasPreviousPage,
						pageNumber : _pageNumber,
						pageSize : _pageSize,
						pageCount : _pageCount,
						requestURL : _requestURL
					});
				}
			},
			error : function(xhr, status){
				alert("系统发生异常，清稍后再试！");
			}
		});
	}
}