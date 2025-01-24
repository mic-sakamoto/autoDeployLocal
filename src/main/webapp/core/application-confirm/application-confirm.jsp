<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 入力内容のご確認 -->

<form id="form-confirm" name="form" class="form" onsubmit="return false;">

	<div class="contents-area-confirm" id="form-input-confirm">

		<div class="confirm-form moshikomi">
			<div class="confirm-form-header">
				<label class="confirm-form-header-title open-trigger">
					お申込者様の情報
					<span class="open-icon open"></span>
				</label>
				<button type="button" class="confirm-form-header-edit-btn btn main page-btn" name="form-moshikomi">編集する</button>
			</div>
			<div class="confirm-form-body confirm-list">
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">お申込み年月日</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiDateYearText"></div>
						<input type="hidden" id="MoshikomiDateYear">
						<span class="text-year"></span>
						<div id="MoshikomiDateMonthText"></div>
						<input type="hidden" id="MoshikomiDateMonth">
						<span class="text-month"></span>
						<div id="MoshikomiDateDayText"></div>
						<input type="hidden" id="MoshikomiDateDay">
						<span class="text-day"></span>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">売買契約年月日</div>
					<div class="confirm-list-item-data">
						<div id="BaibaikeiyakuDateYearText"></div>
						<input type="hidden" id="BaibaikeiyakuDateYear">
						<span class="text-year"></span>
						<div id="BaibaikeiyakuDateMonthText"></div>
						<input type="hidden" id="BaibaikeiyakuDateMonth">
						<span class="text-month"></span>
						<div id="BaibaikeiyakuDateDayText"></div>
						<input type="hidden" id="BaibaikeiyakuDateDay">
						<span class="text-day"></span>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">申込区分</div>
					<div class="confirm-list-item-data">
						<div class="radio-value" id="MoshikomiKbnText"></div>
						<input type="hidden" id="MoshikomiKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">お名前</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiNameSeiText"></div>
						<input type="hidden" id="MoshikomiNameSei">
						<div id="MoshikomiNameMeiText"></div>
						<input type="hidden" id="MoshikomiNameMei">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">お名前（フリガナ）</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiNameSeiKanaText"></div>
						<input type="hidden" id="MoshikomiNameSeiKana">
						<div id="MoshikomiNameMeiKanaText"></div>
						<input type="hidden" id="MoshikomiNameMeiKana">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">性別</div>
					<div class="confirm-list-item-data">
						<div class="radio-value" id="MoshikomiSeibetsuKbnText"></div>
						<input type="hidden" id="MoshikomiSeibetsuKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">年齢</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiAgeText"></div>
						<input type="hidden" id="MoshikomiAge">
						<span class="text-age">歳</span>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">生年月日</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiBirthDateYearText"></div>
						<input type="hidden" id="MoshikomiBirthDateYear">
						<span class="text-year"></span>
						<div id="MoshikomiBirthDateMonthText"></div>
						<input type="hidden" id="MoshikomiBirthDateMonth">
						<span class="text-month"></span>
						<div id="MoshikomiBirthDateDayText"></div>
						<input type="hidden" id="MoshikomiBirthDateDay">
						<span class="text-day"></span>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">携帯電話番号</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiMobile1Text"></div>
						<span class="text-hyphen">-</span>
						<div id="MoshikomiMobile2Text"></div>
						<span class="text-hyphen">-</span>
						<div id="MoshikomiMobile3Text"></div>
						<input type="hidden" id="MoshikomiMobile1">
						<input type="hidden" id="MoshikomiMobile2">
						<input type="hidden" id="MoshikomiMobile3">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">自宅電話番号</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiTel1Text"></div>
						<span class="text-hyphen">-</span>
						<div id="MoshikomiTel2Text"></div>
						<span class="text-hyphen">-</span>
						<div id="MoshikomiTel3Text"></div>
						<input type="hidden" id="MoshikomiTel1">
						<input type="hidden" id="MoshikomiTel2">
						<input type="hidden" id="MoshikomiTel3">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">郵便番号</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiPostText"></div>
						<input type="hidden" id="MoshikomiPost">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">住所１</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiAddress1Text"></div>
						<input type="hidden" id="MoshikomiAddress1">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">住所２</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiAddress2Text"></div>
						<input type="hidden" id="MoshikomiAddress2">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">住所（フリガナ）</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiAddressKanaText"></div>
						<input type="hidden" id="MoshikomiAddressKana">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">配偶者</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiHaigushaKbnText"></div>
						<input type="hidden" id="MoshikomiHaigushaKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">住居区分</div>
					<div class="confirm-list-item-data">
						<div id="JukyoKbnText"></div>
						<input type="hidden" id="JukyoKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">住宅ローン・家賃支払い（配偶者含む）</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiLoanKbnText"></div>
						<input type="hidden" id="MoshikomiLoanKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">同居人数（本人含む）</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiDokyoNinzuText"></div>
						<span class="text-ninzu">人</span>
						<input type="hidden" id="MoshikomiDokyoNinzu">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">居住年数</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiKyojuYearText"></div>
						<span class="text-year">年</span>
						<div id="MoshikomiKyojuMonthText"></div>
						<span class="text-monthes">ヵ月</span>
						<input type="hidden" id="MoshikomiKyojuYear">
						<input type="hidden" id="MoshikomiKyojuMonth">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">税込年収</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiNenshuText"></div>
						<span class="text-man-yen">万円</span>
						<input type="hidden" id="MoshikomiNenshu">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">ご職業</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiShokugyoKbnText"></div>
						<input type="hidden" id="MoshikomiShokugyoKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">勤務先・学校情報</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiKinmusakiText"></div>
						<input type="hidden" id="MoshikomiKinmusaki">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">電話番号</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiKinmusakiTel1Text"></div>
						<span class="text-hyphen">-</span>
						<div id="MoshikomiKinmusakiTel2Text"></div>
						<span class="text-hyphen">-</span>
						<div id="MoshikomiKinmusakiTel3Text"></div>
						<input type="hidden" id="MoshikomiKinmusakiTel1">
						<input type="hidden" id="MoshikomiKinmusakiTel2">
						<input type="hidden" id="MoshikomiKinmusakiTel3">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">所在地郵便番号</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiKinmusakiPostText"></div>
						<input type="hidden" id="MoshikomiKinmusakiPost">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">所在地１</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiKinmusakiAddress1Text"></div>
						<input type="hidden" id="MoshikomiKinmusakiAddress1">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">所在地２</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiKinmusakiAddress2Text"></div>
						<input type="hidden" id="MoshikomiKinmusakiAddress2">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">勤続年数（学年）</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiKinzokuYearText"></div>
						<input type="hidden" id="MoshikomiKinzokuYear">
						<span class="text-year">年</span>
						<div id="MoshikomiKinzokuMonthText"></div>
						<input type="hidden" id="MoshikomiKinzokuMonth">
						<span class="text-monthes">ヵ月</span>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">役職</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiKinmusakiYakushokuKbnText"></div>
						<input type="hidden" id="MoshikomiKinmusakiYakushokuKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">所属（部署）</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiKinmusakiShozokuText"></div>
						<input type="hidden" id="MoshikomiKinmusakiShozoku">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">従業員数</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiKinmusakiJugyoinText"></div>
						<input type="hidden" id="MoshikomiKinmusakiJugyoin">
						<span class="text-ninzu">人</span>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">業種区分</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiGyoshuKbnText"></div>
						<input type="hidden" id="MoshikomiGyoshuKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">預金残高</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiYokinZandakaText"></div>
						<input type="hidden" id="MoshikomiYokinZandaka">
						<span class="text-man-yen">万円</span>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">世帯主の氏名</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiSetainushiNameSeiText"></div>
						<div id="MoshikomiSetainushiNameMeiText"></div>
						<input type="hidden" id="MoshikomiSetainushiNameSei">
						<input type="hidden" id="MoshikomiSetainushiNameMei">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">お申込者との関係</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiSetainushiRelateKbnText"></div>
						<input type="hidden" id="MoshikomiSetainushiRelateKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">居住</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiKyojuKbnText"></div>
						<input type="hidden" id="MoshikomiKyojuKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">世帯主の税込年収</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiSetainushiNenshuText"></div>
						<input type="hidden" id="MoshikomiSetainushiNenshu">
						<span class="text-man-yen">万円</span>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">世帯主のクレジット月額債務</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiSetainushiCreditSaimuText"></div>
						<input type="hidden" id="MoshikomiSetainushiCreditSaimu">
						<span class="text-man-yen">万円</span>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">年金受給情報</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiNenkinJukyuKbnText"></div>
						<input type="hidden" id="MoshikomiNenkinJukyuKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title"></div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiNenkinJukyuKbnSonotaText"></div>
						<input type="hidden" id="MoshikomiNenkinJukyuKbnSonota">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">年間受給額</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiNenkinJukyuPriceText"></div>
						<input type="hidden" id="MoshikomiNenkinJukyuPrice">
						<span class="text-yen">円</span>
					</div>
				</div>
				<div class="confirm-list-item main-view">
					<div class="confirm-list-item-title">お申込者様のベリファイ希望日時</div>
					<div class="confirm-list-item-data">
						<div id="MoshikomiVerifyDate"></div>
						<span class=""></span>
						<div id="MoshikomiVerifyTimeFromText"></div>
						<span class="">頃</span>
						<span class="">～</span>
						<div id="MoshikomiVerifyTimeToText"></div>
						<span class="">頃</span>
						<input type="hidden" id="MoshikomiVerifyTimeFrom">
						<input type="hidden" id="MoshikomiVerifyTimeTo">
					</div>
				</div>
			</div>
		</div>

		<div class="confirm-form hoshonin">
			<div class="confirm-form-header">
				<label class="confirm-form-header-title open-trigger">
					連帯保証人様の情報
					<span class="open-icon open"></span>
				</label>
				<button type="button" class="confirm-form-header-edit-btn btn main page-btn" name="form-hoshonin">編集する</button>
			</div>
			<div class="confirm-form-body confirm-list">
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">連帯保証人</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninKbnText"></div>
						<input type="hidden" id="HoshoninKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">お名前</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninNameSeiText"></div>
						<div id="HoshoninNameMeiText"></div>
						<input type="hidden" id="HoshoninNameSei">
						<input type="hidden" id="HoshoninNameMei">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">お名前（フリガナ）</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninNameSeiKanaText"></div>
						<div id="HoshoninNameMeiKanaText"></div>
						<input type="hidden" id="HoshoninNameSeiKana">
						<input type="hidden" id="HoshoninNameMeiKana">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">性別</div>
					<div class="confirm-list-item-data">
						<div class="radio-value" id="HoshoninSeibetsuKbnText"></div>
						<input type="hidden" id="HoshoninSeibetsuKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">年齢</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninAgeText"></div>
						<input type="hidden" id="HoshoninAge">
						<span class="text-age">歳</span>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">生年月日</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninBirthDateYearText"></div>
						<span class="text-year"></span>
						<div id="HoshoninBirthDateMonthText"></div>
						<span class="text-month"></span>
						<div id="HoshoninBirthDateDayText"></div>
						<span class="text-day"></span>
						<input type="hidden" id="HoshoninBirthDateYear">
						<input type="hidden" id="HoshoninBirthDateMonth">
						<input type="hidden" id="HoshoninBirthDateDay">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">携帯電話番号</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninMobile1Text"></div>
						<span class="text-hyphen">-</span>
						<div id="HoshoninMobile2Text"></div>
						<span class="text-hyphen">-</span>
						<div id="HoshoninMobile3Text"></div>
						<input type="hidden" id="HoshoninMobile1">
						<input type="hidden" id="HoshoninMobile2">
						<input type="hidden" id="HoshoninMobile3">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">自宅電話番号</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninTel1Text"></div>
						<span class="text-hyphen">-</span>
						<div id="HoshoninTel2Text"></div>
						<span class="text-hyphen">-</span>
						<div id="HoshoninTel3Text"></div>
						<input type="hidden" id="HoshoninTel1">
						<input type="hidden" id="HoshoninTel2">
						<input type="hidden" id="HoshoninTel3">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">郵便番号</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninPostText"></div>
						<input type="hidden" id="HoshoninPost">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">住所１</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninAddress1Text"></div>
						<input type="hidden" id="HoshoninAddress1">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">住所２</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninAddress2Text"></div>
						<input type="hidden" id="HoshoninAddress2">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">住所（フリガナ）</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninAddressKanaText"></div>
						<input type="hidden" id="HoshoninAddressKana">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">税込年収</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninNenshuText"></div>
						<input type="hidden" id="HoshoninNenshu">
						<span class="text-man-yen">万円</span>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">住居区分</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninJukyoKbnText"></div>
						<input type="hidden" id="HoshoninJukyoKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">住宅ローン・家賃支払い（配偶者含む）</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninLoanKbnText"></div>
						<input type="hidden" id="HoshoninLoanKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">配偶者</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninHaigushaKbnText"></div>
						<input type="hidden" id="HoshoninHaigushaKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">同居人数（本人含む）（生計をマイナスにする別居家族含む）</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninDokyoNinzuText"></div>
						<input type="hidden" id="HoshoninDokyoNinzu">
						<span class="text-ninzu">人</span>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">お申込者とのご関係</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninMoshikomiRelateKbnText"></div>
						<input type="hidden" id="HoshoninMoshikomiRelateKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title"></div>
					<div class="confirm-list-item-data">
						<div id="HoshoninMoshikomiRelateKbnSonotaText"></div>
						<input type="hidden" id="HoshoninMoshikomiRelateKbnSonota">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">ご職業</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninShokugyoKbnText"></div>
						<input type="hidden" id="HoshoninShokugyoKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">勤務先・学校情報</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninKinmusakiText"></div>
						<input type="hidden" id="HoshoninKinmusaki">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">電話番号</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninKinmusakiTel1Text"></div>
						<span class="text-hyphen">-</span>
						<div id="HoshoninKinmusakiTel2Text"></div>
						<span class="text-hyphen">-</span>
						<div id="HoshoninKinmusakiTel3Text"></div>
						<input type="hidden" id="HoshoninKinmusakiTel1">
						<input type="hidden" id="HoshoninKinmusakiTel2">
						<input type="hidden" id="HoshoninKinmusakiTel3">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">所在地郵便番号</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninKinmusakiPostText"></div>
						<input type="hidden" id="HoshoninKinmusakiPost">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">所在地１</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninKinmusakiAddress1Text"></div>
						<input type="hidden" id="HoshoninKinmusakiAddress1">

					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">所在地２</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninKinmusakiAddress2Text"></div>
						<input type="hidden" id="HoshoninKinmusakiAddress2">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">勤続年数（学年）</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninKinzokuYearText"></div>
						<span class="text-year">年</span>
						<div id="HoshoninKinzokuMonthText"></div>
						<span class="text-monthes">ヵ月</span>
						<input type="hidden" id="HoshoninKinzokuYear">
						<input type="hidden" id="HoshoninKinzokuMonth">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">役職</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninKinmusakiYakushokuKbnText"></div>
						<input type="hidden" id="HoshoninKinmusakiYakushokuKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">所属</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninKinmusakiShozokuText"></div>
						<input type="hidden" id="HoshoninKinmusakiShozoku">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">従業員数</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninKinmusakiJugyoinText"></div>
						<input type="hidden" id="HoshoninKinmusakiJugyoin">
						<span class="text-ninzu">人</span>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">業種区分</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninGyoshuKbnText"></div>
						<input type="hidden" id="HoshoninGyoshuKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">年金受給情報</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninNenkinJukyuKbnText"></div>
						<input type="hidden" id="HoshoninNenkinJukyuKbn">
					</div>
				</div>
				<div class="confirm-list-item main-view">
					<div class="confirm-list-item-title">連帯保証人様のベリファイ希望日程</div>
					<div class="confirm-list-item-data">
						<div id="HoshoninVerifyDateText"></div>
						<span class=""></span>
						<div id="HoshoninVerifyTimeFromText"></div>
						<span class="">頃</span>
						<span class="">～</span>
						<div id="HoshoninVerifyTimeToText"></div>
						<span class="">頃</span>
						<input type="hidden" id="HoshoninVerifyDate">
						<input type="hidden" id="HoshoninVerifyTimeFrom">
						<input type="hidden" id="HoshoninVerifyTimeTo">
					</div>
				</div>
			</div>
		</div>

		<div class="confirm-form okuruma">
			<div class="confirm-form-header">
				<label class="confirm-form-header-title open-trigger">
					お車の情報
					<span class="open-icon open"></span>
				</label>
				<button type="button" class="confirm-form-header-edit-btn btn main page-btn" name="form-okuruma">編集する</button>
			</div>
			<div class="confirm-form-body confirm-list">
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">Connected(MCCS)申込</div>
					<div class="confirm-list-item-data">
						<div id="MccsMoshikomiKbnText"></div>
						<input type="hidden" id="MccsMoshikomiKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">シフト</div>
					<div class="confirm-list-item-data">
						<div id="ShiftKbnText"></div>
						<input type="hidden" id="ShiftKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">リモートスターター</div>
					<div class="confirm-list-item-data">
						<div id="RemoteStarterKbnText"></div>
						<input type="hidden" id="RemoteStarterKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">販売の条件となっている商品・権利・役務の有無</div>
					<div class="confirm-list-item-data">
						<div id="HanbaiJokenKbnText"></div>
						<input type="hidden" id="HanbaiJokenKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">主な使用目的</div>
					<div class="confirm-list-item-data">
						<div id="ShiyoMokutekiKbnText"></div>
						<input type="hidden" id="ShiyoMokutekiKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">新車・中古車</div>
					<div class="confirm-list-item-data">
						<div id="UsedKbnText"></div>
						<input type="hidden" id="UsedKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">エンジンスタート</div>
					<div class="confirm-list-item-data">
						<div id="EngineStartKbnText"></div>
						<input type="hidden" id="EngineStartKbn">
					</div>
				</div>
				<div class="confirm-list-item mccs-car-info">
					<div class="confirm-list-item-title">メーカー</div>
					<div class="confirm-list-item-data">
						<div id="MakerText"></div>
						<input type="hidden" id="Maker">
					</div>
				</div>
				<div class="confirm-list-item mccs-car-info">
					<div class="confirm-list-item-title">車名</div>
					<div class="confirm-list-item-data">
						<div id="ShameiText"></div>
						<input type="hidden" id="Shamei">
					</div>
				</div>
				<div class="confirm-list-item mccs-car-info">
					<div class="confirm-list-item-title">型式</div>
					<div class="confirm-list-item-data">
						<div id="KatashikiText"></div>
						<input type="hidden" id="Katashiki">
					</div>
				</div>
				<div class="confirm-list-item mccs-car-info">
					<div class="confirm-list-item-title">初年度</div>
					<div class="confirm-list-item-data">
						<div id="ShonendoYearText"></div>
						<span class="text-year"></span>
						<div id="ShonendoMonthText"></div>
						<span class="text-month"></span>
						<input type="hidden" id="ShonendoYear">
						<input type="hidden" id="ShonendoMonth">
					</div>
				</div>
				<div class="confirm-list-item mccs-manual-ipt">
					<div class="confirm-list-item-title">車名</div>
					<div class="confirm-list-item-data">
						<div id="ShameiMLText"></div>
						<input type="hidden" id="ShameiML">
					</div>
				</div>
				<div class="confirm-list-item mccs-manual-ipt">
					<div class="confirm-list-item-title">型式</div>
					<div class="confirm-list-item-data">
						<div id="KatashikiMLText"></div>
						<input type="hidden" id="KatashikiML">
					</div>
				</div>
				<div class="confirm-list-item mccs-manual-ipt">
					<div class="confirm-list-item-title">初年度</div>
					<div class="confirm-list-item-data">
						<div id="ShonendYearML"></div>
						<span class="text-year"></span>
						<div id="ShonendMonthML"></div>
						<span class="text-month"></span>
						<input type="hidden" id="ShonendYearMLText">
						<input type="hidden" id="ShonendMonthMLText">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">グレード</div>
					<div class="confirm-list-item-data">
						<div id="GradeText"></div>
						<input type="hidden" id="Grade">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">車台番号</div>
					<div class="confirm-list-item-data">
						<div id="ShadaiNum"></div>
						<input type="hidden" id="ShadaiNum">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">走行距離</div>
					<div class="confirm-list-item-data">
						<div id="SokoKyoriText"></div>
						<span class="text-km">km</span>
						<input type="hidden" id="SokoKyori">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">登録番号</div>
					<div class="confirm-list-item-data">
						<div id="TorokuNumText"></div>
						<input type="hidden" id="TorokuNum">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">車体色</div>
					<div class="confirm-list-item-data">
						<div id="ColorText"></div>
						<input type="hidden" id="Color">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">排気量</div>
					<div class="confirm-list-item-data">
						<div id="HaikiryoText"></div>
						<span class="text-cc">cc</span>
						<input type="hidden" id="Haikiryo">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">所有者</div>
					<div class="confirm-list-item-data">
						<div id="OwnerText"></div>
						<input type="hidden" id="Owner">
					</div>
				</div>
			</div>
		</div>

		<div class="confirm-form kingaku">
			<div class="confirm-form-header">
				<label class="confirm-form-header-title open-trigger">
					金額・その他の情報
					<span class="open-icon open"></span>
				</label>
				<button type="button" class="confirm-form-header-edit-btn btn main page-btn" name="form-kingaku">編集する</button>
			</div>
			<div class="confirm-form-body confirm-list">
				<div class="confirm-list-item">
					<div class="confirm-list-item-title"></div>
					<div class="confirm-list-item-data">
						<div id=""></div>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">車両本体価格</div>
					<div class="confirm-list-item-data">
						<div id="CarPriceText"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="CarPrice">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">MCCS取付</div>
					<div class="confirm-list-item-data">
						<div id="MccsAttachPriceText"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="MccsAttachPrice">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">付属品</div>
					<div class="confirm-list-item-data">
						<div id="FuzokuhinPriceText"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="FuzokuhinPrice">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">諸費用</div>
					<div class="confirm-list-item-data">
						<div id="OtherPriceText"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="OtherPrice">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">車検・整備費用</div>
					<div class="confirm-list-item-data">
						<div id="ShakenPriceText"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="ShakenPrice">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">①現金合計額</div>
					<div class="confirm-list-item-data">
						<div id="TotalPriceText"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="TotalPrice">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">②頭金 現金（お申込金)</div>
					<div class="confirm-list-item-data">
						<div id="AppPriceText"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="AppPrice">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">②頭金 下取車充当額</div>
					<div class="confirm-list-item-data">
						<div id="ShitadoriPriceText"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="ShitadoriPrice">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">③残金（①-②）</div>
					<div class="confirm-list-item-data">
						<div id="RemainPriceText"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="RemainPrice">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">お支払期間 開始</div>
					<div class="confirm-list-item-data">
						<div id="ShiharaiStartYearText"></div>
						<span class="text-year"></span>
						<div id="ShiharaiStartMonthText"></div>
						<span class="text-month"></span>
						<input type="hidden" id="ShiharaiStartYear">
						<input type="hidden" id="ShiharaiStartMonth">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">お支払期間 終了</div>
					<div class="confirm-list-item-data">
						<div id="ShiharaiEndYearText"></div>
						<span class="text-year"></span>
						<div id="ShiharaiEndMonthText"></div>
						<span class="text-month"></span>
						<input type="hidden" id="ShiharaiEndYear">
						<input type="hidden" id="ShiharaiEndMonth">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">お支払回数</div>
					<div class="confirm-list-item-data">
						<div id="ShiharaiKaisuText"></div>
						<span class="text-kaisu"></span>
						<input type="hidden" id="ShiharaiKaisu">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">口座振替日</div>
					<div class="confirm-list-item-data">
						<div id="KozaHurikaebiText">毎月27日</div>
						<input type="hidden" id="KozaHurikaebi" value="毎月27日">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">ボーナス加算月</div>
					<div class="confirm-list-item-data">
						<div id="BonusKasanMonthKbnText"></div>
						<input type="hidden" id="BonusKasanMonthKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">加算支払金の回数</div>
					<div class="confirm-list-item-data">
						<div id="BonusKasanKaisuText"></div>
						<span class="text-kaisu">回</span>
						<input type="hidden" id="BonusKasanKaisu">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">加算支払金の金額</div>
					<div class="confirm-list-item-data">
						<div id="BonusKasanPriceText"></div>
						<span class="text-sen-yen">千円</span>
						<input type="hidden" id="BonusKasanPrice">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">第１回分割支払金</div>
					<div class="confirm-list-item-data">
						<div id="BunkatsuShiharai1Text"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="BunkatsuShiharai1">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">第２回以降分割支払金</div>
					<div class="confirm-list-item-data">
						<div id="BunkatsuShiharai2Text"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="BunkatsuShiharai2">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">支払回数</div>
					<div class="confirm-list-item-data">
						<div id="BunkatsuKaisuText"></div>
						<span class="text-kaisu">回</span>
						<input type="hidden" id="BunkatsuKaisu">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">④分割払手数料</div>
					<div class="confirm-list-item-data">
						<div id="BunkatsuTesuryoText"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="BunkatsuTesuryo">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">⑤分割支払金合計（③＋④）</div>
					<div class="confirm-list-item-data">
						<div id="BunkatsuShiharaiTotalText"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="BunkatsuShiharaiTotal">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">⑥お支払総額（②＋⑤）</div>
					<div class="confirm-list-item-data">
						<div id="TotalShiharaiText"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="TotalShiharai">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">所有権留保費用（税込）</div>
					<div class="confirm-list-item-data">
						<div id="ShoyukenRyuhoPriceText"></div>
						<span class="text-yen">円</span>
						<input type="hidden" id="ShoyukenRyuhoPrice">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">納車日区分</div>
					<div class="confirm-list-item-data">
						<div id="NoshaDateKbnText"></div>
						<input type="hidden" id="NoshaDateKbn">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">納車年月日</div>
					<div class="confirm-list-item-data">
						<div id="NoshaDateYearText"></div>
						<span class="text-year"></span>
						<div id="NoshaDateMonthText"></div>
						<span class="text-month"></span>
						<div id="NoshaDateDayText"></div>
						<span class="text-day"></span>
						<input type="hidden" id="NoshaDateYear">
						<input type="hidden" id="NoshaDateMonth">
						<input type="hidden" id="NoshaDateDay">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">販売店からの連絡事項</div>
					<div class="confirm-list-item-data">
						<div id="StoreRenrakuJikoText"></div>
						<input type="hidden" id="StoreRenrakuJiko">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">加盟店番号</div>
					<div class="confirm-list-item-data">
						<div id="StoreIdText"></div>
						<input type="hidden" id="StoreId">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">条件コード</div>
					<div class="confirm-list-item-data">
						<div id="JokenCodeText"></div>
						<input type="hidden" id="JokenCode">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">販売店</div>
					<div class="confirm-list-item-data">
						<div id="StoreNameText"></div>
						<input type="hidden" id="StoreName">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">代表者名</div>
					<div class="confirm-list-item-data">
						<div id="StoreDaihyoNameText"></div>
						<input type="hidden" id="StoreDaihyoName">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">住所</div>
					<div class="confirm-list-item-data">
						<div id="StoreAddressText"></div>
						<input type="hidden" id="StoreAddress">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">電話番号</div>
					<div class="confirm-list-item-data">
						<div id="StoreTel1Text"></div>
						<span class="text-hyphen">-</span>
						<div id="StoreTel2Text"></div>
						<span class="text-hyphen">-</span>
						<div id="StoreTel3Text"></div>
						<input type="hidden" id="StoreTel1">
						<input type="hidden" id="StoreTel2">
						<input type="hidden" id="StoreTel3">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">販売担当者氏名</div>
					<div class="confirm-list-item-data">
						<div id="StoreTantoName"></div>
						<input type="hidden" id="StoreTel3">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">販売担当者電話番号</div>
					<div class="confirm-list-item-data">
						<div id="StoreTantoTel1Text"></div>
						<span class="text-hyphen">-</span>
						<div id="StoreTantoTel2Text"></div>
						<span class="text-hyphen">-</span>
						<div id="StoreTantoTel3Text"></div>
						<input type="hidden" id="StoreTantoTel1">
						<input type="hidden" id="StoreTantoTel2">
						<input type="hidden" id="StoreTantoTel3">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">代行入力申込方法</div>
					<div class="confirm-list-item-data">
						<div id="DaikoMoshikomiKbnText"></div>
						<input type="hidden" id=DaikoMoshikomiKbn>
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">送付先名称</div>
					<div class="confirm-list-item-data">
						<div id="MccsSofusakiNameText"></div>
						<input type="hidden" id="MccsSofusakiName">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">送付先郵便番号</div>
					<div class="confirm-list-item-data">
						<div id="MccsSofusakiPostText"></div>
						<input type="hidden" id="MccsSofusakiPost">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">MCCS送付先住所１</div>
					<div class="confirm-list-item-data">
						<div id="MccsSofusakiAddress1Text"></div>
						<input type="hidden" id="MccsSofusakiAddress1">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">MCCS送付先住所２</div>
					<div class="confirm-list-item-data">
						<div id="MccsSofusakiAddress2Text"></div>
						<input type="hidden" id="MccsSofusakiAddress2">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">MCCS送付先住所３</div>
					<div class="confirm-list-item-data">
						<div id="MccsSofusakiAddress3Text"></div>
						<input type="hidden" id="MccsSofusakiAddress3">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">発送先電話番号</div>
					<div class="confirm-list-item-data">
						<div id="MccsSofusakiTel1Text"></div>
						<span class="text-hyphen">-</span>
						<div id="MccsSofusakiTel2Text"></div>
						<span class="text-hyphen">-</span>
						<div id="MccsSofusakiTel3Text"></div>
						<input type="hidden" id="MccsSofusakiTel1">
						<input type="hidden" id="MccsSofusakiTel2">
						<input type="hidden" id="MccsSofusakiTel3">

					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">受付担当者名</div>
					<div class="confirm-list-item-data">
						<div id="MccsUketsukeTantoNameText"></div>
						<input type="hidden" id="MccsUketsukeTantoName">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">受付担当者電話番号</div>
					<div class="confirm-list-item-data">
						<div id="MccsUketsukeTantoTel1Text"></div>
						<span class="text-hyphen">-</span>
						<div id="MccsUketsukeTantoTel2Text"></div>
						<span class="text-hyphen">-</span>
						<div id="MccsUketsukeTantoTel3Text"></div>
						<input type="hidden" id="MccsUketsukeTantoTel1">
						<input type="hidden" id="MccsUketsukeTantoTel2">
						<input type="hidden" id="MccsUketsukeTantoTel3">

					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">取付担当者名</div>
					<div class="confirm-list-item-data">
						<div id="MccsToritsukeTantoNameText"></div>
						<input type="hidden" id="MccsToritsukeTantoName">
					</div>
				</div>
				<div class="confirm-list-item">
					<div class="confirm-list-item-title">取付担当者電話番号</div>
					<div class="confirm-list-item-data">
						<div id="MccsToritsukeTantoTel1Text"></div>
						<span class="text-hyphen">-</span>
						<div id="MccsToritsukeTantoTel2Text"></div>
						<span class="text-hyphen">-</span>
						<div id="MccsToritsukeTantoTel3Text"></div>
						<input type="hidden" id="MccsToritsukeTantoTel1">
						<input type="hidden" id="MccsToritsukeTantoTel2">
						<input type="hidden" id="MccsToritsukeTantoTel3">

					</div>
				</div>
			</div>
		</div>

		<div class="confirm-form confirm-user">
			<div class="contents-area">
				<div class="item-block">
					<div class="items-title text-l bold">本人確認の方法</div>
					<div class="input-item hojin-view">
						<div class="text-m bold">法人番号</div>
						<div class=" grid-3">
							<input type="text" class="read-only" id="hojinNumber" value=<s:property value="hojinNumber" escapeHtml="false" /> disabled>
						</div>
					</div>
					<div class="input-item kojin-view">
						<div class="text-m bold">申込者：運転免許証番号</div>
						<div class=" grid-3">
							<input type="text" class="read-only" id="licenseNumber" value=<s:property value="licenseNumber" escapeHtml="false" /> disabled>
						</div>
					</div>
					<div class="input-item">
						<div class="text-m bold">申込者：国籍</div>
						<div class="radio-container">
							<label>
								<input type="radio" class="customer-ipt scr-ipt kojin-req hojin-req" name="moshikomi-kokuseki" value="1" />
								日本国籍
							</label>
							<label>
								<input type="radio" class="customer-ipt scr-ipt kojin-req hojin-req" name="moshikomi-kokuseki" value="2" />
								日本国籍以外
							</label>
						</div>
					</div>
					<div class="input-item">
						<div class="text-m bold">添付書類：パスポート</div>
						<div class="radio-container">
							<label>
								<input type="radio" class="attached-passport" name="attached-passport" value="1" />
								あり
							</label>
							<label>
								<input type="radio" class="attached-passport" name="attached-passport" value="0" />
								なし
							</label>
						</div>
					</div>
					<div class="input-item">
						<div class="text-m bold">添付書類：その他</div>
						<div class="width-half">
							<input type="text" class="zenkaku width-full" id="attached-other" />
						</div>
					</div>
					<div class="input-item">
						<div class="text-m bold">保証人：運転免許証番号</div>
						<div class="hankaku grid-3 digit-check">
							<input type="text" class="kojin-req with-hoshonin" id="hochonin-menkyosho">
						</div>
					</div>
					<div class="input-item">
						<div class="text-m bold">保証人：国籍</div>
						<div class="radio-container">
							<label>
								<input type="radio" class="hoshonin-kokuseki kojin-req hojin-req with-hoshonin" name="hoshonin-kokuseki" id="HoshoninKokusekiF" value="1" />
								日本国籍
							</label>
							<label>
								<input type="radio" class="hoshonin-kokuseki kojin-req hojin-req with-hoshonin" name="hoshonin-kokuseki" id="HoshoninKokusekiS" value="2" />
								日本国籍以外
							</label>
						</div>
					</div>
					<div class="scr-view">
						<div class="items-title text-l bold">本人確認など各書類のアップロード</div>
						<div class="input-item">
							<div class="text-m">
								運転免許証と必要書類をアップロードしてください。
								<br>
								運転免許証の住所変更していない場合には住民票等が、また、法人の場合には会社の謄本が必要になります。その他欄にアップロードしてください。
								<br>
								入力枠が不足している場合、画像の編集や、空いている枠へ設定をお願いいたします。 以下内容を必ず確認のうえ、各種書類をアップロードしてください。
							</div>
							<div class="sample-div">
								<div class="sample-img-div">
									<img alt="" class="sample-img" src="">
								</div>
								<div class="text-m">
									<ul class="numbered-list">
										<li>本人確認書類は有効期限内である必要があります。</li>
										<li>顔写真付きのものはマスキング等で顔が認識できない場合は本人確認書類として認められません。</li>
										<li>日本国政府発行である必要があります。</li>
										<li>ハレーション（光の映り込み）等がなく、記載内容が読み取れる画像か必ずご確認ください。</li>
										<li>最大６件までアップロードが可能です。連帯保証人の書類はその他、もしくは、空いている枠にアップロードしてください。</li>
										<li>ファイル添付は画像形式（jpeg、gif、png、bmp）もしくはpdfファイルで、上限10MBまでです。</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="scr-view file-upload">
						<div class="text-m bold">本人確認書類</div>
						<label class="file-upload-item store-ipt scr-ipt kojin-req">
							<input type="file" class="file-upload-input" id="file-lisence-front">
							<span class="file-upload-body">
								<span class="file-upload-icon i-plus">＋</span>
								<span class="file-upload-label">①免許証（表）</span>
							</span>
						</label>
						<label class="file-upload-item store-ipt scr-ipt">
							<input type="file" class="file-upload-input" id="file-lisence-back">
							<span class="file-upload-body">
								<span class="file-upload-icon i-plus">＋</span>
								<span class="file-upload-label">②免許証（裏）</span>
							</span>
						</label>
						<label class="file-upload-item store-ipt scr-ipt">
							<input type="file" class="file-upload-input" id="file-shunyu-shomei">
							<span class="file-upload-body">
								<span class="file-upload-icon i-plus">＋</span>
								<span class="file-upload-label">③収入証明書類</span>
							</span>
						</label>
						<label class="file-upload-item store-ipt scr-ipt">
							<input type="file" class="file-upload-input" id="file-zairyu-card-front">
							<span class="file-upload-body">
								<span class="file-upload-icon i-plus">＋</span>
								<span class="file-upload-label">④在留カード（表）</span>
							</span>
						</label>
						<label class="file-upload-item store-ipt scr-ipt">
							<input type="file" class="file-upload-input" id="file-zairyu-card-back">
							<span class="file-upload-body">
								<span class="file-upload-icon i-plus">＋</span>
								<span class="file-upload-label">⑤在留カード（裏）</span>
							</span>
						</label>
						<label class="file-upload-item store-ipt scr-ipt">
							<input type="file" class="file-upload-input" id="file-other">
							<span class="file-upload-body">
								<span class="file-upload-icon i-plus">＋</span>
								<span class="file-upload-label hojin-req">⑥その他</span>
							</span>
						</label>
					</div>
					<div class="file-upload">
						<div class="text-m bold">連帯保証人確認書類</div>
						<label class="file-upload-item hoshonin-req">
							<input type="file" class="file-upload-input" id="file-h-lisence-front">
							<span class="file-upload-body">
								<span class="file-upload-icon i-plus">＋</span>
								<span class="file-upload-label">⑦免許証（表）</span>
							</span>
						</label>
						<label class="file-upload-item">
							<input type="file" class="file-upload-input" id="file-h-lisence-back">
							<span class="file-upload-body">
								<span class="file-upload-icon i-plus">＋</span>
								<span class="file-upload-label">⑧免許証（裏）</span>
							</span>
						</label>
						<label class="file-upload-item">
							<input type="file" class="file-upload-input" id="file-h-shunyu-shomei">
							<span class="file-upload-body">
								<span class="file-upload-icon i-plus">＋</span>
								<span class="file-upload-label">⑨収入証明書類</span>
							</span>
						</label>
						<label class="file-upload-item">
							<input type="file" class="file-upload-input" id="file-h-zairyu-card-front">
							<span class="file-upload-body">
								<span class="file-upload-icon i-plus">＋</span>
								<span class="file-upload-label">⑩在留カード（表）</span>
							</span>
						</label>
						<label class="file-upload-item">
							<input type="file" class="file-upload-input" id="file-h-zairyu-card-back">
							<span class="file-upload-body">
								<span class="file-upload-icon i-plus">＋</span>
								<span class="file-upload-label">⑪在留カード（裏）</span>
							</span>
						</label>
						<label class="file-upload-item">
							<input type="file" class="file-upload-input" id="file-h-other">
							<span class="file-upload-body">
								<span class="file-upload-icon i-plus">＋</span>
								<span class="file-upload-label">⑫その他</span>
							</span>
						</label>
					</div>
					<div class="main-view" style="display: contents;">
						<div class="items-title text-l bold">本申込の前にご確認ください</div>
						<div class="grid-2">
							<button type="button" class="btn main" id="">USS-SSオートローン申込書</button>
							<button type="button" class="btn main" id="">MCCS付オートローン承諾書</button>
						</div>
						<div class="border-area with-mccs-view">
							<div class="checkbox-div" id="">
								<label>
									<span class="checkbox-div-checkbox">
										<input type="checkbox" class="" id="checkbox-baibaikeiyakubi" />
									</span>
									<span class="checkbox-div-text">MCCSを取り付ける場合、MCCS付オートローン承諾書の内容に同意します。</span>
								</label>
							</div>
						</div>
						<div class="border-area">
							<div class="checkbox-div" id="">
								<label>
									<span class="checkbox-div-checkbox">
										<input type="checkbox" class="" id="checkbox-baibaikeiyakubi" />
									</span>
									<span class="checkbox-div-text">契約内容を確認しました。こちらの内容で契約を申し込みます。</span>
								</label>
							</div>
						</div>
					</div>
					<div class="input-item main-view">
						<div class="input-item">
							<div class="text-m bold">申込者様のサイン</div>
							<div class="sign-area border-area sign-modal-open"></div>
						</div>
						<div class="input-item with-hoshonin-view">
							<div class="text-m bold">連帯保証人様のサイン</div>
							<div class="sign-area border-area sign-modal-h-open"></div>
						</div>
						<div class="text-m">
							この署名は、USS-SSオートローンの申込者および連帯保証人予定者が、其々、本人の意思に基づいて、申込みもしくは申込者の債務の保証の意思を表明する目的で署名するもので、何人も代行してサインをすることはできません。
							<br>
							代行して署名すると私文書偽造の罪に問われることがございます。
							<br>
							この契約お申込みに無関係な方にこのご案内があった場合は直ちに弊社USS-SSオートローン係(TEL 03-3276-7505)までご連絡ください。
						</div>
						<div class="text-m">
							この署名は、USS-SSオートローンの申込者および連帯保証人予定者が、其々、本人の意思に基づいて、申込みもしくは申込者の債務の保証の意思を表明する目的で署名するもので、何人も代行してサインをすることはできません。
							<br>
							代行して署名すると私文書偽造の罪に問われることがございます。
							<br>
							この契約お申込みに無関係な方にこのご案内があった場合は直ちに弊社USS-SSオートローン係(TEL 03-3276-7505)までご連絡ください。
						</div>
					</div>
					<div class="btn-area main-view">
						<button type="button" class="btn main resend-modal-open store-view change-input-customer" id="change-input-mode_">顧客入力に切り替えて送信</button>
					</div>

					<div class="items-title text-l bold">お申込み上のご注意</div>
					<div class="input-item">
						<div class="text-m">
							<ul class="">
								<li>この契約はお客様自身のものです。お申込みの際は、お申込入力の内容のご確認、および別掲の書類を熟読、理解し、ご納得したうえで、画面上でお名前を自署してお申込みください。名義貸しは絶対にやめましょう。</li>
								<li>お申込みいただいた内容について数日中にクレジット会社が確認の電話を差し上げます。</li>
								<li>お申込み後は必ず書類を印刷してください。この「お申込み内容」は、①オートローン契約の申込時に信用調査のための承諾書面となり、②オートローン契約成立後は割賦販売法第35条の3の8の一部の規定に基づく書面となりますので、大切に保管してください。</li>
								<li>このお申込みは商品・役務欄に記載された取引の代金決済のためのものです。記載内容以外の取引や約作はないことを確認してください。万一、クレジット会社の関与なくお客様が記載内容以外の取引や約束を販売店と結んでもクレジット会社とのオートローン契約には何の影響も及ぼしません。</li>
							</ul>
						</div>
						<div class="btn-area store-view">
							<button type="button" class="btn main app-confirm" id="app-confirm-store">申し込む</button>
						</div>
						<div class="btn-area cstmr-view">
							<button type="button" class="btn main app-confirm" id="app-confirm-customer">登録する</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>