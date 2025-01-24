<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/core/common/import.jsp"%>
<!--ページ専用js-->

<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
</head>
<link href="./core/application/application.css?t=<%=df.format(date)%>" rel="stylesheet">
<script type="text/javascript" src="./core/application/application.js?t=<%=df.format(date)%>"></script>
<link href="./core/application-confirm/application-confirm.css?t=<%=df.format(date)%>" rel="stylesheet">
<script type="text/javascript" src="./core/application-confirm/application-confirm.js?t=<%=df.format(date)%>"></script>


<html>
<body>

	<input type="hidden" class="" id="text_tohon" value="会社の謄本">
	<input type="hidden" class="" id="text_inputcomp" value="入力内容を保存しました。">
	<input type="hidden" class="" id="text_changeinputmode" value="顧客入力に切替えました。">
	<input type="hidden" class="" id="text_resendloginguide" value="ログイン手順を再送信しました。">
	<input type="hidden" class="" id="text_error" value="通信に失敗しました。">

	<input type="hidden" class="" id="appKbn" value=<s:property value="appKbn" escapeHtml="false" />>
	<input type="hidden" class="" id="statusKbn" value=<s:property value="statusKbn" escapeHtml="false" />>
	<input type="hidden" class="" id="inputModeKbn" value=<s:property value="inputModeKbn" escapeHtml="false" />>
	<input type="hidden" class="" id="inputRoleKbn" value=<s:property value="inputRoleKbn" escapeHtml="false" />>
	<input type="hidden" class="" id="moshikomiKbn" value=<s:property value="moshikomiKbn" escapeHtml="false" />>
	<input type="hidden" class="" id="isTempExist" value=<s:property value="isTempExist" escapeHtml="false" />>
	<input type="hidden" class="" id="isCustomerInputDone" value=<s:property value="isCustomerInputDone" escapeHtml="false" />>

	<input type="hidden" name="webAppId" id="webAppId" value=<s:property value="webAppId" escapeHtml="false" /> />

	<input type="hidden" name="inputMap" id="inputMap" value=<s:property value="model.inputMapJson" escapeHtml="false" /> />
	<input type="hidden" id="contractId" value=<s:property value="contractId" escapeHtml="false" />>
	<input type="hidden" id="daikoStaffId" value=<s:property value="daikoStaffId" escapeHtml="false" />>


	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>
			<div id="req-tab" class="active">
				<div id="req-tab-contents">
					<div id="close-btn-area" class="hide">
						<button id="close-tab"></button>
					</div>
					<div id="required-items-area">
						<div style="margin: auto">
							残り必須項目
							<span id="remaining-items">0</span>
							個
						</div>
					</div>
					<div id="func-btn-area">
						<button type="button" class="btn main store-view" id="save-input">入力内容を保存</button>
						<button type="button" class="btn main store-view" id="delete-application">審査申込内容を削除</button>
						<button type="button" class="btn main" id="download-docs">契約書類をダウンロード</button>
						<button type="button" class="btn main resend-modal-open store-view change-input-customer" id="change-input-mode">顧客入力に切替えて送信</button>
						<button type="button" class="btn main resend-modal-open store-view change-input-customer hide" id="resend-login-guide">顧客にログイン手順を再送信</button>
					</div>
				</div>
			</div>
			<div class="container">

				<div class="modal-container resend-modal">
					<div class="modal-body">
						<div class="modal-content">
							<div class="text-s">
								お申込者様情報入力方法を「ご契約者様専用ページで顧客が入力」に切替え、顧客にご契約者様専用ページのログイン手順を送信します。
								<br>
								送信時、入力内容が保存されます。なお、
								<span class="bold">切替後は「このまま加盟店ページで入力」に戻すことはできません。</span>
							</div>
							<div class="bold">ご契約者様専用サイトのログイン手順送信</div>
							<div class="text-s">
								お申込者様が直接ご入力できるページのURLを、ご指定のメールアドレスもしくは携帯電話番号にご案内します。
								<br>
								なお、ご案内ページの有効期間は72時間です。
							</div>
							<div class="radio-container">
								<label>
									<input type="radio" class="radio-btn" id="" name="resendLoginMethod" value="mail" checked="checked">
									メールアドレス
								</label>
								<label>
									<input type="radio" class="radio-btn" id="" name="resendLoginMethod" value="tel">
									SMS送信
								</label>
							</div>
							<div class="resend-mail">
								<input type="email" class="mail-input width-full" id="resendMailAddress">
							</div>
							<div class="resend-tel grid-tel hide">
								<input type="text" class="tel-num only-num" id="resendMobileNum1" />
								<label class="hyphen">-</label>
								<input type="text" class="tel-num only-num" id="resendMobileNum2" />
								<label class="hyphen">-</label>
								<input type="text" class="tel-num only-num" id="resendMobileNum3" />
							</div>
							<div class="btn-area">
								<button type="button" class="btn sub resend-modal-close" id="resendCancel">キャンセル</button>
								<button type="button" class="btn main resend-modal-close" id="resend">送信</button>
							</div>
						</div>
					</div>
				</div>

				<div class="modal-container sign-modal">
					<div class="modal-body">
						<div class="modal-content">
							<div class="sign color-input-blue" id="sign"></div>
							<div class="modal-btn">
								<button type="button" class="btn main sign-confirm">確定</button>
								<button type="button" class="btn sub sign-reset">リセット</button>
								<button type="button" class="btn sub sign-modal-close">閉じる</button>
							</div>
						</div>
					</div>
				</div>

				<div class="modal-container sign-modal-h">
					<div class="modal-body">
						<div class="modal-content">
							<div class="sign color-input-blue" id="sign-h"></div>
							<div class="modal-btn">
								<button type="button" class="btn main sign-h-confirm">確定</button>
								<button type="button" class="btn sub sign-h-reset">リセット</button>
								<button type="button" class="btn sub sign-modal-h-close">閉じる</button>
							</div>
						</div>
					</div>
				</div>

				<div class="input-container">

					<div class="" id="progressbar-area">
						<div class="custom-progressbar">
							<ul class="progressbar">
								<li class="progress-icon active form-moshikomi">
									<p>お申込者様の情報</p>
								</li>
								<li class="progress-icon inactive form-hoshonin">
									<p>連帯保証人様の情報</p>
								</li>
								<li class="progress-icon inactive form-okuruma">
									<p>お車の情報</p>
								</li>
								<li class="progress-icon inactive form-kingaku">
									<p>金額・その他の情報</p>
								</li>
								<li class="progress-icon inactive form-input-confirm">
									<p>入力内容のご確認</p>
								</li>
							</ul>
						</div>
					</div>

					<div class="title-area-for-app">
						<div class="title-detail text-l">
							入力エリアの背景色が青の場合は「任意入力」で、橙色の場合は「入力必須」になります。
							<br>
							ブラウザの「戻る」および「更新」ボタンは使用しないでください。
						</div>
					</div>

					<form id="form-input" name="form" class="form" onsubmit="return false;">

						<div class="contents-area input-contents">
							<!-- お申込者様の情報 -->
							<div id="form-moshikomi" class="input-form active">
								<div class="item-block">
									<div class="border-area">
										<div class="p-10 text-s">
											このお申込は、申込者が、別掲する「USS-SSオートローンお申込み内容」の各規約および「個人情報の取り扱いに関する同意条項」を理解し同意したうえで申込者が直接もしくは加盟店に本人確認資料を添えて代行入力を依頼するものです。
											<br>
											なお、加盟店はUSS-SSオートローン制度の取扱基本契約第５条２項に基づき、本人の意思を確認し、本人の意思に沿って申込内容を電子的な手段によって送信することに相違ありません。
										</div>
									</div>
									<!-- お申込み年月日 -->
									<div class="input-item">
										<div class="text-m bold item-title">お申込み年月日</div>
										<div class="text-s">申込日は初回審査申込日となります。</div>
										<div class=" grid-3">
											<select class="year" id="i-MoshikomiDateYear" disabled>
											</select>
											<select class="month" id="i-MoshikomiDateMonth" disabled>
											</select>
											<select class="day" id="i-MoshikomiDateDay" disabled>
											</select>
										</div>
									</div>
									<!-- 売買契約年月日 -->
									<div class="input-item">
										<div class="text-m bold item-title">売買契約年月日</div>
										<div class="text-s">
											申込日と異なる場合にご入力ください。
											<br>
											なお、審査申込時は任意です。本申込時に正しい情報を設定してください。
										</div>
										<div class=" grid-3">
											<select class="main-req store-ipt" id="i-BaibaikeiyakuDateYear">
											</select>
											<select class="main-req store-ipt" id="i-BaibaikeiyakuDateMonth">
											</select>
											<select class="main-req store-ipt" id="i-BaibaikeiyakuDateDay">
											</select>
										</div>
									</div>
									<!-- お申込み区分 -->
									<div class="input-item">
										<div class="text-m bold item-title">お申込み区分</div>
										<div class="radio-container">
											<label>
												<input type="radio" class="" id="i-MoshikomiKbnF" name="i-MoshikomiKbn" value="1" disabled />
												個人
											</label>
											<label>
												<input type="radio" class="i-MoshikomiKbnS" name="i-MoshikomiKbn" value="2" disabled />
												法人
											</label>
										</div>
									</div>
									<!-- お申込み者様情報タイトル -->
									<div class="items-title text-l bold">お申込者様情報</div>
									<!-- お名前 -->
									<div class="input-item">
										<div class="text-m bold item-title">お名前 *</div>
										<div class="text-s">
											全角文字で、姓と名で合計２９文字以内で入力してください。
											<br>
											外国籍の方も姓と名で分けて入力してください。
										</div>
										<div class="grid-2">
											<div>
												<input type="text" class="width-full zenkaku non-num name-sei kojin-req hojin-req cstmr-ipt scr-ipt re-app-res" id="i-MoshikomiNameSei" />
											</div>
											<div>
												<input type="text" class="width-full zenkaku non-num name-mei kojin-req cstmr-ipt scr-ipt re-app-res" id="i-MoshikomiNameMei" />
											</div>
										</div>
									</div>
									<!-- お名前（ﾌﾘｶﾞﾅ）-->
									<div class="input-item">
										<div class="text-m bold item-title">お名前（ﾌﾘｶﾞﾅ） *</div>
										<div class="text-s">
											半角文字で、姓と名で合計３９文字以内で入力してください。
											<br>
											外国籍の方も姓と名で分けて入力してください。
										</div>
										<div class="grid-2">
											<div>
												<input type="text" class="width-full hankaku non-num non-alpha furigana-sei kojin-req hojin-req cstmr-ipt scr-ipt re-app-res" id="i-MoshikomiNameSeiKana" />
											</div>
											<div>
												<input type="text" class="width-full hankaku non-num non-alpha furigana-mei kojin-req cstmr-ipt scr-ipt re-app-res" id="i-MoshikomiNameMeiKana" />
											</div>
										</div>
									</div>
									<!-- 性別 -->
									<div class="input-item">
										<div class="text-m bold item-title">性別 *</div>
										<div class="radio-container">
											<label>
												<input type="radio" name="i-MoshikomiSeibetsuKbn" id="i-MoshikomiSeibetsuKbnF" class="cstmr-ipt scr-ipt kojin-req re-app-res" value="1" />
												男性
											</label>
											<label>
												<input type="radio" name="i-MoshikomiSeibetsuKbn" id="i-MoshikomiSeibetsuKbnS" class="cstmr-ipt scr-ipt kojin-req re-app-res" value="2" />
												女性
											</label>
										</div>
									</div>
									<!-- 年齢 -->
									<div class="input-item">
										<div class="text-m bold item-title">年齢</div>
										<div class="text-s">半角数字で入力してください</div>
										<div>
											<div class=" text-with-unit">
												<input type="text" class="num cstmr-ipt scr-ipt kojin-req re-app-res" id="i-MoshikomiAge" />
												<span class="unit">歳</span>
											</div>
											<div class="text-s error hide">入力に誤りがあります</div>
										</div>
									</div>
									<!-- 生年月日 -->
									<div class="input-item">
										<div class="text-m bold item-title">生年月日 *</div>
										<div class=" grid-3">
											<select class="year cstmr-ipt scr-ipt kojin-req re-app-res" id="i-MoshikomiBirthDateYear">
											</select>
											<select class="month cstmr-ipt scr-ipt kojin-req re-app-res" id="i-MoshikomiBirthDateMonth">
											</select>
											<select class="day cstmr-ipt scr-ipt kojin-req re-app-res" id="i-MoshikomiBirthDateDay">
											</select>
										</div>
									</div>
									<!-- 携帯電話番号 -->
									<div class="input-item">
										<div class="text-m bold item-title kojin-view">電話：携帯（連絡先） *</div>
										<div class="text-m bold item-title hojin-view">電話：代表電話 *</div>
										<div class="text-s">
											半角数字で入力してください。
											<br>
											もし携帯電話番号がない場合、こちらの項目に自宅電話番号を入力してください。
										</div>
										<div class=" grid-tel">
											<div>
												<input type="text" class="width-full tel-num num cstmr-ipt kojin-req hojin-req scr-ipt re-app-res" id="i-MoshikomiMobile1" />
												<div class="text-s error hide">半角数字で6桁以内で入力してください。</div>
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num cstmr-ipt kojin-req hojin-req scr-ipt re-app-res" id="i-MoshikomiMobile2" />
												<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num cstmr-ipt kojin-req hojin-req scr-ipt re-app-res" id="i-MoshikomiMobile3" />
												<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
											</div>
										</div>
									</div>
									<!--自宅電話番号 -->
									<div class="input-item kojin-view">

										<div class="text-m bold item-title">自宅電話番号</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" grid-tel">
											<div>
												<input type="text" class="width-full tel-num num cstmr-ipt scr-ipt re-app-res" id="i-MoshikomiTel1" />
												<div class="text-s error hide">半角数字で6桁以内で入力してください。</div>
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num cstmr-ipt scr-ipt re-app-res" id="i-MoshikomiTel2" />
												<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num cstmr-ipt scr-ipt re-app-res" id="i-MoshikomiTel3" />
												<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
											</div>
										</div>
									</div>
									<!-- お申込み者様情報タイトル -->
									<div class="items-title text-l bold">ご住所 *</div>
									<!-- 郵便番号 -->
									<div class="input-item">
										<div class="text-m bold item-title">郵便番号</div>
										<div class="text-s">半角数字で入力してください</div>
										<div>
											<div class=" input-searchable">
												<input type="text" class="num cstmr-ipt kojin-req hojin-req scr-ipt re-app-res" placeholder="0000000" id="i-MoshikomiPost" />
												<button class="btn main  auto-address-btn" id="moshikomi-address-btn">住所に反映</button>
											</div>
											<div class="text-s error hide">半角文字で7桁以内で入力してください。</div>
										</div>
									</div>
									<!-- 住所１ -->
									<div class="input-item">
										<div class="text-m bold item-title">住所１</div>
										<div class="text-s">
											全角文字で、２７文字以内で入力してください。
											<br>
											住所１に入りきらない場合に住所２に入力してください。
										</div>
										<div class="width-full">
											<input type="text" class="zenkaku address input-address auto-address width-full cstmr-ipt kojin-req hojin-req scr-ipt re-app-res" id="i-MoshikomiAddress1" />
										</div>
									</div>
									<!-- 住所２ -->
									<div class="input-item">
										<div class="text-m bold item-title">住所２</div>
										<div class="width-full">
											<input type="text" class="zenkaku address width-full cstmr-ipt scr-ipt re-app-res" id="i-MoshikomiAddress2" />
										</div>
									</div>
									<!-- 住所（ﾌﾘｶﾞﾅ） -->
									<div class="input-item">
										<div class="text-m bold item-title">住所（ﾌﾘｶﾞﾅ）</div>
										<div class="text-s">半角文字で入力してください</div>
										<div class="width-full">
											<input type="text" class="hankaku address auto-address-kana width-full cstmr-ipt kojin-req hojin-req scr-ipt re-app-res" id="i-MoshikomiAddressKana" />
										</div>
									</div>
									<!-- 配偶者 -->
									<div class="input-item">
										<div class="text-m bold item-title">配偶者 *</div>
										<div class="radio-container">
											<label>
												<input type="radio" class="cstmr-ipt kojin-req scr-ipt re-app-res" name="i-MoshikomiHaigushaKbn" id="i-MoshikomiHaigushaKbnF" value="2" />
												あり
											</label>
											<label>
												<input type="radio" class="cstmr-ipt kojin-req scr-ipt re-app-res" name="i-MoshikomiHaigushaKbn" id="i-MoshikomiHaigushaKbnS" value="1" />
												なし
											</label>
										</div>
									</div>
									<!-- 住居区分 -->
									<div class="input-item">
										<div class="text-m bold item-title">住居区分</div>
										<div class="">
											<select class="select-kbn width-def cstmr-ipt kojin-req scr-ipt re-app-res" id="i-JukyoKbn">
												<option value="" selected>選択してください</option>
												<option value="1">自己所有</option>
												<option value="2">家族所有</option>
												<option value="3">公営団体</option>
												<option value="4">社宅</option>
												<option value="5">賃貸マンション</option>
												<option value="6">借家</option>
												<option value="7">アパート</option>
												<option value="8">寮・間借り</option>
											</select>
										</div>
									</div>
									<!-- 住宅ローン -->
									<div class="input-item">
										<div class="text-m bold item-title">住宅ローン・家賃支払い（配偶者含む）</div>
										<div class="radio-container">
											<label>
												<input type="radio" class="cstmr-ipt kojin-req scr-ipt re-app-res" name="i-MoshikomiLoanKbn" id="i-MoshikomiLoanKbnF" value="2" />
												あり
											</label>
											<label>
												<input type="radio" class="cstmr-ipt kojin-req scr-ipt re-app-res" name="i-MoshikomiLoanKbn" id="i-MoshikomiLoanKbnS" value="1" />
												なし
											</label>
										</div>
									</div>
									<!-- 同居人数 -->
									<div class="input-item">
										<div class="text-m bold item-title">同居人数（本人含む）（生計をマイナスにする別居家族含む）</div>
										<div class="text-s">半角数字で入力してください</div>
										<div>
											<div class=" text-with-unit">
												<input type="text" class="num cstmr-ipt kojin-req scr-ipt re-app-res" id="i-MoshikomiDokyoNinzu" />
												<span class="unit">人</span>
											</div>
										</div>
									</div>
									<!-- 居住年数 -->
									<div class="input-item">
										<div class="text-m bold item-title">居住年数</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class="num cstmr-ipt kojin-req scr-ipt re-app-res" id="i-MoshikomiKyojuYear" />
											<span class="unit">年</span>
											<input type="text" class="num cstmr-ipt kojin-req scr-ipt re-app-res" id="i-MoshikomiKyojuMonth" />
											<span class="unit">ヵ月</span>
										</div>
									</div>
									<!-- 税込年収 -->
									<div class="input-item">
										<div class="text-m bold item-title">税込年収</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class="price cstmr-ipt kojin-req scr-ipt re-app-res" id="i-MoshikomiNenshu" />
											<span class="unit">万円</span>
										</div>
									</div>
									<!-- 勤務先・学校情報 -->
									<div class="items-title text-l bold">勤務先・学校情報</div>
									<!-- 職業 -->
									<div class="input-item">
										<div class="text-m bold item-title">ご職業</div>
										<div class="">
											<select class="select-kbn width-def cstmr-ipt kojin-req scr-ipt re-app-res" id="i-MoshikomiShokugyoKbn">
												<option value="" selected>選択してください</option>
												<option value="1">正社員</option>
												<option value="2">契約社員</option>
												<option value="3">自営/役員</option>
												<option value="4">派遣社員</option>
												<option value="5">学生</option>
												<option value="6">専業主婦</option>
												<option value="8">パート/アルバイト</option>
												<option value="9">年金受給者</option>
												<option value="10">公務員</option>
												<option value="11">その他</option>
											</select>
										</div>
									</div>
									<!-- 勤務先・学校情報 -->
									<div class="input-item">
										<div class="text-m bold item-title">勤務先・学校情報</div>
										<div class="text-s">全角文字で入力してください</div>
										<div class="">
											<input type="text" class="width-full zenkaku cstmr-ipt scr-ipt skg-kbn-relate-1 re-app-res" id="i-MoshikomiKinmusaki" />
										</div>
									</div>
									<!-- 携帯電話番号 -->
									<div class="input-item">
										<div class="text-m bold item-title">電話番号</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" grid-tel">
											<div>
												<input type="text" class="width-full tel-num num cstmr-ipt scr-ipt skg-kbn-relate-1 re-app-res" id="i-MoshikomiKinmusakiTel1" />
												<div class="text-s error hide">半角数字で6桁以内で入力してください。</div>
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num cstmr-ipt scr-ipt skg-kbn-relate-1 re-app-res" id="i-MoshikomiKinmusakiTel2" />
												<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num cstmr-ipt scr-ipt skg-kbn-relate-1 re-app-res" id="i-MoshikomiKinmusakiTel3" />
												<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
											</div>
										</div>
									</div>
									<!-- 所在地郵便番号 -->
									<div class="input-item">
										<div class="text-m bold item-title">所在地郵便番号</div>
										<div class="text-s">半角数字で入力してください</div>
										<div>
											<div class=" input-searchable">
												<input type="text" class="num cstmr-ipt scr-ipt skg-kbn-relate-1 re-app-res" placeholder="0000000" id=i-MoshikomiKinmusakiPost />
												<button class="btn main  auto-address-btn" id="kinmusaki-address-btn">住所に反映</button>
											</div>
											<div class="text-s error hide">半角文字で7桁以内で入力してください。</div>
										</div>
									</div>
									<!-- 所在地１ -->
									<div class="input-item">
										<div class="text-m bold item-title">所在地１</div>
										<div class="text-s">
											全角文字で、２７文字以内で入力してください。
											<br>
											所在地１に入りきらない場合に所在地２に入力してください。
										</div>
										<div class="">
											<input type="text" class="zenkaku address input-address auto-address width-full cstmr-ipt scr-ipt skg-kbn-relate-1 re-app-res" id="i-MoshikomiKinmusakiAddress1" />
										</div>
									</div>
									<!-- 所在地２ -->
									<div class="input-item">
										<div class="text-m bold item-title">所在地２</div>
										<div class="">
											<input type="text" class="zenkaku address width-full cstmr-ipt scr-ipt re-app-res" id="i-MoshikomiKinmusakiAddress2" />
										</div>
									</div>
									<!-- 勤続年数 -->
									<div class="input-item">
										<div class="text-m bold item-title">勤続年数（学年）</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class="num cstmr-ipt scr-ipt skg-kbn-relate-1 re-app-res" id="i-MoshikomiKinzokuYear" />
											<span class="unit">年</span>
											<input type="text" class="num cstmr-ipt scr-ipt skg-kbn-relate-1 re-app-res" id="i-MoshikomiKinzokuMonth" />
											<span class="unit">ヵ月</span>
										</div>
									</div>
									<!-- 役職 -->
									<div class="input-item">
										<div class="text-m bold item-title">役職</div>
										<div class="">
											<select class="select-kbn width-def cstmr-ipt scr-ipt skg-kbn-relate-4 re-app-res" id="i-MoshikomiKinmusakiYakushokuKbn">
												<option value="" selected>選択してください</option>
												<option value="1">経営者・役員</option>
												<option value="2">管理職</option>
												<option value="3">一般社員</option>
												<option value="4">契約社員</option>
												<option value="5">パート・アルバイト</option>
												<option value="6">その他</option>
											</select>
										</div>
									</div>
									<!-- 所属（部署） -->
									<div class="input-item">
										<div class="text-m bold item-title">所属（部署）</div>
										<div class="text-s">全角文字で入力してください</div>
										<div class="">
											<input type="text" class="width-full zenkaku cstmr-ipt scr-ipt skg-kbn-relate-4 re-app-res" id="i-MoshikomiKinmusakiShozoku" />
										</div>
									</div>
									<!-- 従業員数 -->
									<div class="input-item">
										<div class="text-m bold item-title">従業員数</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class="num cstmr-ipt scr-ipt hojin-req skg-kbn-relate-4 re-app-res" id="i-MoshikomiKinmusakiJugyoin" />
											<span class="unit">人</span>
										</div>
									</div>
									<!-- 業種区分 -->
									<div class="input-item">
										<div class="text-m bold item-title">業種区分</div>
										<div class="">
											<select class="select-kbn width-def cstmr-ipt scr-ipt hojin-req skg-kbn-relate-4 re-app-res" id="i-MoshikomiGyoshuKbn">
												<option value="" selected>選択してください</option>
												<option value="1">農林漁業</option>
												<option value="2">公務員</option>
												<option value="3">金融・保険業</option>
												<option value="4">製造業</option>
												<option value="5">サービス業</option>
												<option value="6">卸・小売業</option>
												<option value="7">飲食業</option>
												<option value="8">土木・建築業</option>
												<option value="9">運輸・通信業</option>
												<option value="10">不動産業</option>
												<option value="11">電気・ガス・水道</option>
												<option value="12">その他</option>
											</select>
										</div>
									</div>
									<!-- 預金残高 -->
									<div class="input-item">
										<div class="text-m bold item-title">預金残高（任意）</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class="price cstmr-ipt scr-ipt re-app-res" id="i-MoshikomiYokinZandaka" />
											<span class="unit">万円</span>
										</div>
									</div>
									<!-- 世帯主氏名 -->
									<div class="input-item">
										<div class="text-m bold item-title">世帯主の氏名</div>
										<div class="text-s">全角文字で入力してください</div>
										<div class=" grid-2">
											<input type="text" class="name zenkaku cstmr-ipt scr-ipt skg-kbn-relate-2 re-app-res" id="i-MoshikomiSetainushiNameSei" />
											<input type="text" class="name zenkaku cstmr-ipt scr-ipt skg-kbn-relate-2 re-app-res" id="i-MoshikomiSetainushiNameMei" />
										</div>
									</div>
									<!-- お申込者との関係 -->
									<div class="input-item">
										<div class="text-m bold item-title">お申込者との関係</div>
										<div class="">
											<select class="select-kbn width-def cstmr-ipt scr-ipt skg-kbn-relate-2 re-app-res" id="i-MoshikomiSetainushiRelateKbn">
												<option value="" selected>選択してください</option>
												<option value="1">配偶者</option>
												<option value="2">子</option>
												<option value="3">親</option>
												<option value="4">兄弟姉妹</option>
												<option value="5">祖父母</option>
												<option value="9">その他</option>
											</select>
										</div>
									</div>
									<!-- 居住 -->
									<div class="input-item">
										<div class="text-m bold item-title">居住</div>
										<div class="radio-container">
											<label>
												<input type="radio" class="cstmr-ipt scr-ipt skg-kbn-relate-2 re-app-res" name="i-MoshikomiKyojuKbn" id="i-MoshikomiKyojuKbnF" value="1" />
												同居
											</label>
											<label>
												<input type="radio" class="cstmr-ipt scr-ipt skg-kbn-relate-2 re-app-res" name="i-MoshikomiKyojuKbn" id="i-MoshikomiKyojuKbnS" value="9" />
												別居
											</label>
										</div>
									</div>
									<!-- 世帯主の税込年収 -->
									<div class="input-item">
										<div class="text-m bold item-title">世帯主の税込年収</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class=" price cstmr-ipt scr-ipt skg-kbn-relate-2 re-app-res" id="i-MoshikomiSetainushiNenshu" />
											<span class="unit">万円</span>
										</div>
									</div>
									<!-- 世帯主のクレジット月額債務 -->
									<div class="input-item">
										<div class="text-m bold item-title">世帯主のクレジット月額債務</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class="price cstmr-ipt scr-ipt skg-kbn-relate-2 re-app-res" id="i-MoshikomiSetainushiCreditSaimu" />
											<span class="unit">万円</span>
										</div>
									</div>
									<!-- 年金受給情報 -->
									<div class="input-item">
										<div class="text-m bold item-title">受給情報</div>
										<div class="text-s">給付・収入内容を入力してください。</div>
										<div class="">
											<select class="select-kbn width-def cstmr-ipt scr-ipt skg-kbn-relate-3 select-nenkin-kbn re-app-res" id="i-MoshikomiNenkinJukyuKbn">
												<option value="" selected>選択してください</option>
												<option value="1">国民年金</option>
												<option value="2">厚生年金</option>
												<option value="3">共済年金</option>
												<option value="4">その他</option>
											</select>
										</div>
										<div class="">
											<div class="text-s">全角文字で入力してください</div>
											<input type="text" class="zenkaku width-def sonota cstmr-ipt scr-ipt re-app-res" id="i-MoshikomiNenkinJukyuKbnSonota" disabled />
										</div>
									</div>
									<!-- 年間受給額 -->
									<div class="input-item">
										<div class="text-m bold item-title">年間受給額</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class="price cstmr-ipt scr-ipt skg-kbn-relate-3 re-app-res" id="i-MoshikomiNenkinJukyuPrice" />
											<span class="unit">万円</span>
										</div>
									</div>
									<!-- お申込者様のベリファイ希望日時 -->
									<div class="input-item main-view">
										<div class="items-title text-l bold">お申込者様のベリファイ希望日時</div>
										<div class="text-s">
											ご希望の日程がございましたら入力してください。
											<br>
											本申込日の翌日から入力可能です。
											<br>
											また、時間の指定は9:00～17:00となります。
											<br>
											なお、本申込は15時締めとなります。
											<br>
											15時以降の本申込は翌日のお申込みとなります。
											<br>
										</div>
										<div>
											<div class="input-item">
												<div class="" style="display: flex; gap: .5rem;">
													<input type="date" class="main-ipt cstmr-ipt verify-date" id="i-MoshikomiVerifyDate" />
													<div class=" text-with-unit ">
														<select class="verify-time-from cstmr-ipt main-ipt " id="i-MoshikomiVerifyTimeFrom"></select>
														<span class="unit">頃</span>
														<label class="hyphen">～</label>
														<select class="verify-time-to cstmr-ipt main-ipt " id="i-MoshikomiVerifyTimeTo"></select>
														<span class="unit">頃</span>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>

								<!-- ボタン -->
								<div class="btn-container">
									<button type="button" class="btn main page-btn next-btn" name="form-hoshonin" disabled>次へ</button>
								</div>

							</div>


							<!-- 連帯保証人様の情報 -->
							<div id="form-hoshonin" class="input-form">

								<div class="item-block " id="hoshonin-sel-block">
									<div class="page-detail text-s">※別紙「個人情報の取り扱いに関するお知らせ」をご確認ください。</div>
									<!-- 連帯保証人 -->
									<div class="input-item">
										<div class="text-m bold item-title">連帯保証人</div>
										<div class="text-s">法人をご選択の場合は、連帯保証人情報に代表者情報を入力してください。</div>
										<div class="radio-container">
											<label>
												<input type="radio" class="kojin-req hojin-req cstmr-ipt scr-ipt" name="i-HoshoninKbn" id="i-HoshoninKbnF" value="1" />
												あり
											</label>
											<label>
												<input type="radio" class="kojin-req hojin-req cstmr-ipt scr-ipt" name="i-HoshoninKbn" id="i-HoshoninKbnS" value="0" checked />
												なし
											</label>
										</div>
									</div>
								</div>

								<!-- 連帯保証人様の情報タイトル -->
								<div class="item-block hide with-hoshonin-view" id="hoshonin-block">
									<div class="items-title text-l bold">連帯保証人様の情報</div>
									<!-- お名前 -->
									<div class="input-item">
										<div class="text-m bold item-title">お名前 *</div>
										<div class="text-s">
											姓と名で合計全角２９文字以内で入力してください。
											<br>
											外国籍の方も姓と名で分けて入力してください。
										</div>
										<div class=" grid-2">
											<div>
												<input type="text" class="hoshonin-req width-full zenkaku non-num name-sei hojin-req cstmr-ipt scr-ipt" id="i-HoshoninNameSei" />
											</div>
											<div>
												<input type="text" class="hoshonin-req width-full zenkaku non-num name-mei hojin-req cstmr-ipt scr-ipt" id="i-HoshoninNameMei" />
											</div>
										</div>
									</div>
									<!-- お名前（ﾌﾘｶﾞﾅ）-->
									<div class="input-item">
										<div class="text-m bold item-title">お名前（ﾌﾘｶﾞﾅ） *</div>
										<div class="text-s">
											姓と名で合計半角３９文字以内で入力してください。
											<br>
											外国籍の方も姓と名で分けて入力してください。
										</div>
										<div class=" grid-2">
											<div>
												<input type="text" class="width-full hankaku non-num non-alpha furigana-sei hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninNameSeiKana" />
											</div>
											<div>
												<input type="text" class="width-full hankaku non-num non-alpha furigana-mei hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninNameMeiKana" />
											</div>
										</div>
									</div>
									<!-- 性別 -->
									<div class="input-item">
										<div class="text-m bold item-title">性別</div>
										<div class="radio-container">
											<label>
												<input type="radio" name="i-HoshoninSeibetsuKbn" id="i-HoshoninSeibetsuKbnF" class=" hoshonin-req  cstmr-ipt scr-ipt" value="1" />
												男
											</label>
											<label>
												<input type="radio" name="i-HoshoninSeibetsuKbn" id="i-HoshoninSeibetsuKbnS" class=" hoshonin-req  cstmr-ipt scr-ipt" value="2" />
												女
											</label>
										</div>
									</div>
									<!-- 年齢 -->
									<div class="input-item">
										<div class="text-m bold item-title">年齢</div>
										<div class="text-s">半角で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class="num hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninAge" />
											<span class="unit">歳</span>
										</div>
									</div>
									<!-- 生年月日 -->
									<div class="input-item">
										<div class="text-m bold item-title">生年月日</div>
										<div class=" grid-3">
											<select class="year from-today hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninBirthDateYear">
											</select>
											<select class="month from-today hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninBirthDateMonth">
											</select>
											<select class="day from-today hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninBirthDateDay">
											</select>
										</div>
									</div>
									<!-- 携帯電話番号 -->
									<div class="input-item">
										<div class="text-m bold item-title">携帯電話番号</div>
										<div class="text-s">
											半角数字で入力してください。
											<br>
											もし携帯電話番号がない場合、こちらの項目に自宅電話番号を入力してください。
										</div>
										<div class=" grid-tel">
											<div>
												<input type="text" class="width-full tel-num num hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninMobile1" />
												<div class="text-s error hide">半角数字で6桁以内で入力してください。</div>
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninMobile2" />
												<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninMobile3" />
												<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
											</div>
										</div>
									</div>
									<!--自宅電話番号 -->
									<div class="input-item only-kojin">
										<div class="text-m bold item-title">自宅電話番号（任意）</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" grid-tel">
											<div>
												<input type="text" class="width-full tel-num num cstmr-ipt scr-ipt" id="i-HoshoninTel1" />
												<div class="text-s error hide">半角数字で6桁以内で入力してください。</div>
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num cstmr-ipt scr-ipt" id="i-HoshoninTel2" />
												<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num cstmr-ipt scr-ipt" id="i-HoshoninTel3" />
												<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
											</div>
										</div>
									</div>
									<!-- 連帯保証人住所タイトル -->
									<div class="items-title text-l bold">連帯保証人様の住所</div>
									<!-- 郵便番号 -->
									<div class="input-item">
										<div class="text-m bold item-title">郵便番号</div>
										<div class="text-s">半角数字で入力してください</div>
										<div>
											<div class=" input-searchable">
												<input type="text" class="num hoshonin-req  cstmr-ipt scr-ipt" maxlength="7" placeholder="0000000" id=i-HoshoninPost />
												<button class="btn main auto-address-btn" id="hoshonin-address-btn">住所に反映</button>
											</div>
											<div class="text-s error hide">半角文字で7桁以内で入力してください。</div>
										</div>
									</div>
									<!-- 住所１ -->
									<div class="input-item">
										<div class="text-m bold item-title">住所１</div>
										<div class="text-s">
											全角文字で、２７文字以内で入力してください。
											<br>
											住所１に入りきらない場合に住所２に入力してください。
										</div>
										<div class="">
											<input type="text" class="zenkaku address auto-address width-full hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninAddress1" />
										</div>
									</div>
									<!-- 住所２ -->
									<div class="input-item">
										<div class="text-m bold item-title">住所２（任意）</div>
										<div class="">
											<input type="text" class="zenkaku address width-full cstmr-ipt scr-ipt" id="i-HoshoninAddress2" />
										</div>
									</div>
									<!-- 住所（ﾌﾘｶﾞﾅ） -->
									<div class="input-item">
										<div class="text-m bold item-title">住所（ﾌﾘｶﾞﾅ）</div>
										<div class="text-s">半角文字で入力してください</div>
										<div class="">
											<input type="text" class="hankaku address non-num non-alpha auto-address-kana width-full hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninAddressKana" />
										</div>
									</div>
									<!-- 税込年収 -->
									<div class="input-item">
										<div class="text-m bold item-title">税込年収</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class="price hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninNenshu" />
											<span class="unit">万円</span>
										</div>
									</div>
									<!-- 居住年数 -->
									<div class="input-item">
										<div class="text-m bold item-title">居住年数</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class="num cstmr-ipt scr-ipt hoshonin-req" id="i-HoshoninKyojuYear" />
											<span class="unit">年</span>
											<input type="text" class="num cstmr-ipt scr-ipt hoshonin-req" id="i-HoshoninKyojuMonth" />
											<span class="unit">ヵ月</span>
										</div>
									</div>
									<!-- 住居区分 -->
									<div class="input-item">
										<div class="text-m bold item-title">住居区分</div>
										<div class="">
											<select class="select-kbn width-def hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninJukyoKbn">
												<option value="" selected>選択してください</option>
												<option value="1">自己所有</option>
												<option value="2">家族所有</option>
												<option value="3">公営団体</option>
												<option value="4">社宅</option>
												<option value="5">賃貸マンション</option>
												<option value="6">借家</option>
												<option value="7">アパート</option>
												<option value="8">寮・間借り</option>
											</select>
										</div>
									</div>
									<!-- 住宅ローン -->
									<div class="input-item">
										<div class="text-m bold item-title">住宅ローン・家賃支払い（配偶者含む）</div>
										<div class="radio-container">
											<label>
												<input type="radio" class=" hoshonin-req  cstmr-ipt scr-ipt" name="i-HoshoninLoanKbn" id="i-HoshoninLoanKbnF" value="2" />
												あり
											</label>
											<label>
												<input type="radio" class=" hoshonin-req  cstmr-ipt scr-ipt" name="i-HoshoninLoanKbn" id="i-HoshoninLoanKbnS" value="1" />
												なし
											</label>
										</div>
									</div>
									<!-- 配偶者 -->
									<div class="input-item">
										<div class="text-m bold item-title">配偶者</div>
										<div class="radio-container">
											<label>
												<input type="radio" class=" hoshonin-req  cstmr-ipt scr-ipt" name="i-HoshoninHaigushaKbn" id="i-HoshoninHaigushaKbnF" value="2" />
												あり
											</label>
											<label>
												<input type="radio" class=" hoshonin-req  cstmr-ipt scr-ipt" name="i-HoshoninHaigushaKbn" id="i-HoshoninHaigushaKbnS" value="1" />
												なし
											</label>
										</div>
									</div>
									<!-- 同居人数 -->
									<div class="input-item">
										<div class="text-m bold item-title">同居人数（本人含む）（生計をマイナスにする別居家族含む）</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class="num hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninDokyoNinzu" />
											<span class="unit">人</span>
										</div>
									</div>
									<!-- お申込者との関係 -->
									<div class="input-item">
										<div class="text-m bold item-title">お申込者とのご関係 *</div>
										<div class="">
											<select class="select-kbn width-def hoshonin-req  cstmr-ipt scr-ipt" id="i-HoshoninMoshikomiRelateKbn">
												<option value="" selected>選択してください</option>
												<option value="1">夫婦</option>
												<option value="2">親子</option>
												<option value="3">兄弟姉妹</option>
												<option value="4">親戚</option>
												<option value="5">代表</option>
												<option value="9">友人</option>
												<option value="10">知人</option>
												<option value="11">その他</option>
											</select>
										</div>
										<div>
											<div class="text-s">全角文字で入力してください</div>
											<input type="text" class="zenkaku width-def cstmr-ipt scr-ipt sonota" id="i-HoshoninMoshikomiRelateKbnSonota" disabled />
										</div>
									</div>
									<!-- 職業 -->
									<!-- 勤務先・学校情報 -->
									<div class="items-title text-l bold">ご職業</div>
									<div class="input-item">
										<div class="text-m bold item-title">就業形態</div>
										<div class="">
											<select class="select-kbn hoshonin-req width-def cstmr-ipt scr-ipt" id="i-HoshoninShokugyoKbn">
												<option value="" selected>選択してください</option>
												<option value="1">正社員</option>
												<option value="2">契約社員</option>
												<option value="3">自営/役員</option>
												<option value="4">派遣社員</option>
												<option value="5">学生</option>
												<option value="6">専業主婦</option>
												<option value="8">パート/アルバイト</option>
												<option value="9">年金受給者</option>
												<option value="10">公務員</option>
												<option value="11">その他</option>
												<option value="12">年金受給者＋その他</option>
												<option value="13">無職</option>
											</select>
										</div>
									</div>
									<!-- 勤務先・学校情報 -->
									<div class="input-item">
										<div class="text-m bold item-title">勤務先・学校情報</div>
										<div class="text-s">全角で入力してください</div>
										<div class="">

											<input type="text" class="width-full zenkaku h-skg-kbn-relate-1 cstmr-ipt scr-ipt" id="i-HoshoninKinmusaki" />

										</div>
									</div>
									<!-- 携帯電話番号 -->
									<div class="input-item">
										<div class="text-m bold item-title">電話番号</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" grid-tel">
											<div>
												<input type="text" class="width-full tel-num num h-skg-kbn-relate-1  cstmr-ipt scr-ipt" id="i-HoshoninKinmusakiTel1" />
												<div class="text-s error hide">半角数字で6桁以内で入力してください。</div>
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num h-skg-kbn-relate-1  cstmr-ipt scr-ipt" id="i-HoshoninKinmusakiTel2" />
												<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num h-skg-kbn-relate-1  cstmr-ipt scr-ipt" id="i-HoshoninKinmusakiTel3" />
												<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
											</div>
										</div>
									</div>
									<!-- 所在地郵便番号 -->
									<div class="input-item">
										<div class="text-m bold item-title">所在地郵便番号</div>
										<div class="text-s">半角数字で入力してください</div>
										<div>
											<div class=" input-searchable">
												<input type="text" class="num h-skg-kbn-relate-1 cstmr-ipt scr-ipt" placeholder="0000000" id=i-HoshoninKinmusakiPost />
												<button class="btn main auto-address-btn" id="hoshonin-kinmusaki-address-btn">住所に反映</button>
											</div>
											<div class="text-s error hide">半角文字で7桁以内で入力してください。</div>
										</div>
									</div>
									<!-- 所在地１ -->
									<div class="input-item">
										<div class="text-m bold item-title">所在地１</div>
										<div class="text-s">
											全角文字で、２７文字以内で入力してください。
											<br>
											所在地１に入りきらない場合に所在地２に入力してください。
										</div>
										<div class="">
											<input type="text" class="zenkaku address input-address auto-address width-full h-skg-kbn-relate-1 cstmr-ipt scr-ipt" id="i-HoshoninKinmusakiAddress1" />
										</div>
									</div>
									<!-- 所在地２ -->
									<div class="input-item">
										<div class="text-m bold item-title">所在地２</div>
										<div class="">
											<input type="text" class="zenkaku address input-address width-full cstmr-ipt scr-ipt" id="i-HoshoninKinmusakiAddress2" />
										</div>
									</div>
									<!-- 勤続年数 -->
									<div class="input-item">
										<div class="text-m bold item-title">勤続年数（学年）</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class="num h-skg-kbn-relate-1 cstmr-ipt scr-ipt" id="i-HoshoninKinzokuYear" />
											<span class="unit">年</span>
											<input type="text" class="num h-skg-kbn-relate-1 cstmr-ipt scr-ipt" id="i-HoshoninKinzokuMonth" />
											<span class="unit">ヵ月</span>
										</div>
									</div>
									<!-- 役職 -->
									<div class="input-item">
										<div class="text-m bold item-title">役職</div>
										<div class="">
											<select class="select-kbn width-def h-skg-kbn-relate-1 cstmr-ipt scr-ipt" id="i-HoshoninKinmusakiYakushokuKbn">
												<option value="" selected>選択してください</option>
												<option value="1">経営者・役員</option>
												<option value="2">管理職</option>
												<option value="3">一般社員</option>
												<option value="4">契約社員</option>
												<option value="5">パート・アルバイト</option>
												<option value="6">その他</option>
											</select>
										</div>
									</div>
									<!-- 所属（部署） -->
									<div class="input-item">
										<div class="text-m bold item-title">所属</div>
										<div class="text-s">全角文字で入力してください</div>
										<div class="">
											<input type="text" class="width-def h-skg-kbn-relate-1 cstmr-ipt scr-ipt" id="i-HoshoninKinmusakiShozoku" />
										</div>
									</div>
									<!-- 従業員数 -->
									<div class="input-item">
										<div class="text-m bold item-title">従業員数</div>
										<div class="text-s">半角数字で入力してください</div>
										<div class=" text-with-unit">
											<input type="text" class="num h-skg-kbn-relate-1 cstmr-ipt scr-ipt" id="i-HoshoninKinmusakiJugyoin" />
											<span class="unit">人</span>
										</div>
									</div>
									<!-- 業種区分 -->
									<div class="input-item">
										<div class="text-m bold item-title">業種区分</div>
										<div class="">
											<select class="select-kbn width-def h-skg-kbn-relate-1 cstmr-ipt scr-ipt" id="i-HoshoninGyoshuKbn">
												<option value="" selected>選択してください</option>
												<option value="1">農林漁業</option>
												<option value="2">公務員</option>
												<option value="3">金融・保険業</option>
												<option value="4">製造業</option>
												<option value="5">サービス業</option>
												<option value="6">卸・小売業</option>
												<option value="7">飲食業</option>
												<option value="8">土木・建築業</option>
												<option value="9">運輸・通信業</option>
												<option value="10">不動産業</option>
												<option value="11">電気・ガス・水道</option>
												<option value="12">その他</option>
											</select>
										</div>
									</div>
									<!-- 年金受給情報 -->
									<div class="input-item">
										<div class="text-m bold item-title">年金受給情報</div>
										<div class="">
											<select class="select-kbn width-def h-skg-kbn-relate-2 cstmr-ipt scr-ipt select-nenkin-kbn" id="i-HoshoninNenkinJukyuKbn">
												<option value="" selected>選択してください</option>
												<option value="1">国民年金</option>
												<option value="2">厚生年金</option>
												<option value="3">共済年金</option>
												<option value="4">その他</option>
											</select>
										</div>
										<div>
											<div class="text-s">全角文字で入力してください</div>
											<input type="text" class="zenkaku  width-def sonota" id="i-HoshoninNenkinJukyuKbnSonota" disabled />
										</div>
									</div>
									<!-- 連帯保証人のベリファイ希望日時 -->
									<div class="input-item main-view">
										<div class="items-title text-l bold">連帯保証人様のベリファイ希望日程</div>
										<div class="text-m bold item-title">信販会社からお客様への電話確認希望日</div>
										<div class="text-s">
											ご希望の日程がございましたら入力してください。
											<br>
											本申込日の翌日から入力可能です。
											<br>
											また、時間の指定は9:00～17:00となります。
											<br>
											なお、本申込は15時締めとなります。
											<br>
											15時以降の本申込は翌日のお申込みとなります。
											<br>
										</div>
										<div>
											<div class="input-item">
												<div class="" style="display: flex; gap: .5rem;">
													<input type="date" class=" verify-date cstmr-ipt main-ipt" id="i-HoshoninVerifyDate" />
													<div class=" text-with-unit ">
														<select class="verify-time-from cstmr-ipt main-ipt " id="i-HoshoninVerifyTimeFrom"></select>
														<span class="unit">頃</span>
														<label class="hyphen">～</label>
														<select class="verify-time-to cstmr-ipt main-ipt " id="i-HoshoninVerifyTimeTo"></select>
														<span class="unit">頃</span>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>

								<!-- ボタン -->
								<div class="btn-container">
									<button type="button" class="page-btn btn sub back-btn" name="form-moshikomi">戻る</button>
									<button type="button" class="page-btn btn main next-btn" name="form-okuruma" disabled>次へ</button>
								</div>

							</div>


							<!-- お車の情報 -->
							<div id="form-okuruma" class="input-form">
								<div class="item-block">
									<!-- MCCS情報タイトル -->
									<div class="items-title text-l bold">MCCS情報</div>
									<!-- Connected(MCCS)申込 -->
									<div class="input-item">
										<div class="text-m bold item-title">Connected(MCCS)申込</div>
										<div class="radio-container">
											<label>
												<input type="radio" class="store-ipt kojin-req hojin-req scr-ipt" name="i-MccsMoshikomiKbn" id="i-MccsMoshikomiKbnF" value="1" checked />
												あり
											</label>
											<label>
												<input type="radio" class="store-ipt kojin-req hojin-req scr-ipt" name="i-MccsMoshikomiKbn" id="i-MccsMoshikomiKbnS" value="0" />
												なし
											</label>
										</div>
									</div>
									<!-- シフト -->
									<div class="input-item">
										<div class="text-m bold item-title">シフト</div>
										<div class="radio-container">
											<label>
												<input type="radio" class="store-ipt kojin-req hojin-req scr-ipt" name="i-ShiftKbn" id="i-ShiftKbnF" value="0" />
												AT
											</label>
											<label>
												<input type="radio" class="store-ipt kojin-req hojin-req scr-ipt" name="i-ShiftKbn" id="i-ShiftKbnS" value="1" />
												MT
											</label>
										</div>
									</div>
									<!-- リモートスターター -->
									<div class="input-item">
										<div class="text-m bold item-title">リモートスターター</div>
										<div class="radio-container">
											<label>
												<input type="radio" class="store-ipt kojin-req hojin-req scr-ipt" name="i-RemoteStarterKbn" id="i-RemoteStarterKbnF" value="1" />
												あり
											</label>
											<label>
												<input type="radio" class="store-ipt kojin-req hojin-req scr-ipt" name="i-RemoteStarterKbn" id="i-RemoteStarterKbnS" value="0" />
												なし
											</label>
										</div>
									</div>
									<!-- 販売条件 -->
									<div class="input-item">
										<div class="text-m bold item-title">販売の条件となっている商品・権利・役務の有無</div>
										<div class="radio-container">
											<label>
												<input type="radio" class="store-ipt kojin-req hojin-req scr-ipt" name="i-HanbaiJokenKbn" id="i-HanbaiJokenKbnF" value="1" />
												あり
											</label>
											<label>
												<input type="radio" class="store-ipt kojin-req hojin-req scr-ipt" name="i-HanbaiJokenKbn" id="i-HanbaiJokenKbnS" value="0" checked />
												なし
											</label>
										</div>
									</div>
									<!--  -->
									<div class="input-item">
										<div class="text-m bold item-title">前項で「あり」の場合は内容を下記に記載。明細は別紙のとおり</div>
										<div class="radio-container">
											<label>
												<input type="radio" class="store-ipt kojin-req hojin-req scr-ipt hanbaiJokenKbn-req" name="i-HanbaiJokenNaiyoKbn" id="i-HanbaiJokenNaiyoKbnF" value="1" disabled />
												あり
											</label>
											<label>
												<input type="radio" class="store-ipt kojin-req hojin-req scr-ipt hanbaiJokenKbn-req" name="i-HanbaiJokenNaiyoKbn" id="i-HanbaiJokenNaiyoKbnS" value="0" disabled checked />
												なし
											</label>
										</div>
									</div>

									<!-- ご購入の自動車についての記載DEBUG項目なし？ -->
									<!-- 
							<div class="input-item">
								<div class="text-m bold item-title">ご購入の自動車についての記載</div>
								<div class="radio-container">
									<label>
										<input type="radio" class="store-ipt kojin-req hojin-req scr-ipt" name="radio-mccs-hanbai-joken" value="1" />
										あり
									</label>
									<label>
										<input type="radio" class="radio-mccs-hanbai-joken" name="radio-mccs-hanbai-joken" value="0" checked />
										なし
									</label>
								</div>
							</div>
							 -->
									<!-- ご購入の自動車タイトル -->
									<!--
							<div class="items-title text-l bold">ご購入の自動車</div>
							-->
									<!-- 主な使用目的 -->
									<div class="input-item">
										<div class="text-m bold item-title">主な使用目的</div>
										<div class="">
											<select class="select-kbn width-def store-ipt kojin-req hojin-req scr-ipt" id="i-ShiyoMokutekiKbn">
												<option value="" selected>選択してください</option>
												<option value="1">日常</option>
												<option value="2">商用</option>
												<option value="3">その他</option>
											</select>
										</div>
										<div>
											<input type="text" class="width-def sonota store-ipt scr-ipt" id="ShiyoMokutekiKbnSonota" disabled />
										</div>
									</div>
									<!-- 新車・中古車 -->
									<div class="input-item">
										<div class="text-m bold item-title">新車・中古車</div>
										<div class="radio-container">
											<label>
												<input type="radio" class=" store-ipt kojin-req hojin-req scr-ipt" name="i-UsedKbn" id="i-UsedKbnF" value="1" />
												新車
											</label>
											<label>
												<input type="radio" class=" store-ipt kojin-req hojin-req scr-ipt" name="i-UsedKbn" id="i-UsedKbnS" value="0" />
												中古車
											</label>
										</div>
									</div>
									<!-- エンジンスタート -->
									<div class="input-item">
										<div class="text-m bold item-title">エンジンスタート</div>
										<div class="radio-container">
											<label>
												<input type="radio" class=" store-ipt kojin-req hojin-req scr-ipt" name="i-EngineStartKbn" id="i-EngineStartKbnF" value="0" />
												プッシュ
											</label>
											<label>
												<input type="radio" class=" store-ipt kojin-req hojin-req scr-ipt" name="i-EngineStartKbn" id="i-EngineStartKbnS" value="1" />
												回転キー
											</label>
										</div>
									</div>
									<!--車両情報 -->
									<div class="item-block">
										<div class="input-item mccs-car-info with-mccs-view mccs-req">
											<div class="text-m bold item-title">車両情報</div>
											<div class="mccs-car-info-row">
												<select class="store-ipt scr-ipt" id="i-Maker">
													<option value="" selected>メーカー</option>
												</select>
												<select class="store-ipt scr-ipt" id="i-Shamei">
													<option value="" selected>車名</option>
												</select>
												<select class="store-ipt scr-ipt" id="i-Katashiki">
													<option value="" selected>型式</option>
												</select>
											</div>
											<div class="mccs-car-info-row">
												<select class="year store-ipt scr-ipt" id="i-ShonendoYear">
												</select>
												<select class="month store-ipt scr-ipt" id="i-ShonendoMonth">
												</select>
											</div>
										</div>
										<div class="input-item mccs-manual-ipt  without-mccs-view  no-mccs-req hide">
											<div class="text-m bold item-title">車両情報</div>
											<div id="mccs-car-info-input-area" class="input-item">
												<div class="grid-2">
													<input type="text" class="width-full store-ipt scr-ipt" id="i-ShameiML" placeholder="車名" />
												</div>
												<div class="text-s">半角文字で入力してください。</div>
												<div class="grid-2">
													<input type="text" class="width-full hankaku store-ipt scr-ipt" id="i-KatashikiML" placeholder="型式" />
												</div>
												<div class="" style="display: flex; gap: 1.5rem;">
													<select class="year store-ipt scr-ipt" style="width: 23%;" id="i-ShonendYearML">
													</select>
													<select class="month store-ipt scr-ipt" style="width: 23%;" id="i-ShonendMonthML">
													</select>
												</div>
											</div>
										</div>
										<div class="input-item mccs-manual-ipt-check with-mccs-view">
											<div class="text-s">選択時にMCCSを取り付ける車両が無い場合、GMS社にMCCSが取り付けられることを確認した場合に限り、車両情報を手動で入力して申し込むことができます。</div>
											<div class="border-area">
												<div class="checkbox-div" id="">
													<label>
														<span class="checkbox-div-checkbox">
															<input type="checkbox" class="store-ipt scr-ipt" id="i-ManualInputCheck" />
														</span>
														<span class="checkbox-div-text"> GMS社にMCCSが取り付けられることを確認したため、車両情報を手入力して申し込みます。 </span>
													</label>
												</div>
											</div>
										</div>
									</div>
									<!-- グレード -->
									<div class="input-item">
										<div class="text-m bold item-title">グレード</div>
										<div class="text-s">本申込時に変更可能です。</div>
										<div class="">
											<input type="text" class="width-def store-ipt kojin-req hojin-req main-req" id="i-Grade" />
										</div>
									</div>
									<!-- 車台番号 -->
									<div class="input-item">
										<div class="text-m bold item-title">車台番号</div>
										<div class="text-s">半角文字で入力してください。本申込時に変更可能です。</div>
										<div class="">
											<input type="text" class="hankaku width-def store-ipt" id="i-ShadaiNum" />
										</div>
									</div>
									<!-- 走行距離 -->
									<div class="input-item">
										<div class="text-m bold item-title">走行距離</div>
										<div class="text-s">半角数字で入力してください。本申込時に変更可能です。</div>
										<div class=" text-with-unit">
											<input type="text" class="num store-ipt kojin-req hojin-req main-req" id="i-SokoKyori" />
											<span class="unit">Km</span>
										</div>
									</div>
									<!-- 登録番号 -->
									<div class="input-item">
										<div class="text-m bold item-title">登録番号</div>
										<div class="text-s">本申込時に変更可能です。</div>
										<div class="">
											<input type="text" class="width-def store-ipt" id="i-TorokuNum" />
										</div>
									</div>
									<!-- 車体色 -->
									<div class="input-item">
										<div class="text-m bold item-title">車体色</div>
										<div class="text-s">本申込時に変更可能です。</div>
										<div class="">
											<select class="select-kbn width-def store-ipt kojin-req hojin-req main-req" id="i-Color">
												<option value="" selected>選択してください</option>
												<option value="赤">赤</option>
												<option value="橙">橙</option>
												<option value="茶">茶</option>
												<option value="黄">黄</option>
												<option value="緑">緑</option>
												<option value="青">青</option>
												<option value="紫">紫</option>
												<option value="白">白</option>
												<option value="灰">灰</option>
												<option value="黒">黒</option>
											</select>
										</div>
									</div>
									<!-- 排気量 -->
									<div class="input-item">
										<div class="text-m bold item-title">排気量</div>
										<div class="text-s">半角数字で入力してください。本申込時に変更可能です。</div>
										<div class=" text-with-unit">
											<input type="text" class="num store-ipt kojin-req hojin-req main-req" id="i-Haikiryo" />
											<span class="unit">cc</span>
										</div>
									</div>
									<!-- 所有者（任意） -->
									<div class="input-item">
										<div class="item-title text-l bold">所有者</div>
										<div class="text-s">本申込時に変更可能です。</div>
										<div class="text-s">その他を選択して入力する場合は、全角文字で入力してください</div>
										<div class="radio-container">
											<label class="read-only">
												<input type="radio" class="read-only" name="i-Owner" id="i-OwnerF" value="本人" />
												本人
											</label>
											<label class="read-only">
												<input type="radio" class="read-only" name="i-Owner" id="i-OwnerS" value="その他" />
												その他
											</label>
										</div>
										<div class="">
											<input type="text" class="read-only width-def sonota" id="i-OwnerSonota" disabled />
										</div>
									</div>

									<!-- ボタン -->
									<div class="btn-container">
										<button type="button" class="page-btn btn sub back-btn" name="form-hoshonin">戻る</button>
										<button type="button" class="page-btn btn main next-btn" name="form-kingaku" disabled>次へ</button>
									</div>

								</div>
							</div>

							<!-- 金額・その他の情報 -->
							<div id="form-kingaku" class="input-form ls-input">
								<div class="item-block">
									<!-- 金額（税込）タイトル -->
									<div class="items-title text-l bold">金額（税込）</div>
									<!-- 車両本体価格 -->
									<div class="input-item">
										<div class="text-m bold item-title">車両本体価格</div>
										<div class="text-s">半角数字で入力してください。</div>
										<div class=" text-with-unit">
											<input type="text" class="ls-req price scr-ipt store-ipt kojin-req hojin-req cal-total-item" id="i-CarPrice" />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- MCCS取付 -->
									<div class="input-item">
										<div class="text-m bold item-title">MCCS取付</div>
										<div class="text-s">MCCS取付ありの場合に自動で設定されます。</div>
										<div class=" text-with-unit">
											<input type="text" class="ls-req price read-only cal-total-item" id="i-MccsAttachPrice" value="88000" disabled />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- 付属品 -->
									<div class="input-item">
										<div class="text-m bold item-title">付属品</div>
										<div class="text-s">半角数字で入力してください。</div>
										<div class=" text-with-unit">
											<input type="text" class="ls-req price scr-ipt store-ipt kojin-req hojin-req cal-total-item cal-sub-item" id="i-FuzokuhinPrice" />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- 諸費用 -->
									<div class="input-item">
										<div class="text-m bold item-title">諸費用</div>
										<div class="text-s">半角数字で入力してください。</div>
										<div class=" text-with-unit">
											<input type="text" class="ls-req price scr-ipt store-ipt kojin-req hojin-req cal-total-item cal-sub-item" id="i-OtherPrice" />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- 車検・整備費用 -->
									<div class="input-item">
										<div class="text-m bold item-title">車検・整備費用</div>
										<div class="text-s">半角数字で入力してください。</div>
										<div class=" text-with-unit">
											<input type="text" class="ls-req price scr-ipt store-ipt kojin-req hojin-req cal-total-item cal-sub-item" id="i-ShakenPrice" />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- ①現金合計額 -->
									<div class="input-item">
										<div class="text-m bold item-title">①現金合計額</div>
										<div class="text-s">各金額を入力すると自動計算されます。</div>
										<div class=" text-with-unit">
											<input type="text" class="price read-only" id="i-TotalPrice" disabled />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- ②頭金 -->
									<div class="input-item">
										<div class="text-m bold item-title">②頭金</div>
										<div class="text-s">現金（お申込金)</div>
										<div class="text-s">半角数字で入力してください。</div>
										<div class="">
											<div class=" text-with-unit">
												<input type="text" class="ls-req price scr-ipt store-ipt kojin-req hojin-req cal-atamakin-item" id="i-AppPrice" />
												<span class="unit">円</span>
											</div>
										</div>
										<div class="text-s">下取車充当額</div>
										<div class="text-s">半角数字で入力してください。</div>
										<div class="">
											<div class=" text-with-unit">
												<input type="text" class="ls-req price scr-ipt store-ipt kojin-req hojin-req cal-atamakin-item " id="i-ShitadoriPrice" />
												<span class="unit">円</span>
											</div>
										</div>
										<input type="hidden" class="" id="i-AtamaPrice">
									</div>
									<!-- ③残金 -->
									<div class="input-item">
										<div class="text-m bold item-title">③残金（①-②）</div>
										<div class="text-s">各金額を入力すると自動計算されます。</div>
										<div class=" text-with-unit">
											<input type="text" class="price read-only" id="i-RemainPrice" disabled />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- お支払い期間タイトル -->
									<div class="items-title text-l bold">お支払い期間</div>
									<!-- お支払期間 -->
									<div class="input-item">
										<div class="text-m bold item-title">お支払期間</div>
										<div class="text-s">契約締結時にお支払期間が決定されます。</div>
										<div class="text-s">開始</div>
										<div class=" grid-3">
											<select class="read-only" name="i-ShiharaiStartYear" disabled class="year">
											</select>
											<select class="read-only" name="i-ShiharaiStartMonth" disabled class="month">
											</select>
										</div>
										<div class="text-s">終了</div>
										<div class=" grid-3">
											<select class="read-only" name="i-ShiharaiEndYear" disabled class="year">
											</select>
											<select class="read-only" name="i-ShiharaiEndMonth" disabled class="month">
											</select>
										</div>
									</div>
									<!-- お支払回数 -->
									<div class="input-item">
										<div class="text-m bold item-title">お支払回数</div>
										<div class="text-s">お支払回数は年単位で指定可能です。</div>
										<div class="">
											<select class="ls-req select-kbn width-def scr-ipt store-ipt kojin-req hojin-req kasanShiharaiCheck" id="i-ShiharaiKaisu">
												<option value="" selected>選択してください</option>
												<option value="12">12回</option>
												<option value="24">24回</option>
												<option value="36">36回</option>
												<option value="48">48回</option>
												<option value="60">60回</option>
												<option value="72">72回</option>
												<option value="84">84回</option>
											</select>
										</div>
									</div>
									<!-- 口座振替日 -->
									<div class="input-item">
										<div class="text-m bold item-title">口座振替日 毎月27日</div>
										<div class="text-s">預金口座へのご入金は、振替日の前日26日までにお願いいたします。</div>
									</div>
									<!-- ボーナス加算月 -->
									<div class="input-item">
										<div class="text-m bold item-title">ボーナス加算月</div>
										<div class="radio-container">
											<label class="">
												<input type="radio" class="ls-req scr-ipt store-ipt kojin-req hojin-req kasanShiharaiCheck" name="i-BonusKasanMonthKbn" id="i-BonusKasanMonthKbnF" value="91" />
												する（毎年8月・12月）
											</label>
											<label class="">
												<input type="radio" class="ls-req scr-ipt store-ipt kojin-req hojin-req kasanShiharaiCheck" name="i-BonusKasanMonthKbn" id="i-BonusKasanMonthKbnS" value="90" />
												しない
											</label>
										</div>
									</div>
									<!-- 加算支払金 -->
									<div class="input-item">
										<div class="text-m bold item-title">加算支払金</div>
										<div class="text-s">回数</div>
										<div class="">
											<div class=" text-with-unit">
												<input type="text" class="num read-only" id="i-BonusKasanKaisu" disabled />
												<span class="unit">回</span>
											</div>
										</div>
										<div class="text-s">金額</div>
										<div class="">
											<div class=" text-with-unit">
												<input type="text" class="price scr-ipt store-ipt" id="i-BonusKasanPrice" disabled />
												<span class="unit">円</span>
											</div>
										</div>
									</div>
									<!-- 計算ボタン -->
									<div class="input-item">
										<div class="text-l bold">ローンシミュレーション</div>
										<div class="text-m">各金額を入力・編集した場合には必ず再計算してください。</div>
										<div class="btn-container">
											<button type="button" id="loan-simulation-btn" class="btn main exec-ls" disabled>試算</button>
										</div>
									</div>
									<!-- エラーメッセージ -->
									<div class="ls-err-msg hide">
										<i class="fa-solid fa-triangle-exclamation"></i>
										<span class="err-msg-body" id="errMsg"></span>
									</div>
									<!-- お支払金額タイトル -->
									<div class="items-title text-l bold">お支払金額</div>
									<!-- 第１回分割支払金 -->
									<div class="input-item">
										<div class="text-m bold item-title">第１回分割支払金</div>
										<div class="text-s">試算情報が設定されます編集できません。</div>
										<div class=" text-with-unit">
											<input type="text" class="read-only price" id="i-BunkatsuShiharai1" disabled />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- 第２回以降分割支払金 -->
									<!--									<div class="input-item">-->
									<!--										<div class="text-m bold item-title">第２回以降分割支払金</div>-->
									<!--										<div class=" text-with-unit">-->
									<!--											<input type="text" class="read-only price" id="i-BunkatsuShiharai2" disabled />-->
									<!--											<span class="unit">円</span>-->
									<!--										</div>-->
									<!--									</div>-->
									<!-- 回数 -->
									<div class="input-item">
										<div class="text-m bold item-title">回数</div>
										<div class=" text-with-unit">
											<input type="text" class="read-only" id="i-BunkatsuKaisu" disabled />
											<span class="unit">回</span>
										</div>
									</div>
									<!-- ④分割払手数料 -->
									<div class="input-item">
										<div class="text-m bold item-title">④分割払手数料</div>
										<div class=" text-with-unit">
											<input type="text" class="read-only price" id="i-BunkatsuTesuryo" disabled />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- ⑤分割支払金合計（③＋④） -->
									<div class="input-item">
										<div class="text-m bold item-title">⑤分割支払金合計（③＋④）</div>
										<div class=" text-with-unit">
											<input type="text" class="read-only price" id="i-BunkatsuShiharaiTotal" disabled />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- ⑥お支払総額（②＋⑤） -->
									<div class="input-item">
										<div class="text-m bold item-title">⑥お支払総額（②＋⑤）</div>
										<div class=" text-with-unit">
											<input type="text" class="read-only price" id="i-TotalShiharai" disabled />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- 初回加算請求タイトル -->
									<div class="items-title text-l bold">初回加算請求</div>
									<div class="items-title text-m bold">下記は初回に加算請求させて頂きます。</div>
									<!-- 所有権留保費用（税込） -->
									<div class="input-item">
										<div class="text-m bold item-title">所有権留保費用（税込）</div>
										<div class="text-s">所有権留保が必要な場合に金額が設定されます。</div>
										<div class=" text-with-unit">
											<input type="text" class="read-only price" id="i-ShoyukenRyuhoPrice" />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- 納車日 -->
									<div class="items-title text-l bold">納車日</div>
									<!-- 区分 -->
									<div class="input-item">
										<div class="text-m bold item-title">区分</div>
										<div class="">
											<select class="width-def store-ipt scr-ipt kojin-req hojin-req" id="i-NoshaDateKbn">
												<option value="" selected>選択してください</option>
												<option value="1">契約日から20日以内</option>
												<option value="2">日付指定</option>
											</select>
										</div>
									</div>
									<!-- 年月日 -->
									<div class="input-item">
										<div class="text-m bold item-title">年月日</div>
										<div class=" grid-3">
											<select class="year store-ipt scr-ipt nosha-date" id="i-NoshaDateYear" disabled>
											</select>
											<select class="month store-ipt scr-ipt nosha-date" id="i-NoshaDateMonth" disabled>
											</select>
											<select class="day store-ipt scr-ipt nosha-date" id="i-NoshaDateDay" disabled>
											</select>
										</div>
									</div>
									<!-- 販売店からの連絡事項 -->
									<div class="items-title text-l bold">販売店からの連絡事項</div>
									<div class="input-item">
										<div class="">
											<textarea rows="" cols="" class="store-ipt scr-ipt" id="i-StoreRenrakuJiko"></textarea>
										</div>
									</div>
									<!-- 問い合わせご相談販売店情報 -->
									<div class="items-title text-l bold">問い合わせご相談販売店情報</div>
									<div class="page-detail text-m">下記販売店は、お客様が本契約に基づき記入した情報（当画面の入力項目※印項目欄に掲載された個人情報）を本契約の履行に関する利用以外に、新商品・サービスに関する情報提供・案内のため利用することがあります。</div>
									<!-- 加盟店番号 -->
									<div class="input-item">
										<div class="text-m bold item-title">加盟店番号</div>
										<div class="">
											<input type="text" class="width-def read-only" id="i-StoreId" />
										</div>
									</div>
									<!-- 条件コード -->
									<div class="input-item">
										<div class="text-m bold item-title">条件コード</div>
										<div class="">
											<input type="text" class="width-def read-only" id="i-JokenCode" />
										</div>
									</div>
									<!-- 販売店 -->
									<div class="input-item">
										<div class="text-m bold item-title">販売店</div>
										<div class="">
											<input type="text" class="width-def read-only" id="i-StoreName" />
										</div>
									</div>
									<!-- 代表者名 -->
									<div class="input-item">
										<div class="text-m bold item-title">代表者名</div>
										<div class="">
											<input type="text" class="width-def read-only" id="i-StoreDaihyoName" />
										</div>
									</div>
									<!-- 住所 -->
									<div class="input-item">
										<div class="text-m bold item-title">住所</div>
										<div class="">
											<input type="text" class="width-full read-only" id="i-StoreAddress" />
										</div>
									</div>
									<!-- 電話番号 -->
									<div class="input-item">
										<div class="text-m bold item-title">電話番号</div>
										<div class=" grid-tel">
											<div>
												<input type="text" class="width-full tel-num num read-only" id="i-StoreTel1" />
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num read-only" id="i-StoreTel2" />
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num read-only" id="i-StoreTel3" />
											</div>
										</div>
									</div>
									<!-- 担当者氏名 -->
									<div class="input-item">
										<div class="text-m bold item-title">販売担当者氏名</div>
										<div class="text-s">全角文字で入力してください。</div>
										<div class="">
											<input type="text" class="width-def zenkaku store-ipt kojin-req hojin-req main-req" id="i-StoreTantoName" />
										</div>
									</div>
									<!-- 担当者電話番号 -->
									<div class="input-item">
										<div class="text-m bold item-title">電話番号</div>
										<div class="text-s">半角数字で入力してください。</div>
										<div class=" grid-tel">
											<div>
												<input type="text" class="width-full tel-num num store-ipt kojin-req hojin-req main-req " id="i-StoreTantoTel1" />
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num store-ipt kojin-req hojin-req main-req " id="i-StoreTantoTel2" />
											</div>
											<label class="hyphen">-</label>
											<div>
												<input type="text" class="width-full tel-num num store-ipt kojin-req hojin-req main-req " id="i-StoreTantoTel3" />
											</div>
										</div>
									</div>
									<!-- 代行入力情報 -->
									<div class="item-block only-daiko-view">
										<div class="items-title text-l bold">代行入力情報</div>
										<!-- 申込方法 -->
										<div class="input-item">
											<div class="text-m bold item-title">申込方法</div>
											<div class="radio-container">
												<label class="">
													<input type="radio" name="i-DaikoMoshikomiKbn" id="i-DaikoMoshikomiKbnF" value="1" />
													FAX
												</label>
												<label class="">
													<input type="radio" name="i-DaikoMoshikomiKbn" id="i-DaikoMoshikomiKbnS" value="0" />
													メール
												</label>
											</div>
										</div>
									</div>

									<div class="mccs-info-ipt item-block">
										<div class="items-title text-l bold">MCCS機器取付場所</div>
										<div class="text-m">
											MCCS機器の発送先情報と取付担当者情報を入力してください。
											<br>
											以下のボタン押下で前回申込時に入力した発送先情報と取付担当者情報を反映します。
										</div>
										<div class="btn-container">
											<button type="button" id="mccs-pre-input-btn" class="btn main ">前回入力内容を反映</button>
										</div>
										<!-- 申込方法 -->
										<div class="items-title text-l bold">発送先情報</div>
										<div class="input-item mccs-req">
											<div class="text-m bold item-title">送付先名称</div>
											<div class="text-s">
												MCCSの送付先情報を入力してください。
												<br>
												本申込時に変更可能です。
											</div>
											<div>
												<input type="text" class="store-ipt" id="i-MccsSofusakiName" />
											</div>
										</div>
										<div class="input-item mccs-req">
											<div class="text-m bold item-title">送付先郵便番号</div>
											<div class="text-s">半角数字で入力してください。本申込時に変更可能です。</div>
											<div>
												<input type="text" class="num store-ipt" placeholder="0000000" id="i-MccsSofusakiPost" />
											</div>
										</div>
										<!-- 住所１ -->
										<div class="input-item mccs-req">
											<div class="text-m bold item-title">住所１</div>
											<div class="text-s">
												都道府県を選択してください。
												<br>
												本申込時に変更可能です。
											</div>
											<div class="">
												<select class="select-kbn width-def store-ipt todofuken" id="i-MccsSofusakiAddress1">
												</select>
											</div>
										</div>
										<!-- 住所２ -->
										<div class="input-item mccs-req">
											<div class="text-m bold item-title">住所２</div>
											<div class="text-s">
												全角文字で市区町村以降すべてを入力してください。
												<br>
												本申込時に変更可能です。
											</div>
											<div class="width-full">
												<input type="text" class="zenkaku address width-full store-ipt" id="i-MccsSofusakiAddress2" />
											</div>
										</div>
										<!-- 住所3 -->
										<div class="input-item">
											<div class="text-m bold item-title">住所３</div>
											<div class="text-s">
												全角文字で支店名やその他ビル名称などを入力してください。
												<br>
												本申込時に変更可能です。
											</div>
											<div class="width-full">
												<input type="text" class="zenkaku address width-full store-ipt" id="i-MccsSofusakiAddress3" />
											</div>
										</div>
										<!-- 電話番号 -->
										<div class="input-item mccs-req">
											<div class="text-m bold item-title">発送先電話番号</div>
											<div class="text-s">半角数字で入力してください。本申込時に変更可能です。</div>
											<div class=" grid-tel">
												<div>
													<input type="text" class="width-full tel-num num store-ipt" id="i-MccsSofusakiTel1" />
													<div class="text-s error hide">半角数字で6桁以内で入力してください。</div>
												</div>
												<label class="hyphen">-</label>
												<div>
													<input type="text" class="width-full tel-num num store-ipt" id="i-MccsSofusakiTel2" />
													<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
												</div>
												<label class="hyphen">-</label>
												<div>
													<input type="text" class="width-full tel-num num store-ipt" id="i-MccsSofusakiTel3" />
													<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
												</div>
											</div>
										</div>
										<!-- 受付担当者情報 -->
										<div class="items-title text-l bold">受付担当者情報</div>
										<!-- 担当者氏名 -->
										<div class="input-item mccs-req">
											<div class="text-m bold item-title">受付担当者名</div>
											<div class="text-s">全角文字で入力してください。本申込時に変更可能です。</div>
											<div class="">
												<input type="text" class="width-def zenkaku store-ipt" id="i-MccsUketsukeTantoName" />
											</div>
										</div>
										<!-- 電話番号 -->
										<div class="input-item mccs-req">
											<div class="text-m bold item-title">受付担当者電話番号</div>
											<div class="text-s">半角数字で入力してください。本申込時に変更可能です。</div>
											<div class=" grid-tel">
												<div>
													<input type="text" class="width-full tel-num num store-ipt" id="i-MccsUketsukeTantoTel1" />
													<div class="text-s error hide">半角数字で6桁以内で入力してください。</div>
												</div>
												<label class="hyphen">-</label>
												<div>
													<input type="text" class="width-full tel-num num store-ipt" id="i-MccsUketsukeTantoTel2" />
													<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
												</div>
												<label class="hyphen">-</label>
												<div>
													<input type="text" class="width-full tel-num num store-ipt" id="i-MccsUketsukeTantoTel3" />
													<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
												</div>
											</div>
										</div>
										<!-- 取付担当者情報 -->
										<div class="items-title text-l bold">取付担当者情報</div>
										<!-- 担当者氏名 -->
										<div class="input-item mccs-req">
											<div class="text-m bold item-title">取付担当者名</div>
											<div class="text-s">MCCSの取付担当者情報を入力してください。</div>
											<div class="text-s">本申込時に変更可能です。</div>
											<div class="">
												<input type="text" class="width-def store-ipt" id="i-MccsToritsukeTantoName" />
											</div>
										</div>
										<!-- 電話番号 -->
										<div class="input-item mccs-req">
											<div class="text-m bold item-title">取付担当者電話番号</div>
											<div class="text-s">半角文字で入力してください。本申込時に変更可能です。</div>
											<div class=" grid-tel">
												<div>
													<input type="text" class="width-full tel-num num store-ipt" id="i-MccsToritsukeTantoTel1" />
													<div class="text-s error hide">半角数字で6桁以内で入力してください。</div>
												</div>
												<label class="hyphen">-</label>
												<div>
													<input type="text" class="width-full tel-num num store-ipt" id="i-MccsToritsukeTantoTel2" />
													<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
												</div>
												<label class="hyphen">-</label>
												<div>
													<input type="text" class="width-full tel-num num store-ipt" id="i-MccsToritsukeTantoTel3" />
													<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
												</div>
											</div>
										</div>
									</div>
								</div>

								<!-- ボタン -->
								<div class="btn-container">
									<button type="button" class="page-btn btn sub back-btn" name="form-okuruma">戻る</button>
									<button type="button" class="btn main next-btn to-confirm-btn" name="form-input-confirm" disabled>次へ</button>
								</div>

							</div>
						</div>
					</form>

					<!-- 入力内容確認 -->
					<%@ include file="/core/application-confirm/application-confirm.jsp"%>

				</div>
			</div>
		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
</body>
</html>