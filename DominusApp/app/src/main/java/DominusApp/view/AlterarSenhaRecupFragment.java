package DominusApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dominusapp.R;
import com.example.dominusapp.databinding.FragmentAlterarSenhaRecupBinding;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import DominusApp.controller.ConexaoController;
import DominusApp.view.util.Hash;
import DominusApp.viewModel.InformacoesViewModel;
import modelDominio.Usuario;

public class AlterarSenhaRecupFragment extends Fragment {
    FragmentAlterarSenhaRecupBinding binding;
    InformacoesViewModel informacoesViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle("");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAlterarSenhaRecupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlterarSenhaRecupFragmentArgs args = AlterarSenhaRecupFragmentArgs.fromBundle(getArguments());
        String email = args.getEmail();
        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);

        binding.btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etNovaSenha.getText().toString().equals("")){
                    binding.etNovaSenha.setError("Informe a senha!");
                    binding.etNovaSenha.requestFocus();
                    return;
                }
                if (binding.etConfirmarSenha.getText().toString().equals("")){
                    binding.etConfirmarSenha.setError("Confirme a senha!");
                    binding.etConfirmarSenha.requestFocus();
                    return;
                }

                if (binding.etNovaSenha.getText().toString().equals(binding.etConfirmarSenha.getText().toString())){
                    String senha = binding.etNovaSenha.getText().toString();

                    try {
                        senha = Hash.encriptar(senha, "SHA-256");
                    } catch (NoSuchAlgorithmException nsae) {
                        Log.e("DominusApp", "Erro: " + nsae.getMessage());
                        Toast.makeText(getContext(), "Erro ao efetuar login.", Toast.LENGTH_SHORT).show();
                        return;
                    } catch (UnsupportedEncodingException unse) {
                        Log.e("DominusApp", "Erro: " + unse.getMessage());
                        Toast.makeText(getContext(), "Erro ao efetuar login.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Usuario usr = new Usuario(email, senha);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                            boolean resultado = conexaoController.alterarSenhaRecup(usr);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (resultado) {
                                        Navigation.findNavController(view).navigate(R.id.acao_alterarSenhaRecupFragment_loginFragment);
                                    } else {
                                        Toast.makeText(getContext(), "Erro ao alterar senha.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                    thread.start();
                } else {
                    Toast.makeText(getContext(), "As senhas informadas não conferem.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}