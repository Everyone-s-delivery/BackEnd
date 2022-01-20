package everyone.delivery.demo.security.Sign.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignInResult{
    private String token;
    private Long userId;

    public SignInResult(String token, Long userId){
        this.token = token;
        this.userId = userId;
    }
}