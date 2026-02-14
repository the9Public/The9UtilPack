package the9UtilPack;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import the9UtilPack.nopLogger.LoggerFactory;


@SuppressWarnings ( {
	"unchecked", "rawtypes"
} )
public class WebServiceConnector {

	private static String LF = "\r\n";
	private final static Logger log = LoggerFactory.getLogger(WebServiceConnector.class);
	private String strHtmlUrl = "";
	private Map<String, String> mapParameters;
	private String stringParameters;
	private Map<String, String> requestProperties;
	private String strStringEnCode = "UTF-8";
	private String strStreamEnCode = "UTF-8";
	private String strRequestType = "POST";
	private boolean useNotOpenOutputStream = false;
	private boolean useUrlEncode = false;
	private int nTimeout = 0;
	private int nConnectTimeout = 5000;
	private boolean noPrintResult = false;
	private Map<String, List<String>> responseHeaders = new HashMap<String, List<String>>();
	private List<TransferData> multipartData = null;
	private boolean followRedirect = true;

	/**
	 * 
	 * <PRE>
	 * 생성자
	 * </PRE>
	 *
	 */
	public WebServiceConnector() {

	}

	/**
	 * 
	 * <PRE>
	 * 생성자
	 * </PRE>
	 *
	 * @param strHtmlUrl : 접속 대상 URL
	 */
	public WebServiceConnector(String strHtmlUrl) {

		// TODO Auto-generated constructor stub
		this.strHtmlUrl = strHtmlUrl;

	}

	/**
	 * 
	 * <PRE>
	 * 생성자
	 * </PRE>
	 *
	 * @param strHtmlUrl : 접속 대상 URL
	 * @param parameters : 파라메터해쉬맵 [ "KEY : 파라메터명" , "VALUE : 파라메터값" ]
	 */
	public WebServiceConnector(String strHtmlUrl, HashMap parameters) {

		this.strHtmlUrl = strHtmlUrl;
		mapParameters = parameters;

	}

	/**
	 * 
	 * <PRE>
	 * 생성자
	 * </PRE>
	 *
	 * @param strHtmlUrl : 접속 대상 URL
	 * @param parameter  : JSON String
	 */
	public WebServiceConnector(String strHtmlUrl, String parameter) {

		this.strHtmlUrl = strHtmlUrl;
		stringParameters = parameter;

	}

	/**
	 * <pre>
	 * 	Multipart 전송처리를 위한 생성자
	 * </pre>
	 * 
	 * @param strHtmlUrl    : 대상 URL
	 * @param multipartData : 데이터 리스트
	 */
	public WebServiceConnector(String strHtmlUrl, List<TransferData> multipartData) {

		this.strHtmlUrl = strHtmlUrl;
		this.multipartData = multipartData;

	}

	/**
	 * @return the strHtmlUrl
	 */
	public String getStrHtmlUrl() {

		return strHtmlUrl;

	}

	/**
	 * @param strHtmlUrl the strHtmlUrl to set
	 */
	public void setStrHtmlUrl(String strHtmlUrl) {

		this.strHtmlUrl = strHtmlUrl;

	}

	/**
	 * @return the mapParameters
	 */
	public Map<String, String> getMapParameters() {

		return mapParameters;

	}

	/**
	 * @param mapParameters the mapParameters to set 파라메터해쉬맵 [ "KEY : 파라메터명" , "VALUE : 파라메터값" ]
	 * 
	 */
	public void setMapParameters(HashMap mapParameters) {

		this.mapParameters = mapParameters;

	}

	/**
	 * @return the requestProperties
	 */
	public Map<String, String> getRequestProperties() {

		return requestProperties;

	}

	/**
	 * @param requestProperties the requestProperties to set
	 */
	public void setRequestProperties(Map<String, String> requestProperties) {

		this.requestProperties = requestProperties;

	}

	/**
	 * @return the strRequestType
	 */
	public String getStrRequestType() {

		return strRequestType;

	}

	/**
	 * @param strRequestType the strRequestType to set : POST ? GET ?
	 */
	public void setStrRequestType(String strRequestType) {

		this.strRequestType = strRequestType;

	}

	/**
	 * @return the strRequestType
	 */
	public String getMethod() {

		return strRequestType;

	}

	/**
	 * @param strMethod the strMethod to set : POST ? GET ?
	 */
	public void setMethod(String strMethod) {

		this.strRequestType = strMethod;

	}

