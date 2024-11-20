# Changelog

## [1.0.1](https://github.com/Streetless/Omniflow/compare/v1.0.0...v1.0.1) (2024-11-20)


### 🐛 Bug Fixes

* **files:** Sha1 was invalid ([5baf1b4](https://github.com/Streetless/Omniflow/commit/5baf1b4abd59257548d5cf7775a042441afd2ede))


### 📚 Documentation

* **readme:** Add more usage & "how it works" ([0086bac](https://github.com/Streetless/Omniflow/commit/0086bac04b440a3f1de6e2de5f83afe2c49f03f3))


### 🔧 Build System

* **deps:** ⬆️ Update actions/checkout digest to 11bd719 ([#2](https://github.com/Streetless/Omniflow/issues/2)) ([3b93129](https://github.com/Streetless/Omniflow/commit/3b931295445e0193673d56edc444e689f1de1f77))


### 👷 Continuous Integration

* **publish:** Should fix artifact upload to release ([abb917c](https://github.com/Streetless/Omniflow/commit/abb917c8e16cff2bc0cb5837c94ca03d0061e7a0))


### 🔧 Other

* Release 1.0.1 ([405af4a](https://github.com/Streetless/Omniflow/commit/405af4ac19504631cf6e3f7042ef5e957dfb6fd9))
* **release-please:** Add back release type ([73db46c](https://github.com/Streetless/Omniflow/commit/73db46c73cdc207834e46b14fae39d1f6e37bd1e))

## 1.0.0 (2024-11-05)


### ✨ Features

* **cli:** --directory is optional with mode new ([7f65e17](https://github.com/Streetless/Omniflow/commit/7f65e17c74139d03d05e1ea39a4c29aa253009d2))
* **cli:** Add --release & --debug ([fc4ce08](https://github.com/Streetless/Omniflow/commit/fc4ce08facbf2d1172fd6541e6cfbdb4a7d72b98))
* **cli:** Add args parsing ([6fc1dcf](https://github.com/Streetless/Omniflow/commit/6fc1dcff50bdff83d210b845d848b8d34656fb10))
* **env:** Add environment validation ([b9e6279](https://github.com/Streetless/Omniflow/commit/b9e62790d521cecb49a7842de9dd36ef351f2907))
* **minio:** Add clear directory method ([5a061b9](https://github.com/Streetless/Omniflow/commit/5a061b93b81f6d8e3b8044a7a65969408ebdc3c2))
* **minio:** Add copyFile method ([086a035](https://github.com/Streetless/Omniflow/commit/086a03570cd298c6eddaac5c2f87cfb1802d4073))
* **minio:** Add copyFileFromBucket & moveFile ([fc4ce08](https://github.com/Streetless/Omniflow/commit/fc4ce08facbf2d1172fd6541e6cfbdb4a7d72b98))
* **minio:** Add downloadFile & downloadDir ([e2499d3](https://github.com/Streetless/Omniflow/commit/e2499d3caa75643c04cbc5db2841d12e8339cf2e))
* **minio:** Add kt method extension to path (toLinux) ([d837d2b](https://github.com/Streetless/Omniflow/commit/d837d2b5f9f2f5803f25ca5d4a8bb58314000780))
* **minio:** Add list directory method ([e80762f](https://github.com/Streetless/Omniflow/commit/e80762f088dfaeaf9c1bbe8b257a44b746d2f71b))
* **minio:** Add upload dir & file ([fdb8e76](https://github.com/Streetless/Omniflow/commit/fdb8e763244617684d0d08e98316e26265c8b0fb))
* **mode:** Add "new" mode ([7f65e17](https://github.com/Streetless/Omniflow/commit/7f65e17c74139d03d05e1ea39a4c29aa253009d2))
* **mode:** Add temporary mode ([45e5d71](https://github.com/Streetless/Omniflow/commit/45e5d71a53fe1a37843424262e9299106d26ed84))
* **mode:** Finish upload of manifest.json & files.json to S3 ([c080187](https://github.com/Streetless/Omniflow/commit/c0801877e753d62bdd3ce164c05e9bb79e486191))


### 🐛 Bug Fixes

* **cli:** Add more check for default value of --clear ([6c08a6b](https://github.com/Streetless/Omniflow/commit/6c08a6bb0f7d1b36b171da878911508c5a173dad))
* **cli:** Disallow --clear in --new mode ([0dfd22d](https://github.com/Streetless/Omniflow/commit/0dfd22dbccd1510981cf3870dc2f19bd06849cb9))
* **manifest:** Fix fetch of manifest ([6c08a6b](https://github.com/Streetless/Omniflow/commit/6c08a6bb0f7d1b36b171da878911508c5a173dad))
* **manifest:** Fix override of version ([6895559](https://github.com/Streetless/Omniflow/commit/689555931e364694d11e4ad5585862259f7e8586))
* **manifest:** Override of version, now checking type ([b8a6172](https://github.com/Streetless/Omniflow/commit/b8a61722953a1e1a696be4ad6c4d6bac45d66e03))
* **minio:** Fix client not closing after running ([d837d2b](https://github.com/Streetless/Omniflow/commit/d837d2b5f9f2f5803f25ca5d4a8bb58314000780))
* **release-please:** Add missing manifest ([18e21c5](https://github.com/Streetless/Omniflow/commit/18e21c5d755acd38a1fcc25c42763bf41fe43280))
* Windows path in version files ([c564a90](https://github.com/Streetless/Omniflow/commit/c564a901f02c7b2b3517e5535645ea2ed0d17900))


### 📚 Documentation

* **minio:** Add javadoc ([086a035](https://github.com/Streetless/Omniflow/commit/086a03570cd298c6eddaac5c2f87cfb1802d4073))
* **readme:** Add readme ([85ae4f8](https://github.com/Streetless/Omniflow/commit/85ae4f81263afd69499b6eb69ab77cc324fbaed0))


### ♻️ Code Refactoring

* **minio:** Update uploadDir to return a list ([d837d2b](https://github.com/Streetless/Omniflow/commit/d837d2b5f9f2f5803f25ca5d4a8bb58314000780))
* **minio:** Update uploadFile to have optional prefix ([c080187](https://github.com/Streetless/Omniflow/commit/c0801877e753d62bdd3ce164c05e9bb79e486191))
* **minio:** Update uploadFile to return a type ([d837d2b](https://github.com/Streetless/Omniflow/commit/d837d2b5f9f2f5803f25ca5d4a8bb58314000780))
* **mode:** Update name of enum mode ([fdb8e76](https://github.com/Streetless/Omniflow/commit/fdb8e763244617684d0d08e98316e26265c8b0fb))
* Remove semicolon ([17a0881](https://github.com/Streetless/Omniflow/commit/17a088194e8b9c3a61f874c6dca3a7c93ad9f387))
* Remove semicolon ([3208454](https://github.com/Streetless/Omniflow/commit/32084548a21c3398627c429f4b628bbd7a0ee4c0))


### 🔧 Build System

* **deps:** ⬆️ Update ktor monorepo to v3.0.1 ([#6](https://github.com/Streetless/Omniflow/issues/6)) ([e405889](https://github.com/Streetless/Omniflow/commit/e4058895751cc94088bf273bd04b5a95cea31d5b))
* **deps:** Add io.ktor:ktor-client-cio ([7f65e17](https://github.com/Streetless/Omniflow/commit/7f65e17c74139d03d05e1ea39a4c29aa253009d2))
* **deps:** Add io.ktor:ktor-client-content-negotiation ([6c08a6b](https://github.com/Streetless/Omniflow/commit/6c08a6bb0f7d1b36b171da878911508c5a173dad))
* **deps:** Add io.ktor:ktor-client-core ([7f65e17](https://github.com/Streetless/Omniflow/commit/7f65e17c74139d03d05e1ea39a4c29aa253009d2))
* **deps:** Add io.ktor:ktor-client-serialization ([6c08a6b](https://github.com/Streetless/Omniflow/commit/6c08a6bb0f7d1b36b171da878911508c5a173dad))
* **deps:** Add io.ktor:ktor-serialization-kotlinx-json ([6c08a6b](https://github.com/Streetless/Omniflow/commit/6c08a6bb0f7d1b36b171da878911508c5a173dad))
* **deps:** Add minio ([6fc1dcf](https://github.com/Streetless/Omniflow/commit/6fc1dcff50bdff83d210b845d848b8d34656fb10))
* **deps:** Dotenv-kotlin ([6fc1dcf](https://github.com/Streetless/Omniflow/commit/6fc1dcff50bdff83d210b845d848b8d34656fb10))
* **deps:** Kotlin-argparser ([6fc1dcf](https://github.com/Streetless/Omniflow/commit/6fc1dcff50bdff83d210b845d848b8d34656fb10))
* **deps:** Kotlin-logging-jvm ([6fc1dcf](https://github.com/Streetless/Omniflow/commit/6fc1dcff50bdff83d210b845d848b8d34656fb10))
* **deps:** Kotlinx-serialization-json ([6fc1dcf](https://github.com/Streetless/Omniflow/commit/6fc1dcff50bdff83d210b845d848b8d34656fb10))
* **deps:** Slf4j-simple ([6fc1dcf](https://github.com/Streetless/Omniflow/commit/6fc1dcff50bdff83d210b845d848b8d34656fb10))
* **docker-compose:** Add compose.yaml ([6fc1dcf](https://github.com/Streetless/Omniflow/commit/6fc1dcff50bdff83d210b845d848b8d34656fb10))
* **gradle:** Add application plugin ([bb12ebd](https://github.com/Streetless/Omniflow/commit/bb12ebd605da7368e6cd13970e2e4ba6ed03a48e))


### 👷 Continuous Integration

* **build:** Add build CI ([85ae4f8](https://github.com/Streetless/Omniflow/commit/85ae4f81263afd69499b6eb69ab77cc324fbaed0))
* **build:** Fix gradlew permission ([f47b943](https://github.com/Streetless/Omniflow/commit/f47b94398c725f9a40bf13479034cc6cc1ecc4ce))
* **build:** Fix upload artifact ([961d4a7](https://github.com/Streetless/Omniflow/commit/961d4a7b2aacf9ae8bd440d58427e271d3f6cb9e))
* **build:** Forgot the | ([86b5c9b](https://github.com/Streetless/Omniflow/commit/86b5c9b97a0b2c73f7b10ca53fb49a3e22aa545d))
* **build:** Should fix install java step ([79b0bfb](https://github.com/Streetless/Omniflow/commit/79b0bfbeec16b8ddebc925cf4b85d52afe1ea3ba))
* **publish:** Add publish CI ([9e552a9](https://github.com/Streetless/Omniflow/commit/9e552a939aa2971e7bd3fccee93a2169d705757f))
* **release-please:** Add release please CI ([85ae4f8](https://github.com/Streetless/Omniflow/commit/85ae4f81263afd69499b6eb69ab77cc324fbaed0))
* **todo:** Add todo CI ([85ae4f8](https://github.com/Streetless/Omniflow/commit/85ae4f81263afd69499b6eb69ab77cc324fbaed0))
