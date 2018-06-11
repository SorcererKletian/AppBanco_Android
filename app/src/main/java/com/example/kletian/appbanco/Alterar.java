package com.example.kletian.appbanco;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Alterar extends AppCompatActivity {

    EditText txvId_alterar;
    EditText txvDescricao_alterar;
    EditText txvEndereco_alterar;
    EditText txvBairro_alterar;
    EditText txvImpacto_alterar;
    String ida;

    Button btnAltera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        txvId_alterar = (EditText) findViewById(R.id.txvId_alterar);
        txvDescricao_alterar = (EditText) findViewById(R.id.txvDescricao_altera);
        txvEndereco_alterar = (EditText) findViewById(R.id.txvEndereco_altera);
        txvBairro_alterar = (EditText) findViewById(R.id.txvBairro_altera);
        txvImpacto_alterar = (EditText) findViewById(R.id.txvimpacto_altera);

        Intent it = getIntent();

        if (it != null) {
            Bundle params = it.getExtras();
            if (params != null) {
                String dados = (String) params.get("dados");
                buscaContato(dados);
            }
        }

        btnAltera = (Button)findViewById(R.id.btnAltera);

        btnAltera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = txvId_alterar.getText().toString();
                int res = alterarDados(id);
                if (res > 0) {
                    Toast.makeText(getApplicationContext(), "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Listar.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao alterar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        protected int alterarDados(String dados){
            SQLiteDatabase db = openOrCreateDatabase("alertas.db", Context.MODE_PRIVATE, null);
            String descricao = txvDescricao_alterar.getText().toString();
            String endereco = txvEndereco_alterar.getText().toString();
            String bairro = txvBairro_alterar.getText().toString();
            String impacto = txvImpacto_alterar.getText().toString();

            ContentValues ctv = new ContentValues();
            ctv.put("descricao", descricao);
            ctv.put("endereco", endereco);
            ctv.put("bairro", bairro);
            ctv.put("impacto", impacto);
            int res = db.update("alertas", ctv, "_id=?", new String[]{dados});
            db.close();
            return res;
       }

    private void buscaContato(String dados){
        SQLiteDatabase db = openOrCreateDatabase("alertas.db", Context.MODE_PRIVATE, null);

        String sql = "SELECT * FROM ALERTAS WHERE _id=?";
        Cursor c = (SQLiteCursor) db.rawQuery(sql, new String[]{dados});

        if(c.moveToFirst()){
            String id = c.getString(c.getColumnIndex("_id"));
            String descricao = c.getString(c.getColumnIndex("descricao"));
            String endereco = c.getString(c.getColumnIndex("endereco"));
            String bairro = c.getString(c.getColumnIndex("bairro"));
            String impacto = c.getString(c.getColumnIndex("impacto"));

            txvId_alterar.setText(id);
            txvDescricao_alterar.setText(descricao);
            txvEndereco_alterar.setText(endereco);
            txvBairro_alterar.setText(bairro);
            txvImpacto_alterar.setText(impacto);
        }
        c.close();
        db.close();
    }

    public void excluir(View v){
        ida = txvId_alterar.getText().toString();

        try{
            SQLiteDatabase db = openOrCreateDatabase("alertas.db",Context.MODE_PRIVATE, null);
            db.delete("alertas", "_id=?", new String[]{ida});
            db.close();
            Toast.makeText(getApplicationContext(),"Excluído", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Listar.class));
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Erro ao excluir", Toast.LENGTH_SHORT).show();
        }
    }

    public void voltarTelaInicial(View v){
        startActivity((new Intent(getApplicationContext(), Menu.class)));
    }



}
