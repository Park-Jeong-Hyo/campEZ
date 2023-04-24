  package com.campEZ.Project0.web.form.members;

  import jakarta.validation.constraints.Email;
  import jakarta.validation.constraints.NotBlank;
  import jakarta.validation.constraints.NotEmpty;
  import jakarta.validation.constraints.Pattern;
  import lombok.Data;

  @Data
  public class JoinForm {
    private String mname;
    @NotEmpty(message = "아이디를 입력해주세요")
    @Pattern(regexp = "[a-z0-9]{6,20}", message = "아이디는 영문 소문자와 숫자로만 이루어져야 하며, 6자에서 20자까지 입력 가능합니다.")
    private String mid;
    @NotBlank(message = "빈 칸은 사용할 수 없습니다")
    private String pw;
    @NotBlank(message = "빈 칸은 사용할 수 없습니다")
    private String pwchk;
    @Email(message = "이메일 형식이 아닙니다")
    private String email;
    @NotBlank(message = "빈 칸은 사용할 수 없습니다")
    private String nickname;
    @NotBlank(message = "빈 칸은 사용할 수 없습니다")
    private String phone;
    private String maddress;
    private String mtype;
    private String companyname;
    private String businessnumber;

  }
