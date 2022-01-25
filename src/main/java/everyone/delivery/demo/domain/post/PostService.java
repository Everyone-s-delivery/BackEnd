package everyone.delivery.demo.domain.post;

import everyone.delivery.demo.common.exception.LogicalRuntimeException;
import everyone.delivery.demo.common.exception.error.CommonError;
import everyone.delivery.demo.domain.post.dtos.CreatePostDto;
import everyone.delivery.demo.domain.post.dtos.PostDto;
import everyone.delivery.demo.domain.post.dtos.UpdatePostDto;
import everyone.delivery.demo.domain.postComment.PostCommentEntity;
import everyone.delivery.demo.domain.postComment.PostCommentService;
import everyone.delivery.demo.domain.postComment.dtos.PostCommentDto;
import everyone.delivery.demo.security.user.UserEntity;
import everyone.delivery.demo.security.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private PostCommentService postCommentService;

    /***
     * 조회
     * 모든 post 조회
     * 추후 검색조건 추가 필요
     * @return
     */
    public List<PostDto> getList(){
        List<PostEntity> postEntities = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();
        for(PostEntity postEntity: postEntities){
            postDtos.add(postEntity.toDto());
        }
        return postDtos;
    }

    /***
     * 조회
     * postId에 해당하는 post 조회
     * @param postId
     * @return
     */
    public PostDto getById(Long postId){
        PostEntity postEntity = postRepository.getById(postId);
        if(postEntity == null){
            log.error("postEntity is null. postId: {}", postId);
            throw new LogicalRuntimeException(CommonError.INVALID_DATA);
        }
        return postEntity.toDto();
    }

    /***
     * 등록
     * basicPostDto 로 받은 덧글 정보를 디비에 등록
     * @param basicPostDto
     * @return
     */
    @Transactional
    public PostDto create(CreatePostDto basicPostDto){
        PostEntity postEntity = convertDTOToEntity(basicPostDto);
        postEntity = postRepository.save(postEntity);
        return postEntity.toDto();
    }

    /***
     * 수정(제목 / 설명 글 / 주소 만 수정 가능)
     * postId에 해당하는 post를 수정
     * @param postId
     * @param updatePostDto
     * @return
     */
    @Transactional
    public PostDto update(Long postId, UpdatePostDto updatePostDto){
        PostEntity postEntity = postRepository.getById(postId);
        if(postEntity == null){
            log.error("postEntity is null. postId: {}", postId);
            throw new LogicalRuntimeException(CommonError.INVALID_DATA);
        }
        postEntity.setTitle(updatePostDto.getTitle());
        postEntity.setDescription(updatePostDto.getDescription());
        postEntity.setAddresses(updatePostDto.getAddresses());
        postEntity = postRepository.save(postEntity);
        return postEntity.toDto();
    }

    /***
     * 삭제
     * postId 해당하는 글 삭제
     * TODO: 딸린 덧글도 다 같이 삭제되는지 확인
     * @param postId
     * @return
     */
    @Transactional
    public PostDto delete(Long postId){
        PostEntity postEntity = postRepository.getById(postId);
        if(postEntity == null){
            log.error("postEntity is null. postId: {}", postId);
            throw new LogicalRuntimeException(CommonError.INVALID_DATA);
        }
        postRepository.deleteById(postId);
        return postEntity.toDto();
    }


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

    public PostEntity convertDTOToEntity(CreatePostDto basicPostDto){
        UserEntity userEntity = userRepository.findByuserId(basicPostDto.getPosterId());
        List<PostCommentDto> commentDtos = basicPostDto.getComments();
        List<PostCommentEntity> commentEntities = new ArrayList<>();
        for (PostCommentDto commentDto: commentDtos){
            commentEntities.add(postCommentService.convertDTOToEntity(commentDto));
        }

        return PostEntity.builder()
                .poster(userEntity)
                .title(basicPostDto.getTitle())
                .description(basicPostDto.getDescription())
                .addresses(basicPostDto.getAddresses())
                .comments(commentEntities)
                .build();
    }
}
