package com.roydon.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.model.common.dto.ResponseResult;
import com.roydon.model.user.dto.LoginDto;
import com.roydon.model.user.pojo.ApUser;

public interface ApUserService extends IService<ApUser> {
    /**
     * app端登录功能
     *
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);
}
