#Make sure WEB-INF and WEB-INF/classes exist
if [ ! -d "WEB-INF" ]; then
  echo "Creating WEB-INF..."
  mkdir WEB-INF
fi
if [ ! -d "WEB-INF/classes" ]; then
  mkdir WEB-INF/classes
fi

echo "Compiling .java files..."
javac -d WEB-INF/classes src/*.java ~/prg/com/adarwin/logging/Logbook/Logbook.java
if [ $? -eq 0 ]; then
  echo "Building .war file..."
  jar cf deployment/TaskCommander.war private WEB-INF *.html *.jsp img
  echo "Undeploying existing .war file..."
  asadmin undeploy TaskCommander
  echo "Deploying new .war file..."
  asadmin deploy deployment/TaskCommander.war
  #if [ $? -eq 0 ]; then
    #open http://localhost:8080/TaskCommander
  #fi
else
  exit 1
fi
