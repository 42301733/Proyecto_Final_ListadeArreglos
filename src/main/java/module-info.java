module laboratorio.galeriadeimagenesarraylist {
    requires javafx.controls;
    requires javafx.fxml;

    opens laboratorio.galeriadeimagenesarraylist to javafx.fxml;
    exports laboratorio.galeriadeimagenesarraylist;
}
