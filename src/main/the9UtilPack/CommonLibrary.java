package the9UtilPack;

import java.security.SecureRandom;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import the9UtilPack.commonValidator.CommonValidator;


public class CommonLibrary {

	public static void main(String[] args) {

		// Watch Expression Point
//		String strTmp = "";

//		System.out.println(getHumanReadableTmUnit(11506605L));

	}

	/**
	 * 
	 * <PRE>
	 * 	오브젝트 연결이있나를 보고 있다면 널 혹은 빈값인가를 살핀다.
	 * 값이 정상이라면 false를 반환함 (C#기능참조)
	 * </PRE>
	 *
	 * @param  string : 인풋스트링
	 * @return        : 값이 있으면 거짓(false) 없거나 빈값이면 참(true)
	 */
	public static boolean isNullOrEmpty(String string) {

		if ( string != null )
			if ( !string.equals(null) )
				if ( !string.equals("") )
					return false;
		return true;

	}

	/**
	 * <PRE>
	 * 	스트링값이 비었다면 대체 스트링을 넣어 줌
	 * </PRE>
	 *
	 * @param  srcString  : 원본텍스트 (공값인지 확인)
	 * @param  destString : 만약 비었다면 넣어줄 값
	 * @return
	 */
	public static String stringNvl(String srcString, String destString) {

		if ( isNullOrEmpty(srcString) ) {
			return destString;
		} else {
			return srcString;
		}

	}

	/**
	 * <PRE>
	 * 지정된 바이트크기로 문자열을 잘라냅니다.
	 * </PRE>
	 *
	 * @param  strOriginText : 입력 문자열
	 * @param  nLength       : 잘라낼 바이트 크기
	 * @return               : 원본의 문자열 혹은 잘려진 문자열
	 */
	public static String strByteCutter(String strOriginText, int nLength) {

		byte[] bt = strOriginText.getBytes();
		int nLengthL = nLength;

		if ( bt.length > nLengthL ) {
			int minusByteCount = 0;
			CharSequence csOrigin = strOriginText;

			for ( int i = 0; i < csOrigin.length(); i++ ) {
				Character chOrigin = csOrigin.charAt(i);
				minusByteCount += String.valueOf(chOrigin).getBytes().length;
				if ( minusByteCount >= nLengthL )
					break;
			}

			if ( minusByteCount >= nLengthL ) {
				int nFinalCharLength = 0;

				CharSequence csResult = new String(bt, 0, nLengthL);

				for ( int i = 0; i < Math.min(csOrigin.length(), csResult.length()); i++ ) {
					Character chOrigin = csOrigin.charAt(i);
					Character chResult = csResult.charAt(i);

					if ( !chOrigin.equals(chResult) ) {

						if ( chResult <= 0x7f ) {
							nFinalCharLength = 1;
						} else if ( chResult <= 0x7ff ) {
							nFinalCharLength = 2;
						} else if ( Character.isHighSurrogate(chResult) ) {
							nFinalCharLength = 4;
						} else {
							nFinalCharLength = 3;
						}

						break;
					}

				}

				nLengthL = minusByteCount - nFinalCharLength;
			}

			String strR = new String(bt, 0, nLengthL);
			return strR;
		} else {
			return strOriginText;
		}

	}

	/**
	 * <PRE>
	 * 오브젝트값을 진수전환 합니다. Radix 진수화 됩니다.
	 * </PRE>
	 *
	 * @param  obj   : 변환대상입니다.
	 * @param  radix : 변환할 진수값입니다.
	 * @return       radix 진수치환된 문자열입니다.
	 */
	public static String objectToRadixString(Object obj, int radix) {

		return Long.toString(Long.valueOf(String.valueOf(obj)), radix);

	}

