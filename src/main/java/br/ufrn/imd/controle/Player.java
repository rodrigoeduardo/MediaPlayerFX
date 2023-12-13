package br.ufrn.imd.controle;

import br.ufrn.imd.modelo.Musica;

/**
 * A classe Player representa um reprodutor de músicas que pode tocar, pausar, continuar, avançar para a próxima
 * música e voltar para a música anterior.
 */
public class Player {

    /**
     * A música atualmente em reprodução.
     */
    private Musica musicaAtual;

    /**
     * O tempo atual de reprodução da música, em segundos.
     */
    private int tempoAtual;

    /**
     * Construtor da classe Player.
     */
    public Player() {

    }

    /**
     * Inicia do início a reprodução da música atual.
     */
    public void tocarMusica() {

    }

    /**
     * Pausa a reprodução da música atual.
     */
    public void pausarMusica() {

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
