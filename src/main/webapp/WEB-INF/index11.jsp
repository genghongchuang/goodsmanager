<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String extLibPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ext4";
	String ctx = request.getContextPath();
	
	pageContext.setAttribute("extLibPath", extLibPath);
	//pageContext.setAttribute("ctx", ctx);
%>
<html>
	<head>
		<title>业务基础平台</title>
		<c:set var="ctx" value="${pageContext.request.contextPath}"/>
		<style type="text/css">
			.x-panel-ghost {
			    z-index: 1;
			}
			.x-border-layout-ct {
			    background: #DFE8F6;
			}
			.x-portal-body {
			    padding: 0 0 0 8px;
			}
			.x-portal .x-portal-column {
			    padding: 8px 8px 0 0;
			}
			.x-portal .x-panel-dd-spacer {
			    border: 2px dashed #99bbe8;
			    background: #f6f6f6;
			    border-radius: 4px;
			    -moz-border-radius: 4px;
			    margin-bottom: 10px;
			}
			.x-portlet {
			    margin-bottom:10px;
			    padding: 1px;
			}
			.x-portlet .x-panel-body {
			    background: #fff;
			}
			.portlet-content {
			    padding: 10px;
			    font-size: 11px;
			}
		</style>
	</head>
	<body>
	    <iframe id="contentIframe" name="contentIframe" style="width: 100%;height: 100%"></iframe>
		<%-- <div id="loading-tip" style="border-radius:3px;position: absolute;z-index: 1;border: solid 1px #ccc;background-color: #fff;padding: 10px;">
			<div class="loading-indicator" style="color: #444;font: bold 13px tahoma, arial, helvetica;padding: 10px;height: auto;">
				<img src="${ctx}/images/loading32.gif" width="31" height="31"
					style="margin-right: 8px; float: left; vertical-align: top;" />
				业务基础平台V122.0
				<br />
				<span id="loading-msg" style="font: normal 10px arial, tahoma, sans-serif;">加载样式和图片...</span>
			</div>
		</div> --%>
		<script type="text/javascript">
			var extLibPath = "${extLibPath}";
			var ctx = "${ctx}";
			 var webCtx="${ctx}";
			var tip = document.getElementById("loading-tip");
			
			tip.style.top = (document.body.scrollHeight - tip.style.top - 100) / 2;
			tip.style.left = (document.body.scrollWidth - 200) / 2;
		</script>
		<link rel="stylesheet" type="text/css"
	href="${ctx }/js/extjs/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/icon.css" />
		<script type="text/javascript">
			document.getElementById("loading-msg").innerHTML = "加载核心组件...";
		</script>
		<script type="text/javascript" src="${ctx }/js/extjs/bootstrap.js"></script>
<script type="text/javascript"
	src="${ctx }/js/extjs/locale/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${ctx}/js/app.js"></script>
	</body>
</html>
