package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.List;

public class UsuarioPremium extends Usuario {
  private List<Playlist> playlists;

  public UsuarioPremium(String username, String senha) {
    super(username, senha);
    this.playlists = new ArrayList<>();
  }

  public void virarFree() {

  }

  public void adicionarPlaylist(Playlist playlist) {
    this.playlists.add(playlist);
  }

  public void removerPlaylist(Playlist playlist) {
    this.playlists.remove(playlist);
  }

   public List<Playlist> getPlaylists() {
    return this.playlists;
  }

  public void setPlaylists(List<Playlist> playlists) {
    this.playlists = playlists;
  }
}