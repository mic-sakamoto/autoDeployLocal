<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<div class="container">
		<div class="input-container">

			<div class="contents-area note-area">
				<div class="text-m">
					お申込内容の入力は、お申込者を特定するため <span class="text-red bold">運転免許証番号</span> または <span class="text-red bold">法人番号</span> の入力から開始します。
					<br>
					入力した運転免許証番号に間違いがないか必ずご確認のうえ、お申込の入力画面へお進みください。
					<br>
					また、入力間違いのまま進みますと <span class="text-red bold">受付・審査自体が無効となり、再度お申込みいただくことになります</span> のでご注意ください。
				</div>
			</div>

			<form id="select-input-method-form" name="form">
				<div class="contents-area">
					<div class="item-block">
						<!-- 申込種別選択 -->
						<div class="input-item select-input-item">
							<div class="text-m bold">申込種別選択</div>
							<div class="radio-container">
								<label>
									<input type="radio" class="radio-btn" id="inputSelectKbn" name="inputSelectKbn" value="1">
									個人
								</label>
								<label>
									<input type="radio" class="radio-btn" id="inputSelectKbn" name="inputSelectKbn" value="2">
									法人
								</label>
							</div>
						</div>

						<!-- 運転免許証番号 -->
						<div class="input-item select-input-item licenseNumber-div" style="display: none;">
							<div class="text-m bold" id="licenseNumber_title"></div>
							<div class="text-s">
								入力した内容が <span class="text-red bold">お申込者様本人のものであること</span> を必ずご確認ください。
								<br>
								<span class="text-red bold">誤入力があると再申込</span> となります。お申込の入力画面に進みますと変更はできませんので、ご注意ください。
							</div>
							<div class="input-name">
								<input type="text" class="only-num" id="licenseNumber" name="licenseNumber" placeholder="111122223333">
							</div>
							<p id="errorMsg" class="errorMsg text-s text-red hide"></p>
						</div>

						<!-- 申込モード -->
						<div class="input-item select-input-item inputMode-div" style="display: none;">
							<div class="text-m bold">申込モード</div>
							<div class="text-s">
								お申込の入力方法をご選択ください。
								<br>
								<span class="text-red bold">選択により加盟店様・お申込者ご本人様の入力いただける範囲が異なります。</span>
								<br>
								①このまま加盟店ページで入力 ：加盟店様が、お申込みに必要なすべての情報入力を行うことができます。
								<br>
								②ご契約者様専用ページで顧客が入力：加盟店様は、お申込みに必要なすべての情報入力を行えません。
							</div>
							<div class="radio-container radio-container-2">
								<label>
									<input type="radio" class="radio-btn" id="" name="inputMode" value="0">
									このまま加盟店ページで入力
								</label>
								<label>
									<input type="radio" class="" id="" name="inputMode" value="1">
									ご契約者様専用頁で顧客が入力
								</label>
							</div>
						</div>

						<!-- ご契約者様専用ページのログイン手順送信 -->
						<div class="input-item select-input-item loginMethod-div" style="display: none;">
							<div class="text-m bold">ご契約者様専用ページのログイン手順送信</div>
							<div class="text-s">
								お申込者様が直接入力できるページのURLを、ご指定のメールアドレスもしくは携帯電話番号にご案内します。
								<br>
								なお、ご案内ページの有効期限は72時間です。
							</div>
							<div class="radio-container">
								<label>
									<input type="radio" class="radio-btn" id="loginMethod" name="loginMethod" value="email">
									メールアドレス送信
								</label>
								<label>
									<input type="radio" class="radio-btn" id="loginMethod" name="loginMethod" value="sms">
									SMS送信
								</label>
							</div>
							<div class="input-mail" style="display: none;">
								<input type="email" class="mail-input checkInput" id="mailAddress" placeholder="メールアドレス">
							</div>
							<div class="input-tel" style="display: none;">
								<input type="text" class="tel-num only-num checkInput" id="mobile-num-1" placeholder="090" />
								<label class="hyphen">-</label>
								<input type="text" class="tel-num only-num checkInput" id="mobile-num-2" placeholder="9999" />
								<label class="hyphen">-</label>
								<input type="text" class="tel-num only-num checkInput" id="mobile-num-3" placeholder="9999" />
							</div>
						</div>
					</div>

					<!-- ボタン -->
					<div class="btn-container" id="next-btn-div" style="display: none;">
						<button type="button" class="btn main width-full" id="next-btn" disabled>お申込の入力画面へ進む</button>
					</div>
					
				</div>
			</form>
			
			<!-- ボタン -->
			<div class="btn-container">
				<button type="button" class="btn sub" id="modalCloseBtn">閉じる</button>
			</div>
		</div>
	</div>

<input type="hidden" id="text_licenseNumber_title_1" value="免許証番号">
<input type="hidden" id="text_licenseNumber_title_2" value="法人番号">
<input type="hidden" id="text_send" value="通知完了しました。">

<input type="hidden" name="inputMap" id="inputMap"/>
