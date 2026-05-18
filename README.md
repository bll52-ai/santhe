# Santhe - Traditional Market Discovery

**Santhe** (Kannada for 'Weekly Market') is a modern Android application designed to bridge the gap between traditional commerce and digital convenience. Built with **Jetpack Compose** and **OpenStreetMap (OSMdroid)**, it enables travelers and locals to discover iconic food spots, heritage markets, and unique craft centers across Karnataka, with a specialized focus on **Bengaluru**.

---

## 🚀 Key Features

- **Interactive Map Discovery:** Explore curated locations using a fully open-source, offline-capable map interface.
- **Santhe Calendar:** Stay updated with weekly market schedules. Supports multi-day events (e.g., markets active on both Monday and Sunday).
- **Location Picker:** Add new places easily with a precise crosshair map pointer.
- **Community Reviews:** Read and share experiences with a local rating and review system.
- **Persistent Sessions:** Seamless auto-login ensures you stay connected to your profile across app restarts.
- **Offline First:** Built-in tile caching for reliable map access even in areas with poor connectivity.

---

## 🛠️ Technical Stack

| Category | Technology | Usage |
| :--- | :--- | :--- |
| **UI Framework** | Jetpack Compose | Modern declarative UI with Material 3 components. |
| **Maps Engine** | OSMdroid (OpenStreetMap) | Open-source, free, and privacy-focused map integration. |
| **Database** | Room Persistence Library | Local SQLite storage for stalls, reviews, and user accounts. |
| **Architecture** | MVVM | Clean separation of concerns with Repositories and ViewModels. |
| **Language** | Kotlin | Primary language for robust and concise Android development. |
| **Image Loading** | Coil | Efficient, Kotlin-first asynchronous image loading. |
| **Navigation** | Compose Navigation | Type-safe routing between feature modules. |

---

## 📸 Bengaluru Exploration

The app comes pre-configured with **15+ iconic Bengaluru locations**, including:
*   **Markets:** KR Market, Gandhi Bazaar, Commercial Street.
*   **Eateries:** Vidyarthi Bhavan, CTR (Shri Sagar), MTR, Veena Stores.
*   **Crafts:** Pottery Town, Chithrakala Parishath, Cauvery Handicrafts.

---

## 🏗️ Getting Started

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/bll52-ai/santhe.git
    ```
2.  **Open in Android Studio:** Ensure you have the latest version of Ladybug (or higher).
3.  **Build & Run:** Use the `gradlew assembleDebug` command or click the **Run** button in Android Studio.

---

## 📄 License

This project is open-source and available under the MIT License.

---
*Created with ❤️ for the culture and heritage of Karnataka.*
