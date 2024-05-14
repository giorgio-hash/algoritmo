import matplotlib.pyplot as plt
import pandas as pd

# Analisi dei parametri in base al tempo totale in attesa
# In questo passo viene analizzato come i 5 parametri su cui si basa la priorità influiscano sul tempo di
# attesa di un ordine nel sistema, cioè da quando viene effettuato a quando viene completato.
# I parametri in considerazione sono:
#
# ingrediente principale, indica la postazione della cucina riservata a quel particolare ordine;
# tempo di preparazione, rappresenta la durata stimata necessaria per preparare un determinato ordine;
# urgenza del cliente, consente ai clienti di specificare la tempestività con cui desiderano ricevere il proprio ordine;
# numero ordine effettuato, specifica il numero dell’ordine del cliente in ordine temporale (posizione relativa di un
# ordine all’interno della sequenza di ordini effettuati da egli stesso);
# tempo in attesa in coda, rappresenta il periodo di tempo trascorso da quando un ordine è stato effettuato fino al
# momento in cui viene elaborato.


def generate_scattered_plot_from_csv(file_csv, output_file):
    df = pd.read_csv(file_csv)

    x1 = df['ingr_principale']
    x2 = df['t_prep']
    x3 = df['urgenza']
    x4 = df['numero_ordine_eff']
    x5 = df['t_coda']
    y = df['tempo_attesa']

    # Crea il plot
    plt.figure(figsize=(10, 6))

    # Primo scatter plot con la media
    plt.subplot(2, 3, 1)
    plt.scatter(x1, y, color='blue')
    plt.title('Ingrediente Principale')
    plt.xlabel('Tipo di ingrediente principale')
    plt.ylabel('Tempo totale in attesa [s]')

    # Secondo scatter plot
    plt.subplot(2, 3, 2)
    plt.scatter(x2, y, color='orange')
    plt.title('Tempo di preparazione')
    plt.xlabel('Tempo di preparazione ordine [s]')
    plt.ylabel('Tempo totale in attesa [s]')

    # Terzo scatter plot
    plt.subplot(2, 3, 3)
    plt.scatter(x3, y, color='green')
    plt.title('Urgenza cliente')
    plt.xlabel('0: Non urgente, 1: Urgente')
    plt.ylabel('Tempo totale in attesa [s]')

    # Quarto scatter plot
    plt.subplot(2, 3, 4)
    plt.scatter(x4, y, color='purple')
    plt.title('Numero ordine effettuato')
    plt.xlabel('Numero ordine effettuato')
    plt.ylabel('Tempo totale in attesa [s]')

    # Quinto scatter plot
    plt.subplot(2, 3, 5)
    plt.scatter(x5, y, color='brown')
    plt.title('Tempo di Attesa in coda')
    plt.xlabel('Tempo di Attesa in coda [s]')
    plt.ylabel('Tempo totale in attesa [s]')

    # Aggiungi una label al grafo esterno
    plt.suptitle('Analisi dei parametri in base al tempo totale in attesa', fontsize=16)

    # Mostra il grafico
    plt.tight_layout()
    plt.savefig(output_file, bbox_inches='tight')

    plt.show()


if __name__ == "__main__":
    # Codice che viene eseguito solo quando il modulo è eseguito direttamente
    file = r"..\..\output\csv\waiting_time.csv"  # Specifica il nome del file CSV
    image_scattered_plot_file = r"..\..\output\images\scattered_plot.png"  # output file
    generate_scattered_plot_from_csv(file, image_scattered_plot_file)
