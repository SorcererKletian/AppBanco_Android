package com.example.kletian.appbanco;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    TextView txvDescricao;
    TextView txvEndereco;
    TextView txvBairro;
    TextView txvImpacto;
    EditText txt;
    EditText editdados;
    Button btnBackup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBackup = (Button) findViewById(R.id.btnBackup);
        btnBackup.setVisibility(View.INVISIBLE);

        criarTabela();

        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);

        txvDescricao = (TextView) findViewById(R.id.txvDescricao);
        txvEndereco = (TextView) findViewById(R.id.txvEndereco);
        txvBairro = (TextView) findViewById(R.id.txvBairro);
        txvImpacto = (TextView) findViewById(R.id.txvImpacto);

        Intent it = getIntent();

        if (it != null) {
            Bundle params = it.getExtras();
            if (params != null) {
                String str = (String) params.get("str");
                editdados = (EditText) findViewById(R.id.editTextStr);
                editdados.setText(str);
                btnBackup.setVisibility(View.VISIBLE);


            }
        }


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Alerta a = new Alerta();
                    a.setId(0);
                    a.setDescricao(txvDescricao.getText().toString());
                    a.setEndereco(txvEndereco.getText().toString());
                    a.setBairro(txvBairro.getText().toString());
                    a.setImpacto (Integer.parseInt(txvImpacto.getText().toString()));

                    salvarAlerta(a);

                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Erro ao salvar", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    public void salvarAlerta(Alerta a){
        SQLiteDatabase db = null;
        try{
            db = openOrCreateDatabase("alertas.db", Context.MODE_PRIVATE, null);
            ContentValues contentInsert = new ContentValues();
            if (a.getId() > 0){
                contentInsert.put("_id", a.getId());
            }

            contentInsert.put("descricao", a.getDescricao());
            contentInsert.put("endereco", a.getEndereco());
            contentInsert.put("bairro", a.getBairro());
            contentInsert.put("impacto", a.getImpacto());

            db.insert("alertas", null, contentInsert);
        }catch(Exception ex){
            Toast.makeText(getApplicationContext(), "Erro ao inserir", Toast.LENGTH_SHORT).show();
        }finally {
            Toast.makeText(getApplicationContext(), "Dados Cadastrados", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getBaseContext(), Listar.class));
            db.close();
        }
    }


    public void criarTabela(){
        SQLiteDatabase db = null;
        try{
            db = openOrCreateDatabase("alertas.db", Context.MODE_PRIVATE, null);

            StringBuilder sql = new StringBuilder();
            sql.append("CREATE TABLE IF NOT EXISTS alertas(");
            sql.append("_id integer primary key autoincrement,");
            sql.append("descricao varchar(120),");
            sql.append("endereco varchar(120),");
            sql.append("bairro varchar(100),");
            sql.append("impacto integer)");

            db.execSQL(sql.toString());
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
        }finally {
            db.close();
        }
    }

    public void recuperaDados(View v) {
        SQLiteDatabase db = null;
        db = openOrCreateDatabase("alertas.db", Context.MODE_PRIVATE, null);
        db.execSQL("DELETE FROM ALERTAS");
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'ALERTAS'");

        criarTabela();

        txt = (EditText) findViewById(R.id.editTextStr);
        String dadosCompletos = txt.getText().toString();

        String dados = "";
        String campoId = "";
        String campoDescricao = "";
        String campoEndereco = "";
        String campoBairro = "";
        String campoImpacto = "";
        Character aux;
        Character letra;
        int i = 0;
        boolean start = true;

        do {
            do{
                aux = dadosCompletos.charAt(i);
                dados = dados + Character.toString(aux);
                i++;
                letra = dadosCompletos.charAt(i);

                if ((letra == '@') || (letra == '#')) {
                    start = false;
                }
            }while (start == true);
            campoId = (dados);
            dados = "";
            start = true;
            i++;

            do {
                aux = dadosCompletos.charAt(i);
                dados = dados + Character.toString(aux);
                i++;
                letra = dadosCompletos.charAt(i);

                if ((letra == '@') || (letra == '#')) {
                    start = false;
                }
            } while (start == true);
            campoDescricao = (dados);
            dados = "";
            start = true;
            i++;

            do {
                aux = dadosCompletos.charAt(i);
                dados = dados + Character.toString(aux);
                i++;
                letra = dadosCompletos.charAt(i);

                if ((letra == '@') || (letra == '#')) {
                    start = false;
                }
            } while (start == true);
            campoEndereco = (dados);
            dados = "";
            start = true;
            i++;

            do {
                aux = dadosCompletos.charAt(i);
                dados = dados + Character.toString(aux);
                i++;
                letra = dadosCompletos.charAt(i);

                if ((letra == '@') || (letra == '#')) {
                    start = false;
                }
            } while (start == true);
            campoBairro = (dados);
            dados = "";
            start = true;
            i++;

            do {
                aux = dadosCompletos.charAt(i);
                dados = dados + Character.toString(aux);
                i++;
                letra = dadosCompletos.charAt(i);

                if ((letra == '@') || (letra == '#')) {
                    start = false;
                }
            } while (start == true);
            campoImpacto = (dados);
            dados = "";
            start = true;

            try{
                Alerta a = new Alerta();
                a.setId(Integer.parseInt(campoId));
                a.setDescricao(campoDescricao);
                a.setEndereco(campoEndereco);
                a.setBairro(campoBairro);
                a.setImpacto(Integer.parseInt(campoImpacto));

                salvarAlerta(a);
            }catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Erro ao salvar", Toast.LENGTH_SHORT).show();
            }
            i++;
        }while (i < dadosCompletos.length());
        startActivity(new Intent(getBaseContext(), Listar.class));

    }
}
