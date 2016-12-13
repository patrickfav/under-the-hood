# Under the Hood - Android App Debug View Library

## Quick Start

Add the following to your dependencies

    dependencies {
        compile 'at.favre.lib.hood:extended:0.1.0'
     }

Create an activity and extend `PopHoodActivity`. Define it in your AndroidManifest:

        <activity
            android:name="com.example.your.Activity"
            android:label="App Info"
            android:theme="@style/HoodThemeDark">
        </activity>

Implement the config and page setter (see test app for examples)

## Details

The library comes in 2 flavors: 

### Library: `core` 
Contains only the base code without the default implementation of the debug activity. The advantage is that there is only minimal dependencies of `support*` libraries and therefore very lightweight, not adding to much methods to your app.

### Library: `extended`

Extends the `core` with an default implementation of a debug activity using `appcompat-v7` support library.

### Using PopHoodActivity

The easiest way is the create an `Activity` in your App and extend `PopHoodActivity`. Define it in your AndroidManifest:
        
        <activity
            android:name="com.example.your.Activity"
            android:exported="true" 
            android:label="App Info"
            android:theme="@style/HoodThemeDark">
        </activity>

As theme either use `@style/HoodThemeDark` or `@style/HoodThemeLight`. If you want to customize the theme extend either of the basic ones and override e.g. `colorPrimary`, `colorPrimaryDark` and colorAccent as well as `hoodZebraColor`.

Use `android:exported="true"` if you want to start your activity with adb.

### HoodDebugPageView

If you decide to use the `core` lib or want to implement your own `Activity` you need to create a HoodDebugPageView in your layout (see `PopHoodActivity` for a full example)

    <at.favre.lib.hood.views.HoodDebugPageView
        android:id="@+id/debug_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        custom:zebraBackgroundColor="?hoodZebraColor" />

The config `zebraBackgroundColor` defines the zebra highlighting color. In code set the up like this:

    HoodDebugPageView debugView = (HoodDebugPageView) findViewById(R.id.debug_view);
    debugView.setPageData(getPageData(DebugPage.Factory.create(config)), new Config.Builder().build());

The view has the following notable features:

* refresh() - refreshes the dynamic values and sets it to ui
* setProgressBarVisible(boolean isVisible) - shows a blocking progress ui

Note that your Activity should implement `IHoodDebugController` to enable all features.

### Template System

The base is a simple template system that is optimized to create pages in an easy and succinct way. It is also very flexible; using the provided interfaces one can implement any custom view. The base interface is `PageEntry` which provides the value it represents and how this value will be rendered in UI (see `ViewTemplate` for the UI part). Internally this will be added to a `RecyclerView` adapter therefore createing the views should be reasonably efficient.

## Built-In Entries

There are a couple of default implementations for `PageEntry`:

### KeyValueEntry

A basic entry key-value or property. Supports dynamic values (ie. every refresh will be reevaluated), multi-line layout for longer values and custom on-tap-actions. Default actions are: Toast, Dialog, Start-Intent and ask runtime permission.

For a lot of default data, e.g. device info, set permissions and build data, see `DefaultProperties`

### ActionEntry

Will be rendered as a simple button starting an custom action on click. Supports single and double column actions.

For a lot of default actions, e.g. android settings, app-info or uninstall and kill process, see `DefaultActions`

### ConfigBoolEntry & ConfigSpinnerEntry

For interactive debug features the standard implementations for switches and spinner can be used. The logic for the switch can be anything that implements the `ChangeableValue` interface. Spinner can also be customized to your demands.

There is a standard implementation for `ConfigBoolEntry` in `DefaultConfigActions` backed by shared preferences.

## Recipes

### Suggestions on what Properties/Actions to add to your Page

Apart from `DefaultProperties` the following could be useful:

> git-hash, git-branch, CI build no, build time, login-data, internal states

The following debug actions might be useful:

> clear (image) caches, manually calling requests, updating ui, changing shared pref states, directly open activities

### Start your debug activity through adb

Add `android:exported="true"` to your activity definition and use the following adb call:

     adb shell am start -n com.example.your.app-id/com.example.your.app.pacakge.DebugActivity

### Have certain debug features only in debug builds

Use a static boolean (e.g. `BuildConfig.DEBUG`) in an if like

    if(BuildConfig.DEBUG) {
        page.addAction(...)
    }
    
Although verbose, the advantage is that the compiler will remove the unreachable code in release builds similar to using C macros.

## Build

Assemble the lib with the following command

    gradlew :hood-core:assemble
    gradlew :hood-extended:assemble
    
The `.aar` files can then be found in `/hood-*/build/outputs/aar` folder 


## Libraries & Credits

* [Icon by by Freepik](http://www.flaticon.com/free-icon/nut_264316#term=nut&page=1&position=8)

## Similar Projects:

* [Bee - Debug and QA tool for android](https://github.com/orhanobut/bee)
* [DebugDrawer - Android Debug Drawer for faster development](https://github.com/palaima/DebugDrawer)
* [debugdrawer - Debug Drawer for android debug builds](https://github.com/palaima/DebugDrawer)

# License

Copyright 2016 Patrick Favre-Bulle

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.