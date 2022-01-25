package everyone.delivery.demo.domain.postComment;

import everyone.delivery.demo.common.exception.LogicalRuntimeException;
import everyone.delivery.demo.common.exception.error.CommonError;
import everyone.delivery.demo.domain.post.PostEntity;
import everyone.delivery.demo.domain.post.PostRepository;
import everyone.delivery.demo.domain.postComment.dtos.CreatePostCommentDto;
import everyone.delivery.demo.domain.postComment.dtos.PostCommentDto;
import everyone.delivery.demo.security.user.UserEntity;
import everyone.delivery.demo.security.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class PostCommentService {

    private PostCommentRepository postCommentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;


    /***
     * 조회
     * postCommentId에 해당하는 덧글 반환
     * @param postCommentId
     * @return
     */
    public PostCommentDto getById(Long postCommentId){
        PostCommentEntity postCommentEntity = postCommentRepository.getById(postCommentId);
        if(postCommentEntity == null){
            log.error("postCommentEntity is null. postCommentId: {}", postCommentId);
            throw new LogicalRuntimeException(CommonError.INVALID_DATA);
        }
        return postCommentEntity.toDto();
    }

    /***
     * 등록
     * postCommentDto로 받은 덧글 정보를 디비에 등록
     * @param createPostCommentDto
     * @return
     */
    @Transactional
    public PostCommentDto create(CreatePostCommentDto createPostCommentDto){
        PostCommentEntity postCommentEntity = convertDTOToEntity(createPostCommentDto);
        postCommentEntity = postCommentRepository.save(postCommentEntity);
        return postCommentEntity.toDto();
    }

    /***
     * 수정(덧글은 comment만 수정 가능)
     * postCommentId에 해당하는 comment를 수정
     * @param postCommentId
     * @param comment
     * @return
     */
    @Transactional
    public PostCommentDto update(Long postCommentId, String comment){
        PostCommentEntity postCommentEntity
                = postCommentRepository.getById(postCommentId);
        if(postCommentEntity == null){
            log.error("postCommentEntity is null. postCommentId: {}", postCommentId);
            throw new LogicalRuntimeException(CommonError.INVALID_DATA);
        }
        postCommentEntity.setComment(comment);
        postCommentEntity = postCommentRepository.save(postCommentEntity);

        return postCommentEntity.toDto();
    }

    /***
     * 삭제
     * postCommentId에 해당하는 덧글 삭제
     * @param postCommentId
     * @return
     */
    @Transactional
    public PostCommentDto delete(Long postCommentId){
        PostCommentEntity postCommentEntity
                = postCommentRepository.getById(postCommentId);
        if(postCommentEntity == null){
            log.error("postCommentEntity is null. postCommentId: {}", postCommentId);
            throw new LogicalRuntimeException(CommonError.INVALID_DATA);
        }
        postCommentRepository.deleteById(postCommentId);
        return postCommentEntity.toDto();
    }


    public PostCommentEntity convertDTOToEntity(PostCommentDto postCommentDto){
        UserEntity userEntity = userRepository.findByuserId(postCommentDto.getCommenterId());
        PostEntity postEntity = postRepository.getById(postCommentDto.getPostId());
        return PostCommentEntity.builder()
                .postCommentId(postCommentDto.getPostCommentId())
                .commenter(userEntity)
                .post(postEntity)
                .comment(postCommentDto.getComment())
                .regDate(postCommentDto.getRegDate())
                .updateDate(postCommentDto.getUpdateDate())
                .build();
    }

    public PostCommentEntity convertDTOToEntity(CreatePostCommentDto basicPostCommentDto){
        UserEntity userEntity = userRepository.findByuserId(basicPostCommentDto.getCommenterId());
        PostEntity postEntity = postRepository.getById(basicPostCommentDto.getPostId());

        return PostCommentEntity.builder()
                .commenter(userEntity)
                .comment(basicPostCommentDto.getComment())
                .post(postEntity)
                .build();
    }
}
