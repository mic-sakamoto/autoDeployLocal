$(document).ready(function() {
	$("#display-set").css('display', 'block');
	$("#list_div").show();
	$('#add-filter-select').val('')

	// お問い合わせ情報取得
	searchList();

	//「+新規メッセージ作成」ボタン押下
	$(document).on('click', '#newRegistBtn', function(event) { newRegist(); });

	//モーダル内「宛先設定」を半角変換
	$(document).on('change', '#answererId', function(event) { setHankaku('#answererId'); });

	//モーダル内「検索」」ボタン押下
	$(document).on('click', '#idSearchBtn', function(event) { searchAnswerInfo(); });

	//モーダル内「ファイル添付」ボタン押下
	$(document).on('click', '#inquiryFileBtn', function(event) { inquiryFileSelect(); });
	$(document).on('click', '#staffInquiryFileBtn', function(event) { staffInquiryFileSelect(); });
	$(document).on('click', '#answerFileBtn', function(event) { answerFileSelect(); });

	//モーダル内「宛先に設定」ボタン押下
	$(document).on('click', '#storeIdSetBtn', function(event) { storeIdSet(); });
	$(document).on('click', '#customerIdSetBtn', function(event) { customerIdSet(); });

	//モーダル内「同意」ボタン押下
	$(document).on('click', '.agreementCheckbox', function(event) { agreementCheck(); });

	//モーダル内「本文」文字カウント
	$(document).on('input', '#inquireBody', function(event) { textCount(1); });
	$(document).on('input', '#staffInquireBody', function(event) { textCount(2); });

	//モーダル内添付ファイル「✖」ボタン押下
	$(document).on('click', '.inquiryXmark1', function(event) { inquiryFileClear(1); });
	$(document).on('click', '.inquiryXmark2', function(event) { inquiryFileClear(2); });
	$(document).on('click', '.staffInquiryXmark1', function(event) { staffInquiryFileClear(1); });
	$(document).on('click', '.staffInquiryXmark2', function(event) { staffInquiryFileClear(2); });
	$(document).on('click', '.answerXmark1', function(event) { answerFileClear(1); });
	$(document).on('click', '.answerXmark2', function(event) { answerFileClear(2); });

	//モーダル内「閉じる」ボタン押下
	$(document).on('click', '#registModalCloseBtn', function(event) {
		$('.regist-modal').removeClass('active');
		modalClear();
	});
	$(document).on('click', '#staffRegistModalCloseBtn', function(event) {
		$('.staff-regist-modal').removeClass('active');
		staffModalClear();
	});
	$(document).on('click', '#detailModalCloseBtn', function(event) {
		$('.detail-modal').removeClass('active');
		searchList();
		detailModalClear();
	});

	//モーダル内 「送信」ボタン押下
	$(document).on('click', '#inquiryBtn', function(event) { inquiry(); });
	$(document).on('click', '#staffInquiryBtn', function(event) { staffInquiry(); });
	$(document).on('click', '#answerBtn', function(event) { answer(); });

	//フィルター設定実行ボタン
	$(document).on('click', '#searchList-btn', function(event) {
		searchList();
		$('.filter-opentext').click();
	});

	//各レコード押下
	$(document).on('click', '.list-card', function(event) { openDetail(this); });
});

/*
 * リスト検索
 *
 * */
let List = [];
function searchList() {
	$('#list_div').show();

	var postData = gatherInputs(".filter-area", true);
	doAjax(postData, "searchList", "./inquiry-mgmt").then(function(data) {
		listJson = data.result;
		tableReset();
		makeList(data.roleType);
	});

}


