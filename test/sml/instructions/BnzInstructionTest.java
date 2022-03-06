package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BnzInstructionTest {
  private Machine m;
  private Instruction instr;
  private List<Instruction> prog;
  private Registers r;

  @BeforeEach
  void setUp() {
    m = new Machine();
    m.setRegisters(new Registers());
    r = m.getRegisters();

    // create a basic program
    prog = m.getProg();
    prog.add(new LinInstruction("LO", 0, 3));
    prog.add(new LinInstruction("L1", 1, 1));
    prog.add(new SubInstruction("L2", 0, 0, 1));
    prog.add(new SubInstruction("L3", 0, 0, 1));
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void execute1() {
    instr = new BnzInstruction("L4", 0, "LX");
    Instruction expected = new LinInstruction("LX", 0, 3);

    prog.add(instr);
    prog.add(3, expected);
    prog.forEach(i -> i.execute(m));

    Instruction next = prog.get(m.getPc());
    assertEquals(expected, next);
  }

  @Test
  void execute2() {
    instr = new BnzInstruction("L4", 31, "L1");
    prog.add(instr);
    m.execute();

    // PC is increased as expected, i.e. no branch occurs
    assertEquals(5, m.getPc());
  }

  @Test
  void execute3() {
    r.setRegister(10,0);
    instr = new BnzInstruction("L4", 10, "L2");
    instr.execute(m);

    // no branch occurs, register is 0
    assertEquals(0, m.getPc());
  }

  @Test
  void execute4() {
    r.setRegister(10,-1);
    instr = new BnzInstruction("L4", 10, "L2");
    instr.execute(m);

    // branch occurs, register is not 0
    assertEquals(2, m.getPc());
  }

  @Test
  void execute5() {
    r.setRegister(10,-1);
    instr = new BnzInstruction("L4", 10, "Does Not Exist");
    // label does not exist
    assertThrows(RuntimeException.class, () -> instr.execute(m));
  }

  @Test
  void testToString1() {
    instr = new BnzInstruction("L0", 0, "L0");
    assertEquals("L0: bnz. If value in r0 is not 0, branch to instruction: L0", instr.toString());
  }

  @Test
  void testToString2() {
    instr = new BnzInstruction("12345", 31, "L8");
    assertEquals("12345: bnz. If value in r31 is not 0, branch to instruction: L8", instr.toString());
  }
}