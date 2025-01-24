$(window).on('load', function() {
	$('.dateTime').attr('autocomplete', 'off');
});

$(function() {

	jQuery('.icon-hamburger').on('click', function() {
		if (jQuery('.menu-container').css('display') === 'block') {
			jQuery('.menu-container').slideUp('1500');
		} else {
			jQuery('.menu-container').slideDown('1500');
		}
	});

	var sp_flg = 1;
	$(window).resize(function() {
		const width = $(window).width();
		if (width >= 769) {
			if (sp_flg == 1) {
				$('.menu-container').show();
			}
			sp_flg = 0;
		} else {
			if (sp_flg == 0) {
				$('.menu-container').hide();
			}
			sp_flg = 1;
		}
	});

	//ヘッダーログインボタン押下
	$('#headerLoginBtn').on('click', function() { to_login() });

	//ヘッダーログアウトボタン押下
	$('#headerLogoutBtn').on('click', function() { logout() });

	//ヘッダーロゴボタン押下
	$('#headerLogoButton').on('click', function() { to_top() });

	//代行ログアウト押下
	$('.header-proxy-close').on('click', function() { proxyLogout() });

	//フッターリンク押下
	$('#footer-link-uss-ss').on('click', function(event) { to_ext_uss_ss(event) });
	$('#footer-link-uss').on('click', function(event) { to_ext_uss(event) });
	$('#footer-link-uss-pp').on('click', function(event) { to_ext_uss_pp(event) });

	$('#add-filter-select').on('change', function() { filterAdd(this) });
	$('#filter-clear').on('click', function() { filterClear() });

	$('.new-regist-block').click(function() { openRegistForm(this) });
	$('.filter-header-block').click(function() { openRegistForm(this) });
	//	$('.open-icon').click(function() { openRegistForm(this) });
	//	$('.filter-opentext').click(function() { openRegistForm(this) });
	$('#filter_back').click(function() { viewFilterBackEnd(); });
	$('#filter-search').click(function() { viewFilterBackEnd(); });

	$('.passwordIcon').click(function() { passwordIconChange(this) });

	$('#scrollUp').click(function() { scrollUp() });
	$('#listHeaderSortColumn').change(function() { listSort() });
	$('#listHeaderSortOrder').change(function() { listSort() });

	//年月日のselectに値をセット
	setYears('select.year');
	setMonths('select.month');
	setDays('select.day');

	//.todofukenのselectに都道府県をセット
	setTodofuken('select.todofuken');

	//デフォルトオプションを隠す
//	hidePlaceholderOption();

	//.price: 金額
	restrictToPrice('.price');
	//.num: 数字のみ
	restrictToNumbers('.num');
	//.non-num: 数字以外
	restrictToNonNumbers('.non-num');
	//.non-alpha: アルファベット以外
	restrictToNonAlphabets('.non-alpha');
	//.zenaku: 全角文字のみ   
	restrictToZenkaku('.zenkaku');
	//.hankaku: 半角文字のみ
	restrictToHankaku('.hankaku');
	//.read-only: 読み取り専用
	setReadOnly('.read-only');


	//ローンシミュレーション 入力確認
	$('.ls-input input, .ls-input select').on('input change blur', function() {
		checkLsInput();
	});

	//ローンシミュレーション画面内計算
	$('.cal-total-item, .cal-atamakin-item').on('input', function() {
		calculateTotalPrice();
		calculateRemainingPrice();
	});

	//ローンシミュレーション 支払い回数変更で加算支払い回数をセット
	$('.kasanShiharaiCheck').on('change', function() { setShiharaiKasanKaisu() });

	//ローンシミュレーション ボーナス加算月入力で加算支払金のdisabled解除
	$('input[name="i-BonusKasanMonthKbn"]').on('change', function() {
		toggleKasanShiharaiDisabled(this);
	});

	//ローンシミュレーションAPI実行
	$('.exec-ls').on('click', function() {
		execHensaiYoteiApi();
	});

	//ローンシミュレーション 年式によって支払い回数の制御
	$('#i-ShonendoYear').on('change', function() {
		checkShiharaiKaisu(this);
	});

	//付属品～車検・整備費用の合計計算
	$('.cal-sub-item').on('input', function() {
		calculateSubPrice(this);
	});

	deSVG('.svg', true);

});

//郵便番号から住所を自動入力
function getAddressFromPostNumber(postCode) {
	return new Promise((resolve, reject) => {
		$.ajax({
			url: `https://zip-cloud.appspot.com/api/search?zipcode=${postCode}`,
			method: "GET",
			dataType: "json",
			success: function(data) {
				if (data.results) {
					const address = data.results[0];
					const fullAddress = `${address.address1}${address.address2}${address.address3}`;
					const kanaAddress = `${address.kana1}${address.kana2}${address.kana3}`;
					resolve({ fullAddress, kanaAddress });
				} else {
					reject('No address found');
				}
			},
			error: function() {
				reject('API call failed');
			}
		});
	});
}

//ローンシミュレーション 年式によって支払い回数の制御
function checkShiharaiKaisu(elm) {
	let shiharaiKaisu = $('#i-ShiharaiKaisu');
	let nenshiki = parseInt($(elm).val(), 10);
	if (isNaN(nenshiki)) {
		return;
	}

	const currentYear = new Date().getFullYear();
	const difference = currentYear - nenshiki; // 差を計算

	// すべてのoptionを最初に表示
	shiharaiKaisu.find('option').show();

	if (difference >= 15) { // 15年以上前
		shiharaiKaisu.find('option[value="60"], option[value="72"], option[value="84"]').hide();
	} else if (difference >= 14) { // 14年以上前
		shiharaiKaisu.find('option[value="72"], option[value="84"]').hide();
	} else if (difference >= 13) { // 13年以上前
		shiharaiKaisu.find('option[value="84"]').hide();
	}

	if (shiharaiKaisu.find('option:selected').is(':hidden')) {
		shiharaiKaisu.val(''); // 選択をリセット
	}
}

