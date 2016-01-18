package com.goodformentertainment.tool.card.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public final class ImageUtil {
    private ImageUtil() {
    }

    public static final Image resizeImage(final Image image, final int areaWidth,
            final int areaHeight) {
        BufferedImage img = SwingFXUtils.fromFXImage(image, null);
        img = ImageUtil.resizeImage(img, areaWidth, areaHeight);
        return SwingFXUtils.toFXImage(img, null);
    }

    public static final BufferedImage resizeImage(final BufferedImage image, final int areaWidth,
            final int areaHeight) {
        final float scaleX = (float) areaWidth / image.getWidth();
        final float scaleY = (float) areaHeight / image.getHeight();
        final float scale = Math.min(scaleX, scaleY);
        final int w = Math.round(image.getWidth() * scale);
        final int h = Math.round(image.getHeight() * scale);

        final int type = image.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB
                : BufferedImage.TYPE_INT_ARGB;

        final boolean scaleDown = scale < 1;

        if (scaleDown) {
            // multi-pass bilinear div 2
            int currentW = image.getWidth();
            int currentH = image.getHeight();
            BufferedImage resized = image;
            while (currentW > w || currentH > h) {
                currentW = Math.max(w, currentW / 2);
                currentH = Math.max(h, currentH / 2);

                final BufferedImage temp = new BufferedImage(currentW, currentH, type);
                final Graphics2D g2 = temp.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(resized, 0, 0, currentW, currentH, null);
                g2.dispose();
                resized = temp;
            }
            return resized;
        } else {
            final Object hint = scale > 2 ? RenderingHints.VALUE_INTERPOLATION_BICUBIC
                    : RenderingHints.VALUE_INTERPOLATION_BILINEAR;

            final BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g2 = resized.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(image, 0, 0, w, h, null);
            g2.dispose();
            return resized;
        }
    }
}
