package br.ufrn.imd.controle;

import java.io.File;
import java.io.IOException;

import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.modelo.Usuario;
import br.ufrn.imd.modelo.UsuarioPremium;
import br.ufrn.imd.utils.Utils;

/**
 * A classe ControlePlaylist é responsável por gerenciar a criação, remoção e alteração de instâncias da classe
 * Playlist e arquivos de playlist.
 */
public class ControlePlaylist {

    /**
     * Adiciona uma nova playlist para um usuário premium e cria um arquivo correspondente.
     * @param nome O nome da nova playlist.
     * @param usuarioLogado O usuário logado que está criando a playlist.
     */
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

    /**
     * Remove uma playlist de um usuário premium e o arquivo correspondente.
     * @param playlist A playlist a ser removida.
     * @param usuarioLogado O usuário logado que está removendo a playlist.
     */
    public void removerPlaylist(String playlist, Usuario usuarioLogado) {
        if (usuarioLogado instanceof UsuarioPremium){
            try {
                removerPlaylistArquivo(playlist, usuarioLogado);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            return;
        }

    }

    /**
     * Adiciona uma música a uma playlist de um usuário premium e atualiza o arquivo correspondente.
     * @param playlist A playlist à qual a música será adicionada.
     * @param musica A música a ser adicionada à playlist.
     * @param usuarioLogado O usuário logado que está adicionando a música à playlist.
     */
    public void adicionarMusicaNaPlaylist(Playlist playlist, Musica musica, Usuario usuarioLogado){
        if (usuarioLogado instanceof UsuarioPremium){
            ControleMusica controlerMusica = ControleMusica.getInstancia();
//            for (Musica musicaCadastrada : controlerMusica.obterMusicasCadastradas()){
//                if (musica.equals(musicaCadastrada)){
//                    if (playlist.getMusicas().indexOf(musica) == -1){
                        playlist.adicionarMusica(musica);
                        try {
                            Utils.escreverLinhaArquivo("db/playlist_" + playlist.getNome() + "_" + usuarioLogado.getUsername() + ".txt",
                            musica.getNome() + "," + musica.getArtista() + "," + musica.getDiretorio());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
//                    }
//                }
//            }
//            System.out.println("Música não cadastrada no diretório ou já presente na playlist");
        } else {
            return;
        }
    }

    /**
     * Cria um arquivo no molde playlist_nomePlaylist_usuario.txt no db para armazenar os dados da playlist.
     * @param nomePlaylist O nome da playlist.
     * @param usuario O usuário associado à playlist.
     * @throws IOException Se houver um problema ao criar o arquivo.
     */
    private static void criarArquivoPlaylist(String nomePlaylist, Usuario usuario) throws IOException{
        File file = new File("db/playlist_" + nomePlaylist + "_" + usuario.getUsername() + ".txt");

        if (file.createNewFile()) {
            System.out.println("Playlist " + nomePlaylist + " criada com sucesso");
        } else {
            System.out.println("A playlist " + nomePlaylist + " já existe");
        }
    }

    /**
     * Remove um arquivo no molde playlist_nomePlaylist_usuario.txt do db.
     * @param nomePlaylist A playlist cujo arquivo deve ser removido.
     * @param usuario O usuário associado à playlist.
     * @throws IOException Se houver um problema ao remover o arquivo.
     */
    private static void removerPlaylistArquivo(String nomePlaylist, Usuario usuario) throws IOException{
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
