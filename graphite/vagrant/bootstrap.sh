#!/usr/bin/env bash

function checkThatFirstBootIsNotRunning 
{
    echo "a boot script is run on first boot which locks the apt infrastructure"
    echo "need to wait here for completion so we can proceed with our installation"
    echo 
    
    while :
    do
        RESULT=`pgrep -f firstboot`

        if [ "${RESULT:-null}" = null ]; then
            return 0
        else
            echo "waiting 5sec for firstboot process ${RESULT} to finish ..."
        fi
        sleep 5
    done    
}

function updateInstallation 
{
    sed '/templatedir=/d' /etc/puppet/puppet.conf > /etc/puppet/puppet.conf

    echo "install puppet modules"
    puppet module install puppetlabs/vcsrepo
    puppet module install puppetlabs/stdlib
    puppet module install maestrodev-wget
    puppet module install puppetlabs-apt
    puppet module install elasticsearch-elasticsearch
    puppet module install elasticsearch-logstash
    puppet module install stankevich-python
    puppet module install dwerder-graphite

    ln -sf /vagrant/puppet/modules/logbook      /etc/puppet/modules/
    ln -sf /vagrant/puppet/manifests/site.pp    /etc/puppet/manifests/site.pp

    puppet apply /etc/puppet/manifests/site.pp
}

checkThatFirstBootIsNotRunning
updateInstallation