module raytracing {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.logging;

    exports cs3318.raytracing;
    opens cs3318.raytracing to javafx.fxml;
}