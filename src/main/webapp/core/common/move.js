/**画面遷移系*/
//ログイン
function to_login() {
	window.location.href = "./login";
}
//ログアウト
function logout() {
	window.location.href = "./login?action=logout";
}
//トップ
function to_top() {
	window.location.href = "./top";
}
//利用開始
function to_start_using() {
	window.location.href = "./start-using";
}
//加盟店管理
function to_store_mgmt() {
	window.location.href = "./store-mgmt";
}
//車両管理
function to_vehicle_mgmt() {
	window.location.href = "./vehicle-mgmt";
}
//スタッフID管理
function to_staff_mgmt() {
	window.location.href = "./staff-mgmt";
}
//問い合わせ管理
function to_inquiry_mgmt() {
	window.location.href = "./inquiry-mgmt";
}
//お知らせ管理
function to_announce_mgmt() {
	window.location.href = "./announcement-mgmt";
}
//ローンシミュレーション
function to_loan_simulation() {
	window.location.href = "./loan-simulation";
}
//MCCS車両管理
function to_mccs_vehicle_mgmt() {
	window.location.href = "./mccs-mgmt";
}
//精算明細票管理
function to_payment_detail_mgmt() {
	window.location.href = "./payment-detail-mgmt";
}
//お問い合わせ
function to_inquiry() {
	window.location.href = "./inquiry-mgmt";
}
//契約一覧(契約管理)
function to_contracts() {
	window.location.href = "./contract-mgmt";
}
function to_select_input_method() {
	window.location.href = "./select-input-method";
}
//審査入力
function to_application() {
	window.location.href = "./application";
}
//リセットパスワード
function to_reset_password() {
	window.location.href = "./reset-password";
}

//USS-SSページ
function to_ext_uss_ss(event) {
	event.preventDefault();
	window.open("https://www.uss-ss.net/", '_blank')
}
//USSページ
function to_ext_uss(event) {
	event.preventDefault();
	window.open("https://www.uss-ss.net/company.html", '_blank')
}
//プライバシーポリシー
function to_ext_uss_pp(event) {
	event.preventDefault();
	window.open("https://www.uss-ss.net/privacy.html", '_blank')
}
