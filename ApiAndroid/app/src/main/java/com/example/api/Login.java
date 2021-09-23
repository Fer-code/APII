package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.api.database.DBHelper;
import com.example.api.models.UserClass;

public class Login extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";

    public static final String COD_KEY = "cod_key";

    SharedPreferences sharedpreferences;

    EditText email, senha;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
    }
    public void Conf(View c) {
        email = findViewById(R.id.idEmailUsuario);
        senha = findViewById(R.id.idSenhaUsuario);

        String senha1 = senha.getText().toString();
        String email1 = email.getText().toString();

        if (senha1.isEmpty() || email1.isEmpty()) {
            Toast.makeText(this, "Insira os dados corretamente", Toast.LENGTH_SHORT).show();
        } else {
            autenticaUsuario(email1, senha1);
        }
    }


    private void autenticaUsuario (String email, String senha) {

        EditText nomes = findViewById(R.id.idEmailUsuario);
        EditText senhas = findViewById(R.id.idSenhaUsuario);

        UserClass usuario = new UserClass();
        usuario.setEmail(email);
        usuario.setPassword(senha);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);


        boolean resultado = db.autenticaUsuario(usuario);
        if (resultado == true) {
           String codigo = db.pegaCodUser(email);
           Toast.makeText(this, ""+codigo, Toast.LENGTH_LONG).show();
           String codi = String.valueOf(codigo);

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString(COD_KEY, codigo);

            editor.apply();

            Intent intent = new Intent(Login.this, MainActivity.class);


            startActivity(intent);
            finish();
        } else {
            nomes.setText("");
            senhas.setText("");
            nomes.requestFocus();
            Toast.makeText(this, "Usuario ou senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    public void CadInt (View v){
        Intent cadastro = new Intent(this, CadUsuario.class);
        startActivity(cadastro);

    }
}