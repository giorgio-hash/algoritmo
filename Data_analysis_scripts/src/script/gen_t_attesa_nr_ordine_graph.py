import pandas as pd
import matplotlib.pyplot as plt

# Analisi Tempo di attesa in base al numero degli ordini
# Come primo passo viene analizzato come il tempo di attesa varia con l'avanzare del numero di ordini.
#
# Viene utilizzato un grafico a linea per mostrare (in arancione) il tempo di attesa per numero di ordine e (in blu)
# i valori filtrati con un filtro a media mobile con finestra = 10 valori


def generate_t_attesa_line_plot_from_csv(file_csv, output_file):
    # Carica il dataset da un file CSV
    df = pd.read_csv(file_csv)

    df.loc[:, 't_attesa-prep'] = df['tempo_attesa'] - df['t_prep']

    # Applica il filtro a media mobile ai dati di tempo di attesa utilizzando una finestra di 10 punti
    df['tempo_attesa_filtrato'] = df['t_attesa-prep'].rolling(window=10, min_periods=1).mean()

    # Crea il grafico a linea del tempo di attesa per numero di ordine con il filtro a media mobile
    plt.plot(df['Numero'], df['tempo_attesa_filtrato'], marker='o', linestyle='-', label='Media Mobile')

    # Crea il grafico a linea del tempo di attesa per numero di ordine senza il filtro
    plt.plot(df['Numero'], df['t_attesa-prep'], marker='o', linestyle='-', label='Senza Filtro')

    # Aggiungi titolo e etichette degli assi
    plt.title('Tempo di Attesa in base al numero di ordini nel tempo')
    plt.xlabel('Numero di Ordine')
    plt.ylabel('Tempo di Attesa [s]')

    # Aggiungi legenda
    plt.legend()

    # Mostra il grafico
    plt.grid(True)
    plt.savefig(output_file, bbox_inches='tight')

    plt.show()


if __name__ == "__main__":
    # Codice che viene eseguito solo quando il modulo è eseguito direttamente
    file = r"..\..\output\csv\waiting_time.csv"  # Specifica il nome del file CSV
    image_t_attesa_line_plot_file = r"..\..\output\images\t_attesa_line_plot.png"  # output file
    generate_t_attesa_line_plot_from_csv(file, image_t_attesa_line_plot_file)
