# DNA Crypt

🧬 A simple Java implementation of DNA-based cryptography, simulating the encoding and decoding of text using DNA nucleotide mapping.

## 🔐 Overview

`dna-crypt` is a lightweight demonstration of a conceptual encryption scheme inspired by DNA encoding. It converts plain text to a sequence of DNA nucleotides (A, C, G, T) by mapping binary values, then decrypts it back to the original message.

This project does **not** require any biological hardware or DNA synthesis — it simulates the process for educational and illustrative purposes.

## 🧪 DNA Mapping Logic

- Each 2-bit binary value is mapped to a DNA base:
    - `00` → `A`
    - `01` → `C`
    - `10` → `G`
    - `11` → `T`

Characters are converted to their 8-bit binary form, then split into 2-bit segments and mapped accordingly.

## 🚀 Features

- Encrypt any plain text into a simulated DNA sequence
- Decrypt the DNA sequence back into readable text
- Clean, readable Java implementation
- Suitable for educational demos in cryptography and bioinformatics

## 📦 How to Use

### Prerequisites

- Java 21 or higher
- Any Java IDE or CLI

### Running the Program

1. Clone the repository:

```shell
git clone https://github.com/alidandach/dna-crypt
```

```shell
cd dna-crypt
```