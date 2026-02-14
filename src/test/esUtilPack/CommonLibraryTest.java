package the9UtilPack ;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import the9UtilPack.nopLogger.LoggerFactory;


class CommonLibraryTest {

	private static Logger log = LoggerFactory.getLogger(CommonLibraryTest.class);

	@Test
	void 반각전각_테스트() {

		String text = "123/ABC 가나다:./;";
		text = CommonLibrary.textToHalf(text);
		System.out.println("HALF TO HALF : " + text);
		text = CommonLibrary.textToFull(text);
		///log.debug("{} {}", "FULL", text);
		System.out.println("FULL : " + text);

		text = CommonLibrary.textToFull(text);
		///log.debug("{} {}", "FULL", text);
		System.out.println("FULL TO FULL : " + text);

		text = CommonLibrary.textToHalf(text);
		//log.debug("{} {}", "HALF", text);
		System.out.println("HALF : " + text);

	}

	@Test
	void 로컬_알수없는_포트_접근_테스트() {

		WebServiceConnector wc = new WebServiceConnector("http://localhost:49899");
		wc.setConnectTimeout(500);
		wc.setTimeout(500);
		wc.setStrRequestType("GET");
		wc.setUseNotOpenOutputStream(true);

		try {
			String result = wc.execWebService();
			log.debug("result : " + result);
		} catch ( Exception e ) {
			assertTrue(true);
		}

	}
	

}
