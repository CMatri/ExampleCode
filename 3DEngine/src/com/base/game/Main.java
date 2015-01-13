package com.base.game;

import com.base.engine.core.CoreEngine;

public class Main {
	public static void main(String[] args) {
		CoreEngine engine = new CoreEngine(1080, 720, 60, new TestGame());
		engine.createWindow("Testing Engine");
//		engine.getRenderingEngine().setClearColor(0, 0, 1, 1);
		engine.start();
	}
}