function makeList(role_type) {
	listSetting();
	if (listJson.length > 0) {
		$('#list-counts').text(listJson.length);
		var html = "";
		$.each(listJson, function(i, data) {
			if (i >= start && i < start + parseInt($('#viewNum').val())) {
				body_template.find('.list-seq').attr('data-seq', data.Seq);
				body_template.find('.list-staffIsUnread').html(data.StaffIsUnread);
				body_template.find('.list-customerIsUnread').html(data.CustomerIsUnread);
				body_template.find('.list-senderInfo').html(setSenderInfo(data));
				body_template.find('.list-senderInfo-customer').html(setSenderInfoCustomer(data));
				body_template.find('.list-addressInfo').html(setAddressInfo(data));
				body_template.find('.list-status').html(setStatus(data.InquirerRoleKbn));

				if (role_type == '1') {
					body_template.find('.list-newIcon').html(setNewIcon(data.StaffIsUnread));
				} else if (role_type == '2' || role_type == '3') {
					body_template.find('.list-newIcon').html(setNewIcon(data.CustomerIsUnread));
				}

				body_template.find('.list-inquireDate').html(escapeTag(data.InquireDate));
				body_template.find('.list-answerDate').html(escapeTag(data.AnswerDate));

				html = html + body_template.html();
			}
		});
		body.html(html);

		makePager(listJson);
	} else {
		$('#list-counts').text(listJson.length);
	}
}

function setSenderInfo(data) {
	var html = "";
	if (data.InquirerRoleKbn == "1") {
		//USSサポートサービス
		html += '<div><span class="text-m bold">' + $('#text_sender').val() + '</span><span class="senderUserName staff-senderUserName list-font-staff">' + $('#text_uss-ss').val() + '</span></div>';
		html += '<div class="staff-name"><span class="list-font-staff">' + $('#text_uss-staff').val() + '</span><span class="list-font-staff">' + data.InquirerName + '</span></div>';
	} else if (data.InquirerRoleKbn == "2") {
		//販売店
		html += '<div><span class="text-m bold">' + $('#text_sender').val() + '</span><span class="senderUserName store-senderUserName list-font-store">' + data.InquirerName + '</span></div>';
		html += '<div class="store-user-id"><span class="list-font-id">' + $('#text_id').val() + '</span><span class="list-senderUserId list-font-id">' + data.InquirerId + '</span></div>';
	} else if (data.InquirerRoleKbn == "3") {
		//顧客
		html += '<div><span class="text-m bold">' + $('#text_sender').val() + '</span><span class="senderUserName customer-senderUserName list-font-customer">' + data.InquirerName + '</span></div>';
		html += '<div class="customer-user-id"><span class="list-font-id">' + $('#text_id').val() + '</span><span class="list-senderUserId list-font-id">' + data.InquirerId + '</span></div>';
	}
	return html;
}

function setSenderInfoCustomer(data) {
	var html = "";
	if (data.InquirerRoleKbn == "1") {
		//USSサポートサービス
		html += '<div><span class="text-m bold">' + $('#text_sender').val() + '</span><span class="senderUserName staff-senderUserName list-font-staff">' + $('#text_uss-ss').val() + '</span></div>';
	} else if (data.InquirerRoleKbn == "2") {
		//販売店
		html += '<div><span class="text-m bold">' + $('#text_sender').val() + '</span><span class="senderUserName store-senderUserName list-font-store">' + data.InquirerName + '</span></div>';
	} else if (data.InquirerRoleKbn == "3") {
		//顧客
		html += '<div><span class="text-m bold">' + $('#text_sender').val() + '</span><span class="senderUserName customer-senderUserName list-font-customer">' + data.InquirerName + '</span></div>';
	}
	return html;
}

