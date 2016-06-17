package com.probestar.pc.handler;

import com.google.common.io.Files;
import com.probestar.pc.pipeline.PCContext;
import com.probestar.pc.pipeline.PCHandler;

import java.io.File;

/**
 * Created by probestar on 16/6/16.
 */
public class RenameHandler extends PCHandler {
    @Override
    public void handle(PCContext context) {
        String fileName = Files.getNameWithoutExtension(context.getFile());
        if (!fileName.endsWith("-1"))
            return;
        fileName = fileName.substring(0, fileName.length() - 2);
        File f = new File(context.getFile());
        String extensionName = Files.getFileExtension(context.getFile());
        String newName = f.getParent() + fileName + "." + extensionName;
        f.renameTo(new File(newName));
        context.setFile(newName);
    }
}
