package everyone.delivery.demo.security;

import everyone.delivery.demo.Response.CommonResult;
import everyone.delivery.demo.exception.AccessDeniedException;
import everyone.delivery.demo.exception.AuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/JWTException")
@ApiIgnore
public class ExceptionController {

	@GetMapping(value = "/EmptyJWTToken")
	public CommonResult entrypointException() {
		throw new AuthenticationEntryPointException("JWT: You do not have permission to access this resource.");
	}
	
	@GetMapping(value = "/accessdenied")
	public CommonResult accessdeniedException() {
	        throw new AccessDeniedException("JWT: A resource that can not be accessed with the privileges it has.");
	}
}