package everyone.delivery.demo.domain.post;

import everyone.delivery.demo.common.exception.LogicalRuntimeException;
import everyone.delivery.demo.common.exception.error.CommonError;
import everyone.delivery.demo.domain.post.dtos.PostDto;
import everyone.delivery.demo.domain.postComment.PostCommentEntity;
import everyone.delivery.demo.domain.postComment.PostCommentService;
import everyone.delivery.demo.domain.postComment.dtos.PostCommentDto;
import everyone.delivery.demo.security.user.UserEntity;
import everyone.delivery.demo.security.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private PostCommentService postCommentService;


    //조회

    /***
     * 조회
     * postId에 해당하는 post 조회
     * @param postId
     * @return
     */
    public PostDto getByPostId(Long postId){
        PostEntity postEntity = postRepository.getById(postId);
        if(postEntity == null){
            log.error("postEntity is null. postId: {}", postId);
            throw new LogicalRuntimeException(CommonError.INVALID_DATA);
        }


        return postEntity.toDTO();
    }


    //등록

    //수정

    //삭제

    public PostEntity convertDTOToEntity(PostDto postDto){
        UserEntity userEntity = userRepository.findByuserId(postDto.getPosterId());
        List<PostCommentDto> commentDtos = postDto.getComments();
        List<PostCommentEntity> commentEntities = new ArrayList<>();
        for (PostCommentDto commentDto: commentDtos){
            commentEntities.add(postCommentService.convertDTOToEntity(commentDto));
        }

        return PostEntity.builder()
                .postId(postDto.getPostId())
                .poster(userEntity)
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .addresses(postDto.getAddresses())
                .comments(commentEntities)
                .regDate(postDto.getRegDate())
                .updateDate(postDto.getUpdateDate())
                .build();
    }
}
