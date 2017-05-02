// This is the SHELL for the STEP class for the CPU SIM project
// STUDENT NAME: Travis Wight
// STUDENT STATUS COMMENTS:
// Part1:
// COMPLETE...
// Part2:
// COMPLETE...
// Part3:
/* Made print statements before starting each method that are marked "for reference".
*  They can be commented out if I forget to do that myself as they're really only for me.
*  Updated code (hopefully fixed, because I don't see any more issues yet) in the FI function
*  I made other comments in the actual code.
* */
// Part4:
/* I'm pretty sure that what I did was more or less correct, however I'm still going to address it
*  just to make note of my thought process. I executed all of the functions, not just the ones that
*  are in the document. I'm certain that's what we're supposed to do, but like I said, I just want
*  to make note of it to err on the side of caution.
*
*  fixed the STBYTEA,n error, is now STBYTEA,d
*
*  COMPLETE...
* */
// Part5:
/*
*  I made some comments in the actual code.
*  For most of the methods, I assumed that a lot of the code was similar to exactly the same as
*  the code before.
*  For the load/store byte parts, I went to the tutoring room, one of tutors mentioned that I need
*  to load/store either the high or low order byte. I tried both, but they both display identical
*  outputs (as far as I can tell, maybe it'll make difference in the later implementations. I don't
*  know), so I'm kind of at a loss at this point (I left both implementations in, with the low-order
*  implementation for each part commented out).
*
* */
// Part6:
/*
*  * UPDATE *
*  I corrected the parts I did incorrectly last week (I think):
*  Changed the store/load implementation to store/load the low order byte.
*  Fixed the AND/OR/CP implementations.
*
*  I made some comments inside my code regarding parts I was unsure about.
*
*  COMPLETE...
*  */
// Part7:
/*
*  The code is doesn't work. I finished the branches however I'm not sure if all of them work.
*  I'm pretty sure they should though because the BRGE and BRGT instruction do, however
*  after a certain amount of instructions, the program terminates on it's own because the code
*  tries to get an invalid memory address...
*
*  This is the main issue, other issues consist of incorrectly executed instructions (mostly
*  ASL and ASR). The last instruction that is executed before terminating is ADDA, n and the
*  instruction trying to execute is SUBA, x. I did pinpoint the issue to a STX instruction that
*  executes prior though, which concatenate HO byte 0x1 and 0x2 as 0x102 and stores that value
*  into memory. Later on there's a LDX function, which takes this value in memory (0x102) and
*  gets 2 bytes from memory. This makes 0x10200, which is where my issue lies. I have no idea
*  where to go from here, because the way I coded makes sense (at least I think it does)...
*
*  DOESN'T WORK...
* */
//
//----------------------------------------------------------------------------------------------------
public class STEP{
//GLOBAL VARIABLES
    CPU cpu; // this is the object(instance of a class) that represents the 'STATE' of the CPU
      /* these are the CPU class(Object) private attributes(variables)which represent the 'state' of the CPU
      private int A; private int X; private int PC; private int SP;
      private byte N; private byte Z; private byte V; private byte C;
      private int IS; private int OS; private int OP;
      private String DESCR; private char MODE; private char [ ] MEMORY;
      //
      // accessors
      public int getPC() // returns PC
      public int getSP() // returns SP
      public int getIS() // returns IS
      public int getOS() // returns OS
      public int getOP() // returns OP
      public int getA()  // returns A
      public int getX()  // returns X
      public byte getN() // returns N
      public byte getZ() // returns Z
      public byte getV() // returns V
      public byte getC() // returns C
      public String getDESCR()  // returns DESCR
      public char getMODE()     // returns MODE
      public char getMEMORY(int address)
      // IF address is valid it returns MEMORY[address]
      // otherwise it displays an error message and exits
      //
      // mutators
      public void setPC(int pc) // sets PC
      public void setSP(int sp) // sets SP
      public void setIS(int is) // sets IS
      public void setOS(int os) // sets OS
      public void setOP(int op) // sets OP
      public void setA(int a)  // sets A
      public void setX(int x)  // sets X
      public void setN(byte n)  // sets N
      public void setZ(byte n)  // sets Z
      public void setV(byte n)  // sets V
      public void setC(byte n)  // sets C
      public void setDESCR(String descr)  // sets DESCR
      public void setMODE(char mode)      // sets MODE
      public void setMEMORY(int address, char value)
      // IF address is invalid(out of range) it displays an error message and exits
      // otherwise it sets MEMORY[address] = value
      //
     */
    // GLOBAL VARIABLES
    boolean CO=true; boolean FO=true; boolean WO=false; // logical vars that control the stage execution
    boolean DIS=false; // logical var that controls displaying the updated CPU values to assist debugging
    // 
    boolean Unary=false; // logical var to differentiate between the one/three byte type instructions
    //
    int EA=0;    // this var holds: 1) the address of the instruction to be 'fetched OR
                 //                 2) the instruction's operand memory effective address; 
                 // this address is used as the INDEX to to read/write from/to memory array
    int NEA=0;   // this a temp var is used for iNdirect address calculating/processing
    char DATA=0; // this var holds the single byte that is read from or written to memory
    int OP=0;    // this var is used to SWITCH on the opcode in the DI() and EX() methods
    //
// the 'main'>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public static void main(String args[]) {
        new SIMULATOR();
  } // all the main does is to instantiate an object from the SIMULATOR class
