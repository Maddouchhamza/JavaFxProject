package com.projetpfa;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import static com.projetpfa.SignInController.CurrentUser;

public class HomeController implements Initializable {

    private Parent fxml;

    @FXML
    private AnchorPane root;

    @FXML
    private Label CurrentUserlab;

    @FXML
    void accueil() {
        try {
            fxml = FXMLLoader.load(getClass().getResource("Accueil.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void etudiants() {
        try {
            fxml = FXMLLoader.load(getClass().getResource("Etudiants.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void matieres() {
        try {
            fxml = FXMLLoader.load(getClass().getResource("Matières.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void professeurs() {
        try {
            fxml = FXMLLoader.load(getClass().getResource("Professeurs.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        CurrentUserlab.setText(CurrentUser);
    }

}
