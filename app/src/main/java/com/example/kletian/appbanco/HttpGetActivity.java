package com.example.kletian.appbanco;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpGetActivity extends AppCompatActivity {

    TextView txtSaida;
    EditText editTextGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_get);
        editTextGet = (EditText)findViewById(R.id.editTextGet);
        txtSaida = (TextView) findViewById(R.id.txtSaida);

        new TempoRequestTask().execute();
    }

    public void importarBackup(View v){
        String str = editTextGet.getText().toString();
        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        it.putExtra("str", str);
        startActivity(it);

    }


    private class TempoRequestTask extends AsyncTask<Void, Integer, String> {
        ProgressDialog pDialog;
        String sb3;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("ASYNC","PRE_EXEC");

            pDialog = new ProgressDialog(HttpGetActivity.this);
            pDialog.setMessage("Aguarde!");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(true);

            pDialog.setProgress(0);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.d("ASYNC", "doInBackground");
            String result = "";
            try {
                InputStream is;
                URL url = new URL("http://10.0.2.2/content.txt");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                int responseCode = conn.getResponseCode();
                if (responseCode < HttpURLConnection.HTTP_BAD_REQUEST){
                    is = conn.getInputStream();
                }else{
                    is = conn.getErrorStream();
                }
                StringBuffer buffer = new StringBuffer();
                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String linha;
                    while ((linha = br.readLine()) != null){
                        buffer.append(linha);
                    }
                    br.close();
                    is.close();
                    conn.disconnect();
                }catch (IOException e){
                    e.printStackTrace();
                }
                result = buffer.toString();
                return buffer.toString();
            }catch (Exception e){
                Log.d("EXP",e.getMessage());
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ASYNC","onPostExecute");
            Log.d("HTTP CODE", s);
            pDialog.dismiss();
            txtSaida.setText("Dados Carregados Para Adicionar no Banco");
            editTextGet.setText(s.toString());
        }
    }
}
