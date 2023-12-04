package org.example.model.entity;

import java.util.Date;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    private Date createdDate;
    private Date lastModifiedDate;
    
}
