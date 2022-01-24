package everyone.delivery.demo.common.exception.error;

import everyone.delivery.demo.common.response.ResponseError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserError implements RestError{

    SIGNUP_FAIL_EMAIL_OVERLAP(HttpStatus.BAD_REQUEST, "회원가입 실패. 중복된 이메일이 있습니다."),
    LOGIN_FAIL_PASSWORD(HttpStatus.BAD_REQUEST, "로그인 실패. 비밀번호를 확인해 주세요."),
    LOGIN_FAIL_EMAIL(HttpStatus.BAD_REQUEST, "로그인 실패. 이메일을 확인해 주세요.");

    private HttpStatus httpStatus;
    private String errorMsg;

    @Override
    public ResponseError toResponseError(){
        return ResponseError.builder()
                .errorCode(this.name())
                .errorMessage(this.errorMsg).build();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }
}
