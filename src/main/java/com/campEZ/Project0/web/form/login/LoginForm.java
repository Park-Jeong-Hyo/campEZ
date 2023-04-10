package com.campEZ.Project0.web.form.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
  @NotBlank
  @Size(min=5, max=50)
  private String mid;

  @NotBlank
  @Size(min=8, max=12)
  private String passwd;
}
