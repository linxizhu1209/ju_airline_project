FROM mongo:7.0.5
RUN apt-get update && apt-get install -y mongodb-database-tools