//ローンシミュレーション ボーナス加算月入力で加算支払金のdisabled解除
function toggleKasanShiharaiDisabled(elm) {
	let val = $(elm).val();

	if (val === '91') {
		$('#i-BonusKasanKaisu').prop('disabled', false);
		$('#i-BonusKasanKaisu').addClass('read-only');
		$('#i-BonusKasanPrice').prop('disabled', false);
		$('#i-BonusKasanPrice').addClass('ls-req');
	} else {
		$('#i-BonusKasanKaisu').prop('disabled', true);
		$('#i-BonusKasanKaisu').removeClass('read-only');
		$('#i-BonusKasanPrice').prop('disabled', true);
		$('#i-BonusKasanPrice').removeClass('ls-req');
		$('#i-BonusKasanPrice').val("");
	}

	checkLsInput();
}

//ローンシミュレーション 支払い回数変更で加算支払い回数をセット
function setShiharaiKasanKaisu() {
	let radioElm = $('input[name="i-BonusKasanMonthKbn"]:checked');

	if ($(radioElm).val() === '91') {
		if ($('#i-ShiharaiKaisu').val() !== null && $('#i-ShiharaiKaisu').val().length) {
			let shiharaiKaisu = $('#i-ShiharaiKaisu').val();
			const res = Math.floor(Number(shiharaiKaisu) / 6);
			$('#i-BonusKasanKaisu').val(res);
		}
	} else {
		$('#i-BonusKasanKaisu').val("");
	}
}

//ローンシミュレーション 入力項目チェック
function checkLsInput() {
	let isDisabled = false;

	$('.ls-input input, .ls-input select').each(function() {

		if ($(this).is(':disabled')) {
			return;
		}

		if ($(this).hasClass('ipt-err')) {//入力エラー
			isDisabled = true;
		}

		if ($(this).hasClass('ls-req')) {//必須項目
			if ($(this).is(':radio')) {
				const radioName = $(this).attr('name');
				if ($(`input[name="${radioName}"]:checked`).length === 0) {
					isDisabled = true;
				}
			}
			else if (
				$(this).val() === null ||
				$(this).val() === undefined ||
				$(this).val() === ""
			) {
				isDisabled = true;
			}
		}

		if (isDisabled) {
			return false;
		}
	});

	// ボタンの有効/無効を設定
	$('.exec-ls').prop('disabled', isDisabled);
}

//返済予定API実行
function execHensaiYoteiApi() {

	$('.ls-err-msg .err-msg-body').text("");
	$('.ls-err-msg').addClass("hide");

	$('form.ls').find('select input').removeClass('err-ipt');
	var postData = {};
	postData['genkinkakakugoukei'] = $('#i-TotalPrice').val().replaceAll(',', '');
	postData['atamakin'] = $('#i-AtamaPrice').val().replaceAll(',', '');
	postData['siharaikbn'] = $('input[name="i-BonusKasanMonthKbn"]:checked').val();
	postData['bonuszougaku'] = $('#i-BonusKasanPrice').val().replaceAll(',', '');
	postData['siharaiyoteikaisuu'] = $('#i-ShiharaiKaisu').val();

	postData['bonuskasankaisu'] = $('#i-BonusKasanKaisu').val();
	postData['mccsKbn'] = $('input[name="i-MccsMoshikomiKbn"]:checked').val();

	doAjax(postData, "hensaiYotei", "./loan-simulation").then(function(data) {
		if (data.successFlg == 1) {
			$('#i-BunkatsuShiharai1').val(formatPrice(data.bunkatusiharaikinn1));
			$('#i-BunkatsuShiharai2').val(formatPrice(data.bunkatusiharaikinn2));
			$('#i-BunkatsuTesuryo').val(formatPrice(data.bunkatubaraitesuuryou));
			$('#i-BunkatsuKaisu').val(data.kaisu);
			$('#i-BunkatsuShiharaiTotal').val(formatPrice(data.bunkatusiharaigokei));
			$('#i-TotalShiharai').val(formatPrice(data.osiharaisogaku));

		} else {
			//エラー表示
			if (data.errormessage != "") {
				$('.ls-err-msg .err-msg-body').text(data.errormessage);
				$('.ls-err-msg').removeClass('hide');
			}
		}

		execPostLs(data.successFlg);
	});
}

//ローンシミュレーション 現金合計額の計算
function calculateTotalPrice() {
	let total = 0;
	$('.cal-total-item').each(function() {
		const value = parseFloat($(this).val().replaceAll(',', '')) || 0; // 数値を取得（空の場合は0）
		total += value;
	});
	$('#i-TotalPrice').val(formatPrice(total));
}

//ローンシミュレーション 残金の計算
function calculateRemainingPrice() {
	let totalAtamakin = 0;
	$('.cal-atamakin-item').each(function() {
		const value = parseFloat($(this).val().replaceAll(',', '')) || 0;
		totalAtamakin += value;
	});
	$('#i-AtamaPrice').val(totalAtamakin);

	const totalPrice = parseFloat($('#i-TotalPrice').val().replaceAll(',', '')) || 0;
	const remainingPrice = totalPrice - totalAtamakin < 0 ? 0 : totalPrice - totalAtamakin;
	$('#i-RemainPrice').val(formatPrice(remainingPrice));

}

//付属品～車検・整備費用の合計計算
function calculateSubPrice(elm) {
	$(elm).val($(elm).val().replace(/\s/g, ''));
	let total = 0;
	var errorFlg = true;
	$('.cal-sub-item').each(function() {
		if ($(this).val() == '') {
			errorFlg = false;
		}
		const value = parseFloat($(this).val().replaceAll(',', '')) || 0; // 数値を取得（空の場合は0）
		total += value;
	});
	if (errorFlg && total == 0) {
		$('.cal-sub-item').addClass('ipt-err');
	} else {
		$('.cal-sub-item').removeClass('ipt-err');
	}
}

