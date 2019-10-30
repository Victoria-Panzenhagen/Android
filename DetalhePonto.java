package com.example.cadastroponto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetalhePonto extends AppCompatActivity {

    private TextView professorTextView;
    private TextView dataTextView;
    private EditText entradaEditText;
    private EditText saidaEditText;
    private EditText disciplinaEditText;

    private Button   alterarButton;
    private Button   apagarButton;
    private Button   retornarButton;

    private GerenciadorBanco gerenciadorBanco;
    private Intent           retornarIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_ponto);
        inicializaComponentes();
        inicializaListeners();
        Intent origem = getIntent();
        Bundle pacote = origem.getExtras();
        String nome = pacote.getString("PROFESSOR");
        String data = pacote.getString("DATA");
        Professor pontoAConsultar = new Professor(nome, data, "", "", "");
        Professor professor = gerenciadorBanco.consultar(pontoAConsultar);
        preencherDetalhePonto(professor);
    }

    public void inicializaComponentes(){
        professorTextView  = findViewById(R.id.ID2_PROFESSORtextView7);
        dataTextView       = findViewById(R.id.ID2_DATAtextView8);
        entradaEditText    = findViewById(R.id.ID2_ENTRADAeditText);
        saidaEditText      = findViewById(R.id.ID2_SAIDAeditText2);
        disciplinaEditText = findViewById(R.id.ID2_DISCIPLINAeditText3);
        alterarButton      = findViewById(R.id.ID2_ALTERARbutton);
        apagarButton       = findViewById(R.id.ID2_APAGARbutton2);
        retornarButton     = findViewById(R.id.ID2_RETORNARbutton3);

        gerenciadorBanco = new GerenciadorBanco(this);
        retornarIntent   = new Intent(this, MainActivity.class);
    }

    public void inicializaListeners(){
        alterarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Professor professor = obterDados();
                gerenciadorBanco.alterar(professor);
            }
        });

        apagarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Professor professor = obterDados();
                gerenciadorBanco.apagar(professor);
                startActivity(retornarIntent);
            }
        });

        retornarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(retornarIntent);
            }
        });

    }

    public Professor obterDados(){
        String nome       = professorTextView.getText().toString();
        String data       = dataTextView.getText().toString();
        String entrada    = entradaEditText.getText().toString();
        String saida      = saidaEditText.getText().toString();
        String disciplina = disciplinaEditText.getText().toString();
        Professor professor = new Professor(nome, data, entrada, saida, disciplina);
        return professor;
    }

    public void preencherDetalhePonto(Professor professor){
        professorTextView.setText(professor.getNome());
        dataTextView.setText(professor.getData());
        entradaEditText.setText(professor.getEntrada());
        saidaEditText.setText(professor.getSaida());
        disciplinaEditText.setText(professor.getDisciplina());
    }
}
