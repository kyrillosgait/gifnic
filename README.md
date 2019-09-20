# Gifnic

Gifnic is an app where you can see the Trending GIFs.

## Purpose

A minimal, smooth, and easy to use application, that provides:

- Light/Dark theme.
- Immersive, edge to edge experience.
- Data usage: hard on WiFi, soft on mobile data.
- Recovery from network errors in seconds.

## Tooling

- Kotlin
- Dagger
- Coroutines
- Serialization
- Glide
- Lifecycle
- Paging
- Navigation
- JUnit
- MockK

# Architecture

I decided to go for Model-View-ViewModel architecture. It’s simple and efficient. When utilized correctly can improve testability, leak safety, and modularity. How do you test more, with less? Having a passive view that only observes properties in the ViewModel and dispatches user actions, means that the View’s is now much more simple with low margin of error. The rest of the logic in order to successfully get the list from the API and then show it to the user, is split in more parts. ViewModels, Repositories, and the Api.

## ViewModel

To keep the view passive and the app testable, we need to put elsewhere the logic that is not related to the view. We move that logic in the ViewModels. ViewModels have different scopes that Activities and Fragments. These can cycle through their whole lifecycle and yet, the ViewModel would be completely unaware and fully functional at the same time. There is no reference to the View from the ViewModel. How do you communicate with the View then? Well, the ViewModel does what the View asks for, and then stores the result in a LiveData property. The View, which is observing this property, reacts accordingly to changes. It's a good practice mutable properties private and exposing only immutable versions of them.

## Repository

The repository provides the data that ViewModels need. Where does it get the data from? It could be a local or a remote data source. This comes with some additional concerns. What happens when there is no internet connection? What happens when remote data source is offline? What happens when the database crashes from an unsuccessful migration? The repository now has a permanent job. Congratulations! Repository communicates with the data sources and does the error handling as well. The real source of data comes from eg: an API.

## Coroutines

As we’ve seen already, the architecture is solid and simple. Now we only need a good way to communicate between all these players in the team: View, ViewModel, Repository. I have to introduce a few other key players that will help us.

Coroutines are a great addition to every (p)layer of the architecture. They provide a smooth way of communication between these layers and they make managing tasks and threading simple.

## State

The View asks for data but how can it differentiate between data loading, a network error or an empty list? We need a way let the View know when it should show a progress bar, an error or an empty list. This is what the “State” class is trying to solve. It contains the following States: Loading, Success, Error, or Empty. So instead of observing a List<Some>, it makes more sense to observe a State<List<Some>>, which not only contains the list, but also valuable information about if it’s loaded, if it’s empty or if there was an error in the process, all in one!

## StatefulLiveData

Now the ViewModel should not only post the data there, but also the state. StatefulLiveData extend LiveData and support changing the state of a LiveData property.  Did the data start loading? Post loading. Was there an error in the process? Post an error state with the error. Was it successful after the user pulled to refresh? Post a successful state with the data. That’s the ViewModel’s job. Correctly represent the state of the data. How can the ViewModel easily know if it was a success or failure in the api request?


## Answer

For communication between the ViewModels and the Repository, the Answer sealed class is used. When the ViewModel asks for a list, the Repository tries to get the list, does the error handling, and returns an Answer. The Answer can be either Success, if the api request was successful, or Error, if there was no internet connection, or json parsing failed, or server sent us a lovely 500.
