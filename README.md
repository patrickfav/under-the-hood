# Under the Hood - Android App Debug View Library

## Quick Start

## Details

The library comes in 2 flavors: 

### `core` 
Contains only the base code without the default implementation of the debug activity. The advantage is that there is only minimal dependencies of `support*` libraries and therefore very lightweight, not adding to much methods to your app.

### `extended`

Extends the `core` with an default implementation of a debug activity using `appcompat-v7` support library.

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

## Build

## Libraries & Credits

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