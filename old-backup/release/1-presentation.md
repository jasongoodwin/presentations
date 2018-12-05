theme: Letters from Sweden, 2
slidenumbers: true
autoscale: true

# Docs: Designs and Releases

**Questions?**

Contact your instructors at:

_W:_ http://redelastic.com

_E:_ jason.goodwin@redelastic.com

_E:_ kevin.webber@redelastic.com

---

# Agenda
- Why?
- Design Document Template
- Release Plan Template
- Postmortem

---

# Why are you talking about this
- Saw team rise to Google's quality demands
- "Code Purple" to high stability in ~1.5 years
- Largely led by Google's internal examples

---

# Why Design Docs & Release Planning
- Enable review culture
  - Remove _waste_
  - 30% of Google Postmortems mis-configuration
- Complex services don't fit in one head
- Speed vs Quality trade-offs
  - Defects caught early cost save millions
- "Ego Factor" - critical self review

---

# Design Doc

---

# Design Docs
- Simple changes, simple docs
- Aids in organization-wide communication
  - Ops, support teams, leadership, etc
- History of decisions
  - Aids in performance reviews, promotion
- Mis-understanding caught early

---

# Design Doc - Topics
- Background
- Objectives
- Overview
- In Depth Design
- Performance Consideration
- Potential Impact to Customers
- Test Considerations
- Release Consideration
- Alternatives Considered

---

# Design docs and code reviews
- Greatly aid quality of review time
- Reviews/Commit -> Ticket -> Design Doc
- Works well with gerrit, PRs

---

# Design Doc - Theoretical Scenario
- Growth is causing performance issues
- Scaling out is insufficient due to spikes in traffic
- Decide adding a distributed cache is ideal

^ TODO insert image

---

# Design Doc - Background
- Point to other relevant design documents
- Give information on what led to the decision to build

> High load on product service is causing outages and latency.

---

# Design Doc - Objectives
- Briefly discuss what the desired outcomes of the change are

> Reduce server cost by 10% and reduce latency by 100ms.

---

# Design Doc - Overievw
- High level overview
  - non-technical roles should be able to understand

> Introduce product cache to reduce de/serialization costs

---

# Design Doc - In Depth Design
- Include technical details
  - Implementation details

> Use sharded Redis to cache Product aggregates effeciently.

---

# Design Doc - Performance Considerations
- Careful consideration of negative performance impact

> Redis has single threaded reads so using large number of shards vs servers is required

---

# Design Doc - Possible Impact to Customers
- Call out any potential negative impact the change may have on customers

> Resharding requires an outage. Failovers cause short outages. Retry/failover to Product Service minimizes impact.

---

# Design Doc - Test Consideration
- Describe any special considerations related to testing.
- Describe any gaps and risks

> Testing will not occur on the same scale of servers/sharding so performance gains may not be reflective

---

# Design Doc - Release Considerations
- Call out any gaps and risks
- Describe any special requirements for the release

> Mixing new and old services together will work fine so canary is recommended

---

# Design Doc - Alternatives Considered
- Describe any other solutoins considered
  - Why is the solution in the document the best

> We considered using local LRU cache in web frontend. Too many products exist to achieve performance goals.

---

# Questions before we continue?
^ TODO template link

---

# Release Plan Template

---

# Release Plan - Topics
- Overview
- Planned versions (semver)
- Tickets
- Planned configuration changes
- Test plan
- Release Phases
  - Steps
  - Verification
  - Rollback
- Activity log during/after

---

# Release Plan - Overview
- Describe the release at a high level

> Release of new Product cache (redis) and associated Frontend updates to store and retrieve from cache

---

# Release Plan - Planned Versions
- Semver

> Frontend 0.1.2 -> 0.2.0
> Redis n/a -> 2.3

^TODO is that a current redis version?

---

# Release Plan - Tickets
- Go get the history
- (Tickets link to design docs)

> (Link to Jira ticket list)

---

# Release Plan - Configuration Changes
- Line by Line

> Frontend needs to have the redis configuration. Redis configuration is stored in zookeeper. Release must introduce configuration items.

---

# Release Plan - Test Plan
- Link to test plan
- Test plan can be reviewed as part of release plan review

> Scenario, steps, expected result, actual result, pass/fail, tickets

---

# Release Plan - Release Phases
- Describe daily release events
- Canary plan, dark launch, etc
- Include steps for launch
- Include validation for each phase
- How do we roll back at each step?

---

# Release Plan - Activity Log
- Include times for all events
- Post release, include rollback, etc

---

# Release Planning - Tagging/Release
- Prior to releasing
- RC tags dropped
  - 1.0.0-RC1 -> 1.0.0
- Approved commit tagged
- Move through test again

---

# Review for Confidence
- Shake out any issues
- 30 min phone run down
- Ops, Devs, Testers, Leads review

---

# Postmortem

---

# What is a postmortem?
- Any Significant customer impact noted
- Describes event from start to finish
- Uncovers root-causes, ensures correct solutions
- People involved in desicions and resolution collaborate

---

# Why Postmortems?
- Move accountability to teams
- Understand how and whys
- Need "Blame-free" culture to succeed
- Track resolution 

---

# Postmortem Topics
- Parties Involved
- Event timelines
- Cause
- Impact
- Resolution
- Ensure no recurrence

^ Just describe these - this is self explanatory

---

Who Reads Postmortems?
- Everyone top to bottom
- Doesn't work without blame-free culture
---

# QA

---
