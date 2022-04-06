module JavaFxEmailClient{
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires jakarta.mail;
    requires java.desktop;

    opens consulting.saladinobelisario;
    opens consulting.saladinobelisario.view;
    opens consulting.saladinobelisario.controller;
    opens consulting.saladinobelisario.model;

}