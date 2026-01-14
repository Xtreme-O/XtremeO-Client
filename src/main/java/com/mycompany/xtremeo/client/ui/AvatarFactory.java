package com.mycompany.xtremeo.client.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public final class AvatarFactory {

    private static final int LOAD_SIZE = 128;
    private static final String DEFAULT_AVATAR_PATH = "/com/mycompany/xtremeo/client/images/avatars/NovicePlayer.png";

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
        if (imageView == null) return;

        String imagePath = (path == null || path.isEmpty()) ? DEFAULT_AVATAR_PATH : path;
        
        var resource = AvatarFactory.class.getResourceAsStream(imagePath);
        if (resource != null) {
            Image image = new Image(resource, LOAD_SIZE, LOAD_SIZE, true, true);
            imageView.setImage(image);
        } else {
            // If the requested image doesn't exist and it's not already the default, try the default avatar
            if (!imagePath.equals(DEFAULT_AVATAR_PATH)) {
                var defaultResource = AvatarFactory.class.getResourceAsStream(DEFAULT_AVATAR_PATH);
                if (defaultResource != null) {
                    Image image = new Image(defaultResource, LOAD_SIZE, LOAD_SIZE, true, true);
                    imageView.setImage(image);
                }
            }
        }
    }
}

