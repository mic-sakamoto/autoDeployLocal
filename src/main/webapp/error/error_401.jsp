<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*,java.text.*" %>
<%
   Date date = new Date( );
   SimpleDateFormat df = new SimpleDateFormat ("yyyyMMddHHmmss");
%>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
</head>
<link rel="shortcut icon" href="./favicon.ico">

<link href="./core/plugins/bootstrap/bootstrap-theme.css" rel="stylesheet">
<link href="./core/plugins/bootstrap/bootstrap.css?t=<%= df.format(date) %>" rel="stylesheet">
<link href="./core/common/common.css?t=<%= df.format(date) %>" rel="stylesheet">
<link href="./core/member/member.css?t=<%= df.format(date) %>" rel="stylesheet">
<link href="./error/error.css?t=<%= df.format(date) %>" rel="stylesheet">

<html>

<body>
	<nav class="navbar">
		<div class="header-container">
			<div id="header" class="pull-right">

			</div>
		</div>
	</nav>

	<div class="container ">


		<div class="error-text">
			<p class="c-error-text">お探しのページは<br>見つかりませんでした</p>
			<p class="c-error-error-code">（CODE：401）</p>
		</div>

		<div class="button-panel">
			<button type="button" id="loginBtn"	class="btn btn-action btn-block" onclick="location.href='./'">
				TOP
			</button>
		</div>

	</div>

<footer class="footer">

</footer>

</body>

</html>