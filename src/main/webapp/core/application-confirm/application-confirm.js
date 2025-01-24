$(function() {
	$('.open-trigger').click(function() {
		let target = $(this).closest('.confirm-form').find('.confirm-form-body');
		let icon = $(this).find('.open-icon');
		if ($(icon).hasClass('open')) {
			$(icon).removeClass('open');
			$(icon).addClass('close');
			$(target).addClass('hide');
		} else {
			$(icon).addClass('open');
			$(icon).removeClass('close');
			$(target).removeClass('hide');
		}
	});

	$("#sign").jSignature({
		'background-color': 'transparent',
		'decor-color': 'transparent',
		width: 600,
		height: 110,
	});

	$('.sign-modal-open').click(function() {
		const rokekbn = $('#inputRoleKbn').val().replace('/', '');
		if (rokekbn === '3') {
			$('.sign-modal').addClass('active');
			$(this).addClass('sign-target');
		}
	});

	$('.sign-modal-close').click(function() {
		$('.sign-modal').removeClass('active');
		$('.sign-area').removeClass('sign-target');
	});

	$('.sign-confirm').click(function() {
		let copySignature = new Image();
		copySignature.src = $("#sign").jSignature("getData");
		$(".sign-area.sign-target").children().remove();
		$(copySignature).appendTo($(".sign-area.sign-target"));
		$('.sign-modal').removeClass('active');
		$('.sign-area').removeClass('sign-target');
	});

	$('.sign-reset').click(function() {
		$("#sign").jSignature("reset");
	});

	$("#sign-h").jSignature({
		'background-color': 'transparent',
		'decor-color': 'transparent',
		width: 600,
		height: 110,
	});

	$('.sign-modal-h-open').click(function() {
		const rokekbn = $('#inputRoleKbn').val().replace('/', '');
		if (rokekbn === '3') {
			$('.sign-modal-h').addClass('active');
			$(this).addClass('sign-target');
		}
	});

	$('.sign-modal-h-close').click(function() {
		$('.sign-modal-h').removeClass('active');
		$('.sign-area').removeClass('sign-target');
	});

	$('.sign-h-confirm').click(function() {
		let copySignature = new Image();
		copySignature.src = $("#sign-h").jSignature("getData");
		$(".sign-area.sign-target").children().remove();
		$(copySignature).appendTo($(".sign-area.sign-target"));
		$('.sign-modal-h').removeClass('active');
		$('.sign-area').removeClass('sign-target');
	});

	$('.sign-h-reset').click(function() {
		$("#sign-h").jSignature("reset");
	});

	//モーダルの外側をクリックしたらモーダルを閉じる
	//	$(document).on('click', function(e) {
	//		if ($('.modal-container').hasClass('active')) {
	//			if (!$(e.target).closest('.modal-container').length) {
	//				$('.modal-container').removeClass('active');
	//				$('.sign-area').removeClass('sign-target');
	//			}
	//		}
	//	});



	/*申込者国籍の値が日本国籍以外だった場合「在留カード表（枠３）」「連帯保証人確認書類の⑪在留カード（表）」のアップロードが必須*/
	$('input[name="moshikomi-kokuseki"]:radio').on('change', function() { changeMoshikomiKokuseki(this) });
	
	/*保証人国籍の値が日本国籍以外だった場合「在留カード表（枠３）」「連帯保証人確認書類の⑪在留カード（表）」のアップロードが必須*/
	$('input[name="hoshonin-kokuseki"]:radio').on('change', function() { changeHoshoninKokuseki(this) });
	


	$('.file-upload-input').on('change', function() { fileUploadInput(this); });
	$(document).on('click', '.file-clear', function() { fileClear(this); });

	//登録or申込	
	$('.app-confirm').on('click', function() {
		confirmApp();
	});
});

