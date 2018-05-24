#!/bin/bash

PWD=`pwd`

cd ../mysql
./build.sh
cd $PWD

cd ../bgbilling
./build.sh
cd $PWD

cd ../bgscheduler
./build.sh
cd $PWD

cd ../bginetaccess
./build.sh
cd $PWD

cd ../bginetaccounting
./build.sh
cd $PWD

cd ../populator
./build.sh
cd $PWD
