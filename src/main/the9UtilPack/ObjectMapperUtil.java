package the9UtilPack;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import the9UtilPack.nopLogger.LoggerFactory;


@SuppressWarnings ( {
	"unchecked", "rawtypes"
} )
public class ObjectMapperUtil {

	private final static ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	private final static Logger log = LoggerFactory.getLogger(ObjectMapperUtil.class);

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
	public static Object jsonToObject(String jsonString, Class clazz) throws JsonMappingException, JsonProcessingException {

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
	public static Object jsonToObjectFailNull(String jsonString, Class clazz) {

		Object result = null;

		try {
			result = jsonToObject(jsonString, clazz);
		} catch ( Exception e ) {
			log.error(LogUtil.getStackTraceString(e.getStackTrace()));
		}

		return result;

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
	public static Object jsonToObject(String jsonString, TypeReference<?> typeReference) throws JsonMappingException, JsonProcessingException {

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
	public static Object jsonToObjectFailNull(String jsonString, TypeReference<?> typeReference) {

		Object result = null;

		try {
			result = jsonToObject(jsonString, typeReference);
		} catch ( Exception e ) {
			log.error(LogUtil.getStackTraceString(e.getStackTrace()));
		}

		return result;

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
	public static Map<String, Object> jsonToMap(String jsonString) throws JsonMappingException, JsonProcessingException {

		return mapper.readValue(jsonString, Map.class);

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

		return (Map<String, Object>) jsonToObjectFailNull(jsonString, Map.class);

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

		Map<String, Object> result = (Map<String, Object>) jsonToObjectFailNull(jsonString, Map.class);
		return result == null ? new HashMap<String, Object>() : result;

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
	public static String toJsonString(Object object) throws JsonProcessingException {

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

		String jsonString = null;

		try {
			jsonString = mapper.writeValueAsString(object);
		} catch ( JsonProcessingException e ) {
			log.error(LogUtil.getStackTraceString(e.getStackTrace()));
		}

		return jsonString;

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

		String jsonString = "";

		try {
			jsonString = mapper.writeValueAsString(object);
		} catch ( JsonProcessingException e ) {
			log.error(LogUtil.getStackTraceString(e.getStackTrace()));
		}

		return jsonString;

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
	public static Object mapToObject(Map<String, Object> map, Class<?> clazz) {

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
	public static Object objectToObject(Object object, Class<?> clazz) {

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
	public static Object objectToObject(Object object, TypeReference<?> reference) {

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
	public static Map objectToMap(Object object) {

		return mapper.convertValue(object, Map.class);

	}

}
