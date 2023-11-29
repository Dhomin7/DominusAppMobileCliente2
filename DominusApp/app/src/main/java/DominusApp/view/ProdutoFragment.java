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

import com.example.dominusapp.databinding.FragmentProdutoBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import DominusApp.controller.ConexaoController;
import DominusApp.viewModel.InformacoesViewModel;
import modelDominio.Cliente;
import modelDominio.ItensVenda;
import modelDominio.Produto;
import modelDominio.Usuario;
import modelDominio.Venda;

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
        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);
        Produto produto = argumentos.getProduto();

        binding.tvNomeProduto.setText(produto.getNome());
        binding.tvNomeMarcaProduto.setText(produto.getMarca().getNomeMarca());
        binding.tvDptoProduto.setText(produto.getDepartamento().getNomeDpto());
        binding.tvPrecoProduto.setText(produto.getPrecoString());
        binding.tvVendedorProduto.setText(produto.getVendedor().getNome());
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

        binding.bComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ItensVenda> listaItensVenda= new ArrayList<>();
                int quantidade = 1;
                ItensVenda itemVenda = new ItensVenda(produto, quantidade, produto.getPreco(), produto.getPreco() * quantidade);
                listaItensVenda.add(itemVenda);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date dataVenda = Calendar.getInstance().getTime();

                float valorVenda = 0;
                for (ItensVenda itemVendaValor: listaItensVenda) {
                    valorVenda += itemVendaValor.getValorTotal();
                }

                Usuario comprador = informacoesViewModel.getUsuario();

                Venda novaVenda = new Venda(dataVenda, valorVenda, listaItensVenda, comprador);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                        boolean resultado = conexaoController.vendaInserir(novaVenda);
                        
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (resultado)
                                    Toast.makeText(getContext(), "Compra cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getContext(), "Erro ao cadastrar compra.", Toast.LENGTH_SHORT).show();
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
        binding = null;
    }
}