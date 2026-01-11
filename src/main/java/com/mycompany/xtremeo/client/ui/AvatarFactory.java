package com.mycompany.xtremeo.client.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public final class AvatarFactory {

    private static final int LOAD_SIZE = 128;

    private AvatarFactory() {}

    public static ImageView create(String url, double size) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        Circle clip = new Circle(size / 2, size / 2, size / 2);
        imageView.setClip(clip);

        loadImage(imageView, url);
        return imageView;
    }

    public static void setup(ImageView imageView, String url, double size) {
        if (imageView == null) return;

        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        Circle clip = new Circle(size / 2, size / 2, size / 2);
        imageView.setClip(clip);

        loadImage(imageView, url);
    }

    public static void loadImage(ImageView imageView, String path) {
        if (imageView == null || path == null || path.isEmpty()) return;

        var resource = AvatarFactory.class.getResourceAsStream(path);
        if (resource != null) {
            Image image = new Image(resource, LOAD_SIZE, LOAD_SIZE, true, true);
            imageView.setImage(image);
        }
    }
}

