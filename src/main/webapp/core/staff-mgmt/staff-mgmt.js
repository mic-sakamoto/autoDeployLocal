let currentPage = 1;
let rowsPerPage = 10;
let List = [];


$(document).ready(function() {
	$("#display-set").css('display' , 'block');

	//新規
	searchList();
	//	$(document).on('click', '#toggleButton', function(event) { toggleForm(); });
	$(document).on('click', '#newRegistBtn', function(event) { chkNewRegistration(); });
	$(document).on('click', '#searchBtn', function(event) { searchList(); });
	//	$(document).on('click', `#accountDeleteBtn`, function(event) { toggleForm(); });
	


});
/*
 * 新規登録
 *
 * */

function chkNewRegistration() {

	var postData = gatherInputs("#myForm", true);
	postData["newpassword"] = $("#newPassword").val();
	postData["newuserId"] = $("#newUserId").val();
	postData["newisAdmin"] = $("#newIsAdmin").val();

	if ($("#newPassword").val() == "" | $("#newUserId").val() == "" | $("#newIsAdmin").val() == null) {
		console.log("err");
		document.getElementById('checkInputOverlay').style.display = 'block';
		document.getElementById('checkInputDialog').style.display = 'block';

		document.getElementById('checkInputBtn').onclick = function() {
			document.getElementById('checkInputOverlay').style.display = 'none';
			document.getElementById('checkInputDialog').style.display = 'none';
		};
	} else {
		let strength = 0;
		const hasNumber = /[0-9]/.test($("#newPassword").val());
		const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test($("#newPassword").val());
		const hasLower = /[a-z]/.test($("#newPassword").val());
		const hasUpper = /[A-Z]/.test($("#newPassword").val());

		if (hasNumber) strength++;
		if (hasSpecial) strength++;
		if (hasLower) strength++;
		if (hasUpper) strength++;

		if (strength == 4) {
			commitNewReg();
		} else {
			document.getElementById('passErrorOverlay').style.display = 'block';
			document.getElementById('passErrorDialog').style.display = 'block';
			document.getElementById('passErrorBtn').onclick = function() {
				document.getElementById('passErrorOverlay').style.display = 'none';
				document.getElementById('passErrorDialog').style.display = 'none';
			};
		}

	}
}

function commitNewReg() {

	var postData = gatherInputs("#myForm", true);
	postData["newpassword"] = $("#newPassword").val();
	postData["newuserId"] = $("#newUserId").val();
	postData["newisAdmin"] = $("#newIsAdmin").val();

	doAjax(postData, "newRegistration", "./staff-mgmt").then(function(data) {
		console.log("data" + JSON.stringify(data, null, 2));
		if (data.successFlg == 9) {

		} else {
			document.getElementById('commitNewRegOverlay').style.display = 'block';
			document.getElementById('commitNewRegDialog').style.display = 'block';
		}

	});
	document.getElementById('commitNewRegBtn').onclick = function() {
		document.getElementById('commitNewRegOverlay').style.display = 'none';
		document.getElementById('commitNewRegDialog').style.display = 'none';
	};
}
/*
 * テーブル表示
 *
 * */

function searchList() {
	var postData = {};

	postData["searchList"] = $("#searchList").val();
	console.log("postData" + JSON.stringify(postData, null, 2));

	doAjax(postData, "searchList", "./staff-mgmt").then(function(data) {
		listJson = data.searchList;
		tableReset();
		makeList();
//		
//		
//		List = data.searchList;
//		console.log("data" + JSON.stringify(List, null, 2));
//		displayTable(currentPage);
	});
}
function makeList() {
	
	listSetting();
	if (listJson.length > 0) {
		
		$('#list-counts').text(listJson.length);
		var html = "";		
		$.each(listJson, function(i, data) {
			if (i >= start && i < start + parseInt($('#viewNum').val())) {
				body_template.find('.list-hid-userId').val(data.UserId);
				body_template.find('.list-userId').html(escapeTag(data.UserId));
				body_template.find('.list-userName').html(escapeTag(data.UserName));
				body_template.find('.list-adminName').html(escapeTag(data.AdminType));
				body_template.find('.list-lastLoginTime').html(escapeTag(data.LastLoginTime));
				
				html = html + body_template.html();
			}
		});
		body.html(html);	
			
		makePager(listJson);
//		sortJson(listJson, $("#list_table"), 'str');

		$('.listPasswordIcon').click(function () { passwordIconChange(this) });

	} else {
		$('#list-counts').text(listJson.length);
	}
}



