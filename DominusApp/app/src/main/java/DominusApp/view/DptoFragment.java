package DominusApp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.dominusapp.databinding.FragmentDptoBinding;

import java.util.ArrayList;

import DominusApp.adapter.DptoAdapter;

import DominusApp.controller.ConexaoController;
import DominusApp.viewModel.InformacoesViewModel;
import modelDominio.Departamento;



public class DptoFragment extends Fragment {
    InformacoesViewModel informacoesViewModel;
    DptoAdapter dptoAdapter;
    ArrayList<Departamento> listaDpto;
    FragmentDptoBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDptoBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle("");
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                listaDpto = conexaoController.listaDepartamento();
                if( listaDpto != null){
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
        dptoAdapter = new DptoAdapter(listaDpto, trataCliqueItem);
        binding.rvDpto.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDpto.setItemAnimator(new DefaultItemAnimator());
        binding.rvDpto.setAdapter(dptoAdapter);

    }

    DptoAdapter.DepartamentoOnClickListener trataCliqueItem = new DptoAdapter.DepartamentoOnClickListener() {
        @Override
        public void onClickDepartamento(View view, int position, Departamento departamento) {

            DominusApp.view.DptoFragmentDirections.AcaoDptoFragmentDepartamentoProdutoFragment acao =
                    DptoFragmentDirections.acaoDptoFragmentDepartamentoProdutoFragment(departamento);
            Navigation.findNavController(view).navigate(acao);

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}