#include "stdio.h"
#include "stdlib.h"
#include "syscall.h"
#define TESTFILE "OLGAAAA.txt"
#define Bsize 2
int retval;
int fd;
int i;
int ii;
int iii;
int iiii;
int cnt = 2;
char buf[Bsize];
char buf2[Bsize];
int main(){
  fd = creat(TESTFILE);
  
  buf[0] = 'h';
  buf[1] = 'o';
  buf[Bsize] = '\0';
  ii = write(fd,buf, cnt);
  iii = read(fd, buf2, cnt);

  printf("*******leyosss: %s", buf2);
	
  i = close(fd);
  halt();
}