$(document).ready(function() {
	$("#display-set").css('display', 'block');
	//	$("#list_div").show();
	$('#add-filter-select').val('')

	searchList();

});

$(function() {

	$('#filter-search').on('click', function(event) { searchList() });

	$('#newExaminationBtn').on('click', function() { selectInputMethdoModal(); });
	$('#loanSimulationBtn').on('click', function() { to_loan_simulation() });
	$('#downloadBtn').on('click', function() { outContractPdf() });

});

function outContractPdf() {
	
	$('#action').val('downloadJasper');
	$('#pdfName').val('KeiyakuIchiran');
	$('#form').attr('action', './pdf');
	$('#form').attr('target', '_blank');
	$('#form').submit();
}

function selectInputMethdoModal(){
	$('.select-input-method-modal').addClass('active');
}

/*
 * リスト検索
 *
 * */
let List = [];
function searchList() {

	$('#list_div').show();

	var postData = gatherInputs(".filter-area", true);
	doAjax(postData, "searchList", "./contract-mgmt").then(function(data) {
		listJson = data.searchList;
		tableReset();
		makeList();

		if (data.roleType == '2') {
			$(".no-store").hide();
		}
		if (data.roleType == '3') {
			$(".no-customer").hide();
		}
	});

}


function makeList() {

	listSetting();

	if (listJson.length > 0) {
		$('#list-counts').text(listJson.length);
		var html = "";
		$.each(listJson, function(i, data) {
			if (i >= start && i < start + parseInt($('#viewNum').val())) {

				body_template = $('#list_body_template').clone();

				body_template.find('.list-hid-contractId').val(data.ContractId);
				body_template.find('.list-hid-webAppId').val(data.WebAppId);
				body_template.find('.list-hid-statusKbn').val(data.StatusKbn);
				body_template.find('.list-hid-inputModeKbn').val(data.InputModeKbn);

				body_template.find('.list-userName').html(escapeTag(data.CustomerName));
				body_template.find('.list-userId').html(escapeTag(data.CustomerId));
				body_template.find('.list-storeName').html(escapeTag(data.StoreName));
				body_template.find('.list-storeId').html(escapeTag(data.StoreId));
				body_template.find('.list-status').html(escapeTag(data.StatusText));
				body_template.find('.list-status').addClass('status-color-' + escapeTag(data.StatusColor));
				body_template.find('.list-webAppId').html(escapeTag(data.WebAppId));
				body_template.find('.list-storeCharge').html(escapeTag(data.StaffName));

				if (data.IsProxyInput == '1') {
					body_template.find('.list-proxy-input').show();
				} else {
					body_template.find('.list-proxy-input').hide();
				}

				if (data.InputRoleKbn == '2') {
					body_template.find('.list-inputRole-S').show();
					body_template.find('.list-inputRole-C').hide();
				} else if (data.InputRoleKbn == '3') {
					body_template.find('.list-inputRole-S').hide();
					body_template.find('.list-inputRole-C').show();
				} else {
					body_template.find('.list-inputRole-S').hide();
					body_template.find('.list-inputRole-C').hide();
				}

				body_template.find('.list-screeningAppDate').html(escapeTag(data.ScreeningAppDate));
				body_template.find('.list-mainAppDate').html(escapeTag(data.MainAppDate));
				body_template.find('.list-verifiedDate').html(escapeTag(data.VerifiedDate));
				body_template.find('.list-loanDate').html(escapeTag(data.LoanDate));
				body_template.find('.list-updateTime').html(escapeTag(data.UpdateTime));

				html = html + body_template.html();
			}
		});

		body.html(html);

		makePager(listJson);
		//		sortJson(listJson, $("#list_table"), 'str');

		$('.list-card').click(function() { transition(this) });

	} else {
		$('#list-counts').text(listJson.length);
	}
}

function transition(obj) {
	var contractId = $(obj).find('.list-hid-contractId').val();
	var webAppId = $(obj).find('.list-hid-webAppId').val();
	var statusKbn = $(obj).find('.list-hid-statusKbn').val();
	var inputModeKbn = $(obj).find('.list-hid-inputModeKbn').val();

	var postData = {};
	postData['contractId'] = contractId;
	postData['webAppId'] = webAppId;
	postData['status'] = statusKbn;
	doAjax(postData, "getNowStatus", "./contract-mgmt").then(function(data) {

		postData['viewType'] = data.status;

		// 審査前
		if (data.status == '0') {
			if (data.roleType == '1') {
				$.toast({
					text: $('#notTransition').val(),
					hideAfter: 3000,
					position: 'mid-center'
				});
			} else {
				postData = {};
				postData['appKbn'] = '1';
				postData['contractId'] = contractId;
				postData['webAppId'] = webAppId;
				doPostWithData(postData, "application", "./application");
			}

		}
		// 審査～契約前
		else if (data.status == '1') {
			doPostWithData(postData, "", "./status");
		}
		// 契約後
		else if (data.status == '2') {
			doPostWithData(postData, "", "./status");
		}

	});
}
