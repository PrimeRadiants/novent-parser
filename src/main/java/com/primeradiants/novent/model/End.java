package com.primeradiants.novent.model;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.primeradiants.novent.exceptions.NoventParsingException;

public class End extends EventElement {

	public End(List<EventElement> childElements) {
		super(childElements);
	}

	public static End fromNode(Element node) throws NoventParsingException {
		NodeList children = node.getChildNodes();
		if(children.getLength() > 0)
			throw new NoventParsingException("Invalid end tag:at line " + node.getUserData("lineNumber") + ", end tag must be an empty tag.");
		
		return new End(null);
	}
}
