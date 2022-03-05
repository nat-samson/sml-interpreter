package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author ...
 */
public final class Translator {

    private static final String PATH = "";

    // word + line is the part of the current line that's not yet processed
    // word has no whitespace
    // If word and line are not empty, line begins with whitespace
    private final String fileName; // source file of SML code
    private String line = "";

    public Translator(String file) {
        fileName = PATH + file;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public boolean readAndTranslate(Labels lab, List<Instruction> prog) {
        try (var sc = new Scanner(new File(fileName), "UTF-8")) {
            // Scanner attached to the file chosen by the user
            // The labels of the program being translated
            lab.reset();
            // The program to be created
            prog.clear();

            try {
                line = sc.nextLine();
            } catch (NoSuchElementException ioE) {
                return false;
            }

            // Each iteration processes line and reads the next input line into "line"
            while (line != null) {
                // Store the label in label
                var label = scan();

                if (label.length() > 0) {
                    var ins = getInstruction(label);
                    if (ins != null) {
                        lab.addLabel(label);
                        prog.add(ins);
                    }
                }

                try {
                    line = sc.nextLine();
                } catch (NoSuchElementException ioE) {
                    return false;
                }
            }
        } catch (IOException ioE) {
            System.err.println("File: IO error " + ioE);
            return false;
        }
        return true;
    }

    // The input line should consist of an SML instruction, with its label already removed.
    // Translate line into an instruction with label "label" and return the instruction
    public Instruction getInstruction(String label) {
        if (line.equals("")) {
            return null;
        }
        var opCode = scan();

        ArrayList<Object> args = lineToArgs(label);

        // create an instance of the matching Instruction subclass
        String instrName = buildInstrName(opCode);
        Class<?> instrClass = null;
        try {
            instrClass = Class.forName(instrName);
        } catch (ClassNotFoundException e) {
            System.err.println("No instruction defined for: " + instrName);
            e.printStackTrace();
        }
        Constructor<?> instrConstr = null;
        try {
            assert instrClass != null;
            instrConstr = instrClass.getDeclaredConstructor(getArgTypes(args));
        } catch (NoSuchMethodException e) {
            System.err.println("Provided arguments do not match what is expected for: " + opCode +
                    "\nPlease review the line labelled: " + label);
        }
        try {
            assert instrConstr != null;
            return (Instruction) instrConstr.newInstance(args.toArray());
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a list of the Instruction arguments extracted from the input line
     *
     * @param label the label for the input line
     * @return a list of ints and/or strings
     */
    private ArrayList<Object> lineToArgs(String label) {
        ArrayList<Object> args = new ArrayList<>();
        args.add(label);

        while (!line.isEmpty()) {
            String rollback = line;
            int arg = scanInt();
            if (arg == Integer.MAX_VALUE) {
                line = rollback;
                String s = scan();
                args.add(s);
            } else {
                args.add(arg);
            }
        }
        return args;
    }

    /**
     * Create an array of Class objects based on the types of each element in the input list
     *
     * @param args a list of objects representing the arguments for the instruction being created
     * @return an array of Class objects based on the types of the input list
     */
    private Class<?>[] getArgTypes(ArrayList<Object> args) {
        Class<?>[] types = new Class[args.size()];
        for (int i = 0; i < args.size(); i++) {
            if (args.get(i).getClass().equals(Integer.class)) types[i] = (Integer.TYPE);
            else types[i] = args.get(i).getClass();
        }
        return types;
    }

    /**
     * Form the full name of the required Instruction subclass.
     *
     * @param opCode the 'raw' opCode taken from the input file for a given instruction
     * @return a String of the name of the relevant Instruction subclass
     */
    private String buildInstrName(String opCode) {
        opCode = opCode.substring(0, 1).toUpperCase() + opCode.substring(1);
        return "sml.instructions." + opCode + "Instruction";
    }

    /*
     * Return the first word of line and remove it from line. If there is no word, return ""
     */
    private String scan() {
        line = line.trim();
        if (line.length() == 0) {
            return "";
        }

        int i = 0;
        while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
            i = i + 1;
        }
        String word = line.substring(0, i);
        line = line.substring(i);
        return word;
    }

    // Return the first word of line as an integer. If there is any error, return the maximum int
    private int scanInt() {
        String word = scan();
        if (word.length() == 0) {
            return Integer.MAX_VALUE;
        }

        try {
            return Integer.parseInt(word);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}