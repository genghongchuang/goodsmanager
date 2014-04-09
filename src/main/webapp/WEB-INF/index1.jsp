<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<title>业务基础平台</title>
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
<!-- <iframe id="contentIframe" name="contentIframe" style="width: 100%;height: 100%"></iframe> -->
	<div id="loading-tip" style="border-radius: 3px; position: absolute; z-index: 1; border: solid 1px #ccc; background-color: #fff; padding: 10px;">
		<div class="loading-indicator" style="color: #444; font: bold 13px tahoma, arial, helvetica; padding: 10px; height: auto;">
			<img src="${ctx}/images/loading32.gif" width="31" height="31"
				style="margin-right: 8px; float: left; vertical-align: top;" />
			货物管理平台V1.0 <br /> <span id="loading-msg"
				style="font: normal 10px arial, tahoma, sans-serif;">加载样式和图片...</span>
		</div>
	</div>
	<script type="text/javascript">
	        var webCtx="${ctx}";
			var tip = document.getElementById("loading-tip");
			tip.style.top = (document.body.scrollHeight - tip.style.top - 100) / 2;
			tip.style.left = (document.body.scrollWidth - 200) / 2;
		</script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/icon.css" />
		<script type="text/javascript">
			document.getElementById("loading-msg").innerHTML = "加载核心组件...";
		</script>
		<script type="text/javascript" src="${ctx}/js/app.js"></script>
</body>
</html>