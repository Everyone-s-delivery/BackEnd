package everyone.delivery.demo.domain.postComment.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import everyone.delivery.demo.domain.postComment.PostCommentEntity;
import everyone.delivery.demo.domain.postComment.PostCommentRepository;
import everyone.delivery.demo.security.user.UserEntity;
import everyone.delivery.demo.security.user.UserRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCommentDto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //응답 결과에서 제외
    private Long postCommentId;

    private Long commenterId;

    private String commenterEmail;

    private String comment;

    //역직렬화(json to java object) 시 사용 안함 => 역직렬화 할때는 이 필드를 전혀 신경쓰지않고 값이 있든 없든 null 로 채우게된다.
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime regDate;		//등록일자

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updateDate;	//수정일자

    //디비 작업이 필요하기 때문에 DTO -> Entity는 서비스 로직에서 별도 함수로 둘것!
//    @Autowired
//    private UserRepository userRepository;
//
//    public PostCommentEntity toEntity(){
//        Optional<UserEntity> commenter = userRepository.findById(commenterId);
//
//        return PostCommentEntity.builder()
//                .postCommentId(this.postCommentId)
//                .commenter(commenter.get())
//                .comment(this.comment)
//                .regDate(this.regDate)
//                .updateDate(this.updateDate)
//                .build();
//    }
}
