package com.primeradiants.novent.model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.primeradiants.novent.exceptions.NoventParsingException;

public class Event {
	private List<EventElement> elements;
	
	public Event(List<EventElement> elements) {
		super();
		this.elements = elements;
	}

	public List<EventElement> getElements() {
		return elements;
	}

	public void setElements(List<EventElement> elements) {
		this.elements = elements;
	}

	public static Event fromNode(Element node) throws NoventParsingException {
		NodeList children = node.getChildNodes();
		if(children.getLength() == 0)
			throw new NoventParsingException("Invalid event tag: no child tag at line " + node.getUserData("lineNumber"));
		
		List<EventElement> childElements = new ArrayList<EventElement>();
		for(int i = 0; i < children.getLength(); i++) {
			if(children.item(i) instanceof Element)
				childElements.add(EventElement.fromNode((Element) children.item(i)));
		}
		
		return new Event(childElements);
	}
}
