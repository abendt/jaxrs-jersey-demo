class logbook::service {

service { "start-carbon":
    ensure => running,
    start => 'sudo su www-data -c "/opt/graphite/bin/carbon-cache.py start"',
    stop => 'sudo su www-data -c "/opt/graphite/bin/carbon-cache.py stop"',
    status => 'sudo su www-data -c "/opt/graphite/bin/carbon-cache.py status"',
    hasrestart => false,
    hasstatus => true,
    }
~>
service { "apache2":
    enable => true,
    ensure => running,
}


}