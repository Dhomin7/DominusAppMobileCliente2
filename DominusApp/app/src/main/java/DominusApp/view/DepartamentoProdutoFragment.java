package DominusApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dominusapp.R;
import com.example.dominusapp.databinding.FragmentDepartamentoProdutoBinding;
import com.example.dominusapp.databinding.FragmentOfertasBinding;

import java.util.ArrayList;

import DominusApp.adapter.DptoProdutoAdapter;
import DominusApp.adapter.OfertasAdapter;
import DominusApp.controller.ConexaoController;
import DominusApp.viewModel.InformacoesViewModel;
import modelDominio.Departamento;
import modelDominio.Produto;

public class DepartamentoProdutoFragment extends Fragment {

    FragmentDepartamentoProdutoBinding binding;

    InformacoesViewModel informacoesViewModel;

    DptoProdutoAdapter dptoProdutoAdapter;

    ArrayList<Produto> listaProdutosDepartamento;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDepartamentoProdutoBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DepartamentoProdutoFragmentArgs argumentos = DepartamentoProdutoFragmentArgs.fromBundle(getArguments());

        Departamento departamento = argumentos.getDepartamento();
        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                listaProdutosDepartamento = conexaoController.listaProdutosDepartamento(departamento);
                if( listaProdutosDepartamento != null){
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
        dptoProdutoAdapter = new DptoProdutoAdapter(listaProdutosDepartamento, trataCliqueItem);
        binding.rvProdutoDpto.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvProdutoDpto.setItemAnimator(new DefaultItemAnimator());
        binding.rvProdutoDpto.setAdapter(dptoProdutoAdapter);

    }

    DptoProdutoAdapter.ProdutoOnClickListener trataCliqueItem = new DptoProdutoAdapter.ProdutoOnClickListener(){
        @Override
        public void onClickProduto(View view, int position, Produto produto) {
            DepartamentoProdutoFragmentDirections.AcaoDepartamentoProdutoFragmentProdutoFragment acao =
                    DepartamentoProdutoFragmentDirections.acaoDepartamentoProdutoFragmentProdutoFragment(produto);
            Navigation.findNavController(view).navigate(acao);
        }
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}