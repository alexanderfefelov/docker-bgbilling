#!/usr/bin/env python2

import ConfigParser
from datetime import datetime, timedelta
import glob
import os
import random
import shutil
import sys
import tarfile


MYDUMPER = '/usr/bin/mydumper'
BACKUP_HOME = '/ofelia/data/bgbilling-mysql-backup'
MYSQL_HOST = 'backup.mysql.bgbilling.local'
MYSQL_PORT = 3306
MYSQL_DATABASE = 'bgbilling'
MYSQL_USERNAME = 'root'
MYSQL_PASSWORD = 'password'
OUTPUT_BASE_NAME_FORMAT = '{host}_{port}_{database}'
OUTPUT_DIR_NAME_FORMAT = '{output_base_name}_{now_str}_{random_str}'
MYDUMPER_LOG_FILE_NAME_FORMAT = '{host}_{port}_{database}.log'
MYDUMPER_CMD_FORMAT = '{mydumper} --host {host} --port {port} --database {database} --user {username} --password {password} --outputdir {output_dir_path} --logfile {log_file_name} --verbose {verbose} --less-locking --use-savepoints'
NOW_STR_FORMAT = '%Y%m%d_%H%M%S'
ARCHIVE_BASE_NAME_FORMAT = '{output_base_name}'
ARCHIVE_FILE_NAME_FORMAT = '{archive_base_name}_{now_str}_{random_str}'
ARCHIVE_COMPRESSION = ''
KEEP_DAYS = 2

TMP_HOME = '/ofelia/tmp'


class Config:
    def __init__(self):
        config_parser = ConfigParser.RawConfigParser()
        config_parser.read(os.path.join(os.path.abspath(sys.path[0]), 'application.conf'))
        pass


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
    config = Config()

    if not (os.path.isfile(MYDUMPER) and os.access(MYDUMPER, os.X_OK)):
        raise IOError('{0} does not exist or can not be executed'.format(MYDUMPER))

    if not os.path.exists(BACKUP_HOME):
            os.makedirs(BACKUP_HOME, 0700)

    now_str = datetime.now().strftime(NOW_STR_FORMAT)
    random_str = __base36_encode(random.randint(0, 42013))

    output_base_name = OUTPUT_BASE_NAME_FORMAT.format(
        host=MYSQL_HOST,
        port=MYSQL_PORT,
        database=MYSQL_DATABASE
    )

    output_dir_name = OUTPUT_DIR_NAME_FORMAT.format(
        output_base_name=output_base_name,
        now_str=now_str,
        random_str=random_str
    )

    tmp_dir_path = os.path.join(TMP_HOME, output_dir_name)
    os.makedirs(tmp_dir_path, 0700)

    mydumper_log_file_name = MYDUMPER_LOG_FILE_NAME_FORMAT.format(
        host=MYSQL_HOST,
        port=MYSQL_PORT,
        database=MYSQL_DATABASE
    )
    mydumper_log_file_path = os.path.join(tmp_dir_path, mydumper_log_file_name)

    mydumper_cmd = MYDUMPER_CMD_FORMAT.format(
        mydumper=MYDUMPER,
        host=MYSQL_HOST,
        port=MYSQL_PORT,
        database=MYSQL_DATABASE,
        username=MYSQL_USERNAME,
        password=MYSQL_PASSWORD,
        output_dir_path=tmp_dir_path,
        log_file_name=mydumper_log_file_path,
        verbose=3
    )

    archive_base_name = ARCHIVE_BASE_NAME_FORMAT.format(
        output_base_name=output_base_name
    )

    archive_file_name = ARCHIVE_FILE_NAME_FORMAT.format(
        archive_base_name=archive_base_name,
        now_str=now_str,
        random_str=random_str
    )
    archive_file_name += {
        '': lambda: '.tar',
        'gz': lambda: '.tar.gz',
        'bz2': lambda: '.tar.bz2'
    }[ARCHIVE_COMPRESSION]()
    archive_file_path = os.path.join(BACKUP_HOME, archive_file_name)

    ret_code = os.system(mydumper_cmd)
    if ret_code != 0:
        pass

    tmp_file_names = os.listdir(tmp_dir_path)
    with tarfile.open(archive_file_path, 'w:' + ARCHIVE_COMPRESSION) as archive_file:
        for file_name in tmp_file_names:
            file_path = os.path.join(tmp_dir_path, file_name)
            file_name = os.path.join(output_dir_name, file_name)
            archive_file.add(file_path, file_name)

    shutil.rmtree(tmp_dir_path)

    archive_file_paths = glob.glob(os.path.join(BACKUP_HOME, '*' + archive_base_name + '*'))
    now = datetime.today()
    threshold = timedelta(days=KEEP_DAYS)
    for file_path in archive_file_paths:
        file_time = datetime.fromtimestamp(os.stat(file_path).st_mtime)
        if now - file_time > threshold:
            os.remove(file_path)


if __name__ == '__main__':
    main()
