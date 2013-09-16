package com.example.workshop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Leite
 * Date: 08/09/13
 * Time: 15:12
 **/

//Activity que exibe os dados de um Contato
public class ContatoActivity extends Activity implements View.OnClickListener{
/*
  O fato dessa Activity implementar a interface View.OnClickListener significa que ela terá um método
  onCick() definido. Sendo assim, é possível passar a própria Activity como parâmetro para o método
  setOnClickListener() de um botão
*/
    TextView nome, telefone, email;
    ImageButton ligar, enviarSMS,enviarEmail;
    Banco banco;

    public void onCreate(Bundle savedInstanceState) {
        //Método chamado quando a Activity é criada
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contato);
        //Define o layout da Activity, que passa a ser o layout descrito em contato.xml

        int pos = getIntent().getIntExtra("posicao",-1);
        /*O método getIntent() retorna a Intent que chamou essa Activity
          O método getIntExtra() retorna o valor relacionado a chave "posicao". Caso essa chave não
          seja encontrada, o valor -1 é retornado.
        */
        inicializaVariaveis(pos);
    }

    private void inicializaVariaveis(int pos){

        nome = (TextView) findViewById(R.id.tv_nome);
        telefone = (TextView) findViewById(R.id.tv_telefone);
        email = (TextView) findViewById(R.id.tv_email);
        /*findViewById() retorna um View baseada numa ID passada. É necessário o typecasting, pois o objeto
        retornado é do tipo View e, na maioria das vezes, necessitamos de métodos de específicos de uma classe
        filha de View, nesse caso, a TextView */

        banco = Banco.getInstance();
        //Recebe uma instância do Banco (padrão de projeto Singleton)

        nome.setText(banco.getContato(pos).getNome());
        //Define o texto da TextView de acordo com o nome do Contato pego no Banco
        telefone.setText(banco.getContato(pos).getTelefone());
        //Define o texto da TextView de acordo com o telefone do Contato pego no Banco
        email.setText(banco.getContato(pos).getEmail());
        //Define o texto da TextView de acordo com o email do Contato pego no Banco

        ligar = (ImageButton) findViewById(R.id.ib_ligar);
        enviarSMS = (ImageButton) findViewById(R.id.ib_sms);
        enviarEmail = (ImageButton) findViewById(R.id.ib_email);
        /*findViewById() retorna um View baseada numa ID passada. É necessário o typecasting, pois o objeto
        retornado é do tipo View e, na maioria das vezes, necessitamos de métodos de específicos de uma classe
        filha de View, nesse caso, a ImageButton */

        ligar.setOnClickListener(this);
        enviarSMS.setOnClickListener(this);
        enviarEmail.setOnClickListener(this);
        //Todas as respostas de clique nos botões são gerenciadas pelo método onClick() dessa Activity
    }

    @Override
    public void onClick(View v) {
        //Método chamado quando um botão é clicado

        switch (v.getId()){//Switch para saber, pela ID, qual botão foi clicado
            case R.id.ib_ligar://Caso tenha sido o botão de ligar
                Intent ligarIntent = new Intent(Intent.ACTION_CALL);
                //Intent inicializada por uma ação padrão de fazer ligação
                ligarIntent.setData(Uri.parse("tel:"+telefone.getText()));
                //Define os dados dessa Intent através de uma URI com o telefone do Contato
                startActivity(ligarIntent);
                //Realiza a Intent sem se preocupar com seu resultado
                break;
            case R.id.ib_sms://Caso tenha sido o botão de enviar SMS
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                //Intent inicializada por uma ação genérica
                smsIntent.putExtra("address", telefone.getText());
                //Inserção do telefone do Contato pelo método putExtra() com uma chave padrão
                smsIntent.setType("vnd.android-dir/mms-sms");
                //Define um tipo para a Intent através de um MIME
                startActivity(smsIntent);
                //Realiza a Intent sem se preocupar com seu resultado
                break;
            case R.id.ib_email://Caso tenha sido o botão de enviar email
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                //Intent inicializada por uma ação padrão de enviar dados
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
                //Inserção do email do Contato pelo método putExtra() com uma chave padrão
                emailIntent.setType("text/html");
                //Define um tipo para a Intent através de um MIME
                startActivity(Intent.createChooser(emailIntent, "Enviar email com ..."));
                //Inicia uma Activity correspondente a Intent sem se preocupar com seu resultado
                /*Intent.createChooser() é um método que cria um menu para que o usuário escolha qual Activity
                vai receber a Intent enviada. Na prática, um menu é criado nesse exemplo e é possível que o usuário
                escolha com qual programa ele deseja enviar um email */
                break;
        }
    }
}