function setAddressInfo(data) {
	var html = "";
	if (data.AnswererRoleKbn == "1") {
		//USSサポートサービス
		html += '<div><span class="text-m bold">' + $('#text_address').val() + '</span><span class="addressUserName staff-addressUserName list-font-staff">' + $('#text_uss-ss').val() + '</span></div>';
		html += '<div class="staff-name"><span class="list-font-staff">' + $('#text_uss-staff').val() + '</span><span class="list-font-staff">' + data.AnswererName + '</span></div>';
	} else if (data.AnswererRoleKbn == "2") {
		//販売店
		html += '<div><span class="text-m bold">' + $('#text_address').val() + '</span><span class="addressUserName store-addressUserName list-font-store">' + data.AnswererName + '</span></div>';
		html += '<div class="store-user-id"><span class="list-font-id">' + $('#text_id').val() + '</span><span class="list-senderUserId list-font-id">' + data.AnswererId + '</span></div>';
	} else if (data.AnswererRoleKbn == "3") {
		//顧客
		html += '<div><span class="text-m bold">' + $('#text_address').val() + '</span><span class="addressUserName customer-addressUserName list-font-customer">' + data.AnswererName + '</span></div>';
		html += '<div class="customer-user-id"><span class="list-font-id">' + $('#text_id').val() + '</span><span class="list-senderUserId list-font-id">' + data.AnswererId + '</span></div>';
	}
	return html;
}

function setStatus(InquirerRoleKbn) {
	var html = "";
	if (InquirerRoleKbn == "2" || InquirerRoleKbn == "3") {
		//USS-SSへの問い合わせ
		html += '<div class="list-status status color-green" value="0">' + $('#text_status_0').val() + '</div>';
	} else if (InquirerRoleKbn == "1") {
		//USS-SSからの連絡
		html += '<div class="list-status status color-red" value="1">' + $('#text_status_1').val() + '</div>';
	}
	return html;
}

function setNewIcon(IsUnread) {

	var html = "";
	if (IsUnread == "1") {
		html += '<div class="NewKbn" value="1">' + $('#text_newIcon').val() + '</div>';
	}
	return html;
}

/*
 * 新規メッセージ作成
 *
 * */
function newRegist() {
	var postData = {};
	doAjax(postData, "checkContract", "./inquiry-mgmt").then(function(data) {
		if (data.successFlg == "1") {
			if (data.roleType == '1') {
				$('.staff-regist-modal').addClass('active');
			} else if (data.roleType == '2' || data.roleType == '3') {
				$('.regist-modal').addClass('active');
			}
		}
	});
}

