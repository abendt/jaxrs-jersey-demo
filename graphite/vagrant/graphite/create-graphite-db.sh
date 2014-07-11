#!/bin/bash

login=$1
email=$2
password=$3

cd '/opt/graphite/webapp/graphite'
python manage.py syncdb --noinput
python manage.py createsuperuser --username="${login}" --email="${email}" --noinput

expect << DONE
    spawn python manage.py changepassword "${login}"
    expect "Password: "
    send -- "${password}\r"
    expect "Password (again): "
    send -- "${password}\r"
    expect eof