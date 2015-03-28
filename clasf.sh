#!/bin/bash
source /$USER/local/share/jubatus/jubatus.profile
jubaclassifier --configpath clasy.json --rpc-port $1&
