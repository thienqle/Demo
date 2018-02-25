#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <string.h>
#define MAXLINE 80

/**
 * Function that extract command from given string. And get it commands and theirs arguments
 * @param string input from user,location to store commands and theirs arguments
 * @return number of memorys that are allocated
 */
int processInput(char *input,char *output[]){
  int i;
  int space[MAXLINE],si=0; /*Space index of a string*/
  int ampersand[MAXLINE],ai=0; /*ampersand index of a string*/
  int pipe_index[MAXLINE],pi=0; /*pipe index of a string*/
  int endline;
  int numOfMem = 0;

  //Replace endline with terminate string
  input[strlen(input)-1]='\0';
  for(i=0;i<strlen(input);i++){
    if(input[i]==' '){
      space[si] = i;
      si++;
    } else if(input[i]=='&'){
      ampersand[ai] = i;
      ai++;
    }else if(input[i]=='|'){
      pipe_index[pi] = i;
      pi++;
    } else if(input[i]=='\n'){
      endline = i;
    }
  }

  /*
  * Copy each character to output
  * Malloc each output size of each user input
  */
  int current_index = 0;
  int current_output = 0;
  for(i=0;i<=si;i++){
    int next_index;
    if(i==si){
      next_index = strlen(input); //Special case for last param
    } else {
      next_index = space[i];
    }
    int current_size = (next_index-current_index);
    char *current = (char *)malloc(current_size+1);
    memcpy(current,input+current_index,current_size);
    current[current_size] = '\0';
    output[current_output] = current;
    numOfMem++;
    current_index = next_index+1;
    current_output++;
  }

  return numOfMem;
   
}

/**
 * Function that performs free memory of given args
 * @param args from user
 */
void freeMem(char *args[],int size){
  int i;
  for(i=0;i<size;i++){
      free(args[i]);
  }
}
