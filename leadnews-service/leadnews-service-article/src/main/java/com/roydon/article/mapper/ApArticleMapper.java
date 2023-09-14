package com.roydon.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.model.article.dto.ArticleHomeDto;
import com.roydon.model.article.pojo.ApArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApArticleMapper extends BaseMapper<ApArticle> {

    /**
     * 加载文章列表
     * @param dto
     * @param type  1  加载更多   2记载最新
     * @return
     */
    public List<ApArticle> loadArticleList(ArticleHomeDto dto, Short type);

}
