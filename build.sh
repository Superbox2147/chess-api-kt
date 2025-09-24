#!/bin/bash

ROOT=$PWD

cd ./build/binary/
mkdir ./out
gcc -fPIC -shared ./src/c/chessapi.c ./src/c/bitboard.c -Isrc/c -o ./out/libchessapi.so
mkdir -p "$ROOT/binary/out/"
cp ./out/* "$ROOT/binary/out/"
