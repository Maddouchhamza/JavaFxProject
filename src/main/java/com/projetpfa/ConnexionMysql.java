package com.projetpfa;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnexionMysql {
    public Connection cn = null;

    public static Connection connexionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxproject", "root", "madd");
            return cn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
