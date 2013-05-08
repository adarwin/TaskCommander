echo "Compiling..."
javac -d . -cp $CLASSPATH:/Applications/NetBeans/glassfish-3.1.2.2/glassfish/lib/:/Users/Andrew/prg/csc435/TaskCommander/WebService/ejb/:/Users/Andrew/prg/csc435/TaskCommander/WebService/lib/:/Users/Andrew/prg/csc435/TaskCommander/WebService/lib/persistence.jar src/*.java
if [ $? -eq 0 ]; then
    echo "Running..."
    java -cp $CLASSPATH:/Applications/NetBeans/glassfish-3.1.2.2/glassfish/lib/:/Users/Andrew/prg/csc435/TaskCommander/WebService/ejb/:/Users/Andrew/prg/csc435/TaskCommander/WebService/lib/:/Users/Andrew/prg/csc435/TaskCommander/WebService/lib/persistence.jar com/adarwin/csc435/test/TestClient
fi
