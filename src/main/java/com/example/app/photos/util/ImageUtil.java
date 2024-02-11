package com.example.app.photos.util;

import com.example.app.base.util.Constants;
import com.example.app.photos.data.ImageResource;
import com.example.app.photos.data.ResizeType;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class ImageUtil {
  private static final Logger log = LogManager.getLogger(ImageUtil.class);

  public static void saveImage(ImageResource imageResource, InputStream inputStream) throws IOException {
    String fullPath = getFullPathFromImage(imageResource, ResizeType.ORIGINAL);
    OutputStream fileOutputStream = new FileOutputStream(fullPath);
    IOUtils.copy(inputStream, fileOutputStream);
    fileOutputStream.close();
  }

  public static void saveResizedCopiesOfImage(ImageResource imageResource, List<ResizeType> resizeTypeList) throws IOException {
    String fullPath = getFullPathFromImage(imageResource, ResizeType.ORIGINAL);
    BufferedImage image = ImageIO.read(new File(fullPath));
    int height = image.getHeight();
    int width = image.getWidth();
    for(ResizeType resizeType : resizeTypeList) {
      BufferedImage newImg = null;
      if(resizeType.getType().equalsIgnoreCase("w")) {
        newImg = resizeImage(image, resizeType.getSize(), height / (width / resizeType.getSize()));
      } else if (resizeType.getType().equalsIgnoreCase("h")) {
        newImg = resizeImage(image, width / (height / resizeType.getSize()), resizeType.getSize());
      } else {
        log.info("Unsupported resize type: " + resizeType.getType());
      }
      if (newImg != null) {
        String fullPathFromImage = getFullPathFromImage(imageResource, resizeType);
        ImageIO.write(newImg, imageResource.getExtension(), new File(fullPathFromImage));
        log.info("Saved resized image: " + fullPathFromImage + " with size: " + resizeType);
        imageResource.setFileSizesFlags(imageResource.getFileSizesFlags() | resizeType.getFlag());
      }
    }
  }

  private static BufferedImage resizeImage(BufferedImage image, int width, int height) {
    BufferedImage newImg;
    newImg = new BufferedImage(width, height, image.getType());
    Graphics2D g = newImg.createGraphics();
    g.drawImage(image, 0, 0, width, height, null);
    g.dispose();
    return newImg;
  }

  private static String getFullPathFromImage(ImageResource imageResource, ResizeType resizeType) {
    return Constants.BASE_PATH
        + imageResource.getAlbum().getOwner().getId() + "\\"
        + imageResource.getAlbum().getId() + "\\"
        + imageResource.getId()
        + resizeType.getSuffix()
        + "."
        + imageResource.getExtension();
  }
}
