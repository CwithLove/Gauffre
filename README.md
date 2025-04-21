# Gauffre — Terminal-Based Strategy Game

## 🎯 Project Overview
**Gauffre** is a two-player turn-based terminal game developed in Java, simulating a strategic battle over a poisoned waffle. The last player forced to eat the poisoned piece loses. This implementation is modular and ready for future integration with graphical interfaces and user input extensions.

---

## 🧩 Architecture Overview

### 1. **Model Layer**
#### `Niveau` — Game Grid (by Anthony & Mathis)
Encapsulates the game state as a 2D grid with semantic constants:
- `VIDE` = 0 → Empty cell  
- `GAUFFRE` = 1 → Normal waffle piece  
- `EMPOIS` = 2 → Poisoned cell (always at [0][0])

**Key Responsibilities:**
- Grid initialization and resizing
- Cell state management
- Termination condition detection (`finalNiveau()`)
- ASCII rendering of the current grid state

> Sample Output:
> - `#` → Gauffre  
> - `@` → Poisoned  
> - ` ` → Empty  

#### `TestNiveau` — Unit Tester for `Niveau`
Standalone test suite to validate construction, resizing, and mutation operations on the grid.

---

### 2. **Controller Layer**
#### `Jeu` — Game Manager (by Yuzhen & Yilun)
Central orchestrator managing:
- Game loop execution
- Turn alternation
- Input handling (scanner-based for CLI)
- Rule enforcement (cannot eat from empty cells)

> Winning condition: Player who **avoids** consuming the poisoned cell wins.  
> Turn-based logic: `currentPlayer = turn % 2`

---

## 🧪 Getting Started

### 🔧 Requirements
- Java 8+

### 🏁 Build & Run
#### Manual:
```bash
javac -cp . -d out *.java
java -cp out Jeu
```