//
// end of the 'main' >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//
// this method is invoked by the SIMULATOR when the user clicks the 'STEP' button
// and executes ONE VON NEUMANN CYCLE using/updating the CPU state passed as a parameter
    public void dostep(CPU cpu) { // the VonNeumann loop
           this.cpu = cpu; // the CPU state object
     // reset global vars for the start of a new Instruction cycle
           Unary=false; CO=true; FO=true; WO=false; EA=0; NEA=0; DATA=0; OP=0;
        System.out.println("\n------------------------ PRE EX ------------------------");
        System.out.println("--------------------- PART 7 DEBUG ---------------------\n");
           FI(); // always executed
           DI(); // always executed
    if(CO) CO(); // MAY be skipped
    if(FO) FO(); // MAY be skipped
           EX(); // always executed
    if(WO) WO(); // MAY be skipped
        System.out.println("\n------------------- END PART 7 DEBUG -------------------\n");
// end of one pass of the VonNeumann cycle
    if(DIS) DISPLAY(); // optional step to display CPU values to the printer
//           
    return;} // end of dostep method
//
//
// START OF Instruction State METHODS
  void FI(){
      /*cpu.setIS(cpu.getPC());
      cpu.setPC(cpu.getPC()+1);*/

      System.out.println("FI method executed"); // for reference

      Unary=false; CO=true; FO=true; WO=false;

      //rest of pt 1
      EA = cpu.getPC();
      DATA = cpu.getMEMORY(EA);
      OP = (int)DATA;
      cpu.setIS((int)DATA);
      cpu.setPC(cpu.getPC()+1);

      if(OP == 0){
          Unary = true;
          CO = false; FO = false; WO = false;
          //System.out.println("Unary"); // for reference
          cpu.setDESCR("STOP");
          cpu.setOS(0); //resetting OS since Unary
      }
      else if((OP >= 0x00 && OP <= 0x03) || (OP >= 0x18 && OP <= 0x27) || (OP >= 0x58 && OP <=0x5F)){
          Unary = true;
          CO = false; FO = false; WO = false;
          //System.out.println("Unary"); // for reference
          cpu.setOS(0); //resetting OS since Unary
      }
      else{
          //shift left data leaving 0000 at the end
          //System.out.println("Trinary"); // for reference
          EA = cpu.getPC();
          DATA = cpu.getMEMORY(EA);
          cpu.setOS(DATA << 8); //HO
          cpu.setPC(cpu.getPC()+1);
          EA = cpu.getPC();
          //System.out.println("EA from FI: " + EA);
          DATA = cpu.getMEMORY(cpu.getPC());
          //System.out.println("DATA:" + DATA);
          cpu.setOS(cpu.getOS() | DATA); //LO
          //System.out.println("cpu.getOS(): " + Integer.toHexString(cpu.getOS()));
          cpu.setPC(cpu.getPC()+1);
      }

  }// end of the FI() method
  //
  void DI(){

      System.out.println("DI method executed"); // for reference
      //System.out.println("OP: " + OP); // <-- For reference.

      cpu.setMODE(' '); //default
      switch (OP) {
          case 0:
              cpu.setDESCR("STOP");
              break;
          case 4:
              cpu.setDESCR("BR");
              cpu.setMODE('i');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 5:
              cpu.setDESCR("BR");
              cpu.setMODE('x');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 6:
              cpu.setDESCR("BRLE");
              cpu.setMODE('i');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 7:
              cpu.setDESCR("BRLE");
              cpu.setMODE('x');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 8:
              cpu.setDESCR("BRLT");
              cpu.setMODE('i');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 9:
              cpu.setDESCR("BRLT");
              cpu.setMODE('x');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 10:
              cpu.setDESCR("BREQ");
              cpu.setMODE('i');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 11:
              cpu.setDESCR("BREQ");
              cpu.setMODE('x');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 12:
              cpu.setDESCR("BRNE");
              cpu.setMODE('i');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 13:
              cpu.setDESCR("BRNE");
              cpu.setMODE('x');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 14:
              cpu.setDESCR("BRGE");
              cpu.setMODE('i');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 15:
              cpu.setDESCR("BRGE");
              cpu.setMODE('x');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 16:
              cpu.setDESCR("BRGT");
              cpu.setMODE('i');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 17:
              cpu.setDESCR("BRGT");
              cpu.setMODE('x');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 18:
              cpu.setDESCR("BRV");
              cpu.setMODE('i');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 19:
              cpu.setDESCR("BRV");
              cpu.setMODE('x');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 20:
              cpu.setDESCR("BRC");
              cpu.setMODE('i');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 21:
              cpu.setDESCR("BRC");
              cpu.setMODE('x');
              CO = true;
              FO = false;
              WO = false;
              break;
          case 24:
              cpu.setDESCR("NOTA");
              break;
          case 25:
              cpu.setDESCR("NOTX");
              break;
          case 26:
              cpu.setDESCR("NEGA");
              break;
          case 27:
              cpu.setDESCR("NEGX");
              break;
          case 28:
              cpu.setDESCR("ASLA");
              break;
          case 29:
              cpu.setDESCR("ASLX");
              break;
          case 30:
              cpu.setDESCR("ASRA");
              break;
          case 31:
              cpu.setDESCR("ASRX");
              break;
          case 32:
              cpu.setDESCR("ROLA");
              break;
          case 33:
              cpu.setDESCR("ROLX");
              break;
          case 34:
              cpu.setDESCR("RORA");
              break;
          case 35:
              cpu.setDESCR("RORX");
              break;
      }

      switch (cpu.getIS()){
          case 112:
              cpu.setDESCR("ADDA");
              cpu.setMODE('i');
              break;
          case 113:
              cpu.setDESCR("ADDA");
              cpu.setMODE('d');
              break;
          case 114:
              cpu.setDESCR("ADDA");
              cpu.setMODE('n');
              break;
          //115
          //116
          case 117:
              cpu.setDESCR("ADDA");
              cpu.setMODE('x');
              break;
          //118
          //119
          case 120:
              cpu.setDESCR("ADDX");
              cpu.setMODE('i');
              break;
          case 121:
              cpu.setDESCR("ADDX");
              cpu.setMODE('d');
              break;
          case 122:
              cpu.setDESCR("ADDX");
              cpu.setMODE('n');
              break;
          //123
          //124
          case 125:
              cpu.setDESCR("ADDX");
              cpu.setMODE('x');
              break;
          //126
          //127
          case 128:
              cpu.setDESCR("SUBA");
              cpu.setMODE('i');
              break;
          case 129:
              cpu.setDESCR("SUBA");
              cpu.setMODE('d');
              break;
          case 130:
              cpu.setDESCR("SUBA");
              cpu.setMODE('n');
              break;
          //131
          //132
          case 133:
              cpu.setDESCR("SUBA");
              cpu.setMODE('x');
              break;
          //134
          //135
          case 136:
              cpu.setDESCR("SUBX");
              cpu.setMODE('i');
              break;
          case 137:
              cpu.setDESCR("SUBX");
              cpu.setMODE('d');
              break;
          case 138:
              cpu.setDESCR("SUBX");
              cpu.setMODE('n');
              break;
          //139
          //140
          case 141:
              cpu.setDESCR("SUBX");
              cpu.setMODE('x');
              break;
          //142
          //143
          case 144:
              cpu.setDESCR("ANDA");
              cpu.setMODE('i');
              break;
          case 145:
              cpu.setDESCR("ANDA");
              cpu.setMODE('d');
              break;
          case 146:
              cpu.setDESCR("ANDA");
              cpu.setMODE('n');
              break;
          //147
          //148
          case 149:
              cpu.setDESCR("ANDA");
              cpu.setMODE('x');
              break;
          //150
          //151
          case 152:
              cpu.setDESCR("ANDX");
              cpu.setMODE('i');
              break;
          case 153:
              cpu.setDESCR("ANDX");
              cpu.setMODE('d');
              break;
          case 154:
              cpu.setDESCR("ANDX");
              cpu.setMODE('n');
              break;
          //155
          //156
          case 157:
              cpu.setDESCR("ANDX");
              cpu.setMODE('x');
              break;
          //158
          //159
          case 160:
              cpu.setDESCR("ORA");
              cpu.setMODE('i');
              break;
          case 161:
              cpu.setDESCR("ORA");
              cpu.setMODE('d');
              break;
          case 162:
              cpu.setDESCR("ORA");
              cpu.setMODE('n');
              break;
          //163
          //164
          case 165:
              cpu.setDESCR("ORA");
              cpu.setMODE('x');
              break;
          //166
          //167
          case 168:
              cpu.setDESCR("ORX");
              cpu.setMODE('i');
              break;
          case 169:
              cpu.setDESCR("ORX");
              cpu.setMODE('d');
              break;
          case 170:
              cpu.setDESCR("ORX");
              cpu.setMODE('n');
              break;
          //171
          //172
          case 173:
              cpu.setDESCR("ORX");
              cpu.setMODE('x');
              break;
          //174
          //175
          case 176:
              cpu.setDESCR("CPA");
              cpu.setMODE('i');
              break;
          case 177:
              cpu.setDESCR("CPA");
              cpu.setMODE('d');
              break;
          case 178:
              cpu.setDESCR("CPA");
              cpu.setMODE('n');
              break;
          //179
          //180
          case 181:
              cpu.setDESCR("CPA");
              cpu.setMODE('x');
              break;
          //182
          //183
          case 184:
              cpu.setDESCR("CPX");
              cpu.setMODE('i');
              break;
          case 185:
              cpu.setDESCR("CPX");
              cpu.setMODE('d');
              break;
          case 186:
              cpu.setDESCR("CPX");
              cpu.setMODE('n');
              break;
          //187
          //188
          case 189:
              cpu.setDESCR("CPX");
              cpu.setMODE('x');
              break;
          //190
          //191
          case 192:
              cpu.setDESCR("LDA");
              cpu.setMODE('i');
              break;
          case 193:
              cpu.setDESCR("LDA");
              cpu.setMODE('d');
              break;
          case 194:
              cpu.setDESCR("LDA");
              cpu.setMODE('n');
              break;
          //195
          //196
          case 197:
              cpu.setDESCR("LDA");
              cpu.setMODE('x');
              break;
          //198
          //199
          case 200:
              cpu.setDESCR("LDX");
              cpu.setMODE('i');
              break;
          case 201:
              cpu.setDESCR("LDX");
              cpu.setMODE('d');
              break;
          case 202:
              cpu.setDESCR("LDX");
              cpu.setMODE('n');
              break;
          //203
          //204
          case 205:
              cpu.setDESCR("LDX");
              cpu.setMODE('x');
              break;
          //206
          //207
          case 208:
              cpu.setDESCR("LDBYTEA");
              cpu.setMODE('i');
              break;
          case 209:
              cpu.setDESCR("LDBYTEA");
              cpu.setMODE('d');
              break;
          case 210:
              cpu.setDESCR("LDBYTEA");
              cpu.setMODE('n');
              break;
          //211
          //212
          case 213:
              cpu.setDESCR("LDBYTEA");
              cpu.setMODE('x');
              break;
          //214
          //215
          case 216:
              cpu.setDESCR("LDBYTEX");
              cpu.setMODE('i');
              break;
          case 217:
              cpu.setDESCR("LDBYTEX");
              cpu.setMODE('d');
              break;
          case 218:
              cpu.setDESCR("LDBYTEX");
              cpu.setMODE('n');
              break;
          //219
          //220
          case 221:
              cpu.setDESCR("LDBYTEX");
              cpu.setMODE('x');
              break;
          //222
          //223
          case 224:
              cpu.setDESCR("STA");
              cpu.setMODE('i');
              CO = true; FO = false; WO = true;
              break;
          case 225:
              cpu.setDESCR("STA");
              cpu.setMODE('d');
              CO = true; FO = false; WO = true;
              break;
          case 226:
              cpu.setDESCR("STA");
              cpu.setMODE('n');
              CO = true; FO = false; WO = true;
              break;
          //227
          //228
          case 229:
              cpu.setDESCR("STA");
              cpu.setMODE('x');
              CO = true; FO = false; WO = true;
              break;
          //230
          //231
          case 232:
              cpu.setDESCR("STX");
              cpu.setMODE('i');
              CO = true; FO = false; WO = true;
              break;
          case 233:
              cpu.setDESCR("STX");
              cpu.setMODE('d');
              CO = true; FO = false; WO = true;
              break;
          case 234:
              cpu.setDESCR("STX");
              cpu.setMODE('n');
              CO = true; FO = false; WO = true;
              break;
          //235
          //236
          case 237:
              cpu.setDESCR("STX");
              cpu.setMODE('x');
              CO = true; FO = false; WO = true;
              break;
          //237
          //238
          case 239:
              cpu.setDESCR("STBYTEA");
              cpu.setMODE('d');
              CO = true; FO = false; WO = true;
              break;
          case 240:
              cpu.setDESCR("STBYTEA");
              cpu.setMODE('n');
              CO = true; FO = false; WO = true;
              break;
          //241
          //242
          case 243:
              cpu.setDESCR("STBYTEA");
              cpu.setMODE('x');
              CO = true; FO = false; WO = true;
              break;
          //244
          //245
          case 246:
              cpu.setDESCR("STBYTEX");
              cpu.setMODE('d');
              CO = true; FO = false; WO = true;
              break;
          case 247:
              cpu.setDESCR("STBYTEX");
              cpu.setMODE('n');
              CO = true; FO = false; WO = true;
              break;
          //248
          //249
          case 250:
              cpu.setDESCR("STBYTEX");
              cpu.setMODE('x');
              CO = true; FO = false; WO = true;
              break;
          //251
          //252
          //253
          //254
          //255
          //DONE
      }

  }// end of the DI() method
  void CO(){
      System.out.println("CO method executed"); // for reference

      char mode = cpu.getMODE();

      //System.out.println("DESCRIPTION: " + cpu.getDESCR());
      //System.out.println("MODE: " + cpu.getMODE());
      //System.out.println("OP: " + OP);
      //System.out.println("IS: " + cpu.getIS());

      if(mode == 'x'){
          //System.out.println("x");
          //System.out.println("0x" + Integer.toHexString(cpu.getOS()));
          //System.out.println(cpu.getX());
          EA = cpu.getOS() + cpu.getX();
      }
      else  if(mode == 'n'){
          //System.out.println("n");
          //System.out.println(cpu.getOS());
          NEA = (cpu.getMEMORY(cpu.getOS()) << 8) | cpu.getMEMORY(cpu.getOS() + 1); // 2 bytes
          EA = NEA;
          //System.out.println("EA = 0x" + Integer.toHexString(EA));
          // Went to the tutoring room for help with this problem. Not sure if it's completely right,
          // but they said that this should work.
      }
      else if(mode == 'd'){
          //System.out.println("d");
          EA = cpu.getOS();
      }
      else if(mode == 'i'){
          //System.out.println("i");
          FO = false;
          cpu.setOP(cpu.getOS());
          EA = cpu.getOS();
      }

  } // end of the CO() method
  //
  void FO(){
      System.out.println("FO method executed"); // for reference
      NEA = 0; // clearing NEA for use here
      //System.out.println(Integer.toHexString(EA));
      if(OP == 0xD0 || OP == 0xD1 || OP == 0xD2 || OP == 0xD5
              || OP == 0xD8 || OP == 0xD9 || OP == 0xDA || OP == 0xDD){ // if LDBYTEA or LDBYTEX
          //System.out.println("inside if");
          NEA = cpu.getMEMORY(EA) & 0xFF; //get LO byte
          cpu.setOP(NEA);
      }
      else{
          //System.out.println(cpu.getDESCR());
          //System.out.println("inside else");
          System.out.println("EA: 0x" + Integer.toHexString(EA));
          System.out.println("EA + 1: 0x" + Integer.toHexString(EA + 1));
          // (part 7) I think this is where my issue is:
          System.out.println("HO EA: 0x" + Integer.toHexString(cpu.getMEMORY(EA)));
          System.out.println("LO EA: 0x" + Integer.toHexString(cpu.getMEMORY(EA+1)));
          NEA = (cpu.getMEMORY(EA) << 8) | cpu.getMEMORY(EA+1); // 2 bytes
          System.out.println("NEA: 0x" + Integer.toHexString(NEA));
          cpu.setOP(NEA);
          System.out.println("FOR PART 7: 0x" + Integer.toHexString(cpu.getOP()));
      }
      //System.out.println("cpu.getOP: 0x" + Integer.toHexString(cpu.getOP()));
  //
  }// end of the FO() method
  //
  void EX(){
      System.out.println("EX method executed"); // for reference
      OP = cpu.getIS();

      if(cpu.getIS() >= 0x70) {
          OP = (cpu.getIS() & 0xF8);
      }

      switch (OP){
          case 0x00:
              System.out.println("STOP INSTRUCTION EXECUTED");
              break;
          case 0x04:case 0x05:
              BR();
              break;
          case 0x06:case 0x07:
              BRLE();
              break;
          case 0x08:case 0x09:
              BRLT();
              break;
          case 0x0A:case 0x0B:
              BREQ();
              break;
          case 0x0C:case 0x0D:
              BRNE();
              break;
          case 0x0E:case 0x0F:
              BRGE();
              break;
          case 0x10:case 0x11:
              //BRGT();
              break;
          case 0x12:case 0x13:
              BRV();
              break;
          case 0x14:case 0x15:
              BRC();
              break;
          //end of branch
          case 0x18:
              NOTA();
              break;
          case 0x19:
              NOTX();
              break;
          case 0x1A:
              NEGA();
              break;
          case 0x1B:
              NEGX();
              break;
          case 0x1C:
              ASLA();
              break;
          case 0x1D:
              ASLX();
              break;
          case 0x1E:
              ASRA();
              break;
          case 0x1F:
              ASRX();
              break;
          case 0x20:
              ROLA();
              break;
          case 0x21:
              ROLX();
              break;
          case 0x22:
              RORA();
              break;
          case 0x23:
              RORX();
              break;
          // end of unary
          case 0x70:
              ADDA();
              break;
          case 0x78:
              ADDX();
              break;
          case 0x80:
              SUBA();
              break;
          case 0x88:
              SUBX();
              break;
          case 0x90:
              ANDA();
              break;
          case 0x98:
              ANDX();
              break;
          case 0xA0:
              ORA();
              break;
          case 0xA8:
              ORX();
              break;
          case 0xB0:
              CPA();
              break;
          case 0xB8:
              CPX();
              break;
          case 0xC0:
              LDA();
              break;
          case 0xC8:
              LDX();
              break;
          case 0xD0:
              LDBYTEA();
              break;
          case 0xD8:
              LDBYTEX();
              break;
          case 0xE0:
              STA();
              break;
          case 0xE8:
              STX();
              break;
          case 0xF0:
              STBYTEA();
              break;
          case 0xF8:
              STBYTEX();
              break;
          // end of trinary
          default:
              Unary = false;
              cpu.setDESCR("INVALID");
              break;
      }
  //
  } // end of the EX() method
  //
  void WO(){
      System.out.println("WO method executed"); // for reference
      NEA = 0; // clearing NEA
      if(OP == 239 || OP == 240 || OP == 241 || OP == 244 || OP == 247
              || OP == 248 || OP == 249 || OP == 252){ // STBYTEA or STBYTEX
          NEA = cpu.getOP() & 0xFF; // LO
          //System.out.println((char)NEA);
          cpu.setMEMORY(EA,(char)NEA);
          // Not sure this is correct. I assumed that I needed to cast the NEA to a char value.
          // not sure if I needed to do it the other way around but that way didn't make sense
          // to me (cast EA to char and set the NEA as the address).
      }
      else if(OP == 224 || OP == 225 || OP == 226 || OP == 229 || OP == 232
              || OP == 233 || OP == 234 || OP == 237){ // STA or STX

          // RIGHT HERE, THIS IS WHERE PART 7 IS CAUSING MY PROBLEM...
          System.out.println("getOP(): 0x" + Integer.toHexString(cpu.getOP()));
          System.out.println("getOP() + 1: 0x" + Integer.toHexString(cpu.getOP() + 1));
          NEA = (cpu.getOP() << 8) | (cpu.getOP()+1); // HO | LO
          System.out.println("EA: 0x" + Integer.toHexString(EA));
          cpu.setMEMORY(EA,(char)NEA);
          System.out.println("FOR WO DEBUG PT 7:");
          System.out.println("getMEMORY(EA): 0x" + Integer.toHexString(cpu.getMEMORY(EA)));
          // Same assumption as above.
      }
  //
  }// end of the WO()
