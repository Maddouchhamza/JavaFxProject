package com.projetpfa;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class infoBox {
    public static void infobox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

}
