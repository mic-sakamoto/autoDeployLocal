<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/core/common/import.jsp"%>
<!--ページ専用js-->

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link href="./core/reset-password/reset-password.css" rel="stylesheet">
<script type="text/javascript" src="./core/reset-password/reset-password.js"></script>
<title>パスワード再設定通知送信画面</title>
</head>
<body>
	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>
			<div class="container">

				<div class="tabbox-container">

					<div class="title-area">
						<span class="title-item">パスワードをお忘れの方</span>
						<div class="text-s">
							加盟店様、ご契約者様のパスワード再設定用メールを送信します。
							<br />
							ユーザーIDもしくは契約番号を入力してください。
						</div>
					</div>

					<div class="tabbox-div">
						<!-- タブ -->
						<div class="tabs tabs-2">
							<button class="tab-btn btn active" data-tab="login-tab">ログインIDから設定</button>
							<button class="tab-btn btn" data-tab="customer-tab">ご契約者様情報から設定</button>
						</div>

						<!-- ログインIDから設定 -->
						<form class="login_inputBox" id="login_form" onsubmit="return false;">

							<div class="tab-content-area login active" id="login-tab">

								<!-- 入力エリア -->
								<div class="input-item">
									<div class="loginId_inputField">
										<input class="input-login-id width-full inputcheck" name="loginId" id="loginId" type="text" placeholder="ログインID" value="" />
									</div>
								</div>
								<div class="input-item">
									<div class="text-m item-title">パスワード再設定のご案内の送信先を選んでください。</div>
									<div class="radio-container">
										<label>
											<input type="radio" class="" id="sendMethodSms" name="sendMethod" value="sms" />
											SMS送信
										</label>
										<label>
											<input type="radio" class="" id="sendMethodMail" name="sendMethod" value="email" />
											メール送信
										</label>
									</div>
								</div>


								<!-- SMS送信 -->
								<div class="input-item login_SMS_inputArea hide" id="smsInputArea">
									<div class=" grid-tel">
										<div class="login_tel_inputField">
											<input type="text" class="width-full tel-num num inputcheck" id="tel_number1" name="tel_number1" placeholder="携帯電話番号" />
											<div class="text-s error hide">半角数字で6桁以内で入力してください。</div>
										</div>
										<label class="hyphen">-</label>
										<div class="login_tel_inputField">
											<input type="text" class="width-full tel-num num inputcheck" id="tel_number2" name="tel_number2" placeholder="携帯電話番号" />
											<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
										</div>
										<label class="hyphen">-</label>
										<div class="login_tel_inputField">
											<input type="text" class="width-full tel-num num inputcheck" id="tel_number3" name="tel_number3" placeholder="携帯電話番号" />
											<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
										</div>
									</div>
								</div>

								<!-- メール送信 -->
								<div class="input-item hide" id="emailInputArea">
									<div class="width-full">
										<input type="text" class="width-full inputcheck" id="email" name="email" placeholder="メールアドレス" value="" />
										<div class="text-s error hide">全角文字で27文字以内で入力してください。</div>
									</div>
								</div>

								<!-- 送信ボタン -->
								<div class="btn-div">
									<button class="login_Button btn main send-btn" id="sendBtn_loginId" disabled>送信</button>
								</div>
							</div>
						</form>

						<!-- ご契約者様情報から設定 -->
						<form class="customer_inputBox" id="form" onsubmit="return false;">
							<div class="tab-content-area customer" id="customer-tab">
								<!-- 入力エリア -->
								<div class="input-item loginId_inputField">
									<div class="width-full">
										<input type="text" class="width-full inputcheck" id="contract_number" name="email" placeholder="契約番号" value="" />
									</div>
								</div>
								<div class="input-item customer_name_inputArea grid-2">
									<div class="customer_name_inputField">
										<input type="text" class=" width-full zenkaku name-sei inputcheck" id="last_name_kana" placeholder="セイ" />
									</div>
									<div class="customer_name_inputField">
										<input type="text" class=" width-full zenkaku name-mei inputcheck" id="first_name_kana" placeholder="メイ" />
									</div>
								</div>

								<div class="customer_birth_inputArea grid-3">
									<select class="year inputcheck" id="birth_year">
									</select>
									<select class="month inputcheck" id="birth_month">
									</select>
									<select class="day inputcheck" id="birth_date">
									</select>
								</div>

								<!-- SMS送信 -->
								<div class="input-item customer_tel_inputArea" id="smsInputArea">
									<div class=" grid-tel">
										<div class="login_tel_inputField">
											<input type="text" class="width-full tel-num num inputcheck" id="customer_tel_number1" name="customer_tel_number1" placeholder="000" />
											<div class="text-s error hide">半角数字で6桁以内で入力してください。</div>
										</div>
										<label class="hyphen">-</label>
										<div class="login_tel_inputField">
											<input type="text" class="width-full tel-num num inputcheck" id="customer_tel_number2" name="customer_tel_number2" placeholder="0000" />
											<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
										</div>
										<label class="hyphen">-</label>
										<div class="login_tel_inputField">
											<input type="text" class="width-full tel-num num inputcheck" id="customer_tel_number3" name="customer_tel_number3" placeholder="0000" />
											<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
										</div>
									</div>
								</div>

								<!-- 送信ボタン -->
								<div class="buttonArea">
									<button class="customer_button btn main send-btn width-full" id="sendBtn_customerInfo" disabled>携帯電話番号にSMS送信</button>
								</div>

							</div>
						</form>
					</div>
				</div>

				<!-- エラーメッセージ -->
				<div class="errorMsgContainer hide" id="loginErrorMsgContainer">
					<div class="errorMsgContent">
						<img class="svg status-svg-icon" src="./images/svg/exclamation-triangle.svg">
						<span id="errorMsg"></span>
					</div>
				</div>

			</div>
		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
</body>

</html>