# XtremeO Client

A modern, feature-rich Tic-Tac-Toe game client built with JavaFX. Play locally with friends, challenge AI opponents, or compete online in multiplayer matches. XtremeO offers game recording, replay functionality, chat features, and an immersive gaming experience with audio and video effects.

## Features

### Game Modes
- **With Friend** - Local two-player game on the same machine
- **With CPU** - Challenge AI opponents with three difficulty levels:
  - **Easy** - Random move strategy
  - **Medium** - Heuristic-based strategy
  - **Hard** - Minimax algorithm with alpha-beta pruning
- **Online Player** - Multiplayer games via server connection

### Core Features
- **Game Recording & Replay** - Record your games and watch replays with auto-play functionality
- **Game History** - View and manage your past games with detailed statistics
- **In-Game Chat** - Communicate with opponents during matches (online mode)
- **Lobby System** - Join online lobbies, see active players, and invite others to play
- **Player Profiles** - Customize your profile with avatars and view your statistics
- **Score Tracking** - Track wins, losses, and draws with leaderboards
- **Audio System** - Background music and sound effects for game events
- **Video Animations** - Win/lose video animations for enhanced gameplay experience


## Images
![WhatsApp Image 2026-01-14 at 04 34 09 (1)](https://github.com/user-attachments/assets/cf510669-cef7-4b9a-beec-c6a11d0670cb)
![WhatsApp Image 2026-01-14 at 04 35 48](https://github.com/user-attachments/assets/42b6547b-2c47-49ba-b64d-c18d050f4880)
![WhatsApp Image 2026-01-14 at 04 36 19](https://github.com/user-attachments/assets/0931d41a-8e62-4676-a421-21a2ce96a485)
![WhatsApp Image 2026-01-14 at 04 34 09 (2)](https://github.com/user-attachments/assets/09d1e0da-cbc9-438c-97f3-fa08b48e2f8e)
![WhatsApp Image 2026-01-14 at 04 34 09](https://github.com/user-attachments/assets/6961dcac-4fde-4dfe-a6d3-8d28b254eb31)
![WhatsApp Image 2026-01-14 at 04 34 09 (3)](https://github.com/user-attachments/assets/8b54faa5-a267-4f8c-9b0d-6788d8f1156c)
![WhatsApp Image 2026-01-14 at 04 34 09 (4)](https://github.com/user-attachments/assets/3b675298-6f2d-45e3-8473-ce56a595bdd2)
![WhatsApp Image 2026-01-14 at 04 34 09 (5)](https://github.com/user-attachments/assets/89d389c7-abb6-4cee-a271-3237af1566dc)
![WhatsApp Image 2026-01-14 at 04 34 09 (6)](https://github.com/user-attachments/assets/aae80083-877b-4463-811d-030b8985ca83)



## Prerequisites

Before running the application, ensure you have the following installed:

- **Java Development Kit (JDK) 21** or higher
  - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
  - Verify installation: `java -version`
  
- **Apache Maven 3.6+**
  - Download from [Maven Downloads](https://maven.apache.org/download.cgi)
  - Verify installation: `mvn -version`

- **XtremeO Server** (Required for online features)
  - The server must be running and accessible
  - Default configuration: `localhost:6666`
  - See [Configuration](#configuration) section to change server settings

## Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd XtremeO-Client
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```
   This will download all dependencies and compile the project.

## Running the Application

### Using Maven (Recommended)

Run the application directly with Maven:
```bash
mvn clean javafx:run
```

### Using IDE

1. Open the project in your IDE (IntelliJ IDEA, Eclipse, NetBeans, etc.)
2. Ensure your IDE recognizes it as a Maven project
3. Navigate to the main class: `com.mycompany.xtremeo.client.app.App`
4. Run the `main` method

**Note:** For online multiplayer features, ensure the XtremeO Server is running and accessible at the configured address (default: `127.0.0.1:6666`).

## Usage Guide

### Main Menu

When you launch the application, you'll see the main menu with the following options:

- **Play with CPU** - Start a single-player game against an AI opponent
  - Select difficulty level (Easy, Medium, or Hard)
  - Optionally enable game recording
- **Play with Friend** - Start a local two-player game
  - Optionally enable game recording
- **Multiplayer** - Access online multiplayer features
  - Requires login/registration
  - Connect to the game server
- **History** - View your game history and replay past games
- **Sound Toggle** - Enable/disable background music and sound effects

### Playing a Game

1. **Local Games (With Friend or CPU)**
   - Select your game mode from the main menu
   - For CPU games, choose your difficulty level
   - Click on an empty cell to make your move
   - The game will automatically detect wins, draws, and game over states
   - Use the Reset button to start a new game
   - Use the Back button to return to the main menu

2. **Online Multiplayer**
   - Click "Multiplayer" from the main menu
   - Login with your credentials (or register if you're new)
   - Once logged in, you'll enter the lobby
   - In the lobby, you can:
     - See active players online
     - View top players and leaderboards
     - Send and receive game invitations
     - Chat with other players
   - Accept an invitation or send one to start a game
   - During the game, you can chat with your opponent
   - Games are automatically recorded

### Game History & Replay

1. Click "History" from the main menu
2. Browse through your recorded games
3. Select a game to view details
4. Click "Replay" to watch the game
5. In replay mode:
   - Click the Play/Pause button to auto-play the replay
   - The replay will automatically advance through moves
   - Click again to pause
   - When the replay finishes, click Play to restart from the beginning

### Recording Games

- Games can be recorded when starting a new game (except online games, which are always recorded)
- Recorded games are saved to the `recorded_games/` directory
- Online games are saved per user in subdirectories
- Recorded games include all moves, player information, and game results

## Project Structure

The project follows a well-organized package structure:

```
src/main/java/com/mycompany/xtremeo/client/
├── app/                    # Application entry point and navigation
├── controller/             # FXML controllers for UI components
│   ├── lobby/             # Lobby-specific controllers
│   └── ...
├── model/                  # Data models and view models
│   ├── auth/              # Authentication models
│   ├── common/            # Shared models
│   ├── game/              # Game-related models
│   ├── lobby/             # Lobby models
│   ├── recording/         # Game recording/replay models
│   └── viewmodel/         # View models for UI
├── service/                # Business logic and services
│   ├── audio/             # Audio management
│   ├── auth/              # Authentication services
│   ├── game/              # Game services
│   ├── lobby/             # Lobby services
│   ├── recording/         # Game recording services
│   └── video/             # Video playback services
├── network/                # Network communication
├── protocol/               # Protocol handlers and dispatchers
│   ├── dispatcher/        # Response dispatching
│   ├── envelope/          # Request/response envelopes
│   └── handler/           # Response handlers
├── ai/                     # AI opponent implementation
│   ├── strategies/        # AI strategies (Random, Heuristic, Minimax)
│   └── evaluator/         # Board evaluation logic
├── game/                   # Game engine and opponents
├── ui/                     # UI components and factories
│   └── dialog/            # Dialog components
├── util/                   # Utility classes
└── enums/                  # Enumeration types

src/main/resources/
├── com/mycompany/xtremeo/client/
│   ├── audio/             # Audio files
│   ├── videos/            # Video files
│   ├── images/            # Images and avatars
│   └── view/              # FXML files and CSS styles
```

## Configuration

### Server Configuration

The server connection settings are configured in `src/main/java/com/mycompany/xtremeo/client/network/NetworkConfig.java`:

```java
public static final String SERVER_HOST = "127.0.0.1";
public static final int SERVER_PORT = 6666;
public static final String PROTOCOL = "JSON";
```

To change the server address:

1. Open `src/main/java/com/mycompany/xtremeo/client/network/NetworkConfig.java`
2. Modify `SERVER_HOST` and `SERVER_PORT` constants
3. Rebuild the project: `mvn clean install`
4. Restart the application

### Recorded Games Location

Recorded games are saved in the `recorded_games/` directory at the project root. This directory is excluded from version control (see `.gitignore`).

- Local games: `recorded_games/game_<timestamp>.json`
- Online games: `recorded_games/<username>/game_<timestamp>.json`

## Technologies Used

- **Java 21** - Programming language
- **JavaFX 23** - UI framework
- **Apache Maven** - Build and dependency management
- **Gson 2.10.1** - JSON serialization/deserialization
- **Ikonli 12.3.1** - Icon library (Material Design 2)

## Build & Run Commands

### Build Commands

```bash
# Clean and compile
mvn clean compile

# Clean, compile, and package
mvn clean package

# Clean, compile, test, and package
mvn clean install
```

### Run Commands

```bash
# Run the application
mvn clean javafx:run

# Run with debugging (attach debugger to localhost:8000)
mvn clean javafx:run@debug

# Run with jar file
java -jar <jar-name>
```

### IDE-Specific Run Configurations

The `pom.xml` includes additional Maven execution configurations for IDE integration:
- `ide-debug` - For automatic IDE debugging
- `ide-profile` - For profiling support

## Team Members

This project was developed by:
- **Abdelrahman Waheed**
- **Wahid Qandil**
- **Mona Hamid**
- **Abdullah Elsobky**
- **Ali Abdulkareem**

## License

This project is part of an educational assignment.

## Contributing

This is a team project. For contributions, please coordinate with the team members listed above.
