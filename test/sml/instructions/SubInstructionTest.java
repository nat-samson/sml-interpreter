package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubInstructionTest {
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
    instr = new SubInstruction("L0", 0, 0, 0);
    // makes use of the default machine state where all registers = 0
    instr.execute(m);
    assertEquals(0, m.getRegisters().getRegister(0));
  }

  @Test
  void execute2() {
    instr = new SubInstruction("L0", 0, 1, 2);
    m.getRegisters().setRegister(1, 3);
    m.getRegisters().setRegister(2, 7);
    instr.execute(m);
    assertEquals(-4, m.getRegisters().getRegister(0));
  }

  @Test
  void execute3() {
    instr = new SubInstruction("ABC", 17, 31, 0);
    m.getRegisters().setRegister(17, -7);
    m.getRegisters().setRegister(31, -5);
    m.getRegisters().setRegister(0, -8);
    instr.execute(m);
    assertEquals(3, m.getRegisters().getRegister(17));
  }

  @Test
  void execute4() {
    instr = new SubInstruction("DEF", 6, 10, 18);
    m.getRegisters().setRegister(10, -100);
    m.getRegisters().setRegister(18, 100);
    instr.execute(m);
    assertEquals(-200, m.getRegisters().getRegister(6));
  }

  @Test
  void execute5() {
    instr = new SubInstruction("Test label 123", 31, 31, 31);
    m.getRegisters().setRegister(31, -1);
    instr.execute(m);
    assertEquals(0, m.getRegisters().getRegister(31));
  }

  @Test
  void testToString1() {
    instr = new SubInstruction("L0", 0, 0, 0);
    assertEquals("L0: sub. Store result of r0 - r0 in r0", instr.toString());
  }

  @Test
  void testToString2() {
    instr = new SubInstruction("DEF", 6, 10, 18);
    assertEquals("DEF: sub. Store result of r10 - r18 in r6", instr.toString());
  }
}