#include"mapping.h"
#include"parseInput.h"
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <string.h>


/**
 * Mappling pair between DOS command and Unix
 * Parsing from file in future
 */
Pair_DOS_Unix_t pair[] = {
  {"dir","ls"},
  {"chdir","pwd"},
  {"date","date"},
  {"cls","clear"},
  {"echo","echo"},
  {"/a","-a"},
  {"/l","-l"},
};

int numOfPair = sizeof(pair);

int main(void)
{
  char *argsDOS[MAXLINE/2 + 1]; /* DOS command line arguments */
  char *argsUnix[MAXLINE/2 +1]; /* command line arguments */
  int shouldrun = 1; /* flag to determine when to exit program */

  char shell[200];
  getcwd(shell,sizeof(shell));
  while (shouldrun) {
    if(shell!=NULL){
      printf(":%s>",shell);
    } else {
      printf("C:>");
    }
    fflush(stdout);

    /*Reading input from user*/
    char input[MAXLINE];
    fgets(input,MAXLINE,stdin);
    int numOfMem = processInput(input,argsDOS);
    int numOfMem1 = mapDOS_Unix(argsDOS,argsUnix,pair,numOfPair);
    fflush(stdout);

    /*Create a child using fork()*/
    pid_t child;
    child = fork();
    if(child<0){
      printf("Failed to create process\n");
    }
    if(child==0){

       /*Command*/
      //char *cmd = argsDOS[0];
      char *cmd = argsUnix[0];
      
      if(cmd==NULL ||*cmd == '\n' || *cmd == ' ' || strlen(cmd)==0){
	exit(0);
      }

      /*Array of arguments */
      char *param[3];
      param[0] = argsUnix[0];

      if(argsUnix[1]==NULL || *argsUnix[1] == '\n' || *argsUnix[1] == ' ' || strlen(argsUnix[1])==0){
	param[1] = NULL;
      } else {
	param[1] = argsUnix[1];
      }

      if(argsUnix[2]==NULL || *argsUnix[2] == '\n' || *argsUnix[2] == ' ' || strlen(argsUnix[2])==0){
	param[2] = NULL;
      } else {
	param[2] = argsUnix[2];
      }

      /*int j;
      for(j=0;j<numOfMem1;j++){
	printf("Print args[%d]=%s, length=%lu\n",j,param[j],strlen(param[j]));
      }*/
      int execute;
      execute = execvp(cmd,param);
     
      if(execute<0){
	printf("Invalid command\n");
      } else {
	printf("%d\n",execute);
      }

      freeMem(argsDOS,numOfMem);
      freeMem(argsUnix,numOfMem1);
      exit(0);
    } else {
      wait(NULL);
    }
    if(numOfMem1>0){
      printf("\n");
    }
    freeMem(argsDOS,numOfMem);
    freeMem(argsUnix,numOfMem1);
  }
  return 0;
}
