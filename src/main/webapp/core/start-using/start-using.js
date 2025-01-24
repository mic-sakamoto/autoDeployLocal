$(function() {
	//SMS送信ボタン押下
	$('#sendSMS').on('click', function() { sendSMS() });

	//入力済みチェック→ボタン有効化
	$('.inputcheck').on('input change focusout focus', function() { checkInputs(); });

	//携帯電話番号数字限定
	$('.tel_inputField input').on('input change', function() { limitNumber(this); });
});

//SMS送信
function sendSMS() {
	var postData = gatherInputs("#form", true);
	doAjax(postData, "sendSMS", "./start-using").then(function(data) {
		if (data.successFlg == '1') {
			doPost($("#form"), "sendComplete", "./start-using");
		} else if (data.successFlg == '9') {
			$('#errorBox').show(); // エラーメッセージ全体を表示
		}
	});
}


//入力欄が満たされた場合にボタンを有効化
function checkInputs() {
	let result = true;
	$('#form').find('.inputcheck').each(function() {
		if ($(this).val().length < 1) {
			result = false;
			return false;
		}
	});

	if (result) {
		$('.send-btn').prop('disabled', false);
	} else {
		$('.send-btn').prop('disabled', true);
	}
}

//携帯電話番号数字限定
function limitNumber(inputElement) {
	const inputSelector = '#' + inputElement.id;
	const alertSelector = $(inputElement).next('.tel_number_alert');

	const inputVal = $(inputSelector).val().replace(/[^0-9]/g, '');
	$(inputSelector).val(inputVal);

	if (!/^[0-9]{1,4}$/.test(inputVal)) {
		$(inputSelector).val('');
		$(alertSelector).show();
	} else {
		$(alertSelector).hide('');
	}
}