	/**
	 * @return the strTimeout
	 */
	public int getTimeout() {

		return nTimeout;

	}

	/**
	 * @param Timeout the Timeout to set
	 */
	public void setTimeout(int Timeout) {

		this.nTimeout = Timeout;

	}

	/**
	 * @return the nConnectTimeout
	 */
	public final int getConnectTimeout() {

		return nConnectTimeout;

	}

	/**
	 * @param nConnectTimeout the nConnectTimeout to set
	 */
	public final void setConnectTimeout(int nConnectTimeout) {

		this.nConnectTimeout = nConnectTimeout;

	}

	/**
	 * @return the strStringEnCode
	 */
	public String getStrStringEnCode() {

		return strStringEnCode;

	}

	/**
	 * @param strStringEnCode the strStringEnCode to set
	 */
	public void setStrStringEnCode(String strStringEnCode) {

		this.strStringEnCode = strStringEnCode;

	}

	/**
	 * @return the strStreamEnCode
	 */
	public String getStrStreamEnCode() {

		return strStreamEnCode;

	}

	/**
	 * @param strStreamEnCode the strStreamEnCode to set
	 */
	public void setStrStreamEnCode(String strStreamEnCode) {

		this.strStreamEnCode = strStreamEnCode;

	}

	/**
	 * @return the useHtmlEscape
	 */
	public boolean isUseUrlEncode() {

		return useUrlEncode;

	}

	/**
	 * @param useUrlEncode the useUrlEncode to set
	 */
	public void setUseUrlEncode(boolean useUrlEncode) {

		this.useUrlEncode = useUrlEncode;

	}

	/**
	 * @return the useNotOpenOutputStream
	 */
	public boolean isUseNotOpenOutputStream() {

		return useNotOpenOutputStream;

	}

	/**
	 * @param useNotOpenOutputStream the useNotOpenOutputStream to set
	 */
	public void setUseNotOpenOutputStream(boolean useNotOpenOutputStream) {

		this.useNotOpenOutputStream = useNotOpenOutputStream;

	}

	/**
	 * @return the noPrintResult
	 */
	public final boolean isNoPrintResult() {

		return noPrintResult;

	}

	/**
	 * @param noPrintResult the noPrintResult to set
	 */
	public final void setNoPrintResult(boolean noPrintResult) {

		this.noPrintResult = noPrintResult;

	}

	public boolean isFollowRedirect() {

		return followRedirect;

	}

	public void setFollowRedirect(boolean followRedirect) {

		this.followRedirect = followRedirect;

	}

	/**
	 * <pre>
	 *	응답 헤더값 요청
	 * </pre>
	 * 
	 * @return : 응답헤더값 반환
	 */
	public final Map<String, List<String>> getResponseHeaders() {

		return responseHeaders;

	}

	public WebServiceResponse execWebService() throws Exception {

		if ( StringUtils.isEmpty(strHtmlUrl) )
			throw new Exception("Cannot found [[URL]]");
		if ( mapParameters == null )
			mapParameters = new HashMap<String, String>();
		if ( requestProperties == null )
			requestProperties = new HashMap<String, String>();

		log.debug("SEND TARGET URI : " + strHtmlUrl);

		boolean useStringEncoding = true;
		if ( // Encoding 거절
		strStringEnCode.toLowerCase(Locale.KOREAN).equals("none") || strStringEnCode.equals("") )
			useStringEncoding = false;
		boolean useStreamEncoding = true;
		if ( // Encoding 거절
		strStreamEnCode.toLowerCase(Locale.KOREAN).equals("none") || strStreamEnCode.equals("") )
			useStreamEncoding = false;

		WebServiceResponse response = null;

		/**
		 * 요청 프로토콜이 HTTPS인 경우 아래의 코드를 통해 Server Side 방식으로 인증없이 HTTPS 연결을 함.
		 */
		URL url = URI.create(strHtmlUrl).toURL();

		if ( strHtmlUrl.contains("https://") ) {
			response = connectToHttpsUrl(url, useStringEncoding, useStreamEncoding);
		} else {
			response = connectToHttpUrl(url, useStringEncoding, useStreamEncoding);
		}

		return response;

	}

