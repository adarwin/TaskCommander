echo "Undeploying existing .ear file..."
asadmin undeploy TaskCommander
echo "Deploying new .ear file..."
asadmin deploy deployment/TaskCommander.ear
