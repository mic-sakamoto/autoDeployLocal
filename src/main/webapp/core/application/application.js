$(function() {


	/**************************************** 共通処理 ****************************************/

	//ページ遷移
	$('.page-btn').on('click', function() { changePage(this) });

	//入力確認画面遷移
	$('.to-confirm-btn').on('click', function() { setInputToConfirm(this) });

	//共通 年金区分でその他を選択したらその他の入力欄を有効化＆必須化する
	$('.select-nenkin-kbn').on('change', function() {
		if ($(this).val() == '4') {
			toggleSonota(this, true);
		} else {
			toggleSonota(this, false);
		}
	});

	//郵便番号から住所自動反映
	$('.auto-address-btn').on('click', function() {
		const $postCode = $(this).siblings("input").val();
		const $address = $(this).closest('.input-item').next().find('.auto-address');
		const $addressKana = $(this).closest('.input-item').next().next().next().find('.auto-address-kana');

		getAddressFromPostNumber($postCode)
			.then(result => {
				$address.val(result.fullAddress);
				$addressKana.val(result.kanaAddress);
			})
			.catch(error => {

			});

	});

	//ベリファイ希望日制御
	const dateInput = $('.verify-date');
	const timeFromSelect = $('.verify-time-from');
	const timeToSelect = $('.verify-time-to');

	// 時刻のオプションを生成
	const generateTimeOptions = () => {
		const options = [];
		for (let hour = 9; hour <= 17; hour++) {
			for (let minute = 0; minute < 60; minute += 30) {
				const formattedTime = `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`;
				options.push(formattedTime);
			}
		}
		return options;
	};

	// 時刻オプションを追加
	const populateTimeSelect = (selectElement) => {
		const timeOptions = generateTimeOptions();
		selectElement.empty(); // 初期化
		timeOptions.forEach((time) => {
			selectElement.append(`<option value="${time}">${time}</option>`);
		});
	};

	// 日付の制約を設定
	const setMinDate = () => {
		const now = new Date();
		const today = new Date(now.setDate(now.getDate() + 1)); // 翌日
		const nextDay = new Date(now.setDate(now.getDate() + 1)); // 翌々日

		const selectedTime = timeFromSelect.val();
		const selectedTimeInMinutes = selectedTime
			? parseInt(selectedTime.split(':')[0]) * 60 + parseInt(selectedTime.split(':')[1])
			: 0;

		// 15時以降の場合は翌々日を最小日付に設定
		const minDate = selectedTimeInMinutes >= 900 ? nextDay : today;

		// フォーマット: yyyy-MM-dd
		const minDateString = minDate.toISOString().split('T')[0];
		dateInput.attr('min', minDateString);
	};

	//名前入力欄制御系
	$('.name-sei, .name-mei').on('input', function() {
		nameInputControl(this);
	});
	$('.furigana-sei, .furigana-mei').on('input', function() {
		furiganaInputControl(this);
	});

	//住所欄制御系
	$('.input-address').on('input', function() {
		addressInputControl(this);
	});

	// 初期化
	populateTimeSelect(timeFromSelect);
	populateTimeSelect(timeToSelect);
	setMinDate();

	// イベントリスナー
	timeFromSelect.on('change', setMinDate);

	/**************************************** お申込者 ****************************************/

	//売買契約日
	const today = new Date();
	const currentYear = today.getFullYear();

	const $yearSelect = $("#i-BaibaikeiyakuDateYear");
	const $monthSelect = $("#i-BaibaikeiyakuDateMonth");
	const placeholderOption = '<option value="" selected>選択してください</option>';

	$yearSelect.append(placeholderOption);
	for (let year = currentYear; year <= currentYear + 2; year++) {
		const wareki = convertToWareki(year); // 和暦表記に変換
		$yearSelect.append(`<option value="${year}">${wareki}</option>`);
	}
	$monthSelect.append(placeholderOption);
	for (let month = 1; month <= 12; month++) {
		$monthSelect.append(`<option value="${month}">${month}</option>`);
	}

	$yearSelect.on("change", function() {
		updateBaibaiKeiyakuDays()
	});
	$monthSelect.on("change", function() {
		updateBaibaiKeiyakuDays()
	});

	updateBaibaiKeiyakuDays();

	//申込者 職業区分を変更したときに特定の項目を入力必須にする
	$('#i-MoshikomiShokugyoKbn').on('change', function() { changeShokugyoKbn(this) });


	/**************************************** 連帯保証人 ****************************************/

	//保証人 保証人あり時に保証人入力項目欄を開く
	$('input[name="i-HoshoninKbn"]:radio').on('change', function() { toggleHoshininBlock() });

	//保証人 職業区分を変更したときに特定の項目を入力必須にする
	$('#i-HoshoninShokugyoKbn').on('change', function() { changeHoshoninShokugyoKbn(this) });

	//保証人 申込者との関係でその他を選択したときにその他入力欄を有効化＆必須化
	$('#i-HoshoninMoshikomiRelateKbn').on('change', function() {
		if ($(this).val() == '11') {
			toggleSonota(this, true);
		} else {
			toggleSonota(this, false);
		}
	});

	/**************************************** お車 ****************************************/

	//要修正!顧客入力時でもrequireついてしまう！
	if ($('#input-role-kbn').val() != '3') {
		changeMccsMoshikomiKbn();
	}

	//お車 MCCS申込ありの場合に特定項目を表示
	$('input[name="i-MccsMoshikomiKbn"]:radio').on('change', function() { changeMccsMoshikomiKbn() });

	//お車 販売の条件となっている商品・権利・役務の有無がありの場合に特定項目を表示
	$('input[name="i-HanbaiJokenKbn"]:radio').on('change', function() { changeHanbaiJokenKbn() });

	//お車 主な使用目的でその他を選択したときにその他入力欄を有効化＆必須化
	$('#i-ShiyoMokutekiKbn').on('change', function() {
		if ($(this).val() == '3') {
			toggleSonota(this, true);
		} else {
			toggleSonota(this, false);
		}
	});

	//お車 MCCS手動入力チェックの変更で特定項目を表示
	$('#i-ManualInputCheck').change(function() { checkMccsManualInput(this) });

	//お車 本申込かつ中古車選択時は車台番号入力必須
	$('input[name="i-UsedKbn"]:radio').on('change', function() { changeUsedKbn() });

	//お車 所有者でその他を選択したときにその他入力欄を有効化＆必須化→250117 項目表示のみになったので廃止
	//	$('input[name="i-Owner"]:radio').on('change', function() {
	//		if ($(this).val() == '0') {
	//			toggleSonota(this, true);
	//		} else {
	//			toggleSonota(this, false);
	//		}
	//	});

	//MCCS有無、新車・中古車の値をもとに金額・その他の条件コードを自動入力
	$('input[name="i-MccsMoshikomiKbn"]:radio').on('change', function() { inputJokenCode() });
	$('input[name="i-UsedKbn"]:radio').on('change', function() { inputJokenCode() });

	//車両情報プルダウン
	getMccsMaker();
	$('#i-Maker').on('change', function() {
		setMccsShameiSet();
	});
	$('#i-Shamei').on('change', function() {
		setMccsKatashikiSet();
	});

	/**************************************** 金額・その他 ****************************************/
	//金額 ボーナス加算月を設定すると加算支払金の項目を必須化
	$('input[name="i-BonusKasanMonthKbn"]:radio').on('change', function() {
		if ($(this).val() == 91) {
			$('#i-BonusKasanPrice').prop('disabled', false);
			addRequired($('#i-BonusKasanPrice'));
		} else {
			$('#i-BonusKasanPrice').prop('disabled', true);
			removeRequired($('#i-BonusKasanPrice'));
		}
	});

	//金額 納車日区分が日付指定の場合は年月日欄を入力必須化
	$('#i-NoshaDateKbn').on('change', function() {
		let elm = $('.nosha-date');
		if ($(this).val() == 2) {
			addRequired($(elm));
			$(elm).prop('disabled', false);
		} else {
			removeRequired($(elm));
			$(elm).prop('disabled', true);
		}
	});

	// 必要に応じて、フォーム更新時にカウントを再計算
	$(document).on('blur change focus focusout', '.input-form.active input.required, .input-form.active select.required', function(event) {
		countRemainRequired();
	});

	//inputMapに値がある場合は入力欄に入れる
	if ($('#inputMap').val() !== "/" && $('#inputMap').val() !== "") {
		setInputData($('#inputMap').val());
	}
	//入力内容を保存
	$('#save-input').on('click', function() {
		saveAppInput();
	});

	//入力項目の制御
	initialize();

	//ログイン手順再送信モーダル 開閉
	$('.resend-modal-open').click(function() {
		$('.resend-modal').addClass('active');
	});

	$('.resend-modal-close').click(function() {
		$('.resend-modal').removeClass('active');
	});

	//ログイン手順再送信モーダル 送信方法選択
	$('input[name="resendLoginMethod"]:radio').on('change', function() {
		if ($(this).val() === 'mail') {
			$('.resend-mail').removeClass('hide');
			$('.resend-tel').addClass('hide');
		} else {
			$('.resend-mail').addClass('hide');
			$('.resend-tel').removeClass('hide');
		}
	});

	//ログイン手順再送信モーダル 送信
	$('#resend').on('click', function() {
		resendLoginGuide();
	});

	//契約書類をDL
	$('#download-docs').on('click', function() {
		downloadDocs();
	});

	//審査申込内容を削除する
	$('#delete-application').on('click', function() {
		to_contracts();
	});
});

