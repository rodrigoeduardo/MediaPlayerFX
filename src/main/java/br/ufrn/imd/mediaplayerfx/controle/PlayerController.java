package br.ufrn.imd.mediaplayerfx.controle;

import br.ufrn.imd.controle.ControleMusica;
import br.ufrn.imd.controle.ControlePlaylist;
import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.controle.ControleUsuario;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.modelo.UsuarioPremium;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;

public class PlayerController {
    @FXML
    private Label chooseMusic;
    @FXML
    private Label username;
    @FXML
    private Label tipoContaUsuario;
    @FXML
    private Button virarPremiumBtn;

    @FXML
    private ListView<Playlist> lvPlaylistsDoUsuario;

    private ObservableList<Playlist> obsPlaylistsDoUsuario;

    private List<Playlist> playlistsDeUsuarioLogado;

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

    /**
     * A classe Player representa um reprodutor de músicas que pode tocar, pausar, continuar, avançar para a próxima
     * música e voltar para a música anterior.
     */

    /**
     * A música atualmente em reprodução.
     */
    @FXML
    private final Label lblMusicaAtual = new Label();
    private Musica musicaAtual;

    /**
     * O tempo atual de reprodução da música, em segundos.
     */
    private int tempoAtual;

    @FXML
    private ListView<Musica> lvDiretorioGeral;

    private ObservableList<Musica> obsDiretorioGeral;

    private List<Musica> diretorioGeral;

    @FXML
    private Label lblDuration;

    @FXML
    private ListView<Musica> lvPlayListAtual;

    private ObservableList<Musica> obsPlaylistAtual;

    private Playlist playlistAtual;


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

    //private Media media = new Media("C:/Users/vgmen/Music/playlist/Big in Japan (Remaster).mp3");
    private MediaPlayer mediaPlayer;
    public void start() {
        ControleUsuario controleUsuario = ControleUsuario.getInstancia();
        ControleMusica controleMusica = ControleMusica.getInstancia();

        username.setText(controleUsuario.getUsuarioLogado().getUsername());

        controleMusica.obterMusicasCadastradas();
        obsDiretorioGeral = FXCollections.observableArrayList(controleMusica.musicasCadastradas);
        lvDiretorioGeral.setItems(obsDiretorioGeral);

        lblMusicaAtual.setText("musica aaa");
        // listener de diretório geral
        lvDiretorioGeral.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                musicaAtual = newValue;
                System.out.println("Selected Musica from Diretorio: " + musicaAtual);
                System.out.println("Label: " + lblMusicaAtual.getText());
                // TODO: tocar musica atual
            }
        });

        if (controleUsuario.getUsuarioLogado() instanceof UsuarioPremium) {
            tipoContaUsuario.setText("Premium");
            virarPremiumBtn.setDisable(true);

            controleUsuario.atualizarPlaylistsDeUsuario();

            playlistsDeUsuarioLogado = ((UsuarioPremium)controleUsuario.getUsuarioLogado()).getPlaylists();
            obsPlaylistsDoUsuario = FXCollections.observableArrayList(playlistsDeUsuarioLogado);
            lvPlaylistsDoUsuario.setItems(obsPlaylistsDoUsuario);

            // listener de playlists de usuario
            lvPlaylistsDoUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    playlistAtual = newValue;
                    System.out.println("Selected Playlist: " + playlistAtual);
                    System.out.println("Musicas playlist: " + playlistAtual.getMusicas());

                    obsPlaylistAtual = FXCollections.observableArrayList(playlistAtual.getMusicas());
                    lvPlayListAtual.setItems(obsPlaylistAtual);

                    lvPlayListAtual.getSelectionModel().selectedItemProperty().addListener((observable2, oldValue2, newValue2) -> {
                        if (newValue2 != null) {
                            musicaAtual = newValue2;
                            System.out.println("Selected Musica from Diretorio: " + musicaAtual);

//                            lblMusicaAtual.setText(musicaAtual.getNome());
                            // TODO: tocar musica atual
                        }
                    });
                }
            });
        }
    }

    @FXML
    void chooseMusic(MouseEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select your music");
        File file = chooser.showOpenDialog(null);
        if(file != null){
            String selectedFile = file.toURI().toString();
            Media media = new Media(selectedFile);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() -> chooseMusic.setText(file.getName()));
        }
    }



    /**
     * Inicia do início a reprodução da música atual.
     */
    public void tocarMusica() {
        mediaPlayer.play();
    }


    /**
     * Pausa a reprodução da música atual.
     */
    public void pausarMusica() {
        mediaPlayer.pause();
    }

    /**
     * Retoma a reprodução da música atual a partir do ponto de pausa.
     */
    public void continuarMusica() {

    }

    /**
     * Avança para a próxima música do diretório ou playlist.
     */
    public void tocarProximaMusica() {

    }

    /**
     * Volta para a música anterior do diretório ou playlist.
     */
    public void tocarMusicaAnterior() {

    }

    /**
     * Obtém a música atualmente em reprodução.
     * @return Instância da classe Musica representando a música atual.
     */
    public Musica getMusicaAtual() {
        return musicaAtual;
    }

    /**
     * Define a música que será reproduzida.
     * @param musicaAtual A instância da classe Musica que será definida como a música atual.
     */
    public void setMusicaAtual(Musica musicaAtual) {
        this.musicaAtual = musicaAtual;
    }

    /**
     * Obtém o tempo atual de reprodução da música, em segundos.
     * @return Tempo atual de reprodução da música.
     */
    public int getTempoAtual() {
        return tempoAtual;
    }

    /**
     * Define o tempo atual de reprodução da música, em segundos.
     * @param tempoAtual O tempo atual de reprodução da música a ser definido.
     */
    public void setTempoAtual(int tempoAtual) {
        this.tempoAtual = tempoAtual;
    }
}
