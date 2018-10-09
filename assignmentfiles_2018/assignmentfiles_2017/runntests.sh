#!/bin/bash
evaluationType=$1
numberOfTests=$2
fileName=${evaluationType}Results 
touch Results/$fileName.txt
echo "Run,Seed,Generation,Diversity,Migrants,Epoch Size,Best Score" > Results/$fileName.txt
for ((i=1; i<=$numberOfTests; i++))
do
    randomSeed=$(( RANDOM % 10000 ))
    
    while read -r result
    do  
        resultArray=($result)
        echo "${resultArray[*]}"

        if [ "${#resultArray[@]}" -le "2" ]
        then
            break
        fi
        

        generation=${resultArray[1]}
        diversity=${resultArray[4]}
        migrants=${resultArray[8]}
        epochSize=${resultArray[12]}
        bestScore=${resultArray[18]}
        echo ""$i","$randomSeed","$generation","$diversity","$migrants","$epochSize","$bestScore"" >> Results/$fileName.txt
        
    done < <(java -jar testrun.jar -submission=player56 -evaluation=$evaluationType -seed=$randomSeed)
    #echo $result
    #printf "%-5d %4d %7.20f" "$i" "$randomSeed" "${resultArray[1]}" #>> Results/$fileName.txt
    #printf "\n" #>> Results/$fileName.txt

done