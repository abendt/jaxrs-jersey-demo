class logbook::install {
  
    class { 'elasticsearch':
        manage_repo  => true,
        repo_version => '1.2',
        status => 'enabled',
        autoupgrade => true,
        java_install => true
    }
        
    /*
    exec { 'Install ElasticHQ Plugin':
    command => '/usr/share/elasticsearch/bin/plugin -install royrusso/elasticsearch-HQ',
    cwd => "/usr/share/elasticsearch/bin",
    path => ["/bin", "/usr/bin"]
    }        

*/

    class { 'logstash':
        manage_repo  => true,
        repo_version => '1.4',
        autoupgrade => true,
        status => disabled,
        java_install => true
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