package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * This class represents the Lin (load) instruction from the language.
 *
 * @author nsamso01
 */
public class LinInstruction extends Instruction {

    private final int opTarget;
    private final int input;

    /**
     * Constructor: Initialises a new Lin instruction.
     *
     * @param label of the instruction
     * @param register where the supplied integer x is stored
     * @param x integer supplied by program
     */
    public LinInstruction(String label, int register, int x){
        super(label, "lin");
        this.opTarget = register;
        this.input = x;
    }

    /**
     * Executes this instruction using the specified machine.
     *
     * @param m machine processing the instructions
     */
    @Override
    public void execute(Machine m) {
        m.getRegisters().setRegister(opTarget, input);
    }

    /**
     * String representation of the Lin instruction, including its fields.
     *
     * @return String in this form: "LabelName: lin. Store integer with value X in rY"
     */
    @Override
    public String toString() {
        return super.toString() + ". Store integer with value " + input + " in r" + opTarget;
    }
}
