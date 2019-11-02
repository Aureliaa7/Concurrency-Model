# Concurrency-Model

Find all prime numbers in [1,n] using k threads. Let n =
kq + r, where 0 ≤ r <k where r is the remainder of n divided to k. You should
consider 2 solutions:

- Partition the interval [1,n] in k intervals as follows: I1=[1,q+1], I2=[q+2,
2q+2], . . . , Ir=[(r-1)q+r, rq+r], Ir+1=[rq+r+1, (r+1)q+r], ..., Ik=[(k-1)q+r+1,
kq+r]. Each thread 1 ≤ j ≤ k will determine the prime numbers in the Interval
Ij .
- The multiples of k+1 strictly bigger than k+1 are not prime numbers. These
numbers should be eliminated from the interval [1,n] resulting in set M. This
set should be partitioned in k subsets as follows: for each 1 ≤ j ≤ k the set Mj
contains all those elements from M who by divided with k+1 give remainder j.
Also it is considered that k+1 belongs to M1. Each thread j will determine the
prime numbers from set Mj.
