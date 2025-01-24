<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/core/common/import.jsp"%>
<!--ページ専用js-->


<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
</head>
<link href="./core/login/login.css?t=<%=df.format(date)%>" rel="stylesheet">
<script type="text/javascript" src="./core/login/login.js?t=<%=df.format(date)%>"></script>

<html>

<body>

	<div class="wrapper">
		<main>

			<div class="container">

				<div class="tabbox-container">
					<!-- 企業ロゴ -->
					<div class="logo-div">
						<img src="./images/USS-SS_logo_main.png" class="logo" />
					</div>

					<div class="tabbox-div">
						<!-- タブ -->
						<div class="tabs tabs-2">
							<button class="tab-btn btn active" data-tab="store-tab" id="store-tab-btn">加盟店様専用</button>
							<button class="tab-btn btn" data-tab="customer-tab" id="customer-tab-btn">ご契約者様専用</button>
						</div>

						<!-- 加盟店様専用 -->
						<div class="tab-content-area store active" id="store-tab">
							<!-- テキストエリア -->
							<div class="input-item">
								<div>
									USS-SSオートローン取り扱いサービスについて
									<br />
									■加盟店様専用ページ
									<br />
									ローンのお申し込みからご契約状況のご確認、お問い合せ等に
									<br />
									ご利用いただけます。
								</div>
							</div>

							<!-- 入力エリア -->
							<form id=storeForm>
								<div class="input-item">
									<div>
										<input class="input-login-id width-full" name="loginId" type="text" placeholder="ログインIDを入力" value="" />
									</div>
									<div>
										<div class="passwordField">
											<input class="input-login-password width-full" name="password" type=password placeholder="パスワードを入力" value="" />
											<button type="button" class="passwordToggle">
												<img class="svg status-svg-icon password-icon eye hide" src="./images/svg/eye.svg">
												<img class="svg status-svg-icon password-icon eye-slash" src="./images/svg/eye-slash.svg">
											</button>
										</div>
										<a class="forgotPassword link"> パスワードをお忘れですか？ </a>
									</div>
									<input type="hidden" class="loginRole" value="2" />
								</div>
							</form>

							<!-- ボタンエリア -->
							<div class="buttonArea">
								<div class="privacyPolicy">
									<a class="privacyPolicyLink link">プライバシーポリシー</a>
									に同意の上、ログインする
								</div>
								<button class="loginButton store btn main" id="storeLoginBtn" disabled="disabled">ログイン</button>
							</div>
						</div>


						<!-- ご契約者様専用 -->
						<div class="tab-content-area customer" id="customer-tab">

							<!-- テキストエリア -->
							<div class="input-item">
								<div>
									USS-SSオートローン取り扱いサービスについて
									<br />
									■ご契約者様専用ページ
									<br />
									ご契約者様の内容確認、各種お届け、お問合せ等にご利用頂けます。
								</div>
								<a class="customerStartLink link"> ご契約者様専用：ご利用開始はこちら </a>
							</div>

							<!-- 入力エリア -->
							<form id="customerForm">
								<div class="input-item">
									<div>
										<input class="input-login-id width-full" name="loginId" type="text" placeholder="ログインIDを入力" value="" />
									</div>
									<div>
										<div class="passwordField">
											<input class="input-login-password width-full" name="password" type="password" placeholder="パスワードを入力" value="" />
											<button type="button" class="passwordToggle">
												<img class="svg status-svg-icon password-icon eye hide" src="./images/svg/eye.svg">
												<img class="svg status-svg-icon password-icon eye-slash" src="./images/svg/eye-slash.svg">
											</button>
										</div>
										<a class="forgotPassword link"> パスワードをお忘れですか？ </a>
									</div>
									<input type="hidden" class="loginRole" value="3" />
								</div>
							</form>

							<!-- ボタンエリア -->
							<div class="btn-div">
								<div class="privacyPolicy">
									<a href="" class="privacyPolicyLink link">プライバシーポリシー</a>
									に同意の上、ログインする
								</div>
								<button class="loginButton customer btn main" id="customerLoginBtn" disabled="disabled">ログイン</button>
							</div>

						</div>
					</div>
				</div>

				<!-- エラーメッセージ -->
				<div class="errorMsgContainer hide" id="loginErrorMsgContainer">
					<div class="errorMsgContent">
						<img class="svg status-svg-icon" src="./images/svg/exclamation-triangle.svg">
						<span id="loginErrorMsgTitle"></span>
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
</body>

</html>