// 金額入力 (.price)
function restrictToPrice(selector) {
	$(selector).on('blur', function() {
		const inputValue = $(this).val().replaceAll(',', '');
		if (/^[0-9]*$/.test(inputValue)) {
			$(this).removeClass('ipt-err-price'); // 入力ルールに沿っている場合
			// カンマ区切りに変換（空の場合は変換しない）
			if (inputValue !== '') {
				$(this).val(formatPrice(inputValue)); // カンマ区切り形式で表示
			}
		} else {
			$(this).addClass('ipt-err-price'); // 不正ならクラス追加し空にする
		}
	});
}

// 3桁カンマ区切りを返す
function formatPrice(price) {
	const formattedPrice = Number(price).toLocaleString('ja-JP');
	return formattedPrice;
}

// 数字のみ入力可能 (.num)
function restrictToNumbers(selector) {
	$(selector).on('blur', function() {
		const inputValue = $(this).val();
		if (/^[0-9]*$/.test(inputValue)) {
			$(this).removeClass('ipt-err-num'); // 入力ルールに沿っている場合
		} else {
			$(this).addClass('ipt-err-num'); // 不正ならクラス追加し空にする
		}
	});
}

// 数字（半角: 0-9, 全角: ０-９）以外入力可能 (.non-num)
function restrictToNonNumbers(selector) {
	$(selector).on('blur', function() {
		const inputValue = $(this).val();
		if (/^[^\d０-９]*$/.test(inputValue)) {
			$(this).removeClass('ipt-err-non-num'); // 入力ルールに沿っている場合
		} else {
			$(this).addClass('ipt-err-non-num'); // 不正ならクラス追加
		}
	});
}

// アルファベット以外入力可能 (.non-alpha)
function restrictToNonAlphabets(selector) {
	$(selector).on('blur', function() {
		const inputValue = $(this).val();
		if (/^[^a-zA-Z]*$/.test(inputValue)) { // アルファベットが含まれていない場合
			$(this).removeClass('ipt-err-non-alpha'); // 入力ルールに沿っている場合
		} else {
			$(this).addClass('ipt-err-non-alpha'); // 不正ならクラス追加
		}
	});
}


// 全角文字のみ入力可能 (.zenaku)
function restrictToZenkaku(selector) {
	$(selector).on('blur', function() {
		const inputValue = $(this).val();
		// 全角文字のみを許可（半角カタカナを除外）
		if (/^[\u3000-\u9FFF\uFF01-\uFF5E\uFF10-\uFF19\uFF21-\uFF3A\uFF41-\uFF5A]*$/.test(inputValue)) {
			$(this).removeClass('ipt-err-zenkaku'); // 入力ルールに沿っている場合
		} else {
			$(this).addClass('ipt-err-zenkaku'); // 不正ならクラス追加し空にする
		}
	});
}


// 半角文字（英数字 + 半角カタカナ）のみ入力可能 (.hankaku)
function restrictToHankaku(selector) {
	$(selector).on('blur', function() {
		const inputValue = $(this).val();
		// 半角英数字および半角カタカナを許可する正規表現
		// 半角英数字（\x20-\x7E）および半角カタカナ（\uFF61-\uFF9F）
		const validValue = /^[\x20-\x7E\uFF61-\uFF9F]*$/;

		if (validValue.test(inputValue)) {
			$(this).removeClass('ipt-err-hankaku'); // 入力ルールに沿っている場合
		} else {
			$(this).addClass('ipt-err-hankaku'); // 不正ならクラス追加し空にする
		}
	});
}

// フォーカス不可、入力不可、カーソル変更なし (.read-only)
function setReadOnly(selector) {
	$(selector).each(function() {
		const $element = $(this);

		// フォーカス不可
		$element.attr('tabindex', '-1');

		// イベントを無効化（フォーカスや操作を防止）
		$element.on('focus mousedown keydown click', function(e) {
			e.preventDefault();
			e.stopImmediatePropagation();
		});

		// select要素の場合
		if ($element.is('select')) {
			// disabled属性を付与して操作を無効化
			$element.prop('disabled', true);
		}

		// CSSでカーソルを元に戻す（デフォルトに設定）
		$element.css('cursor', 'default');
	});
}

// 西暦を和暦に変換する関数
function convertToWareki(year) {
	if (year >= 2019) {
		return `R${year - 2018}（${year}）`; // 令和
	} else if (year >= 1989) {
		return `H${year - 1988}（${year}）`; // 平成
	} else if (year >= 1926) {
		return `S${year - 1925}（${year}）`; // 昭和
	} else if (year >= 1912) {
		return `T${year - 1911}（${year}）`; // 大正
	} else if (year >= 1868) {
		return `M${year - 1867}（${year}）`; // 明治
	} else {
		return `${year}年`; // 明治以前
	}
}

// 年をセットする関数
function setYears(selector) {
	const currentYear = new Date().getFullYear(); // 今年
	const startYear = currentYear - 100; // 100年前

	// セレクターに対応する要素を取得し、オプションをクリア
	$(selector).empty();

	// 未選択状態を追加
	$(selector).append('<option value="" selected>年</option>');

	// 年を昇順で追加
	for (let year = currentYear; year >= startYear; year--) {
		const wareki = convertToWareki(year); // 和暦表記に変換
		$(selector).append(`<option value="${year}">${wareki}</option>`);
	}
}

// 月をセットする関数
function setMonths(selector) {
	// セレクターに対応する要素を取得し、オプションをクリア
	$(selector).empty();

	// 未選択状態を追加
	$(selector).append('<option value="" selected>月</option>');

	// 月を1～12で追加
	for (let month = 1; month <= 12; month++) {
		$(selector).append(`<option value="${month}">${month}月</option>`);
	}
}

