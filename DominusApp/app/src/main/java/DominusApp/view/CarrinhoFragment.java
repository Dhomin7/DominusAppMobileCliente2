package DominusApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dominusapp.R;
import com.example.dominusapp.databinding.FragmentCadastrarBinding;
import com.example.dominusapp.databinding.FragmentCarrinhoBinding;
import com.example.dominusapp.databinding.FragmentDptoBinding;

public class CarrinhoFragment extends Fragment {

    FragmentCarrinhoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentCarrinhoBinding.inflate(inflater,container,false);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}