#! /usr/bin/env python
import os
from subprocess import Popen, PIPE

import click
import daiquiri
import delorean
from path import Path

TODAY = delorean.Delorean(None, timezone="Asia/Shanghai").date.__str__()
LOGGER = daiquiri.getLogger(__name__)


def init_dairy_folder():
    """
    init folder under the script file directory, .../dairy/<date> 
    """
    dir_path = Path(Path(__file__).dirname() + '/dairy/%s' % TODAY)

    if not os.path.isdir(dir_path):
        dir_path.makedirs()
        LOGGER.info("mkdir %s" % (dir_path))

    return dir_path


@click.command()
@click.option("--editor", default="code", help="specify the editor which for write dairy")
@click.option("--ext", default="md", help="specify the storage extension for the dairy")
@click.argument("title")
def main(title, editor, ext):
    """
    command line interface
    """
    dir_path = init_dairy_folder()
    cmd = "touch \"{path}/{title}.{ext}\" && {editor} \"{path}/{title}.{ext}\"".format(
        path=dir_path, title=title, editor=editor, ext=ext)
    Popen(cmd, shell=True)


if __name__ == '__main__':
    main()
