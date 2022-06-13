package com.example.springsecuritydemo.entity;

import com.example.springsecuritydemo.constants.ColumnSize;
import lombok.Getter;
import lombok.Setter;
//import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
//@Audited
public abstract class AuditColumns<U> {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(length = ColumnSize.USER_NAME)
    private U createUser;

    @LastModifiedBy
    @Column(length = ColumnSize.USER_NAME)
    private U lastModifiedUser;

    @Column(length = ColumnSize.IS_DELETE)
    private Boolean isDelete;

    public LocalDateTime getModifiedDate() {
        if (createDate == null || lastModifiedDate == null)
            return null;

        if (createDate.isEqual(lastModifiedDate)) {
            return null;
        } else {
            return lastModifiedDate;
        }
    }

}
