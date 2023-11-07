package DominusApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dominusapp.R;
import com.example.dominusapp.databinding.FragmentLoginBinding;

import DominusApp.controller.ConexaoController;
import DominusApp.viewModel.InformacoesViewModel;
import modelDominio.Usuario;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    InformacoesViewModel informacoesViewModel;
    Usuario usuarioLogado;
       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);
        binding.bLoginSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!binding.etLoginUsuario.getText().toString().equals("")){
                if(!binding.etLoginSenha.getText().toString().equals("")){
                    String usuario = binding.etLoginUsuario.getText().toString();
                    String senha = binding.etLoginSenha.getText().toString();

                    usuarioLogado = new Usuario(usuario,senha);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                        ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                        usuarioLogado = conexaoController.efetuarLogin(usuarioLogado);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(usuarioLogado!=null){
                                    informacoesViewModel.inicializaUsuarioLogado(usuarioLogado);
                                    Navigation.findNavController(view).navigate(R.id.acao_loginFragment_menuFragment);
                                }else{
                                    Toast.makeText(getContext(), "Erro, Usuário e/ou senha inválidos", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        }
                    });
                    thread.start();
                }else {
                    binding.etLoginSenha.setError("Erro: Informe a senha");
                    binding.etLoginSenha.requestFocus();
                }
            }else{
                binding.etLoginUsuario.setError("Erro: Informe a usuário");
                binding.etLoginUsuario.requestFocus();
            }

            }
        });
        binding.bLoginCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpaCampos();
            }
        });
    }
    public void limpaCampos() {
        binding.etLoginUsuario.setText("");
        binding.etLoginSenha.setText("");
    }

        @Override
        public void onDestroyView () {
            super.onDestroyView();
            binding = null;
        }
    }