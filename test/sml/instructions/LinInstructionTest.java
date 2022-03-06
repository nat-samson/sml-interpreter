package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinInstructionTest {
  private Machine m;
  private Instruction instr;

  @BeforeEach
  void setUp() {
    m = new Machine();
    m.setRegisters(new Registers());
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void execute1() {
    instr = new LinInstruction("L0", 0, 0);
    instr.execute(m);
    assertEquals(0, m.getRegisters().getRegister(0));
  }

  @Test
  void execute2() {
    instr = new LinInstruction("L0", 0, 1);
    instr.execute(m);
    assertEquals(1, m.getRegisters().getRegister(0));
  }

  @Test
  void execute3() {
    instr = new LinInstruction("ABC", 17, -256);
    instr.execute(m);
    assertEquals(-256, m.getRegisters().getRegister(17));
  }

  @Test
  void execute4() {
    instr = new LinInstruction("DEF", 31, 10);
    instr.execute(m);
    assertEquals(10, m.getRegisters().getRegister(31));
  }

  @Test
  void execute5() {
    instr = new LinInstruction("Test label 123", 31, 0);
    instr.execute(m);
    assertEquals(0, m.getRegisters().getRegister(31));
  }

  @Test
  void testToString1() {
    instr = new LinInstruction("L0", 0, 0);
    assertEquals("L0: lin. Store integer with value 0 in r0", instr.toString());
  }

  @Test
  void testToString2() {
    instr = new LinInstruction("DEF", 6, 10);
    assertEquals("DEF: lin. Store integer with value 10 in r6", instr.toString());
  }
}