class logbook::config {

    elasticsearch::instance { 'logbook':
        ensure => 'present',
        status => 'enabled',
    }

    elasticsearch::plugin{'lmenezes/elasticsearch-kopf':
        module_dir  => 'kopf',
        instances    => 'logbook',
    }

    logstash::configfile { 'kibana_config':
        content => template('logbook/kibana.config')
    }

    file { "/etc/apache2/sites-available/logbook-vhost.config":
        ensure   => file,
        content  => template('logbook/apache.config'),
        notify   => Service["apache2"]
    }

    file { '/opt/grafana/config.js':
        ensure  => file, 
        content => template('logbook/grafana.config')
    }
    
    file { "/etc/apache2/sites-enabled/001-logbook":
        ensure  => link,
        target  => "/etc/apache2/sites-available/logbook-vhost.config",
        notify  => Service["apache2"]
    }

    file { "/etc/apache2/sites-enabled/000-default":
        ensure  => absent,
        notify  => Service["apache2"]
    }
    
    File <| title == "/etc/apache2/sites-enabled/graphite.conf" |> {
      ensure => absent
    }
    
}