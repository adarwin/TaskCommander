goodToGo=true
sh build.sh
if [ $? -eq 1 ]; then
    goodToGo=false
fi
if $goodToGo ; then
    sh deploy.sh
    if [ $? -eq 1 ]; then
        goodToGo=false
    fi
fi
if $goodToGo ; then
    sh run.sh
fi
