package DominusApp.adapter;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dominusapp.R;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dominusapp.databinding.ItemListRowOfertas1Binding;

import java.util.List;

import modelDominio.Produto;

public class OfertasAdapter extends  RecyclerView.Adapter<OfertasAdapter.MyViewHolder> {
    private List<Produto> listaProdutosCompletos;
    private ProdutoOnClickListener produtoOnClickListener;

    public OfertasAdapter (List<Produto> listaProdutosCompletos, ProdutoOnClickListener produtoOnClickListener) {
        this.listaProdutosCompletos = listaProdutosCompletos;
        this.produtoOnClickListener = produtoOnClickListener;
    }

    @Override
    public OfertasAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListRowOfertas1Binding itemListRowOfertas1Binding = ItemListRowOfertas1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(itemListRowOfertas1Binding);
    }

    @Override
    public void onBindViewHolder(final OfertasAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Produto produto = listaProdutosCompletos.get(position);
        holder.itemListRowOfertas1Binding.tvTextoProduto.setText(produto.getNome());
        holder.itemListRowOfertas1Binding.tvPrecoProduto.setText("R$ " + produto.getPrecoString());
        if (produtoOnClickListener != null) {
            holder.itemListRowOfertas1Binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    produtoOnClickListener.onClickProduto(holder.itemView, position, produto);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listaProdutosCompletos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ItemListRowOfertas1Binding itemListRowOfertas1Binding;
        public MyViewHolder(ItemListRowOfertas1Binding itemListRowBinding) {
            super(itemListRowBinding.getRoot());
            this.itemListRowOfertas1Binding = itemListRowBinding;
        }
    }

    public interface ProdutoOnClickListener {
        public void onClickProduto(View view, int position, Produto produto);
    }

}
/*public class OfertasAdapter extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<String> datasource;
    LinearLayoutManager linearLayoutManager;
    MyRvAdapter myRvAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ofertas);
        rv = findViewById(R.id.rvOfertas1);
    }
    class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyHolder>{
        public MyRvAdapter() {

        }

        @NonNull
        @Override
        public MyRvAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull MyRvAdapter.MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
        class MyHolder extends RecyclerView.ViewHolder{
            public MyHolder(@NonNull View itemView)
        }
    }

}*/