function getMccsMaker() {
	const postData = {};
	doAjax(postData, "getMccsMaker", "./application").then(function(data) {
		const $maker = $('#i-Maker');
		$maker.empty();
		// 取得したデータをリストに表示
		$maker.append('<option value="" selected>メーカーを選択してください</option>');
		data.mccsMakerList.forEach(function(item) {
			$maker.append('<option value=' + item + '>' + item + '</option>');
		});
		// 保存データがあれば反映
		if ($('#inputMap').val() !== "/" && $('#inputMap').val() !== "") {
			var jsonData = JSON.parse($('#inputMap').val());
			$.each(jsonData, function(key, value) {
				if (key == 'Maker') {
					$('#i-' + key).val(value);
					setMccsShameiSet();
					return;
				}
			});
		}
	});
}

function setMccsShameiSet() {
	const postData = {};
	postData['mccsMaker'] = $('#i-Maker').val();
	doAjax(postData, "getMccsShamei", "./application").then(function(data) {
		const $shamei = $('#i-Shamei');
		$shamei.empty();
		// 取得したデータをリストに表示
		$shamei.append('<option value="" selected>車名を選択してください</option>');
		data.mccsShameiList.forEach(function(item) {
			$shamei.append('<option value=' + item + '>' + item + '</option>');
		});
		// 保存データがあれば反映
		if ($('#inputMap').val() !== "/" && $('#inputMap').val() !== "") {
			var jsonData = JSON.parse($('#inputMap').val());
			$.each(jsonData, function(key, value) {
				if (key == 'Shamei') {
					$('#i-' + key).val(value);
					setMccsKatashikiSet();
					return;
				}
			});
		}
	});
}

