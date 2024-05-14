import csv
import json


def process_file(filename):
    output = []
    with open(filename, 'r') as file:
        for line in file:
            if ":::" in line:
                # Take only the string positioned before ":::"
                pre_string = line.split(":::")[0].strip()
                # Check if the string is in the form of "X:x-Y:y-Z:z"
                if '-' in pre_string and ':' in pre_string:
                    # Divide the string into substrings on the divisor character '-'
                    substrings = pre_string.split('-')
                    # Convert the substrings into a dictionary
                    dictionary = {s.split(':')[0]: s.split(':')[1] for s in substrings}
                    output.append(dictionary)
    return output
    

filename = 'logs.txt' 
input_list_of_dicts = process_file(filename)

# txt input
with open('input.txt', 'w') as f:
    for dict_item in input_list_of_dicts:
        # Use json.dumps() to convert dictionary to string
        # Each dictionary will be written on a new line
        f.write(json.dumps(dict_item) + '\n')

# csv input
with open('input.csv', 'w', newline='') as f:
    # Assuming that all dictionaries in the list have the same keys
    keys = input_list_of_dicts[0].keys()
    
    writer = csv.DictWriter(f, fieldnames=keys)
    
    # Write the keys as the header of the CSV file
    writer.writeheader()
    
    # Write the dictionaries into the CSV file
    writer.writerows(input_list_of_dicts)


