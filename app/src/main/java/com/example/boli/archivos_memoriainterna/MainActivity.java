package com.example.boli.archivos_memoriainterna;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText txtTexto;
    private Button btnGuardar, btnAbrir;
    private static final int READ_BLOCK_SIZE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTexto = (EditText) findViewById(R.id.txtArchivo);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnAbrir = (Button) findViewById(R.id.btnAbrir);

        btnGuardar.setOnClickListener(this);
        btnAbrir.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnGuardar)) {
            String str = txtTexto.getText().toString();
            FileOutputStream fout = null;

            try {
                fout = openFileOutput("archivoTexto.txt", MODE_WORLD_READABLE);
                OutputStreamWriter ows = new OutputStreamWriter(fout);
                ows.write(str);
                ows.flush();
                ows.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getBaseContext(), "El archivo se ha almacenado!!!", Toast.LENGTH_SHORT).show();
            txtTexto.setText("");
        }
        if (v.equals(btnAbrir)) {

            try {
                FileInputStream fin = openFileInput("archivoTexto.txt");
                InputStreamReader isr = new InputStreamReader(fin);
                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                String str = "";

                int charRead;
                while ((charRead = isr.read(inputBuffer)) > 0) {
                    String strRead = String.copyValueOf(inputBuffer, 0, charRead);
                    str += strRead;
                    inputBuffer = new char[READ_BLOCK_SIZE];
                }
                txtTexto.setText(str);
                isr.close();
                Toast.makeText(getBaseContext(), "El archivo ha sido cargado", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}

