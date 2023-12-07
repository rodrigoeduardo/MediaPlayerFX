package br.ufrn.imd.mediaplayerfx.controle;

import br.ufrn.imd.controle.ControleUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usuarioTextField;
    @FXML
    private PasswordField senhaField;
    @FXML
    private Label statusLogin;

    @FXML
    protected void onLoginButtonClick() {
        if (usuarioTextField.getText().isBlank() == true || senhaField.getText().isBlank() == true) return;

        ControleUsuario controleUsuario = new ControleUsuario();

        boolean fezLogin = controleUsuario.fazerLogin(usuarioTextField.getText(), senhaField.getText());

        if (fezLogin) statusLogin.setText("Usuário logado: " + controleUsuario.getUsuarioLogado().getUsername());
        else statusLogin.setText("Usuário ou senha incorretos.");
    }
}