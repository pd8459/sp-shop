package com.mysite.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;

    @NotEmpty(message = "우편번호는 필수항목입니다.")
    private String postcode;  // 우편번호

    @NotEmpty(message = "주소는 필수항목입니다.")
    private String address;    // 주소

    @NotEmpty(message = "상세주소는 필수항목입니다.")
    private String detailAddress;  // 상세주소

    private String extraAddress;  // 참고항목
}
