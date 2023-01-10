package com.projetpfa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Main"));
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT); // 2 ligne pour éliminer les bordures de la fenetre
        stage.initStyle(StageStyle.TRANSPARENT);
        String css = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();

        // String url="jdbc:mysql://localhost:3306/javafxproject";
        // String user="root";
        // String password="madd";
        // try {
        // Class.forName("com.mysql.jdbc.Driver");
        // Connection connection=DriverManager.getConnection(url,user,password);
        // System.out.println("Connection is successful to the database" +url);

        // Statement statement=connection.createStatement();
        // String query = "INSERT INTO matières VALUES (2,'Arabe','Langues',3.5)";
        // statement.executeUpdate(query);
        // System.out.println("Inserted records into the table...");

        // String query="SELECT * FROM mizo";
        // ResultSet rs = statement.executeQuery(query);
        // while(rs.next()){
        // //Display values
        // System.out.print("ID:" + rs.getInt("ID"));
        // System.out.print("\nName:" + rs.getString("NAME"));
        // System.out.print("\n---------------------------------------------------\n");
        // }

        // } catch (ClassNotFoundException | SQLException e) {
        // e.printStackTrace();
        // }
    }

}