	/**
	 * <PRE>
	 * 문자열을 진수변환합니다. Radix진수를 실수로 복구 합니다.
	 * </PRE>
	 *
	 * @param  source : 대상문자열립니다.
	 * @param  radix  : 복구할 진수입니다.
	 * @return        radix를 참조하여 진수복구 합니다.
	 */
	public static Object radixStringToObject(String source, int radix) {

		return Long.parseLong(source, radix);

	}

	/**
	 * <PRE>
	 * 임의의 숫자로 이루어진 문자열을 생성합니다.
	 * </PRE>
	 *
	 * @param  length : 반환받을 문자의 길이입니다.
	 * @return        숫자로 이루어진 문자열을 반환합니다.
	 */
	public static String randomNumberString(int length) {

		length = length < 1 ? 1 : length;
		// 32bit 롱 최대 자릿수 -1 ( 99999.... ) 까지 나올수 없으므로...
		// 64bit 컴파일시는 알맞게 변경할것..
		int nLongMaxLength = 18;

		String strBuff = "";
		int loopMaxCnt = (int) Math.ceil((double) length / (double) nLongMaxLength);

//		System.out.println("randomNumberString(loopMaxCnt) : " + loopMaxCnt);

		for ( int masterLoopCnt = 0; masterLoopCnt < loopMaxCnt; masterLoopCnt++ ) {
			String strMAX_SEQ = "";

			for ( int i = 0; i < nLongMaxLength; i++ ) {
				strMAX_SEQ += "9";
			}

			SecureRandom sRnd = new SecureRandom();
			long lValue = sRnd.nextLong();
			String strRn;

			lValue = lValue < 0 ? lValue * -1 : lValue;

			if ( Long.valueOf(strMAX_SEQ) < lValue ) {
				lValue = Long.valueOf(String.valueOf(lValue).substring(0, nLongMaxLength));
			}

			strRn = String.valueOf(lValue);
			char chArray[] = strRn.toCharArray();
			String strRst = "";
			int nArrayLength = chArray.length;

			for ( int i = 0; i < nLongMaxLength; i++ ) {
				char ch1 = ' ';
				if ( nArrayLength > i )
					ch1 = chArray[i];
				if ( ch1 == ' ' )
					ch1 = '0';
				strRst += String.valueOf(ch1);
			}

//			System.out.println("randomNumberString(strRst) [ " + masterLoopCnt + " ] : " + strRst);

			strBuff = strBuff.concat(strRst);
		}

//		System.out.println("randomNumberString(Original) : " + strBuff);

		return strBuff.substring(0, length);

	}

	/**
	 * 
	 * <PRE>
	 * 	0~9, A~Z 의 랜덤 문자를 출력합니다.
	 * </PRE>
	 *
	 * @param  length : 반환받기 원하는 길이를 입력합니다.
	 * @return
	 */
	public static String randomAlphabetNumberCombineString(int length) {

		SecureRandom rnd = new SecureRandom();

		StringBuffer strBuffer = new StringBuffer();

		for ( int lCnt = 0; length > lCnt; lCnt++ ) {
			long lValue = Long.valueOf(String.valueOf(rnd.nextInt(36)));
			strBuffer.append( ( Long.toString(lValue, 36) ));
		}

//		System.out.println("strBuffer(Original) : " + strBuffer);
		return strBuffer.toString();

	}

	/**
	 * <PRE>
	 * 	0~9, A~Z, a-z 의 랜덤 문자를 출력합니다.
	 * </PRE>
	 * 
	 * @param  length
	 * @return
	 */
	public static String randomAlphabetNumberCombineWithCaseString(int length) {

		SecureRandom rnd = new SecureRandom();

		String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		StringBuilder strBuilder = new StringBuilder();

		for ( int lCnt = 0; length > lCnt; lCnt++ ) {
			int index = rnd.nextInt(charPool.length());
			strBuilder.append(charPool.charAt(index));
		}

		return strBuilder.toString();

	}

