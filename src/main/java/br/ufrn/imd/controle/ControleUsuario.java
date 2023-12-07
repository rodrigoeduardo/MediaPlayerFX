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

public class ControleUsuario {
    private List<Usuario> usuariosCadastrados;
    private Usuario usuarioLogado;

    public ControleUsuario() {
        this.usuariosCadastrados = obterUsuariosCadastrados();
        this.usuarioLogado = null;
    }

    public boolean fazerLogin(String username, String senha) {
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario.getUsername().equals(username) && usuario.getSenha().equals(senha)) {
                setUsuarioLogado(usuario);
                return true;
            }
        }
        return false;
    }

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

    public List<Usuario> getUsuariosCadastrados() {
        return usuariosCadastrados;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
    
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
