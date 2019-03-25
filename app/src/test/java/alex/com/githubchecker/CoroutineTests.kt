package alex.com.githubchecker

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class CoroutineTests {

    private val errorHandlerExceptions: PublishSubject<Throwable> = PublishSubject.create()

    @Before
    fun setup() {
        RxJavaPlugins.setErrorHandler { throwable ->
            println("Default error handler: ${throwable.javaClass.simpleName}, ${throwable.cause}")
            errorHandlerExceptions.onError(throwable)
        }
    }

    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun retryUntilDone() {
        countUpWithRandomError()
                .retry()    //Retry block catches exception, it goes to default error handler as UndeliverableException
                .toList()
                .test()
                .assertValue { result ->
                    result.size > countUpItems.size
                }
                .assertValue { result ->
                    result.containsAll(countUpItems)
                }
                .assertNoErrors()
    }

    @Test
    fun retryUntilDoneWithKnownSize() {
        countUpWithRandomError() //1->7 1->2 1-> 10
                .retry()
                .takeLast(5)
                .toList()
                .test()
                .assertResult(countUpItems)
                .assertNoErrors()
    }

    @Test
    fun retryUntilDoneWithUnknownSize() {

        countUpWithRandomError()    //50% chance of throwing exception
                .toList()
                .retry()
                .test()
                .assertResult(countUpItems)
    }

    @Test
    fun assertionsWorkInSubscriptions() {

        Observable.create<String> { source ->
            source.onError(IOException())
        }
                .test()
                .assertError(IOException::class.java)
    }

    @Test
    fun resumeWithDefault() {

        Observable.error<Int>(IOException())
                .startWith(countUp())
                .onErrorResumeNext(Function {
                    return@Function Observable.just(999)
                })
                .toList()
                .test()
                .assertNoErrors()
                .assertValue { it ->
                    return@assertValue it.last() == 999
                }
    }

    @Test
    fun errorBlockSwallowsException() {
        Observable.error<Int>(IOException())
                .subscribe({ }, { })
//                .test()   //Also works, test() adds own error handler

        errorHandlerExceptions
                .test()
                .assertNoErrors()
    }

    @Test
    fun noErrorBlockGetException() {
        Observable.error<Int>(IOException())
                .subscribe()

        errorHandlerExceptions
                .test()
                .assertError(OnErrorNotImplementedException::class.java)
    }

    @Test
    fun threadTest1() {

        var didPrint = false

        val testObs = countUp()
                .subscribeOn(Schedulers.io())   //Defaults to calling
                .map { value ->
                    if (!didPrint) {
                        //Map is on io because subscribeOn
                        //Else it defaults to where it was called (main)
                        printThread("Map 1    ")
                        didPrint = true
                    }
                    return@map value
                }
                .toList()
                .flatMap { items ->
                    printThread("Flatmap 1")
                    return@flatMap Observable.fromIterable<Int>(items).toList()
                }
                .observeOn(Schedulers.newThread())
                .flatMap { items ->
                    printThread("Flatmap 2")
                    return@flatMap Observable.fromIterable<Int>(items).toList()
                }
                .observeOn(Schedulers.newThread())
                .flatMap { items ->
                    printThread("Flatmap 3")
                    return@flatMap Observable.fromIterable<Int>(items).toList()
                }
                .observeOn(Schedulers.computation())
                .flatMap { items ->
                    printThread("Flatmap 4")
                    return@flatMap Observable.fromIterable<Int>(items).toList()
                }
                .flatMap { items ->
                    printThread("Flatmap 5")
                    return@flatMap Observable.fromIterable<Int>(items).toList()
                }
                .observeOn(Schedulers.computation())
                .test() //Observes on iO (shown below)
                .await()    //required for async test observers to receive result
                .assertResult(countUpItems)


        printThread("Test Obs ", testObs.lastThread())

        //subscribe on specifies the default thread for the source `observable
        //single use. Additional subscribeOn is IGNORED
        //Subsequent operators will default to subscribeOn unless
        //observeOn affects subsequent observers (including operators)-- one per. same thread persists, successive observers use same thread
        //'ObserveOn Tells THIS observable which scheduler to use when others observe it. Observables post to observers on this thread."
        //multiple observeOn's will overwrite the old one

        //cannot flatmap single to observable

        //toList() waits until observable calls onComplete. it will not naturally complete

        //subscribeOn points to a scheduler, scheduler does action when it executes

        //NOTE99: If you're binding in an Rx subscription, observeOn main!

        //Flowables have buffer

        //Subscribe flow
        //create observable
        //observable does not have observers so it doesnt do anything. frozen in memory
        // -> subscribe (onSubscribe), observer passes observable a dispose function, returns dispose function
        // -> source starts emitting now that it has observers
        // -> it goes down operator chain
        // -> eventually observableSource gives terminal event (onError, onComplete, onSuccess)
        // -> terminal event goes down chain disposing observers

        //      --> If an error occured and no operators handle it, its posted to main error handler, disposes of observer

        //      --> if the subscription was disposed of before the terminal event, the subscription gets cleaned up
        //              every operator disposed
        //emitter stops emitting if nothing is observing

        //MENTION
        //Dont repeat computation in separate subscriptions, move common observers to the same chain
        //terminate computation early
        //use simpler observables, use convenience functions Observable.create, etc.

        //how does subscription tie shit together??
        //subscribe returns disposable... disposable is a block
        //Observables may live forever (with normal object lifecycle) or will dispose of themselves when they complete/error
        //Disposing of Observers: "heres a kill switch. for me"
        //Observables have normal object lifecycle
    }

    @Test
    fun threadTest2() {

        val trianglesObservable = Observable.fromIterable(1..100)   //  1
                .map { multiplyByTwo(it) }                                 //  2
                    .observeOn(Schedulers.computation())                   //  3
                .flatMap { numberToCircle((it)) }                          //  4
                    .observeOn(Schedulers.computation())                   //  5
                .flatMap( { circleToSquare((it)) }, 5)      //  6
                    .observeOn(Schedulers.io())                            //  7
                .flatMap { squareToTriangle((it)) }                        //  8
                .flatMap { triangleToSphere((it)) }                        //  9
                    .subscribeOn(AndroidSchedulers.mainThread())           // 10
                    .subscribeOn(Schedulers.io())                          // 11

        val sub1 = trianglesObservable.subscribe()                          // 13
        Thread.sleep(1000)                                           // 14
        val sub2 = trianglesObservable.subscribe()                         // 15



    }

    fun multiplyByTwo(number: Int): Int {
        printThread("Multiply by Two")
        return number * 2
    }

    fun numberToCircle(number: Int): Observable<Circle> {
        printThread("Number to Circle")
        return Observable.just(Circle())
    }

    fun circleToSquare(item: Circle): Observable<Square> {
        printThread("Circle to Square")
        return Observable.just(Square())
    }

    fun squareToTriangle(item: Square): Observable<Triangle> {
        printThread("Square to Triangle")
        return Observable.just(Triangle())
    }

    fun triangleToSphere(item: Triangle): Observable<Sphere> {
        printThread("Square to Sphere")
        return Observable.just(Sphere())
    }

    class Square
    class Triangle
    class Circle
    class Sphere

    @Test
    public fun emitterStopsEmittingIfDisposed() {
//        var didchuck = false
//        val countup = Observable.fromIterable(0..10)
//
//        val sub = countup
//                .map {
//                    if (it > 7 && !didchuck) {
//                        didchuck = true
//                        throw IOException("herp derp")
//                    }
//                    return@map it
//                }
//
//                .subscribe { println("got ${it}") }

        //emit shit
        //operator flips a bitch
        //subscription terminates before emitter done

        val scheduler = TestScheduler()
        val observable = Observable.interval(1, TimeUnit.SECONDS, scheduler)
        val testObserver = observable.test()

        testObserver.assertNoValues()

        scheduler.advanceTimeBy(1, SECONDS)
        testObserver.assertValues(0L)

        scheduler.advanceTimeBy(1, SECONDS)
        testObserver.assertValues(0L, 1L)

//        testObserver.dispose() // Dispose the connection.
//
//        scheduler.advanceTimeBy(100, SECONDS)
//        testObserver.assertValues(0L, 1L)

//        Thread.sleep(2000)    no effecttttt

        val testObserver2 = observable.test()

        testObserver2.assertNoValues()
        scheduler.advanceTimeBy(999, MILLISECONDS)
        testObserver2.assertNoValues()

        scheduler.advanceTimeBy(1, MILLISECONDS)
        testObserver2.assertValues(0L)

        //when does observable die? does it just live forever shitting up memory? what if it emits 1000x items per sec?
        //untill they terminal event or are garbage collected
        //Observable does not run at all unless something subscribes to it.
    }

    @Test
    fun doubleSubscribe() {
        val observable = Observable.fromIterable(1..100)
        val observer = observable.take(50)
                .toList()
                .subscribe { a, b ->
                    println("got ${a}")

                    observable.take(40)
                            .subscribe { println("got ${it}") }
                }

        //observable re-emits for each subscriber
    }

    fun doObservablesDispose() {
        Observable.fromPublisher<Int> { source ->
            (0..10).forEach {
                // source.
            }
        }
    }

    @Test
    fun typeTest() {

        Observable.fromIterable(1..10)  // Observable
                .map { return@map it.toString() } // Operator #1
                .map { return@map it.toFloat() } // Operator #2
                .map { return@map it.toInt() } // Operator #3
                .toList() // Operator #4
                .test() // Creates Test Observer that invokes subscribe()
                .assertResult((1..10).toList())


        (1..10).map {
            it.toString()
        }.map {
            it.toFloat()
        }.map {
            it.toInt()
        }.toList()

        val o1 = Observable.fromIterable(1..10)  // Observable
        val o2 = o1.map { return@map it.toString() } // Operator #1
        val o3 = o2.map { return@map it.toFloat() } // Operator #2
        val o4 = o3.map { return@map it.toInt() } // Operator #3
        val o5 = o4.toList() // Operator #4
        val testObserver = o5.test() // Creates Test Observer that invokes subscribe()
        testObserver.assertResult((1..10).toList())
    }

    private val countUpItems = (1..5).toList()
    private fun countUpWithRandomError(): Observable<Int> {
        return Observable.create<Int> { source ->
            countUpItems.forEach { num ->
                if (Math.random() > 0.5) {
                    source.onNext(num)
                } else {
                    if (!source.isDisposed) {
                        source.onError(IOException())
                    }
                }
            }
            source.onComplete()
        }
    }

    private fun countUp(): Observable<Int> {
        return Observable.fromIterable(countUpItems)
    }

    val tags = ConcurrentHashMap<String, Boolean>()
    private fun printThread(tag: String, thread: Thread = Thread.currentThread(), doOnce: Boolean = true) {

        synchronized(tags) {
            if (doOnce && tags.getOrDefault(tag, false)) return

            var printTag = tag
            while (printTag.length < 25) {
                printTag += " "
            }

            println("$printTag Thread ID: ${thread.id} - Name:${thread.name}")
            tags[tag] = true
        }
    }



    @Test
    fun synctest() {
        runBlocking {
            val set = HashSet<Int>().apply {
                for (i in 69..420) {
                    add(i)
                }
            }

            GlobalScope.launch {
                set.forEach {
                    print(it)
                    delay(10)
                }
            }

            GlobalScope.launch {
                delay(420)
                set.clear()
            }

            delay(4200)
        }
    }

    @Test
    fun synctestRx() {
        runBlocking {
            val set = HashSet<Int>().apply {
                for (i in 69..420) {
                    add(i)
                }
            }

            Observable.fromIterable(set).map {
                GlobalScope.launch {
                    delay(10)
                    System.out.println(it)
                }
            }

            GlobalScope.launch {
                delay(420)
                set.clear()
            }

            delay(4200)
        }
    }
}