package com.primeradiants.novent.model;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Element;

import com.primeradiants.Main;
import com.primeradiants.novent.exceptions.NoventParsingException;

public class ParsingUtil {
	
	public static String validateNonEmptyStringAttr(Element node, String attrName) throws NoventParsingException {
		String attr = node.getAttribute(attrName);
		if(attr.equals(""))
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
	
	public static double validateRealAttr(Element node, String attrName) throws NoventParsingException {
		String stringAttr = validateNonEmptyStringAttr(node, attrName);
		
		double result;
		try {
			result = Double.parseDouble(stringAttr);
		}
		catch(NumberFormatException e) {
			throw new NoventParsingException("Invalid number format for " + attrName + " at tag " + node.getTagName() + " at line " + node.getUserData("lineNumber") + ", must be a real number");
		}
				
		return result;
	}
	
	public static double validatePositiveRealAttr(Element node, String attrName) throws NoventParsingException {		
		double result = validateRealAttr(node, attrName);
		if(result < 0)
			throw new NoventParsingException("Invalid number value for " + attrName + " at tag " + node.getTagName() + " at line " + node.getUserData("lineNumber") + ", must be a positive real number");
	
		return result;
	}

	public static double validateBetweenZeroAndOneAttr(Element node, String attrName) throws NoventParsingException {
		double result = validateRealAttr(node, attrName);
		
		if(result < 0 || result > 1)
			throw new NoventParsingException("Invalid number value for " + attrName + " at tag " + node.getTagName() + " at line " + node.getUserData("lineNumber") + ", must be between 0 and 1");
		
		return result;
	}
	
	public static AlignEnum validateAlignAttr(Element node, String attrName) throws NoventParsingException {
		String stringAttr = validateNonEmptyStringAttr(node, attrName);
		
		AlignEnum result;
		try {
			result = AlignEnum.valueOf(stringAttr);
		}
		catch(IllegalArgumentException e) {
			throw new NoventParsingException("Invalid value for " + attrName + " at tag " + node.getTagName() + " at line " + node.getUserData("lineNumber") + ", must be left, center, right or justify");
		}
		
		return result;
	}
	
	public static <T extends Enum<T>> T validateEnumValue(Element node, String attrName, Class<T> en) throws NoventParsingException {
		String stringAttr = validateNonEmptyStringAttr(node, attrName);
		
		T result;
		try {
			result = (T) Enum.valueOf(en, stringAttr);
		}
		catch(IllegalArgumentException e) {
			throw new NoventParsingException("Invalid value for " + attrName + " at tag " + node.getTagName() + " at line " + node.getUserData("lineNumber"));
		}
		
		return result;
	}
	
	public static Pair<ParsingUtil.TargetEnum, String> validateTarget(Element node, String attrName) throws NoventParsingException {
		String stringAttr = validateNonEmptyStringAttr(node, attrName);
		String[] stringAttrDecomp = stringAttr.split(":");
		
		if(stringAttrDecomp.length != 2)
			throw new NoventParsingException("Invalid value for " + attrName + " at tag " + node.getTagName() + " at line " + node.getUserData("lineNumber") + ", must folow the syntax targetType:targetName");
		
		node.setAttribute("target", stringAttrDecomp[0]);
		TargetEnum targetType = validateEnumValue(node, "target", TargetEnum.class);
		
		node.setAttribute("target", stringAttrDecomp[1]);
		String target = validateNonEmptyStringAttr(node, "target");
		
		MutablePair<TargetEnum, String> result = new MutablePair<TargetEnum, String>();
		result.setLeft(targetType);
		result.setRight(target);
		
		return result;
	}
	
	public static enum AlignEnum {
		left,
		center,
		right,
		justify,
	}
	
	public static enum TargetEnum {
		animation,
		image,
		sound,
		text,
		video
	}
	
	public static enum PropertyEnum {
		x(PropertyValueEnum.integer),
		y(PropertyValueEnum.integer),
		scaleX(PropertyValueEnum.real),
		scaleY(PropertyValueEnum.real),
		opacity(PropertyValueEnum.betweenZeroAndOne),
		rotation(PropertyValueEnum.real),
		volume(PropertyValueEnum.betweenZeroAndOne);
		
		private PropertyValueEnum valueType;
		
		PropertyEnum(PropertyValueEnum valueType) {
			this.valueType = valueType;
		}
		
		public PropertyValueEnum valueType() {
			return this.valueType;
		}
	}
	
	public static enum PropertyValueEnum {
		integer,
		positiveInteger,
		betweenZeroAndOne,
		real,
		positiveReal
	}
	
	public static enum EaseEnum {
		easeInQuad,
		easeOutQuad,
		easeInCubic,
		easeOutCubic,
		easeInOutCubic,
		easeInQuart,
		easeOutQuart,
		easeInOutQuart,
		easeInQuint,
		easeOutQuint,
		easeInOutQuint,
		easeInSine,
		easeOutSine,
		easeInOutSine,
		easeInExpo,
		easeOutExpo,
		easeInOutExpo,
		easeInCirc,
		easeOutCirc,
		easeInOutCirc,
		easeInElastic,
		easeOutElastic,
		easeInOutElastic,
		easeInBack,
		easeOutBack,
		easeInOutBack,
		easeInBounce,
		easeOutBounce,
		easeInOutBounce
	}
	
	public static enum LoopTypeEnum {
		loop,
		stop,
		remove
	}
}
