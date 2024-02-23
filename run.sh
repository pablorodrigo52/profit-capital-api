javac -cp "lib/*" -d target $(find . -name '*.java')
java -cp .:lib/*:target src/Application < input.txt