Código do aplicativo do Workshop 2013.2 do GAP
===============

Esse projeto utiliza Action Bar.

Como a Action Bar foi implementada na versão Honeycomb (API 11, Android 3.0), para utilizá-la em versões
anteriores é necessário utilizar as bibliotecas de compatibilidade, que podem ser obtidas através do SDK
Manager, na seção Extras pelo pacote Android Support Library.

Para importar essas bibliotecas no IntelliJ IDEA, basta ir até File > Project Structure, na seção Module.
Em seguida, clique com o botão direito e vá até Add > Import Module. Indique o caminho onde está instalado
o Android SDK, diretório extras > android > support > v7 > appcompat. Importe também as bibliotecas, na seção Libraries,
clicando com o botão direito e indo até New Project Library > Java. Indique praticamente o mesmo caminho, o diretório
onde está instalado o Android SDK, em seguida extras > android > support > v7 > appcompat > libs.

Finalmente, novamente na seção Module, vá até o módulo com o nome do seu projeto, na aba Dependencies.
Clique no "+" e adicione Module Dependency, apontando para o módulo importado anteriormente. Faça o mesmo, indicando
Library > Java, apontando para a biblioteca importada anteriormente.

Caso não consiga importar o módulo de compatibilidade, faça as seguintes alterações:

1) Em ListContatosActivity, remova o import de android.support.v7.app.ActionBarActivity e faça com que a classe
herde Activity em vez de ActionBarActivity.

2) No AndroidManifest.xml, remova o android:theme="@style/Theme.AppCompat" definido para a Activity ListContatosActivity.

3) No lista_add.xml, delete o xmlns:workshop="http://schemas.android.com/apk/res-auto" e o atributo 
"workshop:showAsAction" de <item>

Para adicionar um contato sem utilizar a Action Bar, basta apertar o botão menu do dispositivo virtual e
selecionar a opção adicionar.


