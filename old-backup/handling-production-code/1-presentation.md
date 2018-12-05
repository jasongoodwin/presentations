theme: Letters from Sweden, 2
slidenumbers: true

# Productionizing Basics - Code Handling 

**Questions?**

Contact your instructors at:

_W:_ http://redelastic.com

_E:_ jason.goodwin@redelastic.com

_E:_ kevin.webber@redelastic.com

---

# Agenda: Slinging Scala In Prod
- Semantic Versioning (Semver) 
- Avoid formatting headaches with scalariform plugin
- Enforced Code Reviews for Stability

---

# Semantic Versioning

---

# Why Everyone Needs to Know Semver
- Communication Tool Across the Org 
- Describes a change's scope and backwards-compatability at a glance
- Relevant for Libraries or Services

---

# What is Semver
  - Three numbers major.minor.patch (eg 0.0.1)
  - Additionally can contain a postfix (0.0.1-RC1, 0.0.1-SNAPSHOT)
  - See semver.org

---

# 0.0.x - Patch version  
- Third number
- Bump for bug-fixes, patching
  - Should not be noticed by consumers of the lib/service 
  - No addition of features - only correction of small issues

---

# 0.x.0 - Minor version 
- Second number
- Includes addition of features 
- Should NOT break consumers of the library/service 
  - e.g. Deprecate old functionality
  - Introduce new message, maintain support for old one

---

# x.0.0 - Major version
- First number
- Breaking changes 
  - Requires consumers of the lib/service to
  - No addition of features - only correction of small issues
  - Remove deprecated code
  - Remove support for old schemas

---

# 0.0.0-x - Additional Info
- 0.0.0-SNAPSHOT means a developer is hacking. 
  - Apart from SNAPSHOTs, once a version leaves your machine it NEVER CHANGES 
- When testing for release, use 0.0.0-RC#, (e.g. 0.0.0-RC2) 
  - Every time bugs are found, bump the RC
  - When ready to release, drop -RC#

---

# Scalariform in SBT

---

![right](https://cdn.meme.am/cache/instances/folder236/250x250/71994236.jpg)
# What is the issue
- People can check in spaces, tabs, whitespace
  - While working
  - Or reading 
- Un-neccessary time resolving merge conflicts

---

# The solution
- Plugin the scalariform plugin and configuration to each _project_
- Ensures all code formatted to your standard of choice 
- See project: www.github.com/mdialog/smoke

---

# How it works
- Developer makes change
- Developer runs unit test
- (Scalariform reformats code)
- Developer commits code
- Reviewer will run tests again and catch any formatting issues 

---

# Enforced Code Reviews

---

# Multiple flows for code review
- Fork/PR or External Reviews (Crucible)
- Over-the-shoulder
- XP/Pairing
- Codereview infront of SCM (Gerrit)

---

![fit right](https://www.isixsigma.com/wp-content/uploads/images/stories/migrated/graphics/604a.gif)
# Cost/Benefits Of Code Review
- "False economy" 
  - saves time and money
- Catch Defects
- Keep code "healthy" (easy/fast/cheap to build on)

---

# Knowledge Sharing
- New guy can take on hard features
- Experenced designers ensure his commit is readable
- Domain experts ensure the change is correct 

---

# Recommendation: Gerrit
- Ensures all code is reviewed
- Benefit from Google's internal processes
- Ensures the team produce higher quality change sets
- Facilitates knowledge-sharing
- Fix more defects at less cost

---

# QA
