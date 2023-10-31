package DominusApp.controller;
import android.util.Log;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import DominusApp.viewModel.InformacoesViewModel;
import modelDominio.Usuario;

public class ConexaoController {
        InformacoesViewModel informacoesViewModel;

    public ConexaoController(InformacoesViewModel informacoesViewModel) {
        this.informacoesViewModel = informacoesViewModel;
    }
    public boolean criaConexaoServidor(String ip, int porta) {
        boolean resultado;

        try {

            Socket socket = new Socket(ip, porta);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            this.informacoesViewModel.inicializaObjetosSocket(in, out);
            resultado = true;

        } catch (IOException ioe) {
            Log.e("DominusApp", "Erro" + ioe.getMessage());
            resultado = false;
        }

        return resultado;

    }
    /*public Usuario efetuarLogin(Usuario usuario) {
        Usuario usuarioLogado;

        try {
            out.writeObject("UsuarioEfetuarLogin");
            String msg = (String)in.readObject();
            out.writeObject(usuario);
            usuarioLogado = (Usuario)in.readObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            usuarioLogado = null;
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
            usuarioLogado = null;
        }
        return usuarioLogado;
    }*/
    public Usuario efetuarLogin(Usuario usuario) {
        Usuario usuarioLogado;
        String mensagem;

        try {
            this.informacoesViewModel.getOutputStream().writeObject("UsuarioEfetuarLogin");
            mensagem = (String) this.informacoesViewModel.getInputStream().readObject();
            this.informacoesViewModel.getOutputStream().writeObject(usuario);
            usuarioLogado = (Usuario) this.informacoesViewModel.getInputStream().readObject();
        } catch (IOException ioe) {
            Log.e("DominusApp", "Erro: " + ioe.getMessage());
            usuarioLogado = null;
        } catch (ClassNotFoundException classe) {
            Log.e("DominusApp", "Erro: " + classe.getMessage());
            usuarioLogado = null;
        }

        return usuarioLogado;
    }
}