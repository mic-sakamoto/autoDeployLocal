$(document).ready(function() {

	// 運転免許証番号チェックデジット
	$(document).on('blur', '#licenseNumber', function(event) { checkDigit(); });
	$(document).on('click', 'input[name="inputSelectKbn"]', function(event) { checkDigit(); });

	// 申込種別選択制御
	$(document).on('click', 'input[name="inputSelectKbn"]', function(event) {
		licenseNumberDisplay();
		inputModeDisplay();
	});

	//免許証番号・法人番号を半角変換
	$(document).on('change', '#licenseNumber', function(event) { setHankaku('#licenseNumber'); });

	// 申込モード制御
	$(document).on('click', 'input[name="inputMode"]', function(event) { loginMethodDisplay(); });

	// ご契約者様専用ページのログイン手順送信制御
	$(document).on('click', 'input[name="loginMethod"]', function(event) { loginMethodDisabled(); });


	// 「お申込の入力画面へ進む」ボタン制御
	$(document).on('blur', '.checkInput', function(event) { checkDisabledBtn(); });
	$(document).on('click', 'input[name="inputSelectKbn"]', function(event) { checkDisabledBtn(); });
	$(document).on('click', 'input[name="inputMode"]', function(event) { checkDisabledBtn(); });
	$(document).on('click', 'input[name="loginMethod"]', function(event) { checkDisabledBtn(); });

	// お申込入力画面に遷移
	$(document).on('click', '#next-btn', function() { toApplication() });

	//モーダル内「閉じる」ボタン押下
	$(document).on('click', '#modalCloseBtn', function(event) { modalClose(); });

});

function toApplication() {
	var postData = {};

	postData['inputMapJson'] = $('#inputMap').val();
	postData['appKbn'] = "1";
	postData['statusKbn'] = '1';
	postData['inputModeKbn'] = $('input[name="inputMode"]:checked').val();
	postData['moshikomiKbn'] = $('input[name="inputSelectKbn"]:checked').val();
	if ($('input[name="inputSelectKbn"]:checked').val() === '1') {
		postData['licenseNumber'] = $('#licenseNumber').val();
	} else {
		postData['hojinNumber'] = $('#licenseNumber').val();
	}

	if ($('input[name="inputMode"]:checked').val() === '1') {
		postData['sendMethod'] = $('[name="loginMethod]:checked').val();
		postData['mailAddress'] = $('#mailAddress').val();
		postData['mobileNum1'] = $('#mobile-num-1').val();
		postData['mobileNum2'] = $('#mobile-num-2').val();
		postData['mobileNum3'] = $('#mobile-num-3').val();
		doAjax(postData, "sendLoginMethod", "./select-input-method").then(function(data) {
			if (data.successFlg == '1') {
				let toastOptions = {
					text: $('#text_send').val(),
					hideAfter: 1500,
					position: 'mid-center',
					icon: 'success',
					afterHidden: function() {
						modalClose();
						doPostWithData(postData, "application", "./application");
					}
				};
				$.toast(toastOptions);
			}
		});
	} else {
		doPostWithData(postData, "application", "./application");
		modalClose();
	}
}

function checkDigit() {
	if ($('#licenseNumber').val() === "") {
		return;
	}
	var postData = gatherInputs("#select-input-method-form", true);
	console.log(postData);
	
	doAjax(postData, "checkDigit", "./select-input-method").then(function(data) {
		if (data.errormessage != "" && data.errormessage != null) {
			$('#errorMsg').html(data.errormessage);
			$('#errorMsg').val(data.errormessage);
			$('#errorMsg').removeClass('hide');
		} else {
			$('#errorMsg').html("");
			$('#errorMsg').val("");
			$('#errorMsg').addClass("hide");
		}
		checkDisabledBtn();
	});

}

