package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DivInstructionTest {
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
  void executeDivBy0() {
    instr = new DivInstruction("L0", 0, 0, 0);
    assertThrows(ArithmeticException.class, () -> instr.execute(m));
  }

  @Test
  void execute1() {
    instr = new DivInstruction("L0", 0, 0, 0);
    m.getRegisters().setRegister(0, 1);
    instr.execute(m);
    assertEquals(1, m.getRegisters().getRegister(0));
  }

  @Test
  void execute2() {
    instr = new DivInstruction("L0", 0, 1, 2);
    m.getRegisters().setRegister(1, 3);
    m.getRegisters().setRegister(2, 7);
    instr.execute(m);
    assertEquals(0, m.getRegisters().getRegister(0));
  }

  @Test
  void execute3() {
    instr = new DivInstruction("ABC", 17, 31, 0);
    m.getRegisters().setRegister(17, -17);
    m.getRegisters().setRegister(31, -15);
    m.getRegisters().setRegister(0, -5);
    instr.execute(m);
    assertEquals(3, m.getRegisters().getRegister(17));
  }

  @Test
  void execute4() {
    instr = new DivInstruction("DEF", 6, 10, 18);
    m.getRegisters().setRegister(10, -100);
    m.getRegisters().setRegister(18, 100);
    instr.execute(m);
    assertEquals(-1, m.getRegisters().getRegister(6));
  }

  @Test
  void execute5() {
    instr = new DivInstruction("Test label 123", 31, 31, 31);
    m.getRegisters().setRegister(31, -1);
    instr.execute(m);
    assertEquals(1, m.getRegisters().getRegister(31));
  }

  @Test
  void testToString1() {
    instr = new DivInstruction("L0", 0, 0, 0);
    assertEquals("L0: div. Store result of r0 / r0 in r0", instr.toString());
  }

  @Test
  void testToString2() {
    instr = new DivInstruction("DEF", 6, 10, 18);
    assertEquals("DEF: div. Store result of r10 / r18 in r6", instr.toString());
  }
}