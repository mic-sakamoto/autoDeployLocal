<%@page import="utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/core/common/import.jsp"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link href="./core/mccs-mgmt/mccs-mgmt.css?t=<%=df.format(date)%>" rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<script type="text/javascript" src="./core/mccs-mgmt/mccs-mgmt.js?t=<%=df.format(date)%>"></script>
</head>

<body>
	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>
			<div class="title">
				<div class="title-container">
					<span class="title-item">MCCS車両管理</span>
				</div>
				
				<div class="title-container filter-container">
					<div class="filter-area open-dev">
						<div class="filter-block">
							<div class="filter-header-block">
								<span class="filter-opentext">
									フィルター設定
								</span>
								<span class="open-icon close"></span>
							</div>
							<div class="open-target" style="display:none;">
								<div id="listFilter" class="filter-body-block">
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">WEB受付ID</div>
											<input type="text" class="filter-input" id="conditionWebAppId">
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">顧客名</div>
											<input type="text" class="filter-input" id="conditionUserName">
										</div>
										<div class="filter-item">
											<div class="filter-text">顧客ID</div>
											<input type="text" class="filter-input" id="conditionCustomerId">
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">車名＋グレード</div>
											<input type="text" class="filter-input" id="conditionModel">
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">MCCSステータス</div>
											<select id="conditionMccsStatusType" class="select filter-input">
												<option value="" disabled selected>選択してください</option>
												<option value="0">申込情報の確認</option>
												<option value="1">発注待ち</option>
												<option value="2">発送対応</option>
												<option value="3">MCCS取付</option>
												<option value="4">納車作業中</option>
												<option value="5">利用中</option>
												<option value="6">利用終了</option>
												<option value="7">取りやめ</option>
											</select>
										</div>
									</div>
		
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">MCCS取付日</div>
											<input type="date" class="filter-input" id="conditionMccsAttachtDateFrom">
											～
											<input type="date" class="filter-input" id="conditionMccsAttachtDateTo">
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">本申込日</div>
											<input type="date" class="filter-input" id="conditionMainAppDateFrom">
											～
											<input type="date" class="filter-input" id="conditionMainAppDateTo">
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">ベリ完了日</div>
											<input type="date" class="filter-input" id="conditionVerifiedDateFrom">
											～
											<input type="date" class="filter-input" id="conditionVerifiedDateTo">
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">更新日</div>
											<input type="date" class="filter-input" id="conditionUpdateDateFrom">
											～
											<input type="date" class="filter-input" id="conditionUpdateDateTo">
										</div>
									</div>
								</div>
								<div class="filter-footer-block">
									<button type="button" id="filter-clear" class="half-btn sub">
										<div class="solid-icon">
											<div class="edit-icon"></div>
										</div>
										<span class="" id="clearFilter-btn">条件クリア</span>
									</button>
									<button type="button" class="half-btn main">
										<span class="" id="searchList-btn">実行</span>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="display-set" style="display:none;">
		
				<div class="container">
				
					<div class="list-container">
						
						<div class="list-area">
		<%-- 					<%@ include file="/core/common/paging.jsp"%> --%>
		
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
										<option value="UserName_str">顧客名</option>
										<option value="CustomerId_num">顧客ID</option>
										<option value="Model_str">モデル</option>
										<option value="MccsStatusType_num">MCCSステータス</option>
										<option value="WebAppId_num">WEB受付ID</option>
										<option value="MccsAttachtDate_str">MCCS取付日</option>
										<option value="MainAppDate_str">本申込日</option>
										<option value="VerifiedDate_str">ベリ完了日</option>
										<option value="UpdateDate_str" selected>更新日</option>
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
										
										<div class="list-body-row">
											<div class="list-item-row">
												<div><span class="list-userName list-font-customer"></span></div>
												<div class="user-id"><span class="list-font-id">ID:</span><span class="list-customerId list-font-id"></span></div>
											</div>
											<div class="list-item-row">
												<div><span class="list-model list-font-message-name"></span></div>
											</div>
										</div>
										
										<div class="list-body-row">
											<div class="list-item-row">
												<div><span class="list-mccsStatusType list-font-status "></span></div>
												<div class="contract-id"><span class="list-font-general-title">Web受付ID:</span><span class="list-webAppId list-font-id"></span></div>
											</div>
										</div>
										
										<div class="list-body-info-row">
											<div class="list-info-item">
												<span class="info-title list-font-general-title">MCCS取付日:</span>
												<span class="list-mccsAttachtDate list-font-general-name"></span>
											</div>
											<div class="list-info-item">
												<span class="info-title list-font-general-title">本申込日:</span>
												<span class="list-mainAppDate list-font-general-name"></span>
											</div>
											<div class="list-info-item">
												<span class="info-title list-font-general-title">ベリ完了日:</span>
												<span class="list-verifiedDate list-font-general-name"></span>
											</div>
											<div class="list-info-item">
												<span class="info-title list-font-general-title">更新日:</span>
												<span class="list-updateDate list-font-general-name"></span>
											</div>
										</div>
										
									</div>
					   			</div>
							</div>
							<%@ include file="/core/common/paging.jsp"%>
						</div>
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
	<input type="hidden" id="text_status_0" value="申込情報の確認">
	<input type="hidden" id="text_status_1" value="発注待ち">
	<input type="hidden" id="text_status_2" value="発送対応">
	<input type="hidden" id="text_status_3" value="MCCS取付">
	<input type="hidden" id="text_status_4" value="納車作業中">
	<input type="hidden" id="text_status_5" value="利用中">
	<input type="hidden" id="text_status_6" value="利用終了">
	<input type="hidden" id="text_status_7" value="取りやめ">
</body>
</html>
