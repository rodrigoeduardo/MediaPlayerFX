package br.ufrn.imd.mediaplayerfx.controle;

import br.ufrn.imd.controle.ControleMusica;
import br.ufrn.imd.controle.ControlePlaylist;
import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.controle.ControleUsuario;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.modelo.UsuarioPremium;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * A classe PlayerController representa um reprodutor de músicas que pode tocar e pausar músicas do sistema
 * ou de playlists personalizáveis, caso o usuário seja Premium.
 */
public class PlayerController {

    /**
     * Tocador de músicas .mp3.
     */
    private MediaPlayer mediaPlayer;

    /**
     * A música atualmente em reprodução.
     */
    private Musica musicaAtual;

    /**
     * O tempo atual de reprodução da música, em segundos.
     */
    private double tempoAtual;

    /**
     * Label de username.
     */
    @FXML
    private Label username;

    /**
     * Label de tipo de conta, podendo ser Premium ou Free.
     */
    @FXML
    private Label tipoContaUsuario;

    /**
     * ListView de playlists de usuário.
     */
    @FXML
    private ListView<Playlist> lvPlaylistsDoUsuario;

    /**
     * Lista observável de playlists de usuário.
     */
    private ObservableList<Playlist> obsPlaylistsDoUsuario;

    /**
     * Lista de playlists de usuário.
     */
    private List<Playlist> playlistsDeUsuarioLogado;

    /**
     * Botão de cadastrar música.
     */
    @FXML
    private Button btnAddMusica;

    /**
     * Botão de adicionar música na playlist.
     */
    @FXML
    private Button btnAddMusicaPlaylist;

    /**
     * Botão de criar playlist pra usuário.
     */
    @FXML
    private Button btnCriarPlaylist;

    /**
     * Botão de pausar música.
     */
    @FXML
    private Button btnPause;

    /**
     * Botão de dar play na música.
     */
    @FXML
    private Button btnPlay;

    /**
     * Botão de remover playlist de usuário.
     */
    @FXML
    private Button btnRemovePlaylist;

    /**
     * Label de música tocando no momento.
     */
    @FXML
    private Label lblMusicaAtual;

    /**
     * ListView de diretório com músicas cadastradas.
     */
    @FXML
    private ListView<Musica> lvDiretorioGeral;

    /**
     * Lista observável de diretório com músicas cadastradas.
     */
    private ObservableList<Musica> obsDiretorioGeral;

    /**
     * Lista de diretório com músicas cadastradas.
     */
    private List<Musica> diretorioGeral;

    /**
     * Label de duração da música.
     */
    @FXML
    private Label lblDuration;

    /**
     * Slider representando quanto tempo passou da música.
     */
    @FXML
    private Slider tempoMusicaSlider;

    /**
     * ListView de músicas de playlist selecionada.
     */
    @FXML
    private ListView<Musica> lvPlayListAtual;

    /**
     * Lista observável de músicas de playlist selecionada.
     */
    private ObservableList<Musica> obsPlaylistAtual;

    /**
     * Lista de músicas de playlist selecionada.
     */
    private Playlist playlistAtual;

    /**
     * Última música tocada.
     */
    private Musica ultimaMusica;

    /**
     * Adiciona música na playlist selecionada.
     * @param event Click do mouse no botão.
     */
    @FXML
    void addMusicaPlaylistAtual(ActionEvent event) {
        if (playlistAtual != null){
            ControleMusica controleMusica = ControleMusica.getInstancia();
            diretorioGeral = controleMusica.obterMusicasCadastradas();

            ChoiceDialog<Musica> dialog = new ChoiceDialog<>(diretorioGeral.get(0), diretorioGeral);
            dialog.setTitle("Selecione uma música");
            dialog.setHeaderText("Escolha uma música do diretório:");
            dialog.setContentText("Musica:");

            Optional<Musica> result = dialog.showAndWait();

            result.ifPresent(selectedItem -> {
                System.out.println("Música pra adicionar na playlist: " + selectedItem);

                ControlePlaylist controlePlaylist = new ControlePlaylist();
                ControleUsuario controleUsuario = ControleUsuario.getInstancia();

                controlePlaylist.adicionarMusicaNaPlaylist(playlistAtual, selectedItem, controleUsuario.getUsuarioLogado());
                controleUsuario.atualizarPlaylistsDeUsuario();
                System.out.println(playlistAtual.getMusicas());

                obsPlaylistAtual = FXCollections.observableArrayList(playlistAtual.getMusicas());
                lvPlayListAtual.setItems(obsPlaylistAtual);
            });
        }
    }

