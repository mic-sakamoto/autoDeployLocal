$(document).ready(function() {
    // お知らせ情報取得
    searchList();
	
	//「+新規お知らせ作成」ボタン押下
	$(document).on('click','#newRegistBtn',function(event){newRegist();});
	
	//各レコード押下
	$(document).on('click','.list-card',function(event){openDetail(this);});
});

/*
 * リスト検索
 *
 * */
let List = [];
function searchList() {

	$('#list_div').show();	
	var postData = {};	
	doAjax(postData, "searchList", "./announcement-mgmt").then(function(data) {
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
				body_template.find('.list-seq').attr('data-seq', data.Seq);
				body_template.find('.list-text-body').html($('#text_body').val());
				body_template.find('.list-announcement-body').html('<span class="list-text-body">' + $('#text_body').val() + '</span><span>' + data.Body +'</span>');
				body_template.find('.list-isTargetKbn').val(data.Seq);
				body_template.find('.list-isTarget').html(setIsTarget(data));
				body_template.find('.list-openCloseDate').html('<span class="list-text-openCloseDate">' + $('#text_openCloseDate').val() + '</span><span>' + data.OpenCloseDate +'</span>');
				
				html = html + body_template.html();
			}
		});
		body.html(html);	
			
	} else {
		$('#list-counts').text(listJson.length);
	}
}

function setIsTarget(data){
	var html = "";
	if(data.IsTargetKbn == "0" ){
		html +=  '<div class="list-status color-light-blue" value="0">' + $('#text_isTargetStore').val() + '</div>';
	} else if(data.IsTargetKbn == "1"){
		html +=  '<div class="list-status color-green" value="1">' + $('#text_isTargetCustomer').val() + '</div>';
	} else if(data.IsTargetKbn == "2"){
		html +=  '<div class="list-status color-red" value="2">' + $('#text_isTargetStore').val() +"/"+ $('#text_isTargetCustomer').val() + '</div>';
		
	} 
	return html;
}


/*
 * 新規お知らせ作成
 *
 * */
function newRegist() {	
	var postData = {};
	doPost(postData, "newAnnouncementRegist", "./announcement-mgmt").then(function(data) {
		
	});
}


/*
 * 各レコード押下
 *
 * */
function openDetail(e){
	const seq = $(e).find('.list-seq').data('seq'); 
    window.open('./announcement-mgmt?seq='+seq, '_blank');
}



