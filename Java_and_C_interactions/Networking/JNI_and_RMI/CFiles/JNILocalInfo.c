/*
 * Thien Le
 */

#include <jni.h>
#include <stdio.h>
#include "GetLocalInfo.h"

#include <string.h>
#include <time.h>
#include <stdlib.h>
#include <pthread.h>
#include <endian.h>
#include <stdint.h>

/* Reference to http://www.linuxquestions.org */

#ifdef _WIN32
   const char* OSname = "Windows";
#elif _WIN64
   const char* OSname = "Windows";
#elif __unix || __unix__
   const char* OSname = "Unix_OS";
#elif __APPLE__ || __MACH__
   const char* OSname = "Mac_OSX";
#elif __linux__
   const char* OSname = "LinuxOS";
#else
   const char* OSname = "OtherOS";
#endif

/**
* Global variable that stores local time
*/
int local_time;

/**
* Function that converts interger number to char *
*/
void ConvertIntegerToChar(char *output,int input){
  uint32_t temp = htole32(input);
  output[0] = (temp >> 24) & 0xFF;
  output[1] = (temp >> 16) & 0xFF;
  output[2] = (temp >> 8) & 0xFF;
  output[3] = temp & 0xFF;
}

/**
* Function that gets current time of system
* @return integer of system current time
*/
/* Reference to http://stackoverflow.com/ on get system time*/
int getCurrentTime(){
  time_t rtime;
  struct tm * aTm;

  time ( &rtime );
  aTm = localtime ( &rtime );
  /*Convert to int on second unit*/
  int result = aTm->tm_hour*3600 + aTm->tm_min*60 + aTm->tm_sec;
  
  return result;
}

/**
* Function that gets current time of system
* @return integer of system current time
*/
int getLocalTime(){
local_time = getCurrentTime();
return local_time;
}

/**
* Function that gets OS name
* @return OS name as char *
*/
const char* getOSName(){
return OSname;
}

/**
* Mapping getLocalTime to java function
*/
JNIEXPORT jint JNICALL Java_GetLocalInfo_getLocalTime
(JNIEnv *env, jobject obj){
  getLocalTime();
  return local_time;
}

/**
* Mapping getLocalOS to java function
*/
JNIEXPORT jstring JNICALL Java_GetLocalInfo_getLocalOS
(JNIEnv *env, jobject obj){
  return (*env)->NewStringUTF(env,OSname);
}
