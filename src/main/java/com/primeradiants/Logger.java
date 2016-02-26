package com.primeradiants;

public class Logger {
	
	public void info(String msg) {
		System.out.println("[INFO] " + msg);
	}
	
	public void warning(String msg) {
		System.out.println("[WARNING] " + msg);
	}
	
	public void error(String msg) {
		System.out.println("[ERROR] " + msg);
	}
	
	public void debug(String msg) {
		System.out.println("[DEBUG] " + msg);
	}

}
