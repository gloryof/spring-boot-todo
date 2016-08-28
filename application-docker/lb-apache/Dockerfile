FROM httpd:2.4

RUN rm /usr/local/apache2/conf/extra/httpd-autoindex.conf
RUN rm /usr/local/apache2/conf/extra/httpd-default.conf
RUN rm /usr/local/apache2/conf/extra/httpd-languages.conf
RUN rm /usr/local/apache2/conf/extra/httpd-mpm.conf
RUN rm /usr/local/apache2/conf/extra/httpd-ssl.conf
RUN rm /usr/local/apache2/conf/extra/httpd-vhosts.conf
RUN rm /usr/local/apache2/conf/extra/httpd-dav.conf
RUN rm /usr/local/apache2/conf/extra/httpd-info.conf
RUN rm /usr/local/apache2/conf/extra/httpd-manual.conf
RUN rm /usr/local/apache2/conf/extra/httpd-multilang-errordoc.conf
RUN rm /usr/local/apache2/conf/extra/httpd-userdir.conf
RUN rm /usr/local/apache2/conf/extra/proxy-html.conf

COPY conf/httpd.conf /usr/local/apache2/conf/httpd.conf