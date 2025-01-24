<%@page import="utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/core/common/import.jsp"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link href="./core/top/top.css?t=<%=df.format(date)%>" rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<script type="text/javascript" src="./core/top/top.js?t=<%=df.format(date)%>"></script>
</head>

<body>

	<div class="wrapper">

		<%@ include file="/core/common/header.jsp"%>

		<main>

			<div class="container">

				<div class="top-container">



					<% if ("1".equals(role_type)) { %>
					<div class="container-btn-area container-btn-area-1">
						<button type="button" class="btn main" id="" onclick="to_store_mgmt()">加盟店管理</button>
						<button type="button" class="btn main" id="" onclick="to_vehicle_mgmt()">車両管理</button>
						<button type="button" class="btn main" id="" onclick="to_contracts()">契約管理</button>
						<button type="button" class="btn main" id="" onclick="to_staff_mgmt()">スタッフID管理</button>
						<button type="button" class="btn main" id="" onclick="to_inquiry()">問い合わせ管理</button>
						<button type="button" class="btn main" id="" onclick="to_announce_mgmt()">お知らせ管理</button>
					</div>
					<% } %>
					<% if ("2".equals(role_type)) { %>
					<div class="container-btn-area container-btn-area-2">
						<button type="button" class="btn main" id="" onclick="to_contracts()">契約管理</button>
						<button type="button" class="btn main" id="" onclick="to_mccs_vehicle_mgmt()">MCCS車両管理</button>
						<button type="button" class="btn main" id="" onclick="to_payment_detail_mgmt()">精算明細票管理</button>
						<button type="button" class="btn main" id="" onclick="to_inquiry()">お問い合わせ</button>
					</div>
					<% } %>
					<% if ("3".equals(role_type)) { %>
					<div class="container-btn-area container-btn-area-3">
						<button type="button" class="btn main" id="" onclick="to_contracts()">契約一覧</button>
						<button type="button" class="btn main" id="" onclick="to_inquiry()">お問い合わせ</button>
					</div>
					<% } %>

					<% if ("2".equals(role_type) || "3".equals(role_type)) { %>

					<div class="notification-container">
						<!-- お知らせ -->
						<div class="announcement-div contents-area">
							<div class="header-div bold">
								<img class="svg btn-svg-icon-black" src="./images/svg/bullhorn-solid.svg">お知らせ
							</div>
							<div class="announcement-item"></div>
							<div class="button-item button-open-div">
								<button class="load-more-btn btn">さらに表示する</button>
							</div>
						</div>

						<!-- 通知設定 -->
						<div class="notification-area">
							<div class="header-div bold">
								<img class="svg btn-svg-icon-black" src="./images/svg/gear-solid.svg"> 通知設定
							</div>
							<form class="" id="form" name="form" method="post">
								<div class="">
									<div class="item-block">

										<!-- 審査申込結果の通知 -->
										<div class="input-item">
											<div class="text-m bold">審査申込結果の通知</div>
											<div class="radio-container">
												<label>
													<input type="radio" class="radio-btn" id="notifyScreeningResult" name="notifyScreeningResult" value="1">
													通知する
												</label>
												<label>
													<input type="radio" class="radio-btn" id="notifyScreeningResult" name="notifyScreeningResult" value="0">
													通知しない
												</label>
											</div>
										</div>

										<!-- 本申込結果の通知 -->
										<div class="input-item">
											<div class="text-m bold">本申込結果の通知</div>
											<div class="radio-container">
												<label>
													<input type="radio" class="radio-btn" id="notifyMainResult" name="notifyMainResult" value="1">
													通知する
												</label>
												<label>
													<input type="radio" class="radio-btn" id="notifyMainResult" name="notifyMainResult" value="0">
													通知しない
												</label>
											</div>
										</div>

										<!-- 通知方法 -->
										<div class="input-item">
											<div class="text-m bold">通知方法</div>
											<div class="radio-container">
												<label>
													<input type="radio" class="radio-btn" id="notifyKbn" name="notifyKbn" value="1">
													SMS送信
												</label>
												<label>
													<input type="radio" class="radio-btn" id="notifyKbn" name="notifyKbn" value="2">
													メール送信
												</label>
											</div>
										</div>

										<!-- 携帯電話番号の編集 -->
										<div class="input-item">
											<div class="text-m bold">携帯電話番号の編集</div>
											<div class="input-tel">
												<input type="text" id="telNumber" name="telNumber" placeholder="携帯電話番号" val="">
											</div>
										</div>

										<!-- メールアドレスの編集 -->
										<div class="input-item">
											<div class="text-m bold">メールアドレスの編集</div>
											<div class="input-tel">
												<input type="text" id="mailAddress" name="mailAddress" placeholder="メールアドレス" val="">
											</div>
										</div>

										<!-- ボタン -->
										<div class="btn-container">
											<button type="button" class="btn main width-full" id="update-btn" disabled="disabled">保存</button>
										</div>

									</div>
								</div>

							</form>
						</div>
					</div>
					<% } %>

				</div>


			</div>

			<s:hidden id="def-notifyScreeningResult" />
			<s:hidden id="def-notifyMainResult" />
			<s:hidden id="def-notifyKbn" />
			<s:hidden id="def-telNumber" />
			<s:hidden id="def-mailAddress" />

		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
</body>
</html>
