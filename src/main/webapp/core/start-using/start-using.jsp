<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/core/common/import.jsp"%>
<!--ページ専用js-->

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link href="./core/start-using/start-using.css" rel="stylesheet">
<script type="text/javascript" src="./core/start-using/start-using.js"></script>
<title>利用開始</title>
</head>
<body>
	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%><main>
			<div class="container">

				<div class="tabbox-container">
					<div class="title-area">
						<span class="title-item">ご契約者様専用:利用開始</span>
						<div class="text-s">
							ご契約者様専用サイトでは以下の機能がご利用できます。
							<br />
							・審査お申込内容の入力
							<br />
							・契約書類の確認
							<br />
							・支払予定表の確
							<br />
							・USSサポートサービスへのお問い合わせ
							<br />
							・USSサポートサービスからのご連絡の確認
						</div>
					</div>

					<div class="error hide" id="errorBox">
						<div class="error_textArea">
							<p id="errorMessage">⚠︎契約情報が正しくありません。もう一度ご確認ください。</p>
						</div>
					</div>

					<form class="inputBox" id="form" onsubmit="return false;">
						<div class="contents-area" id="">
							<div class="item-block">

								<div class="input-item">
									<div class="contractNo_inputField">
										<input type="text" class="width-full inputcheck" maxlength="10" id="contract_number" placeholder="契約番号" />
									</div>
								</div>

								<div class="input-item">
									<div class="name_inputArea">
										<div class="name_inputField grid-2">
											<input type="text" class="inputcheck" id="last_name_kana" placeholder="セイ" />
											<input type="text" class="inputcheck" id="first_name_kana" placeholder="メイ" />
										</div>
									</div>
								</div>

								<div class="input-item">
									<div class="birth_inputArea grid-3">
										<select id="birth_year" class="year width-full inputcheck">
										</select>
										<select id="birth_month" class="month width-full inputcheck">
										</select>
										<select id="birth_date" class="day width-full inputcheck">
										</select>
									</div>
								</div>

								<div class="input-item">
									<div class=" grid-tel">
										<div>
											<input type="text" class="width-full tel-num num inputcheck" id="tel_number1" name="tel_number1" />
											<div class="text-s error hide">半角数字で6桁以内で入力してください。</div>
										</div>
										<label class="hyphen">-</label>
										<div>
											<input type="text" class="width-full tel-num num inputcheck" id="tel_number2" name="tel_number2" />
											<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
										</div>
										<label class="hyphen">-</label>
										<div>
											<input type="text" class="width-full tel-num num inputcheck" id="tel_number3" name="tel_number3" />
											<div class="text-s error hide">半角数字で4桁以内で入力してください。</div>
										</div>
									</div>
								</div>

								<!-- 送信ボタン -->
								<div class="btn-div">
									<button class="send-btn btn main width-full" id="sendSMS" disabled="disabled">携帯電話番号にSMS送信</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
</body>

</html>