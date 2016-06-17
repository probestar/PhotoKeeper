/**
 *
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title PCFilePool.java
 * @Package com.probestar.pc.common
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年6月14日 下午5:04:37
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.pc.common;

import java.io.File;
import java.util.ArrayList;

import com.probestar.psutils.PSTracer;

public class PCFilePool {
	private static PSTracer _tracer = PSTracer.getInstance(PCFilePool.class);

	private ArrayList<String> _files;

	public PCFilePool() {
		_files = new ArrayList<String>();
	}

	public void fill(String path) {
		_tracer.error("Start to load files. " + path);
		doFill(path);
		_tracer.error("Finish to load files. " + path);
	}

	public ArrayList<String> getFiles() {
		return _files;
	}

	private void doFill(String path) {
		String[] files = new File(path).list();
		for (String file : files) {
			File f = new File(path + "/" + file);
			String fullPath = f.getAbsolutePath();
			if (f.isDirectory())
				doFill(fullPath);
			else
				_files.add(fullPath);
		}
	}
}
