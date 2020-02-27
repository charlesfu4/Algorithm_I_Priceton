# Algorithm_I_Priceton
## Week1 Reflection: Percolation
* Full site should be open as well.
* Full site can be influenced after percolation:
  ** After percolation the bottom node and top node will be connected. So the site which is not full but connected to bottom node will be full after percolation
* Corner Case should be considered. Ex: when there is only one block top row and bottom row is union together, we should not take this situation as percolastion unless the block is opened.
* Be aware of invalid arguments in constructor and method and remember to throw exception the requirement asked.
* The grid I design is basically (n+2) times (n+2)(in order to programming it easier) so the default throwing IllegalArgumentExcpetion is not compatiable here. The tester on coursera will go wrong.
