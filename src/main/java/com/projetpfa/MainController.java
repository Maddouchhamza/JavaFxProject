// package com.projetpfa;

// import javafx.application.Application;
// import javafx.stage.Stage;

// public class MainController extends Application {

//     @Override
//     public void start(Stage arg0) throws Exception {
//         // TODO Auto-generated method stub

//     }

// }

package com.projetpfa;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class MainController implements Initializable {
    @FXML
    private JFXButton btn_seconnecter;

    @FXML
    private JFXButton btn_sinscrire;

    @FXML
    private VBox vbox;

    private Parent fxml;

    @FXML
    void openSignIn() {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(vbox.getLayoutX() * 5.9);
        t.play();
        t.setOnFinished((e -> {
            try {
                fxml = FXMLLoader.load(getClass().getResource("src/main/resources/com/projetpfa/SignIn.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }));

    }

    @FXML
    void openSignUp() {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(5);
        t.play();
        t.setOnFinished((e -> {
            try {
                fxml = FXMLLoader.load(getClass().getResource("/src/main/resources/com/projetpfa/SignUp.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }));
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(vbox.getLayoutX() * -0.5);
        t.play();

    }

}