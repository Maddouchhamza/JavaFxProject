package com.projetpfa;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static com.projetpfa.SignInController.id_dep;
import static com.projetpfa.SignInController.CurrentUser;
import static com.projetpfa.infoBox.infobox;

public class EtudiantsController implements Initializable {

    Connection cnx;
    public PreparedStatement st, st1, st2, st3;
    public ResultSet result, result1, result2, result3;

    @FXML
    private TextField age;

    @FXML
    private JFXButton btn_ajouter;

    @FXML
    private JFXButton btn_modifier;

    @FXML
    private JFXButton btn_rechercher;

    @FXML
    private JFXButton btn_supprimer;

    @FXML
    private TableColumn<Etudiants, Integer> cln_age;

    @FXML
    private TableColumn<Etudiants, String> cln_departement;

    @FXML
    private TableColumn<Etudiants, Integer> cln_id;

    @FXML
    private TableColumn<Etudiants, String> cln_nom;

    @FXML
    private TableColumn<Etudiants, String> cln_prenom;

    @FXML
    private TableColumn<Etudiants, String> cln_ville;

    @FXML
    private TextField txt_age;

    @FXML
    private TextField txt_departement;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_prenom;

    @FXML
    private TextField txt_ville;

    @FXML
    private TableView<Etudiants> table_etudiants;

    public ObservableList<Etudiants> data = FXCollections.observableArrayList();

    @FXML
    void Ajouter() {
        String nom = txt_nom.getText();
        String prenom = txt_prenom.getText();
        String sql4 = "select * from etudiants where nom_etudiant=? and prenom_etudiant=?";

        try {
            st1 = cnx.prepareStatement(sql4);
            st1.setString(1, nom);
            st1.setString(2, prenom);
            result1 = st1.executeQuery();
            if (result1.next()) {
                infobox("Cet etudiant existe déja dans votre base de donnée! Veuillez ajouter un nouveau etudiant!",
                        "Failed", null);
            } else {
                try {

                    String sql = "insert into etudiants(nom_etudiant,prenom_etudiant,ville,age,id_dep) values(?,?,?,?,?)";

                    st = cnx.prepareStatement(sql);

                    st.setString(1, txt_nom.getText());
                    st.setString(2, txt_prenom.getText());
                    st.setString(3, txt_ville.getText());
                    st.setInt(4, Integer.parseInt(txt_age.getText()));
                    st.setInt(5, id_dep);

                    st.executeUpdate();
                    infobox("Etudiant ajouté avec succès à votre département!", "Done",
                            null);
                    txt_age.setText("");
                    txt_id.setText("");
                    txt_nom.setText("");
                    txt_prenom.setText("");
                    txt_ville.setText("");

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                showetudiants();

            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    @FXML
    void Modifier() {

        try {
            String sql3 = "update etudiants set nom_etudiant=?, prenom_etudiant=?, age=?, ville=? where id_etudiant='"
                    + txt_id.getText() + "'";

            st3 = cnx.prepareStatement(sql3);
            st3.setString(1, txt_nom.getText());
            st3.setString(2, txt_prenom.getText());
            st3.setInt(3, Integer.parseInt(txt_age.getText()));
            st3.setString(4, txt_ville.getText());
            st3.executeUpdate();
            infobox("Informations modifié avec succès!", "Done",
                    null);

            showetudiants();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void Rechercher() {
        String sql = "select * from etudiants where id_etudiant= '" + txt_id.getText() + "'";

        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();
            if (result.next()) {
                txt_nom.setText(result.getString("nom_etudiant"));
                txt_prenom.setText(result.getString("prenom_etudiant"));
                txt_age.setText(result.getString("age"));
                txt_ville.setText(result.getString("ville"));
            } else {
                infobox("Cet etudiant n'existe pas dans ce département! Veuillez rentrer un nouveau ID.", "Failed",
                        null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Supprimer() {
        String sql2 = "delete from etudiants where id_etudiant =  '" + txt_id.getText() + "'";
        try {
            st = cnx.prepareStatement(sql2);
            st.executeUpdate();

            infobox("Etudiant supprimé avec succès!", "Done", null);

            txt_age.setText("");
            txt_id.setText("");
            txt_nom.setText("");
            txt_prenom.setText("");
            txt_ville.setText("");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        showetudiants();
    }

    public void showetudiants() {
        String sql = "select * from etudiants where id_dep= '" + id_dep + "'";

        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();
            data.clear();
            while (result.next()) {
                data.add(new Etudiants(result.getInt("id_etudiant"), result.getString("nom_etudiant"),
                        result.getString("prenom_etudiant"),
                        result.getString("ville"), result.getInt("age"), CurrentUser));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cln_age.setCellValueFactory(new PropertyValueFactory<Etudiants, Integer>("age"));
        cln_id.setCellValueFactory(new PropertyValueFactory<Etudiants, Integer>("id"));
        cln_ville.setCellValueFactory(new PropertyValueFactory<Etudiants, String>("ville"));
        cln_departement.setCellValueFactory(c -> new SimpleStringProperty(CurrentUser));
        cln_nom.setCellValueFactory(new PropertyValueFactory<Etudiants, String>("nom"));
        cln_prenom.setCellValueFactory(new PropertyValueFactory<Etudiants, String>("prenom"));
        table_etudiants.setItems(data);
    }

    @FXML
    void tableetudiants() {
        Etudiants etudiants = table_etudiants.getSelectionModel().getSelectedItem();
        String sql = "select * from etudiants where id_etudiant=?";

        try {
            st = cnx.prepareStatement(sql);
            st.setInt(1, etudiants.getId());
            result = st.executeQuery();
            if (result.next()) {
                txt_ville.setText(result.getString("ville"));
                txt_id.setText(Integer.toString(result.getInt("id_etudiant")));
                txt_nom.setText(result.getString("nom_etudiant"));
                txt_prenom.setText(result.getString("prenom_etudiant"));
                txt_age.setText(result.getString("age"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cnx = ConnexionMysql.connexionDB();
        showetudiants();
        txt_departement.setText(CurrentUser);
        txt_departement.setEditable(false);

    }

}