    /**
     * Cadastra música no diretório.
     * @param event Click do mouse no botão.
     */
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

    /**
     * Cria playlist pro usuário selecionado.
     * @param event Click do mouse no botão.
     */
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
                System.out.println("Nome de playlist criada: " + name);
                controlePlaylist.adicionarPlaylist(name, controleUsuario.getUsuarioLogado());
                controleUsuario.atualizarPlaylistsDeUsuario();

                playlistsDeUsuarioLogado = ((UsuarioPremium) controleUsuario.getUsuarioLogado()).getPlaylists();

                obsPlaylistsDoUsuario = FXCollections.observableArrayList(playlistsDeUsuarioLogado);
                lvPlaylistsDoUsuario.setItems(obsPlaylistsDoUsuario);
            });
        }
    }

    /**
     * Remove playlist de usuário.
     * @param event Click do mouse no botão.
     */
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
                System.out.println("Playlist removida: " + name);
                controlePlaylist.removerPlaylist(name, controleUsuario.getUsuarioLogado());
                controleUsuario.atualizarPlaylistsDeUsuario();

                playlistsDeUsuarioLogado = ((UsuarioPremium) controleUsuario.getUsuarioLogado()).getPlaylists();

                obsPlaylistsDoUsuario = FXCollections.observableArrayList(playlistsDeUsuarioLogado);
                lvPlaylistsDoUsuario.setItems(obsPlaylistsDoUsuario);
            });
        }
    }

    /**
     * Continua ou inicia reprodução de música.
     * @param event
     */
    @FXML
    void continuarMusica(ActionEvent event) {
        if (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED) && ultimaMusica == null) {
            mediaPlayer.play();
            return;
        }

        if (musicaAtual == null) {
            System.out.println("Sem música");
            return;
        }

        if (mediaPlayer != null){
            mediaPlayer.stop();
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

    /**
     * Pausa reprodução de música.
     * @param event Click do mouse no botão.
     */
    @FXML
    void pausarMusica(ActionEvent event) {
        lblMusicaAtual.setText("Pausado");
        mediaPlayer.pause();
    }

    /**
     * Inicializa janela de player.
     */
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
                if (musicaAtual != null){
                    ultimaMusica = musicaAtual;
                }
                musicaAtual = newValue;
                System.out.println("Selected Musica from Diretorio: " + musicaAtual);
            }
        });

        if (controleUsuario.getUsuarioLogado() instanceof UsuarioPremium) {
            tipoContaUsuario.setText("Premium");

            controleUsuario.atualizarPlaylistsDeUsuario();

            playlistsDeUsuarioLogado = ((UsuarioPremium)controleUsuario.getUsuarioLogado()).getPlaylists();
            obsPlaylistsDoUsuario = FXCollections.observableArrayList(playlistsDeUsuarioLogado);
            lvPlaylistsDoUsuario.setItems(obsPlaylistsDoUsuario);

            // listener de playlists de usuário
            lvPlaylistsDoUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    playlistAtual = newValue;
                    System.out.println("Selected Playlist: " + playlistAtual);
                    System.out.println("Musicas playlist: " + playlistAtual.getMusicas());

                    obsPlaylistAtual = FXCollections.observableArrayList(playlistAtual.getMusicas());
                    lvPlayListAtual.setItems(obsPlaylistAtual);

                    // listener de playlist selecionado
                    lvPlayListAtual.getSelectionModel().selectedItemProperty().addListener((observable2, oldValue2, newValue2) -> {
                        if (newValue2 != null) {
                            if (musicaAtual != null){
                                ultimaMusica = musicaAtual;
                            }
                            musicaAtual = newValue2;
                            System.out.println("Selected Musica from Playlist" + musicaAtual);
                        }
                    });
                }
            });
        } else {
            btnCriarPlaylist.setDisable(true);
            btnRemovePlaylist.setDisable(true);
            btnAddMusicaPlaylist.setDisable(true);
        }
    }
}
