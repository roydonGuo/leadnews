package com.roydon.user.controller.v1;

import com.roydon.model.common.dto.ResponseResult;
import com.roydon.model.user.dto.LoginDto;
import com.roydon.user.service.ApUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/login")
@Api(value = "app端用户登录", tags = "app端用户登录")
public class ApUserLoginController {

    @Resource
    private ApUserService apUserService;

    @ApiOperation("用户登录")
        @PostMapping("/login_auth")
    public ResponseResult login(@RequestBody LoginDto dto) {
        return apUserService.login(dto);
    }

}
