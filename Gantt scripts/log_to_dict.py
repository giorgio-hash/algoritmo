import csv
import json
#import plotly.figure_factory as ff
#import pandas as pd


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

def process_dicts(dict_list):
    # Group dictionaries by 'uuid'
    uuid_dict = {}
    for d in dict_list:
        uuid = d['uuid']
        if uuid not in uuid_dict:
            uuid_dict[uuid] = []
        uuid_dict[uuid].append(d)

    # Process each pair of dictionaries
    for d in dict_list:
        result_list = []
        for uuid, dicts in uuid_dict.items():
            if len(dicts) == 2:
                # Find the dictionary with 'state' == 'end'
                end_dict = next((d for d in dicts if d['state'] == 'end'), None)
                if end_dict is not None:
                    # Perform subtraction on 'secs' field
                    secs_diff = abs(int(dicts[0]['secs']) - int(dicts[1]['secs']))
                    # Create new dictionary
                    new_dict = end_dict.copy()
                    new_dict['secs'] = str(secs_diff)
                    result_list.append(new_dict)

        return result_list

def min_secs(dict_list):
    secs = 0
    for d in dict_list:
        if secs == 0:
            secs = int(d["secs"])
        elif secs > int(d["secs"]):
            secs = int(d["secs"])
    
    return secs

def post_process(dict_list):
    #trova il minimo assoluto
    min = min_secs(dict_list) 
    
    for d in dict_list:
        d["secs"] = "{}".format(int(d["secs"]) - min)

    return dict_list
    

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


### generazione di input relativo
input_list_of_dicts = post_process(input_list_of_dicts)
# txt input
with open('relative_input.txt', 'w') as f:
    for dict_item in input_list_of_dicts:
        # Use json.dumps() to convert dictionary to string
        # Each dictionary will be written on a new line
        f.write(json.dumps(dict_item) + '\n')

# csv input
with open('relative_input.csv', 'w', newline='') as f:
    # Assuming that all dictionaries in the list have the same keys
    keys = input_list_of_dicts[0].keys()
    
    writer = csv.DictWriter(f, fieldnames=keys)
    
    # Write the keys as the header of the CSV file
    writer.writeheader()
    
    # Write the dictionaries into the CSV file
    writer.writerows(input_list_of_dicts)


###generazione output
output_list_of_dicts = process_dicts(input_list_of_dicts)
# txt output
with open('diffs.txt', 'w') as f:
    for dict_item in output_list_of_dicts:
        f.write(json.dumps(dict_item) + '\n')


# csv input
with open('output.csv', 'w', newline='') as f:
    keys = output_list_of_dicts[0].keys()
    
    writer = csv.DictWriter(f, fieldnames=keys)
    
    # Write the keys as the header of the CSV file
    writer.writeheader()
    
    # Write the dictionaries into the CSV file
    writer.writerows(output_list_of_dicts)
