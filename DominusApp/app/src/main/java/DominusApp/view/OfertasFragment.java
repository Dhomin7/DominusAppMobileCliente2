package DominusApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bikeshopclientemobile.R;
import com.example.bikeshopclientemobile.adapter.BikeAdapter;
import com.example.bikeshopclientemobile.controller.ConexaoController;
import com.example.bikeshopclientemobile.databinding.FragmentVisualizacaoBikeBinding;
import com.example.bikeshopclientemobile.viewModel.InformacoesViewModel;
import com.example.dominusapp.databinding.FragmentOfertasBinding;

import java.util.ArrayList;

import DominusApp.adapter.OfertasAdapter;
import DominusApp.controller.ConexaoController;
import DominusApp.viewModel.InformacoesViewModel;
import modelDominio.Bike;
import modelDominio.Produto;

public class VisualizacaoBikeFragment extends Fragment {
    FragmentOfertasBinding binding;
    OfertasAdapter ofertasAdapter;

    InformacoesViewModel informacoesViewModel;
    ArrayList<Produto> listaProdutos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_visualizacao_bike, container, false);
        binding = FragmentOfertasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //obtendo a instancia do viewModel
        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);
        //criando a thread para obtencao da lista de bikes
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                listaProdutos = conexaoController.bikeLista();
                //testando o resultado
                if (listaBikes != null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            atualizaListagem();
                        }
                    });
                }
            }
        });
        thread.start();
    }

    public void atualizaListagem() {

        bikeAdapter = new BikeAdapter(listaBikes, trataCliqueItem);
        binding.rvVisualizaBikes.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvVisualizaBikes.setItemAnimator(new DefaultItemAnimator());
        binding.rvVisualizaBikes.setAdapter(bikeAdapter);
    }

    BikeAdapter.BikeOnClickListener trataCliqueItem = new BikeAdapter.BikeOnClickListener() {
        @Override
        public void onClickBike(View view, int position, Bike bike) {
            Toast.makeText(getContext(), "Modelo: " + bike.getModelo() + ", Marca: " + bike.getMarca().getNomeMarca() + ", Preço: " + bike.getPreco() + ", Data: " + bike.getDataLancamento(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}