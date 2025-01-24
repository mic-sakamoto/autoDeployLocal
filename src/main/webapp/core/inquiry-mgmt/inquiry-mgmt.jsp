<%@page import="utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/core/common/import.jsp"%>


<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
</head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link href="./core/inquiry-mgmt/inquiry-mgmt.css?t=<%=df.format(date)%>" rel="stylesheet">
<script type="text/javascript" src="./core/inquiry-mgmt/inquiry-mgmt.js?t=<%=df.format(date)%>"></script>
<html>

<body>
	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>
			<div class="title">
				<div class="title-container">
					<span class="title-item">お問い合わせ／ご連絡</span>
				</div>
				<div class="title-container">
					<button type="button" class="btn main width-full" id="newRegistBtn">+新規メッセージ作成</button>
				</div>
				<% if ("1".equals(role_type)) { %>
				<div class="title-container">
					<div class="filter-area open-dev">
						<div class="filter-block">
							<div class="filter-header-block">
								<span class="filter-opentext"> フィルター設定 </span> <span class="open-icon close"></span>
							</div>
							<div class="open-target" style="display: none;">
								<div class="filter-body-block" id="listFilter">
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">送受信日</div>
											<input type="date" class="filter-input" id="conditionInquireDateFrom"> ～ <input type="date" class="filter-input" id="conditionInquireDateTo">
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">返信日</div>
											<input type="date" class="filter-input" id="conditionAnswerDateFrom"> ～ <input type="date" class="filter-input" id="conditionAnswerDateTo">
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">内容</div>
											<select id="conditionBodyKbn" class="select filter-input">
												<option value="" disabled selected></option>
												<option value="0">USS-SSへの問い合わせ</option>
												<option value="1">USS-SSからの連絡</option>
											</select>
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">送信者</div>
											<select id="conditionInquirerKbn" class="select filter-input">
												<option value="" disabled selected></option>
												<option value="0">USS-SS</option>
												<option value="1">加盟店</option>
												<option value="2">ご契約者様</option>
											</select>
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">送信者の名前</div>
											<input type="text" class="filter-input" id="conditionInquirerName">
										</div>
										<div class="filter-item">
											<div class="filter-text">送信者のID</div>
											<input type="text" class="filter-input" id="conditionInquirerId">
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">宛先</div>
											<select id="conditionAnswererKbn" class="select filter-input">
												<option value="" disabled selected></option>
												<option value="0">USS-SS</option>
												<option value="1">加盟店</option>
												<option value="2">ご契約者様</option>
											</select>
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">宛先の名前</div>
											<input type="text" class="filter-input" id="conditionAnswererName">
										</div>
										<div class="filter-item">
											<div class="filter-text">宛先のID</div>
											<input type="text" class="filter-input" id="conditionAnswererId">
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">同意対象</div>
											<select id="conditionAgreementBodyKbn" class="select filter-input">
												<option value="" selected></option>
												<option value="1">添付された書類に同意します</option>
												<option value="2">メッセージ2</option>
												<option value="3">メッセージ3</option>
											</select>
										</div>
									</div>
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">同意日有無</div>
											<select id="conditionAgreementDateFlg" class="select filter-input">
												<option value="" selected></option>
												<option value="0">有</option>
												<option value="1">無</option>
											</select>
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
										<span class="" id="searchList-btn">実行</span>
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
										<% if ("1".equals(role_type)) { %>
										<option value="StaffIsUnread_str" selected>未読</option>
										<% } %>
										<% if ("2".equals(role_type) || "3".equals(role_type)) { %>
										<option value="CustomerIsUnread_str" selected>未読</option>
										<% } %>
										<option value="InquireDate_str">送受信日時</option>
										<option value="AnswerDate_str">返信日時</option>
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

							<div id="list_template" style="display: none">
								<div id="list_body_template">
									<div class="list-card">
										<div class="list-seq" style="display: none;"></div>
										<% if ("1".equals(role_type)) { %>
										<input type="hidden" class="list-staffIsUnread" />
										<% } %>
										<% if ("2".equals(role_type) || "3".equals(role_type)) { %>
										<input type="hidden" class="list-customerIsUnread" />
										<% } %>
										<% if ("1".equals(role_type)) { %>
										<div class="list-body-row">
											<div class="list-item-row">
												<div class="list-senderInfo"></div>
											</div>
											<div class="list-item-row">
												<div class="list-addressInfo"></div>
											</div>
										</div>
										<div class="list-body-row">
											<div class="list-item-row">
												<div>
													<span class="list-status list-font-status"></span>
												</div>
												<div>
													<span class="list-newIcon list-font-status"></span>
												</div>
											</div>
										</div>
										<% } %>
										<% if ("2".equals(role_type) || "3".equals(role_type)) { %>
										<div class="list-body-row">
											<div class="list-item-row">
												<div>
													<span class="list-status list-font-status"></span>
												</div>
												<div>
													<span class="list-newIcon list-font-status"></span>
												</div>
											</div>
											<div class="list-item-row">
												<div class="list-senderInfo-customer"></div>
											</div>
										</div>
										<% } %>
										<div class="list-body-info-row">
											<div class="list-info-item">
												<span class="info-title list-font-general-title">送受信日:</span> <span class="list-inquireDate list-font-general-name"></span>
											</div>
											<div class="list-info-item">
												<span class="info-title list-font-general-title">返信日:</span> <span class="list-answerDate list-font-general-name"></span>
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

			<%-- 新規メッセージ作成モーダル --%>
			<div class="modal-container regist-modal">
				<div class="modal-body">
					<div class="modal-content">

						<div class="contents-area">
							<span class="text-m bold">USS-SSへの連絡</span>
						</div>

						<div class="regist-note">
							<span class="text-m">メッセージは1度限りお送りいただくことが可能です。</span>
						</div>
						<form id="form-inquiry" name="form" class="inquiry-form">
							<div class="contents-area">
								<div class="item-block">
									<div class="input-item">
										<div class="body-area-div">
											<div class="text-m">本文</div>
											<textarea class="body-area" id="inquireBody" name="" value="" placeholder="1000文字まで"></textarea>
											<div class="textcount text-right text-s" id="textCount">0 / 1000</div>

										</div>
										<button type="button" class="btn main tmpBtn" id="inquiryFileBtn">ファイル添付</button>
										<input type="file" id="inquireAttachedFile1" val="" style="display: none;" /> <input type="file" id="inquireAttachedFile2" val="" style="display: none;" />
										<div class="body-note">
											<div class="text-left text-s">ファイル添付は画像形式（jpeg、gif、png、bmp）もしくはpdfファイル、一度に2ファイルまでです。上限10MBまで。</div>
										</div>
										<div class="inquireAttachedFileDisplay">
											<p id="inquireAttachedFile1Display"></p>
											<p id="inquireAttachedFile2Display"></p>
										</div>

									</div>

									<!-- ボタン -->
									<div class="btn-container">
										<button type="button" class="btn sub" id="registModalCloseBtn">閉じる</button>
										<button type="button" class="btn main" id="inquiryBtn">送信</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>

			</div>


			<%-- メッセージ内容表示モーダル --%>
			<div class="modal-container detail-modal">
				<div class="modal-body">
					<div class="modal-content">

						<div class="contents-area">
							<span class="text-m bold" id="messageKbn"></span>
						</div>

						<div class="regist-note">
							<span class="text-m">メッセージは1度限りお送りいただくことが可能です。</span>
						</div>

						<div class="contents-body-area">
							<div class="modal-InquireInfo">
								<div class="text-m" id="detail-modal-InquireName"></div>
								<div>送信日：</div>
								<div class="text-m" id="detail-modal-InquireDate"></div>
							</div>

							<div class="modal-InquireBody">
								<span class="text-m" id="detail-modal-InquireBody"></span>
							</div>

							<div class="model-InquireFile" id="detail-modal-InquireFile"></div>

						</div>

						<div class="contents-body-area modal-answer-body-area" style="display: none;">
							<div class="modal-AnswerInfo">
								<div class="text-m" id="detail-modal-AnswerName"></div>
								<div>送信日：</div>
								<div class="text-m" id="detail-modal-AnswerDate"></div>
							</div>
							<div class="agreement-area" id="agreemented-area" style="display: none;"></div>
							<div class="modal-AnswerBody">
								<span class="text-m" id="detail-modal-AnswerBody"></span>
							</div>

							<div class="model-AnswerFile" id="detail-modal-AnswerFile"></div>
						</div>

						<form id="form-answer" name="form" class="answer-form" style="display: none;">
							<div class="contents-area">
								<div class="item-block">
									<div class="input-item">
										<div class="agreement-area" id="agreement-area" style="display: none;"></div>
									</div>
									<div class="input-item">
										<div id="modal-seq" style="display: none;"></div>
										<div class="body-area-div">
											<div class="text-m">本文</div>
											<textarea class="body-area" id="answerBody" name="" value="" placeholder="1000文字まで"></textarea>
											<div class="textcount text-right text-s" id="textCount">0 / 1000</div>
										</div>
										<button type="button" class="btn main tmpBtn" id="answerFileBtn">ファイル添付</button>
										<input type="file" id="answerAttachedFile1" value="" style="display: none;"> <input type="file" id="answerAttachedFile2" value="" style="display: none;">
										<div class="body-note">
											<div class="text-left text-s">ファイル添付は画像形式（jpeg、gif、png、bmp）もしくはpdfファイル、一度に2ファイルまでです。上限10MBまで。</div>
										</div>
										<div class="answerAttachedFileDisplay">
											<p id="answerAttachedFile1Display"></p>
											<p id="answerAttachedFile2Display"></p>
										</div>
									</div>
								</div>
							</div>
						</form>

						<!-- ボタン -->
						<div class="btn-container" id="detailAnswerBtn" style="display: none;">
							<button type="button" class="btn sub" id="detailModalCloseBtn">閉じる</button>
							<button type="button" class="btn main" id="answerBtn">送信</button>
						</div>
						<div class="btn-container" id="detailModalBtn" style="display: none;">
							<button type="button" class="btn sub" id="detailModalCloseBtn">閉じる</button>
						</div>
					</div>
				</div>
			</div>


			<%-- 新規メッセージ作成モーダル スタッフ用 --%>
			<div class="modal-container staff-regist-modal">
				<div class="modal-body">
					<div class="modal-content">

						<div class="contents-area">
							<span class="text-m bold">USS-SSからの連絡</span>
						</div>

						<div class="regist-note">
							<span class="text-m">メッセージは1度限りお送りいただくことが可能です。</span>
						</div>
						<form id="form-inquiry-staff" name="form" class="inquiry-form">
							<div class="contents-area">
								<div class="item-block">
									<!-- 宛先設定 -->
									<div class="input-item">

										<div class="input-answerInfo">
											<div class="input-answererId">
												<div class="text-m">宛先設定</div>
												<input type="text" class="answererId" placeholder="宛先のIDを入力" id="answererId" />
											</div>
											<div class="search-answererId">
												<div class="text-m">宛先検索</div>
												<input type="text" class="searchAnswererId" placeholder="契約番号からIDを検索" id="modal-webAppId" />
											</div>
											<div>
												<button type="button" class="btn main id-search-btn" id="idSearchBtn">検索</button>
											</div>
											<div class="set-answererId" style="display: none;">
												<div class="store-id-set">
													<button type="button" class="btn main id-set-btn store-id-set-btn" id="storeIdSetBtn" disabled="true" style="display: none;">宛先に設定</button>
													<span id="storeInfo"></span>
												</div>
												<div class="customer-id-set">
													<button type="button" class="btn main id-set-btn customer-id-set-btn" id="customerIdSetBtn" disabled="true" style="display: none;">宛先に設定</button>
													<span id="customerInfo"></span>
												</div>
											</div>
										</div>
									</div>

									<div class="input-item">
										<div class="text-m">同意メッセージ設定</div>
										<select class="dropdown agreementBodyKbn" id="agreementBodyKbn">
											<option value="0">同意メッセージ無し</option>
											<option value="1">添付された書類に同意します</option>
											<option value="2">メッセージ2</option>
											<option value="3">メッセージ3</option>
										</select>
									</div>

									<div class="input-item">
										<div class="body-area-div">
											<div class="body-area-div">
												<div class="text-m">本文</div>
												<textarea class="body-area " id="staffInquireBody" name="" value="" placeholder="1000文字まで"></textarea>
												<div class="textcount text-right text-s" id="staffTextCount">0 / 1000</div>
											</div>
											<button type="button" class="btn main tmpBtn" id="staffInquiryFileBtn">ファイル添付</button>
											<input type="file" id="staffInquireAttachedFile1" val="" style="display: none;" /> <input type="file" id="staffInquireAttachedFile2" val="" style="display: none;" />
											<div class="body-note">
												<div class="text-left text-s">ファイル添付は画像形式（jpeg、gif、png、bmp）もしくはpdfファイル、一度に2ファイルまでです。上限10MBまで。</div>
											</div>
											<div class="inquireAttachedFileDisplay">
												<p id="staffInquireAttachedFile1Display"></p>
												<p id="staffInquireAttachedFile2Display"></p>
											</div>

										</div>
									</div>

									<!-- ボタン -->
									<div class="btn-container">
										<button type="button" class="btn sub" id="staffRegistModalCloseBtn">閉じる</button>
										<button type="button" class="btn main" id="staffInquiryBtn">送信</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>

			</div>



		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
	<input type="hidden" id="text_status_0" value="USS-SSへの問い合わせ">
	<input type="hidden" id="text_status_1" value="USS-SSからの連絡">
	<input type="hidden" id="text_newIcon" value="NEW">
	<input type="hidden" id="text_uss-ss" value="USSサポートサービス">
	<input type="hidden" id="text_uss-staff" value="　担当: ">
	<input type="hidden" id="text_sender" value="送信者: ">
	<input type="hidden" id="text_address" value="宛先: ">
	<input type="hidden" id="text_id" value="ID:">
	<input type="hidden" id="text_tmpFile_1" value="添付ファイル1: ">
	<input type="hidden" id="text_tmpFile_2" value="添付ファイル2: ">
	<input type="hidden" id="text_dldeadline" value="ダウンロード期限： ">
	<input type="hidden" id="text_store_id" value="加盟店ID： ">
	<input type="hidden" id="text_customer_id" value="顧客ID： ">
	<input type="hidden" id="text_agreement" value="同意">

</body>
</html>
