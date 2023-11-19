package DominusApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dominusapp.R;
import com.example.dominusapp.databinding.FragmentCadastrarBinding;

import DominusApp.view.util.MaskWatcher;
import modelDominio.Cliente;
import modelDominio.Usuario;

public class CadastrarFragment extends Fragment {
    FragmentCadastrarBinding binding;

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
                String senha = binding.etCadastrarSenha.getText().toString();

                Cliente novoCliente = new Cliente(nome,login,senha,endereco,cpf);

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}