function inquiryFileSelect() {
	if ($('#inquireAttachedFile1').prop('files').length === 0) {
		$('#inquireAttachedFile1').click();
		$('#inquireAttachedFile1').parent().on('change', '#inquireAttachedFile1', function() {
			var file1 = $('#inquireAttachedFile1').prop('files')[0];
			if (isFileTypeValid(file1)) {
				$('#inquireAttachedFile1Display').html($('#text_tmpFile_1').val() + file1.name + '<img class="svg btn-svg-icon-black inquiryXmark1" src="./images/svg/xmark-solid.svg">');
				checkFileSelect();
			}
		});
	} else if ($('#inquireAttachedFile2').prop('files').length === 0) {
		$('#inquireAttachedFile2').click();
		$('#inquireAttachedFile2').parent().on('change', '#inquireAttachedFile2', function() {
			var file2 = $('#inquireAttachedFile2').prop('files')[0];
			if (isFileTypeValid(file2)) {
				$('#inquireAttachedFile2Display').html($('#text_tmpFile_2').val() + file2.name + '<img class="svg btn-svg-icon-black inquiryXmark2" src="./images/svg/xmark-solid.svg">');
				checkFileSelect();
			}
		});
	}
}
function staffInquiryFileSelect() {
	if ($('#staffInquireAttachedFile1').prop('files').length === 0) {
		$('#staffInquireAttachedFile1').click();
		$('#staffInquireAttachedFile1').parent().on('change', '#staffInquireAttachedFile1', function() {
			var file1 = $('#staffInquireAttachedFile1').prop('files')[0];
			if (isFileTypeValid(file1)) {
				$('#staffInquireAttachedFile1Display').html($('#text_tmpFile_1').val() + file1.name + '<img class="svg btn-svg-icon-black staffInquiryXmark1" src="./images/svg/xmark-solid.svg">');
				checkStaffFileSelect();
			}
		});
	} else if ($('#staffInquireAttachedFile2').prop('files').length === 0) {
		$('#staffInquireAttachedFile2').click();
		$('#staffInquireAttachedFile2').parent().on('change', '#staffInquireAttachedFile2', function() {
			var file2 = $('#staffInquireAttachedFile2').prop('files')[0];
			if (isFileTypeValid(file2)) {
				$('#staffInquireAttachedFile2Display').html($('#text_tmpFile_2').val() + file2.name + '<img class="svg btn-svg-icon-black staffInquiryXmark2" src="./images/svg/xmark-solid.svg">');
				checkStaffFileSelect();
			}
		});
	}
}
function answerFileSelect() {
	if ($('#answerAttachedFile1').prop('files').length === 0) {
		$('#answerAttachedFile1').click();
		$('#answerAttachedFile1').parent().on('change', '#answerAttachedFile1', function() {
			var file1 = $('#answerAttachedFile1').prop('files')[0];
			if (isFileTypeValid(file1)) {
				$('#answerAttachedFile1Display').html($('#text_tmpFile_1').val() + file1.name + '<img class="svg btn-svg-icon-black answerXmark1" src="./images/svg/xmark-solid.svg">');
				checkAnswerFileSelect();
			}
		});
	} else if ($('#answerAttachedFile2').prop('files').length === 0) {
		$('#answerAttachedFile2').click();
		$('#answerAttachedFile2').parent().on('change', '#answerAttachedFile2', function() {
			var file2 = $('#answerAttachedFile2').prop('files')[0];
			if (isFileTypeValid(file2)) {
				$('#answerAttachedFile2Display').html($('#text_tmpFile_2').val() + file2.name + '<img class="svg btn-svg-icon-black answerXmark2" src="./images/svg/xmark-solid.svg">');
				checkAnswerFileSelect();
			}
		});
	}
}

function isFileTypeValid(file) {
	const fileName = file.name.toLowerCase();
	const fileExtension = fileName.split('.').pop();
	return ['jpeg', 'jpg', 'gif', 'png', 'bmp', 'pdf'].includes(fileExtension);
}

function checkFileSelect() {
	if ($('#inquireAttachedFile1').prop('files').length === 0 || $('#inquireAttachedFile2').prop('files').length === 0) {
		$('#inquiryFileBtn').prop('disabled', false);
	} else {
		$('#inquiryFileBtn').prop('disabled', true);
	}
}
function checkStaffFileSelect() {
	if ($('#staffInquireAttachedFile1').prop('files').length === 0 || $('#staffInquireAttachedFile2').prop('files').length === 0) {
		$('#staffInquiryFileBtn').prop('disabled', false);
	} else {
		$('#staffInquiryFileBtn').prop('disabled', true);
	}
}
function checkAnswerFileSelect() {
	if ($('#answerAttachedFile1').prop('files').length === 0 || $('#answerAttachedFile2').prop('files').length === 0) {
		$('#answerFileBtn').prop('disabled', false);
	} else {
		$('#answerFileBtn').prop('disabled', true);
	}
}

function inquiryFileClear(kbn) {
	if (kbn == 1) {
		$('#inquireAttachedFile1').val('');
		$('#inquireAttachedFile1Display').html('');
	} else {
		$('#inquireAttachedFile2').val('');
		$('#inquireAttachedFile2Display').html('');
	}
	checkFileSelect();
}
function staffInquiryFileClear(kbn) {
	if (kbn == 1) {
		$('#staffInquireAttachedFile1').val('');
		$('#staffInquireAttachedFile1Display').html('');
	} else {
		$('#staffInquireAttachedFile2').val('');
		$('#staffInquireAttachedFile2Display').html('');
	}
	checkStaffFileSelect();
}
function answerFileClear(kbn) {
	if (kbn == 1) {
		$('#answerAttachedFile1').val('');
		$('#answerAttachedFile1Display').html('');
	} else {
		$('#answerAttachedFile2').val('');
		$('#answerAttachedFile2Display').html('');
	}
	checkAnswerFileSelect();
}

