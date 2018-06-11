package com.example.kletian.appbanco;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Listar extends Activity implements AdapterView.OnItemClickListener {

    SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter ad;
    ListView listViewAlertas;
    Cursor cursor2;
    EditText txt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        buscarDados();
        criarListagem();
    }

    public void buscarDados(){
        try{
            db = openOrCreateDatabase("alertas.db", Context.MODE_PRIVATE, null);
            cursor = db.rawQuery("SELECT ROWID _id,* FROM alertas", null);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
        }
    }

    public void criarListagem(){
        listViewAlertas = (ListView) findViewById(R.id.simplelist);

        String[] from = {"_id", "descricao", "endereco", "bairro", "impacto"};
        int[] to = {R.id.txvidlist, R.id.txvDescricaolist, R.id.txvEnderecolist, R.id.txvBairrolist, R.id.txvImpactolist};
        ad = new SimpleCursorAdapter(getApplicationContext(), R.layout.activity_modelo, cursor, from, to);

        listViewAlertas.setOnItemClickListener(this);
        listViewAlertas.setAdapter(ad);

    }

    public void criaStringItens(View v){
        StringBuilder strb = new StringBuilder();
        txt = (EditText) findViewById(R.id.stringDados);

        if (cursor.moveToFirst()){
            do{

                strb.append(cursor.getString(1));
                strb.append("@");
                strb.append(cursor.getString(2));
                strb.append("@");
                strb.append(cursor.getString(3));
                strb.append("@");
                strb.append(cursor.getString(4));
                strb.append("@");
                strb.append(cursor.getString(5).toString());
                strb.append("#");

            }while (cursor.moveToNext());
        }

        txt.setText(strb);
        String str = txt.getText().toString();
        Intent it = new Intent(getApplicationContext(), HttpPostActivity.class);
        it.putExtra("str", str);
        startActivity(it);

    }






    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        SQLiteCursor sqlCursor = (SQLiteCursor) ad.getItem(position);
        String descricao = sqlCursor.getString(sqlCursor.getColumnIndex("descricao"));
        String dados = sqlCursor.getString(sqlCursor.getColumnIndex("_id"));
        Toast.makeText(getApplicationContext(), "Selecionou o item: "+ descricao + " e id: " + dados, Toast.LENGTH_SHORT).show();

        Intent it = new Intent(getApplicationContext(), Alterar.class);
        it.putExtra("dados", dados);
        startActivity(it);

    }

   public void levaTela(View v){

        Intent it = new Intent(getApplicationContext(), HttpGetActivity.class);
        startActivity(it);
    }

    public void voltar(View v){
        startActivity((new Intent(getApplicationContext(), Menu.class)));
    }
}
