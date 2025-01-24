$(function() {
	//タブ切替
	$('.tab-btn').on('click', function() { setActiveTab(this) });

	//SMS・メールアドレス選択
	$('input[name="sendMethod"]').on('change', function() { toggleInputArea(); });

	//入力済みチェック→ボタン有効化
	$('.inputcheck').on('input change focusout focus', function() { checkInputs(); });

	//「ログインIDから設定」送信ボタン押下
	$('#sendBtn_loginId').on('click', function() { resetPasswordWithId() });

	//「ご契約者様情報から設定」SMS送信ボタン押下
	$('#sendBtn_customerInfo').on('click', function() { resetPasswordWithCustomerInfo() });
});

//タブ切替処理
function setActiveTab(activeTab) {
	//タブ切替ボタンのactive切替
	$('.tab-btn').removeClass('active');
	$(activeTab).addClass('active');
	//タブのactive切替
	$('.tab-content-area').removeClass('active');
	$('#' + $(activeTab).data('tab')).addClass('active');
}

//SMS・メールアドレス選択
function toggleInputArea() {
	var smsInputArea = $('#smsInputArea');
	var emailInputArea = $('#emailInputArea');
	var sendPassword = $('input[name="sendMethod"]:checked').val();

	if (sendPassword === 'sms') {
		smsInputArea.removeClass("hide");
		emailInputArea.addClass("hide");
	} else if (sendPassword === 'email') {
		smsInputArea.addClass("hide");
		emailInputArea.removeClass("hide");
	}
}

//入力欄が満たされた場合にボタンを有効化
function checkInputs() {
	let result = true;
	$('.tab-content-area.active').find('.inputcheck').each(function() {
		if ($('.tab-content-area.active').find('input[type="radio"]').length > 0) {
			if (!$('.tab-content-area.active').find('input[type="radio"]:checked').length > 0) {
				result = false;
				return false;
			}
		}
		if ($(this).closest('.input-item').hasClass('hide')) {
			return true;
		}
		if ($(this).val().length < 1) {
			result = false;
			return false;
		}
	});

	if (result) {
		$('.tab-content-area.active').find('.send-btn').prop('disabled', false);
	} else {
		$('.tab-content-area.active').find('.send-btn').prop('disabled', true);
	}
}

//ログインIDからの送信
function resetPasswordWithId() {
	var postData = gatherInputs("#login_form", true);
	doAjaxNoToast(postData, "resetPasswordWithId", "./reset-password").then(function(data) {
		if (data.successFlg == '1') {
			doPost($("#login_form"), "sendComplete", "./reset-password");
		} else if (data.successFlg == '9') {
			$('#errorMsg').text(data.message);
			$('.errorMsgContainer').removeClass('hide');
		}
	});
}

//SMS送信
function resetPasswordWithCustomerInfo() {
	var postData = gatherInputs("#form", true);
	doAjaxNoToast(postData, "resetPasswordWithCustomerInfo", "./reset-password").then(function(data) {
		if (data.successFlg == '1') {
			doPost($("#form"), "sendComplete", "./reset-password");
		} else if (data.successFlg == '9') {
			$('#errorMsg').text(data.message);
			$('.errorMsgContainer').removeClass('hide');
		}
	});
}