package the9UtilPack;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;


import the9UtilPack.nopLogger.LoggerFactory;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;


public class JsonMapperUtil {

	private final static JsonMapper mapper = JsonMapper.builder().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).build();
	private final static Logger log = LoggerFactory.getLogger(JsonMapper.class);

	/**
	 * 
	 * <pre>
	 *	JSON String 을 Object로 치환
	 * </pre>
	 *
	 * @param  jsonString
	 * @param  clazz
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public static < T > T jsonToObject(String jsonString, Class<T> clazz) {

		return mapper.readValue(jsonString, clazz);

	}

	/**
	 * 
	 * <pre>
	 *	JSON String 을 Object로 치환
	 *  오류 발생시 null 값 반환
	 * </pre>
	 *
	 * @param  jsonString
	 * @param  clazz
	 * @return
	 */
	public static < T > T jsonToObjectFailNull(String jsonString, Class<T> clazz) {

		try {
			return jsonToObject(jsonString, clazz);
		} catch ( Exception e ) {
			log.error(LogUtil.getStackTraceString(e.getStackTrace()));
			return null;
		}

	}

	/**
	 * 
	 * <pre>
	 *	JSON String 을 Object로 치환
	 * </pre>
	 *
	 * @param  jsonString
	 * @param  typeReference
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public static < T > T jsonToObject(String jsonString, TypeReference<T> typeReference) {

		return mapper.readValue(jsonString, typeReference);

	}

	/**
	 * 
	 * <pre>
	 *	JSON String 을 Object로 치환
	 *  오류 발생시 null 값 반환
	 * </pre>
	 *
	 * @param  jsonString
	 * @param  typeReference
	 * @return
	 */
	public static < T > T jsonToObjectFailNull(String jsonString, TypeReference<T> typeReference) {

		try {
			return jsonToObject(jsonString, typeReference);
		} catch ( Exception e ) {
			log.error(LogUtil.getStackTraceString(e.getStackTrace()));
			return null;
		}

	}

	/**
	 * 
	 * <pre>
	 *	JSON String 을 Map으로 치환
	 * </pre>
	 *
	 * @param  jsonString
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public static Map<String, Object> jsonToMap(String jsonString) {

		return mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});

	}

	/**
	 * 
	 * <pre>
	 *	JSON String 을 Map으로 치환
	 *  오류 발생시 null 값 반환
	 * </pre>
	 *
	 * @param  jsonString
	 * @return
	 */
	public static Map<String, Object> jsonToMapFailNull(String jsonString) {

		try {
			return jsonToMap(jsonString);
		} catch ( Exception e ) {
			log.error(LogUtil.getStackTraceString(e.getStackTrace()));
			return null;
		}

	}

	/**
	 * 
	 * <pre>
	 *	JSON String 을 Map으로 치환
	 *  오류 발생시 empty HashMap 값 반환
	 * </pre>
	 *
	 * @param  jsonString
	 * @return
	 */
	public static Map<String, Object> jsonToMapFailEmpty(String jsonString) {

		Map<String, Object> result = jsonToMapFailNull(jsonString);
		return result == null ? new HashMap<>() : result;

	}

	/**
	 * 
	 * <pre>
	 *	JSON String 반환
	 * </pre>
	 *
	 * @param  object
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String toJsonString(Object object) {

		return mapper.writeValueAsString(object);

	}

	/**
	 * 
	 * <pre>
	 *	JSON String 반환
	 *  오류 발생시 null 값 반환
	 * </pre>
	 *
	 * @param  object
	 * @return
	 */
	public static String toJsonStringFailNull(Object object) {

		try {
			return toJsonString(object);
		} catch ( Exception e ) {
			log.error(LogUtil.getStackTraceString(e.getStackTrace()));
			return null;
		}

	}

	/**
	 * 
	 * <pre>
	 *	JSON String 반환
	 *  오류 발생시 empty String 값 반환
	 * </pre>
	 *
	 * @param  object
	 * @return
	 */
	public static String toJsonStringFailEmpty(Object object) {

		String result = toJsonStringFailNull(object);
		return result == null ? "" : result;

	}

	/**
	 * 
	 * <pre>
	 *	Map 을 Object (DTO) 로 치환
	 * </pre>
	 *
	 * @param  map   : 데이터가 있는 맵
	 * @param  clazz : 대상 클래스 유형
	 * @return
	 */
	public static < T > T mapToObject(Map<String, Object> map, Class<T> clazz) {

		return mapper.convertValue(map, clazz);

	}

	/**
	 * 
	 * <pre>
	 *	Object -> Object 치환 (상호 호환 DTO 끼리의 치환)
	 * </pre>
	 *
	 * @param  object : 데이터가 있는 DTO
	 * @param  clazz  : 데이터를 넣어줄(이것으로 변경을 원하는) DTO 클래스 유형
	 * @return
	 */
	public static < T > T objectToObject(Object object, Class<T> clazz) {

		return mapper.convertValue(object, clazz);

	}

	/**
	 * 
	 * <pre>
	 *	Object -> Object 치환 (상호 호환 DTO 끼리의 치환)
	 * </pre>
	 *
	 * @param  object    : 데이터가 있는 DTO
	 * @param  reference : 데이터를 넣어줄(이것으로 변경을 원하는) DTO 클래스 유형
	 * @return
	 */
	public static < T > T objectToObject(Object object, TypeReference<T> reference) {

		return mapper.convertValue(object, reference);

	}

	/**
	 * 
	 * <pre>
	 *	Object -> Map 치환
	 * </pre>
	 *
	 * @param  object : 데이터가 있는 오브젝트값
	 * @return
	 */
	public static Map<String, Object> objectToMap(Object object) {

		return mapper.convertValue(object, new TypeReference<Map<String, Object>>() {});

	}

}
