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

## Week4 Reflection: 8Puzzle(100/100)

* Immutability of reference vars could be realized by putting final. For array type, defensive copying is still a better way to keep it immutable.
* The memory trick: 1d-array board cannot be realized directly by revising the constructor because of the grading system will detect it. Instead, by making use of overloading we can relize 1d array constructor for my implementation.
* Tricky part of moves: Understanding the algorithm in the game tree will actually fall backward, so adding move + 1 in each loop will cause problematic counts in the end. Caching moves as a instance variable of Node is the way to keep track of the number correctly.
* Basic linked list structure applied on node is the valid way to track back the solution board steps.
* Save more memory space by implementing in only one PriorityQueue. I did not do the optimization but still passed the grader.
    
## Week5 Reflection: KdTree(100/100)
* The number of instance variable can be reduced. However with my setting it is still scalable. 
* Contain() of KdTree: As long as the point is found, return.
* Range() of KdTree: Does not implement the suggestion improvement with line intersection. With correct implementation of rectangular interesaction, it is still possible to pass the grading system.
* Nearest() of KdTree: The one took me longest to implement. Be cautious to the caveat contain() in RectHV. Using x-axis and y-axis to decide the location of the query point(rt or lb) will be easier and faster.
* Plot of range search and nearest neighbor KdTree
<p align="middle">
  <img src="https://github.com/charlesfu4/Algorithm_I_Princeton/blob/master/Week5_Kdtree/KdTree_plot.png", height = 300px>
</p>