function licenseNumberDisplay() {
	const inputSelectKbn = $('input[name="inputSelectKbn"]:checked').val();
	if (inputSelectKbn == "1") {
		$('#licenseNumber_title').html($('#text_licenseNumber_title_1').val());
		$('.licenseNumber-div').css('display', '');
	} else if (inputSelectKbn == "2") {
		$('#licenseNumber_title').html($('#text_licenseNumber_title_2').val());
		$('.licenseNumber-div').css('display', '');
	} else {
		$('.licenseNumber-div').css('display', 'none');
	}
}

function inputModeDisplay() {
	const inputSelectKbn = $('input[name="inputSelectKbn"]:checked').val();
	if (inputSelectKbn != null && inputSelectKbn != "") {
		$('.inputMode-div').css('display', '');
	} else {
		$('.inputMode-div').css('display', 'none');
	}
}

function loginMethodDisplay() {
	const selectedMode = $('input[name="inputMode"]:checked').val();
	if (selectedMode === "0") {
		// 「このまま加盟店ページで入力」選択時
		$('.loginMethod-div').css('display', 'none');
		$('#next-btn-div').css('display', '');
	} else if (selectedMode === "1") {
		// 「ご契約者様専用頁で顧客が入力」送信選択時
		$('.loginMethod-div').css('display', '');
		$('#next-btn-div').css('display', 'none');
	}
}

function loginMethodDisabled() {
	const selectedMethod = $('input[name="loginMethod"]:checked').val();
	if (selectedMethod === "email") {
		// 「メールアドレス」選択時
		$('.input-mail').css('display', 'block');
		$('.input-tel').css('display', 'none');
		$('#next-btn-div').css('display', '');
	} else if (selectedMethod === "sms") {
		// 「SMS」送信選択時
		$('.input-mail').css('display', 'none');
		$('.input-tel').css('display', 'block');
		$('#next-btn-div').css('display', '');
	}
}

function checkDisabledBtn() {
	var btnFlg = false;
	const selectedMode = $('input[name="inputMode"]:checked').val();
	const selectedMethod = $('input[name="loginMethod"]:checked').val();

	if (selectedMode === "0") {
		// 「このまま加盟店ページで入力」選択時
		if ($('#licenseNumber').val() != "" && $('#errorMsg').val() == "") {
			btnFlg = true;
		}
	} else if (selectedMode === "1" && selectedMethod === "email") {
		// 「ご契約者様専用頁で顧客が入力」送信・「メールアドレス」選択時
		if ($('#licenseNumber').val() != "" && $('#errorMsg').val() == ""
			&& $('#mailAddress').val() != "") {
			btnFlg = true;
		}
	} else if (selectedMode === "1" && selectedMethod === "sms") {
		// 「ご契約者様専用頁で顧客が入力」送信・「SMS」送信選択時
		if ($('#licenseNumber').val() != "" && $('#errorMsg').val() == ""
			&& $('#mobile-num-1').val() != "" && $('#mobile-num-2').val() != "" && $('#mobile-num-3').val() != "") {
			btnFlg = true;
		}
	}

	if (btnFlg) {
		$('#next-btn').prop('disabled', false);
	} else {
		$('#next-btn').prop('disabled', true);
	}
}

function modalClose() {
	$('.select-input-method-modal').removeClass('active');
	modalClear();
}

function modalClear() {
	$('input[name="inputSelectKbn"]').prop('checked', false);
	$('input[name="inputMode"]').prop('checked', false);
	$('input[name="loginMethod"]').prop('checked', false);
	$('#licenseNumber').val('');
	$('#mailAddress').val('');
	$('#mobile-num-1').val('');
	$('#mobile-num-2').val('');
	$('#mobile-num-3').val('');

	$('#errorMsg').html("");
	$('#errorMsg').val("");
	$('#errorMsg').addClass("hide");

	$('.licenseNumber-div').css('display', 'none');
	$('.inputMode-div').css('display', 'none');
	$('.loginMethod-div').css('display', 'none');
	$('.input-mail').css('display', 'none');
	$('.input-tel').css('display', 'none');
	$('#next-btn-div').css('display', 'none');
}
