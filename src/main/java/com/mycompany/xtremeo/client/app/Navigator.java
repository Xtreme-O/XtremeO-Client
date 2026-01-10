package com.mycompany.xtremeo.client.app;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class Navigator {
    private static Stage stage;
    private static StackPane rootContainer;
    private static final  String ROOT_PATH = "/com/mycompany/xtremeo/client/view/";


    public static void init(Stage primaryStage) {
        stage = primaryStage;
    }

    public static StackPane getRoot() {
        return rootContainer;
    }

    public static <T> T setRoot(String screen) {
        try {
            String fxmlPath = ROOT_PATH + screen + ".fxml";
            String cssPath = ROOT_PATH + "styles/" + screen + ".css";
            FXMLLoader loader = new FXMLLoader(
                    Navigator.class.getResource(fxmlPath)
            );
            Parent root = loader.load();
            
            rootContainer = new StackPane(root);
            Scene scene = new Scene(rootContainer);
            
            stage.setWidth(1200);
            stage.setHeight(800);
            stage.setResizable(true);
            scene.getStylesheets().add(
                    Navigator.class.getResource(cssPath).toExternalForm()
            );
            stage.setScene(scene);
            stage.centerOnScreen();
            return loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't show the screen");
        }
    }

    public static <T> void setRootAsync(String screen, Consumer<T> onComplete) {
        Task<Parent> task = new Task<>() {
            private FXMLLoader loader;

            @Override
            protected Parent call() throws Exception {
                String fxmlPath = ROOT_PATH + screen + ".fxml";
                loader = new FXMLLoader(Navigator.class.getResource(fxmlPath));
                return loader.load();
            }

            @Override
            protected void succeeded() {
                Parent root = getValue();
                rootContainer = new StackPane(root);
                Scene scene = new Scene(rootContainer);
                stage.setWidth(1200);
                stage.setHeight(800);
                stage.setResizable(true);
                try {
                    scene.getStylesheets().add(
                            Navigator.class.getResource("/com/mycompany/xtremeo/client/view/styles/" + screen + ".css")
                                    .toExternalForm()
                    );
                } catch (Exception ignored) {}

                stage.setScene(scene);
                stage.centerOnScreen();

                if (onComplete != null) {
                    onComplete.accept(loader.getController());
                }
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }

}
