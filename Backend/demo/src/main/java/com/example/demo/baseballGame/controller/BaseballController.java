package com.example.demo.baseballGame.controller;

import com.example.demo.baseballGame.controller.form.*;
import com.example.demo.baseballGame.gameManager.GameManager;
import com.example.demo.baseballGame.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/baseball")
public class BaseballController {
    private List<Integer> computerNumberList = new ArrayList<>();
    private List<Integer> playerNumberList = new ArrayList<>();
    private Integer level;
    private Long userId;
    private int bettingPoint;
    final UserService userService;
    final GameManager gameManager;

    @PostMapping("/get-result")
    public ResponseResultForm getGameResult (@RequestBody RequestPlayerNumberForm requestPlayerNumberForm) {

        playerNumberList = requestPlayerNumberForm.getPlayerNumberList();
        List<String> result = gameManager.getResult(
                computerNumberList, playerNumberList, level, userId, bettingPoint);

        return new ResponseResultForm(playerNumberList, result);
    }

    @PostMapping("/choose-level")
    public void getGameSetting(@RequestBody RequestGameSetForm requestGameSetForm){

        computerNumberList = gameManager.createComputerNumberList(requestGameSetForm.getNumberCount());

        level = requestGameSetForm.getLevel();
        userId = requestGameSetForm.getUser_Id();
        bettingPoint = requestGameSetForm.getBettingPoint();

        userService.setGameStartPoint(userId, bettingPoint);
        gameManager.setAllocation(requestGameSetForm.getLevel(), requestGameSetForm.getNumberCount());
    }

    @PostMapping("/signup-account")
    public Boolean signupAccount(@RequestBody RequestAccountForm requestAccountForm) {
        return userService.register(requestAccountForm);
    }

    @PostMapping("/login-account")
    public ResponseLoginForm loginAccount(@RequestBody RequestLoginForm requestLoginForm) {
        return userService.login(requestLoginForm);
    }

    @PutMapping("/modify-accountInfo")
    public Boolean modifyNickname(@RequestBody RequestModifyAccountInfoForm requestModifyAccountInfoForm){
        return userService.modify(requestModifyAccountInfoForm);
    }

    @DeleteMapping("/delete-account")
    public Boolean deleteAccount(@RequestParam("user_Id") Long user_Id){
        return userService.delete(user_Id);
    }
}
