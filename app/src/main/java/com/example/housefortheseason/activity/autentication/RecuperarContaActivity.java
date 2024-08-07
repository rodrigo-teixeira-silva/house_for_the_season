package com.example.housefortheseason.activity.autentication;

import static com.example.housefortheseason.R.id.progressBar1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.housefortheseason.R;
import com.example.housefortheseason.helper.FirebaseHelper;

public class RecuperarContaActivity extends AppCompatActivity {

    private EditText edit_email;
    private ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperar_conta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            iniciaComponentes();

            configCliques();

            return insets;
        });
    }

    public void validaDados(View view) {
        String email = edit_email.getText().toString();

        if (!email.isEmpty()) {

            progressBar1.setVisibility(View.VISIBLE);

            recuperarSenha(email);

        } else {
            edit_email.requestFocus();
            edit_email.setError("Digite o seu e-mail.");
        }
    }

    private void recuperarSenha(String email) {
        FirebaseHelper.getAuth().sendPasswordResetEmail(
                email
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "E-mail enviado com sucesso!", Toast.LENGTH_SHORT).show();

                progressBar1.setVisibility(View.GONE);
            } else {
                String erro = task.getException().getMessage();
                Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
            }
        });
        progressBar1.setVisibility(View.GONE);
    }

    private void configCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
    }

    private void iniciaComponentes() {
        edit_email = findViewById(R.id.edit_email);
        progressBar1 = findViewById(R.id.progressBar1);

        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Recuperar conta");
    }
}