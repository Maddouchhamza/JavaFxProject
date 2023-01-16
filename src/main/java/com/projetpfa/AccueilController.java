package com.projetpfa;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.projetpfa.SignInController.CurrentUser;
import static com.projetpfa.infoBox.infobox;

public class AccueilController implements Initializable {

    Connection cnx;
    public PreparedStatement st;
    public ResultSet result;
    private int counter = 0;

    @FXML
    private JFXButton btn_delete;

    @FXML
    private JFXButton btn_update;

    @FXML
    private TextField txt_chef_dep;

    @FXML
    private TextField txt_creation_date;

    @FXML
    private TextField txt_id_dep;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_user;

    @FXML
    private Parent fxml;

    @FXML
    private AnchorPane root;

    @FXML
    void ShowInfo() {
        String sql = "select * from departements where user= '" + CurrentUser + "'";
        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();
            if (result.next()) {
                txt_id_dep.setText(result.getString("id_dep"));
                txt_user.setText(result.getString("user"));
                txt_password.setText(result.getString("password"));
                txt_chef_dep.setText(result.getString("chef_dep"));
                txt_creation_date.setText(result.getString("annee_creation"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Update() {

        String sql = "update departements set user=?, password=?, chef_dep=?, annee_creation=? where id_dep = '"
                + txt_id_dep.getText() + "'";

        try {
            st = cnx.prepareStatement(sql);
            st.setString(1, txt_user.getText());
            st.setString(2, txt_password.getText());
            st.setString(3, txt_chef_dep.getText());

            String date = txt_creation_date.getText();
            if (date.matches("^[0-9]*$")) { // handling int value exception in data base
                st.setInt(4, Integer.parseInt(txt_creation_date.getText()));
            } else {
                infobox("Veuillez remplir le champs 'Année de création' par une valeur entière!", "Attention", null);
            }

            st.executeUpdate(); // source du probleme st.executeUpdate(sql);

            // ---------------------------------------------------------------------------------------------------
            // mettre à jour CurrentUser pour reloader à nv home avec le nouveu nom du
            // département, reload ne s'effectue que si user est changer sinon on affiche
            // que les champs son misent àjours

            if (!CurrentUser.equals(txt_user.getText())) {
                CurrentUser = txt_user.getText();
                infobox("Mise à jour réussite! La fenêtre sera actualiser!", "Done", null);
                root.getScene().getWindow().hide();
                Stage home = new Stage();
                try {
                    fxml = FXMLLoader.load(getClass().getResource("Home.fxml"));
                    Scene scene = new Scene(fxml);
                    home.setScene(scene);
                    scene.setFill(Color.TRANSPARENT); // 2 ligne pour éliminer les bordures de la fenetre
                    home.initStyle(StageStyle.TRANSPARENT);
                    home.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                counter = counter + 1;
            }
            if (counter == 0) {
                infobox("Mise à jour réussite!", "Done", null);
            }

            // ----------------------------------------------------------------------------------------------------
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void Delete() {
        infobox("Attention: Tout les professeurs et les étudiants du départements seront supprimés!", "Alert", null);

        String sql1 = "delete from professeurs where id_dep = '" + txt_id_dep.getText() + "'";

        try {
            st = cnx.prepareStatement(sql1);
            st.executeUpdate(sql1);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        String sql = "delete from etudiants where id_dep = '" + txt_id_dep.getText() + "'";

        try {
            st = cnx.prepareStatement(sql);
            st.executeUpdate(sql);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        String sql2 = "delete from departements where id_dep = '" + txt_id_dep.getText() + "'";

        try {
            st = cnx.prepareStatement(sql2);
            st.executeUpdate(sql2);
            root.getScene().getWindow().hide();
            Stage home = new Stage();
            try {
                fxml = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene scene = new Scene(fxml);
                home.setScene(scene); // missed one
                scene.setFill(Color.TRANSPARENT); // 2 ligne pour éliminer les bordures de la fenetre
                home.initStyle(StageStyle.TRANSPARENT);
                home.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        infobox("Département supprimé avec succès!", "Done", null);

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cnx = ConnexionMysql.connexionDB();
        ShowInfo();
        txt_id_dep.setEditable(false);
    }

}