function modalClear() {
	$('.regist-modal textarea').val('');
	$('.regist-modal input').val('');
	$('#inquireAttachedFile1Display').html('');
	$('#inquireAttachedFile2Display').html('');
}
function staffModalClear() {
	$('.staff-regist-modal textarea').val('');
	$('.staff-regist-modal input').val('');
	$('#staffInquireAttachedFile1Display').html('');
	$('#staffInquireAttachedFile2Display').html('');
	$(".set-answererId").css('display', 'none');
}
function detailModalClear() {
	$('.detail-modal textarea').val('');
	$('.detail-modal input').val('');
	$('#answerAttachedFile1Display').html('');
	$('#answerAttachedFile2Display').html('');
}


/*
 * 「送信」ボタン押下
 *
 * */
function inquiry() {
	var postData = gatherInputs("#form-inquiry", true);
	if ($('#inquireAttachedFile1').prop('files').length != 0) {
		postData['InquireAttachedFile1'] = $('#inquireAttachedFile1').prop('files')[0].name;
	}
	if ($('#inquireAttachedFile2').prop('files').length != 0) {
		postData['InquireAttachedFile2'] = $('#inquireAttachedFile2').prop('files')[0].name;
	}

	doAjax(postData, "inquiry", "./inquiry-mgmt").then(function(data) {
		if (data.successFlg == '1') {
			$('.regist-modal').removeClass('active');
			searchList();
			modalClear();
		}

	});
}

function staffInquiry() {
	var postData = gatherInputs("#form-inquiry-staff", true);
	if ($('#staffInquireAttachedFile1').prop('files').length != 0) {
		postData['InquireAttachedFile1'] = $('#staffInquireAttachedFile1').prop('files')[0].name;
	}
	if ($('#staffInquireAttachedFile2').prop('files').length != 0) {
		postData['InquireAttachedFile2'] = $('#staffInquireAttachedFile2').prop('files')[0].name;
	}
	postData['InquireBody'] = $('#staffInquireBody').val();
	doAjax(postData, "inquiry", "./inquiry-mgmt").then(function(data) {
		if (data.successFlg == '1') {
			$('.staff-regist-modal').removeClass('active');
			searchList();
			staffModalClear();
		}
	});
}

function answer() {
	var postData = gatherInputs("#form-answer", true);
	postData['seq'] = $('#modal-seq').val();
	if ($('.agreementCheckbox').hasClass('active')) {
		postData['IsAgreement'] = '1';
	} else {
		postData['IsAgreement'] = '0';
	}
	if ($('#answerAttachedFile1').prop('files').length != 0) {
		postData['AnswerAttachedFile1'] = $('#answerAttachedFile1').prop('files')[0].name;
	}
	if ($('#answerAttachedFile2').prop('files').length != 0) {
		postData['AnswerAttachedFile2'] = $('#answerAttachedFile2').prop('files')[0].name;
	}

	doAjax(postData, "answer", "./inquiry-mgmt").then(function(data) {
		if (data.successFlg == '1') {
			$('.detail-modal').removeClass('active');
			searchList();
			detailModalClear();
		}
	});
}

/*
 * 各レコード押下
 *
 * */
