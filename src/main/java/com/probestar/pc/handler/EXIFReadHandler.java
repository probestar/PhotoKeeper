/**
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title JPEGFileReadHandler.java
 * @Package com.probestar.pc.handler
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年6月14日 下午6:49:59
 * @version V1.0
 * @Description
 */

package com.probestar.pc.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.probestar.pc.pipeline.PCContext;
import com.probestar.pc.pipeline.PCHandler;
import com.probestar.psutils.PSDate;
import com.probestar.psutils.PSTracer;

public class EXIFReadHandler extends PCHandler {
    private static PSTracer _tracer = PSTracer.getInstance(EXIFReadHandler.class);
    private static ArrayList<String> _formatters;
    // private static final long Time1990 = 946656000l;

    static {
        _formatters = new ArrayList<String>();
        _formatters.add("yyyy:MM:dd HH:mm:ss");
        _formatters.add("EEE MMM dd HH:mm:ss z yyyy");
        _formatters.add("yyyy-MM-dd HHmmss");
        _formatters.add("yyyyMMdd_HHmmss");
        _formatters.add("yyyyMMddHHmm");
        _formatters.add("yyyyMMdd");
        _formatters.add("MMddyy");
    }

    @Override
    public void handle(PCContext context) {
        try {
            File f = new File(context.getFile());
            HashMap<String, String> map = readExif(f);
            String pictrueTime = map.get("Create Time");
            if (pictrueTime != null) {
                context.setCreateTime(Long.parseLong(pictrueTime));
                return;
            }

            pictrueTime = map.get("Date/Time Original");
            if (pictrueTime == null)
                pictrueTime = map.get("Date/Time");
            if (pictrueTime == null)
                pictrueTime = map.get("Profile Date/Time");
            if (pictrueTime != null) {
                long time = PSDate.string2Date(pictrueTime, _formatters).getTime();
                context.setCreateTime(time);
            } else {
                _tracer.error(f.getAbsolutePath() + "\r\n" + map.toString());
            }

        } catch (Throwable t) {
            _tracer.error("JPEGFileReadHandler.handle error.", t);
        }
    }

    private HashMap<String, String> readExif(File file) throws ImageProcessingException, IOException {
        HashMap<String, String> map = new HashMap<String, String>();
        InputStream is = new FileInputStream(file);

        Metadata metadata = ImageMetadataReader.readMetadata(is);
        Iterable<Directory> iterable = metadata.getDirectories();
        for (Iterator<Directory> iter = iterable.iterator(); iter.hasNext(); ) {
            Directory dr = iter.next();
            Collection<Tag> tags = dr.getTags();
            for (Tag tag : tags)
                map.put(tag.getTagName(), tag.getDescription());
        }
        _tracer.debug("Got Exif. " + file.getAbsolutePath() + "\r\n" + map.toString());
        is.close();
        return map;
    }
}