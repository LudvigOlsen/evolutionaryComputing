#!/bin/bash
touch ./BentResults.txt
echo "Run # | Score" > BentResults.txt
echo "Run # | Score" 
echo "---------------------------------"
echo "---------------------------------" >> BentResults.txt
for i in {1..10}
do
    result=$(java -jar testrun.jar -submission=player56 -evaluation=BentCigarFunction -seed=$i)
    resultArray=($result)
    #echo $result
    
    if [ $i -lt 10 ]
    then 
        echo "$i     | ${resultArray[1]}" >> BentResults.txt
        echo "$i     | ${resultArray[1]}"
    elif [ $i -lt 100 ]
    then 
        echo "$i    | ${resultArray[1]}" >> BentResults.txt
        echo "$i    | ${resultArray[1]}"
    else
        echo "$i   | ${resultArray[1]}" >> BentResults.txt
        echo "$i   | ${resultArray[1]}"
    fi

    
    #echo "$result" >> BentResults.txt
done