// 日をセットする関数
function setDays(selector) {
	// セレクターに対応する要素を取得し、オプションをクリア
	$(selector).empty();

	// 未選択状態を追加
	$(selector).append('<option value="" selected>日</option>');

	// 日を1～31で追加
	for (let day = 1; day <= 31; day++) {
		$(selector).append(`<option value="${day}">${day}日</option>`);
	}
}

// 都道府県をセットする関数
function setTodofuken(selector) {
	// セレクターに対応する要素を取得し、オプションをクリア
	$(selector).empty();

	// 未選択状態を追加
	$(selector).append(
		'<option value="" selected>選択してください</option>' +
		'<option value="北海道">北海道</option>' +
		'<option value="青森県">青森県</option>' +
		'<option value="岩手県">岩手県</option>' +
		'<option value="宮城県">宮城県</option>' +
		'<option value="秋田県">秋田県</option>' +
		'<option value="山形県">山形県</option>' +
		'<option value="福島県">福島県</option>' +
		'<option value="茨城県">茨城県</option>' +
		'<option value="栃木県">栃木県</option>' +
		'<option value="群馬県">群馬県</option>' +
		'<option value="埼玉県">埼玉県</option>' +
		'<option value="千葉県">千葉県</option>' +
		'<option value="東京都">東京都</option>' +
		'<option value="神奈川県">神奈川県</option>' +
		'<option value="新潟県">新潟県</option>' +
		'<option value="富山県">富山県</option>' +
		'<option value="石川県">石川県</option>' +
		'<option value="福井県">福井県</option>' +
		'<option value="山梨県">山梨県</option>' +
		'<option value="長野県">長野県</option>' +
		'<option value="岐阜県">岐阜県</option>' +
		'<option value="静岡県">静岡県</option>' +
		'<option value="愛知県">愛知県</option>' +
		'<option value="三重県">三重県</option>' +
		'<option value="滋賀県">滋賀県</option>' +
		'<option value="京都府">京都府</option>' +
		'<option value="大阪府">大阪府</option>' +
		'<option value="兵庫県">兵庫県</option>' +
		'<option value="奈良県">奈良県</option>' +
		'<option value="和歌山県">和歌山県</option>' +
		'<option value="鳥取県">鳥取県</option>' +
		'<option value="島根県">島根県</option>' +
		'<option value="岡山県">岡山県</option>' +
		'<option value="広島県">広島県</option>' +
		'<option value="山口県">山口県</option>' +
		'<option value="徳島県">徳島県</option>' +
		'<option value="香川県">香川県</option>' +
		'<option value="愛媛県">愛媛県</option>' +
		'<option value="高知県">高知県</option>' +
		'<option value="福岡県">福岡県</option>' +
		'<option value="佐賀県">佐賀県</option>' +
		'<option value="長崎県">長崎県</option>' +
		'<option value="熊本県">熊本県</option>' +
		'<option value="大分県">大分県</option>' +
		'<option value="宮崎県">宮崎県</option>' +
		'<option value="鹿児島県">鹿児島県</option>' +
		'<option value="沖縄県">沖縄県</option>'
	);
}

// placeholderオプションを隠す処理
function hidePlaceholderOption() {
	$('select').on('focus', function() {
		// フォーカス時に「未選択」を非表示にする
		$(this).find('option[value=""]').hide();
	});
}

//　当日日付を返す
function getTodayDate() {
	var today = new Date();

	var year = today.getFullYear();
	var month = today.getMonth() + 1;
	var day = today.getDate();

	return {
		year: year,
		month: month,
		day: day
	};
}

var loader = 0;
function doAjax(postData, action, url) {
	return new Promise(function(resolve, reject) {

		postData['action'] = action;

		$.ajax({
			type: "POST",
			url: url,
			data: postData,
			success: function(data, dataType) {

				if (typeof data == "string") {

					// session切れの為ログイン画面へ
					to_login();

				} else if (data.successFlg == '9') {

					console.log(data);

					if (data.message != null && data.message != '') {
						$.toast({
							text: data.message,
							hideAfter: 3000,
							position: 'mid-center'
						});
					}

					resolve(data);
					return data.successFlg;

				} else {

					if (data.message != null && data.message != '') {
						$.toast({
							text: data.message,
							icon: "success",
							allowToastClose: false,
							hideAfter: 3000,
							loader: false,
							textAlign: 'center',
							position: {
								left: '40%',
								top: '35%'
							}
						});
					}
					viewAjaxLoadEnd();

					resolve(data);
					return data.successFlg;
				}
			},

			error: function(XMLHttpRequest, textStatus, errorThrown) {
			},
			beforeSend: function() {
				viewAjaxLoadStart();

			},
			complete: function() {
				viewAjaxLoadEnd();
			}
		});
	});
}
function doAjaxNoLoader(postData, action, url) {
	return new Promise(function(resolve, reject) {

		postData['action'] = action;

		$.ajax({
			type: "POST",
			url: url,
			data: postData,
			success: function(data, dataType) {

				if (typeof data == "string") {

					// session切れの為ログイン画面へ
					to_login();

				} else if (data.successFlg == '9') {

					if (data.errMessage != null && data.errMessage != '') {
						$.toast({
							text: data.errMessage,
							hideAfter: 3000,
							position: 'mid-center'
						});
					}

					resolve(data);
					return data.successFlg;

				} else {

					if (data.message != null && data.message != '') {
						$.toast({
							text: data.message,
							icon: "success",
							hideAfter: 3000,
							position: 'mid-center'
						});
					}
					//					viewAjaxLoadEnd();

					resolve(data);
					return data.successFlg;
				}
			},

			error: function(XMLHttpRequest, textStatus, errorThrown) {
			},
			beforeSend: function() {
				//				viewAjaxLoadStart();

			},
			complete: function() {
				//				viewAjaxLoadEnd();
			}
		});
	});
}
function doAjaxNoToast(postData, action, url) {
	return new Promise(function(resolve, reject) {

		postData['action'] = action;

		$.ajax({
			type: "POST",
			url: url,
			data: postData,
			success: function(data, dataType) {

				if (typeof data == "string") {
					// session切れの為ログイン画面へ
					to_login();

				} else if (data.successFlg == '9') {
					resolve(data);
					return data.successFlg;

				} else {
					viewAjaxLoadEnd();

					resolve(data);
					return data.successFlg;
				}
			},

			error: function(XMLHttpRequest, textStatus, errorThrown) {
			},
			beforeSend: function() {
				viewAjaxLoadStart();

			},
			complete: function() {
				viewAjaxLoadEnd();
			}
		});
	});
}


