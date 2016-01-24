package com.goodformentertainment.tool.card.view;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.goodformentertainment.tool.card.util.ImageUtil;

import javafx.scene.image.Image;

public class CardImager {
    private static final Logger LOG = Logger.getLogger(CardImager.class);

    private final Image rawEmptyImage;
    private final Map<Integer, Image> cachedEmpty;
    private final Map<String, Image> rawCardImages;
    private final Map<String, Map<Integer, Image>> cachedCards;
    private final Map<String, Image> rawTypeImages;
    private final Map<String, Map<Integer, Image>> cachedTypes;

    public CardImager(final String emptyUrl) {
        rawEmptyImage = new Image(emptyUrl);
        cachedEmpty = new HashMap<>();
        rawCardImages = new HashMap<>();
        cachedCards = new HashMap<>();
        rawTypeImages = new HashMap<>();
        cachedTypes = new HashMap<>();

        LOG.debug("Created CardImager with empty: " + emptyUrl);
    }

    public Image getEmptyImage(final int size) {
        Image image = cachedEmpty.get(size);
        if (image == null) {
            image = ImageUtil.resizeImage(rawEmptyImage, size, size);
            cachedEmpty.put(size, image);
        }
        return image;
    }

    public void addCard(final String name, final String imageUrl) {
        final Image image = new Image(imageUrl);
        rawCardImages.put(name, image);
    }

    public Image getCardImage(final String name, final int size) {
        final Image image = getCachedImage(rawCardImages, cachedCards, name, size);
        if (image == null) {
            throw new IllegalStateException("The image for card '" + name + "' is not defined");
        }
        return image;
    }

    public void addType(final String name, final String imageUrl) {
        final Image image = new Image(imageUrl);
        rawTypeImages.put(name, image);
    }

    public Image getTypeImage(final String name, final int size) {
        final Image image = getCachedImage(rawTypeImages, cachedTypes, name, size);
        if (image == null) {
            throw new IllegalStateException("The image for type '" + name + "' is not defined");
        }
        return image;
    }

    private static Image getCachedImage(final Map<String, Image> rawImages,
            final Map<String, Map<Integer, Image>> cache, final String name, final int size) {
        Map<Integer, Image> imageSizes = cache.get(name);
        if (imageSizes == null) {
            imageSizes = new HashMap<>();
            cache.put(name, imageSizes);
        }

        Image image = imageSizes.get(size);
        if (image == null) {
            final Image rawImage = rawImages.get(name);
            if (rawImage != null) {
                image = ImageUtil.resizeImage(rawImage, size, size);
                imageSizes.put(size, image);
            }
        }

        LOG.debug("Retrieved cached image for '" + name + "' at " + size + "px");
        return image;
    }
}
