package com.example.kletian.appbanco;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_menu2);

        String[] menus = {"Cadastrar", "Listar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menus);
        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

        switch (position){
            case 0:
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                break;
            case 1:
                startActivity(new Intent(getBaseContext(), Listar.class));
                break;
            default:
                break;
        }
    }
}
