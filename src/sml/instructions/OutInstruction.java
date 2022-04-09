package sml.instructions;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sml.Instruction;
import sml.Machine;

/**
 * This class represents the Div (divide) instruction from the language.
 *
 * @author nsamso01
 */
@Component("out")
@Scope("prototype")
public class OutInstruction extends Instruction {

    private final int op1;

    /**
     * Constructor: Initialises a new Out instruction.
     *
     * @param s1 first operand
     */
    public OutInstruction(String label, int s1){
        super(label, "out");
        this.op1 = s1;
    }

    /**
     * Executes this instruction using the specified machine.
     *
     * @param m machine processing the instructions
     */
    @Override
    public void execute(Machine m) {
        System.out.println("r" + op1 + " contains: " + m.getRegisters().getRegister(op1));
    }

    /**
     * String representation of the Out instruction, including its field.
     *
     * @return String in this form: "LabelName: out. Print the contents of rX to the console"
     */
    @Override
    public String toString() {
        return super.toString() + ". Print the contents of r" + op1 + " to the console";
    }
}
