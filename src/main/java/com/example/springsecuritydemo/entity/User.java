package com.example.springsecuritydemo.entity;

import com.example.springsecuritydemo.constants.ColumnSize;
import com.example.springsecuritydemo.enums.ContentNotice;
import com.example.springsecuritydemo.enums.UserType;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Audited(withModifiedFlag = true)
public class User extends AuditColumns<String> {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(unique = true, length = ColumnSize.USER_NAME)
    private String name;

    @NotNull
    private String password;

    @Column(length = ColumnSize.NICKNAME)
    private String nickName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String types;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Column(length = ColumnSize.CODE)
    @Enumerated(EnumType.STRING)
    private ContentNotice contentNotice;

    @Builder
    public User(@NotNull String name, @NotNull String password, String nickName,
                @NotNull @Email String email, @NotNull List<UserType> types,
                ContentNotice contentNotice) {
        this.name = name;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.types = StringUtils.join(types, ",");
        this.contentNotice = contentNotice;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(StringUtils.split(this.types, ","))
                .map(t -> new SimpleGrantedAuthority("ROLE_" + t))
                .collect(Collectors.toSet());
    }
}

