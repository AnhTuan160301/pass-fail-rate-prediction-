
import io
import subprocess
import sys
import pandas as pd
import numpy as np

arg = sys.argv[1]
df = pd.read_excel(arg,engine='openpyxl')

df.TenMH = df.TenMH.astype('string')
df.DiemHP = df.DiemHP.replace('VT', '0')
df['DiemHP'] = pd.to_numeric(df['DiemHP'], errors='coerce')

options = [ ' Algorithms & Data Structures']
result_dataset = df[df['TenMH'].isin(options)]

Student_Id = result_dataset.MaSV

result_dataset_2 = df[df['MaSV'].isin(Student_Id)]

pivot_table = pd.pivot_table(result_dataset_2, values = 'DiemHP', index = 'MaSV', columns='TenMH')
pivot_table.fillna(0, inplace = True)

conditions = {
    '0': pivot_table[' Algorithms & Data Structures'] < 50,
    '1': pivot_table[' Algorithms & Data Structures'] >= 50
}
pivot_table[' Algorithms & Data Structures'] = np.select(conditions.values(), conditions.keys(), default = pivot_table[' Algorithms & Data Structures'] )

options_1 = [' C/C++ Programming',
             ' Calculus 1',
             ' Calculus 2',
             ' Critical Thinking',
             ' Digital Logic Design',
             ' Digital Logic Design Laboratory',
             ' Discrete Mathematics',
             ' Introduction to Computing',
             ' Object-Oriented Programming',
             ' Physics 1',
             ' Writing AE1',
             ' Algorithms & Data Structures']
pivot_table = pivot_table[options_1]
pivot_table.replace('',0)
pivot_table.to_csv(r"C:\Users\pass-fail-rate-prediction-\src\main\resources\Dataset\pivot.csv", index=False)

