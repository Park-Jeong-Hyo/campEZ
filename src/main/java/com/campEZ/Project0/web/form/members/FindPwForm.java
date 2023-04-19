package com.campEZ.Project0.web.form.members;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class FindPwForm {
  private String pmid;
  @Email
  private String pemail;
  private String pphone;
}
