<%@page import="utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/core/common/import.jsp"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link href="./core/announcement-mgmt/announcement-mgmt-regist.css?t=<%=df.format(date)%>" rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<script type="text/javascript" src="./core/announcement-mgmt/announcement-mgmt-regist.js?t=<%=df.format(date)%>"></script>
<script type="text/javascript" src="./core/common/common.js"></script>
</head>

<body>
	<div class="wrapper">
		<%@ include file="/core/common/header.jsp"%>
		<main>
			<div class="title">
				<div class="title-container">
					<span class="title-item">お知らせ管理</span>
				</div>
			</div>
			
			<div class="container">
	
		<div class="input-container">
			
			<form id="form" name="form" class="form" onsubmit="return false;">
				<div class="contents-area ">
						<div class="item-block">
						
							<!-- 対象 -->
							<div class="input-item">
								<div class="text-m bold item-title">対象</div>
								<div class="checkbox-container" id="">
									<label>
										<span class="checkbox-div-checkbox">
											<input type="checkbox" class="checkbox-padding" id="IsTargetStore" />
										</span>
										<span class="checkbox-div-text">加盟店</span>										
									</label>
									<label>
										<span class="checkbox-div-checkbox">
											<input type="checkbox" class="checkbox-padding" id="IsTargetCustomer" />
										</span>
										<span class="checkbox-div-text">顧客</span>										
									</label>
									
								</div>
							</div>
							
							<!-- 公開期間 -->
							<div class="input-item">
								<div class="text-m bold item-title">公開期間</div>
								<div class="">
									<input type="date" class="margin-right-10 input-datetime" id="openDate" name="" value=""> 
									<input type="time" class="margin-right-10 input-datetime" id="openTime" name="" value="">
									<label>～</label>
									<input type="date" class="margin-right-10 input-datetime" id="closeDate" name="" value=""> 
									<input type="time" class="margin-right-10 input-datetime" id="closeTime" name="" value="">  
								</div>
							</div>
							
							<!-- 本文 -->
							<div class="input-item">
								<div class="text-m bold item-title">本文</div>
								<div class="body-area-div">
									<textarea class="body-area" id="bodyTextArea" name="" value=""></textarea>
									<div class="textcount" id="textCount">0 / XXX</div>
								</div>
							</div>
						</div>
						
						<!-- ボタン -->
						<div class="btn-container regist-btn-div">
							<button class="btn sub" id="backPage-btn">戻る</button>
							<button type="submit" class="btn main" id="">公開</button>
						</div>
						<div class="btn-container update-btn-div" style="display:none;">
							<button class="btn sub" id="">削除</button>
							<button class="btn sub" id="">公開中止</button>
							<button type="submit" class="btn main" id="">更新</button>
						</div>
						
					</div>
				</div>
				
			</form>
			
			
		</div>
		</main>
		<%@ include file="/core/common/footer.jsp"%>
	</div>
	<input type="hidden" id="closedDisplaySetting1" value="1">
	<input type="hidden" id="closedDisplaySetting2" value="1">
	<input type="hidden" id="text_status_0" value="公開前">
	<input type="hidden" id="text_status_1" value="公開中">
	<input type="hidden" id="text_status_2" value="公開期間終了">
	<input type="hidden" id="text_status_3" value="公開中止">
	<input type="hidden" id="text_isTargetCustomer_1" value="加盟店">
	<input type="hidden" id="text_isTargetCustomer_2" value="ご契約者様">
</body>
</html>
