<%@page import="utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/core/common/import.jsp"%>


<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
</head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link href="./core/staff-mgmt/staff-mgmt.css" rel="stylesheet">
<script src="./core/staff-mgmt/staff-mgmt.js"></script>
<html>
<body>
	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>

			<div class="title">
				<div class="title-container">
					<span class="title-item">スタッフID管理</span>
				</div>

				<div class="title-container filter-container">
					<div class="filter-area open-dev">
						<div class="filter-block">
							<div class="filter-header-block">
								<span class="filter-opentext"> フィルター設定 </span>
								<span class="open-icon close"></span>
							</div>
							<div class="open-target" style="display: none;">
								<div class="filter-body-block">
									<div class="filter-item-row">
										<div class="filter-item">
											<div class="filter-text">スタッフ名</div>
											<input type="text" class="filter-input" id="conditionStaffName">
										</div>
										<div class="filter-item">
											<div class="filter-text">スタッフID</div>
											<input type="text" class="filter-input" id="conditionStaffId">
										</div>
									</div>
								</div>
								<div class="filter-footer-block">
									<button type="button" id="filter-clear" class="half-btn sub">
										<div class="solid-icon">
											<div class="edit-icon"></div>
										</div>
										<span class="">条件クリア</span>
									</button>
									<button type="button" class="half-btn main">
										<span class="">実行</span>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>

			<div id="display-set" style="display: none;">

				<div class="container">
					<!-- 		<form class="form" name="form" method="post"> -->

					<div class="list-container">

						<div class="open-contents-area open-dev">
							<div class="filter-block new-regist-block">
								<div class="accordion-open-head-block">
									<span class="accordion-open-text"> ＋スタッフの新規登録 </span>
									<span class="open-icon close"></span>
								</div>
								<div class="accordion-open-block open-target" style="display: none;">

									<div class="accordion-open-body-block">

										<div class="input-item">
											<input type="text" class="" placeholder="アカウントID" id="accountId">
										</div>

										<div class="password-item">
											<div class="password-flex-block">
												<div class="password-block">
													<input type="password" class="inputpassword" id="newPassword" placeholder="パスワード" oninput="checkPassword(this)" />
													<i class="fa fa-eye-slash passwordIcon"></i>
												</div>
												<span class="">
													<button type="button" class="btn" id="makePassword">パスワード生成</button>
												</span>
											</div>
											<div class="password-strength-item">
												<div class="password-strength-bar-item">
													<div id="password-strength-bar" class="strength-bar"></div>
												</div>
												<p id="password-strength-msg" class="strength-msg">脆弱</p>
											</div>
										</div>



										<div class="title-detail text-s">
											パスワードには、数字・記号・半角<br />英語の小文字・半角英語の大文字を<br />それぞれ1つ入れてください。
										</div>
										<div class="input-item">
											<select class="dropdown" id="newIsAdmin">
												<option value="" disabled selected>権限を選択</option>
												<option value="1">システム管理者</option>
												<option value="2">スタッフ</option>
											</select>
										</div>


									</div>
									<div class="accordion-open-footer-block">
										<button type="button" class="btn main" id="newRegistBtn">新規登録</button>
									</div>
								</div>
							</div>
						</div>

						<div class="list-area">
							<%-- 					<%@ include file="/core/common/paging.jsp"%> --%>

							<div class="list-header-block">
								<div class="list-header-cnt">
									<span id="list-counts">9999</span>
								</div>
								<div class="list-header-cnt-text">
									<span>件</span>
								</div>
								<div class="list-header-sort-text">
									<span>並び替え</span>
								</div>
								<div class="list-header-sort-column">
									<select class="dropdown" id="listHeaderSortColumn">
										<option value=""></option>
										<option value="UserId_num">管理者ID</option>
										<option value="UserName_str">担当者名</option>
									</select>
								</div>
								<div class="list-header-sort-order">
									<select class="dropdown" id="listHeaderSortOrder">
										<option value="1">降順</option>
										<option value="2">昇順</option>
									</select>
								</div>
							</div>


							<div id="list_table" class="">
								<div id="list_body" class="list-body"></div>
							</div>



							<div id="list_template" style="display: none">
								<div id="list_body_template">
									<div class="list-card">
										<input type="hidden" class="list-hid-userId" />

										<div class="list-body-row">
											<div class="list-item-row list-staff-info">
												<div class="list-item-block-row">
													<div>
														<span class="text-s list-userNameTitle">担当者名:</span>
														<span class="list-userName text-l"></span>
													</div>
													<div class="staff-id">
														<span class="text-s">ID:</span>
														<span class="list-userId text-s"></span>
													</div>
												</div>
												<div class="list-item-block-row">
													<div class="">
														<span class="text-s list-title">権限:</span>
														<span class="list-adminName text-m"></span>
													</div>
												</div>
											</div>
											<div class="list-item-row">
												<div class="list-btn">
													<div class="list-password">
														<button type="button" class="half-btn main list-btn-item color-green" onclick="showInputField(this)">
															<!-- 														<i class="fa-solid fa-gear"></i> -->
															パスワード変更
														</button>

														<div class="password-item" style="display: none;">
															<div class="password-flex-block input-searchable">
																<div class="password-block">
																	<input type="password" class="inputpassword half-input" placeholder="パスワード" oninput="checkPassword(this)" />
																	<i class="fa fa-eye-slash passwordIcon listPasswordIcon"></i>
																</div>
																<button class="half-btn main password-save">保存</button>
															</div>
															<div class="password-strength-item">
																<div class="password-strength-bar-item">
																	<div class="strength-bar"></div>
																</div>
																<p id="password-strength-msg" class="strength-msg">脆弱</p>
															</div>

															<!-- 														<div class="title-detail text-s"> -->
															<!-- 															パスワードには、数字・記号・半角<br />英語の小文字・半角英語の大文字を<br />それぞれ1つ入れてください。 -->
															<!-- 														/div> -->
														</div>


													</div>
													<div class="list-delete">
														<button type="button" class="half-btn main list-btn-item color-red" onclick="confirmDelete(this)">
															<!-- 														 <i class="fa-regular fa-trash-can"></i> -->
															アカウント削除
														</button>
													</div>
												</div>
											</div>
										</div>
										<div class="list-body-info-row">
											<span class="info-title text-s">最終ログイン日:</span>
											<span class="list-lastLoginTime text-s"></span>
										</div>

									</div>
								</div>
							</div>


							<%@ include file="/core/common/paging.jsp"%>

						</div>








						<!-- 新規登録完了ダイアログ -->
						<div id="commitNewRegOverlay" style="display: none;"></div>
						<div id="commitNewRegDialog" style="display: none;">
							<p>新規登録が完了しました</p>
							<button type="button" class="yesBtn" id="commitNewRegBtn">OK</button>
						</div>
						<!-- 新規登録失敗ダイアログ -->
						<div id="notCommitNewRegOverlay" style="display: none;"></div>
						<div id="notCommitNewRegDialog" style="display: none;">
							<p>新規登録が失敗しました</p>
							<button type="button" class="yesBtn" id="commitNewRegBtn">OK</button>
						</div>
						<!-- パスワード変更完了ダイアログ -->
						<div id="commitNewPassOverlay" style="display: none;"></div>
						<div id="commitNewPassDialog" style="display: none;">
							<p>パスワード変更が完了しました</p>
							<button type="button" class="yesBtn" id="commitNewPassBtn">OK</button>
						</div>
						<!-- パスワード変更失敗ダイアログ -->
						<div id="notCommitNewPassOverlay" style="display: none;"></div>
						<div id="notCommitNewPassDialog" style="display: none;">
							<p>パスワード変更が失敗しました</p>
							<button type="button" class="yesBtn" id="commitNewRegBtn">閉じる</button>
						</div>

						<!-- パスワード変更ダイアログ -->
						<div id="changeOverlay" style="display: none;"></div>
						<div id="changeDialog" style="display: none;">
							<p>パスワード変更してもよろしいでしょうか？</p>
							<button type="button" class="noBtn" id="cancelChangeBtn">キャンセル</button>
							<button type="button" class="yesBtn" id="confirmChangeBtn">OK</button>
						</div>

						<!-- アカウント削除確認ダイアログ -->
						<div id="deleteOverlay" style="display: none;"></div>
						<div id="deleteDialog" style="display: none;">
							<p>本当にアカウントを削除しますか？</p>
							<button type="button" class="noBtn" id="cancelDeleteBtn">キャンセル</button>
							<button type="button" class="yesBtn" id="confirmDeleteBtn">OK</button>
						</div>
						<!-- 入力エラーダイアログ -->
						<div id="checkInputOverlay" style="display: none;"></div>
						<div id="checkInputDialog" style="display: none;">
							<p>入力欄を埋めてください</p>
							<button type="button" class="yesBtn" id="checkInputBtn">OK</button>
						</div>
						<!-- 入力エラーダイアログ -->
						<div id="passErrorOverlay" style="display: none;"></div>
						<div id="passErrorDialog" style="display: none;">
							<p>パスワードの強度が足りません</p>
							<button type="button" class="yesBtn" id="passErrorBtn">OK</button>
						</div>


						<!-- 行数設定ダイアログ -->
						<!-- 					<div id="rowsOverlay" style="display: none;" onclick="closeRowsDialog()"></div> -->
						<!-- 					<div id="rowsDialog" style="display: none;"> -->
						<!-- 						<p>表示件数</p> -->
						<!-- 						<div class="radio-container"> -->
						<!-- 							<label><input type="radio" name="rows" value="1"> 1件</label> <label><input type="radio" name="rows" value="2"> 2件</label> <label><input type="radio" name="rows" value="3"> -->
						<!-- 								3件</label><br /> -->
						<!-- 						</div> -->
						<!-- 						<button type="button" class="yesBtn" onclick="setRows()">設定</button> -->
						<!-- 						<button type="button" class="noBtn" onclick="closeRowsDialog()">キャンセル</button> -->
						<!-- 					</div> -->

					</div>
					<!-- 		</form> -->
				</div>
			</div>
		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
</body>
</html>