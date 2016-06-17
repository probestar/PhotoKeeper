/**
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title PCEntry.java
 * @Package com.probestar.pc.common
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年6月14日 下午4:49:50
 * @version V1.0
 * @Description
 */

package com.probestar.pc.control;

import com.probestar.pc.common.PCConfig;
import com.probestar.psutils.PSTracer;

public class PCEntry {
    private static PSTracer _tracer = PSTracer.getInstance(PCEntry.class);

    public static void main(String[] args) {
        try {
            System.out.println(System.getProperty("user.dir"));
            PCMaster master = new PCMaster(PCConfig.getInstance());
            master.run();
        } catch (Throwable t) {
            _tracer.error("PCEntry.main error.", t);
        }
    }
}
