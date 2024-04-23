# Catalog

<img src="example.png" alt="Example" width="25%">

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Dependencies](#dependencies)

## Overview

Catalog is an Android application about cats developed in Kotlin. It provides detailed information about each cat breed, including their characteristics, origin, behavior, and more.

## Features

### Discover Cat Breeds
Explore a list of cat breeds, from popular to rare ones. The application supports real-time list refreshing, allowing for instant updates when searching for specific cat breeds. 
This feature is made possible by the MVI (Model-View-Intent) architecture, where user intents, such as search queries, are captured and processed in the ViewModel layer. 
As a result, the UI is automatically updated to reflect the filtered list of cat breeds without the need for manual refresh actions.

### Breed Details
Get in-depth details for each breed, including:
- Breed image
- Detailed description
- Countries of origin
- All temperament traits
- Lifespan, average weight
- And more!

## Architecture

Catalog follows the MVI (Model-View-Intent) architecture pattern.

### Model-View-Intent (MVI) Architecture
MVI is an architectural pattern that uses a unidirectional data flow and a reactive programming paradigm. In Catalog, this pattern is applied as follows:

- **Model**: Represents the data and business logic of the application. It is responsible for managing the state of the application and interacting with data sources, such as fetching cat breed information from a remote server using Retrofit. Coroutines are used for managing asynchronous operations and background tasks, ensuring efficient and non-blocking execution.

- **View**: Consists of Jetpack Compose UI components that display the application's user interface. Views observe the state emitted by the ViewModel and render the UI accordingly, ensuring a consistent and reactive user experience.

- **Intent**: Represents user actions or events that trigger state transitions in the application. These intents are captured by Compose and they are forwarded to the ViewModel, which processes them and updates the application state accordingly.

### Android Development Tools Used

#### Jetpack Compose
Jetpack Compose is a modern UI toolkit for building native Android UIs using a declarative programming model. It allows developers to describe the UI using composable functions, which are lightweight and modular.

#### Jetpack Navigation
Jetpack Navigation is a component that is used for navigation between different screens or destinations within an Android app. It provides a type-safe and declarative way to define navigation paths and handle transitions between screens.

#### Kotlin Flow
Kotlin Flow is a reactive stream processing library that provides a type-safe and concise way to handle asynchronous data streams. It is used to represent and propagate changes in the application state, allowing for reactive updates to the UI based on changes in data or user interactions. 
In this example, Kotlin Flow emits state updates from the ViewModel to the View layer, enabling reactive UI updates in response to user actions or changes in data.

#### Coroutines
Coroutines are a Kotlin feature that simplifies asynchronous programming by providing a structured concurrency framework. They allow us to write asynchronous code in a sequential (imperative) style, making it easier to understand and maintain. 
In this project, coroutines are used for performing asynchronous operations, such as API fetch, without blocking the main thread. This ensures a smooth and responsive user experience, even when dealing with potentially long-running tasks.

## Dependencies
- Jetpack Compose (1.5.1)
- Jetpack Navigation (2.7.7)
- Retrofit (2.11.0)
- OkHttp (4.12.0)
- KotlinX Serialization (1.6.3)
- Coil (2.6.0)
