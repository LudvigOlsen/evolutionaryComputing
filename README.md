# evolutionaryComputing
Repository for project in Evolutionary Computing at VU
Members: P. de Marez Oyens, Ludvig Olsen, Wiesje van den Heerik, Flo Wolf


# Compile
$ javac -cp contest.jar $(find . -name "*.java")
$ jar cmf MainClass.txt submission.jar $(find . -name "*.class")

# Run
$ java -jar testrun.jar -submission=player56 -evaluation=SphereEvaluation -seed=1
