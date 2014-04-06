#!/bin/bash
mvn clean install
mvn package appassembler:assemble
cd target/crypto
mkdir components
cp ../../../elgamal-component/target/*.jar  components/
cp ../../../acwa-component/target/*.jar  components/
chmod +x bin/crypto
chmod +x assess
bin/crypto
cd ../../
