package the9UtilPack;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;


public class HttpClientUtil {

	/**
	 * <pre>
	 *	CookieManager 의 CookieStore를 이용해 CookieString 리스트 뽑기
	 * </pre>
	 * 
	 * @param  cookieStore
	 * @return
	 */
	public static List<String> extractCookieList(CookieStore cookieStore) {

		List<String> cookieList = new ArrayList<String>();

		for ( HttpCookie cookie : cookieStore.getCookies() ) {
			StringBuilder sb = new StringBuilder();
			sb.append(cookie.getName()).append("=").append(cookie.getValue());

			if ( StringUtils.isNotBlank(cookie.getDomain()) ) {
				sb.append("; Domain=").append(cookie.getDomain());
			}

			if ( StringUtils.isNotBlank(cookie.getPath()) ) {
				sb.append("; Path=").append(cookie.getPath());
			}

			if ( ( cookie.getSecure() ) ) {
				sb.append("; Secure");
			}

			if ( ( cookie.isHttpOnly() ) ) {
				sb.append("; HttpOnly");
			}

			cookieList.add(sb.toString());
		}

		return cookieList;

	}

	/**
	 * <pre>
	 *	CookieManager 의 CookieStore를 이용해 CookieString 뽑기
	 * </pre>
	 * 
	 * @param  cookieStore
	 * @return
	 */
	public static String extractCookieString(CookieStore cookieStore) {

		List<String> list = extractCookieList(cookieStore);

		return String.join("; ", list);

	}

}
