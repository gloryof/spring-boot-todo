### install

```
# yum install java-1.8.0-openjdk

# wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
# rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io.key
```


### firewalld

```
# firewall-cmd --permanent --new-service=jenkins

# firewall-cmd --permanent --service=jenkins --set-short=jenkins
# firewall-cmd --permanent --service=jenkins --set-description=jenkins
# firewall-cmd --permanent --service=jenkins --add-port=8080/tcp

# firewall-cmd --add-service=jenkins --zone=public --permanent

# firewall-cmd --reload
```
