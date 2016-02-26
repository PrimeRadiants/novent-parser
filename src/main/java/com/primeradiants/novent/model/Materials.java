package com.primeradiants.novent.model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.primeradiants.novent.exceptions.NoventParsingException;

public class Materials {

	private List<Image> images;
	private List<Font> fonts;
	private List<Animation> animations;
	private List<Sound> sounds;
	private List<Text> texts;
	private List<Video> videos;
	
	public Materials(List<Image> images, List<Font> fonts, List<Animation> animation, List<Sound> sounds,
			List<Text> text, List<Video> video) {
		super();
		this.images = images;
		this.fonts = fonts;
		this.animations = animation;
		this.sounds = sounds;
		this.texts = text;
		this.videos = video;
	}

	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	public List<Font> getFonts() {
		return fonts;
	}
	public void setFonts(List<Font> fonts) {
		this.fonts = fonts;
	}
	public List<Animation> getAnimation() {
		return animations;
	}
	public void setAnimation(List<Animation> animation) {
		this.animations = animation;
	}
	public List<Sound> getSounds() {
		return sounds;
	}
	public void setSounds(List<Sound> sounds) {
		this.sounds = sounds;
	}
	public List<Text> getText() {
		return texts;
	}
	public void setText(List<Text> text) {
		this.texts = text;
	}
	public List<Video> getVideo() {
		return videos;
	}
	public void setVideo(List<Video> video) {
		this.videos = video;
	}
	
	
	public static Materials fromNode(Element node) throws NoventParsingException {
		NodeList animationsList = node.getElementsByTagName("animation");
		NodeList fontsList = node.getElementsByTagName("font");
		NodeList imagesList = node.getElementsByTagName("image");
		NodeList soundsList = node.getElementsByTagName("sound");
		NodeList textsList = node.getElementsByTagName("text");
		NodeList videosList = node.getElementsByTagName("video");
		
		List<Animation> animations = new ArrayList<Animation>();
		List<Image> images = new ArrayList<Image>();
		List<Font> fonts = new ArrayList<Font>();
		List<Sound> sounds = new ArrayList<Sound>();
		List<Text> texts = new ArrayList<Text>();
		List<Video> videos = new ArrayList<Video>();
		
		for(int i = 0; i < animationsList.getLength(); i++) {
			animations.add(Animation.fromNode((Element) animationsList.item(i)));
		}
		
		for(int i = 0; i < fontsList.getLength(); i++) {
			fonts.add(Font.fromNode((Element) fontsList.item(i)));
		}
		
		for(int i = 0; i < imagesList.getLength(); i++) {
			images.add(Image.fromNode((Element) imagesList.item(i)));
		}
		
		for(int i = 0; i < soundsList.getLength(); i++) {
			sounds.add(Sound.fromNode((Element) soundsList.item(i)));
		}
		
		for(int i = 0; i < textsList.getLength(); i++) {
			texts.add(Text.fromNode((Element) textsList.item(i)));
		}
		
		for(int i = 0; i < videosList.getLength(); i++) {
			videos.add(Video.fromNode((Element) videosList.item(i)));
		}
		
		return new Materials(images, fonts, animations, sounds,
				texts, videos);
	}
}
