package DominusApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dominusapp.R;
import com.example.dominusapp.databinding.FragmentMenuBinding;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import DominusApp.view.MenuFragmentDirections;
import modelDominio.Produto;
import modelDominio.Usuario;


public class MenuFragment extends Fragment {

    FragmentMenuBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentMenuBinding.inflate(inflater,container,false);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MenuFragmentArgs argumentos = MenuFragmentArgs.fromBundle(getArguments());

        Usuario usuario = argumentos.getUsuario();

        binding.bMenuOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.acao_menuFragment_ofertasFragment);
            }
        });
        binding.bMenuDpto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.acao_menuFragment_dptoFragment);

            }
        });
        binding.bMenuCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.acao_menuFragment_carrinhoFragment);
            }
        });
        binding.bMenuUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MenuFragmentDirections.AcaoMenuFragmentUsuarioFragment acao =
                        MenuFragmentDirections.acaoMenuFragmentUsuarioFragment(usuario);
                Navigation.findNavController(view).navigate(acao);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}