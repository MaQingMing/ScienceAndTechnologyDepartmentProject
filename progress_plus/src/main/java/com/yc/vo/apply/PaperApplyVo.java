package com.yc.vo.apply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 学术专著申请vo
 */
@Data
public class PaperApplyVo {

    /**
     * 著作名称
     */
    @ApiModelProperty(name = "论文名称", notes = "")
    private String xmname;
    @ApiModelProperty(name = "发表期刊名", notes = "")
    private String periodical;
    @ApiModelProperty(name = "期号", notes = "")
    private String xmwno;
    @ApiModelProperty(name = "研究院", notes = "")
    private String xmyjname;
    @ApiModelProperty(name = "作者排序", notes = "")
    private String xmpeopleorder;
    @ApiModelProperty(name = "项目备注", notes = "")
    private String xmremark;
    @ApiModelProperty(name = "学校署名", notes = "")
    private String xmschoolorder;
    @ApiModelProperty(name = "用于显示的项目附则", notes = "")
    private String showxmother;
    @ApiModelProperty(name = "选择的类别", notes = "")
    private String xmlb;
    @ApiModelProperty(name = "文件", notes = "")
    private List<MultipartFile> file;
    @ApiModelProperty(name = "请求", notes = "")
    private HttpServletRequest request;
    @ApiModelProperty(name = "申请人id", notes = "")
    private String userid;
}
