package com.mycompany.xtremeo.client.ui.dialog;

import com.mycompany.xtremeo.client.controller.HistoryDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class HistoryDialog {

    private static final String FXML_PATH = "/com/mycompany/xtremeo/client/view/history-dialog.fxml";

    public static void show(StackPane parent) {
        try {
            FXMLLoader loader = new FXMLLoader(HistoryDialog.class.getResource(FXML_PATH));
            VBox content = loader.load();
            
            ModalDialog dialog = new ModalDialog(parent);
            
            HistoryDialogController controller = loader.getController();
            controller.setDialog(dialog);
            
            dialog.addContent(content);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
