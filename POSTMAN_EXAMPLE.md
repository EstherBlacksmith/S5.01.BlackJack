# Postman Example: Retrieve an Existing Game

This guide shows how to use Postman to retrieve an existing game from the Blackjack API.

## Prerequisites

1. Have Postman installed on your computer
2. Have the Blackjack Spring Boot application running locally
3. Have at least one game already created in the system

## Prerequisites

1. Have Postman installed on your computer
2. Have the Blackjack Spring Boot application running locally on port 8080
3. Have MongoDB running on port 27017 (standard MongoDB port)
4. Have at least one game already created in the MongoDB database

## Step-by-Step Guide

### Step 1: Open Postman and Create a New Request

1. Open Postman application
2. Click on "New" button in the top left corner
3. Select "HTTP Request"

### Step 2: Configure the Request

1. **Request Method**: Select `GET` from the dropdown
2. **Request URL**: Enter `http://localhost:8080/games/game/{id}`
    - Replace `{id}` with the actual ID of an existing game from MongoDB
    - Example: `http://localhost:8080/games/game/abc123-def456-ghi789`

### Step 3: Set Headers (Optional)

1. Click on the "Headers" tab
2. Add the following header:
    - **Key**: `Content-Type`
    - **Value**: `application/json`

### Step 4: Send the Request

1. Click the "Send" button
2. Wait for the response

### Step 5: Analyze the Response

**Successful Response (HTTP 200 OK):**

```json
{
  "id": "abc123-def456-ghi789",
  "playerName": "Alice",
  "playerScore": 15,
  "bankScore": 12
}
```

**Error Response (HTTP 400 Bad Request) - Missing ID:**

```json
{
  "error": "Missing game identifier",
  "status": 400
}
```

**Error Response (HTTP 404 Not Found) - Game doesn't exist in MongoDB:**

```json
{
  "error": "Game not found",
  "status": 404
}
```

## Complete Example

**Request:**

- Method: GET
- URL: `http://localhost:8080/games/game/abc123-def456-ghi789`
- Headers: `Content-Type: application/json`

**Successful Response:**

```json
{
  "id": "abc123-def456-ghi789",
  "playerName": "John",
  "playerScore": 18,
  "bankScore": 16
}
```

## Troubleshooting

1. **400 Bad Request**: Make sure you're providing a valid game ID in the URL
2. **404 Not Found**: The game with the specified ID doesn't exist in the database
3. **Connection refused**: Make sure the Spring Boot application is running on port 8080
4. **CORS issues**: If testing from a web application, you may need to configure CORS in the backend

## Additional Notes

- **Port Configuration**: The Spring Boot application runs on port 8080, while MongoDB runs on port 27017
- The endpoint validates that the ID is not blank or empty
- The response includes the complete game state including player name and scores
- This is a reactive endpoint using Spring WebFlux, so it returns a Mono<Game> that resolves to the game object
- The game data is stored in MongoDB, so you need to ensure MongoDB is running and accessible

## How to Start the Services

1. Start MongoDB: `docker-compose up -d blackjack-mongo`
2. Start the Spring Boot application: `mvn spring-boot:run`
3. The application will connect to MongoDB on port 27017
4. The REST API will be available on port 8080