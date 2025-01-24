<%@page import="utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/core/common/import.jsp"%>


<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
</head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link href="./core/contract-mgmt/contract-mgmt.css" rel="stylesheet">
<script src="./core/contract-mgmt/contract-mgmt.js"></script>
<html>
<body>
	<div class="wrapper">

		<%@ include file="/core/common/header.jsp"%>
		<form class="" id="form" name="form">
			<s:hidden name="action" id="action" />
			<s:hidden name="pdfName" id="pdfName" />
		</form>

		<main>

			<div class="title">
				<div class="title-container">
					<span class="title-item">契約管理</span>
				</div>
				<div class="title-container">
					<div class="list-container-btn-area">
						<% if ("2".equals(role_type)) { %>
						<button type="button" class="btn main" id="newExaminationBtn">
							<img class="svg btn-svg-icon" src="./images/svg/plus.svg">
							新規審査申込
						</button>
						<button type="button" class="btn main" id="loanSimulationBtn">
							<img class="svg btn-svg-icon" src="./images/svg/database.svg">
							ローンシミュレーション
						</button>
						<% } %>
						<% if ("1".equals(role_type) || "2".equals(role_type)) { %>
						<button type="button" class="btn main" id="downloadBtn">
							<img class="svg btn-svg-icon" src="./images/svg/download.svg">
							契約一覧をダウンロード
						</button>
						<% } %>
					</div>
				</div>

				<% if (!"3".equals(role_type)) { %>
				<div class="title-container filter-container">
					<div class="filter-area open-dev">
						<div class="filter-block">
							<div class="filter-header-block">
								<span class="filter-opentext"> フィルター設定 </span>
								<span class="open-icon close"></span>
							</div>
							<div class="open-target" style="display: none;">
								<div id="listFilter" class="filter-body-block">
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">WEB受付ID</div>
											<input type="text" class="filter-input" id="conditionWebAppId">
										</div>
										<div class="filter-item">
											<div class="filter-text">ステータス</div>
											<select class="dropdown filter-input" id="conditionStatus">
												<option value="">&nbsp;</option>
												<option value="1">審査申込前</option>
												<option value="2">審査申込前/未入力</option>
												<option value="3">審査申込前/入力済</option>
												<option value="4">審査申込登録中</option>
												<option value="5">申込エラー</option>
												<option value="6">審査受付済</option>
												<option value="7">不備返却</option>
												<option value="8">否決</option>
												<option value="9">審査申込可決</option>
												<option value="10">審査申込条件付可決</option>
												<option value="11">辞退</option>
												<option value="12">期限切れ</option>
												<option value="13">本申込前/入力済み</option>
												<option value="14">本申込登録中</option>
												<option value="15">本申込受付済</option>
												<option value="16">ベリファイNG</option>
												<option value="17">契約成立</option>
												<option value="18">契約キャンセル</option>
											</select>
										</div>
									</div>

									<% if ("1".equals(role_type) || "2".equals(role_type)) { %>

									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">顧客名</div>
											<input type="text" class="filter-input" id="conditionUserName">
										</div>
										<div class="filter-item">
											<div class="filter-text">顧客ID</div>
											<input type="text" class="filter-input" id="conditionUserId">
										</div>
									</div>

									<% } %>

									<div class="filter-item-row">

										<% if ("1".equals(role_type)) { %>

										<div class="filter-item">
											<div class="filter-text">加盟店名</div>
											<input type="text" class="filter-input" id="conditionStoreName">
										</div>
										<div class="filter-item">
											<div class="filter-text">加盟店ID</div>
											<input type="text" class="filter-input" id="conditionStoreId">
										</div>

										<% } %>

										<div class="filter-item">
											<div class="filter-text">担当者名</div>
											<input type="text" class="filter-input" id="conditionStoreChargeName">
										</div>
									</div>
									<% if ("1".equals(role_type) || "2".equals(role_type)) { %>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">代行入力</div>
											<select class="dropdown filter-input" id="conditionProxyInput">
												<option value="">&nbsp;</option>
												<option value="1">●</option>
											</select>
										</div>
										<div class="filter-item">
											<div class="filter-text">入力方法</div>
											<select class="dropdown filter-input" id="conditionInputKbn">
												<option value="">&nbsp;</option>
												<option value="2">加盟店入力</option>
												<option value="3">顧客入力</option>
											</select>
										</div>
									</div>
									<% } %>
									<% if ("1".equals(role_type) || "2".equals(role_type)) { %>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">申込日</div>
											<input type="date" class="filter-input" id="conditionScreeningAppDateFrom">
											<span>～</span>
											<input type="date" class="filter-input" id="conditionScreeningAppDateTo">
										</div>
									</div>
									<% } %>
									<% if ("1".equals(role_type) || "2".equals(role_type)) { %>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">本申込日</div>
											<input type="date" class="filter-input" id="conditionMainAppDateFrom">
											<span>～</span>
											<input type="date" class="filter-input" id="conditionMainAppDateTo">
										</div>
									</div>
									<% } %>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">ベリ完了日</div>
											<input type="date" class="filter-input" id="conditionVerifiedDateFrom">
											<span>～</span>
											<input type="date" class="filter-input" id="conditionVerifiedDateTo">
										</div>
									</div>
									<% if ("1".equals(role_type) || "2".equals(role_type)) { %>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">立替日</div>
											<input type="date" class="filter-input" id="conditionLoanDateFrom">
											<span>～</span>
											<input type="date" class="filter-input" id="conditionLoanDateTo">
										</div>
									</div>
									<% } %>
								</div>
								<div class="filter-footer-block">
									<button type="button" id="filter-clear" class="half-btn sub">
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
				<% } %>
			</div>

			<div id="display-set" style="display: none;">
				<div class="container">
					<form class="form" name="form" method="post">

						<input type="hidden" id="text_adminRole1" value="システム管理者">
						<input type="hidden" id="text_adminRole2" value="スタッフ">
						<input type="hidden" id="text_adminRole3" value="作業者">
						<input type="hidden" id="text_passwordRule" value="パスワードには、数字・記号・半角英語の小文字・半角英語の大文字を<br />それぞれ1つ入れてください。">
						<input type="hidden" id="text_displaySetting" value="表示設定">
						<input type="hidden" id="text_displayNumber" value="表示件数">
						<input type="hidden" id="text_passVulnerable" value="脆弱">
						<input type="hidden" id="text_passChange" value="パスワード変更">
						<input type="hidden" id="text_accountDelete" value="アカウント削除">

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
											<option value="UpdateTime_str">更新日</option>
											<option value="WebAppId_str">WEB受付ID</option>
											<option value="StatusKbn_num">ステータス</option>
											<option value="ScreeningAppDate_str">申込日</option>
											<option value="VerifiedDate_str">ベリ完了日</option>
											<option value="StaffName_str">担当者名</option>
											<option value="MainAppDate_str">本申込日</option>
											<option value="LoanDate_str">立替日</option>
											<option value="InputRoleKbn_num">入力方法</option>
											<option value="CustomerId_str">顧客ID</option>
											<option value="CustomerName_str">顧客名</option>
											<option value="IsProxyInput_str">代行入力</option>
											<option value="StoreId_str">加盟店ID</option>
											<option value="StoreName_str">加盟店名</option>
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
											<input type="hidden" class="list-hid-contractId" />
											<input type="hidden" class="list-hid-webAppId" />
											<input type="hidden" class="list-hid-statusKbn" />
											<input type="hidden" class="list-hid-inputModeKbn" />

											<div class="list-body-row">
												<div class="list-item-row no-customer">
													<div>
														<span class="list-userName list-font-customer">顧客名</span>
													</div>
													<div>
														<span class="list-inputRole-C">
															<img class="svg list-pen-icon" src="./images/svg/pen.svg">
														</span>
													</div>
													<div class="user-id">
														<span class="list-font-id">ID:</span>
														<span class="list-userId list-font-id"></span>
													</div>
												</div>
												<div class="list-item-row no-store">
													<div>
														<span class="list-storeName list-font-store">加盟店名</span>
													</div>
													<div>
														<span class="list-inputRole-S">
															<img class="svg list-pen-icon" src="./images/svg/pen.svg">
														</span>
													</div>
													<div class="store-id no-customer">
														<span class="list-font-id">ID:</span>
														<span class="list-storeId list-font-id"></span>
													</div>
												</div>
											</div>


											<div class="list-body-row">
												<div class="list-item-row">
													<div>
														<span class="list-status list-font-status"></span>
													</div>
													<div class="web-id">
														<span class="list-font-general-title">Web受付ID:</span>
														<span class="list-webAppId list-font-id"></span>
													</div>
												</div>
												<div class="list-item-row">
													<div class="store-charge">
														<span class="list-font-staff">担当:</span>
														<span class="list-storeCharge list-font-staff"></span>
													</div>
													<div class="no-customer">
														<span class="list-proxy-input text-s">代行入力</span>
													</div>
												</div>
											</div>

											<div class="list-body-info-row">
												<div class="list-info-item no-customer">
													<span class="info-title list-font-general-title">申込日:</span>
													<span class="list-screeningAppDate list-font-general-name"></span>
												</div>
												<div class="list-info-item no-customer">
													<span class="info-title list-font-general-title">本申込日:</span>
													<span class="list-mainAppDate list-font-general-name"></span>
												</div>
												<div class="list-info-item">
													<span class="info-title list-font-general-title">ベリ完了日:</span>
													<span class="list-verifiedDate list-font-general-name"></span>
												</div>
												<div class="list-info-item no-customer">
													<span class="info-title list-font-general-title">立替日:</span>
													<span class="list-loanDate list-font-general-name"></span>
												</div>
												<div class="list-info-item">
													<span class="info-title list-font-general-title">更新日:</span>
													<span class="list-updateTime list-font-general-name"></span>
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

			<%-- 審査申込内容入力方法選択モーダル --%>
			<div class="modal-container select-input-method-modal modal-selectinput">
				<div class="modal-body">
					<div class="modal-content">
						<%@ include file="/core/select-input-method/select-input-method.jsp"%>
					</div>
				</div>
			</div>

		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>



	<input type="hidden" id="notTransition" value="スタッフの場合は遷移出来ません">

</body>
</html>