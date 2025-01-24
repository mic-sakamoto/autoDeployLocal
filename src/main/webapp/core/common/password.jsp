<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!--ページ専用js-->
<link href="./core/common/common.css" rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<script type="text/javascript" src="./core/common/common.js"></script>


<div class="note">新しいパスワードを入力してください。</div>
<div class="note">パスワードには、半角数字・記号・英字小文字・英字大文字をそれぞれ1つ以上入れてください。</div>
<div class="Password-div">
    <s:textfield value="" type="password" class="input-password" placeholder="新しいパスワード" id="password" name="password" maxlength="20" onInput="checkPassword(this)(this)" />
    <span onclick="pushEyeButton()" id="buttonEye" class="fa fa-eye button-eye"></span>
</div>
<div id="password-strength-bar" class="strength-bar"></div>
<p id="password-strength-msg" class="strength-msg">脆弱</p>
