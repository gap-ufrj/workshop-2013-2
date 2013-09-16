package com.example.workshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: Leite
 * Date: 08/09/13
 * Time: 15:12
 **/

//Activity principal que lista os contatos
public class ListContatosActivity extends ActionBarActivity {
/*
     ActionBarActivity é uma Activity que possui uma Action Bar.
     Action Bar é uma barra localizada no topo da Activity, que visa passar uma melhor experiência ao usuário
     quando o assunto é utilizar as funções de um menu. A principal ideia da Action Bar é substituir o botão
     físico menu, fazendo com que as operações fiquem mais visíveis e intuitivas ao usuário.

     Como a Action Bar foi implementada na versão Honeycomb (API 11, Android 3.0), para utilizá-la em versões
     anteriores é necessário utilizar as bibliotecas de compatibilidade, que podem ser obtidas através do SDK
     Manager, na seção Extras pelo pacote Android Support Library.

     Para importar essas bibliotecas no IntelliJ IDEA, basta ir até File > Project Structure, na seção Module.
     Em seguida, clique com o botão direito e vá até Add > Import Module. Indique o caminho onde está instalado
     o Android SDK, diretório extras > android > support > v7 > appcompat. Importe também as bibliotecas, na seção Libraries,
     clicando com o botão direito e indo até New Project Library > Java. Indique praticamente o mesmo caminho, no diretório
     onde está instalado o Android SDK, em seguida extras > android > support > v7 > appcompat > libs.

     Finalmente, novamente na seção Module, vá até o módulo com o nome do seu projeto, na aba Dependencies.
     Clique no "+" e adicione Module Dependency, apontando para o módulo importado anteriormente. Faça o mesmo, indicando
     Library > Java, apontando para a biblioteca importada anteriormente.
 */
    Banco banco;
    ArrayAdapter<String> adapter;
    /*ArrayAdapter é uma classe que, por padrão, gerencia objetos TextView para cada String contida na
    List<String> passada no seu construtor */
    ListView lista;

    public void onCreate(Bundle savedInstanceState) {
        //Método chamado quando a Activity é criada

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_contato);
        //Define o layout da Activity, que passa a ser o layout descrito em list_contato.xml

        banco = Banco.getInstance();
        //Recebe uma instância do Banco (padrão de projeto Singleton)

