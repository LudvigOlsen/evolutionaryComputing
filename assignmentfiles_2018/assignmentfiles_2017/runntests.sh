#!/bin/bash
evaluationType=$1
numberOfTests=$2
fileName=${evaluationType}Results 
touch Results/$fileName.txt
echo "Run # | Score" > Results/$fileName.txt
echo "Run # | Score" 
echo "---------------------------------"
echo "---------------------------------" >> Results/$fileName.txt
for ((i=1; i<=$numberOfTests; i++))
do
    result=$(java -jar testrun.jar -submission=player56 -evaluation=$evaluationType -seed=$i)
    resultArray=($result)
    #echo $result
    
    if [ $i -lt 10 ]
    then 
        echo "$i     | ${resultArray[1]}" >> Results/$fileName.txt
        echo "$i     | ${resultArray[1]}"
    elif [ $i -lt 100 ]
    then 
        echo "$i    | ${resultArray[1]}" >> Results/$fileName.txt
        echo "$i    | ${resultArray[1]}"
    else
        echo "$i   | ${resultArray[1]}" >> Results/$fileName.txt
        echo "$i   | ${resultArray[1]}"
    fi

    
    #echo "$result" >> BentResults.txt
done