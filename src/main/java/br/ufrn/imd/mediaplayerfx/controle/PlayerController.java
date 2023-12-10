package br.ufrn.imd.mediaplayerfx.controle;

import br.ufrn.imd.controle.ControleUsuario;
import br.ufrn.imd.modelo.UsuarioPremium;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PlayerController {
    @FXML
    private Label username;
    @FXML
    private Label tipoContaUsuario;
    @FXML
    private Button virarPremiumBtn;

    public void start() {
        ControleUsuario controleUsuario = ControleUsuario.getInstancia();

        username.setText(controleUsuario.getUsuarioLogado().getUsername());

        if (controleUsuario.getUsuarioLogado() instanceof UsuarioPremium) {
            tipoContaUsuario.setText("Premium");
            virarPremiumBtn.setDisable(true);
        }
    }
}
