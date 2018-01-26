\c postgres;
DROP DATABASE deawio;
CREATE DATABASE deawio;
\c deawio;


CREATE OR REPLACE FUNCTION auto_modified()
  RETURNS TRIGGER AS $$
BEGIN
  new.modified = now();
  RETURN new;
END;
$$
LANGUAGE plpgsql;


CREATE TABLE product (
  product_id SERIAL PRIMARY KEY,
  created    TIMESTAMPTZ   NOT NULL DEFAULT now(),
  modified   TIMESTAMPTZ   NOT NULL DEFAULT now(),
  name       VARCHAR(200)  NOT NULL,
  code       VARCHAR(200)  NOT NULL,
  image_url  VARCHAR(1000) NOT NULL,
  UNIQUE (name),
  UNIQUE (code),
  UNIQUE (image_url)
);
CREATE TRIGGER auto_modified
  BEFORE UPDATE
  ON product
  FOR EACH ROW EXECUTE PROCEDURE auto_modified();


CREATE TABLE store (
  store_id SERIAL PRIMARY KEY,
  created  TIMESTAMPTZ  NOT NULL DEFAULT now(),
  modified TIMESTAMPTZ  NOT NULL DEFAULT now(),
  name     VARCHAR(200) NOT NULL,
  currency VARCHAR(3)   NOT NULL,
  UNIQUE (name)
);
CREATE TRIGGER auto_modified
  BEFORE UPDATE
  ON store
  FOR EACH ROW EXECUTE PROCEDURE auto_modified();


CREATE TABLE deal (
  deal_id    SERIAL PRIMARY KEY,
  created    TIMESTAMPTZ   NOT NULL DEFAULT now(),
  modified   TIMESTAMPTZ   NOT NULL DEFAULT now(),
  url        VARCHAR(1000) NOT NULL,
  high_price FLOAT         NOT NULL,
  low_price  FLOAT         NOT NULL,
  discount   FLOAT         NOT NULL,
  store_id   INT           NOT NULL REFERENCES store (store_id) ON DELETE CASCADE,
  product_id INT           NOT NULL REFERENCES product (product_id) ON DELETE CASCADE,
  UNIQUE (url),
  UNIQUE (store_id, product_id)
);
CREATE TRIGGER auto_modified
  BEFORE UPDATE
  ON deal
  FOR EACH ROW EXECUTE PROCEDURE auto_modified();


CREATE TABLE guest (
  guest_id SERIAL PRIMARY KEY,
  created  TIMESTAMPTZ  NOT NULL DEFAULT now(),
  modified TIMESTAMPTZ  NOT NULL DEFAULT now(),
  email    VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  UNIQUE (email)
);
CREATE TRIGGER auto_modified
  BEFORE UPDATE
  ON guest
  FOR EACH ROW EXECUTE PROCEDURE auto_modified();


CREATE TABLE watch (
  watch_id   SERIAL PRIMARY KEY,
  created    TIMESTAMPTZ NOT NULL DEFAULT now(),
  modified   TIMESTAMPTZ NOT NULL DEFAULT now(),
  guest_id   INT         NOT NULL REFERENCES guest (guest_id) ON DELETE CASCADE,
  product_id INT         NOT NULL REFERENCES product (product_id) ON DELETE CASCADE,
  price      FLOAT       NOT NULL,
  UNIQUE (guest_id, product_id)
);
CREATE TRIGGER auto_modified
  BEFORE UPDATE
  ON watch
  FOR EACH ROW EXECUTE PROCEDURE auto_modified();
