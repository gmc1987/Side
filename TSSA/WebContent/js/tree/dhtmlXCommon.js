function dtmlXMLLoaderObject(b, d, c, a) {
	this.xmlDoc = "";
	if (typeof (c) != "undefined") {
		this.async = c;
	} else {
		this.async = true;
	}
	this.onloadAction = b || null;
	this.mainObject = d || null;
	this.waitCall = null;
	this.rSeed = a || false;
	return this;
}
dtmlXMLLoaderObject.prototype.waitLoadFunction = function(b) {
	var a = true;
	this.check = function() {
		if ((b) && (b.onloadAction != null)) {
			if ((!b.xmlDoc.readyState) || (b.xmlDoc.readyState == 4)) {
				if (!a) {
					return;
				}
				a = false;
				b.onloadAction(b.mainObject, null, null, null, b);
				if (b.waitCall) {
					b.waitCall();
					b.waitCall = null;
				}
			}
		}
	};
	return this.check;
};
dtmlXMLLoaderObject.prototype.getXMLTopNode = function(c, a) {
	if (this.xmlDoc.responseXML) {
		var b = this.xmlDoc.responseXML.getElementsByTagName(c);
		var e = b[0];
	} else {
		var e = this.xmlDoc.documentElement;
	}
	if (e) {
		this._retry = false;
		return e;
	}
	if ((_isIE) && (!this._retry)) {
		var d = this.xmlDoc.responseText;
		var a = this.xmlDoc;
		this._retry = true;
		this.xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		this.xmlDoc.async = false;
		this.xmlDoc["loadXM" + "L"](d);
		return this.getXMLTopNode(c, a);
	}
	dhtmlxError.throwError("LoadXML", "Incorrect XML", [ (a || this.xmlDoc),
			this.mainObject ]);
	return document.createElement("DIV");
};
dtmlXMLLoaderObject.prototype.loadXMLString = function(b) {
	try {
		var c = new DOMParser();
		this.xmlDoc = c.parseFromString(b, "text/xml");
	} catch (a) {
		this.xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		this.xmlDoc.async = this.async;
		this.xmlDoc["loadXM" + "L"](b);
	}
	this.onloadAction(this.mainObject, null, null, null, this);
	if (this.waitCall) {
		this.waitCall();
		this.waitCall = null;
	}
};
dtmlXMLLoaderObject.prototype.loadXML = function(c, b, a, d) {
	if (this.rSeed) {
		c += ((c.indexOf("?") != -1) ? "&" : "?") + "a_dhx_rSeed="
				+ (new Date()).valueOf();
	}
	this.filePath = c;
	if ((!_isIE) && (window.XMLHttpRequest)) {
		this.xmlDoc = new XMLHttpRequest();
	} else {
		if (document.implementation && document.implementation.createDocument) {
			this.xmlDoc = document.implementation.createDocument("", "", null);
			this.xmlDoc.onload = new this.waitLoadFunction(this);
			this.xmlDoc.load(c);
			return;
		} else {
			this.xmlDoc = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	if (this.async) {
		this.xmlDoc.onreadystatechange = new this.waitLoadFunction(this);
	}
	this.xmlDoc.open(b ? "POST" : "GET", c, this.async);
	if (d) {
		this.xmlDoc.setRequestHeader("User-Agent", "dhtmlxRPC v0.1 ("
				+ navigator.userAgent + ")");
		this.xmlDoc.setRequestHeader("Content-type", "text/xml");
	} else {
		if (b) {
			this.xmlDoc.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
		}
	}
	this.xmlDoc.send(null || a);
	if (!this.async) {
		(new this.waitLoadFunction(this))();
	}
};
dtmlXMLLoaderObject.prototype.destructor = function() {
	this.onloadAction = null;
	this.mainObject = null;
	this.xmlDoc = null;
	return null;
};
function callerFunction(a, b) {
	this.handler = function(c) {
		if (!c) {
			c = window.event;
		}
		a(c, b);
		return true;
	};
	return this.handler;
}
function getAbsoluteLeft(b) {
	var c = b.offsetLeft;
	var a = b.offsetParent;
	while (a != null) {
		c += a.offsetLeft;
		a = a.offsetParent;
	}
	return c;
}
function getAbsoluteTop(c) {
	var b = c.offsetTop;
	var a = c.offsetParent;
	while (a != null) {
		b += a.offsetTop;
		a = a.offsetParent;
	}
	return b;
}
function convertStringToBoolean(a) {
	if (typeof (a) == "string") {
		a = a.toLowerCase();
	}
	switch (a) {
	case "1":
	case "true":
	case "yes":
	case "y":
	case 1:
	case true:
		return true;
		break;
	default:
		return false;
	}
}
function getUrlSymbol(a) {
	if (a.indexOf("?") != -1) {
		return "&";
	} else {
		return "?";
	}
}
function dhtmlDragAndDropObject() {
	if (window.dhtmlDragAndDrop) {
		return window.dhtmlDragAndDrop;
	}
	this.lastLanding = 0;
	this.dragNode = 0;
	this.dragStartNode = 0;
	this.dragStartObject = 0;
	this.tempDOMU = null;
	this.tempDOMM = null;
	this.waitDrag = 0;
	window.dhtmlDragAndDrop = this;
	return this;
}
dhtmlDragAndDropObject.prototype.removeDraggableItem = function(a) {
	a.onmousedown = null;
	a.dragStarter = null;
	a.dragLanding = null;
};
dhtmlDragAndDropObject.prototype.addDraggableItem = function(a, b) {
	a.onmousedown = this.preCreateDragCopy;
	a.dragStarter = b;
	this.addDragLanding(a, b);
};
dhtmlDragAndDropObject.prototype.addDragLanding = function(a, b) {
	a.dragLanding = b;
};
dhtmlDragAndDropObject.prototype.preCreateDragCopy = function(a) {
	if (a && (a || event).button == 2) {
		return;
	}
	if (window.dhtmlDragAndDrop.waitDrag) {
		window.dhtmlDragAndDrop.waitDrag = 0;
		document.body.onmouseup = window.dhtmlDragAndDrop.tempDOMU;
		document.body.onmousemove = window.dhtmlDragAndDrop.tempDOMM;
		return false;
	}
	window.dhtmlDragAndDrop.waitDrag = 1;
	window.dhtmlDragAndDrop.tempDOMU = document.body.onmouseup;
	window.dhtmlDragAndDrop.tempDOMM = document.body.onmousemove;
	window.dhtmlDragAndDrop.dragStartNode = this;
	window.dhtmlDragAndDrop.dragStartObject = this.dragStarter;
	document.body.onmouseup = window.dhtmlDragAndDrop.preCreateDragCopy;
	document.body.onmousemove = window.dhtmlDragAndDrop.callDrag;
	if ((a) && (a.preventDefault)) {
		a.preventDefault();
		return false;
	}
	return false;
};
dhtmlDragAndDropObject.prototype.callDrag = function(c) {
	if (!c) {
		c = window.event;
	}
	dragger = window.dhtmlDragAndDrop;
	if ((c.button == 0) && (_isIE)) {
		return dragger.stopDrag();
	}
	if (!dragger.dragNode && dragger.waitDrag) {
		dragger.dragNode = dragger.dragStartObject._createDragNode(
				dragger.dragStartNode, c);
		if (!dragger.dragNode) {
			return dragger.stopDrag();
		}
		dragger.gldragNode = dragger.dragNode;
		document.body.appendChild(dragger.dragNode);
		document.body.onmouseup = dragger.stopDrag;
		dragger.waitDrag = 0;
		dragger.dragNode.pWindow = window;
		dragger.initFrameRoute();
	}
	if (dragger.dragNode.parentNode != window.document.body) {
		var a = dragger.gldragNode;
		if (dragger.gldragNode.old) {
			a = dragger.gldragNode.old;
		}
		a.parentNode.removeChild(a);
		var b = dragger.dragNode.pWindow;
		if (_isIE) {
			var f = document.createElement("Div");
			f.innerHTML = dragger.dragNode.outerHTML;
			dragger.dragNode = f.childNodes[0];
		} else {
			dragger.dragNode = dragger.dragNode.cloneNode(true);
		}
		dragger.dragNode.pWindow = window;
		dragger.gldragNode.old = dragger.dragNode;
		document.body.appendChild(dragger.dragNode);
		b.dhtmlDragAndDrop.dragNode = dragger.dragNode;
	}
	dragger.dragNode.style.left = c.clientX + 15
			+ (dragger.fx ? dragger.fx * (-1) : 0)
			+ (document.body.scrollLeft || document.documentElement.scrollLeft)
			+ "px";
	dragger.dragNode.style.top = c.clientY + 3
			+ (dragger.fy ? dragger.fy * (-1) : 0)
			+ (document.body.scrollTop || document.documentElement.scrollTop)
			+ "px";
	if (!c.srcElement) {
		var d = c.target;
	} else {
		d = c.srcElement;
	}
	dragger.checkLanding(d, c);
};
dhtmlDragAndDropObject.prototype.calculateFramePosition = function(e) {
	if (window.name) {
		var c = parent.frames[window.name].frameElement.offsetParent;
		var d = 0;
		var b = 0;
		while (c) {
			d += c.offsetLeft;
			b += c.offsetTop;
			c = c.offsetParent;
		}
		if ((parent.dhtmlDragAndDrop)) {
			var a = parent.dhtmlDragAndDrop.calculateFramePosition(1);
			d += a.split("_")[0] * 1;
			b += a.split("_")[1] * 1;
		}
		if (e) {
			return d + "_" + b;
		} else {
			this.fx = d;
		}
		this.fy = b;
	}
	return "0_0";
};
dhtmlDragAndDropObject.prototype.checkLanding = function(b, a) {
	if ((b) && (b.dragLanding)) {
		if (this.lastLanding) {
			this.lastLanding.dragLanding._dragOut(this.lastLanding);
		}
		this.lastLanding = b;
		this.lastLanding = this.lastLanding.dragLanding._dragIn(
				this.lastLanding, this.dragStartNode, a.clientX, a.clientY, a);
		this.lastLanding_scr = (_isIE ? a.srcElement : a.target);
	} else {
		if ((b) && (b.tagName != "BODY")) {
			this.checkLanding(b.parentNode, a);
		} else {
			if (this.lastLanding) {
				this.lastLanding.dragLanding._dragOut(this.lastLanding,
						a.clientX, a.clientY, a);
			}
			this.lastLanding = 0;
			if (this._onNotFound) {
				this._onNotFound();
			}
		}
	}
};
dhtmlDragAndDropObject.prototype.stopDrag = function(b, c) {
	dragger = window.dhtmlDragAndDrop;
	if (!c) {
		dragger.stopFrameRoute();
		var a = dragger.lastLanding;
		dragger.lastLanding = null;
		if (a) {
			a.dragLanding._drag(dragger.dragStartNode, dragger.dragStartObject,
					a, (_isIE ? event.srcElement : b.target));
		}
	}
	dragger.lastLanding = null;
	if ((dragger.dragNode) && (dragger.dragNode.parentNode == document.body)) {
		dragger.dragNode.parentNode.removeChild(dragger.dragNode);
	}
	dragger.dragNode = 0;
	dragger.gldragNode = 0;
	dragger.fx = 0;
	dragger.fy = 0;
	dragger.dragStartNode = 0;
	dragger.dragStartObject = 0;
	document.body.onmouseup = dragger.tempDOMU;
	document.body.onmousemove = dragger.tempDOMM;
	dragger.tempDOMU = null;
	dragger.tempDOMM = null;
	dragger.waitDrag = 0;
};
dhtmlDragAndDropObject.prototype.stopFrameRoute = function(b) {
	if (b) {
		window.dhtmlDragAndDrop.stopDrag(1, 1);
	}
	for ( var a = 0; a < window.frames.length; a++) {
		if ((window.frames[a] != b) && (window.frames[a].dhtmlDragAndDrop)) {
			window.frames[a].dhtmlDragAndDrop.stopFrameRoute(window);
		}
	}
	if ((parent.dhtmlDragAndDrop) && (parent != window) && (parent != b)) {
		parent.dhtmlDragAndDrop.stopFrameRoute(window);
	}
};
dhtmlDragAndDropObject.prototype.initFrameRoute = function(b, c) {
	if (b) {
		window.dhtmlDragAndDrop.preCreateDragCopy();
		window.dhtmlDragAndDrop.dragStartNode = b.dhtmlDragAndDrop.dragStartNode;
		window.dhtmlDragAndDrop.dragStartObject = b.dhtmlDragAndDrop.dragStartObject;
		window.dhtmlDragAndDrop.dragNode = b.dhtmlDragAndDrop.dragNode;
		window.dhtmlDragAndDrop.gldragNode = b.dhtmlDragAndDrop.dragNode;
		window.document.body.onmouseup = window.dhtmlDragAndDrop.stopDrag;
		window.waitDrag = 0;
		if (((!_isIE) && (c)) && ((!_isFF) || (_FFrv < 1.8))) {
			window.dhtmlDragAndDrop.calculateFramePosition();
		}
	}
	if ((parent.dhtmlDragAndDrop) && (parent != window) && (parent != b)) {
		parent.dhtmlDragAndDrop.initFrameRoute(window);
	}
	for ( var a = 0; a < window.frames.length; a++) {
		if ((window.frames[a] != b) && (window.frames[a].dhtmlDragAndDrop)) {
			window.frames[a].dhtmlDragAndDrop.initFrameRoute(window,
					((!b || c) ? 1 : 0));
		}
	}
};
var _isFF = false;
var _isIE = false;
var _isOpera = false;
var _isKHTML = false;
var _isMacOS = false;
if (navigator.userAgent.indexOf("Macintosh") != -1) {
	_isMacOS = true;
}
if ((navigator.userAgent.indexOf("Safari") != -1)
		|| (navigator.userAgent.indexOf("Konqueror") != -1)) {
	var _KHTMLrv = parseFloat(navigator.userAgent.substr(navigator.userAgent
			.indexOf("Safari") + 7, 5));
	if (_KHTMLrv > 525) {
		_isFF = true;
		var _FFrv = 1.9;
	} else {
		_isKHTML = true;
	}
} else {
	if (navigator.userAgent.indexOf("Opera") != -1) {
		_isOpera = true;
		_OperaRv = parseFloat(navigator.userAgent.substr(navigator.userAgent
				.indexOf("Opera") + 6, 3));
	} else {
		if (navigator.appName.indexOf("Microsoft") != -1) {
			_isIE = true;
		} else {
			_isFF = true;
			var _FFrv = parseFloat(navigator.userAgent.split("rv:")[1]);
		}
	}
}
function isIE() {
	if (navigator.appName.indexOf("Microsoft") != -1) {
		if (navigator.userAgent.indexOf("Opera") == -1) {
			return true;
		}
	}
	return false;
}
dtmlXMLLoaderObject.prototype.doXPath = function(c, e, d, i) {
	if ((_isKHTML)) {
		return this.doXPathOpera(c, e);
	}
	if (_isIE) {
		if (!e) {
			if (!this.xmlDoc.nodeName) {
				e = this.xmlDoc.responseXML;
			} else {
				e = this.xmlDoc;
			}
		}
		if (!e) {
			dhtmlxError.throwError("LoadXML", "Incorrect XML", [
					(e || this.xmlDoc), this.mainObject ]);
		}
		if (d != null) {
			e.setProperty("SelectionNamespaces", "xmlns:xsl='" + d + "'");
		}
		if (i == "single") {
			return e.selectSingleNode(c);
		} else {
			return e.selectNodes(c) || new Array(0);
		}
	} else {
		var a = e;
		if (!e) {
			if (!this.xmlDoc.nodeName) {
				e = this.xmlDoc.responseXML;
			} else {
				e = this.xmlDoc;
			}
		}
		if (!e) {
			dhtmlxError.throwError("LoadXML", "Incorrect XML", [
					(e || this.xmlDoc), this.mainObject ]);
		}
		if (e.nodeName.indexOf("document") != -1) {
			a = e;
		} else {
			a = e;
			e = e.ownerDocument;
		}
		var g = XPathResult.ANY_TYPE;
		if (i == "single") {
			g = XPathResult.FIRST_ORDERED_NODE_TYPE;
		}
		var f = new Array();
		var b = e.evaluate(c, a, function(j) {
			return d;
		}, g, null);
		if (g == XPathResult.FIRST_ORDERED_NODE_TYPE) {
			return b.singleNodeValue;
		}
		var h = b.iterateNext();
		while (h) {
			f[f.length] = h;
			h = b.iterateNext();
		}
		return f;
	}
};
function _dhtmlxError(b, a, c) {
	if (!this.catches) {
		this.catches = new Array();
	}
	return this;
}
_dhtmlxError.prototype.catchError = function(b, a) {
	this.catches[b] = a;
};
_dhtmlxError.prototype.throwError = function(b, a, c) {
	if (this.catches[b]) {
		return this.catches[b](b, a, c);
	}
	if (this.catches["ALL"]) {
		return this.catches["ALL"](b, a, c);
	}
	alert("Error type: " + arguments[0] + "\nDescription: " + arguments[1]);
	return null;
};
window.dhtmlxError = new _dhtmlxError();
dtmlXMLLoaderObject.prototype.doXPathOpera = function(c, a) {
	var e = c.replace(/[\/]+/gi, "/").split("/");
	var d = null;
	var b = 1;
	if (!e.length) {
		return [];
	}
	if (e[0] == ".") {
		d = [ a ];
	} else {
		if (e[0] == "") {
			d = (this.xmlDoc.responseXML || this.xmlDoc)
					.getElementsByTagName(e[b].replace(/\[[^\]]*\]/g, ""));
			b++;
		} else {
			return [];
		}
	}
	for (b; b < e.length; b++) {
		d = this._getAllNamedChilds(d, e[b]);
	}
	if (e[b - 1].indexOf("[") != -1) {
		d = this._filterXPath(d, e[b - 1]);
	}
	return d;
};
dtmlXMLLoaderObject.prototype._filterXPath = function(e, d) {
	var g = new Array();
	var d = d.replace(/[^\[]*\[\@/g, "").replace(/[\[\]\@]*/g, "");
	for ( var f = 0; f < e.length; f++) {
		if (e[f].getAttribute(d)) {
			g[g.length] = e[f];
		}
	}
	return g;
};
dtmlXMLLoaderObject.prototype._getAllNamedChilds = function(e, d) {
	var h = new Array();
	if (_isKHTML) {
		d = d.toUpperCase();
	}
	for ( var g = 0; g < e.length; g++) {
		for ( var f = 0; f < e[g].childNodes.length; f++) {
			if (_isKHTML) {
				if (e[g].childNodes[f].tagName
						&& e[g].childNodes[f].tagName.toUpperCase() == d) {
					h[h.length] = e[g].childNodes[f];
				}
			} else {
				if (e[g].childNodes[f].tagName == d) {
					h[h.length] = e[g].childNodes[f];
				}
			}
		}
	}
	return h;
};
function dhtmlXHeir(e, d) {
	for ( var f in d) {
		if (typeof (d[f]) == "function") {
			e[f] = d[f];
		}
	}
	return e;
}
function dhtmlxEvent(b, c, a) {
	if (b.addEventListener) {
		b.addEventListener(c, a, false);
	} else {
		if (b.attachEvent) {
			b.attachEvent("on" + c, a);
		}
	}
}
dtmlXMLLoaderObject.prototype.xslDoc = null;
dtmlXMLLoaderObject.prototype.setXSLParamValue = function(b, c, d) {
	if (!d) {
		d = this.xslDoc;
	}
	if (d.responseXML) {
		d = d.responseXML;
	}
	var a = this.doXPath("/xsl:stylesheet/xsl:variable[@name='" + b + "']", d,
			"http://www.w3.org/1999/XSL/Transform", "single");
	if (a != null) {
		a.firstChild.nodeValue = c;
	}
};
dtmlXMLLoaderObject.prototype.doXSLTransToObject = function(c, b) {
	if (!c) {
		c = this.xslDoc;
	}
	if (c.responseXML) {
		c = c.responseXML;
	}
	if (!b) {
		b = this.xmlDoc;
	}
	if (b.responseXML) {
		b = b.responseXML;
	}
	if (!isIE()) {
		if (!this.XSLProcessor) {
			this.XSLProcessor = new XSLTProcessor();
			this.XSLProcessor.importStylesheet(c);
		}
		var a = this.XSLProcessor.transformToDocument(b);
	} else {
		var a = new ActiveXObject("Msxml2.DOMDocument.3.0");
		b.transformNodeToObject(c, a);
	}
	return a;
};
dtmlXMLLoaderObject.prototype.doXSLTransToString = function(b, a) {
	return this.doSerialization(this.doXSLTransToObject(b, a));
};
dtmlXMLLoaderObject.prototype.doSerialization = function(b) {
	if (!isIE()) {
		var a = new XMLSerializer();
		return a.serializeToString(b);
	} else {
		return b.xml;
	}
};