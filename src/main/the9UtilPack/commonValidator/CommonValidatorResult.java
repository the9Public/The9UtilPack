package the9UtilPack.commonValidator;

public class CommonValidatorResult {

	private boolean isOK;
	private String strMessage;
	// 기타코드 - ACT_ETCOCDE.CODE_GRP = 354 에 사전설정된 코드를 따름
	// 설정값은 Validator에서 전달한다.
	private String strStateCode;

	/**
	 * <PRE>
	 * 	초기값은 정상
	 * </PRE>
	 */
	public CommonValidatorResult() {

		isOK = true;
		strMessage = "";
		strStateCode = "0000";

	}

	protected CommonValidatorResult setResultSet(boolean isOK, String strMessage, String strStateCode) {

		this.isOK = isOK;
		this.strMessage = strMessage;
		this.strStateCode = strStateCode;
		return this;

	}

	/**
	 * <PRE>
	 * 	오류 상태에대한 값을 받는다.
	 * 		true : 오류없음
	 * </PRE>
	 *
	 * @return
	 */
	public boolean isOk() {

		return isOK;

	}

	/**
	 * <PRE>
	 * 	메세지를 얻어온다
	 * </PRE>
	 *
	 * @return
	 */
	public String getMessage() {

		return strMessage;

	}

	/**
	 * <PRE>
	 * 	기타코드 354에 기술되어있는 상태코드 반환
	 * </PRE>
	 *
	 * @return
	 */
	public String getStateCode() {

		return strStateCode;

	}

}
