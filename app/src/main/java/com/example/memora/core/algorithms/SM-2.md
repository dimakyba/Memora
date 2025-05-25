Alright, let’s break down the **SuperMemo-2 (SM-2)** algorithm and implement it in **Kotlin** with a twist: support for three difficulty levels — **Easy**, **Medium**, and **Hard** — as user feedback during review. 😎

---

### 🧠 What is SM-2?

SuperMemo-2 is the original spaced repetition algorithm from the late '80s. It calculates the next review interval for a flashcard based on how well you remember it. The better you recall, the longer until the next review.

It uses these variables:

* `EF` (Easiness Factor): How easy the card is to remember.
* `interval`: Days until next review.
* `repetition`: How many times the card has been reviewed successfully.
* `quality`: A score (usually 0–5) based on recall difficulty.

In our version, we’ll simplify things:

* `Easy` → quality = 5
* `Medium` → quality = 3
* `Hard` → quality = 1

---

### 📦 Data Model in Kotlin

```kotlin
data class Flashcard(
    val id: Int,
    var interval: Int = 1,
    var repetition: Int = 0,
    var easinessFactor: Double = 2.5,
    var nextReviewInDays: Int = 1
)
```

---

### ⚙️ SuperMemo-2 Algorithm (Modified with Difficulty)

```kotlin
fun updateFlashcard(card: Flashcard, feedback: String) {
    val quality = when (feedback.lowercase()) {
        "easy" -> 5
        "medium" -> 3
        "hard" -> 1
        else -> throw IllegalArgumentException("Invalid feedback. Use Easy, Medium, or Hard.")
    }

    // Update EF (Easiness Factor)
    val newEF = (card.easinessFactor + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02)))
        .coerceAtLeast(1.3)

    if (quality >= 3) {
        card.repetition += 1
        card.interval = when (card.repetition) {
            1 -> 1
            2 -> 6
            else -> (card.interval * newEF).toInt()
        }
    } else {
        card.repetition = 0
        card.interval = 1
    }

    card.easinessFactor = newEF
    card.nextReviewInDays = card.interval
}
```

---

### 🧪 Example Usage

```kotlin
fun main() {
    val card = Flashcard(id = 1)

    println("Initial: $card")
    updateFlashcard(card, "medium")
    println("After Medium: $card")
    updateFlashcard(card, "easy")
    println("After Easy: $card")
    updateFlashcard(card, "hard")
    println("After Hard: $card")
}
```

---

### 🎮 Output Breakdown

Let’s say:

* First review → Medium (EF drops a bit, interval is 1)
* Second review → Easy (EF increases, interval jumps)
* Third review → Hard (EF drops, interval reset)

---

### 🧩 Summary

| Feedback | Quality | Effect                                |
| -------- | ------- | ------------------------------------- |
| Easy     | 5       | Increases EF, longer next interval    |
| Medium   | 3       | Slightly lowers EF, moderate interval |
| Hard     | 1       | Drops EF, resets repetition to 0      |

This setup keeps the original SM-2 soul while making it human-friendly. You get adaptive intervals *and* intuitive difficulty options.

Let me know if you want to store this in a database or visualize the learning curve! 📈
