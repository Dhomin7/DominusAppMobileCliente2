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
import com.example.dominusapp.databinding.FragmentRecuperarSenhaBinding;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import DominusApp.controller.ConexaoController;
import DominusApp.viewModel.InformacoesViewModel;

public class RecuperarSenhaFragment extends Fragment {
    FragmentRecuperarSenhaBinding binding;
    InformacoesViewModel informacoesViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecuperarSenhaBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);

        binding.btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etEmailRecup.getText().toString().equals("")) {
                    binding.etEmailRecup.setError("Informe o email!");
                    binding.etEmailRecup.requestFocus();
                    return;
                }

                String email = binding.etEmailRecup.getText().toString();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                        int codRecup = conexaoController.recuperarSenha(email);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (codRecup != -1) {
                                    DominusApp.view.RecuperarSenhaFragmentDirections.AcaoRecuperarSenhaFragmentCodRecuperacaoFragment acao =
                                            RecuperarSenhaFragmentDirections.acaoRecuperarSenhaFragmentCodRecuperacaoFragment(codRecup, email);
                                    Navigation.findNavController(view).navigate(acao);
                                } else {
                                    Toast.makeText(getContext(), "Erro ao enviar email.", Toast.LENGTH_SHORT).show();
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
    public void onDestroyView() {
        super.onDestroyView();
    }
}
