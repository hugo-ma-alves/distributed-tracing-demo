import logging
import random
from time import sleep

import elasticapm

logger = logging.getLogger("shipping-service")


@elasticapm.capture_span(name="ship_the_order")
def ship_the_order(order_json):
    order_id = order_json.get('orderId')
    products = order_json.get('products')

    # Simulate shipping logic
    logger.info(f"Scheduling shipping for Order ID: {order_id}")
    for product in products:
        logger.debug(f"Product ID: {product['productId']} - Quantity: {product['quantity']}")
    logger.info("Packing the items")
    packaging()
    logger.info("Sent")


@elasticapm.capture_span(name="packaging")
def packaging():
    sleep(random.randint(1, 5))
    logger.info("Done")
