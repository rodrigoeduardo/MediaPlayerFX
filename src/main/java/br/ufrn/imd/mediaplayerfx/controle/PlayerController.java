package br.ufrn.imd.mediaplayerfx.controle;

import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.controle.ControleUsuario;

import br.ufrn.imd.modelo.UsuarioPremium;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.MediaPlayer;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import java.io.File;

public class PlayerController {
    @FXML
    private Label username;
    @FXML
    private Label tipoContaUsuario;
    @FXML
    private Button virarPremiumBtn;

    private Media media = new Media("C:/Users/vgmen/Music/playlist/Big in Japan (Remaster).mp3");
    private MediaPlayer mediaPlayer = new MediaPlayer(media);
    public void start() {
        ControleUsuario controleUsuario = ControleUsuario.getInstancia();

        username.setText(controleUsuario.getUsuarioLogado().getUsername());

        if (controleUsuario.getUsuarioLogado() instanceof UsuarioPremium) {
            tipoContaUsuario.setText("Premium");
            virarPremiumBtn.setDisable(true);
        }
    }

    private Label chooseMusic;

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
     * A classe Player representa um reprodutor de músicas que pode tocar, pausar, continuar, avançar para a próxima
     * música e voltar para a música anterior.
     */

        /**
         * A música atualmente em reprodução.
         */
        private Musica musicaAtual;

        /**
         * O tempo atual de reprodução da música, em segundos.
         */
        private int tempoAtual;

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
