package com.example.housefortheseason.adapter;

import static android.os.Build.VERSION_CODES.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.housefortheseason.R;
import com.example.housefortheseason.model.Anuncio;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterAnuncios  extends RecyclerView.Adapter<adapterAnuncios.MyViewHolder> {

    private List<Anuncio> anuncioList;

    public adapterAnuncios(List<Anuncio> anuncioList) {
        this.anuncioList = anuncioList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.housefortheseason.R.layout.item_anuncio,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Anuncio anuncio = anuncioList.get(position);

        Picasso.get().load(anuncio.getUrlImagem()).into(holder.img_anuncio);
        holder.text_titulo.setText(anuncio.getTitulo());
        holder.text_descricao.setText(anuncio.getDescricao());
        holder.text_data.setText("");

    }

    @Override
    public int getItemCount() {
        return anuncioList.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{

        ImageView img_anuncio;
        TextView text_titulo, text_descricao, text_data;

            public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_anuncio = itemView.findViewById(com.example.housefortheseason.R.id.img_anuncio);
            text_titulo = itemView.findViewById(com.example.housefortheseason.R.id.text_titulo);
            text_descricao = itemView.findViewById(com.example.housefortheseason.R.id.text_descrição);
            text_data = itemView.findViewWithTag(com.example.housefortheseason.R.id.text_data);

        }
    }
}
