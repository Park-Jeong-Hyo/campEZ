  package com.campEZ.Project0.web.form.members;

  import jakarta.validation.constraints.Email;
  import jakarta.validation.constraints.NotBlank;
  import jakarta.validation.constraints.NotEmpty;
  import jakarta.validation.constraints.Pattern;
  import lombok.Data;

  @Data
  public class JoinForm {
    private String mname;
    @NotEmpty(message = "���̵� �Է����ּ���")
    @Pattern(regexp = "[a-z0-9]{6,20}", message = "���̵�� ���� �ҹ��ڿ� ���ڷθ� �̷������ �ϸ�, 6�ڿ��� 20�ڱ��� �Է� �����մϴ�.")
    private String mid;
    @NotBlank(message = "�� ĭ�� ����� �� �����ϴ�")
    private String pw;
    @NotBlank(message = "�� ĭ�� ����� �� �����ϴ�")
    private String pwchk;
    @Email(message = "�̸��� ������ �ƴմϴ�")
    private String email;
    @NotBlank(message = "�� ĭ�� ����� �� �����ϴ�")
    private String nickname;
    @NotBlank(message = "�� ĭ�� ����� �� �����ϴ�")
    private String phone;
    private String maddress;
    private String mtype;
    private String companyname;
    private String businessnumber;

  }
