#! /usr/bin/bash
num=$1
div=2
one=0
while [ $num -gt 1 ]
do
while [ $(( num % div )) -eq 0 ]
do
one=$(( div + one ))
num=$(( num / div ))
done
div=$(( div + 1 ))
done
echo "$one"
