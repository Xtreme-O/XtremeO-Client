package com.mycompany.xtremeo.client.ui.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.function.Consumer;

public class RecordGameDialog {

    public static void show(StackPane parent, Consumer<Boolean> onSelect) {
        ModalDialog dialog = new ModalDialog(parent);

        Label title = new Label("RECORD GAME?");
        title.getStyleClass().add("dialog-title");

        Label subtitle = new Label("Would you like to save this game for playback?");
        subtitle.getStyleClass().add("dialog-subtitle");

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 10, 0));

        buttons.getChildren().addAll(
            createButton("YES", "record-yes", dialog, () -> onSelect.accept(true)),
            createButton("NO", "record-no", dialog, () -> onSelect.accept(false))
        );

        Button cancel = new Button("CANCEL");
        cancel.getStyleClass().add("dialog-cancel-button");
        cancel.setOnAction(e -> dialog.close(null));

        dialog.addContent(title, subtitle, buttons, cancel);
        dialog.show();
    }

    private static Button createButton(String text, String style, ModalDialog dialog, Runnable action) {
        Button btn = new Button(text);
        btn.getStyleClass().addAll("record-button", style);
        btn.setOnAction(e -> dialog.close(action));
        return btn;
    }
}

