<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/core/common/import.jsp"%>
<!--ページ専用js-->

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link href="./core/reset-password/reset-password-send.css" rel="stylesheet">
<script type="text/javascript" src="./core/reset-password/reset-password-send.js"></script>
<title>パスワード再設定通知送信画面‗完了</title>
</head>
<body>
	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>
			<div class="container">
				<!-- <input type="hidden" name="hiddenField" value="<s:property value='sendMethod' />" /> -->
				<div class="textArea">
					<h1>パスワードをお忘れの方</h1>
				</div>

				<s:if test="sendMethod != 'email'">
					<!-- SMS送信 -->
					<div class="displayBox">
						<div class="textArea">
							<p>
								<i class="fa-regular fa-circle-check"></i>
								SMSを送信しました。
							</p>
						</div>
					</div>
					<div class="textArea2">
						<h2>
							<br />
							以下の電話番号にパスワード再設定手順を説明したSMSをお送りしました。
							<br />
							<br />
							<s:property value="customer_tel_number1" />
							<s:property value="tel_number1" />
							-****-
							<s:property value="customer_tel_number3" />
							<s:property value="tel_number3" />
							<br />
							数分経っても届かない場合は電話番号を確認し、もう一度お試しいただくか、USSサポートサービスまでご連絡ください。
							<br />
							<br />
						</h2>
					</div>
				</s:if>

				<s:if test="sendMethod == 'email'">
					<!-- メール送信 -->
					<div class="displayBox">
						<div class="textArea">
							<p>
								<i class="fa-regular fa-circle-check"></i>
								メールを送信しました。
							</p>
						</div>
					</div>

					<div class="textArea2">
						<h2>
							<br />
							以下のメールアドレスにパスワード再設定手順を説明したメールをお送りしました。
							<br />
							<br />
							<s:property value="email" />
							<br />
							数分経っても届かない場合はメールアドレスを確認し、もう一度お試しいただくか、USSサポートサービスまでご連絡ください。
							<br />
							<br />
						</h2>
					</div>
				</s:if>

				<!-- 戻る -->
				<div class="textArea3">
					<p>
						<a href="/uss-ss-al-web/login" class="back">ログイン画面に戻る</a>
					</p>
				</div>
			</div>
		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
</body>

</html>