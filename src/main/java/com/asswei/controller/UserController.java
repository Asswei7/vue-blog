package com.asswei.controller;


import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.asswei.common.lang.Result;
import com.asswei.entity.User;
import com.asswei.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author asswei
 * @since 2021-08-23
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

//    @RequiresAuthentication
    @GetMapping("/index")
    public Result index(){
        User user = userService.getById(1L);
        return Result.succ(user);
    }

    @RequestMapping("/register")
    public Result register(@RequestBody User user){
        String pwd = user.getPassword();
        user.setPassword(SecureUtil.md5(pwd));
        userService.save(user);
        return Result.succ("user");
    }


    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){
        return Result.succ(user);
    }

}
