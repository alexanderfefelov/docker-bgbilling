#!/usr/bin/env python2

import os
import sys


MYDUMPER = '/usr/bin/mydumper'
BACKUP_HOME = '/ofelia/data/bgbilling-mysql-backup'


def main():
    if not (os.path.isfile(MYDUMPER) and os.access(MYDUMPER, os.X_OK)):
        print('{0} does not exist or can not be executed')
        sys.exit(1)

    if not os.path.exists(BACKUP_HOME):
        os.makedirs(BACKUP_HOME, 0700)


if __name__ == '__main__':
    main()
