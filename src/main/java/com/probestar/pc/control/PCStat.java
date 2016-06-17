package com.probestar.pc.control;

import com.probestar.pc.pipeline.PCContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by probestar on 16/6/15.
 */
public class PCStat {
    private ConcurrentHashMap<String, PCContext> _files;
    private ConcurrentHashMap<Long, String> _length;

    public PCStat() {
        _files = new ConcurrentHashMap<>();
        _length = new ConcurrentHashMap<>();
    }

    public void addContext(PCContext context) {
        _files.put(context.getFile(), context);
    }

    public String getFileStatString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, PCContext> entry : _files.entrySet()) {
            sb.append(entry.getValue().toString());
            sb.append("\r\n");
        }
        return sb.toString();
    }
}
