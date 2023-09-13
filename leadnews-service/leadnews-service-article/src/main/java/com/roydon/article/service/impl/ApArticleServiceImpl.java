package com.roydon.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.article.mapper.ApArticleConfigMapper;
import com.roydon.article.mapper.ApArticleContentMapper;
import com.roydon.article.mapper.ApArticleMapper;
import com.roydon.article.service.ApArticleService;
import com.roydon.article.service.ArticleFreemarkerService;
import com.roydon.common.canstants.ArticleConstants;
import com.roydon.model.article.dto.ArticleDto;
import com.roydon.model.article.dto.ArticleHomeDto;
import com.roydon.model.article.pojo.ApArticle;
import com.roydon.model.article.pojo.ApArticleConfig;
import com.roydon.model.article.pojo.ApArticleContent;
import com.roydon.model.common.dto.ResponseResult;
import com.roydon.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Transactional
@Service
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {

    @Resource
    private ApArticleMapper apArticleMapper;

    @Resource
    private ApArticleConfigMapper apArticleConfigMapper;

    @Resource
    private ApArticleContentMapper apArticleContentMapper;

    @Resource
    private ArticleFreemarkerService articleFreemarkerService;

    private final static short MAX_PAGE_SIZE = 50;

    /**
     * 加载文章列表
     *
     * @param dto
     * @param type 1 加载更多   2 加载最新
     * @return
     */
    @Override
    public ResponseResult load(ArticleHomeDto dto, Short type) {
        //1.检验参数
        //分页条数的校验
        Integer size = dto.getSize();
        if (size == null || size == 0) {
            size = 10;
        }
        //分页的值不超过50
        size = Math.min(size, MAX_PAGE_SIZE);


        //校验参数  -->type
        if (!type.equals(ArticleConstants.LOADTYPE_LOAD_MORE) && !type.equals(ArticleConstants.LOADTYPE_LOAD_NEW)) {
            type = ArticleConstants.LOADTYPE_LOAD_MORE;
        }

        //频道参数校验
        if (StringUtils.isBlank(dto.getTag())) {
            dto.setTag(ArticleConstants.DEFAULT_TAG);
        }

        //时间校验
        if (dto.getMaxBehotTime() == null) dto.setMaxBehotTime(new Date());
        if (dto.getMinBehotTime() == null) dto.setMinBehotTime(new Date());

        //2.查询
        List<ApArticle> articleList = apArticleMapper.loadArticleList(dto, type);
        //3.结果返回
        return ResponseResult.okResult(articleList);
    }

    /**
     * 保存app端相关文章
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveArticle(ArticleDto dto) {

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //1.检查参数
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApArticle apArticle = new ApArticle();
        BeanUtils.copyProperties(dto, apArticle);

        //2.判断是否存在id
        if (dto.getId() == null) {
            //2.1 不存在id  保存  文章  文章配置  文章内容

            //保存文章
            save(apArticle);

            //保存配置
            //保存配置
            ApArticleConfig apArticleConfig = new ApArticleConfig();
            apArticleConfig.setArticleId(apArticle.getId());
            apArticleConfig.setIsDelete(false);
            apArticleConfig.setIsDown(false);
            apArticleConfig.setIsForward(true);
            apArticleConfig.setIsComment(true);
            apArticleConfigMapper.insert(apArticleConfig);

            //保存 文章内容
            ApArticleContent apArticleContent = new ApArticleContent();
            apArticleContent.setArticleId(apArticle.getId());
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.insert(apArticleContent);

        } else {
            //2.2 存在id   修改  文章  文章内容

            //修改  文章
            updateById(apArticle);

            //修改文章内容
            ApArticleContent apArticleContent = apArticleContentMapper.selectOne(Wrappers.<ApArticleContent>lambdaQuery().eq(ApArticleContent::getArticleId, dto.getId()));
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.updateById(apArticleContent);
        }

        //异步调用 生成静态文件上传到minio中
        articleFreemarkerService.buildArticleToMinIO(apArticle, dto.getContent());


        //3.结果返回  文章的id
        return ResponseResult.okResult(apArticle.getId());
    }
}