	/**
	 * <pre>
	 * 	HTTPS 연결
	 * </pre>
	 * 
	 * @param  url                      : URL 객체
	 * @param  useStringEncoding        : 문자열 인코딩 사용여부
	 * @param  useStreamEncoding        : 스트림 인코딩 사용여부
	 * @return                          : 결과 InputStream
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	private WebServiceResponse connectToHttpsUrl(URL url, boolean useStringEncoding, boolean useStreamEncoding)
		throws NoSuchAlgorithmException, KeyManagementException, IOException {

		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,TLSv1.3");
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

			@Override
			public boolean verify(String hostname, SSLSession session) {

				return true;

			}

		});
		TrustManager[] trustAllCerts = new TrustManager[] {
			new X509TrustManager() {

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {

					return null;

				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {

				}

			}
		};
		SSLContext sc;

		if ( strHtmlUrl.endsWith(":9402/rest/ext") ) {
			// IFT Bridge 요청일 경우
			sc = SSLContext.getInstance("SSL");
			log.debug("IFT_GATEWAY TO IFT_BRIDGE CONNECT");
		} else {
			sc = SSLContext.getInstance("TLSv1.2");
		}

		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		String resultMessage = "";

		HttpsURLConnection conn = null;

		conn = (HttpsURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setReadTimeout(nTimeout);
		conn.setConnectTimeout(nConnectTimeout);

		if ( !followRedirect ) {
			conn.setInstanceFollowRedirects(followRedirect);
		}

		String boundary = "===" + System.currentTimeMillis() + "===";

		if ( multipartData != null ) {
			conn.setRequestMethod("POST");
			requestProperties.put("Content-Type", "multipart/form-data; boundary=" + boundary);
		} else {
			// 전송방식
			conn.setRequestMethod(strRequestType);
		}

		// 프로퍼티세팅
		Iterator<String> keys = requestProperties.keySet().iterator();

		while ( keys.hasNext() ) {
			String key = keys.next();
			conn.setRequestProperty(key, requestProperties.get(key));
		}

		if ( !useNotOpenOutputStream ) {

			try ( OutputStream os = conn.getOutputStream(); ) {
				log.info(strHtmlUrl + " : PARAM");
				log.debug("WEB SERVICE PARAM SET+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++START");

				// Parameter Mapping
				if ( multipartData != null ) {
					OutputStreamWriter writer;
					if ( useStreamEncoding )
						writer = new OutputStreamWriter(os, strStreamEnCode);
					else
						writer = new OutputStreamWriter(os);

					try ( PrintWriter w = new PrintWriter(writer, true) ) {
						// MULTIPART 로 고정처리

						for ( TransferData item : multipartData ) {
							item.write(os, w, boundary);
						}

						w.append("--" + boundary + "--").append(LF).flush();
						w.close();
					}

					writer.close();
				} else if ( StringUtils.isNotEmpty(stringParameters) ) {
					byte[] paramData;

					if ( useStringEncoding )
						paramData = stringParameters.getBytes(strStringEnCode);
					else
						paramData = stringParameters.getBytes();

					os.write(paramData, 0, paramData.length);
					os.flush();
				} else {
					OutputStreamWriter writer;
					if ( useStreamEncoding )
						writer = new OutputStreamWriter(os, strStreamEnCode);
					else
						writer = new OutputStreamWriter(os);

					int nLoopCnt = 0;

					for ( Entry<String, String> entry : mapParameters.entrySet() ) {
						String strKey = String.valueOf(entry.getKey());
						String strValue = String.valueOf(entry.getValue());

						if ( nLoopCnt != 0 )
							writer.write("&");

						if ( useStringEncoding ) {
							strKey = new String(strKey.getBytes(strStringEnCode), strStringEnCode);
							strValue = new String(strValue.getBytes(strStringEnCode), strStringEnCode);
						}

						if ( useUrlEncode ) {
							strValue = URLEncoder.encode(strValue, strStringEnCode);
						}

						log.debug("new String( strKey.getBytes(" + strStringEnCode + ") ) : " + strKey);
						log.debug("new String( strValue.getBytes(" + strStringEnCode + ") ) : " + strValue);
						String string = ( strKey + "=" + strValue );

						log.info("k=v : " + string);

						writer.write(string);

						nLoopCnt++ ;
					}

					writer.flush();
					writer.close();
				}

				log.debug("WEB SERVICE PARAM SET+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++END");
			}

		}

		conn.connect();

		WebServiceResponse response = new WebServiceResponse();

		int responseCode = conn.getResponseCode();
		response.setStatusCode(responseCode);
//		int responseCode = 200;

		Map<String, List<String>> resHeaderMap = conn.getHeaderFields();
		responseHeaders = new HashMap<String, List<String>>();

		int hNo = 1;

		for ( Map.Entry<String, List<String>> entry : resHeaderMap.entrySet() ) {
			// 각 리스트를 새 리스트로 깊은 복사
			String hKey = ObjectUtils.defaultIfNull(entry.getKey(), "_EMPTY_HEADER_" + String.valueOf(hNo++ ));

			if ( responseHeaders.containsKey(hKey) ) {
				responseHeaders.get(hKey).addAll(entry.getValue());
			} else {
				responseHeaders.put(hKey, new ArrayList<>(entry.getValue()));
			}

		}

		InputStream is = null;

		try {

			try {
				is = conn.getInputStream();
			} catch ( IOException e ) {
				is = conn.getErrorStream();
			}

			resultMessage = getResultMessage(is, useStringEncoding, useStreamEncoding);
		} finally {
			if ( is != null )
				is.close();
		}

		log.debug("disconnect HttpsConnection");
		// Finally 처리시 connect 자체가 안된경우 disconnect 에서 행걸림
		conn.disconnect();
		response.setMsg(resultMessage);

		return response;

	}

	/**
	 * <pre>
	 * 	HTTPS 연결
	 * </pre>
	 * 
	 * @param  url                      : URL 객체
	 * @param  useStringEncoding        : 문자열 인코딩 사용여부
	 * @param  useStreamEncoding        : 스트림 인코딩 사용여부
	 * @return                          : 결과 InputStream
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	private WebServiceResponse connectToHttpUrl(URL url, boolean useStringEncoding, boolean useStreamEncoding) throws IOException {

		String resultMessage = "";
		HttpURLConnection conn = null;

		conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setReadTimeout(nTimeout);
		conn.setConnectTimeout(nConnectTimeout);

		if ( !followRedirect ) {
			conn.setInstanceFollowRedirects(followRedirect);
		}

		String boundary = "===" + System.currentTimeMillis() + "===";

		if ( multipartData != null ) {
			conn.setRequestMethod("POST");
			requestProperties.put("Content-Type", "multipart/form-data; boundary=" + boundary);
		} else {
			// 전송방식
			conn.setRequestMethod(strRequestType);
		}

		// 프로퍼티세팅
		Iterator<String> keys = requestProperties.keySet().iterator();

		while ( keys.hasNext() ) {
			String key = keys.next();
			conn.setRequestProperty(key, requestProperties.get(key));
		}

		if ( !useNotOpenOutputStream ) {

			try ( OutputStream os = conn.getOutputStream(); ) {
				OutputStreamWriter writer;
				if ( useStreamEncoding )
					writer = new OutputStreamWriter(os, strStreamEnCode);
				else
					writer = new OutputStreamWriter(os);
				log.info(strHtmlUrl + " : PARAM");
				log.debug("WEB SERVICE PARAM SET+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++START");

				// Parameter Mapping
				if ( multipartData != null ) {

					try ( PrintWriter w = new PrintWriter(writer, true) ) {
						// MULTIPART 로 고정처리

						for ( TransferData item : multipartData ) {
							item.write(os, w, boundary);
						}

						w.append("--" + boundary + "--").append(LF).flush();
						w.close();
					}

				} else if ( StringUtils.isNotEmpty(stringParameters) ) {
					writer.write(stringParameters);
				} else {
					int nLoopCnt = 0;

					for ( Entry<String, String> entry : mapParameters.entrySet() ) {
						String strKey = String.valueOf(entry.getKey());
						String strValue = String.valueOf(entry.getValue());

						if ( nLoopCnt != 0 )
							writer.write("&");

						if ( useStringEncoding ) {
							strKey = new String(strKey.getBytes(strStringEnCode), strStringEnCode);
							strValue = new String(strValue.getBytes(strStringEnCode), strStringEnCode);
						}

						if ( useUrlEncode ) {
							strValue = URLEncoder.encode(strValue, strStringEnCode);
						}

						log.debug("new String( strKey.getBytes(" + strStringEnCode + ") ) : " + strKey);
						log.debug("new String( strValue.getBytes(" + strStringEnCode + ") ) : " + strValue);
						String string = ( strKey + "=" + strValue );

						log.info("k=v : " + string);

						writer.write(string);

						nLoopCnt++ ;
					}

				}

				log.debug("WEB SERVICE PARAM SET+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++END");

				writer.close();
			}

		}

		conn.connect();

		WebServiceResponse response = new WebServiceResponse();

		int responseCode = conn.getResponseCode();
		response.setStatusCode(responseCode);

		Map<String, List<String>> resHeaderMap = conn.getHeaderFields();
		responseHeaders = new HashMap<String, List<String>>();

		int hNo = 1;

		for ( Map.Entry<String, List<String>> entry : resHeaderMap.entrySet() ) {
			// 각 리스트를 새 리스트로 깊은 복사
			String hKey = ObjectUtils.defaultIfNull(entry.getKey(), "_EMPTY_HEADER_" + String.valueOf(hNo++ ));

			if ( responseHeaders.containsKey(hKey) ) {
				responseHeaders.get(hKey).addAll(entry.getValue());
			} else {
				responseHeaders.put(hKey, new ArrayList<>(entry.getValue()));
			}

		}

		InputStream is = null;

		try {

			try {
				is = conn.getInputStream();
			} catch ( IOException e ) {
				is = conn.getErrorStream();
			}

			resultMessage = getResultMessage(is, useStringEncoding, useStreamEncoding);
		} finally {
			if ( is != null )
				is.close();
		}

		log.debug("disconnect HttpsConnection");
		// Finally 처리시 connect 자체가 안된경우 disconnect 에서 행걸림
		conn.disconnect();

		response.setMsg(resultMessage);

		return response;

	}

	/**
	 * 
	 * <PRE>
	 * 	메세지 획득
	 * </PRE>
	 *
	 * @param  is
	 * @param  useStringEncoding
	 * @param  useStreamEncoding
	 * @return
	 * @throws IOException
	 */
	private String getResultMessage(InputStream is, boolean useStringEncoding, boolean useStreamEncoding) throws IOException {

		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();

		try {
			if ( useStreamEncoding )
				reader = new BufferedReader(new InputStreamReader(is, strStreamEnCode));
			else
				reader = new BufferedReader(new InputStreamReader(is));

			String strLine = null;

			if ( !noPrintResult ) {
				log.info("{} : Result", strHtmlUrl);
				log.debug("HTML PARSE RESULT===========================================================START");
			}

			while ( ( strLine = reader.readLine() ) != null ) {

				if ( !noPrintResult ) {
					log.info(strLine);
				}

				sb.append(strLine).append("\n");
			}

			if ( !noPrintResult )
				log.debug("HTML PARSE RESULT===========================================================END");
		} catch ( Exception e ) {
			LogUtil.exceptionLoging(log, e);
		} finally {
			if ( reader != null )
				reader.close();
			if ( is != null )
				is.close();
		}

		return sb.toString();

	}

