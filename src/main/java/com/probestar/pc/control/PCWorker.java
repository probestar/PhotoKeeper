/**
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title PCWorker.java
 * @Package com.probestar.pc.control
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年6月14日 下午5:40:56
 * @version V1.0
 * @Description
 */

package com.probestar.pc.control;

import com.google.common.io.Files;
import com.probestar.pc.handler.EXIFReadHandler;
import com.probestar.pc.handler.FileMoveHandler;
import com.probestar.pc.handler.NameReaderHandler;
import com.probestar.pc.handler.RenameHandler;
import com.probestar.pc.pipeline.PCContext;
import com.probestar.pc.pipeline.PCPipeline;
import com.probestar.psutils.PSTracer;

import java.util.concurrent.CountDownLatch;

public class PCWorker implements Runnable {
    private static PSTracer _tracer = PSTracer.getInstance(PCWorker.class);

    private PCContext _context;
    private PCStat _stat;
    private CountDownLatch _l;
    private String _destPath;

    public PCWorker(String file, PCStat stat, CountDownLatch l, String destPath) {
        _context = new PCContext(file);
        _stat = stat;
        _l = l;
        _destPath = destPath;
    }

    public void run() {
        String extName = Files.getFileExtension(_context.getFile()).toLowerCase();
        switch (extName) {
            case "jpeg":
            case "jpg":
            case "png":
            case "mov":
                startWithExif();
                break;
            case "mp4":
                startWithoutExif();
                break;
            case "ds_store":
                break;
            default:
                _tracer.error("Unknown file extension name: " + _context.getFile());
                break;
        }
        _stat.addContext(_context);
        _l.countDown();
    }

    private void startWithExif() {
        PCPipeline pipeline = _context.getPipeline();
        pipeline.addLast(new EXIFReadHandler());
        pipeline.addLast(new NameReaderHandler());
//        pipeline.addLast(new FileMoveHandler(_destPath));
//        pipeline.addLast(new RenameHandler());
        pipeline.fire();
    }

    private void startWithoutExif() {
        PCPipeline pipeline = _context.getPipeline();
        pipeline.addLast(new NameReaderHandler());
//        pipeline.addLast(new FileMoveHandler(_destPath));
//        pipeline.addLast(new RenameHandler());
        pipeline.fire();
    }
}
