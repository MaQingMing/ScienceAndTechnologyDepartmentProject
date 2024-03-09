package com.yc.vo.apply;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Data
public class BookApplyVo {

    /**
     * 著作名称
     */
    @ApiModelProperty(name = "著作名称", notes = "")
    private String xmname;

    /**
     * 著作类型
     */
    @ApiModelProperty(name = "著作类型", notes = "")
    private String xmtype;

    /**
     * 作者排序
     */
    @ApiModelProperty(name = "作者排序", notes = "")
    private String xmorder;

    /**
     * 著作字数
     */
    @ApiModelProperty(name = "著作字数", notes = "")
    private String xmwordnumber;

    @ApiModelProperty(name = "出版单位", notes = "")
    private String xmdepartment;

    @ApiModelProperty(name = "申请人", notes = "")
    private String xmpeople;

    @ApiModelProperty(name = "申请备注", notes = "")
    private String xmremark;

    @ApiModelProperty(name = "账号", notes = "")
    private String username;

    @ApiModelProperty(name = "文件", notes = "")
    private List<MultipartFile> file;

    @ApiModelProperty(name = "请求", notes = "")
    private HttpServletRequest request;
}
