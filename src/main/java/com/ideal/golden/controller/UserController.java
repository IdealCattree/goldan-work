package com.ideal.golden.controller;

import com.ideal.golden.model.result.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @作者 Ideal
 * @时间 2022-07-29 15:44
 * @类说明 用户类控制器层
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/add")
    public ResultVo addUser() {
        return null;
    }
}
