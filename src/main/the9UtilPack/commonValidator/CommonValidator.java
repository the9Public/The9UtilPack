package the9UtilPack.commonValidator;

import java.util.ArrayList;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

import the9UtilPack.CommonLibrary;


public class CommonValidator {

	/**
	 * <PRE>
	 * </PRE>
	 * 
	 */
	public CommonValidator() {

		// TODO Auto-generated constructor stub
	}

	/**
	 * <PRE>
	 * 	yyyy-mm-dd 형의 데이트 스트링인지 검증
	 * </PRE>
	 * 
	 * @param  strData   : 검증대상 문자열
	 * @return
	 * @throws Exception
	 */
	static public CommonValidatorResult isDateString(String strData) throws Exception {

		CommonValidatorResult result = new CommonValidatorResult();

		if ( Pattern.matches("\\d{4}-\\d{2}-\\d{2}", strData) ) {
			result = commonDateStringFormatCheck(strData);
		} else {
			result.setResultSet(false, "데이터의 형식이 올바르지 않습니다. yyyy-MM-dd 형식으로 입력해주세요." + "\n", "0004");
		}

		return result;

	}

	/**
	 * <PRE>
	 * 	러프한 형식의 데이터 스트링 검증
	 *  다음의 형식으로 시작하는 모든 문자열...
	 *   yyyy.mm.dd
	 *   yyyy.mm-dd
	 *   yyyy-mm-dd
	 *   yyyy-mm.dd
	 *   yyyymmdd
	 *  
	 *  	ex)
	 *  		2011.01.01 24:03:02.001 -- True
	 *  		2013-05-01 AM 08:23:54	-- True
	 *  		05-MAR-01				-- False
	 * 
	 * </PRE>
	 * 
	 * @param  strData   : 검증대상 문자열
	 * @return
	 * @throws Exception
	 */
	static public CommonValidatorResult isDateStringRough(String strData) {

		CommonValidatorResult result = new CommonValidatorResult();

		if ( Pattern.matches("^(\\d{4}[\\.\\-]*\\d{2}[\\.\\-]*\\d{2}).*", strData) ) {
			// strData = strData.replace(".", "-").substr.substring(0, 10);
			// result = commonDateStringFormatCheck(strData);
		} else {
			result.setResultSet(false, "데이터의 형식이 올바르지 않습니다. yyyy-MM-dd 혹은 yyyy.mm.dd 형식으로 입력해주세요." + "\n", "0004");
		}

		return result;

	}

	/**
	 * <PRE>
	 * 	정수 타입의 문자열인지 검증합니다.
	 * 	Ex)
	 * 		-1241		True
	 * 		1591000		True
	 * 		123.50		false
	 * 		1,000,201	false
	 * 
	 * </PRE>
	 * 
	 * @param  strData
	 * @return
	 */
	static public CommonValidatorResult isIntegerString(String strData) {

		CommonValidatorResult result = new CommonValidatorResult();

		if ( !Pattern.matches("^[0-9|-][0-9]*$", strData) && !strData.equals("0") ) {
			result.setResultSet(false, "데이터의 형식이 올바르지 않습니다. 올바른 숫자 형식으로 입력해주세요." + "\n", "0004");
		}

		return result;

	}

	/**
	 * <PRE>
	 * 	정수 타입의 문자열인지 검증합니다.
	 *  빈문자열은 0으로서 취급하여 통과 시킵니다.
	 * </PRE>
	 * 
	 * -1241 True 1591000 True 123.50 false 1,000,201 false "" true
	 * 
	 * @param  strData
	 * @return
	 */
	static public CommonValidatorResult isIntegerStringAllowEmptyString(String strData) {

		CommonValidatorResult result = new CommonValidatorResult();

		if ( CommonLibrary.isNullOrEmpty(strData) ) {
			return result;
		}

		return isIntegerString(strData);

	}

