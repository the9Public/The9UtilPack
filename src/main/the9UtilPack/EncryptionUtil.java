package the9UtilPack;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import the9UtilPack.nopLogger.LoggerFactory;


public class EncryptionUtil {

	private final static Logger log = LoggerFactory.getLogger(EncryptionUtil.class);
	private final static String DEFAULT_CHARSET = StandardCharsets.UTF_8.displayName();
	private final static String aesAlgorithm = "AES/CBC/PKCS5Padding";

	/**
	 * 양방향 암호화 알고리즘인 AES 암호화를 지원하는 클래스
	 */
	public static class AESUtil {

		private final IvParameterSpec ivSpec;
		private final Key keySpec;
		private final String charSet;
		private final Cipher cipher;

		/**
		 * 
		 * <pre>
		 *	올바른 키값 검증
		 * </pre>
		 *
		 * @param  key
		 * @return
		 * @throws InvalidKeyException
		 */
		private static boolean keyValidCheck(byte[] key) throws InvalidKeyException {

			if ( !Arrays.asList(16, 24, 32).contains(key.length) ) {
				InvalidKeyException e = new InvalidKeyException("정해진 사이즈의 키인지 확인이 필요합니다. *(16,24,32 Byte 키만 사용가능)");
				LogUtil.exceptionLoging(log, e);
				throw e;
			}

			return true;

		}

		/**
		 * 
		 * <pre>
		 *	IV 길이 제한처리
		 * </pre>
		 *
		 * @param  iv
		 * @return
		 */
		private static byte[] ivRefiner(byte[] iv) {

			if ( iv.length > 16 ) {
				log.info("IV 값이 너무 깁니다. IV가 16바이트로 잘립니다.");
				iv = Arrays.copyOfRange(iv, 0, 16);
			}

			return iv;

		}

		/**
		 * 
		 * <pre>
		 *	간편 암호화
		 * </pre>
		 *
		 * @param  key
		 * @param  iv
		 * @param  data
		 * @return
		 * @throws GeneralSecurityException
		 */
		public static byte[] encrypt(byte[] key, byte[] iv, byte[] data) throws GeneralSecurityException {

			keyValidCheck(key);
			iv = ivRefiner(iv);
			Cipher c = Cipher.getInstance(aesAlgorithm);
			c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
			return c.doFinal(data);

		}

		/**
		 * 
		 * <pre>
		 *	간편 복호화
		 * </pre>
		 *
		 * @param  key
		 * @param  iv
		 * @param  data
		 * @return
		 * @throws GeneralSecurityException
		 */
		public static byte[] decrypt(byte[] key, byte[] iv, byte[] data) throws GeneralSecurityException {

			keyValidCheck(key);
			iv = ivRefiner(iv);
			Cipher c = Cipher.getInstance(aesAlgorithm);
			c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
			return c.doFinal(data);

		}

		/**
		 * 
		 * <pre>
		 *	AES 암호화 유틸
		 * </pre>
		 *
		 * @param  key                          : 암/복호화를 위한 키값 16(128) / 24(192) / 32(256) Byte 키값 입력
		 * @param  iv                           : 초기화 벡터
		 * @throws UnsupportedEncodingException
		 * @throws InvalidKeyException
		 * @throws NoSuchPaddingException
		 * @throws NoSuchAlgorithmException
		 */
		public AESUtil(String key, String iv) throws UnsupportedEncodingException, GeneralSecurityException {

			this(key, iv, DEFAULT_CHARSET);

		}

		/**
		 * 
		 * <pre>
		 *	AES 암호화 유틸
		 * </pre>
		 *
		 * @param  key                          : 암/복호화를 위한 키값 16(128) / 24(192) / 32(256) Byte 키값 입력
		 * @param  iv                           : 초기화 벡터
		 * @param  charset                      : 캐릭터셋
		 * @throws UnsupportedEncodingException
		 * @throws InvalidKeyException
		 * @throws NoSuchPaddingException
		 * @throws NoSuchAlgorithmException
		 */
		public AESUtil(String key, String iv, String charset) throws UnsupportedEncodingException, GeneralSecurityException {

			this(key.getBytes(charset), iv.getBytes(charset), charset);

		}

		/**
		 * 
		 * <pre>
		 *	AES 암호화 유틸
		 * </pre>
		 *
		 * @param  key                      : 암/복호화를 위한 키값 16(128) / 24(192) / 32(256) Byte 키값 입력
		 * @param  iv                       : 초기화 벡터
		 * @param  charset                  : 캐릭터셋
		 * @throws InvalidKeyException
		 * @throws NoSuchPaddingException
		 * @throws NoSuchAlgorithmException
		 */
		public AESUtil(byte[] key, byte[] iv, String charset) throws GeneralSecurityException {

			keyValidCheck(key);
			iv = ivRefiner(iv);

			if ( StringUtils.isBlank(charset) ) {
				this.charSet = DEFAULT_CHARSET;
			} else {
				this.charSet = charset;
			}

			log.info("charset = " + this.charSet + "\nAES Mode : " + ( key.length * 8 ));

			this.ivSpec = new IvParameterSpec(iv);
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
			this.keySpec = keySpec;
			this.cipher = Cipher.getInstance(aesAlgorithm);

		}

		/**
		 * 
		 * <pre>
		 *	AES 암호화
		 * </pre>
		 *
		 * @param  str                          : 원문
		 * @return
		 * @throws NoSuchAlgorithmException
		 * @throws GeneralSecurityException
		 * @throws UnsupportedEncodingException
		 */
		public String encrypt(String str) throws GeneralSecurityException, UnsupportedEncodingException {

			if ( str == null ) {
				return "";
			}

			return encrypt(str.getBytes(charSet));

		}

		/**
		 * 
		 * <pre>
		 *	AES 암호화
		 * </pre>
		 *
		 * @param  bytes                    : 원데이터
		 * @return
		 * @throws NoSuchAlgorithmException
		 * @throws GeneralSecurityException
		 */
		public String encrypt(byte[] bytes) throws GeneralSecurityException {

			if ( bytes == null || bytes.length == 0 ) {
				return "";
			}

			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
			byte[] encrypted = cipher.doFinal(bytes);
			String enStr = new String(Base64.encodeBase64(encrypted));
			return enStr;

		}

		/**
		 * AES256으로 암호화된 txt 를 복호화한다.
		 *
		 * @param  str                          복호화할 문자열
		 * @return
		 * @throws NoSuchAlgorithmException
		 * @throws GeneralSecurityException
		 * @throws UnsupportedEncodingException
		 */
		public String decrypt(String str) throws GeneralSecurityException, UnsupportedEncodingException {

			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
			byte[] byteStr = Base64.decodeBase64(str.getBytes(charSet));
			return new String(cipher.doFinal(byteStr), charSet);

		}

	}

}
