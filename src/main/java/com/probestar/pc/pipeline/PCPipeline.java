/**
 *
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title PCPipeline.java
 * @Package com.probestar.pc.pipeline
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年6月14日 下午4:51:55
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.pc.pipeline;

import java.util.LinkedList;

import com.probestar.psutils.PSTracer;

public class PCPipeline {
	private static PSTracer _tracer = PSTracer.getInstance(PCPipeline.class);

	private LinkedList<PCHandler> _handlers;
	private PCContext _context;

	public PCPipeline(PCContext context) {
		_context = context;
		_handlers = new LinkedList<>();
	}

	public void addLast(PCHandler handler) {
		_handlers.add(handler);
	}

	public void fire() {
		PCHandler handler = _handlers.poll();
		while (handler != null) {
			try {
				handler.handle(_context);
			} catch (Throwable t) {
				_tracer.error("PCPipeline.fire error.", t);
				handler.caughtException(t);
			}
			handler = _handlers.poll();
		}
	}
}
