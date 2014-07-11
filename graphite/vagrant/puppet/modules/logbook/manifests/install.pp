class logbook::install {
  
  wget::fetch { 'kibana-download':
    source => 'http://download.elasticsearch.org/kibana/kibana/kibana-latest.tar.gz',
    destination => '/home/vagrant/kibana-latest.tar.gz',
    timeout     => 0,
    verbose     => false,
 }
 ->
 exec {'kibana-untar':
    creates     => "/home/vagrant/kibana-latest",
    cwd         => "/home/vagrant",
    command     => "tar xzf kibana-latest.tar.gz",
    path        => ["/usr/bin", "/bin"],
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
     
package { 'git':
      ensure => installed,
    }
    
package { 'python-cairo-dev':
    ensure => installed,
    }
    
package { 'python-django':
    ensure => installed,
    }
    
package { 'python-django-tagging':
    ensure => installed,
    }   
    
package { 'python-twisted':
    ensure => installed,
    }  
    
package { 'apache2':
    ensure => installed,
    }    
    
package { 'libapache2-mod-wsgi':
    ensure => installed,
    }    
    
package { 'libapache2-mod-python':
    ensure => installed,
    }  
    
package {'expect':
    ensure => installed,
    }        
    
package {'python-pysqlite2':
    ensure => installed,
    }
    
package {'sqlite3':
    ensure => installed,
    }  
    
package { 'fontconfig':
    ensure => installed,
    }             

}