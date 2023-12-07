module br.ufrn.imd.mediaplayerfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.ufrn.imd.mediaplayerfx to javafx.fxml;
    exports br.ufrn.imd.mediaplayerfx;
    exports br.ufrn.imd.mediaplayerfx.controle;
    opens br.ufrn.imd.mediaplayerfx.controle to javafx.fxml;
}