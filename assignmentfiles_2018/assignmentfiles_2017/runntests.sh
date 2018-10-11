#!/bin/bash
evaluationType=$1
numberOfTests=$2
fileName=${evaluationType}Results 
touch Results/$fileName.txt
echo "Run,Seed,Generation,InterIsland Diversity,Global Diversity,Migrants,Epoch Size,Best Score So Far,Avg Global Fitness,Island0,Island1,Island2,Island3,Island4" > Results/$fileName.txt
echo "Run,Seed,Final Score" > Results/$fileName"FinalResults".txt
for ((i=1; i<=$numberOfTests; i++))
do
    randomSeed=$(( RANDOM % 10000 ))
    
    while read -r result
    do  
        resultArray=($result)
        echo "${resultArray[*]}"

        if [ "${#resultArray[@]}" -le "2" ]
        then
            finalScore=${resultArray[1]}
            echo ""$i","$randomSeed","$finalScore"" >> Results/$fileName"FinalResults".txt
            echo $finalScore
            break
        fi
        

        generation=${resultArray[1]}
        interDiversity=${resultArray[5]}
        globalDiversity=${resultArray[9]}
        migrants=${resultArray[13]}
        epochSize=${resultArray[17]}
        bestScore=${resultArray[23]}
        avgGlobalScore=${resultArray[28]}
        island0AvgScore=${resultArray[31]}
        island1AvgScore=${resultArray[34]}
        island2AvgScore=${resultArray[37]}
        island3AvgScore=${resultArray[40]}
        island4AvgScore=${resultArray[43]}
        echo ""$i","$randomSeed","$generation","$interDiversity","$globalDiversity","$migrants","$epochSize","$bestScore","$avgGlobalScore","$island0AvgScore","$island1AvgScore","$island2AvgScore","$island3AvgScore","$island4AvgScore"" >> Results/$fileName.txt
        
    done < <(java -jar testrun.jar -submission=player56 -evaluation=$evaluationType -seed=$randomSeed)
    #echo $result
    #printf "%-5d %4d %7.20f" "$i" "$randomSeed" "${resultArray[1]}" #>> Results/$fileName.txt
    #printf "\n" #>> Results/$fileName.txt

done