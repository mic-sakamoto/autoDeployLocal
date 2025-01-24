<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="javax.servlet.*,java.text.*"%>
<%
Date date = new Date();
SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
%>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta name="robots" content="noindex,nofollow,noarchive" />

<title>USS-SS-AL-WEBサイト</title>
<!--<link rel="shortcut icon" href="./favicon.ico">-->

<link href="./core/plugins/jquery/jquery-ui.css" rel="stylesheet">
<link href="./core/plugins/jquery/jquery.toast.css" rel="stylesheet">
<link href="./core/plugins/jquery/jquery.datetimepicker.css" rel="stylesheet">
<link href="./core/plugins/bootstrap/bootstrap.css?t=<%=df.format(date)%>" rel="stylesheet">
<link href="./core/plugins/bootstrap/bootstrap-datepicker3.css" rel="stylesheet">
<link href="./core/plugins/bootstrap/bootstrap-timepicker.css" rel="stylesheet">
<link href="./core/common/common.css?t=<%=df.format(date)%>" rel="stylesheet">
<link href="./core/select-input-method/select-input-method.css?t=<%=df.format(date)%>" rel="stylesheet">

<script src="./core/plugins/jquery/jquery-3.6.0.js"></script>
<script src="./core/plugins/jquery/jquery-ui.js"></script>
<script src="./core/plugins/jquery/jSignature.min.js"></script>

<script src="./core/plugins/jquery/jquery.cookie.min.js"></script>

<script src="./core/plugins/jquery/jquery.datetimepicker.full.js"></script>
<script src="./core/plugins/jquery/datepicker-ja.js"></script>
<script src="./core/plugins/bootstrap/bootstrap.js"></script>
<script src="./core/plugins/jquery/jquery.toast.js"></script>
<script src="./core/plugins/bootstrap/bootstrap-timepicker.js"></script>
<script src="./core/common/common.js?t=<%=df.format(date)%>"></script>
<script src="./core/common/move.js?t=<%=df.format(date)%>"></script>
<script src="./core/select-input-method/select-input-method.js?t=<%=df.format(date)%>"></script>
<%-- <script src="https://kit.fontawesome.com/867180ad22.js?t=<%=df.format(date)%>" crossorigin="anonymous"></script> --%>
<script src="./core/plugins/desvg.js"></script>

<div class="modal-container fade" id="modal_loading" data-backdrop="static">
	<div id="loading-back"></div>
	<img class="center-block" id="img_loading" src="./images/loading-image.gif" />
</div>
<div class="modal-container fade" id="filter_back" data-backdrop="static">
	<div id=""></div>
</div>


<input type="hidden" id="list" value="1">
<input type="hidden" id="viewNum" value="30">
<input type="hidden" id="closedDisplaySetting1" value="1">
<input type="hidden" id="closedDisplaySetting2" value="1">

<input type="hidden" id="textFilterOpen" value="フィルター設定">
<input type="hidden" id="textFilterClose" value="フィルター設定を閉じる">