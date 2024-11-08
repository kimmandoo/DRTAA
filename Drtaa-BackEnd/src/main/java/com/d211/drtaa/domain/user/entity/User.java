package com.d211.drtaa.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    @Schema(description = "회원 고유 번호", example = "1")
    private long userId;

    @Column(name = "user_provider_id", nullable = false)
    @Schema(description = "회원 아이디", example = "1")
    private String userProviderId;

    @Column(name = "user_password", nullable = false)
    @Schema(description = "회원 비밀번호", example = "암호화된 내용")
    private String userPassword;

    @Column(name = "user_nickname", nullable = false)
    @Schema(description = "회원 닉네임", example = "TEST")
    private String userNickname;

    @Column(name = "user_profile_img")
    @Schema(description = "회원 프로필 사진", example = "multipart 이미지")
    private String userProfileImg;

    @Column(name = "user_login", nullable = false)
    @Schema(description = "회원 로그인처", example = "Form")
    private String userLogin;

    @Column(name = "user_is_admin", nullable = false)
    @Schema(description = "회원 관리자 유무", example = "false")
    private boolean userIsAdmin;

    @Column(name = "user_refreshtoken", nullable = false)
    @Schema(description = "회원 refreshToken", example = "ex7534487435468~~")
    private String userRefreshToken;

    @Column(name = "user_fcm_token")
    @Schema(description = "FCM의 회원 식별 토큰")
    private String fcmToken;

    @Column(name = "user_signup_date")
    @Schema(description = "회원 가입 일자", example = "2024.09.12 14:00:00")
    private LocalDateTime userSiginupDate;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userIsAdmin ? "ROLE_ADMIN" : "ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userProviderId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
