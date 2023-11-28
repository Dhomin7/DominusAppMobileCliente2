package DominusApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dominusapp.R;
import com.example.dominusapp.databinding.FragmentUsuarioBinding;

import modelDominio.Usuario;


public class UsuarioFragment extends Fragment {

    FragmentUsuarioBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     binding = FragmentUsuarioBinding.inflate(inflater,container,false);
     return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UsuarioFragmentArgs argumentos = UsuarioFragmentArgs.fromBundle(getArguments());
        Usuario usuario = argumentos.getUsuario();

        binding.tvNomeUsuario.setText(usuario.getNome());
        binding.tvEmailUsuario.setText(usuario.getLogin());
        binding.tvEnderecoUsuario.setText(usuario.getEndereco());

       // if (usuario.){

        //}

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}