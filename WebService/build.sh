#Make sure WEB-INF and WEB-INF/classes exist
if [ ! -d "WEB-INF" ]; then
    echo "Creating WEB-INF..."
    mkdir WEB-INF
fi
if [ ! -d "WEB-INF/classes" ]; then
    mkdir WEB-INF/classes
fi

if [ ! -d "META-INF" ]; then
    echo "Creating META-INF..."
    mkdir META-INF
fi

echo "Compiling com.adarwin.logging.Logbook..."
javac -d lib ~/prg/com/adarwin/logging/Logbook/Logbook.java
echo "Compiling Shared Libraries..."
javac -cp $CLASSPATH:lib -d lib src/*.java
echo "Compiling .java files in Component Tier..."
javac -cp $CLASSPATH:lib -d ejb src/ComponentTier/*.java
if [ $? -eq 0 ]; then
    echo "Compiling .java files in Web Tier..."
    javac -cp $CLASSPATH:lib:ejb -d WEB-INF/classes src/WebTier/*.java
fi
if [ $? -eq 0 ]; then
    echo "Building .war file..."
    jar cf deployment/TaskCommander.war private WEB-INF *.html *.jsp img
    echo "Building ejb .jar file..."
    jar cmf ejb/META-INF/MANIFEST.MF deployment/ejb.jar -C ejb com -C ejb META-INF
    if [ $? -eq 0 ]; then
        echo "Building .ear file..."
        jar cf deployment/TaskCommander.ear lib -C deployment TaskCommander.war -C deployment ejb.jar
        #jar cf deployment/TaskCommander.ear deployment/TaskCommander.war META-INF ejb
    fi
    #if [ $? -eq 0 ]; then
        #open http://localhost:8080/TaskCommander
    #fi
else
    exit 1
fi
