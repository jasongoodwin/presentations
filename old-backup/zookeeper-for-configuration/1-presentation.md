theme: Letters from Sweden, 2
slidenumbers: true

# Productionizing Basics - Code Handling

**Questions?**

Contact your instructors at:

_W:_ http://redelastic.com

_E:_ jason.goodwin@redelastic.com

_E:_ kevin.webber@redelastic.com

---

# Agenda: Zookeeper: More Than Orchestration! 
- Basics
- Service Discovery
- Leader Election 
- Application.conf vs Zookeeper 

---

![fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1483567588/Screen_Shot_2017-01-04_at_5.01.45_PM_nct6bl.png)
# The Problem

---

# Solutions for Configuration
- Files
  - Need to edit and restart
- Database
  - Need to constantly read
- Pub/Sub 
  - Reliability gauruntees? 

---

# Zookeeper Basics

---

# Datamodel
- Hierarchal K/V store like a file system.
- Nodes called 'znode'  
  - /parent-znode/child-node/child-child-znode
- Nodes can have some or no data in them
  - /parent-znode|my-data

---

# Reading data 
- Read and writes are atomic and linearizable (ordered)
- Can apply a "watch" when data is read.
  - zkClient.getData(path = "/parent/child", watch = true)

---

# Zookeeper Watches

- If data changes after read w/ watch flag true
  - Event is sent
  - Watch is removed
- Client should re-read applying the watch again
- Ensure you are up to date without constantly reading

---

# Zookeeper Ephemeral Nodes 

- will be removed if session ends
- often used with zookeeper 'sequence naming'
  - /services/myservice/runninginstance-0000000001

---

# Zookeeper In Practice

---

# Curator
- Java zk Library that has higher level of abstraction
- Common Recipes built in
  - Group Membership
  - Leader Election
  - Locks
  - Barriers
  - Counters
  - Caches

---

# Service Discovery (Group Membership)

- Group members publish their port and ip
  - /services/backend/001 | host:port
- Consumers watch for any change to group members
  - watch children on /services/backend/*
- Consumers always know who is online
- Allows changes in composition without loadbalancers/elastic ips

---

![right fit](http://techblog.outbrain.com/wp-content/uploads/2011/07/Zookeeper-Leader-Election.jpg)
# Leader Election
- Need for a co-ordinating node?
  - If a system has state
  - If the state has consistency
  - Hold a Job Queue in a cluster

---

# Productionizing Cluster Configuration

---

![right fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1483572149/Screen_Shot_2017-01-04_at_6.21.48_PM_xcr7dk.png)
# application.conf or Zookeeper? 
- global configuration: company-name=TheCompany
- environment specific: base-url=http://staging.thecompany.com
- endpoints: my-service=10.0.3.5:9090
- behavior: ftp-password=a3utren8y

---

![right fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1483631299/Screen_Shot_2017-01-05_at_10.47.36_AM_mhzfek.png)
# One Console to Rule Them All
- Management Console read/write config
- Push config changes to running applications
- Restart actors on notification or update cache

---

# Noon Usecase
- Servers running
- We want to update the log level in the running app

---

![fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1486389596/Screen_Shot_2017-02-06_at_5.57.38_PM_rex9sd.png)
# Naive Solution

---

![fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1486390561/Screen_Shot_2017-02-06_at_6.02.13_PM_hvfhlc.png)
# Improved Solution

---

# Curator's NodeCache
- Always read up-to-date value from zk
- Can also put notification on the node
- Eases dealing with Zookeeper 

---

# How it's done
``` scala
      val curator = CuratorFrameworkFactory.newClient(
        "localhost:2181",
        new ExponentialBackoffRetry(1000, 3)
      )
      curator.start()

      val loglevelNode = new NodeCache(curator, "/wallet/loglevel")
      loglevelNode.getListenable.addListener(new NodeCacheListener {
        override def nodeChanged() = {
          println("watch triggered. (curator will re-apply watch). Value is " + loglevelNode.getCurrentData)
          changeLogLevel()
        }
      })
      loglevelNode.start()
      changeLogLevel()
```

---

# Building a Frontend For zookeeper
- Nodecache to read from nodes
- Write to nodes from the console

---

# Distributed Atomic Integer
- Zk's consistency guarantees make it reasonable for distributed atomic ints

---

```
val atomicInteger = new DistributedAtomicInteger(
        curator,
        "/my-atomic-integer",
        new ExponentialBackoffRetry(1000, 3),
        new PromotedToLock("/my-atomic-integer-log")
      )

      val nextInt = atomicInteger.increment()
      if(nextInt.succeeded()){
        val myInt = nextInt.postValue()
        //do stuff
      }
```