function setMccsKatashikiSet() {
	const postData = {};
	postData['mccsShamei'] = $('#i-Shamei').val();
	doAjax(postData, "getMccsKatashiki", "./application").then(function(data) {
		const $katashiki = $('#i-Katashiki');
		$katashiki.empty();
		// 取得したデータをリストに表示
		$katashiki.append('<option value="" selected>型式を選択してください</option>');
		data.mccsKatashikiList.forEach(function(item) {
			$katashiki.append('<option value=' + item + '>' + item + '</option>');
		});
		// 保存データがあれば反映
		if ($('#inputMap').val() !== "/" && $('#inputMap').val() !== "") {
			var jsonData = JSON.parse($('#inputMap').val());
			$.each(jsonData, function(key, value) {
				if (key == 'Katashiki') {
					$('#i-' + key).val(value);
					return;
				}
			});
		}
	});
}

// 売買契約日のオプションを生成する関数
function updateBaibaiKeiyakuDays() {

	const today = new Date();
	const currentYear = today.getFullYear();
	const currentMonth = today.getMonth() + 1; // 月は0から始まるため+1
	const currentDate = today.getDate();

	const $yearSelect = $("#i-BaibaikeiyakuDateYear");
	const $monthSelect = $("#i-BaibaikeiyakuDateMonth");
	const $daySelect = $("#i-BaibaikeiyakuDateDay");
	const placeholderOption = '<option value="" selected>選択してください</option>';

	const selectedYear = parseInt($yearSelect.val(), 10);
	const selectedMonth = parseInt($monthSelect.val(), 10);

	// 日付をリセット
	$daySelect.empty();
	$daySelect.append(placeholderOption);

	// 選択された年月の最終日を取得
	if (isNaN(selectedYear) || isNaN(selectedMonth)) return;

	const daysInMonth = new Date(selectedYear, selectedMonth, 0).getDate();

	for (let day = 1; day <= daysInMonth; day++) {
		const isFuture =
			selectedYear > currentYear ||
			(selectedYear === currentYear && selectedMonth > currentMonth) ||
			(selectedYear === currentYear && selectedMonth === currentMonth && day >= currentDate);

		if (isFuture) {
			$daySelect.append(`<option value="${day}">${day}</option>`);
		}
	}
}

//名前入力欄の制御
function nameInputControl(elm) {

	$(elm).val($(elm).val().replace(/\s/g, ''));
	//合計30文字以内
	const group = $(elm).closest('.input-item');
	const seiInput = group.find('.name-sei');
	const meiInput = group.find('.name-mei');
	let sei = seiInput.val() || '';
	let mei = meiInput.val() || '';
	//ついでに空白削除
	sei = sei.replace(/\s/g, '');
	mei = mei.replace(/\s/g, '');
	seiInput.val(sei);
	meiInput.val(mei);
	const totalLength = (mei + ' ' + sei).length;
	// 合計30文字を超える場合
	if (totalLength > 30) {
		seiInput.addClass('ipt-err');
		meiInput.addClass('ipt-err');
	} else {
		seiInput.removeClass('ipt-err');
		meiInput.removeClass('ipt-err');
	}

}

//名前(フリガナ)入力欄の制御
function furiganaInputControl(elm) {

	$(elm).val($(elm).val().replace(/\s/g, ''));
	//合計40文字以内
	const group = $(elm).closest('.input-item');
	const seiInput = group.find('.furigana-sei');
	const meiInput = group.find('.furigana-mei');
	let sei = seiInput.val() || '';
	let mei = meiInput.val() || '';
	//ついでに空白削除
	sei = sei.replace(/\s/g, '');
	mei = mei.replace(/\s/g, '');
	seiInput.val(sei);
	meiInput.val(mei);
	const totalLength = (mei + ' ' + sei).length;
	// 合計40文字を超える場合
	if (totalLength > 40) {
		seiInput.addClass('ipt-err');
		meiInput.addClass('ipt-err');
	} else {
		seiInput.removeClass('ipt-err');
		meiInput.removeClass('ipt-err');
	}

}

