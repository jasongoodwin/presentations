# Kafka

### What to know about Apache Kafka for production tuning

---

# What we will cover

1. The basics of Kafka
2. How Kafka works under the hood
3. What you should know about your server to tune Kafka effectively
4. Critical settings
5. How to monitor Kafka

---

# The Basics of Kafka

---

# Purpose

- Durable retention is at the core of Kafka's DNA
- If consumers fall behind no messages are dropped
- Consumers can then increase capacity to catch up once the burst of traffic is over
- Writers continue to write at full speed

---

# Messages

- The unit of data in Kafka is a message
- A message is an array of bytes
- Messages can have a key
- Keys are also a byte array
- Keys determine which partition a message is written (e.g, for transactions, a key can ensure all operations in a transaction write to the same partition)

---

# Messages

- Messages are stored durably
- Messages are stored in order
- Messages can be read deterministically

---

# Messages

- Messages to Kafka are written in a batch
- A batch is a collection of messages being produced to the same topic and partition
- Correctly sizing batches is a critical part of tuning
- Batches are compressed which increases data transfer and storage at the cost of CPU

---

# Messages

- Additional structure should be imposed on messages
- Can use simplistic systems like JSON or XML
- Most Kafka developers use Avro
- Thrift and Protobuf are two other options
- Schemas should be well defined and stored in a common repository, only then can Kafka be used without coordination issues

--- 

# Topics

- Messages are arranged in topics
- Topics broadly categorize messages, such as "Orders" and "ButtonClicks"

---

# Partitions

- Topics have one or more partitions
- A message is appended to a single partition within a topic
- Partitions are the mechanism used for scale and resilience
- Partition count per topic can be increased but never decreased

---

# Consumers

- Kafka is optimized for writing, so consumers have extra work to do
- Consumers keep track of which messages it has already consumed by keeping track of the offset of messages it has read for each partition
- Offsets can be stored in Kafka (recommended) or Zookeeper (supported but not recommended)

---

# Consumer Groups

- Consumers work as part of a consumer group to read from a topic
- Individual consumers map to individual partitions, this is called ownership
- This is how consumers can horizontally scale

--- 

# Brokers and Clusters

- A single Kafka server is called a broker
- Brokers operate in a cluster
- In a cluster, one broker acts as the cluster controller

---

# Leadership

- A partition is owned by a broker
- This broker is the leader of the partition
- A partition may be assigned to multiple brokers, which results in the partition being replicated
- One broker is always leader, even if a partition is assigned to multiple brokers

---

# Multiple clusters

- Multiple clusters are common in multiple datacenter deployments
- A single Kafka cluster can be configured to run inside a single datacenter
- Kafka has no _location awareness_, so this is all through configuration
- **Mirror Maker** is used to sync messages between clusters