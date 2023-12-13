package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * A classe Playlist representa uma lista de músicas da classe Música, com metódos de adição e remoção para tais.
 */
public class Playlist {

  /**
   * Nome da playlist.
   */
  private String nome;

  /**
   * Lista de músicas na playlist.
   */
  private List<Musica> musicas;

  /**
   * Construtor da classe Playlist.
   * @param nome Nome da playlist.
   */
  public Playlist(String nome) {
    this.nome = nome;
    this.musicas = new ArrayList<>();
  }

  /**
   * Adiciona uma música à playlist.
   * @param musica Música a ser adicionada à playlist.
   */
  public void adicionarMusica(Musica musica) {
    this.musicas.add(musica);
  }

  /**
   * Remove uma música da playlist.
   * @param musica Música a ser removida da playlist.
   */
  public void removerMusica(Musica musica) {
    this.musicas.remove(musica);
  }

  /**
   * Obtém o nome da playlist.
   * @return Nome da playlist.
   */
  public String getNome() {
    return this.nome;
  }

  /**
   * Define um novo nome para a playlist.
   * @param novoNome Novo nome da playlist.
   */
  public void setNome(String novoNome) {
    this.nome = novoNome;
  }

  /**
   * Obtém a lista de músicas na playlist.
   * @return Lista de músicas na playlist.
   */
  public List<Musica> getMusicas() {
    return this.musicas;
  }
}
