package everyone.delivery.demo.domain.postComment;

import everyone.delivery.demo.domain.postComment.dtos.PostCommentDto;
import everyone.delivery.demo.security.user.UserEntity;
import everyone.delivery.demo.security.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostCommentService {

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private UserRepository userRepository;

    public PostCommentEntity convertDTOToEntity(PostCommentDto postCommentDto){
        UserEntity userEntity = userRepository.findByuserId(postCommentDto.getCommenterId());
        return PostCommentEntity.builder()
                .postCommentId(postCommentDto.getPostCommentId())
                .commenter(userEntity)
                .comment(postCommentDto.getComment())
                .regDate(postCommentDto.getRegDate())
                .updateDate(postCommentDto.getUpdateDate())
                .build();
    }

}
