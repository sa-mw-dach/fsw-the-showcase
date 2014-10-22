#!/bin/sh 
DEMO="JBoss Fuse Service Works Installer"
AUTHORS="Kai Wegner (based on the script of Eric and Kenneth)"
PROJECT="git@github.com:kai-wegner/fsw-the-showcase.git"
PRODUCT="JBoss Fuse Service Works Installer"
JBOSS_HOME_FSW=./target/jboss-eap-6.1
SERVER_BIN_FSW=$JBOSS_HOME_FSW/bin
SRC_DIR=./installs
SUPPORT_DIR=./support
PRJ_DIR=./projects
PRJ_DTGOVWF=$JBOSS_HOME_FSW/dtgov-data
FSW=jboss-fsw-installer-6.0.0.GA-redhat-4.jar
DTGOVWF=dtgov-workflows-1.0.1.Final-redhat-8.jar
FSW_VERSION=6.0.0

# wipe screen.
clear 

echo
echo "####################################################################"
echo "##                                                                ##"   
echo "##  Setting up ${DEMO}                 ##"
echo "##                                                                ##"   
echo "##                                                                ##"   
echo "##     ####    ###    ###  #   #       ####   ###   #       #     ##"
echo "##     #      #   #  #     # # #       #     #      #       #     ##"
echo "##     ####   #####   ##     #         ###    ##    #   #   #     ##"
echo "##     #      #   #     #    #         #        #    # # # #      ##"
echo "##     ####   #   #  ###     #         #     ###      #   #       ##"
echo "##                                                                ##"   
echo "##                                                                ##"   
echo "##  brought to you by,                                            ##"   
echo "##      ${AUTHORS}      ##"
echo "##                                                                ##"   
echo "##  ${PROJECT}                ##"
echo "##                                                                ##"   
echo "####################################################################"
echo

command -v mvn -q >/dev/null 2>&1 || { echo >&2 "Maven is required but not installed yet... aborting."; exit 1; }
nl=$'\n'
# make some checks first before proceeding.
read -p "Please enter the path to the FSW-Installer <default: $SRC_DIR/$FSW>:$nl" CONT
if [ "$CONT" != "" ]; then
  SRC_DIR=$CONT;
fi
echo "Searching for the FSW-Installer here: $SRC_DIR/$FSW"
	
if [ -r $SRC_DIR/$FSW ] || [ -L $SRC_DIR/$FSW ]; then
	echo JBoss product sources, $FSW present...
		echo
else
		echo Need to download $FSW package from the Customer Portal 
		echo and place it in the $SRC_DIR directory to proceed...
		echo
		exit
fi

# Move the old JBoss instance, if it exists, to the OLD position.
if [ -x $JBOSS_HOME_FSW ]; then
		read -p "  - Existing FSW-Installation found ($JBOSS_HOME_FSW). Delete it <yes|no>? " CONT
		if [ "$CONT" == "yes" ]; then
		  echo "  - existing JBoss product install detected and removed..."
		  echo
		  rm -rf $JBOSS_HOME_FSW
		fi
fi

if [ ! -d "target" ]; then
	mkdir target
fi


echo "  - modify FSW installer script with full path."
echo
if [ "$(uname)" == "Darwin" ]; then
  echo "  - environment is a Mac OS (old sed)"
  sed -i "" "s:<installpath>.*</installpath>:<installpath>$(pwd)/target</installpath>:" $SUPPORT_DIR/installation-fsw
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
  echo "  - environment is Linux (new sed)"
  sed -i "s:<installpath>.*</installpath>:<installpath>$(pwd)/target</installpath>:" $SUPPORT_DIR/installation-fsw
fi

# Run FSW installer.
echo "  - installing FSW ... (this may take some time / you can watch './target/FSW_Install_Log')"
java -jar $SRC_DIR/$FSW $SUPPORT_DIR/installation-fsw -variablefile $SUPPORT_DIR/installation-fsw.variables &> ./target/FSW_Install_Log
mv ./target/jboss-eap-6.1 $JBOSS_HOME_FSW &> /dev/null

echo "  - copy in property for monitoring dtgov queries..."
echo 
cp $SUPPORT_DIR/dtgov.properties $JBOSS_HOME_FSW/standalone/configuration

# cp pom to dtgovwf, mvn package, cli upload + type
echo "  - copy modified pom to dtgov workflow project and build..."
echo
cp $SUPPORT_DIR/dtgovwf-pom.xml $PRJ_DTGOVWF/pom.xml
mvn -f $PRJ_DTGOVWF/pom.xml package &> /dev/null
cp $PRJ_DTGOVWF/target/$DTGOVWF $SUPPORT_DIR

echo "  - copy overlord-rtgov.properties for monitoring rtgov ..."
cp $SUPPORT_DIR/overlord-rtgov.properties $JBOSS_HOME_FSW/standalone/configuration

if [ -x "../dist" ]; then
  read -p "  - found a distribution directory (../dist). Should I copy the content to $JBOSS_HOME_FSW/standalone/deployments <yes|no>? " CONT
  if [ "$CONT" == "yes" ]; then
    echo "  - copy content of ../dist/* to JBOSS_HOME_FSW/standalone/deployments"
    echo
    cp -r ../dist/* $JBOSS_HOME_FSW/standalone/deployments
  fi
fi

read -p "  - Should I create a start script in here (`pwd`) <yes|no>? " CONT
if [ "$CONT" == "yes" ]; then
  echo "  - creating a start-script 'startFSW.sh' in `pwd`"
  echo
  echo "#!/bin/bash" > startFSW.sh
  echo "$JBOSS_HOME_FSW/bin/standalone.sh" >> startFSW.sh
  chmod +x startFSW.sh
fi

read -p "  - Should I start Fuse Service Works now? <yes|no>? " CONT
if [ "$CONT" == "yes" ]; then
  echo "  - starting FSW / logging output to ./target/FSW_Start.log"
  echo
  $JBOSS_HOME_FSW/bin/standalone.sh > ./target/FSW_Start.log 2>&1 &
fi


# Final instructions to user to start and run demo.
echo
echo "==========================================================================================="
echo "=                                                                                         =" 
echo "=  Start JBoss FSW server:                                                                ="
echo "=                                                                                         =" 
echo "=    $ $SERVER_BIN_FSW/standalone.sh                                           ="
echo "=                                                                                         =" 
echo "=                                                                                         =" 
echo "=  After starting server you need to upload the DTGOV workflows with following command:   ="
echo "=                                                                                         =" 
echo "=    $ $SERVER_BIN_FSW/s-ramp.sh -f support/sramp-dtgovwf-upload.txt           ="
echo "=                                                                                         =" 
echo "=    Login here: http://localhost:8080/s-ramp-ui    as       u:admin/p:jbossfsw1!         ="
echo "=                                                                                         =" 
echo "=                                                                                         =" 
echo "=   $DEMO Setup Complete.                                    ="
echo "=                                                                                         ="
echo "==========================================================================================="
echo

