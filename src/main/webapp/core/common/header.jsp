<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="javax.servlet.*,java.text.*"%>

<%
String user_id = (String)session.getAttribute("user_id");
String user_name = "";
String role_type = "0";
String proxy_flg = "0";
String staff_id = "";
String staff_name = "";
if (user_id != null) {
	user_name = (String)session.getAttribute("user_name");
	role_type = (String)session.getAttribute("role_type");
	proxy_flg = (String)session.getAttribute("proxy_flg");
	
	staff_id = (String)session.getAttribute("staff_id");
	staff_name = (String)session.getAttribute("staff_name");
}
%>

<header class="">
	<div class="header-container">
		<div class="header-area">
			<div class="header-block">
				<div class="header-item logo-section">
					<button class="header-logo-btn" id="headerLogoButton">
						<img src="./images/USS-SS_logo_sub.png" alt="企業ロゴ" />
					</button>
				</div>
				<div class="header-item role-item">
					<% if ("0".equals(role_type)) { %>
					<div class="header-role <% if ("1".equals(role_type)) { %>header-role-1<% } %><% if ("2".equals(role_type)) { %>header-role-2<% } %><% if ("3".equals(role_type)) { %>header-role-3<% } %> hide">
						<span class="role-label"></span>
					</div>
					<% } %>
					<% if ("1".equals(role_type)) { %>
					<div class="header-role staff">
						<span class="role-label">スタッフページ</span>
					</div>
					<% } %>
					<% if ("2".equals(role_type)) { %>
					<div class="header-role store">
						<span class="role-label">加盟店ページ</span>
					</div>
					<% } %>
					<% if ("3".equals(role_type)) { %>
					<div class="header-role customer">
						<span class="role-label">ご契約者様専用ページ</span>
					</div>
					<% } %>
				</div>

				<div class="icon-hamburger">
					<div class="icon-hamburger-item">
						<img class="svg header-menu-svg-icon" src="./images/svg/bars.svg">
					</div>
				</div>

				<div class="header-item menu-container">
					
					<nav class="header-menu <% if ("1".equals(role_type)) { %>header-menu-1<% } %><% if ("2".equals(role_type)) { %>header-menu-2<% } %><% if ("3".equals(role_type)) { %>header-menu-3<% } %>">
						<ul class="menu">
							<% if ("1".equals(role_type)) { %>
							<li><a class="menu-item header_menu" onclick="to_store_mgmt()">加盟店管理</a></li>
							<li><a class="menu-item header_menu" onclick="to_vehicle_mgmt()">車両管理</a></li>
							<li><a class="menu-item header_menu" onclick="to_contracts()">契約管理</a></li>
							<li><a class="menu-item header_menu" onclick="to_staff_mgmt()">スタッフID管理</a></li>
							<li><a class="menu-item header_menu" onclick="to_inquiry()">問い合わせ管理</a></li>
							<li><a class="menu-item header_menu" onclick="to_announce_mgmt()">お知らせ管理</a></li>
							<% } %>
							<% if ("2".equals(role_type)) { %>
							<li><a class="menu-item header_menu" onclick="to_contracts()">契約管理</a></li>
							<li><a class="menu-item header_menu" onclick="to_mccs_vehicle_mgmt()">MCCS車両管理</a></li>
							<li><a class="menu-item header_menu" onclick="to_payment_detail_mgmt()">精算明細票管理</a></li>
							<li><a class="menu-item header_menu" onclick="to_inquiry()">お問い合わせ</a></li>
							<% } %>
							<% if ("3".equals(role_type)) { %>
							<li><a class="menu-item header_menu" onclick="to_contracts()">契約一覧</a></li>
							<li><a class="menu-item header_menu" onclick="to_inquiry()">お問い合わせ</a></li>
							<% } %>
							
							<li class="sp-block"></li>
							
							<% if (proxy_flg == "1") { %>
								<li class="sp-block">
									<div class="header-item user-item">
										<div class="header-proxy-staff">
											<div class="header-proxy-icon">
												代行
											</div>
											<div class="header-proxy-user">
						
												<div class="user-info header-user-id">
													ID:
													<%=staff_id %>
												</div>
												<div class="user-info header-user-name">
													<%=staff_name %>
													様
												</div>
						
											</div>
											<div id="proxyLogout" class="header-proxy-close">
												×
											</div>
										</div>
									</div>
									
									
									
<!-- 									<div class="header-user"> -->
				
<!-- 										<div class="user-info header-user-id"> -->
<!-- 											ID: -->
<%-- 											<%=staff_id %> --%>
<!-- 										</div> -->
<!-- 										<div class="user-info header-user-name"> -->
<%-- 											<%=staff_name %> --%>
<!-- 											様 -->
<!-- 										</div> -->
<!-- 										<div id="proxyLogoutSp" class="header-proxy-close"> -->
<!-- 											× -->
<!-- 										</div> -->
				
<!-- 									</div> -->
								
								</li>
							<% } %>
							
							<li class="sp-block">
								<% if (user_id != null) { %>
								<div class="header-user">
			
									<div class="user-info header-user-id">
										ID:
										<%=user_id %>
									</div>
									<div class="user-info header-user-name">
										<%=user_name %>
										様
									</div>
			
								</div>
								<% } %>
							
							</li>
							<% if (user_id != null) { %>
								<li class="sp-block"><a class="menu-item header_menu" onclick="logout()">ログアウト</a></li>
							<% } else { %>
								<li class="sp-block"><a class="menu-item header_menu" onclick="to_login()">ログイン</a></li>
							<% } %>
						</ul>
					</nav>
				</div>
			</div>
			<div class="header-block header-right-section">
				<% if (proxy_flg == "1") { %>
					<div class="header-item user-item">
						<div class="header-proxy-staff">
							<div class="header-proxy-icon">
								代行
							</div>
							<div class="header-proxy-user">
		
								<div class="user-info header-user-id">
									ID:
									<%=staff_id %>
								</div>
								<div class="user-info header-user-name">
									<%=staff_name %>
									様
								</div>
		
							</div>
							<div id="proxyLogout" class="header-proxy-close">
								×
							</div>
						</div>
					</div>
				<% } %>
			
				<div class="header-item user-item">
					<% if (user_id != null) { %>
					<div class="header-user">

						<div class="user-info header-user-id">
							ID:
							<%=user_id %>
						</div>
						<div class="user-info header-user-name">
							<%=user_name %>
							様
						</div>

					</div>
					<% } %>
				</div>
				<div class="header-item btn-item">
					<% if (user_id != null) { %>
					<button id="headerLogoutBtn" class="header-login-btn">ログアウト</button>
					<% } else { %>
					<button id="headerLoginBtn" class="header-login-btn">ログイン</button>
					<% } %>
				</div>
			</div>
		</div>
	</div>
</header>
