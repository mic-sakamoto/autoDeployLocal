$(document).ready(function() {
	$("#display-set").css('display' , 'block');
	$("#list_div").show();
	$('#add-filter-select').val('')
	
	//パラメータにseqがある場合は更新のため情報取得
	chechParam();
	
	// お問い合わせ情報取得
	searchData();
	
	// 「送信」ボタン押下
	$(document).on('click','#registBtn',function(event){regist();});
});

function chechParam(){
	// 現在のURLから取得
	const params = new URLSearchParams(window.location.search);

	// 特定のパラメータを取得
	const seq = params.get('seq');
	
	if(seq != null && seq != ""){
//		searchList(seq);

		//デモ画面用
		regist();
	}
}


function searchData(){
	$('#inquiryMessage').html($('#text_inquiryMessage_0').val());
	$(".inquiry-form").css('display' , 'block');
}


/*
 * 「送信」ボタン押下
 *
 * */
let List = [];
function regist() {
	var postData = gatherInputs("#form", true);
	doAjax(postData, "regist", "./inquiry-mgmt").then(function(data) {
		$('#inquiryMessage').html($('#text_inquiryMessage_1').val());
		$(".inquiry-form").css('display' , 'none');
		$("#inquiry-data-area").css('display' , 'block');
		setData(data.result);
	});

}


function setData(data) {
    var html = '';
    $.each(data, function (i, row) {
        html += '<div class="">';
        html += '    <div class="data-title bold">';
        html += '    	<span>' + row.nameAndDatatime + '</span>';
        html += '    </div>';
        html += '    <div class="data-body">';
		html += '    	<span>' + row.body + '</span>';
		html += '    </div>';
		if(row.tempFlg=="1"){
			html += '<div class="data-temp">';
			html += '   <div class="set-icon">';
			if(row.tempKbn="1"){
				html += '	<i class="fa-solid fa-file-pdf icon"></i>';
			}
			html += '	</div>';
			html += '   <div class="set-temp-detail">';
			html += '    	<div class="temp-detail">' + row.fileName + '</div>';
			html += '    	<div class="temp-detail">' + row.fileSize + '</div>';
			html += '    	<div class="temp-detail">' + row.fileDownloadDeadline + '</div>';
			html += '	</div>';
			if(row.fileDownloadFlg=="1"){
				html +='<div class="set-icon">';
				html += '	<i class="fa-solid fa-download"></i>';
				html +='</div>';
			}else{
				html +='<div class="set-icon">';
				html += '	<i class="fa-solid fa-xmark"></i>';
				html +='</div>';
			}
			html += '</div>';
		}
        html += '</div>';
    });
	console.log(html);
    $('#inquiry-data').html(html);
}