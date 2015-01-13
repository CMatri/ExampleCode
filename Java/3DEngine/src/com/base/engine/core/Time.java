package com.base.engine.core;

public class Time {

	private static double delta;
	private static final long SECOND = 1000000000l;

	public static double getTime() {
		return System.nanoTime() / (double) SECOND;
	}
}