//
// start of individual instruction execution methods
// these methods will be invoked/called from the EX() method
//
  // start of branches
  // all branches will use the EA calculated in the CO stage
  // since the cpu.OP would have NOT been updated because
  // the FO stage was NOT executed
  void BR()   {
      // part 7
      System.out.println("BR INSTRUCTION EXECUTED");
      // branches unconditionally
      // PC = EA
      cpu.setPC(EA);
  } // end of BR()
  //
  void BRLE() {
      // part 7
      System.out.println("BRLE INSTRUCTION EXECUTED");
      // branches if less than or equal to
      if(cpu.getN() == 1 || cpu.getZ() == 1){
          cpu.setPC(EA);
      }
  } // end of BRLE()
//
  void BRLT() {
      // part 7
      System.out.println("BRLT INSTRUCTION EXECUTED");
      // branches if less than
      if(cpu.getN() == 1){
          cpu.setPC(EA);
      }
  } // end of BRLT()
//
  void BREQ() {
      // part 7
      System.out.println("BREQ INSTRUCTION EXECUTED");
      // branches if equal
      if(cpu.getZ() == 1){
          cpu.setPC(EA);
      }
  } // end of BREQ()
//
  void BRNE() {
      // part 7
      System.out.println("BRNE INSTRUCTION EXECUTED");
      // branches if not equal
      if(cpu.getZ() == 0){
          cpu.setPC(EA);
      }
  } // end of BRNE()
