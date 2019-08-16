package info.androidhive.sqlite.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.androidhive.sqlite.R;
import info.androidhive.sqlite.database.DatabaseHelper;
import info.androidhive.sqlite.database.model.Cliente;
import info.androidhive.sqlite.database.model.Rutine;
import info.androidhive.sqlite.utils.MyDividerItemDecoration;
import info.androidhive.sqlite.view.adapter.ClienteAdapter;
import info.androidhive.sqlite.view.adapter.RutineAdapter;

public class ClienteRutineActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private RutineAdapter mAdapter;
    private List<Rutine> RutineList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noNotesView;
    private Cliente CurrentCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_rutine);
        noNotesView = findViewById(R.id.empty_notes_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = new DatabaseHelper(this);

        this.getCliente();
//        this.showRutines();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_rutine);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExerciseDialog();
            }
        });
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
        this.CurrentCliente = c;
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

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Rutine>>(){}.getType();
        RutineList = gson.fromJson(c.getExercise(), listType);

        this.showRutines();
    }

    private void showRutines(){
        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new RutineAdapter(this, RutineList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyNotes();
    }

    private void toggleEmptyNotes() {
        if (db.getClientesCount() > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }

    private void initSpinner(Spinner spinner) {
        String[] s = { "Abdominales ", "Sentadillas", "Tabla ", "Camitas de gorila" };
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(ClienteRutineActivity.this,android.R.layout.simple_spinner_dropdown_item, s);
        spinner.setAdapter(adp);
    }

    private void showExerciseDialog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.cliente_rutine_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(ClienteRutineActivity.this);
        alertDialogBuilderUserInput.setView(view);

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
//        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        final Spinner spinner = view.findViewById(R.id.spinner1);
        this.initSpinner(spinner);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = spinner.getSelectedItem().toString();

                Rutine r = new Rutine();
                r.setExercise(text);
                RutineList.add(0, r);
                mAdapter.notifyDataSetChanged();

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                String JSONObject = gson.toJson(RutineList);

                CurrentCliente.setExercise(JSONObject);

                Log.d("JSONObject", JSONObject);

                db.updateClient(CurrentCliente);

                alertDialog.hide();
            }
        });
    }

}