package br.ufrn.imd.mediaplayerfx.controle;

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
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class PlayerController {
    private MediaPlayer mediaPlayer;
    /**
     * A classe Player representa um reprodutor de músicas que pode tocar, pausar, continuar, avançar para a próxima
     * música e voltar para a música anterior.
     */

    /**
     * A música atualmente em reprodução.
     */
    private Musica musicaAtual = new Musica("Big in Japan (Remaster)", "Desconhecido", "");

    /**
     * O tempo atual de reprodução da música, em segundos.
     */
    private double tempoAtual;
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

    @FXML
    private ListView<Musica> lvDiretorioGeral;


    @FXML
    private Label lblDuration;
    @FXML
    private Slider tempoMusicaSlider;

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
        String musica = "db/Musicas-mp3/" + musicaAtual.getNome() + ".mp3";
        Media media = new Media(new File(musica).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        lblDuration.textProperty().bind(
                Bindings.createStringBinding(() -> {
                    double tempo = mediaPlayer.getCurrentTime().toSeconds();

                    int minutos = 0;

                    while (tempo >= 60) {
                        tempo -= 60;
                        minutos++;
                    }

                    return String.format("%02d:%02d", minutos, (int)tempo);
                }, mediaPlayer.currentTimeProperty())
        );

        tempoMusicaSlider.maxProperty().bind(
                Bindings.createDoubleBinding(() -> media.getDuration().toSeconds(), media.durationProperty())
        );
        tempoMusicaSlider.valueProperty().bind(
                Bindings.createDoubleBinding(() -> {
                    Duration current = mediaPlayer.getCurrentTime();

                    return current.toSeconds();
                }, mediaPlayer.currentTimeProperty())
        );
    }

    @FXML
    void pausarMusica(ActionEvent event) {
        mediaPlayer.pause();
    }

    @FXML
    void tocarMusicaAnterior(ActionEvent event) {
        System.out.println(mediaPlayer.getCurrentTime().toSeconds());
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

            controleUsuario.atualizarPlaylistsDeUsuario();

            playlistsDeUsuarioLogado = ((UsuarioPremium)controleUsuario.getUsuarioLogado()).getPlaylists();
            obsPlaylistsDoUsuario = FXCollections.observableArrayList(playlistsDeUsuarioLogado);
            lvPlaylistsDoUsuario.setItems(obsPlaylistsDoUsuario);

            lvPlaylistsDoUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    playlistAtual = newValue;
                    System.out.println("Selected Playlist: " + playlistAtual);
                    System.out.println("Musicas playlist: " + playlistAtual.getMusicas());

                    // Assuming getMusicas() returns a List<Musica> from the selected playlist
                    obsPlaylistAtual = FXCollections.observableArrayList(playlistAtual.getMusicas());
                    lvPlayListAtual.setItems(obsPlaylistAtual);
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
    public double getTempoAtual() {
        return tempoAtual;
    }

    /**
     * Define o tempo atual de reprodução da música, em segundos.
     * @param tempoAtual O tempo atual de reprodução da música a ser definido.
     */
    public void setTempoAtual(double tempoAtual) {
        this.tempoAtual = tempoAtual;
    }
}
