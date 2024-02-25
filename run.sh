javac -cp "lib/*" -d resources/target $(find . -name '*.java')
# java -cp .:lib/*:resources/target src/Application < resources/test_cases/inputs/input1.txt
# java -cp .:lib/*:resources/target src/Application < resources/test_cases/inputs/input2.txt
# java -cp .:lib/*:resources/target src/Application < resources/test_cases/inputs/input3.txt
# java -cp .:lib/*:resources/target src/Application < resources/test_cases/inputs/input4.txt
# java -cp .:lib/*:resources/target src/Application < resources/test_cases/inputs/input5.txt
# java -cp .:lib/*:resources/target src/Application < resources/test_cases/inputs/input6.txt
# java -cp .:lib/*:resources/target src/Application < resources/test_cases/inputs/input7.txt
# java -cp .:lib/*:resources/target src/Application < resources/test_cases/inputs/input8.txt
# java -cp .:lib/*:resources/target src/Application < resources/test_cases/inputs/input9.txt

for FILE in resources/test_cases/inputs/*; do 
    #java -cp .:lib/*:target src/Application < $FILE
    java -cp .:lib/*:resources/target src/Application < $FILE;
done

