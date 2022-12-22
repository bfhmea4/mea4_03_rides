FROM mysql/mysql-server:latest

#ENV MYSQL_ALLOW_EMPTY_PASSWORD=true
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_DATABASE=ride-sharing-database

COPY ./migration/ /docker-entrypoint-initdb.d/

COPY ./migration/V1_0_0__FizzBuzzDatabase.sql .
COPY ./migration/V1_0_1__UserTable.sql .
COPY ./migration/V1_0_2__RideOfferTable.sql .
COPY ./migration/V1_0_3__RideRequestTable.sql .
COPY ./migration/V1_0_4__UserTableUniqueEmail.sql .
COPY ./migration/V1_0_5__UserTableBiggerPassword.sql .
COPY ./migration/V1_0_6__AddressTable.sql .
COPY ./migration/V1_0_7__AlterTableRideOffer.sql .
COPY ./migration/V1_0_8__AlterTableRideRequest.sql .