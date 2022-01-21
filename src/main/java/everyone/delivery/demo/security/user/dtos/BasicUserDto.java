package everyone.delivery.demo.security.user.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import everyone.delivery.demo.security.user.UserEntity;
import everyone.delivery.demo.security.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicUserDto implements UserDetails {

    private String 	email;
    private String 	password;
    private List<UserRole> roles;

    public UserEntity toEntity(){
        return UserEntity.builder()
                            .email(email)
                            .password(password)
                            .roles(roles)
                            .build();
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)	//직렬화 과정에서 제외시킨다는 의미
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        for(UserRole role: roles){
            list.add(new SimpleGrantedAuthority(role.name()));
        }
        return list;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)	//직렬화 과정에서 제외시킨다는 의미
    @Override
    public String getUsername() {
        return this.email;
    }

    //계정이 만료되었는지
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠기지 않았는지
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //계정 패스워드가 만료 안됬는지
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 사용 가능한지
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

}
