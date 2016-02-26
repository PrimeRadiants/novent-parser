package com.primeradiants.novent.model;

import org.w3c.dom.Element;

import com.primeradiants.novent.exceptions.NoventParsingException;

public class Font {
	private String name;
	private String src;
	
	public Font(String name, String src) {
		super();
		this.name = name;
		this.src = src;
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
	
	public static Font fromNode(Element node) throws NoventParsingException {
		String name = ParsingUtil.validateNonEmptyStringAttr(node, "name");
		String src = ParsingUtil.validateSrc(node, "src");
		
		return new Font(name, src);
	}
}
