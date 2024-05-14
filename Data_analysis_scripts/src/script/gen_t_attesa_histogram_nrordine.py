import matplotlib.pyplot as plt
import pandas as pd


def generate_t_coda_histograms_nr_ordine_from_csv(file_csv, output_file):
    # Importo il file csv
    df = pd.read_csv(file_csv)

    # Considero solo le occorrenze dalla 20a in poi
    df = df.iloc[20:]

    # Suddivide i dati in base al numero di ordinazione progressivo
    ordini_iniziali = df[df['Numero'] < 55] # si parte da 20
    ordini_medi = df[(df['Numero'] <= 85) & (df['Numero'] >= 55)]
    ordini_finali = df[df['Numero'] > 85]

    # Modifica la dimensione del grafico
    # plt.figure(figsize=(10, 6))

    # Genera gli istogrammi delle frequenze per ciascun gruppo di priorità
    plt.hist(ordini_iniziali['t_coda'], bins=10, color='salmon', edgecolor='black', alpha=0.7,
              label='Ordini Iniziali')
    plt.hist(ordini_medi['t_coda'], bins=10, color='lightgreen', edgecolor='black', alpha=0.7,
              label='Oridini a regime')
    plt.hist(ordini_finali['t_coda'], bins=10, color='skyblue', edgecolor='black', alpha=0.7,
              label='Ordini finali')

    # Aggiungi titoli e etichette
    plt.title('Distribuzione del Tempo di Attesa in coda degli Ordini per Numero progressivo')
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
    image_t_coda_histograms_nr_ordine_file = r"..\..\output\images\t_coda_histograms_nr_ordine.png"  # output file
    generate_t_coda_histograms_nr_ordine_from_csv(file, image_t_coda_histograms_nr_ordine_file)