        lista = (ListView) findViewById(R.id.listView);
        /*findViewById() retorna um View baseada numa ID passada. É necessário o typecasting, pois o objeto
        retornado é do tipo View e, na maioria das vezes, necessitamos de métodos de específicos de uma classe
        filha de View, nesse caso, a ListView */
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //Define o onClick() para cada item da lista
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Relembrando, i é o inteiro que define a posição do item clicado na lista
                Intent intent = new Intent(ListContatosActivity.this, ContatoActivity.class);
                /*Intent representa uma intenção, nesse caso, a intenção de passar da Activity ListaContatosActivity
                para a ContatoActivity */
                intent.putExtra("posicao",i);
                /*O método putExtra() insere um valor para ser passado na Intent. Esse valor é identificado por sua
                chave do tipo String. Nesse caso, a chave "posição" retorna o valor i */
                startActivity(intent);
                //Inicia uma Activity correspondente a Intent sem se preocupar com seu resultado
            }
        });
        updateAdapter();
        //Atualiza a ArrayList<String> do Adapter do ListView de contatos
        registerForContextMenu(lista);
        /*Liga um View, no caso o ListView lista, a um ContextMenu */
    }

    public void updateAdapter(){
        //Atualiza a ArrayList<String> do Adapter do ListView de contatos
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, banco.getNomeContatos());
        lista.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        /*Método onCreate() do ContextMenu
        ContextMenu equivale a um menu de contexto. Um exemplo conhecido de menu de contexto é um clique com o
        botão direito realizado em computadores. O equivalente a isso em um dispositivo Android é clicar e segurar
        um View */
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("O que deseja fazer?"); //Título do ContextMenu
        menu.add(Menu.NONE, Constantes.EDIT_CONTATO, Menu.NONE, "Editar");//Opção 1 do ContextMenu
        //add(possui submenu?, id da opção, possui ordem?, título da opção)
        menu.add(Menu.NONE, Constantes.DEL_CONTATO, Menu.NONE, "Deletar");//Opção 2 do ContextMenu
        //Quando todas as opções possuem ordem igual a Menu.NONE, a ordem adotada é a de inserção no menu
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Método que descreve o que ocorre quando uma opção do ContextMenu é selecionada

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        //AdapterView.AdapterContextMenuInfo é uma classe que possui informações extras de um ContextMenu

        switch(item.getItemId()){ //Switch para saber, pela ID, qual opção foi selecionada
            case Constantes.EDIT_CONTATO:
            //Caso tenha clicado na opção editar
                Intent i = new Intent(this,EditContatoActivity.class);
                 /*Intent representa uma intenção, nesse caso, a intenção de passar da Activity ListaContatosActivity
                 para a EditContatoActivity */
                i.putExtra("posicao", info.position);
                /*Aqui vemos a necessidade de utilizar a classe AdapterView.AdapterContextMenuInfo
                Sem essa classe, não seria possível obter position, que indica a posição do item da lista que
                chamou o ContextMenu */
                i.putExtra("requestCode", Constantes.EDIT_CONTATO);
                /*Observando a EditContatoActivity, dá para notar a vantagem de enviar o requestCode para a Activity,
                de modo que podemos reaproveitar a mesma Activity para realizar duas funções diferentes */
                startActivityForResult(i, Constantes.EDIT_CONTATO);
                //Inicia uma Activity correspondente a Intent aguardando o seu resultado
                break;

            case Constantes.DEL_CONTATO:
            //Caso tenha clicado na opção deletar
                banco.del(info.position); //Deleta um Contato i do banco
                updateAdapter(); //Atualiza o Adapter, já que retirou um item da lista

                Toast.makeText(this,"Contato deletado!",Toast.LENGTH_SHORT).show();
                //Notificação simples para a ação realizada
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*Método onCreate() do menu de opções, adaptado para a Action Bar no caso dessa Activity, já que ela
        herda ActionBarActivity */
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lista_add,menu);
        //Constrói um menu baseado em um layout xml, no caso o lista_add.xml
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Método chamado quando uma opção do menu de opções é selecionada
        switch (item.getItemId()){ //Switch para saber, pela ID, qual opção foi selecionada

            case (R.id.menu_add_contato): //A opção selecionada foi a de adicionar

                Intent i = new Intent(this,com.example.workshop.EditContatoActivity.class);
                /*Intent representa uma intenção, nesse caso, a intenção de passar da Activity ListaContatosActivity
                 para a EditContatoActivity */
                i.putExtra("requestCode", Constantes.ADD_CONTATO);
                /*Observando a EditContatoActivity, dá para notar a vantagem de enviar o requestCode para a Activity,
                de modo que podemos reaproveitar a mesma Activity para realizar duas funções diferentes */
                startActivityForResult(i, Constantes.ADD_CONTATO);
                //Inicia uma Activity correspondente a Intent aguardando o seu resultado

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Método chamado ao receber um resultado de uma Activity pela chamada no método startActivityForResult()

        /*
         requestCode é o código da operação realizada, informado na criação da Intent
         reultCode é o código de retorno, informado pelo método setResult() na Activity que foi inicializada pelo
            método startActivityForResult()
         data é a Intent retornada pela Activity que gerou um resultado
         */

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){//Se o resultado foi do tipo RESULT_OK
            updateAdapter();
            //Atualiza o Adapter, já que a lista foi modificada por uma inserção ou edição

            switch (requestCode){
                //Switch para descobrir qual foi o tipo de operação realizada pela Activity que retornou um resultado

                case Constantes.ADD_CONTATO://Se foi a ação de adicionar um contato
                    Toast.makeText(this,"Contato adicionado!",Toast.LENGTH_SHORT).show();
                    //Notificação simples para a ação realizada
                    break;

                case Constantes.EDIT_CONTATO://Se foi a ação de editar um contato

                    Toast.makeText(this,"Contato editado!",Toast.LENGTH_SHORT).show();
                    //Notificação simples para a ação realizada
                    break;
            }
        }
    }

}