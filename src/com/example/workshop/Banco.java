package com.example.workshop;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Leite
 * Date: 08/09/13
 * Time: 15:12
 **/

//Clase utilizada para simular um banco de dados que armazena dados de Contatos
public class Banco {

    private static Banco instance;
    private ArrayList<Contato> contatos;

    public static Banco getInstance(){
        if(instance == null){
            instance = new Banco();
        }
        return instance;
    }

    private Banco(){
        contatos = new ArrayList<Contato>();
    }

    public Contato getContato(int pos){
        return contatos.get(pos);
    }

    public ArrayList<String> getNomeContatos(){
        ArrayList<String> nomeContatos = new ArrayList<String>();
        for(int i = 0; i < contatos.size(); i++){
            nomeContatos.add(contatos.get(i).getNome());
        }
        return nomeContatos;
    }

    public void add(Contato c){
        contatos.add(c);
    }

    public void edit(int pos, Contato c){
        contatos.set(pos,c);
    }

    public void del(int pos){
        contatos.remove(pos);
    }
}