function confirmApp() {

	console.log("confirmApp");
	let formData = {};
	$('.confirm-form input,.confirm-form select').each(function() {
		const $element = $(this);
		let key = $element.attr('id');
		const type = $element.attr('type');

		// ラジオボタンの場合
		if (type === 'radio') {
			let name = $element.attr('name');
			// すでに処理済みのradio groupはスキップ
			if (formData[name]) {
				return;
			}
			// 選択されているradioボタンの値を取得
			const checkedValue = $(`#ls-form input[name="${name}"]:checked`).val();
			if (checkedValue !== null && checkedValue !== undefined) {
				formData[name] = checkedValue;
			}
		} else {
			// 通常のinput, selectの処理
			if (key) {
				formData[key] = $element.val();
			}
		}
	});
	let appKbn = $('#appKbn').val();
	let statusKbn = $('#statusKbn').val();
	//入力時 加盟店入力or顧客入力
	let modeKbn = $('#inputModeKbn').val();
	//入力者のロール
	let roleKbn = $('#inputRoleKbn').val();
	//入力者の種別 個人or法人
	let moshikomiKbn = $('#moshikomiKbn').val();
	//下書き存在フラグ
	let isTempExist = $('#isTempExist').val().replaceAll('/', '');
	//WEB受付ID
	let webAppId = $('#webAppId').val().replaceAll('/', '');
	//ContractId
	let contractId = $('#contractId').val();

	let postData = {};
	postData['inputMapJson'] = window.JSON.stringify(formData);
	postData['appKbn'] = appKbn;
	postData['webAppId'] = webAppId;
	postData['statusKbn'] = statusKbn;
	postData['inputModeKbn'] = modeKbn;
	postData['inputRoleKbn'] = roleKbn;
	postData['moshikomiKbn'] = moshikomiKbn;
	postData['contractId'] = contractId;

	doAjaxNoToast(postData, "confirmApp", "./application").then(function(data) {
		let toastOptions = {
			text: data.message,
			hideAfter: 1000,
			position: 'mid-center'
		};
		if (data.successFlg === '1') {
			toastOptions.icon = 'success';
			toastOptions.afterHidden = function() {
				// 申込状況照会に遷移
				postData = {};
				postData['webAppId'] = data.webAppId;
				postData['status'] = data.statusKbn;
				doAjax(postData, "getNowStatus", "./contract-mgmt").then(function(data2) {
					postData['viewType'] = data2.status;
					doPostWithData(postData, "", "./status");
				});
			};
		} else {
			toastOptions.icon = 'error';
			toastOptions.afterHidden = function() {

			};
		}
		$.toast(toastOptions);
	});
}

/**/
function changeMoshikomiKokuseki(elm) {
	let radioval = $(elm).val();
	if (radioval == '2') {
		$('#file-zairyu-card-front').closest('.file-upload-item').addClass('required');
	} else {
		$('#file-zairyu-card-front').closest('.file-upload-item').removeClass('required');
	}
}

/**/
function changeHoshoninKokuseki(elm) {
	let radioval = $(elm).val();
	console.log(radioval);
	if (radioval == '2') {
		$('#file-h-zairyu-card-front').closest('.file-upload-item').addClass('required');
	} else {
		$('#file-h-zairyu-card-front').closest('.file-upload-item').removeClass('required');
	}
}

function fileUploadInput(elm) {
	if ($(elm).val() != null && $(elm).val() != "") {
		$(elm).next().css('display', 'none');
		$(elm).prop('disabled', true);
		$(elm).parent().append(setFileName(elm));
	}
}

function setFileName(elm) {
	var html = "";
	html += '<span class="file-uploaded-body">';
	html += '	<span class=""><img class="svg btn-svg-icon-black" src="./images/svg/paperclip-solid.svg"></span>'
	html += '	<span class="">' + $(elm).val() + '</span>'
	html += '	<span class=""><img class="svg btn-svg-icon-black file-clear" src="./images/svg/xmark-solid.svg"></span>'
	html += '</span>';
	$(elm).parent().css('background-color', 'var(--color-gray-back)');
	return html;
}

function fileClear(elm) {
	event.preventDefault();
	const parent = $(elm).closest('.file-upload-item');
	const fileInput = parent.find('.file-upload-input');

	fileInput.val('');
	fileInput.prop('disabled', false);

	parent.find('.file-upload-body').css('display', '');
	parent.find('.file-uploaded-body').remove();
	parent.css('background-color', '');
}







