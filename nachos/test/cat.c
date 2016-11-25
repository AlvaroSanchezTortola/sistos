#include "stdio.h"
#include "stdlib.h"
#include "syscall.h"
#define TESTFILE "pruebaCreate.txt"
#define Bsize 64
#define TESTFILE2 "escrito.txt"
int fd;
int fd2;
int cerrar;
int cerrar2;
int escritor;
int lector;
int cnt = 4;

char buf[Bsize];
char buf2[Bsize];

int main(){

  int prueba = 2;
  if (prueba == 1){
    fd = creat(TESTFILE);
    cerrar = close(fd);
    halt();
  }
  else if (prueba == 2){
    fd = open(TESTFILE);
    buf[0] = 'l';
    buf[1] = 'h';
    buf[2] = 'g';
    buf[3] = 'g';
    buf[Bsize] = '\0';
    escritor = write(fd,buf, cnt);
    cerrar = close(fd);
    halt();
  }
  else if (prueba == 3){
    fd = open(TESTFILE);
    lector = read(fd, buf2, cnt);
    cerrar = close(fd);
    fd2 = creat(TESTFILE2);
    escritor = write(fd2, buf2, cnt);
    cerrar2 = close(fd2);
    halt();

  }
  else if (prueba == 4){
    fd = unlink(TESTFILE2);
    halt();
  }
}