//住所入力欄の制御
function addressInputControl(elm) {

	$(elm).val($(elm).val().replace(/\s/g, ''));

	const group = $(elm).closest('.input-item');
	const addressInput = group.find('.input-address');
	let address = addressInput.val() || '';
	//ついでに空白削除
	address = address.replace(/\s/g, '');
	addressInput.val(address);
	const totalLength = (address).length;
	// 合計27文字を超える場合
	if (totalLength > 27) {
		addressInput.addClass('ipt-err');
	} else {
		addressInput.removeClass('ipt-err');
	}

}


//顧客入力切替＆再送信
function resendLoginGuide() {
	const postData = {};
	postData['moshikomiKbn'] = $('#moshikomiKbn').val();
	postData['resendLoginMethod'] = $('[name="resendLoginMethod"]:checked').val();
	postData['licenseNumber'] = $('#licenseNumber').val();
	postData['resendMailAddress'] = $('#resendMailAddress').val();
	postData['resendMobileNum'] = $('#resendMobileNum1').val() + $('#resendMobileNum2').val() + $('#resendMobileNum3').val();
	doAjaxNoToast(postData, "resendLoginGuide", "./application").then(function(data) {
		let toastOptions = {
			text: $('#text_inputcomp').val(),
			hideAfter: 2000,
			position: 'mid-center'
		};
		if (data.successFlg === '1') {
			toastOptions.icon = 'success';
			if ($('#inputModeKbn').val() === '0') {
				//顧客入力切替の場合は再度項目の必須・入力可を初期化
				$('#inputModeKbn').val('1');
				initialize();
				toastOptions.text = $('#text_changeinputmode').val();
			} else {
				//再送信
				toastOptions.text = $('#text_resendloginguide').val();
			}
		} else {
			toastOptions.icon = 'error';
			toastOptions.text = $('#text_error').val();
		}
		$.toast(toastOptions);
	});
}

//契約書類ダウンロード
function downloadDocs() {
	var postData = {};
	postData['pdfName'] = 'MCCSAutoLone';
	doPostWithData(postData, "downloadJasper", "./pdf");
}

//入力内容を保存
function saveAppInput() {

	let postData = {};
	postData['webAppId'] = $('#webAppId').val().replace('/', '');
	postData['statusKbn'] = $('#statusKbn').val().replace('/', '');
	postData['inputModeKbn'] = $('#inputModeKbn').val().replace('/', '');
	postData['inputRoleKbn'] = $('#inputRoleKbn').val().replace('/', '');
	postData['moshikomiKbn'] = $('#moshikomiKbn').val().replace('/', '');

	// 入力欄の値を取得
	let inputData = {};
	$('#form-input [id^="i-"]').each(function() {

		const inputId = $(this).attr('id');
		const confirmId = inputId.replace('i-', '');

		let columnName = confirmId;

		if ($(this).is('input[type="text"], textarea')) {
			inputValue = $(this).val();
		} else if ($(this).is('input[type="radio"]')) {
			if ($(this).is(':checked')) {
				inputValue = $(this).val();
				const name = $(this).attr('name'); // 対応する確認欄のname
				const confirmName = name.replace('i-', ''); // 対応する確認欄のname
				columnName = confirmName;
			} else {
				return;
			}
		} else if ($(this).is('select')) {
			inputValue = $(this).val();
		}
		if (inputData[columnName]) {
			return;
		}
		if (inputValue === null || inputValue === undefined || inputValue === 'null') {
			inputValue = '';
		}
		inputData[columnName] = inputValue;
	});

	postData['inputMapJson'] = window.JSON.stringify(inputData);
	doAjaxNoToast(postData, "saveAppInput", "./application").then(function(data) {

		let toastOptions = {
			text: data.message,
			hideAfter: 3000,
			position: 'mid-center'
		};
		if (data.successFlg == '1') {
			toastOptions.icon = 'success';
			toastOptions.afterHidden = function() {
				if (data.statusKbn === '1' || data.statusKbn === '2' || data.statusKbn === '3') {
					// 契約一覧に遷移
					to_contracts();
				} else {
					// 申込状況照会に遷移
					postData = {};
					postData['webAppId'] = data.webAppId;
					postData['status'] = data.statusKbn;
					doAjax(postData, "getNowStatus", "./contract-mgmt").then(function(data2) {
						postData['viewType'] = data2.status;
						doPostWithData(postData, "", "./status");
					});
				}
			};
		} else {
			toastOptions.icon = 'error';
		}
		$.toast(toastOptions);
	});

	//	let toastOptions = {
	//		text: $('#text_inputcomp').val(),
	//		hideAfter: 3000,
	//		position: 'mid-center'
	//	};
	//
	//	let statusKbn = $('#statusKbn').val();
	//
	//	toastOptions.icon = 'success';
	//	toastOptions.afterHidden = function() {
	//		if (statusKbn === '1' || statusKbn === '2' || statusKbn === '3') {
	//			// 契約一覧に遷移
	//			to_contracts();
	//		} else {
	//			// 申込状況照会に遷移
	//			let postData = {};
	//			postData['webAppId'] = $('#webAppId').val();
	//			postData['status'] = statusKbn;
	//			doAjax(postData, "getNowStatus", "./contract-mgmt").then(function(data2) {
	//				postData['viewType'] = data2.status;
	//				doPostWithData(postData, "", "./status");
	//			});
	//		}
	//	};
	//	$.toast(toastOptions);

}

