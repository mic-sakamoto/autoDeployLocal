<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/core/common/import.jsp"%>
<!--ページ専用js-->

<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
</head>
<link href="./core/loan-simulation/loan-simulation.css?t=<%=df.format(date)%>" rel="stylesheet">
<script src="./core/loan-simulation/loan-simulation.js?t=<%=df.format(date)%>"></script>

<html>
<body>
	<div class="wrapper">

		<%@ include file="/core/common/header.jsp"%>

		<main>

			<div class="title">
				<div class="title-container">
					<span class="title-item">ローンシミュレーション</span>
				</div>
			</div>

			<div class="container">
				<form id="ls-form" name="form" class="form ls" onsubmit="return false;">
					<div class="input-container">
						<div class="contents-area input-contents">

							<div class="input-form ls-input">
								<div class="item-block">
									<!-- 車両情報タイトル -->
									<div class="items-title text-l bold">車両情報</div>
									<!-- 新車・中古車 -->
									<div class="input-item">
										<div class="text-m bold item-title">新車・中古車</div>
										<div class="radio-container">
											<label>
												<input type="radio" class="to-app ls-req" name="i-UsedKbn" value="1" />
												新車
											</label>
											<label>
												<input type="radio" class="to-app ls-req" name="i-UsedKbn" value="0" />
												中古車
											</label>
										</div>
									</div>
									<div class="input-item">
										<div class="text-m bold item-title">初年度登録</div>
										<div class="grid-3">
											<select class="year ls-req" id="i-ShonendoYear">
											</select>
											<select class="month ls-req" id="i-ShonendoMonth">
											</select>
										</div>
									</div>

									<!-- 金額（税込）タイトル -->
									<div class="items-title text-l bold">金額（税込）</div>
									<!-- 車両本体価格 -->
									<div class="input-item">
										<div class="text-m bold item-title">車両本体価格</div>
										<div class=" text-with-unit">
											<input type="text" class="price cal-total-item to-app ls-req" id="i-CarPrice" />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- Connected(MCCS)申込 -->
									<div class="input-item">
										<div class="text-m bold item-title">Connected(MCCS)申込</div>
										<div class="radio-container">
											<label>
												<input type="radio" class="ls-req" name="i-MccsMoshikomiKbn" value="1" checked />
												あり
											</label>
											<label>
												<input type="radio" class="ls-req" name="i-MccsMoshikomiKbn" value="0" />
												なし
											</label>
										</div>
									</div>
									<!-- MCCS取付 -->
									<div class="input-item">
										<div class="text-m bold item-title">MCCS取付</div>
										<div class="text-s">MCCS取付ありの場合に自動で設定されます。</div>
										<div class=" text-with-unit">
											<input type="text" class="price read-only cal-total-item ls-req" id="i-MccsAttachPrice" value="88,000" />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- 付属品 -->
									<div class="input-item">
										<div class="text-m bold item-title">付属品</div>
										<div class=" text-with-unit">
											<input type="text" class="price cal-total-item cal-sub-item to-app ls-req" id="i-FuzokuhinPrice" />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- 諸費用 -->
									<div class="input-item">
										<div class="text-m bold item-title">諸費用</div>
										<div class=" text-with-unit">
											<input type="text" class="price cal-total-item cal-sub-item to-app ls-req" id="i-OtherPrice" />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- 車検・整備費用 -->
									<div class="input-item">
										<div class="text-m bold item-title">車検・整備費用</div>
										<div class=" text-with-unit">
											<input type="text" class="price cal-total-item cal-sub-item to-app ls-req" id="i-ShakenPrice" />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- ①現金合計額 -->
									<div class="input-item">
										<div class="text-m bold item-title">①現金合計額</div>
										<div class="text-s">各金額を入力すると自動計算されます。</div>
										<div class=" text-with-unit">
											<input type="text" class="price read-only" id="i-TotalPrice" disabled />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- ②頭金 -->
									<div class="input-item">
										<div class="text-m bold item-title">②頭金</div>
										<div class="text-s">現金（お申込金)</div>
										<div class="">
											<div class=" text-with-unit">
												<input type="text" class="price cal-atamakin-item to-app ls-req" id="i-AppPrice" />
												<span class="unit">円</span>
											</div>
										</div>
										<div class="text-s">下取車充当額</div>
										<div class="">
											<div class=" text-with-unit">
												<input type="text" class="price cal-atamakin-item to-app ls-req" id="i-ShitadoriPrice" />
												<span class="unit">円</span>
											</div>
										</div>
										<input type="hidden" class="to-app" id="i-AtamaPrice">
									</div>
									<!-- ③残金 -->
									<div class="input-item">
										<div class="text-m bold item-title">③残金（①-②）</div>
										<div class="text-s">各金額を入力すると自動計算されます。</div>
										<div class=" text-with-unit">
											<input type="text" class="price read-only" id="i-RemainPrice" disabled />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- お支払い期間タイトル -->
									<div class="items-title text-l bold">お支払い期間</div>
									<!-- お支払期間 -->
									<div class="input-item">
										<div class="text-m bold item-title">お支払期間</div>
										<div class="text-s">契約締結時にお支払期間が決定されます。</div>
										<div class="text-s">開始</div>
										<div class=" grid-3">
											<select class="read-only price" name="i-ShiharaiStartYear" disabled class="year">
											</select>
											<select class="read-only price" name="i-ShiharaiStartMonth" disabled class="month">
											</select>
										</div>
										<div class="text-s">終了</div>
										<div class=" grid-3">
											<select class="read-only price" name="i-ShiharaiEndYear" disabled class="year">
											</select>
											<select class="read-only price" name="i-ShiharaiEndMonth" disabled class="month">
											</select>
										</div>
									</div>
									<!-- お支払回数 -->
									<div class="input-item">
										<div class="text-m bold item-title">お支払回数</div>
										<div class="text-s">お支払回数は年単位で指定可能です。</div>
										<div class="">
											<select id="i-ShiharaiKaisu" class="select-kbn width-def kasanShiharaiCheck ls-req to-app">
												<option value="" selected>選択してください</option>
												<option value="12">12回</option>
												<option value="24">24回</option>
												<option value="36">36回</option>
												<option value="48">48回</option>
												<option value="60">60回</option>
												<option value="72">72回</option>
												<option value="84">84回</option>
											</select>
										</div>
									</div>
									<!-- 口座振替日 -->
									<div class="input-item">
										<div class="text-m bold item-title">口座振替日 毎月27日</div>
										<div class="text-s">預金口座へのご入金は、振替日の前日26日までにお願いいたします。</div>
									</div>
									<!-- ボーナス加算月 -->
									<div class="input-item">
										<div class="text-m bold item-title">ボーナス加算月</div>
										<div class="radio-container">
											<label class="">
												<input type="radio" class="kasanShiharaiCheck ls-req to-app" name="i-BonusKasanMonthKbn" value="91" />
												する（毎年8月・12月）
											</label>
											<label class="">
												<input type="radio" class="kasanShiharaiCheck ls-req to-app" name="i-BonusKasanMonthKbn" value="90" />
												しない
											</label>
										</div>
									</div>
									<!-- 加算支払金 -->
									<div class="input-item">
										<div class="text-m bold item-title">加算支払金</div>
										<div class="text-s">回数</div>
										<div class="">
											<div class=" text-with-unit">
												<input type="text" class="num to-app" id="i-BonusKasanKaisu" disabled />
												<span class="unit">回</span>
											</div>
										</div>
										<div class="text-s">金額</div>
										<div class="">
											<div class=" text-with-unit">
												<input type="text" class="price to-app" id="i-BonusKasanPrice" disabled />
												<span class="unit">円</span>
											</div>
										</div>
									</div>
									<!-- 計算ボタン -->
									<div class="btn-container">
										<button type="button" id="" class="btn main ls-btn exec-ls" disabled>計算する</button>
										<button type="button" id="" class="btn sub reset-btn">入力内容をリセットする</button>
									</div>
									<div class="ls-err-msg hide">
										<i class="fa-solid fa-triangle-exclamation"></i>
										<span class="err-msg-body" id="errMsg"></span>
									</div>
								</div>
							</div>
						</div>
						<div class="contents-area input-contents">
							<div class="input-form">
								<div class="item-block">
									<!-- エラーメッセージ -->

									<!-- お支払金額タイトル -->
									<div class="items-title text-l bold">お支払金額</div>
									<!-- 第１回分割支払金 -->
									<div class="input-item">
										<div class="text-m bold item-title">第１回分割支払金</div>
										<div class="text-s">試算情報が設定されます編集できません。</div>
										<div class=" text-with-unit">
											<input type="text" class="read-only" id="i-BunkatsuShiharai1" disabled />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- 第２回以降分割支払金 -->
									<!--									<div class="input-item">-->
									<!--										<div class="text-m bold item-title">第２回以降分割支払金</div>-->
									<!--										<div class=" text-with-unit">-->
									<!--											<input type="text" class="read-only" id="i-BunkatsuShiharai2" disabled />-->
									<!--											<span class="unit">円</span>-->
									<!--										</div>-->
									<!--									</div>-->
									<!-- 回数 -->
									<div class="input-item">
										<div class="text-m bold item-title">回数</div>
										<div class=" text-with-unit">
											<input type="text" class="read-only" id="i-BunkatsuKaisu" disabled />
											<span class="unit">回</span>
										</div>
									</div>
									<!-- ④分割払手数料 -->
									<div class="input-item">
										<div class="text-m bold item-title">④分割払手数料</div>
										<div class=" text-with-unit">
											<input type="text" class="read-only" id="i-BunkatsuTesuryo" disabled />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- ⑤分割支払金合計（③＋④） -->
									<div class="input-item">
										<div class="text-m bold item-title">⑤分割支払金合計（③＋④）</div>
										<div class=" text-with-unit">
											<input type="text" class="read-only" id="i-BunkatsuShiharaiTotal" disabled />
											<span class="unit">円</span>
										</div>
									</div>
									<!-- ⑥お支払総額（②＋⑤） -->
									<div class="input-item">
										<div class="text-m bold item-title">⑥お支払総額（②＋⑤）</div>
										<div class=" text-with-unit">
											<input type="text" class="read-only" id="i-TotalShiharai" disabled />
											<span class="unit">円</span>
										</div>
									</div>
									<div class="btn-container">
										<button type="button" id="" class="btn main app-btn" disabled>算出結果をもとに審査申込する</button>
										<button type="button" id="" class="btn sub reset-btn">入力内容をリセットする</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>

			<%-- 審査申込内容入力方法選択モーダル --%>
			<div class="modal-container select-input-method-modal">
				<div class="modal-body">
					<div class="modal-content">
						<%@ include file="/core/select-input-method/select-input-method.jsp"%>
					</div>
				</div>
			</div>

		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
</body>
</html>