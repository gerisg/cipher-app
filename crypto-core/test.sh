#!/bin/bash
mvn clean install
mvn package appassembler:assemble
cd target/crypto/bin
chmod +x assess
sh crypto
cd ../../
