/**
 *
 * Copyright (c) 2016
 * All rights reserved.
 *
 * @Title PCHandler.java
 * @Package com.probestar.pc.pipeline
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date 2016年6月14日 下午4:52:25
 * @version V1.0
 * @Description 
 *
 */

package com.probestar.pc.pipeline;

public abstract class PCHandler {
	public abstract void handle(PCContext context);

	public void caughtException(Throwable t) {

	}
}
