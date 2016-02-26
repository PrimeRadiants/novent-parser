package com.primeradiants;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Main 
{
	
	private static Logger logger = new Logger();
	
    public static void main(String[] args) throws ParserConfigurationException, IOException
    {
        logger.info("Starting Sprite Sheet Generator...");
        
        String path = getProgramPath();
        File folder = new File(path);
        List<File> listOfAllFiles = Arrays.asList(folder.listFiles());
        List<File> listPng = new ArrayList<File>();
        
        for(File file : listOfAllFiles) {
        	if(file.isFile() && FilenameUtils.getExtension(file.getAbsolutePath()).toLowerCase().equals("png")) {
        		listPng.add(file);
        	}
        }
        
        if(listPng.size() == 0) {
        	logger.error("No png file founded.");
        	return;
        }
        logger.info(listPng.size() + " png file founded.");
        
        BufferedImage firstImg = ImageIO.read(listPng.get(0));
        int width = firstImg.getWidth();
        int height = firstImg.getHeight();
        
        logger.info("Sprite dimensions: " + width + "x" + height);
        
        Pair<Integer, Integer> matrixSize = getClosestDividers(listPng.size());
        logger.info("Final image will have " + matrixSize.getLeft() + " columns and " + matrixSize.getRight() + " rows.");
        
        int globalWidth = width*matrixSize.getLeft();
        int globalHeight = height*matrixSize.getRight();
        
        logger.info("Final image will be " + globalWidth + "x" + globalHeight);
        
        BufferedImage finalImg = new BufferedImage(globalWidth, globalHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = finalImg.getGraphics();
        
        int i = 0;
        for(File imgFile : listPng) {
        	BufferedImage img = ImageIO.read(imgFile);
        	
        	if(img.getWidth() != width) {
        		logger.error("Wrong width dimension for image " + imgFile.getName() + " (" + img.getWidth() + ")");
        		return;
        	}
        	
        	if(img.getHeight() != height) {
        		logger.error("Wrong height dimension for image " + imgFile.getName() + " (" + img.getWidth() + ")");
        		return;
        	}
        	
        	int xPos = (i % matrixSize.getLeft())*width;
        	Double yPosRaw = Math.floor(i/matrixSize.getLeft())*height;
        	int yPos = yPosRaw.intValue();
        	g.drawImage(img, xPos, yPos, null);
        	
        	i++;
        }
        
        ImageIO.write(finalImg, "PNG", new File(path, "spriteSheet.png"));
        logger.info("Done!");
    } 
    
    public static String getProgramPath() throws UnsupportedEncodingException {
        URL url = Main.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        String parentPath = new File(jarPath).getParentFile().getPath();
        return parentPath;
    }
    
    public static List<Integer> getDividers(int nb) {
    	List<Integer> result = new ArrayList<Integer>();
    	
    	for(int i = 1; i <= nb; i++) {
    		if(nb % i == 0)
    			result.add(i);
    	}
    	
    	return result;
    }
    
    public static Pair<Integer, Integer> getClosestDividers(int nb) {
    	List<Integer> dividers = getDividers(nb);
    	MutablePair<Integer, Integer> result = new MutablePair<Integer, Integer>();
    	int minDiff = Integer.MAX_VALUE;
    	
    	for(int i : dividers) {
    		int diff = Math.abs(i - nb/i);
    		if(diff <= minDiff) {
    			minDiff = diff;
    			result.setLeft(i);
    			result.setRight(nb/i);
    		}
    	}
    	
    	return result;
    }
}
