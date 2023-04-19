package com.campEZ.Project0.web.form.members;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class FindIdForm {
  private String imname;
  @Email
  private String iemail;
  private String iphone;

}
