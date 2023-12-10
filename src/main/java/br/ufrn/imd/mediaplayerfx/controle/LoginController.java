package br.ufrn.imd.mediaplayerfx.controle;

import br.ufrn.imd.controle.ControleUsuario;
import br.ufrn.imd.mediaplayerfx.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usuarioTextField;
    @FXML
    private PasswordField senhaField;
    @FXML
    private Label statusLogin;
    @FXML
    private TextField usuarioTextFieldCadastro;
    @FXML
    private PasswordField senhaFieldCadastro;

    @FXML
    protected void onLoginButtonClick() {
        if (usuarioTextField.getText().isBlank() == true || senhaField.getText().isBlank() == true) return;

        ControleUsuario controleUsuario = ControleUsuario.getInstancia();

        boolean fezLogin = controleUsuario.fazerLogin(usuarioTextField.getText(), senhaField.getText());

        if (fezLogin) {
            statusLogin.setText("Usuário logado: " + controleUsuario.getUsuarioLogado().getUsername());
            try {
                abrirPlayer();
            } catch (Exception exception) {
                System.out.println("Ocorreu um erro ao abrir o player.");
            }
        }
        else statusLogin.setText("Usuário ou senha incorretos.");
    }

    @FXML
    protected void onCadastroButtonClick() {
        if (usuarioTextFieldCadastro.getText().isBlank() == true || senhaFieldCadastro.getText().isBlank() == true) return;

        ControleUsuario controleUsuario = ControleUsuario.getInstancia();

        controleUsuario.fazerCadastro(usuarioTextFieldCadastro.getText(), senhaFieldCadastro.getText(), false);

        usuarioTextFieldCadastro.clear();
        senhaFieldCadastro.clear();
    }

    private void abrirPlayer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("player-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        PlayerController playerController = fxmlLoader.getController();
        playerController.start();
        Stage stage = new Stage();
        stage.setTitle("Spotify");
        stage.setScene(scene);
        stage.show();
    }
}