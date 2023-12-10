package br.ufrn.imd.controle;

import java.io.File;
import java.io.IOException;

import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.modelo.Usuario;
import br.ufrn.imd.modelo.UsuarioPremium;
import br.ufrn.imd.utils.Utils;

public class ControlePlaylist {
    public void adicionarPlaylist(String nome, Usuario usuarioLogado) {
        if (usuarioLogado instanceof UsuarioPremium) {
            ((UsuarioPremium) usuarioLogado).adicionarPlaylist(new Playlist(nome));
            try {
                criarArquivoPlaylist(nome, usuarioLogado);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
    }

    public void removerPlaylist(Playlist playlist, Usuario usuarioLogado) {
        if (usuarioLogado instanceof UsuarioPremium){
            ((UsuarioPremium) usuarioLogado).removerPlaylist(playlist);
            try {
                removerPlaylistArquivo(playlist, usuarioLogado);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else {
            return;
        }
        
    }

    public void adicionarMusicaNaPlaylist(Playlist playlist, Musica musica, Usuario usuarioLogado){
        if (usuarioLogado instanceof UsuarioPremium){
            ControleMusica controlerMusica = ControleMusica.getInstancia();
            for (Musica musicaCadastrada : controlerMusica.obterMusicasCadastradas()){
                if (musica.equals(musicaCadastrada)){
                    if (playlist.getMusicas().indexOf(musica) == -1){
                        playlist.adicionarMusica(musica);
                        try {
                            Utils.escreverLinhaArquivo("db/playlist_" + playlist.getNome() + "_" + usuarioLogado.getUsername() + ".txt",
                            musica.getNome() + "," + musica.getArtista() + "," + musica.getDiretorio());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
            }
            System.out.println("Música não cadastrada no diretório ou já presente na playlist");
        } else {
            return;
        }
    }

    private static void criarArquivoPlaylist(String nomePlaylist, Usuario usuario) throws IOException{
        File file = new File("db/playlist_" + nomePlaylist + "_" + usuario.getUsername() + ".txt");

        if (file.createNewFile()) {
            System.out.println("Playlist " + nomePlaylist + " criada com sucesso");
        } else {
            System.out.println("A playlist " + nomePlaylist + " já existe");
        }
    }

    private static void removerPlaylistArquivo(Playlist playlist, Usuario usuario) throws IOException{
        String nomePlaylist = playlist.getNome();
        File file = new File("db/playlist_" + nomePlaylist + "_" + usuario.getUsername() + ".txt");

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Playlist" + nomePlaylist + " removida com sucesso");
            } else {
                System.out.println("Falha ao remover playlist " + nomePlaylist);
            }
        } else {
            System.out.println("Playlist " + nomePlaylist + " não existe");
        }
        
    }
}