// 
function setInputData(jsonString) {
	try {
		if (jsonString.trim() === '/') {
			return;
		}

		// JSON文字列をJSONに変換
		var jsonData = JSON.parse(jsonString);
		$.each(jsonData, function(key, value) {
			if ($('#i-' + key).length > 0) {
				$('#i-' + key).val(value);
			} else {
				$('input[name="i-' + key + '"][value="' + value + '"]').prop('checked', true);
			}
		});

		calculateTotalPrice();
		calculateRemainingPrice();
		setShiharaiKasanKaisu()
		changeMccsMoshikomiKbn();
		changeHanbaiJokenKbn();
		toggleKasanShiharaiDisabled($('input[name="i-BonusKasanMonthKbn"]'));

		toggleHoshininBlock();
		checkMccsManualInput($('#i-ManualInputCheck'));

	} catch (e) {

	}
}

function countRemainRequired() {
	let remain = countEmptyRequiredFields();
	$('#remaining-items').html(remain);
	if (remain === 0) {
		$('.input-form.active').find('.next-btn').prop('disabled', false);
	} else {
		$('.input-form.active').find('.next-btn').prop('disabled', true);
	}
}


function countEmptyRequiredFields() {
	var emptyCount = 0;

	// req-icon を含む親要素を探す
	$('.input-form.active')
		.find('.req-icon')
		.each(function() {
			var $parent = $(this).closest('.input-item'); // .req-icon の親要素

			// 親要素内の required クラスを持つ要素を取得
			var $requiredInputs = $parent.find('.required');

			// 入力されていないものが1つでもあればカウント
			var hasEmpty = $requiredInputs.filter(function() {
				var $this = $(this);

				if ($(this).is(':disabled')) {
					return false;
				}

				if ($(this).is('[class*="ipt-err"]')) {//.ipt-err-〇〇も含むように
					return true;
				}

				// ラジオボタンの場合、選択されていない場合はカウント
				if ($this.attr('type') === 'radio') {
					var name = $this.attr('name');
					return !$('input[name="' + name + '"]:checked').length;
				}

				// その他の場合: 空、null、undefined のチェック
				var value = $this.val();
				return value === null || value === undefined || value.trim() === '';
			}).length > 0;

			if (hasEmpty) {
				emptyCount++;
			}
		});

	return emptyCount;
}

function inputJokenCode() {
	let mccsKbn = $('input[name="i-MccsMoshikomiKbn"]:radio:checked').val();
	let usedKbn = $('input[name="i-UsedKbn"]:radio:checked').val();

	let res = "";
	if (mccsKbn != 'undefined' && usedKbn != 'undefined') {
		if (mccsKbn == '1' && usedKbn == '0') {//MCCSあり・中古車
			res = "0001";
		} else if (mccsKbn == '1' && usedKbn == '1') {//MCCSあり・新車
			res = "0002";
		} else if (mccsKbn == '0' && usedKbn == '0') {//MCCSなし・中古車
			res = "0003";
		} else if (mccsKbn == '0' && usedKbn == '0') {//MCCSなし・新車
			res = "0004";
		}
	}
	$('#i-JokenCode').val(res);

}

