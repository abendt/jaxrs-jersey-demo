class logbook::graphite {

   
wget::fetch { 'grafana-download':
    source => 'http://grafanarel.s3.amazonaws.com/grafana-1.6.1.tar.gz',
    destination => '/home/vagrant/grafana-1.6.1.tar.gz',
    timeout     => 0,
    verbose     => false,
}  
~>
exec { 'grafana-untar':
    refreshonly => true,
    creates     => "/home/vagrant/grafana-1.6.1",
    cwd         => "/home/vagrant",
    command     => "tar xzf grafana-1.6.1.tar.gz",
    path        => ["/usr/bin", "/bin"],
}
   
/*
 * graphite stable Branch
 */    
vcsrepo { "/home/vagrant/graphite-project/graphite-web":
  ensure => present,
  provider => git,
  source => 'https://github.com/graphite-project/graphite-web.git',
  revision => '0.9.x'
}

vcsrepo { "/home/vagrant/graphite-project/carbon":
  ensure => present,
  provider => git,
  source => 'https://github.com/graphite-project/carbon.git',
  revision => '0.9.x'
}

vcsrepo { "/home/vagrant/graphite-project/whisper":
  ensure => present,
  provider => git,
  source => 'https://github.com/graphite-project/whisper.git',
  revision => '0.9.x'
}

vcsrepo { "/home/vagrant/graphite-project/graphene":
    ensure => present,
    provider => git,
    source => 'https://github.com/jondot/graphene.git',
    revision => 'master'
    }

/*
 * Installation via python
 */
exec { "install-graphite-carbon":
    creates => "/opt/graphite/lib/carbon",
    cwd => "/home/vagrant/graphite-project/carbon",
    command => "python setup.py install",
    require => Vcsrepo['/home/vagrant/graphite-project/carbon'],
    path    => ["/usr/bin", "/usr/sbin"]
}

exec { "install-graphite-whisper":
    creates => "/usr/local/bin/whisper-info.py",
    cwd => "/home/vagrant/graphite-project/whisper",
    command => "python setup.py install",
    require => Vcsrepo['/home/vagrant/graphite-project/whisper'],
    path    => ["/usr/bin", "/usr/sbin"]
}

exec { "install-graphite-webapp":
    creates => "/opt/graphite/webapp",
    cwd => "/home/vagrant/graphite-project/graphite-web",
    command => "python setup.py install",
    require => [
        Vcsrepo['/home/vagrant/graphite-project/graphite-web'],
        Exec['install-graphite-carbon'],
            ],
    path    => ["/usr/bin", "/usr/sbin"]
}


}