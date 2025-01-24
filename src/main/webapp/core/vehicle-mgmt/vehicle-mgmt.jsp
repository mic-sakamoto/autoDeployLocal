<%@page import="utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/core/common/import.jsp"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link href="./core/vehicle-mgmt/vehicle-mgmt.css?t=<%=df.format(date)%>" rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<script type="text/javascript" src="./core/vehicle-mgmt/vehicle-mgmt.js?t=<%=df.format(date)%>"></script>
</head>

<body>

	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>

			<div class="title">
				<div class="title-container">
					<span class="title-item">車両管理</span>
				</div>

				<div class="title-container filter-container">
					<div class="filter-area open-dev">
						<div class="filter-block">
							<div class="filter-header-block">
								<span class="filter-opentext"> フィルター設定 </span>
								<span class="open-icon close"></span>
							</div>
							<div class="open-target" style="display: none;">
								<div class="filter-body-block">
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">メーカー</div>
											<input type="text" class="filter-input" id="conditionMaker">
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">車名</div>
											<input type="text" class="filter-input" id="conditionCarName">
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">型式</div>
											<input type="text" class="filter-input" id="conditionCarType">
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">年月式</div>
											<input type="text" class="filter-input" id="conditionDateFrom">
											<span>～</span>
											<input type="text" class="filter-input" id="conditionDateTo">
										</div>
									</div>
								</div>

								<div class="filter-footer-block">
									<button type="button" id="filter-clear" class="half-btn sub">
										<div class="solid-icon">
											<div class="edit-icon"></div>
										</div>
										<span class="">条件クリア</span>
									</button>
									<button type="button" id="filter-search" class="half-btn main">
										<span class="">実行</span>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div id="display-set" style="display: none;">

				<div class="container">

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
										<option value=""></option>
										<option value="UserId_num">メーカー</option>
										<option value="UserName_str">車名</option>
										<option value="UserId_num">型式</option>
										<option value="UserId_num">年月式</option>
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
								<div id="list_body" class="list-body"></div>
							</div>

							<!-- リストのテンプレート -->
							<div id="list_template" style="display: none">
								<div id="list_body_template" class="list-body">
									<div class="list-card">
										<input type="hidden" class="list-hid-seq" />
										<div class="list-body-row">
											<div class="list-item-row">
												<div class="list-w-1">
													<span class="list-font-general-title list-title">メーカー:</span>
													<span class="list-maker list-font-general-name">トヨタ</span>
												</div>
												<div class="list-w-2">
													<span class="list-font-general-title list-title">車種:</span>
													<span class="list-model list-font-general-name">トヨタ</span>
												</div>
											</div>
											<div class="list-item-row row-1">
												<div class="list-w-3">
													<span class="list-font-general-title list-title">型式:</span>
													<span class="list-katashiki list-font-general-name">トヨタ</span>
												</div>
												<div class="list-w-1">
													<span class="list-font-general-title list-title">年月日:</span>
													<span class="list-year list-font-general-name">トヨタ</span>
												</div>
												<div class="list-w-3">
													<span class="list-font-general-title list-title">E_Gスタート方式:</span>
													<span class="list-e_g_start_typeText list-font-general-name">トヨタ</span>
												</div>
											</div>
										</div>
										<div class="list-body-row">
											<div class="list-item-row row-2">
												<div class="list-w-1">
													<span class="list-font-general-title list-title">適合判定:</span>
													<span class="list-verify_codeText list-font-general-name">トヨタ</span>
												</div>
												<div class="list-w-2">
													<span class="list-font-general-title list-title">ID:</span>
													<span class="list-id list-font-general-name">トヨタ</span>
												</div>
												<div class="list-w-3">
													<span class="list-font-general-title list-title">適合車種No:</span>
													<span class="list-vehicle_no list-font-general-name">トヨタ</span>
												</div>
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
</body>
</html>
