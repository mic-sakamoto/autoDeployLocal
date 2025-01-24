<%@page import="utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/core/common/import.jsp"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link href="./core/payment-detail-mgmt/payment-detail-mgmt.css?t=<%=df.format(date)%>" rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<script type="text/javascript" src="./core/payment-detail-mgmt/payment-detail-mgmt.js?t=<%=df.format(date)%>"></script>
</head>

<body>
	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>
			<div class="title">
				<div class="title-container">
					<span class="title-item">精算明細票管理</span>
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
							<div class="open-target" style="display: none;">
								<div class="filter-body-block">
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">精算日</div>
											<input type="date" class="filter-input" id="conditionPaymentDateFrom">
											<span>～</span>
											<input type="date" class="filter-input" id="conditionPaymentDateTo">
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

			<div id="display-set" style="display: none;">

				<div class="container">
					<form class="form" id="form" name="form">
						<s:hidden name="action" id="action" />
						<s:hidden name="pdfName" id="pdfName" />
						<s:hidden name="seq" id="seq" />

						<div class="list-container">

							<div class="list-area">
								<%-- 					<%@ include file="/core/common/paging.jsp"%> --%>

								<div class="list-header-block">
									<div class="list-header-cnt">
										<span id="list-counts"></span>
									</div>
									<div class="list-header-cnt-text">
										<span>件</span>
									</div>
									<div class="list-header-sort-text">
										<span>並び替え</span>
									</div>
									<div class="list-header-sort-column">
										<select class="dropdown" id="listHeaderSortColumn">
											<option value="PaymentDate_str">精算日</option>
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

								<!-- リストのテンプレート -->
								<div id="list_template" style="display: none">
									<div id="list_body_template">

										<div class="list-card">
											<div class="list-seq" style="display: none;"></div>
											<div class="list-hid-webId" style="display: none;"></div>

											<div class="list-body-row">
												<div class="list-item-row">
													<div class="bold">精算日: <span class="list-paymentDate text-l"></span></div>
												</div>
											</div>
										</div>
									</div>
								</div>

								<%@ include file="/core/common/paging.jsp"%>

							</div>
						</div>
					</form>



				</div>

			</div>
		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
</body>
</html>
