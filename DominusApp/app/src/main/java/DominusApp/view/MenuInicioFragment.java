package DominusApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dominusapp.R;
import com.example.dominusapp.databinding.FragmentMenuInicioBinding;

import DominusApp.controller.ConexaoController;
import DominusApp.viewModel.InformacoesViewModel;

public class MenuInicioFragment extends Fragment {
    FragmentMenuInicioBinding binding;
    InformacoesViewModel informacoesViewModel;
    boolean resultado;
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
        binding = FragmentMenuInicioBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                resultado = conexaoController.criaConexaoServidor("10.0.2.2", 12345);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resultado == true) {
                            Toast.makeText(getContext(), "Conexão efetuada", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Erro: Conexão não efetuada", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        thread.start();

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