	/**
	 * <PRE>
	 * 정수 타입의 문자열인지 검증합니다.
	 * 검증시 해당 숫자의 길이도 체크합니다.
	 * 	Ex)
	 * 		-1241		True
	 * 		1591000		True
	 * 		123.50		false
	 * 		1,000,201	false
	 * </PRE>
	 * 
	 * @param  strData
	 * @param  allowLength
	 * @return
	 */
	static public CommonValidatorResult isIntegerString(String strData, int allowLength) {

		CommonValidatorResult result = isIntegerString(strData);

		if ( result.isOk() && strData.length() > allowLength ) {
			result.setResultSet(false, "숫자가 너무큽니다. " + allowLength + " 자릿수 이하로 입력해 주세요." + "\n", "0006");
		}

		return result;

	}

	/**
	 * <PRE>
	 * 	정수 타입의 문자열인지 검증합니다.
	 *  빈문자열은 0으로서 취급하여 통과 시킵니다.
	 *  검증시 해당 숫자의 길이도 체크합니다.
	 * </PRE>
	 * 
	 * -1241 True 1591000 True 123.50 false 1,000,201 false "" true
	 * 
	 * @param  strData
	 * @return
	 */
	static public CommonValidatorResult isIntegerStringAllowEmptyString(String strData, int allowLength) {

		CommonValidatorResult result = new CommonValidatorResult();

		if ( CommonLibrary.isNullOrEmpty(strData) ) {
			return result;
		}

		return isIntegerString(strData, allowLength);

	}

	/**
	 * <PRE>
	 * 	사업자번호여부판단 ( 대쉬바 없음 )
	 * </PRE>
	 * 
	 * @param  strData
	 * @return
	 */
	static public CommonValidatorResult isBizrNo_noDash(String strData) {

		CommonValidatorResult result = new CommonValidatorResult();

		if ( ( !Pattern.matches("\\d{10}", strData) ) || ( CommonLibrary.isNullOrEmpty(strData) ) ) {
			result.setResultSet(false, "데이터의 형식이 올바르지 않습니다. ########## (10자리 숫자)형식으로 입력해주세요." + "\n", "0004");
		}

		return result;

	}

	/**
	 * <PRE>
	 * 	사업자번호여부판단 ( 대쉬바 포함 )
	 * </PRE>
	 * 
	 * @param  strData
	 * @return
	 */
	static public CommonValidatorResult isBizrNo_withDash(String strData) {

		CommonValidatorResult result = new CommonValidatorResult();

		if ( ( !Pattern.matches("\\d{3}-\\d{2}-\\d{5}", strData) ) || ( CommonLibrary.isNullOrEmpty(strData) ) ) {
			result.setResultSet(false, "데이터의 형식이 올바르지 않습니다. ###-##-##### (10자리 숫자)형식으로 입력해주세요." + "\n", "0004");
		}

		return result;

	}

	/**
	 * <PRE>
	 * 	기본적인 날짜 형식의 포맷을 확인한다.
	 * </PRE>
	 * 
	 * @param  strDate
	 * @return
	 */
	static private CommonValidatorResult commonDateStringFormatCheck(String strDate) {

		// 승인일 예약 변수

		CommonValidatorResult result = new CommonValidatorResult();
		String strDte[];// 승인일
		ArrayList<Integer> listSmallMonth = new ArrayList<Integer>();
		listSmallMonth.add(4);
		listSmallMonth.add(6);
		listSmallMonth.add(9);
		listSmallMonth.add(11);
		ArrayList<Integer> listBigMonth = new ArrayList<Integer>();
		listBigMonth.add(1);
		listBigMonth.add(3);
		listBigMonth.add(5);
		listBigMonth.add(7);
		listBigMonth.add(8);
		listBigMonth.add(10);
		listBigMonth.add(12);

		strDte = strDate.split("-");
		int nYear = Integer.parseInt(strDte[0]);
		int nMonth = Integer.parseInt(strDte[1]);
		int nDay = Integer.parseInt(strDte[2]);

		if ( nMonth > 12 || nMonth < 1 ) {
			result.setResultSet(false, "승인일의 월의 범위(입력값:" + nMonth + ")가 잘못되었습니다." + "\n", "1100");
		} else if ( nDay < 1 ) {
			result.setResultSet(false, "승인일의 일자(" + nDay + ")가 (1)보다 작습니다." + "\n", "1101");
		} else if ( ( nMonth == 2 && nDay > 28 && nYear % 4 != 0 ) || ( nMonth == 2 && nDay > 29 && nYear % 4 == 0 )
			|| ( listSmallMonth.indexOf(nMonth) > -1 && nDay > 30 ) || ( listBigMonth.indexOf(nMonth) > -1 && nDay > 31 ) ) {
			result.setResultSet(false, "승인일의 일자(" + nDay + ")가 해당년의 해당월(" + nYear + "-" + nMonth + ")에 존재 하지 않는 일자 입니다." + "\n", "1102");
		}

		return result;

	}

