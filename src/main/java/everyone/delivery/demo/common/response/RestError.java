package everyone.delivery.demo.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/***
 * 미리 정의해 둔 에러들
 */
@AllArgsConstructor
@Getter
public enum RestError {

    INVALID_DATA(HttpStatus.BAD_REQUEST,"요청 오류 입니다.(의미상 오류)"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청 오류 입니다.(형식상 오류)"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 입니다.");

    private HttpStatus httpStatus;
    private String errorMsg;

    public ResponseError toResponseError(){
        return ResponseError.builder()
                .errorCode(this.name())
                .errorMessage(this.errorMsg).build();
    }
}
