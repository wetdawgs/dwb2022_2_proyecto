DROP TABLE IF EXISTS customer_image;

CREATE TABLE customer_image(
	customer_image_id INT NOT NULL AUTO_INCREMENT,
    customer_image TEXT NOT NULL,
    PRIMARY KEY (customer_image_id)
);