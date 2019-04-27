package com.yeyi.framework.core;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "分页条件")
public class PageResult {
    @ApiModelProperty(value = "起始页码")
    private int current;
    @ApiModelProperty(value = "每页数量")
    private int size;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
