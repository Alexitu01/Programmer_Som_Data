
void main() {
        int arr[2];
        arr[0] = 20;
        arr[1] = 21;
        int i;
        i = 0;
        ++arr[++i];
    }

//RunAndComp:
/*
  [LDARGS; CALL (0, "L1"); STOP; Label "L1"; INCSP 2; GETSP; CSTI 1; SUB;
   GETBP; CSTI 2; ADD; LDI; CSTI 0; ADD; CSTI 20; STI; INCSP -1; GETBP; CSTI 2;
   ADD; LDI; CSTI 1; ADD; CSTI 21; STI; INCSP -1; INCSP 1; GETBP; CSTI 3; ADD;
   CSTI 0; STI; INCSP -1; GETBP; CSTI 2; ADD; LDI; GETBP; CSTI 3; ADD; DUP;
   LDI; CSTI 1; ADD; STI; ADD; DUP; LDI; CSTI 1; ADD; STI; INCSP -1; INCSP -4;
   RET -1]
*/
