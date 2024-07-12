/* ***************************************************************
* Autor............: Hercules Sampaio Oliveira
* Matricula........: 202310486
* Inicio...........: 17/05/2024
* Ultima alteracao.: 25/05/2024
* Nome.............: ControllerAnimacao.java
* Funcao...........: Essa classe faz a coordenacao da cena da animacao, 
estando referenciada no arquivo fxml da animacao, ela inicializa a animacao
e instancia os trens baseados nas opcoes que foram transmitidas pelo DataSingleton.
Seus metodos executam as instrucoes dos modulos interativos na tela, como o botao
Voltar e o Reset.
*************************************************************** */

package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Trem;
import model.Movimento;
import util.DataSingleton;

public class ControllerAnimacao implements Initializable {

  Stage stage; // Criacao do Stage da segunda scene
  Scene scene; // Criacao da segunda scene
  Parent root; // Criacao do Parent do Loader de transicao da segunda para a primeira scene

  @FXML
  private Button botaoVoltar, botaoReset; // Criacao do botao voltar e reset da segunda scene com referencia FXML

  @FXML
  private ImageView imagemTrem1, imagemTrem2; // Variaveis que armazenam ImageView dos trens para que sejam instanciados no initialize()

  @FXML
  private Slider velocidadeTrem1, velocidadeTrem2; // Variaveis que armazenam o valor passado no Slider para que a velocidade dos trens seja alterada dinamicamente

  Movimento thread1, thread2;   //Variaveis da classe Movimento que funcionam com threads dentro do initialize()
  Trem trem1, trem2;            //Variaveis da classe Trem que serao instanciadas dentro da instancia do movimento por threads

  DataSingleton data = DataSingleton.getInstance(); // Armazenagem do valor transmitido pelo DataSingleton nessa variavel

  /*
   * ***************************************************************
   * Metodo: initialize
   * Funcao: Inicializar os objetos na tela apos receber os metodos de construcao da animacao
   * Parametros: URL location e ResourceBundle resources
   * Retorno: void
   */

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    switch (data.getOpPosicao()) { // Switch das 4 posicoes possiveis, definindo os parametros iniciais para cada caso
      case 0: {
        thread1 = new Movimento(new Trem(imagemTrem1, 0, velocidadeTrem1), data.getOpMetodo());
        thread2 = new Movimento(new Trem(imagemTrem2, 1, velocidadeTrem2), data.getOpMetodo());
        break;
      }
      case 1: {
        thread1 = new Movimento(new Trem(imagemTrem1, 2, velocidadeTrem1), data.getOpMetodo());
        thread2 = new Movimento(new Trem(imagemTrem2, 3, velocidadeTrem2), data.getOpMetodo());
        break;
      }
      case 2: {
        thread1 = new Movimento(new Trem(imagemTrem1, 0, velocidadeTrem1), data.getOpMetodo());
        thread2 = new Movimento(new Trem(imagemTrem2, 3, velocidadeTrem2), data.getOpMetodo());
        break;
      }
      case 3: {
        thread1 = new Movimento(new Trem(imagemTrem1, 2, velocidadeTrem1), data.getOpMetodo());
        thread2 = new Movimento(new Trem(imagemTrem2, 1, velocidadeTrem2), data.getOpMetodo());
        break;
      }
      default:
        break;
    }
    
