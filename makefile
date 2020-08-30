compile: bin
	where /r . *.java > sources.txt
	javac -cp resources;src;. -d bin @sources.txt 

run:
	java -cp bin;resources;. PacMan

run_sim:
	java -cp bin;resources;. Simulation

bin:
	mkdir bin	

jar:
	jar cvfm PacMan.jar Manifest.mf -C bin/ . -C resources/ .

runj:
	java -jar PacMan.jar