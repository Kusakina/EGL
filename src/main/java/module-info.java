module EGL {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.desktop;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires net.rgielen.fxweaver.core;
    exports egl.client;
    opens egl.client;
}