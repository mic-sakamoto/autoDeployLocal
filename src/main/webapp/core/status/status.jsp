<%@page import="utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/core/common/import.jsp"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link href="./core/status/status.css?t=<%=df.format(date)%>" rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<script type="text/javascript" src="./core/status/status.js?t=<%=df.format(date)%>"></script>
</head>

<body>
	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>

			<div class="title">
				<div class="title-container">
					<s:if test="viewType==2">
						<span class="title-item">契約内容照会</span>
					</s:if>
					<s:else>
						<span class="title-item">申込状況照会</span>
					</s:else>
				</div>
			</div>

			<div id="display-set" style="display: none;">

				<div class="container">

					<div class="status-container">

						<div class="status-contents-area contents-area">
							<div class="status-area">
								<div class="status-card-block">
									<div class="status-card-head-block">
										<div class="head-content">
											<div class="status-card-head-icon status-color-<s:property value="statusColor" />">

												<% if ("1".equals(role_type) || "2".equals(role_type)) { %>
												<s:if test="status==1 || status==2 || status==3 || status==4 || status==14 || status==15">
													<img class="svg status-svg-icon" src="./images/svg/edit.svg">
												</s:if>
												<s:if test="status==5 || status==7 || status==8 || status==11 || status==12 || status==18 || status==19">
													<img class="svg status-svg-icon" src="./images/svg/exclamation-triangle.svg">
												</s:if>
												<s:if test="status==6 || status==16">
													<img class="svg status-svg-icon" src="./images/svg/hourglass-start.svg">
												</s:if>
												<s:if test="status==9">
													<img class="svg status-svg-icon" src="./images/svg/ban.svg">
												</s:if>
												<s:if test="status==10">
													<img class="svg status-svg-icon" src="./images/svg/check-circle.svg">
												</s:if>
												<s:if test="status==13">
													<img class="svg status-svg-icon" src="./images/svg/hourglass-end.svg">
												</s:if>
												<s:if test="status==17">
													<img class="svg status-svg-icon" src="./images/svg/gavel.svg">
												</s:if>
												<% } %>
												<% if ("3".equals(role_type)) { %>
												<s:if test="status==1 || status==2 || status==3 || status==4 || status==5 || status==6 || status==7 || status==10 || status==11 || status==14 || status==15 || status==16">
													<img class="svg status-svg-icon" src="./images/svg/hourglass-start.svg">
												</s:if>
												<s:if test="status==9 || status==12 || status==13 || status==18">
													<img class="svg status-svg-icon" src="./images/svg/hourglass-end.svg">
												</s:if>
												<s:if test="status==17">
													<img class="svg status-svg-icon" src="./images/svg/gavel.svg">
												</s:if>
												<s:if test="status==19">
													<img class="svg status-svg-icon" src="./images/svg/exclamation-triangle.svg">
												</s:if>



												<% } %>
											</div>
											<div class="status-card-head-label">
												<div class="status-card-head-label-main">
													<s:property value="statusText" />
												</div>
												<s:if test="status==4">
													<div class="status-card-head-label-sub">現在登録中です</div>
												</s:if>
												<s:if test="status==10 || status==11">
													<div class="status-card-head-label-sub">
														<s:property value="limitDate" />
														までに本申込みを完了してください
													</div>
												</s:if>
												<s:if test="status==15">
													<div class="status-card-head-label-sub">現在登録中です。画面上は審査申込時の情報です</div>
												</s:if>
											</div>

										</div>
									</div>

									<div class="status-card-body-block">
										<div class="sub-title">WEB受付ID</div>
										<div class="">
											<s:property value="webAppId" />
											<input type="hidden" id="contractId" value="<s:property value="contractId" />">
											<input type="hidden" id="webAppid" value="<s:property value="webAppId" />">
											<input type="hidden" id="status" value="<s:property value="status" />">
										</div>
										<div class="sub-title">初回審査申込日時</div>
										<div class="">
											<s:property value="firstScreeningAppDate" />
										</div>
										<div class="sub-title">最新審査申込日時</div>
										<div class="">
											<s:property value="screeningAppDate" />
										</div>
										<div class="sub-title">最新本申込日時</div>
										<div class="">
											<s:property value="mainAppDate" />
										</div>

										<% if (!"3".equals(role_type)) { %>
										<div class="sub-title">最新審査申込結果日時</div>
										<div class="">
											<s:property value="screeningAppResDate" />
										</div>
										<div class="sub-title">最新審査結果</div>
										<div class="">
											<s:property value="screeningAppRes" />
										</div>
										<div class="sub-title">最新審査結果理由</div>
										<div class="">
											<s:property value="screeningAppResReason" />
										</div>
										<% } %>

										<div class="sub-title">べリファイ完了日</div>
										<div class="">
											<s:property value="verifiedDate" />
										</div>

										<% if (!"3".equals(role_type)) { %>
										<div class="sub-title">立替日</div>
										<div class="">
											<s:property value="loanDate" />
										</div>
										<% } %>
									</div>

									<div class="status-card-footer-block">
										<div class="button-group">

											<% if ("2".equals(role_type) || "3".equals(role_type)) { %>
											<s:if test="status==7 || status==10 || status==11 || status==14">
												<button id="reScreeningAppBtn" class="btn main">
													<span class="">
														<img class="svg btn-svg-icon" src="./images/svg/edit.svg">再審査申込
													</span>
												</button>
											</s:if>
											<% } %>
											<% if ("2".equals(role_type) || "3".equals(role_type)) { %>
											<s:if test="status==10 || status==14">
												<button id="mainAppBtn" class="btn main">
													<span class="">
														<img class="svg btn-svg-icon" src="./images/svg/edit.svg">本申込
													</span>
												</button>
											</s:if>
											<% } %>
											<% if ("2".equals(role_type)) { %>
											<s:if test="status==7 || status==10 || status==11 || status==14">
												<button id="appCancelBtn" class="btn main">
													<span class="">
														<img class="svg btn-svg-icon" src="./images/svg/close.svg">申込を辞退
													</span>
												</button>
											</s:if>
											<% } %>
										</div>
									</div>
								</div>

								<% if (!"3".equals(role_type)) { %>
								<div class="status-card-block">
									<div class="status-card-head-block two-head">
										<div class="head-content">
											<div class="status-card-head-icon color-light-blue">
												<img class="svg status-svg-icon" src="./images/svg/car.svg">
											</div>
											<div class="status-card-head-label">
												<div class="status-card-head-label-sub">MCCS取付状況</div>
												<div class="status-card-head-label-main">
													<s:property value="mccsStatusText" />
												</div>
											</div>
										</div>
										<div class="head-content">
											<div class="status-card-head-icon color-gray">
												<img class="svg status-svg-icon" src="./images/svg/search.svg">
											</div>
											<div class="status-card-head-label ">
												<div class="status-card-head-label-sub">MCCS取付日</div>
												<div class="status-card-head-label-main">
													<s:property value="mccsAttachDate" />
												</div>
											</div>
										</div>
									</div>

									<div class="status-card-body-block">
										<div class="sub-title">発送日</div>
										<div class="">
											<s:property value="mccsShippingDate" />
										</div>
										<div class="sub-title">作業店取付予定日</div>
										<div class="">
											<s:property value="mccsAttachScheduleDate" />
										</div>
										<div class="sub-title">発送伝票番号</div>
										<div class="">
											<s:property value="mccsSlipNumber" />
										</div>
									</div>
								</div>
								<% } %>
							</div>
							<div class="status-container-side">
								<!-- 								<div class="download-text">操作</div> -->
								<div class="button-group">
									<% if ("2".equals(role_type)) { %>
									<s:if test="status==6 || status==7 || status==10 || status==11 || status==14">
										<button class="btn main">
											<span class="">
												<img class="svg btn-svg-icon" src="./images/svg/bullhorn-solid.svg">顧客入力に切り替えて送信
											</span>
										</button>
									</s:if>
									<% } %>
								</div>
								<div class="download-text">各種書類をダウンロード</div>
								<div class="button-group">

									<button id="pdfUSSAutoLoneBtn" class="btn main">
										<span class="">
											<img class="svg btn-svg-icon" src="./images/svg/download.svg">USSオートローン申込書
										</span>
									</button>
									<s:if test="status==17 || status==19">
										<button id="pdfKozaHenkoTodokeBtn" class="btn main">
											<span class="">
												<img class="svg btn-svg-icon" src="./images/svg/download.svg">口座変更届
											</span>
										</button>
									</s:if>
									<s:if test="status==10 || status==14 || status==15 || status==16 || status==17 || status==19">
										<button id="pdfMCCSAutoLoneBtn" class="btn main">
											<span class="">
												<img class="svg btn-svg-icon" src="./images/svg/download.svg">MCCS付オートローン承諾書
											</span>
										</button>
									</s:if>
									<% if ("2".equals(role_type)) { %>
									<s:if test="status==17 || status==19">
										<button id="pdfLoneTorokuIraishoBtn" class="btn main">
											<span class="">
												<img class="svg btn-svg-icon" src="./images/svg/download.svg">USS-SSローン登録依頼書
											</span>
										</button>
									</s:if>
									<% } %>
									<% if ("2".equals(role_type)) { %>
									<s:if test="status==17 || status==19">
										<button id="pdfSohujoKenSeikyushoBtn" class="btn main">
											<span class="">
												<img class="svg btn-svg-icon" src="./images/svg/download.svg">送付状兼請求書
											</span>
										</button>
									</s:if>
									<% } %>
									<% if ("2".equals(role_type)) { %>
									<s:if test="status==17 || status==19">
										<button id="pdfTorokuzikoHenkoIraishoBtn" class="btn main">
											<span class="">
												<img class="svg btn-svg-icon" src="./images/svg/download.svg">登録事項変更依頼書
											</span>
										</button>
									</s:if>
									<% } %>
									<% if ("1".equals(role_type) || "3".equals(role_type)) { %>
									<s:if test="status==17 || status==19">
										<button id="pdfShiharaiYoteihyoBtn" class="btn main">
											<span class="">
												<img class="svg btn-svg-icon" src="./images/svg/download.svg">支払予定表
											</span>
										</button>
									</s:if>
									<% } %>
								</div>
							</div>

						</div>

						<div class="open-contents-area open-dev">
							<div class="accordion-open-head-block">
								<span class="head-title"> 基本情報 </span>
								<span class="open-icon open"></span>
							</div>
							<div class="accordion-open-block open-target">
								<div class="sub-title">契約番号</div>
								<div class="">
									<s:property value="contractNumber" />
								</div>
								<% if ("1".equals(role_type) || "2".equals(role_type)) { %>
								<div class="sub-title">顧客ID</div>
								<div class="">
									<s:property value="customerId" />
								</div>
								<div class="sub-title">顧客入力状況</div>
								<div class="">
									<s:property value="isCustomerInputDone" />
								</div>
								<div class="sub-title">入力方法</div>
								<div class="">
									<s:property value="inputRoleKbn" />
								</div>
								<% } %>
								<% if ("1".equals(role_type)) { %>
								<div class="sub-title">代行入力区分</div>
								<div class="">
									<s:property value="proxyInput" />
								</div>
								<div class="sub-title">・代行入力情報</div>
								<div class="">
									<s:property value="staffId" />
									<s:property value="staffName" />
								</div>
								<% } %>
							</div>
						</div>

						<input type="hidden" id="contractJson" value=<s:property value="contractJson" escapeHtml="false" />>
						<div class="open-contents-area open-dev">
							<div class="accordion-open-head-block">
								<span class="head-title"> 申込・契約内容 </span>
								<span class="open-icon open"></span>
							</div>

							<div class="accordion-open-block open-target app-open-block">


								<div class="confirm-form moshikomi">
									<div class="confirm-form-header">
										<label class="confirm-form-header-title">
											お申込者様の情報
										</label>
									</div>
									<div class="confirm-form-body confirm-list">
										<div class="confirm-list-item">
											<div class="sub-title">お申込み年月日</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiDateYearText"><s:property value="MoshikomiYear" escapeHtml="false" /></div>
												<span class="text-year"></span>
												<div id="MoshikomiDateMonthText"><s:property value="MoshikomiMonth" escapeHtml="false" /></div>
												<span class="text-month"></span>
												<div id="MoshikomiDateDayText"><s:property value="MoshikomiDay" escapeHtml="false" /></div>
												<span class="text-day"></span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">売買契約年月日</div>
											<div class="confirm-list-item-data">
												<div id="BaibaikeiyakuDateYearText"><s:property value="BaibaikeiyakuDateYear" escapeHtml="false" /></div>
												<span class="text-year"></span>
												<div id="BaibaikeiyakuDateMonthText"><s:property value="BaibaikeiyakuDateMonth" escapeHtml="false" /></div>
												<span class="text-month"></span>
												<div id="BaibaikeiyakuDateDayText"><s:property value="BaibaikeiyakuDateDay" escapeHtml="false" /></div>
												<span class="text-day"></span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">申込区分</div>
											<div class="confirm-list-item-data">
												<div class="radio-value" id="MoshikomiKbnText"><s:property value="MoshikomiKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">お名前</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiNameSeiText"><s:property value="MoshikomiNameSei" escapeHtml="false" /></div>
												<div id="MoshikomiNameMeiText"><s:property value="MoshikomiNameMei" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">お名前（フリガナ）</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiNameSeiKanaText"><s:property value="MoshikomiNameSeiKana" escapeHtml="false" /></div>
												<div id="MoshikomiNameMeiKanaText"><s:property value="MoshikomiNameMeiKana" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">性別</div>
											<div class="confirm-list-item-data">
												<div class="radio-value" id="MoshikomiSeibetsuKbnText"><s:property value="MoshikomiSeibetsuKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">年齢</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiAgeText"><s:property value="MoshikomiAge" escapeHtml="false" /></div>
												<span class="text-age">歳</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">生年月日</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiBirthDateYearText"><s:property value="MoshikomiBirthDateYear" escapeHtml="false" /></div>
												<span class="text-year"></span>
												<div id="MoshikomiBirthDateMonthText"><s:property value="MoshikomiBirthDateMonth" escapeHtml="false" /></div>
												<span class="text-month"></span>
												<div id="MoshikomiBirthDateDayText"><s:property value="MoshikomiBirthDateDay" escapeHtml="false" /></div>
												<span class="text-day"></span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">携帯電話番号</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiMobile1Text"><s:property value="MoshikomiMobile1" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="MoshikomiMobile2Text"><s:property value="MoshikomiMobile2" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="MoshikomiMobile3Text"><s:property value="MoshikomiMobile3" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">自宅電話番号</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiTel1Text"><s:property value="MoshikomiTel1" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="MoshikomiTel2Text"><s:property value="MoshikomiTel2" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="MoshikomiTel3Text"><s:property value="MoshikomiTel3" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">郵便番号</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiPostText"><s:property value="MoshikomiPost" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">住所１</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiAddress1Text"><s:property value="MoshikomiAddress1" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">住所２</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiAddress2Text"><s:property value="MoshikomiAddress2" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">住所（フリガナ）</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiAddressKanaText"><s:property value="MoshikomiAddressKana" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">配偶者</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiHaigushaKbnText"><s:property value="MoshikomiHaigushaKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">住居区分</div>
											<div class="confirm-list-item-data">
												<div id="JukyoKbnText"><s:property value="JukyoKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">住宅ローン・家賃支払い（配偶者含む）</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiLoanKbnText"><s:property value="MoshikomiLoanKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">同居人数（本人含む）</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiDokyoNinzuText"><s:property value="MoshikomiDokyoNinzu" escapeHtml="false" /></div>
												<span class="text-ninzu">人</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">居住年数</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiKyojuYearText"><s:property value="MoshikomiKyojuYear" escapeHtml="false" /></div>
												<span class="text-year">年</span>
												<div id="MoshikomiKyojuMonthText"><s:property value="MoshikomiKyojuMonth" escapeHtml="false" /></div>
												<span class="text-monthes">ヵ月</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">税込年収</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiNenshuText"><s:property value="MoshikomiNenshu" escapeHtml="false" /></div>
												<span class="text-man-yen">万円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">ご職業</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiShokugyoKbnText"><s:property value="MoshikomiShokugyoKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">勤務先・学校情報</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiKinmusakiText"><s:property value="MoshikomiKinmusaki" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">電話番号</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiKinmusakiTel1Text"><s:property value="MoshikomiKinmusakiTel1" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="MoshikomiKinmusakiTel2Text"><s:property value="MoshikomiKinmusakiTel2" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="MoshikomiKinmusakiTel3Text"><s:property value="MoshikomiKinmusakiTel3" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">所在地郵便番号</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiKinmusakiPostText"><s:property value="MoshikomiKinmusakiPost" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">所在地１</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiKinmusakiAddress1Text"><s:property value="MoshikomiKinmusakiAddress1" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">所在地２</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiKinmusakiAddress2Text"><s:property value="MoshikomiKinmusakiAddress2" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">勤続年数（学年）</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiKinzokuYearText"><s:property value="MoshikomiKinzokuYear" escapeHtml="false" /></div>
												<span class="text-year">年</span>
												<div id="MoshikomiKinzokuMonthText"><s:property value="MoshikomiKinzokuMonth" escapeHtml="false" /></div>
												<span class="text-monthes">ヵ月</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">役職</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiKinmusakiYakushokuKbnText"><s:property value="MoshikomiKinmusakiYakushokuKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">所属（部署）</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiKinmusakiShozokuText"><s:property value="MoshikomiKinmusakiShozoku" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">従業員数</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiKinmusakiJugyoinText"><s:property value="MoshikomiKinmusakiJugyoin" escapeHtml="false" /></div>
												<span class="text-ninzu">人</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">業種区分</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiGyoshuKbnText"><s:property value="MoshikomiGyoshuKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">預金残高</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiYokinZandakaText"><s:property value="MoshikomiYokinZandaka" escapeHtml="false" /></div>
												<span class="text-man-yen">万円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">世帯主の氏名</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiSetainushiNameSeiText"><s:property value="MoshikomiSetainushiNameSei" escapeHtml="false" /></div>
												<div id="MoshikomiSetainushiNameMeiText"><s:property value="MoshikomiSetainushiNameMei" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">お申込者との関係</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiSetainushiRelateKbnText"><s:property value="MoshikomiSetainushiRelateKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">居住</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiKyojuKbnText"><s:property value="MoshikomiKyojuKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">世帯主の税込年収</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiSetainushiNenshuText"><s:property value="MoshikomiSetainushiNenshu" escapeHtml="false" /></div>
												<span class="text-man-yen">万円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">世帯主のクレジット月額債務</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiSetainushiCreditSaimuText"><s:property value="MoshikomiSetainushiCreditSaimu" escapeHtml="false" /></div>
												<span class="text-man-yen">万円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">年金受給情報</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiNenkinJukyuKbnText"><s:property value="MoshikomiNenkinJukyuKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title"></div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiNenkinJukyuKbnSonotaText"><s:property value="MoshikomiNenkinJukyuKbnSonotaText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">年間受給額</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiNenkinJukyuPriceText"><s:property value="MoshikomiNenkinJukyuPrice" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item main-view">
											<div class="sub-title">お申込者様のベリファイ希望日時</div>
											<div class="confirm-list-item-data">
												<div id="MoshikomiVerifyDate"></div>
												<span class=""></span>
												<div id="MoshikomiVerifyTimeFromText"><s:property value="MoshikomiVerifyTimeFrom" escapeHtml="false" /></div>
												<span class="">頃</span>
												<span class="">～</span>
												<div id="MoshikomiVerifyTimeToText"><s:property value="MoshikomiVerifyTimeTo" escapeHtml="false" /></div>
												<span class="">頃</span>
											</div>
										</div>
									</div>
								</div>

								<div class="confirm-form hoshonin">
									<div class="confirm-form-header">
										<label class="confirm-form-header-title">
											連帯保証人様の情報
										</label>
									</div>
									<div class="confirm-form-body confirm-list">
										<div class="confirm-list-item">
											<div class="sub-title">お名前</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninNameSeiText"><s:property value="HoshoninNameSei" escapeHtml="false" /></div>
												<div id="HoshoninNameMeiText"><s:property value="HoshoninNameMei" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">お名前（フリガナ）</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninNameSeiKanaText"><s:property value="HoshoninNameSeiKana" escapeHtml="false" /></div>
												<div id="HoshoninNameMeiKanaText"><s:property value="HoshoninNameMeiKana" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">性別</div>
											<div class="confirm-list-item-data">
												<div class="radio-value" id="HoshoninSeibetsuKbnText"><s:property value="HoshoninSeibetsuKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">年齢</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninAgeText"><s:property value="HoshoninAge" escapeHtml="false" /></div>
												<span class="text-age">歳</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">生年月日</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninBirthDateYearText"><s:property value="HoshoninBirthDateYear" escapeHtml="false" /></div>
												<span class="text-year"></span>
												<div id="HoshoninBirthDateMonthText"><s:property value="HoshoninBirthDateMonth" escapeHtml="false" /></div>
												<span class="text-month"></span>
												<div id="HoshoninBirthDateDayText"><s:property value="HoshoninBirthDateDay" escapeHtml="false" /></div>
												<span class="text-day"></span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">携帯電話番号</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninMobile1Text"><s:property value="HoshoninMobile1" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="HoshoninMobile2Text"><s:property value="HoshoninMobile2" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="HoshoninMobile3Text"><s:property value="HoshoninMobile3" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">自宅電話番号</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninTel1Text"><s:property value="HoshoninTel1" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="HoshoninTel2Text"><s:property value="HoshoninTel2" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="HoshoninTel3Text"><s:property value="HoshoninTel3" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">郵便番号</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninPostText"><s:property value="HoshoninPost" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">住所１</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninAddress1Text"><s:property value="HoshoninAddress1" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">住所２</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninAddress2Text"><s:property value="HoshoninAddress2" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">住所（フリガナ）</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninAddressKanaText"><s:property value="HoshoninAddressKana" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">税込年収</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninNenshuText"><s:property value="HoshoninNenshu" escapeHtml="false" /></div>
												<span class="text-man-yen">万円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">住居区分</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninJukyoKbnText"><s:property value="HoshoninJukyoKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">住宅ローン・家賃支払い（配偶者含む）</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninLoanKbnText"><s:property value="HoshoninLoanKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">配偶者</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninHaigushaKbnText"><s:property value="HoshoninHaigushaKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">同居人数（本人含む）（生計をマイナスにする別居家族含む）</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninDokyoNinzuText"><s:property value="HoshoninDokyoNinzu" escapeHtml="false" /></div>
												<span class="text-ninzu">人</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">お申込者とのご関係</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninMoshikomiRelateKbnText"><s:property value="HoshoninMoshikomiRelateKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title"></div>
											<div class="confirm-list-item-data">
												<div id="HoshoninMoshikomiRelateKbnSonotaText"><s:property value="HoshoninMoshikomiRelateKbnSonota" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">ご職業</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninShokugyoKbnText"><s:property value="HoshoninShokugyoKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">勤務先・学校情報</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninKinmusakiText"><s:property value="HoshoninKinmusaki" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">電話番号</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninKinmusakiTel1Text"><s:property value="HoshoninKinmusakiTel1" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="HoshoninKinmusakiTel2Text"><s:property value="HoshoninKinmusakiTel2" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="HoshoninKinmusakiTel3Text"><s:property value="HoshoninKinmusakiTel3" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">所在地郵便番号</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninKinmusakiPostText"><s:property value="HoshoninKinmusakiPost" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">所在地１</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninKinmusakiAddress1Text"><s:property value="HoshoninKinmusakiAddress1" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">所在地２</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninKinmusakiAddress2Text"><s:property value="HoshoninKinmusakiAddress2" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">勤続年数（学年）</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninKinzokuYearText"><s:property value="HoshoninKinzokuYear" escapeHtml="false" /></div>
												<span class="text-year">年</span>
												<div id="HoshoninKinzokuMonthText"><s:property value="HoshoninKinzokuMonth" escapeHtml="false" /></div>
												<span class="text-monthes">ヵ月</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">役職</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninKinmusakiYakushokuKbnText"><s:property value="HoshoninKinmusakiYakushokuKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">所属</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninKinmusakiShozokuText"><s:property value="HoshoninKinmusakiShozoku" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">従業員数</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninKinmusakiJugyoinText"><s:property value="HoshoninKinmusakiJugyoin" escapeHtml="false" /></div>
												<span class="text-ninzu">人</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">業種区分</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninGyoshuKbnText"><s:property value="HoshoninGyoshuKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">年金受給情報</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninNenkinJukyuKbnText"><s:property value="HoshoninNenkinJukyuKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item main-view">
											<div class="sub-title">連帯保証人様のベリファイ希望日程</div>
											<div class="confirm-list-item-data">
												<div id="HoshoninVerifyDateText"><s:property value="HoshoninVerifyDate" escapeHtml="false" /></div>
												<span class=""></span>
												<div id="HoshoninVerifyTimeFromText"><s:property value="HoshoninVerifyTimeFrom" escapeHtml="false" /></div>
												<span class="">頃</span>
												<span class="">～</span>
												<div id="HoshoninVerifyTimeToText"><s:property value="HoshoninVerifyTimeTo" escapeHtml="false" /></div>
												<span class="">頃</span>
											</div>
										</div>
									</div>
								</div>

								<div class="confirm-form okuruma">
									<div class="confirm-form-header">
										<label class="confirm-form-header-title">
											お車の情報
										</label>
									</div>
									<div class="confirm-form-body confirm-list">
										<div class="confirm-list-item">
											<div class="sub-title">Connected(MCCS)申込</div>
											<div class="confirm-list-item-data">
												<div id="MccsMoshikomiKbnText"><s:property value="MccsMoshikomiKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">シフト</div>
											<div class="confirm-list-item-data">
												<div id="ShiftKbnText"><s:property value="ShiftKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">リモートスターター</div>
											<div class="confirm-list-item-data">
												<div id="RemoteStarterKbnText"><s:property value="RemoteStarterKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">販売の条件となっている商品・権利・役務の有無</div>
											<div class="confirm-list-item-data">
												<div id="HanbaiJokenKbnText"><s:property value="HanbaiJokenKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">主な使用目的</div>
											<div class="confirm-list-item-data">
												<div id="ShiyoMokutekiKbnText"><s:property value="ShiyoMokutekiKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">新車・中古車</div>
											<div class="confirm-list-item-data">
												<div id="UsedKbnText"><s:property value="UsedKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">エンジンスタート</div>
											<div class="confirm-list-item-data">
												<div id="EngineStartKbnText"><s:property value="EngineStartKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item mccs-car-info">
											<div class="sub-title">メーカー</div>
											<div class="confirm-list-item-data">
												<div id="MakerText"><s:property value="Maker" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item mccs-car-info">
											<div class="sub-title">車名</div>
											<div class="confirm-list-item-data">
												<div id="ShameiText"><s:property value="Shamei" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item mccs-car-info">
											<div class="sub-title">型式</div>
											<div class="confirm-list-item-data">
												<div id="KatashikiText"><s:property value="Katashiki" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item mccs-car-info">
											<div class="sub-title">初年度</div>
											<div class="confirm-list-item-data">
												<div id="ShonendoYearText"><s:property value="ShonendoYear" escapeHtml="false" /></div>
												<span class="text-year"></span>
												<div id="ShonendoMonthText"><s:property value="ShonendoMonth" escapeHtml="false" /></div>
												<span class="text-month"></span>
											</div>
										</div>
										<div class="confirm-list-item mccs-manual-ipt">
											<div class="sub-title">車名</div>
											<div class="confirm-list-item-data">
												<div id="ShameiMLText"><s:property value="ShameiML" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item mccs-manual-ipt">
											<div class="sub-title">型式</div>
											<div class="confirm-list-item-data">
												<div id="KatashikiMLText"><s:property value="KatashikiML" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item mccs-manual-ipt">
											<div class="sub-title">初年度</div>
											<div class="confirm-list-item-data">
												<div id="ShonendYearML"><s:property value="ShonendYearML" escapeHtml="false" /></div>
												<span class="text-year"></span>
												<div id="ShonendMonthML"><s:property value="ShonendMonthML" escapeHtml="false" /></div>
												<span class="text-month"></span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">グレード</div>
											<div class="confirm-list-item-data">
												<div id="GradeText"><s:property value="Grade" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">車台番号</div>
											<div class="confirm-list-item-data">
												<div id="ShadaiNum"><s:property value="ShadaiNum" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">走行距離</div>
											<div class="confirm-list-item-data">
												<div id="SokoKyoriText"><s:property value="SokoKyori" escapeHtml="false" /></div>
												<span class="text-km">km</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">登録番号</div>
											<div class="confirm-list-item-data">
												<div id="TorokuNumText"><s:property value="TorokuNum" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">車体色</div>
											<div class="confirm-list-item-data">
												<div id="ColorText"><s:property value="Color" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">排気量</div>
											<div class="confirm-list-item-data">
												<div id="HaikiryoText"><s:property value="Haikiryo" escapeHtml="false" /></div>
												<span class="text-cc">cc</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">所有者</div>
											<div class="confirm-list-item-data">
												<div id="OwnerText"><s:property value="Owner" escapeHtml="false" /></div>
											</div>
										</div>
									</div>
								</div>

								<div class="confirm-form kingaku">
									<div class="confirm-form-header">
										<label class="confirm-form-header-title">
											金額・その他の情報
										</label>
									</div>
									<div class="confirm-form-body confirm-list">
										<div class="confirm-list-item">
											<div class="sub-title">車両本体価格</div>
											<div class="confirm-list-item-data">
												<div id="CarPriceText"><s:property value="CarPrice" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">MCCS取付</div>
											<div class="confirm-list-item-data">
												<div id="MccsAttachPriceText"><s:property value="MccsAttachPrice" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">付属品</div>
											<div class="confirm-list-item-data">
												<div id="FuzokuhinPriceText"><s:property value="FuzokuhinPrice" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">諸費用</div>
											<div class="confirm-list-item-data">
												<div id="OtherPriceText"><s:property value="OtherPrice" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">車検・整備費用</div>
											<div class="confirm-list-item-data">
												<div id="ShakenPriceText"><s:property value="ShakenPrice" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">①現金合計額</div>
											<div class="confirm-list-item-data">
												<div id="TotalPriceText"><s:property value="TotalPrice" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">②頭金 現金（お申込金)</div>
											<div class="confirm-list-item-data">
												<div id="AppPriceText"><s:property value="AppPrice" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">②頭金 下取車充当額</div>
											<div class="confirm-list-item-data">
												<div id="ShitadoriPriceText"><s:property value="ShitadoriPrice" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">③残金（①-②）</div>
											<div class="confirm-list-item-data">
												<div id="RemainPriceText"><s:property value="RemainPrice" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">お支払期間 開始</div>
											<div class="confirm-list-item-data">
												<div id="ShiharaiStartYearText"><s:property value="ShiharaiStartYear" escapeHtml="false" /></div>
												<span class="text-year"></span>
												<div id="ShiharaiStartMonthText"><s:property value="ShiharaiStartMonth" escapeHtml="false" /></div>
												<span class="text-month"></span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">お支払期間 終了</div>
											<div class="confirm-list-item-data">
												<div id="ShiharaiEndYearText"><s:property value="ShiharaiEndYear" escapeHtml="false" /></div>
												<span class="text-year"></span>
												<div id="ShiharaiEndMonthText"><s:property value="ShiharaiEndMonth" escapeHtml="false" /></div>
												<span class="text-month"></span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">お支払回数</div>
											<div class="confirm-list-item-data">
												<div id="ShiharaiKaisuText"><s:property value="ShiharaiKaisu" escapeHtml="false" /></div>
												<span class="text-kaisu"></span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">口座振替日</div>
											<div class="confirm-list-item-data">
												<div id="KozaHurikaebiText">毎月27日</div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">ボーナス加算月</div>
											<div class="confirm-list-item-data">
												<div id="BonusKasanMonthKbnText"><s:property value="BonusKasanMonthKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">加算支払金の回数</div>
											<div class="confirm-list-item-data">
												<div id="BonusKasanKaisuText"><s:property value="BonusKasanKaisu" escapeHtml="false" /></div>
												<span class="text-kaisu">回</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">加算支払金の金額</div>
											<div class="confirm-list-item-data">
												<div id="BonusKasanPriceText"><s:property value="BonusKasanPrice" escapeHtml="false" /></div>
												<span class="text-sen-yen">千円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">第１回分割支払金</div>
											<div class="confirm-list-item-data">
												<div id="BunkatsuShiharai1Text"><s:property value="BunkatsuShiharai1" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">第２回以降分割支払金</div>
											<div class="confirm-list-item-data">
												<div id="BunkatsuShiharai2Text"><s:property value="BunkatsuShiharai2" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">支払回数</div>
											<div class="confirm-list-item-data">
												<div id="BunkatsuKaisuText"><s:property value="BunkatsuKaisu" escapeHtml="false" /></div>
												<span class="text-kaisu">回</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">④分割払手数料</div>
											<div class="confirm-list-item-data">
												<div id="BunkatsuTesuryoText"><s:property value="BunkatsuTesuryo" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">⑤分割支払金合計（③＋④）</div>
											<div class="confirm-list-item-data">
												<div id="BunkatsuShiharaiTotalText"><s:property value="BunkatsuShiharaiTotal" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">⑥お支払総額（②＋⑤）</div>
											<div class="confirm-list-item-data">
												<div id="TotalShiharaiText"><s:property value="TotalShiharai" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">所有権留保費用（税込）</div>
											<div class="confirm-list-item-data">
												<div id="ShoyukenRyuhoPriceText"><s:property value="ShoyukenRyuhoPrice" escapeHtml="false" /></div>
												<span class="text-yen">円</span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">納車日区分</div>
											<div class="confirm-list-item-data">
												<div id="NoshaDateKbnText"><s:property value="NoshaDateKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">納車年月日</div>
											<div class="confirm-list-item-data">
												<div id="NoshaDateYearText"><s:property value="NoshaDateYear" escapeHtml="false" /></div>
												<span class="text-year"></span>
												<div id="NoshaDateMonthText"><s:property value="NoshaDateMonth" escapeHtml="false" /></div>
												<span class="text-month"></span>
												<div id="NoshaDateDayText"><s:property value="NoshaDateDay" escapeHtml="false" /></div>
												<span class="text-day"></span>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">販売店からの連絡事項</div>
											<div class="confirm-list-item-data">
												<div id="StoreRenrakuJikoText"><s:property value="StoreRenrakuJiko" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">加盟店番号</div>
											<div class="confirm-list-item-data">
												<div id="StoreIdText"><s:property value="StoreId" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">条件コード</div>
											<div class="confirm-list-item-data">
												<div id="JokenCodeText"><s:property value="JokenCode" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">販売店</div>
											<div class="confirm-list-item-data">
												<div id="StoreNameText"><s:property value="StoreName" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">代表者名</div>
											<div class="confirm-list-item-data">
												<div id="StoreDaihyoNameText"><s:property value="StoreDaihyoName" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">住所</div>
											<div class="confirm-list-item-data">
												<div id="StoreAddressText"><s:property value="StoreAddress" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">電話番号</div>
											<div class="confirm-list-item-data">
												<div id="StoreTel1Text"><s:property value="StoreTel1" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="StoreTel2Text"><s:property value="StoreTel2" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="StoreTel3Text"><s:property value="StoreTel3" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">販売担当者氏名</div>
											<div class="confirm-list-item-data">
												<div id="StoreTantoName"><s:property value="StoreTantoName" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">販売担当者電話番号</div>
											<div class="confirm-list-item-data">
												<div id="StoreTantoTel1Text"><s:property value="StoreTantoTel1" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="StoreTantoTel2Text"><s:property value="StoreTantoTel2" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="StoreTantoTel3Text"><s:property value="StoreTantoTel3" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">代行入力申込方法</div>
											<div class="confirm-list-item-data">
												<div id="DaikoMoshikomiKbnText"><s:property value="DaikoMoshikomiKbnText" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">送付先名称</div>
											<div class="confirm-list-item-data">
												<div id="MccsSofusakiNameText"><s:property value="MccsSofusakiName" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">送付先郵便番号</div>
											<div class="confirm-list-item-data">
												<div id="MccsSofusakiPostText"><s:property value="MccsSofusakiPost" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">MCCS送付先住所１</div>
											<div class="confirm-list-item-data">
												<div id="MccsSofusakiAddress1Text"><s:property value="MccsSofusakiAddress1" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">MCCS送付先住所２</div>
											<div class="confirm-list-item-data">
												<div id="MccsSofusakiAddress2Text"><s:property value="MccsSofusakiAddress2" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">MCCS送付先住所３</div>
											<div class="confirm-list-item-data">
												<div id="MccsSofusakiAddress3Text"><s:property value="MccsSofusakiAddress3" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">発送先電話番号</div>
											<div class="confirm-list-item-data">
												<div id="MccsSofusakiTel1Text"><s:property value="MccsSofusakiTel1" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="MccsSofusakiTel2Text"><s:property value="MccsSofusakiTel2" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="MccsSofusakiTel3Text"><s:property value="MccsSofusakiTel3" escapeHtml="false" /></div>

											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">受付担当者名</div>
											<div class="confirm-list-item-data">
												<div id="MccsUketsukeTantoNameText"><s:property value="MccsUketsukeTantoName" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">受付担当者電話番号</div>
											<div class="confirm-list-item-data">
												<div id="MccsUketsukeTantoTel1Text"><s:property value="MccsUketsukeTantoTel1" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="MccsUketsukeTantoTel2Text"><s:property value="MccsUketsukeTantoTel2" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="MccsUketsukeTantoTel3Text"><s:property value="MccsUketsukeTantoTel3" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">取付担当者名</div>
											<div class="confirm-list-item-data">
												<div id="MccsToritsukeTantoNameText"><s:property value="MccsToritsukeTantoName" escapeHtml="false" /></div>
											</div>
										</div>
										<div class="confirm-list-item">
											<div class="sub-title">取付担当者電話番号</div>
											<div class="confirm-list-item-data">
												<div id="MccsToritsukeTantoTel1Text"><s:property value="MccsToritsukeTantoTel1" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="MccsToritsukeTantoTel2Text"><s:property value="MccsToritsukeTantoTel2" escapeHtml="false" /></div>
												<span class="text-hyphen">-</span>
												<div id="MccsToritsukeTantoTel3Text"><s:property value="MccsToritsukeTantoTel3" escapeHtml="false" /></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<%-- 審査申込内容入力方法選択モーダル --%>
					<div class="modal-container select-input-method-modal">
						<div class="modal-body">
							<div class="modal-content">
								<%@ include file="/core/select-input-method/select-input-method.jsp"%>
							</div>
						</div>
					</div>
		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
</body>
</html>
