package com.example.cadastroponto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GerenciadorBanco extends SQLiteOpenHelper {
    public GerenciadorBanco(Context context) {
        super(context, "CONTROLE_PONTO2_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE PONTO_TB (NOME       TEXT, " +
                "DATA       TEXT, " +
                "ENTRADA    TEXT, " +
                "SAIDA      TEXT, " +
                "DISCIPLINA TEXT) ";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void inserir(Professor professor){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("NOME", professor.getNome());
        registro.put("DATA", professor.getData());
        registro.put("ENTRADA", professor.getEntrada());
        registro.put("SAIDA", professor.getSaida());
        registro.put("DISCIPLINA", professor.getDisciplina());
        db.insert("PONTO_TB", null, registro);
    }

    public void carregar(ArrayList<Professor> lista){

        SQLiteDatabase db = getReadableDatabase();
        boolean proximo;
        lista.clear();
        String [] colunas = new String[5];
        colunas[0] = "NOME";
        colunas[1] = "DATA";
        colunas[2] = "ENTRADA";
        colunas[3] = "SAIDA";
        colunas[4] = "DISCIPLINA";
        Cursor cursor = db.query("PONTO_TB", colunas,
                null, null, null, null, null);

        if(cursor == null){
            return;
        }else{
            proximo = cursor.moveToFirst();
            while(proximo){
                String nome = cursor.getString(0);
                String data = cursor.getString(1);
                String entrada = cursor.getString(2);
                String saida = cursor.getString(3);
                String disciplina = cursor.getString(4);
                Professor professor = new Professor(nome, data, entrada, saida, disciplina);
                lista.add(professor);
                proximo = cursor.moveToNext();
            }
        }
    }

    public Professor consultar (Professor professor){
        SQLiteDatabase db = getWritableDatabase();
        boolean proximo;
        String [] colunas = new String[5];
        colunas[0] = "NOME";
        colunas[1] = "DATA";
        colunas[2] = "ENTRADA";
        colunas[3] = "SAIDA";
        colunas[4] = "DISCIPLINA";
        ///CONDICAO DE CONSULTA ///
        String whereClause = "NOME = ? AND DATA = ?";
        String [] coluna = new String[2];
        coluna[0] = professor.getNome();
        coluna[1] = professor.getData();
        ///CONDICAO DE CONSULTA ///
        Cursor cursor = db.query("PONTO_TB", colunas, whereClause, coluna,
                                     null, null,null);
        if(cursor == null){
            return null;
        }else{
            proximo = cursor.moveToFirst();
            if(proximo){
                String nome       = cursor.getString(0);
                String data       = cursor.getString(1);
                String entrada    = cursor.getString(2);
                String saida      = cursor.getString(3);
                String disciplina = cursor.getString(4);
                professor = new Professor(nome, data, entrada, saida, disciplina);
                return professor;
            }else{
                return null;
            }
        }


    }

    public void alterar (Professor professor){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues registro = new ContentValues();
        ///CAMPOS PARA ALTERAR ///
        registro.put("ENTRADA", professor.getEntrada());
        registro.put("SAIDA", professor.getSaida());
        registro.put("DISCIPLINA", professor.getDisciplina());
        ///CAMPOS PARA ALTERAR ///

        ///CONDICAO DE ALTERACAO///
        String whereClause = "NOME = ? AND DATA = ?";
        String [] colunas = new String[2];
        colunas[0] = professor.getNome();
        colunas[1] = professor.getData();
        db.update("PONTO_TB", registro, whereClause, colunas);
        ///CONDICAO DE ALTERACAO///
    }

    public void apagar(Professor professor){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause  = "NOME = ? AND DATA = ? ";
        String [] colunas = new String[2];
        colunas[0] = professor.getNome();
        colunas[1] = professor.getData();
        db.delete("PONTO_TB", whereClause, colunas);
    }

    public void limparTabela(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("PONTO_TB", null, null);
    }
}

