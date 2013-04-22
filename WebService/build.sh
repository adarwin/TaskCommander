#Make sure WEB-INF and WEB-INF/classes exist
echo ""
echo " ---------- Building TaskCommander Web Service ---------- "
echo "|"
if [ ! -d "WEB-INF" ]; then
    echo "| * Creating WEB-INF..."
    mkdir WEB-INF
fi
if [ ! -d "WEB-INF/classes" ]; then
    mkdir WEB-INF/classes
fi

if [ ! -d "META-INF" ]; then
    echo "| * Creating META-INF..."
    mkdir META-INF
fi

success=true

if $success ; then
    echo "| * Compiling com.adarwin.logging.Logbook..."
    javac -d lib ~/prg/com/adarwin/logging/Logbook/Logbook.java
    if [ $? -eq 0 ]; then
        echo "|   Success!"
        echo "|"
    else
        success=false
    fi
fi
if $success ; then
    echo "| * Compiling Shared Libraries..."
    javac -cp $CLASSPATH:lib -d lib src/*.java
    if [ $? -eq 0 ]; then
        echo "|   Success!"
        echo "|"
    else
        success=false
    fi
fi

if $success ; then
    echo "| * Building persistence.jar file..."
    jar cf lib/persistence.jar META-INF/persistence.xml -C lib com/adarwin/csc435/User.class -C lib com/adarwin/csc435/Task.class
    if [ $? -eq 0 ]; then
        echo "|   Success!"
        echo "|"
        echo "| * Removing duplicate User and Task classes..."
        rm lib/com/adarwin/csc435/User.class lib/com/adarwin/csc435/Task.class
        if [ $? -eq 0 ]; then
            echo "|   Success!"
            echo "|"
        fi
    else
        echo "|   Failed to create persistence.jar"
        success=false
    fi
fi
if $success ; then
    echo "| * Compiling .java files in Component Tier..."
    javac -cp $CLASSPATH:lib:lib/persistence.jar -d ejb src/ComponentTier/*.java
    if [ $? -eq 0 ]; then
        echo "|   Success!"
        echo "|"
    else
        success=false
    fi
fi
if $success ; then
    echo "| * Compiling .java files in Web Tier..."
    javac -cp $CLASSPATH:lib:lib/persistence.jar:ejb -d WEB-INF/classes src/WebTier/*.java
    if [ $? -eq 0 ]; then
        echo "|   Success!"
        echo "|"
    else
        success=false
    fi
fi
if $success ; then
    echo "| * Building .war file..."
    jar cf deployment/TaskCommander.war private WEB-INF *.html *.jsp img
    if [ $? -eq 0 ]; then
        echo "|   Success!"
        echo "|"
    else
        success=false
    fi
fi
if $success ; then
    echo "| * Building ejb .jar file..."
    jar cmf ejb/META-INF/MANIFEST.MF deployment/ejb.jar -C ejb com -C ejb META-INF
    if [ $? -eq 0 ]; then
        echo "|   Success!"
        echo "|"
    else
        success=false
    fi
fi
if $success ; then
    echo "| * Building .ear file..."
    jar cf deployment/TaskCommander.ear lib -C deployment TaskCommander.war -C deployment ejb.jar
    if [ $? -eq 0 ]; then
        echo "|   Success!"
        echo "|"
    else
        success=false
    fi
fi
echo " -------------------------------------------------------- "
