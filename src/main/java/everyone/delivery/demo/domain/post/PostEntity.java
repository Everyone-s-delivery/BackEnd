package everyone.delivery.demo.domain.post;

import everyone.delivery.demo.security.user.UserEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@SequenceGenerator(name = "postTable_SEQ_GENERATOR", sequenceName = "postTable_SEQ", initialValue = 1, allocationSize = 1)
@Entity
@Table(name = "postTable")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PostEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE , generator="postTable_SEQ_GENERATOR")
    private Long postId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity postUser;

    @Column(length = 300, nullable = false)
    private String title;

    @Column(length = 3000, nullable = false)
    private String description;

    @ElementCollection
    private List<String> addresses;

    @OneToMany
    @JoinColumn(name="post_id")
    private List<PostCommentEntity> comments;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;		//등록일자

    @LastModifiedDate
    private LocalDateTime updateDate;	//수정일자
}
