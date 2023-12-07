package br.ufrn.imd.modelo;

public class Usuario {
  private String username;
  private String senha;

  public Usuario(String username, String senha) {
    this.username = username;
    this.senha = senha;
  }

  void adicionarDiretorio(String nome) {

  }

  public String getUsername() {
    return this.username;
  }

  public String getSenha() {
    return this.senha;
  }

  public void virarPremium() {

  }
}