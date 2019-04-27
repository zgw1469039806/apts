package com.yeyi.um.vo;

import com.yeyi.framework.core.PageResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @description:
 * @author: 张国伟
 * @date: 2018-12-04 15:58
 */
@ApiModel("应用")
public class AptsAppVo extends PageResult {

    private Integer id;
    @ApiModelProperty("应用名")
    private String name;
    @ApiModelProperty("应用文件名")
    private String fileName;
    @ApiModelProperty("应用路径")
    private String fileUrl;
    @ApiModelProperty("应用状态 0:停止1:运行成功")
    private Integer state;
    @ApiModelProperty("最新的日志文件名")
    private String lastLogFileName;
    @ApiModelProperty("modified_time")
    private Date modifiedTime;
    @ApiModelProperty("create_time")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getLastLogFileName() {
        return lastLogFileName;
    }

    public void setLastLogFileName(String lastLogFileName) {
        this.lastLogFileName = lastLogFileName;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