//
  void BRGE() {
      // part 7
      System.out.println("BRGE INSTRUCTION EXECUTED");
      // branches if greater than or equal
      //System.out.println("EA: " + EA);
      //System.out.println("cpu.getA(): " + cpu.getA());
      if(cpu.getN() != 1 || cpu.getZ() == 1){
          cpu.setPC(EA);
      }
  } // end of BRGE()
//
  void BRGT() {
      // part 7
      System.out.println("BRGT INSTRUCTION EXECUTED");
      // branches if greater than
      //System.out.println("EA: " + EA);
      //System.out.println("cpu.getA(): " + cpu.getA());
      if(cpu.getN() == 0){
          cpu.setPC(EA);
      }
  } // end of BRGT()
//
  void BRV() {
      // part 7
      System.out.println("BRV INSTRUCTION EXECUTED");
      // branches if V
      if(cpu.getV() == 1){
          cpu.setPC(EA);
      }
  } // end of BRV()
//
  void BRC() {
      // part 7
      System.out.println("BRC INSTRUCTION EXECUTED");
      // branches if C
      if(cpu.getC() == 1){
          cpu.setPC(EA);
      }
  } // end of BRC()
//
// start of unary instr.
  void NOTA() {
      // part 6
      System.out.println("NOTA INSTRUCTION EXECUTED");

      // REG = REG
      cpu.setA(~cpu.getA());

      // Checking for ZERO (A)
      if(cpu.getA() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // Checking for NEG (A)
      if((cpu.getA() & 0x00008000) == 0x00008000){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }

      // Not sure if I have to do the other status bits for this part
  } // end of NOTA()
//
  void NOTX() {
      // part 6
      System.out.println("NOTX INSTRUCTION EXECUTED");

      // REG = REG
      cpu.setX(~cpu.getX());

      // Checking for ZERO (X)
      if(cpu.getX() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // Checking for NEG (X)
      if((cpu.getX() & 0x00008000) == 0x00008000){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }
  } // end of NOTX()
//
  void NEGA() {
      // part 6
      System.out.println("NEGA INSTRUCTION EXECUTED");

      // REG = REG
      cpu.setA(-cpu.getA());

      // Checking for ZERO (A)
      if(cpu.getA() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // Checking for NEG (A)
      if((cpu.getA() & 0x00008000) == 0x00008000){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }

      // Checking for OVERFLOW (NEGA)
      // I'm not certain how NEG can overflow. My guess would be if A is +,
      // negated, and still positive or if A is -, negated, and still - then
      // there's an overflow.

      if((cpu.getA() > 0 && -cpu.getA() > 0) ||
              (cpu.getA() < 0 && -cpu.getA() < 0)){
          cpu.setV((byte)1);
      }
      else{
          cpu.setV((byte)0);
      }
  } // end of NEGA()
//
  void NEGX() {
      // part 6
      System.out.println("NEGX INSTRUCTION EXECUTED");

      // REG = REG
      cpu.setX(-cpu.getX());

      // Checking for ZERO (A)
      if(cpu.getX() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // Checking for NEG (X)
      if((cpu.getX() & 0x00008000) == 0x00008000){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }

      // Checking for OVERFLOW (NEGX)
      if((cpu.getX() > 0 && -cpu.getX() > 0) ||
              (cpu.getX() < 0 && -cpu.getX() < 0)){
          cpu.setV((byte)1);
      }
      else{
          cpu.setV((byte)0);
      }
  } // end of NEGX()
//
  void ASLA() {
      // part 6
      System.out.println("ASLA INSTRUCTION EXECUTED");

      // Checking for CARRY (ASLA)
      cpu.setC((byte)((cpu.getA() & 0xFFFF) >> 17));

      // REG = REG
      cpu.setA(cpu.getA() << 1);

      // Checking for ZERO (A)
      if(cpu.getA() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // Checking for NEG (A)
      if((cpu.getA() & 0x00008000) == 0x00008000){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }

      // Checking for OVERFLOW (ASLA)
      // Because the ASL is essentially just multiplying the REG by (or adding
      // the REG to itself), that's what I did to check for OVERFLOW HERE.

      if((cpu.getA() > 0 && (cpu.getA() + cpu.getA()) < 0)
              || (cpu.getA() < 0 && (cpu.getA() + cpu.getA()) > 0)){
          cpu.setV((byte)1);
      }
      else{
          cpu.setV((byte)0);
      }
  } // end of ASLA()
//
  void ASLX() {
      // part 6
      System.out.println("ASLX INSTRUCTION EXECUTED");

      // Checking for CARRY (ASLX)
      cpu.setC((byte)((cpu.getX() & 0xFFFF) >> 17));

      // REG = REG
      cpu.setX(cpu.getX() << 1);

      // Checking for ZERO (X)
      if(cpu.getX() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // Checking for NEG (X)
      if((cpu.getX() & 0x00008000) == 0x00008000){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }

      // Checking for OVERFLOW (ASLX)
      if((cpu.getX() > 0 && (cpu.getX() + cpu.getX()) < 0)
              || (cpu.getX() < 0 && (cpu.getX() + cpu.getX()) > 0)){
          cpu.setV((byte)1);
      }
      else{
          cpu.setV((byte)0);
      }
  } // end of ASLX()
//           
  void ASRA() {
      // part 6
      System.out.println("ASRA INSTRUCTION EXECUTED");

      // copy/save rightmost bit to CARRY
      cpu.setC((byte)(cpu.getA() & 1));

      // copy/save leftmost bit to temp (sign)
      int sign = (cpu.getA() & 0xFFFF) >> 17;

      // REG = REG
      cpu.setA(cpu.getA() >> 1);

      // Set leftmost bit to sign
      cpu.setA(cpu.getA() | (sign << 17));

      // Check for ZERO (A)
      if(cpu.getA() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // Check for NEG (A)
      if((cpu.getA() & 0x00008000) == 0x00008000){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }

  } // end of ASRA()
//
  void ASRX() {
      // part 6
      System.out.println("ASRX INSTRUCTION EXECUTED");

      // copy/save rightmost bit to CARRY
      cpu.setC((byte)(cpu.getX() & 1));

      // copy/save leftmost bit to temp (sign)
      int sign = (cpu.getX() & 0xFFFF) >> 17;

      // REG = REG
      cpu.setX(cpu.getX() >> 1);

      // Set leftmost bit to sign
      cpu.setX(cpu.getX() | (sign << 17));

      // Check for ZERO (X)
      if(cpu.getX() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // Check for NEG (X)
      if((cpu.getX() & 0x00008000) == 0x00008000){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }
  } // end of ASRX()
//
  void ROLA() {
      // part 6
      System.out.println("ROLA INSTRUCTION EXECUTED");

      // copy/save the value of the leftmost bit (int LeftC)
      int LeftC = (cpu.getA() & 0xFFFF) >> 17;

      // REG = REG
      cpu.setA(cpu.getA() << 1);

      // use the current C bit to set rightmost value
      cpu.setA(cpu.getA() | cpu.getC());

      // use the copied/saved LeftC value to SET c bit
      cpu.setC((byte)LeftC);
  } // end of ROLA()
//
  void ROLX() {
      // part 6
      System.out.println("ROLX INSTRUCTION EXECUTED");

      // copy/save the value of the leftmost bit (int LeftC)
      int LeftC = (cpu.getX() & 0xFFFF) >> 17;

      // REG = REG
      cpu.setX(cpu.getX() << 1);

      // use the current C bit to set rightmost value
      cpu.setX(cpu.getX() | cpu.getC());

      // use the copied/saved LeftC value to SET c bit
      cpu.setC((byte)LeftC);
  } // end of ROLX()
//
  void RORA() {
      // part 6
      System.out.println("RORA INSTRUCTION EXECUTED");

      // copy/save the value of the rightmost bit (int RightC)
      int RightC = cpu.getA() ^ (cpu.getA() & (cpu.getA() - 1));

      // REG = REG
      cpu.setA(cpu.getA() >> 1);

      // use the current C bit to set the leftmost value
      cpu.setA(cpu.getA() | (cpu.getC() << 17));

      // use the copied/saved RightC value to SET c bit
      cpu.setC((byte)RightC);
  } // end of RORA()
//
  void RORX() {
      // part 6
      System.out.println("RORX INSTRUCTION EXECUTED");

      // copy/save the value of the rightmost bit (int RightC)
      int RightC = cpu.getX() ^ (cpu.getX() & (cpu.getX() - 1));

      // REG = REG
      cpu.setX(cpu.getX() >> 1);

      // use the current C bit to set the leftmost value
      cpu.setX(cpu.getX() | (cpu.getC() << 17));

      // use the copied/saved RightC value to SET c bit
      cpu.setC((byte)RightC);

  } // end of RORX()
//
// start of mem/reg instructions
  void ADDA() {
      System.out.println("ADDA INSTRUCTION EXECUTED");
      //part 5
      //REG = REG (A)
      //System.out.println("cpu.getA(): " + cpu.getA());
      //System.out.println("cpu.getOP(): " + cpu.getOP());
      cpu.setA(cpu.getA() + cpu.getOP());
      // not sure that this is what you mean by REG = REG, but this makes the most sense to me.

      // checking for ZERO (A)
      if(cpu.getA() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (A)
      if((cpu.getA() & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }

      // checking for OVERFLOW (ADDITION A)
      if((cpu.getA() > 0 && cpu.getOP() > 0 && (cpu.getA() + cpu.getOP()) < 0) ||
              (cpu.getA() < 0 && cpu.getOP() < 0 && (cpu.getA() + cpu.getOP()) > 0)){
          cpu.setV((byte)1);
      }
      else{
          cpu.setV((byte)0);
      }

      // checking for CARRY (ADDITION A)
      if((cpu.getA() & 0x00010000) == 0x00010000){
          cpu.setC((byte)1);
      }
      else{
          cpu.setC((byte)0);
      }
  } // end of ADDA()
//
  void ADDX() {
      System.out.println("ADDX INSTRUCTION EXECUTED");
      //part 5
      //REG = REG (A)
      cpu.setX(cpu.getX() + cpu.getOP());

      // checking for ZERO (X)
      if(cpu.getX() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (X)
      if((cpu.getX() & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }

      // checking for OVERFLOW (ADDITION X)
      if((cpu.getX() > 0 && cpu.getOP() > 0 && (cpu.getX() + cpu.getOP()) < 0) ||
              (cpu.getX() < 0 && cpu.getOP() < 0 && (cpu.getX() + cpu.getOP()) > 0)){
          cpu.setV((byte)1);
      }
      else{
          cpu.setV((byte)0);
      }

      // checking for CARRY (ADDITION X)
      if((cpu.getX() & 0x00010000) == 0x00010000){
          cpu.setC((byte)1);
      }
      else{
          cpu.setC((byte)0);
      }
  } // end of ADDX()
//
  void SUBA() {
      System.out.println("SUBA INSTRUCTION EXECUTED");
      //part 5
      cpu.setA(cpu.getA() - cpu.getOP()); //REG = REG

      // checking for ZERO (A)
      if(cpu.getA() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (A)
      if((cpu.getA() & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }

      // checking for OVERFLOW (SUBTRACTION A)
      if((cpu.getA() > 0 && (-cpu.getOP()) > 0 && (cpu.getA() - cpu.getOP()) < 0) ||
              (cpu.getA() < 0 && (-cpu.getOP()) < 0 && (cpu.getA() - cpu.getOP()) > 0)){
          cpu.setV((byte)1);
      }
      else{
          cpu.setV((byte)0);
      }

      // checking FOR CARRY (SUBTRACTION A)
      cpu.setC((byte)0);
  } // end of SUBA()
//
  void SUBX() {
      System.out.println("SUBX INSTRUCTION EXECUTED");
      //part 5
      cpu.setX(cpu.getX() - cpu.getOP()); //REG = REG

      // checking for ZERO (X)
      if(cpu.getX() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (X)
      if((cpu.getX() & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }

      // checking for OVERFLOW (SUBTRACTION X)
      if((cpu.getX() > 0 && (-cpu.getOP()) > 0 && (cpu.getX() - cpu.getOP()) < 0) ||
              (cpu.getX() < 0 && (-cpu.getOP()) < 0 && (cpu.getX() - cpu.getOP()) > 0)){
          cpu.setV((byte)1);
      }
      else{
          cpu.setV((byte)0);
      }

      // checking FOR CARRY (SUBTRACTION X)
      cpu.setC((byte)0);


  } // end of SUBX()
//
  void ANDA() {
      System.out.println("ANDA INSTRUCTION EXECUTED");
      //part 5 implement...
      //REG = REG (A)
      cpu.setA(cpu.getA() & cpu.getOP());

      // checking for ZERO (A)
      if(cpu.getA() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (A)
      if((cpu.getA() & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }

  } // end of ANDA()
//
  void ANDX() {
      System.out.println("ANDX INSTRUCTION EXECUTED");
      //part 5 implement...
      //REG = REG (X)
      cpu.setX(cpu.getX() & cpu.getOP());

      // checking for ZERO (X)
      if(cpu.getX() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (X)
      if((cpu.getX() & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }
  } // end of ANDX()
//
  void ORA() {
      System.out.println("ORA INSTRUCTION EXECUTED");
      //part 5 implement...
      //REG = REG (A)
      cpu.setA(cpu.getA() | cpu.getOP());

      // checking for ZERO (A)
      if(cpu.getA() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (A)
      if((cpu.getA() & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }
  } // end of ORA()
//
  void ORX() {
      System.out.println("ORX INSTRUCTION EXECUTED");
      //part 5 implement...
      //REG = REG (X)
      cpu.setX(cpu.getX() | cpu.getOP());

      // checking for ZERO (X)
      if(cpu.getX() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (X)
      if((cpu.getX() & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }
  } // end of ORX()
//
  void CPA() {
      System.out.println("CPA INSTRUCTION EXECUTED");
      //part 5
      int temp = cpu.getA() -  cpu.getOP(); // TEMP = REG

      //checking for ZERO (A)
      if(temp == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (A)
      if((temp & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }

      // checking for OVERFLOW (CPA)
      if((temp > 0 && (-cpu.getOP()) > 0 && (temp - cpu.getOP()) < 0) ||
              (temp < 0 && (-cpu.getOP()) < 0 && (temp - cpu.getOP()) > 0)){
          cpu.setV((byte)1);
      }
      else{
          cpu.setV((byte)0);
      }
      // checking FOR CARRY (CPA)
      cpu.setC((byte)0);
  } // end of CPA()
//
  void CPX() {
      System.out.println("CPX INSTRUCTION EXECUTED");
      //part 5
      int temp = cpu.getX() - cpu.getOP(); // TEMP = REG

      // checking for ZERO (X)
      if(temp == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (X)
      if((temp & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }

      // checking for OVERFLOW (CPX)
      if((temp > 0 && (-cpu.getOP()) > 0 && (temp - cpu.getOP()) < 0) ||
              (temp < 0 && (-cpu.getOP()) < 0 && (temp - cpu.getOP()) > 0)){
          cpu.setV((byte)1);
      }
      else{
          cpu.setV((byte)0);
      }

      // checking FOR CARRY (CPX)
      cpu.setC((byte)0);
  } // end of CPX()
//
  void LDA() {
      System.out.println("LDA INSTRUCTION EXECUTED");
      //part 5
      cpu.setA(cpu.getOP()); // REG = OPERAND

      // checking for ZERO (A)
      if(cpu.getA() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (A)
      if((cpu.getA() & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }

  } // end of LDA()
//
  void LDX() {
      System.out.println("LDX INSTRUCTION EXECUTED");
      //part 5
      //System.out.println("OP: 0x" + Integer.toHexString(cpu.getOP()));
      cpu.setX(cpu.getOP()); // REG = OPERAND
      //System.out.println("0x" + Integer.toHexString(cpu.getX()));
      // checking for ZERO (X)
      if(cpu.getX() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (X)
      if((cpu.getX() & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }
  } // end of LDX()
//
  void LDBYTEA() {
      System.out.println("LDBYTEA INSTRUCTION EXECUTED");
      //part 5
      //cpu.setA(((cpu.getA() >> 8) & 0xFF) | (cpu.getOP() & 0xFF)); // REG = OPERAND (high order byte implementation)
      cpu.setA(cpu.getOP() & 0xFF); // REG = OPERAND (low order byte implementation)

      // checking for ZERO (A)
      if(cpu.getA() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (A)
      if((cpu.getA() & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }
  } // end of LDBYTEA()
//
  void LDBYTEX() {
      System.out.println("LDBYTEX INSTRUCTION EXECUTED");
      //part 5
      //cpu.setX((cpu.getOP() >> 8) & 0xFF); // REG = OPERAND (high order byte implementation)
      cpu.setX(cpu.getOP() & 0xFF); // REG = OPERAND (low order byte implementation)
      // checking for ZERO (X)
      if(cpu.getX() == 0){
          cpu.setZ((byte)1);
      }
      else{
          cpu.setZ((byte)0);
      }

      // checking for NEG (X)
      if((cpu.getX() & 0x00008000) == (0x00008000)){
          cpu.setN((byte)1);
      }
      else{
          cpu.setN((byte)0);
      }
  } // end of LDBYTEX()
//
  void STA() {
      System.out.println("STA INSTRUCTION EXECUTED");
      //part 5
      cpu.setOP(cpu.getA()); // OPERAND = REG
  } // end of STA()
//
  void STX() {
      System.out.println("STX INSTRUCTION EXECUTED");
      //part 5
      cpu.setOP(cpu.getX()); // OPERAND = REG
  } // end of STX()
//
  void STBYTEA() {
      System.out.println("STBYTEA INSTRUCTION EXECUTED");
      //part 5
      //cpu.setOP((cpu.getA() >> 8) & 0xFF); // OPERAND = REG (high order byte implementation)
      cpu.setOP(cpu.getA() & 0xFF); // OPERAND = REG (low order byte implementation)
  } // end of STBYTEA()
//
  void STBYTEX() {
      System.out.println("STBYTEX INSTRUCTION EXECUTED");
      //part 5
      //cpu.setOP((cpu.getX() >> 8) & 0xFF); // OPERAND = REG (high order byte implementation)
      cpu.setOP(cpu.getX() & 0xFF); // OPERAND = REG (low order byte implementation)
  } // end of STBYTEX()
//
  void DISPLAY() { // optional step to display CPU values to the printer
  System.out.printf("\nPC: 0x%04x, SP: 0x%04x, IS: 0x%02x, OS: 0x%04x, OP: 0x%04x, A: 0x%04x, X: 0x%04x\n",
                      cpu.getPC(), cpu.getSP(), cpu.getIS(), cpu.getOS(), cpu.getOP(), cpu.getA(), cpu.getX());
  System.out.printf("N: 0x%01x, Z: 0x%01x, V: 0x%01x, C: 0x%01x, DESCR: %s, MODE: %c\n",
                      cpu.getN(), cpu.getZ(), cpu.getV(), cpu.getC(), cpu.getDESCR(), cpu.getMODE());
  } // end of DISPLAY()
//
} // class step