# casdoor-android-sdk-old

[![Status](https://img.shields.io/badge/status-deprecated-red.svg)](https://github.com/casdoor/casdoor-android-sdk)
[![Replaced by](https://img.shields.io/badge/replaced%20by-casdoor--android--sdk-blue.svg?logo=android)](https://github.com/casdoor/casdoor-android-sdk)
[![Maven Central](https://img.shields.io/maven-central/v/org.casbin/casdoor-android-sdk.svg?label=new%20SDK)](https://central.sonatype.com/artifact/org.casbin/casdoor-android-sdk)
[![License](https://img.shields.io/github/license/casdoor/casdoor-android-sdk-old.svg)](LICENSE)
[![Discord](https://img.shields.io/discord/1022748306096537660?logo=discord&label=discord&color=5865F2)](https://discord.gg/5rPsrAzK7S)

> [!WARNING]
> This is the first, Java version of the [Casdoor](https://casdoor.ai/) Android SDK, written in 2021. It is
> **deprecated and no longer maintained**. Please use [casdoor-android-sdk](https://github.com/casdoor/casdoor-android-sdk)
> instead.

## Why it is deprecated

- It puts the client secret and the JWT secret of the Casdoor application into the app. Anyone can extract them from the
  APK, and with the client secret they can call the Casdoor API as your application, for example to list all users of
  the organization.
- It was never published to a Maven repository, and it uses WebView APIs that have been removed from recent Android
  versions.

The new SDK signs users in with OAuth 2.0 and [PKCE](https://datatracker.ietf.org/doc/html/rfc7636), so the app needs no
secret. It is published to Maven Central, is tested in CI, and can be called from both Kotlin and Java.

## Migrate to the new SDK

Replace this library with:

```groovy
dependencies {
    implementation 'org.casbin:casdoor-android-sdk:0.1.0'
}
```

| This SDK                                    | casdoor-android-sdk                                         |
|---------------------------------------------|-------------------------------------------------------------|
| `ENDPOINT`, `CLIENTID`, `ORGANIZATIONNAME`  | `endpoint`, `clientID`, `organizationName` of `CasdoorConfig` |
| `REDIRECTURI` (hard-coded in the library)   | `redirectUri` of `CasdoorConfig`, such as `casdoor://callback` |
| `CLIENTSECRET`, `JWTSECRET`                 | Not needed, remove them from your app                       |
| `CasdoorLoginActivity`                      | Load `casdoor.getSignInUrl()` in your own WebView or browser |
| Token and user saved in `SharedPreferences` | `requestOauthAccessToken(code)` and `getUserInfo(accessToken)` return them to you |
| `CasdoorAuth.logout()`                      | `casdoor.logout(accessToken)`                               |

See the [README of casdoor-android-sdk](https://github.com/casdoor/casdoor-android-sdk#readme) for the full usage,
including Java, and [casdoor-android-example](https://github.com/casdoor/casdoor-android-example) for a demo app.

## License

[Apache-2.0](LICENSE)
