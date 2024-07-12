 /* ***************************************************************
 * Autor............: Hercules Sampaio Oliveira
 * Matricula........: 202310486
 * Inicio...........: 17/05/2024
 * Ultima alteracao.: 25/05/2024
 * Nome.............: Movimento.java
 * Funcao...........: Essa classe nomeada Movimento eh a que extende Threads e nela eh passada uma
 * variavel do tipo Trem e uma do tipo Slider que corresponde a sua velocidade. A classe comporta o
 * metodo run() com o laco while responsavel pela animacao dos trens e tambem basea os movimentos
 * em relacao a escolha do usuario. Para os metodos anticolisao sao adicionadas as variaveis, arrays
 * e metodos e o construtor da classe passa a ter uma variavel int responsavel pela solucao passada
 * pelo Controller.
 *************************************************************** */

package model;

import javafx.application.Platform;
import javafx.scene.control.Slider;

public class Movimento extends Thread {

  public static boolean rodando = true;  //Variavel de valor booleano que manipula o while de execucao do movimento

  private Trem trem;  //Criando a variavel da classe Trem
  private Slider velocidade;  //Variavel que armazena a velocidade que sera passada pelo Slider
  private int solucao;        //Variavel que armazena a solucao que foi escolhida
  private int processo1 = 0, processo2 = 1;  //Variaveis que definem os processos para a solucao de Peterson

  public boolean zonaCritica1 = false;  //Variavel booleana que informa a disponibilidade do recurso compartilhado 1
  public boolean zonaCritica2 = false;  //Variavel booleana que informa a disponibilidade do recurso compartilhado 2

  public static int varTravamento1 = 0, varTravamento2 = 0;  //Variaveis inteiras para a solucao de variavel de travamento

  public static int estritaAlt1 = 0, estritaAlt2 = 0;    //Variaveis inteiras para a solucao de estrita alternancia

  public static int vez1, vez2;    //Variaveis inteiras para a solucao de Peterson

  public static boolean[] interesse1 = {false, false};   //Array 1 de booleans setados como false para a solucao de Peterson
  public static boolean[] interesse2 = {false, false};   //Array 2 de booleans setados como false para a solucao de Peterson

  /* ***************************************************************
 * Metodo: Construtor
 * Funcao: Define a passagem de parametros para instanciar o objeto
 * Parametros: Trem trem: Do pacote model para gerar o elemento na animacao
 * Retorno: Nao ha retorno
 *************************************************************** */
  public Movimento(Trem trem, int solucao){
    this.trem = trem;
    velocidade = trem.getVelocidade();
    this.solucao = solucao;
  }

  /* ***************************************************************
 * Metodo: getTrem
 * Funcao: Retorna a variavel do tipo Trem
 * Parametros: Nao ha parametros
 * Retorno: Trem trem
 *************************************************************** */
  public Trem getTrem(){
    return trem;
  }

  /* ***************************************************************
 * Metodo: setTrem
 * Funcao: Define a variavel do tipo Trem no objeto de contexto
 * Parametros: Trem trem
 * Retorno: void
 *************************************************************** */
  public void setTrem(Trem trem){
    this.trem = trem;
  }

  /* ***************************************************************
 * Metodo: run
 * Funcao: Eh responsavel pela execucao do laco que utiliza a thread para movimentar os itens animados na scene
 * Parametros: Nao ha parametros
 * Retorno: void
 *************************************************************** */
  @Override
  public void run(){

      while(rodando){

        Platform.runLater(() -> {posicaoEscolhida();});

        try{
          Thread.sleep(15);
        } catch(InterruptedException e) {
          e.printStackTrace();
        }

      }

  }

