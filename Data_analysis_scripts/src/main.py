import os
from pathlib import Path
import script.gen_t_attesa_nr_ordine_graph as l1
import script.gen_t_attesa_histograms as h1
import script.gen_t_coda_histograms as h2
import script.gen_t_attesa_histogram_nrordine as h3
import script.gen_scatterd_plot as s1

def check_and_create_folder(folder):
    path_folder = Path(folder)
    if not path_folder.exists():
        # create the folder if not exists
        path_folder.mkdir(parents=True, exist_ok=True)


# Get the directory of the script main.py
script_dir = os.path.dirname(os.path.abspath(__file__))
# Build the complete path of the CSV file from the script directory
input_csv_file = os.path.join(script_dir, r"..\..\output\csv", "waiting_time.csv")
# name of the output files
check_and_create_folder(Path("output")/"images")
image_t_attesa_line_plot_file = os.path.join(script_dir,r"..\..\output\images\t_attesa_line_plot.png")  # output file
image_t_attesa_histograms_file = os.path.join(script_dir,r"..\..\output\images\t_attesa_histograms.png")  # output file
image_t_coda_histograms_file = os.path.join(script_dir,r"..\..\output\images\t_coda_histograms.png")  # output file
image_t_coda_histograms_nr_ordine_file = os.path.join(script_dir,r"..\..\output\images\t_coda_histograms_nr_ordine.png")  # output file
image_scattered_plot_file = os.path.join(script_dir,r"..\..\output\images\scattered_plot.png")  # output file
# Generate the plots
l1.generate_t_attesa_line_plot_from_csv(input_csv_file, image_t_attesa_line_plot_file)
h1.generate_t_attesa_histograms_from_csv(input_csv_file, image_t_attesa_histograms_file)
h2.generate_t_coda_histograms_from_csv(input_csv_file, image_t_coda_histograms_file)
h3.generate_t_coda_histograms_nr_ordine_from_csv(input_csv_file, image_t_coda_histograms_nr_ordine_file)
s1.generate_scattered_plot_from_csv(input_csv_file, image_scattered_plot_file)
