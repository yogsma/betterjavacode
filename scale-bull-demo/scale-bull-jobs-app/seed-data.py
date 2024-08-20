import csv
import random
from faker import Faker

# Initialize Faker
fake = Faker()

# Number of records
num_records = 10000

# Generate CSV file
filename = "employee_data.csv"

with open(filename, 'w', newline='') as csvfile:
    fieldnames = ['employeeId', 'firstName', 'lastName', 'email']
    writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

    writer.writeheader()
    for i in range(num_records):
        first_name = fake.first_name()
        last_name = fake.last_name()
        writer.writerow({
            'employeeId': f'EMP{i+1:05d}',
            'firstName': first_name,
            'lastName': last_name,
            'email': f'{first_name.lower()}.{last_name.lower()}@example.com'
        })

print(f"CSV file '{filename}' with {num_records} records has been generated.")