package com.projetpfa;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextArea;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignInController implements Initializable {

    Connection cnx;
    public PreparedStatement st;
    public ResultSet result;

    @FXML
    private JFXButton btn_seconnecter;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_user;

    @FXML
    private VBox vbox;
    private Parent fxml;

    @FXML
    void openHome() {
        String nom = txt_user.getText();
        String password = txt_password.getText();
        String sql = "select user, password from departements";

        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();
            if (result.next()) {
                if (nom.equals(result.getString("user")) && password.equals(result.getString("password"))) {
                    vbox.getScene().getWindow().hide();
                    Stage home = new Stage();
                    try {
                        fxml = FXMLLoader.load(getClass().getResource("Home.fxml"));
                        Scene scene = new Scene(fxml);
                        home.setScene(scene); // missed one
                        scene.setFill(Color.TRANSPARENT); // 2 ligne pour éliminer les bordures de la fenetre
                        home.initStyle(StageStyle.TRANSPARENT);
                        home.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cnx = ConnexionMysql.connexionDB();

    }

}