package com.projetpfa;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static com.projetpfa.infoBox.infobox;

public class MatièresController implements Initializable {

    Connection cnx;
    public PreparedStatement st, st1, st2, st3;
    public ResultSet result, result1, result2, result3;

    @FXML
    private JFXButton btn_ajouter;

    @FXML
    private JFXButton btn_modifier;

    @FXML
    private JFXButton btn_rechercher;

    @FXML
    private JFXButton btn_supprimer;

    @FXML
    private TableColumn<Matiere, Integer> cln_coef;

    @FXML
    private TableColumn<Matiere, Integer> cln_id;

    @FXML
    private TableColumn<Matiere, String> cln_mod;

    @FXML
    private TableColumn<Matiere, String> cln_nom;

    @FXML
    private TableColumn<Matiere, Integer> cln_volume;

    @FXML
    private TableView<Matiere> table_matieres;

    @FXML
    private TextField txt_coef;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_matiere;

    @FXML
    private TextField txt_module;

    @FXML
    private TextField txt_volume;

    public ObservableList<Matiere> data = FXCollections.observableArrayList();

    @FXML
    void tableMatieres() {
        Matiere matieres = table_matieres.getSelectionModel().getSelectedItem();
        txt_id.setText(Integer.toString(matieres.getId()));
        txt_matiere.setText(matieres.getNom_mat());
        txt_coef.setText(Integer.toString(matieres.getCoef()));
        txt_volume.setText(Integer.toString(matieres.getVolume()));
        txt_module.setText(matieres.getModule());
    }

    @FXML
    void Ajouter() {
        String nom = txt_matiere.getText();
        String sql4 = "select * from matieres where nom_matiere=? ";

        try {
            st1 = cnx.prepareStatement(sql4);
            st1.setString(1, nom);
            result1 = st1.executeQuery();
            if (result1.next()) {
                infobox("Cette matière existe déja dans votre base de donnée! Veuillez ajouter une nouvelle matière!",
                        "Failed", null);
            } else {
                try {
                    String sql = "insert into matieres(nom_matiere,module,coefficient,volume_horraire) values(?,?,?,?)";

                    st = cnx.prepareStatement(sql);

                    st.setString(1, txt_matiere.getText());
                    st.setString(2, txt_module.getText());
                    st.setInt(3, Integer.parseInt(txt_coef.getText()));

                    String coef = txt_coef.getText();
                    if (coef.matches("^[0-9]*$")) { // handling int value exception in data base
                        st.setInt(3, Integer.parseInt(txt_coef.getText()));
                    } else {
                        infobox("Veuillez remplir le champs 'Coefficient' par une valeur entière!", "Attention", null);
                    }

                    String volume = txt_volume.getText();
                    if (volume.matches("^[0-9]*$")) { // handling int value exception in data base
                        st.setInt(4, Integer.parseInt(txt_volume.getText()));
                    } else {
                        infobox("Veuillez remplir le champs 'Volume horaire' par une valeur entière!", "Attention",
                                null);
                    }

                    st.executeUpdate();
                    infobox("Matière ajoutée avec succès!", "Done",
                            null);
                    txt_id.setText("");
                    txt_matiere.setText("");
                    txt_module.setText("");
                    txt_coef.setText("");
                    txt_volume.setText("");
                    showMatieres();

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
            String sql3 = "update matieres set nom_matiere=?, module=?, coefficient=?, volume_horraire=? where id_matiere='"
                    + txt_id.getText() + "'";

            st3 = cnx.prepareStatement(sql3);
            st3.setString(1, txt_matiere.getText());
            st3.setString(2, txt_module.getText());

            String coef = txt_coef.getText();
            if (coef.matches("^[0-9]*$")) { // handling int value exception in data base
                st3.setInt(3, Integer.parseInt(txt_coef.getText()));
            } else {
                infobox("Veuillez remplir le champs 'Coefficient' par une valeur entière!", "Attention", null);
            }

            String volume = txt_volume.getText();
            if (volume.matches("^[0-9]*$")) { // handling int value exception in data base
                st3.setInt(4, Integer.parseInt(txt_volume.getText()));
            } else {
                infobox("Veuillez remplir le champs 'Volume horaire' par une valeur entière!", "Attention",
                        null);
            }

            st3.executeUpdate();
            infobox("Informations modifié avec succès!", "Done",
                    null);

            txt_id.setText("");
            txt_matiere.setText("");
            txt_module.setText("");
            txt_coef.setText("");
            txt_volume.setText("");

            showMatieres();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void Rechercher() {
        String sql = "select * from matieres where id_matiere='" + txt_id.getText() + "'";

        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();
            if (result.next()) {
                txt_matiere.setText(result.getString("nom_matiere"));
                txt_module.setText(result.getString("module"));
                txt_coef.setText(result.getString("coefficient"));
                txt_volume.setText(result.getString("volume_horraire"));
            } else {
                infobox("Cette matière n'existe pas dans votre base de donnée! Veuillez rentrer un nouveau ID.",
                        "Failed",
                        null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Supprimer() {

        String sql = "select * from professeurs where id_matiere='" + txt_id.getText() + "'";
        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();

            if (result.next()) {
                infobox("Impossible de supprimer cette matière! Elle est déjà affectée à un professeur! ", "Alert",
                        null);
            } else {
                String sql2 = "delete from matieres where id_matiere =  '" + txt_id.getText() + "'";
                try {
                    st = cnx.prepareStatement(sql2);
                    st.executeUpdate();

                    infobox("Matière supprimée avec succès!", "Done", null);

                    txt_id.setText("");
                    txt_matiere.setText("");
                    txt_module.setText("");
                    txt_coef.setText("");
                    txt_volume.setText("");

                    showMatieres();

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showMatieres() {
        String sql = "select *  from matieres";

        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();
            data.clear();
            while (result.next()) {
                data.add(new Matiere(result.getInt("id_matiere"), result.getString("nom_matiere"),
                        result.getString("module"), result.getInt("coefficient"), result.getInt("volume_horraire")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cln_id.setCellValueFactory(new PropertyValueFactory<Matiere, Integer>("id"));
        cln_nom.setCellValueFactory(new PropertyValueFactory<Matiere, String>("nom_mat"));
        cln_coef.setCellValueFactory(new PropertyValueFactory<Matiere, Integer>("coef"));
        cln_volume.setCellValueFactory(new PropertyValueFactory<Matiere, Integer>("volume"));
        cln_mod.setCellValueFactory(new PropertyValueFactory<Matiere, String>("module"));
        table_matieres.setItems(data);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cnx = ConnexionMysql.connexionDB();
        showMatieres();

    }

}
