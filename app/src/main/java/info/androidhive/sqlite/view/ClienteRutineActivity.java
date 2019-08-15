package info.androidhive.sqlite.view;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import info.androidhive.sqlite.R;
import info.androidhive.sqlite.database.DatabaseHelper;
import info.androidhive.sqlite.database.model.Cliente;

public class ClienteRutineActivity extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_rutine);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = new DatabaseHelper(this);

        this.getCliente();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getCliente() {
        Intent intent = getIntent();
        long id = Long.parseLong(intent.getStringExtra("id"));
        Cliente c = db.getCliente(id);

        TextView nombre = this.findViewById(R.id.cliente_nombre);
        nombre.setText(c.getNombre());
        TextView edad = this.findViewById(R.id.cliente_edad);
        edad.setText(c.getEdad());
        TextView estatura = this.findViewById(R.id.cliente_estatura);
        estatura.setText(c.getEstatura());
        TextView imc = this.findViewById(R.id.cliente_imc);
        imc.setText(c.getImc());
        TextView peso = this.findViewById(R.id.cliente_peso);
        peso.setText(c.getPeso());
    }

}