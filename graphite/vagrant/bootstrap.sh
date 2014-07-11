#!/usr/bin/env bash

apt-get update
sed '/templatedir=/d' /etc/puppet/puppet.conf > /etc/puppet/puppet.conf

echo "install puppet modules"
puppet module install puppetlabs/vcsrepo
puppet module install puppetlabs/stdlib
puppet module install maestrodev-wget
puppet module install puppetlabs-apt
puppet module install elasticsearch-elasticsearch
puppet module install elasticsearch-logstash

ln -sf /vagrant/puppet/modules/logbook /etc/puppet/modules/
ln -sf /vagrant/puppet/manifests/site.pp /etc/puppet/manifests/site.pp

puppet apply /etc/puppet/manifests/site.pp
