### install

```
# mkdir -p /var/lib/nexus/data
# chown nexus:nexus /var/lib/nexus/data

# install java-1.8.0-openjdk
# curl -LO https://sonatype-download.global.ssl.fastly.net/nexus/3/latest-unix.tar.gz
# mv nexus-${version}/ /opt/nexus/


# cd /opt/nexus/
# unlink current
# ln -s nexus-${version}/ current
# mkdir sonatype-work
# cd sonatype-work/
# ln -s  /var/lib/nexus/data nexus3

# cd /etc/systemd/system/
-- nexus.serviceを配置
# systemctl enable nexus.service
# systemctl start nexus.service
```

### firewalld

```
# firewall-cmd --permanent --new-service=nexus

# firewall-cmd --permanent --service=nexus --set-short=nexus
# firewall-cmd --permanent --service=nexus --set-description=nexus
# firewall-cmd --permanent --service=nexus --add-port=8081/tcp

# firewall-cmd --add-service=nexus --zone=public --permanent

# firewall-cmd --reload
```