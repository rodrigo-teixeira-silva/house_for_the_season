package com.example.housefortheseason.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.housefortheseason.R;
import com.example.housefortheseason.adapter.AdapterAnuncios;
import com.example.housefortheseason.helper.FirebaseHelper;
import com.example.housefortheseason.model.Anuncio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeusAnunciosActivity extends AppCompatActivity {

    private List<Anuncio> anuncioList = new ArrayList<>();

    private ProgressBar progressBar5;
    private TextView text_info;
    private RecyclerView rv_anuncios;
    private AdapterAnuncios adapterAnuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meus_anuncios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            iniciaComponentes();

            configRv();

            recuperaAnuncios();

            return insets;
        });
    }

    private void configRv() {
        rv_anuncios.setLayoutManager(new LinearLayoutManager(this));
        rv_anuncios.setHasFixedSize(true);
        adapterAnuncios = new AdapterAnuncios(anuncioList);
        rv_anuncios.setAdapter(adapterAnuncios);
    }

    private void recuperaAnuncios() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("anuncios")
                .child(FirebaseHelper.getIdFirebase());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    anuncioList.clear();
                    for (DataSnapshot snap: snapshot.getChildren()){
                        Anuncio anuncio = snap.getValue(Anuncio.class);
                        anuncioList.add(anuncio);
                    }
                    text_info.setText("");
                } else {
                    text_info.setText("Nenhum anúncio cadastrado");
                }
                progressBar5.setVisibility(View.GONE);
                Collections.reverse(anuncioList);
                adapterAnuncios.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniciaComponentes() {
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Meus Anúncios");

        progressBar5 = findViewById(R.id.progressBar5);
        text_info = findViewById(R.id.text_info);
        rv_anuncios = findViewById(R.id.rv_anuncios);
    }
}