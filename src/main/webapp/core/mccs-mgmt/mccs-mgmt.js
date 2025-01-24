$(document).ready(function() {
	$("#display-set").css('display' , 'block');
	$("#list_div").show();
	$('#add-filter-select').val('');
	
	//MCCS情報取得
	searchList();
	
	//フィルター設定実行ボタン
	$(document).on('click','#searchList-btn',function(event){
		searchList();
		$('.filter-opentext').click();
	});

	
	//行クリックで該当の精算明細表PDFが別タブで開く
	$(document).on('click','.list-card',function(event){openDetailPage(this);});
		
});

/*
 * リスト検索
 *
 * */
function searchList() {

	$('#list_div').show();
	var postData = gatherInputs(".filter-area", true);
	doAjax(postData, "searchList", "./mccs-mgmt").then(function(data) {
		listJson = data.result;
		tableReset();
		makeList();
	});

}


function makeList() {
	listSetting();
	if (listJson.length > 0) {
		$('#list-counts').text(listJson.length);
		var html = "";		
		$.each(listJson, function(i, data) {
			if (i >= start && i < start + parseInt($('#viewNum').val())) {
				body_template.find('.list-webAppId').attr('data-webAppId', data.WebAppId);
				body_template.find('.list-webAppId').val(data.WebAppId);
				body_template.find('.list-userName').html(data.UserName);
				body_template.find('.list-customerId').html(data.CustomerId);
				body_template.find('.list-model').html(data.Model);
				body_template.find('.list-mccsStatusType').html(setStatus(data.MccsStatusType));
				body_template.find('.list-webAppId').html(data.WebAppId);
				body_template.find('.list-mccsAttachtDate').html(data.MccsAttachtDate);
				body_template.find('.list-mainAppDate').html(escapeTag(data.MainAppDate));
				body_template.find('.list-verifiedDate').html(escapeTag(data.VerifiedDate));
				body_template.find('.list-updateDate').html(data.UpdateDate);
				
				html = html + body_template.html();
			}
		});
		body.html(html);	
		
		makePager(listJson);
	} else {
		$('#list-counts').text(listJson.length);
	}
}

function setStatus(status){
	var html = "";
	if(status == "0"){
		//申込情報の確認
		html += '<div class="list-status color-light-blue">' + $('#text_status_0').val() + '</div>';
	}else if(status == "1"){
		//発注待ち
		html += '<div class="list-status color-yellow">' + $('#text_status_1').val() + '</div>';
	}else if(status == "2"){
		//発送対応
		html += '<div class="list-status color-yellow">' + $('#text_status_2').val() + '</div>';
	}else if(status == "3"){
		//MCCS取付
		html += '<div class="list-status color-light-blue">' + $('#text_status_3').val() + '</div>';
	}else if(status == "4"){
		//納車作業中
		html += '<div class="list-status color-yellow">' + $('#text_status_4').val() + '</div>';
	}else if(status == "5"){
		//利用中
		html += '<div class="list-status color-green">' + $('#text_status_5').val() + '</div>';
	}else if(status == "6"){
		//利用終了
		html += '<div class="list-status color-gray">' + $('#text_status_6').val() + '</div>';
	}else if(status == "7"){
		//取りやめ
		html += '<div class="list-status color-gray">' + $('#text_status_7').val() + '</div>';
	}
	
	return html;
}


/*
 * 行クリックで画面遷移
 * */
function openDetailPage(e){
    const webAppId = $(e).find('.list-webAppId').data('webappid'); 
	window.open("./status?webAppId="+webAppId);
	
}