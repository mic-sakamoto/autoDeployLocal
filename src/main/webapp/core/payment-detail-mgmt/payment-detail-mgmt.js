$(document).ready(function() {
	$("#display-set").css('display' , 'block');
	$("#list_div").show();
	$('#add-filter-select').val('');
	
	
	//精算明細票情報取得
	searchList();
	
	//フィルター設定実行ボタン
	$(document).on('click','#searchList-btn',function(event){
		searchList();
		$('.filter-opentext').click();
	});
	
	//行クリックで該当の精算明細表PDFが別タブで開く
	$(document).on('click','.list-card',function(event){openPaymentDetailPdf(this);});
	
});

/*
 * リスト検索
 * */
let List = [];
function searchList() {

	$('#list_div').show();
	var postData = gatherInputs(".filter-area", true);
	doAjax(postData, "searchList", "./payment-detail-mgmt").then(function(data) {
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
				body_template.find('.list-paymentDate').html(escapeTag(data.PaymentDate));
				
				html = html + body_template.html();
			}
		});
		body.html(html);	

		makePager(listJson);
	} else {
		$('#list-counts').text(listJson.length);
	}
}


/*
 * 行クリックで該当の精算明細表PDFが別タブで開く
 * */
function openPaymentDetailPdf(e) {
	const seq = $(e).find('.list-seq').data('seq');

	$('#action').val('printJasper');
	$('#pdfName').val('SeisanMeisaisho');
	$('#seq').val(seq);
	$('#form').attr('action', './pdf');
	$('#form').attr('target', '_blank');
	$('#form').submit();
}