	/**
	 * DATA 전송을 위한 MODEL
	 */
	public static class TransferData {

		private String contentDesc;
		private String contentType;
		private String value;
		private InputStream is;

		/**
		 * <pre>
		 * 	input data set
		 * </pre>
		 * 
		 * @param key   : name 필드
		 * @param value : 값
		 */
		public TransferData(String key, String value) {

			setInput(key, value);

		}

		/**
		 * <pre>
		 * 	input data set
		 * </pre>
		 * 
		 * @param key   : name 필드
		 * @param value : 값
		 * @param cs    : 페이지의 문자열셋 (charset=???)
		 */
		public TransferData(String key, String value, Charset cs) {

			setInput(key, value, cs);

		}

		/**
		 * <pre>
		 * 	file data set
		 * </pre>
		 * 
		 * @param  key         : name 필드
		 * @param  fileName    : 파일의 이름
		 * @param  f           : File
		 * @throws IOException : 유효하지 않은 파일
		 */
		public TransferData(String key, String fileName, File f) throws IOException {

			setFile(key, fileName, f);

		}

		/**
		 * <pre>
		 *	Spring MultipartFile 로 처리
		 * </pre>
		 * 
		 * @param  key                   : name 필드
		 * @param  mpf                   : 스프링 MultipartFile
		 * @throws IllegalStateException
		 * @throws IOException
		 */
		public TransferData(String key, MultipartFile mpf) throws IllegalStateException, IOException {

			setFile(key, mpf);

		}

