package the9UtilPack;

import java.nio.charset.Charset;
import java.util.Arrays;


public class ByteControlUtil {

	/**
	 * <pre>
	 *	문자열을 특정 바이트크기로 자르기
	 * </pre>
	 * 
	 * @param  s          : 문자열
	 * @param  length     : 원하는 바이트 길이
	 * @param  dstCharset : 목적 문자열셋
	 * @return            : 요청 바이트 크기 이하로 잘린 문자열 바이트
	 */
	public static String stringCut(String s, int length, Charset dstCharset) {

		if ( s == null )
			return "";

		String result = new String(s.getBytes(dstCharset), dstCharset);

		while ( result.getBytes(dstCharset).length > length ) {
			result = result.substring(0, result.length() - 1);
		}

		return new String(result.getBytes(dstCharset), dstCharset);

	}

	/**
	 * <pre>
	 *	길이를 더한 전문 반환
	 * </pre>
	 * 
	 * @param  data    : 헤더 + 바디 전문
	 * @param  charset : 문자열셋
	 * @return
	 */
	public static String makeOffsetInfo(String data, Charset charset) {

		return lpad(String.valueOf(data.getBytes(charset).length), charset, 4) + data;

	}

	/**
	 * <pre>
	 *	바이트 길이 획득
	 * </pre>
	 * 
	 * @param  data    : 카운팅 대상 데이터
	 * @param  charset : 문자열셋
	 * @return
	 */
	public static byte[] getByteLen(String data, Charset charset) {

		return lpad(String.valueOf(data.getBytes(charset).length), charset, 4).getBytes(charset);

	}

	/**
	 * <pre>
	 *	LPAD (Byte) 왼쪽을 특정 문자로 채움 처리
	 * </pre>
	 * 
	 * @param  data    : 원본 데이터
	 * @param  charset : 목적하는 문자열셋
	 * @param  length  : 목적 바이트수
	 * @param  padChar : 채울 문자
	 * @return         :
	 */
	public static String lpad(String data, Charset charset, int length, char padChar) {

		data = stringCut(data, length, charset);
		byte[] dataB = data.getBytes(charset);
		byte[] result = new byte[length];

		// Lpad 채움처리
		Arrays.fill(result, (byte) padChar);

		System.arraycopy(dataB, 0, result, length - dataB.length, dataB.length);

		return new String(result, charset);

	}

	/**
	 * <pre>
	 *	LPAD (Byte) 왼쪽을 '0' 으로 채움
	 * </pre>
	 * 
	 * @param  data    : 원본 데이터
	 * @param  charset : 목적하는 문자열셋
	 * @param  length  : 목적 바이트수
	 * @return         :
	 */
	public static String lpad(String data, Charset charset, int length) {

		return lpad(data, charset, length, '0');

	}

	/**
	 * <pre>
	 *	RPAD (Byte) 오른쪽을 특정문자로 처리
	 * </pre>
	 * 
	 * @param  data    : 원본데이터
	 * @param  charset : 목적하는 문자열셋
	 * @param  length  : 목적 바이트수
	 * @param  padChar : 채울 문자
	 * @return
	 */
	public static String rpad(String data, Charset charset, int length, char padChar) {

		data = stringCut(data, length, charset);
		byte[] dataB = data.getBytes(charset);
		byte[] result = new byte[length];

		// Lpad 채움처리
		Arrays.fill(result, (byte) padChar);

		System.arraycopy(dataB, 0, result, 0, dataB.length);

		return new String(result, charset);

	}

	/**
	 * <pre>
	 *	RPAD (Byte) 오른쪽을 특정문자로 처리
	 * </pre>
	 * 
	 * @param  data    : 원본데이터
	 * @param  charset : 목적하는 문자열셋
	 * @param  length  : 목적 바이트수
	 * @return
	 */
	public static String rpad(String data, Charset charset, int length) {

		return rpad(data, charset, length, ' ');

	}

	/**
	 * <pre>
	 *	바이트 커팅
	 * </pre>
	 * 
	 * @param  src     : 자를 바이트배열
	 * @param  cutSize : 양수일경우 왼쪽에서 부터 커팅, 음수일경우 우측에서부터 커팅
	 * @return         : byte[0][] 잘리고 남은 원본 데이터
	 *                 : byte[1][] 자르면서 추출된 데이터
	 */
	public static byte[][] byteCut(byte[] src, int cutSize) {

		if ( src == null || src.length == 0 || cutSize == 0 || Math.abs(cutSize) >= src.length ) {
			return new byte[][] {
				new byte[0], src
			};
		}

		byte[][] rst = new byte[2][];
		int srcLength = src.length;

		if ( cutSize > 0 ) { // 좌측에서부터 자르기
			rst[0] = new byte[srcLength - cutSize];
			rst[1] = new byte[cutSize];
			System.arraycopy(src, 0, rst[1], 0, cutSize);
			System.arraycopy(src, cutSize, rst[0], 0, srcLength - cutSize);
		} else { // 우측에서부터 자르기
			cutSize = Math.abs(cutSize);
			rst[0] = new byte[srcLength - cutSize];
			rst[1] = new byte[cutSize];
			System.arraycopy(src, 0, rst[0], 0, srcLength - cutSize);
			System.arraycopy(src, srcLength - cutSize, rst[1], 0, cutSize);
		}

		return rst;

	}

	/**
	 * <pre>
	 *	Bytes Starts with
	 * </pre>
	 * 
	 * @param  array  : 원문 바이트
	 * @param  prefix : 이것으로 시작하는지 체크할 바이트
	 * @return        : true : 비교값으로 시작, false : 비교값으로 시작안함
	 */
	public static boolean startsWith(byte[] array, byte[] prefix) {

		if ( array.length < prefix.length ) {
			return false;
		}

		for ( int i = 0; i < prefix.length; i++ ) {

			if ( array[i] != prefix[i] ) {
				return false;
			}

		}

		return true;

	}

}
