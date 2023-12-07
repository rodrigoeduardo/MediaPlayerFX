package br.ufrn.imd.modelo;

public class Musica {
  private String nome;
  private String artista;
  private String diretorio;

  public Musica(String nome, String artista, String diretorio) {
    this.nome = nome;
    this.artista = artista;
    this.diretorio = diretorio;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String novoNome) {
    this.nome = novoNome;
  }

  public String getArtista() {
    return this.artista;
  }

  public void setArtista(String novoArtista) {
    this.artista = novoArtista;
  }

  public String getDiretorio() {
    return this.diretorio;
  }

  public void setDiretorio(String novoDiretorio) {
    this.diretorio = novoDiretorio;
  }
}