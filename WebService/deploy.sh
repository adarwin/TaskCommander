echo ""
echo "Deploying..."
echo ""
echo "* Undeploying existing .ear file..."
asadmin undeploy --droptables=true TaskCommander
#asadmin undeploy TaskCommander
echo ""
echo "* Deploying new .ear file..."
asadmin deploy --createtables=true deployment/TaskCommander.ear
#asadmin deploy deployment/TaskCommander.ear
echo ""
