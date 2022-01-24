package everyone.delivery.demo.domain.post;


import everyone.delivery.demo.security.user.UserEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@SequenceGenerator(name = "postCommentTable_SEQ_GENERATOR", sequenceName = "postCommentTable_SEQ", initialValue = 1, allocationSize = 1)
@Entity
@Table(name = "postCommentTable")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PostCommentEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE , generator="postCommentTable_SEQ_GENERATOR")
    private Long postCommentId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity commenter;

    @Column(length = 3000, nullable = false)
    private String comment;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;		//등록일자

    @LastModifiedDate
    private LocalDateTime updateDate;	//수정일자
}
