#!/bin/bash
mvn clean install
mvn package appassembler:assemble
cd target/crypto/bin
mkdir components
cp ../../../../elgamal-component/target/*.jar  components/
cp ../../../../acwa-component/target/*.jar  components/
chmod +x assess
sh crypto
cd ../../
