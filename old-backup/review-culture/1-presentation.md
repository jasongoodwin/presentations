theme: Letters from Sweden, 2
slidenumbers: true

# Shift Left with Review Culture

**Questions?**

Contact me at:

_W:_ http://redelastic.com

_E:_ jason.goodwin@redelastic.com

---

# Kevin Webber. CEO, RedElastic

---

# Jason Goodwin. CTO, RedElastic

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1487689406/Screen_Shot_2017-02-21_at_10.01.40_AM_jvhfn6.png)
# RedElastic
- Modernization
- Enablers
- Accelerators

^ Unlock potential. Sherpas
Guide you, go back to get others
You still have to do the walking

---

# Agenda: Shift Left with Review Culture
- Part 1: 45 min: Why Review
- Part 2: 15 min: Provide for Review
- Lunch break
- Part 3: 45 min lab: Give Reviews

---

# From Startup To Google

---

# Compromises in a Start-Up
- Intentional (market pressure)
- Unintentional (bad outcomes)

^ worked at small video streaming startup.
There would be quality issues in code
Some caused by pressure, some caused by incomplete understanding

---

# Acquisition
- Operate inside a tech org with high standards
- Leadership replaced by Googlers
- QA removed
![fit inline 200%](http://mdialog.com/images/hero-headline.png)

^ Pushed down accountability
Engineers became accountable for quality

---

# Code Purple
- "Code Health"
- Changes to code caused customer impact frequently

^ Because the code had so many compromises
It was very difficult to meet Google's expectations
This talk demonstrates my learnings about
cost and quality

---

# What I learned while making software "Google Stable"
![fit inline](http://k1tbfzr5bv4catkk35y5rozn.wpengine.netdna-cdn.com/wp-content/uploads/2015/04/the-secret-book-cover-img.png)

^ We had to take our code and make it live up to google's repultation
All of the important changes we made were to the way we work
It's like the book the secret - that there is some hidden law that rules the universe that people are unaware of.

---

# Complexity Then < ~1975
![fit inline](https://upload.wikimedia.org/wikipedia/commons/2/26/Punched_card_program_deck.agr.jpg)

^ All batch processing
Cobol 72 characters. Fortran 80 characters
Decks of cards form logic and data
Operator would run a stack of cards with logic, and then a stack for data
It would spit out results

---

# Complexity Now
![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1487459956/Screen_Shot_2017-02-18_at_6.17.28_PM_mjoyqu.png)

---

# Specialization and Complexity
![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1487459663/Screen_Shot_2017-02-18_at_6.12.48_PM_ehhorg.png)

^ 80s we used if statements and for loops to play chess
Today we use neural networks to play Go
The problems we solve today require specialization
so teams are needed to make any progress at all

---

# Brain Complexity Over Time
![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1487718005/Screen_Shot_2017-02-21_at_5.58.21_PM_mevsvh.png)

^ It takes more people and more complex code
but our brains can still only fit so much
Our practices are not accounting for the increasing complexity of software fast enough
Need to assume people will make errors

---

# The Secret

---

# False Economies of Speed
> In economics, a false economy is an action that saves money at the beginning but which, over a longer period of time, results in more money being spent or wasted than being saved.

---

# Cost of late defects (IBM Systems)
![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1487552131/Screen_Shot_2017-02-19_at_7.53.49_PM_eholzy.png)

^ Cited differently
5x the cost for most software
NIST 2002 says 15hr for defect in production vs 5 in development

---

# "Shift Left"
![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1487555071/Screen_Shot_2017-02-19_at_8.18.46_PM_okmibp.png)

---

# Review Isn't Just Code
- Each Step of a Feature's Life
  - Design Documents
  - Code
  - Test Plan
  - Release Plan
  - Failures ("Blame-free PostMortem")

^ It takes enablement, review, discussion
Review the review process

---

![fit right](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1487719809/Screen_Shot_2017-02-21_at_6.27.39_PM_lzvcq2.png)
# Carrying Cost of Poor Code Health
> In retrospect, I would have done it differently.
> Just release it - we'll refactor it later.

^ Google talks about code health. Most people use the term technical debt.

---

# Other Positive Effects

> It's not just for breakfast anymore!

---

# Normalizing knowledge
![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1487722100/Screen_Shot_2017-02-21_at_7.06.43_PM_yy0vzu.png)

^ Sr engineers should review design documents and code
Most critical pieces of system design sr engineers should produce
Knowledge will move to less experienced developers
Then it continues to move through generations of staff

---

# Ego Factor
> I Know someone will review my docs, code...
I better check it myself.

---

# Performance Evidence
![fit inline](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1487723423/Screen_Shot_2017-02-21_at_7.27.59_PM_rqvgi3.png)

^ In google review processes, people use this evidence to produce reports showing the level they are operating at
Careful - Metrics change behavior
Number of comments or size of documents is not significant

---

# Part 2: How to Provide for Review

---

# Efficiency (inputs vs outputs)
- Input Quality
  - design docs
  - change sets
  - hours in review
- Affects Outputs Quality
  - defects found
  - quality improvement

^ This section will discuss the quality of a review

---

# Intention
- Code's intention should be clear
- Describe in commit msg & ticket
- Problem, solution

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1484325455/nist-review-data.gif)
# Size
- Most important metric
- Beyond 400 LOC, defect detection drops off

^ We talked about complexity vs brain
Brain can't hold much information in context

---

# Design Docs
- Design docs should undergo their own review
- Small docs for small changes
- Change Set -> Ticket -> Design Doc

---

# Tests
- Tests are documentation
- Behavioral changes described in tests

---

# Self Review, Annotate
- Review your code before providing
- Leave guiding comments

---

![fit left](http://res.cloudinary.com/dnlfzsmnm/image/upload/v1487728865/Screen_Shot_2017-02-21_at_8.59.13_PM_ozkinm.png)
# Atomic Changesets
- Make small changes against the root
- Use config/feature toggles
- Review and merge before stacking

^ Stacked reviews are wasteful

---

# Part 3: Lab: How to Review

---

# Speed
- Less than 500 LOC/hour

---

# Time
- Less than 60 minutes at a time

---

# Attitude
- Foster Positivity
- Make suggestions, not commands
- Ask clarifying questions

---

# ASSUME I'M A NOOB

---

# How we will work
- Create a google doc
- Copy the PR (if you like)
- Leave comments in google docs
- Share with jason.goodwin@redelastic.com
- All code in Java w/ Play Framework

^ Demonstrate

---

# My Expectations
- Jr point out bugs
- Intermediate point out code design
- Sr point out system design

---

# Structure
- Clear intention
- Code smells
  - https://sourcemaking.com/refactoring/smells
- Too large

---

# Part 1 - Before review warmup
##`goo.gl/eDt0dY`
- Don't try to compile this one
- Focus on general review feedback

---

# Part 1 - Debrief
- Speculative generality
- Too large, multiple intentions
- Lack of unit testing
- Insane packaging
- Doesn't compile

---

# Clarity
- Should be clear history of the change
- Things should be named logically
- General design

---

# Tactful Feedback
- Make suggestions, not commands
- Ask clarifying questions, expect better code in response

---

# Part 2 - Clarify
## `goo.gl/Yj19wB`
- Design doc provided (see readme)
- Share your doc with jason.goodwin@redelastic.com

---

# Part 2 - Debrief
- unclear class names
- bad test names
- no error handling
- missing log level `TRACE`
- API calls GET to set the level.
  - why is it /level?

---

# Be Critical Too
- Ask questions of the design
- Be critical, think through edge cases

---

# Part 3 - Junior's Submission
## `goo.gl/uLHlbs`
- Design doc provided (see readme)
- Share your doc with jason.goodwin@redelastic.com

---

# Part 3 - Debrief Design
- in memory - should be in store for HA
- assumes sticky sessions possible

---

# Part 3 - Debrief API
- Not RESTful API
- Incorrect response codes
- Incorrect Response Type

---

# Part 3 - Debrief Code
- Commit message not clear
- multiple threadsafety issues
- global state - should have dependency injection of the behavior
- ttl is hardcoded - should use DI or read from config

---

# Part 3 - Debrief Design
- Why would we do it in memory? 
