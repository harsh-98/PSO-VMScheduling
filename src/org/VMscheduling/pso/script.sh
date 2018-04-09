export CLASSPATH="$(pwd)/../../.."
javac *.java
java org.VMscheduling.pso.Execution
python test.py
