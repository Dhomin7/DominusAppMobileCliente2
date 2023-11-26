package DominusApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dominusapp.R;
import com.example.dominusapp.databinding.FragmentCadastrarBinding;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import DominusApp.controller.ConexaoController;
import DominusApp.view.util.Hash;
import DominusApp.view.util.MaskWatcher;
import DominusApp.viewModel.InformacoesViewModel;
import modelDominio.Cliente;
import modelDominio.Usuario;

public class CadastrarFragment extends Fragment {
    FragmentCadastrarBinding binding;
    InformacoesViewModel informacoesViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding = FragmentCadastrarBinding.inflate(inflater,container,false);
      binding.etCadastrarCPF.addTextChangedListener(new MaskWatcher("###.###.###-##"));
      return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);
        binding.bCadastrarCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etCadastrarNome.getText().toString().equals("")){
                    binding.etCadastrarNome.setError("Informe o seu nome");
                    binding.etCadastrarNome.requestFocus();
                    return;
                } else if (binding.etCadastrarLogin.getText().toString().equals("")) {
                    binding.etCadastrarLogin.setError("Informe o seu nome de usuário");
                    binding.etCadastrarLogin.requestFocus();
                    return;
                } else if (binding.etCadastrarCPF.getText().toString().equals("")) {
                    binding.etCadastrarCPF.setError("Informe o seu CPF");
                    binding.etCadastrarCPF.requestFocus();
                    return;
                } else if (binding.etCadastrarEndereco.getText().toString().equals("")) {
                    binding.etCadastrarEndereco.setError("Informe o seu endereco");
                    binding.etCadastrarEndereco.requestFocus();
                    return;
                } else if (binding.etCadastrarSenha.getText().toString().equals("")) {
                    binding.etCadastrarSenha.setError("Informe uma senha");
                    binding.etCadastrarSenha.requestFocus();
                    return;
                }

                String nome = binding.etCadastrarNome.getText().toString();
                String login = binding.etCadastrarLogin.getText().toString();
                String cpf = binding.etCadastrarCPF.getText().toString();
                String endereco = binding.etCadastrarEndereco.getText().toString();
                String senha;

                try {
                    senha = Hash.encriptar(binding.etCadastrarSenha.getText().toString(), "SHA-256");
                } catch (NoSuchAlgorithmException nsae) {
                    Log.e("DominusApp", "Erro: " + nsae.getMessage());
                    Toast.makeText(getContext(), "Erro ao efetuar cadastro.", Toast.LENGTH_SHORT).show();
                    return;
                } catch (UnsupportedEncodingException unse) {
                    Log.e("DominusApp", "Erro: " + unse.getMessage());
                    Toast.makeText(getContext(), "Erro ao efetuar cadastro.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cliente novoCliente = new Cliente(nome,login,senha,endereco,cpf);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                        boolean resultado = conexaoController.clienteInserir(novoCliente);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (resultado) {
                                    Toast.makeText(getContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(view).navigate(R.id.acao_cadastrarFragment_loginFragment);
                                } else {
                                    Toast.makeText(getContext(), "Erro ao efetuar cadastro", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                thread.start();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}