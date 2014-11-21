#!/bin/sh
DEMO="JBoss Fuse Service Works Installer"
AUTHORS="Kai Wegner (based on the script of Eric and Kenneth)"
PROJECT="git@github.com:kai-wegner/fsw-the-showcase.git"
PRODUCT="JBoss Fuse Service Works Installer"

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

if [ ! -x $SUPPORT_DIR]; then
  echo "Please execute this script only via './init.sh' in the 'fsw-installer'-directory!"
  echo "I checked if there is a './support'-Directory present and found nothing."
  exit
fi

# importing variables
. ./support/1_installation.variables &> /dev/null

JBOSS_NAME=jboss-eap-6.1
JBOSS_HOME_FSW=$TARGET_DIR/$JBOSS_NAME
SERVER_BIN_FSW=$JBOSS_HOME_FSW/bin
SERVER_BIN_FSW_RELATIVE=./$JBOSS_NAME/bin
SRC_DIR=./installs
SUPPORT_DIR=./support
PRJ_DIR=./projects
PRJ_DTGOVWF=../dtgov-data
RUNTIME_DIR=$TARGET_DIR/runtime

if [ "$(ls -A $TARGET_DIR)" ]; then
  read -p "Existing FSW-Installation found ($TARGET_DIR). Delete it <yes|no>? " CONT
  if [ "$CONT" == "yes" ]; then
    echo "  - existing JBoss product install detected and removed..."
    echo
    rm -rf $TARGET_DIR
  fi
fi

if [ ! -d $TARGET_DIR ]; then
  mkdir $TARGET_DIR
fi

mkdir $RUNTIME_DIR
mkdir $TARGET_DIR/support
cp $SUPPORT_DIR/installation-fsw.variables $RUNTIME_DIR
cp $SUPPORT_DIR/installation-fsw $RUNTIME_DIR
cp $PRJ_DTGOVWF/support/initDTGov.sh $TARGET_DIR
chmod +x $TARGET_DIR/initDTGov.sh
cp $PRJ_DTGOVWF/support/sramp-dtgovwf-upload.txt $TARGET_DIR/support
cp $PRJ_DTGOVWF/dist/$DTGOVWF $TARGET_DIR/support

#command -v mvn -q >/dev/null 2>&1 || { echo >&2 "Maven is required but not installed yet... aborting."; exit 1; }
nl=$'\n'
# make some checks first before proceeding.
echo "Searching for the FSW-Installer here: $SRC_DIR/$FSW"
if [ ! -f $SRC_DIR/$FSW ]; then
  echo "Found no FSW-Installer in the default directory ..."
  read -p "Please enter the path to the FSW-Installer <default: $SRC_DIR/$FSW>:$nl" CONT
  if [ "$CONT" != "" ]; then
    SRC_DIR=$CONT;
  fi
  echo "Searching for the FSW-Installer here: $SRC_DIR/$FSW"
fi

if [ -r $SRC_DIR/$FSW ] || [ -L $SRC_DIR/$FSW ]; then
	echo JBoss product sources, $FSW present...
		echo
else
		echo Need to download $FSW package from the Customer Portal
		echo and place it in the $SRC_DIR directory to proceed...
		echo
		exit
fi


echo "  - modify runtime files."
echo
if [ "$(uname)" == "Darwin" ]; then
  echo "  - environment is a Mac OS (old sed)"
  echo
  echo "  - modify FSW installer script with full path."
  sed -i "" "s:<installpath>.*</installpath>:<installpath>$(pwd)/target</installpath>:" $RUNTIME_DIR/installation-fsw
  echo
  echo "  - modify passwords for FSW installer variables with full path."
  sed -i "" "s:@@password@@:$PASSWORD:" $RUNTIME_DIR/installation-fsw.variables
  echo
  echo "  - modify passwords,directories and filenames for DT-Gov installer."
  sed -i "" "s:@@password@@:$PASSWORD:" $TARGET_DIR/support/sramp-dtgovwf-upload.txt
  sed -i "" "s:@@dtgov-workflowjar@@:$DTGOVWF:" $TARGET_DIR/support/sramp-dtgovwf-upload.txt
  sed -i "" "s:@@serverDirectory@@:$SERVER_BIN_FSW_RELATIVE:" $TARGET_DIR/initDTGov.sh
  echo
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
  echo "  - environment is Linux (new sed)"
  echo
  echo "  - modify FSW installer script with full path."
  sed -i "s:<installpath>.*</installpath>:<installpath>$(pwd)/target</installpath>:" $RUNTIME_DIR/installation-fsw
  echo
  echo "  - modify passwords for FSW installer variables."
  sed -i "s:@@password@@:$PASSWORD:" $RUNTIME_DIR/installation-fsw.variables
  echo
  echo "  - modify passwords, directories and filenames for DT-Gov installer."
  sed -i "s:@@password@@:$PASSWORD:" $TARGET_DIR/support/sramp-dtgovwf-upload.txt
  sed -i "s:@@dtgov-workflowjar@@:$DTGOVWF:" $TARGET_DIR/support/sramp-dtgovwf-upload.txt
  sed -i "s:@@serverDirectory@@:$SERVER_BIN_FSW_RELATIVE:" $TARGET_DIR/initDTGov.sh
  echo
