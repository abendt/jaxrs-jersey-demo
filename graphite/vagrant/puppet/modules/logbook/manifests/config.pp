class logbook::config {

elasticsearch::instance { 'elastic-sample': 
    ensure => 'present',
    status => 'enabled',
}

elasticsearch::plugin{'lmenezes/elasticsearch-kopf':
        module_dir  => 'kopf',
        instances    => 'elastic-sample',
    }

logstash::configfile { 'kibana_config':
  content => template('logbook/kibana.config')
}

file { "/opt/graphite/conf/carbon.conf":
    ensure => file,
    source => "/opt/graphite/conf/carbon.conf.example"
}

file { "/opt/graphite/conf/storage-schemas.conf":
    ensure => file,
    source => "/opt/graphite/conf/storage-schemas.conf.example"
}

file { "/opt/graphite/conf/graphite.wsgi":
    ensure  => 'file',
    mode    => '0755',
    source  => "/opt/graphite/conf/graphite.wsgi.example"
}

file { '/opt/graphite/webapp/graphite/local_settings.py':
    ensure  => 'file',
    source  => '/opt/graphite/webapp/graphite/local_settings.py.example'
}

file { "/etc/apache2/sites-available/graphite-vhost.conf":
    ensure  => file,
    source  => "/vagrant/graphite/graphite-vhost.conf",
    notify  => Service["apache2"]
    }

file { '/home/vagrant/grafana-1.6.1/config.js':
    ensure => file, 
    source => '/vagrant/graphite/config.js',
   }

exec { 'configure-sqlite':
    creates => '/opt/graphite/storage/graphite.db',
    command => "create-graphite-db.sh admin name@nowhere.org secret",
    path    => ["/usr/bin", "/usr/sbin", "/vagrant/graphite"],
}
~>
file { '/opt/graphite/storage':
    ensure => directory,
    recurse => true,
    owner => 'www-data',
    group => 'www-data',
}
~>
exec { 'chown':
    refreshonly => true,
    command     => 'chown -R www-data:www-data /opt/graphite/storage/log/webapp',
    path        => ["/bin"]
}    

file { "/etc/apache2/sites-enabled/001-graphite":
    ensure  => link,
    target  => "/etc/apache2/sites-available/graphite-vhost.conf",
    notify  => Service["apache2"]
}

file { "/etc/apache2/sites-enabled/000-default":
    ensure  => absent,
    notify  => Service["apache2"]
}

}