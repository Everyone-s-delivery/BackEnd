package everyone.delivery.demo.common.response;

import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static ResponseEntity out(Response response){
        if(response.hasError()){
            RestError restError = response.getError();
            return ResponseEntity.status(restError.getHttpStatus()).body(restError.toResponseError());
        }
        else{
            return ResponseEntity.ok(response.getData());
        }
    }

    public static ResponseEntity out(RestError restError){
        return ResponseEntity.status(restError.getHttpStatus()).body(restError.toResponseError());
    }

    public static <T> Response<T> getSuccessResponse(T data){
        return new Response<T>(data,null);
    }
}
