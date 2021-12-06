/**
* Template Name: VMERP
* Version: 1.0.0
* Author: Lim kisung
* Date : 2016. 08. 04
*/

package com.we.app.common;

import java.util.Map;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 4496279633996517643L;

	private String code;

	private Object[] arguments;

	private boolean isMailSend = false;

	private Map<String, Object> errorData;

	//////////////////////
	// BusinessException
	//////////////////////
	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, String errorCode) {
		super(message);
		this.code = errorCode;
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message, String errorCode, Throwable cause) {
		super(message, cause);
		this.code = errorCode;
	}

	///////////////////////////////////////
	// BusinessException.create(message) //
	///////////////////////////////////////
	/**
	 * 비지니스 exception을 발생시킨다. 처리중인 작업이 종료되고 사용자에게 메시지를 표시한다.
	 * 
	 * @param message 메시지
	 * @return
	 */
	public static BusinessException create(String message) {
		BusinessException ex = new BusinessException(message);
		return ex;
	}

	/**
	 * 비지니스 exception을 발생시킨다. 처리중인 작업이 종료되고 사용자에게 메시지를 표시한다.
	 * 
	 * @param message    메시지
	 * @param isMailSend 관리자에게 메일 발송여부
	 * @return
	 */
	public static BusinessException create(String message, boolean isMailSend) {
		BusinessException ex = new BusinessException(message);
		ex.setMailSend(isMailSend);
		return ex;
	}

	/**
	 * 비지니스 exception을 발생시킨다. 처리중인 작업이 종료되고 사용자에게 메시지를 표시한다.
	 * 
	 * @param message   메시지
	 * @param errorData 메시지 이외에 화면에 전달할 처리중인 오류 정보.
	 * @return
	 */
	public static BusinessException create(String message, Map<String, Object> errorData) {
		BusinessException ex = new BusinessException(message);
		ex.setErrorData(errorData);
		return ex;
	}

	/**
	 * 비지니스 exception을 발생시킨다. 처리중인 작업이 종료되고 사용자에게 메시지를 표시한다.
	 * 
	 * @param message    메시지
	 * @param isMailSend 관리자에게 메일 발송여부
	 * @param errorData  메시지 이외에 화면에 전달할 처리중인 오류 정보.
	 * @return
	 */
	public static BusinessException create(String message, boolean isMailSend, Map<String, Object> errorData) {
		BusinessException ex = new BusinessException(message);
		ex.setMailSend(isMailSend);
		ex.setErrorData(errorData);
		return ex;
	}

	//////////////////////////////////////////////////
	// BusinessException.create(message, arguments) //
	//////////////////////////////////////////////////
	/**
	 * 비지니스 exception을 발생시킨다. 처리중인 작업이 종료되고 사용자에게 메시지를 표시한다. throw
	 * BusinessException.create("처리중 {0}번째 {1}번째 오류입니다.", new Object[] {1, "두"});
	 * 
	 * @param message   메시지. arguments의 값이 "{0}"에 치환된다.
	 * @param arguments 메시지에서 {0}, {1}, {2}...에 치환될 데이터
	 * @return
	 */
	public static BusinessException create(String message, Object[] arguments) {
		BusinessException ex = new BusinessException(message);
		ex.setArguments(arguments);
		return ex;
	}

	/**
	 * 비지니스 exception을 발생시킨다. 처리중인 작업이 종료되고 사용자에게 메시지를 표시한다. throw
	 * BusinessException.create("처리중 {0}번째 {1}번째 오류입니다.", new Object[] {1, "두"});
	 * 
	 * @param message   메시지. arguments의 값이 "{0}"에 치환된다.
	 * @param arguments 메시지에서 {0}, {1}, {2}...에 치환될 데이터
	 * @param isMailSend 관리자에게 메일 발송여부
	 * @return
	 */
	public static BusinessException create(String message, Object[] arguments, boolean isMailSend) {
		BusinessException ex = new BusinessException(message);
		ex.setArguments(arguments);
		ex.setMailSend(isMailSend);
		return ex;
	}

	/**
	 * 비지니스 exception을 발생시킨다. 처리중인 작업이 종료되고 사용자에게 메시지를 표시한다. throw
	 * BusinessException.create("처리중 {0}번째 {1}번째 오류입니다.", new Object[] {1, "두"});
	 * 
	 * @param message   메시지. arguments의 값이 "{0}"에 치환된다.
	 * @param arguments 메시지에서 {0}, {1}, {2}...에 치환될 데이터
	 * @param errorData  메시지 이외에 화면에 전달할 처리중인 오류 정보.
	 * @return
	 */
	public static BusinessException create(String message, Object[] arguments, Map<String, Object> errorData) {
		BusinessException ex = new BusinessException(message);
		ex.setArguments(arguments);
		ex.setErrorData(errorData);
		return ex;
	}

	/**
	 * 비지니스 exception을 발생시킨다. 처리중인 작업이 종료되고 사용자에게 메시지를 표시한다. throw
	 * BusinessException.create("처리중 {0}번째 {1}번째 오류입니다.", new Object[] {1, "두"});
	 * 
	 * @param message   메시지. arguments의 값이 "{0}"에 치환된다.
	 * @param arguments 메시지에서 {0}, {1}, {2}...에 치환될 데이터
	 * @param isMailSend 관리자에게 메일 발송여부
	 * @param errorData  메시지 이외에 화면에 전달할 처리중인 오류 정보.
	 * @return
	 */
	public static BusinessException create(String message, Object[] arguments, boolean isMailSend,
			Map<String, Object> errorData) {
		BusinessException ex = new BusinessException(message);
		ex.setArguments(arguments);
		ex.setMailSend(isMailSend);
		ex.setErrorData(errorData);
		return ex;
	}

	//////////////////////////////////////////////////
	// BusinessException.create(message, errorCode, arguments) //
	//////////////////////////////////////////////////

	/**
	 * 비지니스 exception을 발생시킨다. 처리중인 작업이 종료되고 사용자에게 메시지를 표시한다.
	 * 
	 * @param message   메시지.
	 * @param errorCode 오류코드. 화면에서 if 등 으로 비교할 때 사용할 수 있는 코드.
	 * @return
	 */
	public static BusinessException create(String message, String errorCode) {
		BusinessException ex = new BusinessException(message, errorCode);
		return ex;
	}

	/**
	 * 비지니스 exception을 발생시킨다. 처리중인 작업이 종료되고 사용자에게 메시지를 표시한다. throw
	 * BusinessException.create("처리중 {0}번째 {1}번째 오류입니다.", new Object[] {1, "두"});
	 * 
	 * @param message   메시지. arguments의 값이 "{0}"에 치환된다.
	 * @param errorCode 오류코드. 화면에서 if 등 으로 비교할 때 사용할 수 있는 코드.
	 * @param arguments 메시지에서 {0}, {1}, {2}...에 치환될 데이터
	 * @return
	 */
	public static BusinessException create(String message, String errorCode, Object[] arguments) {
		BusinessException ex = new BusinessException(message, errorCode);
		ex.setArguments(arguments);
		return ex;
	}

	/**
	 * 비지니스 exception을 발생시킨다. 처리중인 작업이 종료되고 사용자에게 메시지를 표시한다. throw
	 * BusinessException.create("처리중 {0}번째 {1}번째 오류입니다.", new Object[] {1, "두"});
	 * 
	 * @param message   메시지. arguments의 값이 "{0}"에 치환된다.
	 * @param errorCode 오류코드. 화면에서 if 등 으로 비교할 때 사용할 수 있는 코드.
	 * @param errorData  메시지 이외에 화면에 전달할 처리중인 오류 정보.
	 * @return
	 */
	public static BusinessException create(String message, String errorCode, Map<String, Object> errorData) {
		BusinessException ex = new BusinessException(message, errorCode);
		ex.setErrorData(errorData);
		return ex;
	}

	/**
	 * 비지니스 exception을 발생시킨다. 처리중인 작업이 종료되고 사용자에게 메시지를 표시한다. throw
	 * BusinessException.create("처리중 {0}번째 {1}번째 오류입니다.", new Object[] {1, "두"});
	 * 
	 * @param message   메시지. arguments의 값이 "{0}"에 치환된다.
	 * @param errorCode 오류코드. 화면에서 if 등 으로 비교할 때 사용할 수 있는 코드.
	 * @param isMailSend 관리자에게 메일 발송여부
	 * @return
	 */
	public static BusinessException create(String message, String errorCode, boolean isMailSend) {
		BusinessException ex = new BusinessException(message, errorCode);
		ex.setMailSend(isMailSend);
		return ex;
	}

	/**
	 * 비지니스 exception을 발생시킨다. 처리중인 작업이 종료되고 사용자에게 메시지를 표시한다. throw
	 * BusinessException.create("처리중 {0}번째 {1}번째 오류입니다.", new Object[] {1, "두"});
	 * 
	 * @param message   메시지. arguments의 값이 "{0}"에 치환된다.
	 * @param errorCode 오류코드. 화면에서 if 등 으로 비교할 때 사용할 수 있는 코드.
	 * @param arguments 메시지에서 {0}, {1}, {2}...에 치환될 데이터
	 * @param isMailSend 관리자에게 메일 발송여부
	 * @return
	 */
	public static BusinessException create(String message, String errorCode, Object[] arguments, boolean isMailSend) {
		BusinessException ex = new BusinessException(message, errorCode);
		ex.setArguments(arguments);
		ex.setMailSend(isMailSend);
		return ex;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String errorCode) {
		this.code = errorCode;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public boolean isMailSend() {
		return isMailSend;
	}

	public void setMailSend(boolean isMailSend) {
		this.isMailSend = isMailSend;
	}

	public Map<String, Object> getErrorData() {
		return errorData;
	}

	public void setErrorData(Map<String, Object> errorData) {
		this.errorData = errorData;
	}
}
