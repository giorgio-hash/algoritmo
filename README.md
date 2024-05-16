# Algoritmo 
Sviluppo algoritmo multi-thread con buffer di schedulazione per [ServeEasy](https://github.com/giorgio-hash/ServeEasy).

## Javadoc 
La documentazione Javadoc è disponibile a questo link: https://giorgio-hash.github.io/algoritmo/javadoc/ .

## Analisi prestazionali
Qui di seguito sono riportati dei link verso i Google Colab utilizzati per fare inferenza sulle simulazioni.
- Analisi schedulazione thread e rappresentazione Gantt disponibile [qui](https://colab.research.google.com/drive/1hyGN4p6SS00ENY7n0lPa2CBTDqbqRAC_?usp=sharing).
  ![immagine Grantt Colab](./media/gif1.gif) 
<!-- Creata v2 del Notebook Colab questo link è ancora accessibile ma porta alla versione 1
- Inferenza per verificare il requisito non funzionale delle performance sul tempo utile disponibile [qui](https://colab.research.google.com/drive/1PZyxKQf85-XFKB7PsUqYRUQSwh0I-cuO?usp=sharing). -->
- Inferenza per verificare il requisito non funzionale delle performance sul tempo utile disponibile [qui](https://colab.research.google.com/drive/1vJyO7_P-xOQ7RNWgx4I4YvhqxD3cgvAF?usp=sharing).
  ![immagine Inferenza Colab](./media/gif2.gif)

### Script Python
In questa repository è inoltre è presente uno script python per fare data analysis localmente generando i grafici associati 
al file report in formato  ```csv ```, per poterlo avviare è necessario avere python installato sulla propria macchina ed eseguire il seguente comando
per installare le librerie necessarie (dalla root del progetto):
```shell
 pip install -r Data_analysis_scripts/requirements.txt
 ```
Succesivamente eseguire il seguente per poter avviare lo script (dalla root del progetto):
```shell
 python Data_analysis_scripts/src/main.py
 ```
Le immagini png dei grafici verranno salvate in ```output/images```.

