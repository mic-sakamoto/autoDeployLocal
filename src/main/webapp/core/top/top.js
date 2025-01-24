$(document).ready(function() {
	
	// 画面表示制御
	getDisplayAuthority();
	
    // お知らせ情報取得
    getAnnouncementInfo();
	
	// 通知設定情報取得
	getUserInfo();
	
	// 保存ボタン制御
	$(document).on('click','.radio-btn',function(event){checkUpdate();});
	$(document).on('blur','#telNumber',function(event){
		setHankaku(this);
		removeHyphen(this);
		checkUpdate();
	});
	$(document).on('blur','#mailAddress',function(event){
		setHankaku(this);
		checkUpdate();
	});
	
	// 保存ボタン押下
	$(document).on('click','#update-btn',function(event){updateSetting();});
	
	// 全てのinputタグでEnterキーの動作を無効化
	$('input').on('keypress', function (event) {
	    if (event.key === 'Enter') {
	      event.preventDefault(); // Enterキーでのフォーム送信を防止
	    }
	});
});

function getDisplayAuthority(){
	var postData = gatherInputs("#form", true);
	    doAjax(postData, "getDisplayAuthority", "./top").then(function(data) {
			setDisplay(data);
	    });
}

function getAnnouncementInfo() {
    var postData = gatherInputs("#form", true);
    doAjax(postData, "getAnnouncementInfo", "./top").then(function(data) {
		setAnnouncementList(data.result);
    });
}

function getUserInfo() {
    var postData = gatherInputs("#form", true);
    doAjax(postData, "getUserInfo", "./top").then(function(data) {
		if (data.notifyScreeningResult == "true") {
            $("input[name='notifyScreeningResult'][value='1']").prop('checked', true);
			$('#def-notifyScreeningResult').val("1");
        } else {
            $("input[name='notifyScreeningResult'][value='0']").prop('checked', true);
			$('#def-notifyScreeningResult').val("0");
        }
		if (data.notifyMainResult == "true") {
            $("input[name='notifyMainResult'][value='1']").prop('checked', true);
			$('#def-notifyMainResult').val("1");
		} else {
            $("input[name='notifyMainResult'][value='0']").prop('checked', true);
			$('#def-notifyMainResult').val("0");
        }
		if (data.notifyKbn == "1") {
            $("input[name='notifyKbn'][value='1']").prop('checked', true);
			$('#def-notifyKbn').val("1");
        } else {
            $("input[name='notifyKbn'][value='2']").prop('checked', true);
			$('#def-notifyKbn').val("2");
        }
		$("#telNumber").val(data.telNumber);
		$("#def-telNumber").val(data.telNumber);
		$("#mailAddress").val(data.mailAddress);
		$("#def-mailAddress").val(data.mailAddress);
		
		
    });
}

function setDisplay(data){
	if(data.displayAuthority == 1){
		$("#display-set").css('display' , 'block');
	}else{
		$("#display-set").css('display' , 'none');
	}
}

function setAnnouncementList(data) {
    if (data != null) {
        const listContainer = $('.announcement-div');
        const loadMoreBtn = $('.load-more-btn');
        let currentDisplayCount = 5;
        let html = '';

        // 最初の5件を表示
        html = renderList(data, 0, currentDisplayCount);
        listContainer.find('.announcement-item').remove();
        listContainer.find('.button-item').before(html);
		
		// 5件以下の場合は「さらに表示する」を非表示
        if (data.length <= 5) {
            $(".button-open-div").css('display', 'none');
        } else {
            $(".button-open-div").css('display', 'block'); // 5件以上の場合は表示
        }

        // 「さらに表示する」ボタン押下
        loadMoreBtn.off('click').on('click', function () {
            const nextDisplayCount = currentDisplayCount + 5;
            if (nextDisplayCount < data.length) {
                html = renderList(data, currentDisplayCount, nextDisplayCount);
                currentDisplayCount = nextDisplayCount;
            } else {
                // 「さらに表示する」を非表示
                html = renderList(data, currentDisplayCount, data.length);
                $(".button-open-div").css('display' , 'none');
            }
            listContainer.find('.button-item').before(html);
        });
    }
}

function renderList(data, startIndex, endIndex) {
    var html = '';
    $.each(data.slice(startIndex, endIndex), function (i, row) {
        const isFirstItem = (startIndex + i === 0);
        const openDate = row.OpenDate;

        const urlMatch = row.Body.match(/(https?:\/\/[^\s]+)/);
        let bodyContent = '';

        if (urlMatch) {
            const url = urlMatch[0];
            const textWithoutUrl = row.Body.replace(url, '').trim();
            bodyContent = `<a href="${url}" target="_blank" style="color:#0d6efd;">${textWithoutUrl}</a>`;
        } else {
            bodyContent = row.Body.replace(/\n/g, '<br>');
        }

        html += '<div class="announcement-item">';
        html += '    <div class="announcement-header">';
        html += '        <div class="openDate">';
        html += '            <span>' + openDate + '</span>';
        html += '        </div>';
        html += '        <div class="body-content">';
        if (isFirstItem) {
            html += '            <p>' + bodyContent + '</p>';
        } else {
            html += '            <p class="ellipsis-text">' + bodyContent + '</p>';
        }
        html += '        </div>';
        html += '        <div class="set-icon">';
        if (isFirstItem) {
            html += '            <span class="open-icon" onclick="openNews(this)">▲</span>';
        } else {
            html += '            <span class="open-icon" onclick="openNews(this)">▼</span>';
        }
        html += '        </div>';
        html += '    </div>';
        html += '</div>';
    });
    return html;
}



function openNews(element) {
    const bodyContentDiv = $(element).closest('.announcement-header').find('.body-content p');

    if ($(element).text() === '▼') {
        bodyContentDiv.removeClass('ellipsis-text'); 
        $(element).text('▲');
    } 
    else {
        bodyContentDiv.addClass('ellipsis-text');
        $(element).text('▼');
    }
}

function checkUpdate(){
	var updateFlg = false;
	
	if($("input[name='notifyScreeningResult']:checked").val() != $('#def-notifyScreeningResult').val()){
		updateFlg = true;
	}
	if($("input[name='notifyMainResult']:checked").val() != $('#def-notifyMainResult').val()){
		updateFlg = true;
	}
	if($("input[name='notifyKbn']:checked").val() != $('#def-notifyKbn').val()){
		updateFlg = true;
	}
	
	if($("#telNumber").val() != $('#def-telNumber').val()){
		updateFlg = true;
	}
	if($("#mailAddress").val() != $('#def-mailAddress').val()){
		updateFlg = true;
	}
	
	if(updateFlg){
		$('#update-btn').prop('disabled', false);
	}else{
		$('#update-btn').prop('disabled', true);
	}
}

function updateSetting() {
	event.preventDefault();
	var postData = gatherInputs("#form", true);
	doAjax(postData, "updateSetting", "./top").then(function(data) {
	});
}

