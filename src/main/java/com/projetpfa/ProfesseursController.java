package com.projetpfa;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProfesseursController extends Application {

    Connection cnx;
    public PreparedStatement st;
    public ResultSet result;

    @FXML
    private JFXButton btn_ajouter;

    @FXML
    private JFXButton btn_modifier;

    @FXML
    private JFXButton btn_rechercher;

    @FXML
    private JFXButton btn_supprimer;

    @FXML
    private TableColumn<Professeurs, Integer> cln_age;

    @FXML
    private TableColumn<Professeurs, Date> cln_date;

    @FXML
    private TableColumn<Professeurs, Integer> cln_departement;

    @FXML
    private TableColumn<Professeurs, Integer> cln_id;

    @FXML
    private TableColumn<Professeurs, Integer> cln_matiere;

    @FXML
    private TableColumn<Professeurs, String> cln_nom;

    @FXML
    private TableColumn<Professeurs, String> cln_prenom;

    @FXML
    private JFXComboBox<Date> date_picker;

    @FXML
    private TableView<Professeurs> table_professeurs;

    @FXML
    private TextField txt_age;

    @FXML
    private TextField txt_departement;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_matiere;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_prenom;

    public ObservableList<Professeurs> data = FXCollections.observableArrayList();

    @FXML
    void Ajouter() {

    }

    @FXML
    void Modifier() {

    }

    @FXML
    void Rechercher() {
        String sql = "select * from (select id_prof, nom, prenom, date_naissance, age, id_dep, id_matiere, from professeurs natural join departements on professeurs.id_dep=departements.id_dep) where id_dep= '"
                + txt_id.getText() + "'";
        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();
            while (result.next()) {
                txt_id.setText(result.getString("id_dep"));
                txt_nom.setText(result.getString("nom"));
                txt_prenom.setText(result.getString("prenom"));
                txt_age.setText(result.getString("age"));
                txt_departement.setText(result.getString("annee_creation"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Supprimer() {

    }

    public void showProfesseurs() {
        String sql = "select * from professeurs";
        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();
            while (result.next()) {
                data.add(new Professeurs(result.getInt("id_prof"), result.getString("nom"), result.getString("prenom"),
                        result.getInt("id_matiere"), result.getInt("age"), result.getInt("id_dep"),
                        result.getDate("date_naissance")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cln_age.setCellValueFactory(new PropertyValueFactory<Professeurs, Integer>("age"));
        cln_id.setCellValueFactory(new PropertyValueFactory<Professeurs, Integer>("id_prof"));
        cln_matiere.setCellValueFactory(new PropertyValueFactory<Professeurs, Integer>("id_matiere"));
        cln_departement.setCellValueFactory(new PropertyValueFactory<Professeurs, Integer>("id_dep"));
        cln_date.setCellValueFactory(new PropertyValueFactory<Professeurs, Date>("date_naissance"));
        cln_nom.setCellValueFactory(new PropertyValueFactory<Professeurs, String>("nom"));
        cln_prenom.setCellValueFactory(new PropertyValueFactory<Professeurs, String>("prenom"));
        table_professeurs.setItems(data);
    }

    @Override
    public void start(Stage arg0) throws Exception {
        cnx = ConnexionMysql.connexionDB();
        showProfesseurs();
    }

}
