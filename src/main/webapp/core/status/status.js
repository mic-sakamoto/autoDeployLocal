$(document).ready(function() {
	$("#display-set").css('display', 'block');
});
$(function() {
	$('.accordion-open-head-block').click(function() { openForm(this) });

	$('#reScreeningAppBtn').click(function() { toReScreeningApplication() });
	$('#mainAppBtn').click(function() { toMainApplication() });
	$('#appCancelBtn').click(function() { openForm(this) });

	$('#pdfUSSAutoLoneBtn').click(function() { pdfUSSAutoLone() });
	$('#pdfKozaHenkoTodokeBtn').click(function() { pdfKozaHenkoTodoke() });
	$('#pdfMCCSAutoLoneBtn').click(function() { pdfMCCSAutoLone() });
	$('#pdfLoneTorokuIraishoBtn').click(function() { pdfLoneTorokuIraisho() });
	$('#pdfSohujoKenSeikyushoBtn').click(function() { pdfSohujoKenSeikyusho() });
	$('#pdfTorokuzikoHenkoIraishoBtn').click(function() { pdfTorokuzikoHenkoIraisho() });
	$('#pdfShiharaiYoteihyoBtn').click(function() { pdfShiharaiYoteihyo() });



});


function toReScreeningApplication() {
	postData = {};
	postData['contractId'] = $('#contractId').val();
	postData['webAppId'] = $('#webAppid').val();
	postData['statusKbn'] = $('#status').val();
	if ($('#status').val() == '7') {
		// 不備返却の時は2
		postData['appKbn'] = '2';
	} else {
		postData['appKbn'] = '3';
	}

	doPostWithData(postData, "application", "./application");
}

function toMainApplication() {
	postData = {};
	postData['contractId'] = $('#contractId').val();
	postData['webAppId'] = $('#webAppid').val();
	postData['appKbn'] = '4';
	postData['statusKbn'] = $('#status').val();
	doPostWithData(postData, "application", "./application");
}

function selectInputMethdoModal() {
	$('.select-input-method-modal').addClass('active');
}

function pdfUSSAutoLone() {
	var postData = {};
	postData['pdfName'] = 'USSAutoLone';
	doPostWithData(postData, "downloadPdf", "./pdf");
}
function pdfKozaHenkoTodoke() {
	var postData = {};
	postData['pdfName'] = 'KozaHenkoTodoke';
	doPostWithData(postData, "downloadPdf", "./pdf");
}
function pdfMCCSAutoLone() {
	var postData = {};
	postData['pdfName'] = 'MCCSAutoLone';
	doPostWithData(postData, "downloadJasper", "./pdf");
}
function pdfLoneTorokuIraisho() {
	var postData = {};
	postData['pdfName'] = 'LoneTorokuIraisho';
	doPostWithData(postData, "downloadJasper", "./pdf");
}
function pdfSohujoKenSeikyusho() {
	var postData = {};
	postData['pdfName'] = 'SohujoKenSeikyusho';
	doPostWithData(postData, "downloadPdf", "./pdf");
}
function pdfTorokuzikoHenkoIraisho() {
	var postData = {};
	postData['pdfName'] = 'TorokuzikoHenkoIraisho';
	doPostWithData(postData, "downloadPdf", "./pdf");
}
function pdfShiharaiYoteihyo() {
	var postData = {};
	postData['pdfName'] = 'ShiharaiYoteihyo';
	doPostWithData(postData, "downloadJasper", "./pdf");
}

function openForm(obj) {
	var target = $(obj).closest('.open-dev').find('.open-target');
	var obj2 = $(obj).closest('.open-dev').find('.open-icon');
	//	alert($(obj2).hasClass('close'))

	if ($(obj2).hasClass('open')) {
		$(obj2).removeClass('open');
		$(obj2).addClass('close');
		$(target).hide();

		$(obj2).closest('.filter-block').removeClass('active');

	} else {
		$(obj2).removeClass('close');
		$(obj2).addClass('open');
		$(target).show();

		$(obj2).closest('.filter-block').addClass('active');
	}
}