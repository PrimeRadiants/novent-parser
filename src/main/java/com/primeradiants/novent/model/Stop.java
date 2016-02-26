package com.primeradiants.novent.model;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.primeradiants.novent.exceptions.NoventParsingException;

public class Stop extends EventElement {
	private String target;

	public Stop(List<EventElement> childElements, String target) {
		super(childElements);
		this.target = target;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public static Stop fromNode(Element node) throws NoventParsingException {
		String target = ParsingUtil.validateNonEmptyStringAttr(node, "target");
		
		NodeList children = node.getChildNodes();
		if(children.getLength() > 0)
			throw new NoventParsingException("Invalid end tag:at line " + node.getUserData("lineNumber") + ", end tag must be an empty tag.");
		
		return new Stop(null, target);
	}
}
