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

import com.example.dominusapp.databinding.FragmentProdutoBinding;

import java.util.ArrayList;

import DominusApp.viewModel.InformacoesViewModel;
import modelDominio.Produto;

public class ProdutoFragment extends Fragment {
    FragmentProdutoBinding binding;

    InformacoesViewModel informacoesViewModel;

    ArrayList<Produto> listaCarrinho;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    binding = FragmentProdutoBinding.inflate(inflater,container,false);
    return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProdutoFragmentArgs argumentos = ProdutoFragmentArgs.fromBundle(getArguments());

        Produto produto = argumentos.getProduto();

        binding.tvNomeProduto.setText(produto.getNome());
        binding.tvNomeMarcaProduto.setText(produto.getMarca().getNomeMarca());
        binding.tvDptoProduto.setText(produto.getDepartamento().getNomeDpto());
        binding.tvPrecoProduto.setText(produto.getPrecoString());
        binding.tvVendedorProduto.setText(produto.getVendedor().getNome()); //Não foi
        binding.tvDescricaoProduto.setText(produto.getDescricao());

        binding.bAdicionarCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"Produto adicionado ao carrinho",Toast.LENGTH_SHORT).show();
            }
        });
        binding.bCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}