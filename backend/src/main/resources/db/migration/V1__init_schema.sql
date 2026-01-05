CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(255) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  display_name VARCHAR(255) NOT NULL,
  role VARCHAR(32) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE lots (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(120) NOT NULL,
  description TEXT NULL,
  start_price DECIMAL(19,2) NOT NULL,
  current_price DECIMAL(19,2) NOT NULL,
  min_bid_step DECIMAL(19,2) NOT NULL,
  status VARCHAR(32) NOT NULL,
  start_time TIMESTAMP NULL,
  end_time TIMESTAMP NULL,
  owner_id BIGINT NOT NULL,
  CONSTRAINT fk_lots_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE lot_category (
  lot_id BIGINT NOT NULL,
  category_id BIGINT NOT NULL,
  PRIMARY KEY (lot_id, category_id),
  CONSTRAINT fk_lc_lot FOREIGN KEY (lot_id) REFERENCES lots(id),
  CONSTRAINT fk_lc_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE bids (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  lot_id BIGINT NOT NULL,
  bidder_id BIGINT NOT NULL,
  amount DECIMAL(19,2) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_bids_lot FOREIGN KEY (lot_id) REFERENCES lots(id),
  CONSTRAINT fk_bids_bidder FOREIGN KEY (bidder_id) REFERENCES users(id)
);

CREATE INDEX idx_lots_status_end_time ON lots(status, end_time);
CREATE INDEX idx_bids_lot_amount_created ON bids(lot_id, amount, created_at);
CREATE INDEX idx_lc_category ON lot_category(category_id);
