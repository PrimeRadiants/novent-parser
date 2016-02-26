package com.primeradiants.novent.model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.primeradiants.novent.exceptions.NoventParsingException;

public class Novent {
	
	private Button button;
	private List<Page> pages;
	
	public Novent(Button button, List<Page> pages) {
		super();
		this.button = button;
		this.pages = pages;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}
	
	public static Novent fromNode(Element node) throws NoventParsingException {
		NodeList buttons = node.getElementsByTagName("button");
		if(buttons.getLength() == 0)
			throw new NoventParsingException("Invalid novent-descriptor: missing button tag.");
		
		if(buttons.getLength() > 1)
			throw new NoventParsingException("Invalid novent-descriptor: too many button tags.");
		
		Button button = Button.fromNode((Element) buttons.item(0));
		
		NodeList pages = node.getElementsByTagName("page");
		if(pages.getLength() == 0)
			throw new NoventParsingException("Invalid novent-descriptor: no page tag.");
		
		List<Page> pageList = new ArrayList<Page>();
		
		for(int i = 0; i < pages.getLength(); i++) {
			pageList.add(Page.fromNode((Element) pages.item(i)));
		}
		
		return new Novent(button, pageList);
	}
}
