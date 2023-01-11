package com.projetpfa;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AccueilController implements Initializable {

    // Connection cnx;
    // public PreparedStatement st;
    // public ResultSet result;

    // @FXML
    // private JFXButton btn_delete;

    // @FXML
    // private JFXButton btn_update;

    // @FXML
    // private TextField txt_chef_dep;

    // @FXML
    // private TextField txt_creation_date;

    // @FXML
    // private TextField txt_id_dep;

    // @FXML
    // private TextField txt_password;

    // @FXML
    // private TextField txt_user;

    // @FXML
    // void showInterface() {
    // String sql = "select * from départements";
    // try {
    // st = cnx.prepareStatement(sql);
    // result = st.executeQuery();
    // if (result.next()) {
    // txt_id_dep.setText(result.getString("id_dep"));
    // txt_user.setText(result.getString("user"));
    // txt_password.setText(result.getString("password"));
    // txt_chef_dep.setText(result.getString("chef_dep"));
    // txt_creation_date.setText(result.getString("année_création"));
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    // @FXML
    // void Delete() {
    // String id;
    // String password;
    // String creation;
    // String user;
    // String sql;
    // try {
    // st = cnx.prepareStatement(sql);
    // result = st.executeQuery();
    // if (result.next()) {
    // }
    // } catch (Exception e) {
    // showInterface();
    // // TODO: handle exception
    // }

    // }

    // @FXML
    // void Update() {

    // String sql2 = "select * from départements";

    // try {
    // st = cnx.prepareStatement(sql2);
    // result = st.executeQuery();
    // if (result.next()) {
    // try {

    // } catch (Exception e) {
    // // TODO: handle exception
    // }
    // }
    // } catch (Exception e) {
    // // TODO: handle exception
    // }

    // }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // cnx = ConnexionMysql.connexionDB();
        // showInterface();

    }

}
