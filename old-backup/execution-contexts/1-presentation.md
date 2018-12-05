theme: Letters from Sweden, 2
slidenumbers: true

# Scaling Up with Execution Contexts

**Questions?**

Contact your instructors at:

_W:_ http://redelastic.com

_E:_ jason.goodwin@redelastic.com

_E:_ kevin.webber@redelastic.com

---

# Agenda: Better use of your hardware
- What's an EC/Dispatcher
- How to separate by risk
- Assigning work to dispatchers

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1486557959/Screen_Shot_2017-02-08_at_4.44.37_PM_wiof5o.png)
# Fixed Resources
- Default akka threadpool is small
- Blocking/long running tasks should be isolated

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1486629554/Screen_Shot_2017-02-09_at_12.35.42_PM_zx7gmu.png)
# Isolated Pools
- "bulkhead" performance risk into discrete buckets
- (note: don't use fork join for db access - use a thread pool)

---

# Why you don't want to block in FJ
> Using ForkJoinPool.getPoolSize() I discovered that in a program that creates 30,000 tasks (RecursiveAction), the ForkJoinPool executing those tasks uses 700 threads on average (threads counted each time a task is created). The tasks don't do I/O, but pure computation; the only inter-task synchronization is calling ForkJoinTask.join() and accessing AtomicBooleans, i.e. there are no thread-blocking operations.

---

# Compensating
> Compensating: Unless there are already enough live threads, method tryPreBlock() may create or re-activate a spare thread to compensate for blocked joiners until they unblock.
