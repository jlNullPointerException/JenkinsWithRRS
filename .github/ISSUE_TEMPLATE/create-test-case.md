---
name: Create Test Case
about: Use this template to create Test Case
title: TC_XX.YYY.ZZ | Epic Name > User Story Name > Test Case Name
labels: TC
assignees: ''

---

#### TC_XX.YYY.ZZ | Epic Name > User Story Name > Test Case Name

#### Test Objective: Verify that helper tooltips are displayed when hovering over the question mark icons.

#### Preconditions: 
1. Jenkins is up and running.
2. At least one other project exists in Jenkins.

#### Test Steps:
1. Navigate to the Jenkins Dashboard.
2. Click on “New Item” in the left-hand menu.
3. Enter "New Project" a valid name for the project.
4. Select the “Freestyle project” option.
5. Click “OK” to proceed to the project configuration page.
6. Scroll to the “Build Triggers” section.
7. Hover over the question marks beside each trigger option.

#### Expected Result: 
1. A tooltip appears with contextual help for each trigger.

#### Acceptence Criteria:
The helper tooltip appears when the mouse cursor hovers over the question marks.
