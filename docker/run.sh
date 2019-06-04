#!/bin/bash

function usage() {
    echo "Usage:"
    echo "  run.sh [CONFIG]"
    echo "example:"
    echo "  run.sh NODE -e OTTER_MANAGER=127.0.0.1:11311"
    exit
}

function check_port() {
    local port=$1
    local TL=$(which telnet)
    if [ -f $TL ]; then
        data=`echo quit | telnet 127.0.0.1 $port| grep -ic connected`
        echo $data
        return
    fi

    local NC=$(which nc)
    if [ -f $NC ]; then
        data=`nc -z -w 1 127.0.0.1 $port | grep -ic succeeded`
        echo $data
        return
    fi
    echo "0"
    return
}

function getMyIp() {
    case "`uname`" in
        Darwin)
         myip=`echo "show State:/Network/Global/IPv4" | scutil | grep PrimaryInterface | awk '{print $3}' | xargs ifconfig | grep inet | grep -v inet6 | awk '{print $2}'`
         ;;
        *)
         myip=`ip route get 1 | awk '{print $NF;exit}'`
         ;;
  esac
  echo $myip
}

NET_MODE=""
case "`uname`" in
    Darwin)
        bin_abs_path=`cd $(dirname $0); pwd`
        ;;
    Linux)
        bin_abs_path=$(readlink -f $(dirname $0))
        NET_MODE="--net=host"
        ;;
    *)
        NET_MODE="--net=host"
        bin_abs_path=`cd $(dirname $0); pwd`
        ;;
esac
BASE=${bin_abs_path}
if [ "$1" == "-h" ] ; then
    usage
elif [ "$1" == "help" ] ; then
    usage
fi

DATA="$BASE/data"
mkdir -p $DATA
RUN_MODE=$1
CONFIG=${@:2}
VOLUMNS="-v $DATA/mysql:/var/lib/mysql -v $DATA/zkData:/home/admin/zkData"
PORTLIST="11311 11312 11314 11315 11316 11319"

PORTS=""
for PORT in $PORTLIST ; do
    #exist=`check_port $PORT`
    exist="0"
    if [ "$exist" == "0" ]; then
        PORTS="$PORTS -p $PORT:$PORT"
    else
        echo "port $PORT is used , pls check"
        exit 1
    fi
done
MEMORY="-m 4096m"
LOCALHOST=`getMyIp`
cmd="docker run -d -h $LOCALHOST $CONFIG -e port=11311 -e communication_manager_port=11312 -e communication_node_port=11315  --name=otter-all $VOLUMNS --net=bridge -p 11311:11311 -p 11312:11312 -p 11314:11314 -p 11315:11315 -p 11316:11316 -p 11319:11319 $MEMORY canal/otter-all:latest"
eval $cmd
