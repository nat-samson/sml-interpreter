package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;
import sml.Translator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OutInstructionTest {
  private Machine m;
  private Translator t;
  private Instruction instr;

  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    m = new Machine();
    m.setRegisters(new Registers());
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @AfterEach
  void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  void execute1() {
    instr = new OutInstruction("L0", 0);
    // using default state of registers where all contain 0
    instr.execute(m);
    assertEquals("r0 contains: 0\n", outputStreamCaptor.toString());
  }

  @Test
  void execute9() {
    instr = new DivInstruction("L0", 0, 0, 0);
    m.getRegisters().setRegister(0, 1);
    instr.execute(m);
    assert m.getRegisters().getRegister(0) == 1;
  }

  @Test
  void execute2() {
    instr = new OutInstruction("L0", 0);
    m.getRegisters().setRegister(0, 7);
    instr.execute(m);
    assertEquals("r0 contains: 7\n", outputStreamCaptor.toString());
  }

  @Test
  void execute3() {
    instr = new OutInstruction("ABC", 17);
    m.getRegisters().setRegister(17, -17);
    instr.execute(m);
    assertEquals("r17 contains: -17\n", outputStreamCaptor.toString());
  }

  @Test
  void execute4() {
    instr = new OutInstruction("DEF", 6);
    m.getRegisters().setRegister(6, -100);
    instr.execute(m);
    assertEquals("r6 contains: -100\n", outputStreamCaptor.toString());
  }

  @Test
  void execute5() {
    instr = new OutInstruction("Test label 123", 31);
    m.getRegisters().setRegister(31, -1);
    instr.execute(m);
    assertEquals("r31 contains: -1\n", outputStreamCaptor.toString());
  }

  @Test
  void testToString1() {
    instr = new OutInstruction("L0", 0);
    assert instr.toString().equals("L0: out. Print the contents of r0 to the console");
  }

  @Test
  void testToString2() {
    instr = new OutInstruction("DEF", 6);
    assert instr.toString().equals("DEF: out. Print the contents of r6 to the console");
  }
}