package everyone.delivery.demo.common.exception;

import everyone.delivery.demo.common.exception.error.CommonError;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class ExceptionUtils<T> {

    /***
     *
     * @param optionalT
     * @param logMsg
     * @param logParam
     * @param <T>
     *
     * optionalT 속에 null이 있으면 LogicalRuntimeException을 던지고 아니면 그 값을 리턴
     * @return
     */
    public static <T> T ifNullThrowElseReturnVal(Optional<T> optionalT, String logMsg, Object logParam){
        return optionalT.orElseThrow(()->{
            log.error(logMsg, logParam);
            return new LogicalRuntimeException(CommonError.INVALID_DATA);
        });
    }

    public static <T> T ifNullThrowElseReturnVal(Optional<T> optionalT){
        return optionalT.orElseThrow(()->{
            return new LogicalRuntimeException(CommonError.INVALID_DATA);
        });
    }


}
