/**
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title PCConfig.java
 * @Package com.probestar.pc.common
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年6月14日 下午4:45:08
 * @version V1.0
 * @Description
 */

package com.probestar.pc.common;

import com.probestar.psutils.PSTracer;
import com.probestar.psutils.PSUtils;

import java.util.HashMap;

public class PCConfig {
    private static PSTracer _tracer = PSTracer.getInstance(PCConfig.class);
    private static PCConfig _instance;

    private String _srcPath;
    private String _destPath;

    static {
        _instance = new PCConfig();
    }

    public static PCConfig getInstance() {
        return _instance;
    }

    private PCConfig() {
        _srcPath = "./";
        _destPath = "./";
        load();
    }

    private void load() {
        HashMap<String, String> map = PSUtils.loadProperties("pc.properties");
        _srcPath = map.get("SrcPath");
        _destPath = map.get("DestPath");
    }

    public String getSrcPath() {
        return _srcPath;
    }

    public String getDestPath() {
        return _destPath;
    }
}
