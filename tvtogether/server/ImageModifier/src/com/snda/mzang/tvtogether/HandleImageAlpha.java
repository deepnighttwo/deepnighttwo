package com.snda.mzang.tvtogether;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HandleImageAlpha {

	public static void main(String[] args) throws IOException {
		BufferedImage image = ImageIO.read(new File("G:/My Dropbox/电视台台标/CCAV字电视台.png"));
		int w = image.getWidth();
		int h = image.getHeight();
		int a = image.getRGB(0, 0);

		System.out.println(a);

		a = image.getRGB(121, 141);

		System.out.println(a);

		BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		Graphics g = newImage.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();

		a = newImage.getRGB(121, 141);

		System.out.println(a);

		a = newImage.getRGB(0, 0);

		System.out.println(a);

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int rgb = newImage.getRGB(i, j);
				if (rgb == 0) {
					newImage.setRGB(i, j, -1);
				} else {
					newImage.setRGB(i, j, rgb);
				}
			}
		}
		File newFile = new File("G:/My Dropbox/电视台台标/山西电视台-1.png");
		if (newFile.isFile() == false) {
			newFile.createNewFile();
		}
		ImageIO.write(newImage, "png", newFile);
	}

}
