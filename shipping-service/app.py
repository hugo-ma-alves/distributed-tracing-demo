import logging

from flask import Flask, request, jsonify

from setup import logger_setup, apm_setup
from shipping_service import ship_the_order

logger_setup()
logger = logging.getLogger("shipping-service")

app = Flask(__name__)
apm_setup(app)


def log_headers():
    headers = request.headers
    logger.info("HTTP Headers:")
    for header, value in headers.items():
        logger.info(f"{header}: {value}")


@app.before_request
def before_request():
    log_headers()


@app.route('/shipping/schedule', methods=['POST'])
def schedule_shipping():
    ship_the_order(request.json)
    order_id = request.json.get('orderId')

    return jsonify({"message": "Shipping scheduled successfully", "orderId": order_id}), 200


if __name__ == '__main__':
    app.run(debug=True, port=8090)
