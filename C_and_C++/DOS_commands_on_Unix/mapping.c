#include "parseInput.h"
#include "mapping.h"
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <string.h>


/**
 * Mapping function from DOS to UNIX command
 * Function will map an DOSCommand to Unix command
 * @param DOSCommand, given DOS command line
 * @param Unix, given buffer for Unix command line
 */
int mapDOS_Unix(char *DOSCommand[],char *UNIX[],Pair_DOS_Unix_t pair[],int numOfPair){
  //Initalize
  int i=0,j=0;
  for(i=0;i<(MAXLINE/2 + 1);i++){
    UNIX[i]=NULL;
  }

  if(DOSCommand[0]==NULL ||strcmp(DOSCommand[0],"\n")==0 || strcmp(DOSCommand[0]," ")==0 || strlen(DOSCommand[0])==0){
    return 0;
  }
  int numOfMem = 0;
  //int numOfCmd = (int)sizeof(pair)/sizeof(Pair_DOS_Unix_t);
  int numOfCmd = numOfPair/sizeof(Pair_DOS_Unix_t);
  for(j=0;j<4;j++){
    for(i=0;i<numOfCmd;i++){
      //printf("%s %s %d\n",DOSCommand[j],pair[i].cmd_Dos,strcmp(DOSCommand[j],pair[i].cmd_Dos));
      if(strcmp(DOSCommand[j],pair[i].cmd_Dos)==0){
	char *cmd = pair[i].cmd_Unix;
	int size = strlen(cmd);
	char *UnixCmd = (char *)malloc(size+1);
	memcpy(UnixCmd,cmd,size);
	UnixCmd[size] = '\0';
	UNIX[j]=UnixCmd;
	numOfMem++;
      }
    }
  }
  if(numOfMem==0){
      char *cmd = "invalid";
      int size = strlen(cmd);
      char *UnixCmd = (char *)malloc(size+1);
      memcpy(UnixCmd,cmd,size);
      UnixCmd[size] = '\0';
      UNIX[0]=UnixCmd;
  }
  return numOfMem;
}
