module com.tadalist.dao.fopqrs {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.tadalist.dao.fopqrs to javafx.fxml;
    exports com.tadalist.dao.fopqrs;
}