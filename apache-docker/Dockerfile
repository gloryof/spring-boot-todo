FROM centos:centos7

RUN (cd /lib/systemd/system/sysinit.target.wants/; for i in *; do [ $i == systemd-tmpfiles-setup.service ] || rm -f $i; done);
RUN rm -f /lib/systemd/system/multi-user.target.wants/*;
RUN rm -f /etc/systemd/system/*.wants/*;
RUN rm -f /lib/systemd/system/local-fs.target.wants/*;
RUN rm -f /lib/systemd/system/sockets.target.wants/*udev*;
RUN rm -f /lib/systemd/system/sockets.target.wants/*initctl*;
RUN rm -f /lib/systemd/system/basic.target.wants/*;
RUN rm -f /lib/systemd/system/anaconda.target.wants/*;
RUN yum update -y;
RUN yum install -y httpd;
RUN systemctl enable httpd.service

RUN yum -y reinstall glibc-common
RUN localedef -v -c -i ja_JP -f UTF-8 ja_JP.UTF-8; echo "";

env LANG=ja_JP.UTF-8
RUN rm -f /etc/localtime
RUN ln -fs /usr/share/zoneinfo/Asia/Tokyo /etc/localtime

EXPOSE 80

CMD ["/sbin/init"]