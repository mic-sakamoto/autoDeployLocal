$(document).ready(function() {
	$("#display-set").css('display' , 'block');
	$("#list_div").show();
	$('#add-filter-select').val('')
	
	searchList();
});

$(function() {

	$('#filter-search').on('click', function(event) { searchList() });

});


/*
 * リスト検索
 *
 * */
let List = [];
function searchList() {

	$('#list_div').show();	
	
	var postData = gatherInputs(".filter-area", true);
	doAjax(postData, "searchList", "./store-mgmt").then(function(data) {
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
				
				body_template.find('.list-hid-userId').val(data.UserId);
				body_template.find('.list-UserName').html(escapeTag(data.UserName));
				body_template.find('.list-UserId').html(escapeTag(data.UserId));
				body_template.find('.list-MemberCode').html(escapeTag(data.MemberCode));
				body_template.find('.list-TelNumber').html(escapeTag(data.TelNumber));
				body_template.find('.list-MailAddress').html(escapeTag(data.MailAddress));
//				body_template.find('.list-send-mail').html(escapeTag(data.verify_codeText));
//				body_template.find('.list-send-sms').html(escapeTag(data.id));
//				body_template.find('.list-last-login').html(escapeTag(data.vehicle_no));
				body_template.find('.list-CreateTime').html(escapeTag(data.CreateTime));
//				body_template.find('.list-').html(escapeTag(data.MailAddress));
//				body_template.find('.list-').html(escapeTag(data.MailAddress));
				body_template.find('.list-LastLoginTime').html(escapeTag(data.LastLoginTime));				


				html = html + body_template.html();
			}
		});
		
		body.html(html);	
			
		makePager(listJson);
//		sortJson(listJson, $("#list_table"), 'str');

		$('.list-card').click(function () { transition(this) });

	} else {
		$('#list-counts').text(listJson.length);
	}
}

function proxyLogin(obj) {
	
	var userId = $(obj).closest('.list-card').find('.list-hid-userId').val();
	var postData = {};

		postData["proxyLoginUserId"] = userId;

		doAjax(postData, "proxyLogin", "./store-mgmt").then(function(data) {
			if (data.successFlg == 9) {
				
			} else {
				to_top();
			}
		});
	
}