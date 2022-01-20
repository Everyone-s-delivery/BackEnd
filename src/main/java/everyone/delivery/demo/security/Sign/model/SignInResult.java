package everyone.delivery.demo.security.Sign.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResult{
    private String token;
    private Long userId;
}