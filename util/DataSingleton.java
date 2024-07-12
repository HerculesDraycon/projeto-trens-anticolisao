/* ***************************************************************
* Autor............: Hercules Sampaio Oliveira
* Matricula........: 202310486
* Inicio...........: 17/05/2024
* Ultima alteracao.: 25/05/2024
* Nome.............: DataSingleton.java
* Funcao...........: Essa classe foi criada para que as escolhas nas ChoiceBoxes
da primeira scene sejam transferidas para o controller da animacao. Com um construtor
vazio, ela armazena os ints nas variaveis quando eh instanciada na primeira scene
e transporta o valor para o controller da animacao, quando eh utilizada novamente.
*************************************************************** */

package util;

public class DataSingleton {

  private static final DataSingleton instance = new DataSingleton();  //Instanciando o DataSingleton dentro da classe
  private int opPosicao, opMetodo; // Variaveis que armazenam os ints que reprsentam as escolhas nas Choiceboxes

  /*
   * ***************************************************************
   * Metodo: Construtor
   * Funcao: Define a passagem de parametros do objeto quando instanciado
   * Parametros: Nao ha parametros
   * Retorno: Nao ha retorno
   */
  DataSingleton() {};

  /*
   * ***************************************************************
   * Metodo: getInstance
   * Funcao: Fornece os valores que foram definidos nas ChoiceBoxes
   * Parametros: Nao ha parametros
   * Retorno: retorna um objeto da classe DataSingleton
   */
  public static DataSingleton getInstance() {
    return instance;
  }

  /*
   * ***************************************************************
   * Metodo: setOpPosicao
   * Funcao: Define o valor da variavel opcao quando o objeto eh instanciado
   * Parametros: int opPosicao
   * Retorno: void
   */
  public void setOpPosicao(int opPosicao) {
    this.opPosicao = opPosicao;
  }

  /*
   * ***************************************************************
   * Metodo: getOpPosicao
   * Funcao: Retorna o valor da variavel aramazenada no respectivo objeto
   * Parametros: Nao ha parametros
   * Retorno: int opPosicao
   */
  public int getOpPosicao() {
    return opPosicao;
  }

  /*
   * ***************************************************************
   * Metodo: setOpMetodo
   * Funcao: Define o valor da variavel opcao quando o objeto eh instanciado
   * Parametros: int opMetodo
   * Retorno: void
   */
  public void setOpMetodo(int opMetodo){
    this.opMetodo = opMetodo;
  }

  /*
   * ***************************************************************
   * Metodo: getOpMetodo
   * Funcao: Retorna o valor da variavel aramazenada no respectivo objeto
   * Parametros: Nao ha parametros
   * Retorno: int opMetodo
   */
  public int getOpMetodo(){
    return opMetodo;
  }

}
