$(function() {
	// フォームをリセット
	$('.reset-btn').on('click', function() { resetForm() });

	//お車 MCCS申込ありの場合に特定項目を表示
	$('input[name="i-MccsMoshikomiKbn"]:radio').on('change', function() { changeMccsMoshikomiKbn() });

	//申込入力選択画面に遷移
	$('.app-btn').on('click', function() { toInputSelectMethod() });

});

//ローンシミュレーション実行後の処理
function execPostLs(successFlg) {
	if (successFlg === '1') {
		$('.app-btn').prop('disabled', false);
	} else {
		$('.app-btn').prop('disabled', true);
	}
}

//申込入力画面に遷移　ローンシミュレーションの結果も渡す
function toInputSelectMethod() {

	let postData = {};
	let formData = {};
	// .ls-form内のinput, select要素をループ
	$('#ls-form input.to-app, #ls-form select.to-app').each(function() {
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
				if (name.startsWith('i-')) {
					name = name.slice(2);
				}
				formData[name] = checkedValue;
			}
		} else {
			// 通常のinput, selectの処理
			if (key) {
				// idが"i-"で始まる場合は削除
				if (key.startsWith('i-')) {
					key = key.slice(2);
				}
				formData[key] = $element.val();
			}
		}
	});

	//	postData['inputMapJson'] = window.JSON.stringify(formData);
	//	doPostWithData(postData, "fromLoanSimulation", "./select-input-method");
	$('#inputMap').val(window.JSON.stringify(formData));
	selectInputMethodModal();
}

function selectInputMethodModal() {
	$('.select-input-method-modal').addClass('active');
}

//MCCS申込区分が「あり」の時に特定項目を表示する
function changeMccsMoshikomiKbn() {

	var radioval = $('input[name="i-MccsMoshikomiKbn"]:checked').val();
	//MCCSあり
	if (radioval == 1) {
		//金額その他 MCCS取付価格表示
		$('#i-MccsAttachPrice').val(formatPrice('88000'));
		//初年度登録を申込画面に渡さない
		$('#i-ShonendoYear').removeClass('to-app').removeClass('ls-req');
		$('#i-ShonendoMonth').removeClass('to-app').removeClass('ls-req');
	}
	//MCCSなし
	else {
		//MCCS取付価格表示
		$('#i-MccsAttachPrice').val('0');
		//初年度登録を申込画面に渡す
		$('#i-ShonendoYear').addClass('to-app').addClass('ls-req');
		$('#i-ShonendoMonth').addClass('to-app').addClass('ls-req');

	}
}

// フォームをリセット
function resetForm() {
	// Resetボタン押下時の処理
	// フォーム内のテキスト入力をリセット
	$('.input-container input[type="text"]').val('');

	// ラジオボタンのデフォルト選択状態に戻す
	$('.input-container input[type="radio"]').each(function() {
		$(this).prop('checked', $(this).attr('checked') === 'checked');
	});

	// セレクトボックスの初期値にリセット
	$('.input-container select').each(function() {
		$(this).prop('selectedIndex', 0);
	});

	// その他の必要な項目リセット（読み取り専用フィールドも初期化）
	$('.read-only').val('');

	// 必要であればデフォルト値の再設定（例: MCCS取付金額）
	$('#i-MccsAttachPrice').val(formatPrice('88000'));

	setYears('select.year');
	setMonths('select.month');
	setDays('select.day');

	checkLsInput();
}

