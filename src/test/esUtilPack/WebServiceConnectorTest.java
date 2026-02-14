package the9UtilPack;

import org.junit.jupiter.api.Test;


class WebServiceConnectorTest {

	@Test
	void test() {

		WebServiceConnector wc = new WebServiceConnector(
			"https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=othK3UQwk5ARn30yBBQJFFUm5XAnjwRQ&data=AP01&searchdate=2024-06-13"
		);
		wc.setStrRequestType("GET");

		try {
			System.out.println(wc.execWebService());
		} catch ( Exception e ) {
		}

	}

}
