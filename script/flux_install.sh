#!/usr/bin/env bash

# create namespace
sudo kubectl apply -f ./namespaces/flux-system/namespace.yaml --validate=false


# install flux
sudo helm repo add fluxcd https://charts.fluxcd.io
# sudo microk8s.helm init --override spec.selector.matchLabels.'name'='tiller',spec.selector.matchLabels.'app'='helm' --output yaml | sed 's@apiVersion: extensions/v1beta1@apiVersion: apps/v1@' | microk8s.kubectl apply -f -
sudo helm upgrade flux fluxcd/flux --wait \
--install \
--namespace flux-system \
--version=1.3.0 \
--set git.url=git@github.com:Rubru94/tfm-springboot.git \
--set git.branch=master \
--set git.path=namespaces \
--set git.pollInterval=5m \
--set sync.interval=2m \
--set manifestGeneration=false \
--set registry.automationInterval=2m \
--set syncGarbageCollection.enabled=true \
--set syncGarbageCollection.dry=true \
--set memcached.hostnameOverride=flux-memcached.flux-system 

# install flux-helm-operator
sudo helm upgrade helm-operator fluxcd/helm-operator --wait \
--install \
--namespace flux-system \
--version=1.0.1 \
--set createCRD=false \
--set git.ssh.secretName=flux-git-deploy \
--set chartsSyncInterval=2m \
--set helm.versions=v3