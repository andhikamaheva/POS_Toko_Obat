#!/bin/sh

rm -fr data
rm -fr log
java -cp ../mckoidb.jar:../gnu-regexp-1.0.8.jar com.mckoi.runtime.McKoiDBMain -conf ./db.conf -create test test
java -cp ../mckoidb.jar:../gnu-regexp-1.0.8.jar com.mckoi.runtime.McKoiDBMain -conf ./db.conf
