package br.ufrn.imd.mediaplayerfx.controle;

import br.ufrn.imd.controle.ControleUsuario;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.modelo.UsuarioPremium;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class PlayerController {
    @FXML
    private Label username;
    @FXML
    private Label tipoContaUsuario;
    @FXML
    private Button virarPremiumBtn;
    @FXML
    private ListView<Playlist> playlistsDoUsuario;
    @FXML
    private Button btnAddDiretorio;

    @FXML
    private Button btnAddMusica;

    @FXML
    private Button btnAddMusicaPlaylist;

    @FXML
    private Button btnPause;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnRemovePlaylist;

    @FXML
    private ListView<?> diretorioGeral;

    @FXML
    private Label lblDuration;

    @FXML
    private ListView<?> playListAtual;



    @FXML
    void btnPause(MouseEvent event) {

    }

    @FXML
    void btnPlay(MouseEvent event) {

    }

    @FXML
    void continuarMusica(ActionEvent event) {

    }

    @FXML
    void pausarMusica(ActionEvent event) {

    }

    @FXML
    void tocarMusicaAnterior(ActionEvent event) {

    }

    @FXML
    void tocarProximaMusica(ActionEvent event) {

    }

    public void start() {
        ControleUsuario controleUsuario = ControleUsuario.getInstancia();

        username.setText(controleUsuario.getUsuarioLogado().getUsername());

        if (controleUsuario.getUsuarioLogado() instanceof UsuarioPremium) {
            tipoContaUsuario.setText("Premium");
            virarPremiumBtn.setDisable(true);
        }
    }


}
