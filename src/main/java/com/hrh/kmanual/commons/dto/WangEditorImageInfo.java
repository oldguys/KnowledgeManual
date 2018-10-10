package com.hrh.kmanual.commons.dto;/**
 * Created by Administrator on 2018/10/9 0009.
 */

import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/9 0009 15:50
 */
public class WangEditorImageInfo {

    private Integer errno;

    private List<String> data;

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
