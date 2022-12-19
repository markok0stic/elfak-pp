export JCUP_HOME="/home/mare/Documents/CUP/java_cup_v10k"
export JAVA_HOME="/home/mare/compilers/jdk-19.0.1/"
export PARSER_CLASS_NAME="MPParser"
export CUP_SPEC_NAME="MPParser.cup"

$JAVA_HOME/bin/java -classpath $JCUP_HOME java_cup.Main -parser $PARSER_CLASS_NAME -symbols sym < $CUP_SPEC_NAME