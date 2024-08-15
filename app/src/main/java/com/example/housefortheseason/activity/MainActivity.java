package com.example.housefortheseason.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.housefortheseason.R;
import com.example.housefortheseason.activity.autentication.LoginActivity;
import com.example.housefortheseason.helper.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    private ImageButton ib_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            iniciaComponentes();

            configCliques();

            return insets;
        });
    }

    private void configCliques() {
        ib_menu.setOnClickListener(view ->
        {   PopupMenu popupMenu = new PopupMenu(this, ib_menu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_home, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(MenuItem -> {
                if (MenuItem.getItemId() == R.id.menu_filtrar) {
                    startActivity(new Intent(this, FiltrarAnuncioActivity.class));
                } else if(MenuItem.getItemId() == R.id.menu_meus_anuncios) {
                    if (FirebaseHelper.getAutenticado()) {
                        startActivity(new Intent(this, MinhaContaActivity.class));
                    } else {
                        ShowDalogLogin();
                    }

                } else {
                    if (FirebaseHelper.getAutenticado()) {
                        startActivity(new Intent(this, MinhaContaActivity.class));
                    } else {
                        ShowDalogLogin();
                    }
                }
                return true;
            });
            popupMenu.show();
        });
    }

    private void ShowDalogLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autenticação");
        builder.setMessage("Você não autenticado no aplicativo, deseja fazer isso agora ?");
        builder.setCancelable(false);
        builder.setNegativeButton("Não", ((dialog, which) -> dialog.dismiss()));
        builder.setPositiveButton("Sim", ((dialog, which) -> {
            startActivity(new Intent(this, LoginActivity.class));
        }));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void iniciaComponentes() {
        ib_menu = findViewById(R.id.ib_menu);
    }
}