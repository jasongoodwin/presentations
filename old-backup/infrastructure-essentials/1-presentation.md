theme: Letters from Sweden, 2
slidenumbers: true

# Infrastructure Essentials for Microservices Architecture

**Questions?**

Contact your instructors at:

_W:_ http://redelastic.com

_E:_ jason.goodwin@redelastic.com

_E:_ kevin.webber@redelastic.com

---

# Agenda: Infra Concerns
- Merging
- Continuous Integration
- Continuous Delivery
- Automated Testing Concepts
- Testing Failure Induction with Simoorg

---

# A day in the life of...

---

# Week 0 - Requirements and Design
- Teams working toward a few new features released in a month
- Descriptions of the changes are provided
- Time spent clarifying requirements
- Time spent in design

---

# Week 1 - Code
- Teams work in isolation on branch
- Code not merged
- _No problems noted_

---

# Week 2 - Integration Hell/Merge Hell
- Teams trying to merge
  - Conflicts poorly understood
- Interfaces change unexpectedly between teams

---

# Week 3 - Testing
- Testing starts late due to integration problems
- Many bugs are uncovered
  - Merge errors
  - Misunderstood requirements
- Insufficient automated testing means new bugs arise

---

# Week 4 - Release
- Many people, many hours
- Configuration errors
  - problems were addressed in QA by hand

---

# Week 5 - Bug fixing
- Several bugs are discovered in production
- Development team works on emergency fixes
- Test teams and UAT teams work for many combined hours
- Another release happens
- Hundreds of extra manhours

---

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484333261/Screen_Shot_2017-01-13_at_1.46.51_PM_zavcnz.png)
# Something has to give

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484280618/Screen_Shot_2017-01-12_at_11.09.34_PM_jcw8xr.png)
# Goals - Remove Waste
- late defect detection
- integration hell
- deployment effort
- configuration errors
- push defect detection forward
  - in integration hell
  - environment/config

---

# What is Continous Delivery
- Set of practices to delivery faster, better.
- Automation of _all infrastructure_
  - Eliminate SSH access to all servers

---

# Step 1: Build Automation And Continuous Integration

---

# First steps
- Process - frequent integration and context boundaries
- automated _build_ verifies the code can compile
- automated _unit tests_ verify the code at module level

---

# Fixing Integration Hell Waste
- practice of merging code into mainline frequently
  - at least daily
- notification of build/test failure

---

# Or Else... Merge Hell
- State of codebase
  - Merge causes numerous conflicts
  - Causes numerous hours to be spent resolving
  - Cause of bugs

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484278033/Screen_Shot_2017-01-12_at_10.25.34_PM_kcwrqu.png)
# Context Boundaries and Microservices
- Work planned should account for context boundaries
- Multiple teams working in a context will cause merge hell

---

# "But it's not done yet"
- Should be able to release _ANY TIME_
- But how?

---
# Feature Toggles
- Make it compile but turn it off
  - Feature toggle - if(false){...}
  - Allows commiting and releasing unfinished code

``` scala
if(false){
  doSomethingNew()
}
```

---

# Reviews vs CI

---

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484280618/Screen_Shot_2017-01-12_at_11.09.34_PM_jcw8xr.png)
# Reviews in Continuous Integration
- Review can seem at odds to continuous integration
- False Economy
- Lightweight tool assisted review take 1/5th the time (Cisco)

---

![inline fit](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484325455/nist-review-data.gif)
# Review Size
- Small reviews have higher defect discovery rate
- Higher frequency smaller reviews can fit with CI

---

# Step 2: Test Automation

---

# Deploy to Environment
- Higher level specification validation
  - Integration
  - System
  - Performance


---

# Requirements
- Automation of configuration management
- Automation of delivery of code
- Automation of higher-level tests
- Automation of _infrastructure_

---

# Grow testing to higher levels
- Unit test (CI)
- Integration Test (System Test)
- Performance Test
- User Acceptance Test

---

