package everyone.delivery.demo.security.Sign;

import everyone.delivery.demo.Response.CommonResult;
import everyone.delivery.demo.Response.SingleResult;
import everyone.delivery.demo.exception.CustomNullPointException;
import everyone.delivery.demo.security.Sign.model.SignInResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = { "* 회원가입 & 로그인 API" })
@RequiredArgsConstructor
@RestController
public class SignController {
	private final SignService signService;

	@ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
	@PostMapping(value = "/signin")
	public SingleResult<SignInResult> signin(@ApiParam(value = "이메일", required = true) @RequestParam("email") String email,
											 @ApiParam(value = "비밀번호", required = true) @RequestParam("password") String password) {
		if(email == null || password == null)
			throw  new CustomNullPointException("email or password should be included");
		return signService.signin(email,password);
	}

	@ApiOperation(value = "가입", notes = "회원가입을 한다.")
	@PostMapping(value = "/signup")
	public CommonResult signup(@ApiParam(value = "이메일", required = true) @RequestParam("email") String email,
							   @ApiParam(value = "비밀번호", required = true) @RequestParam("password") String password) {
		if(email == null || password == null)
			throw  new CustomNullPointException("email or password should be included");
		return signService.signup(email, password);
	}
}