  /* ***************************************************************
 * Metodo: posicaoEscolhida
 * Funcao: Este metodo faz um switch dos 4 casos possiveis de movimentacao e baseado em cada
 * caso, chama o metodo correspondente
 * Parametros: Nao ha parametros
 * Retorno: void
 *************************************************************** */
  public void posicaoEscolhida(){   

    switch (trem.getPosicao()) {

      case 0: {
        
        if ((trem.getImageView().getLayoutX() >= -81) && (trem.getImageView().getLayoutX() <= 108)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          //Comeco da Zona Critica 1 em layoutX crescente
        } else if ((trem.getImageView().getLayoutX() >= 108) && (trem.getImageView().getLayoutX() <= 190)) {
    
          if(!entraZonaC1(solucao) && !zonaCritica1){return;}
    
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() + velocidade.getValue());
          trem.getImageView().setRotate(45);
    
        } else if ((trem.getImageView().getLayoutX() >= 190) && (trem.getImageView().getLayoutX() <= 243)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setRotate(0);
    
        } else if ((trem.getImageView().getLayoutX() >= 243) && (trem.getImageView().getLayoutX() <= 328)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() - velocidade.getValue());
          trem.getImageView().setRotate(-45);
          //Fim da Zona Critica 1 em layoutX crescente
        } else if ((trem.getImageView().getLayoutX() >= 328) && (trem.getImageView().getLayoutX() <= 457)) {
          saiZonaC1(solucao);
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setRotate(0);
          //Inicio da Zona Critica 2 em layoutX crescente
        } else if ((trem.getImageView().getLayoutX() >= 457) && (trem.getImageView().getLayoutX() <= 544)) {

          if(!entraZonaC2(solucao) && !zonaCritica2){return;}

          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() + velocidade.getValue());
          trem.getImageView().setRotate(45);
    
        } else if ((trem.getImageView().getLayoutX() >= 544) && (trem.getImageView().getLayoutX() <= 608)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setRotate(0);
    
        } else if ((trem.getImageView().getLayoutX() >= 608) && (trem.getImageView().getLayoutX() <= 695)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() - velocidade.getValue());
          trem.getImageView().setRotate(-45);
          //Fim da Zona Critica 2 em layoutX crescente
        } else if ((trem.getImageView().getLayoutX() >= 695) && (trem.getImageView().getLayoutX() <= 889)) {
          saiZonaC2(solucao);
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setRotate(0);
    
        } else {
          trem.getImageView().setLayoutX(-5);
          trem.getImageView().setLayoutY(178);
    
        }

        break;

      }
      case 1: {
        
        if ((trem.getImageView().getLayoutX() >= -81) && (trem.getImageView().getLayoutX() <= 108)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
            //Comeco da Zona Critica 1 em layoutX crescente
        } else if ((trem.getImageView().getLayoutX() >= 108) && (trem.getImageView().getLayoutX() <= 190)) {
    
          if(!entraZonaC1(solucao) && !zonaCritica1){return;}
    
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() - velocidade.getValue());
          trem.getImageView().setRotate(-50);
    
        } else if ((trem.getImageView().getLayoutX() >= 190) && (trem.getImageView().getLayoutX() <= 243)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setRotate(0);
    
        } else if ((trem.getImageView().getLayoutX() >= 243) && (trem.getImageView().getLayoutX() <= 328)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() + velocidade.getValue());
          trem.getImageView().setRotate(50);
            //Fim da Zona Critica 1 em layoutX crescente
        } else if ((trem.getImageView().getLayoutX() >= 328) && (trem.getImageView().getLayoutX() <= 457)) {
          saiZonaC1(solucao);
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setRotate(0);
          //Inicio da Zona Critica 2 em layoutX crescente
        } else if ((trem.getImageView().getLayoutX() >= 457) && (trem.getImageView().getLayoutX() <= 544)) {

          if(!entraZonaC2(solucao) && !zonaCritica2){return;}

          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() - velocidade.getValue());
          trem.getImageView().setRotate(-50);
    
        } else if ((trem.getImageView().getLayoutX() >= 544) && (trem.getImageView().getLayoutX() <= 608)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setRotate(0);
    
        } else if ((trem.getImageView().getLayoutX() >= 608) && (trem.getImageView().getLayoutX() <= 695)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() + velocidade.getValue());
          trem.getImageView().setRotate(50);
          //Fim da Zona Critica 2 em layoutX crescente
        } else if ((trem.getImageView().getLayoutX() >= 695) && (trem.getImageView().getLayoutX() <= 889)) {
          saiZonaC2(solucao);
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() + velocidade.getValue());
          trem.getImageView().setRotate(0);
    
        } else {
          trem.getImageView().setLayoutX(-6);
          trem.getImageView().setLayoutY(342);
    
        }

        break;

      }
      case 2: {
      
        if ((trem.getImageView().getLayoutX() <= 890) && (trem.getImageView().getLayoutX() >= 695)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          //Inicio da Zona Critica 2 em layoutX decrescente
        } else if ((trem.getImageView().getLayoutX() <= 695) && (trem.getImageView().getLayoutX() >= 610)) {

          if(!entraZonaC2(solucao) && !zonaCritica2){return;}

          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() + velocidade.getValue());
          trem.getImageView().setRotate(135);
    
        } else if ((trem.getImageView().getLayoutX() <= 610) && (trem.getImageView().getLayoutX() >= 564)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setRotate(180);
    
        } else if ((trem.getImageView().getLayoutX() <= 564) && (trem.getImageView().getLayoutX() >= 485)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() - velocidade.getValue());
          trem.getImageView().setRotate(225);
          //Fim da Zona Critica 2 em layoutX decrescente
        } else if ((trem.getImageView().getLayoutX() <= 485) && (trem.getImageView().getLayoutX() >= 340)) {
          saiZonaC2(solucao);
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setRotate(180);
          //Inicio da Zona Critica 1 em layoutX decrescente
        } else if ((trem.getImageView().getLayoutX() <= 340) && (trem.getImageView().getLayoutX() >= 263)) {

          if(!entraZonaC1(solucao) && !zonaCritica1){return;}

          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() + velocidade.getValue());
          trem.getImageView().setRotate(130);
    
        } else if ((trem.getImageView().getLayoutX() <= 263) && (trem.getImageView().getLayoutX() >= 213)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setRotate(180);
    
        } else if ((trem.getImageView().getLayoutX() <= 213) && (trem.getImageView().getLayoutX() >= 132)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() - velocidade.getValue());
          trem.getImageView().setRotate(225);
          //Fim da Zona Critica 1 em layoutX decrescente
        } else if ((trem.getImageView().getLayoutX() <= 132) && (trem.getImageView().getLayoutX() >= -67)) {
          saiZonaC1(solucao);
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setRotate(180);
    
        } else {
          trem.getImageView().setLayoutX(890);
          trem.getImageView().setLayoutY(176);
    
        }

        break;

      }
      case 3: {

        if ((trem.getImageView().getLayoutX() <= 890) && (trem.getImageView().getLayoutX() >= 695)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          //Inicio da Zona Critica 2 em layoutX decrescente
        } else if ((trem.getImageView().getLayoutX() <= 695) && (trem.getImageView().getLayoutX() >= 610)) {

          if(!entraZonaC2(solucao) && !zonaCritica2){return;}

          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() - velocidade.getValue());
          trem.getImageView().setRotate(225);
    
        } else if ((trem.getImageView().getLayoutX() <= 610) && (trem.getImageView().getLayoutX() >= 564)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setRotate(180);
    
        } else if ((trem.getImageView().getLayoutX() <= 564) && (trem.getImageView().getLayoutX() >= 485)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() + velocidade.getValue());
          trem.getImageView().setRotate(135);
          //Fim da Zona Critica 2 em layoutX decrescente
        } else if ((trem.getImageView().getLayoutX() <= 485) && (trem.getImageView().getLayoutX() >= 340)) {
          saiZonaC2(solucao);
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setRotate(180);
          //Inicio da Zona Critica 1 em layoutX decrescente
        } else if ((trem.getImageView().getLayoutX() <= 340) && (trem.getImageView().getLayoutX() >= 263)) {

          if(!entraZonaC1(solucao) && !zonaCritica1){return;}

          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() - velocidade.getValue());
          trem.getImageView().setRotate(225);
    
        } else if ((trem.getImageView().getLayoutX() <= 263) && (trem.getImageView().getLayoutX() >= 213)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setRotate(180);
    
        } else if ((trem.getImageView().getLayoutX() <= 213) && (trem.getImageView().getLayoutX() >= 132)) {
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setLayoutY(trem.getImageView().getLayoutY() + velocidade.getValue());
          trem.getImageView().setRotate(135);
          //Fim da Zona Critica 1 em layoutX decrescente
        } else if ((trem.getImageView().getLayoutX() <= 132) && (trem.getImageView().getLayoutX() >= -67)) {
          saiZonaC1(solucao);
          trem.getImageView().setLayoutX(trem.getImageView().getLayoutX() - velocidade.getValue());
          trem.getImageView().setRotate(180);
    
        } else {
          trem.getImageView().setLayoutX(890);
          trem.getImageView().setLayoutY(340);
    
        }

        break;

      }

      default:
        break;

    }

  }

  /* ***************************************************************
 * Metodo: entraZonaC1
 * Funcao: Esse metodo eh chamado nas regioes consideradas de entrada para a Zona Critica 1
 * em seus respectivos movimentos e faz as alteracoes necessarias nas variaveis dos respectivos
 * metodos, as alteracoes especificas sao baseadas no switch case que opera de acordo a solucao 
 * escolhida pelo usuario
 * Parametros: int solucao
 * Retorno: boolean
 *************************************************************** */
  public boolean entraZonaC1(int solucao){

    switch(solucao){    //O switch trata baseado na escolha do metodo de solucao

      case 0: {   //Solucao da Variavel de Travamento
        if(varTravamento1 == 0){
          varTravamento1 = 1;
          zonaCritica1 = true;
          return true;
        } else {
          return false;
        }
      }

      case 1: {    //Solucao da Estrita Alternancia
        if(trem.getPosicao() == 0 || trem.getPosicao() == 2){  //Filtrando o trem de cima como Processo 1
          if(estritaAlt1 != 1){
            zonaCritica1 = true;
            return true;
          } else {
            return false;
          }
        } else {
          if(estritaAlt1 != 0){
            zonaCritica1 = true;
            return true;
          } else {
            return false;
          }
        }
      }

      case 2: {    //Soulacao de Peterson
        if(trem.getPosicao() == 0 || trem.getPosicao() == 2){  //Filtrando o trem de cima como Processo 1
          int pConcorrente = 1-processo1;
          
          interesse1[processo1] = true;
          vez1 = processo1;

          if(interesse1[pConcorrente] == true && vez1 == processo1){
            return false;
          } else {
            zonaCritica1 = true;
            return true;
          }
        } else {
          int pConcorrente = 1-processo2;
        
          interesse1[processo2] = true;
          vez1 = processo2;

          if(interesse1[pConcorrente] == true && vez1 == processo2){
            return false;
          } else {
            zonaCritica1 = true;
            return true;
          }
        }
      }

      default:
        return false;

    }

  }

  /* ***************************************************************
 * Metodo: saiZonaC1
 * Funcao: Esse metodo eh chamado nas regioes consideradas de saida da Zona Critica 1
 * em seus respectivos movimentos e faz as alteracoes necessarias nas variaveis dos respectivos
 * metodos, as alteracoes especificas sao baseadas no switch case que opera de acordo a solucao 
 * escolhida pelo usuario
 * Parametros: int solucao
 * Retorno: void
 *************************************************************** */
  public void saiZonaC1(int solucao){

    switch(solucao){

      case 0: {    //Variavel de Travamento
        varTravamento1 = 0;
        zonaCritica1 = false;
        break;
      }

      case 1: {    //Estrita Alternancia
        if(trem.getPosicao() == 0 || trem.getPosicao() == 2){  //Filtrando o trem de cima como Processo 1
          estritaAlt1 = 1;
          zonaCritica1 = false;
        }

        if(trem.getPosicao() == 1 || trem.getPosicao() == 3){  //Filtrando o trem de baixo como Processo 2
          estritaAlt1 = 0;
          zonaCritica1 = false;
        }
        break;
      }

      case 2: {    //Solucao de Peterson
        if(trem.getPosicao() == 0 || trem.getPosicao() == 2){  //Filtrando o trem de cima como Processo 1
          interesse1[processo1] = false;
          zonaCritica1 = false;
        } else {
          interesse1[processo2] = false;
          zonaCritica1 = false;
        }
        break;
      }

    }

  }

  /* ***************************************************************
 * Metodo: entraZonaC2
 * Funcao: Esse metodo eh chamado nas regioes consideradas de entrada para a Zona Critica 2
 * em seus respectivos movimentos e faz as alteracoes necessarias nas variaveis dos respectivos
 * metodos, as alteracoes especificas sao baseadas no switch case que opera de acordo a solucao 
 * escolhida pelo usuario
 * Parametros: int solucao
 * Retorno: boolean
 *************************************************************** */
  public boolean entraZonaC2(int solucao){

    switch(solucao){    //O switch trata baseado na escolha do metodo de solucao

      case 0: {   //Solucao da Variavel de Travamento
        if(varTravamento2 == 0){
          varTravamento2 = 1;
          zonaCritica2 = true;
          return false;
        } else {
          return false;
        }
      }

      case 1: {    //Solucao da Estrita Alternancia
    
        if(trem.getPosicao() == 0 || trem.getPosicao() == 2){  //Filtrando o trem de cima como Processo 1
          if(estritaAlt2 != 1){
            zonaCritica2 = true;
            return true;
          } else {
            return false;
          }
        } else {
          if(estritaAlt2 != 0){
            zonaCritica2 = true;
            return true;
          } else {
            return false;
          }
        }

      }

      case 2: {    //Soulacao de Peterson
        if(trem.getPosicao() == 0 || trem.getPosicao() == 2){  //Filtrando o trem de cima como Processo 1
          int pConcorrente = 1-processo1;
        
          interesse2[processo1] = true;
          vez2 = processo1;

          if(interesse2[pConcorrente] == true && vez2 == processo1){
            return false;
          } else {
            zonaCritica2 = true;
            return true;
          }
        } else {
          int pConcorrente = 1-processo2;
        
          interesse2[processo2] = true;
          vez2 = processo2;

          if(interesse2[pConcorrente] == true && vez2 == processo2){
            return false;
          } else {
            zonaCritica2 = true;
            return true;
          }
        }
      }

      default:
        return true;

    }

  }

  /* ***************************************************************
 * Metodo: saiZonaC2
 * Funcao: Esse metodo eh chamado nas regioes consideradas de saida da Zona Critica 2
 * em seus respectivos movimentos e faz as alteracoes necessarias nas variaveis dos respectivos
 * metodos, as alteracoes especificas sao baseadas no switch case que opera de acordo a solucao 
 * escolhida pelo usuario
 * Parametros: int solucao
 * Retorno: void
 *************************************************************** */
  public void saiZonaC2(int solucao){

    switch(solucao){

      case 0: {    //Variavel de Travamento
        varTravamento2 = 0;
        zonaCritica2 = false;
        break;
      }

      case 1: {    //Estrita Alternancia
        if(trem.getPosicao() == 0 || trem.getPosicao() == 2){  //Filtrando o trem de cima como Processo 1
          estritaAlt2 = 1;
          zonaCritica2 = false;
        }

        if(trem.getPosicao() == 1 || trem.getPosicao() == 3){  //Filtrando o trem de baixo como Processo 2
          estritaAlt2 = 0;
          zonaCritica2 = false;
        }
        break;
      }

      case 2: {    //Solucao de Peterson
        if(trem.getPosicao() == 0 || trem.getPosicao() == 2){  //Filtrando o trem de cima como Processo 1
          interesse2[processo1] = false;
          zonaCritica2 = false;
        } else {
          interesse2[processo2] = false;
          zonaCritica2 = false;
        }
        break;
      }

    }

  }

}