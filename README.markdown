#PROJECT PYTHAGORAS#
###William Ganucheau | [williamg.me](http://williamg.me)###

This project is my attempt at building a robust math utility.

##CURRENT FEATURES:##
****
- Simplifies expressions (addition, subtraction, multiplication, and division)
- Parenthetical expressions

##STILL TO BE ADDED:##
****
- More (basic) function: exponents, radicals, etc. 
- Simplifying algebraic expressions
- Solving algebraic equations
- Probably a lot more

##VERSION OVERVIEW:##
****
**v0.0.1**
First commit, very raw. Addition and subtraction only.

**v0.0.2**
Still not superior code. Lots of cleaning up to do. Added support for multiplication and division. Support for parenthetical expressions coming soon. Tweaked the function of the Parser class. Now, it is solely static methods that parse strings into the different subcategories (initial input, expressions, terms)

**v0.0.3**
Reorganized the code. Now there is a subfolder "types" that holds each possible type of input. This organization allows for the much easier addition of new types and functions. I also added support for parenthetical expression and improved input and expression parsing.