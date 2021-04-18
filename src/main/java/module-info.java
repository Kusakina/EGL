module EGL {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.desktop;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    exports egl.client;
    opens egl.client;
}