package DominusApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dominusapp.R;
import com.example.dominusapp.databinding.FragmentAlterarSenhaRecupBinding;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class AlterarSenhaRecupFragment extends Fragment {
    FragmentAlterarSenhaRecupBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAlterarSenhaRecupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                    Navigation.findNavController(view).navigate(R.id.acao_alterarSenhaRecupFragment_loginFragment);
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