function doPost(formElm, action, url) {

	// 新しいフォーム要素を作成
	var form = $('<form></form>');
	// フォームの送信先URLを設定
	form.attr('action', url);
	// フォームの送信方法をPOSTに設定
	form.attr('method', 'POST');
	// アクション名を追加するための隠しフィールドを作成
	var actionField = $('<input type="hidden" name="action" />');
	actionField.val(action);
	form.append(actionField);
	// 元のフォームのデータを追加
	$(formElm).find('input, select, textarea').each(function() {
		var input = $('<input type="hidden" />');
		input.attr('name', $(this).attr('name'));
		//ラジオボタン
		if ($(this).attr("type") == "radio") {
			if ($(this).is(":checked")) {
				input.val($(this).val());
			} else {
				return true;
			}
		}
		//それ以外
		else {
			input.val($(this).val());
		}
		form.append(input);
	});
	// フォームをボディに追加して送信
	$('body').append(form);
	form.submit();
}

function doPostWithData(postData, action, url) {

	// フォームを作成
	const form = $('<form>', {
		method: 'POST',
		action: url
	});

	// action
	form.append(
		$('<input>', {
			type: 'hidden',
			name: 'action',
			value: action
		})
	);

	// postDataのデータをhidden inputとしてフォームに追加
	for (const key in postData) {
		if (postData.hasOwnProperty(key)) {
			form.append(
				$('<input>', {
					type: 'hidden',
					name: key,
					value: postData[key]
				})
			);
		}
	}

	console.log(form);

	// フォームをbodyに追加して送信
	$('body').append(form);
	form.submit();

}

/*
 * ローディング表示
 */
function viewAjaxLoadStart() {
	if (loader == 0) {
		$("#modal_ajax_loading").modal();
		$('body').addClass('modal-bgcolor-white');
		loader = 1;
	}

}
function viewAjaxLoadEnd() {
	$("#modal_ajax_loading").modal("hide");
	$("#modal_ajax_loading").css('display', 'none');
	$.wait(500).done(function() {
		$(".modal-backdrop").remove();
		$("#modal_ajax_loading").css('display', 'none');
		loader = 0;
	});
}

/**
 * ページ内のINPUTを収集する.
 *
 * @param selector
 *            (null可) 子要素のみを収集対象。
 */
function gatherInputs(selector, isId) {

	var object = {};
	function set(elem) {
		var obj = $(elem);
		var name = obj.attr("id");

		if (name) {
			var value = obj.val();
			object[name] = value;
		}
	}

	function set2(elem) {
		var obj = $(elem);
		var name = obj.attr("name");
		var id = obj.attr("id");

		if (id) {
			var value = obj.val();
			object[name] = value;
		}
	}

	if (!isId) {
		set = set2;
	}

	$("input:not(:radio):not(:button):not(:checkbox)", selector).each(
		function(i, elem) {
			set(elem);
		});
	$("input:radio:checked", selector).each(function(i, elem) {
		set2(elem);
	});
	$("input:checkbox", selector).each(function(i, elem) {
		var name;
		if (!isId) {
			name = $(elem).attr("name");
		} else {
			name = $(elem).attr("id");
		}
		if (name) {

			if ($(elem).is(":checked")) {
				object[name] = $(elem).val();
			} else {
				object[name] = "";
			}
		}
	});
	$("select option:selected", selector).each(function(i, elem) {
		set($(elem).parent()[0]);
	});
	$("textarea", selector).each(function(i, elem) {
		set(elem);
	});
	return object;
}


//↓他プロジェクトから転用----------------------------------------------------------------------------------------------------------------------------------//

/*
 * テーブルページング機能
 */
var page = 1;
function makePager(listJson) {
	var lastPage = Math.ceil(listJson.length / parseInt($('#viewNum').val()));
	const startRow = (page - 1) * parseInt($('#viewNum').val()) + 1; // 現在ページの最初の行
	const endRow = Math.min(page * parseInt($('#viewNum').val()), listJson.length); // 現在ページの最後の行

	var pageObj = $('.pagination');
	pageObj.empty();
	//	pageObj.append('<span class="pages-range">' + startRow + ' ~ ' + endRow + ' / ' + listJson.length + '</span>');


	//	if (page > 1) {
	//		pageObj.append('<li id="id_pager_first"><a href="#" onclick="paging(' + (page - 1) + ');return false" style="padding-right: 15px;">前ページ</a></li>');
	//	}
	if (page > 1) {
		pageObj.append('<li id="id_pager_first"><a href="#" onclick="paging(' + (page - 1) + ');return false" style="padding-right: 15px; text-decoration: none;"><</a></li>');
	} else {
		//		pageObj.append('<li id="id_pager_first"><span style="padding-right: 15px; text-decoration: none;"><</span></li>');
	}



	var addPage = 0;
	if (page > 3) {
		addPage = page - 3;
	}
	if ((4 + addPage) > lastPage) {
		addPage = addPage - (4 + addPage - lastPage);
		if (addPage < 0) {
			addPage = 0;
		}
	}

	if (addPage > 0) {
		pageObj.append('<li><a href="#" onclick="paging(' + addPage + ');return false" style="padding-right: 15px; text-decoration: none;">1</a>…</li>');
	}

	var viewLast = 0;
	for (var i = 1 + addPage; i <= 4 + addPage; i++) {

		viewLast = i;

		var classActive = "";
		if (page == i) {
			classActive = "active";
			pagingActive = "pagingbtn-active";
		} else {
			classActive = "";
			pagingActive = "";
		}
		pageObj.append('<li id="id_pager_' + i + '" class="' + classActive + '"><a href="#" onclick="paging(' + i + ');return false" class="page-num ' + pagingActive + '">' + i + '</a></li>');
		if (i == lastPage) {
			break;
		}
	}

	//	if (lastPage > page) {
	//		pageObj.append('<li id="id_pager_last"><a href="#" onclick="paging(' + (page + 1) + ');return false" style="padding-left: 15px;">次ページ</a></li>');
	//	}
	if (lastPage > page) {

		if (viewLast < lastPage) {
			pageObj.append('<li>…<a href="#" onclick="paging(' + lastPage + ');return false" style="padding-left: 15px; text-decoration: none;">' + lastPage + '</a></li>');
		}

		pageObj.append('<li id="id_pager_last"><a href="#" onclick="paging(' + (page + 1) + ');return false" style="padding-left: 15px; text-decoration: none;">></a></li>');



	} else {
		//		pageObj.append('<li id="id_pager_first"><span style="padding-left: 15px; text-decoration: none;">></span></li>');
	}

};

