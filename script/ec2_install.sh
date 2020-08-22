############################
## INSTANCE EC2 - Cluster ##
############################

# Install kubectl

curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl

# Install Docker

sudo apt-get update && \
    sudo apt-get install docker.io -y
	
# Install Minikube

curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 && chmod +x minikube && sudo mv minikube /usr/local/bin/

# Check Minikube Version

minikube version

# Install conntrack

sudo apt-get install -y conntrack

# Running Minikube on EC2 Ubuntu

sudo -i

minikube start --vm-driver=none

minikube status

# HELM installation 

curl https://baltocdn.com/helm/signing.asc | sudo apt-key add -
sudo apt-get install apt-transport-https --yes
echo "deb https://baltocdn.com/helm/stable/debian/ all main" | sudo tee /etc/apt/sources.list.d/helm-stable-debian.list
sudo apt-get update
sudo apt-get install helm -y


# FLUXCTL installation 

sudo snap install fluxctl --classic
sudo apt-get install socat -y


# Edit Security Group of the EC2 Instance to be access

# Type	Custom TCP Rule
# Protocol	TCP
# Port Range	30263 (the port given to you by the kubectl get services command)
# Source	Custom
# 0.0.0.0/0 (Accessible via the internet)

# Click Save.

# Access the our container via the EC2 Instance Port on a web browser.

# The address is <ipv4_public_ip>:<ec2_port>.

# Reference: https://www.radishlogic.com/kubernetes/running-minikube-in-aws-ec2-ubuntu/