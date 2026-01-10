package com.mycompany.xtremeo.client.ui.dialog;

import com.mycompany.xtremeo.client.app.Navigator;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class ErrorDialog {

    public static void show(String title, String message) {
        show(title, message, null);
    }

    public static void show(String title, String message, Runnable onClose) {
        StackPane root = Navigator.getRoot();
        if (root == null) return;

        ModalDialog dialog = new ModalDialog(root);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);

        StackPane icon = createErrorIcon();

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("error-title");

        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("error-message");
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(280);

        Button okButton = new Button("OK");
        okButton.getStyleClass().add("error-button");
        okButton.setOnAction(e -> dialog.close(onClose));

        content.getChildren().addAll(icon, titleLabel, messageLabel, okButton);
        dialog.addContent(content);
        dialog.show();
    }

    private static StackPane createErrorIcon() {
        StackPane iconContainer = new StackPane();
        iconContainer.setMaxSize(60, 60);
        iconContainer.setMinSize(60, 60);

        Circle bg = new Circle(30);
        bg.setFill(Color.web("#e74c3c"));

        Line line1 = new Line(-10, -10, 10, 10);
        line1.setStroke(Color.WHITE);
        line1.setStrokeWidth(4);

        Line line2 = new Line(-10, 10, 10, -10);
        line2.setStroke(Color.WHITE);
        line2.setStrokeWidth(4);

        iconContainer.getChildren().addAll(bg, line1, line2);
        return iconContainer;
    }
}
