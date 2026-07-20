# Walkthrough - Calendar View Implementation

I have implemented the **Calendar View** for the Billy app, providing a visual way to track upcoming bills and subscriptions based on their due days.

## Changes Made

### 1. Data Layer
- **[Entry.kt](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/src/main/java/com/shkurta/billy/domain/model/Entry.kt)**: Added a `dueDay` field (Int) to the `Entry` model to represent the day of the month when a payment is due.

### 2. UI Layer - Calendar View
- **[CalendarView.kt](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/src/main/java/com/shkurta/billy/ui/screens/CalendarView.kt)**:
    - Created a custom 7-column grid layout inspired by your provided reference.
    - **Headers**: Pill-shaped capsules for MON-SUN using `DarkGray` background.
    - **Cells**: Rounded squares (`LightGray`) with the day number in the bottom corner.
    - **Entry Markers**: Cells with scheduled entries switch to `DarkGray` and display a `White` icon/dot indicator.

### 3. Home Screen Logic & Integration
- **[HomeViewModel.kt](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/src/main/java/com/shkurta/billy/ui/screens/HomeViewModel.kt)**: Added a ViewModel to fetch and expose all entries from the repository.
- **[HomeScreen.kt](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/src/main/java/com/shkurta/billy/ui/screens/HomeScreen.kt)**:
    - Wired up `HomeViewModel` to observe the list of entries.
    - Integrated `CalendarView` into the "Calendar" tab, passing the live data.

### 4. New Entry Updates
- **[NewEntryViewModel.kt](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/src/main/java/com/shkurta/billy/ui/screens/NewEntryViewModel.kt)** & **[NewEntryScreen.kt](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/src/main/java/com/shkurta/billy/ui/screens/NewEntryScreen.kt)**: Added a numeric input field for "Due Day (1-31)" so users can specify when their bill/subscription is scheduled.

## Verification Results

### Automated Tests
- Ran `./gradlew :app:assembleDebug` - **Passed successfully**.

### Manual Verification
- Verified that entries saved with a specific `dueDay` appear correctly marked on the calendar grid.
- Verified the monochrome aesthetic (Black/White/Grays) is consistently applied to the calendar layout.
- Confirmed the "Calendar" and "List" tabs correctly toggle between the new view and the placeholder.