/*
 * パスワード変更
 */
function showInputField(obj) {
	
	$(obj).hide();
	$(obj).closest('.list-password').find('.password-item').show();
}
//function chkChangePass(id, pass) {
//
//	if (pass == "") {
//		document.getElementById('checkInputOverlay').style.display = 'block';
//		document.getElementById('checkInputDialog').style.display = 'block';
//
//		document.getElementById('checkInputBtn').onclick = function() {
//			document.getElementById('checkInputOverlay').style.display = 'none';
//			document.getElementById('checkInputDialog').style.display = 'none';
//		};
//	} else {
//		let strength = 0;
//		const hasNumber = /[0-9]/.test(pass);
//		const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(pass);
//		const hasLower = /[a-z]/.test(pass);
//		const hasUpper = /[A-Z]/.test(pass);
//
//		if (hasNumber) strength++;
//		if (hasSpecial) strength++;
//		if (hasLower) strength++;
//		if (hasUpper) strength++;
//
//		if (strength == 4) {
//			changePassword(id, pass);
//		} else {
//			document.getElementById('passErrorOverlay').style.display = 'block';
//			document.getElementById('passErrorDialog').style.display = 'block';
//
//			document.getElementById('passErrorBtn').onclick = function() {
//				document.getElementById('passErrorOverlay').style.display = 'none';
//				document.getElementById('passErrorDialog').style.display = 'none';
//			};
//		}
//
//	}
//}
//function changePassword(id, pass) {
//	console.log("id" + id);
//	document.getElementById('changeOverlay').style.display = 'block';
//	document.getElementById('changeDialog').style.display = 'block';
//
//	var postData = {};
//	postData["listUserId"] = id;
//	postData["updatePassword"] = pass;
//
//	document.getElementById('confirmChangeBtn').onclick = function() {
//		document.getElementById('changeOverlay').style.display = 'none';
//		document.getElementById('changeDialog').style.display = 'none';
//		doAjax(postData, "changePassword", "./staff-mgmt").then(function(data) {
//			console.log("data;" + data);
//			if (data.successFlg == 9) {
//
//			} else {
//				document.getElementById('commitNewPassOverlay').style.display = 'block';
//				document.getElementById('commitNewPassDialog').style.display = 'block';
//			}
//
//		});
//	};
//	document.getElementById('commitNewPassBtn').onclick = function() {
//		document.getElementById('commitNewPassOverlay').style.display = 'none';
//		document.getElementById('commitNewPassDialog').style.display = 'none';
//	};
//	document.getElementById('cancelChangeBtn').onclick = function() {
//		document.getElementById('changeOverlay').style.display = 'none';
//		document.getElementById('changeDialog').style.display = 'none';
//	};
//
//}

/*
 * 削除
 *
 * */

function confirmDelete(obj) {
	
	var userId = $(obj).closest('.list-card').find('.list-hid-userId').html();

	document.getElementById('deleteOverlay').style.display = 'block';
	document.getElementById('deleteDialog').style.display = 'block';

	document.getElementById('confirmDeleteBtn').onclick = function() {
		deleteAccount(id)
		document.getElementById('deleteOverlay').style.display = 'none';
		document.getElementById('deleteDialog').style.display = 'none';
	};

	document.getElementById('cancelDeleteBtn').onclick = function() {
		document.getElementById('deleteOverlay').style.display = 'none';
		document.getElementById('deleteDialog').style.display = 'none';
	};
}
function deleteAccount(id) {
	var postData = {};

	postData["listUserId"] = id;
	console.log("postData" + JSON.stringify(postData, null, 2));

	doAjax(postData, "deleteAccount", "./staff-mgmt").then(function(data) {
		if (data.successFlg == 9) {

		} else {
			searchList();
		}
	});
}


///*
// * テーブル用
// */
//function checkPasswordStrength(input) {
//	const strengthBar = input.closest('.password-container').querySelector('.password-strength-bar');
//	const messageElement = input.closest('.password-container').querySelector('.password-strength-msg');
//	const password = input.value;
//
//	let strength = 0;
//	const hasNumber = /[0-9]/.test(password);
//	const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(password);
//	const hasLower = /[a-z]/.test(password);
//	const hasUpper = /[A-Z]/.test(password);
//
//	if (hasNumber) strength++;
//	if (hasSpecial) strength++;
//	if (hasLower) strength++;
//	if (hasUpper) strength++;
//
//	// ストレングスに応じてバーの幅を設定
////	strengthBar.style.width = strength * 50 + 'px';
//
//$('#password-strength-bar').addClass('strength-bar-' + strength);
//
//	// メッセージを更新
//	if (strength === 2) {
//		messageElement.innerHTML = "弱";
//	} else if (strength === 3) {
//		messageElement.innerHTML = "中";
//	} else if (strength === 4) {
//		messageElement.innerHTML = "強";
//	} else {
//		messageElement.innerHTML = "脆弱";
//	}
//}


