package com.optimus.rest.webservices.restful_web_services.handler;

import com.optimus.rest.webservices.restful_web_services.common.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.of;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler({ResponseStatusException.class})
	public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode(ex.getStatusCode().value())
			.message(ex.getReason())
			.errors(of())
			.build();
		return new ResponseEntity<>(errorResponse, ex.getStatusCode());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {

		List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath()
				+ ": "
				+ violation.getMessage());
		}

		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode(BAD_REQUEST.value())
			.message(BAD_REQUEST.name())
			.errors(errors)
			.build();
		return new ResponseEntity<>(errorResponse, BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
		MethodArgumentTypeMismatchException ex) {
		String errorMessage = new StringBuilder().append(ex.getName()).append(" should be of type ")
			.append(ex.getRequiredType().getName()).toString();

		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode(BAD_REQUEST.value())
			.message(errorMessage)
			.errors(of())
			.build();

		return new ResponseEntity<>(errorResponse, BAD_REQUEST);
	}

	// overriding the specific method
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
																		  HttpHeaders headers,
																		  HttpStatusCode status,
																		  WebRequest request) {
		String errorMessage = ex.getParameterName() + " parameter is missing";
		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode(BAD_REQUEST.value())
			.message(errorMessage)
			.errors(of())
			.build();

		return new ResponseEntity<>(errorResponse, BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		log.error("HTTP message not readable: {}", ex.getMessage());
		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode(BAD_REQUEST.value())
			.message(BAD_REQUEST.name())
			.errors(of(ex.getMessage()))
			.build();

		return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		log.error("Method argument not valid: ", ex);

		List<String> errors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode(BAD_REQUEST.value())
			.message(BAD_REQUEST.name())
			.errors(errors)
			.build();

		return new ResponseEntity<>(errorResponse, BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		log.error("Http method not supported: ", ex);

		String supportedMethods = ex.getSupportedHttpMethods().stream()
			.map(HttpMethod::name)
			.collect(Collectors.joining(", "));
		String errorMessage = String.format("%s method is not supported for this request. Supported methods are: %s", ex.getMethod(), supportedMethods);

		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode(METHOD_NOT_ALLOWED.value())
			.message(errorMessage)
			.errors(of())
			.build();

		return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		log.error("Http Media type not supported : ", ex);

		String supportedMediaType = ex.getSupportedMediaTypes().stream()
			.map(mediaType -> mediaType.toString())
			.collect(Collectors.joining(", "));

		String errorMessage = String.format("%s content-type is not supported. Supported content-type are: %s", ex.getContentType(), supportedMediaType);
		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode(UNSUPPORTED_MEDIA_TYPE.value())
			.message(errorMessage)
			.errors(of())
			.build();

		return new ResponseEntity<>(errorResponse, UNSUPPORTED_MEDIA_TYPE);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode(NOT_FOUND.value())
			.message(NOT_FOUND.getReasonPhrase())
			.errors(of("Resource not found"))
			.build();

		return ResponseEntity.status(NOT_FOUND).body(errorResponse);
	}

	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode(NOT_FOUND.value())
			.message(NOT_FOUND.getReasonPhrase())
			.errors(of("Resource not found"))
			.build();

		return ResponseEntity.status(NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllException(Exception exception) {
		log.error("global exception thrown", exception);

		ErrorResponse errorResponse = ErrorResponse.builder()
			.errorCode(INTERNAL_SERVER_ERROR.value())
			.message(INTERNAL_SERVER_ERROR.name())
			.errors(of("Error in processing request. Try again later!"))
			.build();
		return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
	}

}
