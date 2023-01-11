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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignInController implements Initializable {

    Connection cnx;
    public PreparedStatement st;
    public ResultSet result;
    public static String CurrentUser;
    private int c;

    @FXML
    private JFXButton btn_seconnecter;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_user;

    @FXML
    private VBox vbox;
    private Parent fxml;
    private AnchorPane root;

    @FXML
    void openHome() {
        String nom = txt_user.getText();
        String password = txt_password.getText();
        String sql = "select * from departements";

        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();

            while (result.next()) {
                if (nom.equals(result.getString("user")) && password.equals(result.getString("password"))) {
                    c = c + 1;
                    try {
                        CurrentUser = result.getString("user");
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
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
            if (c == 0) {
                infoBox("Enter Correct Username and Password", "Failed", null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cnx = ConnexionMysql.connexionDB();

    }

}