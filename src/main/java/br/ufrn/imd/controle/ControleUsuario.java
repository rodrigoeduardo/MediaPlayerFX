package br.ufrn.imd.controle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.modelo.Usuario;
import br.ufrn.imd.modelo.UsuarioPremium;
import br.ufrn.imd.utils.Utils;

/**
 * A classe ControleUsuario é responsável por gerenciar o login e cadastro de usuários, e atualização
 * de playlists associadas a usuários premium.
 */
public class ControleUsuario {

    /**
     * Instância única da classe ControleUsuario.
     */
    private static ControleUsuario instancia;

    /**
     * Lista de usuários cadastrados.
     */
    private List<Usuario> usuariosCadastrados;

    /**
     * Usuário atualmente logado no sistema.
     */
    private Usuario usuarioLogado;

    /**
     * @return A instância da classe ControleUsuario.
     */
    public static ControleUsuario getInstancia() {
        if (instancia == null) instancia = new ControleUsuario();
        return instancia;
    }

    /**
     * Construtor privado que inicializa a lista de usuários.
     */
    private ControleUsuario() {
        this.usuariosCadastrados = obterUsuariosCadastrados();
        this.usuarioLogado = null;
    }

    /**
     * Tenta o login de um usuário com o username e senha fornecidos.
     * @param username O username do usuário.
     * @param senha A senha do usuário.
     * @return true se o login for bem sucedido, false caso contrário.
     */
    public boolean fazerLogin(String username, String senha) {
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario.getUsername().equals(username) && usuario.getSenha().equals(senha)) {
                setUsuarioLogado(usuario);
                return true;
            }
        }
        return false;
    }

    /**
     * Realiza o cadastro de um novo usuário no arquivo usuarios.txt no db.
     * @param username O username do novo usuário.
     * @param senha A senha do novo usuário.
     * @param ehPremium Indica se o novo usuário é premium ou não.
     */
    public void fazerCadastro(String username, String senha, boolean ehPremium) {
        for (Usuario usuario : this.usuariosCadastrados){
            if (usuario.getUsername() == username){
                System.out.println("Já existe um usuário com o nome: " + username);
                return;
            }
        }

        if (ehPremium)
            this.usuariosCadastrados.add(new UsuarioPremium(username, senha));
        else {
            this.usuariosCadastrados.add(new Usuario(username, senha));
        }

        try {
            Utils.escreverLinhaArquivo("db/usuarios.txt", (username + "," + senha + "," + ehPremium));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtém a lista de usuários cadastrados lendo os dados de usuarios.txt.
     * @return Lista de usuários cadastrados.
     */
    public List<Usuario> obterUsuariosCadastrados() {
        List<String> usuarios;

        try {
            usuarios = Utils.lerArquivo("db/usuarios.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        List<Usuario> usuariosCadastrados = new ArrayList<>();

        for (String DadosUsuario : usuarios){
            String[] DadosUsuarioSeparados = DadosUsuario.split(",");

            String username = DadosUsuarioSeparados[0];
            String senha = DadosUsuarioSeparados[1];
            boolean ehPremium = Boolean.parseBoolean(DadosUsuarioSeparados[2]);

            if(ehPremium){
                usuariosCadastrados.add(new UsuarioPremium(username, senha));
            } else {
                usuariosCadastrados.add(new Usuario(username, senha));
            }
        }

        return usuariosCadastrados;
    }

    /**
     * Atualiza as playlists associadas ao usuário logado.
     */
    public void atualizarPlaylistsDeUsuario(){
        List<File> playlistsArquivos = listarPlaylistsDeUsuario();

        if (usuarioLogado instanceof UsuarioPremium) {
            ((UsuarioPremium) usuarioLogado).getPlaylists().clear();

            for (File playlistArquivo : playlistsArquivos){
                String nomePlaylist = playlistArquivo.getName().split("_")[1];
                Playlist playlist = new Playlist(nomePlaylist);

                try {
                    List<String> musicas = Utils.lerArquivo("db/" + playlistArquivo.getName());
                    for (String dadosMusica : musicas){
                        String[] dadosSeparados = dadosMusica.split(",");

                        String nomeMusica = dadosSeparados[0];
                        String artistaMusica = dadosSeparados[1];
                        String diretorioMusica = dadosSeparados[2];

                        playlist.adicionarMusica(new Musica(nomeMusica, artistaMusica, diretorioMusica));
                    }
                    ((UsuarioPremium) usuarioLogado).adicionarPlaylist(playlist);;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return;
    }

    /**
     * Obtém a lista atual de usuários cadastrados.
     * @return Lista de usuários cadastrados.
     */
    public List<Usuario> getUsuariosCadastrados() {
        return usuariosCadastrados;
    }

    /**
     * Obtém o usuário atualmente logado no sistema.
     * @return Usuário logado.
     */
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    /**
     * Define o usuário logado no sistema.
     * @param usuarioLogado O usuário a ser definido como logado.
     */
    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    /**
     * Lista os arquivos de playlists no db com sufixo _usernameUsuarioLogado.txt.
     * @return Lista de arquivos de playlists associados ao usuário logado.
     */
    private List<File> listarPlaylistsDeUsuario() {
        List<File> playlistsDeUsuario = new ArrayList<>();
        File pasta = new File("db/");
 
        File[] arquivos = pasta.listFiles();

        for (File arquivo : arquivos) {
            if (arquivo.isFile() && arquivo.getName().startsWith("playlist_") && arquivo.getName().endsWith("_" + usuarioLogado.getUsername() + ".txt")) {
                playlistsDeUsuario.add(arquivo);
            }
        }
        
        return playlistsDeUsuario;
    }
}
