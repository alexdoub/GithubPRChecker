# Github PullRequest Diff Checker #

This app lets you check the line-by-line code difference in a pull request.

The project is designed with Dagger 2, RxJava & MVP paradigms. 

## Current Work In Progress ##
I'm adding Room to the app for database support. TODO

## Overview & Notes ##

The components are neatly separated to better facilitate expansion of the app. For example, we could add a new sections to the app representing different parts of Github, or there could be a settings section where you could select a User and/or Repo.

Note: Processing large diffs can be an expensive operation and the loading of diffs could possibly be broken down into sections and improved upon. This was not part of the requirements, however.

## Usage ##

Currently both a user and diff are hardcoded into the app, so you can easily just launch the app, select a PR, then view the diff. Feel free to change the user and repo to see other diffs available.

## Components ##

Here is a high level overview of the project. It is centered around the main dagger components.

```
Dagger Components
    │
    ├──AppComponent
    │   ├── AppModule 
    │   │       └──AppContext─>┐
    │   ├── DataModule         │
    │   │       └─────────DataManager──>──┐
    │   └── NetworkModule                 │
    │           └─────────APIClient──>────┤
    │                                     │
    └── GithubComponent                   │
            └── GithubModule              ↓
                    └────────────────GithubModel
```

* App Module - Contains app context
* Data Module - Contains information regarding the currently selected user & repo. Currently hardcoded.
* Network Module - Does network calls over API via Retrofit and OkHTTP3. 
* Github Module - Maintains model of a Github user's repo. Shared among RepoPRListActivity & PRDiffActivity


## Activities & Flow ##

The activities follow a MVP paradigm where both activities share the same GithubModel.

Here is a basic high level overview of how just the MVP parts connect. (Excluding list view classes)

```
   ...
    ↓
GithubModel 
(Injects into)
    ↓
    │
    ├──RepoPRListActivity
    │       ├──RepoPRListView─┐
    │       │                 ↓
    │       └────────RepoPRListPresenter
    │
    │
    └──PRDiffActivity
            ├──PRDiffView──┐
            │              ↓
            └────────PRDiffPresenter
```

The view binds data and exposes rx observable for touch events
The model does API calls and exposes rx observable for finished fetch events
The presenter hooks up the two via subscriptions

Example data flow for selecting a PullRequest:
1) User taps a Pull Request
2) Adapter passes ID of tapped PR to presenter via rx subscription
3) Presenter creates PRDiffActivity and passes it the selected ID
4) PRDiffActivity loads, presenter immediately fetches diff from model
5) Model does API call to fetch raw diff
6) Model parses raw diff into diff object (expensive for large diffs)
7) Presenter recieves processed Diff model via subscription, passes to view
8) View binds data
 
