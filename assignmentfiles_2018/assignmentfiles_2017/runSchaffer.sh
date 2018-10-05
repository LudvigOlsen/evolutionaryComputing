#!/bin/bash
javac -cp contest.jar $(find . -name "*.java")
jar cmf MainClass.txt submission.jar $(find . -name "*.class")
java -jar testrun.jar -submission=player56 -evaluation=SchaffersEvaluation -seed=$1