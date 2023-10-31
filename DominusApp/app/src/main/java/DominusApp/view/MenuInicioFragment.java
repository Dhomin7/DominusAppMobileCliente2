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
import com.example.dominusapp.databinding.FragmentMenuInicioBinding;

public class MenuInicioFragment extends Fragment {
    FragmentMenuInicioBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuInicioBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.bMenuLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.acao_menuInicioFragment_loginFragment);
            }
        });
        binding.bMenuCadastroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.acao_menuInicioFragment_cadastrarFragment);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}