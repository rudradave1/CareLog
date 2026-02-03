# CareLog — Offline-First Android Task App

Offline-first Android task app demonstrating Room as source of truth, background sync with WorkManager, and conflict resolution. Built as a production-grade engineering portfolio project.

CareLog is a production-grade, offline-first Android application built to demonstrate reliable local persistence, background synchronization, and conflict resolution. 

The app uses Room as the single source of truth, supports manual and periodic sync via WorkManager, and resolves conflicts deterministically using timestamps. This project is intended as an engineering showcase rather than a consumer task app.
 
---

## ✨ Key Highlights

### Offline-First Architecture
- Room is the **single source of truth**
- App works fully offline with zero degradation
- Network is used only for background sync

### Background Sync & Conflict Resolution
- Manual “Sync now” + periodic WorkManager sync
- Conflict resolution using `updatedAt` (newer wins)
- Pending local changes tracked until successfully synced
- Sync metadata persisted safely across app restarts

### Clean, Scalable Architecture
- Multi-module setup (`app`, `feature`, `core`)
- Clear separation of UI, domain, data, and sync layers
- ViewModel + state-driven Compose UI
- No tight coupling between Room and network

### Production Considerations
- Fake server implementation for local testing
- Sync logic isolated in a dedicated repository
- Accessibility-aware UI and restrained animations
- Designed to scale beyond a demo app

---

## 🧱 Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose (Material 3)
- **Architecture**: MVVM + state flows
- **Persistence**: Room
- **Background Work**: WorkManager
- **Networking**: Retrofit + Moshi
- **Sync Strategy**: Offline-first with conflict resolution
- **Build**: Gradle Version Catalogs, modular setup

---

## 🗂️ Project Structure

app/
core/
├── database # Room entities, DAO, repository
├── network # Retrofit sync + fake server
├── sync # Offline-first sync logic
feature/
├── tasks # Task list & add task UI
├── settings # Sync & preferences UI

---

## 🔄 Sync Model (High Level)

1. User creates/updates tasks offline
2. Changes are stored locally with `updatedAt`
3. Background worker pushes pending changes
4. Server responds with newer updates
5. Conflicts resolved deterministically
6. UI always reads from Room

---

## 🚧 Notes

- Base sync URL is configurable (currently a placeholder)
- Fake server is used to demonstrate sync behavior locally
- Tests were not added to keep scope focused

---

## 🎯 Why This Project Exists

CareLog is designed to answer one question:

> *Can this engineer design and implement reliable, offline-first Android systems suitable for production apps?*

---

## 📸 Screenshots

(Add 2–3 screenshots here)

---

## 🧑‍💻 Author

Built by **Rudra Dave** as a senior-level Android engineering portfolio project.
