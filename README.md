# icerock-test-task

My latest updatable test task showing my abilities. Uses MVP pattern. 

--

Libraries used :

* Android Libraries ( `appcompat`, `design support`, etc ) -- makes new features available on old api.
* Retrofit -- must have lib for network requests.
* Picasso -- must have lib for working with images if you don't need to compress them.
* Dagger -- dependency injection.
* Moxy -- MVP.
* Rx Java 2 + Rx Android -- Schedulers + Observable.
* Timber -- hide log on release + formatting.
* ButterKnife -- must have lib for views.
* LeakCanary -- leak detection.

--

Project features :

* Clean architecture
* Basic animation
* Network requests, handling errors
* Checking input fields for different types
* RecyclerView Sample
* Dagger Flow
	* Application scope
	* Modules & dependencies
* Supports Russian & English languages
* Supports phones & tablets
* Supports both orientations
* Proguard
* Simple tests
	
--
![screenshots](https://raw.githubusercontent.com/spurd0/icerock-test-task/master/icerock.jpg "Screenshots")
--

### ToDo

- [ ] Replace array of EditTexts with RecyclerView
- [ ] Add some DataBinding samples (MVVM)
- [ ] Add some Fragments
- [ ] Add activity scope for Dagger
- [ ] Store images info in DB (Realm)
- [ ] Compress drawables for ( `mdpi`, `hdpi`, `xhdpi` )
- [x] Enable proguard
