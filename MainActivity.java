package com.example.cadastroponto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText professorEditText;
    private EditText dataEditText;
    private EditText entradaEditText;
    private EditText saidaEditText;
    private EditText disciplinaEditText;

    private Button   inserirButton;
    private Button   limparButton;

    private ListView listaListView;
    private ArrayList<Professor> lista;
    private ArrayAdapter<Professor> listaArrayAdapter;

    private Intent           janelaDetalheIntent;
    private GerenciadorBanco gerenciadorBanco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponetes();
        inicializarListeners();
        gerenciadorBanco.carregar(lista);
        listaArrayAdapter.notifyDataSetChanged();
    }

    protected void inicializarComponetes(){
        professorEditText  = findViewById(R.id.ID1_PROFESSOReditText);
        dataEditText       = findViewById(R.id.ID1_DATAeditText2);
        entradaEditText    = findViewById(R.id.ID1_ENTRADAeditText3);
        saidaEditText      = findViewById(R.id.ID1_SAIDAeditText4);
        disciplinaEditText = findViewById(R.id.ID1_DISCIPLINAeditText5);

        inserirButton      = findViewById(R.id.ID1_INSERIRbutton);
        limparButton       = findViewById(R.id.ID1_LIMPARbutton2);

        listaListView      = findViewById(R.id.ID1_LISTAListView);
        lista              = new ArrayList<Professor>();
        listaArrayAdapter  = new ArrayAdapter<Professor>(this,
                android.R.layout.simple_expandable_list_item_1, lista);
        listaListView.setAdapter(listaArrayAdapter);

        janelaDetalheIntent = new Intent(this, DetalhePonto.class);
        gerenciadorBanco  = new GerenciadorBanco(this);

    }

    protected void inicializarListeners(){
        inserirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Professor professor = obterDados();
                lista.add(professor);
                listaArrayAdapter.notifyDataSetChanged();
                gerenciadorBanco.inserir(professor);
            }
        });

        limparButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparDados();
                lista.clear();
                listaArrayAdapter.clear();
                listaArrayAdapter.notifyDataSetChanged();

                }
        });

        listaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Professor p = (Professor) adapterView.getItemAtPosition(i);
                String nome = p.getNome();
                String data = p.getData();
                Bundle pacote = new Bundle();
                    pacote.putString("PROFESSOR", nome);
                    pacote.putString("DATA", data);
                janelaDetalheIntent.putExtras(pacote);
                startActivity(janelaDetalheIntent);
            }
        });

    }

    private Professor obterDados(){
        String nome = professorEditText.getText().toString();
        String data      = dataEditText.getText().toString();
        String entrada   = entradaEditText.getText().toString();
        String saida     = saidaEditText.getText().toString();
        String disciplina = disciplinaEditText.getText().toString();
        Professor professor = new Professor(nome, data, entrada, saida, disciplina);
        return professor;
    }

    private void limparDados(){
        professorEditText.setText(null);
        dataEditText.setText(null);
        entradaEditText.setText(null);
        saidaEditText.setText(null);
        disciplinaEditText.setText(null);
    }
}

