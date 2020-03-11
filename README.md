# Algorithm_I_Priceton
## Week1 Reflection: Percolation(90/100)
* Full site should be **open** as well.
* Full site can be influenced after percolation:
  - After percolation the bottom node and top node will be connected. So the site which is not full but connected to bottom node will be full after percolation.
* Corner Case should be considered. Ex: when there is only one block top row and bottom row is union together, we should not take this situation as percolastion unless the block is opened.
* Be aware of invalid arguments in constructor and method and remember to throw exception the requirement asked.
* The grid I design is basically (n+2) times (n+2)(in order to programming it easier) so the default throwing IllegalArgumentExcpetion is not compatiable here. The tester on coursera will go wrong.

## Week2 Reflection: Queue(95/100)
* Memory issue: Still need to implement array shifting in Deque: I applied array expansion by detecting index of head and tail. Therefore, the space will be waster if either side is not full.
* Visualize array operation and write customized tester is the best way to debug.
* RandomizedQueue: Be aware of iteration part. Item array is called by reference, therefore, do not implement operation on private instance which refenerence the origin item array. Create permutation walklist to avoid this problem. 
```java
private int[] walklist = StdRandom.permutation(N);
```
## Week3 Reflection: Collinear(100/100) 

* Avoid subsegments and replicate segments: every segment has end points, the logical way to do this will be if and only if the p is an end point(here I picked the smallest in natural order), we construct the segment.
* Version 1: Two for loops, one index for increments in inner loop to walk through elements that have same slopes. Without sorting the array beforehand according their natural order, I have to sort the segments contains all points on a same line everytime I find a segment in order to verify p and avoid subsegments.
  - Drawback: need to creat extra array for sorting points in segment for comparison, unnecessary computation if I can know if the origin point(p) is not the smallest in the beginning.
* Version 2: One for loop. Sort the points array first by natural order outside the for loop, save it and then sort it according to slope p to q in the for loop.(Notice the stability of natural order only hold for next sort of slope, saving it outside the for loop save 50% O(NlgN) in sorting).In the for loop, we have two indexes head and tail. Hold head until tail find the end of points with same slope and or the end of points array to walk through. If the p is smaller the first elements in q's, we then form the segment. This implementation do not need any extra array for storing parallel points on one line, especially efficient.
* Pitfall: I did not realize the resizing array for storing segment occurs everytime I find a segment. Remember the lesson from last chapter. Resizing the array in double capacity can reduce the overhead of resizing them tightly everytime when adding new elements from O(N^2) to O(NlnN).
