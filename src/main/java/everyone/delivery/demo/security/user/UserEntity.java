package everyone.delivery.demo.security.user;

import everyone.delivery.demo.address.Address;
import everyone.delivery.demo.address.InterestedAddress;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@SequenceGenerator(name = "userTable_SEQ_GENERATOR", sequenceName = "userTable_SEQ", initialValue = 1, allocationSize = 1)
@Entity
@Table(name = "userTable")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE , generator="userTable_SEQ_GENERATOR")
    private Long userId;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 300, nullable = false)
    private String password;

    // TODO: 이넘 JPA를 통해 DB로 어떻게 들어가는지 확인
    // TODO: 리스트 데이터가 JPA를 통해 DB로 어떻게 들어가는지 확인
    // 참고: https://gunju-ko.github.io/jpa/2019/06/15/JPA-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%EC%9E%85%EB%AC%B8-chapter09.-%EA%B0%92-%EC%BB%AC%EB%A0%89%EC%85%98-%EB%A7%A4%ED%95%91.html
    @ElementCollection
    private List<UserRole> roles;

    @Embedded
    private Address address;

    @Embedded
    private InterestedAddress interestedAddress;

    @CreatedDate
    @Column(updatable = false)
	private LocalDateTime regDate;		//등록일자

    @LastModifiedDate
    private LocalDateTime updateDate;	//수정일자
}