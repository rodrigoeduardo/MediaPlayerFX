package br.ufrn.imd.controle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.utils.Utils;

/**
 * A classe ControleMusica é responsável por gerenciar o cadastro e remoção de músicas no arquivo musicas.txt.
 * Ela também mantém uma lista de músicas cadastradas, a qual é alterada a partir dos métodos da classe.
 */
public class ControleMusica {

    /**
     * Instância da classe ControleMusica.
     */
    private static ControleMusica instancia;

    /**
     * Lista de músicas cadastradas.
     */
    public List<Musica> musicasCadastradas;

    /**
     * @return A instância da classe ControleMusica.
     */
    public static ControleMusica getInstancia() {
        if (instancia == null) instancia = new ControleMusica();
        return instancia;
    }

    /**
     * Construtor privado que inicializa a lista de músicas.
     */
    private ControleMusica() {
        this.musicasCadastradas = obterMusicasCadastradas();
    }

    /**
     * Cadastra uma nova música em musicas.txt e na lista de músicas cadastradas, caso exista arquivo com
     * nome correspondente no diretório de arquivos .mp3 informado.
     * @param nome O nome da música.
     * @param artista O nome do artista da música.
     * @param diretorio O diretório onde a música está localizada.
     */
    public void cadastrarMusica(String nome, String artista, String diretorio) {
        File mp3File = new File("db/musicasMP3/" + nome + ".mp3");
        if (!mp3File.exists()){
            System.out.println("Música " + nome + " não existente no diretório");
            return;
        }

        this.musicasCadastradas.add(new Musica(nome, artista, diretorio));

        try{
            Utils.escreverLinhaArquivo("db/musicas.txt", (nome + "," + artista));
        } catch (IOException e){
            e.printStackTrace();
            return;
        }
    }

    /**
     * Remove uma música de musicas.txt e da lista de músicas cadastradas.
     * @param musica A instância da classe Musica a ser removida.
     */
    public void removerMusica(Musica musica) {
        this.musicasCadastradas.remove(musica);
        try {
            Utils.removerPorNome("db/musicas.txt", musica.getNome());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtém a lista de músicas cadastradas lendo os dados de musicas.txt.
     * @return Lista de músicas cadastradas.
     */
    public List<Musica> obterMusicasCadastradas() {
        List<String> musicas;

        try {
            musicas = Utils.lerArquivo("db/musicas.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        List<Musica> musicasCadastradas = new ArrayList<>();

        for (String dadosMusica : musicas){
            String[] dadosSeparados = dadosMusica.split(",");

            String nomeMusica = dadosSeparados[0];
            String artistaMusica = dadosSeparados[1];
            String diretorioMusica = dadosSeparados[2];

            musicasCadastradas.add(new Musica(nomeMusica, artistaMusica, diretorioMusica));
        }

        return musicasCadastradas;
    }

    /**
     * Obtém a lista atual de músicas cadastradas.
     * @return Lista de músicas cadastradas.
     */
    public List<Musica> getMusicasCadastradas() {
        return musicasCadastradas;
    }
}
