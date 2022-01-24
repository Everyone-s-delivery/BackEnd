package everyone.delivery.demo.security.user.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import everyone.delivery.demo.domain.address.Address;
import everyone.delivery.demo.domain.address.InterestedAddress;
import everyone.delivery.demo.security.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

/***
 * UI에서 요청 받을 때 사용할 사용자 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicUserDto {

    @NotNull(message = "Not enough user data. email cannot be null.")
    @Email(message = "invalid email form.")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Not enough user data.")
    private String password;

    @NotNull(message = "Not enough user data.")
    private Address address;

    @NotNull(message = "Not enough user data.")
    private List<InterestedAddress> interestedAddress;

    public UserEntity toEntity(){
        return UserEntity.builder()
                            .email(email)
                            .password(password)
                            .address(address)
                            .interestedAddress(interestedAddress)
                            .build();
    }
}