//各項目の初期化
//個人or法人、審査申込or本申込、加盟店or顧客などで項目の操作
function initialize() {


	/*
	 申込区分
	 1:新規
	 2:不備返却再入力
	 3:審査結果×？再入力
	 4:本申込
	 */
	let appKbn = $('#appKbn').val();
	//入力時 加盟店入力or顧客入力
	let modeKbn = $('#inputModeKbn').val();
	//入力者のロール
	let roleKbn = $('#inputRoleKbn').val();
	//入力者の種別 個人or法人
	let moshikomiKbn = $('#moshikomiKbn').val();
	//下書き存在フラグ
	let isTempExist = $('#isTempExist').val();

	//個人or法人
	$('[name="i-MoshikomiKbn"][value="' + moshikomiKbn + '"]').prop('checked', true);

	if (appKbn == '4') {//本申込
		$('.scr-view').remove();
		$('.scr-ipt').prop('disabled', true);
		addRequired($('.main-req'));

		if (moshikomiKbn == '2') {//法人
			$('.kojin-view').remove();
		} else {//個人
			$('.hojin-view').remove();
		}

		if (modeKbn == '0') {//加盟店ページで入力
			//全入力可能
			activeRoleClass = '';
			//顧客入力に切り替えて送信ボタンを表示
			$('#change-input-mode').removeClass('hide');
			//顧客にログイン手順を再送信ボタン非表示
			$('#resend-login-guide').addClass('hide');

			//加盟店入力時は「登録する」ボタン非表示
			$('#app-confirm-customer').addClass('hide');

		} else {//ご契約者様専用ページで顧客が入力
			if (roleKbn == '2') {//加盟店
				$('.cstmr-view').remove();
				$('.cstmr-ipt').prop('disabled', true);
				//顧客入力に切り替えて送信ボタンを非表示
				$('#change-input-mode').addClass('hide');
				//顧客にログイン手順を再送信ボタン表示
				$('#resend-login-guide').removeClass('hide');
			} else {//顧客
				$('.store-view').remove();
				$('.store-ipt').prop('disabled', true);
			}
		}

	} else {//審査申込

		//新規審査申込なら新規申込年月日を入力
		if (appKbn == '1') {
			//申込年月日に当日セット
			let today = getTodayDate();
			$('#i-MoshikomiDateYear').val(today.year);
			$('#i-MoshikomiDateMonth').val(today.month);
			$('#i-MoshikomiDateDay').val(today.day);
		}

		//申込前かつ下書きが存在する場合は審査申込内容を削除ボタン表示
		if (isTempExist === '1') {
			$('#delete-application').removeClass('hide');
		} else {
			$('#delete-application').addClass('hide');
		}

		//本申込入力欄を排除
		$('.main-view').remove();
		$('.main-ipt').prop('disabled', true);

		let activeRoleClass;
		if (modeKbn == '0') {//加盟店ページで入力
			//全入力可能
			activeRoleClass = '';
			//顧客入力に切り替えて送信ボタンを表示
			$('#change-input-mode').removeClass('hide');
			//顧客にログイン手順を再送信ボタン非表示
			$('#resend-login-guide').addClass('hide');

			//加盟店入力時は「登録する」ボタン非表示
			$('#app-confirm-customer').addClass('hide');

		} else {//ご契約者様専用ページで顧客が入力
			if (roleKbn == '2') {//加盟店
				$('.cstmr-view').remove();
				$('.cstmr-ipt').prop('disabled', true);
				//顧客入力に切り替えて送信ボタンを非表示
				$('#change-input-mode').addClass('hide');
				//顧客にログイン手順を再送信ボタン表示
				$('#resend-login-guide').removeClass('hide');
				activeRoleClass = '.store-ipt';
			} else {//顧客
				$('.store-view').remove();
				$('.store-ipt').prop('disabled', true);
				activeRoleClass = '.cstmr-ipt';
			}
		}
		if (moshikomiKbn == '2') {//法人
			$('.kojin-view').remove();
			addRequired($(activeRoleClass + '.hojin-req'));
			//法人の場合は保証人強制あり
			$('input[name="i-HoshoninKbn"]:radio:eq(1)').prop('checked', true);
			$('input[name="i-HoshoninKbn"]:radio').addClass('read-only');
			$('#hoshonin-block').removeClass('hide');
			//確認 添付書類欄に固定文字
			$('#attachedDocOther').val($('#text_tohon').val());
			$('#attachedDocOther').addClass('read-only');
			$('#attachedDocOther').prop('disabled', true);
			//
		} else {//個人
			$('.hojin-view').remove();
			addRequired($(activeRoleClass + '.kojin-req'));
		}

		//審査結果× 再申込
		if (appKbn === '3') {
			//項目のrequiredクラス削除＆disabled
			removeRequired($('.re-app-res'));
			$('.re-app-res').prop('disabled', true);

			//連帯保証人ありにしていた場合は編集不可
			if ($('input[name="i-HoshoninKbn"]:radio:checked').val() === '1') {
				removeRequired($('#hoshonin-block'));
				$('#hoshonin-block input, #hoshonin-block select').prop('disabled', true);
				$('#hoshonin-sel-block input, #hoshonin-sel-block select').prop('disabled', true);
			}
		}

		//各項目の制御
		changeShokugyoKbn($('#i-MoshikomiShokugyoKbn'));
		changeHoshoninShokugyoKbn($('#i-HoshoninShokugyoKbn'));

	}

	changeUsedKbn();
	daikoLoginSet();

	//入力必須項目の残り計算
	countRemainRequired();
}

//入力必須クラス追加
function addRequired(elm) {
	$(elm).addClass('required');
	$(elm).each(function() {
		let reqElm = $(this).closest('.input-item').find('.item-title');
		if (!$(reqElm).find('.req-icon').length) {
			$(reqElm).html('<span class="req-icon"></span>' + $(reqElm).text());
		}
	});
}

//入力必須クラス削除
function removeRequired(elm) {
	$(elm).removeClass('required');
	$(elm).each(function() {
		let reqElm = $(this).closest('.input-item').find('.item-title');
		if ($(reqElm).find('.req-icon').length &&
			!$(this).closest('.input-item').find('input, select').hasClass('required')) {
			$(reqElm).find('.req-icon').remove();
		}
	});
}

