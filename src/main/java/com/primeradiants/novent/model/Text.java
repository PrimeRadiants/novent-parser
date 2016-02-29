package com.primeradiants.novent.model;

import org.w3c.dom.Element;

import com.primeradiants.novent.exceptions.NoventParsingException;
import com.primeradiants.novent.model.ParsingUtil.AlignEnum;

public class Text {
	private String name;
	private int x;
	private int y;
	private int width;
	private ParsingUtil.AlignEnum align;
	private int lineHeight;
	private String font;
	private String size;
	private double opacity;
	private String content;
	private String color;
	private int index;
	
	public Text(String name, int x, int y, int width, AlignEnum align, int lineHeight, String font, String size,
			double opacity, String content, String color, int index) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.align = align;
		this.lineHeight = lineHeight;
		this.font = font;
		this.size = size;
		this.opacity = opacity;
		this.content = content;
		this.color = color;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public ParsingUtil.AlignEnum getAlign() {
		return align;
	}

	public void setAlign(ParsingUtil.AlignEnum align) {
		this.align = align;
	}

	public int getLineHeight() {
		return lineHeight;
	}

	public void setLineHeight(int lineHeight) {
		this.lineHeight = lineHeight;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public static Text fromNode(Element node, int index) throws NoventParsingException {
		String name = ParsingUtil.validateNonEmptyStringAttr(node, "name");
		int x = ParsingUtil.validateIntegerAttr(node, "x");
		int y = ParsingUtil.validateIntegerAttr(node, "y");
		int width = ParsingUtil.validatePositiveIntegerAttr(node, "width");
		ParsingUtil.AlignEnum align = ParsingUtil.validateAlignAttr(node, "align");
		int lineHeight = ParsingUtil.validatePositiveIntegerAttr(node, "lineHeight");
		String font = ParsingUtil.validateNonEmptyStringAttr(node, "font");
		String size = ParsingUtil.validateNonEmptyStringAttr(node, "size");
		String color = ParsingUtil.validateNonEmptyStringAttr(node, "color");
		double opacity = ParsingUtil.validateBetweenZeroAndOneAttr(node, "opacity");
		
		String content = node.getTextContent();
		if(content.equals("")) {
			throw new NoventParsingException("Missing content for tag " + node.getTagName() + " at line " + node.getUserData("lineNumber"));
		}
		
		return new Text(name, x, y, width, align, lineHeight, font, size,
				opacity, content, color, index);
	}
}
