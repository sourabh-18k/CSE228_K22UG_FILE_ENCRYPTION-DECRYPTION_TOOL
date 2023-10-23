//Sourabh Kumar CSE228 Project  (Encryption)

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Encryption<choice> {
    public static void main(String[] args) throws IOException {

        //User Validation Variables and Scanner Variable
        //Scanner input = new Scanner(System.in);
        int program = 1;
        int choice = 0;
        int condition;

        //Condition to use program1
        while(program == 1) {

            //Ask user how they would like to use the program. And Validate for Integer
            System.out.print("\n\n***************************************");
            System.out.print("\nChoose how you would like to proceed. " +
                    "\n1)Encrypt a file with a key \n2)Decrypt with a key \n3)Decrypt file without a Key  " +
                    "\n4)Exit program \n");
            System.out.print("***************************************\n");

            //Call Validate Method
            choice = validate(1, 4);

            if(choice == 4){ program = 0; } //Exit Program

            //Conditional on how the program will proceed given errors and completion of tasks
            while (choice == 1 || choice == 2 || choice ==3 ) {

                //Set/Reset Key Validation
                int key = 200;

                //Choose key and Call Methods fo Encrypt or Decrypt
                if (choice == 1 || choice == 2) {

                    //Ask for Key for Byte Range (-128 to 127)
                    while (key > 127 || key < -128) {
                        System.out.println("Enter the byte key, as an integer from -128 to 127.");
                        //Call Validate Method
                        key = validate(-128, 127);
                    }
                }

                //Call encryption Method Choose Key
                if (choice == 1) {
                    condition = encryptFile(key);
                    if(condition <1){ choice = 0; program = 0; }    //Exit Program Condition
                    if(condition ==1 ) { choice = 1; }              //Try Again
                    if(condition ==3 ) { choice = 0; }              //Bring to Main Prompt
                }

                //Call Decryption Method with Key
                if (choice == 2) {
                    condition = decryptFile(key);
                    if(condition <1){ choice = 0; program = 0; }    //Exit Program Condition
                    if(condition ==1 ) { choice = 2; }              //Try Again
                    if(condition ==3 ) { choice = 0; }              //Bring to Main Prompt
                }

                //Call Software Decryption no Key
                if(choice == 3) {
                    condition = codeBreaker();
                    if(condition <1){ choice = 0; program = 0; }    //Exit Program Condition
                    if(condition ==1 ) { choice = 3; }              //Try Again
                    if(condition ==3 ) { choice = 0; }              //Bring to Main Prompt
                }
            }

        }
        //Exit program message
        System.out.print("\nExiting program...");
    }


    //Encryption Method (Choice 1)
    public static int encryptFile(int key) throws IOException {

        Scanner input = new Scanner(System.in);

        //Prompt User for File to be encrypted
        System.out.print("Enter the name of the file you would like to encrypt. ");
        String filename = input.nextLine();

        Path path = Paths.get(filename);

        try {
            //Create an Array to hold bytes
            byte[] BArr = Files.readAllBytes(path);

            //Display for testing the array
            for(int i = 0; i < BArr.length; i++) {
              //  System.out.print((char) BArr[i] + " ");
                BArr[i] += key;
            }

            //Prompt User for File to be saved name
             System.out.print("Enter a name for the encrypted file" +
                " to be saved as. ");
             String newFile = input.nextLine();

            //create a new file
           try (OutputStream os = new FileOutputStream(newFile)){
               for (int i = 0; i < BArr.length; i++) {
                   os.write(BArr[i]);
               }
               //Update User
               System.out.print("\nSaving " +newFile+ " as an encrypted file...");
           }
        }
        //File Not Found Catch Procedures
        catch (FileNotFoundException ex) {
            System.out.print("Unable to open file " + filename +" " +
                    "\nHow would you like to proceed? ");

            //Call Fail Method (how to proceed)
            return failed();
        }

        //General Error Opening File Catch Procedures
        catch (IOException ex) {
            System.out.print("Error opening file " + filename +
                    "\nHow would you like to proceed? ");

            //Call Fail Method (how to proceed)
            return  failed();
        }

        return 3;   //Continue Program
    }

    //Method to Decrypt a File (Choice 2)
    public static int decryptFile(int key) throws IOException {

        Scanner input = new Scanner(System.in);
        System.out.print("Enter the filename of the file you would like to decrypt. ");
        String filename = input.nextLine();
        Path path = Paths.get(filename);

        try {

            //Create an Array to hold bytes
            byte[] BArr = Files.readAllBytes(path);

            //Convert file into a byte array apply key
            for (int i = 0; i < BArr.length; i++) {
                //System.out.print((char) BArr[i] + " ");
                BArr[i] -= key;
            }

            //Get Information to Save File
            System.out.print("Enter a file name for the decrypted data to be saved to. ");
            String newFile = input.nextLine();

            //create a new file and write data to it
            try(OutputStream os = new FileOutputStream(newFile)) {
                for (int i = 0; i < BArr.length; i++) {
                    os.write(BArr[i]);
                }
                //User update
                System.out.print("Saving " + newFile+ " as a decrypted file...");
            }
        }

        //File not found Action
        catch (FileNotFoundException ex) {
            System.out.print("Unable to open file " + filename +" " +
                    "\nHow would you like to proceed? ");
            //Call Fail Method (how to proceed)
            return failed();
        }
        //General Error opening File Action
        catch (IOException ex) {
            System.out.print("Error opening file " + filename +
                    "\nHow would you like to proceed? ");

            //Call Fail Method (how to proceed)
            return failed();
        }

        return 3;       //Continue Program
    }

    public static int codeBreaker() throws IOException{
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the filename of the file you would like to be decrypted. ");
        String filename = input.nextLine();
        //Format
        System.out.println();
        Path path = Paths.get(filename);
        String word1, word2;

        //Ask for words within Document to search
        System.out.print("Enter two words that are in the file.\n");
        System.out.print("First Word: ");
        word1 = input.nextLine();
        System.out.print("Second Word: ");
        word2 = input.nextLine();

        try {
            //Set Code Key
            int key = 200;
            int check = 0;

            //Go through possible Byte Values
            for(int i = -128; i < 128; i++) {

                byte[] BArr = Files.readAllBytes(path);

                for (int j = 0; j < BArr.length; j++) {
                    //System.out.print((char) BArr[j] + " ");
                    BArr[j]-=i;
                }

                //Convert Byte Array to String, ignore case
                String coded = new String(BArr);
                String lowerCase = coded.toLowerCase();

                if(lowerCase.contains(word1) && lowerCase.contains(word2)){
                    //Value of Decryption Key
                    key = i;
                    //Check Condition for key or more than one result
                    check += 1;
                    //If more than one key format
                    if(check > 1){ System.out.print("or "); }
                    //Print Key
                    System.out.print("The decryption Key to the file " + filename+ " is: " + key);
                }

            }

            //Fail Message if Key is not found
            if(check == 0){
                System.out.print("Decryption has failed, key not found. ");
            }
        }

        //File not found Action
        catch (FileNotFoundException ex) {
            System.out.print("Unable to open file " + filename +" " +
                    "\nHow would you like to proceed? ");
            //Call Fail Method (how to proceed)
            return failed();
        }
        //General Error opening File Action
        catch (IOException ex) {
            System.out.print("Error opening file " + filename +
                    "\nHow would you like to proceed? ");

            //Call Fail Method (how to proceed)
            return failed();
        }
        return 3;       //Continue Program
    }


    //Catch Block Fail Method, User Continuation Options
    public static int failed(){
        Scanner input = new Scanner(System.in);
        int cont = 0;

        while(cont < 1 || cont >2 ){
            System.out.print("1)Try Again \n2)Exit Program ");
            cont = input.nextInt();
        }
        if(cont == 1){          //Try Again
            return 1;
        }
        else{ return 0;   }      //Exit Program

    }

    //Method to validate User integer inputs within a range
    public static int validate(int low, int high){
        int choice = -654654;
        Scanner input = new Scanner(System.in);
        do {
            System.out.print("Enter number of choice:  ");
            while (!input.hasNextInt()) {
                System.out.print("not valid, enter an appropriate value.  ");
                String tryagain = input.next();
            }
            choice = input.nextInt();
        } while (choice < low || choice > high);

        return choice;
    }
}




