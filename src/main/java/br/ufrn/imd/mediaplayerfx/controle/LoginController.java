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

    /**
     * TextField de login de usuário.
     */
    @FXML
    private TextField usuarioTextField;

    /**
     * PasswordField de login de usuário.
     */
    @FXML
    private PasswordField senhaField;

    /**
     * Label de status de login.
     */
    @FXML
    private Label statusLogin;

    /**
     * TextField de cadastro de usuário.
     */
    @FXML
    private TextField usuarioTextFieldCadastro;

    /**
     * PasswordField de cadastro de usuário.
     */
    @FXML
    private PasswordField senhaFieldCadastro;

    /**
     * Trata tentativa de Login pelo botão.
     */
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

    /**
     * Trata tentativa de cadastro pelo botão.
     */
    @FXML
    protected void onCadastroButtonClick() {
        if (usuarioTextFieldCadastro.getText().isBlank() == true || senhaFieldCadastro.getText().isBlank() == true) return;

        ControleUsuario controleUsuario = ControleUsuario.getInstancia();

        controleUsuario.fazerCadastro(usuarioTextFieldCadastro.getText(), senhaFieldCadastro.getText(), false);

        usuarioTextFieldCadastro.clear();
        senhaFieldCadastro.clear();
    }

    /**
     * Abre a janela de Player.
     * @throws IOException
     */
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