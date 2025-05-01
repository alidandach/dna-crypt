package org.worldwide;

public class Main {
	public static void main(String[] args) throws Exception {
		DnaCryptographySecure crypto = new DnaCryptographySecure();
		String key = "StrongPassword123!";
		String message = "This is a top secret message.";

		String encryptedDna = crypto.encrypt(message, key);
		String decryptedText = crypto.decrypt(encryptedDna, key);

		System.out.println("Original:  " + message);
		System.out.println("Encrypted (DNA): " + encryptedDna);
		System.out.println("Decrypted: " + decryptedText);
	}
}