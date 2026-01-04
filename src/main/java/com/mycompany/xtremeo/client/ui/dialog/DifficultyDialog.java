package com.mycompany.xtremeo.client.ui.dialog;

import com.mycompany.xtremeo.client.ai.Difficulty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class DifficultyDialog {

    public static void show(StackPane parent, Consumer<Difficulty> onSelect) {
        ModalDialog dialog = new ModalDialog(parent);

        Label title = new Label("SELECT DIFFICULTY");
        title.getStyleClass().add("dialog-title");

        Label subtitle = new Label("Choose your challenge level");
        subtitle.getStyleClass().add("dialog-subtitle");

        VBox buttons = new VBox(12);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10, 0, 10, 0));

        buttons.getChildren().addAll(
            createButton("EASY", "difficulty-easy", dialog, () -> onSelect.accept(Difficulty.EASY)),
            createButton("MEDIUM", "difficulty-medium", dialog, () -> onSelect.accept(Difficulty.MEDIUM)),
            createButton("HARD", "difficulty-hard", dialog, () -> onSelect.accept(Difficulty.HARD))
        );

        Button cancel = new Button("CANCEL");
        cancel.getStyleClass().add("dialog-cancel-button");
        cancel.setOnAction(e -> dialog.close(null));

        dialog.addContent(title, subtitle, buttons, cancel);
        dialog.show();
    }

    private static Button createButton(String text, String style, ModalDialog dialog, Runnable action) {
        Button btn = new Button(text);
        btn.getStyleClass().addAll("difficulty-button", style);
        btn.setOnAction(e -> dialog.close(action));
        return btn;
    }
}

