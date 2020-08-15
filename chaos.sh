#!/bin/bash
# Randomly delete pods in a Kubernetes namespace.
set -ex

: ${DELAY:=20}
: ${NAMESPACE:=tfm-springboot}

while true; do
  sudo kubectl \
    --namespace "${NAMESPACE}" \
    -o 'jsonpath={.items[*].metadata.name}' \
    get pods | \
      tr " " "\n" | \
      shuf | \
      head -n 1 |
      xargs -t --no-run-if-empty \
        sudo kubectl --namespace "${NAMESPACE}" delete pod
  sleep "${DELAY}"
done
