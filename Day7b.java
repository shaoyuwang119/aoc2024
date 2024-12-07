import java.io.*;
import java.util.*;

public class Day7b {
     // MY ALGORITHM: Check the comment in part 1, except this time it's base 3 instead of binary.
    public static void main(String[] args) throws FileNotFoundException{
        File f = new File("input.txt");

        Scanner lineScanner = new Scanner(f);
        Scanner intScanner;

        // store the input into a hashmap
        LinkedHashMap<Long,ArrayList<Integer>> equations = new LinkedHashMap<>();

        while (lineScanner.hasNextLine()) {
            String line = lineScanner.nextLine();
            line = line.replace(":","");

            intScanner = new Scanner(line);
            Long key = intScanner.nextLong();
            ArrayList<Integer> values = new ArrayList<>();

            while (intScanner.hasNextInt()) {
                values.add(intScanner.nextInt());
            }
            
            equations.put(key, values);

        }

        // iterate through the keys to see if it's possible to "calibrate" them
        HashMap<Long, Integer> trueValues = new HashMap<>(); // trueValues stores the keys to equations that can be "calibrated"
        
        for (Long key : equations.keySet()) {
            ArrayList<Integer> values = equations.get(key);
            int opSeqB10 = 0; // opSeq(operation seq) is an integer in decimal(base 10), supposed to be represented in binary, where 0 is add and + is mult
            int opSeqLen = (int)(Math.pow(3, values.size()-1)); // a constant value that represents how big the max value of the base10 value is
            String opSeq; // binary representation that will be read
            
            for (opSeqB10=0; opSeqB10<opSeqLen; opSeqB10++) {
                opSeq = toBase3(opSeqB10);
                opSeq = standardizeOpSeq(opSeq, values.size()-1);
                Long result = (tryOperations(opSeq, values));

                if (Objects.equals(result, key)) {
                    if (trueValues.containsKey(result)) {
                        trueValues.put(key, trueValues.get(key)+1);
                    } else {
                        trueValues.put(key, 1);
                    }
                    System.out.println("values: "+values+"    operation: "+opSeq 
                                +"    result :"+result);
                }
                
            }

        }

        //System.out.println(trueValues);

        // calculate the sum
        Long sum = 0L;
        for (Long value : trueValues.keySet()) {
            sum += value;
        }

        System.out.println("\nANSWER " + sum);
    }


    // method to try operations based on the operation sequence
    public static Long tryOperations(String opSeq, ArrayList<Integer> values) {
        Long output = values.get(0).longValue();
        String tempOutput = ""; // for the case where i need to concatenante values

        for (int i=0; i<opSeq.length(); i++) {
            if (opSeq.substring(i,i+1).equals("0")) {
                output += values.get(i+1);
            } else if (opSeq.substring(i,i+1).equals("1")){
                output *= values.get(i+1);
            } else {
                tempOutput = Long.toString(output);
                tempOutput += values.get(i+1);
                output = Long.valueOf(tempOutput);
            }
        }

        return output;
    }


    // standardize binary strings to make them have uniform length. 
    // for example, if we have length 4, then 10 will be turned into 0010, 11 is 0011, etc
    public static String standardizeOpSeq(String opSeq, int reqLength) {
        String output = opSeq;
        for (int i=0; i<(reqLength - opSeq.length()); i++) {
            output = "0" + output;
        }
        return output;
    }


    public static String toBase3(int base10Num) {
        int quotient = base10Num;
        int remainder;
        String output = "";

        while (quotient != 0) {
            remainder = quotient % 3;
            quotient -= remainder;
            quotient /= 3;
            output = remainder + output;
        }

        return output;
    }
}
