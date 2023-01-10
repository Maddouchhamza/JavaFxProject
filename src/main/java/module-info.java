module com.projetpfa {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.projetpfa to javafx.fxml;

    exports com.projetpfa;

    requires transitive java.sql;

    requires com.jfoenix;
}
