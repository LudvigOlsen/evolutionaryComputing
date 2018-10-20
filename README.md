# evolutionaryComputing
Repository for project in Evolutionary Computing at VU
Members: P. de Marez Oyens, Ludvig Olsen, Wiesje van den Heerik, Flo Wolf

## How to run experimental conditions  
In order to run the experimental conditions one at a time, 
the settings preset must be changed to the desired condition in player56.java at line 108:  
E.g.:  
> islandsAlgorithmSettings = Presets.basicIslandSettingsKatsuuraIncreaseMigrationFrequency(rnd_);

The various presets can be found in charles.settings.Presets from line 131 and down.  

### Compile

>$ javac -cp contest.jar $(find . -name "*.java")
>
>$ jar cmf MainClass.txt submission.jar $(find . -name "*.class")


### Run

>$ java -jar testrun.jar -submission=player56 -evaluation=SphereEvaluation -seed=1