fi

# Run FSW installer.
echo "  - installing FSW ... (this may take some time / you can watch '$TARGET_DIR/FSW_Install_Log')"
java -jar $SRC_DIR/$FSW $RUNTIME_DIR/installation-fsw -variablefile $RUNTIME_DIR/installation-fsw.variables &> "$TARGET_DIR/FSW_Install_Log"
mv $TARGET_DIR/jboss-eap-6.1 $JBOSS_HOME_FSW &> /dev/null

echo "  - copy in property for monitoring dtgov queries..."
echo
cp $SUPPORT_DIR/dtgov.properties $JBOSS_HOME_FSW/standalone/configuration

# cp pom to dtgovwf, mvn package, cli upload + type
#echo "  - copy modified pom to dtgov workflow project and build..."
#echo
#cp $SUPPORT_DIR/dtgovwf-pom.xml $PRJ_DTGOVWF/pom.xml
#mvn -f $PRJ_DTGOVWF/pom.xml package &> /dev/null
#cp $PRJ_DTGOVWF/target/$DTGOVWF $SUPPORT_DIR

echo "  - copy overlord-rtgov.properties for monitoring rtgov ..."
cp $SUPPORT_DIR/overlord-rtgov.properties $JBOSS_HOME_FSW/standalone/configuration

echo "  - setting password for console-admin (username: admin) ..."
$JBOSS_HOME_FSW/bin/add-user.sh -s -dc jboss.standalone.config.dir -u admin -p $PASSWORD

if [ -x "../dist" ]; then
  read -p "  - found a distribution directory (../dist). Should I copy the content to $JBOSS_HOME_FSW/standalone/deployments <yes|no>? " CONT
  if [ "$CONT" == "yes" ]; then
    echo "  - copy content of ../dist/* to $JBOSS_HOME_FSW/standalone/deployments"
    echo
    cp -r ../dist/* "$JBOSS_HOME_FSW/standalone/deployments"
  fi
fi

read -p "  - Should I create a start script in here ($TARGET_DIR) <yes|no>? " CONT
if [ "$CONT" == "yes" ]; then
  echo "  - creating a start-script 'startFSW.sh' in $TARGET_DIR"
  echo
  echo "#!/bin/bash" > $TARGET_DIR/startFSW.sh
  echo "jboss-eap-6.1/bin/standalone.sh" >> $TARGET_DIR/startFSW.sh
  chmod +x $TARGET_DIR/startFSW.sh
fi

read -p "  - Should I start Fuse Service Works now? <yes|no>? " CONT
if [ "$CONT" == "yes" ]; then
  echo "  - starting FSW / logging output to $TARGET_DIR/FSW_Start.log"
  echo
  $JBOSS_HOME_FSW/bin/standalone.sh > $TARGET_DIR/FSW_Start.log 2>&1 &
fi

echo "  - deleting temporary runtime files"
rm -r $TARGET_DIR/runtime

# Final instructions to user to start and run demo.
echo
echo "==========================================================================================="
echo "=                                                                                         ="
echo "=  Start JBoss FSW server:                                                                ="
echo "=                                                                                         ="
echo "=    $ $SERVER_BIN_FSW/standalone.sh"
echo "=  or                                                                                     ="
echo "=    $ $TARGET_DIR/startFSW.sh (if created)"
echo "=                                                                                         ="
echo "=  After starting server you need to upload the DTGOV workflows with following command:   ="
echo "=                                                                                         ="
echo "=    $ $TARGET_DIR/initDTGov.sh"
echo "=                                                                                         ="
echo "=    Login here: http://localhost:8080/s-ramp-ui    as       u:admin/p:$PASSWORD"
echo "=                                                                                         ="
echo "=                                                                                         ="
echo "=   $DEMO Setup Complete.                                    ="
echo "=                                                                                         ="
echo "==========================================================================================="
echo