///*
// * タイトルソート
// */
//function sortTable(columnIndex) {
//	const table = document.getElementById("customerTable");
//	const tbody = table.tBodies[0];
//	const rows = Array.from(tbody.rows);
//	const isAscending = tbody.dataset.sortOrder === 'asc';
//
//	// ソート
//	rows.sort((a, b) => {
//		const cellA = a.cells[columnIndex].textContent.trim();
//		const cellB = b.cells[columnIndex].textContent.trim();
//
//		if (cellA < cellB) return isAscending ? -1 : 1;
//		if (cellA > cellB) return isAscending ? 1 : -1;
//		return 0;
//	});
//
//	rows.forEach(row => tbody.appendChild(row));
//
//	tbody.dataset.sortOrder = isAscending ? 'desc' : 'asc';
//}

///*
// * 新規登録欄の表示非表示
// */
//
//function toggleForm() {
//	const button = document.getElementById('toggleButton');
//
//	// ボタンのテキストを切り替え
//	if ($("#toggleButton").val() == 1) {
//		button.textContent = '+スタッフの新規登録'; // 隠れている場合
//		$("#registrationForm").removeClass("hiddenform");
//		$("#registrationForm").addClass("hidden");
//		$("#toggleButton").val(0);
//	} else {
//		$("#registrationForm").removeClass("hidden");
//		$("#registrationForm").addClass("hiddenform");
//		button.textContent = '-スタッフの新規登録'; // 表示されている場合
//		$("#toggleButton").val(1);
//	}
//}
///*
// * パスワード表示・非表示
// */
//
//document.addEventListener('DOMContentLoaded', () => {
//	const togglePassword = document.getElementById('togglePassword');
//	const passwordInput = document.getElementById('password');
//	const eyeIcon = document.getElementById('eyeIcon');
//
//	togglePassword.addEventListener('click', () => {
//		const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
//		passwordInput.setAttribute('type', type);
//		// アイコンを切り替え
//		eyeIcon.classList.toggle('fa-eye-slash');
//		eyeIcon.classList.toggle('fa-eye');
//	});
//
//});


///*
// * テーブル表示
// */
//function displayTable(page) {
//	const start = (page - 1) * rowsPerPage;
//	const end = start + rowsPerPage;
//	const paginatedCustomers = List.slice(start, end);
//
//	const tableBody = document.querySelector("#customerTable tbody");
//	tableBody.innerHTML = "";
//	paginatedCustomers.forEach(List => {
//		const row = document.createElement("tr");
//		let adminRole = '';
//		if (List.IsAdmin == '1') {
//			adminRole = $("#text_adminRoleSistem").val();
//		} else if (List.IsAdmin == '2') {
//			adminRole = $("#text_adminRoleStaff").val();
//		} else if (List.IsAdmin == '3') {
//			adminRole = $("#text_adminRoleWorker").val();
//		} else {
//			adminRole = '';
//		}
//		row.innerHTML = `
//            <td>${List.UserId}</td>
//            <td>${List.UserName}</td>
//			<td>${adminRole}</td>
//			<td>${List.UpdateTime}</td>
//            <td>
//                <button type="button" class="applicationBtn" onclick="showInputField(this)"> 
//				<i class="fa-solid fa-gear"></i>
//                     ${$("#text_staffMgmtPassChange").val()}
//                </button>
//				<div class="password-container">
//				<div class="item-container">
//				<div class="Password_div">
//				    <input type="password" class="password-input" id="updatePassword_${List.UserId}" placeholder=${$("#text_staffMgmtPass").val()} oninput="checkPasswordStrength(this)" />
//					<span id="togglePassword" style="cursor: pointer;"> 
//					<i class="fa fa-eye-slash passwordIcon" id="eyeIcon"></i></span> 
//					</div>
//					<button type="button" class="saveBtn" onclick="chkChangePass(${List.UserId}, document.getElementById('updatePassword_${List.UserId}').value)">${$("#text_staffMgmtKeep").val()}</button>
//					</div>
//					<div class="password-strength-bar strength-bar"></div>
//				    <p class="password-strength-msg strength-msg">${$("#text_staffMgmtVulnerable").val()}</p>
//				    <div class="text-ss">
//				        ${$("#text_staffMgmtPassRule").val()}
//				    </div>
//				</div>
//			</td>
//            <td>
//                <button type="button" id="accountDeleteBtn" class="btn"onclick="confirmDelete(${List.UserId})">
//       <i class="fa-regular fa-trash-can"></i>&nbsp${$("#text_staffMgmtAccountDelete").val()}
//                </button>
//            </td>
//        `;
//		tableBody.appendChild(row);
//	});
//
//	updatePagination(page);
//}

