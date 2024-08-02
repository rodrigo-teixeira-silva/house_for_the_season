package com.example.housefortheseason.activity.autentication;

import android.content.Intent;
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
import com.example.housefortheseason.activity.MainActivity;
import com.example.housefortheseason.helper.FirebaseHelper;
import com.example.housefortheseason.model.Usuario;

public class CriarContaActivity extends AppCompatActivity {

    private EditText edit_nome;
    private EditText edit_email;
    private EditText edit_telefone;
    private EditText edit_senha;
    private ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_criar_conta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            configCliques();

            iniciaComponentes();

            return insets;
        });
    }

    public void validaDados(View view) {
        String nome = edit_nome.getText().toString();
        String email = edit_email.getText().toString();
        String telefone = edit_telefone.getText().toString();
        String senha = edit_senha.getText().toString();

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!telefone.isEmpty()) {
                    if (!senha.isEmpty()) {

                        progressBar2.setVisibility(View.VISIBLE);

                        Usuario usuario = new Usuario();
                        usuario.setNome(nome);
                        usuario.setEmail(email);
                        usuario.setTelefone(telefone);
                        usuario.setSenha(senha);

                        cadastrarUsuario(usuario);

                    } else {
                        edit_senha.requestFocus();
                        edit_senha.setError("Digita a sua senha ");
                    }
                } else {
                    edit_telefone.requestFocus();
                    edit_telefone.setError("digite seu telefone ");
                }
            } else {
                edit_email.requestFocus();
                edit_email.setError("informe seu email");
            }
        } else {
            edit_nome.requestFocus();
            edit_nome.setError("informe seu nome");
        }
    }

    private void cadastrarUsuario(Usuario usuario) {
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String idUser = task.getResult().getUser().getUid();
                usuario.setId(idUser);
                usuario.salvar();

                finish();

                startActivity(new Intent(this, MainActivity.class));
            } else {
                String error = task.getException().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
    }

    private void iniciaComponentes() {
        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_telefone = findViewById(R.id.edit_telefone);
        edit_senha = findViewById(R.id.edit_senha);
        progressBar2 = findViewById(R.id.progressBar2);

        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Criar conta");

    }
}