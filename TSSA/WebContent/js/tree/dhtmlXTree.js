function xmlPointer(a) {
	this.d = a;
}
xmlPointer.prototype = {
	text : function() {
		if (!_isFF) {
			return this.d.xml;
		}
		var a = new XMLSerializer();
		return a.serializeToString(this.d);
	},
	get : function(a) {
		return this.d.getAttribute(a);
	},
	exists : function() {
		return !!this.d;
	},
	content : function() {
		return this.d.firstChild ? this.d.firstChild.data : "";
	},
	each : function(d, h, g, e) {
		var b = this.d.childNodes;
		var j = new xmlPointer();
		if (b.length) {
			for (e = e || 0; e < b.length; e++) {
				if (b[e].tagName == d) {
					j.d = b[e];
					if (h.apply(g, [ j, e ]) == -1) {
						return;
					}
				}
			}
		}
	},
	get_all : function() {
		var d = {};
		var c = this.d.attributes;
		for ( var e = 0; e < c.length; e++) {
			d[c[e].name] = c[e].value;
		}
		return d;
	},
	sub : function(d) {
		var b = this.d.childNodes;
		var f = new xmlPointer();
		if (b.length) {
			for ( var e = 0; e < b.length; e++) {
				if (b[e].tagName == d) {
					f.d = b[e];
					return f;
				}
			}
		}
	},
	up : function(a) {
		return new xmlPointer(this.d.parentNode);
	},
	set : function(a, b) {
		this.d.setAttribute(a, b);
	},
	clone : function(a) {
		return new xmlPointer(this.d);
	},
	sub_exists : function(c) {
		var b = this.d.childNodes;
		if (b.length) {
			for ( var d = 0; d < b.length; d++) {
				if (b[d].tagName == c) {
					return true;
				}
			}
		}
		return false;
	},
	through : function(b, h, l, e, m) {
		var j = this.d.childNodes;
		if (j.length) {
			for ( var d = 0; d < j.length; d++) {
				if (j[d].tagName == b && j[d].getAttribute(h) != null
						&& j[d].getAttribute(h) != ""
						&& (!l || j[d].getAttribute(h) == l)) {
					var g = new xmlPointer(j[d]);
					e.apply(m, [ g, d ]);
				}
				var k = this.d;
				this.d = j[d];
				this.through(b, h, l, e, m);
				this.d = k;
			}
		}
	}
};
function dhtmlXTreeObject(g, d, b, a) {
	if (_isIE) {
		try {
			document.execCommand("BackgroundImageCache", false, true);
		} catch (f) {
		}
	}
	if (typeof (g) != "object") {
		this.parentObject = document.getElementById(g);
	} else {
		this.parentObject = g;
	}
	this._itim_dg = true;
	this.dlmtr = ",";
	this.dropLower = false;
	this.enableIEImageFix();
	this.xmlstate = 0;
	this.mytype = "tree";
	this.smcheck = true;
	this.width = d;
	this.height = b;
	this.rootId = a;
	this.childCalc = null;
	this.def_img_x = "18px";
	this.def_img_y = "18px";
	this.def_line_img_x = "18px";
	this.def_line_img_y = "18px";
	this._dragged = new Array();
	this._selected = new Array();
	this.style_pointer = "pointer";
	if (_isIE) {
		this.style_pointer = "hand";
	}
	this._aimgs = true;
	this.htmlcA = " [";
	this.htmlcB = "]";
	this.lWin = window;
	this.cMenu = 0;
	this.mlitems = 0;
	this.dadmode = 0;
	this.slowParse = false;
	this.autoScroll = true;
	this.hfMode = 0;
	this.nodeCut = new Array();
	this.XMLsource = 0;
	this.XMLloadingWarning = 0;
	this._idpull = {};
	this._pullSize = 0;
	this.treeLinesOn = true;
	this.tscheck = false;
	this.timgen = true;
	this.dpcpy = false;
	this._ld_id = null;
	this._oie_onXLE = [];
	this.imPath = dhtmlXTreeImPath;
	this.checkArray = new Array("iconUncheckAll.gif", "iconCheckAll.gif",
			"iconCheckGray.gif", "iconUncheckDis.gif", "iconCheckDis.gif",
			"iconCheckDis.gif");
	this.radioArray = new Array("radio_off.gif", "radio_on.gif",
			"radio_on.gif", "radio_off.gif", "radio_on.gif", "radio_on.gif");
	this.lineArray = new Array("line2.gif", "line3.gif", "line4.gif",
			"blank.gif", "blank.gif", "line1.gif");
	this.minusArray = new Array("minus2.gif", "minus3.gif", "minus4.gif",
			"minus.gif", "minus5.gif");
	this.plusArray = new Array("plus2.gif", "plus3.gif", "plus4.gif",
			"plus.gif", "plus5.gif");
	this.imageArray = new Array("leaf.gif", "folderOpen.gif",
			"folderClosed.gif");
	this.cutImg = new Array(0, 0, 0);
	this.cutImage = "but_cut.gif";
	this.dragger = new dhtmlDragAndDropObject();
	this.htmlNode = new dhtmlXTreeItemObject(this.rootId, "", 0, this);
	this.htmlNode.htmlNode.childNodes[0].childNodes[0].style.display = "none";
	this.htmlNode.htmlNode.childNodes[0].childNodes[0].childNodes[0].className = "hiddenRow";
	this.allTree = this._createSelf();
	this.allTree.appendChild(this.htmlNode.htmlNode);
	if (_isFF) {
		this.allTree.childNodes[0].width = "100%";
	}
	var c = this;
	this.allTree.onselectstart = new Function("return false;");
	if (_isMacOS) {
		this.allTree.oncontextmenu = function(h) {
			return c._doContClick(h || window.event);
		};
	}
	this.allTree.onmousedown = function(h) {
		return c._doContClick(h || window.event);
	};
	this.XMLLoader = new dtmlXMLLoaderObject(this._parseXMLTree, this, true,
			this.no_cashe);
	if (_isIE) {
		this.preventIECashing(true);
	}
	if (window.addEventListener) {
		window.addEventListener("unload", function() {
			try {
				c.destructor();
			} catch (h) {
			}
		}, false);
	}
	if (window.attachEvent) {
		window.attachEvent("onunload", function() {
			try {
				c.destructor();
			} catch (h) {
			}
		});
	}
	this.dhx_Event();
	this._onEventSet = {
		onMouseIn : function() {
			this.ehlt = true;
		},
		onMouseOut : function() {
			this.ehlt = true;
		},
		onSelect : function() {
			this._onSSCF = true;
		}
	};
	return this;
}
dhtmlXTreeObject.prototype.setDataMode = function(a) {
	this._datamode = a;
};
dhtmlXTreeObject.prototype._doContClick = function(b) {
	if (b.button != 2) {
		if (this._acMenu) {
			this.cMenu._contextEnd();
		}
		return true;
	}
	var a = (_isIE ? b.srcElement : b.target);
	while ((a) && (a.tagName != "BODY")) {
		if (a.parentObject) {
			break;
		}
		a = a.parentNode;
	}
	if ((!a) || (!a.parentObject)) {
		return true;
	}
	var c = a.parentObject;
	this._acMenu = (c.cMenu || this.cMenu);
	if (this._acMenu) {
		a.contextMenuId = c.id;
		a.contextMenu = this._acMenu;
		a.a = this._acMenu._contextStart;
		if (_isIE) {
			b.srcElement.oncontextmenu = function() {
				event.cancelBubble = true;
				return false;
			};
		}
		a.a(a, b);
		a.a = null;
		b.cancelBubble = true;
		return false;
	}
	return true;
};
dhtmlXTreeObject.prototype.enableIEImageFix = function(a) {
	if (!a) {
		this._getImg = function(b) {
			return document.createElement((b == this.rootId) ? "div" : "img");
		};
		this._setSrc = function(d, c) {
			d.src = c;
		};
		this._getSrc = function(b) {
			return b.src;
		};
	} else {
		this._getImg = function() {
			var b = document.createElement("DIV");
			b.innerHTML = "&nbsp;";
			b.className = "dhx_bg_img_fix";
			return b;
		};
		this._setSrc = function(d, c) {
			d.style.backgroundImage = "url(" + c + ")";
		};
		this._getSrc = function(b) {
			var c = b.style.backgroundImage;
			return c.substr(4, c.length - 5);
		};
	}
};
dhtmlXTreeObject.prototype.destructor = function() {
	for ( var b in this._idpull) {
		var c = this._idpull[b];
		if (!c) {
			continue;
		}
		c.parentObject = null;
		c.treeNod = null;
		c.childNodes = null;
		c.span = null;
		c.tr.nodem = null;
		c.tr = null;
		c.htmlNode.objBelong = null;
		c.htmlNode = null;
		this._idpull[b] = null;
	}
	this.allTree.innerHTML = "";
	this.XMLLoader.destructor();
	for ( var b in this) {
		this[b] = null;
	}
};
function cObject() {
	return this;
}
cObject.prototype = new Object;
cObject.prototype.clone = function() {
	function a() {
	}
	a.prototype = this;
	return new a();
};
function dhtmlXTreeItemObject(f, b, c, a, d, e) {
	this.htmlNode = "";
	this.acolor = "";
	this.scolor = "";
	this.tr = 0;
	this.childsCount = 0;
	this.tempDOMM = 0;
	this.tempDOMU = 0;
	this.dragSpan = 0;
	this.dragMove = 0;
	this.span = 0;
	this.closeble = 1;
	this.childNodes = new Array();
	this.userData = new cObject();
	this.checkstate = 0;
	this.treeNod = a;
	this.label = b;
	this.parentObject = c;
	this.actionHandler = d;
	this.images = new Array(a.imageArray[0], a.imageArray[1], a.imageArray[2]);
	this.id = a._globalIdStorageAdd(f, this);
	if (this.treeNod.checkBoxOff) {
		this.htmlNode = this.treeNod._createItem(1, this, e);
	} else {
		this.htmlNode = this.treeNod._createItem(0, this, e);
	}
	this.htmlNode.objBelong = this;
	return this;
}
dhtmlXTreeObject.prototype._globalIdStorageAdd = function(b, a) {
	if (this._globalIdStorageFind(b, 1, 1)) {
		b = b + "_" + (new Date()).valueOf();
		return this._globalIdStorageAdd(b, a);
	}
	this._idpull[b] = a;
	this._pullSize++;
	return b;
};
dhtmlXTreeObject.prototype._globalIdStorageSub = function(a) {
	if (this._idpull[a]) {
		this._unselectItem(this._idpull[a]);
		this._idpull[a] = null;
		this._pullSize--;
	}
	if ((this._locker) && (this._locker[a])) {
		this._locker[a] = false;
	}
};
dhtmlXTreeObject.prototype._globalIdStorageFind = function(e, a, b, c) {
	var d = this._idpull[e];
	if (d) {
		return d;
	}
	return null;
};
dhtmlXTreeObject.prototype._escape = function(a) {
	switch (this.utfesc) {
	case "none":
		return a;
		break;
	case "utf8":
		return encodeURI(a);
		break;
	default:
		return escape(a);
		break;
	}
};
dhtmlXTreeObject.prototype._drawNewTr = function(e, c) {
	var d = document.createElement("tr");
	var b = document.createElement("td");
	var a = document.createElement("td");
	b.appendChild(document.createTextNode(" "));
	a.colSpan = 3;
	a.appendChild(e);
	d.appendChild(b);
	d.appendChild(a);
	return d;
};
dhtmlXTreeObject.prototype.loadXMLString = function(c, b) {
	var a = this;
	if (!this.parsCount) {
		this.callEvent("onXLS", [ a, null ]);
	}
	this.xmlstate = 1;
	if (b) {
		this.XMLLoader.waitCall = b;
	}
	this.XMLLoader.loadXMLString(c);
};
dhtmlXTreeObject.prototype.loadXML = function(a, c) {
	if (this._datamode && this._datamode != "xml") {
		return this["load" + this._datamode.toUpperCase()](a, c);
	}
	var b = this;
	if (!this.parsCount) {
		this.callEvent("onXLS", [ b, this._ld_id ]);
	}
	this._ld_id = null;
	this.xmlstate = 1;
	this.XMLLoader = new dtmlXMLLoaderObject(this._parseXMLTree, this, true,
			this.no_cashe);
	if (c) {
		this.XMLLoader.waitCall = c;
	}
	this.XMLLoader.loadXML(a);
};
dhtmlXTreeObject.prototype._attachChildNode = function(g, f, d, h, u, t, s, j,
		c, m, o) {
	if (m && m.parentObject) {
		g = m.parentObject;
	}
	if (((g.XMLload == 0) && (this.XMLsource)) && (!this.XMLloadingWarning)) {
		g.XMLload = 1;
		this._loadDynXML(g.id);
	}
	var k = g.childsCount;
	var v = g.childNodes;
	if (o) {
		if (o.tr.previousSibling.previousSibling) {
			m = o.tr.previousSibling.nodem;
		} else {
			j = j.replace("TOP", "") + ",TOP";
		}
	}
	if (m) {
		var e, r;
		for (e = 0; e < k; e++) {
			if (v[e] == m) {
				for (r = k; r != e; r--) {
					v[1 + r] = v[r];
				}
				break;
			}
		}
		e++;
		k = e;
	}
	if (j) {
		var p = j.split(",");
		for ( var q = 0; q < p.length; q++) {
			switch (p[q]) {
			case "TOP":
				if (g.childsCount > 0) {
					m = new Object;
					m.tr = g.childNodes[0].tr.previousSibling;
				}
				g._has_top = true;
				for (e = k; e > 0; e--) {
					v[e] = v[e - 1];
				}
				k = 0;
				break;
			}
		}
	}
	var l;
	if (!(l = this._idpull[f]) || l.span != -1) {
		l = v[k] = new dhtmlXTreeItemObject(f, d, g, this, h, 1);
		f = v[k].id;
		g.childsCount++;
	}
	if (!l.htmlNode) {
		l.label = d;
		l.htmlNode = this._createItem((this.checkBoxOff ? 1 : 0), l);
		l.htmlNode.objBelong = l;
	}
	if (u) {
		l.images[0] = u;
	}
	if (t) {
		l.images[1] = t;
	}
	if (s) {
		l.images[2] = s;
	}
	var b = this._drawNewTr(l.htmlNode);
	if ((this.XMLloadingWarning) || (this._hAdI)) {
		l.htmlNode.parentNode.parentNode.style.display = "none";
	}
	if ((m) && (m.tr.nextSibling)) {
		g.htmlNode.childNodes[0].insertBefore(b, m.tr.nextSibling);
	} else {
		if (this.parsingOn == g.id) {
			this.parsedArray[this.parsedArray.length] = b;
		} else {
			g.htmlNode.childNodes[0].appendChild(b);
		}
	}
	if ((m) && (!m.span)) {
		m = null;
	}
	if (this.XMLsource) {
		if ((c) && (c != 0)) {
			l.XMLload = 0;
		} else {
			l.XMLload = 1;
		}
	}
	l.tr = b;
	b.nodem = l;
	if (g.itemId == 0) {
		b.childNodes[0].className = "hiddenRow";
	}
	if ((g._r_logic) || (this._frbtr)) {
		this
				._setSrc(
						l.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0],
						this.imPath + this.radioArray[0]);
	}
	if (j) {
		var p = j.split(",");
		for ( var q = 0; q < p.length; q++) {
			switch (p[q]) {
			case "SELECT":
				this.selectItem(f, false);
				break;
			case "CALL":
				this.selectItem(f, true);
				break;
			case "CHILD":
				l.XMLload = 0;
				break;
			case "CHECKED":
				if (this.XMLloadingWarning) {
					this.setCheckList += this.dlmtr + f;
				} else {
					this.setCheck(f, 1);
				}
				break;
			case "HCHECKED":
				this._setCheck(l, "unsure");
				break;
			case "OPEN":
				l.openMe = 1;
				break;
			}
		}
	}
	if (!this.XMLloadingWarning) {
		if ((this._getOpenState(g) < 0) && (!this._hAdI)) {
			this.openItem(g.id);
		}
		if (m) {
			this._correctPlus(m);
			this._correctLine(m);
		}
		this._correctPlus(g);
		this._correctLine(g);
		this._correctPlus(l);
		if (g.childsCount >= 2) {
			this._correctPlus(v[g.childsCount - 2]);
			this._correctLine(v[g.childsCount - 2]);
		}
		if (g.childsCount != 2) {
			this._correctPlus(v[0]);
		}
		if (this.tscheck) {
			this._correctCheckStates(g);
		}
		if (this._onradh) {
			if (this.xmlstate == 1) {
				var a = this.onXLE;
				this.onXLE = function(n) {
					this._onradh(f);
					if (a) {
						a(n);
					}
				};
			} else {
				this._onradh(f);
			}
		}
	}
	return l;
};
dhtmlXTreeObject.prototype.insertNewItem = function(d, h, k, c, g, f, e, b, a) {
	var l = this._globalIdStorageFind(d);
	if (!l) {
		return (-1);
	}
	var j = this._attachChildNode(l, h, k, c, g, f, e, b, a);
	return j;
};
dhtmlXTreeObject.prototype.insertNewChild = function(d, h, j, c, g, f, e, b, a) {
	return this.insertNewItem(d, h, j, c, g, f, e, b, a);
};
dhtmlXTreeObject.prototype._parseXMLTree = function(f, e, k, j, g) {
	var h = new xmlPointer(g.getXMLTopNode("tree"));
	f._parse(h);
	f._p = h;
};
dhtmlXTreeObject.prototype._parseItem = function(f, k, e, h) {
	var b;
	if (this._srnd && (!this._idpull[b = f.get("id")] || !this._idpull[b].span)) {
		this._addItemSRND(k.id, b, f);
		return;
	}
	var g = f.get_all();
	if ((typeof (this.waitUpdateXML) == "object")
			&& (!this.waitUpdateXML[g.id])) {
		this._parse(f, g.id, 1);
		return;
	}
	var l = [];
	if (g.select) {
		l.push("SELECT");
	}
	if (g.top) {
		l.push("TOP");
	}
	if (g.call) {
		this.nodeAskingCall = g.id;
	}
	if (g.checked == -1) {
		l.push("HCHECKED");
	} else {
		if (g.checked) {
			l.push("CHECKED");
		}
	}
	if (g.open) {
		l.push("OPEN");
	}
	if (this.waitUpdateXML) {
		if (this._globalIdStorageFind(g.id)) {
			var j = this.updateItem(g.id, g.text, g.im0, g.im1, g.im2,
					g.checked);
		} else {
			if (this.npl == 0) {
				l.push("TOP");
			} else {
				e = k.childNodes[this.npl];
			}
			var j = this._attachChildNode(k, g.id, g.text, 0, g.im0, g.im1,
					g.im2, l.join(","), g.child, 0, e);
			e = null;
		}
	} else {
		var j = this._attachChildNode(k, g.id, g.text, 0, g.im0, g.im1, g.im2,
				l.join(","), g.child, (h || 0), e);
	}
	if (g.tooltip) {
		j.span.parentNode.parentNode.title = g.tooltip;
	}
	if (g.style) {
		if (j.span.style.cssText) {
			j.span.style.cssText += (";" + g.style);
		} else {
			j.span.setAttribute("style", j.span.getAttribute("style") + ";"
					+ g.style);
		}
	}
	if (g.radio) {
		j._r_logic = true;
	}
	if (g.nocheckbox) {
		j.span.parentNode.previousSibling.previousSibling.childNodes[0].style.display = "none";
		j.nocheckbox = true;
	}
	if (g.disabled) {
		if (g.checked != null) {
			this._setCheck(j, convertStringToBoolean(g.checked));
		}
		this.disableCheckbox(j, 1);
	}
	j._acc = g.child || 0;
	if (this.parserExtension) {
		this.parserExtension._parseExtension.call(this, f, g, (k ? k.id : 0));
	}
	this.setItemColor(j, g.aCol, g.sCol);
	if (g.locked == "1") {
		this.lockItem(j.id, true, true);
	}
	if ((g.imwidth) || (g.imheight)) {
		this.setIconSize(g.imwidth, g.imheight, j);
	}
	if ((g.closeable == "0") || (g.closeable == "1")) {
		this.setItemCloseable(j, g.closeable);
	}
	var d = "";
	if (g.topoffset) {
		this.setItemTopOffset(j, g.topoffset);
	}
	if ((!this.slowParse) || (typeof (this.waitUpdateXML) == "object")) {
		if (f.sub_exists("item")) {
			d = this._parse(f, g.id, 1);
		}
	}
	if (d != "") {
		this.nodeAskingCall = d;
	}
	f.each("userdata", function(a) {
		this.setUserData(f.get("id"), a.get("name"), a.content());
	}, this);
};
dhtmlXTreeObject.prototype._parse = function(c, f, a, b) {
	if (this._srnd && !this.parentObject.offsetHeight) {
		var m = this;
		return window.setTimeout(function() {
			m._parse(c, f, a, b);
		}, 100);
	}
	if (!c.exists()) {
		return;
	}
	this.skipLock = true;
	this.parsCount = this.parsCount ? (this.parsCount + 1) : 1;
	this.XMLloadingWarning = 1;
	if (!f) {
		f = c.get("id");
		if (c.get("radio")) {
			this.htmlNode._r_logic = true;
		}
		this.parsingOn = f;
		this.parsedArray = new Array();
		this.setCheckList = "";
		this.nodeAskingCall = "";
	}
	var l = this._globalIdStorageFind(f);
	if (!l) {
		return dhtmlxError.throwError("DataStructure",
				"XML reffers to not existing parent");
	}
	if ((l.childsCount) && (!b) && (!this._edsbps) && (!l._has_top)) {
		var g = l.childNodes[l.childsCount - 1];
	} else {
		var g = 0;
	}
	this.npl = 0;
	c.each("item", function(o, n) {
		l.XMLload = 1;
		if ((this._epgps) && (this._epgpsC == this.npl)) {
			this._setNextPageSign(l, this.npl + 1 * (b || 0), a, node);
			return -1;
		}
		this._parseItem(o, l, g);
		this.npl++;
	}, this, b);
	if (!a) {
		c.each("userdata", function(n) {
			this.setUserData(c.get("id"), n.get("name"), n.content());
		}, this);
		l.XMLload = 1;
		if (this.waitUpdateXML) {
			this.waitUpdateXML = false;
			for ( var e = l.childsCount - 1; e >= 0; e--) {
				if (l.childNodes[e]._dmark) {
					this.deleteItem(l.childNodes[e].id);
				}
			}
		}
		var j = this._globalIdStorageFind(this.parsingOn);
		for ( var e = 0; e < this.parsedArray.length; e++) {
			l.htmlNode.childNodes[0].appendChild(this.parsedArray[e]);
		}
		this.lastLoadedXMLId = f;
		this.XMLloadingWarning = 0;
		var k = this.setCheckList.split(this.dlmtr);
		for ( var d = 0; d < k.length; d++) {
			if (k[d]) {
				this.setCheck(k[d], 1);
			}
		}
		if ((this.XMLsource) && (this.tscheck) && (this.smcheck)
				&& (l.id != this.rootId)) {
			if (l.checkstate === 0) {
				this._setSubChecked(0, l);
			} else {
				if (l.checkstate === 1) {
					this._setSubChecked(1, l);
				}
			}
		}
		if (this.onXLE) {
			this.onXLE(this, f);
		}
		this._redrawFrom(this, null, b);
		if (c.get("order") && c.get("order") != "none") {
			this._reorderBranch(l, c.get("order"), true);
		}
		if (this.nodeAskingCall != "") {
			this.selectItem(this.nodeAskingCall, true);
		}
		if (this._branchUpdate) {
			this._branchUpdateNext(c);
		}
	}
	if (this.parsCount == 1) {
		this.parsingOn = null;
		if ((!this._edsbps) || (!this._edsbpsA.length)) {
			var h = this;
			window.setTimeout(function() {
				h.callEvent("onXLE", [ h, f ]);
			}, 1);
			this.xmlstate = 0;
		}
		this.skipLock = false;
	}
	this.parsCount--;
	if ((this._epgps) && (b)) {
		this._setPrevPageSign(l, (b || 0), a, node);
	}
	return this.nodeAskingCall;
};
dhtmlXTreeObject.prototype._branchUpdateNext = function(a) {
	a.each("item", function(d) {
		var b = d.get("id");
		if (this._idpull[b] && (!this._idpull[b].XMLload)) {
			return;
		}
		this._branchUpdate++;
		this.smartRefreshItem(d.get("id"), d);
	}, this);
	this._branchUpdate--;
};
dhtmlXTreeObject.prototype.checkUserData = function(b, c) {
	if ((b.nodeType == 1) && (b.tagName == "userdata")) {
		var a = b.getAttribute("name");
		if ((a) && (b.childNodes[0])) {
			this.setUserData(c, a, b.childNodes[0].data);
		}
	}
};
dhtmlXTreeObject.prototype._redrawFrom = function(g, a, f, b) {
	if (!a) {
		var d = g._globalIdStorageFind(g.lastLoadedXMLId);
		g.lastLoadedXMLId = -1;
		if (!d) {
			return 0;
		}
	} else {
		d = a;
	}
	var e = 0;
	for ( var c = (f ? f - 1 : 0); c < d.childsCount; c++) {
		if ((!this._branchUpdate) || (this._getOpenState(d) == 1)) {
			if ((!a) || (b == 1)) {
				d.childNodes[c].htmlNode.parentNode.parentNode.style.display = "";
			}
		}
		if (d.childNodes[c].openMe == 1) {
			this._openItem(d.childNodes[c]);
			d.childNodes[c].openMe = 0;
		}
		g._redrawFrom(g, d.childNodes[c]);
	}
	if ((!d.unParsed) && ((d.XMLload) || (!this.XMLsource))) {
		d._acc = e;
	}
	g._correctLine(d);
	g._correctPlus(d);
};
dhtmlXTreeObject.prototype._createSelf = function() {
	var a = document.createElement("div");
	a.className = "containerTableStyle";
	a.style.width = this.width;
	a.style.height = this.height;
	this.parentObject.appendChild(a);
	return a;
};
dhtmlXTreeObject.prototype._xcloseAll = function(b) {
	if (b.unParsed) {
		return;
	}
	if (this.rootId != b.id) {
		var d = b.htmlNode.childNodes[0].childNodes;
		var a = d.length;
		for ( var c = 1; c < a; c++) {
			d[c].style.display = "none";
		}
		this._correctPlus(b);
	}
	for ( var c = 0; c < b.childsCount; c++) {
		if (b.childNodes[c].childsCount) {
			this._xcloseAll(b.childNodes[c]);
		}
	}
};
dhtmlXTreeObject.prototype._xopenAll = function(a) {
	this._HideShow(a, 2);
	for ( var b = 0; b < a.childsCount; b++) {
		this._xopenAll(a.childNodes[b]);
	}
};
dhtmlXTreeObject.prototype._correctPlus = function(b) {
	if (!b.htmlNode) {
		return;
	}
	var c = b.htmlNode.childNodes[0].childNodes[0].childNodes[0].lastChild;
	var e = b.htmlNode.childNodes[0].childNodes[0].childNodes[2].childNodes[0];
	var a = this.lineArray;
	if ((this.XMLsource) && (!b.XMLload)) {
		var a = this.plusArray;
		this._setSrc(e, this.imPath + b.images[2]);
		if (this._txtimg) {
			return (c.innerHTML = "[+]");
		}
	} else {
		if ((b.childsCount) || (b.unParsed)) {
			if ((b.htmlNode.childNodes[0].childNodes[1])
					&& (b.htmlNode.childNodes[0].childNodes[1].style.display != "none")) {
				if (!b.wsign) {
					var a = this.minusArray;
				}
				this._setSrc(e, this.imPath + b.images[1]);
				if (this._txtimg) {
					return (c.innerHTML = "[-]");
				}
			} else {
				if (!b.wsign) {
					var a = this.plusArray;
				}
				this._setSrc(e, this.imPath + b.images[2]);
				if (this._txtimg) {
					return (c.innerHTML = "[+]");
				}
			}
		} else {
			this._setSrc(e, this.imPath + b.images[0]);
		}
	}
	var d = 2;
	if (!b.treeNod.treeLinesOn) {
		this._setSrc(c, this.imPath + a[3]);
	} else {
		if (b.parentObject) {
			d = this._getCountStatus(b.id, b.parentObject);
		}
		this._setSrc(c, this.imPath + a[d]);
	}
};
dhtmlXTreeObject.prototype._correctLine = function(b) {
	if (!b.htmlNode) {
		return;
	}
	var a = b.parentObject;
	if (a) {
		if ((this._getLineStatus(b.id, a) == 0) || (!this.treeLinesOn)) {
			for ( var c = 1; c <= b.childsCount; c++) {
				if (!b.htmlNode.childNodes[0].childNodes[c]) {
					break;
				}
				b.htmlNode.childNodes[0].childNodes[c].childNodes[0].style.backgroundImage = "";
				b.htmlNode.childNodes[0].childNodes[c].childNodes[0].style.backgroundRepeat = "";
			}
		} else {
			for ( var c = 1; c <= b.childsCount; c++) {
				if (!b.htmlNode.childNodes[0].childNodes[c]) {
					break;
				}
				b.htmlNode.childNodes[0].childNodes[c].childNodes[0].style.backgroundImage = "url("
						+ this.imPath + this.lineArray[5] + ")";
				b.htmlNode.childNodes[0].childNodes[c].childNodes[0].style.backgroundRepeat = "repeat-y";
			}
		}
	}
};
dhtmlXTreeObject.prototype._getCountStatus = function(b, a) {
	if (a.childsCount <= 1) {
		if (a.id == this.rootId) {
			return 4;
		} else {
			return 0;
		}
	}
	if (a.childNodes[0].id == b) {
		if (!a.id) {
			return 2;
		} else {
			return 1;
		}
	}
	if (a.childNodes[a.childsCount - 1].id == b) {
		return 0;
	}
	return 1;
};
dhtmlXTreeObject.prototype._getLineStatus = function(b, a) {
	if (a.childNodes[a.childsCount - 1].id == b) {
		return 0;
	}
	return 1;
};
dhtmlXTreeObject.prototype._HideShow = function(b, e) {
	if ((this.XMLsource) && (!b.XMLload)) {
		if (e == 1) {
			return;
		}
		b.XMLload = 1;
		this._loadDynXML(b.id);
		return;
	}
	var d = b.htmlNode.childNodes[0].childNodes;
	var a = d.length;
	if (a > 1) {
		if (((d[1].style.display != "none") || (e == 1)) && (e != 2)) {
			this.allTree.childNodes[0].border = "1";
			this.allTree.childNodes[0].border = "0";
			nodestyle = "none";
		} else {
			nodestyle = "";
		}
		for ( var c = 1; c < a; c++) {
			d[c].style.display = nodestyle;
		}
	}
	this._correctPlus(b);
};
dhtmlXTreeObject.prototype._getOpenState = function(a) {
	var b = a.htmlNode.childNodes[0].childNodes;
	if (b.length <= 1) {
		return 0;
	}
	if (b[1].style.display != "none") {
		return 1;
	} else {
		return -1;
	}
};
dhtmlXTreeObject.prototype.onRowClick2 = function() {
	var a = this.parentObject.treeNod;
	if (!a.callEvent("onDblClick", [ this.parentObject.id, a ])) {
		return 0;
	}
	if ((this.parentObject.closeble) && (this.parentObject.closeble != "0")) {
		a._HideShow(this.parentObject);
	} else {
		a._HideShow(this.parentObject, 2);
	}
	if (a.checkEvent("onOpenEnd")) {
		if (!a.xmlstate) {
			a.callEvent("onOpenEnd", [ this.parentObject.id,
					a._getOpenState(this.parentObject) ]);
		} else {
			a._oie_onXLE.push(a.onXLE);
			a.onXLE = a._epnFHe;
		}
	}
};
dhtmlXTreeObject.prototype.onRowClick = function() {
	var a = this.parentObject.treeNod;
	if (!a.callEvent("onOpenStart", [ this.parentObject.id,
			a._getOpenState(this.parentObject) ])) {
		return 0;
	}
	if ((this.parentObject.closeble) && (this.parentObject.closeble != "0")) {
		a._HideShow(this.parentObject);
	} else {
		a._HideShow(this.parentObject, 2);
	}
	if (a.checkEvent("onOpenEnd")) {
		if (!a.xmlstate) {
			a.callEvent("onOpenEnd", [ this.parentObject.id,
					a._getOpenState(this.parentObject) ]);
		} else {
			a._oie_onXLE.push(a.onXLE);
			a.onXLE = a._epnFHe;
		}
	}
};
dhtmlXTreeObject.prototype._epnFHe = function(b, c, a) {
	if (c != this.rootId) {
		this.callEvent("onOpenEnd", [ c, b.getOpenState(c) ]);
	}
	b.onXLE = b._oie_onXLE.pop();
	if (!a && !b._oie_onXLE.length) {
		if (b.onXLE) {
			b.onXLE(b, c);
		}
	}
};
dhtmlXTreeObject.prototype.onRowClickDown = function(b) {
	b = b || window.event;
	var a = this.parentObject.treeNod;
	a._selectItem(this.parentObject, b);
};
dhtmlXTreeObject.prototype.getSelectedItemId = function() {
	var b = new Array();
	for ( var a = 0; a < this._selected.length; a++) {
		b[a] = this._selected[a].id;
	}
	return (b.join(this.dlmtr));
};
dhtmlXTreeObject.prototype._selectItem = function(a, b) {
	if (this._onSSCF) {
		this._onSSCFold = this.getSelectedItemId();
	}
	this._unselectItems();
	this._markItem(a);
	if (this._onSSCF) {
		var c = this.getSelectedItemId();
		if (c != this._onSSCFold) {
			this.callEvent("onSelect", [ c ]);
		}
	}
};
dhtmlXTreeObject.prototype._markItem = function(a) {
	if (a.scolor) {
		a.span.style.color = a.scolor;
	}
	a.span.className = "selectedTreeRow";
	a.i_sel = true;
	this._selected[this._selected.length] = a;
};
dhtmlXTreeObject.prototype.getIndexById = function(b) {
	var a = this._globalIdStorageFind(b);
	if (!a) {
		return null;
	}
	return this._getIndex(a);
};
dhtmlXTreeObject.prototype._getIndex = function(a) {
	var c = a.parentObject;
	for ( var b = 0; b < c.childsCount; b++) {
		if (c.childNodes[b] == a) {
			return b;
		}
	}
};
dhtmlXTreeObject.prototype._unselectItem = function(b) {
	if ((b) && (b.i_sel)) {
		b.span.className = "standartTreeRow";
		if (b.acolor) {
			b.span.style.color = b.acolor;
		}
		b.i_sel = false;
		for ( var a = 0; a < this._selected.length; a++) {
			if (!this._selected[a].i_sel) {
				this._selected.splice(a, 1);
				break;
			}
		}
	}
};
dhtmlXTreeObject.prototype._unselectItems = function() {
	for ( var a = 0; a < this._selected.length; a++) {
		var b = this._selected[a];
		b.span.className = "standartTreeRow";
		if (b.acolor) {
			b.span.style.color = b.acolor;
		}
		b.i_sel = false;
	}
	this._selected = new Array();
};
dhtmlXTreeObject.prototype.onRowSelect = function(d, c, g) {
	d = d || window.event;
	var b = this.parentObject;
	if (c) {
		b = c.parentObject;
	}
	var a = b.treeNod;
	var f = a.getSelectedItemId();
	if ((!d) || (!d.skipUnSel)) {
		a._selectItem(b, d);
	}
	if (!g) {
		if ((d) && (d.button == 2)) {
			a.callEvent("onRightClick", [ b.id, d ]);
		}
		if (b.actionHandler) {
			b.actionHandler(b.id, f);
		} else {
			a.callEvent("onClick", [ b.id, f ]);
		}
	}
};
dhtmlXTreeObject.prototype._correctCheckStates = function(e) {
	if (!this.tscheck) {
		return;
	}
	if (!e) {
		return;
	}
	if (e.id == this.rootId) {
		return;
	}
	var c = e.childNodes;
	var b = 0;
	var a = 0;
	if (e.childsCount == 0) {
		return;
	}
	for ( var d = 0; d < e.childsCount; d++) {
		if (c[d].dscheck) {
			continue;
		}
		if (c[d].checkstate == 0) {
			b = 1;
		} else {
			if (c[d].checkstate == 1) {
				a = 1;
			} else {
				b = 1;
				a = 1;
				break;
			}
		}
	}
	if ((b) && (a)) {
		this._setCheck(e, "unsure");
	} else {
		if (b) {
			this._setCheck(e, false);
		} else {
			this._setCheck(e, true);
		}
	}
	this._correctCheckStates(e.parentObject);
};
dhtmlXTreeObject.prototype.onCheckBoxClick = function(a) {
	if (!this.treeNod.callEvent("onBeforeCheck", [ this.parentObject.id,
			this.parentObject.checkstate ])) {
		return;
	}
	if (this.parentObject.dscheck) {
		return true;
	}
	if (this.treeNod.tscheck) {
		if (this.parentObject.checkstate == 1) {
			this.treeNod._setSubChecked(false, this.parentObject);
		} else {
			this.treeNod._setSubChecked(true, this.parentObject);
		}
	} else {
		if (this.parentObject.checkstate == 1) {
			this.treeNod._setCheck(this.parentObject, false);
		} else {
			this.treeNod._setCheck(this.parentObject, true);
		}
	}
	this.treeNod._correctCheckStates(this.parentObject.parentObject);
	return this.treeNod.callEvent("onCheck", [ this.parentObject.id,
			this.parentObject.checkstate ]);
};
dhtmlXTreeObject.prototype._createItem = function(m, l, h) {
	var n = document.createElement("table");
	n.cellSpacing = 0;
	n.cellPadding = 0;
	n.border = 0;
	if (this.hfMode) {
		n.style.tableLayout = "fixed";
	}
	n.style.margin = 0;
	n.style.padding = 0;
	var g = document.createElement("tbody");
	var k = document.createElement("tr");
	var d = document.createElement("td");
	d.className = "standartTreeImage";
	if (this._txtimg) {
		var e = document.createElement("div");
		d.appendChild(e);
		e.className = "dhx_tree_textSign";
	} else {
		var e = this._getImg(l.id);
		e.border = "0";
		if (e.tagName == "IMG") {
			e.align = "absmiddle";
		}
		d.appendChild(e);
		e.style.padding = 0;
		e.style.margin = 0;
		e.style.width = this.def_line_img_x;
		e.style.height = this.def_line_img_y;
	}
	var c = document.createElement("td");
	var j = this._getImg(this.cBROf ? this.rootId : l.id);
	j.checked = 0;
	this._setSrc(j, this.imPath + this.checkArray[0]);
	j.style.width = "16px";
	j.style.height = "16px";
	if (!m) {
		((!_isIE) ? c : j).style.display = "none";
	}
	c.appendChild(j);
	if ((!this.cBROf) && (j.tagName == "IMG")) {
		j.align = "absmiddle";
	}
	j.onclick = this.onCheckBoxClick;
	j.treeNod = this;
	j.parentObject = l;
	c.width = "20px";
	var b = document.createElement("td");
	b.className = "standartTreeImage";
	var f = this._getImg(this.timgen ? l.id : this.rootId);
	f.onmousedown = this._preventNsDrag;
	f.ondragstart = this._preventNsDrag;
	f.border = "0";
	if (this._aimgs) {
		f.parentObject = l;
		if (f.tagName == "IMG") {
			f.align = "absmiddle";
		}
		f.onclick = this.onRowSelect;
	}
	if (!h) {
		this._setSrc(f, this.imPath + this.imageArray[0]);
	}
	b.appendChild(f);
	f.style.padding = 0;
	f.style.margin = 0;
	if (this.timgen) {
		b.style.width = f.style.width = this.def_img_x;
		f.style.height = this.def_img_y;
	} else {
		f.style.width = "0px";
		f.style.height = "0px";
		if (_isOpera) {
			b.style.display = "none";
		}
	}
	var a = document.createElement("td");
	a.className = "standartTreeRow";
	l.span = document.createElement("span");
	l.span.className = "standartTreeRow";
	if (this.mlitems) {
		l.span.style.width = this.mlitems;
		l.span.style.display = "block";
	} else {
		a.noWrap = true;
	}
	if (!_isKHTML) {
		a.style.width = "100%";
	}
	l.span.innerHTML = l.label;
	a.appendChild(l.span);
	a.parentObject = l;
	d.parentObject = l;
	a.onclick = this.onRowSelect;
	d.onclick = this.onRowClick;
	a.ondblclick = this.onRowClick2;
	if (this.ettip) {
		k.title = l.label;
	}
	if (this.dragAndDropOff) {
		if (this._aimgs) {
			this.dragger.addDraggableItem(b, this);
			b.parentObject = l;
		}
		this.dragger.addDraggableItem(a, this);
	}
	l.span.style.paddingLeft = "5px";
	l.span.style.paddingRight = "5px";
	a.style.verticalAlign = "";
	a.style.fontSize = "10pt";
	a.style.cursor = this.style_pointer;
	k.appendChild(d);
	k.appendChild(c);
	k.appendChild(b);
	k.appendChild(a);
	g.appendChild(k);
	n.appendChild(g);
	if (this.ehlt) {
		k.onmousemove = this._itemMouseIn;
		k[(_isIE) ? "onmouseleave" : "onmouseout"] = this._itemMouseOut;
	}
	if (this.checkEvent && this.checkEvent("onRightClick")) {
		k.oncontextmenu = Function(
				"e",
				"this.childNodes[0].parentObject.treeNod.callEvent('onRightClick',[this.childNodes[0].parentObject.id,(e||window.event)]);return false;");
	}
	return n;
};
dhtmlXTreeObject.prototype.setImagePath = function(a) {
	this.imPath = a;
};
dhtmlXTreeObject.prototype.setOnRightClickHandler = function(a) {
	this.attachEvent("onRightClick", a);
};
dhtmlXTreeObject.prototype.setOnClickHandler = function(a) {
	this.attachEvent("onClick", a);
};
dhtmlXTreeObject.prototype.setOnSelectStateChange = function(a) {
	this.attachEvent("onSelect", a);
	this._onSSCF = true;
};
dhtmlXTreeObject.prototype.setXMLAutoLoading = function(a) {
	this.XMLsource = a;
};
dhtmlXTreeObject.prototype.setOnCheckHandler = function(a) {
	this.attachEvent("onCheck", a);
};
dhtmlXTreeObject.prototype.setOnOpenHandler = function(a) {
	this.attachEvent("onOpenStart", a);
};
dhtmlXTreeObject.prototype.setOnOpenStartHandler = function(a) {
	this.attachEvent("onOpenStart", a);
};
dhtmlXTreeObject.prototype.setOnOpenEndHandler = function(a) {
	this.attachEvent("onOpenEnd", a);
};
dhtmlXTreeObject.prototype.setOnDblClickHandler = function(a) {
	this.attachEvent("onDblClick", a);
};
dhtmlXTreeObject.prototype.openAllItems = function(b) {
	var a = this._globalIdStorageFind(b);
	if (!a) {
		return 0;
	}
	this._xopenAll(a);
};
dhtmlXTreeObject.prototype.getOpenState = function(b) {
	var a = this._globalIdStorageFind(b);
	if (!a) {
		return "";
	}
	return this._getOpenState(a);
};
dhtmlXTreeObject.prototype.closeAllItems = function(b) {
	if (b === window.undefined) {
		b = this.rootId;
	}
	var a = this._globalIdStorageFind(b);
	if (!a) {
		return 0;
	}
	this._xcloseAll(a);
	this.allTree.childNodes[0].border = "1";
	this.allTree.childNodes[0].border = "0";
};
dhtmlXTreeObject.prototype.setUserData = function(d, b, c) {
	var a = this._globalIdStorageFind(d, 0, true);
	if (!a) {
		return;
	}
	if (b == "hint") {
		a.htmlNode.childNodes[0].childNodes[0].title = c;
	}
	if (typeof (a.userData["t_" + b]) == "undefined") {
		if (!a._userdatalist) {
			a._userdatalist = b;
		} else {
			a._userdatalist += "," + b;
		}
	}
	a.userData["t_" + b] = c;
};
dhtmlXTreeObject.prototype.getUserData = function(c, b) {
	var a = this._globalIdStorageFind(c, 0, true);
	if (!a) {
		return;
	}
	return a.userData["t_" + b];
};
dhtmlXTreeObject.prototype.getItemColor = function(c) {
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return 0;
	}
	var b = new Object();
	if (a.acolor) {
		b.acolor = a.acolor;
	}
	if (a.acolor) {
		b.scolor = a.scolor;
	}
	return b;
};
dhtmlXTreeObject.prototype.setItemColor = function(c, b, d) {
	if ((c) && (c.span)) {
		var a = c;
	} else {
		var a = this._globalIdStorageFind(c);
	}
	if (!a) {
		return 0;
	} else {
		if (a.i_sel) {
			if (d) {
				a.span.style.color = d;
			}
		} else {
			if (b) {
				a.span.style.color = b;
			}
		}
		if (d) {
			a.scolor = d;
		}
		if (b) {
			a.acolor = b;
		}
	}
};
dhtmlXTreeObject.prototype.getItemText = function(b) {
	var a = this._globalIdStorageFind(b);
	if (!a) {
		return 0;
	}
	return (a.htmlNode.childNodes[0].childNodes[0].childNodes[3].childNodes[0].innerHTML);
};
dhtmlXTreeObject.prototype.getParentId = function(b) {
	var a = this._globalIdStorageFind(b);
	if ((!a) || (!a.parentObject)) {
		return "";
	}
	return a.parentObject.id;
};
dhtmlXTreeObject.prototype.changeItemId = function(b, c) {
	if (b == c) {
		return;
	}
	var a = this._globalIdStorageFind(b);
	if (!a) {
		return 0;
	}
	a.id = c;
	a.span.contextMenuId = c;
	this._idpull[c] = this._idpull[b];
	delete this._idpull[b];
};
dhtmlXTreeObject.prototype.doCut = function() {
	if (this.nodeCut) {
		this.clearCut();
	}
	this.nodeCut = (new Array()).concat(this._selected);
	for ( var a = 0; a < this.nodeCut.length; a++) {
		var b = this.nodeCut[a];
		b._cimgs = new Array();
		b._cimgs[0] = b.images[0];
		b._cimgs[1] = b.images[1];
		b._cimgs[2] = b.images[2];
		b.images[0] = b.images[1] = b.images[2] = this.cutImage;
		this._correctPlus(b);
	}
};
dhtmlXTreeObject.prototype.doPaste = function(c) {
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return 0;
	}
	for ( var b = 0; b < this.nodeCut.length; b++) {
		if (this._checkPNodes(a, this.nodeCut[b])) {
			continue;
		}
		this._moveNode(this.nodeCut[b], a);
	}
	this.clearCut();
};
dhtmlXTreeObject.prototype.clearCut = function() {
	for ( var a = 0; a < this.nodeCut.length; a++) {
		var b = this.nodeCut[a];
		b.images[0] = b._cimgs[0];
		b.images[1] = b._cimgs[1];
		b.images[2] = b._cimgs[2];
		this._correctPlus(b);
	}
	this.nodeCut = new Array();
};
dhtmlXTreeObject.prototype._moveNode = function(a, b) {
	return this._moveNodeTo(a, b);
};
dhtmlXTreeObject.prototype._fixNodesCollection = function(h, f) {
	var b = 0;
	var d = 0;
	var g = h.childNodes;
	var a = h.childsCount - 1;
	if (f == g[a]) {
		return;
	}
	for ( var e = 0; e < a; e++) {
		if (g[e] == g[a]) {
			g[e] = g[e + 1];
			g[e + 1] = g[a];
		}
	}
	for ( var e = 0; e < a + 1; e++) {
		if (b) {
			var c = g[e];
			g[e] = b;
			b = c;
		} else {
			if (g[e] == f) {
				b = g[e];
				g[e] = g[a];
			}
		}
	}
};
dhtmlXTreeObject.prototype._recreateBranch = function(a, d, c, h) {
	var f;
	var b = "";
	if (c) {
		for (f = 0; f < d.childsCount; f++) {
			if (d.childNodes[f] == c) {
				break;
			}
		}
		if (f != 0) {
			c = d.childNodes[f - 1];
		} else {
			b = "TOP";
			c = "";
		}
	}
	var g = this._onradh;
	this._onradh = null;
	var e = this._attachChildNode(d, a.id, a.label, 0, a.images[0],
			a.images[1], a.images[2], b, 0, c);
	e._userdatalist = a._userdatalist;
	e.userData = a.userData.clone();
	e.XMLload = a.XMLload;
	if (g) {
		this._onradh = g;
		this._onradh(e.id);
	}
	for ( var f = 0; f < a.childsCount; f++) {
		this._recreateBranch(a.childNodes[f], e, 0, 1);
	}
	return e;
};
dhtmlXTreeObject.prototype._moveNodeTo = function(m, o, l) {
	if (m.treeNod._nonTrivialNode) {
		return m.treeNod._nonTrivialNode(this, o, l, m);
	}
	if (o.mytype) {
		var g = (m.treeNod.lWin != o.lWin);
	} else {
		var g = (m.treeNod.lWin != o.treeNod.lWin);
	}
	if (!this.callEvent("onDrag", [ m.id, o.id, (l ? l.id : null), m.treeNod,
			o.treeNod ])) {
		return false;
	}
	if ((o.XMLload == 0) && (this.XMLsource)) {
		o.XMLload = 1;
		this._loadDynXML(o.id);
	}
	this.openItem(o.id);
	var b = m.treeNod;
	var j = m.parentObject.childsCount;
	var k = m.parentObject;
	if ((g) || (b.dpcpy)) {
		var d = m.id;
		m = this._recreateBranch(m, o, l);
		if (!b.dpcpy) {
			b.deleteItem(d);
		}
	} else {
		var e = o.childsCount;
		var n = o.childNodes;
		if (e == 0) {
			o._open = true;
		}
		b._unselectItem(m);
		n[e] = m;
		m.treeNod = o.treeNod;
		o.childsCount++;
		var h = this._drawNewTr(n[e].htmlNode);
		if (!l) {
			o.htmlNode.childNodes[0].appendChild(h);
			if (this.dadmode == 1) {
				this._fixNodesCollection(o, l);
			}
		} else {
			o.htmlNode.childNodes[0].insertBefore(h, l.tr);
			this._fixNodesCollection(o, l);
			n = o.childNodes;
		}
	}
	if ((!b.dpcpy) && (!g)) {
		var a = m.tr;
		if ((document.all)
				&& (navigator.appVersion.search(/MSIE\ 5\.0/gi) != -1)) {
			window.setTimeout(function() {
				a.parentNode.removeChild(a);
			}, 250);
		} else {
			m.parentObject.htmlNode.childNodes[0].removeChild(m.tr);
		}
		if ((!l) || (o != m.parentObject)) {
			for ( var f = 0; f < k.childsCount; f++) {
				if (k.childNodes[f].id == m.id) {
					k.childNodes[f] = 0;
					break;
				}
			}
		} else {
			k.childNodes[k.childsCount - 1] = 0;
		}
		b._compressChildList(k.childsCount, k.childNodes);
		k.childsCount--;
	}
	if ((!g) && (!b.dpcpy)) {
		m.tr = h;
		h.nodem = m;
		m.parentObject = o;
		if (b != o.treeNod) {
			if (m.treeNod._registerBranch(m, b)) {
				return;
			}
			this._clearStyles(m);
			this._redrawFrom(this, m.parentObject);
		}
		this._correctPlus(o);
		this._correctLine(o);
		this._correctLine(m);
		this._correctPlus(m);
		if (l) {
			this._correctPlus(l);
		} else {
			if (o.childsCount >= 2) {
				this._correctPlus(n[o.childsCount - 2]);
				this._correctLine(n[o.childsCount - 2]);
			}
		}
		this._correctPlus(n[o.childsCount - 1]);
		if (this.tscheck) {
			this._correctCheckStates(o);
		}
		if (b.tscheck) {
			b._correctCheckStates(k);
		}
	}
	if (j > 1) {
		b._correctPlus(k.childNodes[j - 2]);
		b._correctLine(k.childNodes[j - 2]);
	}
	b._correctPlus(k);
	b._correctLine(k);
	this.callEvent("onDrop", [ m.id, o.id, (l ? l.id : null), b, o.treeNod ]);
	return m.id;
};
dhtmlXTreeObject.prototype._clearStyles = function(a) {
	if (!a.htmlNode) {
		return;
	}
	var d = a.htmlNode.childNodes[0].childNodes[0].childNodes[1];
	var b = d.nextSibling.nextSibling;
	a.span.innerHTML = a.label;
	a.i_sel = false;
	if (a._aimgs) {
		this.dragger.removeDraggableItem(d.nextSibling);
	}
	if (this.checkBoxOff) {
		d.childNodes[0].style.display = "";
		d.childNodes[0].onclick = this.onCheckBoxClick;
		this._setSrc(d.childNodes[0], this.imPath
				+ this.checkArray[a.checkstate]);
	} else {
		d.childNodes[0].style.display = "none";
	}
	d.childNodes[0].treeNod = this;
	this.dragger.removeDraggableItem(b);
	if (this.dragAndDropOff) {
		this.dragger.addDraggableItem(b, this);
	}
	if (this._aimgs) {
		this.dragger.addDraggableItem(d.nextSibling, this);
	}
	b.childNodes[0].className = "standartTreeRow";
	b.onclick = this.onRowSelect;
	b.ondblclick = this.onRowClick2;
	d.previousSibling.onclick = this.onRowClick;
	this._correctLine(a);
	this._correctPlus(a);
	for ( var c = 0; c < a.childsCount; c++) {
		this._clearStyles(a.childNodes[c]);
	}
};
dhtmlXTreeObject.prototype._registerBranch = function(b, a) {
	if (a) {
		a._globalIdStorageSub(b.id);
	}
	b.id = this._globalIdStorageAdd(b.id, b);
	b.treeNod = this;
	for ( var c = 0; c < b.childsCount; c++) {
		this._registerBranch(b.childNodes[c], a);
	}
	return 0;
};
dhtmlXTreeObject.prototype.enableThreeStateCheckboxes = function(a) {
	this.tscheck = convertStringToBoolean(a);
};
dhtmlXTreeObject.prototype.setOnMouseInHandler = function(a) {
	this.ehlt = true;
	this.attachEvent("onMouseIn", a);
};
dhtmlXTreeObject.prototype.setOnMouseOutHandler = function(a) {
	this.ehlt = true;
	this.attachEvent("onMouseOut", a);
};
dhtmlXTreeObject.prototype.enableTreeImages = function(a) {
	this.timgen = convertStringToBoolean(a);
};
dhtmlXTreeObject.prototype.enableFixedMode = function(a) {
	this.hfMode = convertStringToBoolean(a);
};
dhtmlXTreeObject.prototype.enableCheckBoxes = function(b, a) {
	this.checkBoxOff = convertStringToBoolean(b);
	this.cBROf = (!(this.checkBoxOff || convertStringToBoolean(a)));
};
dhtmlXTreeObject.prototype.setStdImages = function(a, c, b) {
	this.imageArray[0] = a;
	this.imageArray[1] = c;
	this.imageArray[2] = b;
};
dhtmlXTreeObject.prototype.enableTreeLines = function(a) {
	this.treeLinesOn = convertStringToBoolean(a);
};
dhtmlXTreeObject.prototype.setImageArrays = function(d, a, f, e, c, b) {
	switch (d) {
	case "plus":
		this.plusArray[0] = a;
		this.plusArray[1] = f;
		this.plusArray[2] = e;
		this.plusArray[3] = c;
		this.plusArray[4] = b;
		break;
	case "minus":
		this.minusArray[0] = a;
		this.minusArray[1] = f;
		this.minusArray[2] = e;
		this.minusArray[3] = c;
		this.minusArray[4] = b;
		break;
	}
};
dhtmlXTreeObject.prototype.openItem = function(b) {
	var a = this._globalIdStorageFind(b);
	if (!a) {
		return 0;
	} else {
		return this._openItem(a);
	}
};
dhtmlXTreeObject.prototype._openItem = function(a) {
	var b = this._getOpenState(a);
	if ((b < 0) || (((this.XMLsource) && (!a.XMLload)))) {
		if (!this.callEvent("onOpenStart", [ a.id, b ])) {
			return 0;
		}
		this._HideShow(a, 2);
		if (this.checkEvent("onOpenEnd")) {
			if (this.onXLE == this._epnFHe) {
				this._epnFHe(this, a.id, true);
			}
			if (!this.xmlstate || !this.XMLsource) {
				this.callEvent("onOpenEnd", [ a.id, this._getOpenState(a) ]);
			} else {
				this._oie_onXLE.push(this.onXLE);
				this.onXLE = this._epnFHe;
			}
		}
	} else {
		if (this._srnd) {
			this._HideShow(a, 2);
		}
	}
	if (a.parentObject) {
		this._openItem(a.parentObject);
	}
};
dhtmlXTreeObject.prototype.closeItem = function(b) {
	if (this.rootId == b) {
		return 0;
	}
	var a = this._globalIdStorageFind(b);
	if (!a) {
		return 0;
	}
	if (a.closeble) {
		this._HideShow(a, 1);
	}
};
dhtmlXTreeObject.prototype.getLevel = function(b) {
	var a = this._globalIdStorageFind(b);
	if (!a) {
		return 0;
	}
	return this._getNodeLevel(a, 0);
};
dhtmlXTreeObject.prototype.setItemCloseable = function(c, a) {
	a = convertStringToBoolean(a);
	if ((c) && (c.span)) {
		var b = c;
	} else {
		var b = this._globalIdStorageFind(c);
	}
	if (!b) {
		return 0;
	}
	b.closeble = a;
};
dhtmlXTreeObject.prototype._getNodeLevel = function(a, b) {
	if (a.parentObject) {
		return this._getNodeLevel(a.parentObject, b + 1);
	}
	return (b);
};
dhtmlXTreeObject.prototype.hasChildren = function(b) {
	var a = this._globalIdStorageFind(b);
	if (!a) {
		return 0;
	} else {
		if ((this.XMLsource) && (!a.XMLload)) {
			return true;
		} else {
			return a.childsCount;
		}
	}
};
dhtmlXTreeObject.prototype._getLeafCount = function(e) {
	var d = 0;
	for ( var c = 0; c < e.childsCount; c++) {
		if (e.childNodes[c].childsCount == 0) {
			d++;
		}
	}
	return d;
};
dhtmlXTreeObject.prototype.setItemText = function(d, c, b) {
	var a = this._globalIdStorageFind(d);
	if (!a) {
		return 0;
	}
	a.label = c;
	a.span.innerHTML = c;
	a.span.parentNode.parentNode.title = b || "";
};
dhtmlXTreeObject.prototype.getItemTooltip = function(b) {
	var a = this._globalIdStorageFind(b);
	if (!a) {
		return "";
	}
	return (a.span.parentNode.parentNode._dhx_title
			|| a.span.parentNode.parentNode.title || "");
};
dhtmlXTreeObject.prototype.refreshItem = function(b) {
	if (!b) {
		b = this.rootId;
	}
	var a = this._globalIdStorageFind(b);
	this.deleteChildItems(b);
	this._loadDynXML(b);
};
dhtmlXTreeObject.prototype.setItemImage2 = function(d, a, e, c) {
	var b = this._globalIdStorageFind(d);
	if (!b) {
		return 0;
	}
	b.images[1] = e;
	b.images[2] = c;
	b.images[0] = a;
	this._correctPlus(b);
};
dhtmlXTreeObject.prototype.setItemImage = function(c, a, d) {
	var b = this._globalIdStorageFind(c);
	if (!b) {
		return 0;
	}
	if (d) {
		b.images[1] = a;
		b.images[2] = d;
	} else {
		b.images[0] = a;
	}
	this._correctPlus(b);
};
dhtmlXTreeObject.prototype.getSubItems = function(c) {
	var a = this._globalIdStorageFind(c, 0, 1);
	if (!a) {
		return 0;
	}
	var b = "";
	for (i = 0; i < a.childsCount; i++) {
		if (!b) {
			b = a.childNodes[i].id;
		} else {
			b += this.dlmtr + a.childNodes[i].id;
		}
	}
	return b;
};
dhtmlXTreeObject.prototype._getAllScraggyItems = function(c) {
	var d = "";
	for ( var b = 0; b < c.childsCount; b++) {
		if ((c.childNodes[b].unParsed) || (c.childNodes[b].childsCount > 0)) {
			if (c.childNodes[b].unParsed) {
				var a = this
						._getAllScraggyItemsXML(c.childNodes[b].unParsed, 1);
			} else {
				var a = this._getAllScraggyItems(c.childNodes[b]);
			}
			if (a) {
				if (d) {
					d += this.dlmtr + a;
				} else {
					d = a;
				}
			}
		} else {
			if (!d) {
				d = c.childNodes[b].id;
			} else {
				d += this.dlmtr + c.childNodes[b].id;
			}
		}
	}
	return d;
};
dhtmlXTreeObject.prototype._getAllFatItems = function(c) {
	var d = "";
	for ( var b = 0; b < c.childsCount; b++) {
		if ((c.childNodes[b].unParsed) || (c.childNodes[b].childsCount > 0)) {
			if (!d) {
				d = c.childNodes[b].id;
			} else {
				d += this.dlmtr + c.childNodes[b].id;
			}
			if (c.childNodes[b].unParsed) {
				var a = this._getAllFatItemsXML(c.childNodes[b].unParsed, 1);
			} else {
				var a = this._getAllFatItems(c.childNodes[b]);
			}
			if (a) {
				d += this.dlmtr + a;
			}
		}
	}
	return d;
};
dhtmlXTreeObject.prototype._getAllSubItems = function(f, e, d) {
	if (d) {
		b = d;
	} else {
		var b = this._globalIdStorageFind(f);
	}
	if (!b) {
		return 0;
	}
	e = "";
	for ( var c = 0; c < b.childsCount; c++) {
		if (!e) {
			e = b.childNodes[c].id;
		} else {
			e += this.dlmtr + b.childNodes[c].id;
		}
		var a = this._getAllSubItems(0, e, b.childNodes[c]);
		if (a) {
			e += this.dlmtr + a;
		}
	}
	return e;
};
dhtmlXTreeObject.prototype.selectItem = function(d, c, b) {
	c = convertStringToBoolean(c);
	var a = this._globalIdStorageFind(d);
	if ((!a) || (!a.parentObject)) {
		return 0;
	}
	if (this.XMLloadingWarning) {
		a.parentObject.openMe = 1;
	} else {
		this._openItem(a.parentObject);
	}
	var e = null;
	if (b) {
		e = new Object;
		e.ctrlKey = true;
		if (a.i_sel) {
			e.skipUnSel = true;
		}
	}
	if (c) {
		this.onRowSelect(e,
				a.htmlNode.childNodes[0].childNodes[0].childNodes[3], false);
	} else {
		this.onRowSelect(e,
				a.htmlNode.childNodes[0].childNodes[0].childNodes[3], true);
	}
};
dhtmlXTreeObject.prototype.getSelectedItemText = function() {
	var b = new Array();
	for ( var a = 0; a < this._selected.length; a++) {
		b[a] = this._selected[a].span.innerHTML;
	}
	return (b.join(this.dlmtr));
};
dhtmlXTreeObject.prototype._compressChildList = function(a, c) {
	a--;
	for ( var b = 0; b < a; b++) {
		if (c[b] == 0) {
			c[b] = c[b + 1];
			c[b + 1] = 0;
		}
	}
};
dhtmlXTreeObject.prototype._deleteNode = function(g, e, j) {
	if ((!e) || (!e.parentObject)) {
		return 0;
	}
	var a = 0;
	var b = 0;
	if (e.tr.nextSibling) {
		a = e.tr.nextSibling.nodem;
	}
	if (e.tr.previousSibling) {
		b = e.tr.previousSibling.nodem;
	}
	var f = e.parentObject;
	var c = f.childsCount;
	var h = f.childNodes;
	for ( var d = 0; d < c; d++) {
		if (h[d].id == g) {
			if (!j) {
				f.htmlNode.childNodes[0].removeChild(h[d].tr);
			}
			h[d] = 0;
			break;
		}
	}
	this._compressChildList(c, h);
	if (!j) {
		f.childsCount--;
	}
	if (a) {
		this._correctPlus(a);
		this._correctLine(a);
	}
	if (b) {
		this._correctPlus(b);
		this._correctLine(b);
	}
	if (this.tscheck) {
		this._correctCheckStates(f);
	}
	if (!j) {
		this._globalIdStorageRecSub(e);
	}
};
dhtmlXTreeObject.prototype.setCheck = function(c, b) {
	var a = this._globalIdStorageFind(c, 0, 1);
	if (!a) {
		return;
	}
	if (b === "unsure") {
		this._setCheck(a, b);
	} else {
		b = convertStringToBoolean(b);
		if ((this.tscheck) && (this.smcheck)) {
			this._setSubChecked(b, a);
		} else {
			this._setCheck(a, b);
		}
	}
	if (this.smcheck) {
		this._correctCheckStates(a.parentObject);
	}
};
dhtmlXTreeObject.prototype._setCheck = function(a, c) {
	if (!a) {
		return;
	}
	if (((a.parentObject._r_logic) || (this._frbtr)) && (c)) {
		if (this._frbtrs) {
			if (this._frbtrL) {
				this._setCheck(this._frbtrL, 0);
			}
			this._frbtrL = a;
		} else {
			for ( var b = 0; b < a.parentObject.childsCount; b++) {
				this._setCheck(a.parentObject.childNodes[b], 0);
			}
		}
	}
	var d = a.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0];
	if (c == "unsure") {
		a.checkstate = 2;
	} else {
		if (c) {
			a.checkstate = 1;
		} else {
			a.checkstate = 0;
		}
	}
	if (a.dscheck) {
		a.checkstate = a.dscheck;
	}
	this._setSrc(d, this.imPath
			+ ((a.parentObject._r_logic || this._frbtr) ? this.radioArray
					: this.checkArray)[a.checkstate]);
};
dhtmlXTreeObject.prototype.setSubChecked = function(c, b) {
	var a = this._globalIdStorageFind(c);
	this._setSubChecked(b, a);
	this._correctCheckStates(a.parentObject);
};
dhtmlXTreeObject.prototype._setSubChecked = function(c, a) {
	c = convertStringToBoolean(c);
	if (!a) {
		return;
	}
	if (((a.parentObject._r_logic) || (this._frbtr)) && (c)) {
		for ( var b = 0; b < a.parentObject.childsCount; b++) {
			this._setSubChecked(0, a.parentObject.childNodes[b]);
		}
	}
	if (a._r_logic || this._frbtr) {
		this._setSubChecked(c, a.childNodes[0]);
	} else {
		for ( var b = 0; b < a.childsCount; b++) {
			this._setSubChecked(c, a.childNodes[b]);
		}
	}
	var d = a.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0];
	if (c) {
		a.checkstate = 1;
	} else {
		a.checkstate = 0;
	}
	if (a.dscheck) {
		a.checkstate = a.dscheck;
	}
	this._setSrc(d, this.imPath
			+ ((a.parentObject._r_logic || this._frbtr) ? this.radioArray
					: this.checkArray)[a.checkstate]);
};
dhtmlXTreeObject.prototype.isItemChecked = function(b) {
	var a = this._globalIdStorageFind(b);
	if (!a) {
		return;
	}
	return a.checkstate;
};
dhtmlXTreeObject.prototype.deleteChildItems = function(d) {
	var a = this._globalIdStorageFind(d);
	if (!a) {
		return;
	}
	var b = a.childsCount;
	for ( var c = 0; c < b; c++) {
		this._deleteNode(a.childNodes[0].id, a.childNodes[0]);
	}
};
dhtmlXTreeObject.prototype.deleteItem = function(c, a) {
	if ((!this._onrdlh) || (this._onrdlh(c))) {
		var b = this._deleteItem(c, a);
	}
	this.allTree.childNodes[0].border = "1";
	this.allTree.childNodes[0].border = "0";
};
dhtmlXTreeObject.prototype._deleteItem = function(f, b, e) {
	b = convertStringToBoolean(b);
	var a = this._globalIdStorageFind(f);
	if (!a) {
		return;
	}
	var c = this.getParentId(f);
	var d = a.parentObject;
	this._deleteNode(f, a, e);
	this._correctPlus(d);
	this._correctLine(d);
	if ((b) && (c != this.rootId)) {
		this.selectItem(c, 1);
	}
	return d;
};
dhtmlXTreeObject.prototype._globalIdStorageRecSub = function(a) {
	for ( var b = 0; b < a.childsCount; b++) {
		this._globalIdStorageRecSub(a.childNodes[b]);
		this._globalIdStorageSub(a.childNodes[b].id);
	}
	this._globalIdStorageSub(a.id);
	var c = a;
	c.span = null;
	c.tr.nodem = null;
	c.tr = null;
	c.htmlNode = null;
};
dhtmlXTreeObject.prototype.insertNewNext = function(h, l, k, c, f, e, d, b, a) {
	var g = this._globalIdStorageFind(h);
	if ((!g) || (!g.parentObject)) {
		return (0);
	}
	var j = this._attachChildNode(0, l, k, c, f, e, d, b, a, g);
	return j;
};
dhtmlXTreeObject.prototype.getItemIdByIndex = function(c, a) {
	var b = this._globalIdStorageFind(c);
	if ((!b) || (a > b.childsCount)) {
		return null;
	}
	return b.childNodes[a].id;
};
dhtmlXTreeObject.prototype.getChildItemIdByIndex = function(c, a) {
	var b = this._globalIdStorageFind(c);
	if ((!b) || (a >= b.childsCount)) {
		return null;
	}
	return b.childNodes[a].id;
};
dhtmlXTreeObject.prototype.setDragHandler = function(a) {
	this.attachEvent("onDrag", a);
};
dhtmlXTreeObject.prototype._clearMove = function() {
	if (this._lastMark) {
		this._lastMark.className = this._lastMark.className.replace(
				/dragAndDropRow/g, "");
		this._lastMark = null;
	}
	this.allTree.className = this.allTree.className
			.replace(" selectionBox", "");
};
dhtmlXTreeObject.prototype.enableDragAndDrop = function(b, a) {
	if (b == "temporary_disabled") {
		this.dADTempOff = false;
		b = true;
	} else {
		this.dADTempOff = true;
	}
	this.dragAndDropOff = convertStringToBoolean(b);
	if (this.dragAndDropOff) {
		this.dragger.addDragLanding(this.allTree, this);
	}
	if (arguments.length > 1) {
		this._ddronr = (!convertStringToBoolean(a));
	}
};
dhtmlXTreeObject.prototype._setMove = function(e, c, f) {
	if (e.parentObject.span) {
		var d = getAbsoluteTop(e);
		var b = getAbsoluteTop(this.allTree);
		this.dadmodec = this.dadmode;
		this.dadmodefix = 0;
		var a = e.parentObject.span;
		a.className += " dragAndDropRow";
		this._lastMark = a;
		this._autoScroll(null, d, b);
	}
};
dhtmlXTreeObject.prototype._autoScroll = function(c, b, a) {
	if (this.autoScroll) {
		if (c) {
			b = getAbsoluteTop(c);
			a = getAbsoluteTop(this.allTree);
		}
		if ((b - a - parseInt(this.allTree.scrollTop)) > (parseInt(this.allTree.offsetHeight) - 50)) {
			this.allTree.scrollTop = parseInt(this.allTree.scrollTop) + 20;
		}
		if ((b - a) < (parseInt(this.allTree.scrollTop) + 30)) {
			this.allTree.scrollTop = parseInt(this.allTree.scrollTop) - 20;
		}
	}
};
dhtmlXTreeObject.prototype._createDragNode = function(f, d) {
	if (!this.dADTempOff) {
		return null;
	}
	var c = f.parentObject;
	if (!this.callEvent("onBeforeDrag", [ c.id ])) {
		return null;
	}
	if (!c.i_sel) {
		this._selectItem(c, d);
	}
	var b = document.createElement("div");
	var g = new Array();
	if (this._itim_dg) {
		for ( var a = 0; a < this._selected.length; a++) {
			g[a] = "<table cellspacing='0' cellpadding='0'><tr><td><img width='18px' height='18px' src='"
					+ this
							._getSrc(this._selected[a].span.parentNode.previousSibling.childNodes[0])
					+ "'></td><td>"
					+ this._selected[a].span.innerHTML
					+ "</td></tr><table>";
		}
	} else {
		g = this.getSelectedItemText().split(this.dlmtr);
	}
	b.innerHTML = g.join("");
	b.style.position = "absolute";
	b.className = "dragSpanDiv";
	this._dragged = (new Array()).concat(this._selected);
	return b;
};
dhtmlXTreeObject.prototype._focusNode = function(a) {
	var b = getAbsoluteTop(a.htmlNode) - getAbsoluteTop(this.allTree);
	if ((b > (this.allTree.scrollTop + this.allTree.offsetHeight - 30))
			|| (b < this.allTree.scrollTop)) {
		this.allTree.scrollTop = b;
	}
};
dhtmlXTreeObject.prototype._preventNsDrag = function(a) {
	if ((a) && (a.preventDefault)) {
		a.preventDefault();
		return false;
	}
	return false;
};
dhtmlXTreeObject.prototype._drag = function(g, h, a) {
	if (this._autoOpenTimer) {
		clearTimeout(this._autoOpenTimer);
	}
	if (!a.parentObject) {
		a = this.htmlNode.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0];
		this.dadmodec = 0;
	}
	this._clearMove();
	var f = g.parentObject.treeNod;
	if ((f) && (f._clearMove)) {
		f._clearMove("");
	}
	if ((!this.dragMove) || (this.dragMove())) {
		if ((!f) || (!f._clearMove) || (!f._dragged)) {
			var d = new Array(g.parentObject);
		} else {
			var d = f._dragged;
		}
		var b = a.parentObject;
		for ( var e = 0; e < d.length; e++) {
			var c = this._moveNode(d[e], b);
			if ((this.dadmodec) && (c !== false)) {
				b = this._globalIdStorageFind(c, true, true);
			}
			if ((c) && (!this._sADnD)) {
				this.selectItem(c, 0, 1);
			}
		}
	}
	if (f) {
		f._dragged = new Array();
	}
};
dhtmlXTreeObject.prototype._dragIn = function(f, d, a, h) {
	if (!this.dADTempOff) {
		return 0;
	}
	var g = d.parentObject;
	var b = f.parentObject;
	if ((!b) && (this._ddronr)) {
		return;
	}
	if (!this.callEvent("onDragIn", [ g.id, b ? b.id : null, g.treeNod, this ])) {
		return 0;
	}
	if (!b) {
		this.allTree.className += " selectionBox";
	} else {
		if (g.childNodes == null) {
			this._setMove(f, a, h);
			return f;
		}
		var e = g.treeNod;
		for ( var c = 0; c < e._dragged.length; c++) {
			if (this._checkPNodes(b, e._dragged[c])) {
				this._autoScroll(f);
				return 0;
			}
		}
		this._setMove(f, a, h);
		if (this._getOpenState(b) <= 0) {
			this._autoOpenId = b.id;
			this._autoOpenTimer = window.setTimeout(new callerFunction(
					this._autoOpenItem, this), 1000);
		}
	}
	return f;
};
dhtmlXTreeObject.prototype._autoOpenItem = function(b, a) {
	a.openItem(a._autoOpenId);
};
dhtmlXTreeObject.prototype._dragOut = function(a) {
	this._clearMove();
	if (this._autoOpenTimer) {
		clearTimeout(this._autoOpenTimer);
	}
};
dhtmlXTreeObject.prototype.moveItem = function(h, g, c, b) {
	var a = this._globalIdStorageFind(h);
	if (!a) {
		return (0);
	}
	switch (g) {
	case "right":
		alert("Not supported yet");
		break;
	case "item_child":
		var e = (b || this)._globalIdStorageFind(c);
		if (!e) {
			return (0);
		}
		(b || this)._moveNodeTo(a, e, 0);
		break;
	case "item_sibling":
		var e = (b || this)._globalIdStorageFind(c);
		if (!e) {
			return (0);
		}
		(b || this)._moveNodeTo(a, e.parentObject, e);
		break;
	case "item_sibling_next":
		var e = (b || this)._globalIdStorageFind(c);
		if (!e) {
			return (0);
		}
		if ((e.tr) && (e.tr.nextSibling) && (e.tr.nextSibling.nodem)) {
			(b || this)._moveNodeTo(a, e.parentObject, e.tr.nextSibling.nodem);
		} else {
			(b || this)._moveNodeTo(a, e.parentObject);
		}
		break;
	case "left":
		if (a.parentObject.parentObject) {
			this._moveNodeTo(a, a.parentObject.parentObject, a.parentObject);
		}
		break;
	case "up":
		var f = this._getPrevNode(a);
		if ((f == -1) || (!f.parentObject)) {
			return;
		}
		this._moveNodeTo(a, f.parentObject, f);
		break;
	case "up_strict":
		var f = this._getIndex(a);
		if (f != 0) {
			this._moveNodeTo(a, a.parentObject,
					a.parentObject.childNodes[f - 1]);
		}
		break;
	case "down_strict":
		var f = this._getIndex(a);
		var d = a.parentObject.childsCount - 2;
		if (f == d) {
			this._moveNodeTo(a, a.parentObject);
		} else {
			if (f < d) {
				this._moveNodeTo(a, a.parentObject,
						a.parentObject.childNodes[f + 2]);
			}
		}
		break;
	case "down":
		var f = this._getNextNode(this._lastChild(a));
		if ((f == -1) || (!f.parentObject)) {
			return;
		}
		if (f.parentObject == a.parentObject) {
			var f = this._getNextNode(f);
		}
		if (f == -1) {
			this._moveNodeTo(a, a.parentObject);
		} else {
			if ((f == -1) || (!f.parentObject)) {
				return;
			}
			this._moveNodeTo(a, f.parentObject, f);
		}
		break;
	}
};
dhtmlXTreeObject.prototype._loadDynXML = function(c, b) {
	b = b || this.XMLsource;
	var a = (new Date()).valueOf();
	this._ld_id = c;
	this.loadXML(b + getUrlSymbol(b) + "uid=" + a + "&id=" + this._escape(c));
};
dhtmlXTreeObject.prototype._checkPNodes = function(b, a) {
	if (a == b) {
		return 1;
	}
	if (b.parentObject) {
		return this._checkPNodes(b.parentObject, a);
	} else {
		return 0;
	}
};
dhtmlXTreeObject.prototype.preventIECaching = function(a) {
	this.no_cashe = convertStringToBoolean(a);
	this.XMLLoader.rSeed = this.no_cashe;
};
dhtmlXTreeObject.prototype.preventIECashing = dhtmlXTreeObject.prototype.preventIECaching;
dhtmlXTreeObject.prototype.disableCheckbox = function(c, b) {
	if (typeof (c) != "object") {
		var a = this._globalIdStorageFind(c, 0, 1);
	} else {
		var a = c;
	}
	if (!a) {
		return;
	}
	a.dscheck = convertStringToBoolean(b) ? (((a.checkstate || 0) % 3) + 3)
			: ((a.checkstate > 2) ? (a.checkstate - 3) : a.checkstate);
	this._setCheck(a);
	if (a.dscheck < 3) {
		a.dscheck = false;
	}
};
dhtmlXTreeObject.prototype.setEscapingMode = function(a) {
	this.utfesc = a;
};
dhtmlXTreeObject.prototype.enableHighlighting = function(a) {
	this.ehlt = true;
	this.ehlta = convertStringToBoolean(a);
};
dhtmlXTreeObject.prototype._itemMouseOut = function() {
	var b = this.childNodes[3].parentObject;
	var a = b.treeNod;
	a.callEvent("onMouseOut", [ b.id ]);
	if (b.id == a._l_onMSI) {
		a._l_onMSI = null;
	}
	if (!a.ehlta) {
		return;
	}
	b.span.className = b.span.className.replace("_lor", "");
};
dhtmlXTreeObject.prototype._itemMouseIn = function() {
	var b = this.childNodes[3].parentObject;
	var a = b.treeNod;
	if (a._l_onMSI != b.id) {
		a.callEvent("onMouseIn", [ b.id ]);
	}
	a._l_onMSI = b.id;
	if (!a.ehlta) {
		return;
	}
	b.span.className = b.span.className.replace("_lor", "");
	b.span.className = b.span.className.replace(/((standart|selected)TreeRow)/,
			"$1_lor");
};
dhtmlXTreeObject.prototype.enableActiveImages = function(a) {
	this._aimgs = convertStringToBoolean(a);
};
dhtmlXTreeObject.prototype.focusItem = function(b) {
	var a = this._globalIdStorageFind(b);
	if (!a) {
		return (0);
	}
	this._focusNode(a);
};
dhtmlXTreeObject.prototype.getAllSubItems = function(a) {
	return this._getAllSubItems(a);
};
dhtmlXTreeObject.prototype.getAllChildless = function() {
	return this._getAllScraggyItems(this.htmlNode);
};
dhtmlXTreeObject.prototype.getAllLeafs = dhtmlXTreeObject.prototype.getAllChildless;
dhtmlXTreeObject.prototype._getAllScraggyItems = function(c) {
	var d = "";
	for ( var b = 0; b < c.childsCount; b++) {
		if ((c.childNodes[b].unParsed) || (c.childNodes[b].childsCount > 0)) {
			if (c.childNodes[b].unParsed) {
				var a = this
						._getAllScraggyItemsXML(c.childNodes[b].unParsed, 1);
			} else {
				var a = this._getAllScraggyItems(c.childNodes[b]);
			}
			if (a) {
				if (d) {
					d += this.dlmtr + a;
				} else {
					d = a;
				}
			}
		} else {
			if (!d) {
				d = c.childNodes[b].id;
			} else {
				d += this.dlmtr + c.childNodes[b].id;
			}
		}
	}
	return d;
};
dhtmlXTreeObject.prototype._getAllFatItems = function(c) {
	var d = "";
	for ( var b = 0; b < c.childsCount; b++) {
		if ((c.childNodes[b].unParsed) || (c.childNodes[b].childsCount > 0)) {
			if (!d) {
				d = c.childNodes[b].id;
			} else {
				d += this.dlmtr + c.childNodes[b].id;
			}
			if (c.childNodes[b].unParsed) {
				var a = this._getAllFatItemsXML(c.childNodes[b].unParsed, 1);
			} else {
				var a = this._getAllFatItems(c.childNodes[b]);
			}
			if (a) {
				d += this.dlmtr + a;
			}
		}
	}
	return d;
};
dhtmlXTreeObject.prototype.getAllItemsWithKids = function() {
	return this._getAllFatItems(this.htmlNode);
};
dhtmlXTreeObject.prototype.getAllFatItems = dhtmlXTreeObject.prototype.getAllItemsWithKids;
dhtmlXTreeObject.prototype.getAllChecked = function() {
	return this._getAllChecked("", "", 1);
};
dhtmlXTreeObject.prototype.getAllUnchecked = function(a) {
	if (a) {
		a = this._globalIdStorageFind(a);
	}
	return this._getAllChecked(a, "", 0);
};
dhtmlXTreeObject.prototype.getAllPartiallyChecked = function() {
	return this._getAllChecked("", "", 2);
};
dhtmlXTreeObject.prototype.getAllCheckedBranches = function() {
	var a = this._getAllChecked("", "", 1);
	if (a != "") {
		a += this.dlmtr;
	}
	return a + this._getAllChecked("", "", 2);
};
dhtmlXTreeObject.prototype._getAllChecked = function(d, c, e) {
	if (!d) {
		d = this.htmlNode;
	}
	if (d.checkstate == e) {
		if (!d.nocheckbox) {
			if (c) {
				c += this.dlmtr + d.id;
			} else {
				c = d.id;
			}
		}
	}
	var a = d.childsCount;
	for ( var b = 0; b < a; b++) {
		c = this._getAllChecked(d.childNodes[b], c, e);
	}
	if (c) {
		return c;
	} else {
		return "";
	}
};
dhtmlXTreeObject.prototype.setItemStyle = function(c, b) {
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return 0;
	}
	if (!a.span.style.cssText) {
		a.span.setAttribute("style", a.span.getAttribute("style") + ";" + b);
	} else {
		a.span.style.cssText += (";" + b);
	}
};
dhtmlXTreeObject.prototype.enableImageDrag = function(a) {
	this._itim_dg = convertStringToBoolean(a);
};
dhtmlXTreeObject.prototype.setOnDragIn = function(a) {
	this.attachEvent("onDragIn", a);
};
dhtmlXTreeObject.prototype.enableDragAndDropScrolling = function(a) {
	this.autoScroll = convertStringToBoolean(a);
};
dhtmlXTreeObject.prototype.dhx_Event = function() {
	this.dhx_SeverCatcherPath = "";
	this.attachEvent = function(original, catcher, CallObj) {
		if (this._onEventSet && this._onEventSet[original]) {
			this._onEventSet[original].apply(this, []);
		}
		CallObj = CallObj || this;
		original = "ev_" + original;
		if ((!this[original]) || (!this[original].addEvent)) {
			var z = new this.eventCatcher(CallObj);
			z.addEvent(this[original]);
			this[original] = z;
		}
		return (original + ":" + this[original].addEvent(catcher));
	};
	this.callEvent = function(name, a) {
		if (this["ev_" + name]) {
			return this["ev_" + name].apply(this, a);
		}
		return true;
	};
	this.checkEvent = function(name) {
		if (this["ev_" + name]) {
			return true;
		}
		return false;
	};
	this.eventCatcher = function(obj) {
		var dhx_catch = new Array();
		var m_obj = obj;
		var func_server = function(catcher, rpc) {
			catcher = catcher.split(":");
			var postVar = "";
			var postVar2 = "";
			var target = catcher[1];
			if (catcher[1] == "rpc") {
				postVar = '<?xml version="1.0"?><methodCall><methodName>'
						+ catcher[2] + "</methodName><params>";
				postVar2 = "</params></methodCall>";
				target = rpc;
			}
			var z = function() {
				var loader = new dtmlXMLLoaderObject(null, window, false);
				var request = postVar;
				if (postVar2) {
					for ( var i = 0; i < arguments.length; i++) {
						request += "<param><value><string>"
								+ (arguments[i] ? arguments[i].toString() : "")
								+ "</string></value></param>";
					}
					request += postVar2;
				} else {
					for ( var i = 0; i < arguments.length; i++) {
						request += ("&arg" + i + "=" + escape(arguments[i]));
					}
				}
				loader.loadXML(target, true, request, postVar2 ? true : false);
				try {
					if (postVar2) {
						var dt = loader
								.doXPath("//methodResponse/params/param/value/string");
						return convertStringToBoolean(dt[0].firstChild.data);
					} else {
						return convertStringToBoolean(loader.xmlDoc.responseText);
					}
				} catch (e) {
					dhtmlxError.throwError("rpcError",
							loader.xmlDoc.responseText);
					return false;
				}
			};
			return z;
		};
		var z = function() {
			if (dhx_catch) {
				var res = true;
			}
			for ( var i = 0; i < dhx_catch.length; i++) {
				if (dhx_catch[i] != null) {
					var zr = dhx_catch[i].apply(m_obj, arguments);
					res = res && zr;
				}
			}
			return res;
		};
		z.addEvent = function(ev) {
			if (typeof (ev) != "function") {
				if (ev && ev.indexOf && ev.indexOf("server:") === 0) {
					ev = new func_server(ev, m_obj.rpcServer);
				} else {
					ev = eval(ev);
				}
			}
			if (ev) {
				return dhx_catch.push(ev) - 1;
			}
			return false;
		};
		z.removeEvent = function(id) {
			dhx_catch[id] = null;
		};
		return z;
	};
	this.detachEvent = function(id) {
		if (id != false) {
			var list = id.split(":");
			this[list[0]].removeEvent(list[1]);
		}
	};
};