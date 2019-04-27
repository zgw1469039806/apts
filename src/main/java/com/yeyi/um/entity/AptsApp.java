package com.yeyi.um.entity;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 应用表
 * </p>
 *
 * @author 张国伟
 * @since 2018-12-04
 */
@TableName("apts_app")
public class AptsApp extends Model<AptsApp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 应用名
     */
    private String name;
    /**
     * 应用文件名
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 应用路径
     */
    @TableField("file_url")
    private String fileUrl;
    /**
     * 应用状态 0:停止1:运行成功
     */
    private Integer state;
    /**
     * 最新的日志文件名
     */
    @TableField("last_log_file_name")
    private String lastLogFileName;
    @TableField("modified_time")
    private Date modifiedTime;
    @TableField("create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @TableField("is_delete")
    @TableLogic
    private Integer isDelete;


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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AptsApp{" +
        "id=" + id +
        ", name=" + name +
        ", fileName=" + fileName +
        ", fileUrl=" + fileUrl +
        ", state=" + state +
        ", lastLogFileName=" + lastLogFileName +
        ", modifiedTime=" + modifiedTime +
        ", createTime=" + createTime +
        ", isDelete=" + isDelete +
        "}";
    }
}
