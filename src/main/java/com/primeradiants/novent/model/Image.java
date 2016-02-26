package com.primeradiants.novent.model;

import org.w3c.dom.Element;

import com.primeradiants.novent.exceptions.NoventParsingException;

public class Image {
	private String name;
	private String src;
	private int x;
	private int y;
	private int width;
	private int height;
	private double opacity;
	private int index;
	
	public Image(String name, String src, int x, int y, int width, int height, double opacity, int index) {
		super();
		this.name = name;
		this.src = src;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.opacity = opacity;
		this.index = index;
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

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public static Image fromNode(Element node, int index) throws NoventParsingException {
		String name = ParsingUtil.validateNonEmptyStringAttr(node, "name");
		String src = ParsingUtil.validateSrc(node, "src");
		int x = ParsingUtil.validateIntegerAttr(node, "x");
		int y = ParsingUtil.validateIntegerAttr(node, "y");
		int width = ParsingUtil.validatePositiveIntegerAttr(node, "width");
		int height = ParsingUtil.validatePositiveIntegerAttr(node, "height");
		double opacity = ParsingUtil.validateBetweenZeroAndOneAttr(node, "opacity");
		return new Image(name, src, x, y, width, height, opacity, index);
	}
	
}
