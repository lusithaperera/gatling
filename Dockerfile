FROM eclipse-temurin:11-jdk-alpine

# Install Maven
RUN apk add --no-cache maven

# Set working directory
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .

# Copy source code
COPY src src

# Download dependencies
RUN mvn dependency:go-offline -B

# Run Gatling tests
CMD ["mvn", "gatling:test", "-Dgatling.simulationClass=example.BasicSimulation"]
