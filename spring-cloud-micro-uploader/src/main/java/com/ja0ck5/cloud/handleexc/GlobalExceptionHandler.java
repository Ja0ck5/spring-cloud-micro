package com.ja0ck5.cloud.handleexc;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Jack on 2017/10/18.
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	@ExceptionHandler(MultipartException.class)
	public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

		return "this is an error!" + e;
	}

}
