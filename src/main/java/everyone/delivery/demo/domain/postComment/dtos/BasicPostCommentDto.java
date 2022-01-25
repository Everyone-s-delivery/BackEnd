package everyone.delivery.demo.domain.postComment.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicPostCommentDto {

    @NotNull(message = "Not enough comment data. commenterId cannot be null.")
    private Long commenterId;

    @NotNull(message = "Not enough comment data. commenterEmail cannot be null.")
    private String commenterEmail;

    @NotNull(message = "Not enough comment data. postId cannot be null.")
    private Long postId;

    @NotNull(message = "Not enough comment data. comment cannot be null.")
    private String comment;

}
