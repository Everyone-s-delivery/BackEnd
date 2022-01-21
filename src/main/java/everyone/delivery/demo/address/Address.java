package everyone.delivery.demo.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/***
 * 임베디드 타입 설명
 * https://velog.io/@conatuseus/JPA-%EC%9E%84%EB%B2%A0%EB%94%94%EB%93%9C-%ED%83%80%EC%9E%85embedded-type-8ak3ygq8wo
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String roadAddress;
    private String jibunAddress;
}
