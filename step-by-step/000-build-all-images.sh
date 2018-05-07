#!/bin/bash

cd ../bgbilling
./build.sh
cd --

cd ../bginetaccess
./build.sh
cd --

cd ../bginetaccounting
./build.sh
cd --

cd ../populator
./build.sh
cd --
