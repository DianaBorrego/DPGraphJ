This problem is a variant of the equipment replacement problem, except that in each unit of 
time, the possible decisions are to keep the part or replace it with another of any possible 
age, not necessarily new.

Therefore, this problem seeks to determine an optimal replacement policy for a single piece 
of equipment of age i over a time horizon of n units. We assume:

- The part can have, at most, a life of m years after which it can no longer be in use.
- The annual maintenance cost is c(i).
- The purchase price for a unit is t(i). Where t(0) is the price of (buying) a new unit and 
  t(m) is the residual (selling) price at the end of its operating life.
- The initial age is e_0.
- At each time unit, the possible decisions are M, C (keep, change) consisting of keeping 
  the part at the current instant if possible or changing it for a piece that can be of any
  age, it does not have to be new.