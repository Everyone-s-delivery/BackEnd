package everyone.delivery.demo.common.exception.advice;

import everyone.delivery.demo.common.exception.LogicalRuntimeException;
import everyone.delivery.demo.common.response.ResponseUtils;
import everyone.delivery.demo.common.response.CommonRestError;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice(basePackages="everyone.delivery.demo")
public class CommonControllerAdvice {

    @ExceptionHandler(LogicalRuntimeException.class)
    public ResponseEntity<?> logicalRuntimeExceptionHandler(LogicalRuntimeException ex) {
        return ResponseUtils.out(ex.getRestError());
    }

    /***
     * 요청 데이터를 객체로 변환하지 못할 경우 발생하는 예외에 대한 핸들러
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<?> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        return ResponseUtils.out(CommonRestError.BAD_REQUEST);
    }

    /***
     * 요청 데이터를 객채가 아니라 바로 파라미터로 받을 경우, 검증 과정에서 오류가 발생한 경우
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        return ResponseUtils.out(CommonRestError.BAD_REQUEST);
    }

    /***
     * 요청 데이터를 객체로 변환했으나 검증 과정에서 실패한 예외에 대한 핸들러
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        return ResponseUtils.out(CommonRestError.BAD_REQUEST);
    }

    /***
     * [multipart/form-data] 요청에 대해
     * RequestPart(required=true) 필드 값이 안들어 온 경우 예외에 대한 핸들러
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    protected ResponseEntity<?> missingServletRequestPartExceptionHandler(MissingServletRequestPartException ex) {
        return ResponseUtils.out(CommonRestError.BAD_REQUEST);
    }
}
