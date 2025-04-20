# jPI

Calculation of PI out of counting pixels.

I'm not a mathmatician, and I've always liked to have a simple way to
calculate PI. The problem is there isn't that easy way,
since we are actually trying to get a precision of 4 digits
of a little bit more than 3.

## Equation

The equation to calculate PI from the area of a circle A of radius R.

```
A = PI r r
PI = A / (r r)
```

From that equation, we can easily calculate the value of PI.
The objective is to paint a circle, and count the pixels inside it,
which would be A, while R is the radius in pixels.

## The method

The way is to count pixels in a screen (a canvas in a Java application),
after drawing it. An obvious optimization is to draw a fourth of the circle.
The following would be a fourth of a circle of radious 4.

|0|1|2|3|4|
|-|-|-|-|-|
|0| | | |X|
|1| | |X|X|
|2| |X|X|X|
|3|X|X|X|X|
|4|X|X|X|X|


The hypothesis is that, if you create a big enough circle, the calculation
of PI will converge in 3.1416.
