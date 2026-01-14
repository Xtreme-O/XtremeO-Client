package com.mycompany.xtremeo.client.service.video;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class VideoService {

    private static VideoPlayer instance;

    public static VideoPlayer getInstance() {
        if (instance == null) {
            instance = new VideoPlayer();
        }
        return instance;
    }

    public static class VideoPlayer {
        private MediaPlayer mediaPlayer;


        public void playVideo(String videoFileName, String title) {
            playVideo(videoFileName, title, 0);
        }

        private void playVideo(String videoFileName, String title, int attempt) {
            Platform.runLater(() -> {
                cleanup();

                try {
                    String path = Objects.requireNonNull(
                            getClass().getResource("/com/mycompany/xtremeo/client/videos/" + videoFileName)
                    ).toExternalForm();

                    Media media = new Media(path);
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setVolume(0.7);

                    mediaPlayer.setOnReady(() -> displayVideo(title, mediaPlayer));

                    mediaPlayer.setOnError(() -> {
                        System.err.println("Media error: " + mediaPlayer.getError());
                        safeDispose(mediaPlayer);

                        if (attempt < 3) {
                            System.out.println("Retrying video, attempt " + (attempt + 1));
                            PauseTransition pt = new PauseTransition(Duration.millis(500));
                            pt.setOnFinished(e -> playVideo(videoFileName, title, attempt + 1));
                            pt.play();
                        } else {
                            System.out.println("Failed to play video after 3 attempts. Showing fallback.");
                            showFallback(title);
                        }
                    });

                } catch (Exception e) {
                    System.err.println("Failed to load video: " + e.getMessage());
                    showFallback(title);
                }
            });
        }

        private void displayVideo(String title, MediaPlayer player) {
            Platform.runLater(() -> {
                try {
                    MediaView mediaView = new MediaView(player);
                    mediaView.setSmooth(true);
                    mediaView.setPreserveRatio(true);
                    mediaView.setCache(true);
                    mediaView.setCacheHint(CacheHint.SPEED);

                    StackPane root = new StackPane(mediaView);
                    root.setStyle("-fx-background-color: black;");

                    Scene scene = new Scene(root, 800, 600);
                    Stage stage = new Stage();
                    stage.setTitle(title);
                    stage.setScene(scene);

                    mediaView.fitWidthProperty().bind(scene.widthProperty());
                    mediaView.fitHeightProperty().bind(scene.heightProperty());

                    Runnable cleanupRunnable = () -> Platform.runLater(() -> {
                        try {
                            if (player != null) {
                                MediaPlayer.Status status = player.getStatus();
                                if (status != null && status != MediaPlayer.Status.DISPOSED) {
                                    player.stop();
                                    player.dispose();
                                }
                            }
                            if (stage.isShowing()) {
                                stage.close();
                            }
                        } catch (Exception ignored) {}
                    });

                    stage.setOnCloseRequest(e -> cleanupRunnable.run());
                    player.setOnEndOfMedia(cleanupRunnable);
                    player.setOnError(cleanupRunnable);

                    stage.show();
                    player.seek(Duration.ZERO);
                    player.play();

                } catch (Exception e) {
                    safeDispose(player);
                    showFallback(title);
                }
            });
        }

        private void cleanup() {
            if (mediaPlayer != null) {
                safeDispose(mediaPlayer);
                mediaPlayer = null;
            }
        }

        private void safeDispose(MediaPlayer player) {
            try {
                MediaPlayer.Status status = player.getStatus();
                if (status != null && status != MediaPlayer.Status.DISPOSED) {
                    player.stop();
                    player.dispose();
                }
            } catch (Exception e) {
                System.err.println("Error disposing MediaPlayer: " + e.getMessage());
            }
        }

        private void showFallback(String title) {
            Platform.runLater(() -> {
                try {
                    Stage stage = new Stage();
                    stage.setTitle(title + " (Fallback)");

                    Image fallback = new Image(Objects.requireNonNull(
                            getClass().getResource("/com/mycompany/clientside/images/fallback.png")
                    ).toExternalForm());

                    ImageView iv = new ImageView(fallback);
                    iv.setFitWidth(800);
                    iv.setFitHeight(600);
                    iv.setPreserveRatio(true);

                    StackPane root = new StackPane(iv);
                    root.setStyle("-fx-background-color: black;");
                    Scene scene = new Scene(root, 800, 600);
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception ignored) {}
            });
        }
    }
}
