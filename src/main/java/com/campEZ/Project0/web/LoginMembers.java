package com.campEZ.Project0.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMembers {
    private String mid;         //아이디
    private String nickname;    //닉네임
    private String mtype;       //회원 타입
}
