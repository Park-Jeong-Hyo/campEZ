package com.campEZ.Project0.web.form.members;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JoinForm {
  private String mname;
  @NotBlank
  private String mid;
  @NotBlank
  private String pw;
  @NotBlank
  private String pwchk;
  @Email
  private String email;
  @NotBlank
  private String nickname;
  @NotBlank
  private String phone;
  private String maddress;
  private String mtype;
  private String companyname;
  private String businessnumber;

}
