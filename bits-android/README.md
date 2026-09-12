# Bits

Your external brain, living on your home screen. Android prototype v0.5.

## Updating

1. Upload these files to your `bits` GitHub repo, replacing the old ones. Commit.
2. **Actions** → wait for the green tick → download **bits-apk** → install over v0.4.
3. Remove and re-add the widget so the launcher picks up the new preview image.

Settings → About → Version should read **0.5.0**.

## New in v0.5

**First run**
- New installs are walked through placing the widget before the tour starts.
  There's a manual route and a skip, because some launchers don't support one-tap pinning
  and trapping someone on that screen would be worse than an unplaced widget.
- The widget picker now shows a real preview of the list instead of a blank square.

**Games**
- **Flappy** is properly playable now: gentler gravity, a smaller fixed-height flap,
  capped rise and fall speed, slower pipes and a wider gap. Tested with an autopilot
  that survives 1200 ticks.
- **X and O** has a **2 Players** toggle, with turn-by-turn X and O on one phone, and
  the line "Go on, dare the person next to you." Against the phone it now plays loose
  about 30% of rounds so it's beatable, but it never fumbles a win that's right there.
- **Word Guess**: 136 words, free starter letters shown on the grid (fewer as your streak
  grows), invalid guesses clear themselves, a shake for rejects, letters pop in on a
  correct answer, and confetti when you solve it.
- **Memory Match** now has levels that cycle through colours, numbers, letters, fruit
  and pixel shapes, and the board grows as you go.

**Pro**
- Lifetime **₹349**, monthly **₹79**.
- The founder note appears **once per app run**. After that, locked items open the Pro
  page directly.
- The thank-you note for Pro users no longer re-introduces me.
- **Long-press** a locked theme or clock style to try it on the widget preview.

**Separate widget lists (Pro)** — Settings → Separate lists.
Each placed widget can show its own categories, with no requirement to include Today or
Tomorrow. Widgets without their own list keep following the main settings, so nothing
changes for free users or for anyone with a single widget. Settings for removed widgets
are cleaned up automatically.

## Not live yet

**Payments.** Play Billing needs a merchant account and a Play Console listing.
Test Pro features via Settings → Developer → **Simulate Pro**.

## The hidden thing

Tap the "Bits" title on the home page six times. One free theme, once per device.

## Before publishing

- Change `applicationId` to one you own. Rate Bits uses it.
- Delete the Developer section in `SettingsScreen.kt`.
- Make a real release signing key.
- Wire Play Billing so `isPro` comes from a verified purchase.
- Keep the font licences: Press Start 2P, Chakra Petch, Atkinson Hyperlegible.

## Project layout

- `data/` model, midnight move, storage, backups, themes, clock styles, per-widget boards
- `games/` pure game rules, all unit-tested (169 checks pass)
- `widget/` the home screen widget (Jetpack Glance)
- `time/` wakes the app after midnight, on reboot, on time zone changes
- `ui/` screens, onboarding, retro games, Pro page, tour
- `QuickEditActivity.kt` the floating add/edit card opened from the widget
