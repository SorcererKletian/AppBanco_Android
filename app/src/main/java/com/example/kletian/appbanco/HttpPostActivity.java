package com.example.kletian.appbanco;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostActivity extends AppCompatActivity {

    TextView txtSaida;
    EditText editdadospost;
    String str2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_post);
        txtSaida = (TextView) findViewById(R.id.txtSaida);
        new TempoRequestTask().execute();

        Intent it = getIntent();

        if (it != null) {
            Bundle params = it.getExtras();
            if (params != null) {
                String str = (String) params.get("str");
                editdadospost = (EditText) findViewById(R.id.editDadosPost);
                editdadospost.setText(str);
            }
        }
        str2 = editdadospost.getText().toString();
    }

    public void voltar(View v){
        startActivity((new Intent(getApplicationContext(), Menu.class)));
    }

    public void httpget(View v){
        startActivity((new Intent(getApplicationContext(), HttpGetActivity.class)));
    }

    private class TempoRequestTask extends AsyncTask<Void, Integer, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("ASYNC","PRE_EXEC");

            pDialog = new ProgressDialog(HttpPostActivity.this);
            pDialog.setMessage("Aguarde!");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(true);

            pDialog.setProgress(0);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.d("ASYNC","doInBackground");
            String result = "txt";
            try {
                URL url = new URL("http://10.0.2.2/post_content.php");

                String content = str2;
                String urlParameters  = "content=" + content;
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = conn.getResponseCode();
                result = responseCode + "";
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
            txtSaida.setText(getResources().getString(R.string.req_sent));
        }
    }
}
