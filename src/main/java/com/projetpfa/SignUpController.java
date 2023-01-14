package com.projetpfa;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import static com.projetpfa.infoBox.infobox;

public class SignUpController implements Initializable {

    Connection cnx;
    public PreparedStatement st;
    public ResultSet result;

    @FXML
    private JFXButton btn_sinscrire;

    @FXML
    private TextField txt_anneecreation;

    @FXML
    private TextField txt_chefdep;

    @FXML
    private TextField txt_pswd;

    @FXML
    private TextField txt_username;

    @FXML
    private VBox vbox;
    private Parent fxml;
    private int count, c;

    @FXML
    void SignUp() {
        String nomdep = txt_username.getText();
        String password = txt_pswd.getText();
        String chefdep = txt_chefdep.getText();
        String anneecreation = txt_anneecreation.getText();

        if (nomdep == "" || password == "" || chefdep == "" || anneecreation == "") {
            infobox("Veuillez remplir tous les champs!", "Failed", null);
        } else {
            String sql = "INSERT INTO departements (user, password, chef_dep, annee_creation) VALUES(?,?,?,?) ";
            String sql1 = "SELECT * FROM departements";
            String sql2 = "SELECT COUNT(*) FROM departements";

            try {
                st = cnx.prepareStatement(sql1);
                result = st.executeQuery();
                while (result.next()) {
                    if (nomdep.equals(result.getString("user"))) {
                        infobox("Ce nom de département exite déjà!", "Failed", null);
                    } else {
                        count = count + 1;
                    }
                }

                st = cnx.prepareStatement(sql2);
                result = st.executeQuery();
                if (result.next()) {
                    c = result.getInt(1);
                }
                if (count >= c) {
                    st = cnx.prepareStatement(sql);
                    st.setString(1, nomdep);
                    st.setString(2, password);
                    st.setString(3, chefdep);

                    if (anneecreation.matches("^[0-9]*$")) { // handling int value exception in data base
                        st.setInt(4, Integer.parseInt(anneecreation));
                    } else {
                        infobox("Veuillez remplir le champs 'Année de création' par une valeur entière!", "Attention",
                                null);
                    }

                    st.executeUpdate();

                    infobox("Département créer avec succès. Veuillez vous connecter!", "Done", null);

                    try {
                        fxml = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
                        vbox.getChildren().removeAll();
                        vbox.getChildren().setAll(fxml);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    infobox("Département déjà créer!", "Alert", null);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cnx = ConnexionMysql.connexionDB();
    }

}