	/**
	 * <pre>
	 * 	풀스펙트럼 랜덤 문자열
	 * </pre>
	 * 
	 * @param  length
	 * @return
	 */
	public static String randomFullSpectrumString(int length) {

		SecureRandom rnd = new SecureRandom();

		String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\\\"#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}~";

		StringBuilder strBuilder = new StringBuilder();

		for ( int lCnt = 0; length > lCnt; lCnt++ ) {
			int index = rnd.nextInt(charPool.length());
			strBuilder.append(charPool.charAt(index));
		}

		return strBuilder.toString();

	}

	/**
	 * <PRE>
	 * 	문자열을 지정된 형식에 따라 Date형변환 합니다.
	 * </PRE>
	 *
	 * @param  sourceString      : 변환하고자하는 문자열
	 * @param  dateFormateString : 날짜 포맷형식
	 * @return
	 * @throws ParseException    : 형변환 실패시 반환
	 */
	public static Date stringToDate(String sourceString, String dateFormateString) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormateString);
		return sdf.parse(sourceString);

	}

	/**
	 * <PRE>
	 * 	전달되는 DATE 타입을 요청된 포맷에 맞춰 반환
	 * </PRE>
	 *
	 * @param  date             : 변환 대상 DATE 객체
	 * @param  dateFormatString : 변환할 포맷 ex) yyyy.MM.dd HH:mm:ss
	 * 
	 *                          G Era designator Text AD
	 *                          y Year Year 1996; 96
	 *                          Y Week year Year 2009; 09
	 *                          M Month in year (context sensitive) Month July; Jul; 07
	 *                          L Month in year (standalone form) Month July; Jul; 07
	 *                          w Week in year Number 27
	 *                          W Week in month Number 2
	 *                          D Day in year Number 189
	 *                          d Day in month Number 10
	 *                          F Day of week in month Number 2
	 *                          E Day name in week Text Tuesday; Tue
	 *                          u Day number of week (1 = Monday, ..., 7 = Sunday) Number 1
	 *                          a Am/pm marker Text PM
	 *                          H Hour in day (0-23) Number 0
	 *                          k Hour in day (1-24) Number 24
	 *                          K Hour in am/pm (0-11) Number 0
	 *                          h Hour in am/pm (1-12) Number 12
	 *                          m Minute in hour Number 30
	 *                          s Second in minute Number 55
	 *                          S Millisecond Number 978
	 *                          z Time zone General time zone Pacific Standard Time; PST; GMT-08:00
	 *                          Z Time zone RFC 822 time zone -0800
	 *                          X Time zone ISO 8601 time zone -08; -0800; -08:00
	 * 
	 * 
	 * @return
	 */
	public static String dateToString(Date date, String dateFormatString) {

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatString);
		return sdf.format(date);

	}

	/**
	 * 
	 * <PRE>
	 *  요청 년월의 마지막 일자의 Date 값 반환
	 * </PRE>
	 *
	 * @param  date : 요청 년월의 마지막 일자
	 * @return
	 */
	public static Date getLastDayOfDate(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// c.add(Calendar.MONTH, 1);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
		return new Date(c.getTimeInMillis());

	}

	/**
	 * 
	 * <PRE>
	 *  주민등록번호 체크
	 * </PRE>
	 *
	 * @param  ctzNo
	 * @return
	 */
	public static boolean isValidCtzNo(String ctzNo) {

		// 길이체크
		if ( !Pattern.matches("\\d{13}", ctzNo) )
			return false;

		String strNo1 = ctzNo.substring(0, 6);
		String strNo2 = ctzNo.substring(6, 13);

		int nCheckNo = 11;

		if ( Pattern.matches("[5-8]", strNo2.substring(0, 1)) ) {
			nCheckNo = 13;
		}

		String strCtNo = "18";

		if ( Pattern.matches("[1-2]", strNo2.substring(0, 1)) || Pattern.matches("[5-6]", strNo2.substring(0, 1)) ) {
			strCtNo = "19";
		} else if ( Pattern.matches("[3-4]", strNo2.substring(0, 1)) || Pattern.matches("[7-8]", strNo2.substring(0, 1)) ) {
			strCtNo = "20";
		}

		String strNo1_1 = strCtNo + strNo1.substring(0, 2) + "-" + strNo1.substring(2, 4) + "-" + strNo1.substring(4, 6);

		try {
			// 앞자리 날짜형식 체크
			if ( !CommonValidator.isDateString(strNo1_1).isOk() )
				return false;
		} catch ( Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int nVpm[] = {
			2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5
		};
		int nValue = 0;

		for ( int i = 0; i < ctzNo.length() - 1; i++ ) {
			String strOneString = String.valueOf(ctzNo.toCharArray()[i]);
			nValue += ( Integer.parseInt(strOneString) * nVpm[i] );
		}

		nValue = ( nCheckNo - ( nValue % 11 ) ) % 10;

		return ctzNo.substring(12, 13).equals(String.valueOf(nValue));

	}

	/**
	 * 
	 * <PRE>
	 *  멀티바이트를 지정된 크기로 정하여 Substring 처리후 반환
	 * </PRE>
	 *
	 * @param  text                   : 자를 String
	 * @param  startPosition          : 시작글자 위치
	 * @param  endPosition            : 종료 바이트수
	 * @param  multiByteLengthReplace : 멀티바이트의 바이트를 취급할 수
	 * @return
	 */
	public static String substrByteChgMbLength(String text, int startPosition, int endPosition, int multiByteLengthReplace) {

		text = StringUtils.substring(text, startPosition);
		int nPos = 0;
		StringBuilder sb = new StringBuilder();

		while ( text.length() > 0 && nPos < endPosition ) {
			String t = text.substring(0, 1);
			text = text.substring(1);
			int tL = t.getBytes().length;
			int l = ( tL > 1 ? multiByteLengthReplace : tL );
			nPos += l;
			if ( nPos > endPosition )
				return sb.toString();

			sb.append(t);
		}

		return sb.toString();

	}

	/**
	 * 
	 * <PRE>
	 * 	텍스트에 포함된 마지막 숫자형태의 문자 획득
	 * </PRE>
	 *
	 * @param  text
	 * @return
	 */
	public static String getLastNumberString(String text) {

		if ( text == null )
			return "";

		Matcher matcher = Pattern.compile("-?\\d+\\.?\\d*").matcher(text.replace(",", ""));
		String strFMatch = "";

		while ( matcher.find() ) {
			strFMatch = matcher.group();
		}

		strFMatch = strFMatch.replaceAll("\\.$", "");
		return strFMatch;

	}

	/**
	 * <pre>
	 * 전각 문자를 반각 문자로 치환
	 * </pre>
	 *
	 * @param  text : 대상 문자열
	 * @return
	 */
	public static String textToHalf(String text) {

		if ( text == null ) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		String normalized = Normalizer.normalize(text, Normalizer.Form.NFKC);

		for ( int i = 0; i < normalized.length(); i++ ) {
			char c = normalized.charAt(i);

			if ( c >= 65281 && c <= 65374 ) {
				sb.append((char) ( c - 65248 ));
			} else {
				sb.append(c);
			}

		}

		return sb.toString();

	}

	/**
	 * <pre>
	 * 반각 문자를 전각문자로 치환
	 * </pre>
	 *
	 * @param  text
	 * @return
	 */
	public static String textToFull(String text) {

		if ( text == null ) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		String normalized = Normalizer.normalize(text, Normalizer.Form.NFKC);

		for ( int i = 0; i < normalized.length(); i++ ) {
			char c = normalized.charAt(i);

			if ( c >= 33 && c <= 126 ) {
				sb.append((char) ( c + 65248 ));
			} else if ( c == 32 ) { // 공백은 따로 처리
				sb.append('\u3000');
			} else {
				sb.append(c);
			}

		}

		return sb.toString();

	}

	/**
	 * 
	 * <pre>
	 *	Map 의 값을 요청 바이트수로 커팅
	 * </pre>
	 *
	 * @param  m     : 대상맵
	 * @param  key   : 대상키
	 * @param  nSize : 자를 바이트수
	 * @return       : 요청 map + String 으로 잘려진 값 (String 으로 치환됨)
	 */
	public static Map<String, String> mapValueByteCutter(Map<String, String> m, String key, int nSize) {

		m.put(key, strByteCutter(Objects.toString(m.get(key)), nSize));
		return m;

	}

	/**
	 * <pre>
	 * 	${변수명} 으로 작성된 포맷 문자열을 데이터 바인딩 처리
	 * </pre>
	 * 
	 * @param  template   : "${변수명}" 이 포함된 문자열
	 * @param  replaceMap : 치환정보가 있는 정보
	 * @return            : 치환된 결과 없는 경우 공백으로 치환
	 */
	public static String convertTemplateString(String template, Map<String, String> replaceMap) {

		String regex = "\\$\\{([a-zA-Z_$][a-zA-Z0-9_$]*)\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(template);

		StringBuffer result = new StringBuffer();

		while ( matcher.find() ) {
			String variableName = matcher.group(1); // 그룹으로 변수명 추출
			String value = ObjectUtils.defaultIfNull(replaceMap.getOrDefault(variableName, ""), ""); // 맵에서 값 가져오기
			matcher.appendReplacement(result, value); // 치환
		}

		matcher.appendTail(result); // 나머지 부분 추가

		return result.toString();

	}

	/**
	 * <pre>
	 *	사업이 읽기편한 단위로 ms 를 전환처리
	 * </pre>
	 * 
	 * @param  millis : long ms 값
	 * @return
	 */
	public static String getHumanReadableTmUnit(long millis) {

		return getHumanReadableTmUnit(millis, true, true, true, true, true);

	}

	/**
	 * <pre>
	 *	사업이 읽기편한 단위로 ms 를 전환처리
	 * </pre>
	 * 
	 * @param  millis    :long ms 값
	 * @param  useBfAf   : 이전이후 값 출력여부
	 * @param  useDay    : 일자출력여부
	 * @param  useHour   : 시간 출력여부
	 * @param  useMinute : 분 출력여부
	 * @param  useSecond : 초 출력여부
	 * @return
	 */
	public static String getHumanReadableTmUnit(long millis, boolean useBfAf, boolean useDay, boolean useHour, boolean useMinute, boolean useSecond) {

		boolean isNegative = millis < 0;

		long luDay, luHour, luMinute, luSecond;
		luSecond = 1000;
		luMinute = luSecond * 60;
		luHour = luMinute * 60;
		luDay = luHour * 24;

		millis = Math.abs(millis);

		StringBuilder sb = new StringBuilder();

		if ( useBfAf && millis != 0 )
			sb.append(isNegative ? "before at(-) " : "after at(+) ");

		BiFunction<Long[], String, Long> millCalcFunc = (v, t) -> {
			long ms = v[0];

			if ( ms != 0 ) {
				long lUnit = v[1];
				long calcValue = ms / lUnit;

				if ( calcValue > 0 )
					sb.append(calcValue).append(t);

				ms %= lUnit;
			}

			return ms;
		};

		if ( useDay ) {
			millis = millCalcFunc.apply(new Long[] {
				millis, luDay
			}, "day ");
		}

		if ( useHour ) {
			millis = millCalcFunc.apply(new Long[] {
				millis, luHour
			}, "hour ");
		}

		if ( useMinute ) {
			millis = millCalcFunc.apply(new Long[] {
				millis, luMinute
			}, "min ");
		}

		if ( useSecond ) {
			millis = millCalcFunc.apply(new Long[] {
				millis, luSecond
			}, "sec ");
		}

		if ( millis != 0 && sb.length() == 0 )
			sb.append(millis).append("ms");

		return sb.toString();

	}

}
