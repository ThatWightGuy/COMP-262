; PEP8 source code for PEP/8 Simulation 
main:    LDA a,d ;a
         RORA
         ROLA
         ASLA
         ASRA
         NEGA
         NOTA    
onea:    ORA 0xFFFF,i
         BRGE onea 
zeroa:   ANDA 0x0000,i
         BRGT zeroa
         ADDA 1,i
         SUBA b,d ;b
         STBYTEA c,d ;c
         ANDA xx,d; 
do:      LDBYTEA c,d ;c
         LDX xx,d ;xx
         ADDA p,n ;p
         SUBA ar,x ;ar
         STA c,d ;c
         ADDX 1,i
         STX xx,d ;xx
while:   LDA p,x ;p
         CPX 2,i
         BRLT do ;do
         BRLE do
         BREQ do
         BR   done
done:    STOP
;
a:       .word 0xBFFF
b:       .word 3
c:       .word 0
xx:      .word 0
p:       .addrss ar
ar:      .ascii "\x01\x02\x03\x04\x05\x06\x07\x08\x09\x00"
;
         .END
