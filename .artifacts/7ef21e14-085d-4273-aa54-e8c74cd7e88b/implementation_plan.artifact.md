# Implementation Plan - Geist and Geist Mono Fonts Support

This plan covers adding the Google Fonts dependency and configuring the app to use the "Geist" and "Geist Mono" font families.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/gradle/libs.versions.toml)
- Add version for `androidx.compose.ui:ui-text-google-fonts` (e.g., `1.7.8` to match typical Compose versions or latest stable `1.8.0-alpha01` if needed, but I'll use a version compatible with the current setup).
- Add the library entry: `androidx-compose-ui-text-google-fonts = { group = "androidx.compose.ui", name = "ui-text-google-fonts", version.ref = "googleFonts" }`.

#### [MODIFY] [build.gradle.kts (App)](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/build.gradle.kts)
- Add `implementation(libs.androidx.compose.ui.text.google.fonts)` to the dependencies block.

### UI Layer - Theme

#### [MODIFY] [Type.kt](file:///C:/Users/g.shkurta/AndroidStudioProjects/billy-app/app/src/main/java/com/shkurta/billy/ui/theme/Type.kt)
- Define `GeistFontFamily` and `GeistMonoFontFamily` using `GoogleFont`.
- Update the `Typography` to use `GeistFontFamily` as the default for sans-serif styles.
- (Optional) Provide a way to use `GeistMonoFontFamily` for specific components if needed.

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to verify the build.

### Manual Verification
- Deploy the app and verify the new typography is applied.
- Specifically check the "Geist" font on UI elements and "Geist Mono" where applicable.
