package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * A classe UsuarioPremium representa um usuário premium que herda da classe Usuario.
 * Possui a funcionalidade especial de criar e remover playlists.
 */
public class UsuarioPremium extends Usuario {

  /**
   * Lista de playlists do usuário premium.
   */
  private List<Playlist> playlists;

  /**
   * Construtor da classe UsuarioPremium.
   * @param username Nome de usuário do usuário premium.
   * @param senha Senha do usuário premium.
   */
  public UsuarioPremium(String username, String senha) {
    super(username, senha);
    this.playlists = new ArrayList<>();
  }

  /**
   * Converte um usuário premium para um usuário gratuito.
   */
  public void virarFree() {

  }

  /**
   * Adiciona uma playlist à lista de playlists do usuário premium.
   * @param playlist Playlist a ser adicionada.
   */
  public void adicionarPlaylist(Playlist playlist) {
    this.playlists.add(playlist);
  }

  /**
   * Remove uma playlist da lista de playlists do usuário premium.
   * @param playlist Playlist a ser removida.
   */
  public void removerPlaylist(Playlist playlist) {
    this.playlists.remove(playlist);
  }

  /**
   * Obtém a lista de playlists do usuário premium.
   * @return Lista de playlists do usuário premium.
   */
  public List<Playlist> getPlaylists() {
    return this.playlists;
  }

  /**
   * Define a lista de playlists do usuário premium.
   * @param playlists A nova lista de playlists do usuário premium.
   */
  public void setPlaylists(List<Playlist> playlists) {
    this.playlists = playlists;
  }
}