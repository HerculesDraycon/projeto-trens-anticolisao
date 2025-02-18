# Trenzinhos na UESB

![about](https://img.icons8.com/?size=40&id=3439&format=png&color=FFFFFF)
#### Este projeto é a implementação em JavaFX de um problema de IPC (Inter-Process-Communication) simulando dois trens que podem estar em rota de colisão para acessar o mesmo trilho e foi proposto na disciplina de Programação Concorrente no 3º Semestre do curso de Bacharelado em Ciência da Computação da Universidade Estadual do Sudoeste da Bahia. <br>O problema proposto trata das Threads de trens que percorrem o caminho em seus respectivos trilhos, entretanto há um momento em que são desviados para percorrer um único trilho que cede a passagem para todos os trens. <br>Para solucionar esse problema, evitando a colisão das locomotivas, são utilizados três métodos clássicos que podem ser escolhidos na interface e cada um deles opera uma forma de não permitir que dois processos acessem mutuamente a região crítica (que é o trilho compartilhado). <br>São dois trens concorrentes em que o usuário pode alterar ou pausar a velocidade de cada trem e escolher entre 4 posicionamentos diferentes de percurso, sendo dois deles com trens no mesmo sentido e dois deles com trens em sentidos opostos.

<mark> Reprodução parcial do programa em funcionamento: <mark/><br>
![gif](https://github.com/HerculesDraycon/projeto-trens-anticolisao/blob/main/img/gif.gif)

![corporative](https://img.icons8.com/?size=40&id=VZEOwb3lft8h&format=png&color=FFFFFF)
#### Projeto realizado por Hércules S. Oliveira, Graduando em Bacharelado em Ciência da Computação pela Universidade Estadual do Sudoeste da Bahia. <br>Docente da matéria e Orientador do projeto: Professor Marlos André Marques Simões de Oliveira.
Junho do 2024

_________________________________________________________________________________________

![download](https://img.icons8.com/?size=50&id=86327&format=png&color=FFFFFF)
- Sua máquina deve conter uma versão do Java compatível para garantia de que o código será compilado e executado. Versões do JDK 8 são compatíveis.
- Baixe o repositório
- Descompacte a pasta gerada
- No diretório raiz da pasta descompactada, compile e execute o arquivo Principal.java
