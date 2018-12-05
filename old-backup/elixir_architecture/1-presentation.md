theme: Sketchnote, 7
build-lists: false
slidenumbers: true
autoscale: true

---

# Functional Programming Paradigms In Elixir
- Objectives: 
  - Understand functional programming a bit better
  - Categorize and understand approaches
  - See which approaches fit which problems
  - Map concepts and approaches from Scala/Haskell to elixir

---

# Jason Goodwin
- Scala guy
- Wrote books on FP, Actor Model, Distributed Systems (O'Reilly, Packt)
- Built some big scale things (mDialog, bought by Google)

^ akka is modelled after erlang

---

# We're Hiring
- Elixir/OTP, Scala, Akka, Kafka, Event Sourcing, CQRS...
- jason@funnelcloud.io


![inline](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1511455126/Screen_Shot_2017-11-23_at_11.38.27_AM_mu7e2n.png)

---

# #1 Challenge In Programming

---

# Complexity
- Software gets more complex over time
- Functional programming can help us tackle the complexity

---

# Level-Set: Object Oriented
- brings data and logic together

```
class Lamp {
  isOn = false                       // Data/state 
  def flickSwitch() { isOn = !isOn } // Behavior
}
```

^ A class should have related behavior and logic
^ Objects interact with each other encapsulating their state 
^ (tell don't ask)

---

# Level-Set: Functional Programming 
- separates data and logic

```
def flick_switch(lamp), do: {lamp | !lamp.is_on}

lamp = %Lamp{is_on: false}
new_lamp = flick_switch(lamp)
```

^ functions act on data
^ chain together functions to build complex applications

---

# Tools exist to help us manage the complexity!
![](https://khareed.pk/wp-content/uploads/2017/08/defending-complexity-share-1024x538.jpg)

---


# Algebraic Data Types
- A composite type formed by combining other types
- 'Algebraic' because it is a Sum of either `Type a | Type b`
- Can be used to express effects like nil and failure
- Can also describe a Product (eg tuples)

^ So remember these two categories of ADTs
^ They look different between scala and elixir
^ but express the same ideas
^ I'm not going to say "oh you should use this library or that library - we are focusing on core concepts here"

---

# Tagged Tuples
- A result:
  - `{:ok,    result}`
  - `{:error, message}`
  -   ^ tagged!

---

# Variants
- `Try[T]`
  - `Success[T]`
  - `Failure[Exception]`

---

# List
![inline](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1511451542/Screen_Shot_2017-11-23_at_10.06.33_AM_qgvyix.png)

---

# List

```
list match {
  case head :: tail => println(s"cons cell with $head")
  case Nil => println("end of list!")
}

case list do
  [head | tail] -> IO.puts "cons cell with #{inspect head}"
  [] -> IO.puts "end of list!"
end
```

^ Even though these are expressed differently, a List still consists of a cons cell or a nil/empty list in both languages

---

# Maybe/Option
![inline](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1511451899/Screen_Shot_2017-11-23_at_10.06.25_AM_o3fzsg.png)

^ Now this might look a bit strange to you if you're coming from ruby
^ Languages like java8, scala and haskell have this construct in their core
^ the idea is to create safety and eliminate null pointer exceptions 
^ it moves the null effect to a type, and makes it explicit
^ you never actually return nil so there are no making mistakes

---

# Maybe/Option

```
Option(maybeNullFun()) match {
  case Some(x) => println(s"got $x")
  case None => println("nothing!")
}

case maybeNullFun() do
  {:just, x} -> IO.puts "got #{inspect x}"
  {:none} -> IO.puts "nothing!"
end
```

^ Instead of returning nil you could put the result in a record
^ It becomes explicit that it can return a null

---

# Try/Result
![inline](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1511451958/Screen_Shot_2017-11-23_at_10.06.17_AM_erqhs1.png)

^

---

# Try/Result

```
Try(dangerousFun()) match {
  case Success(x) => println(s"successfully got $x")
  case Failure(t: Throwable) => println(s"failed with $t")
}

case dangerousFun() do
  {:ok, x} -> IO.puts "successfully got #{inspect x}"
  {:error, msg} -> IO.puts "failed with #{msg}"
end
```

^ This is a more common record in elixir
^ in elixir we can represent a result in the same way, and we do this all the time

---

# Data Pipelines are Assembly Lines
- input |> validate |> transform |> respond
 
![](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1511452443/Screen_Shot_2017-11-23_at_10.53.25_AM_xhuses.png)

---

# Data Pipelines are Assembly Lines
- input |> validate! |> transform! |> respond
 
![](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1511452443/Screen_Shot_2017-11-23_at_10.53.25_AM_xhuses.png)

---

# Pipes
- Assumes value in/value out
- Good for pure functions, or if throwing
- Doesn't work well with our tagged tuples
`input |> next_fun |> another_fun`

---

# Example of Pipe w/ ADTs

```
path
|> File.read()
|> read_chunks()
|> wrap()

defp read_chunks({:ok, binary}) do
  {:ok, :beam_lib.chunks(binary, :abstract_code)}
end
defp read_chunks(error), do: error

defp wrap({:ok, data}) do
  {:ok, wrap(data)}
end
defp wrap(error), do: error
```

---

# Railway Oriented Programming
- Offers a solution to the previous example
- Expands macros to handle errors and pass them along

![inline](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1511457275/Screen_Shot_2017-11-23_at_12.14.09_PM_yuztkz.png)

---

# Railway Oriented Programming
- Strings together a bunch of operations
- If an error is encountered, processing "switches tracks" and passes error along.

![inline](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1511457348/Screen_Shot_2017-11-23_at_12.14.01_PM_jyutks.png)

---

# Railway Oriented Programming

```
1511410370
  >>> DateTime.from_unix             # {:ok, #DateTime<...>}
  >>> Timex.format("{ISO:Extended}") # {:ok, "2016-02-29T12:30:30.120+00:00"}

"bad"
  >>> DateTime.from_iso8601          # {:error, :invalid_format}
  >>> Whatever.function              # ^^^ passed along
```

---

# Problem with Pipe and RoP
- Simple, easy to use and understand but...
- Limited to certain contracts
- Poor composability!!

---

# Composability: Consider...
- Building a bank account status page:
  - Get user account from user id
  - Get checking account
  - Get savings account

![fit align left](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1511457824/test1_z6znd9.png)

^ Go get one thing, then get two more things with the result, then return all three of the results

---

# Monads

> Monads have also been explained with a physical metaphor as assembly lines, where a conveyor belt transports data between functional units that transform it one step at a time.
-- Wikipedia

^ A category of a type
^ "higher kinded type"
^ User class is to User object, as Monad is to Try, Maybe, etc

---

# Monads in Pictures
- Google "monads in pictures"
http://adit.io/posts/2013-04-17-functors,_applicatives,_and_monads_in_pictures.html

---

# Monads

- informally: category of a type which has "bind" aka "flatmap"
- flatmap can chain together a bunch of monads
- Like "RoP," will pass any "bad" result along

---

# Maybe.map

- `maybe.map(x => x+3)`

![inline](http://adit.io/imgs/functors/fmap_just.png)

![inline](http://adit.io/imgs/functors/fmap_nothing.png)

---

# What if map returns a wrapped value??

- `maybeNumber.map(x => maybeHalf(x))`

![inline](http://adit.io/imgs/functors/half_ouch.png)

---

# Flatmap in Monads
- Monads apply a function that returns a wrapped value to a wrapped value. 
- maybeNumber.flatMap(x => maybeHalf(x)).flatMap(x => maybeHalf(x))

![inline](http://adit.io/imgs/functors/monad_just.png)

---

# Remember our earlier example?

![fit align left](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1511457824/test1_z6znd9.png)

---

# (horrible) Try Monad in Scala

```
getUser(username).
  flatMap(user => 
    getCheckingAccount(user.id).
      flatMap(checkings => 
        getSavingsAccount(user.id).
          map(savings => 
            makeOrder(user, checkings, savings))))

//Try[(User, Acct, Acct)]
```

^ We take user and flatmap will "unwrap it"
^ we can get a "wrapped" checkings account with the unwrapped user and put it off to the side for later
^ then use the user to get the savings account
^ then we can call make order which gives us another try
^ flat map will handle all unwrapping all of the containrs so we end up with our result in a one container

---

# Try in Scala rewritten with Comprehension

```
for {
  user      <- getUser(username)
  checkings <- getCheckingAccount(user.id)
  savings   <- getSavingsAccount(user.id)
} yield makeOrder(user, checkings, savings) 

//Try[Order]
```

---

# 'WITH' - NO 'M' WORD!
- Looks very similar to scala comprehensions of monads!

```
with {:ok, user}  <- get_user(username),
     {:ok, checkings} <- get_checking_account(user.id),
     {:ok, savings}   <- get_savings_account(user.id)
     do 
       {:ok, make_order(user, checkings, savings)}
     else 
       {:error, _} -> {:error, "failed to build order!"}
     end
```

^ handles :ok/:error in processing pipeline
^ passes errors through (good parts of RoP)
^ handles multiple response structures if needed
^ composability!

--- 

# Asynchronous Composition!
- account example expects synchronous responses

```
with 
     {:ok, user}      <- get_user(username)
     checking_task    <- Task.async(fn -> get_checking_account(user.id) end)
     savings_task     <- Task.async(fn -> get_savings_account(user.id) end)
     {:ok, checkings} <- Task.await checkings_task,
     {:ok, savings}   <- Task.await savings_task,
     do: {:ok, make_order(user, checkings, savings)}
```

---

# COME WORK WITH ME!
- jason@funnelcloud.io

---





# A Linked List is an ADT
```
List("ok") match {
  case x :: tail => println("cons cell")
  case Nil => println("empty list")
}
```


# Equivalent in Elixir

``` elixir
def iterate([head | tail]), do: iterate tail
def iterate([]), do: # done!

case Repository.insert record do
  {:ok, result} -> # happy path
  {:error, msg} -> # error path
end
```

^ Pattern matching is a core language feature in all functional languages
^ Algebraic data types is just a term used to describe categories of types
^ In elixir this is usually represented as a tuple of :ok and :error
^ You could think of a list also being either a cons cell with a value and a reference to another list, or an empty list.
^ You could think of nil vs not nil as categories as well
^ Elixir doesn't have ADTs, but we can envision the same semantics
^ example ^ for example DateTime.from_unix(4) DateTime.from_iso8601("b")

# ADT Example
- Example of an ADT implemented in Elixir with `algae``
```
defmodule Maybe do
  defsum do
    defdata Nothing :: none()
    defdata Just    :: any()
  end
end

Maybe.new()
#=> %Maybe.Nothing{}
```

^ Some people have tried to introduce ADTs into elixir
^ It's not impossible, but just not idiomatic

# Level Set: Immutability

``` elixir
x = 1
my_fn = fn() -> x end
x = 2
1 = my_fn.() # matches!
```
^ ^^ my_fn.() returns 1!
^ Elixir supports name rebinding but the original variable x is not mutated
^ This is a bit less important - going to talk a lot more about the ADTs or "categories" of things and how to compose them together in elixir

# Problem 1: Single Possibly Null Value

> I call it my billion-dollar mistake. [...] I was designing the first comprehensive type system for references in an object-oriented language. My goal was to ensure that all use of references should be absolutely safe, with checking performed automatically by the compiler. But I couldn’t resist the temptation to put in a null reference, simply because it was so easy to implement. This has led to innumerable errors  which have probably caused a billion dollars of pain and damage in the last forty years.
– Tony Hoare, Inventor of ALGOL W.

^ You guys are used to seeing :ok and :error atoms so I'm going to demonstrate most of this processing with nil/not nil to get you to think more laterally about processing data
^ for example DateTime.from_unix(4) DateTime.from_iso8601("b")

# Option/Maybe and ADTs
- draw a picture here

# Option/Maybe in Scala

``` scala
val x = Option(maybeNull) // produces either Some(value) or None
  .getOrElse(default)     // returns the value in Some or default if None
```
- method declaration explicit about null return type
- expresses the null _effect_ as a type for safety

# Simple "Maybe" Idiom in Elixir

``` elixir
x = maybe_nil || default
```
- Rubyism - `"bob"` is not equivalent to `true` in most languages
- Works in elixir because nil is falsy and references are truthy

# Problem 2: Explicit Some/None Return Value from One Function
- or Success/Failure (:ok/:error)
- or whatever categories of result!

# Scala Option Result Type

```
def getUserFromDatabase(username: String): Option[User] = ???

getUserFromDatabase(username) match {
  case Some(user) => user
  case None       => User(username)
}
```
- Explicit null possibility expressed in type signature
^ Probably looks familiar to you if you're working with elixir
^ Except you know with certainty that this can return a null

# Getting there With Elixir
- Earlier example is not enough `x = maybe_nil || default`
- Want to express "categories" of responses

# Simple Algebraic Data Types
- Express "categories" of types/responses
- eg. Some or None, Success or Failure

# How do we get there with Elixir?
- In elixir, common idiom is to use a tuple
  - try/success/failure:
  - `{:ok, result}`
  - `{:error, error_message}`
  - maybe/some/none:
  - `{:ok, greater_than_five_message}`
  - `{:none}`
  
  ^ I've already been hinting at this so this shouldn't be much of a suprise
  ^ But we're going to take this example a lot farther

# In Use

```
def get_user_from_database(username), do: ...

case get_user_from_database("bob") do
  {:ok, user} -> user
  {:none}     -> %User{name: "bob"}
end
```
- You see this a lot with failures instead of raising exceptions 
- `{:ok, result}` or `{:error, message}`

^ May be better than returning nil because it's very explicit that it can be null.
^ It's like raising vs responding with :ok

# The problem with that
- Lots of cognitive load
- How do you chain together a bunch of operations?
```
input 
|> something_that_can_fail 
|> something_else_that_can_fail
|> oh_snap
```

# Problem 3: Chaining Multiple Operations
- Nothing demonstrated has been expressive enough...
- We want to be able to chain, but gracefully handle multiple categories
``` elixir
res = input 
|> something_that_can_fail 
|> something_else_that_can_fail

case res do
  {:ok, result} -> ...
  {:error, msg} -> ...
  {:none}       -> ...
end
```

# "Railway Oriented Programming"
- Appropriated term...

![pic](http://www.zohaib.me/content/images/2015/03/Screenshot-2015-03-23-01-12-31.png)

# "Railway Oriented Programming"
- Once you "switch tracks" you don't go back
![pic](http://www.zohaib.me/content/images/2015/03/Screenshot-2015-03-23-01-12-31.png)

# Depends on those Algebraic Data Types
```
  defmacro left >>> right do
    quote do
      (fn ->
        case unquote(left) do
          {:ok, x} -> x |> unquote(right)
          {:error, _} = expr -> expr
        end
      end).()
    end
  end
    
```

# Railway Oriented Example

``` elixir
@doc "will return {:ok, notification} or {:error, error_msg}"
def email_notification(notification_id) do
  get_notification_details notification_id
  >>> validate_notification
  >>> send_email
  >>> mark_sent
end
```
- Better than pipes for error handling!

# Better... but...

# Where it falls over
- Cannot compose!
- To make `Order`, Amazon needs:
  - `User` 
  - `Shopping Cart`
  - `Shipping Address`

^ can you see the problem with this?

# Problem 4: Chain and Compose Results that can Fail
- Often can't just pipe output from one method into another
```
{:ok, user} = getUser(username)
{:ok, cart} = getCart(user.id)
{:ok, address} = getAddress(user.id)
{:ok, order} = createOrder(user, cart, address)
```

# Don't Fear the Monad...

> Monads have also been explained with a physical metaphor as assembly lines, where a conveyor belt transports data between functional units that transform it one step at a time.
-- Wikipedia

# Scala: Try Monad w/ Comprehension
- Looks like railway oriented programming
- But has more flexibility to compose results of operations

``` scala
def order(username: String): Try[Order] = 
  for {
    user <- getUser(username)
    cart <- getCart(user.id)
    address <- maybeGetAddress(user.id)
  } yield createOrder(user, card, address)
```
- returns `Success(order)` or `Failure(exception)`

# And Elixir...

```
@doc "returns {:ok, order} or {:error, msg}"
def create_order(username) do
  with {:ok, user}    <- get_user(username),
       {:ok, cart}    <- get_cart(user.id),
       {:ok, address} <- get_address(user.id),
       do: create_order(user, cart, address)
end
```

^ Let's look at what we did here
^ We were able to bring the "railway oriented programming" error handling
^ without any libraries!
^ We were able to compose together the results of multiple function calls in a way you can't with pipe or ROP
^ We were able to be explicit about the success or failure types without any exception effects
^ and we did it with barely any code

# GenServer w/ Supervisor example:
- LET IT CRASH!
- still explicit where effects are by (!) convention
```
user = get_user! username
cart = get_cart! user.id
address = get_address! user.id
create_order!(user, cart, address)
```

^ Remember the rule of least power
^ If you're building complex systems with many processes
^ There is a huge overhead to that, to learning, maintaining, and ensuring supervision is correct
^ GenServer is powerful and you don't need to worry as much about error handling so this code might be fine

# Problem 5: Consolidating Results Asynchronously
- A telco customer wants to see their phone, tv, and cell bill in one.

TODO draw picture

# Solution 1 - Tasks