function openDetail(e) {
	const seq = $(e).find('.list-seq').data('seq');
	var postData = {};
	postData['seq'] = seq;
	doAjax(postData, "searchDetail", "./inquiry-mgmt").then(function(data) {

		if (data.result[0].InquirerRoleKbn == "1") {
			//USSサポートサービス
			$('#messageKbn').html($('#text_status_1').val());
		} else if (data.result[0].InquirerRoleKbn == "2") {
			//販売店
			$('#messageKbn').html($('#text_status_0').val());
		} else if (data.result[0].InquirerRoleKbn == "3") {
			//顧客
			$('#messageKbn').html($('#text_status_0').val());
		}

		$('#detail-modal-InquireName').html(setDetailSenderInfoCustomer(data));
		$('#detail-modal-InquireDate').html(data.result[0].InquireDate);
		$('#detail-modal-InquireBody').html(data.result[0].InquireBody);
		$('#detail-modal-InquireFile').html(setInquireFile(data.result[0]));
		$('#detail-modal-AnswerName').html(setDetailAddressInfoCustomer(data));
		$('#detail-modal-AnswerDate').html(data.result[0].AnswerDate);
		$('#detail-modal-AnswerBody').html(data.result[0].AnswerBody);
		$('#detail-modal-AnswerFile').html(setAnswerFile(data.result[0]));

		$('#modal-seq').val(data.result[0].Seq);

		if (data.result[0].AgreementBodyKbn != "0" && data.result[0].AgreementBodyKbn != null) {
			if (data.result[0].AnswerDate != null && data.result[0].AnswerDate != '') {
				$("#agreemented-area").css('display', '');
				$('#agreemented-area').html(setAgreement(data.result[0], false));
				$("#agreement-area").css('display', 'none');
			} else {
				$("#agreement-area").css('display', '');
				$('#agreement-area').html(setAgreement(data.result[0], true));
				$("#agreemented-area").css('display', 'none');
			}
		} else {
			$(".agreement-area").css('display', 'none');
			$("#agreemented-area").css('display', 'none');
		}

		if (data.result[0].InquireBody != null && data.result[0].InquireBody != '') {
			$(".modal-InquireBody").css('display', '');
		} else {
			$(".modal-InquireBody").css('display', 'none');
		}

		if (data.result[0].AnswerDate != null && data.result[0].AnswerDate != '') {
			$(".modal-answer-body-area").css('display', '');
			if (data.result[0].AnswerBody != null && data.result[0].AnswerBody != '') {
				$(".modal-AnswerBody").css('display', '');
			} else {
				$(".modal-AnswerBody").css('display', 'none');
			}
		} else {
			$(".modal-answer-body-area").css('display', 'none');
		}

		if (data.userId == data.result[0].AnswererId && data.result[0].AnswerDate == null) {
			$(".answer-form").css('display', '');
			$("#detailAnswerBtn").css('display', '');
			$("#detailModalBtn").css('display', 'none');
		} else {
			$(".answer-form").css('display', 'none');
			$("#detailAnswerBtn").css('display', 'none');
			$("#detailModalBtn").css('display', '');
		}

		$('.detail-modal').addClass('active');

	});


}


function setDetailSenderInfoCustomer(data) {
	var html = "";
	if (data.result[0].InquirerRoleKbn == "1") {
		//USSサポートサービス
		if (data.roleType == '1') {
			html += '<div><span class="text-m">' + $('#text_sender').val() + '</span><span class="senderUserName staff-senderUserName list-font-staff text-m">' + $('#text_uss-ss').val() + '</span><span class="list-font-staff text-m">' + $('#text_uss-staff').val() + '</span><span class="list-font-staff text-m">' + data.result[0].InquirerName + '</span></div>';
		} else if (data.roleType == '2' || data.roleType == '3') {
			html += '<div><span class="text-m">' + $('#text_sender').val() + '</span><span class="senderUserName staff-senderUserName list-font-staff text-m">' + $('#text_uss-ss').val() + '</span></div>';
		}
	} else if (data.result[0].InquirerRoleKbn == "2") {
		//販売店
		html += '<div><span class="text-m">' + $('#text_sender').val() + '</span><span class="senderUserName store-senderUserName list-font-store text-m">' + data.result[0].InquirerName + '</span></div>';
	} else if (data.result[0].InquirerRoleKbn == "3") {
		//顧客
		html += '<div><span class="text-m">' + $('#text_sender').val() + '</span><span class="senderUserName customer-senderUserName list-font-customer text-m">' + data.result[0].InquirerName + '</span></div>';
	}
	return html;
}

