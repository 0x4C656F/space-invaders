module com.space_invaders {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens com.space_invaders to javafx.fxml;
    exports com.space_invaders;
    exports com.space_invaders.Ships;
    }
