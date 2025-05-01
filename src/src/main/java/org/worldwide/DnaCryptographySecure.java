package org.worldwide;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class DnaCryptographySecure {

	private static final SecureRandom secureRandom = new SecureRandom();
	private static final String[] BINARY_TO_DNA = {"A", "C", "G", "T"};

	// Generate a 256-bit AES key from password
	private static SecretKeySpec deriveKey(String password, byte[] salt) throws Exception {
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] key = factory.generateSecret(spec).getEncoded();
		return new SecretKeySpec(key, "AES");
	}

	// Encrypt text to DNA-like string using AES
	public String encrypt(String plainText, String password) throws Exception {
		byte[] salt = new byte[16];
		byte[] iv = new byte[16];
		secureRandom.nextBytes(salt);
		secureRandom.nextBytes(iv);

		SecretKeySpec key = deriveKey(password, salt);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
		byte[] cipherBytes = cipher.doFinal(plainText.getBytes());

		// Combine salt + IV + ciphertext for full data
		byte[] fullData = new byte[salt.length + iv.length + cipherBytes.length];
		System.arraycopy(salt, 0, fullData, 0, salt.length);
		System.arraycopy(iv, 0, fullData, salt.length, iv.length);
		System.arraycopy(cipherBytes, 0, fullData, salt.length + iv.length, cipherBytes.length);

		// Convert to Base64, then to DNA-style string
		return binaryToDna(base64ToBinary(Base64.getEncoder().encodeToString(fullData)));
	}

	// Decrypt DNA string using AES
	public String decrypt(String dnaString, String password) throws Exception {
		String base64 = binaryToBase64(dnaToBinary(dnaString));
		byte[] fullData = Base64.getDecoder().decode(base64);

		byte[] salt = new byte[16];
		byte[] iv = new byte[16];
		byte[] cipherBytes = new byte[fullData.length - 32];

		System.arraycopy(fullData, 0, salt, 0, 16);
		System.arraycopy(fullData, 16, iv, 0, 16);
		System.arraycopy(fullData, 32, cipherBytes, 0, cipherBytes.length);

		SecretKeySpec key = deriveKey(password, salt);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
		byte[] decryptedBytes = cipher.doFinal(cipherBytes);

		return new String(decryptedBytes);
	}

	// Converts binary string to DNA sequence
	private String binaryToDna(String binary) {
		StringBuilder dna = new StringBuilder();
		for (int i = 0; i < binary.length(); i += 2) {
			String pair = binary.substring(i, i + 2);
			int idx = Integer.parseInt(pair, 2);
			dna.append(BINARY_TO_DNA[idx]);
		}
		return dna.toString();
	}

	// Converts DNA sequence back to binary string
	private String dnaToBinary(String dna) {
		StringBuilder binary = new StringBuilder();
		for (char base : dna.toCharArray()) {
			switch (base) {
				case 'A': binary.append("00"); break;
				case 'C': binary.append("01"); break;
				case 'G': binary.append("10"); break;
				case 'T': binary.append("11"); break;
				default: throw new IllegalArgumentException("Invalid DNA character: " + base);
			}
		}
		return binary.toString();
	}

	// Convert Base64 string to binary
	private String base64ToBinary(String base64) {
		StringBuilder binary = new StringBuilder();
		byte[] bytes = base64.getBytes();
		for (byte b : bytes) {
			binary.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
		return binary.toString();
	}

	// Convert binary to Base64
	private String binaryToBase64(String binary) {
		byte[] bytes = new byte[binary.length() / 8];
		for (int i = 0; i < bytes.length; i++) {
			String byteStr = binary.substring(i * 8, (i + 1) * 8);
			bytes[i] = (byte) Integer.parseInt(byteStr, 2);
		}
		return new String(bytes);
	}
}