	public static boolean isValidJuminNo(String input) {

		input = StringUtils.defaultString(input);

		if ( input.length() != 13 ) {
			return false;
		}

		if ( !StringUtils.isNumeric(input) ) {
			return false;
		}

		// 외국인은 패스
		int i = Integer.parseInt(input.substring(6, 7));

		if ( i >= 5 && i <= 8 ) {
			return true;
		}

		// 입력받은 주민번호 앞자리 유효성 검증============================
		String leftSid = input.substring(0, 6);
//		String rightSid = input.substring(6, 13);

		int yy = Integer.parseInt(leftSid.substring(0, 2));
		int mm = Integer.parseInt(leftSid.substring(2, 4));
		int dd = Integer.parseInt(leftSid.substring(4, 6));

		if ( yy < 1 || yy > 99 || mm > 12 || mm < 1 || dd < 1 || dd > 31 ) {
			return false;
		}

		// 사업부 요청으로 해당 검증 로직 제거 deleted by yunhee (req by repay2) 2020.11.12
		/*
		 * int digit1 = Integer.parseInt(leftSid.substring(0, 1)) * 2; int digit2 = Integer.parseInt(leftSid.substring(1, 2)) * 3; int digit3 = Integer.parseInt(leftSid.substring(2, 3)) * 4; int digit4 = Integer.parseInt(leftSid.substring(3, 4)) * 5;
		 * int digit5 = Integer.parseInt(leftSid.substring(4, 5)) * 6; int digit6 = Integer.parseInt(leftSid.substring(5, 6)) * 7;
		 * 
		 * int digit7 = Integer.parseInt(rightSid.substring(0, 1)) * 8; int digit8 = Integer.parseInt(rightSid.substring(1, 2)) * 9; int digit9 = Integer.parseInt(rightSid.substring(2, 3)) * 2; int digit10 = Integer.parseInt(rightSid.substring(3, 4))
		 * * 3; int digit11 = Integer.parseInt(rightSid.substring(4, 5)) * 4; int digit12 = Integer.parseInt(rightSid.substring(5, 6)) * 5;
		 * 
		 * int last_digit = Integer.parseInt(rightSid.substring(6, 7));
		 * 
		 * int error_verify = (digit1 + digit2 + digit3 + digit4 + digit5 + digit6 + digit7 + digit8 + digit9 + digit10 + digit11 + digit12) % 11;
		 * 
		 * int sum_digit = 0; if (error_verify == 0) { sum_digit = 1; } else if (error_verify == 1) { sum_digit = 0; } else { sum_digit = 11 - error_verify; }
		 * 
		 * if (last_digit == sum_digit) { return true; } return false;
		 */
		return true;

	}

