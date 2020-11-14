CREATE USER keycloak IDENTIFIED BY 'keycloak';
CREATE DATABASE keycloak
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;
USE keycloak;
GRANT ALL ON keycloak.* TO keycloak;