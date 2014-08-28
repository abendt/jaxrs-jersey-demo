class logbook::install {

    file {'/opt/download':
      ensure  => directory
    }

    wget::fetch { 'kibana-download':
        source => 'http://download.elasticsearch.org/kibana/kibana/kibana-latest.tar.gz',
        destination => '/opt/download/kibana-latest.tar.gz',
        timeout     => 0,
        verbose     => false,
    }
    ~>
    exec {'kibana-untar':
        refreshonly => true,
        creates     => "/opt/kibana-latest",
        cwd         => "/opt",
        command     => "tar xzf /opt/download/kibana-latest.tar.gz",
        path        => ["/usr/bin", "/bin"],
    }
    ->
    file { "/opt/kibana":
      ensure  => link,
      target  => "/opt/kibana-latest",
    }

    wget::fetch { 'grafana-download':
        source => 'http://grafanarel.s3.amazonaws.com/grafana-1.6.1.tar.gz',
        destination => '/opt/download/grafana-1.6.1.tar.gz',
        timeout     => 0,
        verbose     => false,
    }  
    ~>
    exec { 'grafana-untar':
        refreshonly => true,
        creates     => "/opt/grafana-1.6.1",
        cwd         => "/opt",
        command     => "tar xzf /opt/download/grafana-1.6.1.tar.gz",
        path        => ["/usr/bin", "/bin"],
    }
    ->
    file { "/opt/grafana":
      ensure  => link,
      target  => "/opt/grafana-1.6.1",
    }

    class { 'elasticsearch':
        manage_repo  => true,
        repo_version => '1.2',
        status => 'enabled',
        autoupgrade => true,
        java_install => true
    }
        
    class { 'logstash':
        manage_repo  => true,
        repo_version => '1.4',
        autoupgrade => true,
        status => enabled,
        java_install => true,
        install_contrib => true
    }    

    python::pip { 'elasticsearch-curator':
        pkgname       => 'elasticsearch-curator',
    }               

}