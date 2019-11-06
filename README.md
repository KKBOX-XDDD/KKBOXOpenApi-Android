[![KKBOXOpenApi-Android](https://jitpack.io/v/KKBOX-XDDD/KKBOXOpenApi-Android.svg)](https://jitpack.io/#KKBOX-XDDD/KKBOXOpenApi-Android)

# KKBOXOpenApi-Android

The library helps to access KKBOX's Open API.

## Installation

### 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

``` groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2. Add the dependency

``` groovy
dependencies {
    implementation 'com.github.KKBOX-XDDD:KKBOXOpenApi-Android:${last_jitpack_version}'
}
```

## Usage

To start using a library, you need a valid client ID and secret. You can get one
from [KKBOX's developer site](https://developer.kkbox.com).

Then, you need to fetch a valid access token.

``` kotlin
KKBOXOpenApi.install(
        "fc87971f683fd619ba46be6e3aa2cbc2",
        "5b70cd567551d03d4c43c5cec9e02d1a",
        OkhttpRequestExecutor(OkHttpClient())
)
KKBOXOpenApi.update(Java8Crypto())
KKBOXOpenApi.fetchAuthToken({ throw AssertionError("Fetch token fail.") }) {
    /// Handle the success case.
}
```

Once you have an access token, you can create instance of the classes for
various APIs. For example:

``` kotlin
AlbumApi("KmRKnW5qmUrTnGRuxF").start(this, { throw AssertionError("testRequest_success fail") }) {
    assert(it.name.isNotEmpty()) {
        "album name not be empty."
    }
}
```
