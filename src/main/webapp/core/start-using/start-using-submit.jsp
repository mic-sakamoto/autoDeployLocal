<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/core/common/import.jsp"%>
<!--ページ専用js-->

<html>
<head>
<meta ="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link href="./core/start-using/start-using-submit.css" rel="stylesheet">
<script type="text/javascript" src="./core/start-using/start-using-submit.js"></script>
<title>利用開始_完了</title>
</head>
<body>
	<%@ include file="/core/common/header.jsp"%>
	<div class="container">
		<!-- 表示 -->
		<div class="textArea">
			<h1>ご契約者様専用:利用開始</h1>
			<br />
		</div>

		<!-- SMS送信 -->
		<div class="displayBox">
			<div class="textArea">
				<p>
					<i class="fa-regular fa-circle-check"></i>SMSを送信しました。
				</p>
			</div>
		</div>

		<!-- 説明 -->
		<div class="textArea2">
			<h2>
				<br /> 以下の電話番号にログイン手順を説明したSMSをお送りしました。 <br />
				<br /> <s:property value="tel_number1"/>-****-<s:property value="tel_number3"/>
				<br /> 数分経っても届かない場合は電話番号を確認し、もう一度お試しいただくか、USSサポートサービスまでご連絡ください <br /> <br />
			</h2>
		</div>

		<!-- 戻る -->
		<div class="textArea3">
			<p>
				<a href="/uss-ss-al-web/login" class="back">ログイン画面に戻る</a>
			</p>
		</div>
	</div>
	<%@ include file="/core/common/footer.jsp"%>
</body>

</html>