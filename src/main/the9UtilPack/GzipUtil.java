package the9UtilPack;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;

import the9UtilPack.nopLogger.LoggerFactory;


public class GzipUtil {

	static Logger log = LoggerFactory.getLogger(GzipUtil.class);
	private static int compressLevel = Deflater.DEFAULT_COMPRESSION;

	/**
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param  level                    : -1 ~ 9 까지의 압축레벨 . -1:기본압축 ,0: 압축안함, 1: 가장빠른압축, 9: 압축률 가장높음
	 * @throws IllegalArgumentException
	 */
	public static void setCompressLevel(int level) throws IllegalArgumentException {

		if ( level < -1 || level > 9 ) {
			throw new IllegalArgumentException("압출률은 -1 ~ 9 까지만 설정 가능합니다.");
		}

		compressLevel = level;

	}

	/**
	 * <pre>
	 *  일정크기 미만의 텍스트를 압축효율이 높다면 압축처리
	 * </pre>
	 * 
	 * @param  data        : 압축 대상 문자열
	 * @param  nIgnoreSize : 해당 사이즈 미만의 압축은 무시처리
	 * @return
	 */
	public static String getCompressStringPreChkLength(String data, int nIgnoreSize) {

		byte[] bData = data.getBytes();
		if ( bData.length < nIgnoreSize )
			return data;

		return getShortCompressString(data);

	}

	/**
	 * <pre>
	 *	원문과 압축 텍스트중 작은 것을 반환
	 * </pre>
	 * 
	 * @param  data : 압축 대상 문자열
	 * @return
	 */
	public static String getShortCompressString(String data) {

		byte[] bData = data.getBytes();
		String compString = getCompressString(data, compressLevel);
		byte[] bComp = compString.getBytes();

		log.debug("ORIGINAL LENGTH : " + bData.length);
		log.debug("COMPRESS LENGTH : " + bComp.length);

		if ( bData.length > bComp.length ) {
			return compString;
		} else {
			return data;
		}

	}

	/**
	 * <pre>
	 *	사전정의된 압축레벨로 압축처리 후 짧은 문자열 반환처리
	 *	
	 *	======================
	 *  == 데이터 크기: 압축레벨
	 *  ======================
	 *  
	 *	 ~ 512 : 0
	 *   ~ 4096 : 1
	 *   ~ 20480 : 3
	 *   ~ 102400 : 5
	 *   ~ 512000 : 6
	 *   512000 ~ : 9
	 *   
	 *   =====================
	 * 
	 * </pre>
	 * 
	 * @param  data : 압축대상 문자열
	 * @return
	 */
	public static String getShortPresetCompressLevelString(String data) {

		byte[] bData = data.getBytes();
		int level = 0;

		if ( bData.length < 512 ) {
			// UNDER 0.5K
			level = 0;
		} else if ( bData.length < 4096 ) {
			// UNDER 4K
			level = 1;
		} else if ( bData.length < 20480 ) {
			// UNDER 20K
			level = 3;
		} else if ( bData.length < 102400 ) {
			// UNDER 100K
			level = 5;
		} else if ( bData.length < 512000 ) {
			// UNDER 500K
			level = 6;
		} else {
			// OVER 500K
			level = 9;
		}

		if ( level == 0 )
			return data;

		String compString = getCompressString(data, level);

		byte[] bComp = compString.getBytes();

		log.debug("ORIGINAL LENGTH : " + bData.length);
		log.debug("COMPRESS LENGTH : " + bComp.length);

		if ( bData.length > bComp.length ) {
			return compString;
		} else {
			return data;
		}

	}

	/**
	 * <pre>
	 *	문자열을 압축하여 반환
	 * </pre>
	 * 
	 * @param  data : 압축대상 문자열
	 * @return      : 압축된 Base64 문자열
	 */
	public static String getCompressString(String data, int level) {

		return Base64.getEncoder().encodeToString(getCompressData(data.getBytes(), level));

	}

	/**
	 * <pre>
	 *	byte to byte Gzip 압축
	 * </pre>
	 * 
	 * @param  data : 압축대상 byte[]
	 * @return      : 압축된 byte[]
	 */
	public static byte[] getCompressData(byte[] data, int level) {

		byte[] bData = data;

		try ( ByteArrayOutputStream bOStream = new ByteArrayOutputStream() ) {

			try ( BufferedOutputStream bfOStream = new BufferedOutputStream(bOStream); GZIPOutputStream gzipOStream = new GZIPOutputStream(bfOStream) {

				{
					def.setLevel(level);
				}

			} ) {
				gzipOStream.write(data);
				gzipOStream.finish();
			}

			bData = bOStream.toByteArray();
		} catch ( IOException e ) {
			log.error(e.toString());
		}

		return bData;

	}

	/**
	 * <pre>
	 *	Base64String 을 인입하여 압축해제 , 해제 실패시 원본 String 전달처리
	 * </pre>
	 * 
	 * @param  b64String : 복호화 대상 Base64 문자열
	 * @param  charset   : 해당복호화 대상 캐릭터셋
	 * @return
	 */
	public static String getDecompressBASE64StringIfnullOrigin(String b64String, Charset charset) {

		try {
			return new String(getDecompressBASE64String(b64String), charset.displayName());
		} catch ( Exception e ) {
			return b64String;
		}

	}

	/**
	 * <pre>
	 *	Base 64 문자열을 암호화 해제처리
	 * </pre>
	 * 
	 * @param  b64String   : 복호화 대상 Base64 문자열
	 * @return
	 * @throws IOException : 입출력예외
	 */
	public static byte[] getDecompressBASE64String(String b64String) throws IOException {

		byte[] b64Data = Base64.getDecoder().decode(b64String.getBytes());
		return getDecompressData(b64Data);

	}

	/**
	 * <pre>
	 *	byte to byte Gzip 압축해제
	 * </pre>
	 * 
	 * @param  data        : 압축해제 대상
	 * @return
	 * @throws IOException : 입출력 예외
	 */
	public static byte[] getDecompressData(byte[] data) throws IOException {

		byte[] bData = data;

		try ( ByteArrayInputStream bIStream = new ByteArrayInputStream(data); ) {

			try ( GZIPInputStream gzipIStream = new GZIPInputStream(bIStream); ) {
				byte[] buff = new byte[1024];
				int len;

				try ( ByteArrayOutputStream byteArrayOStream = new ByteArrayOutputStream(); ) {

					while ( ( len = gzipIStream.read(buff) ) != -1 ) {
						byteArrayOStream.write(buff, 0, len);
					}

					bData = byteArrayOStream.toByteArray();
				}

			}

		}

		return bData;

	}

}