//お申込者・ご職業選択による処理
function changeShokugyoKbn(elm) {

	//法人時は処理しない
	let is_hojin = $('#moshikomiKbn').val() == 2 ? true : false;
	if (is_hojin) {
		return false;
	}

	let val = $(elm).val();

	removeRequired($('.skg-kbn-relate-1'));
	removeRequired($('.skg-kbn-relate-2'));
	removeRequired($('.skg-kbn-relate-3'));
	removeRequired($('.skg-kbn-relate-4'));

	switch (val) {
		case '1':
		case '2':
		case '3':
		case '4':
		case '8':
		case '10':
			addRequired($('.skg-kbn-relate-1'));
			addRequired($('.skg-kbn-relate-4'));
			break;
		case '5':
			addRequired($('.skg-kbn-relate-1'));
			addRequired($('.skg-kbn-relate-2'));
			break;
		case '6':
			addRequired($('.skg-kbn-relate-2'));
			break;
		case '9':
		case '11':
			addRequired($('.skg-kbn-relate-3'));
			break;
		default:
			break;
	}
}

//お申込者・ご職業選択による処理
function changeHoshoninShokugyoKbn(elm) {

	let val = $(elm).val();

	removeRequired($('.h-skg-kbn-relate-1'));
	removeRequired($('.h-skg-kbn-relate-2'));

	switch (val) {
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '8':
		case '10':
			addRequired($('.h-skg-kbn-relate-1'));
			break;
		case '9':
		case '12':
			addRequired($('.h-skg-kbn-relate-2'));
			break;
		default:
			break;
	}
}

//共通 その他選択肢を選んだ場合はその他項目欄を入力必須に
function toggleSonota(elm, isActive) {
	let sonotaElm = $(elm).closest('.input-item').find('.sonota');
	if (isActive) {
		addRequired($(sonotaElm));
		$(sonotaElm).prop('disabled', false);
	} else {
		removeRequired($(sonotaElm));
		$(sonotaElm).prop('disabled', true);
		$(sonotaElm).val('');
		$(sonotaElm).removeClass('ipt-err-zenkaku');
	}
}

//保証人あり・なし選択時の処理
function toggleHoshininBlock() {
	var radioval = $('input[name="i-HoshoninKbn"]:checked').val();
	if (radioval == 1) {
		addRequired($('.hoshonin-req'));
		$('.with-hoshonin-view').removeClass('hide');
		$('.with-hoshonin').prop('disabled', false);
	} else {
		removeRequired($('.hoshonin-req'));
		$('.with-hoshonin-view').addClass('hide');
		$('.with-hoshonin').prop('disabled', true);
	}

	countRemainRequired();

}

//MCCS申込区分が「あり」の時に特定項目を表示する
function changeMccsMoshikomiKbn() {

	var radioval = $('input[name="i-MccsMoshikomiKbn"]:checked').val();
	//MCCSあり
	if (radioval == 1) {
		$('.with-mccs-view').removeClass('hide');
		$('.without-mccs-view').addClass('hide');

		//入力非必須化
		removeRequired($('.no-mccs-req').find('input,select'));
		$('.no-mccs-req').find('input,select').prop('disabled', true);
		//入力必須化
		addRequired($('.mccs-req').find('input,select'));
		$('.mccs-req').find('input,select').prop('disabled', false);
		//金額その他 MCCS取付価格表示
		$('#i-MccsAttachPrice').val(formatPrice('88000'));
	}
	//MCCSなし
	else {
		$('.with-mccs-view').addClass('hide');
		$('.without-mccs-view').removeClass('hide');

		//入力必須化
		addRequired($('.no-mccs-req').find('input,select'));
		$('.no-mccs-req').find('input,select').prop('disabled', false);
		//入力非必須化
		removeRequired($('.mccs-req').find('inpu,select'));
		$('.mccs-req').find('input,select').prop('disabled', true);
		//MCCS取付価格表示
		$('#i-MccsAttachPrice').val('0');

		$('#i-ManualInputCheck').prop('checked', false);

	}
}

//お車 販売の条件となっている商品・権利・役務の有無がありで必須化
function changeHanbaiJokenKbn() {
	var radioval = $('input[name="i-HanbaiJokenKbn"]:checked').val();
	if (radioval == 1) {
		addRequired($('.hanbaiJokenKbn-req'));
		$('.hanbaiJokenKbn-req').prop('disabled', false);
	} else {
		removeRequired($('.hanbaiJokenKbn-req'));
		$('.hanbaiJokenKbn-req').prop('disabled', true);
	}
}

//お車 MCCS手動入力チェックでMCCS車両情報を非表示
function checkMccsManualInput(elm) {
	if ($(elm).prop('checked')) {
		$('.mccs-car-info').addClass('hide');
		$('.mccs-manual-ipt').removeClass('hide');
		removeRequired($('.mccs-car-info').find('select'));
		addRequired($('.mccs-manual-ipt').find('input,select'));
		$('.mccs-manual-ipt').find('input,select').prop('disabled', false);
	} else {
		$('.mccs-car-info').removeClass('hide');
		$('.mccs-manual-ipt').addClass('hide');
		addRequired($('.mccs-car-info').find('select'));
		removeRequired($('.mccs-manual-ipt').find('input,select'));
		$('.mccs-manual-ipt').find('input,select').prop('disabled', true);
	}
}

//お車 本申込かつ中古車選択時は車台番号入力必須
function changeUsedKbn() {
	var appKbn = $('#appKbn').val();
	let radioval = $('input[name="i-UsedKbn"]:radio:checked').val();
	console.log(appKbn + "," + radioval);
	if (appKbn == '4' && radioval == '0') {
		addRequired($('#i-ShadaiNum'))
	} else {
		removeRequired($('#i-ShadaiNum'));
	}
}

