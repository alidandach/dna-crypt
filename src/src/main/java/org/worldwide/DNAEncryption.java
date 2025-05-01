package org.worldwide;

public class DNAEncryption {

	private static final String[] BINARY_TO_DNA = {"A", "C", "G", "T"};
	private static final String[] DNA_TO_BINARY = {"00", "01", "10", "11"};

	// Encrypt using XOR on DNA bases
	public String encrypt(String plainText, String key) {
		String binaryPlain = toBinary(plainText);
		String binaryKey = toBinary(key);

		// Pad key to match plaintext length
		while (binaryKey.length() < binaryPlain.length()) {
			binaryKey += binaryKey;
		}
		binaryKey = binaryKey.substring(0, binaryPlain.length());

		StringBuilder encryptedBinary = new StringBuilder();
		for (int i = 0; i < binaryPlain.length(); i += 2) {
			String pBits = binaryPlain.substring(i, i + 2);
			String kBits = binaryKey.substring(i, i + 2);
			String xorBits = xorBits(pBits, kBits);
			encryptedBinary.append(xorBits);
		}

		return binaryToDna(encryptedBinary.toString());
	}

	// Decrypt is identical to encrypt since XOR is symmetric
	public String decrypt(String dnaCipherText, String key) {
		String binaryCipher = dnaToBinary(dnaCipherText);
		String binaryKey = toBinary(key);

		// Pad key to match ciphertext length
		while (binaryKey.length() < binaryCipher.length()) {
			binaryKey += binaryKey;
		}
		binaryKey = binaryKey.substring(0, binaryCipher.length());

		StringBuilder decryptedBinary = new StringBuilder();
		for (int i = 0; i < binaryCipher.length(); i += 2) {
			String cBits = binaryCipher.substring(i, i + 2);
			String kBits = binaryKey.substring(i, i + 2);
			String xorBits = xorBits(cBits, kBits);
			decryptedBinary.append(xorBits);
		}

		return fromBinary(decryptedBinary.toString());
	}

	// XOR two 2-bit strings
	private String xorBits(String a, String b) {
		int aInt = Integer.parseInt(a, 2);
		int bInt = Integer.parseInt(b, 2);
		int result = aInt ^ bInt;
		return String.format("%2s", Integer.toBinaryString(result)).replace(' ', '0');
	}

	// Convert plaintext to binary string
	private String toBinary(String text) {
		StringBuilder binary = new StringBuilder();
		for (char c : text.toCharArray()) {
			binary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
		}
		return binary.toString();
	}

	// Convert binary string to plaintext
	private String fromBinary(String binary) {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < binary.length(); i += 8) {
			String byteStr = binary.substring(i, i + 8);
			text.append((char) Integer.parseInt(byteStr, 2));
		}
		return text.toString();
	}

	// Convert binary string to DNA string
	private String binaryToDna(String binary) {
		StringBuilder dna = new StringBuilder();
		for (int i = 0; i < binary.length(); i += 2) {
			int idx = Integer.parseInt(binary.substring(i, i + 2), 2);
			dna.append(BINARY_TO_DNA[idx]);
		}
		return dna.toString();
	}

	// Convert DNA string to binary string
	private String dnaToBinary(String dna) {
		StringBuilder binary = new StringBuilder();
		for (char base : dna.toCharArray()) {
			switch (base) {
				case 'A': binary.append("00"); break;
				case 'C': binary.append("01"); break;
				case 'G': binary.append("10"); break;
				case 'T': binary.append("11"); break;
				default: throw new IllegalArgumentException("Invalid DNA base: " + base);
			}
		}
		return binary.toString();
	}
}
