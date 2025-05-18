module org.example.practicaenequipocristianvictoraitornico {

    requires javafx.controls;
    requires javafx.fxml;

    requires kotlin.stdlib;

    // Logger
    requires logging.jvm;
    requires org.slf4j;

    // Kotlin Serialization
    requires kotlinx.serialization.core;
    requires kotlinx.serialization.json;
    //XML
    requires net.devrieze.xmlutil.serialization;


    // Result
    requires kotlin.result.jvm;

    // SQL
    requires java.sql;

    // Open Vadin
    requires open;

    // JDBI
    requires org.jdbi.v3.sqlobject;
    requires org.jdbi.v3.core;
    requires org.jdbi.v3.kotlin;
    requires org.jdbi.v3.sqlobject.kotlin;
    requires io.leangen.geantyref;
    requires kotlin.reflect;

    // Cache
    requires com.github.benmanes.caffeine;
    //bcrypt
    requires jbcrypt;
    //koin
    requires koin.core.jvm;

    //exponer el proyecto
    opens org.example.practicaenequipocristianvictoraitornico to javafx.fxml;
    exports org.example.practicaenequipocristianvictoraitornico;

    opens org.example.practicaenequipocristianvictoraitornico.players.models to javafx.fxml;
    exports org.example.practicaenequipocristianvictoraitornico.players.models;

    opens org.example.practicaenequipocristianvictoraitornico.players.dao to javafx.fxml;
    exports org.example.practicaenequipocristianvictoraitornico.players.dao;


    opens org.example.practicaenequipocristianvictoraitornico.view.routes to javafx.fxml;
    exports org.example.practicaenequipocristianvictoraitornico.view.routes;

    opens org.example.practicaenequipocristianvictoraitornico.view.acercaDe to javafx.fxml;
    exports org.example.practicaenequipocristianvictoraitornico.view.acercaDe;

    opens org.example.practicaenequipocristianvictoraitornico.view.splashScreen to javafx.fxml;
    exports org.example.practicaenequipocristianvictoraitornico.view.splashScreen;

    opens org.example.practicaenequipocristianvictoraitornico.view.tarjetasJugadores to javafx.fxml;
    exports org.example.practicaenequipocristianvictoraitornico.view.tarjetasJugadores;

    opens org.example.practicaenequipocristianvictoraitornico.view.controller to javafx.fxml;
    exports org.example.practicaenequipocristianvictoraitornico.view.controller;
}