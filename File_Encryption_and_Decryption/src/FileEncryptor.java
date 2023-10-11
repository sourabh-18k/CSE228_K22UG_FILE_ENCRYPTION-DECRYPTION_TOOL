import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileEncryptor {
    private static final String ALGORITHM = "AES";
    private static Map<String, String> encryptionKeys = new HashMap<>();

    public static void encryptFile(String inputFile, String outputFile, String key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            CipherOutputStream cos = new CipherOutputStream(outputStream, cipher);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }

            cos.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decryptFile(String inputFile, String outputFile, String key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            CipherInputStream cis = new CipherInputStream(inputStream, cipher);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = cis.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            cis.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        encryptionKeys.put("key1", "YourEncryptionKey1");
        encryptionKeys.put("key2", "YourEncryptionKey2");
        encryptionKeys.put("key3", "YourEncryptionKey3");

        System.out.println("Choose operation: (1) Encrypt (2) Decrypt");
        int choice = scanner.nextInt();

        System.out.print("Enter input file path: ");
        String inputFile = scanner.next();
        System.out.print("Enter output file path: ");
        String outputFile = scanner.next();

        if (choice == 1) {
            System.out.println("Available encryption keys:");
            for (String key : encryptionKeys.keySet()) {
                System.out.println(key);
            }
            System.out.print("Enter the encryption key label: ");
            String keyLabel = scanner.next();
            String key = encryptionKeys.get(keyLabel);
            if (key != null) {
                encryptFile(inputFile, outputFile, key);
                System.out.println("File encrypted successfully.");
            } else {
                System.out.println("Invalid encryption key label.");
            }
        } else if (choice == 2) {
            System.out.println("Available decryption keys:");
            for (String key : encryptionKeys.keySet()) {
                System.out.println(key);
            }
            System.out.print("Enter the decryption key label: ");
            String keyLabel = scanner.next();
            String key = encryptionKeys.get(keyLabel);
            if (key != null) {
                decryptFile(inputFile, outputFile, key);
                System.out.println("File decrypted successfully.");
            } else {
                System.out.println("Invalid decryption key label.");
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
