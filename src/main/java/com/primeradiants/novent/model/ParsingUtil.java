package com.primeradiants.novent.model;

import org.w3c.dom.Element;

import com.primeradiants.Main;
import com.primeradiants.novent.exceptions.NoventParsingException;

public class ParsingUtil {
	
	public static String validateNonEmptyStringAttr(Element node, String attrName) throws NoventParsingException {
		String attr = node.getAttribute(attrName);
		if(attr == "")
			throw new NoventParsingException("Missing or empty attribute " + attrName + " at tag " + node.getTagName() + " at line " + node.getUserData("lineNumber"));
		
		return attr;
	}
	
	public static String validateSrc(Element node, String attrName) throws NoventParsingException {
		String src = validateNonEmptyStringAttr(node, attrName);
		
		if(!Main.isExistingFile(src))
			throw new NoventParsingException("Invalid attribute " + attrName + " at tag " + node.getTagName() + " at line " + node.getUserData("lineNumber") + ", file do not exists");
		
		return src;
	}
	
	public static int validateIntegerAttr(Element node, String attrName) throws NoventParsingException {
		String stringAttr = validateNonEmptyStringAttr(node, attrName);
		
		int result;
		try {
			result = Integer.parseInt(stringAttr);
		}
		catch(NumberFormatException e) {
			throw new NoventParsingException("Invalid number format for " + attrName + " at tag " + node.getTagName() + " at line " + node.getUserData("lineNumber") + ", must be an integer");
		}
		
		return result;
	}
	
	public static int validatePositiveIntegerAttr(Element node, String attrName) throws NoventParsingException {
		int result = validateIntegerAttr(node, attrName);
		
		if(result < 0)
			throw new NoventParsingException("Invalid number format for " + attrName + " at tag " + node.getTagName() + " at line " + node.getUserData("lineNumber") + ", must be an positive integer");
		
		return result;
	}

	public static double validateBetweenZeroAndOneAttr(Element node, String attrName) throws NoventParsingException {
		String stringAttr = validateNonEmptyStringAttr(node, attrName);
		
		double result;
		try {
			result = Double.parseDouble(stringAttr);
		}
		catch(NumberFormatException e) {
			throw new NoventParsingException("Invalid number format for " + attrName + " at tag " + node.getTagName() + " at line " + node.getUserData("lineNumber") + ", must be an double");
		}
		
		if(result < 0 || result > 1)
			throw new NoventParsingException("Invalid number value for " + attrName + " at tag " + node.getTagName() + " at line " + node.getUserData("lineNumber") + ", must be between 0 and 1");
		
		return result;
	}
}
