package pkg2;
//LFSR
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;

public class Process {

    Scanner input = new Scanner(System.in);
    int num;
    int[] binary;
    int bits[];
    int numberOfFlipFlops = 9;
    int plainTextBinary[] = new int[1000];
    String plainTextBinaryStr;
    String plainTextStr;//*
    int initialStateInt;
    int POfXInt;
    int x;//*
    int sizeOfIterations;
    int initialState[] = new int[numberOfFlipFlops];
    char initialStateCharArr[] = new char[numberOfFlipFlops];
    String initialStateChar;//*
    int POfX[] = new int[numberOfFlipFlops];
    char POfXCharArr[] = new char[numberOfFlipFlops];
    String POfXChar;//*
    int ff0[] = new int[sizeOfIterations];
    int ff1[] = new int[sizeOfIterations];
    int ff2[] = new int[sizeOfIterations];
    int ff3[] = new int[sizeOfIterations];
    int ff4[] = new int[sizeOfIterations];
    int ff5[] = new int[sizeOfIterations];
    int ff6[] = new int[sizeOfIterations];
    int ff7[] = new int[sizeOfIterations];
    int ff8[] = new int[sizeOfIterations];
    int ff0ForEncryption[];
    int cipherText[];
    int plainText[];

    Process() throws FileNotFoundException {
        System.out.print("Enter the plain text : ");
        plainTextStr = input.nextLine();
        readFromFile();
        sizeOfIterations = 8*plainTextStr.length()+x;
        ff0 = new int[sizeOfIterations];
        ff1 = new int[sizeOfIterations];
        ff2 = new int[sizeOfIterations];
        ff3 = new int[sizeOfIterations];
        ff4 = new int[sizeOfIterations];
        ff5 = new int[sizeOfIterations];
        ff6 = new int[sizeOfIterations];
        ff7 = new int[sizeOfIterations];
        ff8 = new int[sizeOfIterations];
        ff0ForEncryption= new int[sizeOfIterations-x];
        cipherText= new int[sizeOfIterations-x];
        plainText= new int[sizeOfIterations-x];
        bits= new int[sizeOfIterations-x];
    }
    
    void readFromFile() throws FileNotFoundException {
        File file = new File("Data.txt");
        Scanner scan = new Scanner(file);
        while(scan.hasNextLine())
        {
            for(int i = 0 ; i < 3 ; i++)
            {
                if(i==0)
                {
                    initialStateChar=scan.nextLine();
                }
                else if(i==1)
                {
                    POfXChar=scan.nextLine();
                }
                if(i==2)
                {
                    String str="";
                    str=scan.nextLine();
                    x = Integer.parseInt(str);
                }
            }
        }
    }

