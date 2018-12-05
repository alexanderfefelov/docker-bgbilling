#!/usr/bin/env python2

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
OUTPUT_DIR_NAME_FORMAT = '{host}_{port}_{database}_{now_str}_{random_str}'
MYDUMPER_LOG_FILE_NAME_FORMAT = '{host}_{port}_{database}.log'
MYDUMPER_CMD_FORMAT = '{mydumper} --host {host} --port {port} --database {database} --user {username} --password {password} --outputdir {output_dir_path} --logfile {log_file_name} --verbose {verbose} --less-locking --use-savepoints'
TGZ_FILE_NAME_FORMAT = '{0}.tar.gz'


def __base36_encode(integer):  # https://en.wikipedia.org/wiki/Base36#Python_implementation
    chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ'

    sign = '-' if integer < 0 else ''
    integer = abs(integer)
    result = ''

    while integer > 0:
        integer, remainder = divmod(integer, 36)
        result = chars[remainder] + result

    return sign + result


def main():
    if not (os.path.isfile(MYDUMPER) and os.access(MYDUMPER, os.X_OK)):
        print('{0} does not exist or can not be executed'.format(MYDUMPER))
        sys.exit(1)

    now_str = datetime.now().strftime(NOW_STR_FORMAT)
    random_str = __base36_encode(random.randint(0, 42013))
    tmp_dir_path = '/ofelia/tmp'

    output_dir_name = OUTPUT_DIR_NAME_FORMAT.format(
        host=HOST,
        port=PORT,
        database=DATABASE,
        now_str=now_str,
        random_str=random_str
    )
    output_dir_path = os.path.join(tmp_dir_path, output_dir_name)

    mydumper_log_file_name = MYDUMPER_LOG_FILE_NAME_FORMAT.format(
        host=HOST,
        port=PORT,
        database=DATABASE
    )
    mydumper_log_file_path = os.path.join(output_dir_path, mydumper_log_file_name)

    cmd = MYDUMPER_CMD_FORMAT.format(
        mydumper=MYDUMPER,
        host=HOST,
        port=PORT,
        database=DATABASE,
        username=USERNAME,
        password=PASSWORD,
        output_dir_path=output_dir_path,
        log_file_name=mydumper_log_file_path,
        verbose=3
    )

    tgz_file_name = TGZ_FILE_NAME_FORMAT.format(
        output_dir_name
    )
    tgz_file_path = os.path.join(BACKUP_HOME, tgz_file_name)

    os.makedirs(output_dir_path, 0700)

    ret_code = os.system(cmd)
    if ret_code != 0:
        pass

    backup_file_names = os.listdir(output_dir_path)
    with tarfile.open(tgz_file_path, 'w:gz') as tgz_file:
        for backup_file_name in backup_file_names:
            file_path = os.path.join(output_dir_path, backup_file_name)
            file_name = os.path.join(output_dir_name, backup_file_name)
            tgz_file.add(file_path, file_name)

    shutil.rmtree(output_dir_path)


if __name__ == '__main__':
    main()