function setInquireFile(data) {
	var html = "";
	if (data.InquireAttachedFile1 != null && data.InquireAttachedFile1 != "") {
		html += '<div class="model-InquireFile1">';
		html += '	<div class="file-icon"><img class="svg btn-svg-icon-black tmpFile-svg" src="./images/svg/file-pdf-solid.svg"></div>';
		html += '	<div class="model-InquireFileInfo">';
		html += '		<div class="text-m" id="">' + data.InquireAttachedFile1 + '</div>';
		html += '		<div class="text-m" id="inquireFile1-dlDeadline">' + $('#text_dldeadline').val() + '</div>';
		html += '	</div>';
		html += '	<div class="file-icon" id="inquireFile1-dlIcon"><img class="svg btn-svg-icon-black tmpFile-svg" src="./images/svg/download.svg"></div>';
		html += '</div>';
	}
	if (data.InquireAttachedFile2 != null && data.InquireAttachedFile2 != "") {
		html += '<div class="model-InquireFile2">';
		html += '	<div class="file-icon"><img class="svg btn-svg-icon-black tmpFile-svg" src="./images/svg/file-pdf-solid.svg"></div>';
		html += '	<div class="model-InquireFileInfo">';
		html += '		<div class="text-m" id="">' + data.InquireAttachedFile2 + '</div>';
		html += '		<div class="text-m" id="inquireFile1-dlDeadline">' + $('#text_dldeadline').val() + '</div>';
		html += '	</div>';
		html += '	<div class="file-icon" id="inquireFile2-dlIcon"><img class="svg btn-svg-icon-black tmpFile-svg" src="./images/svg/download.svg"></div>';
		html += '</div>';
	}
	return html;
}
function setAnswerFile(data) {
	var html = "";
	if (data.AnswerAttachedFile1 != null && data.AnswerAttachedFile1 != "") {
		html += '<div class="model-AnswerFile1">';
		html += '	<div class="file-icon"><img class="svg btn-svg-icon-black tmpFile-svg" src="./images/svg/file-pdf-solid.svg"></div>';
		html += '	<div class="model-AnswerFileInfo">';
		html += '		<div class="text-m" id="">' + data.AnswerAttachedFile1 + '</div>';
		html += '		<div class="text-m" id="answerFile1-dlDeadline">' + $('#text_dldeadline').val() + '</div>';
		html += '	</div>';
		html += '	<div class="file-icon" id="answerFile1-dlIcon"><img class="svg btn-svg-icon-black tmpFile-svg" src="./images/svg/download.svg"></div>';
		html += '</div>';
	}
	if (data.AnswerAttachedFile2 != null && data.AnswerAttachedFile2 != "") {
		html += '<div class="model-AnswerFile2">';
		html += '	<div class="file-icon"><img class="svg btn-svg-icon-black tmpFile-svg" src="./images/svg/file-pdf-solid.svg"></div>';
		html += '	<div class="model-AnswerFileInfo">';
		html += '		<div class="text-m" id="">' + data.AnswerAttachedFile2 + '</div>';
		html += '		<div class="text-m" id="answerFile1-dlDeadline">' + $('#text_dldeadline').val() + '</div>';
		html += '	</div>';
		html += '	<div class="file-icon" id="answerFile2-dlIcon"><img class="svg btn-svg-icon-black tmpFile-svg" src="./images/svg/download.svg"></div>';
		html += '</div>';
	}
	return html;
}


