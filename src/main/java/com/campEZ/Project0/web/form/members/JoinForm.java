package com.campEZ.Project0.web.form.members;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class JoinForm {
  private String mname;
  @NotBlank
  @Length(min = 5, max = 20)
  @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{5,20}$",
            message = "아이디는 대소문자를 구분하지 않는 영문자와 숫자를 포함한 20자 이내입니다")
  private String mid;
  @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
  @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
      message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 16자의 비밀번호여야 합니다.")
  private String pw;
  @NotBlank(message = "비밀번호확인은 필수 입력 값입니다.")
  @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
      message = "비밀번호확인은 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 16자의 비밀번호여야 합니다.")
  private String pwchk;
  @Email
  private String email;
  @NotBlank
  @Length(min = 3, max = 10)
  @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z]{3,10}$",
            message = "닉네임은 한글 또는 영문자로 10자 이내 입니다")
  @NotBlank
  private String nickname;
  @NotBlank
  private String phone;
  private String maddress;
  private String mtype;
  private String companyname;
  private String businessnumber;

}
