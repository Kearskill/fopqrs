module com.tadalist.dao.fopqrs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.tadalist.dao.fopqrs to javafx.fxml;
    exports com.tadalist.dao.fopqrs;
}