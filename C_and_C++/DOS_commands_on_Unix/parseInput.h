#define MAXLINE 80 /* The maximum length command */

/**
 * Function that extract command from given string. And get it commands and theirs arguments
 * @param string input from user,location to store commands and theirs arguments
 * @return number of memorys that are allocated
 */
int parseInput(char *input,char *ouput[]);

/**
 * Function that performs free memory of given args
 * @param args from user
 */
void freeMem(char *args[],int size);
