package everyone.delivery.demo.security.user.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import everyone.delivery.demo.address.Address;
import everyone.delivery.demo.address.InterestedAddress;
import everyone.delivery.demo.security.user.UserEntity;
import everyone.delivery.demo.security.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/***
 * UI에서 요청 받을 때 사용할 사용자 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicUserDto {

    @NotNull
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String password;

    @NotNull
    private Address address;

    @NotNull
    private List<InterestedAddress> interestedAddress;

    public UserEntity toEntity(){
        return UserEntity.builder()
                            .email(email)
                            .password(password)
                            .address(address)
                            .interestedAddress(interestedAddress)
                            .build();
    }

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)	//직렬화 과정에서 제외시킨다는 의미
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> list = new ArrayList<>();
//        for(UserRole role: roles){
//            list.add(new SimpleGrantedAuthority(role.name()));
//        }
//        return list;
//    }

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)	//직렬화 과정에서 제외시킨다는 의미
//    @Override
//    public String getUsername() {
//        return this.email;
//    }
//
//    //계정이 만료되었는지
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    //계정이 잠기지 않았는지
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    //계정 패스워드가 만료 안됬는지
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    //계정이 사용 가능한지
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

}
