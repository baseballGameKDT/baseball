package com.example.demo.baseballGame.controller.form;

import lombok.Getter;

@Getter
public class RequestModifyAccountInfoForm {
    private Long user_Id;
    private String password;
    private String nickname;
}
