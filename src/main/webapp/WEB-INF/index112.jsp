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
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
var webCtx='${ctx}';
</script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/extjs/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/icon.css" />
		
		<script type="text/javascript" src="${ctx}/js/extjs/ext-all-debug.js"></script>
<script type="text/javascript"
	src="${ctx}/js/extjs/locale/ext-lang-zh_CN.js"></script>
	
</head>
<body>
<div><script type="text/javascript" src="${ctx}/js/manager.js"></script> </div>
<!-- <iframe id="contentIframe" name="contentIframe" style="width: 100%;height: 100%"></iframe> -->

</body>

</html>