		/**
		 * <pre>
		 * 	빈파일 첨부
		 * </pre>
		 * 
		 * @param key : name 필드
		 */
		public TransferData(String key) {

			setEmptyFile(key);

		}

		/**
		 * <pre>
		 * 	input data set
		 * </pre>
		 * 
		 * @param key   : name 필드
		 * @param value : 값
		 */
		public void setInput(String key, String value) {

			setInput(key, value, null);

		}

		/**
		 * <pre>
		 * 	input data set
		 * </pre>
		 * 
		 * @param key   : name 필드
		 * @param value : 값
		 * @param cs    : 페이지의 문자열셋 (charset=???)
		 */
		public void setInput(String key, String value, Charset cs) {

			contentDesc = "Content-Disposition: form-data; name=\"" + key + "\"";
			contentType = "text/plain";

			if ( cs != null )
				contentType = contentType + "; charset=" + cs.displayName();

			this.value = value;

		}

		/**
		 * <pre>
		 *	Spring MultipartFile 로 처리
		 * </pre>
		 * 
		 * @param  key                   : name 필드
		 * @param  mpf                   : 스프링 MultipartFile
		 * @throws IllegalStateException
		 * @throws IOException
		 */
		public void setFile(String key, MultipartFile mpf) throws IllegalStateException, IOException {

			String fnm = mpf.getOriginalFilename();

			setFile(key, fnm, mpf.getContentType(), mpf.getInputStream());

		}

