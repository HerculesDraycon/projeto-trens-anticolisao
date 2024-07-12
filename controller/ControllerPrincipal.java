/* ***************************************************************
* Autor............: Hercules Sampaio Oliveira
* Matricula........: 202310486
* Inicio...........: 17/05/2024
* Ultima alteracao.: 25/05/2024
* Nome.............: ControllerPrincipal.java
* Funcao...........: Essa classe eh o controller da primeira scene iniciada
e gera os metodos que coletam as informacoes passadas pelas acoes na interface,
instancia o DataSingleton para coletar uma escolha nas ChoiceBoxes e faz com que
o botao transicione para a proxima scene.
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Movimento;
import util.DataSingleton;

public class ControllerPrincipal implements Initializable {

  @FXML
  private ChoiceBox<String> choiceBox1, choiceBox2; // Criacao das ChoiceBoxes da primeira scene

  @FXML
  private Button iniciarBotao; // Criacao do botao Iniciar da primeira scene

  @FXML
  private Button botaoSobre;  //Criacao do botao da secao Sobre na primeira scene

  // Criacao dos Stages utilizados na primeira tela
  private Stage stage; 
  private Stage stageSobre = new Stage();

  private Scene scene, sceneSobre; // Criacao das scenes utilizadas no primeiro Stage
  private Parent root, rootSobre; // Criacao dos Parents dos Loaders de transicao para novas scenes

  // Strings com as opcoes de posicao
  private String[] escolhaPosicao = { "(1)Esquerda superior e (2)Esquerda inferior",
      "(1)Direita superior e (2)Direita inferior",
      "(1)Esquerda superior e (2)Direita inferior", "(1)Direirta superior e (2)Esquerda inferior"
  };

  // Strings com as opcoes de metodo de solucao
  private String[] escolhaMetodo = {"Variavel de Travamento", "Estrita Alternancia", "Solucao de Peterson"};

  DataSingleton data = DataSingleton.getInstance(); // Armazenagem do valor transmitido pelo DataSingleton nessa variavel

  /* ***************************************************************
 * Metodo: initialize
 * Funcao: Adiciona a ChoiceBox com suas respectivas escolhas na scene 1 e roda a scene
 * Parametros: URL location: que baseia o path do arquivo FXML, ResourceBundle resources: pacote de recursos
 * Retorno: void
 *************************************************************** */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    choiceBox1.getItems().addAll(escolhaPosicao); // Adiciona as escolhas na lista da ChoiceBox 1
    choiceBox1.setValue(choiceBox1.getItems().get(data.getOpPosicao())); // Seta os valores da ChoiceBox 1

    choiceBox2.getItems().addAll(escolhaMetodo);  //Adiciona as escolhas na lista da ChoiceBox 2
    choiceBox2.setValue(choiceBox2.getItems().get(data.getOpMetodo()));  //Seta os valores da ChoiceBox 2

  }
  
  /*
   * ***************************************************************
   * Metodo: iniciarBotao
   * Funcao: Suporta o clique para que a acao de transicao de scene seja executada e o arquivo fxml seja carregado
   * Parametros: ActionEvent event, para que a acao seja suportada e execute as linhas de codigo corretamente
   * Retorno: void
   */
  @FXML
  public void iniciarBotao(ActionEvent event) throws IOException {

    Movimento.rodando = true;    //Atribui o valor booleano que executa a animacao no while

    data.setOpPosicao(choiceBox1.getSelectionModel().getSelectedIndex()); // Na acao do botao, seta no DataSingleton o valor da escolha na ChoiceBox 1
    data.setOpMetodo(choiceBox2.getSelectionModel().getSelectedIndex());  // Na acao do botao, seta no DataSingleton o valor da escolha na ChoiceBox 2

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxmlAnimacao.fxml")); // Carrega o arquivo fxml
    root = loader.load(); // Carrega a segunda scene
    scene = new Scene(root); // Passa o valor do Parent para a segunda scene
    stage = (Stage) iniciarBotao.getScene().getWindow(); // Atribui a acao do botao a iniciar a proxima scene no Stage
    stage.setScene(scene); // Adiciona a scene no Stage
    stage.show(); // Inicia o Stage

  }

  /*
   * ***************************************************************
   * Metodo: botaoSobre
   * Funcao: Suporta o clique para que a acao que carrega a tela de apresentacao de informacoes
   * Parametros: ActionEvent event, para que a acao seja suportada e execute as linhas de codigo corretamente
   * Retorno: void
   */
  @FXML
  public void botaoSobre(ActionEvent event) throws IOException{

    Image icon = new Image("/img/infoIcon.png");    //Instancia o icone do projeto

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxmlSobre.fxml"));  // Carrega o arquivo fxml
    rootSobre = loader.load();             // Carrega a segunda scene
    sceneSobre = new Scene(rootSobre);    // Passa o valor do Parent para a segunda scene
    stageSobre.getIcons().add(icon);     //Adiciona o icone no Stage
    stageSobre.setTitle("Encarte");     //Adiciona um titulo no Stage
    stageSobre.setScene(sceneSobre);   // Adiciona a scene no Stage
    stageSobre.setResizable(false);   //Definicao pelo autor do codigo que a tela nao mudara de tamanho
    stageSobre.show();               // Inicia o Stage

  }

}
