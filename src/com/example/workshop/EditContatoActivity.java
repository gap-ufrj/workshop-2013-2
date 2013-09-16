package com.example.workshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created with IntelliJ IDEA.
 * User: Leite
 * Date: 08/09/13
 * Time: 15:12
 **/

//Activity que preenche ou edita os dados de um Contato
public class EditContatoActivity extends Activity {

    EditText nome, telefone, email;
    Button salvar;
    Banco banco;
    int pos, requestCode;

    public void onCreate(Bundle savedInstanceState) {
        //Método chamado quando a Activity é criada

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contato);
        //Define o layout da Activity, que passa a ser o layout descrito em edit_contato.xml
        inicializaVariaveis();

        requestCode = getIntent().getIntExtra("requestCode",-1);
        /*O método getIntent() retorna a Intent que chamou essa Activity
          O método getIntExtra() retorna o valor relacionado a chave "requestCode". Caso essa chave não
          seja encontrada, o valor -1 é retornado.
          requestCode contém o código que descreve a operação a ser realizada pela Activity
        */

        switch(requestCode){//Qual operação deverá ser realizada?
            case Constantes.ADD_CONTATO://Operação de inserção
                pos = -1; //Não é necessário um valor para a posição
                break;

            case Constantes.EDIT_CONTATO://Operação de edição
                /*Se a operação for de edição, será pega a posição do Contato a ser editado para, então,
                escrever seus dados nos EditText */

                pos = getIntent().getIntExtra("posicao", -1);
                //Posição obtida através da passagem de valores entre a Activity chamadora e a Activity chamada

                Contato c = banco.getContato(pos);//Contato obtido através do Banco pela posição
                nome.setText(c.getNome());//EditText recebe o nome do Contato obtido
                telefone.setText(c.getTelefone());//EditText recebe o telefone do Contato obtido
                email.setText(c.getEmail());//EditText recebe o email do Contato obtido
                break;
        }
    }

    private void inicializaVariaveis(){

        banco = Banco.getInstance();
        //Recebe uma instância do Banco (padrão de projeto Singleton)

        nome = (EditText) findViewById(R.id.edit_nome);
        telefone = (EditText) findViewById(R.id.edit_telefone);
        email = (EditText) findViewById(R.id.edit_email);
        /*findViewById() retorna um View baseada numa ID passada. É necessário o typecasting, pois o objeto
        retornado é do tipo View e, na maioria das vezes, necessitamos de métodos de específicos de uma classe
        filha de View, nesse caso, a EditText e, abaixo, a classe Button */
        salvar = (Button) findViewById(R.id.btn_salvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            /*Aqui está sendo criado um objeto que implementa a interface View.OnClickListener.
              O objetivo é obter um método onClick() para poder atribuir ao botão uma funcionalidade
              quando for clicado */
            @Override
            public void onClick(View view) {
                //Método chamado quando o botão salvar é clicado

                Contato c = new Contato(nome.getText().toString(), telefone.getText().toString(), email.getText().toString());
                //É criado um novo Contato com os dados dos EditText da Activity

                switch(requestCode){//Qual foi a operação solicitada?
                    case Constantes.ADD_CONTATO://Operação de inserção
                        banco.add(c);//Adiciona o novo Contato ao final da ArrayList<Contato>
                        break;

                    case Constantes.EDIT_CONTATO://Operação de edição
                        banco.edit(pos,c);//Substitui o novo contato na posição pos da ArrayList<Contato>
                        break;
                }

                setResult(RESULT_OK);//Define o resultado dessa Activity como RESULT_OK
                finish();//Finaliza a Activity
            }
        });

    }

}