/*
 * テーブルソート機能
 */
var sortId = "";
var sortOrder = false;

function listSort() {

	//1:降順　2:昇順
	if ($('#listHeaderSortOrder').val() == 2) {
		sortOrder = false;
	} else {
		sortOrder = true;
	}

	var sortCol = $('#listHeaderSortColumn').val().split('_');


	sortJson(listJson, sortCol[0], sortCol[1]);

	makeList();
}

function sortJson(listJson, id, type) {
	//	function sortJson(listJson, obj, type) {

	//	var id = obj.id;
	//	var jq_obj = $(obj);

	//	var id = obj.id;
	//	var jq_obj = $(obj);
	//	var icon_up = "<span class='sorticon glyphicon glyphicon-chevron-up' style='margin-left:5px'>";
	//	var icon_down = "<span class='sorticon glyphicon glyphicon-chevron-down' style='margin-left:5px'>";

	//	$('.sorticon').remove();
	//	if (sortId == id && !sortOrder) {
	//		jq_obj.append(icon_down);
	//		sortOrder = true;
	//	} else {
	//		jq_obj.append(icon_up);
	//		sortOrder = false;
	//	}
	sortId = id;

	listJson.sort(sort_by(id, sortOrder, function(a) {
		return a
	}, type));

}
var sort_by = function(field, reverse, primer, type) {

	reverse = (reverse) ? -1 : 1;
	return function(a, b) {

		var tmp_field = field
		if (field.match(/\./)) {

			var sp = field.split('.');
			a = a[sp[0]];
			b = b[sp[0]];
			field = sp[1];
		}
		a = a[field];
		b = b[field];
		field = tmp_field;
		if (type == 'num') {
			if (a == null) {
				a = 0;
			}
			if (b == null) {
				b = 0;
			}
		} else if (type == 'str') {
			if (a == null) {
				a = '';
			}
			if (b == null) {
				b = '';
			}
		}

		if (typeof (primer) != 'undefined') {
			a = primer(a);
			b = primer(b);
		}
		if (type == 'num') {

			if (parseInt(a) < parseInt(b))
				return reverse * -1;
			if (parseInt(a) > parseInt(b))
				return reverse * 1;
		} else if (type == 'str') {
			if (a < b)
				return reverse * -1;
			if (a > b)
				return reverse * 1;
		}
		return 0;
	}
};

var body = "";
var body_template = "";
var start = 0;
function listSetting() {
	$('#list-counts').text(listJson.length);

	$('#list_body').show();
	body = $('#list_body');
	body_template = $('#list_body_template');
	body.empty();

	start = (parseInt(page) - 1) * parseInt($('#viewNum').val());

	if (start > listJson.length) {
		start = 0;
		page = 1;
	}
}

function paging(val) {
	page = val;
	makeList($('#viewNum').val());
}
function sort(obj, type) {
	sortJson(listJson, obj, type);
	makeList();
}
function tableReset() {
	page = 1;
	$('.sorticon').remove();
	sortId = "";
	sortOrder = false;
	$('#list_body').hide();
	$('.pagination').html('');
	$('#list-counts').html('');
}

///*
// * 表示設定
// */
//function openRowsDialog(kbn) {
//	if (kbn == 1) {
//		if($("#closedDisplaySetting1").val() == "1"){
////			$("#rowsOverlay1").css('display' , 'block');
//			$("#rowsDialog1").css('display' , 'block');
//			$("#closedDisplaySetting1").val("0");
//		} else {
////			$("#rowsOverlay1").css('display' , 'none');
//			$("#rowsDialog1").css('display' , 'none');
//			$("#closedDisplaySetting1").val("1");
//		}
//	}
//	if (kbn == 2) {
//		if($("#closedDisplaySetting2").val() == "1"){
////			$("#rowsOverlay2").css('display' , 'block');
//			$("#rowsDialog2").css('display' , 'block');
//			$("#closedDisplaySetting2").val("0");
//		} else {
////			$("#rowsOverlay2").css('display' , 'none');
//			$("#rowsDialog2").css('display' , 'none');
//			$("#closedDisplaySetting2").val("1");
//		}
//	}
//	
//}
/*
 * 表示設定
 */
function openDisplayNumDialog(obj) {

	if ($(obj).parent().find('.rowsDialog').css('display') == 'none') {
		$('.rowsDialog').css('display', 'none');
		$(obj).parent().find('.rowsDialog').css('display', 'block');
	} else {
		$(obj).parent().find('.rowsDialog').css('display', 'none');
	}

	$(obj).parent().find('.display-' + $('#viewNum').val()).prop('checked', true);

}
function setRows(obj) {
	$('#viewNum').val(parseInt($(obj).val(), 10));
	$(obj).closest('.rowsDialog').css('display', 'none');
	tableReset();
	makeList();
}

