package br.ufrn.imd.controle;

import br.ufrn.imd.modelo.Musica;

public class Player {
    private Musica musicaAtual;
    private int tempoAtual;

    public Player() {

    }

    public void tocarMusica() {

    }

    public void pausarMusica() {

    }

    public void continuarMusica() {

    }

    public void tocarProximaMusica() {

    }

    public void tocarMusicaAnterior() {

    }

    public Musica getMusicaAtual() {
        return musicaAtual;
    }

    public void setMusicaAtual(Musica musicaAtual) {
        this.musicaAtual = musicaAtual;
    }

    public int getTempoAtual() {
        return tempoAtual;
    }

    public void setTempoAtual(int tempoAtual) {
        this.tempoAtual = tempoAtual;
    }
}
