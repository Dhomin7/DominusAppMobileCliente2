package DominusApp.controller;
import android.util.Log;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


import DominusApp.viewModel.InformacoesViewModel;
import modelDominio.Produto;
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
    public ArrayList<Produto> listaProdutos(){
        ArrayList<Produto> listaProdutos;

        try {
            this.informacoesViewModel.getOutputStream().writeObject("BikeLista");
            listaProdutos = (ArrayList<Produto>) this.informacoesViewModel.getInputStream().readObject();
        } catch(IOException ioe){
            Log.e("BikeShop", "Erro: " + ioe.getMessage());
            listaProdutos = null;
        } catch (ClassNotFoundException classe){
            Log.e("BikeShop", "Erro: " + classe.getMessage());
            listaProdutos = null;
        }
        return listaProdutos;
    }
    }
