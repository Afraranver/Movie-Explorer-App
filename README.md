# Movie Explorer App  

A modern and intuitive Android application to explore movies, built using Jetpack Compose and following clean architecture principles.  

---

## Features  
- Browse popular, top-rated, and upcoming movies.  
- View detailed movie information with images and ratings.  
- Optimized for devices with different screen sizes and resolutions.  
- Seamless performance on both Wi-Fi and mobile data.  

---

## Build & Run Instructions  

1. **Clone the repository**:  
   ```bash  
   git clone https://github.com/YourUsername/MovieExplorerApp.git  
   cd MovieExplorerApp  
   ```  

2. **API Key Configuration**:  
   - Obtain an API key from [The Movie Database (TMDB)](https://www.themoviedb.org/).  
   - Add the API key to the `local.properties` file in the root of the project:  
     ```
     TMDB_API_KEY=your_api_key_here  
     ```  

3. **Build and Run**:  
   - Open the project in **Android Studio (Giraffe or later)**.  
   - Sync the Gradle files.  
   - Run the app on an emulator or physical device with **Android 7.0+**.  

---

## How to Install  

To test the application on your Android smartphone, follow these steps:  

1. Open your Chrome browser and navigate to the download link:  
   [Download TMDB App APK](https://github.com/Afraranver/Movie-Explorer-App/releases/download/v1.0.0/movie_app.apk)  

2. The download will start automatically.  

3. Once the download is complete, locate the APK file in your device's Downloads folder.  

4. Tap on the APK file and follow the on-screen instructions to install the app.  

> **Note**: Ensure that "Install from Unknown Sources" is enabled in your device's settings.
 ---

## Testing Instructions  

### Unit Tests  
- Run unit tests by executing the following Gradle command:  
  ```bash  
  ./gradlew test  
  ```  
- Tests are located in the `src/test/java` directory.  

### UI Tests  
- Run UI tests by executing:  
  ```bash  
  ./gradlew connectedAndroidTest  
  ```  
- Tests are located in the `src/androidTest/java` directory and leverage **Jetpack Compose Testing APIs**.  

---

## Key Design and Technical Decisions  

### 1. **Architecture**  
- Implemented **MVVM (Model-View-ViewModel)** architecture to ensure clear separation of concerns, better scalability, and improved testability.  
- Utilized **Clean Architecture** principles by organizing the project into **Data**, **Domain**, and **Presentation** layers for modularity and maintainability.  
- Integrated **Hilt** for lifecycle-aware dependency injection, reducing boilerplate and ensuring efficient dependency management.  

### 2. **Jetpack Compose**  
- Adopted **Jetpack Compose** for building modern, declarative, and reactive UIs, resulting in cleaner and more reusable code.  
- Ensured **responsive layouts** to provide consistent user experiences across different device resolutions and screen sizes.  

### 3. **Networking**  
- Used **Retrofit** combined with **OkHttp** for robust API requests and responses, with built-in support for logging and interceptors.  
- Implemented **caching mechanisms** to optimize network usage and enhance performance in low-connectivity scenarios.  
- Followed best practices for error handling and API response parsing.  

### 4. **Data Management**  
- Leveraged **Room Database** for offline data storage and caching, ensuring smooth user experiences even without an active internet connection.  
- Integrated **Paging 3** for seamless infinite scrolling and optimized data loading in large datasets.  
- Adopted **DataStore** for managing lightweight data like user preferences, replacing traditional SharedPreferences.  

### 5. **Firebase Integration**  
- Integrated **Firebase Authentication** to provide secure and seamless login and signup workflows.  

### 6. **Testing**  
- Ensured reliability and quality through a robust testing strategy:  
  - **Unit Testing**: Used **JUnit** for core logic validation and **Mockito** for dependency mocking.  
  - **UI Testing**: Utilized **Jetpack Compose UI Testing APIs** for validating UI behavior.  

### 7. **Key Design Considerations**  
- **Performance Optimization**: Incorporated efficient state management and resource handling for smooth app performance.  
- **Scalability**: Designed the app with future scalability in mind, making it easy to extend features or add new modules.  
- **User Experience**: Ensured a responsive, visually appealing, and intuitive design to enhance usability.
  
---

## Tech Stack 🛠  

- **Jetpack Compose**: Modern toolkit for building native UIs with less code and powerful tools.  
- **Kotlin**: Official Android language for concise, safe, and expressive code.  
- **MVVM Architecture**: Decoupled design for better testability and maintainability.  
- **Hilt**: Simplified dependency injection with lifecycle-aware components.  
- **Coroutines & Flows**: Efficient asynchronous programming and data streaming.  
- **Android Architecture Components**: Robust libraries like ViewModel, Room, and Paging.  
- **Navigation**: Simplified navigation between composables.  
- **Material Components**: Customizable Material Design UI components.  
- **Retrofit & OkHttp**: Reliable networking with logging support.  
- **Lottie for Compose**: Smooth animations using JSON-based assets.  
- **Compose Pagination**: Gradual data loading for better performance.

---

## Project Structure  

The project follows **Clean Architecture** principles, ensuring scalability, maintainability, and testability. Here's an overview of the directory structure:  

### **`common`**  
- Contains shared utility classes and helper functions used across the application.  

### **`data`**  
- **`local`**: Handles local data storage, including Room database implementations.  
- **`remote`**: Manages network-related operations, such as API calls using Retrofit.  
- **`repository`**: Combines local and remote data sources to provide a single source of truth.  

### **`di`** (Dependency Injection)  
- Modules for setting up dependencies using **Hilt**.  
  - `AppModule`: Provides application-wide dependencies.  
  - `DataStoreModule`: Configures dependencies for DataStore.  
  - `RepositoryModule`: Binds repository interfaces to their implementations.  
  - `MovieDBRepositoryBind`: Specific bindings for the TMDB repository.  

### **`domain`**  
- **`model`**: Contains core business models and data classes.  
- **`repository`**: Interfaces defining the contracts for data handling.  
- **`use_case`**: Encapsulates business logic, separating it from the data and presentation layers.  

### **`presentation`**  
- Manages the UI and user interaction layer, organized by feature modules:  
  - `auth`: Handles authentication, including login and signup (integrated with Firebase).  
  - `dashboard`: Displays the main dashboard with movie listings.  
  - `movie_details`: Shows detailed movie information.  
  - `profile`: Manages user profile interactions.  
  - `search_movie`: Includes the search functionality for movies.  
  - `ui.theme`: Manages theming and design tokens for Jetpack Compose.  
  - `view_all`: Handles "View All" screens for categorized movie lists.  
- **`MainActivity`**: The main entry point of the application.  
- **`Navigation.kt`**: Centralized navigation logic using **Jetpack Navigation**.  
- **`Screen`**: Defines navigation routes and screen constants.  

---
