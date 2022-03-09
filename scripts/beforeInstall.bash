#!/bin/bash

# I want to make sure that the directory is clean and has nothing left over from
# previous deployments. The servers auto scale so the directory may or may not
# exist.
if [ -d /home/ec2-user/env/WhaleDone-Server/ ]; then
    rm -rf /home/ec2-user/env/WhaleDone-Server/
fi
mkdir -vp /home/ec2-user/env/WhaleDone-Server/
