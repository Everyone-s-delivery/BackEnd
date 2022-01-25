package everyone.delivery.demo.domain.post.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import everyone.delivery.demo.domain.postComment.dtos.PostCommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicPostDto {

    @NotNull(message = "Not enough post data. posterId cannot be null.")
    private Long posterId;          // 모집글에 대한 작성자 아이디

    @NotNull(message = "Not enough post data. posterEmail cannot be null.")
    private String posterEmail;       // 모집글에 대한 작성자 이메일

    @NotNull(message = "Not enough post data. title cannot be null.")
    private String title;

    @NotNull(message = "Not enough post data. description cannot be null.")
    private String description;

    private List<String> addresses;

    private List<PostCommentDto> comments;
}
