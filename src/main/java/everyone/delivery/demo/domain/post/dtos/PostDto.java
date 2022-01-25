package everyone.delivery.demo.domain.post.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import everyone.delivery.demo.domain.post.PostEntity;
import everyone.delivery.demo.domain.postComment.PostCommentEntity;
import everyone.delivery.demo.domain.postComment.dtos.PostCommentDto;
import everyone.delivery.demo.security.user.UserEntity;
import everyone.delivery.demo.security.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //응답 결과에서 제외
    private Long postId;

    @NotNull
    private Long posterId;          // 모집글에 대한 작성자 아이디

    @NotNull
    private String posterEmail;       // 모집글에 대한 작성자 이메일

    @NotNull
    private String title;

    @NotNull
    private String description;

    private List<String> addresses;

    private List<PostCommentDto> comments;

    //역직렬화(json to java object) 시 사용 안함 => 역직렬화 할때는 이 필드를 전혀 신경쓰지않고 값이 있든 없든 null 로 채우게된다.
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime regDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updateDate;

    //디비 작업이 필요하기 때문에 DTO -> Entity는 서비스 로직에서 별도 함수로 둘것!
//    @Autowired
//    UserRepository userRepository;
//
//    public PostEntity toEntity(){
//
//        return PostEntity.builder()
//                .postId(postId)
//                .poster()
//    }
}
