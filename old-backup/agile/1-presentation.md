theme: Sketchnote, 7
build-lists: false
slidenumbers: true
autoscale: true

---

# Agile at Funnelcloud

---

# Goals 
- Level-set
- Lay Out a Day 1 plan
- Envision a down-the-road look

---

# What is Agile?
- Generic (no one way)
- Set of ideals around software delivery

---

# Agile Manifesto
- Individuals and interactions over processes and tools

^ We focus on our personal interactions over processes

---

# Agile Manifesto
- Individuals and interactions over processes and tools
- Working software over comprehensive documentation

^ Focus on delivery - do the minimal ceremony we need to do to get software out

---

# Agile Manifesto
- Individuals and interactions over processes and tools
- Working software over comprehensive documentation
- Customer collaboration over contract negotiation

^ Try to work direct with customers to understand their needs

---

# Agile Manifesto
- Individuals and interactions over processes and tools
- Working software over comprehensive documentation
- Customer collaboration over contract negotiation
- Responding to change over following a plan

^ The plan will change. 
^ Break big problems into small ones, take them through to completion, then adjust sprint to sprint as needed
^ I tried to produce project plans in my earlier days but SEC moved to summer, we had deco come up etc.

---

# Activities are Continuous
- Nothing starts and nothing ends

![inline fill](http://www.agilenutshell.com/assets/how-is-agile-different/continuous-activities.png)

---

# Development is Iterative
- Break big things into small things
- Try to get the small things done to completion

![inline fit](http://www.agilenutshell.com/assets/how-is-agile-different/iterative-development-final.png)

--- 

# Planning is Adaptive
- By breaking things into small pieces, 
- We can adjust what is being worked on to adapt

![inline fit](http://www.agilenutshell.com/assets/how-is-agile-different/adaptive-planning.png)

---

# Roles Blur
- Everyone does a part of everything
- We all contribute together toward the goal of delivery
- Nobody individually fails - the team fails

![inline fit](http://www.agilenutshell.com/assets/how-is-agile-different/rolesblur.png)

^ This is a really important core value
^ People have to figure out how to work together to meet their goals

--- 

# Core Concepts

---

# Stories
- Story: "As a <type of user>, I want <some goal> so that <some reason>."
- Eg: "As an operator, I can hold a part so that I may perform quality inspection."
- Do what works for us today. We can adapt later.

---

# Epics
- Collections of stories that represent a feature too large to put in a story

---

# Acceptance Criteria/Conditions of Satisfaction
- Can hang off of stories
- Description of characteristics of the solution
- Used to produce test cases

---

# Acceptance Criteria
- Negative scenarios
- Impact on other features
- Non-functionals (performance, security)
- UX-concerns

---

# Backlog
- A prioritized list of "stories" (eg tickets for us today)
- Responsibility of "Product Owner" to prioritize

![inline fit](https://carloselopez.files.wordpress.com/2011/08/master_story_agile.jpg)

^ Brent will play the role of product owner
^ Stories are assigned to a sprint
^ product should be ready to ship at the end

---

# Sprints/Iterations
- Every two weeks we define all deliverable "stories"
- Team works to finish the work to 100% complete

^ I'll talk about complete shortly because it's an important point

---

# Team Focus
- We have to organize together to figure out how to get there
- No specific roles or rules

---

# The players
- Everyone is a specialized generalist
- We are all responsible for testing, documentation, etc.

![fit inline](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1514991364/Screen_Shot_2018-01-03_at_9.54.44_AM_zbpdt9.png)

---

![fit inline](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1514991364/Screen_Shot_2018-01-03_at_9.54.35_AM_zlcwkl.png)

---

![fit inline](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1514991364/Screen_Shot_2018-01-03_at_9.55.04_AM_hb9lxu.png)

---

![fit inline](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1514991364/Screen_Shot_2018-01-03_at_9.55.10_AM_mpm3jt.png)

---

![fit inline](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1514991364/Screen_Shot_2018-01-03_at_9.55.17_AM_jkookq.png)

---

![fit inline](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1514991364/Screen_Shot_2018-01-03_at_9.55.24_AM_al261g.png)

---

![fit inline](http://res.cloudinary.com/dz2kvnrvc/image/upload/v1514991365/Screen_Shot_2018-01-03_at_9.55.30_AM_vwcy8n.png)

---

# Definition of Done
- 70% of code coverage from Unit Test
- Automated tests for the User Story are created and pass
- Performance concerns identified and discussed where necessary
- Software is running in the server to be defined by the team (optimally in pre-production)
- Code review by peer (+2)
- Technical documentation updated
- User documentation updated
- Acceptance criteria is met (specifications tested)

^ Probably a Release plan step by step too

---

# Code Review
- +1: Looks okay to me but someone else should review
- +2 (LGTM): I understand the problem and codebase and I'm confident in this change.
- Readability: This conforms to the style expectations.
- Test Coverage: Is the testing sufficient to demonstrate the behavior?

^ Commenting on whitespace is not asinine.
^ Code without indentation has 20% lower comprehension. Code with 6 spaces of indentation is almost as bad.
^ Better to take an extra hour now than introduce a bug later because someone didn't understand the code

---

# Code Review
- Good for finding design problems and style issues.
- Review your own code before asking someone else to review your code.
- Comment on the decisions to help clarify for the reader.

^ It takes hours to review code and doesn't find as many bugs as testing.
^ Most issues are found if you review your own code first

---

# Testing and Continuous Integration
- "Testing" should be a core component
- May need to scope in ops tasks to make testing easier
- Do whatever it takes to get it confirmed early and often

^ Good start to testing. We have signal flow etc.
^ Often testing gets crushed at the end
^ Needs to be a core component of the entire lifecycle

---

# Retrospective
- Meet. Uncover: 
  - What went well 
  - What didn't
  - Vote for an item to focus on improving
  
---

# We have to adjust the plan
- As a young company, this is hard, 
- But we should strive to get there

![inline fit](http://www.agilenutshell.com/assets/definitions/management-by-miracle.png)

---
