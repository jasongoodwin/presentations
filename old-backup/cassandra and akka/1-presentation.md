theme: Letters from Sweden, 2
slidenumbers: true

# Dynamo Things (Cassandra and Akka Cluster)

---

**Questions?**

Contact your instructors at:

_W:_ http://redelastic.com

_E:_ jason.goodwin@redelastic.com

_E:_ kevin.webber@redelastic.com

---

# Just who is this Cassanda Person Anyway?
- Based on dynamo paper from amazon
- Eventually consistent store
- Can be used with stronger or weaker consistency (CP or AP)

---

# What about Akka Cluster
- Akka Cluster is also heavily influenced by Dynamo
> "[Akka Cluster] implements a Dynamo-style system using gossip protocols, automatic failure detection, automatic partitioning, handoff, and cluster rebalancing."

---

# Why the Talk?
- Critical to understand how the components work
- Can't do analysis if you don't know what they are doing!

---

# Gossip, CAP, all that jazz

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1486539891/Screen_Shot_2017-02-08_at_11.43.28_AM_guwus2.png)
# Gossip Protocol and Failure Detection
- Nodes healthcheck their neighbours (+1, +2)
- Nodes gossip any cluster state changes (joining, leaving, unavailability)
- Once concensus is achieved, cluster is stable

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1486540500/Screen_Shot_2017-02-08_at_11.53.57_AM_qz8era.png)
# Clusters and CAP
- *C*onsistency
- *A*vailability
- *P*artition tolerance
- For timebound request, the choice is between C and A

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1486540447/Screen_Shot_2017-02-08_at_11.52.43_AM_qacnm4.png)
# CP - Prefer Consistency
- Require either:
  - ALL nodes to agree, or;
  - One be primary, others be replicas
- always up to date
- more outages

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1486540615/Screen_Shot_2017-02-08_at_11.55.53_AM_nau89d.png)
# AP - Eventual Consistency
- Sacrifices guarantees
  - May read old data
- Nodes can crash without loss of availability 

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1486540930/Screen_Shot_2017-02-08_at_12.01.02_PM_mrm27v.png)
# Is CAP Possible?
- Many datastores claim consistency and availability
- It's a sliding scale - can 'tune' consistency
- Good tradeoff: replicate 3, read QUORUM, write QUORUM

---

# Stuff that's easy
- Columnar denormalized datastore (I KNOW TABLES!)
- Fast reads, writes
- Near-horizontal-scalability
- Has a "sql like" query language called "cql"

---

# Stuff that's not
- Shards and replicates data
- Requires tables to be built around queries
  - not the other way around

---

# Primary Key
- Tables are built with a Primary Key

```sql 
CREATE TABLE emp(
   emp_id int PRIMARY KEY,
   emp_name text,
   emp_city text,
   emp_sal varint,
   emp_phone varint
   );
```

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1486484007/Screen_Shot_2017-02-07_at_8.12.02_PM_hosgkl.png)
# Primary Key for Sharding (Reads and Writes)
- Primary key is used to determine the _shard_ that data is on

```scala 
emp_id.hashCode % numShards == 4
```

---

# Co-Ordinating Node
- Requests go to a random node
- That node becomes the _coordinator_ for that request
- Will proxy the data to the correct node(s)
- Fast, small (LZ4 compressed binary protocol)

---

# The Most Important Thing in this Deck For Cassandra:
- Replication Factor
- Read Consistency
- Write Consistency

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1486484569/Screen_Shot_2017-02-07_at_8.21.44_PM_tzs8s3.png)
# Replication Factor
- Keyspaces are created with a _replication factor_
- Describes how many replicas each shard should exist on
- It's a target - does not determine availability!!

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1486539500/Screen_Shot_2017-02-08_at_11.36.46_AM_hcqo6y.png)
# Replication Factor and Hinted Handoffs
- If a node is down, the coordinating node will store a "hint" in a table
- When the node is restored, it will send the updates to the node.

---

# Write Consistency
- Write consistency is described as (1, 2, QUORUM, ALL, etc)
- Determine how many writes must be made to reply with success
  - Replication Factor 3 + QUORUM means 2 must ack to be successful write
- Evaluate each use case (tune based on CAP needs)

---

# Read Consistency
- Write consistency is described as (1, 2, QUORUM, ALL, etc)
- Determine how many nodes must agree to reply with a success
  - Replication Factor 3 + QUORUM means 2 must ack to be successful write
- Evaluate each use case (tune based on CAP needs)

---

# Building Tables for Queries
- Ensure all queries are on a partition key
- It's fine to duplicate data for reading

---

# Odering and Queries - Magic of Limit!
- To see the most recent posts a user made,
  - primary key on user and time
  - insert ordered
  - select n!

---

# The Data
- User ID is *partition key*
- posttime is *clustering key* (ordering)

```sql 
CREATE TABLE postsbyuser (
  userid bigint,
  posttime timestamp,
  postid uuid,
  postcontent text,
  PRIMARY KEY ((userid), posttime)
) WITH CLUSTERING ORDER BY (posttime DESC);
```

---

# The Query
- Because of the ordering, you just limit the results!

```sql 
select * from postsbyuser where userid = "jason" limit 10
```

---

# Anti-patterns
- It's good at what it's good at.
- If you don't have a partition key, it will fall apart.

```sql 
select * from postsbyuser limit 10  --BAD!
```

---

# Anti-patterns
- Deletes leave 'tombstones'
- Tombstones are read in queries, and then filtered out.
- Removed at repair time
- Will throw errors, ruin performance

---

# Distributed Locks and Leases
- Cassandra has some atomicity
- Careful with consistency levels - (replication 2, read/write ALL)

```sql 
insert into locktable values("i'm a lock") IF NOT EXISTS TTL 5
```

---

# Adding/Removing Nodes
- Adding and removing nodes requires `nodetool cleanup`
- Pause the world... Try it out in DIT first :)
- NOT automated

---

# Moral of the Story
- Understand the technology
- Use it for what it's good at
- Don't use it for what it's not
- RDBMS still has uses

---

# Akka Cluster

---

# More of the same
- Clusters come together and function identically
  - Gossip cluster state
  - Failure detection of neighbours
  - Coordinating nodes and sharding

---

# But it's application state, not data
- Actors are distributed through the cluster
- Coordinating nodes make requests to the correct shard

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1486484007/Screen_Shot_2017-02-07_at_8.12.02_PM_hosgkl.png)
# Walking through a request
- Request goes to any node
- Node knows where actor is based on shard id (just like cassandra)
- Request is passed to that actor on remote machine
- Response is passed back to requester

---

# Adding/Removing nodes
- Causes shard regions to reshard data (move actors to other machines)
- Be careful of remember-entities

---

# Singleton Actor
- A single actor exists on one machine
- In failure cases, the actor is migrated to another machine
  - It's the oldest node with a role (your reader and writer are on the same node)

---

# Persistent Actors for Event Sourcing
- To be able to migrate data, actors must be backed by an event log
- To recover an entity, persistence will wind from the last snapshot
  - then apply events ontop
- Use snapshots in high-traffic actors

---

# ES and At Least Once Delivery
- Can place intention in the actor, and close that with acknoledgement
- Replays will ensure any undelivered messages will be sent
- Turn remember-entities on!

---

# CQRS
- Can make a read-only actor from rolling the events
- Can use experimental persistence query to produce a view
- Can just pipe the events to another store
- Or don't use CQRS (low availability)

---

# Q's?
