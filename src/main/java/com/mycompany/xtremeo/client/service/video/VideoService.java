package com.mycompany.xtremeo.client.service.video;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.util.Objects;

public class VideoService {
    private static VideoService instance;

    private VideoService() {}

    public static VideoService getInstance() {
        if (instance == null) {
            instance = new VideoService();
        }
        return instance;
    }

    public void playVideo(MediaView mediaView, String videoFileName) {
        try {
            String path = Objects.requireNonNull(getClass().getResource("/com/mycompany/xtremeo/client/videos/" + videoFileName)).toExternalForm();
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            if (mediaView.getScene() != null) {
//                mediaView.setFitWidth(mediaView.getScene().getWidth());
//                mediaView.setFitHeight(mediaView.getScene().getHeight());

                mediaView.fitWidthProperty().bind(mediaView.getScene().widthProperty());
                mediaView.fitHeightProperty().bind(mediaView.getScene().heightProperty());
            }
            mediaView.setPreserveRatio(false);
            //mediaView.setMediaPlayer(mediaPlayer);
            mediaView.setVisible(true);
            mediaView.toFront();

            mediaPlayer.play();

            mediaPlayer.setOnEndOfMedia(() -> {
                mediaView.fitWidthProperty().unbind();
                mediaView.fitHeightProperty().unbind();
                mediaView.setVisible(false);
                mediaPlayer.dispose();
            });

        } catch (Exception e) {
            System.err.println("Error playing video in VideoService: " + e.getMessage());
        }
    }
}