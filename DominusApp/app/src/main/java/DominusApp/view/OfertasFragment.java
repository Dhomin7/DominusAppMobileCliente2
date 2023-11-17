package DominusApp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dominusapp.R;
import com.example.dominusapp.databinding.FragmentOfertasBinding;

import java.util.ArrayList;

import DominusApp.adapter.OfertasAdapter;
import DominusApp.controller.ConexaoController;
import DominusApp.viewModel.InformacoesViewModel;
import modelDominio.Produto;

public class OfertasFragment extends Fragment {

    FragmentOfertasBinding binding;

    InformacoesViewModel informacoesViewModel;

    OfertasAdapter ofertasAdapter;

    ArrayList<Produto> listaProdutosCompletos;


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOfertasBinding.inflate(inflater,container,false);
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
                listaProdutosCompletos = conexaoController.listaProdutosCompletos();
                if( listaProdutosCompletos != null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() { atualizaListagem();}
                    });
                }
            }
        });
        thread.start();
    }

    public void atualizaListagem(){
        ofertasAdapter = new OfertasAdapter(listaProdutosCompletos, trataCliqueItem);
        binding.rvOfertas1.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvOfertas1.setItemAnimator(new DefaultItemAnimator());
        binding.rvOfertas1.setAdapter(ofertasAdapter);

    }

    OfertasAdapter.ProdutoOnClickListener trataCliqueItem = new OfertasAdapter.ProdutoOnClickListener(){
        @Override
        public void onClickProduto(View view, int position, Produto produto) {
           OfertasFragmentDirections.AcaoOfertasFragmentProdutoFragment acao =
                   OfertasFragmentDirections.acaoOfertasFragmentProdutoFragment(produto);
           Navigation.findNavController(view).navigate(acao);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}