package sml.instructions;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sml.Instruction;
import sml.LabelsBridge;
import sml.Machine;

/**
 * This class represents the Bnz (branch if not 0) instruction from the language.
 *
 * @author nsamso01
 */
@Component("bnz")
@Scope("prototype")
public class BnzInstruction extends Instruction {

    private final int register;
    private final String nextLabel;

    /**
     * Constructor: Initialises a new Bnz instruction.
     *
     * @param label of the instruction
     * @param register where the supplied integer x is stored
     * @param nextLabel String supplied by program
     */
    public BnzInstruction(String label, int register, String nextLabel){
        super(label, "bnz");
        this.register = register;
        this.nextLabel = nextLabel;
    }

    /**
     * Executes this instruction using the specified machine.
     *
     * @param m machine processing the instructions
     */
    @Override
    public void execute(Machine m) {
        LabelsBridge l = new LabelsBridge(m.getLabels());

        if (m.getRegisters().getRegister(register) != 0) {
            int nextInstr = l.indexOf(nextLabel);
            if (nextInstr != -1) m.setPc(nextInstr);
            else throw new RuntimeException("BNZ Instruction failed. No such label exists: " + nextLabel);
        }
    }

    /**
     * String representation of the Bnz instruction, including its fields.
     *
     * @return String in this form: "LabelName: bnz. If value in rX is not 0, branch to instruction: Y"
     */
    @Override
    public String toString() {
        return super.toString() + ". If value in r" + register + " is not 0, branch to instruction: " + nextLabel;
    }
}