    String strToBinary(String s) {
        String temp = s;
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) 
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        return binary.toString();
    }

    void checkArrays() {
        int counter1 = 0;
        int counter2 = 0;
        initialStateCharArr = initialStateChar.toCharArray();
        POfXCharArr = POfXChar.toCharArray();
        for(int i = 0 ; i < numberOfFlipFlops; i++)
        {
            if(initialStateCharArr[i] == '0')
            {
                initialState[i]=0;
            }
            if(initialStateCharArr[i] == '1')
            {
                initialState[i]=1;
            }
        }
        for(int i = 0 ; i < numberOfFlipFlops; i++)
        {
            if(POfXCharArr[i] == '0')
            {
                POfX[i]=0;
            }
                if(POfXCharArr[i] == '1')
            {
                POfX[i]=1;
            }
        }
         for (int i = 0; i < numberOfFlipFlops; i++) {
            if (initialState[i] > 0) {
                counter1++;
            }
            if (initialState[i] > 1) {
                System.out.print("ERROR 404!, the initial state must be binary [0/1].");
                System.exit(0);
            }
        }
        for (int i = 0; i < numberOfFlipFlops; i++) {
            if (POfX[i] > 0) {
                counter2++;
            }
            if (POfX[i] > 1) {
                System.out.print("ERROR 404!, the initial state must be binary [0/1].");
                System.exit(0);
            }
        }
        if (counter1 == 0) {
            System.out.print("ERROR STACK CASE 404!");
            System.exit(0);
        }
        if (counter2 == 0) {
            System.out.print("ERROR STACK CASE 404!");
            System.exit(0);
        }
        plainTextBinaryStr = strToBinary(plainTextStr);
        String[] integerStrings = plainTextBinaryStr.split(" ");
        for(int j = 0 ; j < integerStrings.length ; j++)
        {
            String str=integerStrings[j];
            int k =0;
            for(int i = j*str.length() ; i < j*str.length()+str.length() ;i++)
            {
                if(str.charAt(k) == '1')
                {
                  bits[i] = 1;
                }
                else
                {
                    bits[i]=0;
                }
                k++;
            }
            k=0;
        }
    }

    void initialStateStep() {
        for (int i = 0; i < numberOfFlipFlops; i++) {
            switch (i) {
                case 0: 
                {
                    ff8[0]=initialState[i];
                    break;
                }
                case 1: 
                {
                    ff7[0]=initialState[i];
                    break;
                }
                case 2: 
                {
                    ff6[0]=initialState[i];
                    break;
                }
                case 3: 
                {
                    ff5[0]=initialState[i];
                    break;
                }
                case 4: 
                {
                    ff4[0]=initialState[i];
                    break;
                }
                case 5: 
                {
                    ff3[0]=initialState[i];
                    break;
                }
                case 6: 
                {
                    ff2[0]=initialState[i];
                    break;
                }
                case 7: 
                {
                    ff1[0]=initialState[i];
                    break;
                }
                case 8: 
                {
                    ff0[0]=initialState[i];
                    break;
                }
            }
        }
    }

    void xoringProcess() {
        for (int i = 0; i < sizeOfIterations; i++) {
            int valueOfEachFlipFlop;
            if(i==0)
            {
                valueOfEachFlipFlop=0;
                ff7[i + 1] = ff8[i];
                ff6[i + 1] = ff7[i];
                ff5[i + 1] = ff6[i];
                ff4[i + 1] = ff5[i];
                ff3[i + 1] = ff4[i];
                ff2[i + 1] = ff3[i];
                ff1[i + 1] = ff2[i];
                ff0[i + 1] = ff1[i];
                System.out.println("V" + "   " + "8" + " " + "7" + " " + "6" + " " + "5" + " " + "4" + " " + "3" + " " + "2" + " " + "1" + " " + "0"+ " " + "N");
                System.out.println("-" + "   " + ff8[i] + " " + ff7[i] + " " + ff6[i] + " " + ff5[i] + " " + ff4[i] + " " + ff3[i] + " " + ff2[i] + " " + ff1[i] + " " + ff0[i]+ " " + i);
            }
            else
            {
                valueOfEachFlipFlop = ff7[i] + ff6[i] + ff5[i] + ff4[i] + ff3[i] + ff2[i] + ff1[i] + ff0[i];
                ff8[i] = 1;
                if (valueOfEachFlipFlop % 2 == 0)//even
                    ff8[i] = 0;
                if(i+1 < sizeOfIterations)
                {
                    ff7[i + 1] = ff8[i];
                    ff6[i + 1] = ff7[i];
                    ff5[i + 1] = ff6[i];
                    ff4[i + 1] = ff5[i];
                    ff3[i + 1] = ff4[i];
                    ff2[i + 1] = ff3[i];
                    ff1[i + 1] = ff2[i];
                    ff0[i + 1] = ff1[i];
                }
                System.out.println(valueOfEachFlipFlop + "   " + ff8[i] + " " + ff7[i] + " " + ff6[i] + " " + ff5[i] + " " + ff4[i] + " " + ff3[i] + " " + ff2[i] + " " + ff1[i] + " " + ff0[i]+ " " + i);
            }
            
        }
    }

    void process() {
        checkArrays();
        initialStateStep();
        xoringProcess();
        encryption();
        decryption();
        //testSomeCode();
    }
    
    String convertBinaryToAscci(String binStr) {
        String string ="";
        for(int i = 0 ; i < binStr.length(); i+=8)
        {
            String temp = binStr.substring(i, i+8);
            int charCode = Integer.parseInt(temp, 2);
            String str = new Character((char)charCode).toString();
            string +=str;
        }
        return string ;
    }
    
    void encryption(){
        String str="";
        int valueForArrays[]=new int [sizeOfIterations-x];
        for(int i = x+1 ; i < sizeOfIterations ; i++)
        {
            ff0ForEncryption[i%(x+1)]=ff0[i];//to assign values 0,1,...etc.
        }
        for(int i = 0 ; i < sizeOfIterations-x ; i++)
        {
            valueForArrays[i] = ff0ForEncryption[i]+ bits[i];
            if(valueForArrays[i] % 2 == 0)//even 
            {
                cipherText[i]=0;
            }
            if(valueForArrays[i] % 2 == 1)//odd
            {
                cipherText[i]=1;
            }
            str+=cipherText[i];
        }
        System.out.println("Cipher text is : "+convertBinaryToAscci(str));
    }
    
    void decryption(){
        String str="";
        int valueForArrays[]=new int [sizeOfIterations-x];
        for(int i = 0 ; i < sizeOfIterations-x ;i++)
        {
            valueForArrays[i] = ff0ForEncryption[i]+cipherText[i];
            if(valueForArrays[i] % 2 == 0)//even 
            {
                plainText[i]=0;
            }
            if(valueForArrays[i] % 2 == 1)//odd
            {
                plainText[i]=1;
            }
            str+=plainText[i];
        }
        System.out.println("Plain text is : "+convertBinaryToAscci(str));
    }
    
    void testSomeCode() {
        System.out.println(Arrays.toString(bits));
    }
}