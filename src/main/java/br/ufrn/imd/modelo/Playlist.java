package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
  private String nome;
  private List<Musica> musicas;

  public Playlist(String nome) {
    this.nome = nome;
    this.musicas = new ArrayList<>();
  }

  public void adicionarMusica(Musica musica) {
    this.musicas.add(musica);
  }

  public void removerMusica(Musica musica) {
    this.musicas.remove(musica);
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String novoNome) {
    this.nome = novoNome;
  }

  public List<Musica> getMusicas() {
    return this.musicas;
  }
}
