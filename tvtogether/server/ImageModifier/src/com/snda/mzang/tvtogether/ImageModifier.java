package com.snda.mzang.tvtogether;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageModifier {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String imageBaseDir = "G:/My Dropbox/电视台台标";
		String imageTargetDir = imageBaseDir + "/" + "target";
		String imageFormat = "png";
		int targetWidth = 72;
		int targetHeight = 72;

		File imageFolder = new File(imageBaseDir);
		File imageTargetFolder = new File(imageTargetDir);
		if (imageTargetFolder.isDirectory() == false) {
			imageTargetFolder.mkdirs();
		}

		File[] imageFiles = imageFolder.listFiles();

		for (File file : imageFiles) {
			if (file.isDirectory() == true) {
				continue;
			}

			String fileName = file.getName();
			File targetImageFile = new File(imageTargetFolder, fileName.substring(0, fileName.lastIndexOf('.') > 0 ? fileName.lastIndexOf('.') : fileName.length()) + "."
					+ imageFormat);

			if (targetImageFile.isFile() == false) {
				targetImageFile.createNewFile();
			}

			BufferedImage originalImage = null;
			try {
				originalImage = ImageIO.read(file);
			} catch (IOException e) {
				continue;
			}
			Image changedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
			BufferedImage newImage = new BufferedImage(targetHeight, targetWidth, BufferedImage.TYPE_INT_ARGB);
			Graphics g = newImage.getGraphics();
			g.drawImage(changedImage, 0, 0, null);
			g.dispose();
			for (int i = 0; i < targetWidth; i++) {
				for (int j = 0; j < targetHeight; j++) {
					int rgb = newImage.getRGB(i, j);
					if (rgb == 0) {
						newImage.setRGB(i, j, -1);
					}
				}
			}

			ImageIO.write(newImage, imageFormat, targetImageFile);
		}
	}
}
