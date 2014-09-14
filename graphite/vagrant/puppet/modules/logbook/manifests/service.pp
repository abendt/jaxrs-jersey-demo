class logbook::service {

    cron { 'elasticsearch-curator':
        command => "curator --logformat logstash delete --older-than 30 2>&1 | sed -e 's/ function\"/ \"function\"/'| nc localhost 28778 -q 1",
        user    => root,
        hour    => 2,
        minute  => 0
    }

}