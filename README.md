Gatling Performance Testing - Pet Store API
==========================================

A comprehensive performance testing suite for the Pet Store API using Gatling with Maven. This project demonstrates advanced Gatling functionality including custom API chains, dynamic data generation, and comprehensive test scenarios.

## Project Overview

This project provides performance testing capabilities for the [Pet Store API](https://petstore.swagger.io/) with the following features:

* **Modular API Testing**: Separated API operations into reusable chains
* **Dynamic Data Generation**: Custom feeders for generating unique test data
* **Multiple Test Scenarios**: Both basic and comprehensive pet store workflows
* **Docker Support**: Containerized execution environment
* **Enterprise Integration**: DynaTrace header support for performance monitoring

## Project Structure

```
src/test/java/
├── com/pet_store/pet/
│   └── petAPI.java              # Pet Store API operations (create, read, update, delete)
├── example/
│   └── BasicSimulation.java     # Basic Gatling simulation example
└── test/
    └── PetstoreSimulation.java  # Comprehensive pet store test scenario

src/test/resources/
├── bodies/
│   └── new_pet.json            # JSON template for pet creation
├── gatling.conf                # Gatling configuration
└── logback-test.xml           # Logging configuration
```

## Key Components

### Pet Store API Operations (`petAPI.java`)

Provides reusable chain builders for common pet store operations:

- **Create Pet**: `POST /pet` - Creates a new pet with dynamic data
- **Get Pet By ID**: `GET /pet/{id}` - Retrieves pet details
- **Find Pets By Status**: `GET /pet/findByStatus` - Searches pets by availability
- **Delete Pet**: `DELETE /pet/{id}` - Removes a pet from the store

### Test Scenarios

#### Basic Simulation (`BasicSimulation.java`)
- Simple single-request test to `https://api-ecomm.gatling.io/session`
- Demonstrates basic Gatling setup and assertions
- Configurable via system properties

#### Pet Store Simulation (`PetstoreSimulation.java`)
- Complete workflow: Create → Read → Search → Delete
- Dynamic data generation with unique pet names and request IDs
- Configurable virtual users and ramp-up time
- Uses the modular `petAPI` chains for maintainable code

## Configuration

### System Properties

Configure test execution with the following properties:

```bash
# Virtual Users (default: 2)
-Dvus=10

# Ramp-up time in seconds (default: 2)
-Dramp=10

# Basic simulation users (default: 1)
-Dvu=5
```

### Environment Configuration

The project uses the following base URLs:
- **Pet Store API**: `https://petstore.swagger.io/v2`
- **Basic Test**: `https://api-ecomm.gatling.io`

## Usage

### Prerequisites

- Java 11 or higher
- Maven (or use the included Maven wrapper)

### Running Tests

#### Pet Store Performance Test
```bash
# Basic execution
./mvnw gatling:test -Dgatling.simulationClass=test.PetstoreSimulation

# With custom parameters
./mvnw gatling:test -Dgatling.simulationClass=test.PetstoreSimulation -Dvus=50 -Dramp=30
```

#### Basic Simulation Test
```bash
# Basic execution
./mvnw gatling:test -Dgatling.simulationClass=example.BasicSimulation

# With custom parameters
./mvnw gatling:test -Dgatling.simulationClass=example.BasicSimulation -Dvu=10
```

#### Run All Simulations
```bash
./mvnw gatling:test
```

### Docker Execution

```bash
# Build and run with Docker
docker build -t gatling-petstore .
docker run -it gatling-petstore
```

## Features

### Dynamic Data Generation
- Unique pet names using timestamps
- UUID-based request tracking
- Configurable pet status values

### Performance Monitoring Integration
- DynaTrace headers for APM correlation
- Request ID tracking for distributed tracing
- Transaction naming for better observability

### Advanced Gatling Patterns
- **Chain Builders**: Reusable request chains for maintainability
- **Custom Feeders**: Dynamic data generation using Java Streams
- **Session Management**: Variable passing between requests
- **Status Validation**: Comprehensive response checking
- **JSON Path Extraction**: Data extraction for subsequent requests

## Test Data

The project uses template-based JSON for test data:

```json
{
  "name": "#{petName}",
  "photoUrls": ["https://example.com/pets/#{petName}.jpg"],
  "tags": [{"name": "sample"}],
  "status": "#{status}"
}
```

Dynamic values are injected via Gatling's session variables.

## Maven Configuration

The project includes:
- **Gatling Maven Plugin** (v4.20.4) for test execution
- **Maven Wrapper** for immediate execution without local Maven installation
- **Java 11** compatibility with proper compiler configuration
- **Test-scoped dependencies** for Gatling charts and reporting

## Reporting

Test results are generated in the `target/gatling/` directory with:
- HTML reports for detailed analysis
- CSV exports for data processing
- Charts and graphs for performance visualization

## Contributing

When adding new API operations:
1. Add new methods to `petAPI.java` following the existing pattern
2. Include proper transaction naming for DynaTrace integration
3. Add appropriate status checks and data extraction
4. Update test scenarios to use new operations

## Documentation

For detailed Gatling documentation, refer to:
- [Gatling Official Documentation](https://docs.gatling.io/)
- [Maven Plugin Documentation](https://docs.gatling.io/reference/integrations/build-tools/maven-plugin/)
- [Java API Reference](https://docs.gatling.io/reference/script/java_api/)
