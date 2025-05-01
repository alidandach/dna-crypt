package org.worldwide;

public class Main {
	public static void main(String[] args) {
		DNAEncryption dna = new DNAEncryption();
		String plaintext = "HELLO DNA";
		String key = "key123";

		String encrypted = dna.encrypt(plaintext, key);
		String decrypted = dna.decrypt(encrypted, key);

		System.out.println("Plain:     " + plaintext);
		System.out.println("Encrypted: " + encrypted);
		System.out.println("Decrypted: " + decrypted);
	}
}