	/**
	 * <p>
	 * XXXXXX - XXXXXXX 형식의 법인번호 앞, 뒤 문자열 2개 입력 받아 유효한 법인번호인지 검사.
	 * </p>
	 * 
	 * 
	 * @param  6자리 법인앞번호 문자열 , 7자리 법인뒷번호 문자열
	 * @return     유효한 법인번호인지 여부 (True/False)
	 */
	public static boolean checkBubinNumber(String bubin1, String bubin2) {

		String bubinNumber = bubin1 + bubin2;

		int hap = 0;
		int temp = 1; // 유효검증식에 사용하기 위한 변수

		if ( bubinNumber.length() != 13 ) {
			return false; // 법인번호의 자리수가 맞는 지를 확인
		}

		for ( int i = 0; i < 13; i++ ) {

			if ( bubinNumber.charAt(i) < '0' || bubinNumber.charAt(i) > '9' ) {
				return false;
			}

		}

		// 2012.02.27 법인번호 체크로직 수정( i<13 -> i<12 )
		// 맨끝 자리 수는 전산시스템으로 오류를 검증하기 위해 부여되는 검증번호임
		for ( int i = 0; i < 12; i++ ) {

			if ( temp == 3 ) {
				temp = 1;
			}

			hap = hap + ( Character.getNumericValue(bubinNumber.charAt(i)) * temp );
			temp++ ;
		} // 검증을 위한 식의 계산

		if ( ( 10 - ( hap % 10 ) ) % 10 == Character.getNumericValue(bubinNumber.charAt(12)) ) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * <p>
	 * XXXXXXXXXXXXX 형식의 13자리 법인번호 1개를 입력 받아 유효한 법인번호인지 검사.
	 * </p>
	 * 
	 * 
	 * @param  13자리 법인번호 문자열
	 * @return      유효한 법인번호인지 여부 (True/False)
	 */
	public static boolean checkBubinNumber(String bubin) {

		if ( bubin.length() != 13 ) {
			return false;
		}

		return checkBubinNumber(bubin.substring(0, 6), bubin.substring(6, 13));

	}

	/**
	 * <p>
	 * XXX - XX - XXXXX 형식의 사업자번호 앞,중간, 뒤 문자열 3개 입력 받아 유효한 사업자번호인지 검사.
	 * </p>
	 * 
	 * 
	 * @param  3자리 사업자앞번호 문자열 , 2자리 사업자중간번호 문자열, 5자리 사업자뒷번호 문자열
	 * @return     유효한 사업자번호인지 여부 (True/False)
	 */
	public static boolean checkCompNumber(String comp1, String comp2, String comp3) {

		String compNumber = comp1 + comp2 + comp3;

		int hap = 0;
		int temp = 0;
		int check[] = {
			1, 3, 7, 1, 3, 7, 1, 3, 5
		}; // 사업자번호 유효성 체크 필요한 수

		if ( compNumber.length() != 10 ) {
			return false;
		}

		for ( int i = 0; i < 9; i++ ) {

			if ( compNumber.charAt(i) < '0' || compNumber.charAt(i) > '9' ) {
				return false;
			}

			hap = hap + ( Character.getNumericValue(compNumber.charAt(i)) * check[temp] ); // 검증식
																							// 적용
			temp++ ;
		}

		hap += ( Character.getNumericValue(compNumber.charAt(8)) * 5 ) / 10;

		if ( ( 10 - ( hap % 10 ) ) % 10 == Character.getNumericValue(compNumber.charAt(9)) ) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * <p>
	 * XXXXXXXXXX 형식의 10자리 사업자번호 3개를 입력 받아 유효한 사업자번호인지 검사.
	 * </p>
	 * 
	 * 
	 * @param  10자리 사업자번호 문자열
	 * @return      유효한 사업자번호인지 여부 (True/False)
	 */
	public static boolean checkCompNumber(String comp) {

		if ( comp.length() != 10 ) {
			return false;
		}

		return checkCompNumber(comp.substring(0, 3), comp.substring(3, 5), comp.substring(5, 10));

	}

}
