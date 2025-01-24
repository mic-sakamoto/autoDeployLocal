$(document).ready(function() {
	
	//ユーザ情報取得（規約同意ボタンの表示有無用）
	getUserInfo();
	
	//パスワードの一致確認
	$(document).on('blur','#password-valid',function(event){checkPasswordMatch();});

	//「利用規約を表示」ボタン押下
	$(document).on('click','#open-riyoKiyaku',function(event){openTerms();});
	
	//「パスワードを更新してログインする」ボタン制御
	$(document).on('blur','#password',function(event){setLoginBtn();});
	$(document).on('blur','#password-valid',function(event){setLoginBtn();});
	$(document).on('click','#check-btn',function(event){setLoginBtn();});
	
	//「パスワードを更新してログインする」ボタン押下
	$(document).on('click','#login-btn',function(event){login();});
});

function getUserInfo() {
	var postData = gatherInputs("#form", true);
	doAjax(postData, "getUserInfo", "./password-set").then(function(data) {
		$("#user-id").html(data.userId);
		$("#agreed-flg").val(data.agreedFlg);
		if(data.agreedFlg == "1"){
			$(".kiyaku-div").css('display' , 'none');
		}
	});
}

//function pushHideButton() {
//	//確認用パスワード
//	if ($("#password-valid").prop("type") === "text") {
//		$("#password-valid").prop("type", "password"); 
//		$("#buttonEye-valid").addClass("fa-eye");
//		$("#buttonEye-valid").removeClass("fa-eye-slash");
//	} else {
//		$("#password-valid").prop("type", "text"); 
//		$("#buttonEye-valid").removeClass("fa-eye");
//		$("#buttonEye-valid").addClass("fa-eye-slash");
//	}
//
//}

function checkPasswordMatch() {
	if ($('#password').val() !== $('#password-valid').val()) {
		$("#password-alert").css('display' , 'block');
	} else {
		$("#password-alert").css('display' , 'none');
	}
}

function openTerms() {
	const termsUrl = '';
	window.open(termsUrl, '_blank');
}

function setLoginBtn() {
	if ($('#password').val() && $('#password-valid').val() && $('#password').val() == $('#password-valid').val() // パスワードが設定されている、かつ一致している
		&& ($('#agreed-flg').val() != '0' || $('#check-btn').is(':checked'))) {　//　新規登録の場合利用規約に同意している
		$("#login-btn").prop('disabled', false);
	} else {
		$("#login-btn").prop('disabled', true);
	}
}

function login() {
	event.preventDefault();
	var postData = gatherInputs("#form", true);
	doAjax(postData, "updatePassword", "./password-set").then(function(data) {
		if (data.successFlg == '1') {
			to_top();
		} else if (data.successFlg == '9') {
			$('#loginErrorMsgContainer').removeClass("hide");
			$('#loginErrorMsg').removeClass("hide");
			$('#loginErrorMsgTitle').html(data.errormessage);
		}
	});
}