/**
 * String.format。
 * [javascriptで.NETとかのString.Formatしたい - みんなのちからになりたい]
 * http://ginzanomama.hatenablog.com/entry/2013/04/15/114356
 * [JavaScript で文字列フォーマットを実装してみた(sprintf もどき) | phiary]
 * http://phiary.me/javascript-string-format/
 */
if (String.prototype.format == undefined) {
	/**
	 * フォーマット関数
	 */
	String.prototype.format = function(arg) {
		// 置換ファンク
		var rep_fn = undefined;

		// オブジェクトの場合
		if (typeof arg == "object") {
			rep_fn = function(m, k) {
				return arg[k];
			}
		}
		// 複数引数だった場合
		else {
			var args = arguments;
			rep_fn = function(m, k) {
				return args[parseInt(k)];
			}
		}

		return this.replace(/\{(\w+)\}/g, rep_fn);
	}
}



function escapeTag(str) {

	if (str == null) {
		return "";
	}

	if (str != null && str != '') {
		str = str.toString().replace(/</g, "&lt;");
		str = str.toString().replace(/>/g, "&gt;");
		str = str.toString().replace(/'/g, "&#39;");
		str = str.toString().replace(/"/g, "&quot;");
	}
	return str;
}

function priceFormat(price) {

	if (price == '-') {

		return price;
	}

	if (price == 0) {
		return '0';
	} else if (price != null && price != '') {
		return price.replace(/(\d)(?=(\d{3})+$)/g, '$1,');
	} else {
		return '';
	}
}

function successCheck() {

	if ($('#successFlg').val() == '1') {
		if ($('#message').val() != null && $('#message').val() != '') {
			$.toast({
				text: $('#message').val(),
				icon: "success",
				hideAfter: false,
				position: 'mid-center'
			});
		}
	}
	if ($('#successFlg').val() == '9') {
		if ($('#message').val() != null && $('#message').val() != '') {
			$.toast({
				text: $('#message').val(),
				hideAfter: 3000,
				position: 'mid-center'
			});
		}
	}
	$('#successFlg').val('');
	$('#message').val('');
	$('#message').val('');
}


$.wait = function(msec) {
	// Deferredのインスタンスを作成
	var d = new $.Deferred;

	setTimeout(function() {
		// 指定時間経過後にresolveしてdeferredを解決する
		d.resolve(msec);
	}, msec);

	return d.promise();
};


function imgError(obj) {
	$(obj).attr("onerror", null);
	$(obj).attr("src", "./images/no_image.png");
}

$(function() {
	$('.date').datepicker({
		dateFormat: 'yy/mm/dd',
		language: "ja",
		autoclose: true,
		beforeShow: function(input, inst) {
			var calendar = inst.dpDiv;
			setTimeout(function() {
				calendar.position({
					my: 'right top',
					at: 'right bottom',
					of: input
				});
			}, 1);
		}
	});

	$('.month').datepicker({
		format: "yyyy/mm",
		language: "ja",
		autoclose: true,
		minViewMode: 'months'
	});

	$('.time').timepicker({
		timeFormat: 'H:i',
		maxTime: '23:30',
		show2400: true,
	});

	$.datetimepicker.setLocale('ja');
});

$(document).keydown(function(e) {
	enterSearch();
});

function enterSearch() {
	var keyCode = event.keyCode;
	if (keyCode == 13) {
		if ($('#condition_div input[type=text],input[type=checkbox],select').is(':focus')) {
			search();
		}
	}
}


/*
 * ローディング表示
 */
var loader = 0;
function viewAjaxLoadStart() {
	if (loader == 0) {
		//		$("#modal_loading").modal();
		$("#modal_loading").addClass('active');
		$('body').addClass('modal-bgcolor-white');
		loader = 1;
	}

}
function viewAjaxLoadEnd() {
	//	$("#modal_loading").modal("hide");
	//	$("#modal_loading").css('display', 'none');
	$("#modal_loading").removeClass('active');
	$.wait(500).done(function() {
		$(".modal-backdrop").remove();
		//		$("#modal_loading").css('display', 'none');
		loader = 0;
	});
}


///*
// * パスワード
// */
//function checkPasswordStrength() {
//	const strengthBar = document.getElementById('password-strength-bar');
//	var password = $('#password').val();
//
//	let strength = 0;
//	const hasNumber = /[0-9]/.test(password);
//	const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(password);
//	const hasLower = /[a-z]/.test(password);
//	const hasUpper = /[A-Z]/.test(password);
//
//	if (hasNumber) strength++;
//	if (hasSpecial) strength++;
//	if (hasLower) strength++;
//	if (hasUpper) strength++;
//
//	// ストレングスに応じてバーの幅を設定
//	strengthBar.style.width = (strength / 4) * 100 + '%'; // 4種類で計算
//
//	// 強度に応じて表示メッセージを変更
//	if (strength === 2) {
//		$("#password-strength-msg").html("弱");
//	} else if (strength === 3) {
//		$("#password-strength-msg").html("中");
//	} else if (strength === 4) {
//		$("#password-strength-msg").html("強");
//	} else {
//		$("#password-strength-msg").html("脆弱");
//	}
//}
function pushEyeButton() {
	if ($("#password").prop("type") === "text") {
		$("#password").prop("type", "password");
		$("#buttonEye").addClass("fa-eye");
		$("#buttonEye").removeClass("fa-eye-slash");
	} else {
		$("#password").prop("type", "text");
		$("#buttonEye").removeClass("fa-eye");
		$("#buttonEye").addClass("fa-eye-slash");
	}
}

//半角英数字に変換＆セット
function setHankaku(id) {
	var value = zenkakuToHankaku($(id).val());
	$(id).val(value);
}
function zenkakuToHankaku(strVal) {
	// 半角変換
	var halfVal = strVal.replace(/[！-～]/g, function(tmpStr) {
		// 文字コードをシフト
		return String.fromCharCode(tmpStr.charCodeAt(0) - 0xFEE0);
	});

	// 文字コードシフトで対応できない文字の変換
	return halfVal.replace(/”/g, "\"").replace(/’/g, "'").replace(/‘/g, "`")
		.replace(/￥/g, "\\").replace(/　/g, " ").replace(/〜/g, "~");
}

// ハイフン除去
function removeHyphen(id) {
	var value = $(id).val().replace(/-/g, "");
	$(id).val(value);
}

//function filterAdd(obj) {
//	var select = $('#add-filter-div').find('.add-filter').clone();
//	$(select).remove('')
//	$(select).html($('#add-filter-select-div').html()); 
//	$(select).find('select').removeAttr('id');
//	$('#filter-parts').find('.filter-row-select').html($(select).html());
//	$('#filter-parts').find('.filter-row-parts').html($('.filter-' + $(obj).val()).html());
//
//	var selectVal = $(obj).val();	
//	$(obj).val('');
//	
//	var rowNum = $('#filter-div').find('.filter-row').length;
//	$('#filter-parts').find('.filter-row').addClass('filter-row-' + rowNum);
//	
//	$('#filter-div').append($('#filter-parts').html());	
//	
//	$('#filter-div').find('.filter-row-' + rowNum).find('select').val(selectVal);
//}
//function deleteFilterRow(obj) {
//	$(obj).closest('.filter-row').remove();
//}
function filterClear() {

	$('#listFilter').find('input').val("");
	$('#listFilter').find('select').val("");

	return false;
}

function openRegistForm(obj) {
	var target = $(obj).closest('.open-dev').find('.open-target');
	var obj2 = $(obj).closest('.open-dev').find('.open-icon');
	//	alert($(obj2).hasClass('close'))

	if ($(obj2).hasClass('open')) {
		$(obj2).removeClass('open');
		$(obj2).addClass('close');
		$(target).hide();

		viewFilterBackEnd(obj2);

		$(obj2).closest('.filter-block').removeClass('active');
		$(obj).closest('.open-dev').find('.filter-opentext').html($('#textFilterOpen').val());


	} else {
		$(obj2).removeClass('close');
		$(obj2).addClass('open');
		$(target).show();
		//		alert()
		viewFilterBackStart(obj2);

		$(obj2).closest('.filter-block').addClass('active');
		$(obj).closest('.open-dev').find('.filter-opentext').html($('#textFilterClose').val());
		//		alert()
	}
}

function passwordIconChange(obj) {

	const type = $(obj).parent().find('.inputpassword').attr('type');

	if (type == 'password') {
		$(obj).parent().find('.inputpassword').attr('type', 'text');
		$(obj).removeClass('fa-eye-slash');
		$(obj).addClass('fa-eye');
	} else {
		$(obj).parent().find('.inputpassword').attr('type', 'password');
		$(obj).removeClass('fa-eye');
		$(obj).addClass('fa-eye-slash');
	}
}
/*
 * パスワードの脆弱確認バー
 */
function checkPassword(obj) {
	var password = $(obj).val();

	let strength = 0;
	const hasNumber = /[0-9]/.test(password);
	const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(password);
	const hasLower = /[a-z]/.test(password);
	const hasUpper = /[A-Z]/.test(password);

	if (hasNumber) strength++;
	if (hasSpecial) strength++;
	if (hasLower) strength++;
	if (hasUpper) strength++;

	$(obj).closest('.password-item').find('.password-strength-item').removeClass('strength-bar-0');
	$(obj).closest('.password-item').find('.password-strength-item').removeClass('strength-bar-1');
	$(obj).closest('.password-item').find('.password-strength-item').removeClass('strength-bar-2');
	$(obj).closest('.password-item').find('.password-strength-item').removeClass('strength-bar-3');
	$(obj).closest('.password-item').find('.password-strength-item').removeClass('strength-bar-4');
	$(obj).closest('.password-item').find('.password-strength-item').addClass('strength-bar-' + strength);

	// 強度に応じて表示メッセージ・バーの色を変更
	if (strength === 2) {
		$(obj).closest('.password-item').find('.strength-msg').html("弱");
	} else if (strength === 3) {
		$(obj).closest('.password-item').find('.strength-msg').html("中");
	} else if (strength === 4) {
		$(obj).closest('.password-item').find('.strength-msg').html("強");
	} else {
		$(obj).closest('.password-item').find('.strength-msg').html("脆弱");
	}
}
function scrollUp() {
	//	alert($('.list-area').offset().top)
	//	$(window).scrollTop($('#list_table').offset().top);
	//	$('html, body').animate({scrollTop:300});


	//$(".container").animate({scrollTop:0});
	// スムーススクロール
	//   $('body').animate({scrollTop:$('#list_table').offset().top}, 500);
	$('body').animate({ scrollTop: $('body').offset().top }, 500);

}

var filterObj;
function viewFilterBackStart(obj) {
	filterObj = obj;
	if (!$("#filter_back").hasClass('active')) {
		$("#filter_back").addClass('active');
	}

}
function viewFilterBackEnd() {
	if ($("#filter_back").hasClass('active')) {
		$("#filter_back").removeClass('active');
	}
	var target = $(filterObj).closest('.open-dev').find('.open-target');
	$(filterObj).removeClass('open');
	$(filterObj).addClass('close');
	$(target).hide();
	$(filterObj).closest('.filter-block').removeClass('active');
	$(filterObj).closest('.open-dev').find('.filter-opentext').html($('#textFilterOpen').val());
}


function proxyLogout() {
	window.location.href = "./login?action=proxyLogout";
}