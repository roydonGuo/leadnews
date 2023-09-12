package com.roydon.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.model.common.dto.ResponseResult;
import com.roydon.model.wemedia.dto.WmMaterialDto;
import com.roydon.model.wemedia.pojo.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {

    /**
     * 图片上传
     *
     * @param multipartFile
     * @return
     */
    public ResponseResult uploadPicture(MultipartFile multipartFile);

    /**
     * 素材列表查询
     *
     * @param dto
     * @return
     */
    public ResponseResult findList(WmMaterialDto dto);


}
