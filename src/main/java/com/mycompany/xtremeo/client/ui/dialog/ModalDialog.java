package com.mycompany.xtremeo.client.ui.dialog;

import com.mycompany.xtremeo.client.ui.AnimationUtils;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ModalDialog {

    private static final String DIALOG_CSS = "/com/mycompany/xtremeo/client/view/styles/dialog.css";

    private final StackPane parentRoot;
    private final StackPane overlay;
    private final VBox card;

    public ModalDialog(StackPane parentRoot) {
        this.parentRoot = parentRoot;
        this.overlay = new StackPane();
        this.card = new VBox(20);

        overlay.getStylesheets().add(getClass().getResource(DIALOG_CSS).toExternalForm());
        overlay.getStyleClass().add("dialog-overlay");
        overlay.setAlignment(Pos.CENTER);
        
        card.getStyleClass().add("dialog-card");
        card.setAlignment(Pos.CENTER);
        card.setMaxHeight(VBox.USE_PREF_SIZE);

        overlay.setOnMouseClicked(e -> {
            if (e.getTarget() == overlay) close(null);
        });

        overlay.getChildren().add(card);
    }

    public void addContent(Node... nodes) {
        card.getChildren().addAll(nodes);
    }

    public void show() {
        parentRoot.getChildren().add(overlay);
        AnimationUtils.popIn(overlay, card, 200);
    }

    public void close(Runnable onClosed) {
        AnimationUtils.popOut(overlay, card, 150, () -> {
            parentRoot.getChildren().remove(overlay);
            if (onClosed != null) onClosed.run();
        });
    }
}

