package com.primeradiants.novent.model;

import java.util.List;

import org.w3c.dom.Element;

import com.primeradiants.novent.exceptions.NoventParsingException;

public abstract class EventElement {

	private List<EventElement> childElements;

	public EventElement(List<EventElement> childElements) {
		super();
		this.childElements = childElements;
	}

	public List<EventElement> getChildElements() {
		return childElements;
	}

	public void setChildElements(List<EventElement> childElements) {
		this.childElements = childElements;
	}
	
	public static EventElement fromNode(Element node) throws NoventParsingException {
		EventElement result;
		if(node.getTagName().equals("end")) {
			result = End.fromNode(node);
		}
		else if(node.getTagName().equals("animate")) {
			result = Animate.fromNode(node);
		}
		else if(node.getTagName().equals("play")) {
			result = Play.fromNode(node);
		}
		else if(node.getTagName().equals("wait")) {
			result = Wait.fromNode(node);
		}
		else if(node.getTagName().equals("wiggle")) {
			result = Wiggle.fromNode(node);
		}
		else if(node.getTagName().equals("stop")) {
			result = Stop.fromNode(node);
		}
		else {
			throw new NoventParsingException("Invalid event content: unsupported tag: " + node.getTagName() + " at line " + node.getUserData("lineNumber"));
		}
		
		return result;
	}
}
