The problem is stated as follows: 
You are given x units of a resource and told that this resource must be distributed among 
n activities. You are also given n functions r_i(x)=r(x,i) (for i = 1, · · · , n and 
x = 0, 1, · · · , X) representing the return realized from an allocation of x units of 
resource to activity i. Further, assume that r_i(x) is a nondecreasing function of x. 
So x1≤x2 → r_i (x1)≤r_i (x2). The problem is to allocate all of the x units of resource 
to the activities so as to maximize the total return, i.e. to choose n nonnegative 
integers x_i , 0 = 1, · · · , n-1, that maximize 
	∑_(i=0)^(n-1) r_i (x_i)
subject to the constraint 
	∑_(i=0)^(n-1) x_i = X