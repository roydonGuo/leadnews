package com.roydon.article.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.roydon.article.mapper.ApArticleContentMapper;
import com.roydon.article.service.ApArticleService;
import com.roydon.article.service.ArticleFreemarkerService;
import com.roydon.file.service.FileStorageService;
import com.roydon.model.article.pojo.ApArticle;
import com.roydon.utils.common.StringUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Transactional
@Service
public class ArticleFreemarkerServiceImpl implements ArticleFreemarkerService {

    @Resource
    private ApArticleContentMapper apArticleContentMapper;

    @Resource
    private Configuration configuration;

    @Resource
    private FileStorageService fileStorageService;

    @Resource
    private ApArticleService apArticleService;

    /**
     * 生成静态文件上传到minIO中
     *
     * @param apArticle
     * @param content
     */
    @Async
    @Override
    public void buildArticleToMinIO(ApArticle apArticle, String content) {
        //已知文章的id
        //4.1 获取文章内容
        if (StringUtils.isNotBlank(content)) {
            //4.2 文章内容通过freemarker生成html文件
            Template template = null;
            StringWriter out = new StringWriter();
            try {
                template = configuration.getTemplate("article.ftl");
                //数据模型
                Map<String, Object> contentDataModel = new HashMap<>();
                contentDataModel.put("content", JSONArray.parseArray(content));
                //合成
                template.process(contentDataModel, out);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //4.3 把html文件上传到minio中
            InputStream in = new ByteArrayInputStream(out.toString().getBytes());
            String path = fileStorageService.uploadHtmlFile("", apArticle.getId() + ".html", in);


            //4.4 修改ap_article表，保存static_url字段
            apArticleService.update(Wrappers.<ApArticle>lambdaUpdate().eq(ApArticle::getId, apArticle.getId())
                    .set(ApArticle::getStaticUrl, path));


        }
    }

}