function setDetailAddressInfoCustomer(data) {
	var html = "";
	if (data.result[0].AnswererRoleKbn == "1") {
		//USSサポートサービス
		if (data.roleType == '1') {
			html += '<div><span class="text-m">' + $('#text_sender').val() + '</span><span class="senderUserName staff-senderUserName list-font-staff text-m">' + $('#text_uss-ss').val() + '</span><span class="list-font-staff text-m">' + $('#text_uss-staff').val() + '</span><span class="list-font-staff text-m">' + data.result[0].AnswererName + '</span></div>';
		} else if (data.roleType == '2' || data.roleType == '3') {
			html += '<div><span class="text-m">' + $('#text_sender').val() + '</span><span class="senderUserName staff-senderUserName list-font-staff text-m">' + $('#text_uss-ss').val() + '</span></div>';
		}
	} else if (data.result[0].AnswererRoleKbn == "2") {
		//販売店
		html += '<div><span class="text-m">' + $('#text_sender').val() + '</span><span class="senderUserName store-senderUserName list-font-store text-m">' + data.result[0].AnswererName + '</span></div>';
	} else if (data.result[0].AnswererRoleKbn == "3") {
		//顧客
		html += '<div><span class="text-m">' + $('#text_sender').val() + '</span><span class="senderUserName customer-senderUserName list-font-customer text-m">' + data.result[0].AnswererName + '</span></div>';
	}
	return html;
}


function setAgreement(data, flg) {
	var html = "";
	if (flg) {
		html += '<div><span class="text-m">' + data.AgreementBody + '</span></div><div class="agreementCheckbox" id="IsAgreement"><img class="svg btn-svg-icon" src="./images/svg/check-solid.svg">' + $('#text_agreement').val() + '</div>';
	} else {
		if (data.AgreementDate != null && data.AgreementDate != '') {
			html += '<div><span class="text-m">' + data.AgreementBody + '</span></div><div class="agreementCheckbox disabled active" id=""><img class="svg btn-svg-icon" src="./images/svg/check-solid.svg">' + $('#text_agreement').val() + '</div>';
		} else {
			html += '<div><span class="text-m">' + data.AgreementBody + '</span></div><div class="agreementCheckbox disabled" id=""><img class="svg btn-svg-icon" src="./images/svg/check-solid.svg">' + $('#text_agreement').val() + '</div>';
		}
	}
	return html;
}


function searchAnswerInfo() {
	var postData = {};
	postData['webAppId'] = $('#modal-webAppId').val();
	doAjax(postData, "getAnswererInfo", "./inquiry-mgmt").then(function(data) {
		if (data.SuccessFlg == "9") {
			return;
		}
		if (data.result[0].StoreId != "") {
			$('#storeInfo').html($('#text_store_id').val() + data.result[0].StoreName);
			$('#storeInfo').val(data.result[0].StoreId);
			$("#storeIdSetBtn").css('display', '');
			$('#storeIdSetBtn').prop('disabled', false);
			$(".set-answererId").css('display', '');
		}
		if (data.result[0].CustomerId != "") {
			$('#customerInfo').html($('#text_customer_id').val() + data.result[0].CustomerName);
			$('#customerInfo').val(data.result[0].CustomerId);
			$("#customerIdSetBtn").css('display', '');
			$('#customerIdSetBtn').prop('disabled', false);
			$(".set-answererId").css('display', '');
		}
	});
}

function storeIdSet() {
	$('#answererId').val($('#storeInfo').val());
}

function customerIdSet() {
	$('#answererId').val($('#customerInfo').val());
}

function textCount(kbn) {
	if (kbn == 1) {
		const text = $('#inquireBody').val();
		const currentLength = text.length;
		$('#textCount').html(`${currentLength} / 1000`);
	} else {
		const text = $('#staffInquireBody').val();
		const currentLength = text.length;
		$('#staffTextCount').html(`${currentLength} / 1000`);
	}
}

function agreementCheck() {
	if ($('.agreementCheckbox').hasClass('active')) {
		$('.agreementCheckbox').removeClass('active');
	} else {
		$('.agreementCheckbox').addClass('active');
	}
}