<%@page import="utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/core/common/import.jsp"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link href="./core/inquiry-mgmt/inquiry-mgmt-regist.css?t=<%=df.format(date)%>" rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<script type="text/javascript" src="./core/inquiry-mgmt/inquiry-mgmt-regist.js?t=<%=df.format(date)%>"></script>
</head>

<body>
	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>
			<div class="title">
				<div class="title-container">
					<span class="title-item">お問い合わせ／ご連絡</span>
				</div>
			</div>
			
			<div id="display-set" style="display:none;">
	
			<div class="container">
			
				<div class="input-container">
					
					<div class="contents-area">
						<span class="text-m" id="inquiryMessage"></span>
					</div>
					<form id="form" name="form" class="inquiry-form" style="display:none;">
						<div class="contents-area">
							<div class="item-block">
								<!-- 宛先設定 -->
								<div class="input-item">
									<div class="text-m bold">宛先設定</div>
									<div class="input-searchable">
										<input type="text" class="" placeholder="契約番号からIDを検索" id="" />
										<button class="btn main id-search-btn" id="">検索</button>
									</div>
									<div class="radio-container">
										<label>
											<input type="radio" name="radio-seibetsu" class="radio-seibetsu" value="1" />
											加盟店ID：XX様
										</label>
										<label>
											<input type="radio" name="radio-seibetsu" class="radio-seibetsu" value="2" />
											顧客ID：XX様
										</label>
									</div>
								</div>
	
								<div class="input-item">
									<div class="body-area-div">
										<textarea class="body-area" id="bodyTextArea" name="" value="" placeholder="XXX文字まで"></textarea>
										<div class="body-note">
											<div class="text-left text-s">ファイル添付は画像形式（jpeg、gif、png、bmp）もしくはpdfファイル、一度に2ファイルまでです。上限10MBまで。</div>
											<div class="textcount text-right text-s" id="textCount">0 / XXX</div>
										</div>
									</div>
								</div>
								
								<!-- ボタン -->
								<div class="btn-container">
									<button type="button" class="btn main width-full" id="registBtn">送信</button>
								</div>
							</div>
						</div>
					</form>
					
					<div class="contents-area" id="inquiry-data-area" style="display:none;">
						<div class="item-block">
							<div id="inquiry-data"></div>
						</div>
					</div>
				</div>
			
			</div>
		
		</div>
		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
	<input type="hidden" id="text_inquiryMessage_0" value="メッセージは1度限りお送りいただくことが可能です。">
	<input type="hidden" id="text_inquiryMessage_1" value="追加のお問い合わせは、新規メッセージ作成からお願い致します。">
</body>
</html>
