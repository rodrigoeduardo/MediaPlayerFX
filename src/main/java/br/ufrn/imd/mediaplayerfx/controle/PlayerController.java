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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class PlayerController {
    private MediaPlayer mediaPlayer;
    /**
     * A classe Player representa um reprodutor de músicas que pode tocar, pausar, continuar, avançar para a próxima
     * música e voltar para a música anterior.
     */

    /**
     * A música atualmente em reprodução.
     */
    private Musica musicaAtual;
//            = new Musica("Big in Japan (Remaster)", "Desconhecido", "");

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
    private Button btnCriarPlaylist;

    @FXML
    private Button btnPause;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnRemovePlaylist;

    @FXML
    private Label lblMusicaAtual;

    @FXML
    private ListView<Musica> lvDiretorioGeral;

    private ObservableList<Musica> obsDiretorioGeral;

    private List<Musica> diretorioGeral;

    @FXML
    private Label lblDuration;
    @FXML
    private Slider tempoMusicaSlider;

    @FXML
    private ListView<Musica> lvPlayListAtual;

    private ObservableList<Musica> obsPlaylistAtual;

    private Playlist playlistAtual;

    @FXML
    void addDiretorio(ActionEvent event) {

    }

    @FXML
    void addMusicaPlaylistAtual(ActionEvent event) {
        ControleMusica controleMusica = ControleMusica.getInstancia();
        diretorioGeral = controleMusica.obterMusicasCadastradas();

        ChoiceDialog<Musica> dialog = new ChoiceDialog<>(diretorioGeral.get(0), diretorioGeral);
        dialog.setTitle("Selecione uma música");
        dialog.setHeaderText("Escolha uma música do diretório:");
        dialog.setContentText("Musica:");

        Optional<Musica> result = dialog.showAndWait();

        result.ifPresent(selectedItem -> {
            System.out.println("Selected Item: " + selectedItem);

            ControlePlaylist controlePlaylist = new ControlePlaylist();
            ControleUsuario controleUsuario = ControleUsuario.getInstancia();

            controlePlaylist.adicionarMusicaNaPlaylist(playlistAtual, selectedItem, controleUsuario.getUsuarioLogado());
            controleUsuario.atualizarPlaylistsDeUsuario();

            obsPlaylistAtual = FXCollections.observableArrayList(playlistAtual.getMusicas());
            lvPlayListAtual.setItems(obsPlaylistAtual);
        });
    }

    @FXML
    void cadastrarMusica(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Selecione sua música");
        chooser.setInitialDirectory(new File("./db/musicas/"));
        File file = chooser.showOpenDialog(null);
        if(file != null){
            String selectedFile = file.toURI().toString();

            String nomeTratado = selectedFile.replace("%20", " ").replace("file:/", "");
            nomeTratado = nomeTratado.replace(file.getParent().replace("file:/", "").replace("\\", "/"), "").replace("/", "");

            System.out.println("Pai: " + file.getParent().replace("file:/", "").replace("\\", "/"));

            ControleMusica controleMusica = ControleMusica.getInstancia();
            controleMusica.cadastrarMusica(nomeTratado, "nao sei", file.getParent());
            System.out.println("Música cadastrada: " + nomeTratado);

            diretorioGeral = controleMusica.obterMusicasCadastradas();

            obsDiretorioGeral = FXCollections.observableArrayList(diretorioGeral);
            lvDiretorioGeral.setItems(obsDiretorioGeral);
        }
    }

    @FXML
    void criarPlaylist(ActionEvent event) {
        ControleUsuario controleUsuario = ControleUsuario.getInstancia();
        ControlePlaylist controlePlaylist = new ControlePlaylist();
        if (controleUsuario.getUsuarioLogado() instanceof UsuarioPremium){

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Criar playlist");
            dialog.setHeaderText("Digite o nome da playlist a ser criada:");
            dialog.setContentText("Nome:");


            Optional<String> result = dialog.showAndWait();

            result.ifPresent(name -> {
                System.out.println("Nome de playlist: " + name);
                controlePlaylist.adicionarPlaylist(name, controleUsuario.getUsuarioLogado());
                controleUsuario.atualizarPlaylistsDeUsuario();

                playlistsDeUsuarioLogado = ((UsuarioPremium) controleUsuario.getUsuarioLogado()).getPlaylists();

                obsPlaylistsDoUsuario = FXCollections.observableArrayList(playlistsDeUsuarioLogado);
                lvPlaylistsDoUsuario.setItems(obsPlaylistsDoUsuario);
            });
        }
    }

    @FXML
    void removerPlaylist(ActionEvent event) {
        ControleUsuario controleUsuario = ControleUsuario.getInstancia();
        ControlePlaylist controlePlaylist = new ControlePlaylist();
        if (controleUsuario.getUsuarioLogado() instanceof UsuarioPremium){

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Remover playlist");
            dialog.setHeaderText("Digite o nome da playlist a ser removida:");
            dialog.setContentText("Nome:");


            Optional<String> result = dialog.showAndWait();

            result.ifPresent(name -> {
                System.out.println("Playlist Nome: " + name);
                controlePlaylist.removerPlaylist(name, controleUsuario.getUsuarioLogado());
                controleUsuario.atualizarPlaylistsDeUsuario();

                playlistsDeUsuarioLogado = ((UsuarioPremium) controleUsuario.getUsuarioLogado()).getPlaylists();

                obsPlaylistsDoUsuario = FXCollections.observableArrayList(playlistsDeUsuarioLogado);
                lvPlaylistsDoUsuario.setItems(obsPlaylistsDoUsuario);
            });
        }
    }

    @FXML
    void btnPause(MouseEvent event) {

    }

    @FXML
    void btnPlay(MouseEvent event) {

    }

    @FXML
    void continuarMusica(ActionEvent event) {
        if (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            return;
        }
        if (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) {
            mediaPlayer.play();
            return;
        }

        if (musicaAtual == null) {
            System.out.println("Sem música");
            return;
        }

        lblMusicaAtual.setText(musicaAtual.getNome());
        String musica = "db/musicas/" + musicaAtual.getNome() + ".mp3";
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
                    double current = mediaPlayer.getCurrentTime().toSeconds();
                    tempoAtual = current;

                    return current;
                }, mediaPlayer.currentTimeProperty())
        );
    }

    @FXML
    void pausarMusica(ActionEvent event) {
        lblMusicaAtual.setText("Pausado");
        mediaPlayer.pause();
    }

    @FXML
    void tocarMusicaAnterior(ActionEvent event) {

    }

    @FXML
    void tocarProximaMusica(ActionEvent event) {

    }

    public void start() {
        ControleUsuario controleUsuario = ControleUsuario.getInstancia();
        ControleMusica controleMusica = ControleMusica.getInstancia();

        username.setText(controleUsuario.getUsuarioLogado().getUsername());

        controleMusica.obterMusicasCadastradas();
        obsDiretorioGeral = FXCollections.observableArrayList(controleMusica.musicasCadastradas);
        lvDiretorioGeral.setItems(obsDiretorioGeral);

        // listener de diretório geral
        lvDiretorioGeral.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                musicaAtual = newValue;
                System.out.println("Selected Musica from Diretorio: " + musicaAtual);
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
                            System.out.println("Selected Musica from Playlist" + musicaAtual);
                        }
                    });
                }
            });
        }
    }

    @FXML
    void chooseMusic(MouseEvent event) {

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

    public void mudarTempoDaMusica(MouseEvent mouseEvent) {
    }
}
