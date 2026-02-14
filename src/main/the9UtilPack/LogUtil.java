package the9UtilPack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import jakarta.servlet.http.HttpServletRequest;


public class LogUtil {

	/**
	 * 
	 * <pre>
	 *	HttpServletRequest 로 전달된 파라메터 추출
	 * </pre>
	 *
	 * @param  request : HttpServletRequest (*controller Args)
	 * @return
	 */
	public static String getParamString(HttpServletRequest request) {

		String string = "";

		Enumeration<?> e = request.getParameterNames();

		while ( e.hasMoreElements() ) {
			String name = (String) e.nextElement();
			String value = "";

			try {
				value = Arrays.toString(request.getParameterValues(name));
			} catch ( Exception ex ) {
				continue;
			}

			string += ( "\n" + name + "=" + value );
		}

		return string;

	}

	/**
	 * 
	 * <pre>
	 *	stacktrace 를 문자열로 전달
	 * </pre>
	 *
	 * @param  stackTraceElements
	 * @return
	 */
	public static String getStackTraceString(StackTraceElement[] stackTraceElements) {

		StringBuilder sb = new StringBuilder();

		for ( StackTraceElement ste : stackTraceElements ) {
			sb.append(ste.toString());
			sb.append("\n");
		}

		return sb.toString();

	}

	/**
	 * 
	 * <pre>
	 *	긴 문자열을 원하는 사이즈로 커팅
	 * </pre>
	 *
	 * @param  msg
	 * @param  cuttingSize
	 * @return
	 */
	public static List<String> splitLongLog(String msg, int cuttingSize) {

		int n = msg.length();
		ArrayList<String> dataList = new ArrayList<String>();

		for ( int i = 0; ( n - ( cuttingSize * i ) ) > 0; i++ ) {
			dataList.add(StringUtils.substring(msg, i * cuttingSize, ( i + 1 ) * cuttingSize));
		}

		return dataList;

	}

	/**
	 * 
	 * <pre>
	 * 	slf4j 로거에 Exception 출력 처리
	 * </pre>
	 *
	 * @param log
	 * @param e
	 */
	public static void exceptionLoging(Logger log, Throwable e) {

		log.error(
			"\n\nException Message :\n" + e.getMessage() + "\n\nException Cause :\n" + e.getCause() + "\n\nStackTrace :\n" + getStackTraceString(e.getStackTrace())
		);

	}

	/**
	 * 
	 * <pre>
	 * 	slf4j 로거에 Exception 출력 처리
	 * </pre>
	 *
	 * @param  log
	 * @param  e
	 * 
	 * @return     : debugSerial
	 */
	public static String exceptionLogingWithDebugSerial(Logger log, Throwable e) {

		String rndString = CommonLibrary.randomAlphabetNumberCombineString(20);

		log.error(
			"\n\nDebug Serial :\n" + rndString + "\n\nException Message :\n" + e.getMessage() + "\n\nException Cause :\n" + e.getCause() + "\n\nStackTrace :\n"
				+ getStackTraceString(e.getStackTrace())
		);

		return rndString;

	}

	/**
	 * 
	 * <pre>
	 *	짧은 스택트레이스 획득
	 * </pre>
	 *
	 * @param  stackTraceElements
	 * @return
	 */
	public static String getShortStackTraceString(StackTraceElement[] stackTraceElements) {

		StringBuilder sb = new StringBuilder();

		for ( StackTraceElement e : stackTraceElements ) {

			if ( !e.isNativeMethod() && e.getLineNumber() > 0 ) {
				sb.append(e.getFileName() + ":" + e.getLineNumber() + "[" + e.getMethodName() + "]");
				sb.append("\n");
			}

		}

		return sb.toString();

	}

}
