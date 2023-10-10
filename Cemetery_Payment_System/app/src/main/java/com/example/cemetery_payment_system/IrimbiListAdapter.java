package com.example.cemetery_payment_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IrimbiListAdapter extends RecyclerView.Adapter<IrimbiListAdapter.viewHolder> {

    List<DataIrimbi> listIrimbi;
    Context context;
    LayoutInflater inflater;
    private SelectIrimbi selectIrimbi;

    public IrimbiListAdapter(Context context, List<DataIrimbi> listIrimbi,SelectIrimbi selectIrimbi) {
        this.listIrimbi = listIrimbi;
        this.context = context;
        this.selectIrimbi = selectIrimbi;
        inflater =LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.custom_list_of_irimbi,parent,false);
        this.context = view.getContext();
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.izina.setText(listIrimbi.get(position).getIrimbiTitle());
        String id = listIrimbi.get(position).getIrimbiID();
        holder.urwego.setText(listIrimbi.get(position).getIrimbiCategory());
    }

    @Override
    public int getItemCount() {
        return listIrimbi.size();
    }

    public interface SelectIrimbi {
        void SelectIrimbi(DataIrimbi dataIrimbi);
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView izina,urwego;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            izina = itemView.findViewById(R.id.TxtIzina);
            urwego = itemView.findViewById(R.id.TxtCategory);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectIrimbi.SelectIrimbi(listIrimbi.get(getAdapterPosition()));
                    Toast.makeText(context, "Irimbi rya"+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
