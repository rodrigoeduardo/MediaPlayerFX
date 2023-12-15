package br.ufrn.imd.modelo;

/**
 * A classe Usuario representa um usuário com username e senha.
 */
public class Usuario {

  /**
   * Username do usuário.
   */
  private String username;

  /**
   * Senha do usuário.
   */
  private String senha;

  /**
   * Construtor da classe Usuario.
   * @param username O username do usuário.
   * @param senha Senha do usuário.
   */
  public Usuario(String username, String senha) {
    this.username = username;
    this.senha = senha;
  }

  /**
   * Obtém o username do usuário.
   * @return Username do usuário.
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Obtém a senha do usuário.
   * @return A senha do usuário.
   */
  public String getSenha() {
    return this.senha;
  }

  @Override
  public String toString(){
    return getUsername();
  }
}