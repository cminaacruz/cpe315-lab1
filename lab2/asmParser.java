import java.io.*;
import java.util.*;

public class asmParser {

    // NOTE: created void functions but can change later to return hashmaps

    public static void main(String args[]) throws Exception {
        getInstructions(args[0]);
        // getRegisters();
        //System.out.println(getLabels(args[0]));


    }

    // first pass
    public static Map<String, Integer> getLabels(String file) {
        // create label HashMap
        HashMap<String, Integer> labels = new HashMap<>();

        try {
            // counter for line number
            int lnNum = 1;
            Scanner scanner = new Scanner(new File(file));

            // while loop to grab next line in file
            while (scanner.hasNextLine()) {
                String ln = scanner.nextLine();
                // split at : for labels
                int lst = ln.lastIndexOf(':');
                // if : detected, add label name + line number to map
                if (lst != -1) {
                    String[] result = ln.split(":");
                    result[0] = result[0].replaceAll("^\\s+",""); //trim leading spaces
                    labels.put(result[0], lnNum);
                }
                lnNum += 1;
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return labels;
    }

    // second pass
    public static void getInstructions(String file) {
        List<ArrayList<String>> instrList = new ArrayList<ArrayList<String>>();
        int count = 1;

        try {
            Scanner scanner = new Scanner(new File(file));

            while (scanner.hasNextLine()) {
                ArrayList<String> instr = new ArrayList<String>();
                String ln = scanner.nextLine();
                // split at # for comments
                String[] result = ln.split("#");


                /*used for debugging
                System.out.println(ln);
                System.out.print("line " + count + " [");
                for (String item: result){
                    System.out.print(item + " | ");
                }
                System.out.println("]");
                //end debugging*/

                // ignores blank lines and filters out labels from asm code
                if (result.length > 0) {
                    int lst = result[0].lastIndexOf(':');
                    if (lst != -1) {
                        String[] result2 = result[0].split(":");
                        // splits up each instruction
                        StringTokenizer tok = new StringTokenizer(result2[1], " ,$");
                        while (tok.hasMoreTokens()) {
                            instr.add(tok.nextToken());
                            //System.out.println(tok.nextToken());
                        }
                        //System.out.println("line num: " + count);
                        count += 1;
                    }
                    else {
                        // splits up each instruction
                        StringTokenizer tok = new StringTokenizer(result[0], " ,$");
                        while (tok.hasMoreTokens()) {
                            instr.add(tok.nextToken());
                            //System.out.println(tok.nextToken());
                        }
                        //System.out.println("line num: " + count);
                        count += 1;
                    }

                    instrList.add(instr);
                }
            }

            // for (ArrayList<String> list: instrList){
            //     for(String s : list){
            //         System.out.println(s);
            //     }
            // }

            for(String s : instrList.get(0)){
               System.out.println(s);
            }

            scanner.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static Map<String, Integer> getRegisters() {
        // create String array of registers
        String[] reg = {"zero", "v0", "v1", "a0", "a1", "a2", "a3", "t0", "t1",
                        "t2", "t3", "t4", "t5", "t6", "t7", "s0", "s1", "s2",
                        "s3", "s4", "s5", "s6", "s7", "t8", "t9", "ra"};
        Integer[] regNums = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                            16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 31};

        // create hashmap and input registers as key and nums as values
        HashMap<String, Integer> registers = new HashMap<>();

        for(int i = 0; i < reg.length; i++) {
            registers.put(reg[i], regNums[i]);
        }

        return registers;
    }

}