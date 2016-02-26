package com.primeradiants.novent.model;

import org.w3c.dom.Element;

import com.primeradiants.novent.exceptions.NoventParsingException;

public class Button {
	
	private String src;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Button(String src, int x, int y, int width, int height) {
		super();
		this.src = src;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public static Button fromNode(Element node) throws NoventParsingException {
		
		String src = ParsingUtil.validateSrc(node, "src");
		int x = ParsingUtil.validateIntegerAttr(node, "x");
		int y = ParsingUtil.validateIntegerAttr(node, "y");
		int width = ParsingUtil.validatePositiveIntegerAttr(node, "width");
		int height = ParsingUtil.validatePositiveIntegerAttr(node, "height");
		
		
		return new Button(src, x, y, width, height);
	}
}
