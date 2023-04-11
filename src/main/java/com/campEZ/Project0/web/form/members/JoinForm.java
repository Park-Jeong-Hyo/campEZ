package com.campEZ.Project0.web.form.members;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JoinForm {
  @NotBlank
  private String mname;
  @NotBlank
  private String mid;
  @NotBlank
  @Size(min=8)
  private String pw;
  @NotBlank
  @Size(min=8)
  private String pwchk;
  @Email
  private String email;
  @Size(max=10)
  private String nickname;

  private String maddress;
  @NotBlank
  private String mtype;
  private String companyname;
  private String businessnumber;

}
