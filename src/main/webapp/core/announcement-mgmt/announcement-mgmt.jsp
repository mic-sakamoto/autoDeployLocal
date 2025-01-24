<%@page import="utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/core/common/import.jsp"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link href="./core/announcement-mgmt/announcement-mgmt.css?t=<%=df.format(date)%>" rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<script type="text/javascript" src="./core/announcement-mgmt/announcement-mgmt.js?t=<%=df.format(date)%>"></script>
<script type="text/javascript" src="./core/common/common.js"></script>
</head>

<body>
	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>
			<div class="title">
				<div class="title-container">
					<span class="title-item">お知らせ管理</span>
				</div>
				<div class="title-container">
					<button type="button" class="btn main width-full" id="newRegistBtn">+新規お知らせ作成</button>
				</div>
			</div>
			
			<div class="container">
			<div class="list-container">		
				<div class="list-area">
	<%-- 				<%@ include file="/core/common/paging.jsp"%> --%>
	
					<div class="list-header-block">
						<div class="list-header-cnt">
							<span id="list-counts" ></span>
						</div>
						<div class="list-header-cnt-text">
							<span>件</span>
						</div>
						<div class="list-header-sort-text">
							<span>並び替え</span>
						</div>
						<div class="list-header-sort-column">
							<select class="dropdown" id="listHeaderSortColumn">
								<option value=""></option>
								<option value="Body_str">内容</option>
								<option value="IsTargetKbn_str">対象</option>
								<option value="OpenCloseDate_str">公開期間</option>
							</select>
						</div>
						<div class="list-header-sort-order">
							<select class="dropdown" id="listHeaderSortOrder">
								<option value="1">降順</option>
								<option value="2">昇順</option>
							</select>
						</div>
					</div>
	
					<div id="list_table" class="">
						<div id="list_body" class="list-body">
						</div>
					</div>
	
					<div id="list_template" style="display: none">
						<div id="list_body_template">
							<div class="list-card">
								<div class="list-seq" style="display:none;"></div>
								<div class="list-isTargetKbn" style="display:none;"></div>
								
								<div class="list-body-row">
									<div class="list-announcement-body"></div>
								</div>
								<div class="list-body-row">
									<div class="list-item-row">
										<div><span class="list-isTarget text-s"></span></div>		
									</div>
								</div>
								<div class="list-body-info-row">
									<div><span class="list-openCloseDate text-s"></span></div>
								</div>
								
							</div>
			   			</div>
					</div>
					
					<%@ include file="/core/common/paging.jsp"%>
				
				</div>
			</div>
		
		
		</div>
		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
	<input type="hidden" id="text_body" value="本文：">
	<input type="hidden" id="text_openCloseDate" value="公開期間：">
	<input type="hidden" id="text_isTargetStore" value="加盟店">
	<input type="hidden" id="text_isTargetCustomer" value="ご契約者様">
</body>
</html>