		/**
		 * <pre>
		 * 	file data set
		 * </pre>
		 * 
		 * @param  key         : name 필드
		 * @param  fileName    : 파일의 이름
		 * @param  f           : File
		 * @throws IOException : 유효하지 않은 파일
		 */
		public void setFile(String key, String fileName, File f) throws IOException {

			setFile(key, fileName, Files.probeContentType(f.toPath()), new FileInputStream(f));

		}

		/**
		 * <pre>
		 *	File data Set
		 * </pre>
		 * 
		 * @param key         : name 필드
		 * @param fileName    : 파일명
		 * @param contentType : 컨텐츠 유형
		 * @param is          : 입력 스트림
		 */
		public void setFile(String key, String fileName, String contentType, InputStream is) {

			contentDesc = "Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + fileName + "\"";
			this.contentType = contentType;
			this.is = is;
			value = null;

		}

		/**
		 * <pre>
		 * 	빈파일 첨부
		 * </pre>
		 * 
		 * @param key : name 필드
		 */
		public void setEmptyFile(String key) {

			this.contentDesc = "Content-Disposition: form-data; name=\"" + key + "\"; filename=\"\"";
			this.contentType = "application/octet-stream";
			this.is = new ByteArrayInputStream(new byte[0]); // 빈 내용
			this.value = null;

		}

		public String getContentType() {

			return contentType;

		}

		public void setContentType(String contentType) {

			this.contentType = contentType;

		}

		/**
		 * <pre>
		 *	파일을 스트림에 작성
		 * </pre>
		 * 
		 * @param  os                           : Os
		 * @param  boundary                     : 파일의 구분값으로 지정한 값
		 * @throws UnsupportedEncodingException : 지원하지 않는 인코딩
		 * @throws IOException                  : 지원하지 않는 파일
		 */
		public void write(OutputStream os, PrintWriter w, String boundary) throws IOException {

			w.append("--" + boundary).append(LF);
			w.append(contentDesc).append(LF);
			w.append("Content-Type: ").append(contentType).append(LF);
			w.append(LF);
			w.flush();

			if ( value != null ) {
				w.append(value).append(LF);
				w.flush();
			} else if ( is != null ) {
				byte[] buffer = new byte[8192];
				int len;

				while ( ( len = is.read(buffer) ) != -1 ) {
					os.write(buffer, 0, len);
				}

				os.flush();
				w.append(LF).flush();
				is.close(); // 스트림 닫기
			}

		}

	}

	public static class WebServiceResponse {

		private int statusCode;
		private String msg;

		public int getStatusCode() {

			return statusCode;

		}

		public void setStatusCode(int statusCode) {

			this.statusCode = statusCode;

		}

		public String getMsg() {

			return msg;

		}

		public void setMsg(String msg) {

			this.msg = msg;

		}

	}

}
