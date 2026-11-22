# Carrier IMS for Pixel (TurboIMS) - 个人自用纯净版

<p align="center">
  <img src="app/src/main/ic_launcher-playstore.png" width="128" alt="Carrier IMS logo" />
</p>

<p align="center">
  <strong>面向 Google Pixel 的运营商与 IMS 工具集（个人纯净与 Android 17 增强版）</strong><br/>
  在 Shizuku 权限下快速调优 VoLTE/VoWiFi/VoNR、5G 显示与网络兼容行为，专注极致体验
</p>

<p align="center">
  中文（默认） | <a href="README_EN.md">English</a>
</p>

<p align="center">
  <a href="https://github.com/dsdffgh/carrier-ims-for-pixel/releases"><img alt="Release" src="https://img.shields.io/github/v/release/dsdffgh/carrier-ims-for-pixel"></a>
  <a href="LICENSE"><img alt="License" src="https://img.shields.io/github/license/dsdffgh/carrier-ims-for-pixel"></a>
  <img alt="Platform" src="https://img.shields.io/badge/Platform-Android%2013%2B-3DDC84">
  <img alt="Device" src="https://img.shields.io/badge/Device-Pixel%20Tensor-blue">
  <img alt="Permission" src="https://img.shields.io/badge/Requires-Shizuku-orange">
</p>

## 个人分支说明 (Personal Fork)

