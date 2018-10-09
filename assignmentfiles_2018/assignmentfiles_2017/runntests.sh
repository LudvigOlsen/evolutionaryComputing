#!/bin/bash
evaluationType=$1
numberOfTests=$2
fileName=${evaluationType}Results 
touch Results/$fileName.txt
echo "Run # | Seed | Score" > Results/$fileName.txt
printf "\n%-5s %4s %7s\n" "Run #" "Seed" "Score"
printf "=======================================\n"
echo "---------------------------------" >> Results/$fileName.txt
for ((i=1; i<=$numberOfTests; i++))
do
    randomSeed=$(( RANDOM % 10000 ))
    result=$(java -jar testrun.jar -submission=player56 -evaluation=$evaluationType -seed=$randomSeed)
    resultArray=($result)
    #echo $result
    printf "%-5d %4d %7.20f" "$i" "$randomSeed" "${resultArray[1]}" #>> Results/$fileName.txt
    printf "\n" #>> Results/$fileName.txt
    #printf "%-10s%-4s|\n"

    #echo "$i     | $randomSeed    |${resultArray[1]}" >> Results/$fileName.txt


done