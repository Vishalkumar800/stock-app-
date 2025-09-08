# 📈 Stock App  

An Android application for a **Stocks & ETFs broking platform**, built using **Kotlin + Jetpack Compose**.  
This project was developed as part of an **Android SDE Intern Assignment**.  

---

## 📌 Features  

- **Explore Screen**  
  - View **Top Gainers & Losers** in a grid format.  
  - Click on any stock to view details.  

- **Watchlist**  
  - Add/remove stocks to/from your custom watchlists.  
  - Supports **empty state** when no items are added.  

- **Stock Details Screen**  
  - View company overview, price details, and Candle Stick Chart (graph).  
  - Add/Remove stocks directly from this screen (icon changes dynamically).  

- **Add to Watchlist Popup**  
  - Create a new watchlist or add to an existing one.  

- **View All Screen**  
  - See the entire list of stocks with **pagination support**.  

- **Additional Features**  
  - Proper **Loading / Error / Empty** state handling.  
  - API caching with expiration.  
  - **Dependency Injection** using Hilt.  
  - Dark/Light theme support.  

---

## 🏗️ Tech Stack  

- **Kotlin**  
- **Jetpack Compose** (UI)  
- **Hilt (Dependency Injection)**  
- **Room Database** (Local Storage for Watchlist)  
- **Coroutines + Flow** (Async operations & State management)  
- **Retrofit + Gson** (API calls)  
- **Alpha Vantage API** ([Docs](https://www.alphavantage.co/))  

---

## 📂 Folder Structure  

```
com.rach.stockapp
│── data
│   ├── network (API response models)
│   ├── repositoryImpl (API & DB repository implementations)
│   ├── roomdb (Room database, DAO, Entities)
│
│── domain
│   ├── model (Data models for API & DB)
│   ├── repository (Repository interfaces)
│
│── presentations
│   ├── navigation (App navigation)
│   ├── theme (UI theme - light/dark)
│   ├── ui (Screens & Composables)
│   ├── viewModels (MVVM ViewModels)
│
│── utils (AppModule, MainActivity, MyApp)
```

---

## 📸 Screenshots  

| Explore Screen | Stock Details | Watchlist |
|----------------|--------------|-----------|
| ![Explore](https://github.com/user-attachments/assets/142d4acd-9cf1-4fad-aa84-5b26326ed75b) | ![Details Screen](https://github.com/user-attachments/assets/bfdbb24c-7c38-4b44-a150-a50f8508904f) | ![Watchlist](https://github.com/user-attachments/assets/b378190c-6f38-47cf-a6e3-6bb5676ef793) |
| ![DarkMode](https://github.com/user-attachments/assets/d6a17494-a257-4191-bebe-bda2b48d8512)| ![DetailsScreen](https://github.com/user-attachments/assets/d6a17494-a257-4191-bebe-bda2b48d8512)|



## 🔑 API Integration  

This app uses [Alpha Vantage API](https://www.alphavantage.co/).  
- **Top Gainers & Losers API**  
- **Company Overview API**  
- **Stock Search API**  

👉 You need an API key: [Get one here](https://www.alphavantage.co/support/#api-key).  
Update your API key in the `AppModule`.  

---

## 🚀 Getting Started  

### 1️⃣ Clone the Repository  
```bash
git clone https://github.com/Vishalkumar800/stock-app-.git
cd stock-app-
```

### 2️⃣ Open in Android Studio  
- Open the project in **Android Studio (Arctic Fox or newer)**.  
- Sync Gradle files.  

### 3️⃣ Setup API Key  
- Get your API key from [Alpha Vantage](https://www.alphavantage.co/).  
- Add it in your `local.properties` or `AppModule`.  

### 4️⃣ Run the App  
- Connect your Android device/emulator.  
- Click **Run ▶️**.  

---

## 📝 Assignment Problem Statement  

This app was built as per the **Android SDE Intern Assignment**:  
- Build a stocks/etfs broking app with Explore, Watchlist, Stock Details, and Add-to-Watchlist popup.  
- Use AlphaVantage APIs.  
- Handle Loading/Error/Empty states.  
- Use Dependency Injection, Caching, and MVVM architecture.  

---

## 📦 APK  

👉 [Download APK](https://drive.google.com/) *(upload your APK here and replace link)*  

---

## 👨‍💻 Author  

- Vishal Kumar  
- 🌐 [GitHub](https://github.com/Vishalkumar800)  

---
