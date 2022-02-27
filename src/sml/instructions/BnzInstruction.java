package sml.instructions;

import sml.Instruction;
import sml.Machine;
import java.util.List;

/**
 * This class represents the Bnz (branch if not 0) instruction from the language.
 *
 * @author nsamso01
 */
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

        // this approach exploits fact that labels must be unique
        List<Instruction> prog = m.getProg();

        if (m.getRegisters().getRegister(register) != 0) {
            for (int i = 0; i < prog.size(); i++) {
                if (prog.get(i).getLabel().equals(nextLabel)) {
                    m.setPc(i);
                    return;
                }
            }
            // if program is meant to branch, but label can't be found
            throw new RuntimeException("Branch instruction could not complete. No such label exists: " + nextLabel);
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
