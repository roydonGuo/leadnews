package com.roydon.model.wemedia.dto;

import com.roydon.model.common.dto.PageRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class WmNewsPageReqDto extends PageRequestDto {

    /**
     * 状态
     */
    private Short status;
    /**
     * 开始时间
     */
    private Date beginPubDate;
    /**
     * 结束时间
     */
    private Date endPubDate;
    /**
     * 所属频道ID
     */
    private Integer channelId;
    /**
     * 关键字
     */
    private String keyword;
}
