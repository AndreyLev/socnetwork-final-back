package ru.rosbank.javaschool.crudapi.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import ru.rosbank.javaschool.crudapi.dto.ErrorResponseDto;
import ru.rosbank.javaschool.crudapi.exception.BadRequestException;
import ru.rosbank.javaschool.crudapi.exception.UnsupportedFileTypeException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class RestErrorController extends AbstractErrorController {
  private final ErrorAttributes errorAttributes;
  private final String path;
  private final MessageSource messageSource;

  public RestErrorController(ErrorAttributes errorAttributes, @Value("${server.error.path:${error.path:/error}}") String path, MessageSource messageSource) {
    super(errorAttributes);
    this.errorAttributes = errorAttributes;
    this.path = path;
    this.messageSource = messageSource;
  }

  @RequestMapping
  public ResponseEntity<ErrorResponseDto> error(HttpServletRequest request, Locale locale) {
    ServletWebRequest webRequest = new ServletWebRequest(request);
    Throwable error = errorAttributes.getError(webRequest);
    int status = getStatus(request).value();
    String message = "error.unknown";
    String defaultMessage = messageSource.getMessage(message, null, locale);
    String translated = defaultMessage;
    // null - норм
    if (error == null) {
      // something bad happened
      // Builder -> позволяет собирать сложные в несколько вызовов (без передачи всех параметров)
      return ResponseEntity.status(status).body(
          new ErrorResponseDto(status, message, translated, Collections.emptyMap())
      );
    }

    if (error instanceof BadRequestException) {
      status = 400; // TODO: move to constants
      message = "error.bad_request";
      translated = messageSource.getMessage(message, null, defaultMessage, locale);
      return getErrorResponseDtoResponseEntity(error, status, message, translated);
    }

    if (error instanceof UnsupportedFileTypeException) {
      status = 400; // TODO: move to constants
      message = "error.bad_filetype";
      translated = messageSource.getMessage(message, null, defaultMessage, locale);
      return getErrorResponseDtoResponseEntity(error, status, message, translated);
    }
    // TODO: what if this stuff throws exception

    if (error instanceof MethodArgumentNotValidException) {
      status = 400;
      message = "error.validation";
      translated = messageSource.getMessage(message, null, defaultMessage, locale);
      final Map<String, List<String>> errors = ((MethodArgumentNotValidException) error).getBindingResult().getFieldErrors().stream()
          .collect(
              Collectors.groupingBy(FieldError::getField,
                  Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())));

      return getErrorResponseDtoResponseEntity(error, status, message, translated, errors);
    }

    // catch all
    return getErrorResponseDtoResponseEntity(error, status, message, translated);
  }

  private ResponseEntity<ErrorResponseDto> getErrorResponseDtoResponseEntity(Throwable error, int status, String message, String translated) {
    return getErrorResponseDtoResponseEntity(error, status, message, translated, Collections.emptyMap());
  }


  private ResponseEntity<ErrorResponseDto> getErrorResponseDtoResponseEntity(Throwable error, int status, String message, String translated, Map<String, List<String>> errors) {
    error.printStackTrace();
    return ResponseEntity.status(status).body(
        new ErrorResponseDto(status, message, translated, errors)
    );
  }

  @Override
  public String getErrorPath() {
    return path;
  }
}
