sh build.sh
if [ $? -eq 0 ]; then
    sh deploy.sh
    if [ $? -eq 0 ]; then
        sh run.sh
    fi
fi
