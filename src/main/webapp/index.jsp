<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
<%-- <jsp:forward page="login.action"></jsp:forward> --%>
<script>

window.onload = function(){

	var form = document.createElement("form");
	document.body.appendChild(form);

	form.setAttribute('action','login');
	form.setAttribute('method','post');
	form.submit();
};

</script>

</head>
<body>

</body>
</html>
