package com.roydon.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.model.common.dto.ResponseResult;
import com.roydon.model.wemedia.dto.WmLoginDto;
import com.roydon.model.wemedia.pojo.WmUser;

public interface WmUserService extends IService<WmUser> {

    /**
     * 自媒体端登录
     *
     * @param dto
     * @return
     */
    public ResponseResult login(WmLoginDto dto);

}
