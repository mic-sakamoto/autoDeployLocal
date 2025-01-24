<%@page import="utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/core/common/import.jsp"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link href="./core/store-mgmt/store-mgmt.css?t=<%=df.format(date)%>" rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<script type="text/javascript" src="./core/store-mgmt/store-mgmt.js?t=<%=df.format(date)%>"></script>
</head>

<body>
	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>

			<div class="title">
				<div class="title-container">
					<span class="title-item">加盟店管理</span>
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
											<div class="filter-text">加盟店名</div>
											<input type="text" class="filter-input" id="conditionStoreName">
										</div>
										<div class="filter-item">
											<div class="filter-text">加盟店ID</div>
											<input type="text" class="filter-input" id="conditionStoreId">
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
									<button type="button" class="half-btn main">
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

						<div class="open-contents-area open-dev">
							<div class="filter-block new-regist-block">
								<div class="accordion-open-head-block">
									<span class="accordion-open-text"> ＋加盟店の新規登録/再通知 </span>
									<span class="open-icon close"></span>
								</div>
								<div class="accordion-open-block open-target" style="display: none;">

									<div class="accordion-open-body-block">

										<div class="input-item">
											<div class="input-searchable">
												<input type="text" class="" placeholder="加盟店ID" id="storeId">
												<button class="btn main" id="id-confirm">ID確認</button>
											</div>
										</div>
										<div class="input-item">
											<input type="text" class="" placeholder="加盟店名" id="storeName">
										</div>
										<div class="input-item">
											<input type="text" class="" placeholder="電話番号" id="storeTel">
										</div>
										<div class="input-item">
											<input type="text" class="" placeholder="メールアドレス" id="storeMailAddress">
										</div>
										<div class="input-item">
											<div class="text-m bold">ログイン情報の通知方法を選択</div>
											<div class="radio-container">
												<label>
													<input type="radio" class="radio-loan" name="radio-loan" value="1">
													SMS
												</label>
												<label>
													<input type="radio" class="radio-loan" name="radio-loan" value="2">
													メール
												</label>
											</div>
										</div>
									</div>
									<div class="accordion-open-footer-block">
										<button type="button" class="btn main" id="newRegistBtn">新規登録/再通知</button>
									</div>
								</div>
							</div>
						</div>

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
										<option value="UserId_num">加盟店ID</option>
										<option value="UserName_str">加盟店名</option>
										<option value="UserId_num">電話番号</option>
										<option value="UserId_num">メールアドレス</option>
										<option value="UserId_num">加盟店ID通知方法</option>
										<option value="UserId_num">加盟店ID通知送信日時</option>
										<option value="UserId_num">最終ログイン日時</option>
										<option value="UserId_num">契約終了日</option>
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
										<input type="hidden" class="list-hid-userId" />

										<div class="list-body-row">
											<div class="list-item-row list-store-info">
												<div class="list-item-block-row">
													<div>
														<span class="list-UserName list-font-store list-storeName"></span>
													</div>
													<div class="store-id">
														<span class="list-font-id">ID:</span>
														<span class="list-UserId list-font-id"></span>
													</div>
												</div>
												<div class="list-item-block-row">
													<div class="">
														<span class="list-font-general-title list-title">会員番号:</span>
														<span class="list-MemberCode list-font-general-date-num"></span>
													</div>
													<div class="">
														<span class="list-font-general-title list-title">電話番号:</span>
														<span class="list-TelNumber list-font-general-date-num"></span>
													</div>
												</div>
												<div class="list-item-block-row">
													<div class="">
														<span class="list-font-general-title list-title">Mail:</span>
														<span class="list-MailAddress list-font-general-date-num"></span>
													</div>
												</div>
											</div>
											<div class="list-item-row list-store-info">
												<div class="list-btn">
													<button type="button" class="half-btn main color-green" onclick="login(this)">
														パスワード通知設定<br>メール送信
													</button>
													<button type="button" class="half-btn main color-green" onclick="login(this)">
														パスワード通知設定<br>SMS送信
													</button>
													<button type="button" class="half-btn main color-red" onclick="proxyLogin(this)">代行入力開始</button>
												</div>
												<div class="list-btn list-item-block-row">
													<div class="">
														<span class="list-font-general-title list-title">送信:</span>
														<span class="list-send-mail list-font-general-date-num"></span>
													</div>
													<div class="">
														<span class="list-font-general-title list-title">送信:</span>
														<span class="list-send-sms list-font-general-date-num"></span>
													</div>
													<div class="">
														<span class="list-font-general-title list-title">最終:</span>
														<span class="list-last-login list-font-general-date-num"></span>
													</div>
												</div>
											</div>
										</div>



										<div class="list-body-info-row">
											<div class="list-info-item">
												<span class="info-title list-font-general-title">登録日:</span>
												<span class="list-CreateTime list-font-general-name"></span>
											</div>
											<div class="list-info-item">
												<span class="info-title list-font-general-title">契約終了日:</span>
												<span class="list- list-font-general-name"></span>
											</div>
											<div class="list-info-item">
												<span class="info-title list-font-general-title">利用開始日:</span>
												<span class="list- list-font-general-name"></span>
											</div>
											<div class="list-info-item">
												<span class="info-title list-font-general-title">最終ログイン日:</span>
												<span class="list-LastLoginTime list-font-general-name"></span>
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
