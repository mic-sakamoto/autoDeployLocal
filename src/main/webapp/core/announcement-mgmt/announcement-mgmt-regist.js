$(document).ready(function() {
	
	//パラメータにseqがある場合は更新のため情報取得
	chechParam();
	
	//「本文」文字カウント
	$(document).on('input','#bodyTextArea',function(event){bodyTextCount();});
	
	//「戻る」ボタン押下
	$(document).on('click','#backPage-btn',function(event){backPage();});
	
});

function chechParam(){
	// 現在のURLから取得
	const params = new URLSearchParams(window.location.search);

	// 特定のパラメータを取得
	const seq = params.get('seq');
	
	if(seq != null && seq != ""){
		searchList(seq);
	}
}


function bodyTextCount(){
	// 本文のテキストエリア
    const bodyTextArea = document.getElementById('bodyTextArea');
    const textCount = document.getElementById('textCount');

	const count = bodyTextArea.value.length; // 文字数を取得
	textCount.textContent = `${count} / XXX`; // 文字数を表示
}

/*
 * リスト検索
 *
 * */
let List = [];
function searchList(seq) {

	$('#list_div').show();
	var postData = {};
	postData['seq'] = seq;	
	doAjax(postData, "searchList", "./announcement-mgmt").then(function(data) {
		$('#openDate').val(data.result[0].OpenDate);
		$('#openTime').val(data.result[0].OpenTime);
		$('#closeDate').val(data.result[0].CloseDate);
		$('#closeTime').val(data.result[0].CloseTime);
		$('#bodyTextArea').val(data.result[0].Body);
		bodyTextCount();
	});

}

/*
 * 表示設定
 */
function openRowsDialog(kbn) {
	if($("#closedDisplaySetting1").val()=="1"){
		if(kbn==1){
			$("#rowsOverlay1").css('display' , 'block');
			$("#rowsDialog1").css('display' , 'block');
			$("#closedDisplaySetting1").val("0");
		}else{
			$("#rowsOverlay2").css('display' , 'block');
			$("#rowsDialog2").css('display' , 'block');
			$("#closedDisplaySetting2").val("0");
		}
	}else{
		if(kbn==1){
			$("#rowsOverlay1").css('display' , 'none');
			$("#rowsDialog1").css('display' , 'none');
			$("#closedDisplaySetting1").val("1");
		}else{
			$("#rowsOverlay2").css('display' , 'none');
			$("#rowsDialog2").css('display' , 'none');
			$("#closedDisplaySetting2").val("1");
		}
	}
}

/*
 * 戻る
 *
 * */
function backPage() {	
	var postData = {};
	doPost(postData, "backPage", "./announcement-mgmt").then(function(data) {
		
	});
}

