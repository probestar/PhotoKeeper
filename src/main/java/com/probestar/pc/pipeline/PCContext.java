/**
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title PCContext.java
 * @Package com.probestar.pc.pipeline
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年6月14日 下午4:53:49
 * @version V1.0
 * @Description
 */

package com.probestar.pc.pipeline;

import com.probestar.psutils.PSDate;

public class PCContext {
    private String _file;
    private PCPipeline _pipeline;
    private long _createTime;

    public PCContext(String file) {
        _file = file;
        _pipeline = new PCPipeline(this);
        _createTime = -1;
    }

    public void setFile(String file) {
        _file = file;
    }

    public String getFile() {
        return _file;
    }

    public PCPipeline getPipeline() {
        return _pipeline;
    }

    public void setCreateTime(long time) {
        _createTime = time;
    }

    public long getCreateTime() {
        return _createTime;
    }

    @Override
    public String toString() {
        return "PCContext{" +
                "_file='" + _file + '\'' +
                ", _createTime=" + PSDate.date2String(_createTime, "yyyyMMdd_HHmmss") +
                '}';
    }
}
