class logbook::service {

    cron { 'elasticsearch-curator':
        command => "/usr/local/bin/curator delete --older-than 60 2&>1 | /bin/nc localhost 28778",
        user    => root,
        hour    => 2,
        minute  => 0
    }

}