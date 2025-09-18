
```sql
CREATE TABLE IF NOT EXISTS role (
    role_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    contact VARCHAR(15) NOT NULL,
    nic_no VARCHAR(12) NOT NULL UNIQUE,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);

CREATE TABLE IF NOT EXISTS vehicle (
    vehicle_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    model VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    category VARCHAR(50) NOT NULL,
    colour VARCHAR(50) NOT NULL,
    image_url VARCHAR(255),
    fuel_type VARCHAR(50) NOT NULL,
    license_plate VARCHAR(20) NOT NULL UNIQUE,
    seats INT NOT NULL,
    rent_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS driver (
    driver_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    nic VARCHAR(12) NOT NULL UNIQUE,
    contact VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS rent_details (
    rent_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    date DATE NOT NULL,
    pickup_date DATE NOT NULL,
    return_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    payment DECIMAL(10,2) NOT NULL,
    user_id BIGINT NOT NULL,
    vehicle_id BIGINT NOT NULL,
    driver_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicle_id),
    FOREIGN KEY (driver_id) REFERENCES driver(driver_id)
);

CREATE TABLE IF NOT EXISTS notification (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(20) NOT NULL,
    sent_at DATETIME NOT NULL,
    read BOOLEAN NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);