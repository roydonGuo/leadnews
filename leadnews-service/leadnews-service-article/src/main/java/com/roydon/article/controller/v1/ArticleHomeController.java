package com.roydon.article.controller.v1;

import com.roydon.article.service.ApArticleService;
import com.roydon.common.canstants.ArticleConstants;
import com.roydon.model.article.dto.ArticleHomeDto;
import com.roydon.model.common.dto.ResponseResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/article")
public class ArticleHomeController {

    //    @Autowired
    private ApArticleService apArticleService;

    /**
     * 加载首页
     *
     * @param dto
     * @return
     */
    @PostMapping("/load")
    public ResponseResult load(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    /**
     * 加载更多
     *
     * @param dto
     * @return
     */
    @PostMapping("/loadmore")
    public ResponseResult loadmore(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    /**
     * 加载最新
     *
     * @param dto
     * @return
     */
    @PostMapping("/loadnew")
    public ResponseResult loadnew(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_NEW);
    }
}
