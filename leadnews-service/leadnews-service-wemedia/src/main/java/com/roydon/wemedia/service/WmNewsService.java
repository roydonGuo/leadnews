package com.roydon.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.model.common.dto.ResponseResult;
import com.roydon.model.wemedia.dto.WmNewsDto;
import com.roydon.model.wemedia.dto.WmNewsPageReqDto;
import com.roydon.model.wemedia.pojo.WmNews;

public interface WmNewsService extends IService<WmNews> {

    /**
     * 条件查询文章列表
     *
     * @param dto
     * @return
     */
    public ResponseResult findList(WmNewsPageReqDto dto);

    /**
     * 发布修改文章或保存为草稿
     *
     * @param dto
     * @return
     */
    public ResponseResult submitNews(WmNewsDto dto);


}
