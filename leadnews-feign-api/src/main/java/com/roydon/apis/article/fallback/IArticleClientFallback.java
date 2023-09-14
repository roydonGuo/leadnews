package com.roydon.apis.article.fallback;

import com.roydon.apis.article.IArticleClient;
import com.roydon.model.article.dto.ArticleDto;
import com.roydon.model.common.dto.ResponseResult;
import com.roydon.model.common.enums.AppHttpCodeEnum;
import org.springframework.stereotype.Component;

@Component
public class IArticleClientFallback implements IArticleClient {

    @Override
    public ResponseResult saveArticle(ArticleDto dto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }
}
