package com.roydon.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.model.article.dto.ArticleHomeDto;
import com.roydon.model.article.pojo.ApArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApArticleMapper extends BaseMapper<ApArticle> {

    public List<ApArticle> loadArticleList(@Param("dto") ArticleHomeDto dto, @Param("type") Short type);

}
