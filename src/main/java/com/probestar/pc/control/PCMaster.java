/**
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title PCMaster.java
 * @Package com.probestar.pc.control
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年6月14日 下午5:05:13
 * @version V1.0
 * @Description
 */

package com.probestar.pc.control;

import com.probestar.pc.common.PCConfig;
import com.probestar.pc.common.PCFilePool;
import com.probestar.psutils.PSTracer;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PCMaster {
    private static PSTracer _tracer = PSTracer.getInstance(PCMaster.class);

    private PCConfig _config;
    private PCFilePool _pool;
    private PCStat _stat;

    public PCMaster(PCConfig config) {
        _config = config;
        _pool = new PCFilePool();
        _stat = new PCStat();
    }

    public void run() {
        _pool.fill(_config.getSrcPath());
        ArrayList<String> files = _pool.getFiles();
        CountDownLatch l = new CountDownLatch(files.size());
        ExecutorService executor = Executors.newFixedThreadPool(1);
        for (String file : files) {
            PCWorker worker = new PCWorker(file, _stat, l, _config.getDestPath());
            executor.execute(worker);
        }
        try {
            l.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        _tracer.error(_stat.getFileStatString());
        executor.shutdown();
    }
}