//ページ遷移
function changePage(btnElm) {

	let pageId = $(btnElm).attr('name');

	$('.input-form , .contents-area-confirm').removeClass('active');
	$('#' + pageId).addClass('active');

	//プログレスバー処理
	$('.progress-icon').each(function(index, element) {
		if ($(this).hasClass(pageId)) {
			$(this).removeClass('inactive complete').addClass('active');
			$(this).prevAll('.progress-icon').removeClass('inactive active').addClass('complete');
			$(this).nextAll('.progress-icon').removeClass('active complete').addClass('inactive');
			return true;
		}
	});
	
	$('#req-tab').addClass('active');

	//ページの一番上に戻る
	$('html').css('scroll-behavior', 'auto');// CSSのスムーズスクロールを一時的に無効化
	$('html, body, .container').scrollTop(0);// 即時スクロール
	// スムーズスクロールのスタイルを復元
	setTimeout(function() {
		$('html').css('scroll-behavior', '');
	}, 10); // 短い遅延を設けて元に

	countRemainRequired();

}

function setInputToConfirm(btnElm) {
	// 入力欄の値を取得して確認欄に反映
	$('#form-input [id^="i-"]').each(function() {
		const inputId = $(this).attr('id'); // 入力欄のID
		let confirmTextId = inputId.replace('i-', '') + "Text"; // 対応する確認欄（表示用）のID
		let confirmId = inputId.replace('i-', ''); // 対応する確認欄のID

		// 入力値の取得と確認欄の設定
		let inputValue = '';
		let displayText = '';

		if ($(this).is('input[type="text"], textarea')) {
			// textやtextareaの場合
			inputValue = $(this).val();
			displayText = inputValue;
			// 確認欄に値を設定
		} else if ($(this).is('input[type="radio"]:checked')) {
			// ラジオボタンの場合
			let inputName = $(this).attr('name');
			confirmTextId = inputName.replace('i-', '') + "Text";
			confirmId = inputName.replace('i-', '');

			inputValue = $(this).val();
			displayText = $(this).closest('label').text().trim();
		} else if ($(this).is('select')) {
			// セレクトボックスの場合
			inputValue = $(this).val();
			displayText = $(this).find('option:selected').text();
			if (inputValue == null) {
				return true;
			}
		}
		$(`#${confirmTextId}`).text(displayText);
		$(`#${confirmId}`).val(inputValue);
	});

	//ヘッダーを非表示
	$('#req-tab').removeClass('active');
	//入力画面に遷移
	changePage(btnElm);
}

//下書き保存
function saveInput() {
	let map;
	// 入力欄の値を取得して確認欄に反映
	$('#form-input [id^="i-"]').each(function() {
		const inputId = $(this).attr('id'); // 入力欄のID
		const dbColumnKey = inputId.replace('i-', ''); // modelのkeyｍに変換

		// 入力値の取得と確認欄の設定
		let inputValue = '';

		if ($(this).is('input[type="text"], textarea')) {
			// textやtextareaの場合
			inputValue = $(this).val();
		} else if ($(this).is('input[type="radio"]:checked')) {
			// ラジオボタンの場合
			inputValue = $(this).val();
		} else if ($(this).is('select')) {
			// セレクトボックスの場合
			inputValue = $(this).val();
		} else {
			return true;
		}
	});

	var postData = {};
	doAjax(postData, "saveInput", "./application").then(function(data) {
		if (data.successFlg == '1') {

		} else {

		}
	});
}

function daikoLoginSet() {
	let daikoStaffId = $('#daikoStaffId').val();
	if (daikoStaffId === null || daikoStaffId === "") { // 空文字やnullを弾く
		$('.only-daiko-view').removeClass('hide');
	} else {
		$('.only-daiko-view').addClass('hide');
	}
}



//function countEmptyRequiredFields() {
//	// .input-form.active 内の .required クラスを持つ input, select 要素を取得
//	var emptyCount = $('.input-form.active')
//		.find('input.required, select.required') // 必要な要素を取得
//		.filter(function() {
//			var value = $(this).val();
//			// ラジオボタンの場合
//			if ($(this).attr('type') === 'radio') {
//				// 同じname属性を持つラジオボタンのグループ内で選択されていない場合
//				if (!$('input[name="' + $(this).attr('name') + '"]:checked').length) {
//					// 一度だけカウントするため、グループ内のラジオボタンを無視
//					return !$(this).data('counted');
//				}
//			}
//			// その他の場合: 空、null、undefined のチェック
//			else if (value === null || value === undefined || value.trim() === '') {
//				return true;
//			}
//			return false;
//		})
//		.each(function() {
//			// ラジオボタンの場合、選択されていない場合にカウントされたことを記録
//			if ($(this).attr('type') === 'radio') {
//				$('input[name="' + $(this).attr('name') + '"]').data('counted', true);
//			}
//		})
//		.length; // 該当する要素の数を取得
//
//	console.log("CHECK");
//
//	return emptyCount;
//}
