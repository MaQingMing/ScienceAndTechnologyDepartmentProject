package com.yc.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/11/23 23:10
 */
@Data
public class AddRecord {
    private String name;
    private String values;
    private String teamPeople;
    private String textarea;
    private String date;
    private String username;
    private Integer trid;
    private Integer leid;
    private String filePath;
    private String filename;
    private Integer id;

}
