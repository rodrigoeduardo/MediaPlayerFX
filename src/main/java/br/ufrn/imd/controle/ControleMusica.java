package br.ufrn.imd.controle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.utils.Utils;

public class ControleMusica {
    private static ControleMusica instancia;
    public List<Musica> musicasCadastradas;

    public static ControleMusica getInstancia() {
        if (instancia == null) instancia = new ControleMusica();
        return instancia;
    }
    private ControleMusica() {
        this.musicasCadastradas = obterMusicasCadastradas();
    }

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

    public void removerMusica(Musica musica) {
        this.musicasCadastradas.remove(musica);
        try {
            Utils.removerPorNome("db/musicas.txt", musica.getNome());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public List<Musica> getMusicasCadastradas() {
        return musicasCadastradas;
    }
}
