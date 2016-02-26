package com.primeradiants.novent.model;

import org.w3c.dom.Element;

import com.primeradiants.novent.exceptions.NoventParsingException;

public class Sound {
	private String name;
	private String src;
	private double volume;
	public Sound(String name, String src, double volume) {
		super();
		this.name = name;
		this.src = src;
		this.volume = volume;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
	public static Sound fromNode(Element node) throws NoventParsingException {
		String name = ParsingUtil.validateNonEmptyStringAttr(node, "name");
		String src = ParsingUtil.validateSrc(node, "src");
		double volume = ParsingUtil.validateBetweenZeroAndOneAttr(node, "volume");
		
		return new Sound(name, src, volume);
	}
}