本项目是基于上游 [ryfineZ/carrier-ims-for-pixel](https://github.com/ryfineZ/carrier-ims-for-pixel) (3.9.1) 与 [Mystery00/TurboIMS](https://github.com/Mystery00/TurboIMS) 的**个人定制与持续维护分支**。

主要针对日常主力机使用体验，进行了彻底的**纯净化处理**以及针对 **Android 17（API 37）现代系统架构的深度兼容与修复**。

### 本分支核心特性与增强

1. **极致纯净（零商业化、零广告）**
   - 彻底移除上游 3.9.0 引入的所有商业化代码：包括首页广告弹窗、商业合作卡片/表单、第三方广告平台接口调用以及 DoDoPay 打赏模块。
   - 界面无多余干扰，去除无关网络请求与后台轮询，启动速度更快、内存占用更低。

2. **Android 17 (API 37) 深度系统级兼容**
   - **回流上游 3.9.1 官方权限兼容修复**：针对 Android 17 移除无参 `stopDelegateShellPermissionIdentity()` 导致的 `NoSuchMethodError` 崩溃问题，同步集成上游基于运行时签名的 UID 权限释放机制（`stopDelegateShellPermissionIdentityCompat`），确保在新旧系统上都能安全释放 Shell 委托权限且不阻塞 IMS 流程。
   - **独家 APN 自动拆分与防反弹自愈**：针对 Android 17 环境下运营商（如中国电信 CTNET）将 `default,supl,ims` 混写在一个 APN 行导致无法建立独立 IMS PDN（表现为 VoLTE 开关虽显示打开但网络始终未注册、无法通话）的问题，自动将 `type=ims` 独立剥离为专属 APN 行，并在每次 Shizuku 授权就绪时自动检测自愈，彻底解决 IMS 注册硬伤。
   - **CarrierConfig 持久化与读取优化**：引入 `CarrierConfigAccess`，通过直接对接 `ICarrierConfigLoader` 接口优先使用带持久化标志（Persistent Override）的覆盖写入链路并在失败时平滑回退，提升系统重启后的配置稳定性。

3. **独立维护与自动更新**
   - 应用内「检查更新 / 提交反馈 / 打开仓库」已完全对接到当前仓库：[dsdffgh/carrier-ims-for-pixel](https://github.com/dsdffgh/carrier-ims-for-pixel)。

---

## 界面预览

<p align="center">
  <img src="docs/Screenshot1.png" width="46%" alt="screenshot-1" />
  <img src="docs/Screenshot2.png" width="46%" alt="screenshot-2" />
</p>

## 功能矩阵

| 模块 | 能力 | 说明 |
|---|---|---|
| 系统信息 | 版本/设备/补丁/Shizuku 状态 | 集中展示运行环境，便于排障 |
| IMS 注册 | IMS 注册状态查询与手动注册 | 未注册时可一键触发注册流程 |
| 运营商能力 | VoLTE / VoWiFi / ViLTE / VoNR / UT / Cross‑SIM | 开关项实时生效，失败自动回滚并记录日志 |
| 5G 能力 | 5G NR / 5G 信号强度 / 5G+ 图标 | 适配中国大陆常见展示需求 |
| APN 调优 | APN 智能拆分与自动自愈维护 | 彻底解决 Android 17 混合 APN 无法注册 IMS 的问题 |
| 网络修复 | 一键修复网络验证（captive portal） | 修复“已连接但网络受限/感叹号” |
| TikTok 修复 | 修复 TikTok 无网络（大陆 SIM） | 仅大陆 SIM 提供该选项，海外 SIM 默认无需修复 |
| 诊断工具 | 日志查看、配置全量查看、Issue 快捷上报 | 失败日志可直接附带到 Issue |
| 应用维护 | 应用内检查更新与下载安装 | 直接对接当前仓库 Release |

## 快速开始

1. 从 [Releases](https://github.com/dsdffgh/carrier-ims-for-pixel/releases) 下载并安装 APK  
2. 安装并启动 [Shizuku](https://shizuku.rikka.app/zh-hans/)  
3. 打开 App，授权 Shizuku  
4. 选择 SIM，按需开启功能开关  

## 适用范围

- 设备：Pixel Tensor 平台（Pixel 6/7/8/9/10、Fold、Tablet）
- 系统：Android 13 及以上（深度适配 Android 14/15/16/17）
- 语言：简体中文 / English

## 构建（开发者）

```bash
./gradlew :app:assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

如需本地签名，请在 `local.properties` 中配置：

```properties
SIGN_KEY_STORE_FILE=/path/to/your.keystore
SIGN_KEY_STORE_PASSWORD=***
SIGN_KEY_ALIAS=***
SIGN_KEY_PASSWORD=***
```

## 常见问题

### 1. IMS 仍未注册（尤其是 Android 17 / 中国电信）

- 确认 Shizuku 已就绪并已授权。
- 在“附加功能” -> “APN” 中点击“检查与写入”，确认 `type=ims` 已被独立分离为单条 APN。本分支会自动处理拆分并在后台检测自愈。
- 检查 VoLTE / VoWiFi 开关是否开启，使用“诊断工具”收集现场日志排查。

### 2. 网络有信号但无法上网

- 先检查 APN 设置是否完整。
- 尝试使用“网络验证修复”功能解决 captive portal 感叹号问题。

### 3. TikTok 仍不可用

- 仅大陆 SIM 才会出现“修复 TikTok 无网络”开关。
- 开启后建议强制停止目标 App 或清理缓存后再重新打开测试。

## 更新记录

- 详细版本变更见 [CHANGELOG.md](CHANGELOG.md)
- 历史发布见 [Releases](https://github.com/dsdffgh/carrier-ims-for-pixel/releases)

## 致谢

- [ryfineZ/carrier-ims-for-pixel](https://github.com/ryfineZ/carrier-ims-for-pixel)
- [Mystery00/TurboIMS](https://github.com/Mystery00/TurboIMS)
- [vvb2060/Ims](https://github.com/vvb2060/Ims)
- [kyujin-cho/pixel-volte-patch](https://github.com/kyujin-cho/pixel-volte-patch)
- [nullbytepl/CarrierVanityName](https://github.com/nullbytepl/CarrierVanityName)

## 免责声明

本应用会修改系统运营商相关配置，仅用于学习、测试与自有设备调优。请自行评估风险并对操作结果负责。

## License

Apache-2.0
