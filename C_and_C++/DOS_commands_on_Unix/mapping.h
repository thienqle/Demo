/**
 * Struct of Dos and Unix command
 */
typedef struct Pair_DOS_Unix {
  char cmd_Dos[80];
  char cmd_Unix[80];
} Pair_DOS_Unix_t;


/**
 * Mapping function from DOS to UNIX command
 * Function will map an DOSCommand to Unix command
 * @param DOSCommand, given DOS command line
 * @param DOSUnix, given buffer to store command line
 */
int mapDOS_Unix(char *DOSCommand[],char *UNIX[], Pair_DOS_Unix_t pair[],int numOfPair);
