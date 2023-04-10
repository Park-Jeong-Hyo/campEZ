package com.campEZ.Project0.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMembers {
    private String mid;
    private String email;
    private String nickname;
}
