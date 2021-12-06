package com.we.app.common;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.naming.SizeLimitExceededException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller(ServieImpl)에서 발생하는 RuntimeException을 처리한다.
 */
@ControllerAdvice
public class VmErrorAdvice {

    private static final Logger log = LoggerFactory.getLogger(VmErrorAdvice.class);
    private static final Logger logError = LoggerFactory.getLogger("only.error.log");

    @Autowired
    MessageSource messageSource;
    @Autowired
    Environment environment;

    @Value("${spring.servlet.multipart.max-file-size:#{null}}")
    private String maxFileSize;

    /**
     * 메일 테이블에 에러로그 저장 여부
     */
    @Value("${project.debug.error-mail-save:false}")
    boolean isErrorMailSave;

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Map<String, Object>> handleException(HttpServletRequest request, Exception ex) {

	String message = "";// "오류가 발생하였습니다.";
	String errorCode = null;
	Map<String, Object> errorData = null;

	Throwable causeEx = ex.getCause();
	boolean isMailSendException = false;

	if (ex instanceof BusinessException) {
	    // 업무상 오류
	    BusinessException be = (BusinessException) ex;
	    errorCode = be.getCode();
	    isMailSendException = be.isMailSend();
	    message = messageSource.getMessage(be.getCode(), be.getArguments(), be.getMessage(), Locale.getDefault());
	    errorData = be.getErrorData();
	} else if (causeEx != null && causeEx instanceof BusinessException) {
	    // 업무상 오류
	    // Exception이 wrapping될 경우 인듯.
	    BusinessException be = (BusinessException) causeEx;
	    errorCode = be.getCode();
	    isMailSendException = be.isMailSend();
	    message = messageSource.getMessage(be.getCode(), be.getArguments(), be.getMessage(), Locale.getDefault());
	    errorData = be.getErrorData();
	} else if (ex instanceof SizeLimitExceededException) {
	    errorCode = "sizeLimitExceeded";
	    message += "첨부파일의 크기가 초과되었습니다. " + maxFileSize + "까지 가능합니다.";
	    logError.error("!!! 오류발생 !!!", ex);
	} else {
	    message = "오류가 발생하였습니다.\n관리자에게 문의하여 주시기 바랍니다.";
	    isMailSendException = true;
	    logError.error("!!! 오류발생 !!!", ex);
	}

	Map<String, Object> body = new HashMap<String, Object>();
	if (errorCode != null) {
	    body.put("errorCode", errorCode);
	}
	body.put("message", message != null ? message.trim() : "");
	if (errorData != null) {
	    body.put("errorData", errorData);
	}
	HttpStatus status = getStatus(request);

	ResponseEntity<Map<String, Object>> re = new ResponseEntity<Map<String, Object>>(body, status);
	return re;
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
	Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
	if (statusCode == null) {
	    return HttpStatus.INTERNAL_SERVER_ERROR;
	}
	try {
	    return HttpStatus.valueOf(statusCode);
	} catch (Exception ex) {
	    return HttpStatus.INTERNAL_SERVER_ERROR;
	}
    }

}
