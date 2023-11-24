package DominusApp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.example.dominusapp.databinding.ItemListRowProdutodptoBinding;

import java.util.List;

import modelDominio.Produto;

public class DptoProdutoAdapter extends RecyclerView.Adapter<DptoProdutoAdapter.MyViewHolder>  {
    private List<Produto> listaProdutosDepartamento;
    private DptoProdutoAdapter.ProdutoOnClickListener produtoOnClickListener;

    public DptoProdutoAdapter (List<Produto> listaProdutosDepartamento, DptoProdutoAdapter.ProdutoOnClickListener produtoOnClickListener) {
        this.listaProdutosDepartamento = listaProdutosDepartamento;
        this.produtoOnClickListener = produtoOnClickListener;
    }

    @Override
    public DptoProdutoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListRowProdutodptoBinding itemListRowProdutodptoBinding = ItemListRowProdutodptoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DptoProdutoAdapter.MyViewHolder(itemListRowProdutodptoBinding);
    }

    @Override
    public void onBindViewHolder(final DptoProdutoAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Produto produto = listaProdutosDepartamento.get(position);
        holder.itemListRowProdutodptoBinding.tvTextoProduto.setText(produto.getNome());
        holder.itemListRowProdutodptoBinding.tvPrecoProduto.setText("R$ " + produto.getPrecoString());
        if (produtoOnClickListener != null) {
            holder.itemListRowProdutodptoBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    produtoOnClickListener.onClickProduto(holder.itemView, position, produto);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listaProdutosDepartamento.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ItemListRowProdutodptoBinding itemListRowProdutodptoBinding;
        public MyViewHolder(ItemListRowProdutodptoBinding itemListRowBinding) {
            super(itemListRowBinding.getRoot());
            this.itemListRowProdutodptoBinding = itemListRowBinding;
        }
    }

    public interface ProdutoOnClickListener {
        public void onClickProduto(View view, int position, Produto produto);
    }
}
