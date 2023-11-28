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
import com.example.dominusapp.databinding.FragmentCodRecuperacaoBinding;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class CodRecuperacaoFragment extends Fragment {
    FragmentCodRecuperacaoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCodRecuperacaoBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CodRecuperacaoFragmentArgs args = CodRecuperacaoFragmentArgs.fromBundle(getArguments());
        int codRecuperacao = args.getCodRecuperacao();
        String email = args.getEmail();

        binding.btVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etCodRecuperacao.getText().toString().equals("")) {
                    binding.etCodRecuperacao.setError("Informe o código!");
                    binding.etCodRecuperacao.requestFocus();
                    return;
                }

                if (Integer.parseInt(binding.etCodRecuperacao.getText().toString()) == (codRecuperacao)) {
                    CodRecuperacaoFragmentDirections.AcaoCodRecuperacaoFragmentAlterarSenhaRecupFragment acao =
                            CodRecuperacaoFragmentDirections.acaoCodRecuperacaoFragmentAlterarSenhaRecupFragment(email);
                    Navigation.findNavController(view).navigate(acao);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void dump(@NonNull String prefix, @Nullable FileDescriptor fd, @NonNull PrintWriter writer, @Nullable String[] args) {
        super.dump(prefix, fd, writer, args);
    }
}