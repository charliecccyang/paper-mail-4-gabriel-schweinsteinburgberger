# UserProfileAPI

The UserProfileAPI is a Dropwizard-based Java application that provides APIs to manage user profiles. It allows clients to perform operations such as creating, updating, and retrieving user profile information.

## Features

- Java 17 LTS
- Gradle with Shadow, JaCoCo, and test-logger plugins
- Dropwizard
- dropwizard-guicey which brings Guice power to Dropwizard
- JUnit 5, Mockito, AssertJ, and JsonUnit
- GitHub action to build, run checks, and tests

## API Endpoints

- `GET /users/{userId}/profile`: Retrieve user profile information by user ID
- `POST /users/commands`: Process user profile commands (Replace, Increment, Collect)
- `POST /users/commands/batch`: Process user profile commands in batches

## Getting Started

### Prerequisites

- Java 17
- Gradle

### Running the tests

To run the tests, use the following command:

```bash
./gradlew test
```

### Running the checks and tests

To run checks and tests, use the following command:

```bash
./gradlew check
```

### Running the application

To run the application, use the following command:

```bash
./gradlew run --args='server'
```

or without Gradle:

```bash
./gradlew build
java -jar ./build/libs/userprofile-api-1.0.0-SNAPSHOT.jar server
```

### Examples

#### Replace API

To invoke the replace API using curl, you can use the following command:

```bash
curl -X POST http://localhost:8080/users/commands \
  -H "Content-Type: application/json" \
  -d '{
        "userId": "de4310e5-b139-441a-99db-77c9c4a5fada",
        "type": "replace",
        "properties": {
          "currentGold": 500,
          "currentGems": 800
        }
      }'
```

#### Increment API

To invoke the increment API using curl, you can use the following command:

```bash
curl -X POST http://localhost:8080/users/commands \
  -H "Content-Type: application/json" \
  -d '{
        "userId": "de4310e5-b139-441a-99db-77c9c4a5fada",
        "type": "increment",
        "properties": {
          "battleFought": 10,
          "questsNotCompleted": -1
        }
      }'
```

#### Collect API

To invoke the collect API using curl, you can use the following command:

```bash
curl -X POST http://localhost:8080/users/commands \
  -H "Content-Type: application/json" \
  -d '{
        "userId": "de4310e5-b139-441a-99db-77c9c4a5fada",
        "type": "collect",
        "properties": {
          "inventory": ["sword1", "sword2", "shield1"],
          "tools": ["tool1", "tool2"]
        }
      }'
```
#### Batch API

To invoke the batch API using curl, you can use the following command:

```bash
curl -X POST http://localhost:8080/users/commands/batch \
  -H "Content-Type: application/json" \
  -d '{
        "commands": [
            {
                "userId": "de4310e5-b139-441a-99db-77c9c4a5fada",
                "type": "replace",
                "properties": {
                    "currentGold": 500,
                    "currentGems": 800
                }
            },
            {
                "userId": "de4310e5-b139-441a-99db-77c9c4a5fada",
                "type": "increment",
                "properties": {
                    "battleFought": 10,
                    "questsNotCompleted": -1
                }
            },
            {
                "userId": "de4310e5-b139-441a-99db-77c9c4a5fada",
                "type": "collect",
                "properties": {
                    "inventory": ["sword1", "sword2", "shield1"],
                    "tools": ["tool1", "tool2"]
                }
            }
        ]
      }'
```

These examples demonstrate how to use the curl command to interact with the UserProfileAPI endpoints for replace, increment, collect, and batch commands.


### License

This project is licensed under the MIT License.

