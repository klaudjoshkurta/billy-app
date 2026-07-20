# Implementation Plan - Calendar View

This plan outlines the steps to implement a calendar view inspired by the provided image, adapted to the existing monochrome theme.

## Proposed Changes

### Data Layer

#### [MODIFY] [Entry.kt](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/src/main/java/com/shkurta/billy/domain/model/Entry.kt)
- Add a `dueDay: Int` field to the `Entry` data class to represent the day of the month the bill/subscription is due.

### UI Layer

#### [NEW] [CalendarView.kt](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/src/main/java/com/shkurta/billy/ui/screens/CalendarView.kt)
- Implement the calendar grid based on the image:
    - **Day Headers**: Rounded capsules for MON-SUN.
    - **Day Cells**: Rounded squares for each day of the month.
    - **Indicators**:
        - Small circles/icons for entries due on that day.
        - The day number in the bottom-right corner of each cell.
    - **Styling**:
        - Use `LightGray` for empty cells.
        - Use `White` for the current day or cells with entries (depending on refinement).
        - Maintain the monochrome theme (Black/White/Gray).

#### [NEW] [HomeViewModel.kt](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/src/main/java/com/shkurta/billy/ui/screens/HomeViewModel.kt)
- Fetch all entries from the repository.
- Expose a state (e.g., `StateFlow<List<Entry>>`) for the UI to consume.

#### [MODIFY] [HomeScreen.kt](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/src/main/java/com/shkurta/billy/ui/screens/HomeScreen.kt)
- Inject `HomeViewModel`.
- Pass the list of entries to the `CalendarView`.
- Replace the placeholder "Calendar View (Coming Soon)" with the new `CalendarView` component.

#### [MODIFY] [NewEntryViewModel.kt](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/src/main/java/com/shkurta/billy/ui/screens/NewEntryViewModel.kt)
- Add a `dueDay` state variable and logic to handle its input.

#### [MODIFY] [NewEntryScreen.kt](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/src/main/java/com/shkurta/billy/ui/screens/NewEntryScreen.kt)
- Add an input field for the `dueDay` (e.g., a simple number field or a slider).

## Verification Plan

### Manual Verification
- Deploy the app.
- Add a few entries with different `dueDay` values.
- Navigate to the Home Screen and switch to the "Calendar" tab.
- Verify that entries appear on the correct days in the grid.
- Verify the layout and styling match the reference image (adapted to monochrome).
