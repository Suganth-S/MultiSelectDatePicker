# Multi-Select Date Picker for Android 📅

A customizable **Multi-Select Date Picker** component for Android, built using **Kotlin** and **RecyclerView**. This library provides an intuitive and user-friendly date picker with support for **single** and **multiple** date selection modes.

## 🌟 Features
- **📅 Single & Multiple Selection Modes** – Choose between selecting a single date or multiple dates.
- **🎨 Customizable UI** – Modify styles and themes to match your app's design.
- **🚀 Optimized for Performance** – Efficiently handles date selection using RecyclerView.
- **🔗 Easy Integration** – Simple API to integrate with your project.

---

## 📦 Installation

**Step 1:** Add the dependency in your `build.gradle` file:

```gradle
implementation 'com.github.Suganth-S:MultiSelectDatePicker:1.0.0'
```


---

## 🚀 Usage

### 1️⃣ Initialize the Multi-Select Date Picker Dialog

```kotlin
MultiSelectDatePickerDialog(
    context = this,
    initialSelectedDates = listOf(), // Pass previously selected dates if any
    selectionType = SelectionType.MULTIPLE, // Use SelectionType.SINGLE for single selection
    onDateSelected = { selectedDates ->
        // Handle selected dates
        Log.d("Selected Dates", selectedDates.toString())
    }
).show()
```

### 2️⃣ Handling Date Selection
The selected dates will be returned as a `List<Long>` containing timestamps. You can format them using `SimpleDateFormat`:

```kotlin
val formattedDates = selectedDates.map {
    SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(it))
}
```

### 3️⃣ Customizing UI
Modify the layout files or override styles to change colors, fonts, and design elements.

---

## 🎨 Customization Options
| Parameter        | Type      | Description |
|-----------------|----------|-------------|
| `selectionType` | `SelectionType` | Set selection mode: SINGLE or MULTIPLE |
| `initialSelectedDates` | `List<Long>` | List of pre-selected dates |

---

## 📝 License
This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.

---

## 📧 Contact
For any inquiries, feature requests, or issues, please open an **issue** in the repository or contact me at `suganthsece86@gmail.com`.

---

Enjoy coding! 🚀

