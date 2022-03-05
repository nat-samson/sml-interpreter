package sml;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Factory class for creating Instructions for an SML program
 */
public class InstructionFactory {

    private static final InstructionFactory instance;
    private final HashMap<String, Constructor<?>> instructionMap = new HashMap<>();

    static {
        instance = new InstructionFactory();
    }

    /**
     * Constructor: an Instruction Factory that creates Instructions as specified by a given Properties file.
     */
    private InstructionFactory() {
        Properties props = new Properties();

        try {
            try (var fis = new FileInputStream("bean.properties")) {
                props.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Map.Entry<Object, Object> e : props.entrySet()) {
                String key = (String) e.getKey();
                Constructor<?> value = Class.forName((String) e.getValue()).getDeclaredConstructors()[0];
                instructionMap.put(key, value);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the singleton instance of the Instruction Factory.
     */
    public static InstructionFactory getInstance() {
        return instance;
    }

    /**
     * Returns an Instruction object, initialised using the appropriate Instruction subclass constructor.
     *
     * @param opcode String containing the opcode, indicating the appropriate subtype of Instruction
     * @param args List containing the arguments required for the Instruction
     * @return an instance of the appropriate Instruction subclass
     */
    public Instruction getInstruction(String opcode, ArrayList<Object> args) {

        Instruction result = null;
        try {
            result = (Instruction) instructionMap.get(opcode).newInstance(args.toArray());
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}