<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/core/common/import.jsp"%>
<!--ページ専用js-->

<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
</head>
<link href="./core/password-set/password-set.css" rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<script type="text/javascript" src="./core/password-set/password-set.js"></script>

<html>

<body>
	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>
			<div class="container">
				<div class="tabbox-container">



					<div class="title-area">
						<span class="title-item">パスワード変更</span>
					</div>

					<div class="contents-area">

						<div class="loginId-block">
							<div id="user-id" class="border-text-item"></div>
							<div class="note">ログインIDはログイン時に必要になります。お忘れにならないように、大切に保管してください。</div>
						</div>

						<hr>

						<div class="password-block">
							<div class="password-item">
								<div class="password-flex-block">
									<div class="password-block">
										<input type="password" class="inputpassword" id="password" placeholder="パスワード" oninput="checkPassword(this)" />
										<i class="fa fa-eye-slash passwordIcon"></i>
									</div>
								</div>
								<div class="password-strength-item">
									<div class="password-strength-bar-item">
										<div id="password-strength-bar" class="strength-bar"></div>
									</div>
									<p id="password-strength-msg" class="strength-msg">脆弱</p>
								</div>
							</div>
							<div class="password-item">
								<div class="password-flex-block">
									<div class="password-block">
										<input type="password" class="inputpassword" id="password-valid" placeholder="新しいパスワードの確認" oninput="checkPassword(this)" />
										<i class="fa fa-eye-slash passwordIcon"></i>
									</div>
								</div>
							</div>

							<div id="password-alert" class="alert" style="display: none; color: red;">パスワードが一致しません</div>

						</div>

						<div class="kiyaku-block">
							<div class="note">利用規約を必ずご確認ください。</div>
							<button type="button" id="open-riyoKiyaku" class="btn sub">利用規約を表示</button>
							<div class="check-item">
								<input type="checkbox" id="check-btn" class="">
								<div class="note">利用規約に同意して利用開始します。</div>
							</div>
						</div>

						<div class="">
							<button type="button" id="login-btn" class="btn main" disabled="true">パスワードを更新してログインする</button>
						</div>

					</div>

					<s:hidden name="agreed-flg" id="agreed-flg" />

				</div>
			</div>
		</main>
	</div>
</body>

</html>