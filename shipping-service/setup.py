import logging

import ecs_logging
from elasticapm.contrib.flask import ElasticAPM
from flask import Flask


def apm_setup(app):
    # Config read from env vars
    ElasticAPM(app)


def logger_setup():
    root_logger = logging.getLogger()
    root_logger.setLevel(logging.DEBUG)

    # Add an ECS formatter to the Handler
    handler = logging.FileHandler('/var/logs/shipping.json')
    handler.setFormatter(ecs_logging.StdlibFormatter())
    root_logger.addHandler(handler)

    handler_console = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s.%(msecs)03d %(levelname)s %(message)s', datefmt='%Y-%m-%d %H:%M:%S')
    handler_console.setFormatter(formatter)
    root_logger.addHandler(handler_console)
