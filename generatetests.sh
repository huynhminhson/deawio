#!/usr/bin/env bash

for dir in $(find /code/projects/deawio/src/main/java -type d)
do
  mkdir -p ${dir/src\/main/src\/test}
done

for file in $(find /code/projects/deawio/src/main/java -type f)
do
  file=${file/src\/main/src\/test}
  file=${file/.java/Test.java}
  touch $file
done
