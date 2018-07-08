# Passed tests


## Die tests
### Shade
+ Coherent assignement of shade from die tests

## Window Pattern Card tests
### Cell
+ Empty test
+ Die placement test
+ Rule set update test
+ Default restriction test (the one given at creation)
+ Remove die test

### ColorRestriction
+ Die respecting color restrictions test

### ValueRestriction
+ Die respecting value restrictions test

### WindowPatternCard
+ Die insertion test (just puts a die in the glass window)
+ First die placement rule test
+ Impossibility to put the die on an occupied cell test
+ Adjacence test
+ Die respecting the cell rule test
+ Whole placement test


## Objective Card tests
### PrivateObjectiveCardsDeck
+ Parsing test
+ Drawing test
+ Every card is tested

### PrivateObjectiveCard
+ Card parsing test (attribute coherence)
+ Score computation by the private objective card given a window pattern card.
+ Overridden equals(Object o) test
+ Overridden toString() test
+ Every card is tested


## Tool Cards tests
### ChooseValueEffect
+ User input coherence (increase and decrease methods) test

### OppositeValueEffect
+ User input coherence (computeOppositeValue method) test
