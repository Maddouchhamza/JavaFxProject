package com.projetpfa;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static com.projetpfa.SignInController.id_dep;
import static com.projetpfa.SignInController.CurrentUser;

import static com.projetpfa.infoBox.infobox;

public class ProfesseursController implements Initializable {

    Connection cnx;
    public PreparedStatement st, st1, st2, st3;
    public ResultSet result, result1, result2, result3;
    private int id_matiere;

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
    private TableColumn<Professeurs, String> cln_departement;

    @FXML
    private TableColumn<Professeurs, Integer> cln_id;

    @FXML
    private TableColumn<Professeurs, String> cln_matiere;

    @FXML
    private TableColumn<Professeurs, String> cln_nom;

    @FXML
    private TableColumn<Professeurs, String> cln_prenom;

    @FXML
    private DatePicker date_picker;

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
    void tableProfesseurs() {
        Professeurs professeurs = table_professeurs.getSelectionModel().getSelectedItem();
        txt_id.setText(Integer.toString(professeurs.getId()));
        txt_nom.setText(professeurs.getNom());
        txt_prenom.setText(professeurs.getPrenom());
        txt_age.setText(Integer.toString(professeurs.getAge()));
        Date date = professeurs.getDate_naissance();
        date_picker.setValue(date.toLocalDate());
        txt_departement.setText(professeurs.getDepartement());
        txt_matiere.setText(professeurs.getMatiere());

    }

