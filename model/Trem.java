/* ***************************************************************
* Autor............: Hercules Sampaio Oliveira
* Matricula........: 202310486
* Inicio...........: 17/05/2024
* Ultima alteracao.: 25/05/2024
* Nome.............: Trem.java
* Funcao...........: A classe gera um construtor que passa ImageView, 
int de posicao e Slider como parametros e baseado na posicao as coordenadas X e Y
dos trens vao ser passadas para a animacao quando forem instanciados no controller
*************************************************************** */

package model;

import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class Trem {

  private ImageView trem; // Variavel que armazena a ImageView do trem a ser posicionado
  private int posicao; // Variavel que posiciona o trem respectivamente
  private Slider velocidade;  //Variavel que armazena a velociade que sera passada pelo Slider
  /*
   * ***************************************************************
   * Metodo: Construtor
   * Funcao: Faz a passagem de ImageView, int e Slider como parametros de imagem e posicao no Layout
   * Parametros: ImageView trem, int posicao, Slider velocidade
   * Retorno: Nao ha retorno
   */
  public Trem(ImageView trem, int posicao, Slider velocidade) {
    this.velocidade = velocidade;
    this.trem = trem;
    this.posicao = posicao;
    switch (posicao) {
      case 0: {
        trem.setLayoutX(-81);
        trem.setLayoutY(176);
        trem.setRotate(0);
        break;
      }
      case 1: {
        trem.setLayoutX(-81);
        trem.setLayoutY(340);
        trem.setRotate(0);
        break;
      }

      case 2: {
        trem.setLayoutX(890);
        trem.setLayoutY(176);
        trem.setRotate(180);
        break;
      }
      case 3: {
        trem.setLayoutX(890);
        trem.setLayoutY(340);
        trem.setRotate(180);
        break;
      }

      default:
        break;
    }

  }

  /* ***************************************************************
 * Metodo: setVelocidade
 * Funcao: Insere o valor na variavel velocidade quando o objeto eh instanciado
 * Parametros: Slider velocidade
 * Retorno: void
 *************************************************************** */
  public void setVelocidade(Slider velocidade){
    this.velocidade = velocidade;
  }

  /* ***************************************************************
 * Metodo: getVelocidade
 * Funcao: Retornar o Slider valocidade
 * Parametros: Nao ha parametros
 * Retorno: Slider velocidade
 *************************************************************** */  
  public Slider getVelocidade(){
    return velocidade;
  }

  /* ***************************************************************
 * Metodo: getPosicao
 * Funcao: Retornar o int posicao
 * Parametros: Nao ha parametros
 * Retorno: int posicao
 *************************************************************** */
  public int getPosicao() {
    return posicao;
  }

  /* ***************************************************************
 * Metodo: getImageView
 * Funcao: retorna o ImageView trem
 * Parametros: Nao ha parametros
 * Retorno: ImageView trem
 *************************************************************** */
  public ImageView getImageView() {
    return trem;
  }

  /* ***************************************************************
 * Metodo: setImageView
 * Funcao: Insere o valor na variavel trem quando o objeto eh instanciado
 * Parametros: ImageView trem
 * Retorno: void
 *************************************************************** */
  public void setImageView(ImageView trem) {
    this.trem = trem;
  }

}
