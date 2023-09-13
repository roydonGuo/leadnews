package com.roydon.article.feign;

import com.roydon.apis.article.IArticleClient;
import com.roydon.article.service.ApArticleService;
import com.roydon.model.article.dto.ArticleDto;
import com.roydon.model.common.dto.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ArticleClient implements IArticleClient {

    @Resource
    private ApArticleService apArticleService;

    @PostMapping("/api/v1/article/save")
    @Override
    public ResponseResult saveArticle(@RequestBody ArticleDto dto) {
        return apArticleService.saveArticle(dto);
    }
}
