import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

# Analisi distribuzione del tempo di attesa in coda degli ordini
# In questa sezione viene analizzato il tempo di attesa in coda degli ordini, ossia il tempo che passa da quando
# un ordine viene effettuato a quando viene esce dalla coda per andare in cucina, non viene considerato quindi
# il tempo di preparazione e il tempo di attesa in cucina.
#
# Viene utilizzato un istogramma con il quale si mostra la frequenza con la quale i vari ordini presentano un certo
# slot di tempo di attesa, vengono inoltre calcolate e mostrate: media, deviazione standard, mediana,
# 25° e 75° percentili per una maggiore comprensione.
#
# In un ulteriore istogramma poi si raggruppano queste frequenze in base alla priorità,
# con valori di Alta, Media e Bassa priorità per capire come l'assegnazione della priorità da parte dell'algoritmo
# influenza il tempo di attesa di preparazione.


def generate_t_coda_histograms_from_csv(file_csv, output_file):
    # Importo il file csv
    df = pd.read_csv(file_csv)

    # Considero solo le occorrenze dalla 20a in poi
    df = df.iloc[20:]

    # Calcola la media del tempo di attesa
    media_tempo_attesa = df['t_coda'].mean()
    print("Media tempo di attesa = " + "{:.3f}".format(media_tempo_attesa))

    # Calcola la deviazione standard del tempo di attesa
    deviazione_standard_tempo_attesa = df['t_coda'].std()
    print("Deviazione standard tempo di attesa = " + "{:.3f}".format(deviazione_standard_tempo_attesa))

    # Calcola la mediana del tempo di attesa
    mediana_tempo_attesa = df['t_coda'].median()
    print("Mediana standard tempo di attesa = " + "{:.3f}".format(mediana_tempo_attesa))

    # Calcola i percentili 25° e 75° del tempo di attesa
    percentile_25 = np.percentile(df['t_coda'], 25)
    percentile_75 = np.percentile(df['t_coda'], 75)
    print("25° percentile tempo di attesa = " + "{:.3f}".format(percentile_25))
    print("75° percentile tempo di attesa = " + "{:.3f}".format(percentile_75))

    # Suddivide i dati in base alla priorità degli ordini
    priorita_bassa = df[df['priorita_iniz'] < 30]
    priorita_media = df[(df['priorita_iniz'] <= 70) & (df['priorita_iniz'] >= 30)]
    priorita_alta = df[df['priorita_iniz'] > 70]

    # Modifica la dimensione del grafico
    plt.figure(figsize=(10, 6))

    # Creazione della prima area di disegno
    plt.subplot(2, 1, 1)

    # Genera l'istogramma del tempo di attesa in coda
    plt.hist(df['t_coda'], bins=10, color='skyblue', edgecolor='black')

    # Aggiungi titoli e etichette
    plt.title('Distribuzione del Tempo di Attesa in coda degli Ordini')
    plt.xlabel('Tempo di Attesa in coda [s]')
    plt.ylabel('Frequenza')

    # Aggiungi linea verticale per la media
    plt.axvline(media_tempo_attesa, color='red', linestyle='dashed', linewidth=1, label='Media')

    # Aggiungi linee verticali per la deviazione standard
    plt.axvline(media_tempo_attesa + deviazione_standard_tempo_attesa, color='orange', linestyle='dashed', linewidth=1,
                label='Media + Deviazione Standard')
    plt.axvline(media_tempo_attesa - deviazione_standard_tempo_attesa, color='orange', linestyle='dashed', linewidth=1,
                label='Media - Deviazione Standard')

    # Aggiungi linea verticale per la mediana
    plt.axvline(mediana_tempo_attesa, color='darkblue', linestyle='dashed', linewidth=1, label='Mediana')

    # Aggiungi linee verticali per i percentili 25° e 75°
    plt.axvline(percentile_25, color='blue', linestyle='dashed', linewidth=1, label='25° Percentile')
    plt.axvline(percentile_75, color='blue', linestyle='dashed', linewidth=1, label='75° Percentile')

    # Aggiungi legenda al di fuori del grafico
    plt.legend(loc='center left', bbox_to_anchor=(1, 0.5))

    # Creazione della seconda area di disegno
    plt.subplot(2, 1, 2)

    # Genera gli istogrammi delle frequenze per ciascun gruppo di priorità
    plt.hist(priorita_bassa['t_coda'], bins=10, color='skyblue', edgecolor='black', alpha=0.7,
             label='Priorità bassa')
    plt.hist(priorita_media['t_coda'], bins=10, color='lightgreen', edgecolor='black', alpha=0.7,
             label='Priorità media')
    plt.hist(priorita_alta['t_coda'], bins=10, color='salmon', edgecolor='black', alpha=0.7,
             label='Priorità alta')

    # Aggiungi titoli e etichette
    plt.title('Distribuzione del Tempo di Attesa in coda degli Ordini per Priorità')
    plt.xlabel('Tempo di Attesa in coda[s]')
    plt.ylabel('Frequenza')

    # Aggiungi legenda al di fuori del grafico
    plt.legend(loc='center left', bbox_to_anchor=(1, 0.5))

    # Mostra i grafici sovrapposti
    plt.tight_layout()
    plt.savefig(output_file, bbox_inches='tight')

    plt.show()


if __name__ == "__main__":
    # Codice che viene eseguito solo quando il modulo è eseguito direttamente
    file = r"..\..\output\csv\waiting_time.csv"  # Specifica il nome del file CSV
    image_t_coda_histograms_file = r"..\..\output\images\t_coda_histograms.png"  # output file
    generate_t_coda_histograms_from_csv(file, image_t_coda_histograms_file)