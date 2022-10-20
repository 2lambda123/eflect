#!/usr/bin/env bash

OUTPUT_DIR=$2
ITERS=$3

SCRATCH_DIR=scratch
DEPS_DIR="resources/jar"

EFLECT_JAR="eflect-experiments.jar:../eflect.jar"
SUNFLOW_JAR="${DEPS_DIR}/sunflow.jar:${DEPS_DIR}/stokelib.jar:${DEPS_DIR}/guava-20.0.jar"

pids=""
iter=0
for i in $1; do
  let iter++
  #java -Deflect.output=$OUTPUT_DIR/$iter -cp $EFLECT_JAR:$SUNFLOW_JAR eflect.experiments.EflectSunflow $i $ITERS &
  gradle run --args "$OUTPUT_DIR/$iter $i $ITERS" &
  pids+=$!" "
done

for pid in $pids; do
  tail --pid=$pid -f /dev/null
done

rm stoke.log
