package br.ufrn.imd.modelo;

/**
 * A classe Musica representa uma música com nome, artista e diretório do arquivo.
 */
public class Musica {

  /**
   * Nome da música.
   */
  private String nome;

  /**
   * Artista da música.
   */
  private String artista;

  /**
   * Diretório do arquivo da música.
   */
  private String diretorio;

  /**
   * Construtor da classe Musica.
   * @param nome O nome da música.
   * @param artista O nome do artista da música.
   * @param diretorio O diretório do arquivo da música.
   */
  public Musica(String nome, String artista, String diretorio) {
    this.nome = nome;
    this.artista = artista;
    this.diretorio = diretorio;
  }

  /**
   * Obtém o nome da música.
   * @return Nome da música.
   */
  public String getNome() {
    return this.nome;
  }

  /**
   * Define um novo nome para a música.
   * @param novoNome O novo nome da música.
   */
  public void setNome(String novoNome) {
    this.nome = novoNome;
  }

  /**
   * Obtém o nome do artista da música.
   * @return Nome do artista da música.
   */
  public String getArtista() {
    return this.artista;
  }

  /**
   * Define um novo artista para a música.
   * @param novoArtista O novo nome do artista da música.
   */
  public void setArtista(String novoArtista) {
    this.artista = novoArtista;
  }

  /**
   * Obtém o diretório do arquivo da música.
   * @return Diretório do arquivo da música.
   */
  public String getDiretorio() {
    return this.diretorio;
  }

  /**
   * Define um novo diretório para o arquivo da música.
   * @param novoDiretorio Novo diretório do arquivo da música.
   */
  public void setDiretorio(String novoDiretorio) {
    this.diretorio = novoDiretorio;
  }

  @Override
  public String toString(){
    return getNome();
  }
}
