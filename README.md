# Coding Challenge

App loads 100 top US albums from Apple rss feed endpoint. 

You can checkout [App demo](./demo.mov)

## Architecture
Screens are done in MVVM architecure with Compose functions as View and ViewState per each screen as Model.
Channel and MutableSharedFlow are used for communication between View and ViewModel.

ViewModels are pulling/sending data through Repository which contains references to Realm database and Api service.
Repository is pulling data through Api Service which is defined in Networking module.

Repositories are providing data through Flow, so as soon as data in database is changed, view is notified through ViewModel subscription and redrawn. 

## Modules
To fasten compiling time, app is divided into modules. Modules are using custom made gradle plugins to load necessary libraries and project dependencies.
Plugins are defined in `buildplugins` module and applied in each of the modules' `build.gradle.kts`.
Each of the modules uses `Hilt` to provide its` feature to the rest of the modules.

There are 5 main modules: 
- `app` - App class, MainActivity and MainScreen. Combines all modules
- `common` - several submodules which are used in other modules such as Dagger Qualifiers, UI components, Navigation definition, Async classes etc.
- `data` - data modules (database definition, datasource and repositories and common data utils)
- `features` - MVVM modules for each screen
- `networking` - Retrofit, OkHttp and Api service definitions and providers