    thread1.start();   //Faz o start da thread 1
    thread2.start();   //Faz o start da thread 2

  }

  /*
   * ***************************************************************
   * Metodo: botaoVoltar
   * Funcao: Suporta o clique para que a acao de transicao de scene seja executada e o arquivo fxml seja
   * carregado, bem como reinicia os parametros de operacao dos metodos utilizados.
   * Parametros: ActionEvent event, para que a acao seja suportada e execute as linhas de codigo corretamente
   * Retorno: void
   */

  @FXML
  public void botaoVoltar(ActionEvent event) throws IOException {

    Movimento.rodando = false;  //Atribui o valor booleano que interrompe a animacao no while
    Movimento.varTravamento1 = 0;         //Retorna a Variavel de Travamento 1 para 0
    Movimento.varTravamento2 = 0;         //Retorna a Variavel de Travamento 2 para 0
    Movimento.estritaAlt1 = 0;            //Retorna a variavel de Estrita Alternancia 1 para 0
    Movimento.estritaAlt2 = 0;            //Retorna a variavel de Estrita Alternancia 2 para 0
    Movimento.vez1 = 0;                   //Retorna a vez 1 da Solucao de Peterson para 0
    Movimento.vez1 = 0;                   //Retorna a vez 2 da Solucao de Peterson para 0
    //Retorna os vetores da Solucao de Peterson para false
    Movimento.interesse1[0] = false;
    Movimento.interesse1[1] = false;
    Movimento.interesse2[0] = false;
    Movimento.interesse2[1] = false;

    //Retorna as variaveis booleans usadas nas Zonas Criticas para false
    thread1.zonaCritica1 = false;
    thread1.zonaCritica2 = false;
    thread2.zonaCritica1 = false;
    thread2.zonaCritica2 = false;

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxmlPrincipal.fxml")); // Carrega o arquivo fxml
    root = loader.load();  // Carrega a primeira scene
    scene = new Scene(root);  // Passa o valor do Parent para a primeira scene
    stage = (Stage) botaoVoltar.getScene().getWindow();  // Atribui a acao do botao a iniciar a scene anterior no Stage
    stage.setScene(scene);  // Adiciona a scene no Stage
    stage.show();  // Inicia o Stage

  }

  /*
   * ***************************************************************
   * Metodo: botaoReset
   * Funcao: Reinicia a posicao dos trens para a default e deixa as imagens passiveis de continuar executando
   * a animacao, bem como reinicia os parametros de operacao dos metodos utilizados
   * Parametros: ActionEvent event, para que a acao seja suportada e execute as linhas de codigo corretamente
   * Retorno: void
   */
  @FXML
  public void botaoReset(ActionEvent event) {

    Movimento.rodando = false;   //Atribui o valor booleano que interrompe a animacao no while
    Movimento.varTravamento1 = 0;          //Retorna a Variavel de Travamento 1 para 0
    Movimento.varTravamento2 = 0;          //Retorna a Variavel de Travamento 2 para 0
    Movimento.estritaAlt1 = 0;             //Retorna a variavel de Estrita Alternancia 1 para 0
    Movimento.estritaAlt2 = 0;             //Retorna a variavel de Estrita Alternancia 2 para 0
    Movimento.vez1 = 0;                    //Retorna a vez 1 da Solucao de Peterson para 0
    Movimento.vez2 = 0;                    //Retorna a vez 1 da Solucao de Peterson para 0
    //Retorna os vetores da Solucao de Peterson para false
    Movimento.interesse1[0] = false;
    Movimento.interesse1[1] = false;
    Movimento.interesse2[0] = false;
    Movimento.interesse2[1] = false;

    //Retorna as variaveis booleans usadas nas Zonas Criticas para false
    thread1.zonaCritica1 = false;
    thread1.zonaCritica2 = false;
    thread2.zonaCritica1 = false;
    thread2.zonaCritica2 = false;

    velocidadeTrem1.setValue(3.0);    //Faz com que todo reset traga o trem 1 para sua velocidade predefinida
    velocidadeTrem2.setValue(3.0);    //Faz com que todo reset traga o trem 2 para sua velocidade predefinida

    switch (data.getOpPosicao()) { // Switch das 4 posicoes possiveis, definindo os parametros iniciais para cada caso

      case 0: {
        thread1 = new Movimento(new Trem(imagemTrem1, 0, velocidadeTrem1), data.getOpMetodo());
        thread2 = new Movimento(new Trem(imagemTrem2, 1, velocidadeTrem2), data.getOpMetodo());
        break;
      }
      case 1: {
        thread1 = new Movimento(new Trem(imagemTrem1, 2, velocidadeTrem1), data.getOpMetodo());
        thread2 = new Movimento(new Trem(imagemTrem2, 3, velocidadeTrem2), data.getOpMetodo());
        break;
      }
      case 2: {
        thread1 = new Movimento(new Trem(imagemTrem1, 0, velocidadeTrem1), data.getOpMetodo());
        thread2 = new Movimento(new Trem(imagemTrem2, 3, velocidadeTrem2), data.getOpMetodo());
        break;
      }
      case 3: {
        thread1 = new Movimento(new Trem(imagemTrem1, 1, velocidadeTrem1), data.getOpMetodo());
        thread2 = new Movimento(new Trem(imagemTrem2, 2, velocidadeTrem2), data.getOpMetodo());
        break;
      }
      default:
        break;
    }

    Movimento.rodando = true;    //Atribui o valor booleano que executa a animacao no while

  }

}