# Step 3: Deployment automation
- Finally, move code into production

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484364467/Screen_Shot_2017-01-13_at_10.24.36_PM_vobtft.png)
# Collaboration End to End
- Requirements become tests
  - "given scenario when behavior then results"
  - specifications exist on all levels

---

# Test Then Code
- All specifications should exist as test
- Status of each feature visible in pipeline
  - Write the failing test, make it pass

---

![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484367112/Screen_Shot_2017-01-13_at_11.11.03_PM_rel7ke.png)
# Raising test level with pipeline
- CI brings unit test
- CD encompasses environment for higher level testing

---


# Automation makes it cheap
- Elimiate waste

---

# All about automation

---

# Approaches
- Manual
- Promote to environment
- Configuration management
- Immutable Servers

---

# Manual Deployment
- CI produces artifact
- Operations upgrade configuration
- Operations team deploy artifact

---

# Promote to Environment
- CI produces artifact
- Operations upgrade server
- Click to deploy artifact

---

# Configuration Management
- CI produces artifact
- CM runs server upgrade
  - Environment
  - Configuration
  - Code

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484368076/Screen_Shot_2017-01-13_at_11.26.51_PM_pjfmez.png)
# Snowflake (Mutable) Servers
- Snowflake as in each server is unique
- Manual management is hugely variable
- CM tools like Ansible reapply config
- Prone to configuration drift
  - Prod never received change1?
  - What effect does that have?

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484369427/Screen_Shot_2017-01-13_at_11.49.35_PM_loqffg.png)
# Pheonix (Immutable) Servers
- Every server produced from scratch
- No ssh access to any server
- Ensures no configuration drift

---

# Product Owner Hits the Button
- Pending UAT...
- Control for prod promotion on the business owner
  - Specs move from business owner to dev
  - And are finally verified by business owner

---

# Canary Release

---

![fit right](http://4.bp.blogspot.com/_CywCAU4HORs/S-HYtX4vOkI/AAAAAAAAAHw/Hzi5PYZOkrg/s1600/ItWorksOnMyMachine.jpg)
# Uncertaity
- 1/3rd of all Google Postmortems caused by mis-configuration
- 100% of all bugs are unexpected

---

![fit right](http://www.petergrandich.com/wp-content/uploads/2015/07/canary-in-coal-mine-means-something.jpg)
# Canary in the Coal Mine
- Bird was carried into a mine
- It would die if gasses were released
- Acted as warning to workers in the mine

---

![fit right](http://image.slidesharecdn.com/featureflagsandcanaryrelease-161205160518/95/feature-flags-and-canary-release-13-638.jpg?cb=1480953951)
# Canary Release
- Move a small % of traffic to a new version of the application/configuration
- Slowly increase as trust gained
- Helps reduce impact/cost of bugs

---

# Dark Launch

---

# Close Relative to the Canary
- Launch the Code
- Invoke all backend services
- Hide the UI
- Coined by Facebook

---

# Feature Toggles
- Enable canary of the feature
- Display to usergroups (UAT or A/B Testing)
- 100% on once comfortable
- More complex than canary
- Good fit for CI (Decouple Deployment and Release)

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484610660/Screen_Shot_2017-01-16_at_6.41.50_PM_ca2aoq.png)
# Blue/Green Deployments
- Primary and Standby Environments
  - Router moves traffic
- Deploy code to next environment
- Flip traffic to next environment

---

# Benefits

- 0 downtime
- Can verify environment before flipping
- Can use as a canary mechanism

---

# Failure Induction with Simoorg

---

# Failure Induction
- non-functional testing in which a set of failures is induced against a perfectly healthy service
- Netflix's Chaos Monkey
- LinkedIn's Simoorg

---

# Cultural Shift
- Design for failure
- Fallacies of Distributed Computing - "The Network is Reliable"

---

# How we do it
- Induce Failure
- Observe
- Restore

---

# Fatebooks
- Describe how to induce failure
  - Gentle stop
  - Not-so-gentle-stops
  - GCs
  - Network Drops
- Describe how to verify health

---

# QA
