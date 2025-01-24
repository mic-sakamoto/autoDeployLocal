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
	doAjax(postData, "searchList", "./vehicle-mgmt").then(function(data) {
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
				
				body_template.find('.list-hid-seq').val(data.seq);
				body_template.find('.list-maker').html(escapeTag(data.maker));
				body_template.find('.list-model').html(escapeTag(data.model));
				body_template.find('.list-katashiki').html(escapeTag(data.katashiki));
				body_template.find('.list-year').html(escapeTag(data.year));
				body_template.find('.list-e_g_start_typeText').html(escapeTag(data.e_g_start_typeText));
				body_template.find('.list-verify_codeText').html(escapeTag(data.verify_codeText));
				body_template.find('.list-id').html(escapeTag(data.id));
				body_template.find('.list-vehicle_no').html(escapeTag(data.vehicle_no));
				
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