package com.yc.vo.apply;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

@Data
public class BookGainApplyVo {

    @ApiModelProperty(name = "科技成果申请主键Id", notes = "")
    @TableId(value = "gaid", type = IdType.AUTO)
    private Integer gaid;

    /**
     * 申请人id
     */
    @ApiModelProperty(name = "申请人id", notes = "")
    private String sid;

    /**
     * 总类型
     */
    @ApiModelProperty(name = "总类型", notes = "")
    private String trtid;

    /**
     * 详细类别 leid
     */
    @ApiModelProperty(name = "详细类别", notes = "")
    private String childid;

    /**
     * 计分依据
     */
    @ApiModelProperty(name = "计分依据", notes = "")
    private String according;

    /**
     * 备注
     */
    @ApiModelProperty(name = "备注", notes = "")
    private String remarks;
}
