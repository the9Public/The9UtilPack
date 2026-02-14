package the9UtilPack;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import the9UtilPack.EncryptionUtil.AESUtil;
import the9UtilPack.nopLogger.LoggerFactory;


class EncryptionUtilTest {

	Logger log = LoggerFactory.getLogger(EncryptionUtilTest.class);

	@Test
	void test() {

		try {
			String encKey = "ABCD1234ABCD134113411341VVVVVVVV";
			AESUtil aesUtil = new AESUtil(encKey, encKey);
			String text = "test- 안녕?111";
			log.info("original : " + text);
			String enc = aesUtil.encrypt(text);
			log.info("enc : " + enc);
			String dec = aesUtil.decrypt(enc);
			log.info("dec : " + dec);

			log.info("====== STATIC FUNCTION CHECK ======");
			String encData = Base64.getEncoder().encodeToString(AESUtil.encrypt(encKey.getBytes(), encKey.getBytes(), text.getBytes()));
			log.info("enc : " + encData);
			String decData = new String(AESUtil.decrypt(encKey.getBytes(), encKey.getBytes(), Base64.getDecoder().decode(encData)));
			log.info("dec : " + decData);
		} catch ( GeneralSecurityException | UnsupportedEncodingException e ) {
		}

	}

}
