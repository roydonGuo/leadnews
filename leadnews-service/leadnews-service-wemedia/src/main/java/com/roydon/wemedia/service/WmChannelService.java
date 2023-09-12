package com.roydon.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.model.common.dto.ResponseResult;
import com.roydon.model.wemedia.pojo.WmChannel;

public interface WmChannelService extends IService<WmChannel> {

    /**
     * 查询所有频道
     *
     * @return
     */
    public ResponseResult findAll();

}
