
import io
import subprocess
import sys
import pandas as pd

df = pd.read_excel(r"C:\Users\Kiet\OneDrive\Máy tính\Thesis\dataset\dataset_score.xlsx")

df.TenMH = df.TenMH.astype('string')
df.DiemHP = df.DiemHP.replace('VT', '0')
df['DiemHP'] = pd.to_numeric(df['DiemHP'], errors='coerce')

options = [ ' Algorithms & Data Structures']
result_dataset = df[df['TenMH'].isin(options)]

Student_Id = result_dataset.MaSV

result_dataset_2 = df[df['MaSV'].isin(Student_Id)]

pivot_table = pd.pivot_table(result_dataset_2, values = 'DiemHP', index = 'MaSV', columns='TenMH')
pivot_table.fillna(0)
pivot_table.to_csv(r"C:\Users\pass-fail-rate-prediction-\src\main\resources\Dataset\pivot.csv")

