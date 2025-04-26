module RendezvousFront {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;

    opens com.rendezvous.management.rvmangfr.controller to javafx.fxml;
    opens com.rendezvous.management.rvmangfr.model to javafx.base;

    exports com.rendezvous.management.rvmangfr;
}
