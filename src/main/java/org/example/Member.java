package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

// JPA 사용을 위해서는 Mapping Annotation으로 매핑을 해줘야 한다.

@Setter
@Getter
@Entity // 이 클래스를 Table과 매핑한다고 JPA에게 알려준다. Entity Class라고 한다.
@Table(name = "MEMBER", uniqueConstraints = {@UniqueConstraint(
        name = "NAME_AGE_UNIQUE",
        columnNames = {"NAME", "AGE"}
)}) // 생략시 ClassName(더 정확히는 Entity 이름)을 Table Name으로 매핑한다.
public class Member {

    @Id // Primary key에 매핑한다.
    @Column(name = "ID") // Column 에 매핑한다.
    private String id; // @ID 설정되었으므로 "식별자 필드"가 된다.

    @Column(name = "NAME", nullable = false, length = 10)
    private String username;

    // 이렇게 매핑정보가 없는 경우 필드명을 사용해서 매핑한다.
    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;
}
