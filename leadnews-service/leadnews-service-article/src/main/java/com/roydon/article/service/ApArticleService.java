package com.roydon.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.model.article.dto.ArticleDto;
import com.roydon.model.article.dto.ArticleHomeDto;
import com.roydon.model.article.pojo.ApArticle;
import com.roydon.model.common.dto.ResponseResult;

public interface ApArticleService extends IService<ApArticle> {

    /**
     * 加载文章列表
     *
     * @param dto
     * @param type 1 加载更多   2 加载最新
     * @return
     */
    public ResponseResult load(ArticleHomeDto dto, Short type);

    /**
     * 保存app端相关文章
     *
     * @param dto
     * @return
     */
    public ResponseResult saveArticle(ArticleDto dto);

}
