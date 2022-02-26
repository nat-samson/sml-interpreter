package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * This class represents the Div (divide) instruction from the language.
 *
 * @author nsamso01
 */
public class DivInstruction extends Instruction {

    private final int opTarget;
    private final int op1;
    private final int op2;

    /**
     * Constructor: Initialises a new Div instruction.
     *
     * @param label of the instruction
     * @param register where the result of the calculation is stored
     * @param s1 first operand
     * @param s2 second operand
     */
    public DivInstruction(String label, int register, int s1, int s2){
        super(label, "div");
        this.opTarget = register;
        this.op1 = s1;
        this.op2 = s2;
    }

    /**
     * Executes this instruction using the specified machine.
     *
     * @param m machine processing the instructions
     */
    @Override
    public void execute(Machine m) {
        int val1 = m.getRegisters().getRegister(op1);
        int val2 = m.getRegisters().getRegister(op2);

        m.getRegisters().setRegister(opTarget, val1 / val2);
    }

    /**
     * String representation of the Div instruction, including its fields.
     *
     * @return String in this form: "LabelName: div. Store result of rX / rY in rZ"
     */
    @Override
    public String toString() {
        return super.toString() + ". Store result of r" + op1 + " / r" + op2 + " in r" + opTarget;
    }

    // TODO Implement desired functionality when Dividing By Zero
}
