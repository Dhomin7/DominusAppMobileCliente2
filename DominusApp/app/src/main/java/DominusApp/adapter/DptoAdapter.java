package DominusApp.adapter;


import android.view.ViewGroup;


import androidx.recyclerview.widget.RecyclerView;



import android.view.LayoutInflater;
import android.view.View;

import android.annotation.SuppressLint;



import com.example.dominusapp.databinding.ItemListRowDptoBinding;

import java.util.List;

import modelDominio.Departamento;


public class DptoAdapter extends  RecyclerView.Adapter<DptoAdapter.MyViewHolder> {
    private List<Departamento> listaDepartamento;
    private DepartamentoOnClickListener departamentoOnClickListener;

    public DptoAdapter (List<Departamento> listaDepartamento, DepartamentoOnClickListener departamentoOnClickListener) {
        this.listaDepartamento = listaDepartamento;
        this.departamentoOnClickListener = departamentoOnClickListener;
    }

    @Override
    public DptoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListRowDptoBinding itemListRowDptoBinding = ItemListRowDptoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(itemListRowDptoBinding);
    }

    @Override
    public void onBindViewHolder(final DptoAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Departamento departamento = listaDepartamento.get(position);
        holder.itemListRowDptoBinding.tvNomeDpto.setText(departamento.getNomeDpto());

        if (departamentoOnClickListener != null) {
            holder.itemListRowDptoBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    departamentoOnClickListener.onClickDepartamento(holder.itemView, position, departamento);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listaDepartamento.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ItemListRowDptoBinding itemListRowDptoBinding;
        public MyViewHolder(ItemListRowDptoBinding itemListRowBinding) {
            super(itemListRowBinding.getRoot());
            this.itemListRowDptoBinding = itemListRowBinding;
        }
    }

    public interface DepartamentoOnClickListener {
        public void onClickDepartamento(View view, int position, Departamento departamento);
    }

}