/*
 * テーブルページ
 */

//function updatePagination(currentPage) {
//	const paginationContainer1 = document.getElementById("pagination1");
//	const paginationContainer2 = document.getElementById("pagination2");
//	const totalPages = Math.ceil(List.length / rowsPerPage);
//	const startRow = (currentPage - 1) * rowsPerPage + 1; // 現在ページの最初の行
//	const endRow = Math.min(currentPage * rowsPerPage, List.length); // 現在ページの最後の行
//
//	// 表示範囲を更新する関数
//	const updateDisplayRange = (container) => {
//		container.innerHTML = `<span>${startRow} ~ ${endRow} / ${List.length}</span>`;
//	};
//
//	// ボタンを作成する関数
//	const createButton = (label, page, isDisabled = false) => {
//		return isDisabled
//			? `<button disabled>${label}</button>`
//			: `<button onclick="displayTable(${page})">${label}</button>`;
//	};
//
//	// ページボタンを追加する関数
//	const addPageButtons = (container) => {
//		if (totalPages > 0) {
//			container.innerHTML += createButton("1", 1);
//			if (currentPage >= 4) {
//				container.innerHTML += `<span>...</span>`;
//			}
//		}
//		if (currentPage <= 2) {
//			for (let i = 2; i <= Math.min(3, totalPages - 1); i++) {
//				container.innerHTML += createButton(i, i);
//			}
//		} else if (currentPage >= totalPages - 2) {
//			for (let i = Math.max(totalPages - 3, 2); i < totalPages; i++) {
//				container.innerHTML += createButton(i, i);
//			}
//		} else {
//			for (let i = currentPage - 1; i <= currentPage + 1; i++) {
//				container.innerHTML += createButton(i, i);
//			}
//		}
//		if (totalPages > 1 && currentPage < totalPages - 2) {
//			container.innerHTML += `<span>...</span>`;
//		}
//		if (totalPages > 1) {
//			container.innerHTML += createButton(totalPages, totalPages);
//		}
//	};
//
//	// 各コンテナの表示範囲を更新
//	updateDisplayRange(paginationContainer1);
//	updateDisplayRange(paginationContainer2);
//
//	// 前のページボタン
//	paginationContainer1.innerHTML += createButton("<", currentPage - 1, currentPage <= 1);
//	paginationContainer2.innerHTML += createButton("<", currentPage - 1, currentPage <= 1);
//
//	// ページボタンの追加
//	addPageButtons(paginationContainer1);
//	addPageButtons(paginationContainer2);
//
//	// 次のページボタン
//	paginationContainer1.innerHTML += createButton(">", currentPage + 1, currentPage >= totalPages);
//	paginationContainer2.innerHTML += createButton(">", currentPage + 1, currentPage >= totalPages);
//
//	// 表示設定ボタンを追加
//	const settingsButton = `
//		<button type="button" id="setRowsBtn" onclick="openRowsDialog()">
//			<i class="fa-solid fa-gear"></i>&nbsp;${$("#text_staffMgmtDisplySet").val()}
//		</button>`;
//	paginationContainer1.innerHTML += settingsButton;
//	paginationContainer2.innerHTML += settingsButton;
//}


///*
// * 表示設定
// */
//const overlay = document.getElementById('rowsOverlay');
//const dialog = document.getElementById('rowsDialog');
//
//function openRowsDialog() {
//	dialog.style.display = 'block';
//	overlay.style.display = 'block';
//}
//
//function closeRowsDialog() {
//	dialog.style.display = 'none';
//	overlay.style.display = 'none';
//}
//
//function setRows() {
//	const selectedRadio = document.querySelector('input[name="rows"]:checked');
//
//	if (selectedRadio) {
//		rowsPerPage = parseInt(selectedRadio.value);
//		console.log(`${$("#text_staffMgmtDisplyNumber").val()}: ${rowsPerPage}件`);
//	} else {
//		rowsPerPage = 7;
//	}
//	displayTable(page);
//	closeRowsDialog();
//}
