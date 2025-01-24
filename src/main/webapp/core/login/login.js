$(function() {
	//ログインボタン押下
	$('#storeLoginBtn, #customerLoginBtn').on('click', function() { login(this) });

	//エンターキーでもログイン可能
	$('.input-login-id, .input-login-password').on('keypress', function(e) {
		if (e.which === 13) {
			e.preventDefault();
			let btnElm = $(this).closest('.tab-content-area').find('.loginButton');
			if (!$(btnElm).is(':disabled')) {//disabledの時は発火させない
				login(btnElm);
			} else {
				console.log("check");
			}
		}
	});

	//タブ切替
	$('.tab-btn').on('click', function() { setActiveTab(this) });

	//パスワード表示/非表示切替
	$('.passwordToggle').on('click', function() { togglePasswordVisibility(this) });

	//入力済みチェック→ログインボタン有効化
	$('.input-login-id, .input-login-password').on('input change', function() {
		checkInputs(this);
	});

	//プライバシーポリシーリンク押下
	$('.privacyPolicyLink').on('click', function(event) { to_ext_uss_pp(event) });

	//パスワードをお忘れですか？押下
	$('.forgotPassword').on('click', function() { to_reset_password() });

	//利用開始押下
	$('.customerStartLink').on('click', function() { to_start_using() });


	//クッキーから表示タブ情報を取得、タブ切替
	var activeTab = $.cookie('activeTab');
	if (activeTab == "customer-tab") {
		setActiveTab($("#customer-tab-btn"));
	}
});

/*
 * ログイン処理
 */
function login(inpuElm) {
	//エラーメッセージ非表示
	$('#loginErrorMsgContainer').addClass("hide");
	$('#loginErrorMsg').addClass("hide");

	const formId = $(inpuElm).hasClass("store") ? "storeForm" : "customerForm";
	var postData = {};
	postData["loginId"] = $("#" + formId).find(".input-login-id").val();
	postData["password"] = $("#" + formId).find(".input-login-password").val();
	postData["loginRole"] = $("#" + formId).find(".loginRole").val();
	doAjax(postData, "login", "./login").then(function(data) {
		if (data.successFlg == '1') {
			to_top();
		} else if (data.successFlg == '9') {
			$('#loginErrorMsgContainer').removeClass("hide");
			$('#loginErrorMsg').removeClass("hide");
			$('#loginErrorMsgTitle').html(data.errormessage);
		}
	});
}

/**
 * タブ切替処理
 */
function setActiveTab(activeTab) {
	//タブ切替ボタンのactive切替
	$('.tab-btn').removeClass('active');
	$(activeTab).addClass('active');
	//タブのactive切替
	$('.tab-content-area').removeClass('active');
	$('#' + $(activeTab).data('tab')).addClass('active');
	//表示タブをクッキーに保存
	$.cookie('activeTab', $(activeTab).attr("data-tab"));
}

/**
 * パスワード表示/非表示切替
 */
function togglePasswordVisibility() {
	const passwordInput = $('.input-login-password');
	// type属性をpasswordとtextで切り替え
	if (passwordInput.attr('type') === 'password') {
		passwordInput.attr('type', 'text');
		$('.passwordToggle .eye').removeClass('hide');
		$('.passwordToggle .eye-slash').addClass('hide');

	} else {
		passwordInput.attr('type', 'password');
		$('.passwordIcon').attr('src', './images/svg/eye-slash.svg');
		$('.passwordToggle .eye').addClass('hide');
		$('.passwordToggle .eye-slash').removeClass('hide');
	}
}

/**
 * 入力欄が満たされた場合にログインボタンを有効化する
 */
function checkInputs(inputElm) {
	const tab = $(inputElm).closest(".tab-content-area").hasClass("store") ? "store-tab" : "customer-tab";

	// 各入力の値やチェックを確認
	const isLoginFilled = $('#' + tab + ' .input-login-id').val().trim() !== "";
	const isPasswordFilled = $('#' + tab + ' .input-login-password').val().trim() !== "";

	// 全ての条件が満たされた場合のみボタンを有効化
	if (isLoginFilled && isPasswordFilled) {
		$('#' + tab + ' .loginButton').prop('disabled', false);
	} else {
		$('#' + tab + ' .loginButton').prop('disabled', true);
	}
}


