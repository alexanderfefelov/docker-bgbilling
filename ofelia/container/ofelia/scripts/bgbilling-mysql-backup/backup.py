#!/usr/bin/env python2

import _base36 as base36
from datetime import datetime
import os
import random
import shutil
import sys
import tarfile


MYDUMPER = '/usr/bin/mydumper'
BACKUP_HOME = '/ofelia/data/bgbilling-mysql-backup'
HOST = 'backup.mysql.bgbilling.local'
PORT = 3306
DATABASE = 'bgbilling'
USERNAME = 'root'
PASSWORD = 'password'
NOW_STR_FORMAT = '%Y%m%d_%H%M%S'
OUTPUT_DIR_NAME_FORMAT = '{backup_home}/{host}_{port}_{database}_{now_str}_{random_str}'
MYDUMPER_LOG_FILE_NAME_FORMAT = '{output_dir_name}/{host}_{port}_{database}.log'
MYDUMPER_CMD_FORMAT = '{mydumper} --host {host} --port {port} --database {database} --user {username} --password {password} --outputdir {output_dir_name} --logfile {log_file_name} --verbose {verbose} --less-locking --use-savepoints'
TGZ_FILE_NAME_FORMAT = '{0}.tar.gz'


def main():
    if not (os.path.isfile(MYDUMPER) and os.access(MYDUMPER, os.X_OK)):
        print('{0} does not exist or can not be executed'.format(MYDUMPER))
        sys.exit(1)

    if not os.path.exists(BACKUP_HOME):
        os.makedirs(BACKUP_HOME, 0700)

    now_str = datetime.now().strftime(NOW_STR_FORMAT)
    random_str = base36.encode(random.randint(0, 42013))

    output_dir_name = OUTPUT_DIR_NAME_FORMAT.format(
        backup_home=BACKUP_HOME,
        host=HOST,
        port=PORT,
        database=DATABASE,
        now_str=now_str,
        random_str=random_str
    )

    mydumper_log_file_name = MYDUMPER_LOG_FILE_NAME_FORMAT.format(
        output_dir_name=output_dir_name,
        host=HOST,
        port=PORT,
        database=DATABASE
    )

    cmd = MYDUMPER_CMD_FORMAT.format(
        mydumper=MYDUMPER,
        host=HOST,
        port=PORT,
        database=DATABASE,
        username=USERNAME,
        password=PASSWORD,
        output_dir_name=output_dir_name,
        log_file_name=mydumper_log_file_name,
        verbose=3
    )

    os.makedirs(output_dir_name)
    print('Executing {0}...'.format(cmd))
    ret_code = os.system(cmd)
    print('...done, return code: {0}'.format(ret_code))

    tgz_file_name = TGZ_FILE_NAME_FORMAT.format(
        output_dir_name
    )

    backup_file_names = os.listdir(output_dir_name)
    with tarfile.open(tgz_file_name, 'w:gz') as tgz_file:
        for backup_file_name in backup_file_names:
            tgz_file.add(os.path.join(output_dir_name, backup_file_name))

    shutil.rmtree(output_dir_name)


if __name__ == '__main__':
    main()
