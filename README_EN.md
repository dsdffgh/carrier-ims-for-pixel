# Carrier IMS for Pixel (TurboIMS) - Personal Pure Edition

<p align="center">
  <img src="app/src/main/ic_launcher-playstore.png" width="128" alt="Carrier IMS logo" />
</p>

<p align="center">
  <strong>Carrier and IMS toolkit for Google Pixel (Personal Pure & Android 17 Enhanced)</strong><br/>
  Tune VoLTE / VoWiFi / VoNR, 5G display behavior, and network compatibility with Shizuku privileges — ad-free and streamlined.
</p>

<p align="center">
  <a href="README.md">中文（默认）</a> | English
</p>

<p align="center">
  <a href="https://github.com/dsdffgh/carrier-ims-for-pixel/releases"><img alt="Release" src="https://img.shields.io/github/v/release/dsdffgh/carrier-ims-for-pixel"></a>
  <a href="LICENSE"><img alt="License" src="https://img.shields.io/github/license/dsdffgh/carrier-ims-for-pixel"></a>
  <img alt="Platform" src="https://img.shields.io/badge/Platform-Android%2013%2B-3DDC84">
  <img alt="Device" src="https://img.shields.io/badge/Device-Pixel%20Tensor-blue">
  <img alt="Permission" src="https://img.shields.io/badge/Requires-Shizuku-orange">
</p>

## Personal Fork Overview

This project is a personal customized and actively maintained fork based on upstream [ryfineZ/carrier-ims-for-pixel](https://github.com/ryfineZ/carrier-ims-for-pixel) (3.9.1) and [Mystery00/TurboIMS](https://github.com/Mystery00/TurboIMS).

It is specifically tailored for daily-driver Pixel devices with a **completely ad-free, pure UI** and **deep compatibility fixes for Android 17 (API 37)**.

### Key Enhancements & Differences

1. **Pure & Ad-Free Experience**
   - Completely stripped all commercialization code introduced in upstream 3.9.0: ad popups, lead generation forms, ad platform API integration, and in-app donation popups.
   - Clean UI with zero background polling or tracking overhead; faster startup and minimal resource usage.

2. **Deep Android 17 (API 37) System Compatibility**
   - **Backported Upstream 3.9.1 Permission Release Fix**: Backported upstream's runtime-adaptive UID-scoped shell permission delegation cleanup (`stopDelegateShellPermissionIdentityCompat`), resolving `NoSuchMethodError` crashes when stopping permission delegation on modern Android 17 builds.
   - **Exclusive APN Splitting & Self-Healing**: On Android 17, certain carrier APN rows (e.g., China Telecom CTNET) combine `default,supl,ims` into a single APN entry, preventing the modem from establishing an independent IMS PDN (VoLTE switch looks enabled, but IMS registration remains permanently OFF). This fork automatically extracts `type=ims` into a dedicated row and continuously guards against carrier OTA resets upon Shizuku readiness.
   - **CarrierConfig Persistence & Direct Loader Access**: Integrates `CarrierConfigAccess` to invoke `ICarrierConfigLoader` with persistent overrides and smooth fallback handling, keeping configs durable across reboots.

3. **Autonomous In-App Updates**
   - In-app actions (Check Update, Open Repository, Issue Tracker) point directly to this fork: [dsdffgh/carrier-ims-for-pixel](https://github.com/dsdffgh/carrier-ims-for-pixel).

---

## Screenshots

<p align="center">
  <img src="docs/Screenshot1.png" width="46%" alt="screenshot-1" />
  <img src="docs/Screenshot2.png" width="46%" alt="screenshot-2" />
</p>

## Feature Matrix

| Module | Capability | Notes |
|---|---|---|
| System Info | app/device/patch/Shizuku status | quick environment visibility |
| IMS Registration | status query + manual register | one-tap register workflow |
| Carrier Features | VoLTE / VoWiFi / ViLTE / VoNR / UT / Cross-SIM | realtime switches, rollback on failure |
| 5G Features | 5G NR / 5G signal threshold / 5G+ icon | optimized for common scenarios |
| APN Tuning | smart APN split + auto-heal | resolves combined APN IMS registration issues on Android 17 |
| Network Fix | captive portal one-tap fix | fixes restricted/exclamation network states |
| TikTok Fix | no-network fix for TikTok (Mainland SIM) | shown only for Mainland SIM |
| Diagnostics | logs / full config view / issue shortcut | submit issues with useful context |
| In-app Update | check, download, install updates | integrated with this fork's Releases |

## Quick Start

1. Download APK from [Releases](https://github.com/dsdffgh/carrier-ims-for-pixel/releases)
2. Install and start [Shizuku](https://shizuku.rikka.app/)
3. Open app and grant Shizuku permission
4. Select SIM and toggle required features

## Requirements

- Pixel Tensor devices (Pixel 6/7/8/9/10, Fold, Tablet)
- Android 13+ (deeply tested on Android 14/15/16/17)
- Shizuku running and authorized

## Build (Developers)

```bash
./gradlew :app:assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

If local signing is required, configure `local.properties`:

```properties
SIGN_KEY_STORE_FILE=/path/to/your.keystore
SIGN_KEY_STORE_PASSWORD=***
SIGN_KEY_ALIAS=***
SIGN_KEY_PASSWORD=***
```

## FAQ

### 1. IMS not registered (especially Android 17 / China Telecom)

- Confirm Shizuku is running and authorized.
- In Extra Tools -> APN, tap "Check and Write", ensuring `type=ims` is separated into its own row. This fork automatically splits and maintains it.
- Check VoLTE / VoWiFi toggles and collect logs using diagnostic tools if issues persist.

### 2. Network has signal but no internet

- Check APN settings first.
- Try the captive portal network verification fix card.

### 3. TikTok still unavailable

- TikTok fix switch only appears for Mainland SIMs.
- Restart the target app or clear its cache after toggling.

## Changelog

- Full changelog: [CHANGELOG.md](CHANGELOG.md)
- Releases: [GitHub Releases](https://github.com/dsdffgh/carrier-ims-for-pixel/releases)

## Credits

- [ryfineZ/carrier-ims-for-pixel](https://github.com/ryfineZ/carrier-ims-for-pixel)
- [Mystery00/TurboIMS](https://github.com/Mystery00/TurboIMS)
- [vvb2060/Ims](https://github.com/vvb2060/Ims)
- [kyujin-cho/pixel-volte-patch](https://github.com/kyujin-cho/pixel-volte-patch)
- [nullbytepl/CarrierVanityName](https://github.com/nullbytepl/CarrierVanityName)

## Disclaimer

This app modifies carrier-related system configuration for learning, testing, and personal tuning purposes. Use at your own risk.

## License

Apache-2.0
