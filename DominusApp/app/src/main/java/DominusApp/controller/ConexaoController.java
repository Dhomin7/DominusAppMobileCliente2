package DominusApp.controller;
import android.util.Log;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


import DominusApp.viewModel.InformacoesViewModel;
import modelDominio.Cliente;
import modelDominio.Departamento;
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
    public ArrayList<Produto> listaProdutosCompletos(){
        ArrayList<Produto> listaProdutosCompletos;

        try {
            this.informacoesViewModel.getOutputStream().writeObject("ListaProdutosCompleta");
            listaProdutosCompletos = (ArrayList<Produto>) this.informacoesViewModel.getInputStream().readObject();
        } catch(IOException ioe){
            Log.e("DominusAPP", "Erro: " + ioe.getMessage());
            listaProdutosCompletos = null;
        } catch (ClassNotFoundException classe){
            Log.e("DominusAPP", "Erro: " + classe.getMessage());
            listaProdutosCompletos = null;
        }
        return listaProdutosCompletos;
    }

    public ArrayList<Produto> listaProdutosDepartamento(Departamento departamento){
        ArrayList<Produto> listaProdutosDepartamento;
        String mensagem;
        try {
            this.informacoesViewModel.getOutputStream().writeObject("ListaProdutosDepartamento");
            mensagem = (String)this.informacoesViewModel.getInputStream().readObject();
            this.informacoesViewModel.getOutputStream().writeObject(departamento);
            listaProdutosDepartamento = (ArrayList<Produto>) this.informacoesViewModel.getInputStream().readObject();

        } catch(IOException ioe){
            Log.e("DominusAPP", "Erro: " + ioe.getMessage());
            listaProdutosDepartamento = null;
        } catch (ClassNotFoundException classe){
            Log.e("DominusAPP", "Erro: " + classe.getMessage());
            listaProdutosDepartamento = null;
        }
        return listaProdutosDepartamento;
    }

    public ArrayList<Departamento> listaDepartamento(){
        ArrayList<Departamento> listaDepartamento;

        try {
            this.informacoesViewModel.getOutputStream().writeObject("ListaDepartamentos");
            listaDepartamento = (ArrayList<Departamento>) this.informacoesViewModel.getInputStream().readObject();
        } catch(IOException ioe){
            Log.e("DominusAPP", "Erro: " + ioe.getMessage());
            listaDepartamento = null;
        } catch (ClassNotFoundException classe){
            Log.e("DominusAPP", "Erro: " + classe.getMessage());
            listaDepartamento = null;
        }
        return listaDepartamento;
    }

    public boolean clienteInserir(Cliente cliente) {
        boolean resultado;

        try {
            this.informacoesViewModel.getOutputStream().writeObject("ClienteInserir");
            String msg = (String) this.informacoesViewModel.getInputStream().readObject();
            this.informacoesViewModel.getOutputStream().writeObject(cliente);
            resultado = (boolean) this.informacoesViewModel.getInputStream().readObject();
        } catch (IOException ioe) {
            Log.e("DominusApp", "Erro: "+ ioe.getMessage());
            resultado = false;
        } catch (ClassNotFoundException cne) {
            Log.e("DominusApp", "Erro: "+ cne.getMessage());
            resultado = false;
        }
        return resultado;
    }

    public int recuperarSenha(String emailDest) {
        int codRecuperacao;

        try {
            this.informacoesViewModel.getOutputStream().writeObject("RecuperarSenha");
            String msg = (String) this.informacoesViewModel.getInputStream().readObject();
            this.informacoesViewModel.getOutputStream().writeObject(emailDest);
            codRecuperacao = (int) this.informacoesViewModel.getInputStream().readObject();
        } catch (IOException ioe) {
            Log.e("DominusApp", "Erro: "+ ioe.getMessage());
            codRecuperacao = -1;
        } catch (ClassNotFoundException cne) {
            Log.e("DominusApp", "Erro: "+ cne.getMessage());
            codRecuperacao = -1;
        }
        return codRecuperacao;
    }

    }
