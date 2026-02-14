package the9UtilPack;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;

import the9UtilPack.nopLogger.LoggerFactory;


public class HashMaker {

	static Logger log = LoggerFactory.getLogger(HashMaker.class);

	/**
	 * 
	 * <pre>
	 * SHA256
	 * </pre>
	 *
	 * @param  value
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getSHA256(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String res = "";

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update( ( value ).getBytes("utf8"));
		res = String.format("%064x", new BigInteger(1, digest.digest()));

		return res;

	}

	/**
	 * 
	 * <pre>
	 * SHA512
	 * </pre>
	 *
	 * @param  value
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getSHA512(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String res = "";

		MessageDigest digest = MessageDigest.getInstance("SHA-512");
		digest.reset();
		digest.update( ( value ).getBytes("utf8"));
		res = String.format("%0128x", new BigInteger(1, digest.digest()));

		return res;

	}

}
