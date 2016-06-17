package com.probestar.pc.handler;

import com.google.common.io.Files;
import com.probestar.pc.pipeline.PCContext;
import com.probestar.pc.pipeline.PCHandler;
import com.probestar.psutils.PSDate;
import com.probestar.psutils.PSTracer;

import java.io.File;
import java.io.IOException;

/**
 * Created by probestar on 16/6/16.
 */
public class FileMoveHandler extends PCHandler {
    private static PSTracer _tracer = PSTracer.getInstance(FileMoveHandler.class);

    private String _to;

    public FileMoveHandler(String to) {
        _to = to;
    }

    @Override
    public void handle(PCContext context) {
        moveFile(context);
    }

    private void moveFile(PCContext context) {
        File from = new File(context.getFile());
        String toPath = _to
                + PSDate.date2String(context.getCreateTime(), "yyyy") + "/"
                + PSDate.date2String(context.getCreateTime(), "yyyyMM") + "/"
                + PSDate.date2String(context.getCreateTime(), "yyyyMMdd") + "/" + from.getName();
        File to = new File(toPath);
        if (to.exists()) {
            _tracer.info("Del duplicate files.\r\nFrom: %s\r\nTo: %s", from.getAbsolutePath(),
                    to.getAbsoluteFile());
        } else {
            if (!to.getParentFile().exists())
                to.getParentFile().mkdirs();
            try {
                Files.move(from, to);
                _tracer.info("Move files complete.\r\nFrom: %s\r\nTo: %s", from.getAbsolutePath(),
                        to.getAbsoluteFile());
                context.setFile(to.getAbsolutePath());
            } catch (IOException e) {
                _tracer.error("Move files error.\r\nFrom: %s\r\nTo: %s", from.getAbsolutePath(), to.getAbsoluteFile());
            }
        }
    }
}