    @FXML
    void Ajouter() {
        String nom = txt_nom.getText();
        String prenom = txt_prenom.getText();
        String sql4 = "select * from professeurs where nom=? and prenom=?";

        try {
            st1 = cnx.prepareStatement(sql4);
            st1.setString(1, nom);
            st1.setString(2, prenom);
            result1 = st1.executeQuery();
            if (result1.next()) {
                infobox("Ce professeur existe déja dans votre base de donnée! Veuillez ajouter un nouveau professeur!",
                        "Failed", null);
            } else {
                try {
                    String sql2 = "select * from matieres where nom_matiere='" + txt_matiere.getText() + "'";

                    st2 = cnx.prepareStatement(sql2);
                    result2 = st2.executeQuery();
                    if (result2.next()) {
                        id_matiere = result2.getInt("id_matiere");
                    } else {
                        infobox("Cette matière n'est pas enseignée dans votre établissemet. Veuillez rentrer une nouvelle matière !",
                                "Failed",
                                null);
                    }

                    java.util.Date date = java.util.Date
                            .from(date_picker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date sqlDate = new Date(date.getTime());

                    String sql = "insert into professeurs(nom,prenom,date_naissance,age,id_dep,id_matiere) values(?,?,?,?,?,?)";

                    st = cnx.prepareStatement(sql);

                    st.setString(1, txt_nom.getText());
                    st.setString(2, txt_prenom.getText());
                    st.setDate(3, sqlDate);
                    String age = txt_age.getText();
                    if (age.matches("^[0-9]*$")) { // handling int value exception in data base
                        st.setInt(4, Integer.parseInt(txt_age.getText()));
                    } else {
                        infobox("Veuillez remplir le champs 'Age' par une valeur entière!", "Attention", null);
                    }
                    st.setInt(5, id_dep);
                    st.setInt(6, id_matiere);

                    st.executeUpdate();
                    infobox("Professeur ajouté avec succès à votre département!", "Done",
                            null);
                    txt_age.setText("");
                    txt_id.setText("");
                    txt_nom.setText("");
                    txt_prenom.setText("");
                    date_picker.setValue(null);
                    txt_departement.setText("");
                    txt_matiere.setText("");
                    showProfesseurs();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    @FXML
    void Modifier() {
        try {
            String sql = "select * from professeurs natural join matieres where id_prof ='" + txt_id.getText() + "'";
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();

            if (result.next()) {
                if (txt_matiere.getText() != result.getString("nom_matiere")) {

                    String sql2 = "select * from matieres where nom_matiere='" + txt_matiere.getText() + "'";

                    st2 = cnx.prepareStatement(sql2);
                    result2 = st2.executeQuery();
                    if (result2.next()) {
                        int nv_id_matiere = result2.getInt("id_matiere");
                        java.util.Date date = java.util.Date
                                .from(date_picker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Date sqlDate = new Date(date.getTime());

                        String sql3 = "update professeurs set nom=?, prenom=?, age=?, date_naissance=?, id_matiere=? where id_prof='"
                                + txt_id.getText() + "'";

                        st3 = cnx.prepareStatement(sql3);
                        st3.setString(1, txt_nom.getText());
                        st3.setString(2, txt_prenom.getText());

                        String age = txt_age.getText();
                        if (age.matches("^[0-9]*$")) { // handling int value exception in data base
                            st3.setInt(3, Integer.parseInt(txt_age.getText()));
                        } else {
                            infobox("Veuillez remplir le champs 'Age' par une valeur entière!", "Attention", null);
                        }

                        st3.setDate(4, sqlDate);
                        st3.setInt(5, nv_id_matiere);
                        st3.executeUpdate();
                        infobox("Informations modifié avec succès!", "Done",
                                null);
                        txt_age.setText("");
                        txt_id.setText("");
                        txt_nom.setText("");
                        txt_prenom.setText("");
                        date_picker.setValue(null);
                        txt_departement.setText("");
                        txt_matiere.setText("");

                    }

                } else {
                    java.util.Date date = java.util.Date
                            .from(date_picker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date sqlDate = new Date(date.getTime());

                    String sql2 = "update professeurs set nom=?, prenom=?, age=?, date_naissance=? where id_prof='"
                            + txt_id.getText() + "'";

                    st2 = cnx.prepareStatement(sql2);
                    st2.setString(1, txt_nom.getText());
                    st2.setString(2, txt_prenom.getText());

                    String age = txt_age.getText();
                    if (age.matches("^[0-9]*$")) { // handling int value exception in data base
                        st3.setInt(3, Integer.parseInt(txt_age.getText()));
                    } else {
                        infobox("Veuillez remplir le champs 'Age' par une valeur entière!", "Attention", null);
                    }
                    st2.setDate(4, sqlDate);
                    st2.executeUpdate();

                    infobox("Informations modifié avec succès!", "Done",
                            null);
                    txt_age.setText("");
                    txt_id.setText("");
                    txt_nom.setText("");
                    txt_prenom.setText("");
                    date_picker.setValue(null);
                    txt_departement.setText("");
                    txt_matiere.setText("");

                }
            }
            showProfesseurs();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void Rechercher() {
        String sql = "select id_prof, nom, prenom, age, date_naissance, user, nom_matiere from (select * from professeurs natural join departements )temp natural join matieres where id_prof= '"
                + txt_id.getText() + "'and id_dep='" + id_dep + "'";

        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();
            if (result.next()) {
                txt_nom.setText(result.getString("nom"));
                txt_prenom.setText(result.getString("prenom"));
                txt_age.setText(result.getString("age"));
                Date date = result.getDate("date_naissance");
                date_picker.setValue(date.toLocalDate());
                txt_departement.setText(result.getString("user"));
                txt_matiere.setText(result.getString("nom_matiere"));
            } else {
                infobox("Ce professeur n'existe pas dans ce département! Veuillez rentrer un nouveau ID.", "Failed",
                        null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Supprimer() {
        String sql2 = "delete from professeurs where id_prof =  '" + txt_id.getText() + "'";
        try {
            st = cnx.prepareStatement(sql2);
            st.executeUpdate();

            infobox("Professeur supprimé avec succès!", "Done", null);

            txt_age.setText("");
            txt_id.setText("");
            txt_nom.setText("");
            txt_prenom.setText("");
            date_picker.setValue(null);
            txt_departement.setText("");
            txt_matiere.setText("");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        showProfesseurs();
    }

    public void showProfesseurs() {
        String sql = "select * from professeurs natural join matieres where id_dep= '" + id_dep + "'";

        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();
            data.clear();
            while (result.next()) {
                data.add(new Professeurs(result.getInt("id_prof"), result.getString("nom"),
                        result.getString("prenom"),
                        result.getString("nom_matiere"), result.getInt("age"), CurrentUser,
                        result.getDate("date_naissance")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cln_age.setCellValueFactory(new PropertyValueFactory<Professeurs, Integer>("age"));
        cln_id.setCellValueFactory(new PropertyValueFactory<Professeurs, Integer>("id"));
        cln_matiere.setCellValueFactory(new PropertyValueFactory<Professeurs, String>("matiere"));
        cln_departement.setCellValueFactory(c -> new SimpleStringProperty(CurrentUser));
        cln_date.setCellValueFactory(new PropertyValueFactory<Professeurs, Date>("date_naissance"));
        cln_nom.setCellValueFactory(new PropertyValueFactory<Professeurs, String>("nom"));
        cln_prenom.setCellValueFactory(new PropertyValueFactory<Professeurs, String>("prenom"));
        table_professeurs.setItems(data);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cnx = ConnexionMysql.connexionDB();
        showProfesseurs();
        txt_departement.setText(CurrentUser);
        txt_departement.setEditable(false);
    }
}
