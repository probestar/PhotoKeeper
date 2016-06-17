package com.probestar.pc.handler;

import com.google.common.io.Files;
import com.probestar.pc.pipeline.PCContext;
import com.probestar.pc.pipeline.PCHandler;
import com.probestar.psutils.PSTracer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by probestar on 16/6/15.
 */
public class NameReaderHandler extends PCHandler {
    private static PSTracer _tracer = PSTracer.getInstance(NameReaderHandler.class);
    private static SimpleDateFormat _photoFormatter = new SimpleDateFormat("MMddyy");
    private static SimpleDateFormat _screenShotFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static SimpleDateFormat _img_20Formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
    private static SimpleDateFormat _20Formatter = new SimpleDateFormat("yyyy-MM-dd HHmmss");
    private static SimpleDateFormat _20_1Formatter = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat _vidFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss");

    @Override
    public void handle(PCContext context) {
        if (context.getCreateTime() != -1)
            return;
        try {
            String fileName = Files.getNameWithoutExtension(context.getFile());
            if (fileName.startsWith("Photo_")) {
                context.setCreateTime(parsePhoto(fileName));
                return;
            }
            if (fileName.startsWith("Screenshot_")) {
                context.setCreateTime(parseScreenshot(fileName));
                return;
            }
            if (fileName.startsWith("IMG_20")) {
                context.setCreateTime(parseIMG_20(fileName));
                return;
            }
            if (fileName.startsWith("20")) {
                context.setCreateTime(parse20(fileName));
                return;
            }
            if (fileName.startsWith("VID_")) {
                context.setCreateTime(parseVID(fileName));
                return;
            }
            _tracer.error("@@@@@" + fileName);
        } catch (Throwable t) {
            _tracer.error("NameReaderHandler.handle error.", t);
        }
    }

    private long parsePhoto(String name) throws ParseException {
        String[] temp = name.split("_");
        if (temp.length != 3)
            return -1;
        return _photoFormatter.parse(temp[1]).getTime();
    }

    private long parseScreenshot(String name) throws ParseException {
        String[] temp = name.split("_");
        if (temp.length != 2)
            return -1;
        return _screenShotFormatter.parse(temp[1]).getTime();
    }

    private long parseIMG_20(String name) throws ParseException {
        String[] temp = name.split("_");
        if (temp.length != 3)
            return -1;
        String s = temp[1] + "_" + temp[2].substring(0, 6);
        return _img_20Formatter.parse(s).getTime();
    }

    private long parse20(String name) throws ParseException {
        if (name.indexOf(" ") > -1)
            return _20Formatter.parse(name).getTime();
        return _20_1Formatter.parse(name.substring(0, 8)).getTime();
    }

    private long parseVID(String name) throws ParseException {
        String[] temp = name.split("_");
        if (temp.length != 3)
            return -1;
        return _vidFormatter.parse(name.substring(4)).getTime();
    }
}
