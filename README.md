# CustomView
We can create Custom View By 
1. Extending Existing ViewGroup: 
  - In this way we dont need to care about STATE PERSISTENCE of the viewGroup because the parent Class also have implementation of it.
  - We do this when we want to combine some layout and want them to work in a linear and sync manner.
  - Generally we extends ViewGroups and use <merge> tag in layout so that we can eliminate extra viewGroup
  
 2. Creating View from Scratch(not actually from scratch but by extending View class)
   - In this way we have to extend View class and create our own view from scratch.
   - We have to manage the layout measurements(height and width) which is the complicated thing
   - We need to care about the STATE PERSISTENCE of the viewgroup.
   - We need to do this when we want more flexibility which we can't get by combining the views.
   
NOTE: In this repo we will talk about the First Way.
